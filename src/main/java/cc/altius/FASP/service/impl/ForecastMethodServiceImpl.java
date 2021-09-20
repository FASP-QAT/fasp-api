/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ForecastMethodDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastMethod;
import cc.altius.FASP.service.ForecastMethodService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class ForecastMethodServiceImpl implements ForecastMethodService {

    @Autowired
    private ForecastMethodDao forecastMethodDao;

    @Override
    public List<ForecastMethod> getForecastMethodList(boolean active, CustomUserDetails curUser) {
        return this.forecastMethodDao.getForecastMethodList(active, curUser);
    }

    @Override
    public List<ForecastMethod> getForecastMethodListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.forecastMethodDao.getForecastMethodListForSync(lastSyncDate, curUser);
    }

    @Override
    public int addAndUpdateForecastMethod(List<ForecastMethod> forecastMethodList, CustomUserDetails curUser) {
        return this.forecastMethodDao.addAndUpdateForecastMethod(forecastMethodList, curUser);
    }

}
