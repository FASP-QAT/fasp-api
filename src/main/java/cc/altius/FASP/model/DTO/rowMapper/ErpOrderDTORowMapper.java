/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ErpOrderDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ErpOrderDTORowMapper implements RowMapper<ErpOrderDTO> {

    @Override
    public ErpOrderDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        ErpOrderDTO e = new ErpOrderDTO();
        e.setErpOrderId(rs.getInt("ERP_ORDER_ID"));
        e.setShipmentId(rs.getInt("SHIPMENT_ID"));
        e.setQuantity(rs.getInt("QTY"));
        e.setOrderNo(rs.getString("ORDER_NO"));
        e.setPrimeLineNo("PRIME_LINE_NO");
        return e;
    }

}
