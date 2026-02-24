/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentDetailsFundingSourceProcurementAgentRowMapper implements RowMapper<ShipmentDetailsFundingSourceProcurementAgent> {

    @Override
    public ShipmentDetailsFundingSourceProcurementAgent mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentDetailsFundingSourceProcurementAgent sdfs = new ShipmentDetailsFundingSourceProcurementAgent();
        sdfs.setFspa(new SimpleCodeObject(rs.getInt("FSPA_ID"), new LabelRowMapper("FSPA_").mapRow(rs, i), rs.getString("FSPA_CODE")));
        sdfs.setCountries(rs.getInt("COUNTRIES"));
        sdfs.setPrograms(rs.getInt("PROGRAMS"));
        sdfs.setShipments(rs.getInt("SHIPMENTS"));
        sdfs.setCost(rs.getDouble("COST"));
        return sdfs;
    }

}
