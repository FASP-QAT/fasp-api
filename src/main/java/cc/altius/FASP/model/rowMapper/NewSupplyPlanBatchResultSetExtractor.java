/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BatchData;
import cc.altius.FASP.model.NewSupplyPlan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class NewSupplyPlanBatchResultSetExtractor implements ResultSetExtractor<List<NewSupplyPlan>> {

    private List<NewSupplyPlan> nspList;

    public NewSupplyPlanBatchResultSetExtractor(List<NewSupplyPlan> nspList) {
        this.nspList = nspList;
    }

    @Override
    public List<NewSupplyPlan> extractData(ResultSet rs) throws SQLException, DataAccessException {
        while (rs.next()) {
            NewSupplyPlan nsp = new NewSupplyPlan(rs.getInt("PLANNING_UNIT_ID"), rs.getString("TRANS_DATE"));
            int idx = this.nspList.indexOf(nsp);
            if (idx == -1) {
                throw new SQLException("Cannot find record for planningUnitId:" + nsp.getPlanningUnitId() + " transDate:" + nsp.getTransDate());
            } else {
                nsp = this.nspList.get(idx);
            }
            BatchData bd = new BatchData();
            bd.setBatchId(rs.getInt("BATCH_ID"));
            if (rs.wasNull()) {
                bd.setBatchId(null);
            }
            bd.setExpiryDate(rs.getString("EXPIRY_DATE"));
            bd.setShelfLife(rs.getInt("SHELF_LIFE"));
            bd.setActualConsumption(rs.getDouble("ACTUAL_CONSUMPTION"));
            if (rs.wasNull()) {
                bd.setActualConsumption(null);
            }
            bd.setShipment(rs.getDouble("SHIPMENT"));
            bd.setShipmentWps(rs.getDouble("SHIPMENT_WPS"));
            bd.setAdjustment(rs.getDouble("ADJUSTMENT"));
            if (rs.wasNull()) {
                bd.setAdjustment(null);
            }
            bd.setStock(rs.getDouble("STOCK"));
            if (rs.wasNull()) {
                bd.setStock(null);
            }
            bd.setInventoryQty(rs.getLong("INVENTORY_QTY"));
            if (rs.wasNull()) {
                bd.setInventoryQty(null);
            }
            bd.setAllRegionsReportedStock(nsp.isAllRegionsReportedStock());
            bd.setUseActualConsumption(nsp.isActualConsumptionFlag());
            nsp.getBatchDataList().add(bd);
        }
        return this.nspList;
    }

}
