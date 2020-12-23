/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ExpiredStockOutputRowMapper implements RowMapper<ExpiredStockOutput> {

    @Override
    public ExpiredStockOutput mapRow(ResultSet rs, int i) throws SQLException {
        ExpiredStockOutput es = new ExpiredStockOutput();
        es.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        es.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        es.setBatchInfo(new Batch(rs.getInt("BATCH_ID"), rs.getInt("PLANNING_UNIT_ID"), rs.getString("BATCH_NO"), rs.getBoolean("AUTO_GENERATED"), rs.getDate("EXPIRY_DATE"), rs.getTimestamp("CREATED_DATE")));
        es.setExpiredQty(rs.getLong("EXPIRED_STOCK"));
        return es;
    }

}
