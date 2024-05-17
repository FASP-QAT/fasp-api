/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.PlanningUnitDao;
import cc.altius.FASP.dao.ProcurementUnitDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.exception.DuplicateNameException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.ProcurementUnit;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProcurementUnitService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class ProcurementUnitServiceImpl implements ProcurementUnitService {

    @Autowired
    private ProcurementUnitDao procurementUnitDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private PlanningUnitDao planningUnitDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<ProcurementUnit> getProcurementUnitList(boolean active, CustomUserDetails curUser) {
        return this.procurementUnitDao.getProcurementUnitList(active, curUser);
    }

    @Override
    public List<ProcurementUnit> getProcurementUnitListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.procurementUnitDao.getProcurementUnitListForRealm(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ProcurementUnit> getProcurementUnitListByPlanningUnit(int planningUnitId, boolean active, CustomUserDetails curUser) {
        PlanningUnit pu = this.planningUnitDao.getPlanningUnitById(planningUnitId, curUser);
        if (pu == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, pu.getForecastingUnit().getRealm().getId())) {
            return this.procurementUnitDao.getProcurementUnitListByPlanningUnit(planningUnitId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int addProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) throws DuplicateNameException {
        PlanningUnit pu = this.planningUnitDao.getPlanningUnitById(procurementUnit.getPlanningUnit().getPlanningUnitId(), curUser);
        if (pu == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, pu.getForecastingUnit().getRealm().getId())) {
            return this.procurementUnitDao.addProcurementUnit(procurementUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) throws DuplicateNameException {
        ProcurementUnit pr = this.procurementUnitDao.getProcurementUnitById(procurementUnit.getProcurementUnitId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pr.getPlanningUnit().getForecastingUnit().getRealm().getId())) {
            return this.procurementUnitDao.updateProcurementUnit(procurementUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public ProcurementUnit getProcurementUnitById(int procurementUnitId, CustomUserDetails curUser) {
        ProcurementUnit pr = this.procurementUnitDao.getProcurementUnitById(procurementUnitId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pr.getPlanningUnit().getForecastingUnit().getRealm().getId())) {
            return pr;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ProcurementUnit> getProcurementUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.procurementUnitDao.getProcurementUnitListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<ProcurementUnit> getProcurementUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        if (programIdsString.length() > 0) {
            return this.procurementUnitDao.getProcurementUnitListForSyncProgram(programIdsString, curUser);
        } else {
            return new LinkedList<>();
        }
    }

}
