/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PlanningUnitArtmisPull;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class PlanningUnitArtmisPullRowMapper implements RowMapper<PlanningUnitArtmisPull> {

    @Override
    public PlanningUnitArtmisPull mapRow(ResultSet rs, int i) throws SQLException {
        PlanningUnitArtmisPull p = new PlanningUnitArtmisPull();
        p.setId(rs.getInt("ID"));
        p.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        p.setLabel(rs.getString("LABEL"));
        p.setMultiplier(rs.getDouble("MULTIPLIER"));
        p.setUnitId(rs.getInt("UNIT_ID"));
        p.setSkuCode(rs.getString("SKU_CODE"));
        p.setForecastingUnitId(rs.getInt("FORECASTING_UNIT_ID"));
        p.setCatalogPrice(rs.getDouble("CATALOG_PRICE"));
        p.setMoq(rs.getInt("MOQ"));
        p.setUnitsPerPalletEuro1(rs.getInt("UNITS_PER_PALLET_EURO1"));
        p.setUnitsPerPalletEuro2(rs.getInt("UNITS_PER_PALLET_EURO2"));
        p.setUnitsPerContainer(rs.getInt("UNITS_PER_CONTAINER"));
        p.setVolume(rs.getDouble("VOLUME"));
        p.setWeight(rs.getDouble("WEIGHT"));
        p.setFound(rs.getBoolean("FOUND"));
        p.setDuplicate((rs.getBoolean("DUPLICATE")));
        return p;
    }

}
