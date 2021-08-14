/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.NotificationUser;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ReviewedProblem;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForSupplyPlanObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.SupplyPlanCommitRequest;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.SupplyPlanCommitRequestInput;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProgramDataDao {

    public Version getVersionInfo(int programId, int versionId);

    public List<Consumption> getConsumptionList(int programId, int versionId);

    public List<Inventory> getInventoryList(int programId, int versionId);

    public List<Shipment> getShipmentList(int programId, int versionId, boolean active);

    public List<Batch> getBatchList(int programId, int versionId);

    public int saveProgramData(ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException;

    public void processCommitRequest(CustomUserDetails curUser);

    public List<SupplyPlanCommitRequest> getSupplyPlanCommitRequestList(SupplyPlanCommitRequestInput spcr, CustomUserDetails curUser);

    public Version executeProgramDataCommit(int commitRequestId, ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException;

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

    public List<SimplifiedSupplyPlan> getSimplifiedSupplyPlan(int programId, int versionId);

    public int getLatestVersionForProgram(int programId);

    public List<ProgramIntegrationDTO> getSupplyPlanToExportList();

    public boolean updateSupplyPlanAsExported(int programVersionTransId, int integrationId);

    public String getSupplyPlanReviewerEmialList(int realmCountryId);

    public List<SimplePlanningUnitForSupplyPlanObject> getPlanningUnitListForProgramData(int programId, CustomUserDetails curUser);

    public List<NotificationUser> getSupplyPlanNotificationList(int programId, int versionId, int statusType, String toCc);

    public String getLastModifiedDateForProgram(int programId, int versionId);

}
