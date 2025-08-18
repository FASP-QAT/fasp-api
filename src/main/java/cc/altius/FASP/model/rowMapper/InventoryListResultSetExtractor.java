/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.InventoryBatchInfo;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleForecastingUnitProductCategoryObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitProductCategoryObject;
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
public class InventoryListResultSetExtractor implements ResultSetExtractor<List<Inventory>> {
    
    @Override
    public List<Inventory> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Inventory> inventoryList = new LinkedList<>();
        while (rs.next()) {
            Inventory i = new Inventory();
            i.setInventoryId(rs.getInt("INVENTORY_ID"));
            int idx = inventoryList.indexOf(i);
            if (idx == -1) {
                inventoryList.add(i);
            } else {
                i = inventoryList.get(idx);
            }
            i.setInventoryDate(rs.getString("INVENTORY_DATE"));
            i.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 1)));
            i.setRealmCountryPlanningUnit(new SimpleObject(rs.getInt("REALM_COUNTRY_PLANNING_UNIT_ID"), new LabelRowMapper("REALM_COUNTRY_PLANNING_UNIT_").mapRow(rs, 1)));
            i.setPlanningUnit(new SimplePlanningUnitProductCategoryObject(
                    rs.getInt("PLANNING_UNIT_ID"),
                    new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1),
                    new SimpleForecastingUnitProductCategoryObject(
                            rs.getInt("FORECASTING_UNIT_ID"),
                            new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, 1),
                            new SimpleObject(
                                    rs.getInt("PRODUCT_CATEGORY_ID"),
                                    new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, 1))
                    )));
            i.setMultiplier(rs.getDouble("MULTIPLIER"));
            i.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
            i.setAdjustmentQty(rs.getDouble("ADJUSTMENT_QTY"));
            if (rs.wasNull()) {
                i.setAdjustmentQty(null);
            }
            i.setExpectedBal(rs.getDouble("EXPECTED_BAL"));
            i.setUnit(new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, 1), rs.getString("UNIT_CODE")));
            i.setDataSource(new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, 1)));
            i.setNotes(rs.getString("NOTES"));
            i.setVersionId(rs.getInt("VERSION_ID"));
            i.setActive(rs.getBoolean("ACTIVE"));
            i.setAddNewBatch(rs.getBoolean("ADD_NEW_BATCH"));
            i.setActualQty(rs.getDouble("ACTUAL_QTY"));
            if (rs.wasNull()) {
                i.setActualQty(null);
            }
            i.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
            i.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
            i.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
            i.setLastModifiedBy(new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")));
            InventoryBatchInfo ib = new InventoryBatchInfoRowMapper().mapRow(rs, 1);
            if (ib != null && i.getBatchInfoList().indexOf(ib) == -1) {
                i.getBatchInfoList().add(ib);
            }
        }
        return inventoryList;
    }
}
