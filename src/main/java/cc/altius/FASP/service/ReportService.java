/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.AnnualShipmentCostOutput;
import cc.altius.FASP.model.report.BudgetReportInput;
import cc.altius.FASP.model.report.BudgetReportOutput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualInput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualOutput;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.CostOfInventoryOutput;
import cc.altius.FASP.model.report.ExpiredStockInput;
import cc.altius.FASP.model.report.ExpiredStockOutput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionInput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionOutput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyInput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyOutput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutput;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.InventoryTurnsOutput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutput;
import cc.altius.FASP.model.report.ProgramLeadTimesInput;
import cc.altius.FASP.model.report.ProgramLeadTimesOutput;
import cc.altius.FASP.model.report.ProgramProductCatalogInput;
import cc.altius.FASP.model.report.ProgramProductCatalogOutput;
import cc.altius.FASP.model.report.ShipmentDetailsInput;
import cc.altius.FASP.model.report.ShipmentDetailsOutput;
import cc.altius.FASP.model.report.ShipmentGlobalDemandInput;
import cc.altius.FASP.model.report.ShipmentGlobalDemandOutput;
import cc.altius.FASP.model.report.ShipmentOverviewInput;
import cc.altius.FASP.model.report.ShipmentOverviewOutput;
import cc.altius.FASP.model.report.ShipmentReportInput;
import cc.altius.FASP.model.report.ShipmentReportOutput;
import cc.altius.FASP.model.report.StockAdjustmentReportInput;
import cc.altius.FASP.model.report.StockAdjustmentReportOutput;
import cc.altius.FASP.model.report.StockStatusAcrossProductsInput;
import cc.altius.FASP.model.report.StockStatusAcrossProductsOutput;
import cc.altius.FASP.model.report.StockStatusOverTimeInput;
import cc.altius.FASP.model.report.StockStatusOverTimeOutput;
import cc.altius.FASP.model.report.StockStatusForProgramInput;
import cc.altius.FASP.model.report.StockStatusForProgramOutput;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
import cc.altius.FASP.model.report.StockStatusMatrixOutput;
import cc.altius.FASP.model.report.StockStatusVerticalInput;
import cc.altius.FASP.model.report.StockStatusVerticalOutput;
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.model.report.WarehouseCapacityOutput;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ekta
 */
public interface ReportService {

    public List<Map<String, Object>> getConsumptionData(int realmId, int productcategoryId, int planningUnitId, String StartDate, String endDate);

    // Report no 1
    public List<ProgramProductCatalogOutput> getProgramProductCatalog(ProgramProductCatalogInput ppc, CustomUserDetails curUser);

    // Report no 2
    public List<ConsumptionForecastVsActualOutput> getConsumptionForecastVsActual(ConsumptionForecastVsActualInput cfa, CustomUserDetails curUser);

    // Report no 3
    public List<GlobalConsumptionOutput> getGlobalConsumption(GlobalConsumptionInput gci, CustomUserDetails curUser);

    // Report no 4
    public List<ForecastMetricsMonthlyOutput> getForecastMetricsMonthly(ForecastMetricsMonthlyInput fmi, CustomUserDetails curUser);

    // Report no 5
    public List<ForecastMetricsComparisionOutput> getForecastMetricsComparision(ForecastMetricsComparisionInput fmi, CustomUserDetails curUser);

    // Report no 7
    public List<WarehouseCapacityOutput> getWarehouseCapacityReport(WarehouseCapacityInput wci, CustomUserDetails curUser);

    // Report no 8
    public List<CostOfInventoryOutput> getCostOfInventory(CostOfInventoryInput cii, CustomUserDetails curUser);

    // Report no 9
    public List<InventoryTurnsOutput> getInventoryTurns(CostOfInventoryInput it, CustomUserDetails curUser);

    // Report no 10
    public List<ExpiredStockOutput> getExpiredStock(ExpiredStockInput esi, CustomUserDetails curUser);
    
    // Report no 12
    public List<StockAdjustmentReportOutput> getStockAdjustmentReport(StockAdjustmentReportInput si, CustomUserDetails curUser);

    // Report no 13
    public List<ProcurementAgentShipmentReportOutput> getProcurementAgentShipmentReport(ProcurementAgentShipmentReportInput pari, CustomUserDetails curUser);

    // Report no 14
    public List<ProgramLeadTimesOutput> getProgramLeadTimes(ProgramLeadTimesInput plt, CustomUserDetails curUser);

    // Report no 15
    public List<FundingSourceShipmentReportOutput> getFundingSourceShipmentReport(FundingSourceShipmentReportInput fsri, CustomUserDetails curUser);

    // Report no 16
    public List<StockStatusVerticalOutput> getStockStatusVertical(StockStatusVerticalInput ssv, CustomUserDetails curUser);

    // Report no 17
    public List<StockStatusOverTimeOutput> getStockStatusOverTime(StockStatusOverTimeInput ssot, CustomUserDetails curUser);

    // Report no 18
    public List<StockStatusMatrixOutput> getStockStatusMatrix(StockStatusMatrixInput ssm);

    // Report no 19
    public ShipmentDetailsOutput getShipmentDetails(ShipmentDetailsInput sd, CustomUserDetails curUser);

    // Report no 20
    public ShipmentOverviewOutput getShipmentOverview(ShipmentOverviewInput so, CustomUserDetails curUser);

    // Report no 21
    public ShipmentGlobalDemandOutput getShipmentGlobalDemand(ShipmentGlobalDemandInput sgd, CustomUserDetails curUser);

    // Report no 22
    public List<AnnualShipmentCostOutput> getAnnualShipmentCost(AnnualShipmentCostInput asci, CustomUserDetails curUser);

    // Report no 24
    public List<ShipmentReportOutput> getAggregateShipmentByProduct(ShipmentReportInput sri, CustomUserDetails curUser);

    // Report no 28
    public List<StockStatusForProgramOutput> getStockStatusForProgram(StockStatusForProgramInput sspi, CustomUserDetails curUser);

    // Report no 29
    public List<BudgetReportOutput> getBudgetReport(BudgetReportInput br, CustomUserDetails curUser);

    // Report no 30
    public List<StockStatusAcrossProductsOutput> getStockStatusAcrossProducts(StockStatusAcrossProductsInput ssap, CustomUserDetails curUser);

}
