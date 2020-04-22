/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Dimension;
import cc.altius.FASP.model.rowMapper.DimensionRowMapper;

import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import cc.altius.FASP.dao.DimensionDao;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @adhor altius
 */
@Repository
public class DimensionDaoImpl implements DimensionDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListString = "SELECT d.DIMENSION_ID,  "
            + "	dl.LABEL_ID, dl.LABEL_EN, dl.LABEL_FR, dl.LABEL_PR, dl.LABEL_SP, "
            + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, d.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, d.LAST_MODIFIED_DATE, d.ACTIVE  "
            + " FROM ap_dimension d  "
            + " LEFT JOIN ap_label dl ON d.LABEL_ID=dl.LABEL_ID "
            + " LEFT JOIN us_user cb ON d.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON d.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE ";

    @Override
    public int addDimension(Dimension dimension, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int labelId = this.labelDao.addLabel(dimension.getLabel(), curUser.getUserId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ap_dimension").usingGeneratedKeyColumns("DIMENSION_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_ID", labelId);
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("ACTIVE", 1);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateDimension(Dimension dimension, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE ap_dimension d LEFT JOIN ap_label dl ON d.LABEL_ID=dl.LABEL_ID "
                + "SET  "
                + "	d.ACTIVE=:active, "
                + "	d.LAST_MODIFIED_BY = IF(d.ACTIVE!=:active, :curUser, d.LAST_MODIFIED_BY), "
                + "    d.LAST_MODIFIED_DATE = IF(d.ACTIVE!=:active, :curDate, d.LAST_MODIFIED_DATE), "
                + "    dl.LABEL_EN=:label_en,  "
                + "    dl.LAST_MODIFIED_BY = IF(dl.LABEL_EN!=:label_en, :curUser, dl.LAST_MODIFIED_BY), "
                + "    dl.LAST_MODIFIED_DATE = IF(dl.LABEL_EN!=:label_en, :curDate, dl.LAST_MODIFIED_DATE) "
                + "WHERE d.DIMENSION_ID=:dimensionId";
        Map<String, Object> params = new HashMap<>();
        params.put("active", dimension.isActive());
        params.put("label_en", dimension.getLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("dimensionId", dimension.getDimensionId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Dimension> getDimensionList(boolean active) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if(active) {
            sqlStringBuilder.append(" AND d.ACTIVE");
            params.put("active", active);
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new DimensionRowMapper());
    }

    @Override
    public Dimension getDimensionById(int dimensionId) {
        String sqlString = this.sqlListString + " AND d.DIMENSION_ID=:dimensionId ";
        Map<String, Object> params = new HashMap<>();
        params.put("dimensionId", dimensionId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DimensionRowMapper());
    }

    @Override
    public List<Dimension> getDimensionListForSync(String lastSyncDate) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append("AND d.LAST_MODIFIED_DATE>=:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new DimensionRowMapper());
    }
}
