/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.IntegrationProgramDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.IntegrationProgram;
import cc.altius.FASP.model.ManualIntegration;
import cc.altius.FASP.model.report.ManualJsonPushReportInput;
import cc.altius.FASP.model.rowMapper.IntegrationProgramRowMapper;
import cc.altius.FASP.model.rowMapper.ManualIntegrationRowMapper;
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

/**
 *
 * @author akil
 */
@Repository
public class IntegrationProgramDaoImpl implements IntegrationProgramDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private AclService aclService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    private final String sqlListString = "SELECT  "
            + "    ip.INTEGRATION_PROGRAM_ID, ip.ACTIVE, ip.CREATED_DATE, ip.LAST_MODIFIED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, "
            + "    p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, "
            + "    i.INTEGRATION_ID, i.INTEGRATION_NAME, i.FOLDER_LOCATION, i.FILE_NAME, "
            + "    r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_SP `REALM_LABEL_SP`, r.LABEL_PR `REALM_LABEL_PR`, "
            + "    iv.INTEGRATION_VIEW_ID, iv.INTEGRATION_VIEW_NAME, iv.INTEGRATION_VIEW_DESC, "
            + "    vt.VERSION_TYPE_ID, vt.LABEL_ID `VERSION_TYPE_LABEL_ID`, vt.LABEL_EN `VERSION_TYPE_LABEL_EN`, vt.LABEL_FR `VERSION_TYPE_LABEL_FR`, vt.LABEL_SP `VERSION_TYPE_LABEL_SP`, vt.LABEL_PR `VERSION_TYPE_LABEL_PR`, "
            + "    vs.VERSION_STATUS_ID, vs.LABEL_ID `VERSION_STATUS_LABEL_ID`, vs.LABEL_EN `VERSION_STATUS_LABEL_EN`, vs.LABEL_FR `VERSION_STATUS_LABEL_FR`, vs.LABEL_SP `VERSION_STATUS_LABEL_SP`, vs.LABEL_PR `VERSION_STATUS_LABEL_PR` "
            + "FROM rm_integration_program ip "
            + "LEFT JOIN ap_integration i on ip.INTEGRATION_ID=i.INTEGRATION_ID "
            + "LEFT JOIN vw_program p ON ip.PROGRAM_ID=p.PROGRAM_ID "
            + "LEFT JOIN vw_realm r ON i.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_integration_view iv ON i.INTEGRATION_VIEW_ID=iv.INTEGRATION_VIEW_ID "
            + "LEFT JOIN vw_version_type vt ON ip.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
            + "LEFT JOIN vw_version_status vs ON ip.VERSION_STATUS_ID=vs.VERSION_STATUS_ID "
            + "LEFT JOIN us_user cb ON ip.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON ip.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";
    private final String sqlManualIntegration = "SELECT "
            + "	im.MANUAL_INTEGRATION_ID, "
            + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, "
            + "    im.VERSION_ID, i.INTEGRATION_ID, i.INTEGRATION_NAME, iv.INTEGRATION_VIEW_ID, iv.INTEGRATION_VIEW_DESC, iv.INTEGRATION_VIEW_NAME, i.FOLDER_LOCATION, i.FILE_NAME, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, im.CREATED_DATE, im.COMPLETED_DATE "
            + "FROM rm_integration_manual im "
            + "LEFT JOIN vw_program p ON im.PROGRAM_ID=p.PROGRAM_ID "
            + "LEFT JOIN ap_integration i ON im.INTEGRATION_ID=i.INTEGRATION_ID "
            + "LEFT JOIN us_user cb ON im.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN ap_integration_view iv ON i.INTEGRATION_VIEW_ID=iv.INTEGRATION_VIEW_ID ";
