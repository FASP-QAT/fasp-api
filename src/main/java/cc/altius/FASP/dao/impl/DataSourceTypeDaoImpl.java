/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DataSourceTypeDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.rowMapper.DataSourceTypeRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author palash
 */
@Repository
public class DataSourceTypeDaoImpl implements DataSourceTypeDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListString = "SELECT dst.DATA_SOURCE_TYPE_ID,  "
            + "  dstl.LABEL_ID, dstl.LABEL_EN, dstl.LABEL_FR, dstl.LABEL_PR, dstl.LABEL_SP, "
            + "  r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
            + "  cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, dst.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, dst.LAST_MODIFIED_DATE, dst.ACTIVE  "
            + "FROM rm_data_source_type dst  "
            + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
            + "LEFT JOIN rm_realm r ON dst.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + "LEFT JOIN us_user cb ON dst.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON dst.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Transactional
    @Override
    public int addDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int insertedRow = this.labelDao.addLabel(dataSourceType.getLabel(), LabelConstants.RM_DATA_SOURCE_TYPE, curUser.getUserId());
        SimpleJdbcInsert insertDataSource = new SimpleJdbcInsert(dataSource).withTableName("rm_data_source_type").usingGeneratedKeyColumns("DATA_SOURCE_TYPE_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_ID", insertedRow);
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        params.put("ACTIVE", 1);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return insertDataSource.executeAndReturnKey(params).intValue();
    }

    @Transactional
    @Override
    public int updateDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser) {
        String sqlString = "UPDATE rm_data_source_type dst LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "SET  "
                + "  dst.ACTIVE=:active, "
                + "  dst.LAST_MODIFIED_BY = :curUser, "
                + "  dst.LAST_MODIFIED_DATE = :curDate, "
                + "  dstl.LABEL_EN=:label_en,  "
                + "  dstl.LAST_MODIFIED_BY = :curUser, "
                + "  dstl.LAST_MODIFIED_DATE = :curDate "
                + "WHERE dst.DATA_SOURCE_TYPE_ID=:dataSourceTypeId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("active", dataSourceType.isActive());
        params.put("label_en", dataSourceType.getLabel().getLabel_en());
        params.put("curDate", DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS));
        params.put("curUser", curUser.getUserId());
        params.put("dataSourceTypeId", dataSourceType.getDataSourceTypeId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<DataSourceType> getDataSourceTypeList(boolean active, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        if (active) {
            sqlStringBuilder.append(" AND dst.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new DataSourceTypeRowMapper());
    }

    @Override
    public DataSourceType getDataSourceTypeById(int dataSourceTypeId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        sqlStringBuilder.append(" AND dst.DATA_SOURCE_TYPE_ID=:dataSourceTypeId");
        params.put("dataSourceTypeId", dataSourceTypeId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new DataSourceTypeRowMapper());
    }

    @Override
    public List<DataSourceType> getDataSourceTypeForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new DataSourceTypeRowMapper());
    }

    @Override
    public List<DataSourceType> getDataSourceTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        sqlStringBuilder.append(" AND dst.LAST_MODIFIED_DATE>:lastModifiedDate");
        params.put("lastModifiedDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new DataSourceTypeRowMapper());
    }

}
