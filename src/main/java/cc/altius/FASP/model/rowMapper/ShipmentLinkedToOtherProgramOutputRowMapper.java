/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ShipmentLinkedToOtherProgramOutput;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentLinkedToOtherProgramOutputRowMapper implements RowMapper<ShipmentLinkedToOtherProgramOutput> {

    @Override
    public ShipmentLinkedToOtherProgramOutput mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShipmentLinkedToOtherProgramOutput so = new ShipmentLinkedToOtherProgramOutput();
        so.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper().mapRow(rs, rowNum), rs.getString("PROGRAM_CODE")));
        so.setRoNo(rs.getString("RO_NO"));
        so.setRoPrimeLineNo(rs.getString("RO_PRIME_LINE_NO"));
        so.setShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        so.setProgramManager(new BasicUser(rs.getInt("USER_ID"), rs.getString("USERNAME")));
        return so;
    }

}
