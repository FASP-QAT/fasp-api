/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ReportDao;
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
import cc.altius.FASP.service.ReportService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ekta
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDao reportDao;

    @Override
    public List<Map<String, Object>> getConsumptionData(int realmId, int productcategoryId, int planningUnitId, String StartDate, String endDate) {
        return this.reportDao.getConsumptionData(realmId, productcategoryId, planningUnitId, StartDate, endDate);
    }

    @Override
    public List<StockStatusMatrixOutput> getStockStatusMatrix(StockStatusMatrixInput ssm) {
        return this.reportDao.getStockStatusMatrix(ssm);
    }

    @Override
    public List<ForecastErrorOutput> getForecastError(ForecastErrorInput fei, CustomUserDetails curUser) {
        return this.reportDao.getForecastError(fei, curUser);
    }

    @Override
    public List<ForecastMetricsOutput> getForecastMetrics(ForecastMetricsInput fmi, CustomUserDetails curUser) {
        return this.reportDao.getForecastMetrics(fmi, curUser);
    }

    @Override
    public List<GlobalConsumptionOutput> getGlobalConsumption(GlobalConsumptionInput gci, CustomUserDetails curUser) {
        return this.reportDao.getGlobalConsumption(gci, curUser);
    }

    @Override
    public List<StockStatusOverTimeOutput> getStockStatusOverTime(StockStatusOverTimeInput ssot, CustomUserDetails curUser) {
        return this.reportDao.getStockStatusOverTime(ssot, curUser);
    }

    @Override
    public List<AnnualShipmentCostOutput> getAnnualShipmentCost(AnnualShipmentCostInput asci, CustomUserDetails curUser) {
        return this.reportDao.getAnnualShipmentCost(asci, curUser);
    }

    @Override
    public List<CostOfInventoryOutput> getCostOfInventory(CostOfInventoryInput cii, CustomUserDetails curUser) {
        return this.reportDao.getCostOfInventory(cii, curUser);
    }

    @Override
    public List<InventoryTurnsOutput> getInventoryTurns(CostOfInventoryInput it, CustomUserDetails curUser) {
        return this.reportDao.getInventoryTurns(it, curUser);
    }

    @Override
    public List<StockAdjustmentReportOutput> getStockAdjustmentReport(StockAdjustmentReportInput si, CustomUserDetails curUser) {
        return this.reportDao.getStockAdjustmentReport(si, curUser);
    }

    @Override
    public List<ProcurementAgentShipmentReportOutput> getProcurementAgentShipmentReport(ProcurementAgentShipmentReportInput pari, CustomUserDetails curUser) {
        return this.reportDao.getProcurementAgentShipmentReport(pari, curUser);
    }

    @Override
    public List<FundingSourceShipmentReportOutput> getFundingSourceShipmentReport(FundingSourceShipmentReportInput fsri, CustomUserDetails curUser) {
        return this.reportDao.getFundingSourceShipmentReport(fsri, curUser);
    }

    @Override
    public List<ShipmentReportOutput> getAggregateShipmentByProduct(ShipmentReportInput sri, CustomUserDetails curUser) {
        return this.reportDao.getAggregateShipmentByProduct(sri, curUser);
    }

    @Override
    public List<WarehouseCapacityOutput> getWarehouseCapacityReport(WarehouseCapacityInput wci, CustomUserDetails curUser) {
        return this.reportDao.getWarehouseCapacityReport(wci, curUser);
    }

    @Override
    public List<StockStatusForProgramOutput> getStockStatusForProgram(StockStatusForProgramInput sspi, CustomUserDetails curUser) {
        return this.reportDao.getStockStatusForProgram(sspi, curUser);
    }

    @Override
    public List<ProgramProductCatalogOutput> getProgramProductCatalog(ProgramProductCatalogInput ppc, CustomUserDetails curUser) {
        return this.reportDao.getProgramProductCatalog(ppc, curUser);
    }

    @Override
    public List<ProgramLeadTimesOutput> getProgramLeadTimes(ProgramLeadTimesInput plt, CustomUserDetails curUser) {
        return this.reportDao.getProgramLeadTimes(plt, curUser);
    }

    @Override
    public List<StockStatusVerticalOutput> getStockStatusVertical(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        return this.reportDao.getStockStatusVertical(ssv, curUser);
    }

    @Override
    public List<ShipmentDetailsOutput> getShipmentDetails(ShipmentDetailsInput sd, CustomUserDetails curUser) {
        return this.reportDao.getShipmentDetails(sd, curUser);
    }

    @Override
    public ShipmentOverviewOutput getShipmentOverview(ShipmentOverviewInput so, CustomUserDetails curUser) {
        return this.reportDao.getShipmentOverview(so, curUser);
    }

    @Override
    public ShipmentGlobalDemandOutput getShipmentGlobalDemand(ShipmentGlobalDemandInput sgd, CustomUserDetails curUser) {
        return this.reportDao.getShipmentGlobalDemand(sgd, curUser);
    }

}
