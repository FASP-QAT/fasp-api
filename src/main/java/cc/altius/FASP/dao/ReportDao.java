/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.CostOfInventoryOutput;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastErrorOutput;
import cc.altius.FASP.model.report.ForecastMetricsInput;
import cc.altius.FASP.model.report.ForecastMetricsOutput;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.StockOverTimeInput;
import cc.altius.FASP.model.report.StockOverTimeOutput;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ekta
 */
public interface ReportDao {

    public List<Map<String, Object>> getConsumptionData(int realmId, int productcategoryId, int planningUnitId, String StartDate, String endDate);

    public List<Map<String, Object>> getStockStatusMatrix(int realmId, int productcategoryId, int planningUnitId, int view, String startDate, String endDate);

    public List<ForecastErrorOutput> getForecastError(ForecastErrorInput fei, CustomUserDetails curUser);

    public List<ForecastMetricsOutput> getForecastMetrics(ForecastMetricsInput fmi, CustomUserDetails curUser);

    public List<GlobalConsumptionOutput> getGlobalConsumption(GlobalConsumptionInput gci, CustomUserDetails curUser);

    public List<List<StockOverTimeOutput>> getStockOverTime(StockOverTimeInput soti, CustomUserDetails curUser);

    public List<Map<String, Object>> getAnnualShipmentCost(AnnualShipmentCostInput asci, CustomUserDetails curUser);
    
    public List<CostOfInventoryOutput> getCostOfInventory(CostOfInventoryInput cii, CustomUserDetails curUser);
}
