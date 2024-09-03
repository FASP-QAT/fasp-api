/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.AnnualTargetCalculator;
import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.DatasetTree;
import cc.altius.FASP.model.ForecastActualConsumption;
import cc.altius.FASP.model.ForecastTree;
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.NotificationUser;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ReviewedProblem;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForSupplyPlanObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.report.ActualConsumptionDataInput;
import cc.altius.FASP.model.report.ActualConsumptionDataOutput;
import cc.altius.FASP.model.ForecastConsumptionExtrapolation;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.DatasetVersionListInput;
import cc.altius.FASP.model.NodeDataExtrapolation;
import cc.altius.FASP.model.NodeDataExtrapolationOption;
import cc.altius.FASP.model.NodeDataModeling;
import cc.altius.FASP.model.NodeDataMom;
import cc.altius.FASP.model.NodeDataOverride;
import cc.altius.FASP.model.ShipmentBudgetAmt;
import cc.altius.FASP.model.ShipmentLinking;
import cc.altius.FASP.model.Version;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProgramDataDao {

    public Version getVersionInfo(int programId, int versionId);

    public List<Consumption> getConsumptionList(int programId, int versionId, boolean planningUnitActive);

    public List<Inventory> getInventoryList(int programId, int versionId, boolean planningUnitActive);

    public List<Shipment> getShipmentList(int programId, int versionId, boolean shipmentActive, boolean planningUnitActive);

    public List<ShipmentLinking> getShipmentLinkingList(int programId, int versionId);

    public List<Batch> getBatchList(int programId, int versionId, boolean planningUnitActive);
    
    public List<ShipmentBudgetAmt> getShipmentBudgetList(int programId, int versionId, CustomUserDetails curUser);

    public Version processSupplyPlanCommitRequest(CommitRequest spcr, CustomUserDetails curUser) throws CouldNotSaveException;

    public Version processDatasetCommitRequest(CommitRequest spcr, CustomUserDetails curUser);

    public List<SimpleObject> getVersionTypeList();

    public List<SimpleObject> getVersionStatusList();

    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser);

    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, String notes, CustomUserDetails curUser, List<ReviewedProblem> reviewedProblemList);

    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId);

    public SupplyPlan getSupplyPlan(int programId, int versionId);

    public List<SimplifiedSupplyPlan> getNewSupplyPlanList(int programId, int versionId, boolean rebuild, boolean returnSupplyPlan) throws ParseException;

    public List<SimplifiedSupplyPlan> updateSupplyPlanBatchInfo(SupplyPlan sp);

    public int updateSentToARTMISFlag(String programVersionIds);

    public List<Shipment> getShipmentListForSync(int programId, int versionId, String lastSyncDate);

    public List<Batch> getBatchListForSync(int programId, int versionId, String lastSyncDate);

    public List<SimplifiedSupplyPlan> getSimplifiedSupplyPlan(int programId, int versionId, boolean planningUnitActive);

    public List<ProgramIntegrationDTO> getSupplyPlanToExportList();

    public boolean updateSupplyPlanAsExported(int programVersionTransId, int integrationId);

    public String getSupplyPlanReviewerEmialList(int realmCountryId);

    public List<SimplePlanningUnitForSupplyPlanObject> getPlanningUnitListForProgramData(int programId, CustomUserDetails curUser, boolean planningUnitActive);

    public List<NotificationUser> getSupplyPlanNotificationList(int programId, int versionId, int statusType, String toCc);

    public String getLastModifiedDateForProgram(int programId, int versionId);

    public List<DatasetTree> getTreeListForDataset(int programId, int versionId, CustomUserDetails curUser);

    public ForecastTree<TreeNode> getTreeData(int treeId, CustomUserDetails curUser);

    public List<ForecastActualConsumption> getForecastActualConsumptionData(int programId, int versionId, CustomUserDetails curUser);

    public List<ForecastConsumptionExtrapolation> getForecastConsumptionExtrapolation(int programId, int versionId, CustomUserDetails curUser);

    public List<ActualConsumptionDataOutput> getActualConsumptionDataInput(ActualConsumptionDataInput acd, CustomUserDetails curUser);

    public int addSupplyPlanCommitRequest(CommitRequest spcr, CustomUserDetails curUser);

    public List<ProgramVersion> getDatasetVersionList(DatasetVersionListInput datasetVersionListInput, CustomUserDetails curUser);

    public List<NodeDataModeling> getModelingDataForNodeDataId(int nodeDataId, boolean isTemplate);

    public AnnualTargetCalculator getAnnualTargetCalculatorForNodeDataId(int nodeDataId, boolean isTemplate);

    public List<NodeDataMom> getMomDataForNodeDataId(int nodeDataId);

    public List<NodeDataOverride> getOverrideDataForNodeDataId(int nodeDataId, boolean isTemplate);

    public NodeDataExtrapolation getNodeDataExtrapolationForNodeDataId(int nodeDataId);

    public List<NodeDataExtrapolationOption> getNodeDataExtrapolationOptionForNodeDataId(int nodeDataId);
    
}
