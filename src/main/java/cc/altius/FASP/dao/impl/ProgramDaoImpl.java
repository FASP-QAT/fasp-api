/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.PlanningUnitDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ErpBatchDTO;
import cc.altius.FASP.model.DTO.ErpOrderAutocompleteDTO;
import cc.altius.FASP.model.DTO.ErpOrderDTO;
import cc.altius.FASP.model.DTO.ErpShipmentDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.DTO.rowMapper.ERPLinkedShipmentsDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ERPNewBatchDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpBatchDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderAutocompleteDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderDTOListResultSetExtractor;
import cc.altius.FASP.model.DTO.rowMapper.ManualTaggingDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ManualTaggingOrderDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.NotERPLinkedShipmentsRowMapper;
import cc.altius.FASP.model.DTO.rowMapper.NotificationSummaryDTORowMapper;
import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.DTO.HealthAreaAndRealmCountryDTO;
import cc.altius.FASP.model.DTO.ProgramPlanningUnitProcurementAgentInput;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.SimpleObjectWithType;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.SimplePlanningUnitObject;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.report.TreeAnchorInput;
import cc.altius.FASP.model.report.TreeAnchorOutput;
import cc.altius.FASP.model.report.TreeAnchorOutputRowMapper;
import cc.altius.FASP.model.rowMapper.DatasetPlanningUnitListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.LoadProgramListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.LoadProgramResultSetExtractor;
import cc.altius.FASP.model.rowMapper.LoadVersionRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramIdAndVersionIdRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramPlanningUnitProcurementAgentPriceRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramPlanningUnitResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleObjectWithTypeRowMapper;
import cc.altius.FASP.model.rowMapper.SimplePlanningUnitObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleProgramListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.VersionRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.FASP.utils.SuggestedDisplayName;
import cc.altius.utils.DateUtils;
import cc.altius.utils.PassPhrase;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
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
    @Autowired
    private ProgramCommonDao programCommonDao;
    @Autowired
    private ProgramDataDao programDataDao;
    @Autowired
    private PlanningUnitDao planningUnitDao;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String sqlListString1 = "SELECT   "
            + "     p.PROGRAM_ID, p.`PROGRAM_CODE`, p.AIR_FREIGHT_PERC, p.SEA_FREIGHT_PERC, p.ROAD_FREIGHT_PERC, p.PLANNED_TO_SUBMITTED_LEAD_TIME, p.PROGRAM_TYPE_ID, "
            + "     cpv.VERSION_ID `CV_VERSION_ID`, cpv.NOTES `CV_VERSION_NOTES`, cpv.CREATED_DATE `CV_CREATED_DATE`, cpvcb.USER_ID `CV_CB_USER_ID`, cpvcb.USERNAME `CV_CB_USERNAME`, cpv.LAST_MODIFIED_DATE `CV_LAST_MODIFIED_DATE`, cpvlmb.USER_ID `CV_LMB_USER_ID`, cpvlmb.USERNAME `CV_LMB_USERNAME`,  "
            + "     vt.VERSION_TYPE_ID `CV_VERSION_TYPE_ID`, vt.LABEL_ID `CV_VERSION_TYPE_LABEL_ID`, vt.LABEL_EN `CV_VERSION_TYPE_LABEL_EN`, vt.LABEL_FR `CV_VERSION_TYPE_LABEL_FR`, vt.LABEL_SP `CV_VERSION_TYPE_LABEL_SP`, vt.LABEL_PR `CV_VERSION_TYPE_LABEL_PR`,  "
            + "     cpv.FORECAST_START_DATE `CV_FORECAST_START_DATE`, cpv.FORECAST_STOP_DATE `CV_FORECAST_STOP_DATE`, cpv.`DAYS_IN_MONTH`, cpv.`FREIGHT_PERC`, cpv.`FORECAST_THRESHOLD_HIGH_PERC`, cpv.`FORECAST_THRESHOLD_LOW_PERC`, "
            + "     vs.VERSION_STATUS_ID `CV_VERSION_STATUS_ID`, vs.LABEL_ID `CV_VERSION_STATUS_LABEL_ID`, vs.LABEL_EN `CV_VERSION_STATUS_LABEL_EN`, vs.LABEL_FR `CV_VERSION_STATUS_LABEL_FR`, vs.LABEL_SP `CV_VERSION_STATUS_LABEL_SP`, vs.LABEL_PR `CV_VERSION_STATUS_LABEL_PR`,  "
            + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME, p.ARRIVED_TO_DELIVERED_LEAD_TIME,  "
            + "     p.PROGRAM_NOTES, pm.USERNAME `PROGRAM_MANAGER_USERNAME`, pm.USER_ID `PROGRAM_MANAGER_USER_ID`,  "
            + "     p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_PR, p.LABEL_SP,  "
            + "     rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MAX_GAURDRAIL, r.MAX_MOS_MAX_GAURDRAIL,  r.MIN_QPL_TOLERANCE, r.MIN_QPL_TOLERANCE_CUT_OFF, r.MAX_QPL_TOLERANCE, r.ACTUAL_CONSUMPTION_MONTHS_IN_PAST, r.FORECAST_CONSUMPTION_MONTH_IN_PAST, r.INVENTORY_MONTHS_IN_PAST, r.MIN_COUNT_FOR_MODE, r.MIN_PERC_FOR_MODE, "
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
            + "     pv.FORECAST_START_DATE `VT_FORECAST_START_DATE`, pv.FORECAST_STOP_DATE `VT_FORECAST_STOP_DATE`, pv.`DAYS_IN_MONTH` `VT_DAYS_IN_MONTH`, pv.`FREIGHT_PERC` `VT_FREIGHT_PERC`, pv.`FORECAST_THRESHOLD_HIGH_PERC` `VT_FORECAST_THRESHOLD_HIGH_PERC`, pv.`FORECAST_THRESHOLD_LOW_PERC` `VT_FORECAST_THRESHOLD_LOW_PERC`, "
            + "     p.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE  ";

    private static String sqlListBasicString = "SELECT   "
            + "     p.PROGRAM_ID, p.`PROGRAM_CODE`, p.PROGRAM_TYPE_ID, "
            + "     p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_PR, p.LABEL_SP,  "
            + "     rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MAX_GAURDRAIL, r.MAX_MOS_MAX_GAURDRAIL,  r.MIN_QPL_TOLERANCE, r.MIN_QPL_TOLERANCE_CUT_OFF, r.MAX_QPL_TOLERANCE, r.ACTUAL_CONSUMPTION_MONTHS_IN_PAST, r.FORECAST_CONSUMPTION_MONTH_IN_PAST, r.INVENTORY_MONTHS_IN_PAST, r.MIN_COUNT_FOR_MODE, r.MIN_PERC_FOR_MODE, "
            + "     r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`,  "
            + "     c.COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2,   "
            + "     c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_PR `COUNTRY_LABEL_PR`, c.LABEL_SP `COUNTRY_LABEL_SP`,  "
            + "     o.ORGANISATION_ID, o.ORGANISATION_CODE,  "
            + "     o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_PR `ORGANISATION_LABEL_PR`, o.LABEL_SP `ORGANISATION_LABEL_SP`,  "
            + "     ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE,  "
            + "     ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`,  "
            + "     p.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE  ";

    private static String sqlListString2 = " LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
            + " LEFT JOIN vw_realm r ON rc.REALM_ID=r.REALM_ID  "
            + " LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  "
            + " LEFT JOIN vw_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID  "
            + " LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID  "
            + " LEFT JOIN rm_program_health_area pha ON p.PROGRAM_ID=pha.PROGRAM_ID "
            + " LEFT JOIN vw_health_area ha ON pha.HEALTH_AREA_ID=ha.HEALTH_AREA_ID  "
            + " LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID  "
            + " LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID  "
            + " LEFT JOIN vw_region re ON pr.REGION_ID=re.REGION_ID  "
            + " LEFT JOIN vw_unit u ON rc.PALLET_UNIT_ID=u.UNIT_ID  "
            + " LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID  "
            + " LEFT JOIN rm_program_version cpv ON p.PROGRAM_ID=cpv.PROGRAM_ID AND p.CURRENT_VERSION_ID=cpv.VERSION_ID "
            + " LEFT JOIN vw_version_type vt ON cpv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID  "
            + " LEFT JOIN vw_version_status vs ON cpv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID  "
            + " LEFT JOIN us_user cpvcb ON cpv.CREATED_BY=cpvcb.USER_ID  "
            + " LEFT JOIN us_user cpvlmb ON cpv.LAST_MODIFIED_BY=cpvlmb.USER_ID  "
            + " LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID AND pv.VERSION_READY "
            + " LEFT JOIN vw_version_type pvt ON pv.VERSION_TYPE_ID=pvt.VERSION_TYPE_ID  "
            + " LEFT JOIN vw_version_status pvs ON pv.VERSION_STATUS_ID=pvs.VERSION_STATUS_ID  "
            + " LEFT JOIN us_user pvcb ON pv.CREATED_BY=pvcb.USER_ID  "
            + " LEFT JOIN us_user pvlmb ON pv.LAST_MODIFIED_BY=pvlmb.USER_ID  "
            + " WHERE TRUE ";

    private static String sqlListBasicString2 = " LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
            + " LEFT JOIN vw_realm r ON rc.REALM_ID=r.REALM_ID  "
            + " LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  "
            + " LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID  "
            + " LEFT JOIN rm_program_health_area pha ON p.PROGRAM_ID=pha.PROGRAM_ID "
            + " LEFT JOIN vw_health_area ha ON pha.HEALTH_AREA_ID=ha.HEALTH_AREA_ID  "
            + " LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID  "
            + " WHERE TRUE ";
    public final static String sqlOrderBy = " ORDER BY p.PROGRAM_CODE, pv.VERSION_ID";
    public final static String sqlProgramListString = sqlListString1 + " FROM vw_program p   " + sqlListString2;
    public final static String sqlDatasetListString = sqlListString1 + " FROM vw_dataset p   " + sqlListString2;

    public final static String sqlProgramListBasicString = sqlListBasicString + " FROM vw_program p   " + sqlListBasicString2;
    public final static String sqlDatasetListBasicString = sqlListBasicString + " FROM vw_dataset p   " + sqlListBasicString2;
    public final static String sqlAllProgramListBasicString = sqlListBasicString + " FROM vw_all_program p   " + sqlListBasicString2;
    public final static String sqlOrderByBasic = " ORDER BY p.PROGRAM_CODE";

    public String sqlListStringForProgramPlanningUnit = "SELECT ppu.PROGRAM_PLANNING_UNIT_ID,   "
            + "    pg.PROGRAM_ID, pg.LABEL_ID `PROGRAM_LABEL_ID`, pg.LABEL_EN `PROGRAM_LABEL_EN`, pg.LABEL_FR `PROGRAM_LABEL_FR`, pg.LABEL_PR `PROGRAM_LABEL_PR`, pg.LABEL_SP `PROGRAM_LABEL_SP`,  "
            + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.MULTIPLIER, "
            + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, "
            + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`,  "
            + "    ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, ppu.LOCAL_PROCUREMENT_LEAD_TIME, ppu.SHELF_LIFE, ppu.CATALOG_PRICE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC,  "
            + "    ppu.PLAN_BASED_ON, ppu.MIN_QTY, ppu.DISTRIBUTION_LEAD_TIME, "
            + "    ppu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ppu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ppu.LAST_MODIFIED_DATE  "
            + " FROM  rm_program_planning_unit ppu   "
            + " LEFT JOIN vw_program pg ON pg.PROGRAM_ID=ppu.PROGRAM_ID  "
            + " LEFT JOIN rm_realm_country rc ON pg.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
            + " LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
            + " LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
            + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
            + " LEFT JOIN us_user cb ON ppu.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON ppu.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE ";

    public String sqlListStringForSimplePlanningUnit = "SELECT "
            + "    pu.PLANNING_UNIT_ID    `PU_ID`, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_PR `PU_LABEL_PR`, pu.LABEL_SP `PU_LABEL_SP`, pu.MULTIPLIER, "
            + "    puu.UNIT_ID `PU_UNIT_ID`, puu.LABEL_ID `PUU_LABEL_ID`, puu.LABEL_EN `PUU_LABEL_EN`, puu.LABEL_FR `PUU_LABEL_FR`, puu.LABEL_SP `PUU_LABEL_SP`, puu.LABEL_PR `PUU_LABEL_PR`, puu.UNIT_CODE `PU_UNIT_CODE`, "
            + "    fu.FORECASTING_UNIT_ID `FU_ID`, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
            + "    fuu.UNIT_ID `FU_UNIT_ID`, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, fuu.UNIT_CODE `FU_UNIT_CODE`, "
            + "    pc.PRODUCT_CATEGORY_ID `PC_ID`, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_PR `PC_LABEL_PR`, pc.LABEL_SP `PC_LABEL_SP`,  "
            + "    tc.TRACER_CATEGORY_ID  `TC_ID`, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_PR `TC_LABEL_PR`, tc.LABEL_SP `TC_LABEL_SP`  "
            + " FROM  rm_program_planning_unit ppu   "
            + " LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
            + " LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID  "
            + " LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
            + " LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID  "
            + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
            + " LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID  "
            + " WHERE TRUE ";

    //TODO change to include all the settings
    public String sqlListStringForProgramPlanningUnitProcurementAgentPricing = "SELECT ppu.PROGRAM_PLANNING_UNIT_ID,   "
            + "    pg.PROGRAM_ID, pg.LABEL_ID `PROGRAM_LABEL_ID`, pg.LABEL_EN `PROGRAM_LABEL_EN`, pg.LABEL_FR `PROGRAM_LABEL_FR`, pg.LABEL_PR `PROGRAM_LABEL_PR`, pg.LABEL_SP `PROGRAM_LABEL_SP`,  "
            + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,  pu.MULTIPLIER, "
            + "    pu2.PLANNING_UNIT_ID `PPUPA_PLANNING_UNIT_ID`, pu2.LABEL_ID `PPUPA_LABEL_ID`, pu2.LABEL_EN `PPUPA_LABEL_EN`, pu2.LABEL_FR `PPUPA_LABEL_FR`, pu2.LABEL_PR `PPUPA_LABEL_PR`, pu2.LABEL_SP `PPUPA_LABEL_SP`,  pu2.MULTIPLIER, "
            + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, "
            + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`,  "
            + "    ppu.REORDER_FREQUENCY_IN_MONTHS, ppu.MIN_MONTHS_OF_STOCK, ppu.LOCAL_PROCUREMENT_LEAD_TIME, ppu.SHELF_LIFE, ppu.CATALOG_PRICE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC,  "
            + "    ppu.PLAN_BASED_ON, ppu.MIN_QTY, ppu.DISTRIBUTION_LEAD_TIME, "
            + "    ppu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ppu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ppu.LAST_MODIFIED_DATE,  "
            + "    pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.PROCUREMENT_AGENT_CODE, "
            + "    ppupa.PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID, ppupa.PRICE `PROCUREMENT_AGENT_PRICE`, ppupa.`SEA_FREIGHT_PERC`, ppupa.`AIR_FREIGHT_PERC`, ppupa.`ROAD_FREIGHT_PERC`, "
            + "    ppupa.`PLANNED_TO_SUBMITTED_LEAD_TIME`, ppupa.`SUBMITTED_TO_APPROVED_LEAD_TIME`, ppupa.`APPROVED_TO_SHIPPED_LEAD_TIME`, ppupa.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`, ppupa.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`, ppupa.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`, ppupa.`ARRIVED_TO_DELIVERED_LEAD_TIME`, ppupa.`LOCAL_PROCUREMENT_LEAD_TIME`, "
            + "    ppupa.ACTIVE `PPUPA_ACTIVE`, cb2.USER_ID `PPUPA_CB_USER_ID`, cb2.USERNAME `PPUPA_CB_USERNAME`, ppupa.CREATED_DATE `PPUPA_CREATED_DATE`, lmb2.USER_ID `PPUPA_LMB_USER_ID`, lmb2.USERNAME `PPUPA_LMB_USERNAME`, ppupa.LAST_MODIFIED_DATE `PPUPA_LAST_MODIFIED_DATE`  "
            + " FROM  rm_program_planning_unit ppu   "
            + " LEFT JOIN vw_program pg ON pg.PROGRAM_ID=ppu.PROGRAM_ID  "
            + " LEFT JOIN rm_realm_country rc ON pg.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
            + " LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
            + " LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
            + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
            + " LEFT JOIN us_user cb ON ppu.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON ppu.LAST_MODIFIED_BY=lmb.USER_ID "
            + " LEFT JOIN rm_program_planning_unit_procurement_agent ppupa ON ppu.PROGRAM_ID=ppupa.PROGRAM_ID AND (ppu.PLANNING_UNIT_ID=ppupa.PLANNING_UNIT_ID OR ppupa.PLANNING_UNIT_ID is NULL) "
            + " LEFT JOIN vw_planning_unit pu2 ON ppupa.PLANNING_UNIT_ID=pu2.PLANNING_UNIT_ID  "
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
        SimpleJdbcInsert si;
        if (p.getProgramTypeId() == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            si = new SimpleJdbcInsert(dataSource).withTableName("rm_program").usingColumns("PROGRAM_TYPE_ID", "REALM_ID", "REALM_COUNTRY_ID", "ORGANISATION_ID", "PROGRAM_CODE", "LABEL_ID", "PROGRAM_MANAGER_USER_ID", "PROGRAM_NOTES", "AIR_FREIGHT_PERC", "SEA_FREIGHT_PERC", "ROAD_FREIGHT_PERC", "PLANNED_TO_SUBMITTED_LEAD_TIME", "SUBMITTED_TO_APPROVED_LEAD_TIME", "APPROVED_TO_SHIPPED_LEAD_TIME", "SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME", "SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME", "SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME", "ARRIVED_TO_DELIVERED_LEAD_TIME", "CURRENT_VERSION_ID", "ACTIVE", "CREATED_BY", "CREATED_DATE", "LAST_MODIFIED_BY", "LAST_MODIFIED_DATE").usingGeneratedKeyColumns("PROGRAM_ID");
        } else {
            si = new SimpleJdbcInsert(dataSource).withTableName("rm_program").usingColumns("PROGRAM_TYPE_ID", "REALM_ID", "REALM_COUNTRY_ID", "ORGANISATION_ID", "PROGRAM_CODE", "LABEL_ID", "PROGRAM_MANAGER_USER_ID", "PROGRAM_NOTES", "CURRENT_VERSION_ID", "ACTIVE", "CREATED_BY", "CREATED_DATE", "LAST_MODIFIED_BY", "LAST_MODIFIED_DATE").usingGeneratedKeyColumns("PROGRAM_ID");
        }
        params.put("REALM_ID", realmId);
        params.put("REALM_COUNTRY_ID", p.getRealmCountry().getRealmCountryId());
        params.put("ORGANISATION_ID", p.getOrganisation().getId());
        params.put("PROGRAM_CODE", p.getProgramCode());
        params.put("LABEL_ID", labelId);
        params.put("PROGRAM_MANAGER_USER_ID", p.getProgramManager().getUserId());
        params.put("PROGRAM_NOTES", p.getProgramNotes());
        if (p.getProgramTypeId() == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            params.put("AIR_FREIGHT_PERC", p.getAirFreightPerc());
            params.put("SEA_FREIGHT_PERC", p.getSeaFreightPerc());
            params.put("ROAD_FREIGHT_PERC", p.getRoadFreightPerc());
            params.put("PLANNED_TO_SUBMITTED_LEAD_TIME", p.getPlannedToSubmittedLeadTime());
            params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
            params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
            params.put("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME", p.getShippedToArrivedBySeaLeadTime());
            params.put("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME", p.getShippedToArrivedByAirLeadTime());
            params.put("SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME", p.getShippedToArrivedByRoadLeadTime());
            params.put("ARRIVED_TO_DELIVERED_LEAD_TIME", p.getArrivedToDeliveredLeadTime());
        }
        params.put("PROGRAM_TYPE_ID", p.getProgramTypeId());
        params.put("CURRENT_VERSION_ID", null);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int programId = si.executeAndReturnKey(params).intValue();
        si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_health_area");
        SqlParameterSource[] paramList = new SqlParameterSource[p.getHealthAreaIdList().size()];
        int i = 0;
        for (SimpleCodeObject ha : p.getHealthAreaList()) {
            params = new HashMap<>();
            params.put("HEALTH_AREA_ID", ha.getId());
            params.put("PROGRAM_ID", programId);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        paramList = null;
        si = null;
        si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_region");
        paramList = new SqlParameterSource[p.getRegionArray().length];
        i = 0;
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
        params.put("notes", p.getProgramNotes());
        params.put("forecastStartDate", (p.getCurrentVersion() == null ? null : p.getCurrentVersion().getForecastStartDate()));
        params.put("forecastStopDate", (p.getCurrentVersion() == null ? null : p.getCurrentVersion().getForecastStopDate()));
        this.namedParameterJdbcTemplate.queryForObject("CALL getVersionId(:programId,:versionTypeId,:versionStatusId,:notes,:forecastStartDate,:forecastStopDate,null,null,null,null,:curUser,:curDate,1)", params, new VersionRowMapper());
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
        if (p.getProgramTypeId() == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            params.put("airFreightPerc", p.getAirFreightPerc());
            params.put("seaFreightPerc", p.getSeaFreightPerc());
            params.put("roadFreightPerc", p.getRoadFreightPerc());
            params.put("plannedToSubmittedLeadTime", p.getPlannedToSubmittedLeadTime());
            params.put("submittedToApprovedLeadTime", p.getSubmittedToApprovedLeadTime());
            params.put("approvedToShippedLeadTime", p.getApprovedToShippedLeadTime());
            params.put("shippedToArrivedBySeaLeadTime", p.getShippedToArrivedBySeaLeadTime());
            params.put("shippedToArrivedByAirLeadTime", p.getShippedToArrivedByAirLeadTime());
            params.put("shippedToArrivedByRoadLeadTime", p.getShippedToArrivedByRoadLeadTime());
            params.put("arrivedToDeliveredLeadTime", p.getArrivedToDeliveredLeadTime());
        }
        params.put("active", p.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        String sqlString = "UPDATE rm_program p "
                + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
                + "SET "
                + "p.PROGRAM_MANAGER_USER_ID=:programManagerUserId, "
                + "p.PROGRAM_NOTES=:programNotes,"
                + "p.PROGRAM_CODE=:programCode, "
                + "p.ORGANISATION_ID=:organisationId, ";
        if (p.getProgramTypeId() == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sqlString
                    += "p.AIR_FREIGHT_PERC=:airFreightPerc, "
                    + "p.SEA_FREIGHT_PERC=:seaFreightPerc, "
                    + "p.ROAD_FREIGHT_PERC=:roadFreightPerc, "
                    + "p.PLANNED_TO_SUBMITTED_LEAD_TIME=:plannedToSubmittedLeadTime, "
                    + "p.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
                    + "p.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
                    + "p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=:shippedToArrivedBySeaLeadTime, "
                    + "p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=:shippedToArrivedByAirLeadTime, "
                    + "p.SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME=:shippedToArrivedByRoadLeadTime, "
                    + "p.ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime, ";
        }
        sqlString
                += "p.ACTIVE=:active,"
                + "p.LAST_MODIFIED_BY=:curUser, "
                + "p.LAST_MODIFIED_DATE=:curDate, "
                + "pl.LABEL_EN=:labelEn, "
                + "pl.LAST_MODIFIED_BY=:curUser, "
                + "pl.LAST_MODIFIED_DATE=:curDate "
                + "WHERE p.PROGRAM_ID=:programId";
        int rows = this.namedParameterJdbcTemplate.update(sqlString, params);
        params.clear();
        params.put("programId", p.getProgramId());
        this.namedParameterJdbcTemplate.update("DELETE FROM rm_program_health_area WHERE PROGRAM_ID=:programId", params);
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_health_area");
        SqlParameterSource[] paramList = new SqlParameterSource[p.getHealthAreaList().size()];
        int i = 0;
        for (SimpleCodeObject ha : p.getHealthAreaList()) {
            params = new HashMap<>();
            params.put("PROGRAM_ID", p.getProgramId());
            params.put("HEALTH_AREA_ID", ha.getId());
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        si = null;
        paramList = null;
        params.clear();
        params.put("programId", p.getProgramId());
        this.namedParameterJdbcTemplate.update("DELETE FROM rm_program_region WHERE PROGRAM_ID=:programId", params);
        si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_region");
        paramList = new SqlParameterSource[p.getRegionArray().length];
        i = 0;
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
    public List<SimpleProgram> getProgramListForDropdown(int realmId, int programTypeId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(ProgramCommonDaoImpl.sqlSimpleProgramString).append(" AND (rc.REALM_ID=:realmId OR :realmId=-1) AND (p.PROGRAM_TYPE_ID=:programTypeId OR :programTypeId=0) AND p.ACTIVE ");
        params.put("realmId", realmId);
        params.put("programTypeId", programTypeId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" ORDER BY p.PROGRAM_TYPE_ID, p.PROGRAM_CODE ");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleProgramListResultSetExtractor());
    }

    @Override
    public List<SimpleProgram> getProgramWithFilterForHealthAreaAndRealmCountryListForDropdown(int realmId, int programTypeId, HealthAreaAndRealmCountryDTO input, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(ProgramCommonDaoImpl.sqlSimpleProgramString).append(" AND rc.REALM_ID=:realmId AND (p.PROGRAM_TYPE_ID=:programTypeId OR :programTypeId=0) AND p.ACTIVE ");
        params.put("realmId", realmId);
        params.put("programTypeId", programTypeId);
        if (input.getHealthAreaId() != null) {
            sqlStringBuilder.append(" AND FIND_IN_SET(:healthAreaId, p.HEALTH_AREA_ID) ");
            params.put("healthAreaId", input.getHealthAreaId());
        }
        if (input.getRealmCountryId() != null) {
            sqlStringBuilder.append(" AND p.REALM_COUNTRY_ID=:realmCountryId ");
            params.put("realmCountryId", input.getRealmCountryId());
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" ORDER BY p.PROGRAM_TYPE_ID, p.PROGRAM_CODE ");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleProgramListResultSetExtractor());
    }

    @Override
    public List<SimpleProgram> getProgramWithFilterForMultipleRealmCountryListForDropdown(int programTypeId, String realmCountryIdsStr, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(ProgramCommonDaoImpl.sqlSimpleProgramString).append(" AND (p.PROGRAM_TYPE_ID=:programTypeId OR :programTypeId=0) AND p.ACTIVE ");
        params.put("programTypeId", programTypeId);
        if (realmCountryIdsStr.length() > 0) {
            sqlStringBuilder.append(" AND FIND_IN_SET(p.REALM_COUNTRY_ID, :realmCountryIds) ");
            params.put("realmCountryIds", realmCountryIdsStr);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" ORDER BY p.PROGRAM_TYPE_ID, p.PROGRAM_CODE ");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleProgramListResultSetExtractor());
    }

    @Override
    public List<Program> getProgramListForProgramIds(String[] programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlProgramListString);
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
    public List<Program> getProgramList(int programTypeId, CustomUserDetails curUser, boolean active) {
        StringBuilder sqlStringBuilder;
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sqlStringBuilder = new StringBuilder(this.sqlProgramListString);
        } else {
            sqlStringBuilder = new StringBuilder(this.sqlDatasetListString);
        }
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        if (active) {
            sqlStringBuilder.append(" AND p.ACTIVE ").append(this.sqlOrderBy);
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramListResultSetExtractor());
    }

    @Override
    public List<Program> getProgramListForRealmId(int realmId, int programTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder;
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sqlStringBuilder = new StringBuilder(this.sqlProgramListString);
        } else {
            sqlStringBuilder = new StringBuilder(this.sqlDatasetListString);
        }
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramListResultSetExtractor());
    }

