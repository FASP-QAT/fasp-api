/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentGlobalDemandShipmentListRowMapper implements RowMapper<ShipmentGlobalDemandShipmentList> {

    @Override
    public ShipmentGlobalDemandShipmentList mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentGlobalDemandShipmentList sl = new ShipmentGlobalDemandShipmentList();
        sl.setCountry(new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")));
        sl.setTransDate(rs.getDate("TRANS_DATE"));
        sl.setAmount(rs.getDouble("AMOUNT"));
        sl.setFundingSourceProcurementAgent(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_PROCUREMENT_AGENT_ID"), new LabelRowMapper("FUNDING_SOURCE_PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_PROCUREMENT_AGENT_CODE")));
        sl.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, i)));
        return sl;
    }

}
