/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.UnaccountedConsumption;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class UnaccountedConsumptionRowMapper implements RowMapper<UnaccountedConsumption> {

    @Override
    public UnaccountedConsumption mapRow(ResultSet rs, int i) throws SQLException {
        return new UnaccountedConsumption(rs.getDate("TRANS_DATE"), rs.getInt("CONSUMPTION_QTY"));
    }
    
}
