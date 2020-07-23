/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProcurementAgentDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import cc.altius.FASP.model.rowMapper.ProcurementAgentPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentProcurementUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProcurementAgentRowMapper;
import cc.altius.FASP.service.AclService;
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

    private String sqlListString = " SELECT pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.COLOR_HTML_CODE, pa.SUBMITTED_TO_APPROVED_LEAD_TIME, pa.APPROVED_TO_SHIPPED_LEAD_TIME, pa.LOCAL_PROCUREMENT_AGENT, "
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
        int labelId = this.labelDao.addLabel(p.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
        params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
        params.put("LOCAL_PROCUREMENT_AGENT", p.isLocalProcurementAgent());
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
                + " pa.LAST_MODIFIED_BY=IF("
                + "     pa.PROCUREMENT_AGENT_CODE!=:procurementAgentCode OR "
                + "     pa.COLOR_HTML_CODE!=:colorHtmlCode OR "
                + "     pa.SUBMITTED_TO_APPROVED_LEAD_TIME!=:submittedToApprovedLeadTime OR "
                + "     pa.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR "
                + "     pa.ACTIVE!=:active, :curUser, pa.LAST_MODIFIED_BY), "
                + " pa.LAST_MODIFIED_DATE=IF("
                + "     pa.PROCUREMENT_AGENT_CODE!=:procurementAgentCode OR "
                + "     pa.COLOR_HTML_CODE!=:colorHtmlCode OR "
                + "     pa.SUBMITTED_TO_APPROVED_LEAD_TIME!=:submittedToApprovedLeadTime OR "
                + "     pa.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR "
                + "     pa.ACTIVE!=:active, :curDate, pa.LAST_MODIFIED_DATE), "
                + " pal.LABEL_EN=:labelEn, "
                + " pal.LAST_MODIFIED_BY=IF(pal.LABEL_EN!=:labelEn, :curUser, pal.LAST_MODIFIED_BY), "
                + " pal.LAST_MODIFIED_DATE=IF(pal.LABEL_EN!=:labelEn, :curDate, pal.LAST_MODIFIED_DATE) "
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
    public int saveProcurementAgentPlanningUnit(ProcurementAgentPlanningUnit[] procurementAgentPlanningUnits, CustomUserDetails curUser) {
        System.out.println("procurementAgentPlanningUnits---"+Arrays.toString(procurementAgentPlanningUnits));
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_procurement_agent_planning_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (ProcurementAgentPlanningUnit papu : procurementAgentPlanningUnits) {
            if (papu.getProcurementAgentPlanningUnitId() == 0) {
                System.out.println("inside if-----------------");
                // Insert
                params = new HashMap<>();
                params.put("PLANNING_UNIT_ID", papu.getPlanningUnit().getId());
                params.put("PROCUREMENT_AGENT_ID", papu.getProcurementAgent().getId());
                params.put("MOQ", papu.getMoq());
                params.put("SKU_CODE", papu.getSkuCode());
                params.put("CATALOG_PRICE", papu.getCatalogPrice());
                System.out.println("papu.getUnitsPerPalletEuro1()");
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
                System.out.println("inside else-----------------");
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
            System.out.println("inside insert-----------------");
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        if (updateList.size() > 0) {
            System.out.println("inside update-----------------");
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
                    + " papu.ACTIVE=:active, "
                    + "papu.LAST_MODIFIED_DATE=IF(papu.ACTIVE!=:active OR papu.MOQ!=:moq OR papu.SKU_CODE!=:skuCode OR papu.UNITS_PER_CONTAINER!=:unitsPerContainer OR papu.UNITS_PER_PALLET_EURO1!=:unitsPerPalletEuro1 OR papu.UNITS_PER_PALLET_EURO2!=:unitsPerPalletEuro2 OR papu.VOLUME!=:volume OR papu.WEIGHT!=:weight, :curDate, papu.LAST_MODIFIED_DATE), "
                    + "papu.LAST_MODIFIED_BY=IF(papu.ACTIVE!=:active OR papu.MOQ!=:moq OR papu.SKU_CODE!=:skuCode OR papu.UNITS_PER_CONTAINER!=:unitsPerContainer OR papu.UNITS_PER_PALLET_EURO1!=:unitsPerPalletEuro1 OR papu.UNITS_PER_PALLET_EURO2!=:unitsPerPalletEuro2 OR papu.VOLUME!=:volume OR papu.WEIGHT!=:weight, :curUser, papu.LAST_MODIFIED_BY) "
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
                    + "papu.LAST_MODIFIED_DATE=IF(papu.ACTIVE!=:active OR papu.SKU_CODE!=:skuCode OR papu.VENDOR_PRICE!=:vendorPrice OR papu.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR papu.GTIN!=:gtin, :curDate, papu.LAST_MODIFIED_DATE), "
                    + "papu.LAST_MODIFIED_BY=IF(papu.ACTIVE!=:active OR papu.SKU_CODE!=:skuCode OR papu.VENDOR_PRICE!=:vendorPrice OR papu.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR papu.GTIN!=:gtin, :curUser, papu.LAST_MODIFIED_BY) "
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
}
