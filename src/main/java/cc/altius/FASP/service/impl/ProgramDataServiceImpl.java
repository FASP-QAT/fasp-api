/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProcurementAgentDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.DatasetData;
import cc.altius.FASP.model.NotificationUser;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ShipmentSync;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.DatasetVersionListInput;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramVersionTrans;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.UpdateProgramVersion;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.ActualConsumptionData;
import cc.altius.FASP.model.report.ActualConsumptionDataInput;
import cc.altius.FASP.model.report.ActualConsumptionDataOutput;
import cc.altius.FASP.model.report.LoadProgramInput;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.DashboardService;
import cc.altius.FASP.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.ProgramDataService;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private ProcurementAgentDao procurementAgentDao;
    @Autowired
    private ProgramCommonDao programCommonDao;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private AclService aclService;
    @Autowired
    private DashboardService dashboardService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser, boolean shipmentActive, boolean planningUnitActive) throws AccessControlFailedException {
        ProgramData pd = new ProgramData(this.programCommonDao.getFullProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser));
        pd.setRequestedProgramVersion(versionId);
        pd.setCurrentVersion(this.programDataDao.getVersionInfo(programId, versionId));
        versionId = pd.getCurrentVersion().getVersionId();
        pd.setCurrentVersionTrans(this.programDataDao.getProgramVersionTrans(programId, versionId, curUser));
        pd.setConsumptionList(this.programDataDao.getConsumptionList(programId, versionId, planningUnitActive, null));
        pd.setInventoryList(this.programDataDao.getInventoryList(programId, versionId, planningUnitActive, null));
        pd.setShipmentList(this.programDataDao.getShipmentList(programId, versionId, shipmentActive, planningUnitActive, null));
        pd.setShipmentLinkingList(this.programDataDao.getShipmentLinkingList(programId, versionId, null));
        pd.setBatchInventoryList(this.programDataDao.getBatchInventoryList(programId, versionId, planningUnitActive, null));
        pd.setBatchInfoList(this.programDataDao.getBatchList(programId, versionId, planningUnitActive, null));
        pd.setProblemReportList(this.problemService.getProblemReportList(programId, versionId, curUser));
        pd.setSupplyPlan(this.programDataDao.getSimplifiedSupplyPlan(programId, versionId, planningUnitActive, null));
        pd.setPlanningUnitList(this.programDataDao.getPlanningUnitListForProgramData(programId, curUser, planningUnitActive));
        pd.setProcurementAgentList(this.procurementAgentDao.getProcurementAgentListByProgramId(programId, curUser));
        pd.setShipmentBudgetList(this.programDataDao.getShipmentBudgetList(programId, versionId, curUser));
        return pd;
    }

    @Override
    public List<ProgramData> getProgramData(List<LoadProgramInput> lpInputList, CustomUserDetails curUser) throws ParseException, AccessControlFailedException {
        List<ProgramData> programDataList = new LinkedList<>();
        for (LoadProgramInput pv : lpInputList) {
            Program p = this.programCommonDao.getFullProgramById(pv.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            ProgramData pd = new ProgramData(p);
            pd.setCutOffDate(pv.getCutOffDate());
            pd.setRequestedProgramVersion(pv.getVersionId());
            pd.setCurrentVersion(this.programDataDao.getVersionInfo(pv.getProgramId(), pv.getVersionId()));
            pd.setCurrentVersionTrans(this.programDataDao.getProgramVersionTrans(pv.getProgramId(), pd.getCurrentVersion().getVersionId(), curUser));
            int versionId = pd.getCurrentVersion().getVersionId();
            pd.setConsumptionList(this.programDataDao.getConsumptionList(pv.getProgramId(), versionId, false, pv.getCutOffDate()));
            pd.setInventoryList(this.programDataDao.getInventoryList(pv.getProgramId(), versionId, false, pv.getCutOffDate()));
            pd.setShipmentList(this.programDataDao.getShipmentList(pv.getProgramId(), versionId, false, false, pv.getCutOffDate()));
            pd.setShipmentLinkingList(this.programDataDao.getShipmentLinkingList(pv.getProgramId(), versionId, pv.getCutOffDate()));
            pd.setBatchInventoryList(this.programDataDao.getBatchInventoryList(pv.getProgramId(), versionId, false, pv.getCutOffDate()));
            pd.setBatchInfoList(this.programDataDao.getBatchList(pv.getProgramId(), versionId, false, pv.getCutOffDate()));
            pd.setProblemReportList(this.problemService.getProblemReportList(pv.getProgramId(), versionId, curUser));
            pd.setSupplyPlan(this.programDataDao.getSimplifiedSupplyPlan(pv.getProgramId(), versionId, false, pv.getCutOffDate()));
            pd.setPlanningUnitList(this.programDataDao.getPlanningUnitListForProgramData(pv.getProgramId(), curUser, false));
            pd.setProcurementAgentList(this.procurementAgentDao.getProcurementAgentListByProgramId(pv.getProgramId(), curUser));
            pd.setShipmentBudgetList(this.programDataDao.getShipmentBudgetList(pv.getProgramId(), pv.getVersionId(), curUser));
            int noOfMonthsInPastForBottom, noOfMonthsInFutureForBottom, noOfMonthsInPastForTop = p.getRealmCountry().getRealm().getNoOfMonthsInPastForTopDashboard(), noOfMonthsInFutureForTop = p.getRealmCountry().getRealm().getNoOfMonthsInFutureForTopDashboard();
            if (p.getNoOfMonthsInPastForBottomDashboard() == null) {
                noOfMonthsInPastForBottom = p.getRealmCountry().getRealm().getNoOfMonthsInPastForBottomDashboard();
            } else {
                noOfMonthsInPastForBottom = p.getNoOfMonthsInPastForBottomDashboard();
            }
            if (p.getNoOfMonthsInFutureForBottomDashboard() == null) {
                noOfMonthsInFutureForBottom = p.getRealmCountry().getRealm().getNoOfMonthsInFutureForBottomDashboard();
            } else {
                noOfMonthsInFutureForBottom = p.getNoOfMonthsInFutureForBottomDashboard();
            }
            try {
                pd.setDashboardData(this.dashboardService.getDashboardForLoadProgram(pv.getProgramId(), versionId, noOfMonthsInPastForBottom, noOfMonthsInFutureForBottom, noOfMonthsInPastForTop, noOfMonthsInFutureForTop, curUser));
            } catch (ParseException ex) {
                logger.error("Error occurred getting the dates for Dashboard", ex);
            }
            programDataList.add(pd);
        }
        return programDataList;
    }

    @Override
    public DatasetData getDatasetData(int programId, int versionId, boolean includeTreeData, CustomUserDetails curUser) throws AccessControlFailedException {
        if (versionId == -1) {
            versionId = this.programDao.getLatestVersionForPrograms("" + programId).get(0).getVersionId();
        }
        DatasetData dd = new DatasetData(this.programCommonDao.getFullProgramById(programId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser));
        dd.setPlanningUnitList(this.programDao.getDatasetPlanningUnitList(programId, versionId));
        dd.setCurrentVersion(this.programDataDao.getVersionInfo(programId, versionId));
        if (includeTreeData) {
            dd.setTreeList(this.programDataDao.getTreeListForDataset(programId, versionId, curUser));
            dd.getTreeList().forEach(t -> {
                t.setTree(this.programDataDao.getTreeData(t.getTreeId(), curUser));
                t.getTree().getFlatList().forEach(n -> {
                    n.getPayload().getNodeDataMap().values().forEach(s -> {
                        s.forEach(nd -> {
                            if (n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PERCENTAGE || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_FU || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PU) {
                                nd.setNodeDataModelingList(this.programDataDao.getModelingDataForNodeDataId(nd.getNodeDataId(), false));
                                nd.setAnnualTargetCalculator(this.programDataDao.getAnnualTargetCalculatorForNodeDataId(nd.getNodeDataId(), false));
                                nd.setNodeDataOverrideList(this.programDataDao.getOverrideDataForNodeDataId(nd.getNodeDataId(), false));
                            }
                            nd.setNodeDataMomList(this.programDataDao.getMomDataForNodeDataId(nd.getNodeDataId()));
                            if (nd.isExtrapolation()) {
                                nd.setNodeDataExtrapolation(this.programDataDao.getNodeDataExtrapolationForNodeDataId(nd.getNodeDataId()));
                                nd.setNodeDataExtrapolationOptionList(this.programDataDao.getNodeDataExtrapolationOptionForNodeDataId(nd.getNodeDataId()));
                            }
                        });
                    });
                });
            });
        }
        dd.setActualConsumptionList(this.programDataDao.getForecastActualConsumptionData(programId, versionId, curUser));
        dd.setConsumptionExtrapolation(this.programDataDao.getForecastConsumptionExtrapolation(programId, versionId, curUser));
        return dd;
    }

    @Override
    public List<DatasetPlanningUnit> getDatasetPlanningUnit(int programId, int versionId, CustomUserDetails curUser) throws AccessControlFailedException {
        SimpleProgram sp = this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
        return this.programDao.getDatasetPlanningUnitList(programId, versionId);
    }

    @Override
    public List<DatasetData> getDatasetData(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser) throws AccessControlFailedException {
        List<DatasetData> datasetDataList = new LinkedList<>();
        for (ProgramIdAndVersionId pv : programVersionList) {
            datasetDataList.add(getDatasetData(pv.getProgramId(), pv.getVersionId(), true, curUser));
        }
        return datasetDataList;
    }

    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser) {
        return this.programDataDao.getProgramVersionList(programId, versionId, realmCountryId, healthAreaId, organisationId, versionTypeId, versionStatusId, startDate, stopDate, curUser);
    }

    @Override
    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, UpdateProgramVersion updateProgramVersion, CustomUserDetails curUser) throws AccessControlFailedException {
//        if (programId != 0) {
//            try {
        this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
//            } catch (EmptyResultDataAccessException e) {
//                throw new AccessControlFailedException();
//            }
        return this.programDataDao.updateProgramVersion(programId, versionId, versionStatusId, updateProgramVersion, curUser);
    }

    @Override
    public void resetProblemListForPrograms(int[] programIds, CustomUserDetails curUser) throws AccessControlFailedException {
        for (int programId : programIds) {
//            try {
            this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
//            } catch (EmptyResultDataAccessException e) {
//                throw new AccessControlFailedException();
//            }
        }
        this.programDataDao.resetProblemListForPrograms(programIds, curUser);
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
//        ss.setShipmentList(this.programDataDao.getShipmentListForSync(programId, versionId, lastSyncDate));
//        ss.setBatchInfoList(this.programDataDao.getBatchListForSync(programId, versionId, lastSyncDate));
        ss.setProblemReportList(this.problemService.getProblemReportListForSync(programId, versionId, lastSyncDate));
        ss.setVersionNotes(this.programDataDao.getVersionInfo(programId, versionId).getNotes());
        return ss;
    }

    @Override
    public boolean checkNewerVersions(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser) throws AccessControlFailedException {
        boolean newer = false;
        ListMultimap<Integer, Integer> programMap = LinkedListMultimap.create();
        for (ProgramIdAndVersionId pv : programVersionList) {
            programMap.put(pv.getProgramId(), pv.getVersionId());
        }
        for (int programId : programMap.keySet()) {
            Integer versionId = programMap.get(programId).stream().mapToInt(v -> v).max().orElse(-1);
            if (versionId != -1) {
                SimpleProgram sp = this.programCommonDao.getSimpleProgramById(programId, 0, curUser);
                if (sp != null && sp.getCurrentVersionId() > versionId) {
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
    public Map<String, List<ActualConsumptionDataOutput>> getActualConsumptionDataInput(ActualConsumptionDataInput acd, CustomUserDetails curUser) throws AccessControlFailedException {
        Map<String, List<ActualConsumptionDataOutput>> actualConsumptionMap = new HashMap<>();
        for (ActualConsumptionData pd : acd.getProgramDataList()) {
            SimpleProgram sp = this.programCommonDao.getSimpleProgramById(pd.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
//            if (sp != null && this.aclService.checkAccessForUser(curUser, sp.getRealmId(), sp.getRealmCountry().getId(), sp.getHealthAreaIdList(), sp.getOrganisation().getId(), sp.getId())) {
            List<ActualConsumptionDataOutput> acdo = this.programDataDao.getActualConsumptionDataInput(pd, acd.getStartDate(), acd.getStopDate(), curUser);
            actualConsumptionMap.put(pd.getProgramId() + "~" + pd.getVersionId(), acdo);
//            } else {
//                throw new AccessDeniedException("Access denied");
//            }
        }
        return actualConsumptionMap;
    }

    @Override
    public int addSupplyPlanCommitRequest(CommitRequest spcr, CustomUserDetails curUser) {
        return this.programDataDao.addSupplyPlanCommitRequest(spcr, curUser);
    }

    @Override
    public List<ProgramVersion> getDatasetVersionList(DatasetVersionListInput datasetVersionListInput, CustomUserDetails curUser) {
        return this.programDataDao.getDatasetVersionList(datasetVersionListInput, curUser);
    }

    @Override
    public List<ProgramVersionTrans> getProgramVersionTrans(int programId, int versionId, CustomUserDetails curUser) {
        return this.programDataDao.getProgramVersionTrans(programId, versionId, curUser);
    }

}
