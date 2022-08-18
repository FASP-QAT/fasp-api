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
import cc.altius.FASP.model.rowMapper.DashboardUserRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramCountRowMapper;
import cc.altius.FASP.service.AclService;
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
    
}
