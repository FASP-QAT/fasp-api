/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProcurementAgentDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ProcurementAgentForecastingUnit;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import cc.altius.FASP.model.ProcurementAgentType;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.PlanningUnitTracerCategoryRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentForecastingUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProcurementAgentPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentProcurementUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProcurementAgentTypeRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.FASP.utils.LogUtils;
import cc.altius.FASP.utils.SuggestedDisplayName;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
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
 * @author altius
 */
@Repository
public class ProcurementAgentDaoImpl implements ProcurementAgentDao {

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

    private String procurementAgentSqlString = " SELECT pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.COLOR_HTML_CODE, pa.SUBMITTED_TO_APPROVED_LEAD_TIME, pa.APPROVED_TO_SHIPPED_LEAD_TIME, "
            + " r.REALM_ID, r.REALM_CODE, r.`LABEL_ID` `REALM_LABEL_ID` ,r.`LABEL_EN` `REALM_LABEL_EN`, r.`LABEL_FR` `REALM_LABEL_FR`, r.`LABEL_PR` `REALM_LABEL_PR`, r.`LABEL_SP` `REALM_LABEL_SP`,"
            + " pa.`LABEL_ID` ,pa.`LABEL_EN`, pa.`LABEL_FR`, pa.`LABEL_PR`, pa.`LABEL_SP`,"
            + " pat.PROCUREMENT_AGENT_TYPE_ID, pat.PROCUREMENT_AGENT_TYPE_CODE, pat.`LABEL_ID` `PAT_LABEL_ID`, pat.`LABEL_EN` `PAT_LABEL_EN`, pat.`LABEL_FR` `PAT_LABEL_FR`, pat.`LABEL_PR` `PAT_LABEL_PR`, pat.`LABEL_SP` `PAT_LABEL_SP`, "
            + " p.PROGRAM_ID `P_ID`, p.PROGRAM_CODE `P_CODE`, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, "
            + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pa.ACTIVE, pa.CREATED_DATE, pa.LAST_MODIFIED_DATE "
            + " FROM vw_procurement_agent pa "
            + "  LEFT JOIN vw_realm r ON pa.REALM_ID=r.REALM_ID "
            + "  LEFT JOIN us_user cb ON pa.CREATED_BY=cb.USER_ID "
            + "  LEFT JOIN us_user lmb ON pa.LAST_MODIFIED_BY=lmb.USER_ID "
            + "  LEFT JOIN vw_procurement_agent_type pat ON pa.PROCUREMENT_AGENT_TYPE_ID=pat.PROCUREMENT_AGENT_TYPE_ID "
            + "  LEFT JOIN rm_program_procurement_agent ppa ON pa.PROCUREMENT_AGENT_ID=ppa.PROCUREMENT_AGENT_ID "
            + "  LEFT JOIN vw_program p ON ppa.PROGRAM_ID=p.PROGRAM_ID "
            + "  WHERE TRUE ";

    private String procurementAgentTypeSqlString = " SELECT pa.PROCUREMENT_AGENT_TYPE_ID, pa.PROCUREMENT_AGENT_TYPE_CODE, "
            + " r.REALM_ID, r.REALM_CODE, r.`LABEL_ID` `REALM_LABEL_ID` ,r.`LABEL_EN` `REALM_LABEL_EN`, r.`LABEL_FR` `REALM_LABEL_FR`, r.`LABEL_PR` `REALM_LABEL_PR`, r.`LABEL_SP` `REALM_LABEL_SP`,"
            + " pa.`LABEL_ID` ,pa.`LABEL_EN`, pa.`LABEL_FR`, pa.`LABEL_PR`, pa.`LABEL_SP`,"
            + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pa.ACTIVE, pa.CREATED_DATE, pa.LAST_MODIFIED_DATE "
            + " FROM vw_procurement_agent_type pa "
            + "  LEFT JOIN vw_realm r ON pa.REALM_ID=r.REALM_ID "
            + "  LEFT JOIN us_user cb ON pa.CREATED_BY=cb.USER_ID "
            + "  LEFT JOIN us_user lmb ON pa.LAST_MODIFIED_BY=lmb.USER_ID "
            + "  WHERE TRUE ";

