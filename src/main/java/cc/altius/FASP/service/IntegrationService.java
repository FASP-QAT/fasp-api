/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Integration;
import java.util.List;

/**
 *
 * @author akil
 */
public interface IntegrationService {

    public int addIntegration(Integration integration, CustomUserDetails curUser);

    public int updateIntegration(Integration integration, CustomUserDetails curUser);
    
    public List<Integration> getIntegrationList(CustomUserDetails curUser);

    public Integration getIntegrationById(int integrationId, CustomUserDetails curUser);
   
}
