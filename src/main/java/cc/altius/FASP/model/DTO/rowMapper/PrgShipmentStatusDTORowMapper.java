/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgShipmentBudgetDTO;
import cc.altius.FASP.model.DTO.PrgShipmentStatusDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgShipmentStatusDTORowMapper implements RowMapper<PrgShipmentStatusDTO>{

    @Override
    public PrgShipmentStatusDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgShipmentStatusDTO ss=new PrgShipmentStatusDTO();
        ss.setActive(rs.getBoolean("ACTIVE"));
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        ss.setLabel(label);
        ss.setShipmentStatusId(rs.getInt("SHIPMENT_STATUS_ID"));
        return ss;
    }
    
    
    
}
