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
public class ShipmentGlobalDemandCountryShipmentSplitRowMapper implements RowMapper<ShipmentGlobalDemandCountryShipmentSplit> {

    @Override
    public ShipmentGlobalDemandCountryShipmentSplit mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentGlobalDemandCountryShipmentSplit sgd = new ShipmentGlobalDemandCountryShipmentSplit();
        sgd.setCountry(new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")));
        sgd.setPlannedShipmentAmt(rs.getInt("PLANNED_SHIPMENT_AMT"));
        sgd.setOrderedShipmentAmt(rs.getInt("ORDERED_SHIPMENT_AMT"));
        return sgd;
    }

}