//  Moved to ProgramCommonDaoImpl
//  public Program getProgramById(int programId, CustomUserDetails curUser)
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
    public List<ProgramPlanningUnit> getPlanningUnitListForProgramIdAndTracerCategoryIds(int programId, boolean active, String[] tracerCategoryIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForProgramPlanningUnit).append(" AND pg.PROGRAM_ID=:programId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        if (tracerCategoryIds.length > 0) {
            sqlStringBuilder.append(" AND find_in_set(fu.TRACER_CATEGORY_ID,:tracerCategoryIds)");
            params.put("tracerCategoryIds", String.join(",", tracerCategoryIds));
        }
        if (active) {
            sqlStringBuilder = sqlStringBuilder.append(" AND ppu.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramPlanningUnitRowMapper());
    }

    @Override
    public List<SimplePlanningUnitObject> getSimplePlanningUnitListForProgramIdAndTracerCategoryIds(int programId, boolean active, String[] tracerCategoryIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForSimplePlanningUnit).append(" AND ppu.PROGRAM_ID=:programId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        if (tracerCategoryIds.length > 0) {
            sqlStringBuilder.append(" AND find_in_set(fu.TRACER_CATEGORY_ID,:tracerCategoryIds)");
            params.put("tracerCategoryIds", String.join(",", tracerCategoryIds));
        }
        if (active) {
            sqlStringBuilder = sqlStringBuilder.append(" AND ppu.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimplePlanningUnitObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getPlanningUnitListForProgramIds(String programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM vw_program p LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID WHERE p.PROGRAM_ID IN (" + programIds + ") AND ppu.PROGRAM_ID IS NOT NULL AND ppu.ACTIVE AND pu.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", programIds);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY pu.PLANNING_UNIT_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleObjectRowMapper());
    }
    
    @Override
    public List<SimpleObjectWithType> getProgramAndPlanningUnitListForProgramIds(String programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT p.PROGRAM_ID `TYPE_ID`, pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR FROM vw_program p LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID WHERE p.PROGRAM_ID IN (" + programIds + ") AND ppu.PROGRAM_ID IS NOT NULL");
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", programIds);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY p.PROGRAM_ID, pu.PLANNING_UNIT_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleObjectWithTypeRowMapper());
    }

    @Override
    @Transactional
    public int saveProgramPlanningUnit(ProgramPlanningUnit[] programPlanningUnits, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_planning_unit").usingColumns("PLANNING_UNIT_ID", "PROGRAM_ID", "REORDER_FREQUENCY_IN_MONTHS", "MIN_MONTHS_OF_STOCK", "LOCAL_PROCUREMENT_LEAD_TIME", "SHELF_LIFE", "CATALOG_PRICE", "MONTHS_IN_PAST_FOR_AMC", "MONTHS_IN_FUTURE_FOR_AMC", "PLAN_BASED_ON", "MIN_QTY", "DISTRIBUTION_LEAD_TIME", "CREATED_DATE", "CREATED_BY", "LAST_MODIFIED_DATE", "LAST_MODIFIED_BY", "ACTIVE");
        SimpleJdbcInsert rcpuSi = new SimpleJdbcInsert(dataSource).withTableName("rm_realm_country_planning_unit");
        List<SqlParameterSource> updateList = new ArrayList<>();
        List<Integer> programIds = new ArrayList<>();
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
                params.put("PLAN_BASED_ON", ppu.getPlanBasedOn());
                params.put("MIN_QTY", ppu.getMinQty());
                params.put("DISTRIBUTION_LEAD_TIME", ppu.getDistributionLeadTime());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                // insert the ProgramPlanningUnit
                si.execute(params);
                rowsEffected++;
                SimpleProgram p = this.programCommonDao.getSimpleProgramById(ppu.getProgram().getId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                String sql = "SELECT count(*) ACTIVE_COUNT FROM rm_realm_country_planning_unit rcpu WHERE rcpu.REALM_COUNTRY_ID=:realmCountryId AND rcpu.PLANNING_UNIT_ID=:planningUnitId AND rcpu.MULTIPLIER=:multiplier AND rcpu.ACTIVE";
                params.clear();
                params.put("realmCountryId", p.getRealmCountry().getId());
                params.put("planningUnitId", ppu.getPlanningUnit().getId());
                params.put("multiplier", 1);
                int activeCount = this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
                if (activeCount == 0) {
                    PlanningUnit pu = this.planningUnitDao.getPlanningUnitById(ppu.getPlanningUnit().getId(), curUser);
                    String skuCode = new StringBuilder(ppu.getProgram().getIdString()).append("-").append(SuggestedDisplayName.getAlphaNumericString(pu.getLabel().getLabel_en(), SuggestedDisplayName.REALM_COUNTRY_PLANNING_UNIT_LENGTH)).append("_").append(PassPhrase.getPassword(4).toUpperCase()).toString();
                    int rcpuLabelId = this.labelDao.addLabel(pu.getLabel(), LabelConstants.RM_REALM_COUNTRY_PLANNING_UNIT, curUser.getUserId());
                    params.clear();
                    params.put("PLANNING_UNIT_ID", ppu.getPlanningUnit().getId());
                    params.put("REALM_COUNTRY_ID", p.getRealmCountry().getId());
                    params.put("LABEL_ID", rcpuLabelId);
                    params.put("SKU_CODE", skuCode);
                    params.put("UNIT_ID", pu.getUnit().getId());
                    params.put("MULTIPLIER", 1);
                    params.put("CREATED_DATE", curDate);
                    params.put("CREATED_BY", curUser.getUserId());
                    params.put("LAST_MODIFIED_DATE", curDate);
                    params.put("LAST_MODIFIED_BY", curUser.getUserId());
                    params.put("ACTIVE", true);
                    rcpuSi.execute(params);
                }
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
                params.put("planBasedOn", ppu.getPlanBasedOn());
                params.put("minQty", ppu.getMinQty());
                params.put("distributionLeadTime", ppu.getDistributionLeadTime());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", ppu.isActive());
                updateList.add(new MapSqlParameterSource(params));
                if (programIds.indexOf(ppu.getProgram().getId()) == -1) {
                    programIds.add(ppu.getProgram().getId());
                }
            }
        }
