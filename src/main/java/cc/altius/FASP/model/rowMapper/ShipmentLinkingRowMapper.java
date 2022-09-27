/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ShipmentLinking;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentLinkingRowMapper implements RowMapper<ShipmentLinking> {

    @Override
    public ShipmentLinking mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShipmentLinking ls = new ShipmentLinking();
        ls.setShipmentLinkingId(rs.getInt("SHIPMENT_LINKING_ID"));
        ls.setProgramId(rs.getInt("PROGRAM_ID"));
        ls.setVersionId(rs.getInt("VERSION_ID"));
        ls.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PA_").mapRow(rs, rowNum), rs.getString("PROCUREMENT_AGENT_CODE")));
        ls.setParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        ls.setChildShipmentId(rs.getInt("CHILD_SHIPMENT_ID"));
        ls.setErpPlanningUnit(new SimpleObject(rs.getInt("ERP_PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum)));
        ls.setErpShipmentStatus(rs.getString("ERP_SHIPMENT_STATUS"));
        ls.setRoNo(rs.getString("RO_NO"));
        ls.setRoPrimeLineNo(rs.getString("RO_PRIME_LINE_NO"));
        ls.setOrderNo(rs.getString("ORDER_NO"));
        ls.setPrimeLineNo(rs.getString("PRIME_LINE_NO"));
        ls.setKnShipmentNo(rs.getString("KN_SHIPMENT_NO"));
        ls.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
        ls.setQatPlanningUnitId(rs.getInt("QAT_PLANNING_UNIT_ID"));
        if (rs.wasNull()) {
            ls.setQatPlanningUnitId(null);
        }
        ls.setVersionId(rs.getInt("VERSION_ID"));
        ls.setTempParentShipmentId(null);
        ls.setTempChildShipmentId(null);
        ls.setActive(rs.getBoolean("ACTIVE"));
        ls.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        ls.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        ls.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
        ls.setLastModifiedBy(new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")));
        return ls;
    }

}
