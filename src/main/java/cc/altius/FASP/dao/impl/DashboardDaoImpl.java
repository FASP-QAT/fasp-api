/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DashboardDao;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.rowMapper.DashboardUserRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Map<String, Object> getApplicationLevelDashboard() {
        Map<String, Object> map = new HashMap<>();
        String sql;
        //Realm Count
        sql = "SELECT COUNT(*) FROM rm_realm r WHERE r.`ACTIVE`;";
        map.put("REALM_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        //Language Count
        sql = "SELECT COUNT(*) FROM ap_language l WHERE l.`ACTIVE`;";
        map.put("LANGUAGE_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        //Supply plan waiting for approval
        sql = " SELECT COUNT(*) FROM rm_program_version pv "
                + " LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + " WHERE TRUE AND  pv.`VERSION_STATUS_ID`=1 AND pv.`VERSION_TYPE_ID`=2; ";
        map.put("SUPPLY_PLAN_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        return map;
    }

    @Override
    public Map<String, Object> getRealmLevelDashboard(int realmId) {
        Map<String, Object> map = new HashMap<>();
        String sql;
        //Realm Country Count
        sql = "SELECT COUNT(*) FROM rm_realm_country r WHERE r.`ACTIVE` AND r.`REALM_ID`=?;";
        map.put("REALM_COUNTRY_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class, realmId));
        //Technical area
        sql = "SELECT COUNT(*) FROM rm_health_area h WHERE h.`ACTIVE` AND h.`REALM_ID`=?;";
        map.put("TECHNICAL_AREA_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class, realmId));
        //Region
        sql = "SELECT COUNT(*) FROM rm_region r "
                + "LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=r.`REALM_COUNTRY_ID` "
                + "WHERE r.`ACTIVE` AND rc.`REALM_ID`=? "
                + "GROUP BY rc.`REALM_ID`;";
        map.put("REGION_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class, realmId));
        //Organization
        sql = "SELECT COUNT(*) FROM rm_organisation o WHERE o.`ACTIVE` AND o.`REALM_ID`=?;";
        map.put("ORGANIZATION_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class, realmId));
        //Programs
        sql = "SELECT COUNT(*) FROM rm_program p "
                + "LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID` "
                + "WHERE p.`ACTIVE` AND rc.`REALM_ID`=?;";
        map.put("PROGRAM_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class, realmId));
        //Supply plan waiting for approval
        sql = " SELECT COUNT(*) FROM rm_program_version pv "
                + " LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + " LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID` "
                + " WHERE TRUE AND  pv.`VERSION_STATUS_ID`=1 AND pv.`VERSION_TYPE_ID`=2  AND rc.`REALM_ID`=?; ";
        map.put("SUPPLY_PLAN_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class, realmId));

        return map;
    }

    @Override
    public List<DashboardUser> getUserListForApplicationLevelAdmin() {
        String sql = "SELECT l.*,COUNT(DISTINCT(u.`USER_ID`)) AS COUNT FROM us_role r "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID` "
                + "LEFT JOIN us_user_role ur ON ur.`ROLE_ID`=r.`ROLE_ID` "
                + "LEFT JOIN us_user u ON u.`USER_ID`=ur.`USER_ID` AND u.`ACTIVE` "
                + "GROUP BY r.`ROLE_ID`;";
        return this.jdbcTemplate.query(sql, new DashboardUserRowMapper());
    }

    @Override
    public List<DashboardUser> getUserListForRealmLevelAdmin(int realmId) {
        String sql = "SELECT l.*,COUNT(DISTINCT(u.`USER_ID`)) AS COUNT FROM us_role r "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID` "
                + "LEFT JOIN us_user_role ur ON ur.`ROLE_ID`=r.`ROLE_ID` "
                + "LEFT JOIN us_user u ON u.`USER_ID`=ur.`USER_ID` AND u.`ACTIVE` AND u.`REALM_ID`=? "
                + "WHERE r.`ROLE_ID` != 'ROLE_APPLICATION_ADMIN' "
                + "GROUP BY r.`ROLE_ID`;";
        return this.jdbcTemplate.query(sql, new DashboardUserRowMapper(), realmId);
    }

}
