/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DataSourceDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgDataSourceDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgDataSourceDTORowMapper;
import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.model.rowMapper.DataSourceRowMapper;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
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
public class DataSourceDaoImpl implements DataSourceDao {

    private javax.sql.DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public int addDataSource(DataSource dataSource, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int insertedLabelRowId = this.labelDao.addLabel(dataSource.getLabel(), curUser.getUserId());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.dataSource).withTableName("rm_data_source").usingGeneratedKeyColumns("DATA_SOURCE_ID");
        Map<String, Object> map = new HashedMap<>();
        map.put("DATA_SOURCE_TYPE_ID", dataSource.getDataSourceType().getDataSourceTypeId());
        map.put("REALM_ID", dataSource.getRealm().getRealmId());
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", curUser.getUserId());
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser.getUserId());
        map.put("LAST_MODIFIED_DATE", curDate);
        int dataSourceId = insert.executeAndReturnKey(map).intValue();
        return dataSourceId;
    }

    @Override
    public List<DataSource> getDataSourceList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT ds.DATA_SOURCE_ID,  "
                + "	dsl.LABEL_ID, dsl.LABEL_EN, dsl.LABEL_FR, dsl.LABEL_PR, dsl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	dst.DATA_SOURCE_TYPE_ID, dstl.LABEL_ID `DATA_SOURCE_TYPE_LABEL_ID`, dstl.LABEL_EN `DATA_SOURCE_TYPE_LABEL_EN`, dstl.LABEL_FR `DATA_SOURCE_TYPE_LABEL_FR`, dstl.LABEL_PR `DATA_SOURCE_TYPE_LABEL_PR`, dstl.LABEL_SP `DATA_SOURCE_TYPE_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ds.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ds.LAST_MODIFIED_DATE, ds.ACTIVE  "
                + "FROM rm_data_source ds  "
                + "LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON ds.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN rm_data_source_type dst ON ds.DATA_SOURCE_TYPE_ID=dst.DATA_SOURCE_TYPE_ID "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ds.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ds.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND ds.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        if (active) {
            sqlString += " AND ds.ACTIVE ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DataSourceRowMapper());
    }

    @Override
    public DataSource getDataSourceById(int dataSourceId, CustomUserDetails curUser) {
        String sqlString = "SELECT ds.DATA_SOURCE_ID,  "
                + "	dsl.LABEL_ID, dsl.LABEL_EN, dsl.LABEL_FR, dsl.LABEL_PR, dsl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	dst.DATA_SOURCE_TYPE_ID, dstl.LABEL_ID `DATA_SOURCE_TYPE_LABEL_ID`, dstl.LABEL_EN `DATA_SOURCE_TYPE_LABEL_EN`, dstl.LABEL_FR `DATA_SOURCE_TYPE_LABEL_FR`, dstl.LABEL_PR `DATA_SOURCE_TYPE_LABEL_PR`, dstl.LABEL_SP `DATA_SOURCE_TYPE_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ds.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ds.LAST_MODIFIED_DATE, ds.ACTIVE  "
                + "FROM rm_data_source ds  "
                + "LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON ds.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN rm_data_source_type dst ON ds.DATA_SOURCE_TYPE_ID=dst.DATA_SOURCE_TYPE_ID "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ds.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ds.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND ds.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        sqlString += " AND ds.DATA_SOURCE_ID=:dataSourceId ";
        params.put("dataSourceId", dataSourceId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DataSourceRowMapper());
    }

    @Override
    public List<DataSource> getDataSourceForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT ds.DATA_SOURCE_ID,  "
                + "	dsl.LABEL_ID, dsl.LABEL_EN, dsl.LABEL_FR, dsl.LABEL_PR, dsl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	dst.DATA_SOURCE_TYPE_ID, dstl.LABEL_ID `DATA_SOURCE_TYPE_LABEL_ID`, dstl.LABEL_EN `DATA_SOURCE_TYPE_LABEL_EN`, dstl.LABEL_FR `DATA_SOURCE_TYPE_LABEL_FR`, dstl.LABEL_PR `DATA_SOURCE_TYPE_LABEL_PR`, dstl.LABEL_SP `DATA_SOURCE_TYPE_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ds.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ds.LAST_MODIFIED_DATE, ds.ACTIVE  "
                + "FROM rm_data_source ds  "
                + "LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON ds.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN rm_data_source_type dst ON ds.DATA_SOURCE_TYPE_ID=dst.DATA_SOURCE_TYPE_ID "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ds.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ds.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND ds.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        sqlString += " AND ds.REALM_ID=:realmId ";
        params.put("realmId", realmId);
        if (active) {
            sqlString += " AND ds.ACTIVE ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DataSourceRowMapper());
    }

    @Override
    public List<DataSource> getDataSourceForDataSourceType(int dataSourceTypeId, boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT ds.DATA_SOURCE_ID,  "
                + "	dsl.LABEL_ID, dsl.LABEL_EN, dsl.LABEL_FR, dsl.LABEL_PR, dsl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	dst.DATA_SOURCE_TYPE_ID, dstl.LABEL_ID `DATA_SOURCE_TYPE_LABEL_ID`, dstl.LABEL_EN `DATA_SOURCE_TYPE_LABEL_EN`, dstl.LABEL_FR `DATA_SOURCE_TYPE_LABEL_FR`, dstl.LABEL_PR `DATA_SOURCE_TYPE_LABEL_PR`, dstl.LABEL_SP `DATA_SOURCE_TYPE_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ds.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ds.LAST_MODIFIED_DATE, ds.ACTIVE  "
                + "FROM rm_data_source ds  "
                + "LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON ds.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN rm_data_source_type dst ON ds.DATA_SOURCE_TYPE_ID=dst.DATA_SOURCE_TYPE_ID "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ds.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ds.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND ds.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        sqlString += " AND ds.DATA_SOURCE_TYPE_ID=:dataSourceTypeId ";
        params.put("dataSourceTypeId", dataSourceTypeId);
        if (active) {
            sqlString += " AND ds.ACTIVE ";
        }
        System.out.println(sqlString);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DataSourceRowMapper());
    }

    @Override
    public int updateDataSource(DataSource dataSource, CustomUserDetails curUser) {
        String sqlString = "UPDATE rm_data_source ds LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "SET  "
                + "	ds.ACTIVE=:active, "
                + "	ds.LAST_MODIFIED_BY = IF(ds.ACTIVE!=:active, :curUser, ds.LAST_MODIFIED_BY), "
                + "    ds.LAST_MODIFIED_DATE = IF(ds.ACTIVE!=:active, :curDate, ds.LAST_MODIFIED_DATE), "
                + "    dsl.LABEL_EN=:label_en,  "
                + "    dsl.LAST_MODIFIED_BY = IF(dsl.LABEL_EN!=:label_en, :curUser, dsl.LAST_MODIFIED_BY), "
                + "    dsl.LAST_MODIFIED_DATE = IF(dsl.LABEL_EN!=:label_en, :curDate, dsl.LAST_MODIFIED_DATE) "
                + "WHERE ds.DATA_SOURCE_ID=:dataSourceId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("active", dataSource.isActive());
        params.put("label_en", dataSource.getLabel().getLabel_en());
        params.put("curDate", DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS));
        params.put("curUser", curUser.getUserId());
        params.put("dataSourceId", dataSource.getDataSourceId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<DataSource> getDataSourceListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sqlString = "SELECT ds.DATA_SOURCE_ID,  "
                + "	dsl.LABEL_ID, dsl.LABEL_EN, dsl.LABEL_FR, dsl.LABEL_PR, dsl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	dst.DATA_SOURCE_TYPE_ID, dstl.LABEL_ID `DATA_SOURCE_TYPE_LABEL_ID`, dstl.LABEL_EN `DATA_SOURCE_TYPE_LABEL_EN`, dstl.LABEL_FR `DATA_SOURCE_TYPE_LABEL_FR`, dstl.LABEL_PR `DATA_SOURCE_TYPE_LABEL_PR`, dstl.LABEL_SP `DATA_SOURCE_TYPE_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ds.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ds.LAST_MODIFIED_DATE, ds.ACTIVE  "
                + "FROM rm_data_source ds  "
                + "LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON ds.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN rm_data_source_type dst ON ds.DATA_SOURCE_TYPE_ID=dst.DATA_SOURCE_TYPE_ID "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ds.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ds.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE ds.LAST_MODIFIED_DATE>:lastSyncDate ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND ds.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }

        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DataSourceRowMapper());
    }
}
