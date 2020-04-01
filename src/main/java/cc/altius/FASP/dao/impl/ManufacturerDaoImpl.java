/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ManufacturerDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgManufacturerDTORowMapper;
import cc.altius.FASP.model.Manufacturer;
import cc.altius.FASP.model.rowMapper.ManufacturerRowMapper;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class ManufacturerDaoImpl implements ManufacturerDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Autowired
    private LabelDao labelDao;

    @Override
    public List<PrgManufacturerDTO> getManufacturerListForSync(String lastSyncDate,int realmId) {
        String sql = "SELECT m.`ACTIVE`,m.`MANUFACTURER_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,m.`REALM_ID`\n"
                + "FROM rm_manufacturer m \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=m.`LABEL_ID` WHERE (m.`REALM_ID`=:realmId OR -1=:realmId)";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " AND m.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        params.put("realmId", realmId);
        return this.namedParameterJdbcTemplate.query(sql, params, new PrgManufacturerDTORowMapper());
    }

    @Override
    @Transactional
    public int addManufacturer(Manufacturer m, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_manufacturer").usingGeneratedKeyColumns("MANUFACTURER_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", m.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(m.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateManufacturer(Manufacturer m, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_manufacturer m LEFT JOIN ap_label ml ON m.LABEL_ID=ml.LABEL_ID "
                + "SET  "
                + "m.`ACTIVE`=:active, "
                + "m.`LAST_MODIFIED_BY`=IF(m.`ACTIVE`!=:active, :curUser, m.LAST_MODIFIED_BY), "
                + "m.`LAST_MODIFIED_DATE`=IF(m.`ACTIVE`!=:active, :curDate, m.LAST_MODIFIED_DATE), "
                + "ml.LABEL_EN=:labelEn, "
                + "ml.`LAST_MODIFIED_BY`=IF(ml.LABEL_EN!=:labelEn, :curUser, ml.LAST_MODIFIED_BY), "
                + "ml.`LAST_MODIFIED_DATE`=IF(ml.LABEL_EN!=:labelEn, :curDate, ml.LAST_MODIFIED_DATE) "
                + " WHERE m.`MANUFACTURER_ID`=:manufacturerId";
        Map<String, Object> params = new HashMap<>();
        params.put("manufacturerId", m.getManufacturerId());
        params.put("active", m.isActive());
        params.put("curDate", curDate);
        params.put("curUser", curUser.getUserId());
        params.put("labelEn", m.getLabel().getLabel_en());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Manufacturer> getManufacturerList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    m.MANUFACTURER_ID,  "
                + "    ml.LABEL_ID, ml.LABEL_EN, ml.LABEL_FR, ml.LABEL_SP, ml.LABEL_PR, "
                + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
                + "    m.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, m.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, m.LAST_MODIFIED_DATE "
                + "FROM rm_manufacturer m  "
                + "LEFT JOIN ap_label ml ON m.LABEL_ID=ml.LABEL_ID "
                + "LEFT JOIN rm_realm r ON m.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON m.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON m.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND m.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        if (active) {
            sqlString +=" AND m.ACTIVE ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ManufacturerRowMapper());
    }

    @Override
    public List<Manufacturer> getManufacturerListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    m.MANUFACTURER_ID,  "
                + "    ml.LABEL_ID, ml.LABEL_EN, ml.LABEL_FR, ml.LABEL_SP, ml.LABEL_PR, "
                + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
                + "    m.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, m.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, m.LAST_MODIFIED_DATE "
                + "FROM rm_manufacturer m  "
                + "LEFT JOIN ap_label ml ON m.LABEL_ID=ml.LABEL_ID "
                + "LEFT JOIN rm_realm r ON m.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON m.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON m.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE AND m.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND m.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        if (active) {
            sqlString +=" AND m.ACTIVE ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new ManufacturerRowMapper());
    }

    @Override
    public Manufacturer getManufacturerById(int manufacturerId, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "    m.MANUFACTURER_ID,  "
                + "    ml.LABEL_ID, ml.LABEL_EN, ml.LABEL_FR, ml.LABEL_SP, ml.LABEL_PR, "
                + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
                + "    m.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, m.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, m.LAST_MODIFIED_DATE "
                + "FROM rm_manufacturer m  "
                + "LEFT JOIN ap_label ml ON m.LABEL_ID=ml.LABEL_ID "
                + "LEFT JOIN rm_realm r ON m.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON m.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON m.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE m.`MANUFACTURER_ID`=:manufacturerId ";
        Map<String, Object> params = new HashMap<>();
        params.put("manufacturerId", manufacturerId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND m.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new ManufacturerRowMapper());
    }

}
