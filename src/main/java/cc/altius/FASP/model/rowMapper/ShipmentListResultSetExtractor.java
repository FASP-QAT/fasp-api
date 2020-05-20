/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.ShipmentBudget;
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
        boolean isFirst = true;
        Shipment s = new Shipment();
        while (rs.next()) {
            int shipmentId = rs.getInt("SHIPMENT_ID");
            Shipment tmpShip = new Shipment();
            tmpShip.setShipmentId(shipmentId);
            if (sList.indexOf(tmpShip) == -1) {
                if (!isFirst) {
                    s = new Shipment();
                }
                sList.add(s);
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
                s.setSuggestedQty(rs.getDouble("SUGGESTED_QTY"));
                s.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE")));
                s.setProcurementUnit(new SimpleObject(rs.getInt("PROCUREMENT_UNIT_ID"), new LabelRowMapper("PROCUREMENT_UNIT_").mapRow(rs, 1)));
                s.setSupplier(new SimpleObject(rs.getInt("SUPPLIER_ID"), new LabelRowMapper("SUPPLIER_").mapRow(rs, 1)));
                s.setQuantity(rs.getDouble("QUANTITY"));
                s.setRate(rs.getDouble("RATE"));
                s.setProductCost(rs.getDouble("PRODUCT_COST"));
                s.setShipmentMode(rs.getString("SHIPPING_MODE"));
                s.setFreightCost(rs.getDouble("FREIGHT_COST"));
                s.setOrderedDate(rs.getDate("ORDERED_DATE"));
                s.setShippedDate(rs.getDate("SHIPPED_DATE"));
                s.setReceivedDate(rs.getDate("RECEIVED_DATE"));
                s.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, 1)));
                s.setNotes(rs.getString("NOTES"));
                s.setDataSource(new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, 1)));
                s.setAccountFlag(rs.getBoolean("ACCOUNT_FLAG"));
                s.setErpFlag(rs.getBoolean("ERP_FLAG"));
                s.setOrderNo(rs.getString("ORDER_NO"));
                s.setPrimeLineNo(rs.getString("PRIME_LINE_NO"));
                s.setVersionId(rs.getInt("VERSION_ID"));
                s.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
            } else {
                s = sList.get(sList.indexOf(tmpShip));
            }
            ShipmentBudget sb = new ShipmentBudget(
                    rs.getInt("SHIPMENT_BUDGET_ID"),
                    new Budget(
                            rs.getInt("BUDGET_ID"), 
                            new FundingSource(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, 1)),
                            new LabelRowMapper("BUDGET_").mapRow(rs, 1)
                    ), 
                    rs.getBoolean("SHIPMENT_BUDGET_ACTIVE"),
                    rs.getDouble("BUDGET_AMT"), 
                    rs.getDouble("CONVERSION_RATE_TO_USD"),
                    new Currency(
                            rs.getInt("CURRENCY_ID"), 
                            rs.getString("CURRENCY_CODE"), 
                            new LabelRowMapper("CURRENCY_").mapRow(rs, 1), 
                            rs.getDouble("CURRENCY_CONVERSION_RATE_TO_USD")
                    )
            );
            if (s.getShipmentBudgetList().indexOf(sb) == -1) {
                s.getShipmentBudgetList().add(sb);
            }
            isFirst = false;
        }
        return sList;
    }

}
