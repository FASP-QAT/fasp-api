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
import cc.altius.FASP.model.ExtrapolationMethod;
import cc.altius.FASP.model.NodeType;
import cc.altius.FASP.model.NodeTypeSync;
import cc.altius.FASP.service.MasterDataService;
import cc.altius.FASP.dao.MasterDataDao;
import cc.altius.FASP.model.ShipmentStatus;
import cc.altius.FASP.model.SimpleObject;

/**
 *
 * @author akil
 */
@Service
public class MasterDataServiceImpl implements MasterDataService {

    @Autowired
    private MasterDataDao masterDataDao;

    @Override
    public List<SimpleBaseModel> getUsageTypeList(boolean active, CustomUserDetails curUser) {
        return this.masterDataDao.getUsageTypeList(active, curUser);
    }

    @Override
    public List<NodeType> getNodeTypeList(boolean active, CustomUserDetails curUser) {
        return this.masterDataDao.getNodeTypeList(active, curUser);
    }

    @Override
    public List<SimpleBaseModel> getForecastMethodTypeList(boolean active, CustomUserDetails curUser) {
        return this.masterDataDao.getForecastMethodTypeList(active, curUser);
    }

    @Override
    public List<SimpleBaseModel> getUsageTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.masterDataDao.getUsageTypeListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<NodeTypeSync> getNodeTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.masterDataDao.getNodeTypeListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<SimpleBaseModel> getForecastMethodTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.masterDataDao.getForecastMethodTypeListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<ExtrapolationMethod> getExtrapolationMethodListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.masterDataDao.getExtrapolationMethodListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<SimpleObject> getVersionTypeList() {
        return this.masterDataDao.getVersionTypeList();
    }

    @Override
    public List<SimpleObject> getVersionStatusList() {
        return this.masterDataDao.getVersionStatusList();
    }
    
    @Override
    public List<ShipmentStatus> getShipmentStatusList(boolean active) {
        return this.masterDataDao.getShipmentStatusList(active);
    }
    @Override
    public List<ShipmentStatus> getShipmentStatusListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.masterDataDao.getShipmentStatusListForSync(lastSyncDate, curUser);
    }
}
