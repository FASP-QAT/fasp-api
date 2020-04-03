/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.PlanningUnit;
import java.util.List;

/**
 *
 * @author altius
 */
public interface PlanningUnitDao {
    
    public List<PlanningUnit> getPlanningUnitList(boolean active, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitList(int realmId, boolean active, CustomUserDetails curUser);
    
    public List<PlanningUnit> getPlanningUnitListByForecastingUnit(int forecastingUnitId, boolean active, CustomUserDetails curUser);

    public int addPlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser);

    public int updatePlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser);

    public PlanningUnit getPlanningUnitById(int planningUnitId, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser);
    
}
