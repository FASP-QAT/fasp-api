/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.DataSourceDao;
import cc.altius.FASP.model.DTO.PrgDataSourceDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgDataSourceDTORowMapper;
import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.model.rowMapper.DataSourceRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
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
public class DataSourceDaoImpl implements DataSourceDao {

    private JdbcTemplate jdbcTemplate;
    private javax.sql.DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public int addDataSource(DataSource dataSourceObj) {

        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);

        SimpleJdbcInsert labelInsert = new SimpleJdbcInsert(dataSource).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();

//        params.put("LABEL_EN", dataSourceObj.getLabel().getEngLabel());
//        params.put("LABEL_FR", dataSourceObj.getLabel().getFreLabel());
//        params.put("LABEL_SP", dataSourceObj.getLabel().getSpaLabel());//alreday scanned
//        params.put("LABEL_PR", dataSourceObj.getLabel().getPorLabel());

        params.put("LABEL_EN", dataSourceObj.getLabel().getLabel_en());
//        params.put("LABEL_FR", dataSourceObj.getLabel().getLabel_fr());
//        params.put("LABEL_SP", dataSourceObj.getLabel().getLabel_sp());//alreday scanned
//        params.put("LABEL_PR", dataSourceObj.getLabel().getLabel_pr());

        params.put("CREATED_BY", 1);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", 1);
        params.put("LAST_MODIFIED_DATE", curDate);
        int insertedLabelRowId = labelInsert.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_data_source").usingGeneratedKeyColumns("DATA_SOURCE_ID");
        Map<String, Object> map = new HashedMap<>();
        map.put("DATA_SOURCE_TYPE_ID", dataSourceObj.getDataSourceType().getDataSourceTypeId());
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", 1);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", 1);
        map.put("LAST_MODIFIED_DATE", curDate);
        int dataSourceId = insert.executeAndReturnKey(map).intValue();
        return dataSourceId;

    }

    @Override
    public List<DataSource> getDataSourceList(boolean active) {

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ds.`LABEL_ID` `LANG_LABEL_ID`,ds.`ACTIVE`,ds.`DATA_SOURCE_TYPE_ID`,ds.`DATA_SOURCE_ID`,\n"
                + "al.`LABEL_EN` `LANG_LABEL_EN`,al.`LABEL_FR` `LANG_LABEL_FR`,al.`LABEL_PR` `LANG_LABEL_PR`,al.`LABEL_SP` `LANG_LABEL_SP`,l.`LABEL_EN` AS dataSourceTypeName \n"
                + "FROM ap_data_source ds  \n"
                + "LEFT JOIN  ap_label al ON al.`LABEL_ID`=ds.`LABEL_ID` \n"
                + "LEFT JOIN  ap_data_source_type dt ON dt.`DATA_SOURCE_TYPE_ID`=ds.`DATA_SOURCE_TYPE_ID` \n"
                + "LEFT JOIN  ap_label l ON l.`LABEL_ID`=dt.`LABEL_ID`");
        if (active) {
            sb.append(" WHERE dt.`ACTIVE` ");
        }
        return this.jdbcTemplate.query(sb.toString(), new DataSourceRowMapper());
    }

    @Transactional
    @Override
    public int updateDataSource(DataSource dataSource) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);

        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, dataSource.getLabel().getLabel_en(), 1, curDt, dataSource.getLabel().getLabelId());


//        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=? , al.`LABEL_FR`=?,"
//                + "al.`LABEL_PR`=?,al.`LABEL_SP`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
//        this.jdbcTemplate.update(sqlOne, dataSource.getLabel().getLabel_en(), dataSource.getLabel().getLabel_fr(),
//                dataSource.getLabel().getLabel_pr(), dataSource.getLabel().getLabel_sp(), 1, curDt, dataSource.getLabel().getLabelId());


        String sqlTwo = "UPDATE ap_data_source dt SET  dt.`DATA_SOURCE_TYPE_ID`=?,dt.`ACTIVE`=?,dt.`LAST_MODIFIED_BY`=?,dt.`LAST_MODIFIED_DATE`=?"
                + " WHERE dt.`DATA_SOURCE_ID`=?;";
        return this.jdbcTemplate.update(sqlTwo, dataSource.getDataSourceType().getDataSourceTypeId(), dataSource.isActive(), 1, curDt, dataSource.getDataSourceId());
    }

    @Override
    public List<PrgDataSourceDTO> getDataSourceListForSync(String lastSyncDate) {
        String sql = "SELECT ds.`ACTIVE`,ds.`DATA_SOURCE_ID`,ds.`DATA_SOURCE_TYPE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM ap_data_source ds\n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=ds.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE ds.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgDataSourceDTORowMapper());
    }

}
