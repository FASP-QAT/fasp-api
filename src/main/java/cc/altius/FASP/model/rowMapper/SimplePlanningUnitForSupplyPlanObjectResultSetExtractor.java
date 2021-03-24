/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimplePlanningUnitForSupplyPlanObject;
import cc.altius.FASP.model.SimpleProcurementAgentSkuObject;
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
public class SimplePlanningUnitForSupplyPlanObjectResultSetExtractor implements ResultSetExtractor<List<SimplePlanningUnitForSupplyPlanObject>> {

    @Override
    public List<SimplePlanningUnitForSupplyPlanObject> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<SimplePlanningUnitForSupplyPlanObject> puList = new LinkedList<>();
        while (rs.next()) {
            SimplePlanningUnitForSupplyPlanObject pu = new SimplePlanningUnitForSupplyPlanObject();
            pu.setId(rs.getInt("PLANNING_UNIT_ID"));
            int puIdx = puList.indexOf(pu);
            if (puIdx == -1) {
                pu.setLabel(new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1));
                pu.setActive(rs.getBoolean("PROGRAM_PLANNING_UNIT_ACTIVE"));
                pu.setConversionFactor(rs.getDouble("MULTIPLIER"));
            } else {
                pu = puList.get(puIdx);
            }
            SimpleProcurementAgentSkuObject paSku = new SimpleProcurementAgentSkuObject();
            paSku.setId(rs.getInt("PROCUREMENT_AGENT_ID"));
            if (!rs.wasNull()) {
                int paSkuIdx = pu.getProcurementAgentSkuList().indexOf(paSku);
                if (paSkuIdx == -1) {
                    paSku.setLabel(new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, puIdx));
                    paSku.setActive(rs.getBoolean("PROCUREMENT_AGENT_PLANNING_UNIT_ACTIVE"));
                    paSku.setSkuCode(rs.getString("SKU_CODE"));
                    pu.getProcurementAgentSkuList().add(paSku);
                }
            }
            if (puIdx == -1) {
                puList.add(pu);
            }
        }
        return puList;
    }

}
