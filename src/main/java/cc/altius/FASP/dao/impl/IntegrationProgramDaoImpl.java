/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.IntegrationProgramDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.IntegrationProgram;
import cc.altius.FASP.model.rowMapper.IntegrationProgramRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    @Override
    public int addIntegrationProgram(IntegrationProgram i, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_integration_program").usingGeneratedKeyColumns("INTEGRATION_PROGRAM_ID");
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        params.put("INTEGRATION_ID", i.getIntegration().getIntegrationId());
        params.put("PROGRAM_ID", i.getProgram().getId());
        params.put("VERSION_TYPE_ID", i.getVersionType().getId());
        params.put("VERSION_STATUS_ID", i.getVersionStatus().getId());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateIntegrationProgram(IntegrationProgram i, CustomUserDetails curUser) {
        String sql = "UPDATE rm_integration_program ip SET ip.VERSION_TYPE_ID=:versionTypeId, ip.VERSION_STATUS_ID=:versionStatusId, ip.ACTIVE=:active, ip.LAST_MODIFIED_BY=:curUser, ip.LAST_MODIFIED_DATE=:curDate WHERE ip.INTEGRATION_PROGRAM_ID=:integrationProgramId";
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        params.put("integrationProgramId", i.getIntegrationProgramId());
        params.put("versionTypeId", i.getVersionType().getId());
        params.put("versionStatusId", i.getVersionStatus().getId());
        params.put("active", i.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sql, params);
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

}
