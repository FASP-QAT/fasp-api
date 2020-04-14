/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DataSourceDao;
import cc.altius.FASP.dao.DataSourceTypeDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.DataSourceService;
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
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceDao dataSourceDao;
    @Autowired
    private DataSourceTypeDao dataSourceTypeDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<DataSource> getDataSourceListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.dataSourceDao.getDataSourceListForSync(lastSyncDate, curUser);
    }

    @Override
    public int addDataSource(DataSource dataSource, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, dataSource.getRealm().getId())) {
            return this.dataSourceDao.addDataSource(dataSource, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateDataSource(DataSource dataSource, CustomUserDetails curUser) {
        DataSource ds = this.dataSourceDao.getDataSourceById(dataSource.getDataSourceId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, ds.getRealm().getId())) {
            return this.dataSourceDao.updateDataSource(dataSource, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public DataSource getDataSourceById(int dataSourceId, CustomUserDetails curUser) {
        DataSource ds = this.dataSourceDao.getDataSourceById(dataSourceId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, ds.getRealm().getId())) {
            return ds;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<DataSource> getDataSourceList(boolean active, CustomUserDetails curUser) {
        return this.dataSourceDao.getDataSourceList(active, curUser);
    }

    @Override
    public List<DataSource> getDataSourceForRealmAndProgram(int realmId, int programId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId) && this.aclService.checkProgramAccessForUser(curUser, programId)) {
            return this.dataSourceDao.getDataSourceForRealmAndProgram(realmId, programId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<DataSource> getDataSourceForDataSourceType(int dataSourceTypeId, boolean active, CustomUserDetails curUser) {
        DataSourceType dst = this.dataSourceTypeDao.getDataSourceTypeById(dataSourceTypeId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, dst.getRealm().getId())) {
            return this.dataSourceDao.getDataSourceForDataSourceType(dataSourceTypeId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
