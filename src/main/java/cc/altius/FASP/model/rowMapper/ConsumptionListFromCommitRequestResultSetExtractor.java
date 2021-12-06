/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.ConsumptionBatchInfo;
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
public class ConsumptionListFromCommitRequestResultSetExtractor implements ResultSetExtractor<List<Consumption>> {

    @Override
    public List<Consumption> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Consumption> consumptionList = new LinkedList<>();
        int oldId = -1, curId = 0;
        Consumption c = null;
        while (rs.next()) {
            curId = rs.getInt("ID");
            if (curId != oldId) {
                c = new Consumption();
                c.setConsumptionId(rs.getInt("CONSUMPTION_ID"));
                c.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 1)));
                c.setPlanningUnit(new SimplePlanningUnitObject(
                        rs.getInt("PLANNING_UNIT_ID"),
                        new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1),
                        new SimpleForecastingUnitObject(
                                rs.getInt("FORECASTING_UNIT_ID"),
                                new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, 1),
                                new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, 1)))));
                c.setRealmCountryPlanningUnit(new SimpleObject(rs.getInt("REALM_COUNTRY_PLANNING_UNIT_ID"), new LabelRowMapper("RCPU_").mapRow(rs, 1)));
                c.setMultiplier(rs.getDouble("MULTIPLIER"));
                c.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
                c.setConsumptionDate(rs.getString("CONSUMPTION_DATE"));
                c.setActualFlag(rs.getBoolean("ACTUAL_FLAG"));
                c.setConsumptionRcpuQty(rs.getDouble("CONSUMPTION_RCPU_QTY"));
                c.setConsumptionQty(rs.getDouble("CONSUMPTION_QTY"));
                c.setDayOfStockOut(rs.getInt("DAYS_OF_STOCK_OUT"));
                c.setDataSource(new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, 1)));
                c.setNotes(rs.getString("NOTES"));
                c.setVersionId(rs.getInt("VERSION_ID"));
                c.setActive(rs.getBoolean("ACTIVE"));
                c.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                c.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
                c.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
                c.setLastModifiedBy(new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")));
                consumptionList.add(c);
            }
            ConsumptionBatchInfo cb = new ConsumptionBatchInfo();
            cb.setConsumptionTransBatchInfoId(rs.getInt("CONSUMPTION_TRANS_BATCH_INFO_ID"));
            if (!rs.wasNull()) {
                cb.setBatch(new Batch(rs.getInt("BATCH_ID"), rs.getInt("BATCH_PLANNING_UNIT_ID"), rs.getString("BATCH_NO"), rs.getBoolean("AUTO_GENERATED"), rs.getDate("EXPIRY_DATE")));
                cb.getBatch().setCreatedDate(rs.getDate("BATCH_CREATED_DATE"));
                cb.setConsumptionQty(rs.getDouble("BATCH_QTY"));
                c.getBatchInfoList().add(cb);
            }
            oldId = curId;
        }
        return consumptionList;
    }
}
