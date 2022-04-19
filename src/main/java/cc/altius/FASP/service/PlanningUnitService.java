/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramAndTracerCategoryDTO;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.PlanningUnitCapacity;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitObject;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author altius
 */
public interface PlanningUnitService {

    public List<PlanningUnit> getPlanningUnitList(boolean active, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitList(int realmId, boolean active, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitListByForecastingUnit(int forecastingUnitId, boolean active, CustomUserDetails curUser);

    public int addPlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser);

    public int updatePlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser);

    public PlanningUnit getPlanningUnitById(int planningUnitId, CustomUserDetails curUser);

    public List<PlanningUnitCapacity> getPlanningUnitCapacityForRealm(int realmId, String startDate, String stopDate, CustomUserDetails curUser) throws ParseException;

    public List<PlanningUnitCapacity> getPlanningUnitCapacityForId(int planningUnitId, String startDate, String stopDate, CustomUserDetails curUser) throws ParseException;

    public List<PlanningUnitCapacity> getPlanningUnitCapacityList(CustomUserDetails curUser);

    public int savePlanningUnitCapacity(PlanningUnitCapacity[] planningUnitCapacitys, CustomUserDetails curUser) throws ParseException;

    public List<PlanningUnit> getPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<PlanningUnit> getPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitListForProductCategory(int productCategoryId, boolean active, CustomUserDetails curUser);
    
    public List<SimpleObject> getPlanningUnitListForProductCategoryList(String[] productCategoryIds, boolean active, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitListByRealmCountryId(int realmCountryId, CustomUserDetails curUser);
    
    public List<SimpleObject> getPlanningUnitByProgramAndTracerCategory(ProgramAndTracerCategoryDTO programAndTracerCategory, CustomUserDetails curUser);
    
    public List<SimpleObject> getPlanningUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser);
    
    public List<PlanningUnit> getPlanningUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser);
    
    public List<SimplePlanningUnitObject> getPlanningUnitListForDataset(int programId, int versionId, CustomUserDetails curUser);
}
