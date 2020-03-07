/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgConsumptionDTO;
import cc.altius.FASP.model.DTO.PrgDataSourceDTO;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgLogisticsUnitDTO;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
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
public class PrgConsumptionDTORowMapper implements RowMapper<PrgConsumptionDTO> {

    @Override
    public PrgConsumptionDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgConsumptionDTO consumption = new PrgConsumptionDTO();
        consumption.setConsumptionId(rs.getInt("CONSUMPTION_ID"));
        consumption.setConsumptionQty(rs.getDouble("CONSUMPTION_QTY"));
        PrgDataSourceDTO consumptionDataSource = new PrgDataSourceDTO();
        consumptionDataSource.setDataSourceId(rs.getInt("DATA_SOURCE_ID"));
        PrgDataSourceTypeDTO consumptionDataSourceType = new PrgDataSourceTypeDTO();
        consumptionDataSourceType.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
        PrgLabelDTO consumptionDataSourceTypeLabel = new PrgLabelDTO();
        consumptionDataSourceTypeLabel.setLabelEn(rs.getString("DATA_SOURCE_TYPE_NAME_EN"));
        consumptionDataSourceTypeLabel.setLabelFr(rs.getString("DATA_SOURCE_TYPE_NAME_FR"));
        consumptionDataSourceTypeLabel.setLabelPr(rs.getString("DATA_SOURCE_TYPE_NAME_PR"));
        consumptionDataSourceTypeLabel.setLabelSp(rs.getString("DATA_SOURCE_TYPE_NAME_SP"));
        consumptionDataSourceType.setLabel(consumptionDataSourceTypeLabel);
        consumptionDataSource.setDataSourceType(consumptionDataSourceType);
        PrgLabelDTO consumptionDataSourceLabel = new PrgLabelDTO();
        consumptionDataSourceLabel.setLabelEn(rs.getString("DATA_SOURCE_NAME_EN"));
        consumptionDataSourceLabel.setLabelFr(rs.getString("DATA_SOURCE_NAME_FR"));
        consumptionDataSourceLabel.setLabelPr(rs.getString("DATA_SOURCE_NAME_PR"));
        consumptionDataSourceLabel.setLabelSp(rs.getString("DATA_SOURCE_NAME_SP"));
        consumptionDataSource.setLabel(consumptionDataSourceLabel);
        consumption.setDataSource(consumptionDataSource);

        consumption.setDaysOfStockOut(rs.getInt("DAYS_OF_STOCK_OUT"));

        PrgLogisticsUnitDTO consumptionLogisticsUnit = new PrgLogisticsUnitDTO();
        consumptionLogisticsUnit.setHeightQty(rs.getDouble("HEIGHT_QTY"));
        PrgUnitDTO consumptionHeightUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionHeightUnitLabel = new PrgLabelDTO();
        consumptionHeightUnitLabel.setLabelEn(rs.getString("HEIGHT_UNIT_NAME_EN"));
        consumptionHeightUnitLabel.setLabelFr(rs.getString("HEIGHT_UNIT_NAME_FR"));
        consumptionHeightUnitLabel.setLabelPr(rs.getString("HEIGHT_UNIT_NAME_PR"));
        consumptionHeightUnitLabel.setLabelSp(rs.getString("HEIGHT_UNIT_NAME_SP"));
        consumptionHeightUnit.setLabel(consumptionHeightUnitLabel);
        consumptionHeightUnit.setUnitCode(rs.getString("HEIGHT_UNIT_CODE"));
        consumptionHeightUnit.setUnitId(rs.getInt("HEIGHT_UNIT_ID"));
        PrgUnitTypeDTO consumptionHeightUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionHeightUnitTypeLabel = new PrgLabelDTO();
        consumptionHeightUnitTypeLabel.setLabelEn(rs.getString("HEIGHT_UNIT_TYPE_NAME_EN"));
        consumptionHeightUnitTypeLabel.setLabelFr(rs.getString("HEIGHT_UNIT_TYPE_NAME_FR"));
        consumptionHeightUnitTypeLabel.setLabelPr(rs.getString("HEIGHT_UNIT_TYPE_NAME_PR"));
        consumptionHeightUnitTypeLabel.setLabelSp(rs.getString("HEIGHT_UNIT_TYPE_NAME_SP"));
        consumptionHeightUnitType.setLabel(consumptionHeightUnitTypeLabel);
        consumptionHeightUnitType.setUnitTypeId(rs.getInt("HEIGHT_UNIT_TYPE_ID"));
        consumptionHeightUnit.setUnitType(consumptionHeightUnitType);
        consumptionLogisticsUnit.setHeightUnit(consumptionHeightUnit);
        PrgLabelDTO consumptionLogisticsUnitLabel = new PrgLabelDTO();
        consumptionLogisticsUnitLabel.setLabelEn(rs.getString("LOGISTICS_UNIT_NAME_EN"));
        consumptionLogisticsUnitLabel.setLabelFr(rs.getString("LOGISTICS_UNIT_NAME_FR"));
        consumptionLogisticsUnitLabel.setLabelPr(rs.getString("LOGISTICS_UNIT_NAME_PR"));
        consumptionLogisticsUnitLabel.setLabelSp(rs.getString("LOGISTICS_UNIT_NAME_SP"));
        consumptionLogisticsUnit.setLabel(consumptionLogisticsUnitLabel);
        consumptionLogisticsUnit.setLengthQty(rs.getInt("LENGTH_QTY"));

