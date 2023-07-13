/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.AutocompleteInputWithTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProductCategoryAndTracerCategoryDTO;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.SimpleObject;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ForecastingUnitService {

    public List<ForecastingUnit> getForecastingUnitList(boolean active, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitList(int realmId, boolean active, CustomUserDetails curUser);

    public int addForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser);

    public int updateForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser);

    public ForecastingUnit getForecastingUnitById(int forecastingUnitId, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitListForDataset(int programId, int versionId, CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitListForAutoComplete(AutoCompleteInput autoCompleteInput, CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitListForAutoCompleteWithFilterTracerCategory(AutocompleteInputWithTracerCategoryDTO autoCompleteInput, CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitDropdownList(CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitDropdownListWithFilterForPuAndTc(ProductCategoryAndTracerCategoryDTO input, CustomUserDetails curUser);

}
