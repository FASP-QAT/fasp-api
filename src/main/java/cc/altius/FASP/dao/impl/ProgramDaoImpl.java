/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ErpBatchDTO;
import cc.altius.FASP.model.DTO.ErpOrderAutocompleteDTO;
import cc.altius.FASP.model.DTO.ErpOrderDTO;
import cc.altius.FASP.model.DTO.ErpShipmentDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.DTO.rowMapper.ARTMISHistoryDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ERPLinkedShipmentsDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpBatchDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderAutocompleteDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderDTOListResultSetExtractor;
import cc.altius.FASP.model.DTO.rowMapper.ManualTaggingDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ManualTaggingOrderDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.NotERPLinkedShipmentsRowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ProgramDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ShipmentNotificationDTORowMapper;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.rowMapper.LoadProgramRowMapper;
import cc.altius.FASP.model.rowMapper.LoadProgramVersionRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramPlanningUnitProcurementAgentPriceRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramPlanningUnitResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import cc.altius.FASP.model.rowMapper.VersionRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.DateUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Autowired
    private ProgramService programService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public String sqlListString = "SELECT   "
            + "     p.PROGRAM_ID, p.`PROGRAM_CODE`, p.AIR_FREIGHT_PERC, p.SEA_FREIGHT_PERC, p.PLANNED_TO_SUBMITTED_LEAD_TIME,  "
            + "     cpv.VERSION_ID `CV_VERSION_ID`, cpv.NOTES `CV_VERSION_NOTES`, cpv.CREATED_DATE `CV_CREATED_DATE`, cpvcb.USER_ID `CV_CB_USER_ID`, cpvcb.USERNAME `CV_CB_USERNAME`, cpv.LAST_MODIFIED_DATE `CV_LAST_MODIFIED_DATE`, cpvlmb.USER_ID `CV_LMB_USER_ID`, cpvlmb.USERNAME `CV_LMB_USERNAME`,  "
            + "     vt.VERSION_TYPE_ID `CV_VERSION_TYPE_ID`, vt.LABEL_ID `CV_VERSION_TYPE_LABEL_ID`, vt.LABEL_EN `CV_VERSION_TYPE_LABEL_EN`, vt.LABEL_FR `CV_VERSION_TYPE_LABEL_FR`, vt.LABEL_SP `CV_VERSION_TYPE_LABEL_SP`, vt.LABEL_PR `CV_VERSION_TYPE_LABEL_PR`,  "
            + "     vs.VERSION_STATUS_ID `CV_VERSION_STATUS_ID`, vs.LABEL_ID `CV_VERSION_STATUS_LABEL_ID`, vs.LABEL_EN `CV_VERSION_STATUS_LABEL_EN`, vs.LABEL_FR `CV_VERSION_STATUS_LABEL_FR`, vs.LABEL_SP `CV_VERSION_STATUS_LABEL_SP`, vs.LABEL_PR `CV_VERSION_STATUS_LABEL_PR`,  "
            + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME, p.ARRIVED_TO_DELIVERED_LEAD_TIME,  "
            + "     p.PROGRAM_NOTES, pm.USERNAME `PROGRAM_MANAGER_USERNAME`, pm.USER_ID `PROGRAM_MANAGER_USER_ID`,  "
            + "     p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_PR, p.LABEL_SP,  "
            + "     rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MAX_GAURDRAIL, r.MAX_MOS_MAX_GAURDRAIL,  r.MIN_QPL_TOLERANCE, r.MIN_QPL_TOLERANCE_CUT_OFF, r.MAX_QPL_TOLERANCE, "
            + "     r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`,  "
            + "     c.COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2,   "
            + "     c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_PR `COUNTRY_LABEL_PR`, c.LABEL_SP `COUNTRY_LABEL_SP`,  "
            + "     cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD,   "
            + "     cu.LABEL_ID `CURRENCY_LABEL_ID`, cu.LABEL_EN `CURRENCY_LABEL_EN`, cu.LABEL_FR `CURRENCY_LABEL_FR`, cu.LABEL_PR `CURRENCY_LABEL_PR`, cu.LABEL_SP `CURRENCY_LABEL_SP`,  "
            + "     o.ORGANISATION_ID, o.ORGANISATION_CODE,  "
            + "     o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_PR `ORGANISATION_LABEL_PR`, o.LABEL_SP `ORGANISATION_LABEL_SP`,  "
            + "     ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE,  "
            + "     ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`,  "
            + "     re.REGION_ID, re.CAPACITY_CBM, re.GLN,  "
            + "     re.LABEL_ID `REGION_LABEL_ID`, re.LABEL_EN `REGION_LABEL_EN`, re.LABEL_FR `REGION_LABEL_FR`, re.LABEL_PR `REGION_LABEL_PR`, re.LABEL_SP `REGION_LABEL_SP`,  "
            + "     u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_PR `UNIT_LABEL_PR`, u.LABEL_SP `UNIT_LABEL_SP`,  "
            + "     pv.VERSION_ID `VT_VERSION_ID`, pv.NOTES `VT_VERSION_NOTES`, pv.CREATED_DATE `VT_CREATED_DATE`, pvcb.USER_ID `VT_CB_USER_ID`, pvcb.USERNAME `VT_CB_USERNAME`, pv.LAST_MODIFIED_DATE `VT_LAST_MODIFIED_DATE`, pvlmb.USER_ID `VT_LMB_USER_ID`, pvlmb.USERNAME `VT_LMB_USERNAME`,  "
            + "     pvt.VERSION_TYPE_ID `VT_VERSION_TYPE_ID`, pvt.LABEL_ID `VT_VERSION_TYPE_LABEL_ID`, pvt.LABEL_EN `VT_VERSION_TYPE_LABEL_EN`, pvt.LABEL_FR `VT_VERSION_TYPE_LABEL_FR`, pvt.LABEL_SP `VT_VERSION_TYPE_LABEL_SP`, pvt.LABEL_PR `VT_VERSION_TYPE_LABEL_PR`,  "
            + "     pvs.VERSION_STATUS_ID `VT_VERSION_STATUS_ID`, pvs.LABEL_ID `VT_VERSION_STATUS_LABEL_ID`, pvs.LABEL_EN `VT_VERSION_STATUS_LABEL_EN`, pvs.LABEL_FR `VT_VERSION_STATUS_LABEL_FR`, pvs.LABEL_SP `VT_VERSION_STATUS_LABEL_SP`, pvs.LABEL_PR `VT_VERSION_STATUS_LABEL_PR`,  "
            + "     p.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE  "
            + " FROM vw_program p   "
            + " LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
            + " LEFT JOIN vw_realm r ON rc.REALM_ID=r.REALM_ID  "
            + " LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  "
            + " LEFT JOIN vw_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID  "
            + " LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID  "
            + " LEFT JOIN vw_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID  "
            + " LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID  "
            + " LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID  "
            + " LEFT JOIN vw_region re ON pr.REGION_ID=re.REGION_ID  "
            + " LEFT JOIN vw_unit u ON rc.PALLET_UNIT_ID=u.UNIT_ID  "
            + " LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID  "
            + " LEFT JOIN rm_program_version cpv ON p.PROGRAM_ID=cpv.PROGRAM_ID AND p.CURRENT_VERSION_ID=cpv.VERSION_ID  "
            + " LEFT JOIN vw_version_type vt ON cpv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID  "
            + " LEFT JOIN vw_version_status vs ON cpv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID  "
            + " LEFT JOIN us_user cpvcb ON cpv.CREATED_BY=cpvcb.USER_ID  "
            + " LEFT JOIN us_user cpvlmb ON cpv.LAST_MODIFIED_BY=cpvlmb.USER_ID  "
            + " LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID  "
            + " LEFT JOIN vw_version_type pvt ON pv.VERSION_TYPE_ID=pvt.VERSION_TYPE_ID  "
            + " LEFT JOIN vw_version_status pvs ON pv.VERSION_STATUS_ID=pvs.VERSION_STATUS_ID  "
            + " LEFT JOIN us_user pvcb ON pv.CREATED_BY=pvcb.USER_ID  "
            + " LEFT JOIN us_user pvlmb ON pv.LAST_MODIFIED_BY=pvlmb.USER_ID  "
            + " WHERE TRUE ";
    private final String sqlOrderBy = " ORDER BY p.PROGRAM_CODE, pv.VERSION_ID";

    public String sqlListStringForProgramPlanningUnit = "SELECT ppu.PROGRAM_PLANNING_UNIT_ID,   "
            + "    pg.PROGRAM_ID, pg.LABEL_ID `PROGRAM_LABEL_ID`, pg.LABEL_EN `PROGRAM_LABEL_EN`, pg.LABEL_FR `PROGRAM_LABEL_FR`, pg.LABEL_PR `PROGRAM_LABEL_PR`, pg.LABEL_SP `PROGRAM_LABEL_SP`,  "
            + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,  "
            + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`,  "
            + "    ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, ppu.LOCAL_PROCUREMENT_LEAD_TIME, ppu.SHELF_LIFE, ppu.CATALOG_PRICE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC,  "
            + "    ppu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ppu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ppu.LAST_MODIFIED_DATE  "
            + " FROM  rm_program_planning_unit ppu   "
            + " LEFT JOIN vw_program pg ON pg.PROGRAM_ID=ppu.PROGRAM_ID  "
            + " LEFT JOIN rm_realm_country rc ON pg.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
            + " LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
            + " LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
            + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
            + " LEFT JOIN us_user cb ON ppu.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON ppu.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE ";

    public String sqlListStringForProgramPlanningUnitProcurementAgentPricing = "SELECT ppu.PROGRAM_PLANNING_UNIT_ID,   "
            + "    pg.PROGRAM_ID, pg.LABEL_ID `PROGRAM_LABEL_ID`, pg.LABEL_EN `PROGRAM_LABEL_EN`, pg.LABEL_FR `PROGRAM_LABEL_FR`, pg.LABEL_PR `PROGRAM_LABEL_PR`, pg.LABEL_SP `PROGRAM_LABEL_SP`,  "
            + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,  "
            + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`,  "
            + "    ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, ppu.LOCAL_PROCUREMENT_LEAD_TIME, ppu.SHELF_LIFE, ppu.CATALOG_PRICE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC,  "
            + "    ppu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ppu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ppu.LAST_MODIFIED_DATE,  "
            + "    pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.PROCUREMENT_AGENT_CODE, "
            + "    ppupa.PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID, ppupa.PRICE `PROCUREMENT_AGENT_PRICE`, "
            + "    ppupa.ACTIVE `PPUPA_ACTIVE`, cb2.USER_ID `PPUPA_CB_USER_ID`, cb2.USERNAME `PPUPA_CB_USERNAME`, ppupa.CREATED_DATE `PPUPA_CREATED_DATE`, lmb2.USER_ID `PPUPA_LMB_USER_ID`, lmb2.USERNAME `PPUPA_LMB_USERNAME`, ppupa.LAST_MODIFIED_DATE `PPUPA_LAST_MODIFIED_DATE`  "
            + " FROM  rm_program_planning_unit ppu   "
            + " LEFT JOIN vw_program pg ON pg.PROGRAM_ID=ppu.PROGRAM_ID  "
            + " LEFT JOIN rm_realm_country rc ON pg.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
            + " LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
            + " LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
            + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
            + " LEFT JOIN us_user cb ON ppu.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON ppu.LAST_MODIFIED_BY=lmb.USER_ID "
            + " LEFT JOIN rm_program_planning_unit_procurement_agent ppupa ON ppu.PROGRAM_PLANNING_UNIT_ID=ppupa.PROGRAM_PLANNING_UNIT_ID "
            + " LEFT JOIN vw_procurement_agent pa ON ppupa.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
            + " LEFT JOIN us_user cb2 ON ppupa.CREATED_BY=cb2.USER_ID  "
            + " LEFT JOIN us_user lmb2 ON ppupa.LAST_MODIFIED_BY=lmb2.USER_ID "
            + " WHERE TRUE ";

    @Override
    @Transactional

    public int addProgram(Program p, int realmId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int labelId = this.labelDao.addLabel(p.getLabel(), LabelConstants.RM_PROGRAM, curUser.getUserId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program").usingGeneratedKeyColumns("PROGRAM_ID");
        params.put("REALM_ID", realmId);
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
        params.put("programCode", p.getProgramCode());
        params.put("organisationId", p.getOrganisation().getId());
        params.put("healthAreaId", p.getHealthArea().getId());
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
                + "p.PROGRAM_NOTES=:programNotes,"
                + "p.PROGRAM_CODE=:programCode, "
                + "p.ORGANISATION_ID=:organisationId, "
                + "p.HEALTH_AREA_ID=:healthAreaId, "
                + "p.AIR_FREIGHT_PERC=:airFreightPerc, "
                + "p.SEA_FREIGHT_PERC=:seaFreightPerc, "
                + "p.PLANNED_TO_SUBMITTED_LEAD_TIME=:plannedToSubmittedLeadTime, "
                + "p.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
                + "p.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
                + "p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=:shippedToArrivedBySeaLeadTime, "
                + "p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=:shippedToArrivedByAirLeadTime, "
                + "p.ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime, "
                + "p.ACTIVE=:active,"
                + "p.LAST_MODIFIED_BY=:curUser, "
                + "p.LAST_MODIFIED_DATE=:curDate, "
                + "pl.LABEL_EN=:labelEn, "
                + "pl.LAST_MODIFIED_BY=:curUser, "
                + "pl.LAST_MODIFIED_DATE=:curDate "
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
    public List<Program> getProgramList(CustomUserDetails curUser, boolean active) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        if (active) {
            sqlStringBuilder.append(" AND p.ACTIVE ").append(this.sqlOrderBy);
        }
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
        sqlStringBuilder.append(this.sqlOrderBy);
        Program p = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramResultSetExtractor());
        if (p == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthArea().getId(), p.getOrganisation().getId())) {
            return p;
        } else {
            return null;
        }
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
        String sql = "SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM rm_program p LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID WHERE p.PROGRAM_ID IN (" + programIds + ") AND ppu.PROGRAM_ID IS NOT NULL AND ppu.ACTIVE AND pu.ACTIVE GROUP BY pu.PLANNING_UNIT_ID";
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
            if (ppu.getProgramPlanningUnitId() == 0) {
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
            String sqlString = "UPDATE rm_program_planning_unit ppu SET ppu.MIN_MONTHS_OF_STOCK=:minMonthsOfStock,ppu.REORDER_FREQUENCY_IN_MONTHS=:reorderFrequencyInMonths, ppu.LOCAL_PROCUREMENT_LEAD_TIME=:localProcurementLeadTime, ppu.SHELF_LIFE=:shelfLife, ppu.CATALOG_PRICE=:catalogPrice, ppu.MONTHS_IN_PAST_FOR_AMC=:monthsInPastForAmc, ppu.MONTHS_IN_FUTURE_FOR_AMC=:monthsInFutureForAmc, ppu.ACTIVE=:active, ppu.LAST_MODIFIED_DATE=:curDate, ppu.LAST_MODIFIED_BY=:curUser WHERE ppu.PROGRAM_PLANNING_UNIT_ID=:programPlanningUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<ProgramPlanningUnitProcurementAgentPrice> getProgramPlanningUnitProcurementAgentList(int programPlanningUnitId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT  "
                + "    ppupa.PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID, ppupa.PROGRAM_PLANNING_UNIT_ID,    "
                + "    pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`,    "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,    "
                + "    p2.PROGRAM_ID, p2.LABEL_ID `PROGRAM_LABEL_ID`, p2.LABEL_EN `PROGRAM_LABEL_EN`, p2.LABEL_FR `PROGRAM_LABEL_FR`, p2.LABEL_SP `PROGRAM_LABEL_SP`, p2.LABEL_PR `PROGRAM_LABEL_PR`,  "
                + "    ppupa.PRICE `PROGRAM_PRICE`,  "
                + "    cb2.USER_ID `PPUPA_CB_USER_ID`, cb2.USERNAME `PPUPA_CB_USERNAME`, lmb2.USER_ID `PPUPA_LMB_USER_ID`, lmb2.USERNAME `PPUPA_LMB_USERNAME`, ppupa.ACTIVE `PPUPA_ACTIVE`, ppupa.CREATED_DATE `PPUPA_CREATED_DATE`, ppupa.LAST_MODIFIED_DATE `PPUPA_LAST_MODIFIED_DATE` "
                + "FROM rm_program_planning_unit_procurement_agent ppupa "
                + "LEFT JOIN rm_program_planning_unit ppu ON ppupa.PROGRAM_PLANNING_UNIT_ID=ppu.PROGRAM_PLANNING_UNIT_ID "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID   "
                + "LEFT JOIN vw_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=ppupa.PROCUREMENT_AGENT_ID    "
                + "LEFT JOIN vw_program p2 ON ppu.PROGRAM_ID=p2.PROGRAM_ID  "
                + "LEFT JOIN rm_realm_country rc ON p2.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN us_user cb2 ON ppupa.CREATED_BY=cb2.USER_ID     "
                + "LEFT JOIN us_user lmb2 ON ppupa.LAST_MODIFIED_BY=lmb2.USER_ID  "
                + "WHERE ppupa.PROGRAM_PLANNING_UNIT_ID=:programPlanningUnitId");
        Map<String, Object> params = new HashMap<>();
        params.put("programPlanningUnitId", programPlanningUnitId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p2", curUser);
        if (active) {
            sqlStringBuilder.append(" AND ppupa.ACTIVE");
        }
        sqlStringBuilder.append(" ORDER BY ppu.PROGRAM_ID, pu.LABEL_EN, pa.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramPlanningUnitProcurementAgentPriceRowMapper());
    }

    @Override
    public int saveProgramPlanningUnitProcurementAgentPrice(ProgramPlanningUnitProcurementAgentPrice[] programPlanningUnitProcurementAgentPrices, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_planning_unit_procurement_agent");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (ProgramPlanningUnitProcurementAgentPrice ppupa : programPlanningUnitProcurementAgentPrices) {
            if (ppupa.getProgramPlanningUnitProcurementAgentId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("PROGRAM_PLANNING_UNIT_ID", ppupa.getProgramPlanningUnitId());
                params.put("PROCUREMENT_AGENT_ID", ppupa.getProcurementAgent().getId());
                params.put("PRICE", ppupa.getPrice());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                // Update
                params = new HashMap<>();
                params.put("programPlanningUnitProcurementAgentId", ppupa.getProgramPlanningUnitProcurementAgentId());
                params.put("price", ppupa.getPrice());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", ppupa.isActive());
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
                    + "rm_program_planning_unit_procurement_agent ppupa "
                    + "SET "
                    + "ppupa.PRICE=:price, "
                    + "ppupa.ACTIVE=:active, "
                    + "ppupa.LAST_MODIFIED_DATE=:curDate, "
                    + "ppupa.LAST_MODIFIED_BY=:curUser "
                    + "WHERE ppupa.PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID=:programPlanningUnitProcurementAgentId";
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
        String sqlListStr = this.sqlListStringForProgramPlanningUnit;
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
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramResultSetExtractor());
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForManualTagging(ManualTaggingDTO manualTaggingDTO, CustomUserDetails curUser) {
        String sql = "";
        List<ManualTaggingDTO> list = null;
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", manualTaggingDTO.getPlanningUnitIdsString());
        params.put("programId", manualTaggingDTO.getProgramId());
        if (manualTaggingDTO.getLinkingType() == 1) {
            sql = "CALL getShipmentListForManualLinking(:programId, :planningUnitId, -1)";
            System.out.println("params---" + params);
            list = this.namedParameterJdbcTemplate.query(sql, params, new ManualTaggingDTORowMapper());
        } else if (manualTaggingDTO.getLinkingType() == 2) {
            sql = "CALL getShipmentListForAlreadyLinkedShipments(:programId, :planningUnitId, -1)";
            list = this.namedParameterJdbcTemplate.query(sql, params, new ERPLinkedShipmentsDTORowMapper());
        } else {
            sql = "CALL getErpShipmentForNotLinked(:countryId,:productcategoryId, :planningUnitId, :realmId)";
            params.put("productcategoryId", manualTaggingDTO.getProductCategoryIdsString());
            params.put("countryId", manualTaggingDTO.getCountryId());
            params.put("realmId", curUser.getRealm().getRealmId());
            list = this.namedParameterJdbcTemplate.query(sql, params, new NotERPLinkedShipmentsRowMapper());
        }

        System.out.println("list---" + manualTaggingDTO);
        return list;
    }

    @Override
    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType) {
        StringBuilder sql = new StringBuilder();
        sql.append("  (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`, "
                + " e.`QTY`,e.`STATUS`,l.`LABEL_ID` AS PLANNING_UNIT_LABEL_ID,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS PLANNING_UNIT_LABEL_EN,p.PLANNING_UNIT_ID, "
                + " l.`LABEL_FR` AS PLANNING_UNIT_LABEL_FR,l.`LABEL_PR` PLANNING_UNIT_LABEL_PR,l.`LABEL_SP` AS PLANNING_UNIT_LABEL_SP,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE,pu.SKU_CODE FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=e.`ERP_ORDER_ID`"
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.STATUS "
                + " WHERE e.`ERP_ORDER_ID` IN (SELECT a.`ERP_ORDER_ID` FROM (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID,sm.SHIPMENT_STATUS_MAPPING_ID "
                + " FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID`"
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + " WHERE e.ORDER_NO=? AND pu.`PLANNING_UNIT_ID`=? AND (mt.SHIPMENT_ID IS NULL  OR mt.ACTIVE=0) "
                + " GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`)  a  "
                + "  ) AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15) GROUP BY e.`ERP_ORDER_ID`) ");
        return this.jdbcTemplate.query(sql.toString(), new NotERPLinkedShipmentsRowMapper(), roNoOrderNo, planningUnitId);
    }

    @Override
    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int programId, int planningUnitId, int linkingType, int parentShipmentId) {
        String reason = "";
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        params.put("roNoOrderNo", roNoOrderNo);
        sql.append("  SELECT sst.`ERP_ORDER_ID`,sst.`RO_NO`,sst.`RO_PRIME_LINE_NO`,sst.`ORDER_NO`,sst.`PRIME_LINE_NO`,sst.`SHIPMENT_ID`,sst.`PLANNING_UNIT_SKU_CODE`,sst.`PROCUREMENT_UNIT_SKU_CODE`,sst.`ORDER_TYPE`,sst.`QTY`,sst.`SUPPLIER_NAME`,sst.`PRICE`,sst.`SHIPPING_COST`,sst.`RECPIENT_COUNTRY`,sst.`STATUS`,sst.`LABEL_ID`,sst.LABEL_EN,sst.`LABEL_FR`,sst.`LABEL_PR`,sst.`LABEL_SP`,sst.CURRENT_ESTIMATED_DELIVERY_DATE,sst.ACTIVE,sst.`NOTES`,sst.`CONVERSION_FACTOR`\n"
                + "FROM ( ");
        sql.append(" (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`,e.`SHIPMENT_ID`,e.`PLANNING_UNIT_SKU_CODE`,e.`PROCUREMENT_UNIT_SKU_CODE`,e.`ORDER_TYPE`,e.`QTY`,e.`SUPPLIER_NAME`,e.`PRICE`,e.`SHIPPING_COST`,e.`RECPIENT_COUNTRY`,e.`STATUS`,l.`LABEL_ID`,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS LABEL_EN,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS CURRENT_ESTIMATED_DELIVERY_DATE,IFNULL(mt.ACTIVE,0) AS ACTIVE,mt.`NOTES`,mt.`CONVERSION_FACTOR` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=e.`ERP_ORDER_ID` ");
        if (linkingType == 2) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }

        sql.append(" WHERE e.`ERP_ORDER_ID` IN (SELECT a.`ERP_ORDER_ID` FROM (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID,sm.`SHIPMENT_STATUS_MAPPING_ID` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE "
                + "	FROM rm_realm_country rc "
                + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=e.RECPIENT_COUNTRY "
                + " LEFT JOIN rm_program pm ON pm.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID  "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS`"
                + " LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + " WHERE pm.`PROGRAM_ID`=:programId ");
        if (planningUnitId != 0) {
            sql.append(" AND pu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        if (roNoOrderNo != null && roNoOrderNo != "" && !roNoOrderNo.equals("0")) {
            sql.append(" AND e.RO_NO=:roNoOrderNo ");
        }
        if (linkingType == 1) {
            sql.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sql.append(" GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`)  a "
                + " ) AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15) ");
        if (linkingType == 2) {
            params.put("parentShipmentId", parentShipmentId);
            sql.append(" AND (mt.SHIPMENT_ID=:parentShipmentId OR mt.SHIPMENT_ID IS NULL) ");
        }
        sql.append(" GROUP BY e.`ERP_ORDER_ID`) ");
        sql.append(" UNION ");
        sql.append(" (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`,e.`SHIPMENT_ID`,e.`PLANNING_UNIT_SKU_CODE`,e.`PROCUREMENT_UNIT_SKU_CODE`,e.`ORDER_TYPE`,e.`QTY`,e.`SUPPLIER_NAME`,e.`PRICE`,e.`SHIPPING_COST`,e.`RECPIENT_COUNTRY`,e.`STATUS`,l.`LABEL_ID`,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS LABEL_EN,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS CURRENT_ESTIMATED_DELIVERY_DATE,IFNULL(mt.ACTIVE,0) AS ACTIVE,mt.`NOTES`,mt.`CONVERSION_FACTOR` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=e.`ERP_ORDER_ID` ");
        if (linkingType == 2) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }

        sql.append(" WHERE e.`ERP_ORDER_ID` IN (SELECT a.`ERP_ORDER_ID` FROM (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID,sm.`SHIPMENT_STATUS_MAPPING_ID` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE "
                + "	FROM rm_realm_country rc "
                + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=e.RECPIENT_COUNTRY "
                + " LEFT JOIN rm_program pm ON pm.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID                      "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + " WHERE pm.`PROGRAM_ID`=:programId ");
        if (planningUnitId != 0) {
            sql.append(" AND pu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        if (roNoOrderNo != null && roNoOrderNo != "" && !roNoOrderNo.equals("0")) {
            sql.append(" AND e.ORDER_NO=:roNoOrderNo ");
        }

        if (linkingType == 1) {
            sql.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sql.append(" GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`) a "
                + " ) AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15) ");
        if (linkingType == 2) {
            params.put("parentShipmentId", parentShipmentId);
            sql.append(" AND (mt.SHIPMENT_ID=:parentShipmentId OR mt.SHIPMENT_ID IS NULL) ");
        }
        sql.append(" GROUP BY e.`ERP_ORDER_ID`) ");
        sql.append("  ) sst ORDER BY sst.CURRENT_ESTIMATED_DELIVERY_DATE DESC ");
        System.out.println("params----" + params);
        System.out.println("query******************************" + sql.toString());
        return this.namedParameterJdbcTemplate.query(sql.toString(), params, new ManualTaggingOrderDTORowMapper());

    }

    @Override
    public int linkShipmentWithARTMIS(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int rows;
        System.out.println("conversion factor---" + manualTaggingOrderDTO.getConversionFactor());
        String sql = "SELECT COUNT(*) FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
        int count = this.jdbcTemplate.queryForObject(sql, Integer.class, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
        if (count == 0) {
            sql = "INSERT INTO rm_manual_tagging VALUES (NULL,?,?,?,?,?,?,?,1,?,?);";
            int row = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), (manualTaggingOrderDTO.getParentShipmentId() != 0 ? manualTaggingOrderDTO.getParentShipmentId() : manualTaggingOrderDTO.getShipmentId()), curDate, curUser.getUserId(), curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getConversionFactor());
            // Get shipment details from erp order table
            sql = " SELECT "
                    + " eo.ERP_ORDER_ID, eo.RO_NO, eo.RO_PRIME_LINE_NO, eo.ORDER_NO, eo.PRIME_LINE_NO , "
                    + " eo.ORDER_TYPE, eo.CREATED_DATE, eo.PARENT_RO, eo.PARENT_CREATED_DATE, eo.PLANNING_UNIT_SKU_CODE,  "
                    + " eo.PROCUREMENT_UNIT_SKU_CODE, eo.QTY, eo.ORDERD_DATE, COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),eo.CURRENT_ESTIMATED_DELIVERY_DATE) as CURRENT_ESTIMATED_DELIVERY_DATE, eo.REQ_DELIVERY_DATE,  "
                    + " eo.AGREED_DELIVERY_DATE, eo.SUPPLIER_NAME, eo.PRICE, eo.SHIPPING_COST, eo.SHIP_BY,  "
                    + " eo.RECPIENT_NAME, eo.RECPIENT_COUNTRY, eo.`STATUS`, eo.`CHANGE_CODE`, ssm.SHIPMENT_STATUS_ID, eo.MANUAL_TAGGING, eo.CONVERSION_FACTOR, "
                    + " MIN(es.ACTUAL_DELIVERY_DATE) `ACTUAL_DELIVERY_DATE`, MIN(es.ACTUAL_SHIPMENT_DATE) `ACTUAL_SHIPMENT_DATE`, MIN(es.ARRIVAL_AT_DESTINATION_DATE) `ARRIVAL_AT_DESTINATION_DATE`, "
                    + " es.BATCH_NO, IF(es.DELIVERED_QTY !=0,COALESCE(es.DELIVERED_QTY, es.SHIPPED_QTY),es.SHIPPED_QTY) `BATCH_QTY`, es.`EXPIRY_DATE`, "
                    + " st.PLANNING_UNIT_ID,papu1.PLANNING_UNIT_ID AS ERP_PLANNING_UNIT_ID, papu2.PROCUREMENT_UNIT_ID, pu2.SUPPLIER_ID, ppu.SHELF_LIFE, "
                    + " sh.SHIPMENT_ID, sh.PROGRAM_ID, sh.PARENT_SHIPMENT_ID, "
                    + " st.SHIPMENT_TRANS_ID, st.VERSION_ID, st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.BUDGET_ID, st.ACTIVE, st.ERP_FLAG, st.ACCOUNT_FLAG, st.DATA_SOURCE_ID,eo.CONVERSION_FACTOR  "
                    + " FROM ( "
                    + " SELECT  "
                    + " e.ERP_ORDER_ID, e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO , "
                    + " e.ORDER_TYPE, e.CREATED_DATE, e.PARENT_RO, e.PARENT_CREATED_DATE, e.PLANNING_UNIT_SKU_CODE,  "
                    + " e.PROCUREMENT_UNIT_SKU_CODE, e.QTY, e.ORDERD_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE, e.REQ_DELIVERY_DATE,  "
                    + " e.AGREED_DELIVERY_DATE, e.SUPPLIER_NAME, e.PRICE, e.SHIPPING_COST, e.SHIP_BY, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, TRUE, FALSE) `MANUAL_TAGGING`, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, mt.CONVERSION_FACTOR, 1) `CONVERSION_FACTOR`,  "
                    + " e.RECPIENT_NAME, e.RECPIENT_COUNTRY, e.STATUS, e.CHANGE_CODE, COALESCE(e.PROGRAM_ID, mts.PROGRAM_ID) `PROGRAM_ID`, COALESCE(e.SHIPMENT_ID, mt.SHIPMENT_ID) `SHIPMENT_ID` "
                    + " FROM ( "
                    + " SELECT MAX(e.`ERP_ORDER_ID`) AS ERP_ORDER_ID FROM rm_erp_order e "
                    + " WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? "
                    + " ) es "
                    + " LEFT JOIN rm_erp_order e  ON e.`ERP_ORDER_ID`=es.`ERP_ORDER_ID` "
                    + " LEFT JOIN rm_manual_tagging mt ON e.ORDER_NO=mt.ORDER_NO AND e.PRIME_LINE_NO=mt.PRIME_LINE_NO AND mt.ACTIVE "
                    + " LEFT JOIN rm_shipment mts ON mt.SHIPMENT_ID=mts.SHIPMENT_ID "
                    + " ) eo "
                    + " LEFT JOIN (SELECT sx1.SHIPMENT_ID, sx1.PROGRAM_ID, sx1.PARENT_SHIPMENT_ID, MAX(st1.VERSION_ID) MAX_VERSION_ID FROM rm_shipment sx1 LEFT JOIN rm_shipment_trans st1 ON sx1.SHIPMENT_ID=st1.SHIPMENT_ID GROUP BY st1.SHIPMENT_ID) sh ON sh.SHIPMENT_ID=eo.SHIPMENT_ID AND sh.PROGRAM_ID=eo.PROGRAM_ID "
                    + " LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=sh.SHIPMENT_ID AND st.VERSION_ID=sh.MAX_VERSION_ID "
                    + " LEFT JOIN vw_planning_unit pu ON st.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                    + " LEFT JOIN rm_procurement_agent_planning_unit papu ON st.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID` AND papu.`PROCUREMENT_AGENT_ID`=1  "
                    + " LEFT JOIN rm_procurement_agent_planning_unit papu1 ON eo.PLANNING_UNIT_SKU_CODE=LEFT(papu1.SKU_CODE,12) AND papu1.PROCUREMENT_AGENT_ID=1 "
                    + " LEFT JOIN rm_procurement_agent_procurement_unit papu2 ON eo.PROCUREMENT_UNIT_SKU_CODE=LEFT(papu2.SKU_CODE,15) AND papu2.PROCUREMENT_AGENT_ID=1 "
                    + " LEFT JOIN rm_procurement_unit pu2 ON papu2.PROCUREMENT_UNIT_ID=pu2.PROCUREMENT_UNIT_ID "
                    + " LEFT JOIN rm_erp_shipment es ON es.ERP_ORDER_ID=eo.ERP_ORDER_ID "
                    + " LEFT JOIN rm_shipment_status_mapping ssm ON eo.`STATUS`=ssm.EXTERNAL_STATUS_STAGE "
                    + " LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=sh.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                    + " GROUP BY eo.`ERP_ORDER_ID`; ";
            List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sql, new ErpOrderDTOListResultSetExtractor(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
            if (erpOrderDTOList.size() == 1) {
                ErpOrderDTO erpOrderDTO = erpOrderDTOList.get(0);
                try {
                    // Shipment id found in file
                    logger.info("-----------------------------------------------------------");
                    logger.info("ERP Order - " + erpOrderDTO);
                    logger.info("Order no - " + erpOrderDTO.getEoOrderNo());
                    logger.info("Prime line no - " + erpOrderDTO.getEoPrimeLineNo());
                    logger.info("Active - " + erpOrderDTO.getShActive());
                    logger.info("ERP Flag - " + erpOrderDTO.getShErpFlag());
                    logger.info("ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                    logger.info("Shipment Id - " + erpOrderDTO.getShShipmentId());
                    logger.info("Change code - " + erpOrderDTO.getEoChangeCode());
                    logger.info("ManualTagging - " + erpOrderDTO.isManualTagging());
                    logger.info("Program Id - " + erpOrderDTO.getShProgramId());
                    logger.info("Shipment id - " + erpOrderDTO.getShShipmentId());
                    if (erpOrderDTO.getEoChangeCode() == 2) {
                        System.out.println("---------------2--------------");
                        // This is the Delete code so go ahead and delete this Order
                        logger.info("Change code is 2 so therefore delete this line item where shipmentId=" + erpOrderDTO.getShShipmentId());
                        sql = "UPDATE rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID SET st.ACTIVE=0, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate WHERE s.PARENT_SHIPMENT_ID=:shipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ACTIVE AND st.ERP_FLAG";
                        params.clear();
//                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                        params.put("shipmentId", erpOrderDTO.getShShipmentId());
                        params.put("orderNo", erpOrderDTO.getEoOrderNo());
                        params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                        params.put("curDate", curDate);
                        rows = this.namedParameterJdbcTemplate.update(sql, params);
                        logger.info(rows + " rows updated");

                    } else if (erpOrderDTO.isShErpFlag() && erpOrderDTO.getShParentShipmentId() == null) {
                        System.out.println("---------------3--------------");
                        // The ERP Flag is true and the Parent Shipment Id is null
                        logger.info("ERP Flag is true and Parent Shipment Id is null");
                        // Find all Shipments whose Parent Shipment Id is :parentShipmentId and :orderNo and :primeLineNo are matching
                        params.clear();
                        params.put("parentShipmentId", erpOrderDTO.getShShipmentId());
                        params.put("orderNo", erpOrderDTO.getEoOrderNo());
                        params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                        sql = "SELECT  st.SHIPMENT_TRANS_ID "
                                + "    FROM rm_shipment s "
                                + "LEFT JOIN (SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s left join rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo group by st.SHIPMENT_ID) sm ON sm.SHIPMENT_ID=s.SHIPMENT_ID "
                                + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID=sm.MAX_VERSION_ID "
                                + "WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ERP_FLAG=1 AND st.ACTIVE";
                        try {
                            logger.info("Trying to see if the ShipmentTrans exists with the same orderNo, primeLineNo and parentShipmentId");
                            int shipmentTransId = this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
                            logger.info("ShipmentTransId " + shipmentTransId + " found so going to update that with latest information");
                            // TODO shipment found therefore update it with all the information
                            sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID "
                                    + "SET  "
                                    + "    st.EXPECTED_DELIVERY_DATE=:expectedDeliveryDate, st.FREIGHT_COST=:freightCost, st.PRODUCT_COST=:productCost, "
                                    + "    st.RATE=:price, st.SHIPMENT_MODE=:shipBy, st.SHIPMENT_QTY=:qty, "
                                    + "    st.SHIPMENT_STATUS_ID=:shipmentStatusId, st.SUPPLIER_ID=:supplierId, st.PLANNED_DATE=:plannedDate, "
                                    + "    st.SUBMITTED_DATE=:submittedDate, st.APPROVED_DATE=:approvedDate, st.SHIPPED_DATE=:shippedDate, "
                                    + "    st.ARRIVED_DATE=:arrivedDate, st.RECEIVED_DATE=:receivedDate, st.LAST_MODIFIED_BY=1, "
                                    + "    st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate, st.NOTES=:notes "
                                    + "WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                            params.clear();
//                            params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                            params.put("shipmentTransId", shipmentTransId);
                            params.put("expectedDeliveryDate", erpOrderDTO.getExpectedDeliveryDate());
                            params.put("freightCost", erpOrderDTO.getEoShippingCost());
                            params.put("productCost", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * erpOrderDTO.getEoPrice() : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                            params.put("price", erpOrderDTO.getEoPrice());
                            params.put("shipBy", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                            params.put("qty", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor()) : erpOrderDTO.getEoQty()));
                            params.put("shipmentStatusId", erpOrderDTO.getEoShipmentStatusId());
                            params.put("supplierId", erpOrderDTO.getEoSupplierId());
                            params.put("plannedDate", erpOrderDTO.getEoCreatedDate());
                            params.put("submittedDate", erpOrderDTO.getEoCreatedDate());
                            params.put("approvedDate", erpOrderDTO.getEoOrderedDate());
                            params.put("shippedDate", erpOrderDTO.getEoActualShipmentDate());
                            params.put("arrivedDate", erpOrderDTO.getEoArrivalAtDestinationDate());
                            params.put("receivedDate", erpOrderDTO.getEoActualDeliveryDate());
                            params.put("curDate", curDate);
                            params.put("notes", "Auto updated from shipment linking");
                            this.namedParameterJdbcTemplate.update(sql, params);
                            logger.info("Updated the already existing Shipment Trans record (" + shipmentTransId + ") with new data");
                            logger.info("Now need to update the Batch information");
                            sql = "SELECT bi.BATCH_ID, stbi.SHIPMENT_TRANS_BATCH_INFO_ID, bi.BATCH_NO, bi.EXPIRY_DATE, stbi.BATCH_SHIPMENT_QTY FROM rm_shipment_trans_batch_info stbi LEFT JOIN rm_batch_info bi ON stbi.BATCH_ID=bi.BATCH_ID where stbi.SHIPMENT_TRANS_ID=:shipmentTransId group by stbi.BATCH_ID";
                            params.clear();
                            params.put("shipmentTransId", shipmentTransId);
                            List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ErpBatchDTORowMapper());
                            if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                                logger.info("Some batch information exists so need to check if it matches with what was already created");
                                for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                    if (es.isAutoGenerated()) {
                                        // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                        boolean found = false;
                                        for (ErpBatchDTO eb : erpBatchList) {
                                            if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                                // match found so no need to do anything
                                                eb.setStatus(0); // Leave alone
                                                es.setStatus(0); // Leave alone
                                                found = true;
                                                break;
                                            }
                                        }
                                        if (found == false) {
                                            es.setStatus(2); // Insert
                                        }
                                    } else {
                                        // This is not an autogenerated batch which means that we can match it on BatchNo
                                        ErpBatchDTO tempB = new ErpBatchDTO();
                                        tempB.setBatchNo(es.getBatchNo());
                                        int index = erpBatchList.indexOf(tempB);
                                        if (index == -1) {
                                            // Batch not found
                                            // therefore need to insert 
                                            es.setStatus(2); // Insert
                                        } else {
                                            // Batch found now check for Expiry date and Qty
                                            ErpBatchDTO eb = erpBatchList.get(index);
                                            if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                                // match found so no nneed to do anything
                                                eb.setStatus(0); // Leave alone
                                                es.setStatus(0); // Leave alone
                                            } else {
                                                es.setStatus(1); // Update
                                                eb.setStatus(1); // Update
                                                es.setExistingBatchId(eb.getBatchId());
                                                es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                            }
                                        }
                                    }
                                    logger.info("Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                    switch (es.getStatus()) {
                                        case 0: // Do nothing
                                            logger.info("This Batch matched with what was already there so do nothing");
                                            break;
                                        case 1: // update
                                            logger.info("Need to update this Batch");
                                            sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                            params.clear();
                                            params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("batchId", es.getExistingBatchId());
                                            this.namedParameterJdbcTemplate.update(sql, params);
                                            sql = "UPDATE rm_shipment_trans_batch_info stbi SET stbi.SHIPMENT_QTY=:qty WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                            params.clear();
                                            params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                            params.put("qty", es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty());
                                            this.namedParameterJdbcTemplate.update(sql, params);
                                            break;
                                        case -1: // Delete
                                            logger.info("Need to delete this Batch");
                                            sql = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                            params.clear();
                                            params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                            this.namedParameterJdbcTemplate.update(sql, params);
                                        case 2: // Insert
                                            logger.info("Need to insert this Batch");
                                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                            params.clear();
                                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                            params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                                            params.put("AUTO_GENERATED", es.isAutoGenerated());
                                            int batchId = sib.executeAndReturnKey(params).intValue();
                                            logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", batchId);
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            sib.execute(params);
                                            logger.info("Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                    }
                                }
                                logger.info("Checking if any old batches need to be deleted");
                                for (ErpBatchDTO eb : erpBatchList) {
                                    if (eb.getStatus() == -1) {
                                        logger.info("Batch no: " + eb.getBatchNo() + " Qty:" + eb.getQty() + " is going to be deleted");
                                        sql = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                        params.clear();
                                        params.put("shipmentTransBatchInfoId", eb.getShipmentTransBatchInfoId());
                                        this.namedParameterJdbcTemplate.update(sql, params);
                                    }
                                }
                            }
                        } catch (EmptyResultDataAccessException erda) {
                            // Counldn't find a record that matches the Order no and Prime Line no so go ahead and
                            logger.info("Couldn't find a Shipment Trans so this is a new record going ahead with creation");
                            // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                            // All other details to be taken from ARTMIS
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("SUGGESTED_QTY", null);
                            params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                            params.put("CONVERSION_RATE_TO_USD", 1);
                            params.put("PARENT_SHIPMENT_ID", (erpOrderDTO.getShParentShipmentId() != null ? erpOrderDTO.getShParentShipmentId() : erpOrderDTO.getShShipmentId()));
                            params.put("CREATED_BY", 1); //Default auto user in QAT
                            params.put("CREATED_DATE", curDate);
                            params.put("LAST_MODIFIED_BY", 1); //Default auto user in QAT
                            params.put("LAST_MODIFIED_DATE", curDate);
                            params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                            int newShipmentId = si.executeAndReturnKey(params).intValue();
                            logger.info("Shipment Id " + newShipmentId + " created");
                            SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                            params.clear();
                            params.put("SHIPMENT_ID", newShipmentId);
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                            params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                            params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                            params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                            params.put("PROCUREMENT_UNIT_ID", erpOrderDTO.getEoProcurementUnitId());
                            params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                            params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor()) : erpOrderDTO.getEoQty()));
                            params.put("RATE", erpOrderDTO.getEoPrice());
                            params.put("PRODUCT_COST", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * erpOrderDTO.getEoPrice() : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                            params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                            params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                            params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                            params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                            params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                            params.put("SHIPPED_DATE", erpOrderDTO.getEoActualShipmentDate());
                            params.put("ARRIVED_DATE", erpOrderDTO.getEoArrivalAtDestinationDate());
                            params.put("RECEIVED_DATE", erpOrderDTO.getEoActualDeliveryDate());
                            params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                            params.put("NOTES", (manualTaggingOrderDTO.getNotes() != null && manualTaggingOrderDTO.getNotes() != "" ? manualTaggingOrderDTO.getNotes() : "Auto created from shipment linking"));
                            params.put("ERP_FLAG", 1);
                            params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                            params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                            params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                            params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                            params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                            params.put("LAST_MODIFIED_BY", 1); // Default user
                            params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                            params.put("LAST_MODIFIED_DATE", curDate);
                            params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                            params.put("ACTIVE", true);
                            int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                            logger.info("Shipment Trans Id " + shipmentTransId + " created");
                            if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                                logger.info("Some batch information exists so going to create Batches");
                                for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                    // Insert into Batch info for each record
                                    SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                    params.clear();
                                    params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                    params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                    params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                    params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                    params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                                    params.put("AUTO_GENERATED", es.isAutoGenerated());
                                    int batchId = sib.executeAndReturnKey(params).intValue();
                                    logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                    params.clear();
                                    sib = null;
                                    sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                    params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                    params.put("BATCH_ID", batchId);
                                    params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                    sib.execute(params);
                                    logger.info("Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                }
                            } else {
                                // Insert into Batch info for each record
                                logger.info("No Batch information exists so creating one automatically");
                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                params.clear();
                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                                params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                                params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                                params.put("AUTO_GENERATED", true);
                                int batchId = sib.executeAndReturnKey(params).intValue();
                                logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                params.clear();
                                sib = null;
                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                params.put("BATCH_ID", batchId);
                                params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                                sib.execute(params);
                                logger.info("Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                            }
                        }
                        if (erpOrderDTO.isShipmentCancelled() || erpOrderDTO.isSkuChanged()) {
                            this.createERPNotification(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo(), erpOrderDTO.getShShipmentId(), (erpOrderDTO.isShipmentCancelled() ? 1 : 2));
                        }
                    } else {
                        System.out.println("---------------4--------------");
                        // This is a new Link request coming through
                        // So make the Shipment, Active = fasle and ERPFlag = true
                        logger.info("This is a first time linking attempt");
                        // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                        // All other details to be taken from ARTMIS + Current Shipment
//                        sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID SET st.`PLANNING_UNIT_ID`=:planningUnitId,st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                        sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID "
                                + " SET st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=1, "
                                + " s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate "
                                + " WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                        params.clear();
//                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                        System.out.println("shipment trans id-------------------------" + erpOrderDTO.getShShipmentTransId());
                        params.put("curDate", curDate);
                        params.put("shipmentTransId", erpOrderDTO.getShShipmentTransId());
                        this.namedParameterJdbcTemplate.update(sql, params);
                        logger.info("Existing Shipment has been marked as ERP_FLAG=true and ACTIVE=false");
                        params.clear();
                        params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                        params.put("SUGGESTED_QTY", null);
                        params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                        params.put("CONVERSION_RATE_TO_USD", 1);
                        params.put("PARENT_SHIPMENT_ID", (erpOrderDTO.getShParentShipmentId() != null ? erpOrderDTO.getShParentShipmentId() : erpOrderDTO.getShShipmentId()));
                        params.put("CREATED_BY", 1); //Default auto user in QAT
                        params.put("CREATED_DATE", curDate);
                        params.put("LAST_MODIFIED_BY", 1); //Default auto user in QAT
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                        int newShipmentId = si.executeAndReturnKey(params).intValue();
                        logger.info("Shipment Id " + newShipmentId + " created");
                        SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                        params.clear();
                        params.put("SHIPMENT_ID", newShipmentId);
                        params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                        params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                        params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                        params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                        params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                        params.put("PROCUREMENT_UNIT_ID", erpOrderDTO.getEoProcurementUnitId());
                        params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                        params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor()) : erpOrderDTO.getEoQty()));
                        params.put("RATE", erpOrderDTO.getEoPrice());
                        params.put("PRODUCT_COST", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * erpOrderDTO.getEoPrice() : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                        params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                        params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                        params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                        params.put("SHIPPED_DATE", erpOrderDTO.getEoActualShipmentDate());
                        params.put("ARRIVED_DATE", erpOrderDTO.getEoArrivalAtDestinationDate());
                        params.put("RECEIVED_DATE", erpOrderDTO.getEoActualDeliveryDate());
                        params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                        params.put("NOTES", (manualTaggingOrderDTO.getNotes() != null && manualTaggingOrderDTO.getNotes() != "" ? manualTaggingOrderDTO.getNotes() : "Auto created from shipment linking"));
                        params.put("ERP_FLAG", 1);
                        params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                        params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                        params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                        params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                        params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                        params.put("LAST_MODIFIED_BY", 1); // Default user
                        params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                        params.put("ACTIVE", true);
                        int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                        logger.info("Shipment Trans Id " + shipmentTransId + " created");
                        if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                            logger.info("Some batch information exists so going to create Batches");
                            for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                // Insert into Batch info for each record
                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                params.clear();
                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                                params.put("AUTO_GENERATED", es.isAutoGenerated());
                                int batchId = sib.executeAndReturnKey(params).intValue();
                                logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                params.clear();
                                sib = null;
                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                params.put("BATCH_ID", batchId);
                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                sib.execute(params);
                                logger.info("Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                            }
                        } else {
                            // Insert into Batch info for each record
                            logger.info("No Batch information exists so creating one automatically");
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                            params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                            params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                            params.put("AUTO_GENERATED", true);
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                            sib.execute(params);
                            logger.info("Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                        }
                        if (erpOrderDTO.isShipmentCancelled() || erpOrderDTO.isSkuChanged()) {
                            this.createERPNotification(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo(), erpOrderDTO.getShShipmentId(), (erpOrderDTO.isShipmentCancelled() ? 1 : 2));
                        }
                    }

                } catch (Exception e) {
                    logger.info("Error occurred while trying to import Shipment ", e);
                }

            }

            return row;
        } else {
            //Update conversion factor and notes
            sql = " SELECT st.`SHIPMENT_TRANS_ID`,st.`RATE` FROM rm_shipment_trans st "
                    + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
                    + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE` "
                    + "ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1;";

            Map<String, Object> map = this.jdbcTemplate.queryForMap(sql, manualTaggingOrderDTO.getParentShipmentId(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());

            sql = "UPDATE `rm_manual_tagging` m SET m.`CONVERSION_FACTOR`=?,m.`NOTES`=? "
                    + " WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE` AND m.`SHIPMENT_ID`=?; ";
            this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getConversionFactor(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), manualTaggingOrderDTO.getParentShipmentId());

            System.out.println("conversion factor---" + manualTaggingOrderDTO.getConversionFactor());
            sql = "UPDATE rm_shipment_trans st  SET st.`SHIPMENT_QTY`=?,st.`PRODUCT_COST`=?, "
                    + "st.`LAST_MODIFIED_DATE`=?,st.`LAST_MODIFIED_BY`=?,st.`NOTES`=? "
                    + "WHERE st.`SHIPMENT_TRANS_ID`=?;";
//            long convertedQty = ((new BigDecimal(manualTaggingOrderDTO.getQuantity())).multiply(manualTaggingOrderDTO.getConversionFactor())).longValueExact();
            long convertedQty = (long) ((double) manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor());
            System.out.println("convertedQty---" + convertedQty);
            System.out.println("(double) map.get(\"RATE\")--------" + map.get("RATE"));
            System.out.println("productCost___________________________________________________" + Double.parseDouble(map.get("RATE").toString()));
            double rate = Double.parseDouble(map.get("RATE").toString());
            double productCost = rate * (double) convertedQty;
            this.jdbcTemplate.update(sql, convertedQty, productCost, curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), (long) map.get("SHIPMENT_TRANS_ID"));
//            this.jdbcTemplate.update(sql, convertedQty, 1, curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), (long) map.get("SHIPMENT_TRANS_ID"));
            return -1;
        }
    }

    @Override
    public int linkShipmentWithARTMISWithoutShipmentid(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        String sql;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int parentShipmentId = 0;
        Map<String, Object> params = new HashMap<>();
        for (int i = 1; i <= 2; i++) {
            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
            params.put("SUGGESTED_QTY", null);
            params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
            params.put("CONVERSION_RATE_TO_USD", 1);
            params.put("PARENT_SHIPMENT_ID", (i == 1 ? null : parentShipmentId));
            params.put("CREATED_BY", 1); //Default auto user in QAT
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", 1); //Default auto user in QAT
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("MAX_VERSION_ID", 1); // Same as the Current Version that is already present
            int newShipmentId = si.executeAndReturnKey(params).intValue();
            logger.info("Shipment Id " + newShipmentId + " created");
            if (i == 1) {
                parentShipmentId = newShipmentId;
                sql = "INSERT INTO rm_manual_tagging VALUES (NULL,?,?,?,?,?,?,?,1,?,?);";
                int row = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), newShipmentId, curDate, curUser.getUserId(), curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getConversionFactor());
            }

            sql = "SELECT \n"
                    + "                     eo.ERP_ORDER_ID, eo.RO_NO, eo.RO_PRIME_LINE_NO, eo.ORDER_NO, eo.PRIME_LINE_NO , \n"
                    + "                     eo.ORDER_TYPE, eo.CREATED_DATE, eo.PARENT_RO, eo.PARENT_CREATED_DATE, eo.PLANNING_UNIT_SKU_CODE,  \n"
                    + "                     eo.PROCUREMENT_UNIT_SKU_CODE, eo.QTY, eo.ORDERD_DATE, COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),eo.CURRENT_ESTIMATED_DELIVERY_DATE) AS CURRENT_ESTIMATED_DELIVERY_DATE, eo.REQ_DELIVERY_DATE,  \n"
                    + "                     eo.AGREED_DELIVERY_DATE, eo.SUPPLIER_NAME, eo.PRICE, eo.SHIPPING_COST, eo.SHIP_BY,  \n"
                    + "                     eo.RECPIENT_NAME, eo.RECPIENT_COUNTRY, eo.`STATUS`, eo.`CHANGE_CODE`, ssm.SHIPMENT_STATUS_ID, eo.MANUAL_TAGGING, eo.CONVERSION_FACTOR, \n"
                    + "                     MIN(es.ACTUAL_DELIVERY_DATE) `ACTUAL_DELIVERY_DATE`, MIN(es.ACTUAL_SHIPMENT_DATE) `ACTUAL_SHIPMENT_DATE`, MIN(es.ARRIVAL_AT_DESTINATION_DATE) `ARRIVAL_AT_DESTINATION_DATE`, \n"
                    + "                     es.BATCH_NO, IF(es.DELIVERED_QTY !=0,COALESCE(es.DELIVERED_QTY, es.SHIPPED_QTY),es.SHIPPED_QTY) `BATCH_QTY`, es.`EXPIRY_DATE`, \n"
                    + "                     papu.PLANNING_UNIT_ID,papu.PLANNING_UNIT_ID AS ERP_PLANNING_UNIT_ID, papu2.PROCUREMENT_UNIT_ID, pu2.SUPPLIER_ID, ppu.SHELF_LIFE, \n"
                    + "                     sh.SHIPMENT_ID, sh.PROGRAM_ID, sh.PARENT_SHIPMENT_ID, \n"
                    + "                     st.SHIPMENT_TRANS_ID, st.VERSION_ID, st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.BUDGET_ID, st.ACTIVE, st.ERP_FLAG, st.ACCOUNT_FLAG, st.DATA_SOURCE_ID,eo.CONVERSION_FACTOR  \n"
                    + "                     FROM ( \n"
                    + "                     SELECT  \n"
                    + "                     e.ERP_ORDER_ID, e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO , \n"
                    + "                     e.ORDER_TYPE, e.CREATED_DATE, e.PARENT_RO, e.PARENT_CREATED_DATE, e.PLANNING_UNIT_SKU_CODE,  \n"
                    + "                     e.PROCUREMENT_UNIT_SKU_CODE, e.QTY, e.ORDERD_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE, e.REQ_DELIVERY_DATE,  \n"
                    + "                     e.AGREED_DELIVERY_DATE, e.SUPPLIER_NAME, e.PRICE, e.SHIPPING_COST, e.SHIP_BY, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, TRUE, FALSE) `MANUAL_TAGGING`, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, mt.CONVERSION_FACTOR, 1) `CONVERSION_FACTOR`,  \n"
                    + "                     e.RECPIENT_NAME, e.RECPIENT_COUNTRY, e.STATUS, e.CHANGE_CODE, COALESCE(e.PROGRAM_ID, mts.PROGRAM_ID) `PROGRAM_ID`, COALESCE(e.SHIPMENT_ID, mt.SHIPMENT_ID) `SHIPMENT_ID` \n"
                    + "                     FROM ( \n"
                    + "                     SELECT MAX(e.`ERP_ORDER_ID`) AS ERP_ORDER_ID FROM rm_erp_order e \n"
                    + "                     WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? \n"
                    + "                     ) es \n"
                    + "                     LEFT JOIN rm_erp_order e  ON e.`ERP_ORDER_ID`=es.`ERP_ORDER_ID` \n"
                    + "                     LEFT JOIN rm_manual_tagging mt ON e.ORDER_NO=mt.ORDER_NO AND e.PRIME_LINE_NO=mt.PRIME_LINE_NO AND mt.ACTIVE AND mt.SHIPMENT_ID=? \n"
                    + "                     LEFT JOIN rm_shipment mts ON mt.SHIPMENT_ID=mts.SHIPMENT_ID \n"
                    + "                     ) eo \n"
                    + "                     LEFT JOIN (SELECT sx1.SHIPMENT_ID, sx1.PROGRAM_ID, sx1.PARENT_SHIPMENT_ID, MAX(st1.VERSION_ID) MAX_VERSION_ID FROM rm_shipment sx1 LEFT JOIN rm_shipment_trans st1 ON sx1.SHIPMENT_ID=st1.SHIPMENT_ID GROUP BY st1.SHIPMENT_ID) sh ON sh.SHIPMENT_ID=eo.SHIPMENT_ID AND sh.PROGRAM_ID=eo.PROGRAM_ID \n"
                    + "                     LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=sh.SHIPMENT_ID AND st.VERSION_ID=sh.MAX_VERSION_ID \n"
                    + "                     LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=eo.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1  \n"
                    + "                     LEFT JOIN vw_planning_unit pu ON papu.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` \n"
                    + "                     LEFT JOIN rm_procurement_agent_procurement_unit papu2 ON eo.PROCUREMENT_UNIT_SKU_CODE=LEFT(papu2.SKU_CODE,15) AND papu2.PROCUREMENT_AGENT_ID=1 \n"
                    + "                     LEFT JOIN rm_procurement_unit pu2 ON papu2.PROCUREMENT_UNIT_ID=pu2.PROCUREMENT_UNIT_ID \n"
                    + "                     LEFT JOIN rm_erp_shipment es ON es.ERP_ORDER_ID=eo.ERP_ORDER_ID \n"
                    + "                     LEFT JOIN rm_shipment_status_mapping ssm ON eo.`STATUS`=ssm.EXTERNAL_STATUS_STAGE \n"
                    + "                     LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=sh.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID \n"
                    + "                     GROUP BY eo.`ERP_ORDER_ID`;";
            List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sql, new ErpOrderDTOListResultSetExtractor(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), newShipmentId);
            if (erpOrderDTOList.size() == 1) {
                ErpOrderDTO erpOrderDTO = erpOrderDTOList.get(0);
                try {
                    // Shipment id found in file
                    logger.info("-----------------------------------------------------------");
                    logger.info("ERP Order - " + erpOrderDTO);
                    logger.info("Order no - " + erpOrderDTO.getEoOrderNo());
                    logger.info("Prime line no - " + erpOrderDTO.getEoPrimeLineNo());
                    logger.info("Active - " + erpOrderDTO.getShActive());
                    logger.info("ERP Flag - " + erpOrderDTO.getShErpFlag());
                    logger.info("ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                    logger.info("Shipment Id - " + erpOrderDTO.getShShipmentId());
                    logger.info("Change code - " + erpOrderDTO.getEoChangeCode());
                    logger.info("ManualTagging - " + erpOrderDTO.isManualTagging());
                    logger.info("Program Id - " + erpOrderDTO.getShProgramId());
                    logger.info("Shipment id - " + erpOrderDTO.getShShipmentId());
                    SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                    params.clear();
                    params.put("SHIPMENT_ID", newShipmentId);
                    params.put("PLANNING_UNIT_ID", manualTaggingOrderDTO.getPlanningUnitId());
                    params.put("PROCUREMENT_AGENT_ID", 1);
                    params.put("FUNDING_SOURCE_ID", manualTaggingOrderDTO.getFundingSourceId());
                    params.put("BUDGET_ID", manualTaggingOrderDTO.getBudgetId());
                    params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                    params.put("PROCUREMENT_UNIT_ID", erpOrderDTO.getEoProcurementUnitId());
                    params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                    params.put("SHIPMENT_QTY", (manualTaggingOrderDTO.getConversionFactor() != 0 && manualTaggingOrderDTO.getConversionFactor() != 0.0 ? (manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor()) : manualTaggingOrderDTO.getQuantity()));
                    params.put("RATE", erpOrderDTO.getEoPrice());
                    params.put("PRODUCT_COST", manualTaggingOrderDTO.getQuantity() * erpOrderDTO.getEoPrice());
                    params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                    params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                    params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                    params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                    params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                    params.put("SHIPPED_DATE", erpOrderDTO.getEoActualShipmentDate());
                    params.put("ARRIVED_DATE", erpOrderDTO.getEoArrivalAtDestinationDate());
                    params.put("RECEIVED_DATE", erpOrderDTO.getEoActualDeliveryDate());
                    params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                    params.put("NOTES", "Auto created from shipment linking...");
                    params.put("ERP_FLAG", 1);
                    params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                    params.put("PRIME_LINE_NO", (i == 1 ? null : erpOrderDTO.getEoPrimeLineNo()));
                    params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                    params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                    params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                    params.put("LAST_MODIFIED_BY", 1); // Default user
                    params.put("DATA_SOURCE_ID", 17);
                    params.put("LAST_MODIFIED_DATE", curDate);
                    params.put("VERSION_ID", 1);
                    params.put("ACTIVE", (i == 1 ? false : true));
                    int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                    logger.info("Shipment Trans Id " + shipmentTransId + " created");
                    if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                        logger.info("Some batch information exists so going to create Batches");
                        for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                            // Insert into Batch info for each record
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("BATCH_NO", (es.isAutoGenerated() || i == 1 ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null || i == 1 ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                            params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                            params.put("AUTO_GENERATED", es.isAutoGenerated());
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? manualTaggingOrderDTO.getQuantity() : es.getBatchQty()));
                            sib.execute(params);
                            logger.info("Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                        }
                    } else {
                        // Insert into Batch info for each record
                        logger.info("No Batch information exists so creating one automatically");
                        SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                        params.clear();
                        params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                        params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                        params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                        params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                        params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                        params.put("AUTO_GENERATED", true);
                        int batchId = sib.executeAndReturnKey(params).intValue();
                        logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                        params.clear();
                        sib = null;
                        sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                        params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                        params.put("BATCH_ID", batchId);
                        params.put("BATCH_SHIPMENT_QTY", manualTaggingOrderDTO.getQuantity());
                        sib.execute(params);
                        logger.info("Pushed into shipmentBatchTrans with Qty " + manualTaggingOrderDTO.getQuantity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("Error occured while creating fresh shipment " + e);
                }
            }
        }

        return parentShipmentId;
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForDelinking(int programId, int planningUnitId
    ) {
        String sql = "CALL getShipmentListForDeLinking(:programId, :planningUnitId, -1)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        List<ManualTaggingDTO> list = this.namedParameterJdbcTemplate.query(sql, params, new ManualTaggingDTORowMapper());
        return list;
    }

    @Override
    public List<ManualTaggingDTO> getNotLinkedShipments(int programId, int linkingTypeId
    ) {
        String sql = "";
        List<ManualTaggingDTO> list = null;
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        if (linkingTypeId == 3) {
            sql = "CALL getNotLinkedShipments(:programId, -1)";
            list = this.namedParameterJdbcTemplate.query(sql, params, new ManualTaggingDTORowMapper());
        }

        return list;
    }

    @Override
    @Transactional
    public void delinkShipment(ManualTaggingOrderDTO erpOrderDTO, CustomUserDetails curUser
    ) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sql;
        int maxTransId;
        int parentShipmentId = erpOrderDTO.getParentShipmentId();

        sql = "SELECT b.SHIPMENT_ID FROM (SELECT a.* FROM ( "
                + "SELECT st.`SHIPMENT_TRANS_ID`,s.`SHIPMENT_ID`,st.`ACTIVE` FROM rm_shipment s "
                + "LEFT JOIN rm_shipment_trans st ON st.`SHIPMENT_ID`=s.`SHIPMENT_ID` "
                + "WHERE s.`PARENT_SHIPMENT_ID`=? ORDER BY st.`SHIPMENT_TRANS_ID` DESC) AS a "
                + "GROUP BY a.SHIPMENT_ID) AS b WHERE b.active;";
        System.out.println("delinking parenshipment id----------" + parentShipmentId);
        List<Integer> shipmentIdList = this.jdbcTemplate.queryForList(sql, Integer.class, parentShipmentId);
//        if (shipmentIdList.size() == 1 && erpOrderDTO.getShipmentId() == shipmentIdList.get(0)) {
        if (shipmentIdList.size() == 1) {
//            for (int shipmentId1 : shipmentIdList) {
            if (this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo()) != null) {
                System.out.println("inside if--------------------" + erpOrderDTO);
                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`SHIPMENT_ID`=?", Integer.class, parentShipmentId);
                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=1,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=?,`NOTES`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";
                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, erpOrderDTO.getNotes(), maxTransId);
                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());

                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=0,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=?,`NOTES`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";
                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, erpOrderDTO.getNotes(), maxTransId);
            } else {
                System.out.println("delinking inside else----------" + parentShipmentId);
            }
        } else {
            if (this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo()) != null) {
                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
                System.out.println("maxTransId--------" + maxTransId);
                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=0,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=?,`NOTES`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";
                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, erpOrderDTO.getNotes(), maxTransId);
                System.out.println("delinking inside else else----------" + maxTransId);
            }
        }
