/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DataSourceType;
import java.util.List;

/**
 *
 * @author palash
 */
public interface DataSourceTypeService {

    public int addDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser);
    
    public List<DataSourceType> getDataSourceTypeList(boolean active, CustomUserDetails curUser);
    
    public DataSourceType getDataSourceTypeById(int dataSourceTypeId, CustomUserDetails curUser);
    
    public List<DataSourceType> getDataSourceTypeForRealm(int realmId, boolean active, CustomUserDetails curUser);
    
    public int updateDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser);
    
    public List<PrgDataSourceTypeDTO> getDataSourceTypeListForSync(String lastSyncDate);
}
