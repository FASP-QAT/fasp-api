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
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import cc.altius.FASP.model.rowMapper.PlanningUnitTracerCategoryRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentProcurementUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.SuggestedDisplayName;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private String sqlListString = " SELECT pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.COLOR_HTML_CODE, pa.SUBMITTED_TO_APPROVED_LEAD_TIME, pa.APPROVED_TO_SHIPPED_LEAD_TIME, "
            + " r.REALM_ID, r.REALM_CODE, "
            + " pal.`LABEL_ID` ,pal.`LABEL_EN`, pal.`LABEL_FR`, pal.`LABEL_PR`, pal.`LABEL_SP`,"
            + " rl.`LABEL_ID` `REALM_LABEL_ID` ,rl.`LABEL_EN` `REALM_LABEL_EN`, rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`,"
            + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pa.ACTIVE, pa.CREATED_DATE, pa.LAST_MODIFIED_DATE "
            + " FROM rm_procurement_agent pa "
            + "  LEFT JOIN ap_label pal ON pa.`LABEL_ID`=pal.`LABEL_ID` "
            + "  LEFT JOIN rm_realm r ON pa.REALM_ID=r.REALM_ID "
            + "  LEFT JOIN ap_label rl ON r.`LABEL_ID`=rl.`LABEL_ID` "
            + "  LEFT JOIN us_user cb ON pa.CREATED_BY=cb.USER_ID "
            + "  LEFT JOIN us_user lmb ON pa.LAST_MODIFIED_BY=lmb.USER_ID "
            + "  WHERE TRUE ";

    @Override
    @Transactional
    public int addProcurementAgent(ProcurementAgent p, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_procurement_agent").usingGeneratedKeyColumns("PROCUREMENT_AGENT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("PROCUREMENT_AGENT_CODE", p.getProcurementAgentCode());
        params.put("COLOR_HTML_CODE", p.getColorHtmlCode());
        params.put("REALM_ID", p.getRealm().getId());
        int labelId = this.labelDao.addLabel(p.getLabel(), LabelConstants.RM_PROCUREMENT_AGENT, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
        params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
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
        params.put("colorHtmlCode", p.getColorHtmlCode());
        params.put("submittedToApprovedLeadTime", p.getSubmittedToApprovedLeadTime());
        params.put("approvedToShippedLeadTime", p.getApprovedToShippedLeadTime());
        params.put("active", p.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update("UPDATE rm_procurement_agent pa LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID SET "
                + " pa.PROCUREMENT_AGENT_CODE=:procurementAgentCode, "
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
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentRowMapper());
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentByRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentRowMapper());
    }

    @Override
    public ProcurementAgent getProcurementAgentById(int procurementAgentId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND pa.PROCUREMENT_AGENT_ID=:procurementAgentId ");
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentId", procurementAgentId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new ProcurementAgentRowMapper());
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
        System.out.println("procurementAgentPlanningUnits----" + Arrays.toString(procurementAgentPlanningUnits));
        for (ProcurementAgentPlanningUnit papu : procurementAgentPlanningUnits) {
            System.out.println("papu------" + papu);
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
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND pa.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentRowMapper());
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
    public List<ProcurementAgentProcurementUnit> getProcurementAgentProcurementUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT papu.PROCUREMENT_AGENT_PROCUREMENT_UNIT_ID,  "
                + "     pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`,  "
                + "     pu.PROCUREMENT_UNIT_ID, pu.LABEL_ID `PROCUREMENT_UNIT_LABEL_ID`, pu.LABEL_EN `PROCUREMENT_UNIT_LABEL_EN`, pu.LABEL_FR `PROCUREMENT_UNIT_LABEL_FR`, pu.LABEL_PR `PROCUREMENT_UNIT_LABEL_PR`, pu.LABEL_SP `PROCUREMENT_UNIT_LABEL_SP`,  "
                + "     papu.SKU_CODE, papu.VENDOR_PRICE, papu.APPROVED_TO_SHIPPED_LEAD_TIME, papu.GTIN, "
                + "     cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE   "
                + " FROM rm_program p "
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
                + "FROM rm_program p  "
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
        sqlStringBuilder.append("GROUP BY papu.PROCUREMENT_AGENT_PLANNING_UNIT_ID, papup.PROGRAM_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementAgentPlanningUnitRowMapper());
    }
}
