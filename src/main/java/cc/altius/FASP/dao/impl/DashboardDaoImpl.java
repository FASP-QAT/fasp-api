/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DashboardDao;
import cc.altius.FASP.dao.ReportDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramCount;
import cc.altius.FASP.model.report.DashboardActualConsumptionDetails;
import cc.altius.FASP.model.report.DashboardActualConsumptionResultSetExtractor;
import cc.altius.FASP.model.report.DashboardInput;
import cc.altius.FASP.model.report.DashboardBottom;
import cc.altius.FASP.model.report.DashboardExpiredPuRowMapper;
import cc.altius.FASP.model.report.DashboardPuWithCountRowMapper;
import cc.altius.FASP.model.report.DashboardQpl;
import cc.altius.FASP.model.report.DashboardForLoadProgram;
import cc.altius.FASP.model.report.DashboardExpiriesForLoadProgramResultSetExtractor;
import cc.altius.FASP.model.report.DashboardForecastErrorForLoadProgramResultSetExtractor;
import cc.altius.FASP.model.report.DashboardForecastErrorRowMapper;
import cc.altius.FASP.model.report.DashboardQplForLoadProgramResultSetExtractor;
import cc.altius.FASP.model.report.DashboardQplRowMapper;
import cc.altius.FASP.model.report.DashboardShipmentDetailsReportByRowMapper;
import cc.altius.FASP.model.report.DashboardShipmentDetailsReportForLoadProgramResultSetExtractor;
import cc.altius.FASP.model.report.DashboardTop;
import cc.altius.FASP.model.report.DashboardTopRowMapper;
import cc.altius.FASP.model.report.DashboardStockStatusRowMapper;
import cc.altius.FASP.model.report.DashboardStockStatusForLoadProgramResultSetExtractor;
import cc.altius.FASP.model.report.DashboardTopExpiriesForLoadProgramResultSetExtractor;
import cc.altius.FASP.model.report.DashboardTopStockOutForLoadProgramResultSetExtractor;
import cc.altius.FASP.model.rowMapper.DashboardUserRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramCountRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.utils.DateUtils;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class DashboardDaoImpl implements DashboardDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private AclService aclService;
    @Autowired
    private ReportDao reportDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public int getRealmCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM rm_realm r WHERE r.`ACTIVE`");
        this.aclService.addUserAclForRealm(sb, params, "r", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public int getLanguageCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ap_language l WHERE l.`ACTIVE`");
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public int getHealthAreaCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM rm_health_area h WHERE h.`ACTIVE`");
        this.aclService.addUserAclForRealm(sb, params, "h", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public int getOrganisationCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM rm_organisation o WHERE o.`ACTIVE`");
        this.aclService.addUserAclForRealm(sb, params, "o", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public ProgramCount getProgramCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT SUM(IF(p.PROGRAM_TYPE_ID=1, 1, 0)) PROGRAM_COUNT, SUM(IF(p.PROGRAM_TYPE_ID=2, 1, 0)) DATASET_COUNT FROM vw_program p LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID` WHERE p.`ACTIVE`");
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, new ProgramCountRowMapper());
    }

    @Override
    public int getRealmCountryCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM rm_realm_country r WHERE r.`ACTIVE`");
        this.aclService.addUserAclForRealm(sb, params, "r", curUser);
        this.aclService.addUserAclForRealmCountry(sb, params, "r", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public int getRegionCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM rm_region r LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=r.`REALM_COUNTRY_ID` WHERE r.`ACTIVE`");
        this.aclService.addUserAclForRealm(sb, params, "r", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public int getSupplyPlanPendingCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(*) FROM rm_program_version pv "
                + " LEFT JOIN vw_program p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + " LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID` "
                + " WHERE TRUE AND  pv.`VERSION_STATUS_ID`=1 AND pv.`VERSION_TYPE_ID`=2 ");
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, Integer.class);
    }

    @Override
    public List<DashboardUser> getUserListForApplicationLevelAdmin(CustomUserDetails curUser) {
        String sql = "SELECT l.*,COUNT(DISTINCT(u.`USER_ID`)) AS COUNT FROM us_role r "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID` "
                + "LEFT JOIN us_user_role ur ON ur.`ROLE_ID`=r.`ROLE_ID` "
                + "LEFT JOIN us_user u ON u.`USER_ID`=ur.`USER_ID` AND u.`ACTIVE` "
                + "GROUP BY r.`ROLE_ID`";
        return this.jdbcTemplate.query(sql, new DashboardUserRowMapper());
    }

    @Override
    public List<DashboardUser> getUserListForRealmLevelAdmin(CustomUserDetails curUser) {
        String sql = "SELECT l.*,COUNT(DISTINCT(u.`USER_ID`)) AS COUNT FROM us_role r "
                + " LEFT JOIN us_can_create_role c ON c.`CAN_CREATE_ROLE`=r.`ROLE_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID` "
                + " LEFT JOIN us_user_role ur ON ur.`ROLE_ID`=r.`ROLE_ID` "
                + " LEFT JOIN us_user u ON u.`USER_ID`=ur.`USER_ID` AND u.`ACTIVE` AND (u.`REALM_ID`=:realmId OR :realmId=-1) "
                + " WHERE c.`ROLE_ID`='ROLE_REALM_ADMIN' "
                + " GROUP BY r.`ROLE_ID`";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", curUser.getRealm().getRealmId());
        return this.namedParameterJdbcTemplate.query(sql, params, new DashboardUserRowMapper());
    }

    @Override
    public List<DashboardTop> getDashboardTop(String[] programIds, CustomUserDetails curUser) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT "
                + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, "
                + "    SUM(IF(ppu.ACTIVE && pu.ACTIVE, 1, 0)) `ACTIVE_PPU`, SUM(IF(ppu.ACTIVE && pu.ACTIVE, 0, 1)) `DISABLED_PPU`, "
                + "    pv.LAST_MODIFIED_DATE, pv.CREATED_DATE `COMMIT_DATE`, "
                + "    vt.VERSION_TYPE_ID, vt.LABEL_ID `VT_LABEL_ID`, vt.LABEL_EN `VT_LABEL_EN`, vt.LABEL_FR `VT_LABEL_FR`, vt.LABEL_SP `VT_LABEL_SP`, vt.LABEL_PR `VT_LABEL_PR`, "
                + "    vs.VERSION_STATUS_ID, vs.LABEL_ID `VS_LABEL_ID`, vs.LABEL_EN `VS_LABEL_EN`, vs.LABEL_FR `VS_LABEL_FR`, vs.LABEL_SP `VS_LABEL_SP`, vs.LABEL_PR `VS_LABEL_PR`, "
                + "    vs1.VERSION_STATUS_ID AS LATEST_FINAL_VERSION_STATUS_ID, vs1.LABEL_ID `LATEST_FINAL_VS_LABEL_ID`, vs1.LABEL_EN `LATEST_FINAL_VS_LABEL_EN`, vs1.LABEL_FR `LATEST_FINAL_VS_LABEL_FR`, vs1.LABEL_SP `LATEST_FINAL_VS_LABEL_SP`, vs1.LABEL_PR `LATEST_FINAL_VS_LABEL_PR`, "
                + "    p.CURRENT_VERSION_ID AS VERSION_ID,pv2.LAST_MODIFIED_DATE AS LATEST_FINAL_VERSION_LAST_MODIFIED_DATE,  "
                + "    IFNULL(pr.`COUNT_OF_OPEN_PROBLEM`,0) `COUNT_OF_OPEN_PROBLEM` "
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID AND p.CURRENT_VERSION_ID=pv.VERSION_ID "
                + "LEFT JOIN (SELECT MAX(pv.VERSION_ID) AS VERSION_ID,pv.PROGRAM_ID FROM rm_program_version pv WHERE pv.VERSION_TYPE_ID=2  GROUP BY pv.PROGRAM_ID) AS pv1 ON p.PROGRAM_ID=pv1.PROGRAM_ID "
                + "LEFT JOIN rm_program_version pv2 ON pv1.PROGRAM_ID=pv2.PROGRAM_ID AND pv2.VERSION_ID=pv1.VERSION_ID "
                + "LEFT JOIN vw_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
                + "LEFT JOIN vw_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID "
                + "LEFT JOIN vw_version_status vs1 ON pv2.VERSION_STATUS_ID=vs1.VERSION_STATUS_ID ");

        Map<String, Object> params = new HashMap<>();
        StringBuilder innerString = new StringBuilder("SELECT p.`PROGRAM_ID`, COUNT(pr.`PROBLEM_REPORT_ID`) `COUNT_OF_OPEN_PROBLEM` FROM vw_program p LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=p.PROGRAM_ID AND ppu.ACTIVE LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE LEFT JOIN rm_problem_report pr ON p.PROGRAM_ID=pr.PROGRAM_ID AND pr.DATA3=pu.PLANNING_UNIT_ID WHERE pr.PROBLEM_STATUS_ID=1");
        this.aclService.addFullAclForProgram(innerString, params, "p", curUser);
        innerString.append(" GROUP BY p.PROGRAM_ID");
        sqlBuilder
                .append("LEFT JOIN (")
                .append(innerString)
                .append(") pr ON p.PROGRAM_ID=pr.PROGRAM_ID ")
                .append(" WHERE p.ACTIVE AND FIND_IN_SET(p.PROGRAM_ID, :programIds) ");
        params.put("programIds", ArrayUtils.convertArrayToString(programIds));
        this.aclService.addFullAclForProgram(sqlBuilder, params, "p", curUser);
        sqlBuilder.append(" GROUP BY p.PROGRAM_ID");
        List<DashboardTop> edList = this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new DashboardTopRowMapper());
        Date curMonth = DateUtils.getStartOfMonthVariable(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.EST), -1 * curUser.getRealm().getNoOfMonthsInPastForTopDashboard()));
        Date endMonth = DateUtils.getEndOfMonthVariable(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.EST), curUser.getRealm().getNoOfMonthsInFutureForTopDashboard() - 1));
        edList.forEach(ed -> {
            String sql1 = "SELECT SUM(IF(s1.`SUM_STOCK_OUT`>0,1,0)) `PRODUCTS_WITH_STOCK_OUT` FROM (SELECT sma.PROGRAM_ID, sma.PLANNING_UNIT_ID, SUM(IF(sma.MOS=0,1,0)) `SUM_STOCK_OUT` FROM vw_program p LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.ACTIVE LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE LEFT JOIN rm_supply_plan_amc sma ON p.PROGRAM_ID=sma.PROGRAM_ID AND p.CURRENT_VERSION_ID=sma.VERSION_ID AND pu.PLANNING_UNIT_ID=sma.PLANNING_UNIT_ID WHERE p.PROGRAM_ID=:programId AND sma.TRANS_DATE BETWEEN :curMonth AND :endMonth group by sma.PROGRAM_ID, sma.PLANNING_UNIT_ID) s1 GROUP BY s1.PROGRAM_ID";
            Map<String, Object> eParams = new HashMap<>();
            eParams.put("programId", ed.getProgram().getId());
            eParams.put("curMonth", curMonth);
            eParams.put("endMonth", endMonth);
            String sql2 = "SELECT SUM(ROUND(p1.EXPIRED_STOCK*st.RATE,0)) EXPIRED_VALUE "
                    + "    FROM ( "
                    + "        SELECT "
                    + "            p.PROGRAM_ID, p.CURRENT_VERSION_ID, pu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, "
                    + "            b.BATCH_ID, b.BATCH_NO, b.AUTO_GENERATED, spb.EXPIRY_DATE, SUM(spb.EXPIRED_STOCK) `EXPIRED_STOCK` "
                    + "        FROM vw_program p "
                    + "        LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.ACTIVE "
                    + "        LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE "
                    + "        LEFT JOIN rm_supply_plan_batch_qty spb ON p.PROGRAM_ID=spb.PROGRAM_ID AND spb.VERSION_ID=p.CURRENT_VERSION_ID AND spb.TRANS_DATE BETWEEN :curMonth and :endMonth AND pu.PLANNING_UNIT_ID=spb.PLANNING_UNIT_ID "
                    + "        LEFT JOIN rm_batch_info b ON spb.BATCH_ID=b.BATCH_ID "
                    + "        WHERE p.PROGRAM_ID=:programId AND spb.TRANS_DATE BETWEEN :curMonth and :endMonth AND spb.EXPIRED_STOCK>0 "
                    + "        GROUP BY spb.PLANNING_UNIT_ID, spb.BATCH_ID "
                    + "    ) p1 "
                    + "    LEFT JOIN rm_shipment s on s.PROGRAM_ID=p1.PROGRAM_ID "
                    + "    LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=s.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID "
                    + "    LEFT JOIN rm_shipment_trans_batch_info stbi ON stbi.SHIPMENT_TRANS_ID=st.SHIPMENT_TRANS_ID and stbi.BATCH_ID=p1.BATCH_ID "
                    + "	   WHERE stbi.BATCH_ID IS NOT NULL ";
            try {
                ed.setCountOfStockOutPU(this.namedParameterJdbcTemplate.queryForObject(sql1, eParams, Integer.class));
            } catch (Exception e) {
            }

            try {
                ed.setValueOfExpiredPU(this.namedParameterJdbcTemplate.queryForObject(sql2, eParams, Double.class));
            } catch (Exception e) {

            }
        });
        return edList;
    }

    @Override
    public DashboardBottom getDashboardBottom(DashboardInput ei, CustomUserDetails curUser) throws ParseException {
        DashboardBottom db = new DashboardBottom();
        String sqlString = "CALL getDashboardStockStatus(:startDate, :stopDate, :programId)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", ei.getProgramId());
        params.put("startDate", ei.getStartDate());
        params.put("stopDate", DateUtils.getEndOfMonthVariable(ei.getStopDate()));
        params.put("curDate", DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMD));
        params.put("curStartOfMonth", DateUtils.getStartOfMonthString(DateUtils.YMD));
        params.put("curEndOfMonth", DateUtils.getEndOfMonthString(DateUtils.YMD));
        db.setStockStatus(this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DashboardStockStatusRowMapper()));

        sqlString = "CALL getDashboardStockOutCount(:startDate, :stopDate, :programId, -1)";
        db.getStockStatus().setPuStockOutList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardPuWithCountRowMapper()).stream().filter(d -> d.getCount() > 0).collect(Collectors.toList()));

        sqlString = "CALL getDashboardExpiriesList(:startDate, :stopDate, :programId, -1)";
        db.setExpiriesList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardExpiredPuRowMapper()));

        sqlString = "CALL getDashboardShipmentDetailsReportBy(:startDate, :stopDate, :programId, :displayShipmentsBy)";
        params.put("displayShipmentsBy", ei.getDisplayShipmentsBy());
        db.setShipmentDetailsList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardShipmentDetailsReportByRowMapper()));

        sqlString = "CALL getDashboardShipmentWithFundingSourceTbd(:startDate, :stopDate, :programId)";
        db.setShipmentWithFundingSourceTbd(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardPuWithCountRowMapper()));

        sqlString = "CALL getDashboardForecastErrorNew(:startDate, :stopDate, :programId)";
        db.setForecastErrorList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardForecastErrorRowMapper()));

        sqlString = "CALL getDashboardForecastConsumptionProblems(:programId)";
        db.setForecastConsumptionQpl(this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DashboardQplRowMapper()));

        sqlString = "CALL getDashboardActualConsumptionList(:programId)";
        db.setActualConsumptionQpl(this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DashboardQplRowMapper()));

        sqlString = "CALL getDashboardInventoryProblems(:programId)";
        db.setInventoryQpl(this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DashboardQplRowMapper()));

        sqlString = "CALL getDashboardShipmentProblems(:programId)";
        db.setShipmentQpl(this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DashboardQplRowMapper()));

        return db;
    }

    @Override
    public DashboardForLoadProgram getDashboardForLoadProgram(int programId, int versionId, int noOfMonthsInPastForBottom, int noOfMonthsInFutureForBottom, int noOfMonthsInPastForTop, int noOfMonthsInFutureForTop, CustomUserDetails curUser) throws ParseException {
        DashboardForLoadProgram db = new DashboardForLoadProgram();
        db.setCurDate(DateUtils.getCurrentDateObject(DateUtils.EST));
        db.setStartDateBottom(DateUtils.getStartOfMonthVariable(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.EST), -1 * noOfMonthsInPastForBottom)));
        db.setStopDateBottom(DateUtils.getEndOfMonthVariable(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.EST), noOfMonthsInFutureForBottom - 1)));
        db.setStartDateTop(DateUtils.getStartOfMonthVariable(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.EST), -1 * noOfMonthsInPastForTop)));
        db.setStopDateTop(DateUtils.getEndOfMonthVariable(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.EST), noOfMonthsInFutureForTop - 1)));

        String sqlString = "CALL getDashboardStockStatusForLoadProgram(:startDateBottom, :stopDateBottom, :programId, :versionId)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("startDateBottom", db.getStartDateBottom());
        params.put("stopDateBottom", db.getStopDateBottom());
        params.put("startDateTop", db.getStartDateTop());
        params.put("stopDateTop", db.getStopDateTop());
        params.put("curDate", db.getCurDate());
        params.put("curStartOfMonth", DateUtils.getStartOfMonthString(DateUtils.YMD));
        params.put("curEndOfMonth", DateUtils.getEndOfMonthString(DateUtils.YMD));
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardStockStatusForLoadProgramResultSetExtractor(db));

        sqlString = "SELECT p1.*, st.`RATE` FROM (SELECT "
                + "    p.PROGRAM_ID, spb.VERSION_ID, pu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, "
                + "    b.BATCH_ID, b.BATCH_NO, b.AUTO_GENERATED, spb.EXPIRY_DATE, SUM(spb.EXPIRED_STOCK) `EXPIRED_STOCK` "
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.ACTIVE "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE "
                + "LEFT JOIN rm_supply_plan_batch_qty spb ON p.PROGRAM_ID=spb.PROGRAM_ID AND spb.VERSION_ID=:versionId AND spb.TRANS_DATE BETWEEN :startDateBottom and :stopDateBottom AND pu.PLANNING_UNIT_ID=spb.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_batch_info b ON spb.BATCH_ID=b.BATCH_ID "
                + "WHERE p.PROGRAM_ID=:programId AND spb.TRANS_DATE BETWEEN :startDateBottom and :stopDateBottom AND spb.EXPIRED_STOCK>0 "
                + "GROUP BY spb.PLANNING_UNIT_ID, spb.BATCH_ID) p1 "
                + "LEFT JOIN rm_shipment_trans_batch_info stbi ON p1.BATCH_ID=stbi.BATCH_ID "
                + "LEFT JOIN rm_shipment_trans st ON stbi.SHIPMENT_TRANS_ID=st.SHIPMENT_TRANS_ID AND st.VERSION_ID<=p1.VERSION_ID GROUP BY stbi.BATCH_ID ";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardExpiriesForLoadProgramResultSetExtractor(db));

        sqlString = "CALL getDashboardShipmentDetailsReportByForLoadProgram(:startDateBottom, :stopDateBottom, :programId, :versionId, 1)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardShipmentDetailsReportForLoadProgramResultSetExtractor(db, 1));
        sqlString = "CALL getDashboardShipmentDetailsReportByForLoadProgram(:startDateBottom, :stopDateBottom, :programId, :versionId, 2)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardShipmentDetailsReportForLoadProgramResultSetExtractor(db, 2));
        sqlString = "CALL getDashboardShipmentDetailsReportByForLoadProgram(:startDateBottom, :stopDateBottom, :programId, :versionId, 3)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardShipmentDetailsReportForLoadProgramResultSetExtractor(db, 3));
        sqlString = "CALL getDashboardForecastErrorForLoadProgram(:startDateBottom, :stopDateBottom, :programId, :versionId)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardForecastErrorForLoadProgramResultSetExtractor(db));
        sqlString = "CALL getDashboardForecastConsumptionProblemsForLoadProgram(:programId, :versionId, :curStartOfMonth)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardQplForLoadProgramResultSetExtractor(db, 1));
        sqlString = "CALL getDashboardActualConsumptionListForLoadProgram(:programId, :curStartOfMonth)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardQplForLoadProgramResultSetExtractor(db, 2));
        sqlString = "CALL getDashboardInventoryProblemsForLoadProgram(:programId, :versionId, :curEndOfMonth)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardQplForLoadProgramResultSetExtractor(db, 3));
        sqlString = "CALL getDashboardShipmentProblemsForLoadProgram(:programId, :versionId, :curEndOfMonth)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardQplForLoadProgramResultSetExtractor(db, 4));
        sqlString = "CALL getDashboardStockOutCount(:startDateTop, :stopDateTop, :programId, :versionId)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardTopStockOutForLoadProgramResultSetExtractor(db));
        sqlString = "CALL getDashboardExpiriesList(:startDateTop, :stopDateTop, :programId, :versionId)";
        this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardTopExpiriesForLoadProgramResultSetExtractor(db));

        return db;
    }

    @Override
    public int getUserCount(CustomUserDetails curUser) {
        String sql="SELECT COUNT(u.USER_ID) FROM us_user u WHERE u.ACTIVE ";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int getLinkedErpShipmentsCount(CustomUserDetails curUser) {
        StringBuilder sb1 = new StringBuilder("SELECT COUNT(s.SHIPMENT_ID) FROM rm_shipment s "
                + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID=s.MAX_VERSION_ID "
                + "LEFT JOIN vw_program p ON p.PROGRAM_ID=s.PROGRAM_ID "
                + "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=s.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID "
                + "WHERE st.ACCOUNT_FLAG AND st.ACTIVE AND st.ERP_FLAG and ppu.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        params.put("curUser", curUser.getUserId());
        this.aclService.addFullAclForProgram(sb1, params, "p", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb1.toString(), params, Integer.class);
    }
    
    @Override
    public ProgramCount getFullProgramCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT SUM(IF(p.PROGRAM_TYPE_ID=1, 1, 0)) PROGRAM_COUNT, SUM(IF(p.PROGRAM_TYPE_ID=2, 1, 0)) DATASET_COUNT FROM rm_program p WHERE p.`ACTIVE`");
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, new ProgramCountRowMapper());
    }

}
