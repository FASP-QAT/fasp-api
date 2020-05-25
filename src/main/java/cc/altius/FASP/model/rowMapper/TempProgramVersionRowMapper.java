/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.TempProgramVersion;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class TempProgramVersionRowMapper implements RowMapper<TempProgramVersion> {

    @Override
    public TempProgramVersion mapRow(ResultSet rs, int arg1) throws SQLException {
        TempProgramVersion t = new TempProgramVersion();
        t.setProgramId(rs.getInt("PROGRAM_ID"));
        t.setErpOrderId(rs.getInt("ERP_ORDER_ID"));
        return t;
    }

}