//        if (insertList.size() > 0) {
//            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
//            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
//        }
        if (updateList.size() > 0) {
            SqlParameterSource[] updateParams = new SqlParameterSource[updateList.size()];
            String sqlString = "UPDATE rm_program_planning_unit ppu SET ppu.MIN_MONTHS_OF_STOCK=:minMonthsOfStock,ppu.REORDER_FREQUENCY_IN_MONTHS=:reorderFrequencyInMonths, ppu.LOCAL_PROCUREMENT_LEAD_TIME=:localProcurementLeadTime, ppu.SHELF_LIFE=:shelfLife, ppu.CATALOG_PRICE=:catalogPrice, ppu.MONTHS_IN_PAST_FOR_AMC=:monthsInPastForAmc, ppu.MONTHS_IN_FUTURE_FOR_AMC=:monthsInFutureForAmc, ppu.PLAN_BASED_ON=:planBasedOn, ppu.MIN_QTY=:minQty, ppu.DISTRIBUTION_LEAD_TIME=:distributionLeadTime, ppu.ACTIVE=:active, ppu.LAST_MODIFIED_DATE=:curDate, ppu.LAST_MODIFIED_BY=:curUser WHERE ppu.PROGRAM_PLANNING_UNIT_ID=:programPlanningUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        for (Integer pId : programIds) {
            CommitRequest s = new CommitRequest();
            SimpleCodeObject program = new SimpleCodeObject();
            program.setId(pId);//
            s.setProgram(program);
            s.setCommittedVersionId(-1);
            s.setSaveData(false);
            s.setNotes("Supply Plan Rebuild After program planning unit data modified");
            this.programDataDao.addSupplyPlanCommitRequest(s, curUser);
        }
        return rowsEffected;
    }

    @Override
    public List<ProgramPlanningUnitProcurementAgentPrice> getProgramPlanningUnitProcurementAgentList(ProgramPlanningUnitProcurementAgentInput ppupa, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT "
                + "    ppupa.PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID, "
                + "    p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
                + "    pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, "
                + "    ppupa.PRICE `PROGRAM_PRICE`, ppupa.`SEA_FREIGHT_PERC`, ppupa.`AIR_FREIGHT_PERC`, ppupa.`ROAD_FREIGHT_PERC`, "
                + "    ppupa.`PLANNED_TO_SUBMITTED_LEAD_TIME`, ppupa.`SUBMITTED_TO_APPROVED_LEAD_TIME`, ppupa.`APPROVED_TO_SHIPPED_LEAD_TIME`, ppupa.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`, ppupa.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`, ppupa.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`, ppupa.`ARRIVED_TO_DELIVERED_LEAD_TIME`, ppupa.`LOCAL_PROCUREMENT_LEAD_TIME`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ppupa.ACTIVE `ACTIVE`, ppupa.CREATED_DATE `CREATED_DATE`, ppupa.LAST_MODIFIED_DATE `LAST_MODIFIED_DATE` "
                + "FROM rm_program_planning_unit_procurement_agent ppupa "
                + "LEFT JOIN vw_planning_unit pu ON ppupa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_procurement_agent pa ON pa.PROCUREMENT_AGENT_ID=ppupa.PROCUREMENT_AGENT_ID "
                + "LEFT JOIN vw_program p ON ppupa.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN us_user cb ON ppupa.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ppupa.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE "
                + "ppupa.PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID IS NOT NULL "
                + "AND (LENGTH(:programIdList)=0 OR FIND_IN_SET(ppupa.PROGRAM_ID, :programIdList)) "
                + "AND (LENGTH(:planningUnitIdList)=0 OR FIND_IN_SET(ppupa.PLANNING_UNIT_ID, :planningUnitIdList) OR ppupa.PLANNING_UNIT_ID IS NULL) ");
        Map<String, Object> params = new HashMap<>();
        params.put("programIdList", ArrayUtils.convertListToString(ppupa.getProgramIdList()));
        params.put("planningUnitIdList", ArrayUtils.convertListToString(ppupa.getPlanningUnitIdList()));
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        if (active) {
            sqlStringBuilder.append(" AND ppupa.ACTIVE");
        }
        sqlStringBuilder.append(" ORDER BY p.LABEL_EN, pu.LABEL_EN, pa.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramPlanningUnitProcurementAgentPriceRowMapper());
    }

    @Override
    public int saveProgramPlanningUnitProcurementAgentPrice(ProgramPlanningUnitProcurementAgentPrice[] programPlanningUnitProcurementAgentPrices, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_planning_unit_procurement_agent").usingColumns("PROGRAM_ID","PLANNING_UNIT_ID","PROCUREMENT_AGENT_ID","PRICE","SEA_FREIGHT_PERC","AIR_FREIGHT_PERC","ROAD_FREIGHT_PERC","PLANNED_TO_SUBMITTED_LEAD_TIME","SUBMITTED_TO_APPROVED_LEAD_TIME","APPROVED_TO_SHIPPED_LEAD_TIME","SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME","SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME","SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME","ARRIVED_TO_DELIVERED_LEAD_TIME","LOCAL_PROCUREMENT_LEAD_TIME","CREATED_DATE","CREATED_BY","LAST_MODIFIED_DATE","LAST_MODIFIED_BY","ACTIVE");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (ProgramPlanningUnitProcurementAgentPrice ppupa : programPlanningUnitProcurementAgentPrices) {
            if (ppupa.getProgramPlanningUnitProcurementAgentId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("PROGRAM_ID", ppupa.getProgram().getId());
                params.put("PLANNING_UNIT_ID", (ppupa.getPlanningUnit().getId() == -1 ? null : ppupa.getPlanningUnit().getId()));
                params.put("PROCUREMENT_AGENT_ID", ppupa.getProcurementAgent().getId());
                params.put("PRICE", ppupa.getPrice());
                params.put("SEA_FREIGHT_PERC", ppupa.getSeaFreightPerc());
                params.put("AIR_FREIGHT_PERC", ppupa.getAirFreightPerc());
                params.put("ROAD_FREIGHT_PERC", ppupa.getRoadFreightPerc());
                params.put("PLANNED_TO_SUBMITTED_LEAD_TIME", ppupa.getPlannedToSubmittedLeadTime());
                params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", ppupa.getSubmittedToApprovedLeadTime());
                params.put("APPROVED_TO_SHIPPED_LEAD_TIME", ppupa.getApprovedToShippedLeadTime());
                params.put("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME", ppupa.getShippedToArrivedByAirLeadTime());
                params.put("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME", ppupa.getShippedToArrivedBySeaLeadTime());
                params.put("SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME", ppupa.getShippedToArrivedByRoadLeadTime());
                params.put("ARRIVED_TO_DELIVERED_LEAD_TIME", ppupa.getArrivedToDeliveredLeadTime());
                params.put("LOCAL_PROCUREMENT_LEAD_TIME", ppupa.getLocalProcurementLeadTime());
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
                params.put("planningUnitId", (ppupa.getPlanningUnit().getId() == -1 ? null : ppupa.getPlanningUnit().getId()));
                params.put("price", ppupa.getPrice());
                params.put("seaFreightPerc", ppupa.getSeaFreightPerc());
                params.put("airFreightPerc", ppupa.getAirFreightPerc());
                params.put("roadFreightPerc", ppupa.getRoadFreightPerc());
                params.put("plannedToSubmittedLeadTime", ppupa.getPlannedToSubmittedLeadTime());
                params.put("submittedToApprovedLeadTime", ppupa.getSubmittedToApprovedLeadTime());
                params.put("approvedToShippedLeadTime", ppupa.getApprovedToShippedLeadTime());
                params.put("shippedToArrivedByAirLeadTime", ppupa.getShippedToArrivedByAirLeadTime());
                params.put("shippedToArrivedBySeaLeadTime", ppupa.getShippedToArrivedBySeaLeadTime());
                params.put("shippedToArrivedByRoadLeadTime", ppupa.getShippedToArrivedByRoadLeadTime());
                params.put("arrivedToDeliveredLeadTime", ppupa.getArrivedToDeliveredLeadTime());
                params.put("localProcurementLeadTime", ppupa.getLocalProcurementLeadTime());
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
                    + "ppupa.PLANNING_UNIT_ID=:planningUnitId, "
                    + "ppupa.PRICE=:price, "
                    + "ppupa.SEA_FREIGHT_PERC=:seaFreightPerc, "
                    + "ppupa.AIR_FREIGHT_PERC=:airFreightPerc, "
                    + "ppupa.ROAD_FREIGHT_PERC=:roadFreightPerc, "
                    + "ppupa.PLANNED_TO_SUBMITTED_LEAD_TIME=:plannedToSubmittedLeadTime, "
                    + "ppupa.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
                    + "ppupa.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
                    + "ppupa.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=:shippedToArrivedByAirLeadTime, "
                    + "ppupa.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=:shippedToArrivedBySeaLeadTime, "
                    + "ppupa.SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME=:shippedToArrivedByRoadLeadTime, "
                    + "ppupa.ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime, "
                    + "ppupa.LOCAL_PROCUREMENT_LEAD_TIME=:localProcurementLeadTime, "
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
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlProgramListString).append(" AND p.LAST_MODIFIED_DATE>:lastSyncDate ");
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
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlProgramListString);
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
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", manualTaggingDTO.getPlanningUnitIdsString());
        params.put("curDate", curDate);
        logger.info("ERP Linking : going to get tab wise list---");
        logger.info("ERP Linking : linking type ---" + manualTaggingDTO.getLinkingType());
        if (manualTaggingDTO.getLinkingType() == 1) {
            params.put("programId", manualTaggingDTO.getProgramId());
            sql = "CALL getShipmentListForManualLinking(:programId, :planningUnitId, -1)";
            list = this.namedParameterJdbcTemplate.query(sql, params, new ManualTaggingDTORowMapper());
            logger.info("ERP Linking : tab 1 params ---" + params);
            logger.info("ERP Linking : tab 1 list ---" + list);
        } else if (manualTaggingDTO.getLinkingType() == 2) {
            params.put("programId", manualTaggingDTO.getProgramId());
            sql = "CALL getShipmentListForAlreadyLinkedShipments(:programId, :planningUnitId, -1)";
            list = this.namedParameterJdbcTemplate.query(sql, params, new ERPLinkedShipmentsDTORowMapper());
            logger.info("ERP Linking : tab 2 list ---" + list);
        } else {
            sql = "CALL getErpShipmentForNotLinked(:countryId,:productcategoryId, :planningUnitId, :realmId, :curDate)";
            params.put("productcategoryId", manualTaggingDTO.getProductCategoryIdsString());
            params.put("countryId", manualTaggingDTO.getCountryId());
            params.put("realmId", curUser.getRealm().getRealmId());
            list = this.namedParameterJdbcTemplate.query(sql, params, new NotERPLinkedShipmentsRowMapper());
            logger.info("ERP Linking : tab 3 list ---" + list);
        }
        return list;
    }

    @Override
    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType) {
        logger.info("ERP Linking : get order details for not linked ERP shipments ---");
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", planningUnitId);
        params.put("roNoOrderNo", roNoOrderNo);
        sql.append(" SELECT sst.`ERP_ORDER_ID`,sst.`RO_NO`,sst.`RO_PRIME_LINE_NO`,sst.`ORDER_NO`,sst.`PRIME_LINE_NO`, "
                + " sst.`QTY`,sst.`STATUS`,sst.PLANNING_UNIT_LABEL_ID,sst.PLANNING_UNIT_LABEL_EN,sst.PLANNING_UNIT_ID, "
                + " sst.PLANNING_UNIT_LABEL_FR,sst.PLANNING_UNIT_LABEL_PR,sst.PLANNING_UNIT_LABEL_SP,sst.EXPECTED_DELIVERY_DATE,sst.SKU_CODE FROM  "
                + " (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`, "
                + " e.`QTY`,e.`STATUS`,l.`LABEL_ID` AS PLANNING_UNIT_LABEL_ID,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS PLANNING_UNIT_LABEL_EN,p.PLANNING_UNIT_ID, "
                + " l.`LABEL_FR` AS PLANNING_UNIT_LABEL_FR,l.`LABEL_PR` PLANNING_UNIT_LABEL_PR,l.`LABEL_SP` AS PLANNING_UNIT_LABEL_SP,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE,pu.SKU_CODE FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ORDER_NO`=e.`ORDER_NO` AND es.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` AND es.file_name=( "
                + " SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=e.`ORDER_NO` AND s.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` "
                + " ) "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.STATUS "
                + " WHERE e.`ERP_ORDER_ID` IN (SELECT a.`ERP_ORDER_ID` FROM (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID,sm.SHIPMENT_STATUS_MAPPING_ID "
                + " FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID`"
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + " WHERE (mt.SHIPMENT_ID IS NULL  OR mt.ACTIVE=0) ");
        if (planningUnitId != 0) {
            sql.append(" AND pu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        if (roNoOrderNo != null && roNoOrderNo != "" && !roNoOrderNo.equals("0")) {
            sql.append(" AND e.RO_NO=:roNoOrderNo ");
        }
        sql.append(" GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`)  a  "
                + "  ) AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15) AND e.`CHANGE_CODE` !=2 "
                + " GROUP BY e.`ERP_ORDER_ID`) sst "
                + " LEFT JOIN rm_shipment_status_mapping sms ON sms.`EXTERNAL_STATUS_STAGE`=sst.`STATUS` "
                + " WHERE IF(sst.EXPECTED_DELIVERY_DATE < CURDATE() - INTERVAL 6 MONTH, sms.SHIPMENT_STATUS_MAPPING_ID!=2 , sms.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) ");
        logger.info("ERP Linking : get order details for not linked ERP shipments params ---");
        List<ManualTaggingDTO> list = this.namedParameterJdbcTemplate.query(sql.toString(), params, new NotERPLinkedShipmentsRowMapper());
        logger.info("ERP Linking : get order details for not linked ERP shipments list ---" + list);
        return list;
    }

    @Override
    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int programId, int planningUnitId, int linkingType, int parentShipmentId) {
        logger.info("ERP Linking : get order details for not linked & linked QAT shipments ---");
        String reason = "";
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        params.put("roNoOrderNo", roNoOrderNo);
        sql.append("  SELECT sst.`ERP_ORDER_ID`,sst.`RO_NO`,sst.`RO_PRIME_LINE_NO`,sst.`ORDER_NO`,sst.`PRIME_LINE_NO`,sst.`SHIPMENT_ID`,sst.`PLANNING_UNIT_SKU_CODE`,sst.`PROCUREMENT_UNIT_SKU_CODE`,sst.`ORDER_TYPE`,sst.`QTY`,sst.`SUPPLIER_NAME`,sst.`PRICE`,sst.`SHIPPING_COST`,sst.`RECPIENT_COUNTRY`,sst.`STATUS`,sst.`LABEL_ID`,sst.LABEL_EN,sst.`LABEL_FR`,sst.`LABEL_PR`,sst.`LABEL_SP`,sst.CURRENT_ESTIMATED_DELIVERY_DATE,sst.ACTIVE,sst.`NOTES`,sst.`CONVERSION_FACTOR` "
                + "FROM ( ");
        sql.append(" (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`,e.`SHIPMENT_ID`,e.`PLANNING_UNIT_SKU_CODE`,e.`PROCUREMENT_UNIT_SKU_CODE`,e.`ORDER_TYPE`,e.`QTY`,e.`SUPPLIER_NAME`,e.`PRICE`,e.`SHIPPING_COST`,e.`RECPIENT_COUNTRY`,e.`STATUS`,l.`LABEL_ID`,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS LABEL_EN,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS CURRENT_ESTIMATED_DELIVERY_DATE,IFNULL(mt.ACTIVE,0) AS ACTIVE,mt.`NOTES`,mt.`CONVERSION_FACTOR` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ORDER_NO`=e.`ORDER_NO` AND es.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` AND es.file_name=( "
                + " SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=e.`ORDER_NO` AND s.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` "
                + " ) ");
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
                + " LEFT JOIN rm_program pm ON pm.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID AND pm.PROGRAM_TYPE_ID=" + GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN + " "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` ");
        if (linkingType == 1) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }
        sql.append(" WHERE pm.`PROGRAM_ID`=:programId ");
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
                + " ) AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15) AND e.`CHANGE_CODE` !=2 ");
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
                + " LEFT JOIN rm_erp_shipment es ON es.`ORDER_NO`=e.`ORDER_NO` AND es.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` AND es.file_name=( "
                + " SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=e.`ORDER_NO` AND s.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` "
                + " ) ");
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
                + " LEFT JOIN rm_program pm ON pm.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID AND pm.PROGRAM_TYPE_ID=" + GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN + " "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` ");
        if (linkingType == 1) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }
        sql.append(" WHERE pm.`PROGRAM_ID`=:programId ");
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
                + " ) AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15) AND e.`CHANGE_CODE` !=2 ");
        if (linkingType == 2) {
            params.put("parentShipmentId", parentShipmentId);
            sql.append(" AND (mt.SHIPMENT_ID=:parentShipmentId OR mt.SHIPMENT_ID IS NULL) ");
        }
        sql.append(" GROUP BY e.`ERP_ORDER_ID`) ");
        sql.append("  ) sst "
                + " LEFT JOIN rm_shipment_status_mapping sms ON sms.`EXTERNAL_STATUS_STAGE`=sst.`STATUS` "
                + " WHERE IF((sst.CURRENT_ESTIMATED_DELIVERY_DATE < CURDATE() - INTERVAL 6 MONTH) && sst.ACTIVE=0, sms.SHIPMENT_STATUS_MAPPING_ID!=2 , sms.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) "
                + " ORDER BY sst.CURRENT_ESTIMATED_DELIVERY_DATE DESC ");
//        System.out.println("params----" + params);
//        System.out.println("query******************************" + sql.toString());
        logger.info("ERP Linking : get order details for not linked & linked QAT shipments params ---" + params);
        List<ManualTaggingOrderDTO> list = this.namedParameterJdbcTemplate.query(sql.toString(), params, new ManualTaggingOrderDTORowMapper());
        logger.info("ERP Linking : get order details for not linked & linked QAT shipments list ---" + list);
        return list;

    }

    @Override
    public int checkIfOrderNoAlreadyTagged(String orderNo, int primeLineNo) {
        int count = 0;
        logger.info("ERP Linking : Going to check manual tagging count ---");
        logger.info("ERP Linking : Going to check manual tagging order no ---" + orderNo);
        logger.info("ERP Linking : Going to check manual tagging prime line no ---" + primeLineNo);
        String sql = "SELECT COUNT(*) FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
        count = this.jdbcTemplate.queryForObject(sql, Integer.class,
                orderNo, primeLineNo);

        logger.info("ERP Linking : manual tagging count---" + count);
        return count;
    }

    @Override
    public int updateERPLinking(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        //Update conversion factor and notes
        logger.info("ERP Linking : Going to update conversion factor and notes");
        logger.info("ERP Linking : manual tagging object---" + manualTaggingOrderDTO);
        String sql = " SELECT st.`SHIPMENT_TRANS_ID`,st.`RATE` FROM rm_shipment_trans st "
                + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
                + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE` "
                + "ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1;";

        Map<String, Object> map = this.jdbcTemplate.queryForMap(sql, manualTaggingOrderDTO.getParentShipmentId(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
        logger.info("ERP Linking : Get shipment trans info---" + map);

        sql = "UPDATE `rm_manual_tagging` m SET m.`CONVERSION_FACTOR`=?,m.`NOTES`=? "
                + " WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE` AND m.`SHIPMENT_ID`=?; ";
        int rowsUpdated = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getConversionFactor(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), manualTaggingOrderDTO.getParentShipmentId());
        logger.info("ERP Linking : updated conversion factor and notes rows---" + rowsUpdated);

//            System.out.println("conversion factor---" + manualTaggingOrderDTO.getConversionFactor());
//            long convertedQty = ((new BigDecimal(manualTaggingOrderDTO.getQuantity())).multiply(manualTaggingOrderDTO.getConversionFactor())).longValueExact();
        long convertedQty = (long) Math.round((double) manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor());
        logger.info("ERP Linking : convertedQty---" + convertedQty);
        logger.info("ERP Linking : rate---" + map.get("RATE"));

//        double rate = Double.parseDouble(map.get("RATE").toString());
        sql = "SELECT e.`PRICE` FROM rm_erp_order e WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? "
                + " ORDER BY e.`ERP_ORDER_ID` DESC LIMIT 1;";
        double rate = this.jdbcTemplate.queryForObject(sql, Double.class,
                manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
        logger.info("ERP Linking : calculated rate---" + rate);
        double finalRate = (rate / manualTaggingOrderDTO.getConversionFactor());
        logger.info("ERP Linking : calculated final rate---" + finalRate);
        double productCost = finalRate * (double) convertedQty;
        logger.info("ERP Linking : final product cost---" + productCost);
        sql = "UPDATE rm_shipment_trans st  SET st.`SHIPMENT_QTY`=?,st.`RATE`=?,st.`PRODUCT_COST`=?, "
                + "st.`LAST_MODIFIED_DATE`=?,st.`LAST_MODIFIED_BY`=?,st.`NOTES`=? "
                + "WHERE st.`SHIPMENT_TRANS_ID`=?;";
        rowsUpdated = this.jdbcTemplate.update(sql, Math.round(convertedQty), finalRate, productCost, curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), (long) map.get("SHIPMENT_TRANS_ID"));
        logger.info("ERP Linking : updated shipment trans---" + rowsUpdated);
        return -1;
    }

    @Override
    @Transactional
    public int linkShipmentWithARTMIS(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int rows;
        int row = 0;
//        int count = 0;
        boolean goAhead = false;
        logger.info("ERP Linking : link shipment with QAT object ---" + manualTaggingOrderDTO);
        logger.info("ERP Linking : link shipment with QAT curUser ---" + curUser.getUsername());
        String sql;
//                = "SELECT COUNT(*) FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
//        count = this.jdbcTemplate.queryForObject(sql, Integer.class, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());

//        logger.info("ERP Linking : manual tagging count---" + count);
//        if (count == 0) {
        try {
            logger.info("ERP Linking : going to create entry in manual tagging table---");
            sql = "INSERT INTO rm_manual_tagging VALUES (NULL,?,?,?,?,?,?,?,1,?,?);";
            row = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), (manualTaggingOrderDTO.getParentShipmentId() != 0 ? manualTaggingOrderDTO.getParentShipmentId() : manualTaggingOrderDTO.getShipmentId()), curDate, curUser.getUserId(), curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getConversionFactor());
            logger.info("ERP Linking : entry created in manual tagging table---");
            goAhead = true;
        } catch (Exception e) {
            logger.info("ERP Linking : Can't go ahead and link shipment bcoz it's duplicate---" + manualTaggingOrderDTO.getOrderNo() + "-" + manualTaggingOrderDTO.getPrimeLineNo());
        }
        if (goAhead) {
            logger.info("ERP Linking : going to get erp order object---");

            String filename = this.getMaxERPOrderIdFromERPShipment(manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
            logger.info("ERP Linking : max erp order id from shipment table to get the batch details---" + filename);
            // Get shipment details from erp order table
            sql = " SELECT "
                    + " eo.ERP_ORDER_ID, eo.RO_NO, eo.RO_PRIME_LINE_NO, eo.ORDER_NO, eo.PRIME_LINE_NO , "
                    + " eo.ORDER_TYPE, eo.CREATED_DATE, eo.PARENT_RO, eo.PARENT_CREATED_DATE, eo.PLANNING_UNIT_SKU_CODE,  "
                    + " eo.PROCUREMENT_UNIT_SKU_CODE, eo.QTY, eo.ORDERD_DATE,eo.CURRENT_ESTIMATED_DELIVERY_DATE, eo.REQ_DELIVERY_DATE,  "
                    + " eo.AGREED_DELIVERY_DATE, eo.SUPPLIER_NAME, eo.PRICE, eo.SHIPPING_COST, eo.SHIP_BY,  "
                    + " eo.RECPIENT_NAME, eo.RECPIENT_COUNTRY, eo.`STATUS`, eo.`CHANGE_CODE`, ssm.SHIPMENT_STATUS_ID, eo.MANUAL_TAGGING, eo.CONVERSION_FACTOR, "
                    + " es.ACTUAL_DELIVERY_DATE, es.ACTUAL_SHIPMENT_DATE, es.ARRIVAL_AT_DESTINATION_DATE, "
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
                    + " e.RECPIENT_NAME, e.RECPIENT_COUNTRY, e.STATUS, e.CHANGE_CODE, COALESCE(mts.PROGRAM_ID,e.PROGRAM_ID) `PROGRAM_ID`, COALESCE(mt.SHIPMENT_ID,e.SHIPMENT_ID) `SHIPMENT_ID` "
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
                    //                    + " LEFT JOIN rm_erp_shipment es ON es.ERP_ORDER_ID=eo.ERP_ORDER_ID "
                    + " LEFT JOIN rm_erp_shipment es ON es.FILE_NAME=? AND es.ORDER_NO=eo.ORDER_NO AND es.PRIME_LINE_NO=eo.PRIME_LINE_NO "
                    + " LEFT JOIN rm_shipment_status_mapping ssm ON eo.`STATUS`=ssm.EXTERNAL_STATUS_STAGE "
                    + " LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=sh.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ";
//                    + " GROUP BY eo.`ERP_ORDER_ID`; ";
            List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sql, new ErpOrderDTOListResultSetExtractor(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), filename);
            logger.info("ERP Linking : found erp order object---" + erpOrderDTOList);
            if (erpOrderDTOList.size() == 1) {
                ErpOrderDTO erpOrderDTO = erpOrderDTOList.get(0);
                try {
                    // Shipment id found in file
                    logger.info("-----------------------------------------------------------");
                    logger.info("ERP Linking : ERP Order - " + erpOrderDTO);
                    logger.info("ERP Linking : Order no - " + erpOrderDTO.getEoOrderNo());
                    logger.info("ERP Linking : Prime line no - " + erpOrderDTO.getEoPrimeLineNo());
                    logger.info("ERP Linking : Active - " + erpOrderDTO.getShActive());
                    logger.info("ERP Linking : ERP Flag - " + erpOrderDTO.getShErpFlag());
                    logger.info("ERP Linking : ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                    logger.info("ERP Linking : Shipment Id - " + erpOrderDTO.getShShipmentId());
                    logger.info("ERP Linking : Change code - " + erpOrderDTO.getEoChangeCode());
                    logger.info("ERP Linking : ManualTagging - " + erpOrderDTO.isManualTagging());
                    logger.info("ERP Linking : Program Id - " + erpOrderDTO.getShProgramId());
                    logger.info("ERP Linking : Shipment id - " + erpOrderDTO.getShShipmentId());
                    if (erpOrderDTO.getEoChangeCode() == 2) {
                        logger.info("ERP Linking : Change code is 2 --- ");
                        // This is the Delete code so go ahead and delete this Order
                        logger.info("Change code is 2 so therefore delete this line item where shipmentId=" + erpOrderDTO.getShShipmentId());
                        sql = "UPDATE rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID SET st.ACTIVE=0, st.LAST_MODIFIED_BY=:curUser, st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=:curUser, s.LAST_MODIFIED_DATE=:curDate WHERE s.PARENT_SHIPMENT_ID=:shipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ACTIVE AND st.ERP_FLAG";
                        params.clear();
//                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                        params.put("shipmentId", erpOrderDTO.getShShipmentId());
                        params.put("orderNo", erpOrderDTO.getEoOrderNo());
                        params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                        params.put("curDate", curDate);
                        params.put("curUser", curUser.getUserId());
                        logger.info("ERP Linking : Change code is 2 params--- " + params);
                        rows = this.namedParameterJdbcTemplate.update(sql, params);
                        logger.info(rows + " rows updated");

                    } else if (erpOrderDTO.isShErpFlag() && erpOrderDTO.getShParentShipmentId() == null) {
//                        System.out.println("---------------3--------------");
                        // The ERP Flag is true and the Parent Shipment Id is null
                        logger.info("ERP Linking : ERP Flag is true and Parent Shipment Id is null");
                        logger.info("ERP Linking : Find all Shipments whose Parent Shipment Id is :parentShipmentId and :orderNo and :primeLineNo are matching");
                        // Find all Shipments whose Parent Shipment Id is :parentShipmentId and :orderNo and :primeLineNo are matching
                        params.clear();
                        params.put("parentShipmentId", erpOrderDTO.getShShipmentId());
                        params.put("orderNo", erpOrderDTO.getEoOrderNo());
                        params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                        logger.info("ERP Linking : Find all Shipments params---" + params);
                        sql = "SELECT  st.SHIPMENT_TRANS_ID "
                                + "    FROM rm_shipment s "
                                + "LEFT JOIN (SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s left join rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo group by st.SHIPMENT_ID) sm ON sm.SHIPMENT_ID=s.SHIPMENT_ID "
                                + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID=sm.MAX_VERSION_ID "
                                + "WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ERP_FLAG=1 AND st.ACTIVE";
                        try {
                            logger.info("ERP Linking : Trying to see if the ShipmentTrans exists with the same orderNo, primeLineNo and parentShipmentId");
                            int shipmentTransId = this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class
                            );
                            logger.info("ERP Linking : ShipmentTransId " + shipmentTransId + " found so going to update that with latest information");
                            // TODO shipment found therefore update it with all the information
                            sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID "
                                    + "SET  "
                                    + "    st.EXPECTED_DELIVERY_DATE=:expectedDeliveryDate, st.FREIGHT_COST=:freightCost, st.PRODUCT_COST=:productCost, "
                                    + "    st.RATE=:price, st.SHIPMENT_MODE=:shipBy, st.SHIPMENT_QTY=:qty, "
                                    + "    st.SHIPMENT_STATUS_ID=:shipmentStatusId, st.SUPPLIER_ID=:supplierId, st.PLANNED_DATE=:plannedDate, "
                                    + "    st.SUBMITTED_DATE=:submittedDate, st.APPROVED_DATE=:approvedDate, st.SHIPPED_DATE=:shippedDate, "
                                    + "    st.ARRIVED_DATE=:arrivedDate, st.RECEIVED_DATE=:receivedDate, st.LAST_MODIFIED_BY=:curUser, "
                                    + "    st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=:curUser, s.LAST_MODIFIED_DATE=:curDate, st.NOTES=:notes "
                                    + "WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                            params.clear();
//                            params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                            params.put("shipmentTransId", shipmentTransId);
                            params.put("expectedDeliveryDate", erpOrderDTO.getExpectedDeliveryDate());
                            params.put("freightCost", erpOrderDTO.getEoShippingCost());
                            params.put("productCost", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * erpOrderDTO.getEoPrice() : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                            params.put("price", erpOrderDTO.getEoPrice());
                            params.put("shipBy", (erpOrderDTO.getEoShipBy().equals("Land") ? "Road" : (erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : (erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"))));
                            params.put("qty", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (Math.round(erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor())) : erpOrderDTO.getEoQty()));
                            params.put("shipmentStatusId", erpOrderDTO.getEoShipmentStatusId());
                            params.put("supplierId", erpOrderDTO.getEoSupplierId());
                            params.put("plannedDate", erpOrderDTO.getEoCreatedDate());
                            params.put("submittedDate", erpOrderDTO.getEoCreatedDate());
                            params.put("approvedDate", erpOrderDTO.getEoOrderedDate());
                            params.put("shippedDate", erpOrderDTO.getMinActualShipmentDate());
                            params.put("arrivedDate", erpOrderDTO.getMinArrivalAtDestinationDate());
                            params.put("receivedDate", erpOrderDTO.getMinActualDeliveryDate());
                            params.put("curDate", curDate);
                            params.put("curUser", curUser.getUserId());
                            params.put("notes", "Auto updated from shipment linking");
                            logger.info("ERP Linking : params---" + params);
                            this.namedParameterJdbcTemplate.update(sql, params);
                            logger.info("ERP Linking : Updated the already existing Shipment Trans record (" + shipmentTransId + ") with new data");
                            logger.info("ERP Linking : Now need to update the Batch information");
                            sql = "SELECT bi.BATCH_ID, stbi.SHIPMENT_TRANS_BATCH_INFO_ID, bi.BATCH_NO, bi.EXPIRY_DATE, stbi.BATCH_SHIPMENT_QTY FROM rm_shipment_trans_batch_info stbi LEFT JOIN rm_batch_info bi ON stbi.BATCH_ID=bi.BATCH_ID where stbi.SHIPMENT_TRANS_ID=:shipmentTransId group by stbi.BATCH_ID";
                            params.clear();
                            params.put("shipmentTransId", shipmentTransId);
                            List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ErpBatchDTORowMapper());
                            logger.info("ERP Linking : erpBatchList---" + erpBatchList);
                            if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                                logger.info("ERP Linking : Some batch information exists so need to check if it matches with what was already created");
                                logger.info("ERP Linking : erp shipment batch List---" + erpOrderDTO.getEoShipmentList());
                                for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                    try {
                                        logger.info("ERP Linking : erp shipment batch object---" + es);
                                        if (es.isAutoGenerated()) {
                                            // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                            logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                            boolean found = false;
                                            for (ErpBatchDTO eb : erpBatchList) {
                                                if (es.getExpiryDate() != null) {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no need to do anything
                                                        logger.info("ERP Linking : match found so no need to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                        found = true;
                                                        break;
                                                    }
                                                } else {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no need to do anything
                                                        logger.info("ERP Linking : match found so no need to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (found == false) {
                                                logger.info("ERP Linking : found false---");
                                                es.setStatus(2); // Insert
                                            }
                                        } else {
                                            // This is not an autogenerated batch which means that we can match it on BatchNo
                                            logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                            ErpBatchDTO tempB = new ErpBatchDTO();
                                            tempB.setBatchNo(es.getBatchNo());
                                            int index = erpBatchList.indexOf(tempB);
                                            logger.info("ERP Linking : batch index---" + index);
                                            if (index == -1) {
                                                // Batch not found
                                                logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                                // therefore need to insert 
                                                es.setStatus(2); // Insert
                                            } else {
                                                // Batch found now check for Expiry date and Qty
                                                logger.info("ERP Linking : Batch found now check for Expiry date and Qty---");
                                                ErpBatchDTO eb = erpBatchList.get(index);
                                                logger.info("ERP Linking : Batch eb---" + eb);
                                                logger.info("ERP Linking : eb.getExpiryDate()---" + eb.getExpiryDate());
                                                logger.info("ERP Linking : es.getExpiryDate()---" + es.getExpiryDate());
                                                logger.info("ERP Linking : eb.getQty()---" + eb.getQty());
                                                logger.info("ERP Linking : es.getBatchQty()---" + es.getBatchQty());
                                                if (es.getExpiryDate() != null) {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) <= 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so no nneed to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                    } else if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) < 0 && eb.getQty() != es.getBatchQty()) {
                                                        es.setStatus(3); // Update
                                                        eb.setStatus(3); // Update shipment trans batch info
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    } else {
                                                        logger.info("ERP Linking : match not found---");
                                                        es.setStatus(1); // Update
                                                        eb.setStatus(1); // Update
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    }
                                                } else {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) <= 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so no nneed to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                    } else if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) < 0 && eb.getQty() != es.getBatchQty()) {
                                                        es.setStatus(3); // Update
                                                        eb.setStatus(3); // Update shipment trans batch info
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    } else {
                                                        logger.info("ERP Linking : match not found---");
                                                        es.setStatus(1); // Update
                                                        eb.setStatus(1); // Update
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    }
                                                }
                                            }
                                        }
                                        logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                        logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                        switch (es.getStatus()) {
                                            case 0: // Do nothing
                                                logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                                break;
                                            case 1: // update
                                                logger.info("ERP Linking : Need to update this Batch");
                                                sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                                params.clear();
                                                params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("batchId", es.getExistingBatchId());
                                                logger.info("ERP Linking : case 1 batch info params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                sql = "UPDATE rm_shipment_trans_batch_info stbi SET stbi.BATCH_SHIPMENT_QTY=:qty WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                                params.clear();
                                                params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                                params.put("qty", es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty());
                                                logger.info("ERP Linking : case 1 shipment trans batch info params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                break;
                                            case -1: // Delete
                                                logger.info("ERP Linking : case -1 Need to delete this Batch");
                                                sql = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                                params.clear();
                                                params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                                logger.info("ERP Linking : case -1 params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                break;
                                            case 2: // Insert
                                                logger.info("ERP Linking : case 2 Need to insert this Batch");
                                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                                params.clear();
                                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                                params.put("AUTO_GENERATED", es.isAutoGenerated());
                                                logger.info("ERP Linking : case 2 batch info params---" + params);
                                                int batchId = sib.executeAndReturnKey(params).intValue();
                                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                                params.clear();
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", batchId);
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                            case 3: // Update shipment trans batch info
                                                logger.info("Need to update this Batch case 3");
                                                sql = "UPDATE rm_shipment_trans_batch_info stbi SET stbi.BATCH_SHIPMENT_QTY=:qty WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                                params.clear();
                                                params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                                params.put("qty", es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty());
                                                logger.info("Params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                break;
                                        }
                                    } catch (Exception e) {
                                        logger.info("ERP Linking : Error occured for batch---> " + es);
                                        e.printStackTrace();
                                    }
                                }
                                logger.info("ERP Linking : Checking if any old batches need to be deleted");
                                for (ErpBatchDTO eb : erpBatchList) {
                                    logger.info("ERP Linking : old batch objects---" + eb);
                                    if (eb.getStatus() == -1) {
                                        logger.info("ERP Linking : Batch no: " + eb.getBatchNo() + " Qty:" + eb.getQty() + " is going to be deleted");
                                        sql = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                        params.clear();
                                        params.put("shipmentTransBatchInfoId", eb.getShipmentTransBatchInfoId());
                                        logger.info("ERP Linking : old batch shipment trans batch info---" + params);
                                        this.namedParameterJdbcTemplate.update(sql, params);
                                    }
                                }
                            }
                        } catch (EmptyResultDataAccessException erda) {
                            // Counldn't find a record that matches the Order no and Prime Line no so go ahead and
                            logger.info("ERP Linking : Counldn't find a record that matches the Order no and Prime Line no so go ahead and");
                            logger.info("ERP Linking : Couldn't find a Shipment Trans so this is a new record going ahead with creation");
                            // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                            // All other details to be taken from ARTMIS
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("SUGGESTED_QTY", null);
                            params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                            params.put("CONVERSION_RATE_TO_USD", 1);
                            params.put("PARENT_SHIPMENT_ID", (erpOrderDTO.getShParentShipmentId() != null ? erpOrderDTO.getShParentShipmentId() : erpOrderDTO.getShShipmentId()));
                            params.put("CREATED_BY", curUser.getUserId()); //Default auto user in QAT
                            params.put("CREATED_DATE", curDate);
                            params.put("LAST_MODIFIED_BY", curUser.getUserId()); //Default auto user in QAT
                            params.put("LAST_MODIFIED_DATE", curDate);
                            params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                            logger.info("ERP Linking : shipment data params---" + params);
                            int newShipmentId = si.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Shipment Id " + newShipmentId + " created");
                            SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                            params.clear();
                            params.put("SHIPMENT_ID", newShipmentId);
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                            params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                            params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                            params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                            params.put("PROCUREMENT_UNIT_ID", (erpOrderDTO.getEoProcurementUnitId() != 0 ? erpOrderDTO.getEoProcurementUnitId() : null));
                            params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                            logger.info("ERP Linking : qty---" + erpOrderDTO.getEoQty());
                            logger.info("ERP Linking : conversion factor---" + erpOrderDTO.getConversionFactor());
                            params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (Math.round(erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor())) : erpOrderDTO.getEoQty()));
                            params.put("RATE", (erpOrderDTO.getEoPrice() / erpOrderDTO.getConversionFactor()));
                            params.put("PRODUCT_COST", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * (erpOrderDTO.getEoPrice() / manualTaggingOrderDTO.getConversionFactor()) : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                            params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                            params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                            params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                            params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                            params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                            params.put("SHIPPED_DATE", erpOrderDTO.getMinActualShipmentDate());
                            params.put("ARRIVED_DATE", erpOrderDTO.getMinArrivalAtDestinationDate());
                            params.put("RECEIVED_DATE", erpOrderDTO.getMinActualDeliveryDate());
                            params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                            params.put("NOTES", (manualTaggingOrderDTO.getNotes() != null && manualTaggingOrderDTO.getNotes() != "" ? manualTaggingOrderDTO.getNotes() : "Auto created from shipment linking"));
                            params.put("ERP_FLAG", 1);
                            params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                            params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                            params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                            params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                            params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                            params.put("LAST_MODIFIED_BY", curUser.getUserId()); // Default user
                            params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                            params.put("LAST_MODIFIED_DATE", curDate);
                            params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                            params.put("ACTIVE", true);
                            logger.info("ERP Linking : shipment trans data params---" + params);
                            int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Shipment Trans Id " + shipmentTransId + " created");
                            if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                                logger.info("ERP Linking : Some batch information exists so going to create Batches---" + erpOrderDTO.getEoShipmentList());
                                for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                    try {
                                        logger.info("ERP Linking : batch data object---" + es);
                                        //New code for batch start
                                        if (es.isAutoGenerated()) {
                                            // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                            logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                            es.setStatus(2); // Insert
                                        } else {
                                            // This is not an autogenerated batch which means that we can match it on BatchNo
                                            logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                            sql = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.EXPIRY_DATE "
                                                    + "FROM rm_batch_info bi WHERE  bi.`PROGRAM_ID`=:programId AND bi.`PLANNING_UNIT_ID`=:planningUnitId AND bi.`BATCH_NO`=:batchNo;";
                                            params.clear();
                                            params.put("programId", manualTaggingOrderDTO.getProgramId());
                                            params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                                            params.put("batchNo", es.getBatchNo());
//                                        params.put("expiryDate", es.getExpiryDate());
                                            List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ERPNewBatchDTORowMapper());
                                            logger.info("ERP Linking : erpBatchList---" + erpBatchList);

                                            if (erpBatchList.size() > 0) {
                                                ErpBatchDTO tempB = new ErpBatchDTO();
                                                tempB.setBatchNo(es.getBatchNo());
                                                int index = erpBatchList.indexOf(tempB);
                                                ErpBatchDTO eb = erpBatchList.get(index);
                                                logger.info("ERP Linking : Batch eb---" + eb);
                                                logger.info("ERP Linking : batch index---" + index);
                                                if (es.getExpiryDate() != null) {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) > 0) {
                                                        // Update the batch table with less es.expiry date
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(1); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    } else {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(3); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    }
                                                } else {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) > 0) {
                                                        // Update the batch table with less es.expiry date
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(1); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    } else {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(3); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    }
                                                }
                                            } else {
                                                // Batch not found
                                                logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                                es.setStatus(2); // Insert
                                            }
                                        }

                                        logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                        logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                        switch (es.getStatus()) {
                                            case 0: // Do nothing
                                                logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                                break;
                                            case 1: // update
                                                logger.info("ERP Linking : Need to update this Batch");
                                                sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                                params.clear();
                                                params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("batchId", es.getExistingBatchId());
                                                logger.info("ERP Linking : case 1 batch info params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
//                                             sib = null;
                                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", es.getExistingBatchId());
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                            case 2: // Insert
                                                logger.info("ERP Linking : case 2 Need to insert this Batch");
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                                params.clear();
                                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                                params.put("AUTO_GENERATED", es.isAutoGenerated());
                                                logger.info("ERP Linking : case 2 batch info params---" + params);
                                                int batchId = sib.executeAndReturnKey(params).intValue();
                                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                                params.clear();
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", batchId);
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                            case 3: // Insert
                                                logger.info("ERP Linking : case 3 Need to insert into shipment trans Batch info");
                                                params.clear();
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", es.getExistingBatchId());
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                        }
//                                    // Insert into Batch info for each record
//                                    SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
//                                    params.clear();
//                                    params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
//                                    params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
//                                    params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
//                                    params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
//                                    params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
//                                    params.put("AUTO_GENERATED", es.isAutoGenerated());
//                                    logger.info("ERP Linking : batch info params---" + params);
//                                    int batchId = sib.executeAndReturnKey(params).intValue();
//                                    logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
//                                    params.clear();
//                                    sib = null;
//                                    sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
//                                    params.put("SHIPMENT_TRANS_ID", shipmentTransId);
//                                    params.put("BATCH_ID", batchId);
//                                    params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
//                                    logger.info("ERP Linking :shipment trans batch info params---" + params);
//                                    sib.execute(params);
//                                    logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                    } catch (Exception e) {
                                        logger.info("ERP Linking : Error occured for batch---> " + es);
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                // Insert into Batch info for each record
                                logger.info("ERP Linking : No Batch information exists so creating one automatically");
                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                params.clear();
                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                                params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                params.put("AUTO_GENERATED", true);
                                logger.info("ERP Linking :batch info params---" + params);
                                int batchId = sib.executeAndReturnKey(params).intValue();
                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                params.clear();
                                sib = null;
                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                params.put("BATCH_ID", batchId);
                                params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                                logger.info("ERP Linking :shipment trans batch info params---" + params);
                                sib.execute(params);
                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                            }
                        }
