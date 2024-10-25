/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ConsumptionInfoRowMapper implements RowMapper<ConsumptionInfo>{

    @Override
    public ConsumptionInfo mapRow(ResultSet rs, int i) throws SQLException {
        return new ConsumptionInfo(
                rs.getInt("CONSUMPTION_ID"),
                rs.getDate("CONSUMPTION_DATE"),
                new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, i)),
                new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, i)),
                rs.getString("NOTES"),
                rs.getBoolean("ACTUAL_FLAG")
        );
    }
    
}