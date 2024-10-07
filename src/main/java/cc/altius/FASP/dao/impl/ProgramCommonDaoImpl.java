/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.UpdateProgramInfoOutput;
import cc.altius.FASP.model.report.UpdateProgramInfoOutputRowMapper;
import cc.altius.FASP.model.rowMapper.MultipleProgramVersionDropDownResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleProgramResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.model.rowMapper.VersionDropDownRowMapper;
import cc.altius.FASP.service.AclService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akil
 */
@Repository
public class ProgramCommonDaoImpl implements ProgramCommonDao {

    @Autowired
    private AclService aclService;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public static final String sqlSimpleProgramString = "SELECT "
            + "	p.`PROGRAM_ID` `ID`, p.`PROGRAM_CODE` `CODE`, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_PR, p.LABEL_SP, "
            + "	rc.`REALM_COUNTRY_ID` `RC_ID`, c.`COUNTRY_CODE` `RC_CODE`, c.LABEL_ID `RC_LABEL_ID`, c.LABEL_EN `RC_LABEL_EN`, c.LABEL_FR `RC_LABEL_FR`, c.LABEL_SP `RC_LABEL_SP`, c.LABEL_PR `RC_LABEL_PR`, "
            + "	r.`REGION_ID` `R_ID`, r.`LABEL_ID` `R_LABEL_ID`, r.`LABEL_EN` `R_LABEL_EN`, r.`LABEL_FR` `R_LABEL_FR`, r.`LABEL_SP` `R_LABEL_SP`, r.`LABEL_PR` `R_LABEL_PR`, "
            + "	ha.`HEALTH_AREA_ID` `HA_ID`, ha.`HEALTH_AREA_CODE` `HA_CODE`, ha.`LABEL_ID` `HA_LABEL_ID`, ha.`LABEL_EN` `HA_LABEL_EN`, ha.`LABEL_FR` `HA_LABEL_FR`, ha.`LABEL_SP` `HA_LABEL_SP`, ha.`LABEL_PR` `HA_LABEL_PR`, "
            + "	o.`ORGANISATION_ID` `O_ID`, o.`ORGANISATION_CODE` `O_CODE`, o.`LABEL_ID` `O_LABEL_ID`, o.`LABEL_EN` `O_LABEL_EN`, o.`LABEL_FR` `O_LABEL_FR`, o.`LABEL_SP` `O_LABEL_SP`, o.`LABEL_PR` `O_LABEL_PR`, "
            + "	p.CURRENT_VERSION_ID, p.ACTIVE, p.PROGRAM_TYPE_ID, rc.REALM_ID "
            + "FROM vw_all_program p "
            + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
            + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + "LEFT JOIN rm_program_region pr ON p.PROGRAM_ID=pr.PROGRAM_ID "
            + "LEFT JOIN vw_region r ON pr.REGION_ID=r.REGION_ID "
            + "LEFT JOIN vw_health_area ha ON FIND_IN_SET(ha.HEALTH_AREA_ID, p.HEALTH_AREA_ID) "
            + "LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
            + "WHERE TRUE ";

    @Override
    public Program getFullProgramById(int programId, int programTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder;
        if (programTypeId == -1) {
            Map<String, Object> param = new HashMap<>();
            param.put("programId", programId);
            try {
                programTypeId = this.namedParameterJdbcTemplate.queryForObject("SELECT p.PROGRAM_TYPE_ID FROM rm_program p WHERE p.PROGRAM_ID=:programId", param, Integer.class).intValue();
            } catch (EmptyResultDataAccessException erda) {
                return null;
            }
        }
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlProgramListString);
        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlDatasetListString);
        } else {
            return null;
        }
        sqlStringBuilder.append(" AND p.PROGRAM_ID=:programId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        sqlStringBuilder.append(ProgramDaoImpl.sqlOrderBy);
        Program p = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramResultSetExtractor());
        if (p == null) {
            return null;
        }
        logger.info("p=" + p);
        if (this.aclService.checkAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getRealmCountry().getRealmCountryId(), p.getHealthAreaIdList(), p.getOrganisation().getId(), p.getProgramId())) {
            logger.info("Going to return the Program object");
            return p;
        } else {
            logger.info("Going to return null");
            return null;
        }
    }

    @Override
    public SimpleProgram getSimpleProgramById(int programId, int programTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(sqlSimpleProgramString).append(" AND p.PROGRAM_ID=:programId AND (p.PROGRAM_TYPE_ID=:programTypeId OR :programTypeId=0)");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("programTypeId", programTypeId);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        SimpleProgram sp = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleProgramResultSetExtractor());
        if (sp == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return sp;
    }

