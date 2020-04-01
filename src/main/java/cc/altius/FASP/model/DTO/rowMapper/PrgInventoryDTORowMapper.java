/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgDataSourceDTO;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.PrgInventoryDTO;
import cc.altius.FASP.model.DTO.PrgLogisticsUnitDTO;
import cc.altius.FASP.model.DTO.PrgSupplierDTO;
import cc.altius.FASP.model.DTO.PrgPlanningUnitDTO;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgInventoryDTORowMapper implements RowMapper<PrgInventoryDTO> {

    @Override
    public PrgInventoryDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgInventoryDTO inventory = new PrgInventoryDTO();
        inventory.setInventoryId(rs.getInt("INVENTORY_ID"));
        inventory.setActualQty(rs.getDouble("ACTUAL_QTY"));
        inventory.setAdjustmentQty(rs.getDouble("ADJUSTMENT_QTY"));
        inventory.setBatchNo(rs.getString("BATCH_NO"));
        PrgDataSourceDTO dataSource = new PrgDataSourceDTO();
        dataSource.setDataSourceId(rs.getInt("DATA_SOURCE_ID"));
        PrgDataSourceTypeDTO dataSourceType = new PrgDataSourceTypeDTO();
        dataSourceType.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
        dataSourceType.setLabel(new PrgLabelDTORowMapper("DATA_SOURCE_TYPE_").mapRow(rs, i));
        dataSource.setDataSourceType(dataSourceType);
        dataSource.setLabel(new PrgLabelDTORowMapper("DATA_SOURCE_").mapRow(rs, i));
        inventory.setDataSource(dataSource);
        inventory.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        PrgLogisticsUnitDTO inventoryLogisticsUnit = new PrgLogisticsUnitDTO();
        inventoryLogisticsUnit.setHeightQty(rs.getDouble("HEIGHT_QTY"));
        PrgUnitDTO inventoryHeightUnit = new PrgUnitDTO();
        inventoryHeightUnit.setLabel(new PrgLabelDTORowMapper("HEIGHT_UNIT_").mapRow(rs, i));
        inventoryHeightUnit.setUnitCode(rs.getString("HEIGHT_UNIT_CODE"));
        inventoryHeightUnit.setUnitId(rs.getInt("HEIGHT_UNIT_ID"));
        PrgUnitTypeDTO inventoryHeightUnitType = new PrgUnitTypeDTO();
        inventoryHeightUnitType.setLabel(new PrgLabelDTORowMapper("HEIGHT_UNIT_TYPE_").mapRow(rs, i));
        inventoryHeightUnitType.setUnitTypeId(rs.getInt("HEIGHT_UNIT_TYPE_ID"));
        inventoryHeightUnit.setUnitType(inventoryHeightUnitType);
        inventoryLogisticsUnit.setHeightUnit(inventoryHeightUnit);
        inventoryLogisticsUnit.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_").mapRow(rs, i));
        inventoryLogisticsUnit.setLengthQty(rs.getInt("LENGTH_QTY"));

        PrgUnitDTO inventoryLengthUnit = new PrgUnitDTO();
        inventoryLengthUnit.setLabel(new PrgLabelDTORowMapper("LENGTH_UNIT_").mapRow(rs, i));
        inventoryLengthUnit.setUnitCode(rs.getString("LENGTH_UNIT_CODE"));
        inventoryLengthUnit.setUnitId(rs.getInt("LENGTH_UNIT_ID"));
        PrgUnitTypeDTO inventoryLengthUnitType = new PrgUnitTypeDTO();
        inventoryLengthUnitType.setLabel(new PrgLabelDTORowMapper("LENGTH_UNIT_TYPE_").mapRow(rs, i));
        inventoryLengthUnitType.setUnitTypeId(rs.getInt("LENGTH_UNIT_TYPE_ID"));
        inventoryLengthUnit.setUnitType(inventoryLengthUnitType);
        inventoryLogisticsUnit.setLengthUnit(inventoryLengthUnit);
        inventoryLogisticsUnit.setLogisticsUnitId(rs.getInt("LOGISTICS_UNIT_ID"));
        PrgSupplierDTO inventoryManufacturer = new PrgSupplierDTO();
        inventoryManufacturer.setLabel(new PrgLabelDTORowMapper("SUPPLIER_").mapRow(rs, i));
        inventoryManufacturer.setSupplierId(rs.getInt("SUPPLIER_ID"));
        inventoryLogisticsUnit.setManufacturer(inventoryManufacturer);

        PrgPlanningUnitDTO inventoryPlanningUnit = new PrgPlanningUnitDTO();
        inventoryPlanningUnit.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_").mapRow(rs, i));
        inventoryPlanningUnit.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        inventoryPlanningUnit.setPrice(rs.getDouble("PRICE"));
        inventoryPlanningUnit.setQtyOfForecastingUnits(rs.getDouble("QTY_OF_FORECASTING_UNITS"));
        PrgUnitDTO inventoryPlanningUnitUnit = new PrgUnitDTO();
        inventoryPlanningUnitUnit.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_UNIT_").mapRow(rs, i));
        inventoryPlanningUnitUnit.setUnitCode(rs.getString("PLANNING_UNIT_UNIT_CODE"));
        inventoryPlanningUnitUnit.setUnitId(rs.getInt("PLANNING_UNIT_UNIT_ID"));

        PrgUnitTypeDTO inventoryPlanningUnitUnitType = new PrgUnitTypeDTO();
        inventoryPlanningUnitUnitType.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_UNIT_TYPE_").mapRow(rs, i));
        inventoryPlanningUnitUnitType.setUnitTypeId(rs.getInt("PLANNING_UNIT_UNIT_TYPE_ID"));
        inventoryPlanningUnitUnit.setUnitType(inventoryPlanningUnitUnitType);
        inventoryPlanningUnit.setUnit(inventoryPlanningUnitUnit);
        inventoryLogisticsUnit.setPlanningUnit(inventoryPlanningUnit);
        inventoryLogisticsUnit.setQtyInEuro1(rs.getDouble("QTY_IN_EURO1"));
        inventoryLogisticsUnit.setQtyInEuro2(rs.getDouble("QTY_IN_EURO2"));
        inventoryLogisticsUnit.setQtyOfPlanningUnits(rs.getDouble("QTY_OF_PLANNING_UNITS"));
        PrgUnitDTO inventoryLogisticsUnitUnit = new PrgUnitDTO();
        inventoryLogisticsUnitUnit.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_UNIT_").mapRow(rs, i));
        inventoryLogisticsUnitUnit.setUnitCode(rs.getString("LOGISTICS_UNIT_UNIT_CODE"));
        inventoryLogisticsUnitUnit.setUnitId(rs.getInt("LOGISTICS_UNIT_UNIT_ID"));
        PrgUnitTypeDTO inventoryLogisticsUnitUnitType = new PrgUnitTypeDTO();
        inventoryLogisticsUnitUnitType.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_UNIT_TYPE_").mapRow(rs, i));
        inventoryLogisticsUnitUnitType.setUnitTypeId(rs.getInt("LOGISTICS_UNIT_UNIT_TYPE_ID"));
        inventoryLogisticsUnitUnit.setUnitType(inventoryLogisticsUnitUnitType);
        inventoryLogisticsUnit.setUnit(inventoryLogisticsUnitUnit);
        inventoryLogisticsUnit.setVariant(rs.getString("VARIANT"));

        inventoryLogisticsUnit.setWeightQty(rs.getDouble("WEIGHT_QTY"));
        PrgUnitDTO inventoryWeightUnit = new PrgUnitDTO();
        inventoryWeightUnit.setLabel(new PrgLabelDTORowMapper("WEIGHT_UNIT_").mapRow(rs, i));
        inventoryWeightUnit.setUnitCode(rs.getString("WEIGHT_UNIT_CODE"));
        inventoryWeightUnit.setUnitId(rs.getInt("WEIGHT_UNIT_ID"));
        PrgUnitTypeDTO inventoryWeightUnitType = new PrgUnitTypeDTO();
        inventoryWeightUnitType.setLabel(new PrgLabelDTORowMapper("WEIGHT_UNIT_TYPE_").mapRow(rs, i));
        inventoryWeightUnitType.setUnitTypeId(rs.getInt("WEIGHT_UNIT_TYPE_ID"));
        inventoryWeightUnit.setUnitType(inventoryWeightUnitType);
        inventoryLogisticsUnit.setWeightUnit(inventoryWeightUnit);
        inventoryLogisticsUnit.setWidthQty(rs.getDouble("WIDTH_QTY"));
        PrgUnitDTO inventoryWidthUnit = new PrgUnitDTO();
        inventoryWidthUnit.setLabel(new PrgLabelDTORowMapper("WIDTH_UNIT_").mapRow(rs, i));
        inventoryWidthUnit.setUnitCode(rs.getString("WIDTH_UNIT_CODE"));
        inventoryWidthUnit.setUnitId(rs.getInt("WIDTH_UNIT_ID"));
        PrgUnitTypeDTO inventoryWidthUnitType = new PrgUnitTypeDTO();
        inventoryWidthUnitType.setLabel(new PrgLabelDTORowMapper("WIDTH_UNIT_TYPE_").mapRow(rs, i));
        inventoryWidthUnitType.setUnitTypeId(rs.getInt("WIDTH_UNIT_TYPE_ID"));
        inventoryWidthUnit.setUnitType(inventoryWidthUnitType);
        inventoryLogisticsUnit.setWidthUnit(inventoryWidthUnit);
        inventory.setLogisticsUnit(inventoryLogisticsUnit);
        inventory.setPackSize(rs.getDouble("PACK_SIZE"));
        PrgRegionDTO inventoryRegion = new PrgRegionDTO();
        inventoryRegion.setCapacityCbm(rs.getDouble("REGION_CAPACITY_CBM"));
        inventoryRegion.setLabel(new PrgLabelDTORowMapper("REGION_").mapRow(rs, i));
        inventoryRegion.setRegionId(rs.getInt("REGION_ID"));
        inventory.setRegion(inventoryRegion);
        PrgUnitDTO inventoryUnit = new PrgUnitDTO();
        inventoryUnit.setLabel(new PrgLabelDTORowMapper("UNIT_").mapRow(rs, i));
        inventoryUnit.setUnitCode(rs.getString("UNIT_CODE"));
        inventoryUnit.setUnitId(rs.getInt("UNIT_ID"));
        PrgUnitTypeDTO inventoryUnitType = new PrgUnitTypeDTO();
        inventoryUnitType.setLabel(new PrgLabelDTORowMapper("UNIT_TYPE_").mapRow(rs, i));
        inventoryUnitType.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));
        inventoryUnit.setUnitType(inventoryUnitType);
        inventory.setUnit(inventoryUnit);
        return inventory;
    }

}
