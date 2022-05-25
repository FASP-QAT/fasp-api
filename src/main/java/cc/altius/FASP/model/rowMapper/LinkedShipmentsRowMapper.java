/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.LinkedShipments;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class LinkedShipmentsRowMapper implements RowMapper<LinkedShipments> {

    @Override
    public LinkedShipments mapRow(ResultSet rs, int rowNum) throws SQLException {
        LinkedShipments ls = new LinkedShipments();
        ls.setErpShipmentLinkingId(rs.getInt("ERP_SHIPMENT_LINKING_ID"));
        ls.setVersionId(rs.getInt("VERSION_ID"));
        ls.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PA_").mapRow(rs, rowNum), rs.getString("PROCUREMENT_AGENT_CODE")));
        ls.setParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        ls.setChildShipmentId(rs.getInt("CHILD_SHIPMENT_ID"));
        ls.setErpPlanningUnit(new SimpleObject(rs.getInt("ERP_PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum)));
        ls.setRoNo(rs.getString("RO_NO"));
        ls.setRoPrimeLineNo(rs.getString("RO_PRIME_LINE_NO"));
        ls.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
        ls.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return ls;
    }

}