//        }
        sql = "UPDATE rm_manual_tagging m SET m.`ACTIVE`=0,m.`NOTES`=?,m.`LAST_MODIFIED_DATE`=?,m.`LAST_MODIFIED_BY`=? WHERE m.`SHIPMENT_ID`=? AND m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=?;";
        this.jdbcTemplate.update(sql, erpOrderDTO.getNotes(), curDate, curUser.getUserId(), erpOrderDTO.getParentShipmentId(), erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());

    }

    @Override
    public List<LoadProgram> getLoadProgram(CustomUserDetails curUser
    ) {
        StringBuilder sb = new StringBuilder("SELECT  "
                + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR, "
                + "    rc.REALM_COUNTRY_ID, c.LABEL_ID `REALM_COUNTRY_LABEL_ID`, c.LABEL_EN `REALM_COUNTRY_LABEL_EN`, c.LABEL_FR `REALM_COUNTRY_LABEL_FR`, c.LABEL_SP `REALM_COUNTRY_LABEL_SP`, c.LABEL_PR `REALM_COUNTRY_LABEL_PR`, c.COUNTRY_CODE, "
                + "    ha.HEALTH_AREA_ID, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, ha.HEALTH_AREA_CODE, "
                + "    o.ORGANISATION_ID, o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_SP `ORGANISATION_LABEL_SP`, o.LABEL_PR `ORGANISATION_LABEL_PR`, o.ORGANISATION_CODE, "
                + "    COUNT(pv.VERSION_ID) MAX_COUNT "
                + "FROM vw_program p  "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN vw_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                + "LEFT JOIN rm_health_area_country hac ON  ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID AND rc.REALM_COUNTRY_ID=hac.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID AND rc.REALM_COUNTRY_ID=oc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID "
                + "WHERE p.ACTIVE AND rc.ACTIVE AND ha.ACTIVE AND o.ACTIVE AND hac.ACTIVE AND oc.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" GROUP BY p.PROGRAM_ID");
        List<LoadProgram> programList = this.namedParameterJdbcTemplate.query(sb.toString(), params, new LoadProgramRowMapper());
        params.clear();
        params.put("programId", 0);
        for (LoadProgram lp : programList) {
            params.replace("programId", lp.getProgram().getId());
            lp.setVersionList(this.namedParameterJdbcTemplate.query("SELECT LPAD(pv.VERSION_ID,6,'0') VERSION_ID, vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR`, cb.USER_ID, cb.USERNAME, pv.CREATED_DATE FROM vw_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID WHERE p.ACTIVE AND p.PROGRAM_ID=:programId ORDER BY pv.VERSION_ID DESC LIMIT 0,5", params, new LoadProgramVersionRowMapper()));
        }
        return programList;
    }

    /**
     *
     * @param programId
     * @param page Page no is the pagination value that you want to see. Starts
     * from 0 which is shown by default. Every time the user clicks on More...
     * you should increment the pagination for that Page and return it
     * @param curUser
     * @return
     */
    @Override
    public LoadProgram getLoadProgram(int programId, int page, CustomUserDetails curUser
    ) {
        StringBuilder sb = new StringBuilder("SELECT  "
                + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR, "
                + "    rc.REALM_COUNTRY_ID, c.LABEL_ID `REALM_COUNTRY_LABEL_ID`, c.LABEL_EN `REALM_COUNTRY_LABEL_EN`, c.LABEL_FR `REALM_COUNTRY_LABEL_FR`, c.LABEL_SP `REALM_COUNTRY_LABEL_SP`, c.LABEL_PR `REALM_COUNTRY_LABEL_PR`, c.COUNTRY_CODE, "
                + "    ha.HEALTH_AREA_ID, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, ha.HEALTH_AREA_CODE, "
                + "    o.ORGANISATION_ID, o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_SP `ORGANISATION_LABEL_SP`, o.LABEL_PR `ORGANISATION_LABEL_PR`, o.ORGANISATION_CODE, "
                + "    COUNT(pv.VERSION_ID) MAX_COUNT "
                + "FROM vw_program p  "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN vw_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                + "LEFT JOIN rm_health_area_country hac ON  ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID AND rc.REALM_COUNTRY_ID=hac.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID AND rc.REALM_COUNTRY_ID=oc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID "
                + "WHERE p.ACTIVE AND rc.ACTIVE AND ha.ACTIVE AND o.ACTIVE AND hac.ACTIVE AND oc.ACTIVE AND p.PROGRAM_ID=:programId ");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" GROUP BY p.PROGRAM_ID");
        LoadProgram program = this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, new LoadProgramRowMapper());
        program.setCurrentPage(page);
        params.clear();
        params.put("programId", programId);
        int versionCount = this.namedParameterJdbcTemplate.queryForObject("SELECT COUNT(*) FROM rm_program_version pv WHERE pv.PROGRAM_ID=:programId", params, Integer.class);
        params.put("versionCount", versionCount);
        params.put("offsetNo", page * 5);
        int showCount = 0;
        if (versionCount - page * 5 > 5) {
            showCount = 5;
        } else if (versionCount - page * 5 > 0) {
            showCount = versionCount - page * 5;
        }
        params.put("showCount", showCount);
        program.setVersionList(this.namedParameterJdbcTemplate.query("SELECT LPAD(pv.VERSION_ID,6,'0') VERSION_ID, vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR`, cb.USER_ID, cb.USERNAME, pv.CREATED_DATE FROM vw_program p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID WHERE p.ACTIVE AND p.PROGRAM_ID=:programId ORDER BY pv.VERSION_ID DESC LIMIT :offsetNo, :showCount", params, new LoadProgramVersionRowMapper()));
        return program;
    }

    @Override
    public boolean validateProgramCode(int realmId, int programId, String programCode,
            CustomUserDetails curUser
    ) {
        String sql = "SELECT COUNT(*) FROM rm_program p LEFT JOIN rm_realm_country rc  ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID WHERE rc.REALM_ID = :realmId AND p.PROGRAM_CODE = :programCode AND (:programId = 0 OR p.PROGRAM_ID != :programId)";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("programId", programId);
        params.put("programCode", programCode);
        return (this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class) == 0);
    }

    @Override
    public List<Program> getProgramListForSyncProgram(String programIdsString, CustomUserDetails curUser
    ) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND p.PROGRAM_ID IN (").append(programIdsString).append(") ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramListResultSetExtractor());
    }

    @Override
    public List<ProgramPlanningUnit> getProgramPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForProgramPlanningUnitProcurementAgentPricing).append(" AND ppu.PROGRAM_ID IN (").append(programIdsString).append(") AND ppu.`PROGRAM_PLANNING_UNIT_ID`  IS NOT NULL  ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "pg", curUser);
        sqlStringBuilder.append(" ORDER BY pu.LABEL_EN, pa.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramPlanningUnitResultSetExtractor());
    }

    @Override
    public List<ErpOrderAutocompleteDTO> getErpOrderSearchData(String term, int programId, int planningUnitId, int linkingType
    ) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        params.put("", sb);

        sb.append("(SELECT e.`ERP_ORDER_ID`,e.`RO_NO` AS LABEL FROM rm_erp_order e "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1 "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + "LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE "
                + "FROM rm_realm_country rc "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=e.RECPIENT_COUNTRY "
                + "LEFT JOIN rm_program p ON p.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID AND p.`PROGRAM_ID`=:programId "
                + "LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + "WHERE  p.`REALM_COUNTRY_ID` IS NOT NULL AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15) AND e.`RO_NO` LIKE '%").append(term).append("%' ");
        if (planningUnitId != 0) {
            sb.append(" AND papu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        sb.append(" GROUP BY e.`RO_NO` ");
        if (linkingType == 1) {
            sb.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sb.append(" ) ");
        sb.append(" UNION ");
        sb.append("(SELECT e.`ERP_ORDER_ID`,e.`ORDER_NO` AS LABEL FROM rm_erp_order e "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1 "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + "LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE "
                + "FROM rm_realm_country rc "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=e.RECPIENT_COUNTRY "
                + "LEFT JOIN rm_program p ON p.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID AND p.`PROGRAM_ID`=:programId "
                + "LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + "WHERE p.`REALM_COUNTRY_ID` IS NOT NULL AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15) AND  e.`ORDER_NO` LIKE '%").append(term).append("%' ");
        if (planningUnitId != 0) {
            sb.append(" AND papu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        sb.append(" GROUP BY e.`ORDER_NO` ");

        if (linkingType == 1) {
            sb.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sb.append(" ) ");
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ErpOrderAutocompleteDTORowMapper());
    }

    @Override
    public String getSupplyPlanReviewerList(int programId, CustomUserDetails curUser
    ) {
        Program p = this.getProgramById(programId, curUser);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.EMAIL_ID "
                + "FROM us_user u "
                + "LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID "
                + "LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID "
                + "LEFT JOIN rm_program p ON p.PROGRAM_ID=:programId "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "WHERE "
                + "     ur.ROLE_ID='ROLE_SUPPLY_PLAN_REVIEWER' "
                + "     AND u.ACTIVE AND rc.REALM_ID=:realmId "
                + "     AND (acl.REALM_COUNTRY_ID IS NULL OR acl.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID) "
                + "     AND (acl.PROGRAM_ID IS NULL OR acl.PROGRAM_ID=p.PROGRAM_ID) "
                + "     AND (acl.HEALTH_AREA_ID IS NULL OR acl.HEALTH_AREA_ID=p.HEALTH_AREA_ID) "
                + "     AND (acl.ORGANISATION_ID IS NULL OR acl.ORGANISATION_ID=p.ORGANISATION_ID) ");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("realmId", p.getRealmCountry().getRealm().getRealmId());
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        System.out.println(sb.toString());
        System.out.println(params);
        List<String> emailList = this.namedParameterJdbcTemplate.queryForList(sb.toString(), params, String.class);
        return StringUtils.join(emailList, ",");
    }

    @Override
    public int createERPNotification(String orderNo, int primeLineNo, int shipmentId, int notificationTypeId
    ) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        String sql = "select count(*) from rm_erp_notification n where n.`ORDER_NO`=? and n.`PRIME_LINE_NO`=? and n.`SHIPMENT_ID`=?;";
        int count = this.jdbcTemplate.queryForObject(sql, Integer.class, orderNo, primeLineNo, shipmentId);
        if (count == 0) {
            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_erp_notification").usingGeneratedKeyColumns("NOTIFICATION_ID");
            params.put("ORDER_NO", orderNo);
            params.put("PRIME_LINE_NO", primeLineNo);
            params.put("NOTIFICATION_TYPE_ID", notificationTypeId);
            params.put("SHIPMENT_ID", shipmentId);
            params.put("ADDRESSED", 0);
            params.put("ACTIVE", 1);
            params.put("CREATED_BY", 1);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", 1);
            params.put("LAST_MODIFIED_DATE", curDate);
            int notificationId = si.executeAndReturnKey(params).intValue();
            System.out.println("NotificationId Id " + notificationId + " created");
            return notificationId;
        } else {
            return -1;
        }
    }

    @Override
    public List<ERPNotificationDTO> getNotificationList(ERPNotificationDTO eRPNotificationDTO
    ) {
        String sql = "";
        List<ERPNotificationDTO> list = null;
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", eRPNotificationDTO.getPlanningUnitIdsString());
        params.put("programId", eRPNotificationDTO.getProgramId());
        sql = "CALL getShipmentLinkingNotifications(:programId, :planningUnitId, -1)";
        list = this.namedParameterJdbcTemplate.query(sql, params, new ShipmentNotificationDTORowMapper());
        System.out.println("list---" + list);
        return list;
    }

    @Override
    public int updateNotification(ERPNotificationDTO eRPNotificationDTO, CustomUserDetails curUser
    ) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        System.out.println("eRPNotificationDTO---" + eRPNotificationDTO.toString());
        String sql = "";
        int id;
        if (eRPNotificationDTO.getNotificationType().getId() == 2) {
            sql = " UPDATE rm_erp_notification n "
                    + " LEFT JOIN rm_manual_tagging  m ON m.`ORDER_NO`=n.`ORDER_NO` AND m.`PRIME_LINE_NO`=n.`PRIME_LINE_NO` AND m.`SHIPMENT_ID`=n.`SHIPMENT_ID` AND m.`ACTIVE` "
                    + " SET n.`ADDRESSED`=1,n.`NOTES`=?,m.`NOTES`=?,m.`CONVERSION_FACTOR`=?,n.`LAST_MODIFIED_BY`=?,n.`LAST_MODIFIED_DATE`=?,n.`CONVERSION_FACTOR`=? "
                    + " WHERE n.`NOTIFICATION_ID`=?;";
            id = this.jdbcTemplate.update(sql, eRPNotificationDTO.getNotes(), eRPNotificationDTO.getNotes(), eRPNotificationDTO.getConversionFactor(), curUser.getUserId(), curDate, eRPNotificationDTO.getConversionFactor(), eRPNotificationDTO.getNotificationId());

            sql = " SELECT st.`SHIPMENT_TRANS_ID`,st.`RATE` FROM rm_shipment_trans st "
                    + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
                    + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? "
                    + "ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1;";

            Map<String, Object> map = this.jdbcTemplate.queryForMap(sql, eRPNotificationDTO.getParentShipmentId(), eRPNotificationDTO.getOrderNo(), eRPNotificationDTO.getPrimeLineNo());
            sql = "UPDATE rm_shipment_trans st  SET st.`SHIPMENT_QTY`=?,st.`PRODUCT_COST`=?, "
                    + "st.`LAST_MODIFIED_DATE`=?,st.`LAST_MODIFIED_BY`=?,st.`NOTES`=? "
                    + "WHERE st.`SHIPMENT_TRANS_ID`=?;";
            long convertedQty = (long) ((double) eRPNotificationDTO.getShipmentQty() * eRPNotificationDTO.getConversionFactor());

            double rate = Double.parseDouble(map.get("RATE").toString());
            double productCost = rate * (double) convertedQty;
            this.jdbcTemplate.update(sql, convertedQty, productCost, curDate, curUser.getUserId(), eRPNotificationDTO.getNotes(), (long) map.get("SHIPMENT_TRANS_ID"));
        } else {
            sql = " UPDATE rm_erp_notification n "
                    + " LEFT JOIN rm_manual_tagging  m ON m.`ORDER_NO`=n.`ORDER_NO` AND m.`PRIME_LINE_NO`=n.`PRIME_LINE_NO` AND m.`SHIPMENT_ID`=n.`SHIPMENT_ID` AND m.`ACTIVE` "
                    + " SET n.`ADDRESSED`=1,n.`NOTES`=?,n.`LAST_MODIFIED_BY`=?,n.`LAST_MODIFIED_DATE`=? "
                    + " WHERE n.`NOTIFICATION_ID`=?;";
            id = this.jdbcTemplate.update(sql, eRPNotificationDTO.getNotes(), curUser.getUserId(), curDate, eRPNotificationDTO.getNotificationId());

        }
        return id;
    }

    @Override
    public int getNotificationCount(CustomUserDetails curUser
    ) {
        String programIds = "", sql;
        List<Program> programList = this.programService.getProgramList(curUser, true);
        for (Program p : programList) {
            programIds = programIds + p.getProgramId() + ",";
        }
        programIds = programIds.substring(0, programIds.lastIndexOf(","));
        System.out.println("ids%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + programIds);
        sql = "SELECT COUNT(*) FROM ( "
                + "SELECT n.`NOTIFICATION_ID` FROM rm_erp_notification n "
                + "LEFT JOIN rm_shipment s ON s.`PARENT_SHIPMENT_ID`=n.`SHIPMENT_ID` "
                + "LEFT JOIN rm_shipment_trans st ON st.`SHIPMENT_ID`=s.`SHIPMENT_ID` AND n.`ORDER_NO`=st.`ORDER_NO` AND n.`PRIME_LINE_NO`=st.`PRIME_LINE_NO` "
                + "WHERE n.`ACTIVE` AND n.`ADDRESSED`=0 AND s.`PROGRAM_ID` IN (" + programIds + ") GROUP BY n.`NOTIFICATION_ID` ) AS a;";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<ManualTaggingDTO> getARTMISHistory(String orderNo, int primeLineNo
    ) {
        String sql = "SELECT "
                + "e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO` , "
                + "pu.`PLANNING_UNIT_ID`,pu.`LABEL_ID`,pu.`LABEL_EN`,pu.`LABEL_FR`,pu.`LABEL_PR`,pu.`LABEL_SP`, "
                + "COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE, "
                + "e.`STATUS`,e.`QTY`,e.`LAST_MODIFIED_DATE` "
                + "FROM rm_erp_order e "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + "LEFT JOIN vw_planning_unit pu ON pu.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID` "
                + "LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=e.`ERP_ORDER_ID` "
                + "WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? GROUP BY e.`ERP_ORDER_ID`;";
        return this.jdbcTemplate.query(sql, new ARTMISHistoryDTORowMapper(), orderNo, primeLineNo);
    }

}
