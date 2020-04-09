/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ForecastingUnitDao;
import cc.altius.FASP.dao.PlanningUnitDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.PlanningUnitCapacity;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.PlanningUnitService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class PlanningUnitServiceImpl implements PlanningUnitService {

    @Autowired
    private PlanningUnitDao planningUnitDao;
    @Autowired
    private ForecastingUnitDao forecastingUnitDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<PlanningUnit> getPlanningUnitList(boolean active, CustomUserDetails curUser) {
        return this.planningUnitDao.getPlanningUnitList(active, curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitList(int realmId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.planningUnitDao.getPlanningUnitList(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListByForecastingUnit(int forecastingUnitId, boolean active, CustomUserDetails curUser) {
        ForecastingUnit fu = this.forecastingUnitDao.getForecastingUnitById(forecastingUnitId, curUser);
        if (fu == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, fu.getRealm().getRealmId())) {
            return this.planningUnitDao.getPlanningUnitList(fu.getRealm().getRealmId(), active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int addPlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser) {
        ForecastingUnit fu = this.forecastingUnitDao.getForecastingUnitById(planningUnit.getForeacastingUnit().getForecastingUnitId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, fu.getRealm().getRealmId())) {
            return this.planningUnitDao.addPlanningUnit(planningUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updatePlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser) {
        PlanningUnit pr = this.getPlanningUnitById(planningUnit.getPlanningUnitId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pr.getForeacastingUnit().getRealm().getRealmId())) {
            return this.planningUnitDao.updatePlanningUnit(planningUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public PlanningUnit getPlanningUnitById(int planningUnitId, CustomUserDetails curUser) {
        PlanningUnit pr = this.planningUnitDao.getPlanningUnitById(planningUnitId, curUser);
        if (this.aclService.checkAccessForUser(curUser, pr.getForeacastingUnit().getRealm().getRealmId(), 0, 0, 0, pr.getPlanningUnitId())) {
            return pr;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityForRealm(int realmId, Date dtStartDate, Date dtStopDate, CustomUserDetails curUser) throws ParseException {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            if ((dtStartDate != null && dtStopDate == null) || (dtStopDate != null && dtStartDate == null)) {
                throw new ParseException("One date cannot be null", 1);
            }
            return this.planningUnitDao.getPlanningUnitCapacityForRealm(realmId, dtStartDate, dtStopDate, curUser);

        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityForId(int planningUnitId, Date dtStartDate, Date dtStopDate, CustomUserDetails curUser) throws ParseException {
        PlanningUnit pu = this.planningUnitDao.getPlanningUnitById(planningUnitId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pu.getForeacastingUnit().getRealm().getRealmId())) {
            if ((dtStartDate != null && dtStopDate == null) || (dtStopDate != null && dtStartDate == null)) {
                throw new ParseException("One date cannot be null", 1);
            }
            return this.planningUnitDao.getPlanningUnitCapacityForId(planningUnitId, dtStartDate, dtStopDate, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int savePlanningUnitCapacity(PlanningUnitCapacity[] planningUnitCapacitys, CustomUserDetails curUser) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (PlanningUnitCapacity puc : planningUnitCapacitys) {
            PlanningUnit pu = this.planningUnitDao.getPlanningUnitById(puc.getPlanningUnitId(), curUser);
            if (!this.aclService.checkRealmAccessForUser(curUser, pu.getForeacastingUnit().getRealm().getRealmId())) {
                throw new AccessDeniedException("Access denied");
            }
            sdf.parse(puc.getStartDate());
            sdf.parse(puc.getStopDate());
        }
        return this.planningUnitDao.savePlanningUnitCapacity(planningUnitCapacitys, curUser);
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.getPlanningUnitListForSync(lastSyncDate, curUser);
    }

}
