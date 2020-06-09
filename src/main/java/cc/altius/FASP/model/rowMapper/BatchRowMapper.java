/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Batch;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class BatchRowMapper implements RowMapper<Batch>{

    @Override
    public Batch mapRow(ResultSet rs, int i) throws SQLException {
        return new Batch(rs.getInt("BATCH_ID"), rs.getString("BATCH_NO"), rs.getDate("EXPIRY_DATE"));
    }
    
}
