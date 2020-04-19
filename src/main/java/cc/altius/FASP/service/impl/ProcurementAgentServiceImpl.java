/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProcurementAgentDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProcurementAgentService;
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
public class ProcurementAgentServiceImpl implements ProcurementAgentService {

    @Autowired
    private ProcurementAgentDao procurementAgentDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public int addProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser) {
        return this.procurementAgentDao.addProcurementAgent(procurementAgent, curUser);
    }

    @Override
    public int updateProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser) {
        ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(procurementAgent.getProcurementAgentId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
            return this.procurementAgentDao.updateProcurementAgent(procurementAgent, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentList(boolean active, CustomUserDetails curUser) {
        return this.procurementAgentDao.getProcurementAgentList(active, curUser);
    }

    @Override
    public ProcurementAgent getProcurementAgentById(int procurementAgentId, CustomUserDetails curUser) {
        ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(procurementAgentId, curUser);
        if (pa != null && this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
            return pa;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentByRealm(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.procurementAgentDao.getProcurementAgentByRealm(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitList(int procurementAgentId, boolean active, CustomUserDetails curUser) {
        ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(procurementAgentId, curUser);
        if (pa != null && this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
            return this.procurementAgentDao.getProcurementAgentPlanningUnitList(procurementAgentId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int saveProcurementAgentPlanningUnit(ProcurementAgentPlanningUnit[] procurementAgentPlanningUnits, CustomUserDetails curUser) {
        for (ProcurementAgentPlanningUnit papu : procurementAgentPlanningUnits) {
            ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(papu.getProcurementAgent().getId(), curUser);
            if (!this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
                throw new AccessDeniedException("Access denied");
            }
        }
        return this.procurementAgentDao.saveProcurementAgentPlanningUnit(procurementAgentPlanningUnits, curUser);
    }
    
    
    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitList(int procurementAgentId, boolean active, CustomUserDetails curUser) {
        ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(procurementAgentId, curUser);
        if (pa != null && this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
            return this.procurementAgentDao.getProcurementAgentProcurementUnitList(procurementAgentId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int saveProcurementAgentProcurementUnit(ProcurementAgentProcurementUnit[] procurementAgentProcurementUnits, CustomUserDetails curUser) {
        for (ProcurementAgentProcurementUnit papu : procurementAgentProcurementUnits) {
            ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(papu.getProcurementAgent().getId(), curUser);
            if (!this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
                throw new AccessDeniedException("Access denied");
            }
        }
        return this.procurementAgentDao.saveProcurementAgentProcurementUnit(procurementAgentProcurementUnits, curUser);
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.procurementAgentDao.getProcurementAgentListForSync(lastSyncDate, curUser);
    }

}