        PrgUnitDTO consumptionLengthUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionLengthUnitLabel = new PrgLabelDTO();
        consumptionLengthUnitLabel.setLabelEn(rs.getString("LENGTH_UNIT_NAME_EN"));
        consumptionLengthUnitLabel.setLabelFr(rs.getString("LENGTH_UNIT_NAME_FR"));
        consumptionLengthUnitLabel.setLabelPr(rs.getString("LENGTH_UNIT_NAME_PR"));
        consumptionLengthUnitLabel.setLabelSp(rs.getString("LENGTH_UNIT_NAME_SP"));
        consumptionLengthUnit.setLabel(consumptionLengthUnitLabel);
        consumptionLengthUnit.setUnitCode(rs.getString("LENGTH_UNIT_CODE"));
        consumptionLengthUnit.setUnitId(rs.getInt("LENGTH_UNIT_ID"));
        PrgUnitTypeDTO consumptionLengthUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionLengthUnitTypeLabel = new PrgLabelDTO();
        consumptionLengthUnitTypeLabel.setLabelEn(rs.getString("LENGTH_UNIT_TYPE_NAME_EN"));
        consumptionLengthUnitTypeLabel.setLabelFr(rs.getString("LENGTH_UNIT_TYPE_NAME_FR"));
        consumptionLengthUnitTypeLabel.setLabelPr(rs.getString("LENGTH_UNIT_TYPE_NAME_PR"));
        consumptionLengthUnitTypeLabel.setLabelSp(rs.getString("LENGTH_UNIT_TYPE_NAME_SP"));
        consumptionLengthUnitType.setLabel(consumptionLengthUnitTypeLabel);
        consumptionLengthUnitType.setUnitTypeId(rs.getInt("LENGTH_UNIT_TYPE_ID"));
        consumptionLengthUnit.setUnitType(consumptionLengthUnitType);
        consumptionLogisticsUnit.setLengthUnit(consumptionLengthUnit);
        consumptionLogisticsUnit.setLogisticsUnitId(rs.getInt("LOGISTICS_UNIT_ID"));
        PrgManufacturerDTO consumptionManufacturer = new PrgManufacturerDTO();
        PrgLabelDTO consumptionManufacturerLabel = new PrgLabelDTO();
        consumptionManufacturerLabel.setLabelEn(rs.getString("MANUFACTURER_NAME_EN"));
        consumptionManufacturerLabel.setLabelFr(rs.getString("MANUFACTURER_NAME_FR"));
        consumptionManufacturerLabel.setLabelPr(rs.getString("MANUFACTURER_NAME_PR"));
        consumptionManufacturerLabel.setLabelSp(rs.getString("MANUFACTURER_NAME_SP"));
        consumptionManufacturer.setLabel(consumptionManufacturerLabel);
        consumptionManufacturer.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        consumptionLogisticsUnit.setManufacturer(consumptionManufacturer);
        
