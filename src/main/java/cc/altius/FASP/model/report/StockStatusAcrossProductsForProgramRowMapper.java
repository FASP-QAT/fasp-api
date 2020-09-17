/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class StockStatusAcrossProductsForProgramRowMapper implements RowMapper<StockStatusAcrossProductsForProgram>{

    @Override
    public StockStatusAcrossProductsForProgram mapRow(ResultSet rs, int i) throws SQLException {
        StockStatusAcrossProductsForProgram ssap = new StockStatusAcrossProductsForProgram();
        ssap.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE")));
        ssap.setAmc(rs.getDouble("AMC"));
        ssap.setAmcCount(rs.getInt("AMC_COUNT"));
        ssap.setFinalClosingBalance(rs.getInt("FINAL_CLOSING_BALANCE_WPS"));
        ssap.setMaxMos(rs.getInt("MAX_MONTHS_OF_STOCK"));
        ssap.setMinMos(rs.getInt("MIN_MONTHS_OF_STOCK"));
        ssap.setMos(rs.getDouble("MoS"));
        return ssap;
    }
    
}
