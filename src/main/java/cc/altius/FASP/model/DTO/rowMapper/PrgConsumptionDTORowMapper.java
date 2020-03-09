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
        consumptionDataSourceType.setLabel(new PrgLabelDTORowMapper("DATA_SOURCE_TYPE_").mapRow(rs, i));
        consumptionDataSource.setDataSourceType(consumptionDataSourceType);
        consumptionDataSource.setLabel(new PrgLabelDTORowMapper("DATA_SOURCE_").mapRow(rs, i));
        consumption.setDataSource(consumptionDataSource);

        consumption.setDaysOfStockOut(rs.getInt("DAYS_OF_STOCK_OUT"));

        PrgLogisticsUnitDTO consumptionLogisticsUnit = new PrgLogisticsUnitDTO();
        consumptionLogisticsUnit.setHeightQty(rs.getDouble("HEIGHT_QTY"));
        PrgUnitDTO consumptionHeightUnit = new PrgUnitDTO();    
        consumptionHeightUnit.setLabel(new PrgLabelDTORowMapper("HEIGHT_UNIT_").mapRow(rs, i));
        consumptionHeightUnit.setUnitCode(rs.getString("HEIGHT_UNIT_CODE"));
        consumptionHeightUnit.setUnitId(rs.getInt("HEIGHT_UNIT_ID"));
        PrgUnitTypeDTO consumptionHeightUnitType = new PrgUnitTypeDTO();
        consumptionHeightUnitType.setLabel(new PrgLabelDTORowMapper("HEIGHT_UNIT_TYPE_").mapRow(rs, i));
        consumptionHeightUnitType.setUnitTypeId(rs.getInt("HEIGHT_UNIT_TYPE_ID"));
        consumptionHeightUnit.setUnitType(consumptionHeightUnitType);
        consumptionLogisticsUnit.setHeightUnit(consumptionHeightUnit);
        consumptionLogisticsUnit.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_").mapRow(rs, i));
        consumptionLogisticsUnit.setLengthQty(rs.getInt("LENGTH_QTY"));

        PrgUnitDTO consumptionLengthUnit = new PrgUnitDTO();
        consumptionLengthUnit.setLabel(new PrgLabelDTORowMapper("LENGTH_UNIT_").mapRow(rs, i));
        consumptionLengthUnit.setUnitCode(rs.getString("LENGTH_UNIT_CODE"));
        consumptionLengthUnit.setUnitId(rs.getInt("LENGTH_UNIT_ID"));
        PrgUnitTypeDTO consumptionLengthUnitType = new PrgUnitTypeDTO();
        consumptionLengthUnitType.setLabel(new PrgLabelDTORowMapper("LENGTH_UNIT_TYPE_").mapRow(rs, i));
        consumptionLengthUnitType.setUnitTypeId(rs.getInt("LENGTH_UNIT_TYPE_ID"));
        consumptionLengthUnit.setUnitType(consumptionLengthUnitType);
        consumptionLogisticsUnit.setLengthUnit(consumptionLengthUnit);
        consumptionLogisticsUnit.setLogisticsUnitId(rs.getInt("LOGISTICS_UNIT_ID"));
        PrgManufacturerDTO consumptionManufacturer = new PrgManufacturerDTO();
        consumptionManufacturer.setLabel(new PrgLabelDTORowMapper("MANUFACTURER_").mapRow(rs, i));
        consumptionManufacturer.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        consumptionLogisticsUnit.setManufacturer(consumptionManufacturer);
        
        PrgPlanningUnitDTO consumptionLUPlanningUnit = new PrgPlanningUnitDTO();
        consumptionLUPlanningUnit.setLabel(new PrgLabelDTORowMapper("LU_PLANNING_UNIT_").mapRow(rs, i));
        consumptionLUPlanningUnit.setPlanningUnitId(rs.getInt("LU_PLANNING_UNIT_ID"));
        consumptionLUPlanningUnit.setPrice(rs.getDouble("LU_PRICE"));
        consumptionLUPlanningUnit.setQtyOfForecastingUnits(rs.getDouble("LU_QTY_OF_FORECASTING_UNITS"));
        PrgUnitDTO consumptionLUPlanningUnitUnit = new PrgUnitDTO();
        consumptionLUPlanningUnitUnit.setLabel(new PrgLabelDTORowMapper("LU_PLANNING_UNIT_UNIT_").mapRow(rs, i));
        consumptionLUPlanningUnitUnit.setUnitCode(rs.getString("LU_PLANNING_UNIT_UNIT_CODE"));
        consumptionLUPlanningUnitUnit.setUnitId(rs.getInt("LU_PLANNING_UNIT_UNIT_ID"));

        PrgUnitTypeDTO consumptionLUPlanningUnitUnitType = new PrgUnitTypeDTO();
        consumptionLUPlanningUnitUnitType.setLabel(new PrgLabelDTORowMapper("LU_PLANNING_UNIT_UNIT_TYPE_").mapRow(rs, i));
        consumptionLUPlanningUnitUnitType.setUnitTypeId(rs.getInt("LU_PLANNING_UNIT_UNIT_TYPE_ID"));
        consumptionLUPlanningUnitUnit.setUnitType(consumptionLUPlanningUnitUnitType);
        consumptionLUPlanningUnit.setUnit(consumptionLUPlanningUnitUnit);
        consumptionLogisticsUnit.setPlanningUnit(consumptionLUPlanningUnit);
        
        consumptionLogisticsUnit.setQtyInEuro1(rs.getDouble("QTY_IN_EURO1"));
        consumptionLogisticsUnit.setQtyInEuro2(rs.getDouble("QTY_IN_EURO2"));
        consumptionLogisticsUnit.setQtyOfPlanningUnits(rs.getDouble("QTY_OF_PLANNING_UNITS"));
        PrgUnitDTO consumptionLogisticsUnitUnit = new PrgUnitDTO();
        consumptionLogisticsUnitUnit.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_UNIT_").mapRow(rs, i));
        consumptionLogisticsUnitUnit.setUnitCode(rs.getString("LOGISTICS_UNIT_UNIT_CODE"));
        consumptionLogisticsUnitUnit.setUnitId(rs.getInt("LOGISTICS_UNIT_UNIT_ID"));
        PrgUnitTypeDTO consumptionLogisticsUnitUnitType = new PrgUnitTypeDTO();
        consumptionLogisticsUnitUnitType.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_UNIT_TYPE_").mapRow(rs, i));
        consumptionLogisticsUnitUnitType.setUnitTypeId(rs.getInt("LOGISTICS_UNIT_UNIT_TYPE_ID"));
        consumptionLogisticsUnitUnit.setUnitType(consumptionLogisticsUnitUnitType);
        consumptionLogisticsUnit.setUnit(consumptionLogisticsUnitUnit);
        consumptionLogisticsUnit.setVariant(rs.getString("VARIANT"));

        consumptionLogisticsUnit.setWeightQty(rs.getDouble("WEIGHT_QTY"));
        PrgUnitDTO consumptionWeightUnit = new PrgUnitDTO();
        consumptionWeightUnit.setLabel(new PrgLabelDTORowMapper("WEIGHT_UNIT_").mapRow(rs, i));
        consumptionWeightUnit.setUnitCode(rs.getString("WEIGHT_UNIT_CODE"));
        consumptionWeightUnit.setUnitId(rs.getInt("WEIGHT_UNIT_ID"));
        PrgUnitTypeDTO consumptionWeightUnitType = new PrgUnitTypeDTO();
        consumptionWeightUnitType.setLabel(new PrgLabelDTORowMapper("WEIGHT_UNIT_TYPE_").mapRow(rs, i));
        consumptionWeightUnitType.setUnitTypeId(rs.getInt("WEIGHT_UNIT_TYPE_ID"));
        consumptionWeightUnit.setUnitType(consumptionWeightUnitType);
        consumptionLogisticsUnit.setWeightUnit(consumptionWeightUnit);
        consumptionLogisticsUnit.setWidthQty(rs.getDouble("WIDTH_QTY"));
        PrgUnitDTO consumptionWidthUnit = new PrgUnitDTO();
        consumptionWidthUnit.setLabel(new PrgLabelDTORowMapper("WIDTH_UNIT_").mapRow(rs, i));
        consumptionWidthUnit.setUnitCode(rs.getString("WIDTH_UNIT_CODE"));
        consumptionWidthUnit.setUnitId(rs.getInt("WIDTH_UNIT_ID"));
        PrgUnitTypeDTO consumptionWidthUnitType = new PrgUnitTypeDTO();
        consumptionWidthUnitType.setLabel(new PrgLabelDTORowMapper("WIDTH_UNIT_TYPE_").mapRow(rs, i));
        consumptionWidthUnitType.setUnitTypeId(rs.getInt("WIDTH_UNIT_TYPE_ID"));
        consumptionWidthUnit.setUnitType(consumptionWidthUnitType);
        consumptionLogisticsUnit.setWidthUnit(consumptionWidthUnit);
        consumption.setLogisticsUnit(consumptionLogisticsUnit);
        consumption.setPackSize(rs.getDouble("PACK_SIZE"));
        PrgRegionDTO consumptionRegion = new PrgRegionDTO();
        consumptionRegion.setCapacityCbm(rs.getDouble("REGION_CAPACITY_CBM"));
        consumptionRegion.setLabel(new PrgLabelDTORowMapper("REGION_").mapRow(rs, i));
        consumptionRegion.setRegionId(rs.getInt("REGION_ID"));
        consumption.setRegion(consumptionRegion);
        PrgUnitDTO consumptionUnit = new PrgUnitDTO();
        consumptionUnit.setLabel(new PrgLabelDTORowMapper("UNIT_").mapRow(rs, i));
        consumptionUnit.setUnitCode(rs.getString("UNIT_CODE"));
        consumptionUnit.setUnitId(rs.getInt("UNIT_ID"));
        PrgUnitTypeDTO consumptionUnitType = new PrgUnitTypeDTO();
        consumptionUnitType.setLabel(new PrgLabelDTORowMapper("UNIT_TYPE_").mapRow(rs, i));
        consumptionUnitType.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));
        consumptionUnit.setUnitType(consumptionUnitType);
        consumption.setUnit(consumptionUnit);
        consumption.setStartDate(rs.getDate("START_DATE"));
        consumption.setStopDate(rs.getDate("STOP_DATE"));
        
        
        PrgPlanningUnitDTO consumptionPlanningUnit = new PrgPlanningUnitDTO();
        consumptionPlanningUnit.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_").mapRow(rs, i));
        consumptionPlanningUnit.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        consumptionPlanningUnit.setPrice(rs.getDouble("PRICE"));
        consumptionPlanningUnit.setQtyOfForecastingUnits(rs.getDouble("QTY_OF_FORECASTING_UNITS"));
        PrgUnitDTO consumptionPlanningUnitUnit = new PrgUnitDTO();
        consumptionPlanningUnitUnit.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_UNIT_").mapRow(rs, i));
        consumptionPlanningUnitUnit.setUnitCode(rs.getString("PLANNING_UNIT_UNIT_CODE"));
        consumptionPlanningUnitUnit.setUnitId(rs.getInt("PLANNING_UNIT_UNIT_ID"));

        PrgUnitTypeDTO consumptionPlanningUnitUnitType = new PrgUnitTypeDTO();
        consumptionPlanningUnitUnitType.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_UNIT_TYPE_").mapRow(rs, i));
        consumptionPlanningUnitUnitType.setUnitTypeId(rs.getInt("PLANNING_UNIT_UNIT_TYPE_ID"));
        consumptionPlanningUnitUnit.setUnitType(consumptionPlanningUnitUnitType);
        consumptionPlanningUnit.setUnit(consumptionPlanningUnitUnit);
        consumption.setPlanningUnit(consumptionPlanningUnit);
        return consumption;
    }

}
