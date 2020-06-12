/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.Version;
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

    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, CustomUserDetails curUser);

    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId);

//    public void buildStockBalances(int programId, int versionId);

    public List<ProgramVersion> getProgramVersionForARTMIS(int realmId);

    public int updateSentToARTMISFlag(String programVersionIds);

//    public void buildStockBalances(int programId, int versionId);
    public SupplyPlan getSupplyPlan(int programId, int versionId);

    public void updateSupplyPlanBatchInfo(SupplyPlan sp);
}
