/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.ShipmentStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class ShipmentStatusRowMapper implements RowMapper<ShipmentStatus>{

    @Override
    public ShipmentStatus mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentStatus ss= new ShipmentStatus();
        ss.setActive(rs.getBoolean("ACTIVE"));
        ss.setShipmentStatusId(rs.getInt("SHIPMENT_STATUS_ID"));
        Label l= new Label();
        l.setLabelId(rs.getInt("LABEL_ID"));
        l.setEngLabel(rs.getString("LABEL_EN"));
        l.setFreLabel(rs.getString("LABEL_FR"));
        l.setSpaLabel(rs.getString("LABEL_SP"));
        l.setPorLabel(rs.getString("LABEL_PR"));
        ss.setLabel(l);
        return ss;
    }
    
}