    @Override
    @Transactional
    public int addProcurementAgent(ProcurementAgent p, CustomUserDetails curUser) {
//        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_procurement_agent").usingGeneratedKeyColumns("PROCUREMENT_AGENT_ID");
        SimpleJdbcInsert si;
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_procurement_agent")
                .usingColumns("PROCUREMENT_AGENT_CODE", "COLOR_HTML_CODE", "REALM_ID", "LABEL_ID", "PROCUREMENT_AGENT_TYPE_ID", "SUBMITTED_TO_APPROVED_LEAD_TIME",
                        "APPROVED_TO_SHIPPED_LEAD_TIME", "ACTIVE", "CREATED_BY", "CREATED_DATE", "LAST_MODIFIED_BY", "LAST_MODIFIED_DATE")
                .usingGeneratedKeyColumns("PROCUREMENT_AGENT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("PROCUREMENT_AGENT_CODE", p.getProcurementAgentCode());
        params.put("COLOR_HTML_CODE", p.getColorHtmlCode());
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(p.getLabel(), LabelConstants.RM_PROCUREMENT_AGENT, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("PROCUREMENT_AGENT_TYPE_ID", p.getProcurementAgentType().getId());
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
        params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int procurementAgentId = si.executeAndReturnKey(params).intValue();
        MapSqlParameterSource[] batchParams;
        batchParams = new MapSqlParameterSource[p.getProgramList().size()];
        si = null;
        int x = 0;
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_procurement_agent");
        for (SimpleObject program : p.getProgramList()) {
            params = new HashMap<>();
            params.put("PROGRAM_ID", program.getId());
            params.put("PROCUREMENT_AGENT_ID", procurementAgentId);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }
        si.executeBatch(batchParams);
        return procurementAgentId;
    }

    @Override
    @Transactional
    public int addProcurementAgentType(ProcurementAgentType p, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_procurement_agent_type").usingGeneratedKeyColumns("PROCUREMENT_AGENT_TYPE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("PROCUREMENT_AGENT_TYPE_CODE", p.getProcurementAgentTypeCode());
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(p.getLabel(), LabelConstants.RM_PROCUREMENT_AGENT_TYPE, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    @Transactional
    public int updateProcurementAgent(ProcurementAgent p, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentId", p.getProcurementAgentId());
        params.put("labelEn", p.getLabel().getLabel_en());
        params.put("procurementAgentCode", p.getProcurementAgentCode());
        params.put("procurementAgentTypeId", p.getProcurementAgentType().getId());
        params.put("colorHtmlCode", p.getColorHtmlCode());
        params.put("submittedToApprovedLeadTime", p.getSubmittedToApprovedLeadTime());
        params.put("approvedToShippedLeadTime", p.getApprovedToShippedLeadTime());
        params.put("active", p.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        int linesUpdated = this.namedParameterJdbcTemplate.update("UPDATE rm_procurement_agent pa LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID SET "
                + " pa.PROCUREMENT_AGENT_CODE=:procurementAgentCode, "
                + " pa.PROCUREMENT_AGENT_TYPE_ID=:procurementAgentTypeId, "
                + " pa.COLOR_HTML_CODE=:colorHtmlCode, "
                + " pa.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
                + " pa.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
                + " pa.ACTIVE=:active, "
                + " pa.LAST_MODIFIED_BY=:curUser, "
                + " pa.LAST_MODIFIED_DATE=:curDate, "
                + " pal.LABEL_EN=:labelEn, "
                + " pal.LAST_MODIFIED_BY=:curUser, "
                + " pal.LAST_MODIFIED_DATE=:curDate "
                + " WHERE pa.PROCUREMENT_AGENT_ID=:procurementAgentId", params);
        this.namedParameterJdbcTemplate.update("DELETE ppa.* FROM rm_program_procurement_agent ppa WHERE ppa.PROCUREMENT_AGENT_ID=:procurementAgentId", params);
        MapSqlParameterSource[] batchParams;
        batchParams = new MapSqlParameterSource[p.getProgramList().size()];
        int x = 0;
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_procurement_agent");
        for (SimpleObject program : p.getProgramList()) {
            params = new HashMap<>();
            params.put("PROGRAM_ID", program.getId());
            params.put("PROCUREMENT_AGENT_ID", p.getProcurementAgentId());
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }
        si.executeBatch(batchParams);
        return linesUpdated;
    }

    @Override
    @Transactional
    public int updateProcurementAgentType(ProcurementAgentType p, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentTypeId", p.getProcurementAgentTypeId());
        params.put("labelEn", p.getLabel().getLabel_en());
        params.put("procurementAgentTypeCode", p.getProcurementAgentTypeCode());
        params.put("active", p.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update("UPDATE rm_procurement_agent_type pa "
                + "LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID SET "
                + " pa.PROCUREMENT_AGENT_TYPE_CODE=:procurementAgentTypeCode, "
                + " pa.ACTIVE=:active, "
                + " pa.LAST_MODIFIED_BY=:curUser, "
                + " pa.LAST_MODIFIED_DATE=:curDate, "
                + " pal.LABEL_EN=:labelEn, "
                + " pal.LAST_MODIFIED_BY=:curUser, "
                + " pal.LAST_MODIFIED_DATE=:curDate "
                + " WHERE pa.PROCUREMENT_AGENT_TYPE_ID=:procurementAgentTypeId", params);
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentSqlString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentListResultSetExtractor());
    }

    @Override
    public List<SimpleCodeObject> getProcurementAgentDropdownList(CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pa.PROCUREMENT_AGENT_ID `ID`, pa.LABEL_ID, pa.LABEL_EN, pa.LABEL_FR, pa.LABEL_SP, pa.LABEL_PR, pa.PROCUREMENT_AGENT_CODE `CODE` FROM vw_procurement_agent pa WHERE pa.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(stringBuilder, params, "pa", curUser);
        stringBuilder.append(" ORDER BY pa.PROCUREMENT_AGENT_CODE");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<SimpleCodeObject> getProcurementAgentDropdownListForFilterMultiplePrograms(String programIds, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pa.PROCUREMENT_AGENT_ID `ID`, pa.LABEL_ID, pa.LABEL_EN, pa.LABEL_FR, pa.LABEL_SP, pa.LABEL_PR, pa.PROCUREMENT_AGENT_CODE `CODE` FROM rm_program p LEFT JOIN rm_program_procurement_agent ppa ON p.PROGRAM_ID=ppa.PROGRAM_ID LEFT JOIN vw_procurement_agent pa ON ppa.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID WHERE p.ACTIVE AND pa.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        if (programIds.length() > 0) {
            stringBuilder.append(" AND FIND_IN_SET(ppa.PROGRAM_ID, :programIds) ");
            params.put("programIds", programIds);
        }
        this.aclService.addUserAclForRealm(stringBuilder, params, "pa", curUser);
        stringBuilder.append(" GROUP BY pa.PROCUREMENT_AGENT_ID ORDER BY pa.PROCUREMENT_AGENT_CODE");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<ProcurementAgentType> getProcurementAgentTypeList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentTypeSqlString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentTypeRowMapper());
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentByRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentSqlString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentListResultSetExtractor());
    }

    @Override
    public List<ProcurementAgentType> getProcurementAgentTypeByRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentTypeSqlString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentTypeRowMapper());
    }

    @Override
    public ProcurementAgent getProcurementAgentById(int procurementAgentId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentSqlString).append(" AND pa.PROCUREMENT_AGENT_ID=:procurementAgentId ");
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentId", procurementAgentId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        System.out.println(LogUtils.buildStringForLog(sqlStringBuilder.toString(), params));
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentResultSetExtractor());
    }

