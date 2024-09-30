/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.DatasetData;
import cc.altius.FASP.model.NotificationUser;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ShipmentSync;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.DatasetVersionListInput;
import cc.altius.FASP.model.ProgramVersionTrans;
import cc.altius.FASP.model.UpdateProgramVersion;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.ActualConsumptionDataInput;
import cc.altius.FASP.model.report.ActualConsumptionDataOutput;
import cc.altius.FASP.model.report.LoadProgramInput;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface ProgramDataService {

    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser, boolean shipmentActive, boolean planningUnitActive);

    public List<ProgramData> getProgramData(List<LoadProgramInput> lpInputList, CustomUserDetails curUser) throws ParseException;

    public List<DatasetData> getDatasetData(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser);

    public DatasetData getDatasetData(int programId, int versionId, boolean includeTreeData, CustomUserDetails curUser);

    public List<DatasetPlanningUnit> getDatasetPlanningUnit(int programId, int versionId, CustomUserDetails curUser);

    public List<SimpleObject> getVersionTypeList();

    public List<SimpleObject> getVersionStatusList();

    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser);

    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, UpdateProgramVersion updateProgramVersion, CustomUserDetails curUser);

    public void resetProblemListForPrograms(int[] programIds, CustomUserDetails curUser);

    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId);

    public int updateSentToARTMISFlag(String programVersionIds);

    public SupplyPlan getSupplyPlan(int programId, int versionId);

    public List<SimplifiedSupplyPlan> getNewSupplyPlanList(int programId, int versionId, boolean rebuild, boolean returnSupplyPlan) throws ParseException;

    public List<SimplifiedSupplyPlan> updateSupplyPlanBatchInfo(SupplyPlan sp);

    public ShipmentSync getShipmentListForSync(int programId, int versionId, int userId, String lastSyncDate, CustomUserDetails curUser);

    public boolean checkNewerVersions(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser);

    public List<ProgramIntegrationDTO> getSupplyPlanToExportList();

    public boolean updateSupplyPlanAsExported(int programVersionTransId, int integrationId);

    public List<NotificationUser> getSupplyPlanNotificationList(int programId, int versionId, int statusType, String toCc);

    public String getLastModifiedDateForProgram(int programId, int versionId);

    public Map<String, List<ActualConsumptionDataOutput>> getActualConsumptionDataInput(ActualConsumptionDataInput acd, CustomUserDetails curUser);

    public int addSupplyPlanCommitRequest(CommitRequest spcr, CustomUserDetails curUser);

    public List<ProgramVersion> getDatasetVersionList(DatasetVersionListInput datasetVersionListInput, CustomUserDetails curUser);

    public List<ProgramVersionTrans> getProgramVersionTrans(int programId, int versionId, CustomUserDetails curUser);
}