//                        if (erpOrderDTO.isShipmentCancelled() || (erpOrderDTO.getErpPlanningUnitId() != this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()))) {
//                            System.out.println("Inside notification------------------------------------------------------------");
//                            System.out.println("Is shipment cancelled-------------------------" + erpOrderDTO.isShipmentCancelled());
//                            System.out.println("Is sku changed--------------------------------------" + erpOrderDTO.isSkuChanged());
//                            System.out.println("previous erp order------------" + this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()));
//                            System.out.println("Current erp planning unit---" + erpOrderDTO.getErpPlanningUnitId());
//                            this.createERPNotification(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo(), erpOrderDTO.getShShipmentId(), (erpOrderDTO.isShipmentCancelled() ? 1 : 2));
//                        }
                    } else {
//                        System.out.println("---------------4--------------");
                        // This is a new Link request coming through
                        // So make the Shipment, Active = fasle and ERPFlag = true
                        logger.info("ERP Linking : This is a new Link request coming through.So make the Shipment, Active = fasle and ERPFlag = true");
                        logger.info("ERP Linking : This is a first time linking attempt");
                        logger.info("ERP Linking : Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo.All other details to be taken from ARTMIS + Current Shipment");
                        // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                        // All other details to be taken from ARTMIS + Current Shipment
//                        sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID SET st.`PLANNING_UNIT_ID`=:planningUnitId,st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                        sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID "
                                + " SET st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=:curUser, "
                                + " s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=:curUser, st.LAST_MODIFIED_DATE=:curDate "
                                + " WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                        params.clear();
//                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
//                        System.out.println("shipment trans id-------------------------" + erpOrderDTO.getShShipmentTransId());
                        params.put("curUser", curUser.getUserId());
                        params.put("curDate", curDate);
                        params.put("shipmentTransId", erpOrderDTO.getShShipmentTransId());
                        logger.info("ERP Linking : update shipment trans params---" + params);
                        this.namedParameterJdbcTemplate.update(sql, params);
                        logger.info("ERP Linking : Existing Shipment has been marked as ERP_FLAG=true and ACTIVE=false");
                        params.clear();
                        params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                        params.put("SUGGESTED_QTY", null);
                        params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                        params.put("CONVERSION_RATE_TO_USD", 1);
                        params.put("PARENT_SHIPMENT_ID", (erpOrderDTO.getShParentShipmentId() != null ? erpOrderDTO.getShParentShipmentId() : erpOrderDTO.getShShipmentId()));
                        params.put("CREATED_BY", curUser.getUserId()); //Default auto user in QAT
                        params.put("CREATED_DATE", curDate);
                        params.put("LAST_MODIFIED_BY", curUser.getUserId()); //Default auto user in QAT
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                        logger.info("ERP Linking : create entry in shipment params---" + params);
                        int newShipmentId = si.executeAndReturnKey(params).intValue();
                        logger.info("ERP Linking : Shipment Id " + newShipmentId + " created");
                        SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                        params.clear();
                        params.put("SHIPMENT_ID", newShipmentId);
                        params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                        params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                        params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                        params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                        params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                        params.put("PROCUREMENT_UNIT_ID", (erpOrderDTO.getEoProcurementUnitId() != 0 ? erpOrderDTO.getEoProcurementUnitId() : null));
                        params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                        params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (Math.round(erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor())) : erpOrderDTO.getEoQty()));
                        params.put("RATE", (erpOrderDTO.getEoPrice() / erpOrderDTO.getConversionFactor()));
                        params.put("PRODUCT_COST", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * erpOrderDTO.getEoPrice() : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                        params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                        params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                        params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                        params.put("SHIPPED_DATE", erpOrderDTO.getMinActualShipmentDate());
                        params.put("ARRIVED_DATE", erpOrderDTO.getMinArrivalAtDestinationDate());
                        params.put("RECEIVED_DATE", erpOrderDTO.getMinActualDeliveryDate());
                        params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                        params.put("NOTES", (manualTaggingOrderDTO.getNotes() != null && manualTaggingOrderDTO.getNotes() != "" ? manualTaggingOrderDTO.getNotes() : "Auto created from shipment linking"));
                        params.put("ERP_FLAG", 1);
                        params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                        params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                        params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                        params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                        params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                        params.put("LAST_MODIFIED_BY", curUser.getUserId()); // Default user
                        params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                        params.put("ACTIVE", true);
                        logger.info("ERP Linking : create entry in shipment trans params---" + params);
                        int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                        logger.info("ERP Linking : Shipment Trans Id " + shipmentTransId + " created");
                        if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                            logger.info("ERP Linking : Some batch information exists so going to create Batches");
                            for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                try {
                                    //New code for batch start
                                    if (es.isAutoGenerated()) {
                                        // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                        logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                        es.setStatus(2); // Insert
                                    } else {
                                        // This is not an autogenerated batch which means that we can match it on BatchNo
                                        logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                        sql = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.EXPIRY_DATE "
                                                + "FROM rm_batch_info bi WHERE  bi.`PROGRAM_ID`=:programId AND bi.`PLANNING_UNIT_ID`=:planningUnitId AND bi.`BATCH_NO`=:batchNo;";
                                        params.clear();
                                        params.put("programId", manualTaggingOrderDTO.getProgramId());
                                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                                        params.put("batchNo", es.getBatchNo());
//                                        params.put("expiryDate", es.getExpiryDate());
                                        List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ERPNewBatchDTORowMapper());
                                        logger.info("ERP Linking : erpBatchList---" + erpBatchList.toString());

                                        if (erpBatchList.size() > 0) {
                                            ErpBatchDTO tempB = new ErpBatchDTO();
                                            tempB.setBatchNo(es.getBatchNo());
                                            int index = erpBatchList.indexOf(tempB);
                                            logger.info("ERP Linking : tempB---" + tempB);
                                            logger.info("ERP Linking : batch no---" + es.getBatchNo());
                                            logger.info("ERP Linking : batch index---" + index);
                                            ErpBatchDTO eb = erpBatchList.get(index);
                                            logger.info("ERP Linking : Batch eb---" + eb);
                                            if (es.getExpiryDate() != null) {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            } else {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            }
                                        } else {
                                            // Batch not found
                                            logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                            es.setStatus(2); // Insert
                                        }
                                    }

                                    logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                    logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                    switch (es.getStatus()) {
                                        case 0: // Do nothing
                                            logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                            break;
                                        case 1: // update
                                            logger.info("ERP Linking : Need to update this Batch");
                                            sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                            params.clear();
                                            params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("batchId", es.getExistingBatchId());
                                            logger.info("ERP Linking : case 1 batch info params---" + params);
                                            this.namedParameterJdbcTemplate.update(sql, params);
//                                             sib = null;
                                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 2: // Insert
                                            logger.info("ERP Linking : case 2 Need to insert this Batch");
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                            params.clear();
                                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                            params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                            params.put("AUTO_GENERATED", es.isAutoGenerated());
                                            logger.info("ERP Linking : case 2 batch info params---" + params);
                                            int batchId = sib.executeAndReturnKey(params).intValue();
                                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", batchId);
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 3: // Insert
                                            logger.info("ERP Linking : case 3 Need to insert into shipment trans Batch info");
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 3 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                    }
                                    // New code for batch end
                                    // Insert into Batch info for each record
                                    // Old code
//                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
//                                params.clear();
//                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
//                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
//                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
//                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
//                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
//                                params.put("AUTO_GENERATED", es.isAutoGenerated());
//                                logger.info("ERP Linking : create entry in batch info params---" + params);
//                                int batchId = sib.executeAndReturnKey(params).intValue();
//                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
//                                params.clear();
//                                sib = null;
//                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
//                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
//                                params.put("BATCH_ID", batchId);
//                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
//                                logger.info("ERP Linking : create entry in shipment trans batch info params---" + params);
//                                sib.execute(params);
//                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                } catch (Exception e) {
                                    logger.info("ERP Linking : Error occured for batch---> " + es);
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            // Insert into Batch info for each record
                            logger.info("ERP Linking : No Batch information exists so creating one automatically");
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                            params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                            params.put("AUTO_GENERATED", true);
                            logger.info("ERP Linking : create entry in batch info params---" + params);
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                            logger.info("ERP Linking : create entry in shipment trans batch info params---" + params);
                            sib.execute(params);
                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                        }
