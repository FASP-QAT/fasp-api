/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ForecastMethodDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastMethod;
import cc.altius.FASP.model.rowMapper.ForecastMethodRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author akil
 */
@Repository
public class ForecastMethodDaoImpl implements ForecastMethodDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<ForecastMethod> getForecastMethodList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    fm.FORECAST_METHOD_ID,  "
                + "    fm.LABEL_ID, fm.LABEL_EN, fm.LABEL_FR, fm.LABEL_SP, fm.LABEL_PR, "
                + "    fm.ACTIVE, fm.FORECAST_METHOD_TYPE_ID, "
                + "    cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, fm.CREATED_DATE, "
                + "    lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME, fm.LAST_MODIFIED_DATE "
                + "FROM vw_forecast_method fm  "
                + "LEFT JOIN us_user cb ON fm.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON fm.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE AND fm.REALM_ID=:realmId ";
        if (active) {
            sqlString += " AND fm.ACTIVE ";
        }
        sqlString += "ORDER BY fm.LABEL_EN";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", curUser.getRealm().getRealmId());
        return namedParameterJdbcTemplate.query(sqlString, params, new ForecastMethodRowMapper());
    }

    @Override
    @Transactional
    public int addAndUpdateForecastMethod(List<ForecastMethod> forecastMethodList, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_method");
        Date dt = DateUtils.getCurrentDateObject(DateUtils.EST);
        List<SqlParameterSource> paramList = new LinkedList<>();
        forecastMethodList.stream().filter(ut -> ut.getForecastMethodId() == 0).collect(Collectors.toList()).forEach(ut -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("LABEL_ID", this.labelDao.addLabel(ut.getLabel(), 41, curUser.getUserId()));
            param.addValue("REALM_ID", curUser.getRealm().getRealmId());
            param.addValue("FORECAST_METHOD_TYPE_ID", ut.getForecastMethodTypeId());
            param.addValue("ACTIVE", 1);
            param.addValue("CREATED_BY", curUser.getUserId());
            param.addValue("CREATED_DATE", dt);
            param.addValue("LAST_MODIFIED_BY", curUser.getUserId());
            param.addValue("LAST_MODIFIED_DATE", dt);
            paramList.add(param);
        }
        );
        int rows = 0;
        int[] updatedRows;
        if (paramList.size() > 0) {
            updatedRows = si.executeBatch(paramList.toArray(new MapSqlParameterSource[paramList.size()]));
            rows += Arrays.stream(updatedRows).filter(i -> i == 1).count();
            paramList.clear();
        }
        forecastMethodList.stream().filter(ut -> ut.getForecastMethodId() != 0).collect(Collectors.toList()).forEach(ut -> {
            Map<String, Object> param = new HashMap<>();
            param.put("forecastMethodId", ut.getForecastMethodId());
            param.put("labelEn", ut.getLabel().getLabel_en());
            param.put("active", ut.isActive());
            param.put("curUser", curUser.getUserId());
            param.put("dt", dt);
            paramList.add(new MapSqlParameterSource(param));
        }
        );
        String sql = "UPDATE rm_forecast_method fm "
                + "LEFT JOIN ap_label l ON fm.LABEL_ID=l.LABEL_ID "
                + "SET "
                + "fm.ACTIVE=:active, "
                + "fm.LAST_MODIFIED_DATE=:dt, "
                + "fm.LAST_MODIFIED_BY=:curUser, "
                + "l.LABEL_EN=:labelEn, "
                + "l.LAST_MODIFIED_DATE=:dt, "
                + "l.LAST_MODIFIED_BY=:curUser "
                + "WHERE "
                + " fm.FORECAST_METHOD_ID=:forecastMethodId AND "
                + " ("
                + "     l.LABEL_EN!=:labelEn OR "
                + "     fm.ACTIVE!=:active"
                + " )";
        if (paramList.size() > 0) {
            updatedRows = namedParameterJdbcTemplate.batchUpdate(sql, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
            rows += Arrays.stream(updatedRows).filter(i -> i > 0).count();
        }
        return rows;
    }

}
