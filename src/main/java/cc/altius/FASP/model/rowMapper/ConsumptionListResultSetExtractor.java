/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.ConsumptionBatchInfo;
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
public class ConsumptionListResultSetExtractor implements ResultSetExtractor<List<Consumption>> {

    @Override
    public List<Consumption> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Consumption> consumptionList = new LinkedList<>();
        while (rs.next()) {
            Consumption c = new Consumption();
            c.setConsumptionId(rs.getInt("CONSUMPTION_ID"));
            int idx = consumptionList.indexOf(c);
            if (idx == -1) {
                consumptionList.add(c);
            } else {
                c = consumptionList.get(idx);
            }
            c.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 1)));
            c.setPlanningUnit(new SimplePlanningUnitProductCategoryObject(
                    rs.getInt("PLANNING_UNIT_ID"),
                    new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1),
                    new SimpleForecastingUnitProductCategoryObject(
                            rs.getInt("FORECASTING_UNIT_ID"),
                            new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, 1),
                            new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, 1)))));
            c.setRealmCountryPlanningUnit(new SimpleObject(rs.getInt("REALM_COUNTRY_PLANNING_UNIT_ID"), new LabelRowMapper("RCPU_").mapRow(rs, idx)));
            c.setConversionNumber(rs.getDouble("CONVERSION_NUMBER"));
            c.setConversionMethod(rs.getDouble("CONVERSION_METHOD"));
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
            ConsumptionBatchInfo cb = new ConsumptionBatchInfoRowMapper().mapRow(rs, 1);
            if (cb != null && c.getBatchInfoList().indexOf(cb) == -1) {
                c.getBatchInfoList().add(cb);
            }
        }
        return consumptionList;
    }
}
