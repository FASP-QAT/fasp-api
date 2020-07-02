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
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.InventoryTurnsOutput;
import cc.altius.FASP.model.report.StockAdjustmentListInput;
import cc.altius.FASP.model.report.StockAdjustmentListOutput;
import cc.altius.FASP.model.report.StockOverTimeInput;
import cc.altius.FASP.model.report.StockOverTimeOutput;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
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
    public List<Map<String, Object>> getStockStatusMatrix(StockStatusMatrixInput ssm) {
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
    public List<List<StockOverTimeOutput>> getStockOverTime(StockOverTimeInput soti, CustomUserDetails curUser) {
        return this.reportDao.getStockOverTime(soti, curUser);
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
    public List<StockAdjustmentListOutput> getStockAdjustment(StockAdjustmentListInput si, CustomUserDetails curUser) {
        return this.reportDao.getStockAdjustment(si, curUser);
    }

}
