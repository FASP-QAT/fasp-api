/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DataSourceDao;
import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.service.DataSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceDao dataSourceDao;

    @Override
    public int addDataSource(DataSource dataSource) {
        return this.dataSourceDao.addDataSource(dataSource);
    }

    @Override
    public List<DataSource> getDataSourceList(boolean active) {
        return this.dataSourceDao.getDataSourceList(active);
    }

    @Override
    public int updateDataSource(DataSource dataSource) {
        return this.dataSourceDao.updateDataSource(dataSource);
    }

}
