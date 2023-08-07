/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SimpleBaseModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.dao.ForecastingStaticDataDao;
import cc.altius.FASP.model.ExtrapolationMethod;
import cc.altius.FASP.model.NodeType;
import cc.altius.FASP.model.NodeTypeSync;
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
    public List<SimpleBaseModel> getUsageTypeList(boolean active, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getUsageTypeList(active, curUser);
    }

    @Override
    public List<NodeType> getNodeTypeList(boolean active, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getNodeTypeList(active, curUser);
    }

    @Override
    public List<SimpleBaseModel> getForecastMethodTypeList(boolean active, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getForecastMethodTypeList(active, curUser);
    }

    @Override
    public List<SimpleBaseModel> getUsageTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getUsageTypeListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<NodeTypeSync> getNodeTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getNodeTypeListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<SimpleBaseModel> getForecastMethodTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getForecastMethodTypeListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<ExtrapolationMethod> getExtrapolationMethodListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.forecastingStaticDataDao.getExtrapolationMethodListForSync(lastSyncDate, curUser);
    }

}
