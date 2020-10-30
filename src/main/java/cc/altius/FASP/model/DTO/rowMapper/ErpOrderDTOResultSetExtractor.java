/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ErpOrderDTO;
import cc.altius.FASP.model.DTO.ErpShipmentDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author altius
 */
public class ErpOrderDTOResultSetExtractor implements ResultSetExtractor<ErpOrderDTO> {

    @Override
    public ErpOrderDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
        ErpOrderDTO e = new ErpOrderDTO();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                e.setEoErpOrderId(rs.getInt("ERP_ORDER_ID"));
                e.setEoRoNo(rs.getString("RO_NO"));
                e.setEoRoPrimeLineNo(rs.getString("RO_PRIME_LINE_NO"));
                e.setEoOrderNo(rs.getString("ORDER_NO"));
                e.setEoPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
                e.setEoOrderType(rs.getString("ORDER_TYPE"));
                e.setEoCreatedDate(rs.getTimestamp("CREATED_DATE"));
                e.setEoParentRo(rs.getString("PARENT_RO"));
                e.setEoParentCreatedDate(rs.getTimestamp("PARENT_CREATED_DATE"));
                e.setEoPlanningUnitSkuCode(rs.getString("PLANNING_UNIT_SKU_CODE"));
                e.setEoProcurementUnitSkuCode(rs.getString("PROCUREMENT_UNIT_SKU_CODE"));
                e.setEoQty(rs.getInt("QTY"));
                e.setEoOrderedDate(rs.getDate("ORDERD_DATE"));
                e.setEoCurrentEstimatedDeliveryDate(rs.getDate("CURRENT_ESTIMATED_DELIVERY_DATE"));
                e.setEoReqDeliveryDate(rs.getDate("REQ_DELIVERY_DATE"));
                e.setEoAgreedDeliveryDate(rs.getDate("AGREED_DELIVERY_DATE"));
                e.setEoSupplierName(rs.getString("SUPPLIER_NAME"));
                e.setEoPrice(rs.getDouble("PRICE"));
                e.setEoShippingCost(rs.getDouble("SHIPPING_COST"));
                e.setEoShipBy(rs.getString("SHIP_BY"));
                e.setEoRecipentName(rs.getString("RECPIENT_NAME"));
                e.setEoRecipentCountry(rs.getString("RECPIENT_COUNTRY"));
                e.setEoStatus(rs.getString("STATUS"));
                e.setEoPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
                e.setEoProcurementUnitId(rs.getInt("PROCUREMENT_UNIT_ID"));
                e.setEoSupplierId(rs.getInt("SUPPLIER_ID"));
                e.setShProgramId(rs.getInt("PROGRAM_ID"));
                e.setShShipmentId(rs.getInt("SHIPMENT_ID"));
                e.setShVersionId(rs.getInt("VERSION_ID"));
                e.setManualTagging(rs.getBoolean("MANUAL_TAGGING_ID"));

                e.setShProgramId(rs.getInt("PROGRAM_ID"));
                e.setShShipmentId(rs.getInt("SHIPMENT_ID"));
                e.setShVersionId(rs.getInt("VERSION_ID"));
                e.setShActive(rs.getBoolean("ACTIVE"));
                if (rs.wasNull()) {
                    e.setShActive(null);
                }
                e.setShErpFlag(rs.getBoolean("ERP_FLAG"));
                if (rs.wasNull()) {
                    e.setShErpFlag(null);
                }
                e.setShParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
                if (rs.wasNull()) {
                    e.setShParentShipmentId(null);
                }
                e.setShFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
                if (rs.wasNull()) {
                    e.setShFundingSourceId(null);
                }
                e.setShProcurementAgentId(rs.getInt("PROCUREMENT_AGENT_ID"));
                if (rs.wasNull()) {
                    e.setShProcurementAgentId(null);
                }
                e.setShBudgetId(rs.getInt("BUDGET_ID"));
                if (rs.wasNull()) {
                    e.setShBudgetId(null);
                }
                e.setShAccountFlag(rs.getBoolean("ACCOUNT_FLAG"));
                if (rs.wasNull()) {
                    e.setShAccountFlag(Boolean.TRUE);
                }
                e.setEoActualShippedDate(rs.getDate("ACTUAL_SHIPPED_DATE"));
                e.setEoActualDeliveryDate(rs.getDate("ACTUAL_DELIVERY_DATE"));
                
            }
            ErpShipmentDTO es = new ErpShipmentDTO();
            es.setBatchNo(rs.getString("BATCH_NO"));
            if (es.getBatchNo() == null || es.getBatchNo().equals("-99")) {
                // you need to create your own Batch here
                es.setBatchQty(e.getEoQty());
                es.setBatchNo("TEMP");
            } else {
                es.setBatchQty(rs.getInt("BATCH_QTY"));
                e.getEoShipmentList().add(es);
            }
        }
        return e;
    }

}
