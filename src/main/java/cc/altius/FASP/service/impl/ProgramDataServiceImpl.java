/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.DatasetData;
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
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.ActualConsumptionDataInput;
import cc.altius.FASP.model.report.ActualConsumptionDataOutput;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.ProgramDataService;
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
    private ProgramDao programDao;
    @Autowired
    private ProgramCommonDao programCommonDao;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private AclService aclService;

    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser, boolean shipmentActive, boolean planningUnitActive) {
        ProgramData pd = new ProgramData(this.programCommonDao.getProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser));
        pd.setRequestedProgramVersion(versionId);
        pd.setCurrentVersion(this.programDataDao.getVersionInfo(programId, versionId));
        versionId = pd.getCurrentVersion().getVersionId();
        pd.setConsumptionList(this.programDataDao.getConsumptionList(programId, versionId, planningUnitActive));
        pd.setInventoryList(this.programDataDao.getInventoryList(programId, versionId, planningUnitActive));
        pd.setShipmentList(this.programDataDao.getShipmentList(programId, versionId, shipmentActive, planningUnitActive));
        pd.setBatchInfoList(this.programDataDao.getBatchList(programId, versionId, planningUnitActive));
        pd.setProblemReportList(this.problemService.getProblemReportList(programId, versionId, curUser));
        pd.setSupplyPlan(this.programDataDao.getSimplifiedSupplyPlan(programId, versionId, planningUnitActive));
        pd.setPlanningUnitList(this.programDataDao.getPlanningUnitListForProgramData(programId, curUser, planningUnitActive));
        return pd;
    }

    @Override
    public List<ProgramData> getProgramData(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser) {
        List<ProgramData> programDataList = new LinkedList<>();
        programVersionList.forEach(pv -> {
            ProgramData pd = new ProgramData(this.programCommonDao.getProgramById(pv.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser));
            pd.setRequestedProgramVersion(pv.getVersionId());
            pd.setCurrentVersion(this.programDataDao.getVersionInfo(pv.getProgramId(), pv.getVersionId()));
            int versionId = pd.getCurrentVersion().getVersionId();
            pd.setConsumptionList(this.programDataDao.getConsumptionList(pv.getProgramId(), versionId, false));
            pd.setInventoryList(this.programDataDao.getInventoryList(pv.getProgramId(), versionId, false));
            pd.setShipmentList(this.programDataDao.getShipmentList(pv.getProgramId(), versionId, false, false));
            pd.setBatchInfoList(this.programDataDao.getBatchList(pv.getProgramId(), versionId, false));
            pd.setProblemReportList(this.problemService.getProblemReportList(pv.getProgramId(), versionId, curUser));
            pd.setSupplyPlan(this.programDataDao.getSimplifiedSupplyPlan(pv.getProgramId(), versionId, false));
            pd.setPlanningUnitList(this.programDataDao.getPlanningUnitListForProgramData(pv.getProgramId(), curUser, false));
            programDataList.add(pd);
        });
        return programDataList;
    }

    @Override
    public DatasetData getDatasetData(int programId, int versionId, CustomUserDetails curUser) {
        if (versionId == -1) {
            versionId = this.programDao.getLatestVersionForPrograms("" + programId).get(0).getVersionId();
        }
        DatasetData dd = new DatasetData(this.programCommonDao.getProgramById(programId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser));
        dd.setPlanningUnitList(this.programDao.getDatasetPlanningUnitList(programId, versionId));
        dd.setCurrentVersion(this.programDataDao.getVersionInfo(programId, versionId));
        dd.setTreeList(this.programDataDao.getTreeListForDataset(programId, versionId, curUser));
        dd.getTreeList().forEach(t -> {
            t.setTree(this.programDataDao.getTreeData(t.getTreeId(), curUser));
        });
        dd.setActualConsumptionList(this.programDataDao.getForecastActualConsumptionData(programId, versionId, curUser));
        dd.setConsumptionExtrapolation(this.programDataDao.getForecastConsumptionExtrapolation(programId, versionId, curUser));
        return dd;
    }

    @Override
    public List<DatasetPlanningUnit> getDatasetPlanningUnit(int programId, int versionId, CustomUserDetails curUser) {
        Program p = this.programCommonDao.getProgramById(programId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), programId, p.getHealthAreaIdList(), p.getOrganisation().getId())) {
            return this.programDao.getDatasetPlanningUnitList(programId, versionId);
        } else {
            throw new AccessDeniedException("You do not have access to this Program");
        }
    }

    @Override
    public List<DatasetData> getDatasetData(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser) {
        List<DatasetData> datasetDataList = new LinkedList<>();
        programVersionList.forEach(pv -> {
            datasetDataList.add(getDatasetData(pv.getProgramId(), pv.getVersionId(), curUser));
        });
        return datasetDataList;
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
                Program p = this.programCommonDao.getProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
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

    @Override
    public List<ActualConsumptionDataOutput> getActualConsumptionDataInput(ActualConsumptionDataInput acd, CustomUserDetails curUser) {
        Program p = this.programCommonDao.getProgramById(acd.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        if (p != null && this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
            return this.programDataDao.getActualConsumptionDataInput(acd, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int addSupplyPlanCommitRequest(CommitRequest spcr, CustomUserDetails curUser) {
        return this.programDataDao.addSupplyPlanCommitRequest(spcr, curUser);
    }

}
