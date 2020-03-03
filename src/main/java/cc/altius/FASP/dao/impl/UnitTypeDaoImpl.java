/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.UnitTypeDao;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class UnitTypeDaoImpl implements UnitTypeDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PrgUnitTypeDTO> getUnitTypeListForSync() {
        String sql = "SELECT u.`UNIT_TYPE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM ap_unit_type u\n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=u.`LABEL_ID`";
        return this.jdbcTemplate.query(sql, new PrgUnitTypeDTORowMapper());
    }
    
    @Override
    public int addUnitType(UnitType unitType, int userId) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int insertedRow = 0;
        int dataSourceInsertedRow = 0;
        SimpleJdbcInsert labelInsert = new SimpleJdbcInsert(dataSource).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", unitType.getLabel().getLabel_en());
        params.put("CREATED_BY", userId);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", userId);
        params.put("LAST_MODIFIED_DATE", curDate);
        insertedRow = labelInsert.executeAndReturnKey(params).intValue();

        if (insertedRow > 0) {
            SimpleJdbcInsert insertDataSource = new SimpleJdbcInsert(dataSource).withTableName("ap_unit_type").usingGeneratedKeyColumns("UNIT_TYPE_ID");
            Map<String, Object> paramsTwo = new HashMap<>();
            paramsTwo.put("LABEL_ID", insertedRow);
//            paramsTwo.put("ACTIVE", 1);
            paramsTwo.put("CREATED_BY", userId);
            paramsTwo.put("CREATED_DATE", curDate);
            paramsTwo.put("LAST_MODIFIED_BY", userId);
            paramsTwo.put("LAST_MODIFIED_DATE", curDate);
            dataSourceInsertedRow = insertDataSource.executeAndReturnKey(paramsTwo).intValue();
        }

        return dataSourceInsertedRow;
    }

    @Override
    public List<UnitType> getUnitTypeList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ut.`LABEL_ID`,ut.`UNIT_TYPE_ID`,\n"
                + "al.`LABEL_EN`,al.`LABEL_FR`,al.`LABEL_PR`,al.`LABEL_SP` \n"
                + "FROM ap_unit_type ut  \n"
                + "LEFT JOIN  ap_label al ON al.`LABEL_ID`=ut.`LABEL_ID`");
        if (active) {
            sb.append(" WHERE dt.`ACTIVE` ");
        }
        return this.jdbcTemplate.query(sb.toString(), new UnitTypeRowMapper());
    }

    @Override
    public int updateUnitType(UnitType unitType, int userId) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=? ,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        return this.jdbcTemplate.update(sqlOne, unitType.getLabel().getLabel_en(), userId, curDt, unitType.getLabel().getLabelId());
    }

}
