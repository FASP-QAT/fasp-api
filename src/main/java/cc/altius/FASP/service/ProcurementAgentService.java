/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProcurementAgentService {

    public List<ProcurementAgent> getProcurementAgentList(boolean active, CustomUserDetails curUser);

    public List<ProcurementAgent> getProcurementAgentByRealm(int realmId, CustomUserDetails curUser);

    public int addProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser);

    public int updateProcurementAgent(ProcurementAgent procurementAgent, CustomUserDetails curUser);

    public ProcurementAgent getProcurementAgentById(int procurementAgentId, CustomUserDetails curUser);

    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitList(int procurementAgentId, boolean active, CustomUserDetails curUser);

    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitListForTracerCategory(int procurementAgentId, int planningUnitId, String term, CustomUserDetails curUser);

    public int saveProcurementAgentPlanningUnit(ProcurementAgentPlanningUnit[] procurementAgentPlanningUnits, CustomUserDetails curUser);

    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitList(int procurementAgentId, boolean active, CustomUserDetails curUser);

    public int saveProcurementAgentProcurementUnit(ProcurementAgentProcurementUnit[] procurementAgentProcurementUnits, CustomUserDetails curUser);

    public List<ProcurementAgent> getProcurementAgentListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser);

    public String getDisplayName(int realmId, String name, CustomUserDetails curUser);

    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);
    
    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);
}
