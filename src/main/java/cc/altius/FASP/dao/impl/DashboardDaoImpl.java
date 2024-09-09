/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DashboardDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.ProgramCount;
import cc.altius.FASP.model.report.DashboardInput;
import cc.altius.FASP.model.report.DashboardBottom;
import cc.altius.FASP.model.report.DashboardExpiredPuRowMapper;
import cc.altius.FASP.model.report.DashboardForecastErrorRowMapper;
import cc.altius.FASP.model.report.DashboardPuWithCountRowMapper;
import cc.altius.FASP.model.report.DashboardShipmentDetailsReportByRowMapper;
import cc.altius.FASP.model.report.DashboardTop;
import cc.altius.FASP.model.report.DashboardTopRowMapper;
import cc.altius.FASP.model.report.DashboardStockOutAndExpired;
import cc.altius.FASP.model.report.DashboardStockOutAndExpiredRowMapper;
import cc.altius.FASP.model.report.DashboardStockStatusRowMapper;
import cc.altius.FASP.model.rowMapper.DashboardUserRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramCountRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
        StringBuilder sb = new StringBuilder("SELECT SUM(IF(p.PROGRAM_TYPE_ID=1, 1, 0)) PROGRAM_COUNT, SUM(IF(p.PROGRAM_TYPE_ID=2, 1, 0)) DATASET_COUNT FROM rm_program p LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID` WHERE p.`ACTIVE`");
        this.aclService.addUserAclForRealm(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sb.toString(), params, new ProgramCountRowMapper());
    }

    @Override
    public int getRealmCountryCount(CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM rm_realm_country r WHERE r.`ACTIVE`");
        this.aclService.addUserAclForRealm(sb, params, "r", curUser);
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
    public List<DashboardTop> getDashboardTop(CustomUserDetails curUser) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT "
                + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, "
                + "    SUM(IF(ppu.ACTIVE && pu.ACTIVE, 1, 0)) `ACTIVE_PPU`, SUM(IF(ppu.ACTIVE && pu.ACTIVE, 0, 1)) `DISABLED_PPU`, "
                + "    p.LAST_MODIFIED_DATE, pv.CREATED_DATE `COMMIT_DATE`, "
                + "    vt.VERSION_TYPE_ID, vt.LABEL_ID `VT_LABEL_ID`, vt.LABEL_EN `VT_LABEL_EN`, vt.LABEL_FR `VT_LABEL_FR`, vt.LABEL_SP `VT_LABEL_SP`, vt.LABEL_PR `VT_LABEL_PR`, "
                + "    vs.VERSION_STATUS_ID, vs.LABEL_ID `VS_LABEL_ID`, vs.LABEL_EN `VS_LABEL_EN`, vs.LABEL_FR `VS_LABEL_FR`, vs.LABEL_SP `VS_LABEL_SP`, vs.LABEL_PR `VS_LABEL_PR`, "
                + "    IFNULL(pr.`COUNT_OF_OPEN_PROBLEM`,0) `COUNT_OF_OPEN_PROBLEM` "
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID AND p.CURRENT_VERSION_ID=pv.VERSION_ID "
                + "LEFT JOIN vw_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
                + "LEFT JOIN vw_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID ");

        Map<String, Object> params = new HashMap<>();
        StringBuilder innerString = new StringBuilder("SELECT p.`PROGRAM_ID`, COUNT(pr.`PROBLEM_REPORT_ID`) `COUNT_OF_OPEN_PROBLEM` FROM vw_program p LEFT JOIN rm_problem_report pr ON p.PROGRAM_ID=pr.PROGRAM_ID AND p.CURRENT_VERSION_ID=pr.VERSION_ID WHERE pr.PROBLEM_STATUS_ID=1");
        this.aclService.addFullAclForProgram(innerString, params, "p", curUser);
        innerString.append(" GROUP BY p.PROGRAM_ID");
        sqlBuilder
                .append("LEFT JOIN (")
                .append(innerString)
                .append(") pr ON p.PROGRAM_ID=pr.PROGRAM_ID ")
                .append(" WHERE p.ACTIVE ");
        this.aclService.addFullAclForProgram(sqlBuilder, params, "p", curUser);
        sqlBuilder.append(" GROUP BY p.PROGRAM_ID");
        List<DashboardTop> edList = this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new DashboardTopRowMapper());
        Date curMonth = DateUtils.getStartOfMonthObject();
        Date endMonth = DateUtils.addMonths(curMonth, 17);
        edList.forEach(ed -> {
            String sql = "SELECT "
                    + "    s1.PROGRAM_ID, "
                    + "    SUM(IF(s1.`SUM_EXPIRED_STOCK`>0,1,0)) `PRODUCTS_WITH_EXPIRED_STOCK`, "
                    + "    SUM(IF(s1.`SUM_UNMET_DEMAND`>0,1,0)) `PRODUCTS_WITH_UNMET_DEMAND` "
                    + "FROM "
                    + "    (SELECT sma.PROGRAM_ID, sma.PLANNING_UNIT_ID, SUM(sma.EXPIRED_STOCK) `SUM_EXPIRED_STOCK` , SUM(sma.UNMET_DEMAND) `SUM_UNMET_DEMAND` FROM vw_program p LEFT JOIN rm_supply_plan_amc sma ON p.PROGRAM_ID=sma.PROGRAM_ID AND p.CURRENT_VERSION_ID=sma.VERSION_ID WHERE p.PROGRAM_ID=:programId AND sma.TRANS_DATE BETWEEN :curMonth AND :endMonth group by sma.PROGRAM_ID, sma.PLANNING_UNIT_ID "
                    + ") s1 GROUP BY s1.PROGRAM_ID ";
            Map<String, Object> eParams = new HashMap<>();
            eParams.put("programId", ed.getProgram().getId());
            eParams.put("curMonth", curMonth);
            eParams.put("endMonth", endMonth);
            try {
                DashboardStockOutAndExpired se = this.namedParameterJdbcTemplate.queryForObject(sql, eParams, new DashboardStockOutAndExpiredRowMapper());
                ed.setCountOfExpiredPU(se.getCountOfExpiredPU());
                ed.setCountOfStockOutPU((Integer) se.getCountOfStockOutPU());
            } catch (IncorrectResultSizeDataAccessException irda) {

            }
        });
        return edList;
    }

    @Override
    public DashboardBottom getDashboardBottom(DashboardInput ei, CustomUserDetails curUser) {
        DashboardBottom db = new DashboardBottom();
        String sqlString = "CALL getDashboardStockStatus(:startDate, :stopDate, :programId)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", ei.getProgramId());
        params.put("startDate", ei.getStartDate());
        params.put("stopDate", ei.getStopDate());
        db.setStockStatus(this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DashboardStockStatusRowMapper()));

        sqlString = "SELECT "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, "
                + "    COUNT(amc.PLANNING_UNIT_ID) `COUNT` "
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.ACTIVE "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE "
                + "LEFT JOIN rm_supply_plan_amc amc ON p.PROGRAM_ID=amc.PROGRAM_ID AND p.CURRENT_VERSION_ID=amc.VERSION_ID AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID "
                + "WHERE p.PROGRAM_ID = :programId AND pu.PLANNING_UNIT_ID IS NOT NULL AND amc.TRANS_DATE BETWEEN :startDate AND :stopDate AND amc.CLOSING_BALANCE=0 AND amc.UNMET_DEMAND>0 "
                + "GROUP BY pu.PLANNING_UNIT_ID";
        db.getStockStatus().setPuStockOutList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardPuWithCountRowMapper()));

        sqlString = "SELECT "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, "
                + "    b.BATCH_ID, b.BATCH_NO, b.AUTO_GENERATED, spb.EXPIRY_DATE, SUM(spb.EXPIRED_STOCK) `EXPIRED_STOCK` , ppu.CATALOG_PRICE "
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.ACTIVE "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID AND pu.ACTIVE "
                + "LEFT JOIN rm_supply_plan_batch_qty spb ON p.PROGRAM_ID=spb.PROGRAM_ID AND p.CURRENT_VERSION_ID=spb.VERSION_ID AND spb.TRANS_DATE BETWEEN :startDate and :stopDate AND pu.PLANNING_UNIT_ID=spb.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_batch_info b ON spb.BATCH_ID=b.BATCH_ID "
                + "WHERE p.PROGRAM_ID=:programId AND spb.TRANS_DATE BETWEEN :startDate and :stopDate AND spb.EXPIRED_STOCK>0 "
                + "GROUP BY spb.PLANNING_UNIT_ID, spb.BATCH_ID";
        db.setExpiriesList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardExpiredPuRowMapper()));

        sqlString = "CALL getDashboardShipmentDetailsReportBy(:startDate, :stopDate, :programId, :displayShipmentsBy)";
        params.put("displayShipmentsBy", ei.getDisplayShipmentsBy());
        db.setShipmentDetailsList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardShipmentDetailsReportByRowMapper()));

        sqlString = "CALL getDashboardShipmentWithFundingSourceTbd(:startDate, :stopDate, :programId)";
        db.setShipmentWithFundingSourceTbd(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardPuWithCountRowMapper()));

        sqlString = "CALL getDashboardForecastError(:startDate, :stopDate, :programId)";
        db.setForecastErrorList(this.namedParameterJdbcTemplate.query(sqlString, params, new DashboardForecastErrorRowMapper()));

        return db;
    }

}
