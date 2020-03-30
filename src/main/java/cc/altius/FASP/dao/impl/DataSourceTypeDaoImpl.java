/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DataSourceTypeDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgDataSourceTypeDTORowMapper;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.rowMapper.DataSourceTypeRowMapper;
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
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public int addDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int insertedRow = this.labelDao.addLabel(dataSourceType.getLabel(), curUser.getUserId());
        SimpleJdbcInsert insertDataSource = new SimpleJdbcInsert(dataSource).withTableName("rm_data_source_type").usingGeneratedKeyColumns("DATA_SOURCE_TYPE_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_ID", insertedRow);
        params.put("REALM_ID", dataSourceType.getRealm().getRealmId());
        params.put("ACTIVE", 1);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return insertDataSource.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<DataSourceType> getDataSourceTypeList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT dst.DATA_SOURCE_TYPE_ID,  "
                + "	dstl.LABEL_ID, dstl.LABEL_EN, dstl.LABEL_FR, dstl.LABEL_PR, dstl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, dst.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, dst.LAST_MODIFIED_DATE, dst.ACTIVE  "
                + "FROM rm_data_source_type dst  "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON dst.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON dst.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON dst.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND dst.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        if (active) {
            sqlString += " AND dst.ACTIVE ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DataSourceTypeRowMapper());
    }

    @Override
    public DataSourceType getDataSourceTypeById(int dataSourceTypeId, CustomUserDetails curUser) {
        String sqlString = "SELECT dst.DATA_SOURCE_TYPE_ID,  "
                + "	dstl.LABEL_ID, dstl.LABEL_EN, dstl.LABEL_FR, dstl.LABEL_PR, dstl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, dst.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, dst.LAST_MODIFIED_DATE, dst.ACTIVE  "
                + "FROM rm_data_source_type dst  "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON dst.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON dst.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON dst.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE AND dst.DATA_SOURCE_TYPE_ID=:dataSourceTypeId";
        Map<String, Object> params = new HashMap<>();
        params.put("dataSourceTypeId", dataSourceTypeId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND dst.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new DataSourceTypeRowMapper());
    }

    @Override
    public List<DataSourceType> getDataSourceTypeForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT dst.DATA_SOURCE_TYPE_ID,  "
                + "	dstl.LABEL_ID, dstl.LABEL_EN, dstl.LABEL_FR, dstl.LABEL_PR, dstl.LABEL_SP, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, dst.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, dst.LAST_MODIFIED_DATE, dst.ACTIVE  "
                + "FROM rm_data_source_type dst  "
                + "LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "LEFT JOIN rm_realm r ON dst.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON dst.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON dst.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE  AND dst.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND dst.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        if (active) {
            sqlString += " AND dst.ACTIVE ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new DataSourceTypeRowMapper());
    }

    @Transactional
    @Override
    public int updateDataSourceType(DataSourceType dataSourceType, CustomUserDetails curUser) {
        String sqlString = "UPDATE rm_data_source_type dst LEFT JOIN ap_label dstl ON dst.LABEL_ID=dstl.LABEL_ID "
                + "SET  "
                + "	dst.ACTIVE=:active, "
                + "	dst.LAST_MODIFIED_BY = IF(dst.ACTIVE!=:active, :curUser, dst.LAST_MODIFIED_BY), "
                + "    dst.LAST_MODIFIED_DATE = IF(dst.ACTIVE!=:active, :curDate, dst.LAST_MODIFIED_DATE), "
                + "    dstl.LABEL_EN=:label_en,  "
                + "    dstl.LAST_MODIFIED_BY = IF(dstl.LABEL_EN!=:label_en, :curUser, dstl.LAST_MODIFIED_BY), "
                + "    dstl.LAST_MODIFIED_DATE = IF(dstl.LABEL_EN!=:label_en, :curDate, dstl.LAST_MODIFIED_DATE) "
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
    public List<PrgDataSourceTypeDTO> getDataSourceTypeListForSync(String lastSyncDate) {
        String sql = "SELECT dst.`ACTIVE`,dst.`DATA_SOURCE_TYPE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP` "
                + "FROM rm_data_source_type dst  "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=dst.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE dst.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        return this.namedParameterJdbcTemplate.query(sql, params, new PrgDataSourceTypeDTORowMapper());
    }

}
