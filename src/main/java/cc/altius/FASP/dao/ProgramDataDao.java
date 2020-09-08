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
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.MasterSupplyPlan;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.Version;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProgramDataDao {

    public Version getVersionInfo(int programId, int versionId);

    public List<Consumption> getConsumptionList(int programId, int versionId);

    public List<Inventory> getInventoryList(int programId, int versionId);

    public List<Shipment> getShipmentList(int programId, int versionId);

    public List<Batch> getBatchList(int programId, int versionId);

    public Version saveProgramData(ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException;

    public List<SimpleObject> getVersionTypeList();

    public List<SimpleObject> getVersionStatusList();

    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser);

    public Version updateProgramVersion(int programId, int versionId, int versionStatusId,String notes, CustomUserDetails curUser);

    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId);

    public SupplyPlan getSupplyPlan(int programId, int versionId);
    
    public MasterSupplyPlan getNewSupplyPlanList(int programId, int versionId, boolean rebuild);

    public List<SimplifiedSupplyPlan> updateSupplyPlanBatchInfo(SupplyPlan sp);

    public List<ProgramVersion> getProgramVersionForARTMIS(int realmId);

    public int updateSentToARTMISFlag(String programVersionIds);
    
    public List<Shipment> getShipmentListForSync(int programId, int versionId, String lastSyncDate);
    
    public List<Batch> getBatchListForSync(int programId, int versionId, String lastSyncDate);
    
    public List<SimplifiedSupplyPlan> getSimplifiedSupplyPlan(int programId, int versionId);

}
