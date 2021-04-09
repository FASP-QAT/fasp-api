/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.IntegrationDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Integration;
import cc.altius.FASP.model.IntegrationView;
import cc.altius.FASP.service.IntegrationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class IntegrationServiceImpl implements IntegrationService {

    @Autowired
    private IntegrationDao integrationDao;
    
    @Override
    public int addIntegration(Integration integration, CustomUserDetails curUser) {
        return this.integrationDao.addIntegration(integration, curUser);
    }

    @Override
    public int updateIntegration(Integration integration, CustomUserDetails curUser) {
        return this.integrationDao.updateIntegration(integration, curUser);
    }
    
    @Override
    public List<Integration> getIntegrationList(CustomUserDetails curUser) {
        return this.integrationDao.getIntegrationList(curUser);
    }

    @Override
    public Integration getIntegrationById(int integrationId, CustomUserDetails curUser) {
        return this.integrationDao.getIntegrationById(integrationId, curUser);
    }

    @Override
    public List<IntegrationView> getIntegrationViewList(CustomUserDetails curUser) {
        return this.integrationDao.getIntegrationViewList(curUser);
    }
    
}
