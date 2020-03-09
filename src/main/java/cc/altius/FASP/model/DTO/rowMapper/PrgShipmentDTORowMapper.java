/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgBudgetDTO;
import cc.altius.FASP.model.DTO.PrgDataSourceDTO;
import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgLogisticsUnitDTO;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
import cc.altius.FASP.model.DTO.PrgPlanningUnitDTO;
import cc.altius.FASP.model.DTO.PrgProcurementAgentDTO;
import cc.altius.FASP.model.DTO.PrgProcurementAgentLogisiticsUnitDTO;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.DTO.PrgShipmentBudgetDTO;
import cc.altius.FASP.model.DTO.PrgShipmentDTO;
import cc.altius.FASP.model.DTO.PrgShipmentStatusDTO;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgShipmentDTORowMapper implements RowMapper<PrgShipmentDTO> {

    @Override
    public PrgShipmentDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgShipmentDTO shipment = new PrgShipmentDTO();
        shipment.setShipmentId(rs.getInt("SHIPMENT_ID"));
        shipment.setArriveDate(rs.getDate("ARRIVE_DATE"));
        PrgDataSourceDTO shipmentDataSource = new PrgDataSourceDTO();
        shipmentDataSource.setDataSourceId(rs.getInt("DATA_SOURCE_ID"));
        PrgDataSourceTypeDTO shipmentDataSourceType = new PrgDataSourceTypeDTO();
        shipmentDataSourceType.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
        shipmentDataSourceType.setLabel(new PrgLabelDTORowMapper("DATA_SOURCE_TYPE_").mapRow(rs, i));
        shipmentDataSource.setDataSourceType(shipmentDataSourceType);
        shipmentDataSource.setLabel(new PrgLabelDTORowMapper("DATA_SOURCE_").mapRow(rs, i));
        shipment.setDataSource(shipmentDataSource);
        shipment.setFrieghtPrice(rs.getDouble("FREIGHT_PRICE"));
        PrgLogisticsUnitDTO shipmentLogisticsUnit = new PrgLogisticsUnitDTO();
        shipmentLogisticsUnit.setHeightQty(rs.getDouble("HEIGHT_QTY"));
        PrgUnitDTO shipmentHeightUnit = new PrgUnitDTO();
        shipmentHeightUnit.setLabel(new PrgLabelDTORowMapper("HEIGHT_UNIT_").mapRow(rs, i));
        shipmentHeightUnit.setUnitCode(rs.getString("HEIGHT_UNIT_CODE"));
        shipmentHeightUnit.setUnitId(rs.getInt("HEIGHT_UNIT_ID"));
        PrgUnitTypeDTO shipmentHeightUnitType = new PrgUnitTypeDTO();
        shipmentHeightUnitType.setLabel(new PrgLabelDTORowMapper("HEIGHT_UNIT_TYPE_").mapRow(rs, i));
        shipmentHeightUnitType.setUnitTypeId(rs.getInt("HEIGHT_UNIT_TYPE_ID"));
        shipmentHeightUnit.setUnitType(shipmentHeightUnitType);
        shipmentLogisticsUnit.setHeightUnit(shipmentHeightUnit);
        shipmentLogisticsUnit.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_").mapRow(rs, i));
        shipmentLogisticsUnit.setLengthQty(rs.getInt("LENGTH_QTY"));

        PrgUnitDTO shipmentLengthUnit = new PrgUnitDTO();
        shipmentLengthUnit.setLabel(new PrgLabelDTORowMapper("LENGTH_UNIT_").mapRow(rs, i));
        shipmentLengthUnit.setUnitCode(rs.getString("LENGTH_UNIT_CODE"));
        shipmentLengthUnit.setUnitId(rs.getInt("LENGTH_UNIT_ID"));
        PrgUnitTypeDTO shipmentLengthUnitType = new PrgUnitTypeDTO();
        shipmentLengthUnitType.setLabel(new PrgLabelDTORowMapper("LENGTH_UNIT_TYPE_").mapRow(rs, i));
        shipmentLengthUnitType.setUnitTypeId(rs.getInt("LENGTH_UNIT_TYPE_ID"));
        shipmentLengthUnit.setUnitType(shipmentLengthUnitType);
        shipmentLogisticsUnit.setLengthUnit(shipmentLengthUnit);
        shipmentLogisticsUnit.setLogisticsUnitId(rs.getInt("LOGISTICS_UNIT_ID"));
        PrgManufacturerDTO shipmentManufacturer = new PrgManufacturerDTO();
        shipmentManufacturer.setLabel(new PrgLabelDTORowMapper("MANUFACTURER_").mapRow(rs, i));
        shipmentManufacturer.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        shipmentLogisticsUnit.setManufacturer(shipmentManufacturer);
        PrgPlanningUnitDTO shipmentPlanningUnit = new PrgPlanningUnitDTO();
        shipmentPlanningUnit.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_").mapRow(rs, i));
        shipmentPlanningUnit.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        shipmentPlanningUnit.setPrice(rs.getDouble("PRICE"));
        shipmentPlanningUnit.setQtyOfForecastingUnits(rs.getDouble("QTY_OF_FORECASTING_UNITS"));
        PrgUnitDTO shipmentPlanningUnitUnit = new PrgUnitDTO();
        shipmentPlanningUnitUnit.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_UNIT_").mapRow(rs, i));
        shipmentPlanningUnitUnit.setUnitCode(rs.getString("PLANNING_UNIT_UNIT_CODE"));
        shipmentPlanningUnitUnit.setUnitId(rs.getInt("PLANNING_UNIT_UNIT_ID"));

        PrgUnitTypeDTO shipmentPlanningUnitUnitType = new PrgUnitTypeDTO();
        shipmentPlanningUnitUnitType.setLabel(new PrgLabelDTORowMapper("PLANNING_UNIT_UNIT_TYPE_").mapRow(rs, i));
        shipmentPlanningUnitUnitType.setUnitTypeId(rs.getInt("PLANNING_UNIT_UNIT_TYPE_ID"));
        shipmentPlanningUnitUnit.setUnitType(shipmentPlanningUnitUnitType);
        shipmentPlanningUnit.setUnit(shipmentPlanningUnitUnit);
        shipmentLogisticsUnit.setPlanningUnit(shipmentPlanningUnit);
        shipmentLogisticsUnit.setQtyInEuro1(rs.getDouble("QTY_IN_EURO1"));
        shipmentLogisticsUnit.setQtyInEuro2(rs.getDouble("QTY_IN_EURO2"));
        shipmentLogisticsUnit.setQtyOfPlanningUnits(rs.getDouble("QTY_OF_PLANNING_UNITS"));
        PrgUnitDTO shipmentLogisticsUnitUnit = new PrgUnitDTO();
        shipmentLogisticsUnitUnit.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_UNIT_").mapRow(rs, i));
        shipmentLogisticsUnitUnit.setUnitCode(rs.getString("LOGISTICS_UNIT_UNIT_CODE"));
        shipmentLogisticsUnitUnit.setUnitId(rs.getInt("LOGISTICS_UNIT_UNIT_ID"));
        PrgUnitTypeDTO shipmentLogisticsUnitUnitType = new PrgUnitTypeDTO();
        shipmentLogisticsUnitUnitType.setLabel(new PrgLabelDTORowMapper("LOGISTICS_UNIT_UNIT_TYPE_").mapRow(rs, i));
        shipmentLogisticsUnitUnitType.setUnitTypeId(rs.getInt("LOGISTICS_UNIT_UNIT_TYPE_ID"));
        shipmentLogisticsUnitUnit.setUnitType(shipmentLogisticsUnitUnitType);
        shipmentLogisticsUnit.setUnit(shipmentLogisticsUnitUnit);
        shipmentLogisticsUnit.setVariant(rs.getString("VARIANT"));

        shipmentLogisticsUnit.setWeightQty(rs.getDouble("WEIGHT_QTY"));
        PrgUnitDTO shipmentWeightUnit = new PrgUnitDTO();
        shipmentWeightUnit.setLabel(new PrgLabelDTORowMapper("WEIGHT_UNIT_").mapRow(rs, i));
        shipmentWeightUnit.setUnitCode(rs.getString("WEIGHT_UNIT_CODE"));
        shipmentWeightUnit.setUnitId(rs.getInt("WEIGHT_UNIT_ID"));
        PrgUnitTypeDTO shipmentWeightUnitType = new PrgUnitTypeDTO();
        shipmentWeightUnitType.setLabel(new PrgLabelDTORowMapper("WEIGHT_UNIT_TYPE_").mapRow(rs, i));
        shipmentWeightUnitType.setUnitTypeId(rs.getInt("WEIGHT_UNIT_TYPE_ID"));
        shipmentWeightUnit.setUnitType(shipmentWeightUnitType);
        shipmentLogisticsUnit.setWeightUnit(shipmentWeightUnit);
        shipmentLogisticsUnit.setWidthQty(rs.getDouble("WIDTH_QTY"));
        PrgUnitDTO shipmentWidthUnit = new PrgUnitDTO();
        shipmentWidthUnit.setLabel(new PrgLabelDTORowMapper("WIDTH_UNIT_").mapRow(rs, i));
        shipmentWidthUnit.setUnitCode(rs.getString("WIDTH_UNIT_CODE"));
        shipmentWidthUnit.setUnitId(rs.getInt("WIDTH_UNIT_ID"));
        PrgUnitTypeDTO shipmentWidthUnitType = new PrgUnitTypeDTO();
        shipmentWidthUnitType.setLabel(new PrgLabelDTORowMapper("WIDTH_UNIT_TYPE_").mapRow(rs, i));
        shipmentWidthUnitType.setUnitTypeId(rs.getInt("WIDTH_UNIT_TYPE_ID"));
        shipmentWidthUnit.setUnitType(shipmentWidthUnitType);
        shipmentLogisticsUnit.setWidthUnit(shipmentWidthUnit);
        shipment.setLogisticsUnit(shipmentLogisticsUnit);
        
        
        shipment.setNotes(rs.getString("NOTES"));
        shipment.setOrderDate(rs.getDate("ORDER_DATE"));
        shipment.setPoroNumber(rs.getString("PO_RO_NUMBER"));
        PrgProcurementAgentDTO procurementAgent = new PrgProcurementAgentDTO();
        procurementAgent.setLabel(new PrgLabelDTORowMapper("PROCURMENT_AGENT_").mapRow(rs, i));
        procurementAgent.setProcurementAgentId(rs.getInt("PROCUREMENT_AGENT_ID"));
        procurementAgent.setSubmittedToApprovedLeadTime(rs.getInt("SUBMITTED_TO_APPROVED_LEAD_TIME"));
        PrgProcurementAgentLogisiticsUnitDTO procurementAgentLogisiticsUnit = new PrgProcurementAgentLogisiticsUnitDTO();
        procurementAgentLogisiticsUnit.setApprovedToShipLeadTime(rs.getInt("APPROVED_TO_SHIP_LEAD_TIME"));
        procurementAgentLogisiticsUnit.setPrice(rs.getDouble("PRICE"));
        procurementAgentLogisiticsUnit.setProcurementAgentSkuId(rs.getInt("PROCUREMENT_AGENT_SKU_ID"));
        procurementAgentLogisiticsUnit.setSkuCode(rs.getString("SKU_CODE"));
        procurementAgent.setPrgProcurementAgentLogisiticsUnit(procurementAgentLogisiticsUnit);
        shipment.setProcurementAgent(procurementAgent);
        shipment.setQty(rs.getDouble("QTY"));
        shipment.setReceiveDate(rs.getDate("RECEIVE_DATE"));
        PrgRegionDTO shipmentRegion = new PrgRegionDTO();
        shipmentRegion.setCapacityCbm(rs.getDouble("REGION_CAPACITY_CBM"));
        shipmentRegion.setLabel(new PrgLabelDTORowMapper("REGION_").mapRow(rs, i));
        shipmentRegion.setRegionId(rs.getInt("REGION_ID"));
        shipment.setRegion(shipmentRegion);
        shipment.setShipDate(rs.getDate("SHIP_DATE"));

        PrgShipmentBudgetDTO shipmentBudget = new PrgShipmentBudgetDTO();
        PrgBudgetDTO shipBud = new PrgBudgetDTO();
        shipBud.setBudgetAmount(rs.getDouble("BUD_AMOUNT"));
        shipBud.setBudgetId(rs.getInt("BUD_BUDGET_ID"));
        shipBud.setLabel(new PrgLabelDTORowMapper("BUD_").mapRow(rs, i));
        shipBud.setStartDate(rs.getDate("BUD_START_DATE"));
        shipBud.setStopDate(rs.getDate("BUD_STOP_DATE"));
        shipmentBudget.setBudget(shipBud);
        shipmentBudget.setBudgetAmount(rs.getDouble("BUDGET_AMOUNT"));
        shipmentBudget.setShipmentBudgetId(rs.getInt("BUDGET_ID"));
        PrgSubFundingSourceDTO shipmentBudgetSubFundingSource = new PrgSubFundingSourceDTO();
        PrgFundingSourceDTO shipmentBudgetFundingSource = new PrgFundingSourceDTO();
        shipmentBudgetFundingSource.setFundingSourceId(rs.getInt("BUDGET_FUNDING_SOURCE_ID"));
        shipmentBudgetFundingSource.setLabel(new PrgLabelDTORowMapper("BUDGET_FUNDING_SOURCE_").mapRow(rs, i));
        shipmentBudgetSubFundingSource.setFundingSource(shipmentBudgetFundingSource);
        shipmentBudgetSubFundingSource.setLabel(new PrgLabelDTORowMapper("BUDGET_SUB_FUNDING_SOURCE_").mapRow(rs, i));
        shipmentBudgetSubFundingSource.setSubFundingSourceId(rs.getInt("BUDGET_SUB_FUNDING_SOURCE_ID"));
        shipmentBudget.setSubFundingSource(shipmentBudgetSubFundingSource);
        shipment.setShipmentBudget(shipmentBudget);
        shipment.setShipmentPrice(rs.getDouble("SHIPMENT_PRICE"));
        PrgShipmentStatusDTO shipmentStatus = new PrgShipmentStatusDTO();
        shipmentStatus.setLabel(new PrgLabelDTORowMapper("SHIPMENT_STATUS_").mapRow(rs, i));
        shipmentStatus.setShipmentStatusId(rs.getInt("SHIPMENT_STATUS_ID"));
        shipment.setShipmentStatus(shipmentStatus);
        shipment.setSuggestedQty(rs.getDouble("SUGGESTED_QTY"));
        return shipment;
    }

}
