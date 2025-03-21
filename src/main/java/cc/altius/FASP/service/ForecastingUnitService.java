/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.exception.DuplicateNameException;
import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.AutocompleteInputWithTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProductCategoryAndTracerCategoryDTO;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.ForecastingUnitWithCount;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleForecastingUnitWithUnitObject;
import cc.altius.FASP.model.SimpleObject;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ForecastingUnitService {

    public List<ForecastingUnit> getForecastingUnitList(boolean active, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitList(int realmId, boolean active, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListByIds(List<String> forecastingUnitIdList, CustomUserDetails curUser);

    public int addForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser) throws DuplicateNameException;

    public int updateForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser) throws DuplicateNameException;

    public ForecastingUnit getForecastingUnitById(int forecastingUnitId, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitListForDataset(int programId, int versionId, CustomUserDetails curUser) throws AccessControlFailedException;

    public List<SimpleObject> getForecastingUnitListForAutoComplete(AutoCompleteInput autoCompleteInput, CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitListForAutoCompleteWithFilterTracerCategory(AutocompleteInputWithTracerCategoryDTO autoCompleteInput, CustomUserDetails curUser);

    public List<SimpleForecastingUnitWithUnitObject> getForecastingUnitDropdownList(CustomUserDetails curUser);

    public List<SimpleObject> getForecastingUnitDropdownListWithFilterForPuAndTc(ProductCategoryAndTracerCategoryDTO input, CustomUserDetails curUser);

    public List<ForecastingUnitWithCount> getForecastingUnitByTracerCategoryAndProductCategory(ProductCategoryAndTracerCategoryDTO input, CustomUserDetails curUser);

    public List<SimpleCodeObject> getListOfSpProgramsForForecastingUnitId(int forecastingUnitId, boolean active, CustomUserDetails curUser);

    public List<SimpleCodeObject> getListOfFcProgramsForForecastingUnitId(int forecastingUnitId, boolean active, CustomUserDetails curUser);

}
