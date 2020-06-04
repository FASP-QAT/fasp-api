/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementAgentPlanningUnitRowMapper implements RowMapper<ProcurementAgentPlanningUnit> {

    @Override
    public ProcurementAgentPlanningUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcurementAgentPlanningUnit papu = new ProcurementAgentPlanningUnit(
                rs.getInt("PROCUREMENT_AGENT_PLANNING_UNIT_ID"),
                new SimpleObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, rowNum)),
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, rowNum))
        );
        papu.setSkuCode(rs.getString("SKU_CODE"));
        papu.setCatalogPrice(rs.getDouble("CATALOG_PRICE"));
        papu.setMoq(rs.getInt("MOQ"));
        papu.setUnitsPerPallet(rs.getInt("UNITS_PER_PALLET"));
        papu.setUnitsPerContainer(rs.getInt("UNITS_PER_CONTAINER"));
        papu.setVolume(rs.getDouble("VOLUME"));
        papu.setWeight(rs.getDouble("WEIGHT"));
        papu.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
        return papu;
    }
}
