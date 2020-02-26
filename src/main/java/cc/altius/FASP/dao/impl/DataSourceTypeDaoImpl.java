/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DataSourceTypeDao;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgDataSourceTypeDTORowMapper;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.rowMapper.DataSourceTypeRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public int addDataSourceType(DataSourceType dataSourceType) {

        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int insertedRow = 0;
        int dataSourceInsertedRow = 0;

        SimpleJdbcInsert labelInsert = new SimpleJdbcInsert(dataSource).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", dataSourceType.getLabel().getEngLabel());
//        params.put("LABEL_FR", dataSourceType.getLabel().getFreLabel());
//        params.put("LABEL_SP", dataSourceType.getLabel().getSpaLabel());//alreday scanned
//        params.put("LABEL_PR", dataSourceType.getLabel().getPorLabel());
        params.put("CREATED_BY", 1);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", 1);
        params.put("LAST_MODIFIED_DATE", curDate);
        insertedRow = labelInsert.executeAndReturnKey(params).intValue();

        if (insertedRow > 0) {
            SimpleJdbcInsert insertDataSource = new SimpleJdbcInsert(dataSource).withTableName("ap_data_source_type").usingGeneratedKeyColumns("DATA_SOURCE_TYPE_ID");
            Map<String, Object> paramsTwo = new HashMap<>();
            paramsTwo.put("LABEL_ID", insertedRow);
            paramsTwo.put("ACTIVE", 1);
            paramsTwo.put("CREATED_BY", 1);
            paramsTwo.put("CREATED_DATE", curDate);
            paramsTwo.put("LAST_MODIFIED_BY", 1);
            paramsTwo.put("LAST_MODIFIED_DATE", curDate);
            dataSourceInsertedRow = insertDataSource.executeAndReturnKey(paramsTwo).intValue();
        }

        return dataSourceInsertedRow;
    }

    @Override
    public List<DataSourceType> getDataSourceTypeList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT dt.`LABEL_ID`,dt.`ACTIVE`,dt.`DATA_SOURCE_TYPE_ID`,\n"
                + "al.`LABEL_EN`,al.`LABEL_FR`,al.`LABEL_PR`,al.`LABEL_SP` \n"
                + "FROM ap_data_source_type dt  \n"
                + "LEFT JOIN  ap_label al ON al.`LABEL_ID`=dt.`LABEL_ID`");
        if (active) {
            sb.append(" WHERE dt.`ACTIVE` ");
        }
        return this.jdbcTemplate.query(sb.toString(), new DataSourceTypeRowMapper());
    }

    @Transactional
    @Override
    public int updateDataSourceType(DataSourceType dataSourceType) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=? ,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, dataSourceType.getLabel().getEngLabel(), 1, curDt, dataSourceType.getLabel().getLabelId());
        String sqlTwo = "UPDATE ap_data_source_type dt SET  dt.`ACTIVE`=?,dt.`LAST_MODIFIED_BY`=?,dt.`LAST_MODIFIED_DATE`=?"
                + " WHERE dt.`DATA_SOURCE_TYPE_ID`=?;";
        return this.jdbcTemplate.update(sqlTwo, dataSourceType.isActive(), 1, curDt, dataSourceType.getDataSourceTypeId());

    }

    @Override
    public List<PrgDataSourceTypeDTO> getDataSourceTypeListForSync(String lastSyncDate) {
        String sql = "SELECT dst.`ACTIVE`,dst.`DATA_SOURCE_TYPE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM ap_data_source_type dst \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=dst.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE dst.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgDataSourceTypeDTORowMapper());
    }

}
