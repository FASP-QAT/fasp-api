/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.DTO.PrgDataSourceDTO;
import cc.altius.FASP.model.DataSource;
import java.util.List;

/**
 *
 * @author palash
 */
public interface DataSourceService {
    
    public int addDataSource(DataSource dataSource);
    
    public List<DataSource> getDataSourceList(boolean active);
    
    public int updateDataSource(DataSource dataSource);
    
    public List<PrgDataSourceDTO> getDataSourceListForSync(String lastSyncDate);
}
