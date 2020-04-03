/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProcurementAgentDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.PlanningUnitForProcurementAgentMapping;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.rowMapper.PlanningUnitForProcurementAgentResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProcurementAgentRowMapper;
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

    private String sqlListString = " SELECT pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.SUBMITTED_TO_APPROVED_LEAD_TIME, "
            + "r.REALM_ID, r.REALM_CODE, "
            + "pal.`LABEL_ID` ,pal.`LABEL_EN`, pal.`LABEL_FR`, pal.`LABEL_PR`, pal.`LABEL_SP`,"
            + "rl.`LABEL_ID` `REALM_LABEL_ID` ,rl.`LABEL_EN` `REALM_LABEL_EN`, rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`,"
            + "cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pa.ACTIVE, pa.CREATED_DATE, pa.LAST_MODIFIED_DATE "
            + "FROM rm_procurement_agent pa "
            + " LEFT JOIN ap_label pal ON pa.`LABEL_ID`=pal.`LABEL_ID` "
            + " LEFT JOIN rm_realm r ON pa.REALM_ID=r.REALM_ID "
            + " LEFT JOIN ap_label rl ON r.`LABEL_ID`=rl.`LABEL_ID` "
            + " LEFT JOIN us_user cb ON pa.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON pa.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE ";

    @Override
    @Transactional
    public int addProcurementAgent(ProcurementAgent p, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_procurement_agent").usingGeneratedKeyColumns("PROCUREMENT_AGENT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("PROCUREMENT_AGENT_CODE", p.getProcurementAgentCode());
        params.put("REALM_ID", p.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(p.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
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
        params.put("submittedToApprovedLeadTime", p.getSubmittedToApprovedLeadTime());
        params.put("active", p.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update("UPDATE rm_procurement_agent pa LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID SET "
                + "pa.PROCUREMENT_AGENT_CODE=:procurementAgentCode, "
                + "pa.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
                + "pa.ACTIVE=:active, "
                + "pa.LAST_MODIFIED_BY=IF("
                + "     pa.PROCUREMENT_AGENT_CODE!=:procurementAgentCode OR "
                + "     pa.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime OR "
                + "     pa.ACTIVE=:active, :curUser, pa.LAST_MODIFIED_BY), "
                + "pa.LAST_MODIFIED_DATE=IF("
                + "     pa.PROCUREMENT_AGENT_CODE!=:procurementAgentCode OR "
                + "     pa.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime OR "
                + "     pa.ACTIVE=:active, :curDate, pa.LAST_MODIFIED_DATE), "
                + "pal.LABEL_EN=:labelEn, "
                + "pal.LAST_MODIFIED_BY=IF(pal.LABEL_EN!=:labelEn, :curUser, pal.LAST_MODIFIED_BY), "
                + "pal.LAST_MODIFIED_DATE=IF(pal.LABEL_EN!=:labelEn, :curDate, pal.LAST_MODIFIED_DATE) "
                + "WHERE pa.PROCUREMENT_AGENT_ID=:procurementAgentId", params);
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentList(boolean active, CustomUserDetails curUser) {
        String sql = this.sqlListString;
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            params.put("realmId", curUser.getRealm().getRealmId());
            sql += " AND pa.REALM_ID=:realmId";
        }
        return this.namedParameterJdbcTemplate.query(sql, params, new ProcurementAgentRowMapper());
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentByRealm(int realmId, CustomUserDetails curUser) {
        String sql = this.sqlListString + " AND pa.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        return this.namedParameterJdbcTemplate.query(sql, params, new ProcurementAgentRowMapper());
    }

    @Override
    public ProcurementAgent getProcurementAgentById(int procurementAgentId, CustomUserDetails curUser) {
        String sql = this.sqlListString + " AND pa.PROCUREMENT_AGENT_ID=:procurementAgentId ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            params.put("realmId", curUser.getRealm().getRealmId());
            sql += " AND pa.REALM_ID=:realmId";
        }
        params.put("procurementAgentId", procurementAgentId);
        return this.namedParameterJdbcTemplate.queryForObject(sql, params, new ProcurementAgentRowMapper());
    }

    @Override
    public ProcurementAgentPlanningUnit getProcurementAgentPlanningUnitList(int procurementAgentId, CustomUserDetails curUser) {
        String sql = "SELECT pa.PROCUREMENT_AGENT_ID, pal.LABEL_ID, pal.LABEL_EN, pal.LABEL_FR, pal.LABEL_PR, pal.LABEL_SP, "
                + " pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
                + " papu.CATALOG_PRICE, papu.MOQ, papu.UNITS_PER_CONTAINER, papu.UNITS_PER_PALLET, papu.SKU_CODE, papu.VOLUME, papu.WEIGHT, "
                + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, papu.ACTIVE, papu.CREATED_DATE, papu.LAST_MODIFIED_DATE  "
                + " FROM rm_procurement_agent pa  "
                + " LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                + " LEFT JOIN rm_procurement_agent_planning_unit papu ON pa.PROCUREMENT_AGENT_ID=papu.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN rm_planning_unit pu on papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " LEFT JOIN ap_label pul on pu.LABEL_ID=pul.LABEL_ID "
                + " LEFT JOIN us_user cb ON papu.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON papu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE pa.PROCUREMENT_AGENT_ID=:procurementAgentId ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            params.put("realmId", curUser.getRealm().getRealmId());
            sql += " AND pa.REALM_ID=:realmId";
        }
        params.put("procurementAgentId", procurementAgentId);
        return this.namedParameterJdbcTemplate.query(sql, params, new PlanningUnitForProcurementAgentResultSetExtractor());
    }

    @Override
    public int saveProcurementAgentPlanningUnit(ProcurementAgentPlanningUnit papu, CustomUserDetails curUser) {
        String sqlString = "DELETE papu.* FROM rm_procurement_agent_planning_unit papu WHERE papu.PROCUREMENT_AGENT_ID=:programId";
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentId", papu.getProcurementAgentId());
        this.namedParameterJdbcTemplate.update(sqlString, params);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_procurement_agent_planning_unit");
        Map<String, Object>[] paramArray = new HashMap[papu.getPlanningUnits().length];
        int x = 0;
        for (PlanningUnitForProcurementAgentMapping sp : papu.getPlanningUnits()) {
            params = new HashMap<>();
            params.put("PROCUREMENT_AGENT_ID", papu.getProcurementAgentId());
            params.put("PLANNING_UNIT_ID", sp.getPlanningUnitId());
            params.put("MOQ", sp.getMoq());
            params.put("SKU_CODE", sp.getSkuCode());
            params.put("UNITS_PER_PALLET", sp.getUnitsPerPallet());
            params.put("UNITS_PER_CONTAINER", sp.getUnitsPerContainer());
            params.put("VOLUME", sp.getVolume());
            params.put("WEIGHT", sp.getWeight());
            params.put("ACTIVE", true);
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            paramArray[x] = params;
            x++;
        }
        return si.executeBatch(paramArray).length;
    }

    @Override
    public List<ProcurementAgent> getProcurementAgentListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sql = this.sqlListString + " AND pa.LAST_MODIFIED_DATE>:lastSyncDate ";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        if (curUser.getRealm().getRealmId() != -1) {
            params.put("realmId", curUser.getRealm().getRealmId());
            sql += " AND pa.REALM_ID=:realmId";
        }
        return this.namedParameterJdbcTemplate.query(sql, params, new ProcurementAgentRowMapper());
    }

}
