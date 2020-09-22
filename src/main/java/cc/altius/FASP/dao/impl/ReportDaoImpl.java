/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.dao.ReportDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.AnnualShipmentCostOutput;
import cc.altius.FASP.model.report.AnnualShipmentCostOutputRowMapper;
import cc.altius.FASP.model.report.BudgetReportInput;
import cc.altius.FASP.model.report.BudgetReportOutput;
import cc.altius.FASP.model.report.BudgetReportOutputRowMapper;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualInput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualOutput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualOutputRowMapper;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.CostOfInventoryOutput;
import cc.altius.FASP.model.report.CostOfInventoryRowMapper;
import cc.altius.FASP.model.report.ExpiredStockInput;
import cc.altius.FASP.model.report.ExpiredStockOutput;
import cc.altius.FASP.model.report.ExpiredStockOutputRowMapper;
import cc.altius.FASP.model.report.ForecastMetricsComparisionInput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionOutput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionOutputRowMapper;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyInput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyOutput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyOutputRowMapper;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutputRowMapper;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.GlobalConsumptionOutputResultSetExtractor;
import cc.altius.FASP.model.report.InventoryTurnsOutput;
import cc.altius.FASP.model.report.InventoryTurnsOutputRowMapper;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutputRowMapper;
import cc.altius.FASP.model.report.ProgramLeadTimesInput;
import cc.altius.FASP.model.report.ProgramLeadTimesOutput;
import cc.altius.FASP.model.report.ProgramLeadTimesOutputRowMapper;
import cc.altius.FASP.model.report.ProgramProductCatalogInput;
import cc.altius.FASP.model.report.ProgramProductCatalogOutput;
import cc.altius.FASP.model.report.ProgramProductCatalogOutputRowMapper;
import cc.altius.FASP.model.report.ShipmentDetailsInput;
import cc.altius.FASP.model.report.ShipmentDetailsOutput;
import cc.altius.FASP.model.report.ShipmentDetailsOutputRowMapper;
import cc.altius.FASP.model.report.ShipmentGlobalDemandCountryShipmentSplitRowMapper;
import cc.altius.FASP.model.report.ShipmentGlobalDemandCountrySplitRowMapper;
import cc.altius.FASP.model.report.ShipmentGlobalDemandDateSplitRowMapper;
import cc.altius.FASP.model.report.ShipmentGlobalDemandInput;
import cc.altius.FASP.model.report.ShipmentGlobalDemandOutput;
import cc.altius.FASP.model.report.ShipmentGlobalDemandShipmentListRowMapper;
import cc.altius.FASP.model.report.ShipmentOverviewFundindSourceSplitRowMapper;
import cc.altius.FASP.model.report.ShipmentOverviewInput;
import cc.altius.FASP.model.report.ShipmentOverviewOutput;
import cc.altius.FASP.model.report.ShipmentOverviewPlanningUnitSplitRowMapper;
import cc.altius.FASP.model.report.ShipmentOverviewProcurementAgentSplitRowMapper;
import cc.altius.FASP.model.report.ShipmentReportInput;
import cc.altius.FASP.model.report.ShipmentReportOutput;
import cc.altius.FASP.model.report.ShipmentReportOutputRowMapper;
import cc.altius.FASP.model.report.StockAdjustmentReportInput;
import cc.altius.FASP.model.report.StockAdjustmentReportOutput;
import cc.altius.FASP.model.report.StockStatusAcrossProductsForProgram;
import cc.altius.FASP.model.report.StockStatusAcrossProductsForProgramRowMapper;
import cc.altius.FASP.model.report.StockStatusAcrossProductsInput;
import cc.altius.FASP.model.report.StockStatusAcrossProductsOutput;
import cc.altius.FASP.model.report.StockStatusAcrossProductsOutputResultsetExtractor;
import cc.altius.FASP.model.report.StockStatusOverTimeInput;
import cc.altius.FASP.model.report.StockStatusOverTimeOutput;
import cc.altius.FASP.model.report.StockStatusOverTimeOutputRowMapper;
import cc.altius.FASP.model.report.StockStatusForProgramInput;
import cc.altius.FASP.model.report.StockStatusForProgramOutput;
import cc.altius.FASP.model.report.StockStatusForProgramOutputRowMapper;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
import cc.altius.FASP.model.report.StockStatusMatrixOutput;
import cc.altius.FASP.model.report.StockStatusMatrixOutputRowMapper;
import cc.altius.FASP.model.report.StockStatusVerticalInput;
import cc.altius.FASP.model.report.StockStatusVerticalOutput;
import cc.altius.FASP.model.report.StockStatusVerticalOutputRowMapper;
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.model.report.WarehouseCapacityOutput;
import cc.altius.FASP.model.report.WarehouseCapacityOutputResultSetExtractor;
import cc.altius.FASP.model.rowMapper.StockAdjustmentReportOutputRowMapper;
import cc.altius.FASP.utils.LogUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ekta
 */
