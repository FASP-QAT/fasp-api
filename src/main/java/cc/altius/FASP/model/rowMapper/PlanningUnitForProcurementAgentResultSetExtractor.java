/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.PlanningUnitForProcurementAgentMapping;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class PlanningUnitForProcurementAgentResultSetExtractor implements ResultSetExtractor<ProcurementAgentPlanningUnit> {

    @Override
    public ProcurementAgentPlanningUnit extractData(ResultSet rs) throws SQLException, DataAccessException {
        ProcurementAgentPlanningUnit ppu = new ProcurementAgentPlanningUnit();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                ppu.setProcurementAgentId(rs.getInt("PROCUREMENT_AGENT_ID"));
                ppu.setLabel(new LabelRowMapper().mapRow(rs, 1));
                ppu.setPlanningUnitList(new LinkedList<>());
            }
            if (rs.getInt("PLANNING_UNIT_ID") > 0) {
                PlanningUnitForProcurementAgentMapping sp = new PlanningUnitForProcurementAgentMapping();
                sp.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
                sp.setLabel(new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1));
                sp.setSkuCode(rs.getString("SKU_CODE"));
                sp.setCatalogPrice(rs.getDouble("CATALOG_PRICE"));
                sp.setMoq(rs.getInt("MOQ"));
                sp.setUnitsPerPallet(rs.getInt("UNITS_PER_PALLET"));
                sp.setUnitsPerContainer(rs.getInt("UNITS_PER_CONTAINER"));
                sp.setVolume(rs.getDouble("VOLUME"));
                sp.setWeight(rs.getDouble("WEIGHT"));
                sp.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                if (ppu.getPlanningUnitList().indexOf(sp) == -1) {
                    ppu.getPlanningUnitList().add(sp);
                }
            }
            isFirst = false;
        }
        return ppu;
    }
}