//    @Override
//    public int addIntegrationProgram(IntegrationProgram i, CustomUserDetails curUser) {
//        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_integration_program").usingGeneratedKeyColumns("INTEGRATION_PROGRAM_ID");
//        Map<String, Object> params = new HashMap<>();
//        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
//        params.put("INTEGRATION_ID", i.getIntegration().getIntegrationId());
//        params.put("PROGRAM_ID", i.getProgram().getId());
//        params.put("VERSION_TYPE_ID", i.getVersionType().getId());
//        params.put("VERSION_STATUS_ID", i.getVersionStatus().getId());
//        params.put("ACTIVE", true);
//        params.put("CREATED_BY", curUser.getUserId());
//        params.put("CREATED_DATE", curDate);
//        params.put("LAST_MODIFIED_BY", curUser.getUserId());
//        params.put("LAST_MODIFIED_DATE", curDate);
//        return si.executeAndReturnKey(params).intValue();
//    }

    @Override
    public int updateIntegrationProgram(IntegrationProgram[] integrationPrograms, CustomUserDetails curUser) {
        String sql = "UPDATE rm_integration_program ip SET ip.VERSION_TYPE_ID=:versionTypeId, ip.VERSION_STATUS_ID=:versionStatusId, ip.ACTIVE=:active, ip.LAST_MODIFIED_BY=:curUser, ip.LAST_MODIFIED_DATE=:curDate WHERE ip.INTEGRATION_PROGRAM_ID=:integrationProgramId";
        final List<SqlParameterSource> insertList = new ArrayList<>();
        final List<SqlParameterSource> updateList = new ArrayList<>();

        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        for (IntegrationProgram ip : integrationPrograms) {
            if (ip.getIntegrationProgramId() == 0) {
                // need to add it
                Map<String, Object> insertParams = new HashMap<>();
                insertParams.put("INTEGRATION_ID", ip.getIntegration().getIntegrationId());
                insertParams.put("PROGRAM_ID", ip.getProgram().getId());
                insertParams.put("VERSION_TYPE_ID", ip.getVersionType().getId());
                insertParams.put("VERSION_STATUS_ID", ip.getVersionStatus().getId());
                insertParams.put("ACTIVE", true);
                insertParams.put("CREATED_BY", curUser.getUserId());
                insertParams.put("CREATED_DATE", curDate);
                insertParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                insertParams.put("LAST_MODIFIED_DATE", curDate);
                insertList.add(new MapSqlParameterSource(insertParams));
            } else {
                // need to update it
                Map<String, Object> updateParams = new HashMap<>();
                updateParams.put("integrationProgramId", ip.getIntegrationProgramId());
                updateParams.put("versionTypeId", ip.getVersionType().getId());
                updateParams.put("versionStatusId", ip.getVersionStatus().getId());
                updateParams.put("active", ip.isActive());
                updateParams.put("curUser", curUser.getUserId());
                updateParams.put("curDate", curDate);
                updateList.add(new MapSqlParameterSource(updateParams));
            }
        }
        int rowsUpdated = 0;
        if (!insertList.isEmpty()) {
            SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_integration_program");
            rowsUpdated += si.executeBatch(insertList.toArray(new MapSqlParameterSource[insertList.size()])).length;
        }
        if (!updateList.isEmpty()) {
            rowsUpdated += this.namedParameterJdbcTemplate.batchUpdate(sql, updateList.toArray(new MapSqlParameterSource[updateList.size()])).length;
        }
        return rowsUpdated;
    }

    @Override
    public List<IntegrationProgram> getIntegrationProgramList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "i", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new IntegrationProgramRowMapper());
    }

    @Override
    public IntegrationProgram getIntegrationProgramById(int integrationProgramId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        params.put("integrationProgramId", integrationProgramId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "i", curUser);
        sqlStringBuilder.append(" AND ip.INTEGRATION_PROGRAM_ID=:integrationProgramId");
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new IntegrationProgramRowMapper());
    }

    @Override
    public List<IntegrationProgram> getIntegrationProgramListForProgramId(int programId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "i", curUser);
        sqlStringBuilder.append(" AND ip.PROGRAM_ID=:programId ");
        params.put("programId", programId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new IntegrationProgramRowMapper());
    }

    @Override
    public int addManualJsonPush(ManualIntegration[] manualIntegrations, CustomUserDetails curUser) {
        final List<SqlParameterSource> insertList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        for (ManualIntegration mi : manualIntegrations) {
            params.put("PROGRAM_ID", mi.getProgram().getId());
            params.put("VERSION_ID", mi.getVersionId());
            params.put("INTEGRATION_ID", mi.getIntegrationId());
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("COMPLETED_DATE", null);
            insertList.add(new MapSqlParameterSource(params));
        }
        int rowsUpdated = 0;
        if (!insertList.isEmpty()) {
            SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_integration_manual");
            rowsUpdated += si.executeBatch(insertList.toArray(new MapSqlParameterSource[insertList.size()])).length;
        }
        return rowsUpdated;
    }

    @Override
    public List<ManualIntegration> getManualJsonPushReport(ManualJsonPushReportInput mi, CustomUserDetails curUser) {
        String sqlString = "CALL getManualJsonPushReport(:startDate, :stopDate, :realmCountryIds, :programIds)";
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", mi.getStartDateString() + " 00:00:00");
        params.put("stopDate", mi.getStopDateString() + " 23:59:59");
        params.put("realmCountryIds", mi.getRealmCountryIdsString());
        params.put("programIds", mi.getProgramIdsString());
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ManualIntegrationRowMapper(false));
    }

    @Override
    public List<ManualIntegration> getManualJsonPushForScheduler() {
        StringBuilder sb = new StringBuilder(sqlManualIntegration).append(" WHERE im.COMPLETED_DATE IS NULL");
        return this.namedParameterJdbcTemplate.query(sb.toString(), new ManualIntegrationRowMapper(true));
    }

    @Override
    public int updateManualIntegrationProgramAsProcessed(int manualIntegrationId) {
        String sqlString = "UPDATE rm_integration_manual im SET im.COMPLETED_DATE=:completedDate WHERE im.MANUAL_INTEGRATION_ID=:manualIntegrationId";
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        params.put("completedDate", curDate);
        params.put("manualIntegrationId", manualIntegrationId);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

}
