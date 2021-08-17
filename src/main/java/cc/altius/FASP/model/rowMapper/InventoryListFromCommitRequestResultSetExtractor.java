/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.InventoryBatchInfo;
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
public class InventoryListFromCommitRequestResultSetExtractor implements ResultSetExtractor<List<Inventory>> {

    @Override
    public List<Inventory> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Inventory> inventoryList = new LinkedList<>();
        int oldId = -1, curId = 0;
        Inventory i = null;
        while (rs.next()) {
            curId = rs.getInt("ID");
            if (curId != oldId) {
                i = new Inventory();
                i.setInventoryId(rs.getInt("INVENTORY_ID"));
                i.setInventoryDate(rs.getString("INVENTORY_DATE"));
                i.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 1)));
                i.setRealmCountryPlanningUnit(new SimpleObject(rs.getInt("REALM_COUNTRY_PLANNING_UNIT_ID"), new LabelRowMapper("REALM_COUNTRY_PLANNING_UNIT_").mapRow(rs, 1)));
                i.setPlanningUnit(new SimplePlanningUnitObject(
                        rs.getInt("PLANNING_UNIT_ID"),
                        new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1),
                        new SimpleForecastingUnitObject(
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
                i.setActualQty(rs.getDouble("ACTUAL_QTY"));
                if (rs.wasNull()) {
                    i.setActualQty(null);
                }
                i.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                i.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
                i.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
                i.setLastModifiedBy(new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")));
                inventoryList.add(i);
            }
            InventoryBatchInfo ib = new InventoryBatchInfoRowMapper().mapRow(rs, 1);
            ib.setInventoryTransBatchInfoId(rs.getInt("INVENTORY_TRANS_BATCH_INFO_ID"));
            if (!rs.wasNull()) {
                ib.setBatch(new Batch(rs.getInt("BATCH_ID"), rs.getInt("BATCH_PLANNING_UNIT_ID"), rs.getString("BATCH_NO"), rs.getBoolean("AUTO_GENERATED"), rs.getDate("EXPIRY_DATE")));
                ib.getBatch().setCreatedDate(rs.getDate("BATCH_CREATED_DATE"));
                ib.setAdjustmentQty(rs.getDouble("BATCH_ADJUSTMENT_QTY"));
                if (rs.wasNull()) {
                    ib.setAdjustmentQty(null);
                }
                ib.setActualQty(rs.getDouble("BATCH_ACTUAL_QTY"));
                if (rs.wasNull()) {
                    ib.setActualQty(null);
                }
                i.getBatchInfoList().add(ib);
            }
            oldId = curId;
        }
        return inventoryList;
    }
}
