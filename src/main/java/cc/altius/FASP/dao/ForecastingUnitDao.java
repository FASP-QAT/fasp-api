/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastingUnit;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ForecastingUnitDao {

    public List<ForecastingUnit> getForecastingUnitList(boolean active, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitList(int realmId, boolean active, CustomUserDetails curUser);

    public int addForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser);

    public int updateForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser);

    public ForecastingUnit getForecastingUnitById(int forecastingUnitId, CustomUserDetails curUser);

    public List<ForecastingUnit> getForecastingUnitListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<ForecastingUnit> getForecastingUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);
    
    public List<ForecastingUnit> getForecastingUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser);
}
