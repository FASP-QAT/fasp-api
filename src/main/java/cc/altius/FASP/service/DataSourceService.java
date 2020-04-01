/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DataSource;
import java.util.List;

/**
 *
 * @author palash
 */
public interface DataSourceService {
    
    public int addDataSource(DataSource dataSource, CustomUserDetails curUser);
    
    public List<DataSource> getDataSourceList(boolean active, CustomUserDetails curUser);
    
    public DataSource getDataSourceById(int dataSourceId, CustomUserDetails curUser);
    
    public List<DataSource> getDataSourceForRealm(int realmId, boolean active, CustomUserDetails curUser);
    
    public List<DataSource> getDataSourceForDataSourceType(int dataSourceTypeId, boolean active, CustomUserDetails curUser);
    
    public int updateDataSource(DataSource dataSource, CustomUserDetails curUser);
    
    public List<DataSource> getDataSourceListForSync(String lastSyncDate, CustomUserDetails curUser);
}
