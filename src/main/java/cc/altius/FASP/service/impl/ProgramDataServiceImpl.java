/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.MasterSupplyPlan;
import cc.altius.FASP.model.NewSupplyPlan;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ShipmentSync;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.DateUtils;
import java.util.Date;
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

    @Override
    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser) {
        ProgramData pd = new ProgramData(this.programService.getProgramById(programId, curUser));
        pd.setRequestedProgramVersion(versionId);
        pd.setCurrentVersion(this.programDataDao.getVersionInfo(programId, versionId));
        versionId = pd.getCurrentVersion().getVersionId();
        pd.setConsumptionList(this.programDataDao.getConsumptionList(programId, versionId));
        pd.setInventoryList(this.programDataDao.getInventoryList(programId, versionId));
        pd.setShipmentList(this.programDataDao.getShipmentList(programId, versionId));
        pd.setBatchInfoList(this.programDataDao.getBatchList(programId, versionId));
        pd.setProblemReportList(this.problemService.getProblemReportList(programId, versionId, curUser));
        pd.setSupplyPlan(this.programDataDao.getSimplifiedSupplyPlan(programId, versionId));
        return pd;
    }

    @Override
    public Version saveProgramData(ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException {
        Program p = this.programService.getProgramById(programData.getProgramId(), curUser);
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthArea().getId(), p.getOrganisation().getId())) {
            return this.programDataDao.saveProgramData(programData, curUser);
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
    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, String notes, CustomUserDetails curUser) {
        return this.programDataDao.updateProgramVersion(programId, versionId, versionStatusId, notes, curUser);
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
    public MasterSupplyPlan getNewSupplyPlanList(int programId, int versionId, boolean rebuild) {
        return this.programDataDao.getNewSupplyPlanList(programId, versionId, rebuild);
    }
    
    @Override
    public List<SimplifiedSupplyPlan> updateSupplyPlanBatchInfo(SupplyPlan sp) {
        return this.programDataDao.updateSupplyPlanBatchInfo(sp);
    }

    @Override
    public List<ProgramVersion> getProgramVersionForARTMIS(int realmId) {
        return this.programDataDao.getProgramVersionForARTMIS(realmId);
    }

    @Override
    public int updateSentToARTMISFlag(String programVersionIds) {
        return this.programDataDao.updateSentToARTMISFlag(programVersionIds);
    }

    @Override
    public ShipmentSync getShipmentListForSync(int programId, int versionId, String lastSyncDate, CustomUserDetails curUser) {
        ShipmentSync ss = new ShipmentSync();
        ss.setProgramId(programId);
        ss.setVersionId(versionId);
        ss.setShipmentList(this.programDataDao.getShipmentListForSync(programId, versionId, lastSyncDate));
        ss.setBatchInfoList(this.programDataDao.getBatchListForSync(programId, versionId, lastSyncDate));
        return ss;
    }

}
