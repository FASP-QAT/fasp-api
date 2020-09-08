/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NewSupplyPlan;
import cc.altius.FASP.model.RegionData;
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
public class NewSupplyPlanRegionResultSetExtractor implements ResultSetExtractor<List<NewSupplyPlan>> {

    @Override
    public List<NewSupplyPlan> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<NewSupplyPlan> nspList = new LinkedList<>();
        while (rs.next()) {
            NewSupplyPlan nsp = new NewSupplyPlan(rs.getInt("PLANNING_UNIT_ID"), rs.getDate("TRANS_DATE"));
            int idx = nspList.indexOf(nsp);
            if (idx == -1) {
                nspList.add(nsp);
            } else {
                nsp = nspList.get(idx);
            }
            RegionData rd = new RegionData();
            rd.setRegionId(rs.getInt("REGION_ID"));
            if (rs.wasNull()) {
                nsp.setShipment(rs.getInt("SHIPMENT"));
                if (rs.wasNull()) {
                    nsp.setShipment(null);
                }
            } else {
                nsp.setActualConsumptionFlag(rs.getBoolean("USE_ACTUAL_CONSUMPTION"));
                nsp.setRegionCountForStock(rs.getInt("REGION_STOCK_COUNT"));
                nsp.setRegionCount(rs.getInt("REGION_COUNT"));
                rd.setForecastedConsumption(rs.getInt("FORECASTED_CONSUMPTION"));
                if (rs.wasNull()) {
                    rd.setForecastedConsumption(null);
                }
                rd.setActualConsumption(rs.getInt("ACTUAL_CONSUMPTION"));
                if (rs.wasNull()) {
                    rd.setActualConsumption(null);
                }
                nsp.addFinalConsumption(nsp.isActualConsumptionFlag() ? rd.getActualConsumption() : rd.getForecastedConsumption());
                rd.setStock(rs.getInt("STOCK"));
                if (rs.wasNull()) {
                    rd.setStock(null);
                }
                rd.setAdjustment(rs.getInt("ADJUSTMENT"));
                if (rs.wasNull()) {
                    rd.setAdjustment(null);
                }
                if (rd.getStock() != null) {
                    nsp.addStock(rd.getStock());
                } else {
                    nsp.addAdjustment(rd.getAdjustment());
                }

                nsp.getRegionDataList().add(rd);
            }
        }
        return nspList;
    }

}