@Repository
public class ReportDaoImpl implements ReportDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private ProgramDao programDao;

    @Override
    public List<Map<String, Object>> getConsumptionData(int realmId, int programId, int planningUnitId, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();

        String sql = "	SELECT "
                + "		DATE_FORMAT(cons.`CONSUMPTION_DATE`,'%m-%Y') consumption_date,SUM(IF(cons.`ACTUAL_FLAG`=1,cons.`CONSUMPTION_QTY`,0)) Actual,SUM(IF(cons.`ACTUAL_FLAG`=0,cons.`CONSUMPTION_QTY`,0)) forcast	FROM  rm_consumption_trans cons "
                + "	LEFT JOIN rm_consumption con  ON con.CONSUMPTION_ID=cons.CONSUMPTION_ID"
                + "	LEFT JOIN rm_program p ON con.PROGRAM_ID=p.PROGRAM_ID"
                + "	LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID`"
                + "	LEFT JOIN rm_region r ON cons.REGION_ID=r.REGION_ID"
                + "	LEFT JOIN rm_planning_unit pu ON cons.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID"
                + "	LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`"
                + "	LEFT JOIN rm_data_source ds ON cons.DATA_SOURCE_ID=ds.DATA_SOURCE_ID"
                + "	WHERE  1";
        //+ "rc.`REALM_ID`=:realmId";
        //params.put("realmId", realmId);
        if (programId > 1) {
            sql += "	AND con.`PROGRAM_ID`=:programId";
            params.put("programId", programId);
        }
        // if (planningUnitId != 0) {
        sql += "	AND pu.`PLANNING_UNIT_ID`=:planningUnitId";
        params.put("planningUnitId", planningUnitId);
        // }
        sql += " And cons.`CONSUMPTION_DATE`between :startDate and :endDate	GROUP BY DATE_FORMAT(cons.`CONSUMPTION_DATE`,'%m-%Y') "
                + "    ORDER BY DATE_FORMAT(cons.`CONSUMPTION_DATE`,'%Y-%m')";
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("planningUnitId", planningUnitId);
        return this.namedParameterJdbcTemplate.queryForList(sql, params);
    }

    // Report no 1
    @Override
    public List<ProgramProductCatalogOutput> getProgramProductCatalog(ProgramProductCatalogInput ppc, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", ppc.getProgramId());
        params.put("productCategoryId", ppc.getProductCategoryId());
        params.put("tracerCategoryId", ppc.getTracerCategoryId());
        return this.namedParameterJdbcTemplate.query("CALL programProductCatalog(:programId, :productCategoryId, :tracerCategoryId)", params, new ProgramProductCatalogOutputRowMapper());
    }

    // Report no 2
    @Override
    public List<ConsumptionForecastVsActualOutput> getConsumptionForecastVsActual(ConsumptionForecastVsActualInput cfa, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", cfa.getStartDate());
        params.put("stopDate", cfa.getStopDate());
        params.put("programId", cfa.getProgramId());
        params.put("versionId", cfa.getVersionId());
        params.put("planningUnitId", cfa.getPlanningUnitId());
        params.put("reportView", cfa.getReportView());
        return this.namedParameterJdbcTemplate.query("CALL consumptionForecastedVsActual (:startDate, :stopDate, :programId, :versionId, :planningUnitId, :reportView)", params, new ConsumptionForecastVsActualOutputRowMapper());
    }

    // Report no 3
    @Override
    public List<GlobalConsumptionOutput> getGlobalConsumption(GlobalConsumptionInput gc, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", gc.getRealmId());
        params.put("startDate", gc.getStartDate());
        params.put("stopDate", gc.getStopDate());
        params.put("realmCountryIds", gc.getRealmCountryIdString());
        params.put("programIds", gc.getProgramIdString());
        params.put("planningUnitIds", gc.getPlanningUnitIdString());
        params.put("reportView", gc.getReportView());
        return this.namedParameterJdbcTemplate.query("CALL globalConsumption(:realmId, :realmCountryIds, :programIds, :planningUnitIds, :startDate, :stopDate, :reportView)", params, new GlobalConsumptionOutputResultSetExtractor());
    }

    // Report no 4
    @Override
    public List<ForecastMetricsMonthlyOutput> getForecastMetricsMonthly(ForecastMetricsMonthlyInput fmi, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", fmi.getStartDate());
        params.put("stopDate", fmi.getStopDate());
        params.put("previousMonths", fmi.getPreviousMonths());
        params.put("planningUnitId", fmi.getPlanningUnitId());
        params.put("programId", fmi.getProgramId());
        params.put("versionId", fmi.getVersionId());
        String sql = "CALL forecastMetricsMonthly(:startDate, :stopDate, :programId, :versionId, :planningUnitId, :previousMonths)";
        return this.namedParameterJdbcTemplate.query(sql, params, new ForecastMetricsMonthlyOutputRowMapper());
    }

    // Report no 5
    @Override
    public List<ForecastMetricsComparisionOutput> getForecastMetricsComparision(ForecastMetricsComparisionInput fmi, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", fmi.getRealmId());
        params.put("startDate", fmi.getStartDate());
        params.put("previousMonths", fmi.getPreviousMonths());
        params.put("realmCountryIds", fmi.getRealmCountryIdString());
        params.put("programIds", fmi.getProgramIdString());
        params.put("planningUnitIds", fmi.getPlanningUnitIdString());
        return this.namedParameterJdbcTemplate.query("CALL forecastMetricsComparision(:realmId, :startDate, :realmCountryIds, :programIds, :planningUnitIds, :previousMonths)", params, new ForecastMetricsComparisionOutputRowMapper());
    }

    // Report no 7
    @Override
    public List<WarehouseCapacityOutput> getWarehouseCapacityReport(WarehouseCapacityInput wci, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", wci.getRealmCountryId());
        params.put("programIds", wci.getProgramIdString());
        return this.namedParameterJdbcTemplate.query("CALL warehouseCapacityReport(:realmCountryId, :programIds)", params, new WarehouseCapacityOutputResultSetExtractor());
    }

    // Report no 8
    @Override
    public List<CostOfInventoryOutput> getCostOfInventory(CostOfInventoryInput cii, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", cii.getProgramId());
        params.put("versionId", cii.getVersionId());
        params.put("dt", cii.getDt());
        params.put("includePlannedShipments", cii.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query("CALL costOfInventory(:programId, :versionId, :dt, :includePlannedShipments)", params, new CostOfInventoryRowMapper());
    }

    // Report no 9
    @Override
    public List<InventoryTurnsOutput> getInventoryTurns(CostOfInventoryInput it, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", it.getProgramId());
        params.put("versionId", it.getVersionId());
        params.put("dt", it.getDt());
        params.put("includePlannedShipments", it.isIncludePlannedShipments());
        String sql = "CALL inventoryTurns(:programId, :versionId, :dt, :includePlannedShipments)";
        return this.namedParameterJdbcTemplate.query(sql, params, new InventoryTurnsOutputRowMapper());
    }

    // Report no 10
    @Override
    public List<ExpiredStockOutput> getExpiredStock(ExpiredStockInput es, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", es.getProgramId());
        params.put("versionId", es.getVersionId());
        params.put("startDate", es.getStartDate());
        params.put("stopDate", es.getStopDate());
        params.put("includePlannedShipments", es.isIncludePlannedShipments());
        String sql = "CALL getExpiredStock(:programId, :versionId, :startDate, :stopDate, :includePlannedShipments)";
        return this.namedParameterJdbcTemplate.query(sql, params, new ExpiredStockOutputRowMapper());
    }

    // Report no 12
    @Override
    public List<StockAdjustmentReportOutput> getStockAdjustmentReport(StockAdjustmentReportInput si, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", si.getProgramId());
        params.put("versionId", si.getVersionId());
        params.put("startDate", si.getStartDate());
        params.put("stopDate", si.getStopDate());
        params.put("planningUnitIds", si.getPlanningUnitIdString());
        String sql = "CALL stockAdjustmentReport(:programId, :versionId, :startDate, :stopDate, :planningUnitIds)";
        return this.namedParameterJdbcTemplate.query(sql, params, new StockAdjustmentReportOutputRowMapper());
    }

    // Report no 13
    @Override
    public List<ProcurementAgentShipmentReportOutput> getProcurementAgentShipmentReport(ProcurementAgentShipmentReportInput pari, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("procurementAgentId", pari.getProcurementAgentId());
        params.put("startDate", pari.getStartDate());
        params.put("stopDate", pari.getStopDate());
        params.put("programId", pari.getProgramId());
        params.put("versionId", pari.getVersionId());
        params.put("planningUnitIds", pari.getPlanningUnitIdString());
        params.put("includePlannedShipments", pari.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query("CALL procurementAgentShipmentReport(:startDate, :stopDate, :procurementAgentId, :programId, :versionId, :planningUnitIds, :includePlannedShipments)", params, new ProcurementAgentShipmentReportOutputRowMapper());
    }

    // Report no 14
    @Override
    public List<ProgramLeadTimesOutput> getProgramLeadTimes(ProgramLeadTimesInput plt, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", plt.getProgramId());
        params.put("procurementAgentIds", plt.getProcurementAgentIdString());
        params.put("planningUnitIds", plt.getPlanningUnitIdString());
        return this.namedParameterJdbcTemplate.query("CALL programLeadTimes(:programId, :procurementAgentIds, :planningUnitIds)", params, new ProgramLeadTimesOutputRowMapper());
    }

    // Report no 15
    @Override
    public List<FundingSourceShipmentReportOutput> getFundingSourceShipmentReport(FundingSourceShipmentReportInput fsri, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("fundingSourceId", fsri.getFundingSourceId());
        params.put("startDate", fsri.getStartDate());
        params.put("stopDate", fsri.getStopDate());
        params.put("programId", fsri.getProgramId());
        params.put("versionId", fsri.getVersionId());
        params.put("planningUnitIds", fsri.getPlanningUnitIdString());
        params.put("includePlannedShipments", fsri.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query("CALL fundingSourceShipmentReport(:startDate, :stopDate, :fundingSourceId, :programId, :versionId, :planningUnitIds, :includePlannedShipments)", params, new FundingSourceShipmentReportOutputRowMapper());
    }

    // Report no 16
    // ActualConsumption = 0 -- Forecasted Consumption
    // ActualConsumption = 1 -- Actual Consumption
    // ActualConsumption = null -- No consumption data
    @Override
    public List<StockStatusVerticalOutput> getStockStatusVertical(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", ssv.getStartDate());
        params.put("stopDate", ssv.getStopDate());
        params.put("programId", ssv.getProgramId());
        params.put("versionId", ssv.getVersionId());
        params.put("planningUnitId", ssv.getPlanningUnitId());
        return this.namedParameterJdbcTemplate.query("CALL stockStatusReportVertical(:startDate, :stopDate, :programId, :versionId, :planningUnitId)", params, new StockStatusVerticalOutputRowMapper());
    }

    // Report no 17
    // ActualConsumption = 0 -- Forecasted Consumption
    // ActualConsumption = 1 -- Actual Consumption
    // ActualConsumption = null -- No consumption data
    @Override
    public List<StockStatusOverTimeOutput> getStockStatusOverTime(StockStatusOverTimeInput ssot, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sqlString = "CALL stockStatusOverTime(:startDate, :stopDate, :programId, :versionId, :planningUnitIds, :mosPast, :mosFuture)";
        params.put("startDate", ssot.getStartDate());
        params.put("stopDate", ssot.getStopDate());
        params.put("mosFuture", ssot.getMosFuture());
        params.put("mosPast", ssot.getMosPast());
        params.put("programId", ssot.getProgramId());
        params.put("versionId", ssot.getVersionId());
        params.put("planningUnitIds", ssot.getPlanningUnitIdString());
        return this.namedParameterJdbcTemplate.query(sqlString, params, new StockStatusOverTimeOutputRowMapper());
    }

    // Report no 18
    @Override
    public List<StockStatusMatrixOutput> getStockStatusMatrix(StockStatusMatrixInput ssm) {
        String sql = "CALL stockStatusMatrix(:programId, :versionId, :planningUnitId, :startDate, :stopDate, :includePlannedShipments)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("programId", ssm.getProgramId());
        params.put("versionId", ssm.getVersionId());
        params.put("startDate", ssm.getStartDate());
        params.put("stopDate", ssm.getStopDate());
        params.put("includePlannedShipments", ssm.isIncludePlannedShipments());
        List<StockStatusMatrixOutput> finalList = new LinkedList<>();
        for (String pu : ssm.getPlanningUnitIds()) {
            params.remove("planningUnitId", pu);
            params.put("planningUnitId", pu);
            finalList.addAll(this.namedParameterJdbcTemplate.query(sql, params, new StockStatusMatrixOutputRowMapper()));
        }
        return finalList;
    }

    // Report no 19
    @Override
    public List<ShipmentDetailsOutput> getShipmentDetails(ShipmentDetailsInput sd, CustomUserDetails curUser) {
        String sql = "CALL shipmentDetails(:startDate, :stopDate, :programId, :versionId, :planningUnitIds)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("programId", sd.getProgramId());
        params.put("versionId", sd.getVersionId());
        params.put("planningUnitIds", sd.getPlanningUnitIdsString());
        params.put("startDate", sd.getStartDate());
        params.put("stopDate", sd.getStopDate());
        return this.namedParameterJdbcTemplate.query(sql, params, new ShipmentDetailsOutputRowMapper());
    }

    // Report no 20
    @Override
    public ShipmentOverviewOutput getShipmentOverview(ShipmentOverviewInput so, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("realmId", so.getRealmId());
        params.put("startDate", so.getStartDate());
        params.put("stopDate", so.getStopDate());
        params.put("planningUnitIds", so.getPlanningUnitIdsString());
        params.put("fundingSourceIds", so.getFundingSourceIdsString());
        params.put("shipmentStatusIds", so.getShipmentStatusIdsString());
        ShipmentOverviewOutput soo = new ShipmentOverviewOutput();
        String sql = "CALL shipmentOverview_FundingSourceSplit(:realmId, :startDate, :stopDate, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds)";
        soo.setFundingSourceSplit(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentOverviewFundindSourceSplitRowMapper()));
        sql = "CALL shipmentOverview_PlanningUnitSplit(:realmId, :startDate, :stopDate, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds)";
        soo.setPlanningUnitSplit(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentOverviewPlanningUnitSplitRowMapper()));
        sql = "CALL shipmentOverview_ProcurementAgentSplit(:realmId, :startDate, :stopDate, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds)";
        soo.setProcurementAgentSplit(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentOverviewProcurementAgentSplitRowMapper()));
        return soo;
    }

    // Report no 21
    @Override
    public ShipmentGlobalDemandOutput getShipmentGlobalDemand(ShipmentGlobalDemandInput sgd, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("realmId", sgd.getRealmId());
        params.put("startDate", sgd.getStartDate());
        params.put("stopDate", sgd.getStopDate());
        params.put("realmCountryIds", sgd.getRealmCountryIdsString());
        params.put("planningUnitId", sgd.getPlanningUnitId());
        params.put("fundingSourceProcurementAgentIds", sgd.getFundingSourceProcurementAgentIdsString());
        params.put("reportView", sgd.getReportView());
        ShipmentGlobalDemandOutput sgdo = new ShipmentGlobalDemandOutput();
        String sql = "CALL shipmentGlobalDemand_ShipmentList(:realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId)";
        sgdo.setShipmentList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandShipmentListRowMapper()));
        if (sgd.getReportView() == 1) {
            sql = "CALL shipmentGlobalDemand_FundingSourceDateSplit(:realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId)";
        } else {
            sql = "CALL shipmentGlobalDemand_ProcurementAgentDateSplit(:realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId)";
        }
        sgdo.setDateSplitList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandDateSplitRowMapper()));

        if (sgd.getReportView() == 1) {
            sql = "CALL shipmentGlobalDemand_FundingSourceCountrySplit(:realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId)";
        } else {
            sql = "CALL shipmentGlobalDemand_ProcurementAgentCountrySplit(:realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId)";
        }
        sgdo.setCountrySplitList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandCountrySplitRowMapper()));
        sql = "CALL shipmentGlobalDemand_CountryShipmentSplit(:realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId)";
        sgdo.setCountryShipmentSplitList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandCountryShipmentSplitRowMapper()));
        return sgdo;
    }

    // Report no 22
    @Override
    public List<AnnualShipmentCostOutput> getAnnualShipmentCost(AnnualShipmentCostInput asci, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", asci.getStartDate());
        params.put("stopDate", asci.getStopDate());
        params.put("procurementAgentId", asci.getProcurementAgentId());
        params.put("programId", asci.getProgramId());
        params.put("versionId", asci.getVersionId());
        params.put("planningUnitId", asci.getPlanningUnitId());
        params.put("fundingSourceId", asci.getFundingSourceId());
        params.put("shipmentStatusId", asci.getShipmentStatusId());
        params.put("reportBasedOn", asci.getReportBasedOn());
        return this.namedParameterJdbcTemplate.query("CALL annualShipmentCost(:programId, :versionId, :procurementAgentId, :planningUnitId, :fundingSourceId, :shipmentStatusId, :startDate, :stopDate, :reportBasedOn)", params, new AnnualShipmentCostOutputRowMapper());
    }

    // Report no 24
    @Override
    public List<ShipmentReportOutput> getAggregateShipmentByProduct(ShipmentReportInput sri, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", sri.getStartDate());
        params.put("stopDate", sri.getStopDate());
        params.put("programId", sri.getProgramId());
        params.put("versionId", sri.getVersionId());
        params.put("planningUnitIds", sri.getPlanningUnitIdString());
        params.put("includePlannedShipments", sri.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query("CALL aggregateShipmentByProduct(:startDate, :stopDate, :programId, :versionId, :planningUnitIds, :includePlannedShipments)", params, new ShipmentReportOutputRowMapper());
    }

    // Report no 28
    @Override
    public List<StockStatusForProgramOutput> getStockStatusForProgram(StockStatusForProgramInput sspi, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("dt", sspi.getDt());
        params.put("programId", sspi.getProgramId());
        params.put("versionId", sspi.getVersionId());
        params.put("includePlannedShipments", sspi.isIncludePlannedShipments());
        List<StockStatusForProgramOutput> finalList = new LinkedList<>();
        String sql = "CALL getStockStatusForProgram(:programId, :versionId, :planningUnitId, :dt, :includePlannedShipments)";
        for (ProgramPlanningUnit ppu : this.programDao.getPlanningUnitListForProgramId(sspi.getProgramId(), true, curUser)) {
            params.remove("planningUnitId");
            params.put("planningUnitId", ppu.getPlanningUnit().getId());
            finalList.addAll(this.namedParameterJdbcTemplate.query(sql, params, new StockStatusForProgramOutputRowMapper()));
        }
        return finalList;
    }

    // Report no 29
    @Override
    public List<BudgetReportOutput> getBudgetReport(BudgetReportInput br, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", br.getProgramId());
        params.put("versionId", br.getVersionId());
        return this.namedParameterJdbcTemplate.query("CALL budgetReport(:programId, :versionId)", params, new BudgetReportOutputRowMapper());
    }

    // Report no 30 - Basic info
    @Override
    public List<StockStatusAcrossProductsOutput> getStockStatusAcrossProductsBasicInfo(StockStatusAcrossProductsInput ssap, CustomUserDetails curUser) {

        String sql = "SELECT "
                + "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,"
                + "	p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR` "
                + "FROM vw_program p "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "WHERE "
                + "rc.REALM_ID = :realmId "
                + " AND (:tracerCategoryId=-1 OR fu.TRACER_CATEGORY_ID=:tracerCategoryId) "
                + " AND ppu.ACTIVE AND p.ACTIVE ";
        if (ssap.getRealmCountryIds().length > 0) {
            sql += " AND p.REALM_COUNTRY_ID in (" + ssap.getRealmCountryIdsString() + ") ";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", ssap.getRealmId());
        params.put("tracerCategoryId", ssap.getTracerCategoryId());
        return this.namedParameterJdbcTemplate.query(sql, params, new StockStatusAcrossProductsOutputResultsetExtractor());
    }

    // Report no 30 - Actual data
    @Override
    public StockStatusAcrossProductsForProgram getStockStatusAcrossProductsProgramData(int programId, int planningUnitId, Date dt) {
        String sql = "CALL stockStatusForProgramPlanningUnit(:programId, -1, :planningUnitId, :dt)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        params.put("dt", dt);
        return this.namedParameterJdbcTemplate.queryForObject(sql, params, new StockStatusAcrossProductsForProgramRowMapper());
    }

}
