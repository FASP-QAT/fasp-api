/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.report.StockAdjustmentListOutput;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class StockAdjustmentListOutputRowMapper implements RowMapper<StockAdjustmentListOutput> {

    @Override
    public StockAdjustmentListOutput mapRow(ResultSet rs, int i) throws SQLException {
        StockAdjustmentListOutput s = new StockAdjustmentListOutput();
        s.setProgram(new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i)));
        s.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        s.setInventoryDate(rs.getDate("INVENTORY_DATE"));
        s.setStockAdjustemntQty(rs.getInt("STOCK_ADJUSTMENT_QTY"));
        s.setLastModifiedBy(new BasicUser(rs.getInt("LAST_MODIFIED_BY_USER_ID"), rs.getString("LAST_MODIFIED_BY_USERNAME")));
        s.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        s.setNotes(rs.getString("NOTES"));
        return s;
    }

}
