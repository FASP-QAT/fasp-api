/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ProcurementAgentDao {

    public List<ProcurementAgent> getProcurementAgentList(boolean active, CustomUserDetails curUser);

    public List<ProcurementAgent> getProcurementAgentByRealm(int realmId, CustomUserDetails curUser);

    public int addProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser);

    public int updateProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser);

    public ProcurementAgent getProcurementAgentById(int procurementAgentId, CustomUserDetails curUser);
    
    public List<ProcurementAgent> getProcurementAgentListForSync(String lastSyncDate, CustomUserDetails curUser);
}
