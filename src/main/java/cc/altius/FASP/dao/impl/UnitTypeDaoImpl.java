/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.UnitTypeDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgUnitTypeDTORowMapper;
import cc.altius.FASP.model.UnitType;
import cc.altius.FASP.model.rowMapper.UnitTypeRowMapper;
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
 * @author altius
 */
@Repository
public class UnitTypeDaoImpl implements UnitTypeDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<PrgUnitTypeDTO> getUnitTypeListForSync() {
        String sql = "SELECT u.`UNIT_TYPE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP` "
                + "FROM ap_unit_type u "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=u.`LABEL_ID`";
        return this.namedParameterJdbcTemplate.query(sql, new PrgUnitTypeDTORowMapper());
    }

    @Override
    public int addUnitType(UnitType unitType, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int labelId = this.labelDao.addLabel(unitType.getLabel(), curUser.getUserId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ap_unit_type").usingGeneratedKeyColumns("UNIT_TYPE_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_ID", labelId);
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("ACTIVE", 1);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<UnitType> getUnitTypeList() {
        String sqlString = "SELECT ut.UNIT_TYPE_ID,  "
                + "	utl.LABEL_ID, utl.LABEL_EN, utl.LABEL_FR, utl.LABEL_PR, utl.LABEL_SP, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ut.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ut.LAST_MODIFIED_DATE, ut.ACTIVE  "
                + "FROM ap_unit_type ut  "
                + "LEFT JOIN ap_label utl ON ut.LABEL_ID=utl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ut.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ut.LAST_MODIFIED_BY=lmb.USER_ID ";
        return this.namedParameterJdbcTemplate.query(sqlString, new UnitTypeRowMapper());
    }

    @Override
    public UnitType getUnitTypeById(int unitTypeId) {
        String sqlString = "SELECT ut.UNIT_TYPE_ID,  "
                + "	utl.LABEL_ID, utl.LABEL_EN, utl.LABEL_FR, utl.LABEL_PR, utl.LABEL_SP, "
                + "	cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ut.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ut.LAST_MODIFIED_DATE, ut.ACTIVE  "
                + "FROM ap_unit_type ut  "
                + "LEFT JOIN ap_label utl ON ut.LABEL_ID=utl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ut.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ut.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE ut.UNIT_TYPE_ID=:unitTypeId";
        Map<String, Object> params = new HashMap<>();
        params.put("unitTypeId", unitTypeId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new UnitTypeRowMapper());
    }

    @Override
    public int updateUnitType(UnitType unitType, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE ap_unit_type ut LEFT JOIN ap_label utl ON ut.LABEL_ID=utl.LABEL_ID "
                + "SET  "
                + "	ut.ACTIVE=:active, "
                + "	ut.LAST_MODIFIED_BY = IF(ut.ACTIVE!=:active, :curUser, ut.LAST_MODIFIED_BY), "
                + "    ut.LAST_MODIFIED_DATE = IF(ut.ACTIVE!=:active, :curDate, ut.LAST_MODIFIED_DATE), "
                + "    utl.LABEL_EN=:label_en,  "
                + "    utl.LAST_MODIFIED_BY = IF(utl.LABEL_EN!=:label_en, :curUser, utl.LAST_MODIFIED_BY), "
                + "    utl.LAST_MODIFIED_DATE = IF(utl.LABEL_EN!=:label_en, :curDate, utl.LAST_MODIFIED_DATE) "
                + "WHERE ut.UNIT_TYPE_ID=:unitTypeId";
        Map<String, Object> params = new HashMap<>();
        params.put("active", unitType.isActive());
        params.put("label_en", unitType.getLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("unitTypeId", unitType.getUnitTypeId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

}
