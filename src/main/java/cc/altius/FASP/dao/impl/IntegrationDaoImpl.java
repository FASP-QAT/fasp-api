/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.IntegrationDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Integration;
import cc.altius.FASP.model.IntegrationView;
import cc.altius.FASP.model.rowMapper.IntegrationRowMapper;
import cc.altius.FASP.model.rowMapper.IntegrationViewRowMapper;
import cc.altius.FASP.service.AclService;
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
public class IntegrationDaoImpl implements IntegrationDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private AclService aclService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    private final String sqlListString = "SELECT   "
            + "    i.INTEGRATION_ID, i.INTEGRATION_NAME, i.FOLDER_LOCATION, i.FILE_NAME, "
            + "    r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_SP `REALM_LABEL_SP`, r.LABEL_PR `REALM_LABEL_PR`, "
            + "    iv.INTEGRATION_VIEW_ID, iv.INTEGRATION_VIEW_NAME, iv.INTEGRATION_VIEW_DESC "
            + "FROM ap_integration i  "
            + "LEFT JOIN vw_realm r ON i.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_integration_view iv ON i.INTEGRATION_VIEW_ID=iv.INTEGRATION_VIEW_ID "
            + "WHERE TRUE ";

    @Override
    public int addIntegration(Integration i, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("ap_integration").usingGeneratedKeyColumns("INTEGRATION_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        params.put("INTEGRATION_NAME", i.getIntegrationName());
        params.put("FOLDER_LOCATION", i.getFolderLocation());
        params.put("FILE_NAME", i.getFileName());
        params.put("INTEGRATION_VIEW_ID", i.getIntegrationView().getIntegrationViewId());
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateIntegration(Integration i, CustomUserDetails curUser) {
        String sql = "UPDATE ap_integration i SET i.INTEGRATION_NAME=:integrationName, i.FOLDER_LOCATION=:folderLocation, i.FILE_NAME=:fileName, i.INTEGRATION_VIEW_ID=:integrationViewId WHERE i.INTEGRATION_ID=:integrationId";
        Map<String, Object> params = new HashMap<>();
        params.put("integrationId", i.getIntegrationId());
        params.put("integrationName", i.getIntegrationName());
        params.put("folderLocation", i.getFolderLocation());
        params.put("fileName", i.getFileName());
        params.put("integrationViewId", i.getIntegrationView().getIntegrationViewId());
        return this.namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<Integration> getIntegrationList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "i", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new IntegrationRowMapper());
    }

    @Override
    public Integration getIntegrationById(int integrationId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        params.put("integrationId", integrationId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "i", curUser);
        sqlStringBuilder.append(" AND i.INTEGRATION_ID=:integrationId");
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new IntegrationRowMapper());
    }

    @Override
    public List<IntegrationView> getIntegrationViewList(CustomUserDetails curUser) {
        return this.namedParameterJdbcTemplate.query("SELECT * FROM ap_integration_view", new IntegrationViewRowMapper());
    }

}
