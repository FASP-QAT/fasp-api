/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ProcurementUnitArtmisPull;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementUnitArtmisPullRowMapper implements RowMapper<ProcurementUnitArtmisPull> {

    @Override
    public ProcurementUnitArtmisPull mapRow(ResultSet rs, int i) throws SQLException {
        ProcurementUnitArtmisPull p = new ProcurementUnitArtmisPull();
        p.setProcurementUnitId(rs.getInt("PROCUREMENT_UNIT_ID"));
        p.setLabel(rs.getString("LABEL"));
        p.setMultiplier(rs.getDouble("MULTIPLIER"));
        p.setUnitId(rs.getInt("UNIT_ID"));
        p.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        p.setSupplierId(rs.getInt("SUPPLIER_ID"));
        p.setLength(rs.getDouble("LENGTH"));
        p.setLengthUnitId(rs.getInt("LENGTH_UNIT_ID"));
        p.setHeight(rs.getDouble("HEIGHT"));
        p.setHeightUnitId(rs.getInt("HEIGHT_UNIT_ID"));
        p.setWidth(rs.getDouble("WIDTH"));
        p.setWidthUnitId(rs.getInt("WIDTH_UNIT_ID"));
        p.setWeight(rs.getDouble("WEIGHT"));
        p.setWeightUnitId(rs.getInt("WEIGHT_UNIT_ID"));
        p.setUnitsPerCase(rs.getInt("UNITS_PER_CASE"));
        p.setUnitsPerPallet(rs.getInt("UNITS_PER_PALLET"));
        p.setUnitsPerContainer(rs.getInt("UNITS_PER_CONTAINER"));
        p.setLabelling(rs.getString("LABELLING"));
        p.setGtin(rs.getString("GTIN"));
        p.setSkuCode(rs.getString("SKU_CODE"));
        p.setVendorPrice(rs.getDouble("VENDOR_PRICE"));
        p.setApprovedToShippedLeadTime(rs.getDouble("APPROVED_TO_SHIPPED_LEAD_TIME"));
        p.setFound(rs.getBoolean("FOUND"));
        return p;
    }

}
