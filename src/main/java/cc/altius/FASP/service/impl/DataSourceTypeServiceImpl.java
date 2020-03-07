/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DataSourceTypeDao;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.service.DataSourceTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class DataSourceTypeServiceImpl implements DataSourceTypeService {

    @Autowired
    private DataSourceTypeDao dataSourceTypeDao;

    @Override
    public int addDataSourceType(DataSourceType dataSourceType) {
        return this.dataSourceTypeDao.addDataSourceType(dataSourceType);
    }

    @Override
    public List<DataSourceType> getDataSourceTypeList(boolean active) {
        return this.dataSourceTypeDao.getDataSourceTypeList(active);
    }

    @Override
    public int updateDataSourceType(DataSourceType dataSourceType) {
        return this.dataSourceTypeDao.updateDataSourceType(dataSourceType);
    }

    @Override
    public List<PrgDataSourceTypeDTO> getDataSourceTypeListForSync(String lastSyncDate) {
        return this.dataSourceTypeDao.getDataSourceTypeListForSync(lastSyncDate);
    }

}
