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
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.rowMapper.ProgramListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import cc.altius.FASP.model.rowMapper.VersionRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
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
    @Autowired
    private AclService aclService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public String sqlListString = "SELECT  "
            + "     p.PROGRAM_ID, p.`PROGRAM_CODE`, p.AIR_FREIGHT_PERC, p.SEA_FREIGHT_PERC, p.PLANNED_TO_SUBMITTED_LEAD_TIME, "
            + "     cpv.VERSION_ID `CV_VERSION_ID`, cpv.NOTES `CV_VERSION_NOTES`, cpv.CREATED_DATE `CV_CREATED_DATE`, cpvcb.USER_ID `CV_CB_USER_ID`, cpvcb.USERNAME `CV_CB_USERNAME`, cpv.LAST_MODIFIED_DATE `CV_LAST_MODIFIED_DATE`, cpvlmb.USER_ID `CV_LMB_USER_ID`, cpvlmb.USERNAME `CV_LMB_USERNAME`, "
            + "     vt.VERSION_TYPE_ID `CV_VERSION_TYPE_ID`, vtl.LABEL_ID `CV_VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `CV_VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `CV_VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `CV_VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `CV_VERSION_TYPE_LABEL_PR`, "
            + "     vs.VERSION_STATUS_ID `CV_VERSION_STATUS_ID`, vsl.LABEL_ID `CV_VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `CV_VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `CV_VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `CV_VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `CV_VERSION_STATUS_LABEL_PR`, "
            + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME, p.ARRIVED_TO_DELIVERED_LEAD_TIME, "
            + "     p.PROGRAM_NOTES, pm.USERNAME `PROGRAM_MANAGER_USERNAME`, pm.USER_ID `PROGRAM_MANAGER_USER_ID`, "
            + "     pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
            + "     rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MAX_GAURDRAIL, r.MAX_MOS_MAX_GAURDRAIL, "
            + "     rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
            + "     c.COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2,  "
            + "     cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_PR `COUNTRY_LABEL_PR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, "
            + "     cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD,  "
            + "     cul.LABEL_ID `CURRENCY_LABEL_ID`, cul.LABEL_EN `CURRENCY_LABEL_EN`, cul.LABEL_FR `CURRENCY_LABEL_FR`, cul.LABEL_PR `CURRENCY_LABEL_PR`, cul.LABEL_SP `CURRENCY_LABEL_SP`, "
            + "     o.ORGANISATION_ID, o.ORGANISATION_CODE, "
            + "     ol.LABEL_ID `ORGANISATION_LABEL_ID`, ol.LABEL_EN `ORGANISATION_LABEL_EN`, ol.LABEL_FR `ORGANISATION_LABEL_FR`, ol.LABEL_PR `ORGANISATION_LABEL_PR`, ol.LABEL_SP `ORGANISATION_LABEL_SP`, "
            + "     ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, "
            + "     hal.LABEL_ID `HEALTH_AREA_LABEL_ID`, hal.LABEL_EN `HEALTH_AREA_LABEL_EN`, hal.LABEL_FR `HEALTH_AREA_LABEL_FR`, hal.LABEL_PR `HEALTH_AREA_LABEL_PR`, hal.LABEL_SP `HEALTH_AREA_LABEL_SP`, "
            + "     re.REGION_ID, re.CAPACITY_CBM, re.GLN, "
            + "     rel.LABEL_ID `REGION_LABEL_ID`, rel.LABEL_EN `REGION_LABEL_EN`, rel.LABEL_FR `REGION_LABEL_FR`, rel.LABEL_PR `REGION_LABEL_PR`, rel.LABEL_SP `REGION_LABEL_SP`, "
            + "     u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_PR `UNIT_LABEL_PR`, ul.LABEL_SP `UNIT_LABEL_SP`, "
            + "     pv.VERSION_ID `VT_VERSION_ID`, pv.NOTES `VT_VERSION_NOTES`, pv.CREATED_DATE `VT_CREATED_DATE`, pvcb.USER_ID `VT_CB_USER_ID`, pvcb.USERNAME `VT_CB_USERNAME`, pv.LAST_MODIFIED_DATE `VT_LAST_MODIFIED_DATE`, pvlmb.USER_ID `VT_LMB_USER_ID`, pvlmb.USERNAME `VT_LMB_USERNAME`, "
            + "     pvt.VERSION_TYPE_ID `VT_VERSION_TYPE_ID`, pvtl.LABEL_ID `VT_VERSION_TYPE_LABEL_ID`, pvtl.LABEL_EN `VT_VERSION_TYPE_LABEL_EN`, pvtl.LABEL_FR `VT_VERSION_TYPE_LABEL_FR`, pvtl.LABEL_SP `VT_VERSION_TYPE_LABEL_SP`, pvtl.LABEL_PR `VT_VERSION_TYPE_LABEL_PR`, "
            + "     pvs.VERSION_STATUS_ID `VT_VERSION_STATUS_ID`, pvsl.LABEL_ID `VT_VERSION_STATUS_LABEL_ID`, pvsl.LABEL_EN `VT_VERSION_STATUS_LABEL_EN`, pvsl.LABEL_FR `VT_VERSION_STATUS_LABEL_FR`, pvsl.LABEL_SP `VT_VERSION_STATUS_LABEL_SP`, pvsl.LABEL_PR `VT_VERSION_STATUS_LABEL_PR`, "
            + "     p.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE "
            + " FROM rm_program p  "
            + " LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
            + " LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
            + " LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
            + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + " LEFT JOIN ap_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID "
            + " LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
            + " LEFT JOIN rm_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
            + " LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
            + " LEFT JOIN rm_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
            + " LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
            + " LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID "
            + " LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID "
            + " LEFT JOIN rm_region re ON pr.REGION_ID=re.REGION_ID "
            + " LEFT JOIN ap_label rel ON re.LABEL_ID=rel.LABEL_ID "
            + " LEFT JOIN ap_unit u ON rc.PALLET_UNIT_ID=u.UNIT_ID "
            + " LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
            + " LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
            + " LEFT JOIN rm_program_version cpv ON p.PROGRAM_ID=cpv.PROGRAM_ID AND p.CURRENT_VERSION_ID=cpv.VERSION_ID "
            + " LEFT JOIN ap_version_type vt ON cpv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
            + " LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID "
            + " LEFT JOIN ap_version_status vs ON cpv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID "
            + " LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID "
            + " LEFT JOIN us_user cpvcb ON cpv.CREATED_BY=cpvcb.USER_ID "
            + " LEFT JOIN us_user cpvlmb ON cpv.LAST_MODIFIED_BY=cpvlmb.USER_ID "
            + " LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID "
            + " LEFT JOIN ap_version_type pvt ON pv.VERSION_TYPE_ID=pvt.VERSION_TYPE_ID "
            + " LEFT JOIN ap_label pvtl ON pvt.LABEL_ID=pvtl.LABEL_ID "
            + " LEFT JOIN ap_version_status pvs ON pv.VERSION_STATUS_ID=pvs.VERSION_STATUS_ID "
            + " LEFT JOIN ap_label pvsl ON pvs.LABEL_ID=pvsl.LABEL_ID "
            + " LEFT JOIN us_user pvcb ON pv.CREATED_BY=pvcb.USER_ID "
            + " LEFT JOIN us_user pvlmb ON pv.LAST_MODIFIED_BY=pvlmb.USER_ID "
            + " WHERE TRUE ";
    private final String sqlOrderBy = "";
