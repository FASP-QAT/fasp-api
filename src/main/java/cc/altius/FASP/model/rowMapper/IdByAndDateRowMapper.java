/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.IdByAndDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class IdByAndDateRowMapper implements RowMapper<IdByAndDate> {

    @Override
    public IdByAndDate mapRow(ResultSet rs, int i) throws SQLException {
        return new IdByAndDate(
                rs.getInt("ID"), 
                rs.getInt("TEMP_ID"), 
                rs.getInt("TEMP_PARENT_ID"), 
                rs.getInt("TEMP_PARENT_LINKED_ID"), 
                rs.getInt("CREATED_BY"), 
                rs.getTimestamp("CREATED_DATE"), 
                rs.getInt("LAST_MODIFIED_BY"), 
                rs.getTimestamp("LAST_MODIFIED_DATE"));
    }

}
