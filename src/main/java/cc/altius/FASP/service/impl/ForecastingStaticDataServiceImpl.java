/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SimpleObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.dao.ForecastingStaticDataDao;
import cc.altius.FASP.service.ForecastingStaticDataService;

/**
 *
 * @author akil
 */
@Service
public class ForecastingStaticDataServiceImpl implements ForecastingStaticDataService {

    @Autowired
    private ForecastingStaticDataDao forecastingStaticDataDao;

    @Override
    public List<SimpleObject> getUsageTypeList(boolean active, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getUsageTypeList(active, curUser);
    }

//    @Override
//    public int addAndUpadteUsageType(List<UsageType> usageTypeList, CustomUserDetails curUser) {
//        return this.usageTypeDao.addAndUpadteUsageType(usageTypeList, curUser);
//    }

    @Override
    public List<SimpleObject> getNodeTypeList(boolean active, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getNodeTypeList(active, curUser);
    }
    
    @Override
    public List<SimpleObject> getForecastMethodTypeList(boolean active, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getForecastMethodTypeList(active, curUser);
    }

}