        PrgPlanningUnitDTO consumptionLUPlanningUnit = new PrgPlanningUnitDTO();
        PrgLabelDTO consumptionLUPlanningUnitLabel = new PrgLabelDTO();
        consumptionLUPlanningUnitLabel.setLabelEn(rs.getString("LU_PLANNING_UNIT_NAME_EN"));
        consumptionLUPlanningUnitLabel.setLabelFr(rs.getString("LU_PLANNING_UNIT_NAME_FR"));
        consumptionLUPlanningUnitLabel.setLabelPr(rs.getString("LU_PLANNING_UNIT_NAME_PR"));
        consumptionLUPlanningUnitLabel.setLabelSp(rs.getString("LU_PLANNING_UNIT_NAME_SP"));
        consumptionLUPlanningUnit.setLabel(consumptionLUPlanningUnitLabel);
        consumptionLUPlanningUnit.setPlanningUnitId(rs.getInt("LU_PLANNING_UNIT_ID"));
        consumptionLUPlanningUnit.setPrice(rs.getDouble("LU_PRICE"));
        consumptionLUPlanningUnit.setQtyOfForecastingUnits(rs.getDouble("LU_QTY_OF_FORECASTING_UNITS"));
        PrgUnitDTO consumptionLUPlanningUnitUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionLUPlanningUnitUnitLabel = new PrgLabelDTO();
        consumptionLUPlanningUnitUnitLabel.setLabelEn(rs.getString("LU_PLANNING_UNIT_UNIT_NAME_EN"));
        consumptionLUPlanningUnitUnitLabel.setLabelFr(rs.getString("LU_PLANNING_UNIT_UNIT_NAME_FR"));
        consumptionLUPlanningUnitUnitLabel.setLabelPr(rs.getString("LU_PLANNING_UNIT_UNIT_NAME_PR"));
        consumptionLUPlanningUnitUnitLabel.setLabelSp(rs.getString("LU_PLANNING_UNIT_UNIT_NAME_SP"));
        consumptionLUPlanningUnitUnit.setLabel(consumptionLUPlanningUnitUnitLabel);
        consumptionLUPlanningUnitUnit.setUnitCode(rs.getString("LU_PLANNING_UNIT_UNIT_CODE"));
        consumptionLUPlanningUnitUnit.setUnitId(rs.getInt("LU_PLANNING_UNIT_UNIT_ID"));

        PrgUnitTypeDTO consumptionLUPlanningUnitUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionLUPlanningUnitUnitTypeLabel = new PrgLabelDTO();
        consumptionLUPlanningUnitUnitTypeLabel.setLabelEn(rs.getString("LU_PLANNING_UNIT_UNIT_TYPE_NAME_EN"));
        consumptionLUPlanningUnitUnitTypeLabel.setLabelFr(rs.getString("LU_PLANNING_UNIT_UNIT_TYPE_NAME_FR"));
        consumptionLUPlanningUnitUnitTypeLabel.setLabelPr(rs.getString("LU_PLANNING_UNIT_UNIT_TYPE_NAME_PR"));
        consumptionLUPlanningUnitUnitTypeLabel.setLabelSp(rs.getString("LU_PLANNING_UNIT_UNIT_TYPE_NAME_SP"));
        consumptionLUPlanningUnitUnitType.setLabel(consumptionLUPlanningUnitUnitTypeLabel);
        consumptionLUPlanningUnitUnitType.setUnitTypeId(rs.getInt("LU_PLANNING_UNIT_UNIT_TYPE_ID"));
        consumptionLUPlanningUnitUnit.setUnitType(consumptionLUPlanningUnitUnitType);
        consumptionLUPlanningUnit.setUnit(consumptionLUPlanningUnitUnit);
        consumptionLogisticsUnit.setPlanningUnit(consumptionLUPlanningUnit);
        
