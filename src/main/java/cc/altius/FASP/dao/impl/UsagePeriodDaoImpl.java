/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.UsagePeriodDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UsagePeriod;
import cc.altius.FASP.model.rowMapper.UsagePeriodRowMapper;
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
public class UsagePeriodDaoImpl implements UsagePeriodDao {

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
    public List<UsagePeriod> getUsagePeriodList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    up.USAGE_PERIOD_ID,  "
                + "    up.LABEL_ID, up.LABEL_EN, up.LABEL_FR, up.LABEL_SP, up.LABEL_PR, "
                + "    up.CONVERT_TO_MONTH, up.ACTIVE, "
                + "    cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, up.CREATED_DATE, "
                + "    lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME, up.LAST_MODIFIED_DATE "
                + "FROM vw_usage_period up  "
                + "LEFT JOIN us_user cb ON up.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON up.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        if (active) {
            sqlString += " AND up.ACTIVE ";
        }
        sqlString += "ORDER BY up.CONVERT_TO_MONTH";
        return namedParameterJdbcTemplate.query(sqlString, new UsagePeriodRowMapper());
    }

    @Override
    @Transactional
    public int addAndUpdateUsagePeriod(List<UsagePeriod> usagePeriodList, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ap_usage_period");
        Date dt = DateUtils.getCurrentDateObject(DateUtils.EST);
        List<SqlParameterSource> paramList = new LinkedList<>();
        usagePeriodList.stream().filter(ut -> ut.getUsagePeriodId() == 0).collect(Collectors.toList()).forEach(ut -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("LABEL_ID", this.labelDao.addLabel(ut.getLabel(), 40, curUser.getUserId()));
            param.addValue("CONVERT_TO_MONTH", ut.getConvertToMonth());
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
        usagePeriodList.stream().filter(ut -> ut.getUsagePeriodId() != 0).collect(Collectors.toList()).forEach(ut -> {
            Map<String, Object> param = new HashMap<>();
            param.put("usagePeriodId", ut.getUsagePeriodId());
            param.put("labelEn", ut.getLabel().getLabel_en());
            param.put("convertToMonth", ut.getConvertToMonth());
            param.put("active", ut.isActive());
            param.put("curUser", curUser.getUserId());
            param.put("dt", dt);
            paramList.add(new MapSqlParameterSource(param));
        }
        );
        String sql = "UPDATE ap_usage_period up "
                + "LEFT JOIN ap_label l ON up.LABEL_ID=l.LABEL_ID "
                + "SET "
                + "up.CONVERT_TO_MONTH=:convertToMonth, "
                + "up.ACTIVE=:active, "
                + "up.LAST_MODIFIED_DATE=:dt, "
                + "up.LAST_MODIFIED_BY=:curUser, "
                + "l.LABEL_EN=:labelEn, "
                + "l.LAST_MODIFIED_DATE=:dt, "
                + "l.LAST_MODIFIED_BY=:curUser "
                + "WHERE "
                + " up.USAGE_PERIOD_ID=:usagePeriodId AND "
                + "  ("
                + "     l.LABEL_EN!=:labelEn OR "
                + "     up.CONVERT_TO_MONTH!=:convertToMonth OR "
                + "     up.ACTIVE!=:active "
                + "  )";
        if (paramList.size() > 0) {
            updatedRows = namedParameterJdbcTemplate.batchUpdate(sql, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
            rows += Arrays.stream(updatedRows).filter(i -> i > 0).count();
        }
        return rows;
    }

}