//    @Override
//    public Program getBasicProgramById(int programId, int programTypeId, CustomUserDetails curUser) {
//        StringBuilder sqlStringBuilder;
//        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
//            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlProgramListBasicString);
//        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
//            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlDatasetListBasicString);
//        } else {
//            sqlStringBuilder = new StringBuilder(ProgramDaoImpl.sqlAllProgramListBasicString);
//        }
//        sqlStringBuilder.append(" AND p.PROGRAM_ID=:programId");
//        Map<String, Object> params = new HashMap<>();
//        params.put("programId", programId);
//        sqlStringBuilder.append(ProgramDaoImpl.sqlOrderByBasic);
//        Program p = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramBasicResultSetExtractor());
//        if (p == null) {
//            throw new EmptyResultDataAccessException(1);
//        }
//        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), p.getProgramId(), p.getHealthAreaIdList(), p.getOrganisation().getId())) {
//            return p;
//        } else {
//            return null;
//        }
//    }
    @Override
    public List<Version> getVersionListForProgramId(int programTypeId, int programId, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT pv.VERSION_ID, vt.VERSION_TYPE_ID, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, vt.LABEL_ID `VERSION_TYPE_LABEL_ID`, vt.LABEL_EN `VERSION_TYPE_LABEL_EN`, vt.LABEL_FR `VERSION_TYPE_LABEL_FR`, vt.LABEL_SP `VERSION_TYPE_LABEL_SP`, vt.LABEL_PR `VERSION_TYPE_LABEL_PR`, vs.VERSION_STATUS_ID, vs.LABEL_ID `VERSION_STATUS_LABEL_ID`, vs.LABEL_EN `VERSION_STATUS_LABEL_EN`, vs.LABEL_FR `VERSION_STATUS_LABEL_FR`, vs.LABEL_SP `VERSION_STATUS_LABEL_SP`, vs.LABEL_PR `VERSION_STATUS_LABEL_PR`, pv.CREATED_DATE FROM ");
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            stringBuilder.append("vw_program");
        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
            stringBuilder.append("vw_dataset");
        } else {
            stringBuilder.append("rm_program");
        }
        stringBuilder.append(" p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID LEFT JOIN vw_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID LEFT JOIN vw_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID WHERE p.PROGRAM_ID=:programId ");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new VersionDropDownRowMapper());
    }

    @Override
    public Map<Integer, List<Version>> getVersionListForPrograms(int programTypeId, String[] programIds, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT p.`PROGRAM_ID` `ID`, p.`PROGRAM_CODE` `CODE`, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_PR, p.LABEL_SP, p.CURRENT_VERSION_ID, pv.VERSION_ID, vt.VERSION_TYPE_ID, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, vt.LABEL_ID `VERSION_TYPE_LABEL_ID`, vt.LABEL_EN `VERSION_TYPE_LABEL_EN`, vt.LABEL_FR `VERSION_TYPE_LABEL_FR`, vt.LABEL_SP `VERSION_TYPE_LABEL_SP`, vt.LABEL_PR `VERSION_TYPE_LABEL_PR`, vs.VERSION_STATUS_ID, vs.LABEL_ID `VERSION_STATUS_LABEL_ID`, vs.LABEL_EN `VERSION_STATUS_LABEL_EN`, vs.LABEL_FR `VERSION_STATUS_LABEL_FR`, vs.LABEL_SP `VERSION_STATUS_LABEL_SP`, vs.LABEL_PR `VERSION_STATUS_LABEL_PR`, pv.CREATED_DATE FROM ");
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            stringBuilder.append("vw_program");
        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
            stringBuilder.append("vw_dataset");
        } else {
            stringBuilder.append("rm_program");
        }
        stringBuilder.append(" p LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID LEFT JOIN vw_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID LEFT JOIN vw_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID WHERE FIND_IN_SET(p.PROGRAM_ID, :programIds) ");
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", String.join(",", programIds));
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new MultipleProgramVersionDropDownResultSetExtractor());
    }

    //active field -> 1 for Active, 0 for Disabled, -1 for Any
    @Override
    public List<UpdateProgramInfoOutput> getUpdateProgramInfoReport(int programTypeId, int realmCountryId, int active, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sb = new StringBuilder("SELECT  "
                + "	p1.*,  "
                + "    null `REG_ID`, null `REG_LABEL_ID`, GROUP_CONCAT(r.LABEL_EN SEPARATOR ', ') `REG_LABEL_EN`, GROUP_CONCAT(r.LABEL_FR SEPARATOR ', ') `REG_LABEL_FR`, GROUP_CONCAT(r.LABEL_SP SEPARATOR ', ') `REG_LABEL_SP`, GROUP_CONCAT(r.LABEL_PR SEPARATOR ', ') `REG_LABEL_PR` "
                + "FROM ( "
                + "	SELECT  "
                + "		p.PROGRAM_ID `ID`, p.PROGRAM_CODE `CODE`, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR, "
                + "		r.REALM_ID `R_ID`, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`, "
                + "		rc.REALM_COUNTRY_ID `RC_ID`, c.LABEL_ID `RC_LABEL_ID`, c.LABEL_EN `RC_LABEL_EN`, c.LABEL_FR `RC_LABEL_FR`, c.LABEL_SP `RC_LABEL_SP`, c.LABEL_PR `RC_LABEL_PR`, "
                + "		o.ORGANISATION_ID `O_ID`, o.LABEL_ID `O_LABEL_ID`, o.LABEL_EN `O_LABEL_EN`, o.LABEL_FR `O_LABEL_FR`, o.LABEL_SP `O_LABEL_SP`, o.LABEL_PR `O_LABEL_PR`, "
                + "		null `HA_ID`, null HA_LABEL_ID, GROUP_CONCAT(ha.LABEL_EN SEPARATOR ', ') `HA_LABEL_EN`, GROUP_CONCAT(ha.LABEL_FR SEPARATOR ', ') `HA_LABEL_FR`, GROUP_CONCAT(ha.LABEL_SP SEPARATOR ', ') `HA_LABEL_SP`, GROUP_CONCAT(ha.LABEL_PR SEPARATOR ', ') `HA_LABEL_PR`, "
                + "             p.PROGRAM_NOTES, pm.USER_ID `PM_USER_ID`, pm.USERNAME `PM_USERNAME`,  "
                + "             lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE, p.ACTIVE ");
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sb.append("	FROM vw_program p  ");
        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
            sb.append("	FROM vw_dataset p  ");
        }
        sb.append("	LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "	LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "	LEFT JOIN vw_realm r ON rc.REALM_ID=r.REALM_ID "
                + "	LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "     LEFT JOIN vw_health_area ha ON FIND_IN_SET(ha.HEALTH_AREA_ID, p.HEALTH_AREA_ID) "
                + "     LEFT JOIN us_user lmb ON lmb.USER_ID=p.LAST_MODIFIED_BY "
                + "     LEFT JOIN us_user pm ON pm.USER_ID=p.PROGRAM_MANAGER_USER_ID "
                + "	WHERE (p.REALM_COUNTRY_ID=:realmCountryId OR :realmCountryId=-1) AND (:active=p.ACTIVE OR :active=-1) "
                + "	GROUP BY p.PROGRAM_ID "
                + ") p1  ");
        if (programTypeId == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
            sb.append("LEFT JOIN vw_program p ON p1.ID=p.PROGRAM_ID ");
        } else if (programTypeId == GlobalConstants.PROGRAM_TYPE_DATASET) {
            sb.append("LEFT JOIN vw_dataset p ON p1.ID=p.PROGRAM_ID ");
        }
        sb.append("LEFT JOIN rm_program_region pr ON p1.ID=pr.PROGRAM_ID "
                + "LEFT JOIN vw_region r ON pr.REGION_ID=r.REGION_ID "
                + "WHERE TRUE ");
        params.put("realmCountryId", realmCountryId);
        params.put("active", active);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" GROUP BY p1.ID");
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new UpdateProgramInfoOutputRowMapper());
    }

    @Override
    public SimpleCodeObject getSimpleSupplyPlanProgramById(int programId, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT p.PROGRAM_ID ID, p.PROGRAM_CODE CODE, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR FROM vw_program p WHERE p.PROGRAM_ID=:programId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        logger.info("Sql for simple supply plan program by Id "+stringBuilder.toString());
        return this.namedParameterJdbcTemplate.queryForObject(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

}
