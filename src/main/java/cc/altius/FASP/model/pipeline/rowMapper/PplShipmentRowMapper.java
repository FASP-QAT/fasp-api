/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.PplShipment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ekta
 */
public class PplShipmentRowMapper implements RowMapper<PplShipment> {

    @Override
    public PplShipment mapRow(ResultSet rs, int arg1) throws SQLException {
        PplShipment pi = new PplShipment();
        pi.setShipamount(rs.getDouble("ShipAmount"));
        pi.setShipnote(rs.getString("ShipNote"));
        pi.setShipordereddate(rs.getDate("ShipOrderedDate"));
        pi.setShipshippeddate(rs.getDate("ShipShippedDate"));
        pi.setShipreceiveddate(rs.getDate("ShipReceivedDate"));
        pi.setShipfreightcost(rs.getDouble("ShipFreightCost"));
        pi.setShippo(rs.getString("ShipPO"));

        return pi;
    }

}
