/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DataSourceTypeDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.DataSourceTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class DataSourceTypeServiceImpl implements DataSourceTypeService {

    @Autowired
    private AclService aclService;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private DataSourceTypeDao dataSourceTypeDao;

    @Override
    public int addDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser) {
        return this.dataSourceTypeDao.addDataSourceType(dataSourceType, curUser);
    }

    @Override
    public int updateDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser) {
        DataSourceType ds = this.dataSourceTypeDao.getDataSourceTypeById(dataSourceType.getDataSourceTypeId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, ds.getRealm().getId())) {
            return this.dataSourceTypeDao.updateDataSourceType(dataSourceType, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public DataSourceType getDataSourceTypeById(int dataSourceTypeId, CustomUserDetails curUser) {
        DataSourceType ds = this.dataSourceTypeDao.getDataSourceTypeById(dataSourceTypeId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, ds.getRealm().getId())) {
            return ds;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<DataSourceType> getDataSourceTypeList(boolean active, CustomUserDetails curUser) {
        return this.dataSourceTypeDao.getDataSourceTypeList(active, curUser);
    }

    @Override
    public List<DataSourceType> getDataSourceTypeForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.dataSourceTypeDao.getDataSourceTypeForRealm(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<DataSourceType> getDataSourceTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.dataSourceTypeDao.getDataSourceTypeListForSync(lastSyncDate, curUser);
    }

}
