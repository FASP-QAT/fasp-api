/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.SimpleCodeObject;
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
                new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, rowNum), rs.getString("PROCUREMENT_AGENT_CODE")),
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, rowNum))
        );
        papu.setSkuCode(rs.getString("SKU_CODE"));
        papu.setCatalogPrice(rs.getDouble("CATALOG_PRICE"));
        if(rs.wasNull()) {
            papu.setCatalogPrice(null);
        }
        papu.setMoq(rs.getInt("MOQ"));
        if(rs.wasNull()) {
            papu.setMoq(null);
        }
        papu.setUnitsPerPalletEuro1(rs.getInt("UNITS_PER_PALLET_EURO1"));
        if(rs.wasNull()) {
            papu.setUnitsPerPalletEuro1(null);
        }
        papu.setUnitsPerPalletEuro2(rs.getInt("UNITS_PER_PALLET_EURO2"));
        if(rs.wasNull()) {
            papu.setUnitsPerPalletEuro2(null);
        }
        papu.setUnitsPerContainer(rs.getInt("UNITS_PER_CONTAINER"));
        if(rs.wasNull()) {
            papu.setUnitsPerContainer(null);
        }
        papu.setVolume(rs.getDouble("VOLUME"));
        if(rs.wasNull()) {
            papu.setVolume(null);
        }
        papu.setWeight(rs.getDouble("WEIGHT"));
        if(rs.wasNull()) {
            papu.setWeight(null);
        }
        papu.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
        return papu;
    }
}