        consumptionLogisticsUnit.setQtyInEuro1(rs.getDouble("QTY_IN_EURO1"));
        consumptionLogisticsUnit.setQtyInEuro2(rs.getDouble("QTY_IN_EURO2"));
        consumptionLogisticsUnit.setQtyOfPlanningUnits(rs.getDouble("QTY_OF_PLANNING_UNITS"));
        PrgUnitDTO consumptionLogisticsUnitUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionLogisticsUnitUnitLabel = new PrgLabelDTO();
        consumptionLogisticsUnitUnitLabel.setLabelEn(rs.getString("LOGISTICS_UNIT_UNIT_NAME_EN"));
        consumptionLogisticsUnitUnitLabel.setLabelFr(rs.getString("LOGISTICS_UNIT_UNIT_NAME_FR"));
        consumptionLogisticsUnitUnitLabel.setLabelPr(rs.getString("LOGISTICS_UNIT_UNIT_NAME_PR"));
        consumptionLogisticsUnitUnitLabel.setLabelSp(rs.getString("LOGISTICS_UNIT_UNIT_NAME_SP"));
        consumptionLogisticsUnitUnit.setLabel(consumptionLogisticsUnitUnitLabel);
        consumptionLogisticsUnitUnit.setUnitCode(rs.getString("LOGISTICS_UNIT_UNIT_CODE"));
        consumptionLogisticsUnitUnit.setUnitId(rs.getInt("LOGISTICS_UNIT_UNIT_ID"));
        PrgUnitTypeDTO consumptionLogisticsUnitUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionLogisticsUnitUnitTypeLabel = new PrgLabelDTO();
        consumptionLogisticsUnitUnitTypeLabel.setLabelEn(rs.getString("LOGISTICS_UNIT_UNIT_TYPE_NAME_EN"));
        consumptionLogisticsUnitUnitTypeLabel.setLabelFr(rs.getString("LOGISTICS_UNIT_UNIT_TYPE_NAME_FR"));
        consumptionLogisticsUnitUnitTypeLabel.setLabelPr(rs.getString("LOGISTICS_UNIT_UNIT_TYPE_NAME_PR"));
        consumptionLogisticsUnitUnitTypeLabel.setLabelSp(rs.getString("LOGISTICS_UNIT_UNIT_TYPE_NAME_SP"));
        consumptionLogisticsUnitUnitType.setLabel(consumptionLogisticsUnitUnitTypeLabel);
        consumptionLogisticsUnitUnitType.setUnitTypeId(rs.getInt("LOGISTICS_UNIT_UNIT_TYPE_ID"));
        consumptionLogisticsUnitUnit.setUnitType(consumptionLogisticsUnitUnitType);
        consumptionLogisticsUnit.setUnit(consumptionLogisticsUnitUnit);
        consumptionLogisticsUnit.setVariant(rs.getString("VARIANT"));

