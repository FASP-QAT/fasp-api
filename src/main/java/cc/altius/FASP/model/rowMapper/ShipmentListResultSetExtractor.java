/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.ShipmentBatchInfo;
import cc.altius.FASP.model.SimpleBudgetObject;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleForecastingUnitObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ShipmentListResultSetExtractor implements ResultSetExtractor<List<Shipment>> {

    @Override
    public List<Shipment> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Shipment> sList = new LinkedList<Shipment>();
        while (rs.next()) {
            int shipmentId = rs.getInt("SHIPMENT_ID");
            Shipment s = new Shipment();
            s.setShipmentId(shipmentId);
            int idx = sList.indexOf(s);
            if (idx == -1) {
                sList.add(s);
            } else {
                s = sList.get(idx);
            }
            s.setShipmentId(rs.getInt("SHIPMENT_ID"));
            s.setPlanningUnit(
                    new SimplePlanningUnitObject(
                            rs.getInt("PLANNING_UNIT_ID"),
                            new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1),
                            new SimpleForecastingUnitObject(
                                    rs.getInt("FORECASTING_UNIT_ID"),
                                    new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, 1),
                                    new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, 1))))
            );
            s.setExpectedDeliveryDate(rs.getDate("EXPECTED_DELIVERY_DATE"));
            s.setSuggestedQty(rs.getInt("SUGGESTED_QTY"));
            s.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE")));
            s.setFundingSource(new SimpleObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, 1)));
            s.setBudget(new SimpleBudgetObject(
                    rs.getInt("BUDGET_ID"), 
                    new LabelRowMapper("BUDGET_").mapRow(rs, 1), 
                    new Currency(
                            rs.getInt("BUDGET_CURRENCY_ID"),
                            rs.getString("BUDGET_CURRENCY_CODE"),
                            new LabelRowMapper("BUDGET_CURRENCY_").mapRow(rs, 1), 
                            rs.getDouble("BUDGET_CURRENCY_CONVERSION_RATE_TO_USD")
                    )
            ));
            s.setProcurementUnit(new SimpleObject(rs.getInt("PROCUREMENT_UNIT_ID"), new LabelRowMapper("PROCUREMENT_UNIT_").mapRow(rs, 1)));
            s.setSupplier(new SimpleObject(rs.getInt("SUPPLIER_ID"), new LabelRowMapper("SUPPLIER_").mapRow(rs, 1)));
            s.setShipmentQty(rs.getInt("SHIPMENT_QTY"));
            s.setRate(rs.getDouble("RATE"));
            s.setProductCost(rs.getDouble("PRODUCT_COST"));
            s.setShipmentMode(rs.getString("SHIPMENT_MODE"));
            s.setFreightCost(rs.getDouble("FREIGHT_COST"));
            s.setOrderedDate(rs.getDate("ORDERED_DATE"));
            s.setShippedDate(rs.getDate("SHIPPED_DATE"));
            s.setDeliveredDate(rs.getDate("DELIVERED_DATE"));
            s.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, 1)));
            s.setNotes(rs.getString("NOTES"));
            s.setDataSource(new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, 1)));
            s.setAccountFlag(rs.getBoolean("ACCOUNT_FLAG"));
            s.setErpFlag(rs.getBoolean("ERP_FLAG"));
            s.setOrderNo(rs.getString("ORDER_NO"));
            s.setPrimeLineNo(rs.getString("PRIME_LINE_NO"));
            s.setEmergencyOrder(rs.getBoolean("EMERGENCY_ORDER"));
            s.setVersionId(rs.getInt("VERSION_ID"));
            s.setCurrency(new Currency(
                    rs.getInt("SHIPMENT_CURRENCY_ID"),
                    rs.getString("SHIPMENT_CURRENCY_CODE"),
                    new LabelRowMapper("SHIPMENT_CURRENCY_").mapRow(rs, 1),
                    rs.getDouble("SHIPMENT_CONVERSION_RATE_TO_USD")
            ));
            s.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
            ShipmentBatchInfo sbi = new ShipmentBatchInfoRowMapper().mapRow(rs, 1);
            if (sbi != null && s.getBatchInfoList().indexOf(sbi) == -1) {
                s.getBatchInfoList().add(sbi);
            }
        }
        return sList;
    }

}
