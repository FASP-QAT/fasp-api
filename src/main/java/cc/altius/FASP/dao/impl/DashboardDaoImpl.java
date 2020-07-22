/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DashboardDao;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author altius
 */
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
                + " WHERE TRUE AND  pv.`VERSION_STATUS_ID`=1; ";
        map.put("SUPPLY_PLAN_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        return map;
    }

    @Override
    public Map<String, Object> getRealmLevelDashboard() {
        Map<String, Object> map = new HashMap<>();
        String sql;
        //Realm Country Count
        sql = "SELECT COUNT(*) FROM rm_realm_country r WHERE r.`ACTIVE`;";
        map.put("REALM_COUNTRY_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        //Technical area
        sql = "SELECT COUNT(*) FROM rm_health_area h WHERE h.`ACTIVE`;";
        map.put("TECHNICAL_AREA_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        //Region
        sql = "SELECT COUNT(*) FROM rm_region r WHERE r.`ACTIVE`;";
        map.put("REGION_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        //Organization
        sql = "SELECT COUNT(*) FROM rm_organisation o WHERE o.`ACTIVE`;";
        map.put("ORGANIZATION_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        //Programs
        sql = "SELECT COUNT(*) FROM rm_program p WHERE p.`ACTIVE`;";
        map.put("PROGRAM_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));
        //Supply plan waiting for approval
        sql = " SELECT COUNT(*) FROM rm_program_version pv "
                + " LEFT JOIN rm_program p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + " WHERE TRUE AND  pv.`VERSION_STATUS_ID`=1; ";
        map.put("SUPPLY_PLAN_COUNT", this.jdbcTemplate.queryForObject(sql, Integer.class));

        return map;
    }

}
