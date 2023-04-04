/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ShipmentNotificationDTORowMapper implements RowMapper<ERPNotificationDTO>{

    @Override
    public ERPNotificationDTO mapRow(ResultSet rs, int rows) throws SQLException {
        ERPNotificationDTO m = new ERPNotificationDTO();
        m.setNotificationId(rs.getInt("NOTIFICATION_ID"));
        m.setNotificationType(new SimpleObject(rs.getInt("NOTIFICATION_TYPE_ID"), new LabelRowMapper("").mapRow(rs, rows)));
        m.setAddressed(rs.getBoolean("ADDRESSED"));
        m.setShipmentLinkingId(rs.getInt("SHIPMENT_LINKING_ID"));
        return m;
    }
    
}