//                        if (erpOrderDTO.isShipmentCancelled() || erpOrderDTO.isSkuChanged() || (erpOrderDTO.getErpPlanningUnitId() != this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()))) {
//                        if (erpOrderDTO.isShipmentCancelled() || (erpOrderDTO.getErpPlanningUnitId() != this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()))) {
//                            System.out.println("Inside notification------------------------------------------------------------");
//                            System.out.println("Is shipment cancelled-------------------------" + erpOrderDTO.isShipmentCancelled());
//                            System.out.println("Is sku changed--------------------------------------" + erpOrderDTO.isSkuChanged());
//                            System.out.println("previous erp order------------" + this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()));
//                            System.out.println("Current erp planning unit---" + erpOrderDTO.getErpPlanningUnitId());
//                            this.createERPNotification(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo(), erpOrderDTO.getShShipmentId(), (erpOrderDTO.isShipmentCancelled() ? 1 : 2));
//                        }
                    }

                } catch (Exception e) {
                    logger.info("ERP Linking : Error occurred while trying to import Shipment ", e);
                }

            }
        }
        return row;
//        } else {
//            //Update conversion factor and notes
//            logger.info("ERP Linking : Going to update conversion factor and notes");
//            logger.info("ERP Linking : manual tagging object---" + manualTaggingOrderDTO);
//            sql = " SELECT st.`SHIPMENT_TRANS_ID`,st.`RATE` FROM rm_shipment_trans st "
//                    + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
//                    + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE` "
//                    + "ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1;";
//
//            Map<String, Object> map = this.jdbcTemplate.queryForMap(sql, manualTaggingOrderDTO.getParentShipmentId(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
//            logger.info("ERP Linking : Get shipment trans info---" + map);
//
//            sql = "UPDATE `rm_manual_tagging` m SET m.`CONVERSION_FACTOR`=?,m.`NOTES`=? "
//                    + " WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE` AND m.`SHIPMENT_ID`=?; ";
//            int rowsUpdated = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getConversionFactor(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), manualTaggingOrderDTO.getParentShipmentId());
//            logger.info("ERP Linking : updated conversion factor and notes rows---" + rowsUpdated);
//
////            System.out.println("conversion factor---" + manualTaggingOrderDTO.getConversionFactor());
//            sql = "UPDATE rm_shipment_trans st  SET st.`SHIPMENT_QTY`=?,st.`PRODUCT_COST`=?, "
//                    + "st.`LAST_MODIFIED_DATE`=?,st.`LAST_MODIFIED_BY`=?,st.`NOTES`=? "
//                    + "WHERE st.`SHIPMENT_TRANS_ID`=?;";
////            long convertedQty = ((new BigDecimal(manualTaggingOrderDTO.getQuantity())).multiply(manualTaggingOrderDTO.getConversionFactor())).longValueExact();
//
//            long convertedQty = (long) Math.round((double) manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor());
//            logger.info("ERP Linking : convertedQty---" + convertedQty);
//            logger.info("ERP Linking : rate---" + map.get("RATE"));
//            logger.info("ERP Linking : product cost---" + Double.parseDouble(map.get("RATE").toString()));
//            double rate = Double.parseDouble(map.get("RATE").toString());
//            double productCost = rate * (double) convertedQty;
//            logger.info("ERP Linking : final product cost---" + productCost);
//            rowsUpdated = this.jdbcTemplate.update(sql, Math.round(convertedQty), productCost, curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), (long) map.get("SHIPMENT_TRANS_ID"));
//            logger.info("ERP Linking : updated shipment trans---" + rowsUpdated);
//            return -1;
//        }
    }

    @Override
    @Transactional
    public int linkShipmentWithARTMISWithoutShipmentid(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        logger.info("ERP Linking : link Shipment With ARTMIS Without Shipmentid---");
        logger.info("ERP Linking : manualTaggingOrderDTO---" + manualTaggingOrderDTO);
        logger.info("ERP Linking : Curuser---" + curUser);
        String sql;
        int count = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int parentShipmentId = 0;
        Map<String, Object> params = new HashMap<>();
        sql = "SELECT COUNT(*) FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
        count = this.jdbcTemplate.queryForObject(sql, Integer.class,
                manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());

        logger.info("ERP Linking : manual tagging order no---" + manualTaggingOrderDTO.getOrderNo());
        logger.info("ERP Linking : manual tagging prime line no.---" + manualTaggingOrderDTO.getPrimeLineNo());
        logger.info("ERP Linking : manual tagging count---" + count);

        if (count == 0) {
            for (int i = 1; i <= 2; i++) {
                logger.info("ERP Linking : Going to create shipment table entry---");
                SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                params.put("SUGGESTED_QTY", null);
                params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                params.put("CONVERSION_RATE_TO_USD", 1);
                params.put("PARENT_SHIPMENT_ID", (i == 1 ? null : parentShipmentId));
                params.put("CREATED_BY", curUser.getUserId()); //Default auto user in QAT
                params.put("CREATED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId()); //Default auto user in QAT
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("MAX_VERSION_ID", 1); // Same as the Current Version that is already present
                logger.info("ERP Linking : Going to create shipment table entry params---" + params);
                int newShipmentId = si.executeAndReturnKey(params).intValue();
                logger.info("ERP Linking : Shipment Id " + newShipmentId + " created");
                if (i == 1) {
                    parentShipmentId = newShipmentId;
                    logger.info("ERP Linking : Going to create manual tagging entry parent shipment id---" + parentShipmentId);
                    sql = "INSERT INTO rm_manual_tagging VALUES (NULL,?,?,?,?,?,?,?,1,?,?);";
                    int row = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), newShipmentId, curDate, curUser.getUserId(), curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getConversionFactor());
                    logger.info("ERP Linking : rows inserted in manual tagging---" + row);

                    logger.info("ERP Linking : Going to do entry in tab3 shipment table---");
                    this.tab3ShipmentCreation(newShipmentId, curUser);
                }
                String filename = this.getMaxERPOrderIdFromERPShipment(manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
                sql = " SELECT "
                        + " eo.ERP_ORDER_ID, eo.RO_NO, eo.RO_PRIME_LINE_NO, eo.ORDER_NO, eo.PRIME_LINE_NO , "
                        + " eo.ORDER_TYPE, eo.CREATED_DATE, eo.PARENT_RO, eo.PARENT_CREATED_DATE, eo.PLANNING_UNIT_SKU_CODE,  "
                        + " eo.PROCUREMENT_UNIT_SKU_CODE, eo.QTY, eo.ORDERD_DATE,eo.CURRENT_ESTIMATED_DELIVERY_DATE, eo.REQ_DELIVERY_DATE,  "
                        + " eo.AGREED_DELIVERY_DATE, eo.SUPPLIER_NAME, eo.PRICE, eo.SHIPPING_COST, eo.SHIP_BY,  "
                        + " eo.RECPIENT_NAME, eo.RECPIENT_COUNTRY, eo.`STATUS`, eo.`CHANGE_CODE`, ssm.SHIPMENT_STATUS_ID, eo.MANUAL_TAGGING, eo.CONVERSION_FACTOR, "
                        + " es.ACTUAL_DELIVERY_DATE, es.ACTUAL_SHIPMENT_DATE, es.ARRIVAL_AT_DESTINATION_DATE, "
                        + " es.BATCH_NO, IF(es.DELIVERED_QTY !=0,COALESCE(es.DELIVERED_QTY, es.SHIPPED_QTY),es.SHIPPED_QTY) `BATCH_QTY`, es.`EXPIRY_DATE`, "
                        + " st.PLANNING_UNIT_ID,papu.PLANNING_UNIT_ID AS ERP_PLANNING_UNIT_ID, papu2.PROCUREMENT_UNIT_ID, pu2.SUPPLIER_ID, ppu.SHELF_LIFE, "
                        + " sh.SHIPMENT_ID, sh.PROGRAM_ID, sh.PARENT_SHIPMENT_ID, "
                        + " st.SHIPMENT_TRANS_ID, st.VERSION_ID, st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.BUDGET_ID, st.ACTIVE, st.ERP_FLAG, st.ACCOUNT_FLAG, st.DATA_SOURCE_ID,eo.CONVERSION_FACTOR  "
                        + " FROM ( "
                        + " SELECT   "
                        + " e.ERP_ORDER_ID, e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO ,  "
                        + " e.ORDER_TYPE, e.CREATED_DATE, e.PARENT_RO, e.PARENT_CREATED_DATE, e.PLANNING_UNIT_SKU_CODE,   "
                        + " e.PROCUREMENT_UNIT_SKU_CODE, e.QTY, e.ORDERD_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE, e.REQ_DELIVERY_DATE,   "
                        + " e.AGREED_DELIVERY_DATE, e.SUPPLIER_NAME, e.PRICE, e.SHIPPING_COST, e.SHIP_BY, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, TRUE, FALSE) `MANUAL_TAGGING`, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, mt.CONVERSION_FACTOR, 1) `CONVERSION_FACTOR`,   "
                        + " e.RECPIENT_NAME, e.RECPIENT_COUNTRY, e.STATUS, e.CHANGE_CODE, COALESCE(e.PROGRAM_ID, mts.PROGRAM_ID) `PROGRAM_ID`, COALESCE(mt.SHIPMENT_ID,e.SHIPMENT_ID) `SHIPMENT_ID`  "
                        + " FROM (  "
                        + " SELECT MAX(e.`ERP_ORDER_ID`) AS ERP_ORDER_ID FROM rm_erp_order e  "
                        + " WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=?  "
                        + " ) es  "
                        + " LEFT JOIN rm_erp_order e  ON e.`ERP_ORDER_ID`=es.`ERP_ORDER_ID`  "
                        + " LEFT JOIN rm_manual_tagging mt ON e.ORDER_NO=mt.ORDER_NO AND e.PRIME_LINE_NO=mt.PRIME_LINE_NO AND mt.ACTIVE AND mt.SHIPMENT_ID=?  "
                        + " LEFT JOIN rm_shipment mts ON mt.SHIPMENT_ID=mts.SHIPMENT_ID  "
                        + " ) eo  "
                        + " LEFT JOIN (SELECT sx1.SHIPMENT_ID, sx1.PROGRAM_ID, sx1.PARENT_SHIPMENT_ID, MAX(st1.VERSION_ID) MAX_VERSION_ID FROM rm_shipment sx1 LEFT JOIN rm_shipment_trans st1 ON sx1.SHIPMENT_ID=st1.SHIPMENT_ID GROUP BY st1.SHIPMENT_ID) sh ON sh.SHIPMENT_ID=eo.SHIPMENT_ID AND sh.PROGRAM_ID=eo.PROGRAM_ID  "
                        + " LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=sh.SHIPMENT_ID AND st.VERSION_ID=sh.MAX_VERSION_ID  "
                        + " LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=eo.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1   "
                        + " LEFT JOIN vw_planning_unit pu ON st.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                        + " LEFT JOIN rm_procurement_agent_procurement_unit papu2 ON eo.PROCUREMENT_UNIT_SKU_CODE=LEFT(papu2.SKU_CODE,15) AND papu2.PROCUREMENT_AGENT_ID=1  "
                        + " LEFT JOIN rm_procurement_unit pu2 ON papu2.PROCUREMENT_UNIT_ID=pu2.PROCUREMENT_UNIT_ID "
                        //                    + "                     LEFT JOIN rm_erp_shipment es ON es.ERP_ORDER_ID=eo.ERP_ORDER_ID  "
                        + " LEFT JOIN rm_erp_shipment es ON es.FILE_NAME=? AND es.ORDER_NO=eo.ORDER_NO AND es.PRIME_LINE_NO=eo.PRIME_LINE_NO "
                        + " LEFT JOIN rm_shipment_status_mapping ssm ON eo.`STATUS`=ssm.EXTERNAL_STATUS_STAGE "
                        + " LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=sh.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  ";
//                    + "                     GROUP BY eo.`ERP_ORDER_ID`;";
                List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sql, new ErpOrderDTOListResultSetExtractor(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), (i == 1 ? newShipmentId : parentShipmentId), filename);
                logger.info("ERP Linking : erp order---" + erpOrderDTOList);
                if (erpOrderDTOList.size() == 1) {
                    ErpOrderDTO erpOrderDTO = erpOrderDTOList.get(0);
                    try {
                        // Shipment id found in file
                        logger.info("-----------------------------------------------------------");
                        logger.info("ERP Linking : ERP Order - " + erpOrderDTO);
                        logger.info("ERP Linking : Order no - " + erpOrderDTO.getEoOrderNo());
                        logger.info("ERP Linking : Prime line no - " + erpOrderDTO.getEoPrimeLineNo());
                        logger.info("ERP Linking : Active - " + erpOrderDTO.getShActive());
                        logger.info("ERP Linking : ERP Flag - " + erpOrderDTO.getShErpFlag());
                        logger.info("ERP Linking : ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                        logger.info("ERP Linking : Shipment Id - " + erpOrderDTO.getShShipmentId());
                        logger.info("ERP Linking : Change code - " + erpOrderDTO.getEoChangeCode());
                        logger.info("ERP Linking : ManualTagging - " + erpOrderDTO.isManualTagging());
                        logger.info("ERP Linking : Program Id - " + erpOrderDTO.getShProgramId());
                        logger.info("ERP Linking : Shipment id - " + erpOrderDTO.getShShipmentId());
                        SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                        params.clear();
                        params.put("SHIPMENT_ID", newShipmentId);
                        params.put("PLANNING_UNIT_ID", manualTaggingOrderDTO.getPlanningUnitId());
                        params.put("PROCUREMENT_AGENT_ID", 1);
                        params.put("FUNDING_SOURCE_ID", manualTaggingOrderDTO.getFundingSourceId());
                        params.put("BUDGET_ID", manualTaggingOrderDTO.getBudgetId());
                        params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                        params.put("PROCUREMENT_UNIT_ID", (erpOrderDTO.getEoProcurementUnitId() != 0 ? erpOrderDTO.getEoProcurementUnitId() : null));
                        params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                        params.put("SHIPMENT_QTY", (manualTaggingOrderDTO.getConversionFactor() != 0 && manualTaggingOrderDTO.getConversionFactor() != 0.0 ? (Math.round(manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor())) : manualTaggingOrderDTO.getQuantity()));
                        params.put("RATE", (erpOrderDTO.getEoPrice() / manualTaggingOrderDTO.getConversionFactor()));
                        params.put("PRODUCT_COST", (manualTaggingOrderDTO.getConversionFactor() != 0 && manualTaggingOrderDTO.getConversionFactor() != 0.0 ? (manualTaggingOrderDTO.getConversionFactor() * manualTaggingOrderDTO.getQuantity()) * (erpOrderDTO.getEoPrice() / manualTaggingOrderDTO.getConversionFactor()) : (erpOrderDTO.getEoPrice() * manualTaggingOrderDTO.getQuantity())));
//                    params.put("PRODUCT_COST", manualTaggingOrderDTO.getQuantity() * erpOrderDTO.getEoPrice());
                        params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") ? "Road" : (erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : (erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"))));
                        params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                        params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                        params.put("SHIPPED_DATE", erpOrderDTO.getMinActualShipmentDate());
                        params.put("ARRIVED_DATE", erpOrderDTO.getMinArrivalAtDestinationDate());
                        params.put("RECEIVED_DATE", erpOrderDTO.getMinActualDeliveryDate());
                        params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
//                    params.put("NOTES", "Auto created from shipment linking...");
                        params.put("NOTES", (i == 1 ? "Auto created from not linked(ERP)..." : manualTaggingOrderDTO.getNotes()));
                        params.put("ERP_FLAG", 1);
                        params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                        params.put("PRIME_LINE_NO", (i == 1 ? null : erpOrderDTO.getEoPrimeLineNo()));
                        params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                        params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                        params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                        params.put("LAST_MODIFIED_BY", curUser.getUserId()); // Default user
                        params.put("DATA_SOURCE_ID", 17);
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("VERSION_ID", 1);
                        params.put("ACTIVE", (i == 1 ? false : true));
                        logger.info("ERP Linking : shipment trans params---" + params);
                        int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                        logger.info("ERP Linking : Shipment Trans Id " + shipmentTransId + " created");
                        if (!erpOrderDTO.getEoShipmentList().isEmpty() && i != 1) {
                            logger.info("ERP Linking : Some batch information exists so going to create Batches---" + erpOrderDTO.getEoShipmentList());
                            for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                try {
                                    //New code for batch
                                    if (es.isAutoGenerated()) {
                                        // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                        logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                        es.setStatus(2); // Insert
                                    } else {
                                        // This is not an autogenerated batch which means that we can match it on BatchNo
                                        logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                        sql = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.EXPIRY_DATE "
                                                + "FROM rm_batch_info bi WHERE  bi.`PROGRAM_ID`=:programId AND bi.`PLANNING_UNIT_ID`=:planningUnitId AND bi.`BATCH_NO`=:batchNo;";
                                        params.clear();
                                        params.put("programId", manualTaggingOrderDTO.getProgramId());
                                        params.put("planningUnitId", manualTaggingOrderDTO.getPlanningUnitId());
                                        params.put("batchNo", es.getBatchNo());
//                                        params.put("expiryDate", es.getExpiryDate());
                                        List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ERPNewBatchDTORowMapper());
                                        logger.info("ERP Linking : erpBatchList---" + erpBatchList);

                                        if (erpBatchList.size() > 0) {
                                            ErpBatchDTO tempB = new ErpBatchDTO();
                                            tempB.setBatchNo(es.getBatchNo());
                                            int index = erpBatchList.indexOf(tempB);
                                            ErpBatchDTO eb = erpBatchList.get(index);
                                            logger.info("ERP Linking : Batch eb---" + eb);
                                            logger.info("ERP Linking : batch index---" + index);
                                            if (es.getExpiryDate() != null) {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            } else {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            }
                                        } else {
                                            // Batch not found
                                            logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                            es.setStatus(2); // Insert
                                        }
                                    }

                                    logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                    logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                    switch (es.getStatus()) {
                                        case 0: // Do nothing
                                            logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                            break;
                                        case 1: // update
                                            logger.info("ERP Linking : Need to update this Batch");
                                            sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                            params.clear();
                                            params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("batchId", es.getExistingBatchId());
                                            logger.info("ERP Linking : case 1 batch info params---" + params);
                                            this.namedParameterJdbcTemplate.update(sql, params);
//                                             sib = null;
                                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 2: // Insert
                                            logger.info("ERP Linking : case 2 Need to insert this Batch");
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                            params.clear();
                                            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                                            params.put("PLANNING_UNIT_ID", manualTaggingOrderDTO.getPlanningUnitId());
                                            params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                            params.put("AUTO_GENERATED", es.isAutoGenerated());
                                            logger.info("ERP Linking : case 2 batch info params---" + params);
                                            int batchId = sib.executeAndReturnKey(params).intValue();
                                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", batchId);
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 3: // Insert
                                            logger.info("ERP Linking : case 3 Need to insert into shipment trans Batch info");
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                    }
                                    // Insert into Batch info for each record
//                            logger.info("ERP Linking : Insert into Batch info for each record---" + es);
//                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
//                            params.clear();
//                            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
//                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
//                            params.put("BATCH_NO", (es.isAutoGenerated() || i == 1 ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
//                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null || i == 1 ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
//                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
//                            params.put("AUTO_GENERATED", es.isAutoGenerated());
//                            logger.info("ERP Linking :  Batch info params---" + params);
//                            int batchId = sib.executeAndReturnKey(params).intValue();
//                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
//                            params.clear();
//                            sib = null;
//                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
//                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
//                            params.put("BATCH_ID", batchId);
//                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? manualTaggingOrderDTO.getQuantity() : es.getBatchQty()));
//                            logger.info("ERP Linking :  shipment trans Batch info params---" + params);
//                            sib.execute(params);
//                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                } catch (Exception e) {
                                    logger.info("ERP Linking : Error occured for batch---> " + es);
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            // Insert into Batch info for each record
                            logger.info("ERP Linking : No Batch information exists so creating one automatically");
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                            params.put("PLANNING_UNIT_ID", manualTaggingOrderDTO.getPlanningUnitId());
                            params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                            params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                            params.put("AUTO_GENERATED", true);
                            logger.info("ERP Linking :  Batch info params---" + params);
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", manualTaggingOrderDTO.getQuantity());
                            logger.info("ERP Linking :  shipment trans Batch info params---" + params);
                            sib.execute(params);
                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + manualTaggingOrderDTO.getQuantity());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("ERP Linking : Error occured while creating fresh shipment " + e);
                    }
                }
            }
        } else {
            parentShipmentId = -2;
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
        logger.info("ERP Linking : Going to delink shipments---" + erpOrderDTO);
        logger.info("ERP Linking : Curuser---" + curUser);
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sql;
        int maxTransId;
        int parentShipmentId = erpOrderDTO.getParentShipmentId();
        logger.info("ERP Linking : parentShipmentId---" + parentShipmentId);

        sql = "SELECT b.SHIPMENT_ID FROM (SELECT a.* FROM ( "
                + "SELECT st.`SHIPMENT_TRANS_ID`,s.`SHIPMENT_ID`,st.`ACTIVE` FROM rm_shipment s "
                + "LEFT JOIN rm_shipment_trans st ON st.`SHIPMENT_ID`=s.`SHIPMENT_ID` "
                + "WHERE s.`PARENT_SHIPMENT_ID`=? ORDER BY st.`SHIPMENT_TRANS_ID` DESC) AS a "
                + "GROUP BY a.SHIPMENT_ID) AS b WHERE b.active;";
        logger.info("delinking parenshipment id----------" + parentShipmentId);
        List<Integer> shipmentIdList = this.jdbcTemplate.queryForList(sql, Integer.class,
                parentShipmentId);
        logger.info("ERP Linking : shipment id list to delink---" + shipmentIdList);
//        if (shipmentIdList.size() == 1 && erpOrderDTO.getShipmentId() == shipmentIdList.get(0)) {
        if (shipmentIdList.size() == 1) {
            logger.info("ERP Linking : one shipment id found to delink---");
//            for (int shipmentId1 : shipmentIdList) {
            if (this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class,
                    erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo()) != null) {
                logger.info(
                        "inside if--------------------" + erpOrderDTO);
                logger.info(
                        "ERP Linking : Going to check tab3 shipments table for parent--------------------" + parentShipmentId);
                sql = "SELECT COUNT(*) FROM rm_erp_tab3_shipments s WHERE s.`SHIPMENT_ID`=? AND s.`ACTIVE`;";
                int count = this.jdbcTemplate.queryForObject(sql, Integer.class, parentShipmentId);

                logger.info(
                        "ERP Linking : Going to check tab3 shipments count--------------------" + count);
//                    logger.info("ERP Linking : Going to check tab3 shipments count is 0 so activate parent--------------------");
                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`SHIPMENT_ID`=?", Integer.class, parentShipmentId);

                logger.info(
                        "ERP Linking : 1st max trans id---" + maxTransId);
                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=?,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";

                this.jdbcTemplate.update(sql,
                        (count == 0 ? 1 : 0), curUser.getUserId(), curDate, maxTransId);
                if (count
                        > 0) {
                    logger.info("ERP Linking : Going to check tab3 shipments count is greater than 0 so leave parent as is and deactivate in tab3 shipment table--------------------");
                    sql = "UPDATE rm_erp_tab3_shipments s SET s.`ACTIVE`=0,s.`LAST_MODIFIED_BY`=?,s.`LAST_MODIFIED_DATE`=? WHERE s.`SHIPMENT_ID`=? AND s.`ACTIVE`; ";
                    this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, parentShipmentId);
                    logger.info("ERP Linking : Updation of tab 3 shiment completed--------------------");
                }

                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());

                logger.info(
                        "ERP Linking : 2nd max trans id---" + maxTransId);

                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=0,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=?,`NOTES`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";

                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, erpOrderDTO.getNotes(), maxTransId);
                logger.info(
                        "ERP Linking : Update completed---");
            } else {
                System.out.println("delinking inside else----------" + parentShipmentId);
            }
        } else {
            logger.info("ERP Linking : Multiple child shipments found---");
            if (this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class,
                    erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo()) != null) {
                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());

                logger.info(
                        "ERP Linking : max trans id---" + maxTransId);
                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=0,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=?,`NOTES`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";

                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, erpOrderDTO.getNotes(), maxTransId);
                System.out.println(
                        "delinking inside else else----------" + maxTransId);
            }
        }
