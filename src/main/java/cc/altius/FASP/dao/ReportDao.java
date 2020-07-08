/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.AnnualShipmentCostOutput;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.CostOfInventoryOutput;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastErrorOutput;
import cc.altius.FASP.model.report.ForecastMetricsInput;
import cc.altius.FASP.model.report.ForecastMetricsOutput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutput;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.InventoryTurnsOutput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutput;
import cc.altius.FASP.model.report.ProgramProductCatalogInput;
import cc.altius.FASP.model.report.ProgramProductCatalogOutput;
import cc.altius.FASP.model.report.ShipmentReportInput;
import cc.altius.FASP.model.report.ShipmentReportOutput;
import cc.altius.FASP.model.report.StockAdjustmentReportInput;
import cc.altius.FASP.model.report.StockAdjustmentReportOutput;
import cc.altius.FASP.model.report.StockOverTimeInput;
import cc.altius.FASP.model.report.StockOverTimeOutput;
import cc.altius.FASP.model.report.StockStatusForProgramInput;
import cc.altius.FASP.model.report.StockStatusForProgramOutput;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
import cc.altius.FASP.model.report.StockStatusMatrixOutput;
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.model.report.WarehouseCapacityOutput;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ekta
 */
public interface ReportDao {

    public List<Map<String, Object>> getConsumptionData(int realmId, int productcategoryId, int planningUnitId, String StartDate, String endDate);

    public List<StockStatusMatrixOutput> getStockStatusMatrix(StockStatusMatrixInput ssm);

    public List<ForecastErrorOutput> getForecastError(ForecastErrorInput fei, CustomUserDetails curUser);

    public List<ForecastMetricsOutput> getForecastMetrics(ForecastMetricsInput fmi, CustomUserDetails curUser);

    public List<GlobalConsumptionOutput> getGlobalConsumption(GlobalConsumptionInput gci, CustomUserDetails curUser);

    public List<List<StockOverTimeOutput>> getStockOverTime(StockOverTimeInput soti, CustomUserDetails curUser);

    public List<AnnualShipmentCostOutput> getAnnualShipmentCost(AnnualShipmentCostInput asci, CustomUserDetails curUser);
    
    public List<CostOfInventoryOutput> getCostOfInventory(CostOfInventoryInput cii, CustomUserDetails curUser);
    
    public List<InventoryTurnsOutput> getInventoryTurns(CostOfInventoryInput it, CustomUserDetails curUser);
    
    public List<StockAdjustmentReportOutput> getStockAdjustmentReport(StockAdjustmentReportInput si, CustomUserDetails curUser);
    
    public List<ProcurementAgentShipmentReportOutput> getProcurementAgentShipmentReport(ProcurementAgentShipmentReportInput pari, CustomUserDetails curUser);
    
    public List<FundingSourceShipmentReportOutput> getFundingSourceShipmentReport(FundingSourceShipmentReportInput fsri, CustomUserDetails curUser);
    
    public List<ShipmentReportOutput> getAggregateShipmentByProduct(ShipmentReportInput sri, CustomUserDetails curUser);
    
    public List<WarehouseCapacityOutput> getWarehouseCapacityReport(WarehouseCapacityInput wci, CustomUserDetails curUser);
    
    public List<StockStatusForProgramOutput> getStockStatusForProgram(StockStatusForProgramInput sspi, CustomUserDetails curUser);
    
    public List<ProgramProductCatalogOutput> getProgramProductCatalog(ProgramProductCatalogInput ppc, CustomUserDetails curUser);
}
