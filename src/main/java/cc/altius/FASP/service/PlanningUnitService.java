/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.AutocompleteInputWithProductCategoryDTO;
import cc.altius.FASP.model.DTO.MultipleProgramAndTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProductCategoryTracerCategoryAndForecastingUnitDTO;
import cc.altius.FASP.model.DTO.ProgramAndVersionDTO;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.PlanningUnitCapacity;
import cc.altius.FASP.model.PlanningUnitWithPrices;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForAdjustPlanningUnit;
import cc.altius.FASP.model.SimplePlanningUnitWithPrices;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author altius
 */
public interface PlanningUnitService {

    public List<PlanningUnit> getPlanningUnitList(boolean active, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitList(int realmId, boolean active, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitListByIds(List<String> planningUnitIdList, CustomUserDetails curUser);

    public List<PlanningUnitWithPrices> getPlanningUnitListWithPricesByIds(List<String> planningUnitIdList, CustomUserDetails curUser);

    public List<SimplePlanningUnitForAdjustPlanningUnit> getPlanningUnitListBasic(CustomUserDetails curUser);

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

    public List<SimpleObject> getPlanningUnitListForProductCategoryList(String[] productCategoryIds, int realmCountryId, boolean active, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitListByRealmCountryId(int realmCountryId, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitByProgramAndTracerCategory(MultipleProgramAndTracerCategoryDTO programAndTracerCategory, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser);

    public List<PlanningUnit> getPlanningUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser);

    public List<SimplePlanningUnitWithPrices> getPlanningUnitListWithPricesForProductCategory(int productCategoryId, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitListForAutoComplete(AutoCompleteInput autoCompleteInput, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitListForAutoCompleteFilterForProductCategory(AutocompleteInputWithProductCategoryDTO autoCompleteInput, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitDropDownList(CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitDropDownListFilterProductCategory(String productCategorySortOrder, CustomUserDetails curUser);

    public List<SimpleObject> getPlanningUnitForDatasetByProgramAndVersion(ProgramAndVersionDTO input, CustomUserDetails curUser);
    
    public List<PlanningUnit> getPlanningUnitByTracerCategoryProductCategoryAndForecastingUnit(ProductCategoryTracerCategoryAndForecastingUnitDTO input, CustomUserDetails curUser);

}
