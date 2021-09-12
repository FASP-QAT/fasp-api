/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ModelingTypeDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ModelingType;
import cc.altius.FASP.model.rowMapper.ModelingTypeRowMapper;
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
public class ModelingTypeDaoImpl implements ModelingTypeDao {

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
    public List<ModelingType> getModelingTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    mt.MODELING_TYPE_ID,  "
                + "    mt.LABEL_ID, mt.LABEL_EN, mt.LABEL_FR, mt.LABEL_SP, mt.LABEL_PR, "
                + "    mt.ACTIVE, "
                + "    cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, mt.CREATED_DATE, "
                + "    lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME, mt.LAST_MODIFIED_DATE "
                + "FROM vw_modeling_type mt  "
                + "LEFT JOIN us_user cb ON mt.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON mt.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        if (active) {
            sqlString += " AND mt.ACTIVE ";
        }
        sqlString += "ORDER BY mt.LABEL_EN";
        return namedParameterJdbcTemplate.query(sqlString, new ModelingTypeRowMapper());
    }

    @Override
    @Transactional
    public int addAndUpdateModelingType(List<ModelingType> modelingTypeList, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ap_modeling_type");
        Date dt = DateUtils.getCurrentDateObject(DateUtils.EST);
        List<SqlParameterSource> paramList = new LinkedList<>();
        modelingTypeList.stream().filter(ut -> ut.getModelingTypeId() == 0).collect(Collectors.toList()).forEach(ut -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("LABEL_ID", this.labelDao.addLabel(ut.getLabel(), 41, curUser.getUserId()));
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
        modelingTypeList.stream().filter(ut -> ut.getModelingTypeId() != 0).collect(Collectors.toList()).forEach(ut -> {
            Map<String, Object> param = new HashMap<>();
            param.put("modelingTypeId", ut.getModelingTypeId());
            param.put("labelEn", ut.getLabel().getLabel_en());
            param.put("active", ut.isActive());
            param.put("curUser", curUser.getUserId());
            param.put("dt", dt);
            paramList.add(new MapSqlParameterSource(param));
        }
        );
        String sql = "UPDATE ap_modeling_type mt "
                + "LEFT JOIN ap_label l ON mt.LABEL_ID=l.LABEL_ID "
                + "SET "
                + "mt.ACTIVE=:active, "
                + "mt.LAST_MODIFIED_DATE=:dt, "
                + "mt.LAST_MODIFIED_BY=:curUser, "
                + "l.LABEL_EN=:labelEn, "
                + "l.LAST_MODIFIED_DATE=:dt, "
                + "l.LAST_MODIFIED_BY=:curUser "
                + "WHERE "
                + " mt.MODELING_TYPE_ID=:modelingTypeId AND "
                + " ("
                + "     l.LABEL_EN!=:labelEn OR "
                + "     mt.ACTIVE!=:active"
                + " )";
        if (paramList.size() > 0) {
            updatedRows = namedParameterJdbcTemplate.batchUpdate(sql, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
            rows += Arrays.stream(updatedRows).filter(i -> i > 0).count();
        }
        return rows;
    }

}
