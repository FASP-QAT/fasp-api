/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.CommitRequestDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DatasetDataJson;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.NotificationUser;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.CommitRequestInput;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.CommitRequestService;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.UserService;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jxl.biff.BaseCellFeatures.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class CommitRequestServiceImpl implements CommitRequestService {

    @Autowired
    private AclService aclService;
    @Autowired
    private ProgramCommonDao programCommonDao;
    @Autowired
    private ProgramDataDao programDataDao;
    @Autowired
    private CommitRequestDao commitRequestDao;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @Override
    public int saveProgramData(ProgramData programData, String json, CustomUserDetails curUser) throws CouldNotSaveException {
        Program p = this.programCommonDao.getProgramById(programData.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
            programData.setRequestedProgramVersion(programData.getCurrentVersion().getVersionId());
            programData.setCurrentVersion(p.getCurrentVersion());
            return this.commitRequestDao.saveProgramData(programData, json, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int saveDatasetData(DatasetDataJson programData, String json, CustomUserDetails curUser) throws CouldNotSaveException {
        Program p = this.programCommonDao.getProgramById(programData.getProgramId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
            int requestedVersionId = programData.getCurrentVersion().getVersionId();
//            programData.setCurrentVersion(p.getCurrentVersion());
            return this.commitRequestDao.saveDatasetData(programData, requestedVersionId, json, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public void processCommitRequest(CustomUserDetails curUser) {
        CommitRequest spcr = this.commitRequestDao.getPendingCommitRequestProcessList();
        if (spcr.getJsonError() != null) {
            logger.error("Error while trying to process CommitRequest " + spcr.getJsonError());
            this.commitRequestDao.updateCommitRequest(spcr.getCommitRequestId(), 3, spcr.getJsonError(), 0);
        } else if (spcr != null) {
            boolean isStatusUpdated = false;
            if (spcr.getProgramTypeId() == 1) {
                Program p = this.programCommonDao.getProgramById(spcr.getProgram().getId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
                    Version version;
                    CustomUserDetails user = this.userService.getCustomUserByUserId(spcr.getCreatedBy().getUserId());
                    try {
                        if (spcr.isSaveData()) {
                            version = this.programDataDao.processSupplyPlanCommitRequest(spcr, user);
                        } else {
                            version = new Version();
                            version.setVersionId(spcr.getCommittedVersionId());
                        }
                    } catch (Exception e) {
                        logger.error("Error while trying to process CommitRequest", e);
                        version = this.commitRequestDao.updateCommitRequest(spcr.getCommitRequestId(), 3, e.getMessage(), 0);
                        isStatusUpdated = true;
                    }
                    try {
                        this.programDataDao.getNewSupplyPlanList(spcr.getProgram().getId(), version.getVersionId(), true, false);
                        if (version.getVersionId() != 0) {
                            this.commitRequestDao.updateCommitRequest(spcr.getCommitRequestId(), 2, "", version.getVersionId());
                        } else {
                            if (!isStatusUpdated) {
                                version = this.commitRequestDao.updateCommitRequest(spcr.getCommitRequestId(), 3, "No new changes found", 0);
                            }
                        }
                        if (version.getVersionId() != 0 && spcr.isSaveData()) {
                            if (spcr.getVersionType().getId() == 2) {
                                List<NotificationUser> toEmailIdsList = this.programDataDao.getSupplyPlanNotificationList(spcr.getProgram().getId(), version.getVersionId(), 1, "To");
                                List<NotificationUser> ccEmailIdsList = this.programDataDao.getSupplyPlanNotificationList(spcr.getProgram().getId(), version.getVersionId(), 1, "Cc");
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
//                                if (sbToEmails.length() != 0) {
//                                    System.out.println("sbToemails===>" + sbToEmails == "" ? "" : sbToEmails.toString());
//                                }
//                                if (sbCcEmails.length() != 0) {
//                                    System.out.println("sbCcemails===>" + sbCcEmails == "" ? "" : sbCcEmails.toString());
//                                }
                                EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(6);
                                String[] subjectParam = new String[]{};
                                String[] bodyParam = null;
                                Emailer emailer = new Emailer();
                                subjectParam = new String[]{spcr.getProgram().getCode()};
                                bodyParam = new String[]{spcr.getProgram().getCode(), String.valueOf(version.getVersionId()), spcr.getNotes()};
                                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), sbToEmails.length() != 0 ? sbToEmails.deleteCharAt(sbToEmails.length() - 1).toString() : "", sbCcEmails.length() != 0 ? sbCcEmails.deleteCharAt(sbCcEmails.length() - 1).toString() : "", subjectParam, bodyParam);
                                int emailerId = this.emailService.saveEmail(emailer);
                                emailer.setEmailerId(emailerId);
                                this.emailService.sendMail(emailer);
                            }
                        }
                    } catch (ParseException pe) {
                        logger.error("Error while sending email", pe);
                    }
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            } else if (spcr.getProgramTypeId() == 2) {
                Program p = this.programCommonDao.getProgramById(spcr.getProgram().getId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
                if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
                    Version version;
                    CustomUserDetails user = this.userService.getCustomUserByUserId(spcr.getCreatedBy().getUserId());
                    try {
                        if (spcr.isSaveData()) {
                            version = this.programDataDao.processDatasetCommitRequest(spcr, user);
                        } else {
                            version = new Version();
                            version.setVersionId(spcr.getCommittedVersionId());
                        }
                    } catch (Exception e) {
                        version = this.commitRequestDao.updateCommitRequest(spcr.getCommitRequestId(), 3, e.getMessage(), 0);
                        isStatusUpdated = true;
                        logger.error("Error while trying to process CommitRequest", e);
                    }
                    try {
//                        getNewSupplyPlanList(spcr.getProgram().getId(), version.getVersionId(), true, false);
                        if (version.getVersionId() != 0) {
                            this.commitRequestDao.updateCommitRequest(spcr.getCommitRequestId(), 2, "", version.getVersionId());
                        } else {
                            if (!isStatusUpdated) {
                                version = this.commitRequestDao.updateCommitRequest(spcr.getCommitRequestId(), 3, "No new changes found", 0);
                            }
                        }
                        if (version.getVersionId() != 0 && spcr.isSaveData()) {
                            if (spcr.getVersionType().getId() == 2) {
                                // Send email
                            }
                        }
                    } catch (Exception pe) {
                        logger.error("Error while sending email", pe);
                    }
                } else {
                    throw new AccessDeniedException("Access denied");
                }
            }
        }
    }

    @Override
    public List<CommitRequest> getCommitRequestList(CommitRequestInput spcr, int requestStatus, CustomUserDetails curUser) {
        return this.commitRequestDao.getCommitRequestList(spcr, requestStatus, curUser);
    }

    @Override
    public CommitRequest getCommitRequestByCommitRequestId(int commitRequestId) {
        return this.commitRequestDao.getCommitRequestByCommitRequestId(commitRequestId);
    }

    @Override
    public boolean checkIfCommitRequestExistsForProgram(int programId) {
        return this.commitRequestDao.checkIfCommitRequestExistsForProgram(programId);
    }

    @Override
    @Async
    public CompletableFuture<Object> getCommitRequestStatusByCommitRequestId(int commitRequestId) {
        CommitRequest spcr = new CommitRequest();
        spcr.setStatus(0);
        while (spcr.getStatus() != 2 && spcr.getStatus() != 3) {
            try {
                spcr = this.commitRequestDao.getCommitRequestByCommitRequestId(commitRequestId);
                Thread.sleep(5000L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Logger.getLogger(CommitRequestServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return CompletableFuture.completedFuture(spcr);
    }

}