//        }
        logger.info("ERP Linking : Going to update manual tagging table---");
//        sql = "UPDATE rm_manual_tagging m SET m.`ACTIVE`=0,m.`NOTES`=?,m.`LAST_MODIFIED_DATE`=?,m.`LAST_MODIFIED_BY`=? WHERE m.`SHIPMENT_ID`=? AND m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=?;";
//        this.jdbcTemplate.update(sql, erpOrderDTO.getNotes(), curDate, curUser.getUserId(), erpOrderDTO.getParentShipmentId(), erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
        sql = "DELETE FROM rm_manual_tagging WHERE `SHIPMENT_ID`=? AND `ORDER_NO`=? AND `PRIME_LINE_NO`=?;";
        this.jdbcTemplate.update(sql, erpOrderDTO.getParentShipmentId(), erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
//        DELETE FROM rm_manual_tagging WHERE active = 0;""
//ALTER TABLE `fasp`.`rm_manual_tagging` ADD UNIQUE `uniqueOrder` (`ORDER_NO`, `PRIME_LINE_NO`, `SHIPMENT_ID`, `ACTIVE`); 
    }

    @Override
    @JsonView(Views.ReportView.class)
    public List<LoadProgram> getLoadProgram(int programTypeId, CustomUserDetails curUser) {
        String programTableName = "";
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            programTableName = "vw_program";
        } else {
            programTableName = "vw_dataset";
        }
        StringBuilder sb = new StringBuilder("SELECT  "
                + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR, "
                + "    rc.REALM_COUNTRY_ID, c.LABEL_ID `REALM_COUNTRY_LABEL_ID`, c.LABEL_EN `REALM_COUNTRY_LABEL_EN`, c.LABEL_FR `REALM_COUNTRY_LABEL_FR`, c.LABEL_SP `REALM_COUNTRY_LABEL_SP`, c.LABEL_PR `REALM_COUNTRY_LABEL_PR`, c.COUNTRY_CODE, "
                + "    ha.HEALTH_AREA_ID, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, ha.HEALTH_AREA_CODE, "
                + "    o.ORGANISATION_ID, o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_SP `ORGANISATION_LABEL_SP`, o.LABEL_PR `ORGANISATION_LABEL_PR`, o.ORGANISATION_CODE, "
                + "    pv.MAX_COUNT "
                + "FROM ")
                .append(programTableName).append(" p ")
                .append(
                        "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                        + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                        + "LEFT JOIN vw_health_area ha ON FIND_IN_SET(ha.HEALTH_AREA_ID,p.HEALTH_AREA_ID) "
                        + "LEFT JOIN rm_health_area_country hac ON  ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID AND rc.REALM_COUNTRY_ID=hac.REALM_COUNTRY_ID "
                        + "LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                        + "LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID AND rc.REALM_COUNTRY_ID=oc.REALM_COUNTRY_ID "
                        + "LEFT JOIN (SELECT pv.PROGRAM_ID, count(*) MAX_COUNT FROM rm_program_version pv WHERE pv.VERSION_READY GROUP BY pv.PROGRAM_ID) pv ON p.PROGRAM_ID=pv.PROGRAM_ID "
                        + "WHERE p.ACTIVE AND rc.ACTIVE AND ha.ACTIVE AND o.ACTIVE AND hac.ACTIVE AND oc.ACTIVE AND p.PROGRAM_TYPE_ID=:programTypeId");
        Map<String, Object> params = new HashMap<>();
        params.put("programTypeId", programTypeId);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" ORDER BY c.LABEL_EN, p.PROGRAM_CODE ");
        List<LoadProgram> programList = this.namedParameterJdbcTemplate.query(sb.toString(), params, new LoadProgramListResultSetExtractor());
        params.clear();
        params.put("programId", 0);
        for (LoadProgram lp : programList) {
            params.replace("programId", lp.getProgram().getId());
            lp.setVersionList(this.namedParameterJdbcTemplate.query("SELECT LPAD(pv.VERSION_ID,6,'0') VERSION_ID, vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR`, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, cb.USER_ID, cb.USERNAME, pv.CREATED_DATE, pv.NOTES FROM " + programTableName + " p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID AND pv.VERSION_READY LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID WHERE p.ACTIVE AND p.PROGRAM_ID=:programId ORDER BY pv.VERSION_ID DESC LIMIT 0,5", params, new LoadVersionRowMapper()));
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
    public LoadProgram getLoadProgram(int programId, int page, int programTypeId, CustomUserDetails curUser) {
        String programTableName = "";
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            programTableName = "vw_program";
        } else {
            programTableName = "vw_dataset";
        }
        StringBuilder sb = new StringBuilder("SELECT  "
                + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR, "
                + "    rc.REALM_COUNTRY_ID, c.LABEL_ID `REALM_COUNTRY_LABEL_ID`, c.LABEL_EN `REALM_COUNTRY_LABEL_EN`, c.LABEL_FR `REALM_COUNTRY_LABEL_FR`, c.LABEL_SP `REALM_COUNTRY_LABEL_SP`, c.LABEL_PR `REALM_COUNTRY_LABEL_PR`, c.COUNTRY_CODE, "
                + "    ha.HEALTH_AREA_ID, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, ha.HEALTH_AREA_CODE, "
                + "    o.ORGANISATION_ID, o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_SP `ORGANISATION_LABEL_SP`, o.LABEL_PR `ORGANISATION_LABEL_PR`, o.ORGANISATION_CODE, "
                + "    COUNT(pv.VERSION_ID) MAX_COUNT "
                + "FROM ")
                .append(programTableName).append(" p ")
                .append(
                        "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                        + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                        + "LEFT JOIN vw_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
                        + "LEFT JOIN rm_health_area_country hac ON  ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID AND rc.REALM_COUNTRY_ID=hac.REALM_COUNTRY_ID "
                        + "LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                        + "LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID AND rc.REALM_COUNTRY_ID=oc.REALM_COUNTRY_ID "
                        + "LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID "
                        + "WHERE p.ACTIVE AND rc.ACTIVE AND ha.ACTIVE AND o.ACTIVE AND hac.ACTIVE AND oc.ACTIVE AND p.PROGRAM_ID=:programId AND p.PROGRAM_TYPE_ID=:programTypeId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("programTypeId", programTypeId);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" GROUP BY p.PROGRAM_ID ORDER BY c.LABEL_EN, p.PROGRAM_CODE ");
        LoadProgram program = this.namedParameterJdbcTemplate.query(sb.toString(), params, new LoadProgramResultSetExtractor());
        program.setCurrentPage(page);
        params.clear();
        params.put("programId", programId);
        int versionCount = this.namedParameterJdbcTemplate.queryForObject("SELECT COUNT(*) FROM rm_program_version pv WHERE pv.PROGRAM_ID=:programId", params, Integer.class
        );
        params.put("versionCount", versionCount);
        params.put("offsetNo", page * 5);
        int showCount = 0;
        if (versionCount - page * 5 > 5) {
            showCount = 5;
        } else if (versionCount - page * 5 > 0) {
            showCount = versionCount - page * 5;
        }
        params.put("showCount", showCount);
        program.setVersionList(this.namedParameterJdbcTemplate.query("SELECT LPAD(pv.VERSION_ID,6,'0') VERSION_ID, vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR`, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, cb.USER_ID, cb.USERNAME, pv.CREATED_DATE, pv.NOTES FROM " + programTableName + " p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID WHERE p.ACTIVE AND p.PROGRAM_ID=:programId ORDER BY pv.VERSION_ID DESC LIMIT :offsetNo, :showCount", params, new LoadVersionRowMapper()));
        return program;
    }

    @Override
    public boolean validateProgramCode(int realmId, int programId, String programCode, CustomUserDetails curUser) {
        String sql = "SELECT COUNT(*) FROM rm_program p LEFT JOIN rm_realm_country rc  ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID WHERE rc.REALM_ID = :realmId AND p.PROGRAM_CODE = :programCode AND (:programId = 0 OR p.PROGRAM_ID != :programId)";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("programId", programId);
        params.put("programCode", programCode);
        return (this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class
        ) == 0);
    }

    @Override
    public List<Program> getProgramListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("(").append(this.sqlProgramListString).append(" AND p.PROGRAM_ID IN (").append(programIdsString).append(") ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy).append(")");
        sqlStringBuilder.append(" UNION ")
                .append("(").append(this.sqlDatasetListString).append(" AND p.PROGRAM_ID IN (").append(programIdsString).append(") ");
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlOrderBy).append(")");
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
    public List<ErpOrderAutocompleteDTO> getErpOrderSearchData(String term, int programId, int planningUnitId, int linkingType) {
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
                + "LEFT JOIN rm_program p ON p.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID AND p.`PROGRAM_ID`=:programId AND p.PROGRAM_TYPE_ID=" + GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN + " "
                + "LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + "WHERE  p.`REALM_COUNTRY_ID` IS NOT NULL AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15) AND e.`RO_NO` LIKE '%").append(term).append("%' ");
        if (planningUnitId != 0) {
            sb.append(" AND papu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        if (linkingType == 1) {
            sb.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sb.append(" GROUP BY e.`RO_NO` ");
        sb.append(" ) ");
        sb.append(" UNION ");
        sb.append("(SELECT e.`ERP_ORDER_ID`,e.`ORDER_NO` AS LABEL FROM rm_erp_order e "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1 "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + "LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE "
                + "FROM rm_realm_country rc "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=e.RECPIENT_COUNTRY "
                + "LEFT JOIN rm_program p ON p.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID AND p.`PROGRAM_ID`=:programId AND p.PROGRAM_TYPE_ID=1 "
                + "LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + "WHERE p.`REALM_COUNTRY_ID` IS NOT NULL AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15) AND  e.`ORDER_NO` LIKE '%").append(term).append("%' ");
        if (planningUnitId != 0) {
            sb.append(" AND papu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }

        if (linkingType == 1) {
            sb.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sb.append(" GROUP BY e.`ORDER_NO` ");
        sb.append(" ) ");
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ErpOrderAutocompleteDTORowMapper());
    }

    @Override
    public String getSupplyPlanReviewerList(int programId, CustomUserDetails curUser) {
        SimpleProgram p = this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT u.EMAIL_ID "
                + "FROM us_user u "
                + "LEFT JOIN us_user_role ur ON u.USER_ID=ur.USER_ID "
                + "LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID "
                + "LEFT JOIN vw_program p ON p.PROGRAM_ID=:programId "
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
        params.put("realmId", p.getRealmId());
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
//        System.out.println(sb.toString());
//        System.out.println(params);
        List<String> emailList = this.namedParameterJdbcTemplate.queryForList(sb.toString(), params, String.class
        );
        return StringUtils.join(emailList, ",");
    }

    @Override
    public ManualTaggingDTO getShipmentDetailsByParentShipmentId(int parentShipmentId) {
        String sql = "SELECT "
                + "        st.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, st.SHIPMENT_QTY, st.EXPECTED_DELIVERY_DATE, st.PRODUCT_COST, st.SKU_CODE, "
                + "        st.PROCUREMENT_AGENT_ID, st.PROCUREMENT_AGENT_CODE, st.`COLOR_HTML_CODE`, st.`PROCUREMENT_AGENT_LABEL_ID`, st.`PROCUREMENT_AGENT_LABEL_EN`, st.`PROCUREMENT_AGENT_LABEL_FR`, st.`PROCUREMENT_AGENT_LABEL_SP`, st.`PROCUREMENT_AGENT_LABEL_PR`, "
                + "        st.FUNDING_SOURCE_ID, st.FUNDING_SOURCE_CODE, st.`FUNDING_SOURCE_LABEL_ID`, st.`FUNDING_SOURCE_LABEL_EN`, st.`FUNDING_SOURCE_LABEL_FR`, st.`FUNDING_SOURCE_LABEL_SP`, st.`FUNDING_SOURCE_LABEL_PR`, "
                + "        st.BUDGET_ID, st.BUDGET_CODE, st.`BUDGET_LABEL_ID`, st.`BUDGET_LABEL_EN`, st.`BUDGET_LABEL_FR`, st.`BUDGET_LABEL_SP`, st.`BUDGET_LABEL_PR`, "
                + "        st.SHIPMENT_STATUS_ID, st.`SHIPMENT_STATUS_LABEL_ID`, st.`SHIPMENT_STATUS_LABEL_EN`, st.`SHIPMENT_STATUS_LABEL_FR`, st.`SHIPMENT_STATUS_LABEL_SP`, st.`SHIPMENT_STATUS_LABEL_PR`, "
                + "        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`, "
                + "        st.ORDER_NO, st.PRIME_LINE_NO,st.`NOTES` "
                + "FROM ( "
                + "        SELECT "
                + "            s.SHIPMENT_ID, st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE,st.SHIPMENT_QTY, st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.ACCOUNT_FLAG, st.SHIPMENT_TRANS_ID, papu.SKU_CODE, "
                + "            pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`COLOR_HTML_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, "
                + "            fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, "
                + "            shs.SHIPMENT_STATUS_ID, shs.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shs.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shs.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shs.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shs.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, "
                + "            sc.CURRENCY_ID `SHIPMENT_CURRENCY_ID`, sc.`CURRENCY_CODE` `SHIPMENT_CURRENCY_CODE`, s.CONVERSION_RATE_TO_USD `SHIPMENT_CONVERSION_RATE_TO_USD`, "
                + "            sc.LABEL_ID `SHIPMENT_CURRENCY_LABEL_ID`, sc.LABEL_EN `SHIPMENT_CURRENCY_LABEL_EN`, sc.LABEL_FR `SHIPMENT_CURRENCY_LABEL_FR`, sc.LABEL_SP `SHIPMENT_CURRENCY_LABEL_SP`, sc.LABEL_PR `SHIPMENT_CURRENCY_LABEL_PR`, "
                + "            st.ACTIVE, st.`ORDER_NO`, st.`PRIME_LINE_NO`, "
                + "            b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID `BUDGET_LABEL_ID`, b.LABEL_EN `BUDGET_LABEL_EN`, b.LABEL_FR `BUDGET_LABEL_FR`, b.LABEL_SP `BUDGET_LABEL_SP`, b.LABEL_PR `BUDGET_LABEL_PR`, "
                + "            st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,st.`NOTES` "
                + "FROM ( "
                + "    SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE st.`SHIPMENT_ID`=? GROUP BY st.SHIPMENT_ID "
                + ") ts "
                + "    LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID "
                + "    LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID "
                + "    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "    LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
                + "    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
                + "    LEFT JOIN vw_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID "
                + "    LEFT JOIN vw_currency sc ON s.CURRENCY_ID=sc.CURRENCY_ID "
                + "    LEFT JOIN vw_budget b ON st.BUDGET_ID=b.BUDGET_ID "
                + "    LEFT JOIN rm_manual_tagging mt ON mt.SHIPMENT_ID=ts.SHIPMENT_ID AND mt.ACTIVE "
                + "    LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID AND papu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID "
                + "    WHERE st.`SHIPMENT_ID`=? group by st.`SHIPMENT_TRANS_ID` "
                + ") st ";
        try {
            return this.jdbcTemplate.queryForObject(sql, new ManualTaggingDTORowMapper(), parentShipmentId, parentShipmentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int checkPreviousARTMISPlanningUnitId(String orderNo, int primeLineNo) {
        String sql = "SELECT papu.`PLANNING_UNIT_ID` FROM ( "
                + "SELECT e.`ERP_ORDER_ID`,e.`PLANNING_UNIT_SKU_CODE` FROM rm_erp_order e "
                + "WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? "
                + "ORDER BY e.`ERP_ORDER_ID` DESC "
                + "LIMIT 2) t "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=t.PLANNING_UNIT_SKU_CODE "
                + "ORDER BY t.ERP_ORDER_ID ASC LIMIT 1;";
        return this.jdbcTemplate.queryForObject(sql, Integer.class,
                orderNo, primeLineNo);
    }

    @Override
    public List<NotificationSummaryDTO> getNotificationSummary(CustomUserDetails curUser) {
        String programIds = "", sql;
        List<Program> programList = this.getProgramList(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, true);
        for (Program p : programList) {
            programIds = programIds + p.getProgramId() + ",";
        }
        programIds = programIds.substring(0, programIds.lastIndexOf(","));
        sql = "SELECT s.`PROGRAM_ID`,l.`LABEL_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_SP`,l.`LABEL_PR`,COUNT(DISTINCT(n.`NOTIFICATION_ID`)) as NOTIFICATION_COUNT FROM rm_erp_notification n "
                + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=n.`CHILD_SHIPMENT_ID` "
                + "LEFT JOIN rm_program p ON p.`PROGRAM_ID`=s.`PROGRAM_ID` AND p.PROGRAM_TYPE_ID=" + GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN + " "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + "WHERE n.`ACTIVE` AND n.`ADDRESSED`=0 "
                + "AND s.`PROGRAM_ID` IN (" + programIds + ") "
                + "GROUP BY s.`PROGRAM_ID` ;";
        return this.jdbcTemplate.query(sql, new NotificationSummaryDTORowMapper());
    }

    public String getMaxERPOrderIdFromERPShipment(String orderNo, int primeLineNo) {
        String sql = "SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=? AND s.`PRIME_LINE_NO`=?;";
        return this.jdbcTemplate.queryForObject(sql, String.class,
                orderNo, primeLineNo);
    }

    @Override
    @Transactional
    public int tab3ShipmentCreation(int shipmentId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        logger.info("ERP Linking : Tab3 new shipment creation---" + shipmentId);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_erp_tab3_shipments").usingGeneratedKeyColumns("TAB3_SHIPMENT_ID");
        params.put("SHIPMENT_ID", shipmentId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        logger.info("ERP Linking : Tab3 new shipment creation params---" + params);
        int id = si.executeAndReturnKey(params).intValue();
        logger.info("ERP Linking : Tab3 new shipment creation completed---" + id);
        return id;
    }

    @Override
    public List<DatasetPlanningUnit> getDatasetPlanningUnitList(int programId, int versionId) {
        String sqlString = "SELECT "
                + "    dpu.PROGRAM_PLANNING_UNIT_ID, dpu.CONSUMPTION_FORECAST, dpu.TREE_FORECAST, dpu.PLANNING_UNIT_NOTES, dpu.CONSUMPTION_NOTES, dpu.HIGHER_THEN_CONSUMPTION_THRESHOLD, dpu.LOWER_THEN_CONSUMPTION_THRESHOLD, "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER `PU_MULTIPLIER_FOR_FU`, "
                + "    puu.UNIT_ID `PUU_UNIT_ID`, puu.LABEL_ID `PUU_LABEL_ID`, puu.LABEL_EN `PUU_LABEL_EN`, puu.LABEL_FR `PUU_LABEL_FR`, puu.LABEL_SP `PUU_LABEL_SP`, puu.LABEL_PR `PUU_LABEL_PR`, puu.UNIT_CODE `PUU_UNIT_CODE`, "
                + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "    fuu.UNIT_ID `FUU_UNIT_ID`, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, fuu.UNIT_CODE `FUU_UNIT_CODE`, "
                + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`,  "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`,  "
                + "    dpu.STOCK, dpu.EXISTING_SHIPMENTS, dpu.MONTHS_OF_STOCK, dpu.PRICE, dpu.CONSUMPTION_DATA_TYPE_ID, "
                + "    pa.PROCUREMENT_AGENT_ID, pa.LABEL_ID `PA_LABEL_ID`, pa.LABEL_EN `PA_LABEL_EN`, pa.LABEL_FR `PA_LABEL_FR`, pa.LABEL_SP `PA_LABEL_SP`, pa.LABEL_PR `PA_LABEL_PR`, pa.PROCUREMENT_AGENT_CODE, "
                + "    l.LABEL_ID `OU_LABEL_ID`, l.LABEL_EN `OU_LABEL_EN`, l.LABEL_FR `OU_LABEL_FR`, l.LABEL_SP `OU_LABEL_SP`, l.LABEL_PR `OU_LABEL_PR`, dpu.OTHER_MULTIPLIER `OU_MULTIPLIER_FOR_FU`, "
                + "    dpus.REGION_ID, dpus.TREE_ID, dpus.SCENARIO_ID, dpus.CONSUMPTION_EXTRAPOLATION_ID, SUM(COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT)) `TOTAL_FORECAST`, dpus.NOTES `SELECTED_NOTES`, "
                + "    dpu.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, dpu.ACTIVE "
                + " FROM rm_dataset_planning_unit dpu  "
                + " LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID"
                + " LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + " LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID"
                + " LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + " LEFT JOIN vw_procurement_agent pa ON dpu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
                + " LEFT JOIN ap_label l ON dpu.OTHER_LABEL_ID=l.LABEL_ID "
                + " LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID "
                + " LEFT JOIN rm_program_version pv ON pv.PROGRAM_ID=dpu.PROGRAM_ID AND pv.VERSION_ID=dpu.VERSION_ID "
                + " LEFT JOIN "
                + "   ( "
                + "   SELECT "
                + "     ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndpu.PLANNING_UNIT_ID "
                + "   FROM vw_forecast_tree_node ftn "
                + "   LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID "
                + "   LEFT JOIN rm_forecast_tree_node_data_pu ftndpu ON ftndpu.NODE_DATA_PU_ID=ftnd.NODE_DATA_PU_ID "
                + "   WHERE ftn.NODE_TYPE_ID=5 "
                + " ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND dpu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID "
                + " LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH BETWEEN pv.FORECAST_START_DATE AND pv.FORECAST_STOP_DATE "
                + " LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID "
                + " LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH BETWEEN pv.FORECAST_START_DATE AND pv.FORECAST_STOP_DATE "
                + " LEFT JOIN us_user cb ON dpu.CREATED_BY=cb.USER_ID "
                + " WHERE dpu.PROGRAM_ID=:programId and dpu.VERSION_ID=:versionId"
                + " GROUP BY dpu.PLANNING_UNIT_ID, dpus.REGION_ID";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DatasetPlanningUnitListResultSetExtractor());
    }

    @Override
    public List<ProgramIdAndVersionId> getLatestVersionForPrograms(String programIds) {
        String sqlString = "SELECT p.CURRENT_VERSION_ID,p.PROGRAM_ID FROM rm_program p WHERE p.PROGRAM_ID IN (" + programIds + ")";
        return this.jdbcTemplate.query(sqlString, new ProgramIdAndVersionIdRowMapper());
    }

    @Override
    public List<SimpleCodeObject> getSimpleProgramListByRealmCountryIdList(String[] realmCountryIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT p.PROGRAM_ID `ID`, p.PROGRAM_CODE `CODE`, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR FROM vw_program p WHERE FIND_IN_SET(p.REALM_COUNTRY_ID, :realmCountryIds) AND p.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryIds", ArrayUtils.convertArrayToString(realmCountryIds));
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" ORDER BY p.PROGRAM_CODE");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<SimpleCodeObject> getSimpleProgramListByProductCategoryIdList(String[] productCategoryIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT GROUP_CONCAT(pc.PRODUCT_CATEGORY_ID) FROM rm_product_category pc LEFT JOIN (SELECT CONCAT(pc1.SORT_ORDER,'%') `SO` FROM rm_product_category pc1 WHERE FIND_IN_SET(pc1.PRODUCT_CATEGORY_ID, :productCategoryIds)) pc2 ON pc.SORT_ORDER LIKE pc2.SO WHERE pc2.SO IS NOT NULL");
        Map<String, Object> params = new HashMap<>();
        params.put("productCategoryIds", ArrayUtils.convertArrayToString(productCategoryIds));
        String finalProductCategoryIds = this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, String.class);
        sqlStringBuilder = null;
        sqlStringBuilder = new StringBuilder("SELECT p.PROGRAM_ID `ID`, p.PROGRAM_CODE `CODE`, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR "
                + "FROM rm_program_planning_unit ppu "
                + "LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                + "WHERE FIND_IN_SET(fu.PRODUCT_CATEGORY_ID, :finalProductCategoryIds) AND ppu.ACTIVE AND pu.ACTIVE AND p.ACTIVE ");
        params.clear();
        params.put("finalProductCategoryIds", finalProductCategoryIds);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append("GROUP BY ppu.PROGRAM_ID ORDER BY p.PROGRAM_CODE");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<TreeAnchorOutput> getTreeAnchorForSync(TreeAnchorInput ta, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT t.TREE_ANCHOR_ID, t.TREE_ID FROM vw_forecast_tree t LEFT JOIN vw_dataset p ON t.PROGRAM_ID=p.PROGRAM_ID WHERE t.PROGRAM_ID=:programId AND FIND_IN_SET(t.TREE_ID, :treeIdList)");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", ta.getProgramId());
        params.put("treeIdList", ArrayUtils.convertArrayToString(ta.getTreeIds()));
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new TreeAnchorOutputRowMapper());

    }

}
