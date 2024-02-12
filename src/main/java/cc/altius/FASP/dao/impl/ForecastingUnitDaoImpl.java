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
import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.DTO.AutocompleteInputWithTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProductCategoryAndTracerCategoryDTO;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.SimpleForecastingUnitWithUnitObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.SimpleForecastingUnitWithUnitObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author altius
 */
@Repository
public class ForecastingUnitDaoImpl implements ForecastingUnitDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private String sqlListString = "SELECT fu.FORECASTING_UNIT_ID,  "
            + "	fu.LABEL_ID, fu.LABEL_EN, fu.LABEL_FR, fu.LABEL_PR, fu.LABEL_SP, "
            + "    pgl.LABEL_ID `GENERIC_LABEL_ID`, pgl.LABEL_EN `GENERIC_LABEL_EN`, pgl.LABEL_FR `GENERIC_LABEL_FR`, pgl.LABEL_PR `GENERIC_LABEL_PR`, pgl.LABEL_SP `GENERIC_LABEL_SP`, "
            + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`, "
            + "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_PR `UNIT_LABEL_PR`, u.LABEL_SP `UNIT_LABEL_SP`, "
            + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
            + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, fu.ACTIVE, fu.CREATED_DATE, fu.LAST_MODIFIED_DATE "
            + "FROM vw_forecasting_unit fu  "
            + "LEFT JOIN ap_label pgl ON fu.GENERIC_LABEL_ID=pgl.LABEL_ID "
            + "LEFT JOIN vw_realm r ON fu.REALM_ID=r.REALM_ID "
            + "LEFT JOIN vw_unit u ON fu.UNIT_ID=u.UNIT_ID "
            + "LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
            + "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
            + "LEFT JOIN us_user cb ON fu.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON fu.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    public int addForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_forecasting_unit").usingGeneratedKeyColumns("FORECASTING_UNIT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int labelId = this.labelDao.addLabel(forecastingUnit.getLabel(), LabelConstants.RM_FORECASTING_UNIT, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        int genericLabelId = this.labelDao.addLabel(forecastingUnit.getGenericLabel(), LabelConstants.RM_FORECASTING_UNIT_GENERIC_NAME, curUser.getUserId());
        params.put("GENERIC_LABEL_ID", genericLabelId);
        params.put("REALM_ID", forecastingUnit.getRealm().getId());
        params.put("UNIT_ID", forecastingUnit.getUnit().getId());
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
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("UPDATE rm_forecasting_unit fu LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID LEFT JOIN ap_label pgl ON fu.GENERIC_LABEL_ID=pgl.LABEL_ID "
                + "SET  "
                + "    fu.PRODUCT_CATEGORY_ID=:productCategoryId, "
                + "    fu.TRACER_CATEGORY_ID=:tracerCategoryId, "
                + "    fu.ACTIVE=:active, "
                + "    fu.LAST_MODIFIED_BY=:curUser, "
                + "    fu.LAST_MODIFIED_DATE=:curDate, "
                + "    ful.LABEL_EN=:labelEn, "
                + "    ful.LAST_MODIFIED_BY=:curUser, "
                + "    ful.LAST_MODIFIED_DATE=:curDate, "
                + "    pgl.LABEL_EN=:genericLabelEn, "
                + "    pgl.LAST_MODIFIED_BY=:curUser, "
                + "    pgl.LAST_MODIFIED_DATE=:curDate ");
        params.put("productCategoryId", forecastingUnit.getProductCategory().getId());
        params.put("tracerCategoryId", forecastingUnit.getTracerCategory().getId());
        params.put("active", forecastingUnit.isActive());
        params.put("labelEn", forecastingUnit.getLabel().getLabel_en());
        params.put("genericLabelEn", forecastingUnit.getGenericLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        if (curUser.hasBusinessFunction("ROLE_BF_UPDATE_UNIT_FOR_FU")) {
            sb.append(",    fu.UNIT_ID=:unitId ");
            params.put("unitId", forecastingUnit.getUnit().getId());
        }
        sb.append("WHERE fu.FORECASTING_UNIT_ID=:forecastingUnitId");
        params.put("forecastingUnitId", forecastingUnit.getForecastingUnitId());

        int rows = this.namedParameterJdbcTemplate.update(sb.toString(), params);
        if (!forecastingUnit.isActive()) {
            String sqlString = "SELECT p.`PLANNING_UNIT_ID` FROM rm_planning_unit p WHERE p.`FORECASTING_UNIT_ID`=?;";
            List<Integer> list = this.jdbcTemplate.queryForList(sqlString, Integer.class, forecastingUnit.getForecastingUnitId());

            MapSqlParameterSource[] batchParams;
            int x;
            params.clear();
            batchParams = new MapSqlParameterSource[list.size()];
            x = 0;
            sqlString = "UPDATE rm_program_planning_unit p SET p.`ACTIVE`=0 WHERE p.`PLANNING_UNIT_ID`=:planningUnitId;";

            for (int planningUnitId : list) {
                params.put("planningUnitId", planningUnitId);
                batchParams[x] = new MapSqlParameterSource(params);
                x++;
            }
            namedParameterJdbcTemplate.batchUpdate(sqlString, batchParams);
            sqlString = "UPDATE rm_procurement_agent_planning_unit p SET p.`ACTIVE`=0 WHERE p.`PLANNING_UNIT_ID`=:planningUnitId;";
            namedParameterJdbcTemplate.batchUpdate(sqlString, batchParams);
            sqlString = "UPDATE rm_procurement_unit p "
                    + "LEFT JOIN rm_procurement_agent_procurement_unit pu ON pu.`PROCUREMENT_UNIT_ID`=p.`PROCUREMENT_UNIT_ID` "
                    + "SET p.`ACTIVE`=0,pu.`ACTIVE`=0 "
                    + "WHERE p.`PLANNING_UNIT_ID`=:planningUnitId;;";
            namedParameterJdbcTemplate.batchUpdate(sqlString, batchParams);
            sqlString = "UPDATE rm_planning_unit p SET p.`ACTIVE`=0 WHERE p.`FORECASTING_UNIT_ID`=?;";
            this.jdbcTemplate.update(sqlString, forecastingUnit.getForecastingUnitId());
        }
        return rows;
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
    public List<ForecastingUnit> getForecastingUnitListByIds(List<String> forecastingUnitIdList, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        sqlStringBuilder.append(" AND FIND_IN_SET(fu.FORECASTING_UNIT_ID, :fuList) ");
        params.put("fuList", ArrayUtils.convertListToString(forecastingUnitIdList));
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
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

    @Override
    public List<ForecastingUnit> getForecastingUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT fu.FORECASTING_UNIT_ID,  "
                + "	fu.LABEL_ID, fu.LABEL_EN, fu.LABEL_FR, fu.LABEL_PR, fu.LABEL_SP, "
                + "    pgl.LABEL_ID `GENERIC_LABEL_ID`, pgl.LABEL_EN `GENERIC_LABEL_EN`, pgl.LABEL_FR `GENERIC_LABEL_FR`, pgl.LABEL_PR `GENERIC_LABEL_PR`, pgl.LABEL_SP `GENERIC_LABEL_SP`, "
                + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`, "
                + "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_PR `UNIT_LABEL_PR`, u.LABEL_SP `UNIT_LABEL_SP`, "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
                + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, fu.ACTIVE, fu.CREATED_DATE, fu.LAST_MODIFIED_DATE "
                + "FROM vw_forecasting_unit fu "
                + "LEFT JOIN ap_label pgl ON fu.GENERIC_LABEL_ID=pgl.LABEL_ID "
                + "LEFT JOIN vw_realm r ON fu.REALM_ID=r.REALM_ID "
                + "LEFT JOIN vw_unit u ON fu.UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "LEFT JOIN us_user cb ON fu.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON fu.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE fu.FORECASTING_UNIT_ID IN ("
                + "SELECT pu.FORECASTING_UNIT_ID "
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
                + "LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") AND pu.`FORECASTING_UNIT_ID`  IS NOT NULL ");
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append("UNION "
                + "SELECT fu.FORECASTING_UNIT_ID "
                + "FROM vw_dataset p "
                + "LEFT JOIN rm_tracer_category tc ON find_in_set(tc.HEALTH_AREA_ID, p.HEALTH_AREA_ID) "
                + "LEFT JOIN vw_forecasting_unit fu ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "WHERE p.PROGRAM_ID IN ("
        ).append(programIdsString).append(") AND fu.`FORECASTING_UNIT_ID`  IS NOT NULL ");
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(")");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ForecastingUnitRowMapper());
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder(this.sqlListString).append(" AND fu.TRACER_CATEGORY_ID=:tracerCategoryId ");
        if (active) {
            sb.append(" AND fu.ACTIVE ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("tracerCategoryId", tracerCategoryId);
        this.aclService.addUserAclForRealm(sb, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ForecastingUnitRowMapper());
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder(this.sqlListString).append(" AND FIND_IN_SET(fu.TRACER_CATEGORY_ID, :tracerCategoryIds) ");
        if (active) {
            sb.append(" AND fu.ACTIVE ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("tracerCategoryIds", String.join(",", tracerCategoryIds));
        this.aclService.addUserAclForRealm(sb, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ForecastingUnitRowMapper());
    }

    @Override
    public List<SimpleObject> getForecastingUnitListForDataset(int programId, int versionId, CustomUserDetails curUser) {
        String sql = "SELECT fu.FORECASTING_UNIT_ID `ID`, fu.LABEL_ID, fu.LABEL_EN, fu.LABEL_FR, fu.LABEL_SP, fu.LABEL_PR FROM rm_dataset_planning_unit dpu LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID WHERE dpu.PROGRAM_ID=:programId AND dpu.VERSION_ID=:versionId";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        return this.namedParameterJdbcTemplate.query(sql, params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getForecastingUnitListForAutoComplete(AutoCompleteInput autoCompleteInput, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT fu.FORECASTING_UNIT_ID `ID`, fu.LABEL_ID, fu.LABEL_EN, fu.LABEL_FR, fu.LABEL_SP, fu.LABEL_PR FROM vw_forecasting_unit fu WHERE fu.ACTIVE AND (COALESCE(fu.LABEL_").append(autoCompleteInput.getLanguage()).append(",fu.LABEL_EN) LIKE CONCAT('%',:searchText '%') OR fu.FORECASTING_UNIT_ID LIKE CONCAT('%',:searchText,'%')) ");
        Map<String, Object> params = new HashMap<>();
        params.put("searchText", autoCompleteInput.getSearchText());
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        stringBuilder.append(" ORDER BY COALESCE(fu.LABEL_").append(autoCompleteInput.getLanguage()).append(",fu.LABEL_EN)");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getForecastingUnitListForAutoCompleteWithFilterTracerCategory(AutocompleteInputWithTracerCategoryDTO autoCompleteInput, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT fu.FORECASTING_UNIT_ID `ID`, fu.LABEL_ID, fu.LABEL_EN, fu.LABEL_FR, fu.LABEL_SP, fu.LABEL_PR FROM vw_forecasting_unit fu WHERE fu.ACTIVE AND (COALESCE(fu.LABEL_").append(autoCompleteInput.getLanguage()).append(",fu.LABEL_EN) LIKE CONCAT('%',:searchText '%') OR fu.FORECASTING_UNIT_ID LIKE CONCAT('%',:searchText,'%')) ");
        Map<String, Object> params = new HashMap<>();
        params.put("searchText", autoCompleteInput.getSearchText());
        if (autoCompleteInput.getTracerCategoryId() != null) {
            stringBuilder.append(" AND fu.TRACER_CATEGORY_ID=:tracerCategoryId ");
            params.put("tracerCategoryId", autoCompleteInput.getTracerCategoryId());
        }
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        stringBuilder.append(" ORDER BY COALESCE(fu.LABEL_").append(autoCompleteInput.getLanguage()).append(",fu.LABEL_EN)");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleForecastingUnitWithUnitObject> getForecastingUnitDropdownList(CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT fu.`FORECASTING_UNIT_ID` `ID`, fu.`LABEL_ID`, fu.`LABEL_EN`, fu.`LABEL_FR`, fu.`LABEL_SP`, fu.`LABEL_PR`, u.`UNIT_ID` `U_ID`, u.`LABEL_ID` `U_LABEL_ID`, u.`LABEL_EN` `U_LABEL_EN`, u.`LABEL_FR` `U_LABEL_FR`, u.`LABEL_SP` `U_LABEL_SP`, u.`LABEL_PR` `U_LABEL_PR`, u.`UNIT_CODE` `U_CODE` FROM vw_forecasting_unit fu LEFT JOIN vw_unit u ON fu.UNIT_ID=u.UNIT_ID WHERE fu.`ACTIVE` ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        stringBuilder.append(" ORDER BY fu.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleForecastingUnitWithUnitObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getForecastingUnitDropdownListWithFilterForPuAndTc(ProductCategoryAndTracerCategoryDTO input, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT fu.FORECASTING_UNIT_ID `ID`, fu.LABEL_ID, fu.LABEL_EN, fu.LABEL_FR, fu.LABEL_SP, fu.LABEL_PR FROM vw_forecasting_unit fu LEFT JOIN rm_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID WHERE fu.ACTIVE AND pc.ACTIVE AND tc.ACTIVE");
        Map<String, Object> params = new HashMap<>();
        if (input.getProductCategorySortOrder() != null) {
            stringBuilder.append(" AND pc.SORT_ORDER LIKE CONCAT(:productCategorySortOrder,'%') ");
            params.put("productCategorySortOrder", input.getProductCategorySortOrder());
        }
        if (input.getTracerCategoryId() != null) {
            stringBuilder.append(" AND fu.TRACER_CATEGORY_ID=:tracerCategoryId ");
            params.put("tracerCategoryId", input.getTracerCategoryId());
        }
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        stringBuilder.append(" ORDER BY fu.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

}
