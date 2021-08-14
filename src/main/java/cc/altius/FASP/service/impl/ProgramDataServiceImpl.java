/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.NotificationUser;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ReviewedProblem;
import cc.altius.FASP.model.ShipmentSync;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.SupplyPlanCommitRequest;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.SupplyPlanCommitRequestInput;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;

/**
 *
 * @author altius
 */
@Service
public class ProgramDataServiceImpl implements ProgramDataService {

    @Autowired
    private ProgramDataDao programDataDao;
    @Autowired
    private ProgramService programService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private AclService aclService;
    @Autowired
    private EmailService emailService;

    @Override
    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser, boolean active) {
        ProgramData pd = new ProgramData(this.programService.getProgramById(programId, curUser));
        pd.setRequestedProgramVersion(versionId);
        pd.setCurrentVersion(this.programDataDao.getVersionInfo(programId, versionId));
        versionId = pd.getCurrentVersion().getVersionId();
        pd.setConsumptionList(this.programDataDao.getConsumptionList(programId, versionId));
        pd.setInventoryList(this.programDataDao.getInventoryList(programId, versionId));
        pd.setShipmentList(this.programDataDao.getShipmentList(programId, versionId, active));
        pd.setBatchInfoList(this.programDataDao.getBatchList(programId, versionId));
        pd.setProblemReportList(this.problemService.getProblemReportList(programId, versionId, curUser));
        pd.setSupplyPlan(this.programDataDao.getSimplifiedSupplyPlan(programId, versionId));
        pd.setPlanningUnitList(this.programDataDao.getPlanningUnitListForProgramData(programId, curUser));
        return pd;
    }

    @Override
    public List<ProgramData> getProgramData(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser) {
        List<ProgramData> programDataList = new LinkedList<>();
        programVersionList.forEach(pv -> {
            ProgramData pd = new ProgramData(this.programService.getProgramById(pv.getProgramId(), curUser));
            pd.setRequestedProgramVersion(pv.getVersionId());
            pd.setCurrentVersion(this.programDataDao.getVersionInfo(pv.getProgramId(), pv.getVersionId()));
            int versionId = pd.getCurrentVersion().getVersionId();
            pd.setConsumptionList(this.programDataDao.getConsumptionList(pv.getProgramId(), versionId));
            pd.setInventoryList(this.programDataDao.getInventoryList(pv.getProgramId(), versionId));
            pd.setShipmentList(this.programDataDao.getShipmentList(pv.getProgramId(), versionId, false));
            pd.setBatchInfoList(this.programDataDao.getBatchList(pv.getProgramId(), versionId));
            pd.setProblemReportList(this.problemService.getProblemReportList(pv.getProgramId(), versionId, curUser));
            pd.setSupplyPlan(this.programDataDao.getSimplifiedSupplyPlan(pv.getProgramId(), versionId));
            pd.setPlanningUnitList(this.programDataDao.getPlanningUnitListForProgramData(pv.getProgramId(), curUser));
            programDataList.add(pd);
        });
        return programDataList;
    }

    @Override
    public int saveProgramData(ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException {
        Program p = this.programService.getProgramById(programData.getProgramId(), curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthArea().getId(), p.getOrganisation().getId())) {
            programData.setCurrentVersion(p.getCurrentVersion());
            return this.programDataDao.saveProgramData(programData, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public void processCommitRequest(CustomUserDetails curUser) {
        this.programDataDao.processCommitRequest(curUser);
    }

    @Override
    public List<SupplyPlanCommitRequest> getSupplyPlanCommitRequestList(SupplyPlanCommitRequestInput spcr, CustomUserDetails curUser) {
        return this.programDataDao.getSupplyPlanCommitRequestList(spcr, curUser);
    }

    @Override
    public Version executeProgramDataCommit(int commitRequestId, ProgramData programData) throws CouldNotSaveException {
        CustomUserDetails curUser = new CustomUserDetails();
        curUser.setUserId(1);
        Program p = this.programService.getProgramById(programData.getProgramId(), curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthArea().getId(), p.getOrganisation().getId())) {
            programData.setCurrentVersion(p.getCurrentVersion());
//            System.out.println("++++" + p.getCurrentVersion());
            Version version = this.programDataDao.executeProgramDataCommit(commitRequestId, programData, curUser);
//            System.out.println("version++++" + version);
            try {
                getNewSupplyPlanList(programData.getProgramId(), version.getVersionId(), true, false);
                if (programData.getVersionType().getId() == 2 && version.getVersionId() != 0) {
                    List<NotificationUser> toEmailIdsList = this.programDataDao.getSupplyPlanNotificationList(programData.getProgramId(), version.getVersionId(), 1, "To");
                    List<NotificationUser> ccEmailIdsList = this.programDataDao.getSupplyPlanNotificationList(programData.getProgramId(), version.getVersionId(), 1, "Cc");
                    StringBuilder sbToEmails = new StringBuilder();
                    StringBuilder sbCcEmails = new StringBuilder();
                    if (toEmailIdsList.size() > 0) {
                        for (NotificationUser ns : toEmailIdsList) {
                            sbToEmails.append(ns.getEmailId()).append(",");
                        }
                    }
                    if (ccEmailIdsList.size() > 0) {
                        for (NotificationUser ns : ccEmailIdsList) {
                            sbCcEmails.append(ns.getEmailId()).append(",");
                        }
                    }
                    if (sbToEmails.length() != 0) {
                        System.out.println("sbToemails===>" + sbToEmails == "" ? "" : sbToEmails.toString());
                    }
                    if (sbCcEmails.length() != 0) {
                        System.out.println("sbCcemails===>" + sbCcEmails == "" ? "" : sbCcEmails.toString());
                    }
                    EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(6);
                    String[] subjectParam = new String[]{};
                    String[] bodyParam = null;
                    Emailer emailer = new Emailer();
                    subjectParam = new String[]{programData.getProgramCode()};
                    bodyParam = new String[]{programData.getProgramCode(), String.valueOf(version.getVersionId()), programData.getNotes()};
//                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "shubham.y@altius.cc,harshana.c@altius.cc", "palash.n@altius.cc,dolly.c@altius.cc", subjectParam, bodyParam);
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), sbToEmails.length() != 0 ? sbToEmails.deleteCharAt(sbToEmails.length() - 1).toString() : "", sbCcEmails.length() != 0 ? sbCcEmails.deleteCharAt(sbCcEmails.length() - 1).toString() : "", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                }

                return version;
            } catch (ParseException pe) {
                throw new CouldNotSaveException(pe.getMessage());
            }
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<SimpleObject> getVersionTypeList() {
        return this.programDataDao.getVersionTypeList();
    }

    @Override
    public List<SimpleObject> getVersionStatusList() {
        return this.programDataDao.getVersionStatusList();
    }

    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser) {
        return this.programDataDao.getProgramVersionList(programId, versionId, realmCountryId, healthAreaId, organisationId, versionTypeId, versionStatusId, startDate, stopDate, curUser);
    }

    @Override
    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, String notes, CustomUserDetails curUser, List<ReviewedProblem> reviewedProblemList) {
        return this.programDataDao.updateProgramVersion(programId, versionId, versionStatusId, notes, curUser, reviewedProblemList);
    }

    @Override
    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId) {
        return this.programDataDao.checkErpOrder(orderNo, primeLineNo, realmCountryId, planningUnitId);
    }

    @Override
    public SupplyPlan getSupplyPlan(int programId, int versionId) {
        return this.programDataDao.getSupplyPlan(programId, versionId);
    }

    @Override
    public List<SimplifiedSupplyPlan> getNewSupplyPlanList(int programId, int versionId, boolean rebuild, boolean returnSupplyPlan) throws ParseException {
        return this.programDataDao.getNewSupplyPlanList(programId, versionId, rebuild, returnSupplyPlan);
    }

    @Override
    public List<SimplifiedSupplyPlan> updateSupplyPlanBatchInfo(SupplyPlan sp) {
        return this.programDataDao.updateSupplyPlanBatchInfo(sp);
    }

    @Override
    public int updateSentToARTMISFlag(String programVersionIds) {
        return this.programDataDao.updateSentToARTMISFlag(programVersionIds);
    }

    @Override
    public ShipmentSync getShipmentListForSync(int programId, int versionId, int userId, String lastSyncDate, CustomUserDetails curUser) {
        ShipmentSync ss = new ShipmentSync();
        ss.setProgramId(programId);
        ss.setVersionId(versionId);
        ss.setUserId(userId);
        ss.setShipmentList(this.programDataDao.getShipmentListForSync(programId, versionId, lastSyncDate));
        ss.setBatchInfoList(this.programDataDao.getBatchListForSync(programId, versionId, lastSyncDate));
        ss.setProblemReportList(this.problemService.getProblemReportListForSync(programId, versionId, lastSyncDate));
        return ss;
    }

    @Override
    public boolean checkNewerVersions(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser) {
        boolean newer = false;
        ListMultimap<Integer, Integer> programMap = LinkedListMultimap.create();
        for (ProgramIdAndVersionId pv : programVersionList) {
            programMap.put(pv.getProgramId(), pv.getVersionId());
        }
        for (int programId : programMap.keySet()) {
            Integer versionId = programMap.get(programId).stream().mapToInt(v -> v).max().orElse(-1);
            if (versionId != -1) {
                Program p = this.programService.getProgramById(programId, curUser);
                if (p.getCurrentVersion().getVersionId() > versionId) {
                    newer = true;
                }
            }
        }
        return newer;
    }

    @Override
    public List<ProgramIntegrationDTO> getSupplyPlanToExportList() {
        return this.programDataDao.getSupplyPlanToExportList();
    }

    @Override
    public boolean updateSupplyPlanAsExported(int programVersionTransId, int integrationId) {
        return this.programDataDao.updateSupplyPlanAsExported(programVersionTransId, integrationId);
    }

    @Override
    public int getLatestVersionForProgram(int programId) {
        return this.programDataDao.getLatestVersionForProgram(programId);
    }

    /**
     *
     * @param programId ProgramId that you want to send the Notification for
     * @param versionId VersionId of the SupplyPlan you want to send the
     * Notification for
     * @param statusType if the Supply Plan has just been committed
     * statusType=1, If the Supply Plan has been approved statusType=2, If the
     * Supply Plan has been rejected statusType=3
     * @param toCc "To" if you want the To list and "Cc" if you want the cc list
     * @return List of the people the Notification needs to be sent to
     */
    @Override
    public List<NotificationUser> getSupplyPlanNotificationList(int programId, int versionId, int statusType, String toCc) {
        return this.programDataDao.getSupplyPlanNotificationList(programId, versionId, statusType, toCc);
    }

    @Override
    public String getLastModifiedDateForProgram(int programId, int versionId) {
        return this.programDataDao.getLastModifiedDateForProgram(programId, versionId);
    }

}
