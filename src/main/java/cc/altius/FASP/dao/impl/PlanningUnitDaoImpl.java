/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.PlanningUnitDao;
import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.AutocompleteInputWithProductCategoryDTO;
import cc.altius.FASP.model.DTO.MultipleProgramAndTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProgramAndVersionDTO;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.PlanningUnitCapacity;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForAdjustPlanningUnit;
import cc.altius.FASP.model.SimplePlanningUnitWithPrices;
import cc.altius.FASP.model.rowMapper.PlanningUnitCapacityRowMapper;
import cc.altius.FASP.model.rowMapper.PlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimplePlanningUnitForAdjustPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.SimplePlanningUnitWithPricesListResultSetExtractor;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class PlanningUnitDaoImpl implements PlanningUnitDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private String sqlListString = "SELECT pu.PLANNING_UNIT_ID, pu.MULTIPLIER, fu.FORECASTING_UNIT_ID, "
            + "	pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_PR, pu.LABEL_SP, "
            + "    fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
            + "    fuu.UNIT_ID FU_UNIT_ID, fuu.UNIT_CODE FU_UNIT_CODE, fuu.LABEL_ID `FU_UNIT_LABEL_ID`, fuu.LABEL_EN `FU_UNIT_LABEL_EN`, fuu.LABEL_FR `FU_UNIT_LABEL_FR`, fuu.LABEL_PR `FU_UNIT_LABEL_PR`, fuu.LABEL_SP `FU_UNIT_LABEL_SP`, "
            + "    fugl.LABEL_ID `GENERIC_LABEL_ID`, fugl.LABEL_EN `GENERIC_LABEL_EN`, fugl.LABEL_FR `GENERIC_LABEL_FR`, fugl.LABEL_PR `GENERIC_LABEL_PR`, fugl.LABEL_SP `GENERIC_LABEL_SP`, "
            + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`, "
            + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
            + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, "
            + "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_PR `UNIT_LABEL_PR`, u.LABEL_SP `UNIT_LABEL_SP`, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pu.ACTIVE, pu.CREATED_DATE, pu.LAST_MODIFIED_DATE  "
            + " FROM vw_planning_unit pu "
            + " LEFT JOIN vw_forecasting_unit fu on pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
            + " LEFT JOIN ap_label fugl ON fu.GENERIC_LABEL_ID=fugl.LABEL_ID  "
            + " LEFT JOIN vw_realm r ON fu.REALM_ID=r.REALM_ID  "
            + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
            + " LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID  "
            + " LEFT JOIN vw_unit u ON pu.UNIT_ID=u.UNIT_ID "
            + " LEFT JOIN us_user cb ON pu.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON pu.LAST_MODIFIED_BY=lmb.USER_ID "
            + " LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID "
            + " WHERE TRUE ";

    private final String sqlPlanningUnitCapacityListString = "SELECT  "
            + "     puc.PLANNING_UNIT_CAPACITY_ID, puc.START_DATE, puc.STOP_DATE, puc.CAPACITY, "
            + "     pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, "
            + "     s.SUPPLIER_ID, s.LABEL_ID `SUPPLIER_LABEL_ID`, s.LABEL_EN `SUPPLIER_LABEL_EN`, s.LABEL_FR `SUPPLIER_LABEL_FR`, s.LABEL_SP `SUPPLIER_LABEL_SP`, s.LABEL_PR `SUPPLIER_LABEL_PR`, "
            + "     puc.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, puc.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, puc.LAST_MODIFIED_DATE,puc.`ACTIVE` "
            + " FROM rm_planning_unit_capacity puc  "
            + " LEFT JOIN vw_planning_unit pu ON puc.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
            + " LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
            + " LEFT JOIN vw_supplier s ON puc.SUPPLIER_ID=s.SUPPLIER_ID "
            + " LEFT JOIN us_user cb ON puc.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON puc.LAST_MODIFIED_BY=lmb.USER_ID"
            + " WHERE TRUE";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int addPlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_planning_unit").usingGeneratedKeyColumns("PLANNING_UNIT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int labelId = this.labelDao.addLabel(planningUnit.getLabel(), LabelConstants.RM_PLANNING_UNIT, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("FORECASTING_UNIT_ID", planningUnit.getForecastingUnit().getForecastingUnitId());
        params.put("UNIT_ID", planningUnit.getUnit().getId());
        params.put("MULTIPLIER", planningUnit.getMultiplier());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updatePlanningUnit(PlanningUnit planningUnit, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_planning_unit pu LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID "
                + "SET  "
                + "    pu.MULTIPLIER=:multiplier, "
                + "    pu.UNIT_ID=:unitId, "
                + "    pu.ACTIVE=:active, "
                + "    pu.LAST_MODIFIED_BY=:curUser, "
                + "    pu.LAST_MODIFIED_DATE=:curDate, "
                + "    pul.LABEL_EN=:labelEn, "
                + "    pul.LAST_MODIFIED_BY=:curUser, "
                + "    pul.LAST_MODIFIED_DATE=:curDate "
                + "WHERE pu.PLANNING_UNIT_ID=:planningUnitId";
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", planningUnit.getPlanningUnitId());
        params.put("unitId", planningUnit.getUnit().getId());
        params.put("active", planningUnit.isActive());
        params.put("labelEn", planningUnit.getLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("multiplier", planningUnit.getMultiplier());
        int rows = this.namedParameterJdbcTemplate.update(sqlString, params);

        if (!planningUnit.isActive()) {
            // Program planning unit
            sqlString = "UPDATE rm_program_planning_unit p SET p.`ACTIVE`=0,p.LAST_MODIFIED_DATE=:curDate, p.LAST_MODIFIED_BY=:curUser WHERE p.`PLANNING_UNIT_ID`=:planningUnitId";
            this.namedParameterJdbcTemplate.update(sqlString, params);
            // Procurement agent planning unit
            sqlString = "UPDATE rm_procurement_agent_planning_unit p SET p.`ACTIVE`=0,p.LAST_MODIFIED_DATE=:curDate, p.LAST_MODIFIED_BY=:curUser WHERE p.`PLANNING_UNIT_ID`=:planningUnitId";
            this.namedParameterJdbcTemplate.update(sqlString, params);
            // Procurement unit and procurement agent procurement unit
            sqlString = "UPDATE rm_procurement_unit p LEFT JOIN rm_procurement_agent_procurement_unit pu ON pu.`PROCUREMENT_UNIT_ID`=p.`PROCUREMENT_UNIT_ID` SET p.`ACTIVE`=0, pu.`ACTIVE`=0,p.LAST_MODIFIED_DATE=:curDate, p.LAST_MODIFIED_BY=:curUser,pu.LAST_MODIFIED_DATE=:curDate, pu.LAST_MODIFIED_BY=:curUser "
                    + "WHERE p.`PLANNING_UNIT_ID`=:planningUnitId";
            this.namedParameterJdbcTemplate.update(sqlString, params);
        }
        return rows;
    }

    @Override
    public List<PlanningUnit> getPlanningUnitList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder.append(" AND pu.ACTIVE=:active ");
            params.put("active", active);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListByIds(List<String> planningUnitIdList, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        sqlStringBuilder.append(" AND FIND_IN_SET(pu.PLANNING_UNIT_ID, :puList) ");
        params.put("puList", ArrayUtils.convertListToString(planningUnitIdList));
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public List<PlanningUnit> getPlanningUnitList(int realmId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", realmId, curUser);
        if (active) {
            sqlStringBuilder.append(" AND pu.ACTIVE=:active ");
            params.put("active", active);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public List<SimplePlanningUnitForAdjustPlanningUnit> getPlanningUnitListBasic(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT "
                + "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, "
                + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR` "
                + "FROM vw_planning_unit pu "
                + "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN rm_realm r ON fu.REALM_ID=r.REALM_ID "
                + "WHERE pu.ACTIVE");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimplePlanningUnitForAdjustPlanningUnitRowMapper());
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListByForecastingUnit(int forecastingUnitId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND pu.FORECASTING_UNIT_ID=:forecastingUnitId ");
        Map<String, Object> params = new HashMap<>();
        params.put("forecastingUnitId", forecastingUnitId);
        if (active) {
            sqlStringBuilder.append(" AND pu.ACTIVE=:active ");
            params.put("active", active);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public PlanningUnit getPlanningUnitById(int planningUnitId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND pu.PLANNING_UNIT_ID=:planningUnitId ");
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", planningUnitId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityList(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlPlanningUnitCapacityListString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitCapacityRowMapper());
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityForRealm(int realmId, String startDate, String stopDate, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlPlanningUnitCapacityListString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", realmId, curUser);
        if (startDate != null && stopDate != null) {
            sqlStringBuilder.append(" AND puc.START_DATE BETWEEN :startDate AND :stopDate AND puc.STOP_DATE BETWEEN :startDate AND :stopDate");
            params.put("startDate", startDate);
            params.put("stopDate", stopDate);
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitCapacityRowMapper());
    }

    @Override
    public List<PlanningUnitCapacity> getPlanningUnitCapacityForId(int planningUnitId, String startDate, String stopDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlPlanningUnitCapacityListString).append(" AND puc.PLANNING_UNIT_ID=:planningUnitId");
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", planningUnitId);
        if (startDate != null && stopDate != null) {
            sqlStringBuilder.append(" AND puc.START_DATE BETWEEN :startDate AND :stopDate AND puc.STOP_DATE BETWEEN :startDate AND :stopDate");
            params.put("startDate", startDate);
            params.put("stopDate", stopDate);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitCapacityRowMapper());
    }

    @Override
    public int savePlanningUnitCapacity(PlanningUnitCapacity[] planningUnitCapacitys, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_planning_unit_capacity");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (PlanningUnitCapacity puc : planningUnitCapacitys) {
            if (puc.getPlanningUnitCapacityId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("PLANNING_UNIT_ID", puc.getPlanningUnit().getId());
                params.put("SUPPLIER_ID", puc.getSupplier().getId());
                params.put("START_DATE", puc.getStartDate());
                params.put("STOP_DATE", puc.getStopDate());
                params.put("CAPACITY", puc.getCapacity());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                // Update
                params = new HashMap<>();
                params.put("planningUnitCapacityId", puc.getPlanningUnitCapacityId());
                params.put("startDate", puc.getStartDate());
                params.put("stopDate", puc.getStopDate());
                params.put("capacity", puc.getCapacity());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", puc.isActive());
                updateList.add(new MapSqlParameterSource(params));
            }
        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        if (updateList.size() > 0) {
            SqlParameterSource[] updateParams = new SqlParameterSource[updateList.size()];
            String sqlString = "UPDATE "
                    + "rm_planning_unit_capacity puc SET puc.START_DATE=:startDate, puc.STOP_DATE=:stopDate, puc.CAPACITY=:capacity, puc.ACTIVE=:active, "
                    + "puc.LAST_MODIFIED_DATE=:curDate, "
                    + "puc.LAST_MODIFIED_BY=:curUser "
                    + "WHERE puc.PLANNING_UNIT_CAPACITY_ID=:planningUnitCapacityId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND pu.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID, pu.MULTIPLIER, fu.FORECASTING_UNIT_ID, "
                + "	pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_PR, pu.LABEL_SP, "
                + "    fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
                + "    fuu.UNIT_ID FU_UNIT_ID, fuu.UNIT_CODE FU_UNIT_CODE, fuu.LABEL_ID `FU_UNIT_LABEL_ID`, fuu.LABEL_EN `FU_UNIT_LABEL_EN`, fuu.LABEL_FR `FU_UNIT_LABEL_FR`, fuu.LABEL_PR `FU_UNIT_LABEL_PR`, fuu.LABEL_SP `FU_UNIT_LABEL_SP`, "
                + "    fugl.LABEL_ID `GENERIC_LABEL_ID`, fugl.LABEL_EN `GENERIC_LABEL_EN`, fugl.LABEL_FR `GENERIC_LABEL_FR`, fugl.LABEL_PR `GENERIC_LABEL_PR`, fugl.LABEL_SP `GENERIC_LABEL_SP`, "
                + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`, "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
                + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, "
                + "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_PR `UNIT_LABEL_PR`, u.LABEL_SP `UNIT_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pu.ACTIVE, pu.CREATED_DATE, pu.LAST_MODIFIED_DATE  "
                + " FROM vw_planning_unit pu "
                + " LEFT JOIN vw_forecasting_unit fu on pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + " LEFT JOIN ap_label fugl ON fu.GENERIC_LABEL_ID=fugl.LABEL_ID  "
                + " LEFT JOIN vw_realm r ON fu.REALM_ID=r.REALM_ID  "
                + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + " LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID  "
                + " LEFT JOIN vw_unit u ON pu.UNIT_ID=u.UNIT_ID "
                + " LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID "
                + " LEFT JOIN us_user cb ON pu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON pu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE pu.PLANNING_UNIT_ID IN ( "
                + "     SELECT ppu.PLANNING_UNIT_ID FROM vw_program p LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "     WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") ");
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" UNION ")
                .append("     SELECT dpu.PLANNING_UNIT_ID "
                        + "      FROM vw_dataset p "
                        + "      LEFT JOIN rm_dataset_planning_unit dpu ON p.PROGRAM_ID=dpu.PROGRAM_ID"
                        + "      LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                        + "      WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") ");
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(")");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListForProductCategory(String productCategorySortOrder, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        sqlStringBuilder
                .append(" AND r.REALM_ID=:realmId")
                .append(" AND pc.SORT_ORDER LIKE CONCAT(:productCategorySortOrder, '%')");
        params.put("realmId", curUser.getRealm().getRealmId());
        params.put("productCategorySortOrder", productCategorySortOrder);
        if (active) {
            sqlStringBuilder.append(" AND pu.ACTIVE=:active ");
            params.put("active", active);
        }
        sqlStringBuilder.append(" ORDER BY pu.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForProductCategoryList(String[] productCategoryIds, boolean active, CustomUserDetails curUser) {
        StringBuilder subBuilder = new StringBuilder("SELECT DISTINCT(pc2.PRODUCT_CATEGORY_ID) `PRODUCT_CATEGORY_ID` FROM rm_product_category pc LEFT JOIN rm_product_category pc2 ON pc2.SORT_ORDER LIKE CONCAT(pc.SORT_ORDER,\"%\") WHERE FIND_IN_SET(pc.PRODUCT_CATEGORY_ID, :productCategoryList) ");
        Map<String, Object> params = new HashMap<>();
        params.put("productCategoryList", String.join(",", productCategoryIds));
        this.aclService.addUserAclForRealm(subBuilder, params, "pc", curUser);
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT GROUP_CONCAT(pc3.`PRODUCT_CATEGORY_ID`) `allProductCategories` FROM (").append(subBuilder).append(") AS pc3");
        String finalProductCategoryIds = this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, String.class);
        params.clear();
        sqlStringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID` , pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR "
                + "FROM rm_program_planning_unit ppu "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                + "WHERE "
                + "    FIND_IN_SET(fu.PRODUCT_CATEGORY_ID,:finalProductCategoryIds) "
                + "    AND pu.ACTIVE "
                + "    AND fu.ACTIVE "
                + "    AND ppu.ACTIVE "
                + "    AND p.ACTIVE ");
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY pu.PLANNING_UNIT_ID ORDER BY pu.LABEL_EN");
        params.put("finalProductCategoryIds", finalProductCategoryIds);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitListByRealmCountryId(int realmCountryId, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.`LABEL_ID`, pu.`LABEL_EN`, pu.`LABEL_FR`, pu.`LABEL_SP`, pu.`LABEL_PR` "
                + "FROM rm_program_planning_unit ppu "
                + "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "WHERE "
                + "    rc.ACTIVE AND p.ACTIVE AND ppu.ACTIVE AND (rc.REALM_COUNTRY_ID=:realmCountryId OR :realmCountryId=-1)");
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", realmCountryId);
        this.aclService.addUserAclForRealm(sb, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitByProgramAndTracerCategory(MultipleProgramAndTracerCategoryDTO programAndTracerCategory, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.`LABEL_ID`, pu.`LABEL_EN`, pu.`LABEL_FR`, pu.`LABEL_SP`, pu.`LABEL_PR` "
                + "FROM rm_program_planning_unit ppu "
                + "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "WHERE "
                + "    r.ACTIVE AND rc.ACTIVE AND p.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE "
                + "AND (LENGTH(:programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, :programIds)) "
                + "AND (LENGTH(:tracerCategoryIds)=0 OR FIND_IN_SET(fu.TRACER_CATEGORY_ID, :tracerCategoryIds)) ");
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", programAndTracerCategory.getProgramIdsString());
        params.put("tracerCategoryIds", programAndTracerCategory.getTracerCategoryIdsString());
        this.aclService.addUserAclForRealm(sb, params, "r", curUser);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" GROUP BY pu.PLANNING_UNIT_ID ");
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT pu.PLANNING_UNIT_ID as ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM FROM vw_planning_unit pu LEFT JOIN rm_forecasting_unit fu ON fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID WHERE fu.TRACER_CATEGORY_ID=:tracerCategoryId AND ((:active=TRUE AND pu.ACTIVE) OR (:active=FALSE))");
        Map<String, Object> params = new HashMap<>();
        params.put("tracerCategoryId", tracerCategoryId);
        params.put("active", active);
        this.aclService.addUserAclForRealm(sb, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<PlanningUnit> getPlanningUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder(sqlListString).append(" AND FIND_IN_SET(fu.TRACER_CATEGORY_ID, :tracerCategoryIds) AND ((:active=TRUE AND pu.ACTIVE) OR (:active=FALSE))");
        Map<String, Object> params = new HashMap<>();
        if (tracerCategoryIds != null) {
            params.put("tracerCategoryIds", String.join(",", tracerCategoryIds));
            params.put("active", active);
            this.aclService.addUserAclForRealm(sb, params, "fu", curUser);
            return this.namedParameterJdbcTemplate.query(sb.toString(), params, new PlanningUnitRowMapper());
        } else {
            return null;
        }

    }

    @Override
    public List<SimplePlanningUnitWithPrices> getPlanningUnitListWithPricesForProductCategory(int productCategoryId, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, "
                + "    puu.UNIT_ID PUU_UNIT_ID, puu.LABEL_ID `PUU_LABEL_ID`, puu.LABEL_EN `PUU_LABEL_EN`, puu.LABEL_FR `PUU_LABEL_FR`, puu.LABEL_SP `PUU_LABEL_SP`, puu.LABEL_PR `PUU_LABEL_PR`, puu.UNIT_CODE PUU_CODE, "
                + "    pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PA_LABEL_ID`, pa.LABEL_EN `PA_LABEL_EN`, pa.LABEL_FR `PA_LABEL_FR`, pa.LABEL_SP `PA_LABEL_SP`, pa.LABEL_PR `PA_LABEL_PR`, "
                + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "    fu.UNIT_ID FUU_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`, "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, "
                + "    fuu.UNIT_ID FUU_UNIT_ID, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, fuu.UNIT_CODE FUU_CODE, "
                + "    pu.MULTIPLIER, papu.CATALOG_PRICE "
                + "FROM vw_planning_unit pu "
                + "LEFT JOIN vw_unit puu on pu.UNIT_ID=puu.UNIT_ID "
                + "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "LEFT JOIN vw_unit fuu on fu.UNIT_ID=fuu.UNIT_ID "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON pu.PLANNING_UNIT_ID=papu.PLANNING_UNIT_ID " // AND ppa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID "
                + "LEFT JOIN vw_procurement_agent pa ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
                + "WHERE "
                + "    pu.ACTIVE"
                + "    AND (fu.PRODUCT_CATEGORY_ID=:productCategoryId OR :productCategoryId=-1) "
        //                + "    AND fu.TRACER_CATEGORY_ID IN "
        //                + "         (SELECT tc.TRACER_CATEGORY_ID FROM vw_all_program p LEFT JOIN rm_tracer_category tc on FIND_IN_SET(tc.HEALTH_AREA_ID,p.HEALTH_AREA_ID) WHERE p.PROGRAM_ID=:programId)")
        );
        Map<String, Object> params = new HashMap<>();
//        params.put("programId", programId);
        params.put("productCategoryId", productCategoryId);
        this.aclService.addUserAclForRealm(sb, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new SimplePlanningUnitWithPricesListResultSetExtractor());
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForAutoComplete(AutoCompleteInput autoCompleteInput, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM vw_planning_unit pu LEFT JOIN rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID WHERE pu.ACTIVE AND (pu.LABEL_").append(autoCompleteInput.getLanguage()).append(" LIKE CONCAT('%',:searchText,'%') OR pu.PLANNING_UNIT_ID LIKE CONCAT('%',:searchText,'%')) ");
        Map<String, Object> params = new HashMap<>();
        params.put("searchText", autoCompleteInput.getSearchText());
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        stringBuilder.append(" ORDER BY pu.LABEL_").append(autoCompleteInput.getLanguage());
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForAutoCompleteFilterForProductCategory(AutocompleteInputWithProductCategoryDTO autoCompleteInput, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM vw_planning_unit pu LEFT JOIN rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID WHERE pu.ACTIVE AND (pu.LABEL_").append(autoCompleteInput.getLanguage()).append(" LIKE CONCAT('%',:searchText,'%') OR pu.PLANNING_UNIT_ID LIKE CONCAT('%',:searchText,'%')) ");
        Map<String, Object> params = new HashMap<>();
        params.put("searchText", autoCompleteInput.getSearchText());
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        if (autoCompleteInput.getProductCategorySortOrder() != null) {
            stringBuilder.append(" AND pc.SORT_ORDER LIKE CONCAT(:productCategorySortOrder, '%') ");
            params.put("productCategorySortOrder", autoCompleteInput.getProductCategorySortOrder());
        }
        stringBuilder.append(" ORDER BY pu.LABEL_").append(autoCompleteInput.getLanguage());
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitDropDownList(CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM vw_planning_unit pu LEFT JOIN rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID WHERE pu.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        stringBuilder.append(" ORDER BY pu.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitDropDownListFilterProductCategory(String productCategorySortOrder, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM vw_planning_unit pu LEFT JOIN rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID WHERE pu.ACTIVE AND fu.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        if (productCategorySortOrder != null) {
            stringBuilder.append(" AND pc.SORT_ORDER LIKE CONCAT(:productCategorySortOrder, '%') ");
            params.put("productCategorySortOrder", productCategorySortOrder);
        }
        this.aclService.addUserAclForRealm(stringBuilder, params, "fu", curUser);
        stringBuilder.append(" ORDER BY pu.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitForDatasetByProgramAndVersion(ProgramAndVersionDTO input, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM rm_dataset_planning_unit dpu LEFT JOIN vw_dataset p ON dpu.PROGRAM_ID=p.PROGRAM_ID LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID WHERE dpu.PROGRAM_ID=:programId aND dpu.VERSION_ID=:versionId AND dpu.ACTIVE and pu.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", input.getProgramId());
        params.put("versionId", input.getVersionId());
        this.aclService.addUserAclForRealm(stringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        stringBuilder.append(" ORDER BY pu.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

}
