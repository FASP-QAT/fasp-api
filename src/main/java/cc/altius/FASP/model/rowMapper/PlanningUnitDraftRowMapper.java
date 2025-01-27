package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.PlanningUnitDraft;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * RowMapper for PlanningUnitDraft objects
 */
public class PlanningUnitDraftRowMapper implements RowMapper<PlanningUnitDraft> {
    
    @Override
    public PlanningUnitDraft mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlanningUnitDraft draft = new PlanningUnitDraft();
        
        // Map primary and basic fields
        draft.setPlanningUnitDraftId(rs.getInt("PLANNING_UNIT_DRAFT_ID"));
        draft.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        draft.setAction(rs.getInt("ACTION"));
        draft.setTaskOrder(rs.getString("TaskOrder"));
        draft.setCommodityCouncil(rs.getString("CommodityCouncil"));
        draft.setSubcategory(rs.getString("Subcategory"));
        draft.setTracerCategory(rs.getString("TracerCategory"));
        draft.setProductActive(rs.getString("ProductActive"));
        
        // Map product related fields
        draft.setProductIdNoPack(rs.getString("ProductIDNoPack"));
        draft.setProductNameNoPack(rs.getString("ProductNameNoPack"));
        draft.setProductId(rs.getString("ProductID"));
        draft.setProductName(rs.getString("ProductName"));
        
        // Map unit and quantity fields
        draft.setOrderUom(rs.getString("OrderUOM"));
        draft.setPackSize(rs.getString("PackSize"));
        draft.setNoOfBaseUnits(rs.getString("NoofBaseUnits"));
        draft.setBaseUnit(rs.getString("BaseUnit"));
        
        // Map additional product classification fields
        draft.setL5DataTrusteeCode(rs.getString("L5DataTrusteeCode"));
        draft.setUnspsc(rs.getString("UNSPSC"));
        draft.setInn(rs.getString("INN"));
        draft.setControlled(rs.getString("Controlled"));
        
        // Map drug specific fields
        draft.setRoute(rs.getString("Route"));
        draft.setForm(rs.getString("Form"));
        draft.setQaCategory(rs.getString("QACategory"));
        draft.setQaCriteria(rs.getString("QACriteria"));
        
        // Map drug 1 details
        draft.setDrug1Name(rs.getString("Drug1Name"));
        draft.setDrug1Abbr(rs.getString("Drug1Abbr"));
        draft.setDrug1Qty(rs.getString("Drug1Qty"));
        draft.setDrug1Meas(rs.getString("Drug1Meas"));
        draft.setDrug1Unit(rs.getString("Drug1Unit"));
        
        // Map drug 2 details
        draft.setDrug2Name(rs.getString("Drug2Name"));
        draft.setDrug2Abbr(rs.getString("Drug2Abbr"));
        draft.setDrug2Qty(rs.getString("Drug2Qty"));
        draft.setDrug2Meas(rs.getString("Drug2Meas"));
        draft.setDrug2Unit(rs.getString("Drug2Unit"));
        
        // Map drug 3 details
        draft.setDrug3Name(rs.getString("Drug3Name"));
        draft.setDrug3Abbr(rs.getString("Drug3Abbr"));
        draft.setDrug3Qty(rs.getString("Drug3Qty"));
        draft.setDrug3Meas(rs.getString("Drug3Meas"));
        draft.setDrug3Unit(rs.getString("Drug3Unit"));
        
        // Map drug 4 details
        draft.setDrug4Name(rs.getString("Drug4Name"));
        draft.setDrug4Abbr(rs.getString("Drug4Abbr"));
        draft.setDrug4Qty(rs.getString("Drug4Qty"));
        draft.setDrug4Meas(rs.getString("Drug4Meas"));
        draft.setDrug4Unit(rs.getString("Drug4Unit"));
        
        // Map USAID specific field
        draft.setUsaidArvTier(rs.getString("USAIDARVTier"));
        
        // Map planning unit metrics
        draft.setPlanningUnitMoq(rs.getString("PlanningUnitMOQ"));
        draft.setPlanningUnitsPerPallet(rs.getString("PlanningUnitsperPallet"));
        draft.setPlanningUnitsPerContainer(rs.getString("PlanningUnitsperContainer"));
        draft.setPlanningUnitVolumeM3(rs.getString("PlanningUnitVolumem3"));
        draft.setPlanningUnitWeightKg(rs.getString("PlanningUnitWeightkg"));
        
        // Map item details
        draft.setItemId(rs.getString("ItemID"));
        draft.setItemName(rs.getString("ItemName"));
        draft.setSupplier(rs.getString("Supplier"));
        
        // Map physical specifications
        draft.setWeightUom(rs.getString("WeightUOM"));
        draft.setWeight(rs.getString("Weight"));
        draft.setHeightUom(rs.getString("HeightUOM"));
        draft.setHeight(rs.getString("Height"));
        draft.setLength(rs.getString("Length"));
        draft.setWidth(rs.getString("Width"));
        
        // Map additional item details
        draft.setGtin(rs.getString("GTIN"));
        draft.setLabeling(rs.getString("Labeling"));
        draft.setItemAvailable(rs.getString("ItemAvailable"));
        draft.setUnitsPerCase(rs.getString("UnitsperCase"));
        draft.setUnitsPerPallet(rs.getString("UnitsperPallet"));
        draft.setUnitsPerContainer(rs.getString("UnitsperContainer"));
        
        // Map pricing fields
        draft.setEstPrice(rs.getString("EstPrice"));
        draft.setEuro1(rs.getString("Euro1"));
        draft.setEuro2(rs.getString("Euro2"));
        
        // Map audit fields
        draft.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        
        return draft;
    }
} 