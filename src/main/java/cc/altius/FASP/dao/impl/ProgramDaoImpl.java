/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.DTO.rowMapper.ProgramDTORowMapper;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.rowMapper.ProgramListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramResultSetExtractor;
import cc.altius.utils.DateUtils;
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
public class ProgramDaoImpl implements ProgramDao {

    @Autowired
    private LabelDao labelDao;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<ProgramDTO> getProgramList() {
        String sql = "SELECT r.`PROGRAM_ID`,label.`LABEL_ID`,label.`LABEL_EN`,label.`LABEL_FR`,label.`LABEL_PR`,label.`LABEL_SP` "
                + "FROM rm_program r  "
                + "LEFT JOIN ap_label label ON label.`LABEL_ID`=r.`LABEL_ID`;";
        return this.namedParameterJdbcTemplate.query(sql, new ProgramDTORowMapper());
    }

    @Override
    @Transactional
    public int addProgram(Program p, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int labelId = this.labelDao.addLabel(p.getLabel(), curUser.getUserId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program").usingGeneratedKeyColumns("PROGRAM_ID");
        params.put("REALM_COUNTRY_ID", p.getRealmCountry().getRealmCountryId());
        params.put("ORGANISATION_ID", p.getOrganisation().getOrganisationId());
        params.put("HEALTH_AREA_ID", p.getHealthArea().getHealthAreaId());
        params.put("LABEL_ID", labelId);
        params.put("PROGRAM_MANAGER_USER_ID", p.getProgramManager().getUserId());
        params.put("PROGRAM_NOTES", p.getProgramNotes());
        params.put("AIR_FREIGHT_PERC", p.getAirFreightPerc());
        params.put("SEA_FREIGHT_PERC", p.getSeaFreightPerc());
        params.put("PLANNED_TO_DRAFT_LEAD_TIME", p.getPlannedToDraftLeadTime());
        params.put("DRAFT_TO_SUBMITTED_LEAD_TIME", p.getDraftToSubmittedLeadTime());
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
        params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
        params.put("DELIVERED_TO_RECEIVED_LEAD_TIME", p.getDeliveredToReceivedLeadTime());
        params.put("MONTHS_IN_PAST_FOR_AMC", p.getMonthsInPastForAmc());
        params.put("MONTHS_IN_FUTURE_FOR_AMC", p.getMonthsInFutureForAmc());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int programId = si.executeAndReturnKey(params).intValue();
        si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_region");
        SqlParameterSource[] paramList = new SqlParameterSource[p.getRegionArray().length];
        int i = 0;
        for (String rId : p.getRegionArray()) {
            params = new HashMap<>();
            params.put("REGION_ID", rId);
            params.put("PROGRAM_ID", programId);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ACTIVE", true);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return programId;
    }

    @Override
    public int updateProgram(Program p, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("programId", p.getProgramId());
        params.put("labelEn", p.getLabel().getLabel_en());
        params.put("programManagerUserId", p.getProgramManager().getUserId());
        params.put("programNotes", p.getProgramNotes());
        params.put("airFreightPerc", p.getAirFreightPerc());
        params.put("seaFreightPerc", p.getSeaFreightPerc());
        params.put("plannedToDraftLeadTime", p.getPlannedToDraftLeadTime());
        params.put("draftToSubmittedLeadTime", p.getDraftToSubmittedLeadTime());
        params.put("submittedToApprovedLeadTime", p.getSubmittedToApprovedLeadTime());
        params.put("approvedToShippedLeadTime", p.getApprovedToShippedLeadTime());
        params.put("deliveredToReceivedLeadTime", p.getDeliveredToReceivedLeadTime());
        params.put("monthsInPastForAmc", p.getMonthsInPastForAmc());
        params.put("monthsInFutureForAmc", p.getMonthsInFutureForAmc());
        params.put("active", p.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        String sqlString = "UPDATE rm_program p "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "SET "
                + "p.PROGRAM_MANAGER_USER_ID=:programManagerUserId, "
                + "p.PROGRAM_NOTES=:programNotes, "
                + "p.AIR_FREIGHT_PERC=:airFreightPerc, "
                + "p.SEA_FREIGHT_PERC=:seaFreightPerc, "
                + "p.PLANNED_TO_DRAFT_LEAD_TIME=:plannedToDraftLeadTime, "
                + "p.DRAFT_TO_SUBMITTED_LEAD_TIME=:draftToSubmittedLeadTime, "
                + "p.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
                + "p.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
                + "p.DELIVERED_TO_RECEIVED_LEAD_TIME=:deliveredToReceivedLeadTime, "
                + "p.MONTHS_IN_PAST_FOR_AMC=:monthsInPastForAmc, "
                + "p.MONTHS_IN_FUTURE_FOR_AMC=:monthsInFutureForAmc, "
                + "p.ACTIVE=:active,"
                + "p.LAST_MODIFIED_BY=IF("
                + "     p.PROGRAM_MANAGER_USER_ID!=:programManagerUserId OR "
                + "     p.PROGRAM_NOTES!=:programNotes OR "
                + "     p.AIR_FREIGHT_PERC!=:airFreightPerc OR "
                + "     p.SEA_FREIGHT_PERC!=:seaFreightPerc OR "
                + "     p.PLANNED_TO_DRAFT_LEAD_TIME!=:plannedToDraftLeadTime OR "
                + "     p.DRAFT_TO_SUBMITTED_LEAD_TIME!=:draftToSubmittedLeadTime OR "
                + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME!=:submittedToApprovedLeadTime OR "
                + "     p.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR "
                + "     p.DELIVERED_TO_RECEIVED_LEAD_TIME!=:deliveredToReceivedLeadTime OR "
                + "     p.MONTHS_IN_PAST_FOR_AMC!=:monthsInPastForAmc OR "
                + "     p.MONTHS_IN_FUTURE_FOR_AMC!=:monthsInFutureForAmc OR "
                + "     p.ACTIVE!=:active, "
                + ":curUser, p.LAST_MODIFIED_BY), "
                + "p.LAST_MODIFIED_DATE=IF("
                + "     p.PROGRAM_MANAGER_USER_ID!=:programManagerUserId OR "
                + "     p.PROGRAM_NOTES!=:programNotes OR "
                + "     p.AIR_FREIGHT_PERC!=:airFreightPerc OR "
                + "     p.SEA_FREIGHT_PERC!=:seaFreightPerc OR "
                + "     p.PLANNED_TO_DRAFT_LEAD_TIME!=:plannedToDraftLeadTime OR "
                + "     p.DRAFT_TO_SUBMITTED_LEAD_TIME!=:draftToSubmittedLeadTime OR "
                + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME!=:submittedToApprovedLeadTime OR "
                + "     p.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR "
                + "     p.DELIVERED_TO_RECEIVED_LEAD_TIME!=:deliveredToReceivedLeadTime OR "
                + "     p.MONTHS_IN_PAST_FOR_AMC!=:monthsInPastForAmc OR "
                + "     p.MONTHS_IN_FUTURE_FOR_AMC!=:monthsInFutureForAmc OR "
                + "     p.ACTIVE!=:active, "
                + ":curDate, p.LAST_MODIFIED_DATE), "
                + "pl.LABEL_EN=:labelEn, "
                + "pl.LAST_MODIFIED_BY=IF(pl.LABEL_EN!=:labelEn, :curUser, pl.LAST_MODIFIED_BY), "
                + "pl.LAST_MODIFIED_DATE=IF(pl.LABEL_EN!=:labelEn, :curDate, pl.LAST_MODIFIED_DATE) "
                + "WHERE p.PROGRAM_ID=:programId";
        int rows = this.namedParameterJdbcTemplate.update(sqlString, params);
        params.clear();
        params.put("programId", p.getProgramId());
        this.namedParameterJdbcTemplate.update("DELETE FROM rm_program_region WHERE PROGRAM_ID=:programId", params);
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_region");
        SqlParameterSource[] paramList = new SqlParameterSource[p.getRegionArray().length];
        int i = 0;
        for (String regionId : p.getRegionArray()) {
            params = new HashMap<>();
            params.put("PROGRAM_ID", p.getProgramId());
            params.put("REGION_ID", regionId);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ACTIVE", true);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return rows;
    }

    @Override
    public List<Program> getProgramList(CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "	p.PROGRAM_ID, p.AIR_FREIGHT_PERC, p.SEA_FREIGHT_PERC, p.PLANNED_TO_DRAFT_LEAD_TIME, p.DRAFT_TO_SUBMITTED_LEAD_TIME,  "
                + "	p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.DELIVERED_TO_RECEIVED_LEAD_TIME, p.MONTHS_IN_PAST_FOR_AMC, p.MONTHS_IN_FUTURE_FOR_AMC, "
                + "     p.PROGRAM_NOTES, pm.USERNAME `PROGRAM_MANAGER_USERNAME`, pm.USER_ID `PROGRAM_MANAGER_USER_ID`, "
                + "     pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
                + "	rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, rc.AIR_FREIGHT_PERC `REALM_COUNTRY_AIR_FREIGHT_PERC`, rc.SEA_FREIGHT_PERC `REALM_COUNTRY_SEA_FREIGHT_PERC`, rc.SHIPPED_TO_ARRIVED_AIR_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_AIR_LEAD_TIME`, rc.SHIPPED_TO_ARRIVED_SEA_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_SEA_LEAD_TIME`, rc.ARRIVED_TO_DELIVERED_LEAD_TIME `REALM_COUNTRY_ARRIVED_TO_DELIVERED_LEAD_TIME`, "
                + "     rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "     c.COUNTRY_ID, c.COUNTRY_CODE,  "
                + "     cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_PR `COUNTRY_LABEL_PR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, "
                + "     cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CURRENCY_SYMBOL, cu.CONVERSION_RATE_TO_USD,  "
                + "     cul.LABEL_ID `CURRENCY_LABEL_ID`, cul.LABEL_EN `CURRENCY_LABEL_EN`, cul.LABEL_FR `CURRENCY_LABEL_FR`, cul.LABEL_PR `CURRENCY_LABEL_PR`, cul.LABEL_SP `CURRENCY_LABEL_SP`, "
                + "     o.ORGANISATION_ID, o.ORGANISATION_CODE, "
                + "     ol.LABEL_ID `ORGANISATION_LABEL_ID`, ol.LABEL_EN `ORGANISATION_LABEL_EN`, ol.LABEL_FR `ORGANISATION_LABEL_FR`, ol.LABEL_PR `ORGANISATION_LABEL_PR`, ol.LABEL_SP `ORGANISATION_LABEL_SP`, "
                + "     ha.HEALTH_AREA_ID, "
                + "     hal.LABEL_ID `HEALTH_AREA_LABEL_ID`, hal.LABEL_EN `HEALTH_AREA_LABEL_EN`, hal.LABEL_FR `HEALTH_AREA_LABEL_FR`, hal.LABEL_PR `HEALTH_AREA_LABEL_PR`, hal.LABEL_SP `HEALTH_AREA_LABEL_SP`, "
                + "     re.REGION_ID, "
                + "     rel.LABEL_ID `REGION_LABEL_ID`, rel.LABEL_EN `REGION_LABEL_EN`, rel.LABEL_FR `REGION_LABEL_FR`, rel.LABEL_PR `REGION_LABEL_PR`, rel.LABEL_SP `REGION_LABEL_SP`, "
                + "     u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_PR `UNIT_LABEL_PR`, ul.LABEL_SP `UNIT_LABEL_SP`, "
                + "     p.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE "
                + "FROM rm_program p  "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "LEFT JOIN ap_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID "
                + "LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
                + "LEFT JOIN rm_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + "LEFT JOIN rm_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID "
                + "LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID "
                + "LEFT JOIN rm_region re ON pr.REGION_ID=re.REGION_ID "
                + "LEFT JOIN ap_label rel ON re.LABEL_ID=rel.LABEL_ID "
                + "LEFT JOIN ap_unit u ON rc.PALLET_UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND rc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        int count = 1;
        for (UserAcl acl : curUser.getAclList()) {
            sqlString += "AND ("
                    + "(p.PROGRAM_ID=:programId" + count + " OR :programId" + count + "=-1) AND "
                    + "(p.REALM_COUNTRY_ID=:realmCountryId" + count + " OR :realmCountryId" + count + "=-1) AND "
                    + "(p.ORGANISATION_ID=:organisationId" + count + " OR :organisationId" + count + "=-1) AND "
                    + "(p.HEALTH_AREA_ID=:healthAreaId" + count + " OR :healthAreaId" + count + "=-1)) ";
            params.put("programId" + count, acl.getProgramId());
            params.put("realmCountryId" + count, acl.getRealmCountryId());
            params.put("organisationId" + count, acl.getOrganisationId());
            params.put("healthAreaId" + count, acl.getHealthAreaId());
        }
        System.out.println(sqlString);
        System.out.println(params);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProgramListResultSetExtractor());
    }

    @Override
    public List<Program> getProgramList(int realmId, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "	p.PROGRAM_ID, p.AIR_FREIGHT_PERC, p.SEA_FREIGHT_PERC, p.PLANNED_TO_DRAFT_LEAD_TIME, p.DRAFT_TO_SUBMITTED_LEAD_TIME,  "
                + "	p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.DELIVERED_TO_RECEIVED_LEAD_TIME, p.MONTHS_IN_PAST_FOR_AMC, p.MONTHS_IN_FUTURE_FOR_AMC, "
                + "     p.PROGRAM_NOTES, pm.USERNAME `PROGRAM_MANAGER_USERNAME`, pm.USER_ID `PROGRAM_MANAGER_USER_ID`, "
                + "     pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
                + "	rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, rc.AIR_FREIGHT_PERC `REALM_COUNTRY_AIR_FREIGHT_PERC`, rc.SEA_FREIGHT_PERC `REALM_COUNTRY_SEA_FREIGHT_PERC`, rc.SHIPPED_TO_ARRIVED_AIR_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_AIR_LEAD_TIME`, rc.SHIPPED_TO_ARRIVED_SEA_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_SEA_LEAD_TIME`, rc.ARRIVED_TO_DELIVERED_LEAD_TIME `REALM_COUNTRY_ARRIVED_TO_DELIVERED_LEAD_TIME`, "
                + "     rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "     c.COUNTRY_ID, c.COUNTRY_CODE,  "
                + "     cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_PR `COUNTRY_LABEL_PR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, "
                + "     cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CURRENCY_SYMBOL, cu.CONVERSION_RATE_TO_USD,  "
                + "     cul.LABEL_ID `CURRENCY_LABEL_ID`, cul.LABEL_EN `CURRENCY_LABEL_EN`, cul.LABEL_FR `CURRENCY_LABEL_FR`, cul.LABEL_PR `CURRENCY_LABEL_PR`, cul.LABEL_SP `CURRENCY_LABEL_SP`, "
                + "     o.ORGANISATION_ID, o.ORGANISATION_CODE, "
                + "     ol.LABEL_ID `ORGANISATION_LABEL_ID`, ol.LABEL_EN `ORGANISATION_LABEL_EN`, ol.LABEL_FR `ORGANISATION_LABEL_FR`, ol.LABEL_PR `ORGANISATION_LABEL_PR`, ol.LABEL_SP `ORGANISATION_LABEL_SP`, "
                + "     ha.HEALTH_AREA_ID, "
                + "     hal.LABEL_ID `HEALTH_AREA_LABEL_ID`, hal.LABEL_EN `HEALTH_AREA_LABEL_EN`, hal.LABEL_FR `HEALTH_AREA_LABEL_FR`, hal.LABEL_PR `HEALTH_AREA_LABEL_PR`, hal.LABEL_SP `HEALTH_AREA_LABEL_SP`, "
                + "     re.REGION_ID, "
                + "     rel.LABEL_ID `REGION_LABEL_ID`, rel.LABEL_EN `REGION_LABEL_EN`, rel.LABEL_FR `REGION_LABEL_FR`, rel.LABEL_PR `REGION_LABEL_PR`, rel.LABEL_SP `REGION_LABEL_SP`, "
                + "     u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_PR `UNIT_LABEL_PR`, ul.LABEL_SP `UNIT_LABEL_SP`, "
                + "     p.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE "
                + "FROM rm_program p  "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "LEFT JOIN ap_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID "
                + "LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
                + "LEFT JOIN rm_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + "LEFT JOIN rm_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID "
                + "LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID "
                + "LEFT JOIN rm_region re ON pr.REGION_ID=re.REGION_ID "
                + "LEFT JOIN ap_label rel ON re.LABEL_ID=rel.LABEL_ID "
                + "LEFT JOIN ap_unit u ON rc.PALLET_UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE AND rc.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND rc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        int count = 1;
        for (UserAcl acl : curUser.getAclList()) {
            sqlString += "AND ("
                    + "(p.PROGRAM_ID=:programId" + count + " OR :programId" + count + "=-1) AND "
                    + "(p.REALM_COUNTRY_ID=:realmCountryId" + count + " OR :realmCountryId" + count + "=-1) AND "
                    + "(p.ORGANISATION_ID=:organisationId" + count + " OR :organisationId" + count + "=-1) AND "
                    + "(p.HEALTH_AREA_ID=:healthAreaId" + count + " OR :healthAreaId" + count + "=-1)) ";
            params.put("programId" + count, acl.getProgramId());
            params.put("realmCountryId" + count, acl.getRealmCountryId());
            params.put("organisationId" + count, acl.getOrganisationId());
            params.put("healthAreaId" + count, acl.getHealthAreaId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProgramListResultSetExtractor());
    }

    @Override
    public Program getProgramById(int programId, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "	p.PROGRAM_ID, p.AIR_FREIGHT_PERC, p.SEA_FREIGHT_PERC, p.PLANNED_TO_DRAFT_LEAD_TIME, p.DRAFT_TO_SUBMITTED_LEAD_TIME,  "
                + "	p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.DELIVERED_TO_RECEIVED_LEAD_TIME, p.MONTHS_IN_PAST_FOR_AMC, p.MONTHS_IN_FUTURE_FOR_AMC, "
                + "     p.PROGRAM_NOTES, pm.USERNAME `PROGRAM_MANAGER_USERNAME`, pm.USER_ID `PROGRAM_MANAGER_USER_ID`, "
                + "     pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
                + "	rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, rc.AIR_FREIGHT_PERC `REALM_COUNTRY_AIR_FREIGHT_PERC`, rc.SEA_FREIGHT_PERC `REALM_COUNTRY_SEA_FREIGHT_PERC`, rc.SHIPPED_TO_ARRIVED_AIR_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_AIR_LEAD_TIME`, rc.SHIPPED_TO_ARRIVED_SEA_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_SEA_LEAD_TIME`, rc.ARRIVED_TO_DELIVERED_LEAD_TIME `REALM_COUNTRY_ARRIVED_TO_DELIVERED_LEAD_TIME`, "
                + "     rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "     c.COUNTRY_ID, c.COUNTRY_CODE,  "
                + "     cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_PR `COUNTRY_LABEL_PR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, "
                + "     cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CURRENCY_SYMBOL, cu.CONVERSION_RATE_TO_USD,  "
                + "     cul.LABEL_ID `CURRENCY_LABEL_ID`, cul.LABEL_EN `CURRENCY_LABEL_EN`, cul.LABEL_FR `CURRENCY_LABEL_FR`, cul.LABEL_PR `CURRENCY_LABEL_PR`, cul.LABEL_SP `CURRENCY_LABEL_SP`, "
                + "     o.ORGANISATION_ID, o.ORGANISATION_CODE, "
                + "     ol.LABEL_ID `ORGANISATION_LABEL_ID`, ol.LABEL_EN `ORGANISATION_LABEL_EN`, ol.LABEL_FR `ORGANISATION_LABEL_FR`, ol.LABEL_PR `ORGANISATION_LABEL_PR`, ol.LABEL_SP `ORGANISATION_LABEL_SP`, "
                + "     ha.HEALTH_AREA_ID, "
                + "     hal.LABEL_ID `HEALTH_AREA_LABEL_ID`, hal.LABEL_EN `HEALTH_AREA_LABEL_EN`, hal.LABEL_FR `HEALTH_AREA_LABEL_FR`, hal.LABEL_PR `HEALTH_AREA_LABEL_PR`, hal.LABEL_SP `HEALTH_AREA_LABEL_SP`, "
                + "     re.REGION_ID, "
                + "     rel.LABEL_ID `REGION_LABEL_ID`, rel.LABEL_EN `REGION_LABEL_EN`, rel.LABEL_FR `REGION_LABEL_FR`, rel.LABEL_PR `REGION_LABEL_PR`, rel.LABEL_SP `REGION_LABEL_SP`, "
                + "     u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_PR `UNIT_LABEL_PR`, ul.LABEL_SP `UNIT_LABEL_SP`, "
                + "     p.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE "
                + "FROM rm_program p  "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "LEFT JOIN ap_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID "
                + "LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
                + "LEFT JOIN rm_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + "LEFT JOIN rm_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID "
                + "LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID "
                + "LEFT JOIN rm_region re ON pr.REGION_ID=re.REGION_ID "
                + "LEFT JOIN ap_label rel ON re.LABEL_ID=rel.LABEL_ID "
                + "LEFT JOIN ap_unit u ON rc.PALLET_UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE AND p.PROGRAM_ID=:programId ";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND rc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        int count = 1;
        for (UserAcl acl : curUser.getAclList()) {
            sqlString += "AND ("
                    + "(p.PROGRAM_ID=:programId" + count + " OR :programId" + count + "=-1) AND "
                    + "(p.REALM_COUNTRY_ID=:realmCountryId" + count + " OR :realmCountryId" + count + "=-1) AND "
                    + "(p.ORGANISATION_ID=:organisationId" + count + " OR :organisationId" + count + "=-1) AND "
                    + "(p.HEALTH_AREA_ID=:healthAreaId" + count + " OR :healthAreaId" + count + "=-1)) ";
            params.put("programId" + count, acl.getProgramId());
            params.put("realmCountryId" + count, acl.getRealmCountryId());
            params.put("organisationId" + count, acl.getOrganisationId());
            params.put("healthAreaId" + count, acl.getHealthAreaId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ProgramResultSetExtractor());
    }

}
