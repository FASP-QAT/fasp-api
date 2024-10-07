/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.AclDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SecurityRequestMatcher;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.rowMapper.SecurityRequestMatcherRowMapper;
import cc.altius.FASP.model.rowMapper.UserAclBasicRowMapper;
import cc.altius.FASP.model.rowMapper.UserAclRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akil
 */
@Repository
public class AclDaoImpl implements AclDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int buildSecurity() {
        String sqlString = "TRUNCATE TABLE ap_security";
        this.jdbcTemplate.update(sqlString);

        sqlString = "SELECT SECURITY_ID, METHOD, URL_LIST, BF_LIST FROM temp_security";
        List<SecurityRequestMatcher> secList = this.jdbcTemplate.query(sqlString, new SecurityRequestMatcherRowMapper());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ap_security");
        int fail = 0;
        for (SecurityRequestMatcher sec : secList) {
            for (String url : sec.getUrlList().split("~")) {
                for (String bf : sec.getBfList().split("~")) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("METHOD", sec.getMethod());
                    params.put("URL", url);
                    params.put("BF", bf);
                    try {
                        si.execute(params);
                    } catch (DuplicateKeyException d) {
//                        logger.error("Duplicate Key Error while trying to add ap_security", d);
                    } catch (DataIntegrityViolationException de) {
                        logger.error("Failed to build for " + params, de);
                        fail++;
                    }
                }
            }
        }
        return fail;
    }

    @Override
    public List<UserAcl> expandUserAccess(UserAcl acl, CustomUserDetails curUser) {
        String sql = "SELECT  "
                + "    :newRoleId `ROLE_ID` "
                + "    , IF(:newRealmCountryId=-1, curUserAcl.REALM_COUNTRY_ID, :newRealmCountryId) `REALM_COUNTRY_ID` "
                + "    , IF(:newHealthAreaId=-1, curUserAcl.HEALTH_AREA_ID, :newHealthAreaId) `HEALTH_AREA_ID` "
                + "    , IF(:newOrganisationId=-1, curUserAcl.ORGANISATION_ID, :newOrganisationId) `ORGANISATION_ID` "
                + "    , IF(:newProgramId=-1, curUserAcl.PROGRAM_ID, :newProgramId) `PROGRAM_ID` "
                + "FROM us_user_acl curUserAcl  "
                + "LEFT JOIN us_can_create_role ccr ON curUserAcl.ROLE_ID=ccr.ROLE_ID  "
                + "WHERE  "
                + "    curUserAcl.USER_ID=:curUser  "
                + "    AND ccr.CAN_CREATE_ROLE=:newRoleId  "
                + "    AND (curUserAcl.REALM_COUNTRY_ID is null OR curUserAcl.REALM_COUNTRY_ID=:newRealmCountryId OR :newRealmCountryId=-1)  "
                + "    AND (curUserAcl.HEALTH_AREA_ID IS NULL OR curUserAcl.HEALTH_AREA_ID=:newHealthAreaId OR :newHealthAreaId=-1)  "
                + "    AND (curUserAcl.ORGANISATION_ID IS NULL OR curUserAcl.ORGANISATION_ID=:newOrganisationId OR :newOrganisationId=-1)  "
                + "    AND (curUserAcl.PROGRAM_ID IS NULL OR curUserAcl.PROGRAM_ID=:newProgramId OR :newProgramId=-1)";
        Map<String, Object> params = new HashMap<>();
        params.put("newRoleId", acl.getRoleId());
        params.put("newRealmCountryId", acl.getRealmCountryId());
        params.put("newHealthAreaId", acl.getHealthAreaId());
        params.put("newOrganisationId", acl.getOrganisationId());
        params.put("newProgramId", acl.getProgramId());
        params.put("curUser", curUser.getUserId());
        return this.namedParameterJdbcTemplate.query(sql, params, new UserAclBasicRowMapper());
    }

}