        consumptionLogisticsUnit.setWeightQty(rs.getDouble("WEIGHT_QTY"));
        PrgUnitDTO consumptionWeightUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionWeightUnitLabel = new PrgLabelDTO();
        consumptionWeightUnitLabel.setLabelEn(rs.getString("WEIGHT_UNIT_NAME_EN"));
        consumptionWeightUnitLabel.setLabelFr(rs.getString("WEIGHT_UNIT_NAME_FR"));
        consumptionWeightUnitLabel.setLabelPr(rs.getString("WEIGHT_UNIT_NAME_PR"));
        consumptionWeightUnitLabel.setLabelSp(rs.getString("WEIGHT_UNIT_NAME_SP"));
        consumptionWeightUnit.setLabel(consumptionWeightUnitLabel);
        consumptionWeightUnit.setUnitCode(rs.getString("WEIGHT_UNIT_CODE"));
        consumptionWeightUnit.setUnitId(rs.getInt("WEIGHT_UNIT_ID"));
        PrgUnitTypeDTO consumptionWeightUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionWeightUnitTypeLabel = new PrgLabelDTO();
        consumptionWeightUnitTypeLabel.setLabelEn(rs.getString("WEIGHT_UNIT_TYPE_NAME_EN"));
        consumptionWeightUnitTypeLabel.setLabelFr(rs.getString("WEIGHT_UNIT_TYPE_NAME_FR"));
        consumptionWeightUnitTypeLabel.setLabelPr(rs.getString("WEIGHT_UNIT_TYPE_NAME_PR"));
        consumptionWeightUnitTypeLabel.setLabelSp(rs.getString("WEIGHT_UNIT_TYPE_NAME_SP"));
        consumptionWeightUnitType.setLabel(consumptionWeightUnitTypeLabel);
        consumptionWeightUnitType.setUnitTypeId(rs.getInt("WEIGHT_UNIT_TYPE_ID"));
        consumptionWeightUnit.setUnitType(consumptionWeightUnitType);
        consumptionLogisticsUnit.setWeightUnit(consumptionWeightUnit);
        consumptionLogisticsUnit.setWidthQty(rs.getDouble("WIDTH_QTY"));
        PrgUnitDTO consumptionWidthUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionWidthUnitLabel = new PrgLabelDTO();
        consumptionWidthUnitLabel.setLabelEn(rs.getString("WIDTH_UNIT_NAME_EN"));
        consumptionWidthUnitLabel.setLabelFr(rs.getString("WIDTH_UNIT_NAME_FR"));
        consumptionWidthUnitLabel.setLabelPr(rs.getString("WIDTH_UNIT_NAME_PR"));
        consumptionWidthUnitLabel.setLabelSp(rs.getString("WIDTH_UNIT_NAME_SP"));
        consumptionWidthUnit.setLabel(consumptionWidthUnitLabel);
        consumptionWidthUnit.setUnitCode(rs.getString("WIDTH_UNIT_CODE"));
        consumptionWidthUnit.setUnitId(rs.getInt("WIDTH_UNIT_ID"));
        PrgUnitTypeDTO consumptionWidthUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionWidthUnitTypeLabel = new PrgLabelDTO();
        consumptionWidthUnitTypeLabel.setLabelEn(rs.getString("WIDTH_UNIT_TYPE_NAME_EN"));
        consumptionWidthUnitTypeLabel.setLabelFr(rs.getString("WIDTH_UNIT_TYPE_NAME_FR"));
        consumptionWidthUnitTypeLabel.setLabelPr(rs.getString("WIDTH_UNIT_TYPE_NAME_PR"));
        consumptionWidthUnitTypeLabel.setLabelSp(rs.getString("WIDTH_UNIT_TYPE_NAME_SP"));
        consumptionWidthUnitType.setLabel(consumptionWidthUnitTypeLabel);
        consumptionWidthUnitType.setUnitTypeId(rs.getInt("WIDTH_UNIT_TYPE_ID"));
        consumptionWidthUnit.setUnitType(consumptionWidthUnitType);
        consumptionLogisticsUnit.setWidthUnit(consumptionWidthUnit);
        consumption.setLogisticsUnit(consumptionLogisticsUnit);
        consumption.setPackSize(rs.getDouble("PACK_SIZE"));
        PrgRegionDTO consumptionRegion = new PrgRegionDTO();
        consumptionRegion.setCapacityCbm(rs.getDouble("REGION_CAPACITY_CBM"));
        PrgLabelDTO consumptionRegionLabel = new PrgLabelDTO();
        consumptionRegionLabel.setLabelEn(rs.getString("REGION_NAME_EN"));
        consumptionRegionLabel.setLabelFr(rs.getString("REGION_NAME_FR"));
        consumptionRegionLabel.setLabelPr(rs.getString("REGION_NAME_PR"));
        consumptionRegionLabel.setLabelSp(rs.getString("REGION_NAME_SP"));
        consumptionRegion.setLabel(consumptionRegionLabel);
        consumptionRegion.setRegionId(rs.getInt("REGION_ID"));
        consumption.setRegion(consumptionRegion);
        PrgUnitDTO consumptionUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionUnitLabel = new PrgLabelDTO();
        consumptionUnitLabel.setLabelEn(rs.getString("UNIT_NAME_EN"));
        consumptionUnitLabel.setLabelFr(rs.getString("UNIT_NAME_FR"));
        consumptionUnitLabel.setLabelPr(rs.getString("UNIT_NAME_PR"));
        consumptionUnitLabel.setLabelSp(rs.getString("UNIT_NAME_SP"));
        consumptionUnit.setLabel(consumptionUnitLabel);
        consumptionUnit.setUnitCode(rs.getString("UNIT_CODE"));
        consumptionUnit.setUnitId(rs.getInt("UNIT_ID"));
        PrgUnitTypeDTO consumptionUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionUnitTypeLabel = new PrgLabelDTO();
        consumptionUnitTypeLabel.setLabelEn(rs.getString("UNIT_TYPE_NAME_EN"));
        consumptionUnitTypeLabel.setLabelFr(rs.getString("UNIT_TYPE_NAME_FR"));
        consumptionUnitTypeLabel.setLabelPr(rs.getString("UNIT_TYPE_NAME_PR"));
        consumptionUnitTypeLabel.setLabelSp(rs.getString("UNIT_TYPE_NAME_SP"));
        consumptionUnitType.setLabel(consumptionUnitTypeLabel);
        consumptionUnitType.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));
        consumptionUnit.setUnitType(consumptionUnitType);
        consumption.setUnit(consumptionUnit);
        consumption.setStartDate(rs.getDate("START_DATE"));
        consumption.setStopDate(rs.getDate("STOP_DATE"));
        
        
        PrgPlanningUnitDTO consumptionPlanningUnit = new PrgPlanningUnitDTO();
        PrgLabelDTO consumptionPlanningUnitLabel = new PrgLabelDTO();
        consumptionPlanningUnitLabel.setLabelEn(rs.getString("PLANNING_UNIT_NAME_EN"));
        consumptionPlanningUnitLabel.setLabelFr(rs.getString("PLANNING_UNIT_NAME_FR"));
        consumptionPlanningUnitLabel.setLabelPr(rs.getString("PLANNING_UNIT_NAME_PR"));
        consumptionPlanningUnitLabel.setLabelSp(rs.getString("PLANNING_UNIT_NAME_SP"));
        consumptionPlanningUnit.setLabel(consumptionPlanningUnitLabel);
        consumptionPlanningUnit.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        consumptionPlanningUnit.setPrice(rs.getDouble("PRICE"));
        consumptionPlanningUnit.setQtyOfForecastingUnits(rs.getDouble("QTY_OF_FORECASTING_UNITS"));
        PrgUnitDTO consumptionPlanningUnitUnit = new PrgUnitDTO();
        PrgLabelDTO consumptionPlanningUnitUnitLabel = new PrgLabelDTO();
        consumptionPlanningUnitUnitLabel.setLabelEn(rs.getString("PLANNING_UNIT_UNIT_NAME_EN"));
        consumptionPlanningUnitUnitLabel.setLabelFr(rs.getString("PLANNING_UNIT_UNIT_NAME_FR"));
        consumptionPlanningUnitUnitLabel.setLabelPr(rs.getString("PLANNING_UNIT_UNIT_NAME_PR"));
        consumptionPlanningUnitUnitLabel.setLabelSp(rs.getString("PLANNING_UNIT_UNIT_NAME_SP"));
        consumptionPlanningUnitUnit.setLabel(consumptionPlanningUnitUnitLabel);
        consumptionPlanningUnitUnit.setUnitCode(rs.getString("PLANNING_UNIT_UNIT_CODE"));
        consumptionPlanningUnitUnit.setUnitId(rs.getInt("PLANNING_UNIT_UNIT_ID"));

        PrgUnitTypeDTO consumptionPlanningUnitUnitType = new PrgUnitTypeDTO();
        PrgLabelDTO consumptionPlanningUnitUnitTypeLabel = new PrgLabelDTO();
        consumptionPlanningUnitUnitTypeLabel.setLabelEn(rs.getString("PLANNING_UNIT_UNIT_TYPE_NAME_EN"));
        consumptionPlanningUnitUnitTypeLabel.setLabelFr(rs.getString("PLANNING_UNIT_UNIT_TYPE_NAME_FR"));
        consumptionPlanningUnitUnitTypeLabel.setLabelPr(rs.getString("PLANNING_UNIT_UNIT_TYPE_NAME_PR"));
        consumptionPlanningUnitUnitTypeLabel.setLabelSp(rs.getString("PLANNING_UNIT_UNIT_TYPE_NAME_SP"));
        consumptionPlanningUnitUnitType.setLabel(consumptionPlanningUnitUnitTypeLabel);
        consumptionPlanningUnitUnitType.setUnitTypeId(rs.getInt("PLANNING_UNIT_UNIT_TYPE_ID"));
        consumptionPlanningUnitUnit.setUnitType(consumptionPlanningUnitUnitType);
        consumptionPlanningUnit.setUnit(consumptionPlanningUnitUnit);
        consumption.setPlanningUnit(consumptionPlanningUnit);
        return consumption;
    }

}