//            " ORDER BY p.PROGRAM_ID, pv.VERSION_ID, pr.REGION_ID ";

    public String sqlListStringForProgramPlanningUnit = " SELECT ppu.PROGRAM_PLANNING_UNIT_ID,  "
            + " pg.PROGRAM_ID, pgl.LABEL_ID `PROGRAM_LABEL_ID`, pgl.LABEL_EN `PROGRAM_LABEL_EN`, pgl.LABEL_FR `PROGRAM_LABEL_FR`, pgl.LABEL_PR `PROGRAM_LABEL_PR`, pgl.LABEL_SP `PROGRAM_LABEL_SP`, "
            + " pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
            + " ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, ppu.LOCAL_PROCUREMENT_LEAD_TIME, ppu.SHELF_LIFE, ppu.CATALOG_PRICE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC, "
            + " ppu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ppu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ppu.LAST_MODIFIED_DATE "
            + " FROM  rm_program_planning_unit ppu  "
            + " LEFT JOIN rm_program pg ON pg.PROGRAM_ID=ppu.PROGRAM_ID "
            + " LEFT JOIN rm_realm_country rc ON pg.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
            + " LEFT JOIN ap_label pgl ON pgl.LABEL_ID=pg.LABEL_ID "
            + " LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
            + " LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID "
            + " LEFT JOIN us_user cb ON ppu.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON ppu.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE ";

    @Override
    @Transactional
    public int addProgram(Program p, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int labelId = this.labelDao.addLabel(p.getLabel(), curUser.getUserId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program").usingGeneratedKeyColumns("PROGRAM_ID");
        params.put("REALM_COUNTRY_ID", p.getRealmCountry().getRealmCountryId());
        params.put("ORGANISATION_ID", p.getOrganisation().getId());
        params.put("HEALTH_AREA_ID", p.getHealthArea().getId());
        params.put("PROGRAM_CODE", p.getProgramCode());
        params.put("LABEL_ID", labelId);
        params.put("PROGRAM_MANAGER_USER_ID", p.getProgramManager().getUserId());
        params.put("PROGRAM_NOTES", p.getProgramNotes());
        params.put("AIR_FREIGHT_PERC", p.getAirFreightPerc());
        params.put("SEA_FREIGHT_PERC", p.getSeaFreightPerc());
        params.put("PLANNED_TO_SUBMITTED_LEAD_TIME", p.getPlannedToSubmittedLeadTime());
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
        params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
        params.put("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME", p.getShippedToArrivedBySeaLeadTime());
        params.put("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME", p.getShippedToArrivedByAirLeadTime());
        params.put("ARRIVED_TO_DELIVERED_LEAD_TIME", p.getArrivedToDeliveredLeadTime());
        params.put("CURRENT_VERSION_ID", null);
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
        params.clear();
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("programId", programId);
        params.put("versionTypeId", 1);
        params.put("versionStatusId", 1);
        params.put("notes", "");
        Version version = new Version();
//        int versionId = this.namedParameterJdbcTemplate.queryForObject("CALL getVersionId(:programId,:versionTypeId,:versionStatusId,:notes,:curUser, :curDate)", params, new VersionRowMapper());
        version = this.namedParameterJdbcTemplate.queryForObject("CALL getVersionId(:programId,:versionTypeId,:versionStatusId,:notes,:curUser, :curDate)", params, new VersionRowMapper());
        params.put("versionId", version.getVersionId());
        this.namedParameterJdbcTemplate.update("UPDATE rm_program SET CURRENT_VERSION_ID=:versionId WHERE PROGRAM_ID=:programId", params);
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
        params.put("plannedToSubmittedLeadTime", p.getPlannedToSubmittedLeadTime());
        params.put("submittedToApprovedLeadTime", p.getSubmittedToApprovedLeadTime());
        params.put("approvedToShippedLeadTime", p.getApprovedToShippedLeadTime());
        params.put("shippedToArrivedBySeaLeadTime", p.getShippedToArrivedBySeaLeadTime());
        params.put("shippedToArrivedByAirLeadTime", p.getShippedToArrivedByAirLeadTime());
        params.put("arrivedToDeliveredLeadTime", p.getArrivedToDeliveredLeadTime());
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
                + "p.PLANNED_TO_SUBMITTED_LEAD_TIME=:plannedToSubmittedLeadTime, "
                + "p.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
                + "p.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
                + "p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=:shippedToArrivedBySeaLeadTime, "
                + "p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=:shippedToArrivedByAirLeadTime, "
                + "p.ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime, "
                + "p.ACTIVE=:active,"
                + "p.LAST_MODIFIED_BY=IF("
                + "     p.PROGRAM_MANAGER_USER_ID!=:programManagerUserId OR "
                + "     p.PROGRAM_NOTES!=:programNotes OR "
                + "     p.AIR_FREIGHT_PERC!=:airFreightPerc OR "
                + "     p.SEA_FREIGHT_PERC!=:seaFreightPerc OR "
                + "     p.PLANNED_TO_SUBMITTED_LEAD_TIME!=:plannedToSubmittedLeadTime OR "
                + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME!=:submittedToApprovedLeadTime OR "
                + "     p.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR "
                + "     p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=:shippedToArrivedBySeaLeadTime OR "
                + "     p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=:shippedToArrivedByAirLeadTime OR "
                + "     p.ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime OR "
                + "     p.ACTIVE!=:active, "
                + ":curUser, p.LAST_MODIFIED_BY), "
                + "p.LAST_MODIFIED_DATE=IF("
                + "     p.PROGRAM_MANAGER_USER_ID!=:programManagerUserId OR "
                + "     p.PROGRAM_NOTES!=:programNotes OR "
                + "     p.AIR_FREIGHT_PERC!=:airFreightPerc OR "
                + "     p.SEA_FREIGHT_PERC!=:seaFreightPerc OR "
                + "     p.PLANNED_TO_SUBMITTED_LEAD_TIME!=:plannedToSubmittedLeadTime OR "
                + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME!=:submittedToApprovedLeadTime OR "
                + "     p.APPROVED_TO_SHIPPED_LEAD_TIME!=:approvedToShippedLeadTime OR "
                + "     p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=:shippedToArrivedBySeaLeadTime OR "
                + "     p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=:shippedToArrivedByAirLeadTime OR "
                + "     p.ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime OR "
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
    public List<ProgramDTO> getProgramListForDropdown(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sql = "SELECT r.`PROGRAM_ID`,label.`LABEL_ID`,label.`LABEL_EN`,label.`LABEL_FR`,label.`LABEL_PR`,label.`LABEL_SP` "
                + "FROM rm_program r  "
                + "LEFT JOIN ap_label label ON label.`LABEL_ID`=r.`LABEL_ID` WHERE 1 ";
        int count = 1;
        for (UserAcl acl : curUser.getAclList()) {
            sql += "AND ("
                    + "(r.PROGRAM_ID=:programId" + count + " OR :programId" + count + "=-1)) ";
            params.put("programId" + count, acl.getProgramId());
            count++;
        }
        return this.namedParameterJdbcTemplate.query(sql, params, new ProgramDTORowMapper());
    }

    @Override
    public List<Program> getProgramListForProgramIds(String[] programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        StringBuilder paramBuilder = new StringBuilder();
        for (String pId : programIds) {
            paramBuilder.append("'").append(pId).append("',");
        }
        if (programIds.length > 0) {
            paramBuilder.setLength(paramBuilder.length() - 1);
        }
        sqlStringBuilder.append(" AND p.PROGRAM_ID IN (").append(paramBuilder).append(") ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramListResultSetExtractor());
    }

    @Override
    public List<Program> getProgramList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramListResultSetExtractor());
    }

    @Override
    public List<Program> getProgramList(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramListResultSetExtractor());
    }

    @Override
    public Program getProgramById(int programId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND p.PROGRAM_ID=:programId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramResultSetExtractor());
    }

    @Override
    public List<ProgramPlanningUnit> getPlanningUnitListForProgramId(int programId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForProgramPlanningUnit).append(" AND pg.PROGRAM_ID=:programId");
        if (active) {
            sqlStringBuilder = sqlStringBuilder.append(" AND ppu.ACTIVE ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramPlanningUnitRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForProgramIds(String programIds, CustomUserDetails curUser) {
        String sql = "SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM rm_program p LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID WHERE p.PROGRAM_ID IN (" + programIds + ") AND ppu.PROGRAM_ID IS NOT NULL";
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", programIds);
        return this.namedParameterJdbcTemplate.query(sql, params, new SimpleObjectRowMapper());
    }

    @Override
    public int saveProgramPlanningUnit(ProgramPlanningUnit[] programPlanningUnits, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_planning_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (ProgramPlanningUnit ppu : programPlanningUnits) {
            System.out.println("ppu.getProgramPlanningUnitId()---" + ppu.getProgramPlanningUnitId());
            if (ppu.getProgramPlanningUnitId() == 0) {
                System.out.println("insert----------------");
                // Insert
                params = new HashMap<>();
                params.put("PLANNING_UNIT_ID", ppu.getPlanningUnit().getId());
                params.put("PROGRAM_ID", ppu.getProgram().getId());
                params.put("REORDER_FREQUENCY_IN_MONTHS", ppu.getReorderFrequencyInMonths());
                params.put("MIN_MONTHS_OF_STOCK", ppu.getMinMonthsOfStock());
                params.put("LOCAL_PROCUREMENT_LEAD_TIME", ppu.getLocalProcurementLeadTime());
                params.put("SHELF_LIFE", ppu.getShelfLife());
                params.put("CATALOG_PRICE", ppu.getCatalogPrice());
                params.put("MONTHS_IN_PAST_FOR_AMC", ppu.getMonthsInPastForAmc());
                params.put("MONTHS_IN_FUTURE_FOR_AMC", ppu.getMonthsInFutureForAmc());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                System.out.println("update----------------");
                // Update
                params = new HashMap<>();
                params.put("programPlanningUnitId", ppu.getProgramPlanningUnitId());
                params.put("reorderFrequencyInMonths", ppu.getReorderFrequencyInMonths());
                params.put("minMonthsOfStock", ppu.getMinMonthsOfStock());
                params.put("localProcurementLeadTime", ppu.getLocalProcurementLeadTime());
                params.put("shelfLife", ppu.getShelfLife());
                params.put("catalogPrice", ppu.getCatalogPrice());
                params.put("monthsInPastForAmc", ppu.getMonthsInPastForAmc());
                params.put("monthsInFutureForAmc", ppu.getMonthsInFutureForAmc());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", ppu.isActive());
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
                    + "rm_program_planning_unit ppu SET ppu.MIN_MONTHS_OF_STOCK=:minMonthsOfStock,ppu.REORDER_FREQUENCY_IN_MONTHS=:reorderFrequencyInMonths, ppu.LOCAL_PROCUREMENT_LEAD_TIME=:localProcurementLeadTime, ppu.SHELF_LIFE=:shelfLife, ppu.CATALOG_PRICE=:catalogPrice, ppu.MONTHS_IN_PAST_FOR_AMC=:monthsInPastForAmc, ppu.MONTHS_IN_FUTURE_FOR_AMC=:monthsInFutureForAmc, ppu.ACTIVE=:active, "
                    + "ppu.LAST_MODIFIED_DATE=IF(ppu.ACTIVE!=:active OR ppu.REORDER_FREQUENCY_IN_MONTHS!=:reorderFrequencyInMonths OR ppu.LOCAL_PROCUREMENT_LEAD_TIME!=:localProcurementLeadTime OR ppu.SHELF_LIFE!=:shelfLife OR ppu.CATALOG_PRICE!=:catalogPrice OR ppu.MONTHS_IN_PAST_FOR_AMC!=:monthsInPastForAmc OR ppu.MONTHS_IN_FUTURE_FOR_AMC!=:monthsInFutureForAmc, :curDate, ppu.LAST_MODIFIED_DATE), "
                    + "ppu.LAST_MODIFIED_BY=IF  (ppu.ACTIVE!=:active OR ppu.REORDER_FREQUENCY_IN_MONTHS!=:reorderFrequencyInMonths OR ppu.LOCAL_PROCUREMENT_LEAD_TIME!=:localProcurementLeadTime OR ppu.SHELF_LIFE!=:shelfLife OR ppu.CATALOG_PRICE!=:catalogPrice OR ppu.MONTHS_IN_PAST_FOR_AMC!=:monthsInPastForAmc OR ppu.MONTHS_IN_FUTURE_FOR_AMC!=:monthsInFutureForAmc, :curUser, ppu.LAST_MODIFIED_BY) "
                    + "WHERE ppu.PROGRAM_PLANNING_UNIT_ID=:programPlanningUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<Program> getProgramListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND p.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramListResultSetExtractor());
    }

    @Override
    public List<ProgramPlanningUnit> getProgramPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForProgramPlanningUnit).append(" AND ppu.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "pg", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramPlanningUnitRowMapper());
    }

    @Override
    public List<ProgramPlanningUnit> getPlanningUnitListForProgramAndCategoryId(int programId, int productCategoryId, boolean active, CustomUserDetails curUser) {
        String sqlListStr = " SELECT ppu.PROGRAM_PLANNING_UNIT_ID,  "
                + " pg.PROGRAM_ID, pgl.LABEL_ID `PROGRAM_LABEL_ID`, pgl.LABEL_EN `PROGRAM_LABEL_EN`, pgl.LABEL_FR `PROGRAM_LABEL_FR`, pgl.LABEL_PR `PROGRAM_LABEL_PR`, pgl.LABEL_SP `PROGRAM_LABEL_SP`, "
                + " pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
                + " ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK,ppu.LOCAL_PROCUREMENT_LEAD_TIME, ppu.SHELF_LIFE, ppu.CATALOG_PRICE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC, "
                + " ppu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ppu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ppu.LAST_MODIFIED_DATE "
                + " FROM  rm_program_planning_unit ppu  "
                + " LEFT JOIN rm_program pg ON pg.PROGRAM_ID=ppu.PROGRAM_ID "
                + " LEFT JOIN rm_realm_country rc ON pg.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + " LEFT JOIN ap_label pgl ON pgl.LABEL_ID=pg.LABEL_ID "
                + " LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID "
                + " LEFT JOIN us_user cb ON ppu.CREATED_BY=cb.USER_ID "
                + " LEFT JOIN us_user lmb ON ppu.LAST_MODIFIED_BY=lmb.USER_ID "
                + " LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.FORECASTING_UNIT_ID"
                + " WHERE true";
        StringBuilder sqlStringBuilder = new StringBuilder(sqlListStr).append(" AND pg.PROGRAM_ID=:programId");
        if (active) {
            sqlStringBuilder = sqlStringBuilder.append(" AND ppu.ACTIVE ");
        }
        Map<String, Object> params = new HashMap<>();
        if (productCategoryId > 0) {
            sqlStringBuilder = sqlStringBuilder.append(" AND fu.PRODUCT_CATEGORY_ID=:productCategoryId ");
            params.put("productCategoryId", productCategoryId);
        }

        params.put("programId", programId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramPlanningUnitRowMapper());
    }

    @Override
    public Program getProgramList(int realmId, int programId, int versionId) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("programId", programId);
        params.put("versionId", versionId);
        sqlStringBuilder.append(" AND rc.`REALM_ID`=:realmId AND p.`PROGRAM_ID`=:programId AND cpv.`VERSION_ID`=:versionId GROUP BY p.`PROGRAM_ID`");
        sqlStringBuilder.append(this.sqlOrderBy);
        System.out.println("sqlStringBuilder.toString()---" + sqlStringBuilder.toString());
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramResultSetExtractor());
    }

}
