/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastMethod;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ForecastMethodService {

    public List<ForecastMethod> getForecastMethodList(boolean active, CustomUserDetails curUser);

    public List<ForecastMethod> getForecastMethodListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public int addAndUpdateForecastMethod(List<ForecastMethod> forecastMethodList, CustomUserDetails curUser);
}