    @Override
    public ProcurementAgentType getProcurementAgentTypeById(int procurementAgentTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentTypeSqlString).append(" AND pa.PROCUREMENT_AGENT_TYPE_ID=:procurementAgentTypeId ");
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentTypeId", procurementAgentTypeId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new ProcurementAgentTypeRowMapper());
    }

    @Override
    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitList(int procurementAgentId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PLANNING_UNIT_ID, "
                + " pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pal.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pal.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pal.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pal.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pal.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                + " pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
                + " papu.CATALOG_PRICE, papu.MOQ, papu.UNITS_PER_CONTAINER, papu.UNITS_PER_PALLET_EURO1, papu.UNITS_PER_PALLET_EURO2, papu.SKU_CODE, papu.VOLUME, papu.WEIGHT, "
                + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE  "
                + " FROM rm_procurement_agent_planning_unit papu  "
                + " LEFT JOIN rm_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                + " LEFT JOIN rm_planning_unit pu on papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID "
                + " LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE papu.PROCUREMENT_AGENT_ID=:procurementAgentId ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        if (active) {
            sqlStringBuilder.append(" AND papu.ACTIVE");
        }
        params.put("procurementAgentId", procurementAgentId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentPlanningUnitRowMapper());
    }

    @Override
    public List<ProcurementAgentForecastingUnit> getProcurementAgentForecastingUnitList(int procurementAgentId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT pafu.PROCUREMENT_AGENT_FORECASTING_UNIT_ID, "
                + " pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                + " fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
                + " pafu.SKU_CODE, "
                + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pafu.ACTIVE, pafu.CREATED_DATE, pafu.LAST_MODIFIED_DATE  "
                + " FROM rm_procurement_agent_forecasting_unit pafu  "
                + " LEFT JOIN vw_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=pafu.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN vw_forecasting_unit fu on pafu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + " LEFT JOIN us_user cb ON pafu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON pafu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE pafu.PROCUREMENT_AGENT_ID=:procurementAgentId ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        if (active) {
            sqlStringBuilder.append(" AND pafu.ACTIVE");
        }
        params.put("procurementAgentId", procurementAgentId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentForecastingUnitRowMapper());
    }

    @Override
    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitListForTracerCategory(int procurementAgentId, int planningUnitId, String term, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(" SELECT pu.PLANNING_UNIT_ID, pul.LABEL_ID, pul.LABEL_EN, pul.LABEL_FR, pul.LABEL_PR, pul.LABEL_SP,papu.SKU_CODE "
                + " FROM rm_procurement_agent_planning_unit papu  "
                + " LEFT JOIN rm_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID` "
                + " LEFT JOIN rm_tracer_category tc ON tc.`TRACER_CATEGORY_ID`=fu.`TRACER_CATEGORY_ID` "
                + " LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID "
                + " WHERE papu.PROCUREMENT_AGENT_ID=:procurementAgentId  AND pu.`ACTIVE` AND papu.`ACTIVE` AND tc.`TRACER_CATEGORY_ID`=(SELECT t.`TRACER_CATEGORY_ID` FROM rm_planning_unit p "
                + " LEFT JOIN rm_forecasting_unit f ON f.`FORECASTING_UNIT_ID`=p.`FORECASTING_UNIT_ID` "
                + " LEFT JOIN rm_tracer_category t ON t.`TRACER_CATEGORY_ID`=f.`TRACER_CATEGORY_ID` "
                + " WHERE p.`PLANNING_UNIT_ID`=:planningUnitId) AND (UPPER(pul.`LABEL_EN`) LIKE '%").append(term).append("%' OR UPPER(papu.`SKU_CODE`) LIKE '%").append(term).append("%')");
        Map<String, Object> params = new HashMap<>();
//        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        params.put("procurementAgentId", procurementAgentId);
        params.put("planningUnitId", planningUnitId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new PlanningUnitTracerCategoryRowMapper());
    }

    @Override
    public int saveProcurementAgentPlanningUnit(ProcurementAgentPlanningUnit[] procurementAgentPlanningUnits, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_procurement_agent_planning_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (ProcurementAgentPlanningUnit papu : procurementAgentPlanningUnits) {
            if (papu.getProcurementAgentPlanningUnitId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("PLANNING_UNIT_ID", papu.getPlanningUnit().getId());
                params.put("PROCUREMENT_AGENT_ID", papu.getProcurementAgent().getId());
                params.put("MOQ", papu.getMoq());
                params.put("SKU_CODE", papu.getSkuCode());
                params.put("CATALOG_PRICE", papu.getCatalogPrice());
                params.put("UNITS_PER_PALLET_EURO1", papu.getUnitsPerPalletEuro1());
                params.put("UNITS_PER_PALLET_EURO2", papu.getUnitsPerPalletEuro2());
                params.put("UNITS_PER_CONTAINER", papu.getUnitsPerContainer());
                params.put("VOLUME", papu.getVolume());
                params.put("WEIGHT", papu.getWeight());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                // Update
                params = new HashMap<>();
                params.put("procurementAgentPlanningUnitId", papu.getProcurementAgentPlanningUnitId());
                params.put("planningUnitId", papu.getPlanningUnit().getId());
                params.put("moq", papu.getMoq());
                params.put("skuCode", papu.getSkuCode());
                params.put("catalogPrice", papu.getCatalogPrice());
                params.put("unitsPerContainer", papu.getUnitsPerContainer());
                params.put("unitsPerPalletEuro1", papu.getUnitsPerPalletEuro1());
                params.put("unitsPerPalletEuro2", papu.getUnitsPerPalletEuro2());
                params.put("volume", papu.getVolume());
                params.put("weight", papu.getWeight());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", papu.isActive());
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
                    + "rm_procurement_agent_planning_unit papu "
                    + "SET "
                    + "papu.PLANNING_UNIT_ID=:planningUnitId, "
                    + "papu.MOQ=:moq, "
                    + "papu.SKU_CODE=:skuCode, "
                    + "papu.UNITS_PER_CONTAINER=:unitsPerContainer, "
                    + "papu.UNITS_PER_PALLET_EURO1=:unitsPerPalletEuro1, "
                    + "papu.UNITS_PER_PALLET_EURO2=:unitsPerPalletEuro2, "
                    + "papu.VOLUME=:volume, "
                    + "papu.WEIGHT=:weight, "
                    + "papu.CATALOG_PRICE=:catalogPrice, "
                    + "papu.ACTIVE=:active, "
                    + "papu.LAST_MODIFIED_DATE=:curDate, "
                    + "papu.LAST_MODIFIED_BY=:curUser "
                    + "WHERE papu.PROCUREMENT_AGENT_PLANNING_UNIT_ID=:procurementAgentPlanningUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public int saveProcurementAgentForecastingUnit(ProcurementAgentForecastingUnit[] procurementAgentForecastingUnits, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_procurement_agent_forecasting_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (ProcurementAgentForecastingUnit pafu : procurementAgentForecastingUnits) {
            if (pafu.getProcurementAgentForecastingUnitId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("FORECASTING_UNIT_ID", pafu.getForecastingUnit().getId());
                params.put("PROCUREMENT_AGENT_ID", pafu.getProcurementAgent().getId());
                params.put("SKU_CODE", pafu.getSkuCode());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                // Update
                params = new HashMap<>();
                params.put("procurementAgentForecastingUnitId", pafu.getProcurementAgentForecastingUnitId());
                params.put("forecastingUnitId", pafu.getForecastingUnit().getId());
                params.put("skuCode", pafu.getSkuCode());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", pafu.isActive());
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
                    + "rm_procurement_agent_forecasting_unit pafu "
                    + "SET "
                    + "pafu.FORECASTING_UNIT_ID=:forecastingUnitId, "
                    + "pafu.SKU_CODE=:skuCode, "
                    + "pafu.ACTIVE=:active, "
                    + "pafu.LAST_MODIFIED_DATE=:curDate, "
                    + "pafu.LAST_MODIFIED_BY=:curUser "
                    + "WHERE pafu.PROCUREMENT_AGENT_FORECASTING_UNIT_ID=:procurementAgentForecastingUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitList(int procurementAgentId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID, "
                + "     pa.PROCUREMENT_AGENT_ID, pal.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pal.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pal.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pal.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pal.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                + "     pu.PROCUREMENT_UNIT_ID, pul.LABEL_ID `PROCUREMENT_UNIT_LABEL_ID`, pul.LABEL_EN `PROCUREMENT_UNIT_LABEL_EN`, pul.LABEL_FR `PROCUREMENT_UNIT_LABEL_FR`, pul.LABEL_PR `PROCUREMENT_UNIT_LABEL_PR`, pul.LABEL_SP `PROCUREMENT_UNIT_LABEL_SP`, "
                + "     papu.SKU_CODE, papu.VENDOR_PRICE, papu.APPROVED_TO_SHIPPED_LEAD_TIME, papu.GTIN,"
                + "     cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE  "
                + " FROM rm_procurement_agent_procurement_unit papu  "
                + " LEFT JOIN rm_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                + " LEFT JOIN rm_procurement_unit pu on papu.PROCUREMENT_UNIT_ID=pu.PROCUREMENT_UNIT_ID"
                + " LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID "
                + " LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE papu.PROCUREMENT_AGENT_ID=:procurementAgentId ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        if (active) {
            sqlStringBuilder.append(" AND papu.ACTIVE");
        }
        params.put("procurementAgentId", procurementAgentId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentProcurementUnitRowMapper());
    }

    @Override
    public int saveProcurementAgentProcurementUnit(ProcurementAgentProcurementUnit[] procurementAgentProcurementUnits, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_procurement_agent_procurement_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (ProcurementAgentProcurementUnit papu : procurementAgentProcurementUnits) {
            if (papu.getProcurementAgentProcurementUnitId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("PROCUREMENT_UNIT_ID", papu.getProcurementUnit().getId());
                params.put("PROCUREMENT_AGENT_ID", papu.getProcurementAgent().getId());
                params.put("SKU_CODE", papu.getSkuCode());
                params.put("VENDOR_PRICE", papu.getVendorPrice());
                params.put("APPROVED_TO_SHIPPED_LEAD_TIME", papu.getApprovedToShippedLeadTime());
                params.put("GTIN", papu.getGtin());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                // Update
                params = new HashMap<>();
                params.put("procurementAgentProcurementUnitId", papu.getProcurementAgentProcurementUnitId());
                params.put("skuCode", papu.getSkuCode());
                params.put("vendorPrice", papu.getVendorPrice());
                params.put("approvedToShippedLeadTime", papu.getApprovedToShippedLeadTime());
                params.put("gtin", papu.getGtin());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", papu.isActive());
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
                    + "rm_procurement_agent_procurement_unit papu "
                    + "SET "
                    + "papu.SKU_CODE=:skuCode, "
                    + "papu.VENDOR_PRICE=:vendorPrice, "
                    + "papu.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
                    + "papu.GTIN=:gtin, "
                    + "papu.ACTIVE=:active, "
                    + "papu.LAST_MODIFIED_DATE=:curDate, "
                    + "papu.LAST_MODIFIED_BY=:curUser "
                    + "WHERE papu.PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID=:procurementAgentProcurementUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentSqlString).append(" AND (pa.LAST_MODIFIED_DATE>:lastSyncDate OR EXISTS (SELECT 1 FROM rm_program_procurement_agent ppa WHERE pa.PROCUREMENT_AGENT_ID = ppa.PROCUREMENT_AGENT_ID AND ppa.LAST_MODIFIED_DATE > :lastSyncDate)) ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentListResultSetExtractor());
    }

    @Override
    public List<ProcurementAgentType> getProcurementAgentTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.procurementAgentTypeSqlString).append(" AND pa.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentTypeRowMapper());
    }

    @Override
    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID, "
                + "     pa.PROCUREMENT_AGENT_ID, pal.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pal.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pal.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pal.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pal.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                + "     pu.PROCUREMENT_UNIT_ID, pul.LABEL_ID `PROCUREMENT_UNIT_LABEL_ID`, pul.LABEL_EN `PROCUREMENT_UNIT_LABEL_EN`, pul.LABEL_FR `PROCUREMENT_UNIT_LABEL_FR`, pul.LABEL_PR `PROCUREMENT_UNIT_LABEL_PR`, pul.LABEL_SP `PROCUREMENT_UNIT_LABEL_SP`, "
                + "     papu.SKU_CODE, papu.VENDOR_PRICE, papu.APPROVED_TO_SHIPPED_LEAD_TIME, papu.GTIN,"
                + "     cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE  "
                + " FROM rm_procurement_agent_procurement_unit papu  "
                + " LEFT JOIN rm_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                + " LEFT JOIN rm_procurement_unit pu on papu.PROCUREMENT_UNIT_ID=pu.PROCUREMENT_UNIT_ID"
                + " LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID "
                + " LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE papu.LAST_MODIFIED_DATE>:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentProcurementUnitRowMapper());
    }

    @Override
    public String getDisplayName(int realmId, String name, CustomUserDetails curUser) {
        String extractedName = SuggestedDisplayName.getAlphaNumericString(name, SuggestedDisplayName.PROCUREMENT_AGENT_LENGTH);
        String sqlString = "SELECT COUNT(*) CNT FROM rm_procurement_agent pa WHERE pa.REALM_ID=:realmId AND UPPER(LEFT(pa.PROCUREMENT_AGENT_CODE,:len))=:extractedName";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("len", extractedName.length());
        params.put("extractedName", extractedName);
        int cnt = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        return SuggestedDisplayName.getFinalDisplayName(extractedName, cnt);
    }

    @Override
    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PLANNING_UNIT_ID, "
                + " pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pal.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pal.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pal.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pal.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pal.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                + " pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
                + " papu.CATALOG_PRICE, papu.MOQ, papu.UNITS_PER_CONTAINER, papu.UNITS_PER_PALLET_EURO1, papu.UNITS_PER_PALLET_EURO2, papu.SKU_CODE, papu.VOLUME, papu.WEIGHT, "
                + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE  "
                + " FROM rm_procurement_agent_planning_unit papu  "
                + " LEFT JOIN rm_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                + " LEFT JOIN rm_planning_unit pu on papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID "
                + " LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE papu.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentPlanningUnitRowMapper());
    }

    @Override
    public List<ProcurementAgentForecastingUnit> getProcurementAgentForecastingUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT pafu.PROCUREMENT_AGENT_FORECASTING_UNIT_ID, "
                + " pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                + " fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
                + " pafu.SKU_CODE, "
                + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pafu.ACTIVE, pafu.CREATED_DATE, pafu.LAST_MODIFIED_DATE  "
                + " FROM rm_procurement_agent_forecasting_unit pafu  "
                + " LEFT JOIN vw_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=pafu.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN vw_forecasting_unit pu on pafu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID "
                + " LEFT JOIN us_user cb ON pafu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON pafu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE pafu.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentForecastingUnitRowMapper());
    }

    @Override
    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID,  "
                + "     pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`,  "
                + "     pu.PROCUREMENT_UNIT_ID, pu.LABEL_ID `PROCUREMENT_UNIT_LABEL_ID`, pu.LABEL_EN `PROCUREMENT_UNIT_LABEL_EN`, pu.LABEL_FR `PROCUREMENT_UNIT_LABEL_FR`, pu.LABEL_PR `PROCUREMENT_UNIT_LABEL_PR`, pu.LABEL_SP `PROCUREMENT_UNIT_LABEL_SP`,  "
                + "     papu.SKU_CODE, papu.VENDOR_PRICE, papu.APPROVED_TO_SHIPPED_LEAD_TIME, papu.GTIN, "
                + "     cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE   "
                + " FROM vw_program p "
                + " LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
                + " LEFT JOIN rm_planning_unit plu ON ppu.PLANNING_UNIT_ID=plu.PLANNING_UNIT_ID "
                + " LEFT JOIN vw_procurement_unit pu on plu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " LEFT JOIN rm_procurement_agent_procurement_unit papu ON pu.PROCUREMENT_UNIT_ID=papu.PROCUREMENT_UNIT_ID "
                + " LEFT JOIN vw_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID  "
                + " LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID   "
                + " LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") AND papu.`PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID`  IS NOT NULL  ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY papu.PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentProcurementUnitRowMapper());
    }

    @Override
    public List<ProcurementAgentPlanningUnit> getProcurementAgentPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PLANNING_UNIT_ID,   "
                + "        pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`,   "
                + "        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,   "
                + "        papu.CATALOG_PRICE, papu.MOQ, papu.UNITS_PER_CONTAINER, papu.UNITS_PER_PALLET_EURO1, papu.UNITS_PER_PALLET_EURO2, papu.SKU_CODE, papu.VOLUME, papu.WEIGHT,   "
                + "        cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE "
                + "FROM vw_program p  "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID  "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON pu.PLANNING_UNIT_ID = papu.PLANNING_UNIT_ID  "
                + "LEFT JOIN vw_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID   "
                + "LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID    "
                + "LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID  "
                + "WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") AND papu.`PROCUREMENT_AGENT_PLANNING_UNIT_ID` IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append("GROUP BY papu.PROCUREMENT_AGENT_PLANNING_UNIT_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentPlanningUnitRowMapper());
    }

    @Override
    public List<ProcurementAgentForecastingUnit> getProcurementAgentForecastingUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT pafu.PROCUREMENT_AGENT_FORECASTING_UNIT_ID,   "
                + "        pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`,   "
                + "        fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`,   "
                + "        pafu.SKU_CODE,   "
                + "        cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pafu.ACTIVE, pafu.CREATED_DATE, pafu.LAST_MODIFIED_DATE "
                + "FROM vw_program p  "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID  "
                + "LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
                + "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
                + "LEFT JOIN rm_procurement_agent_forecasting_unit pafu ON fu.FORECASTING_UNIT_ID = pafu.FORECASTING_UNIT_ID  "
                + "LEFT JOIN vw_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=pafu.PROCUREMENT_AGENT_ID   "
                + "LEFT JOIN us_user cb ON pafu.CREATED_BY=cb.USER_ID    "
                + "LEFT JOIN us_user lmb ON pafu.LAST_MODIFIED_BY=lmb.USER_ID  "
                + "WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") AND pafu.`PROCUREMENT_AGENT_FORECASTING_UNIT_ID` IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append("GROUP BY pafu.PROCUREMENT_AGENT_FORECASTING_UNIT_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentForecastingUnitRowMapper());
    }

    @Override
    public Map<Integer, List<ProcurementAgentPlanningUnit>> getProcurementAgentPlanningUnitListByPlanningUnitList(int[] planningUnitIds, CustomUserDetails curUser) {
        Map<Integer, List<ProcurementAgentPlanningUnit>> result = new HashMap<>();
        Arrays.stream(planningUnitIds).forEach(puId -> {
            Map<String, Object> params = new HashMap<>();
            params.put("planningUnitId", puId);
            StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PLANNING_UNIT_ID, "
                    + " pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pal.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pal.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pal.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pal.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pal.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                    + " pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
                    + " papu.CATALOG_PRICE, papu.MOQ, papu.UNITS_PER_CONTAINER, papu.UNITS_PER_PALLET_EURO1, papu.UNITS_PER_PALLET_EURO2, papu.SKU_CODE, papu.VOLUME, papu.WEIGHT, "
                    + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE  "
                    + " FROM rm_procurement_agent_planning_unit papu  "
                    + " LEFT JOIN rm_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID "
                    + " LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                    + " LEFT JOIN rm_planning_unit pu on papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                    + " LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID "
                    + " LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID  "
                    + " LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID "
                    + " WHERE papu.PLANNING_UNIT_ID=:planningUnitId ");
            this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
            sqlStringBuilder.append(" AND papu.ACTIVE");
            result.put(puId, this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentPlanningUnitRowMapper()));
        });
        return result;
    }

    @Override
    public List<SimpleCodeObject> getProgramListByProcurementAgentId(int procurementAgentId, CustomUserDetails curUser) {
        String sql = "SELECT p.PROGRAM_ID ID, p.PROGRAM_CODE CODE, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR FROM rm_program_procurement_agent ppa LEFT JOIN vw_program p ON ppa.PROGRAM_ID=p.PROGRAM_ID WHERE ppa.PROCUREMENT_AGENT_ID=:procurementAgentId";
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentId", procurementAgentId);
        return this.namedParameterJdbcTemplate.query(sql, params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<SimpleCodeObject> getProcurementAgentListByProgramId(int programId, CustomUserDetails curUser) {
        String sql = "SELECT pa.PROCUREMENT_AGENT_ID ID, pa.PROCUREMENT_AGENT_CODE CODE, pa.LABEL_ID, pa.LABEL_EN, pa.LABEL_FR, pa.LABEL_SP, pa.LABEL_PR FROM rm_program_procurement_agent ppa LEFT JOIN vw_procurement_agent pa ON ppa.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID WHERE ppa.PROGRAM_ID=:programId";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        return this.namedParameterJdbcTemplate.query(sql, params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    @Transactional
    public int updateProcurementAgentsForProgram(int programId, Integer[] procurementAgentIds, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        String strProcurementAgentIds = ArrayUtils.convertArrayToString(procurementAgentIds);
        params.put("procurementAgentIds", strProcurementAgentIds);
        params.put("programId", programId);
        params.put("curDate", curDate);
        this.namedParameterJdbcTemplate.update("UPDATE rm_procurement_agent pa SET pa.LAST_MODIFIED_DATE=:curDate WHERE FIND_IN_SET(pa.PROCUREMENT_AGENT_ID, :procurementAgentIds) OR pa.PROCUREMENT_AGENT_ID IN (SELECT ppa.PROCUREMENT_AGENT_ID FROM rm_program_procurement_agent ppa WHERE ppa.PROGRAM_ID=:programId)", params);
        this.namedParameterJdbcTemplate.update("UPDATE rm_program p SET p.LAST_MODIFIED_DATE=:curDate WHERE p.PROGRAM_ID=:programId", params);
        this.namedParameterJdbcTemplate.update("DELETE ppa.* FROM rm_program_procurement_agent ppa WHERE ppa.PROGRAM_ID=:programId", params);
        MapSqlParameterSource[] batchParams;
        batchParams = new MapSqlParameterSource[procurementAgentIds.length];
        int x = 0;
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_procurement_agent");
        for (int procurementAgentId : procurementAgentIds) {
            params = new HashMap<>();
            params.put("PROGRAM_ID", programId);
            params.put("PROCUREMENT_AGENT_ID", procurementAgentId);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }
        int[] resultArray = si.executeBatch(batchParams);
        return IntStream.of(resultArray).sum();
    }
}
