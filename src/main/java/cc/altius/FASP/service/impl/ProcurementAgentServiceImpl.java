/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProcurementAgentDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProcurementAgentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AclService aclService;

    @Override
    public int addProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser) {
        return this.procurementAgentDao.addProcurementAgent(procurementAgent, curUser);
    }

    @Override
    public int updateProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser) {
        ProcurementAgent pa = this.procurementAgentDao.getProcurementAgentById(procurementAgent.getProcurementAgentId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getRealmId())) {
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
        if (pa != null && this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getRealmId())) {
            return pa;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
