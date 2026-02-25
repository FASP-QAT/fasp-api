/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
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
public class ShipmentOverviewPlanningUnitQuantityResultSetExtractor implements ResultSetExtractor<List<ShipmentOverviewPlanningUnitQuantity>> {

    @Override
    public List<ShipmentOverviewPlanningUnitQuantity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ShipmentOverviewPlanningUnitQuantity> puList = new LinkedList<>();
        while (rs.next()) {
            ShipmentOverviewPlanningUnitQuantity item = new ShipmentOverviewPlanningUnitQuantity();
            item.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper().mapRow(rs, 0)));
            int idx = puList.indexOf(item);
            if (idx == -1) {
                puList.add(item);
            } else {
                item = puList.get(idx);
            }
            String fspa = rs.getString("FSPA_CODE");
            if (item.getFspaQuantity().containsKey(fspa)) {
                item.getFspaQuantity().put(fspa, item.getFspaQuantity().get(fspa) + rs.getDouble("SHIPMENT_QTY"));
            } else {
                item.getFspaQuantity().put(fspa, rs.getDouble("SHIPMENT_QTY"));
            }
        }
        return puList;
    }

}
