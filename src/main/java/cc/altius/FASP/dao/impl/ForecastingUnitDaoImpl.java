/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.rowMapper.ForecastingUnitRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import cc.altius.FASP.dao.ForecastingUnitDao;
import cc.altius.FASP.service.AclService;

/**
 *
 * @author altius
 */
@Repository
public class ForecastingUnitDaoImpl implements ForecastingUnitDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private String sqlListString = "SELECT fu.FORECASTING_UNIT_ID,  "
            + "	ful.LABEL_ID, ful.LABEL_EN, ful.LABEL_FR, ful.LABEL_PR, ful.LABEL_SP, "
            + "    pgl.LABEL_ID `GENERIC_LABEL_ID`, pgl.LABEL_EN `GENERIC_LABEL_EN`, pgl.LABEL_FR `GENERIC_LABEL_FR`, pgl.LABEL_PR `GENERIC_LABEL_PR`, pgl.LABEL_SP `GENERIC_LABEL_SP`, "
            + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
            + "    pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
            + "    tc.TRACER_CATEGORY_ID, tcl.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tcl.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tcl.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tcl.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tcl.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, fu.ACTIVE, fu.CREATED_DATE, fu.LAST_MODIFIED_DATE "
            + "FROM rm_forecasting_unit fu  "
            + "LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID "
            + "LEFT JOIN ap_label pgl ON fu.GENERIC_LABEL_ID=pgl.LABEL_ID "
            + "LEFT JOIN rm_realm r ON fu.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + "LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
            + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
            + "LEFT JOIN rm_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
            + "LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID "
            + "LEFT JOIN us_user cb ON fu.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON fu.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    public int addForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_forecasting_unit").usingGeneratedKeyColumns("FORECASTING_UNIT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int labelId = this.labelDao.addLabel(forecastingUnit.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        int genericLabelId = this.labelDao.addLabel(forecastingUnit.getGenericLabel(), curUser.getUserId());
        params.put("GENERIC_LABEL_ID", genericLabelId);
        params.put("REALM_ID", forecastingUnit.getRealm().getId());
        params.put("PRODUCT_CATEGORY_ID", forecastingUnit.getProductCategory().getId());
        params.put("TRACER_CATEGORY_ID", forecastingUnit.getTracerCategory().getId());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_forecasting_unit fu LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID LEFT JOIN ap_label pgl ON fu.GENERIC_LABEL_ID=pgl.LABEL_ID "
                + "SET  "
                + "    fu.PRODUCT_CATEGORY_ID=:productCategoryId, "
                + "    fu.TRACER_CATEGORY_ID=:tracerCategoryId, "
                + "    fu.ACTIVE=:active, "
                + "    fu.LAST_MODIFIED_BY=IF(fu.PRODUCT_CATEGORY_ID!=:productCategoryId OR fu.TRACER_CATEGORY_ID!=:tracerCategoryId OR fu.ACTIVE!=:active,:curUser, fu.LAST_MODIFIED_BY), "
                + "    fu.LAST_MODIFIED_DATE=IF(fu.PRODUCT_CATEGORY_ID!=:productCategoryId OR fu.TRACER_CATEGORY_ID!=:tracerCategoryId OR fu.ACTIVE!=:active,:curDate, fu.LAST_MODIFIED_DATE), "
                + "    ful.LABEL_EN=:labelEn, "
                + "    ful.LAST_MODIFIED_BY=IF(ful.LABEL_EN=:labelEn,:curUser, fu.LAST_MODIFIED_BY), "
                + "    ful.LAST_MODIFIED_DATE=IF(ful.LABEL_EN=:labelEn,:curDate, fu.LAST_MODIFIED_DATE), "
                + "    pgl.LABEL_EN=:genericLabelEn, "
                + "    pgl.LAST_MODIFIED_BY=IF(pgl.LABEL_EN=:genericLabelEn,:curUser, fu.LAST_MODIFIED_BY), "
                + "    pgl.LAST_MODIFIED_DATE=IF(pgl.LABEL_EN=:genericLabelEn,:curDate, fu.LAST_MODIFIED_DATE) "
                + "WHERE fu.FORECASTING_UNIT_ID=:forecastingUnitId";
        Map<String, Object> params = new HashMap<>();
        params.put("forecastingUnitId", forecastingUnit.getForecastingUnitId());
        params.put("productCategoryId", forecastingUnit.getProductCategory().getId());
        params.put("tracerCategoryId", forecastingUnit.getTracerCategory().getId());
        params.put("active", forecastingUnit.isActive());
        params.put("labelEn", forecastingUnit.getLabel().getLabel_en());
        params.put("genericLabelEn", forecastingUnit.getGenericLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder.append(" AND fu.ACTIVE=:active ");
            params.put("active", active);
        }
        if (curUser.getRealm().getRealmId() != -1) {
            sqlStringBuilder.append("AND fu.REALM_ID=:realmId ");
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ForecastingUnitRowMapper());
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitList(int realmId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder.append(" AND fu.ACTIVE=:active ");
            params.put("active", active);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ForecastingUnitRowMapper());
    }

    @Override
    public ForecastingUnit getForecastingUnitById(int forecastingUnitId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND fu.FORECASTING_UNIT_ID=:forecastingUnitId ");
        Map<String, Object> params = new HashMap<>();
        params.put("forecastingUnitId", forecastingUnitId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new ForecastingUnitRowMapper());
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND fu.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ForecastingUnitRowMapper());
    }

}
