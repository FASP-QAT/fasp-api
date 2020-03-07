/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DataSourceType;
import java.util.List;

/**
 *
 * @author palash
 */
public interface DataSourceTypeDao {
    
    public int addDataSourceType(DataSourceType dataSourceType);
    
    public List<DataSourceType> getDataSourceTypeList(boolean  active);
    
    public int updateDataSourceType(DataSourceType dataSourceType);
    
    public List<PrgDataSourceTypeDTO> getDataSourceTypeListForSync(String lastSyncDate);
}
