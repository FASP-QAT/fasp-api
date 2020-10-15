/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ReviewedProblem;
import cc.altius.FASP.model.ShipmentSync;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.Version;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProgramDataService {

    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser);

    public Version saveProgramData(ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException;

    public List<SimpleObject> getVersionTypeList();

    public List<SimpleObject> getVersionStatusList();

    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser);

    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, String notes, CustomUserDetails curUser, List<ReviewedProblem> reviewedProblemList);

    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId);

    public List<ProgramVersion> getProgramVersionForARTMIS(int realmId);

    public int updateSentToARTMISFlag(String programVersionIds);

    public SupplyPlan getSupplyPlan(int programId, int versionId);
    
    public List<SimplifiedSupplyPlan> getNewSupplyPlanList(int programId, int versionId, boolean rebuild) throws ParseException;
    
    public List<SimplifiedSupplyPlan> updateSupplyPlanBatchInfo(SupplyPlan sp);

    public ShipmentSync getShipmentListForSync(int programId, int versionId, String lastSyncDate, CustomUserDetails curUser);
    
    public boolean checkNewerVersions(List<ProgramIdAndVersionId> programVersionList, CustomUserDetails curUser);
}
