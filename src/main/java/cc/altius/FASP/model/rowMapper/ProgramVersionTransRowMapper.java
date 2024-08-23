/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProgramVersionTrans;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramVersionTransRowMapper implements RowMapper<ProgramVersionTrans> {

    @Override
    public ProgramVersionTrans mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProgramVersionTrans(
                rs.getInt("VERSION_ID"),
                new SimpleObjectRowMapper("VS_").mapRow(rs, 1),
                new SimpleObjectRowMapper("VT_").mapRow(rs, 1),
                rs.getString("NOTES"),
                new BasicUserRowMapper("LMB_").mapRow(rs, 1),
                rs.getTimestamp("LAST_MODIFIED_DATE")
        );
    }

}
