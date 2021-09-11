/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import cc.altius.FASP.dao.ForecastingStaticDataDao;

/**
 *
 * @author akil
 */
@Repository
public class ForecastingStaticDataDaoImpl implements ForecastingStaticDataDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<SimpleObject> getUsageTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT ut.USAGE_TYPE_ID ID, "
                + "ut.LABEL_ID, ut.LABEL_EN, ut.LABEL_FR, ut.LABEL_SP, ut.LABEL_PR "
                + "FROM vw_usage_type ut "
                + "WHERE 1 ";
        if (active) {
            sqlString += " AND ut.ACTIVE ORDER BY ut.LABEL_EN";
        }
        return namedParameterJdbcTemplate.query(sqlString, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getNodeTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT nt.NODE_TYPE_ID ID, "
                + "nt.LABEL_ID, nt.LABEL_EN, nt.LABEL_FR, nt.LABEL_SP, nt.LABEL_PR "
                + "FROM vw_node_type nt "
                + "WHERE 1 ";
        if (active) {
            sqlString += " AND nt.ACTIVE";
        }
        return namedParameterJdbcTemplate.query(sqlString, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getForecastMethodTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT nt.FORECAST_METHOD_TYPE_ID ID, "
                + "nt.LABEL_ID, nt.LABEL_EN, nt.LABEL_FR, nt.LABEL_SP, nt.LABEL_PR "
                + "FROM vw_forecast_method_type nt "
                + "WHERE 1 ";
        if (active) {
            sqlString += " AND nt.ACTIVE";
        }
        return namedParameterJdbcTemplate.query(sqlString, new SimpleObjectRowMapper());
    }

//    @Override
//    public int addAndUpadteUsageType(List<UsageType> usageTypeList, CustomUserDetails curUser) {
//        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ap_usage_type");
//        usageTypeList.stream().filter(ut -> ut.getUsageTypeId() == 0).collect(Collectors.toList()).forEach(ut -> {
//            
//        }
//        );
//    }
}
