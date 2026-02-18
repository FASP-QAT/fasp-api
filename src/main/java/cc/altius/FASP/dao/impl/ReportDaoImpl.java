/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ReportDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.AnnualShipmentCostOutput;
import cc.altius.FASP.model.report.AnnualShipmentCostOutputRowMapper;
import cc.altius.FASP.model.report.BudgetReportInput;
import cc.altius.FASP.model.report.BudgetReportOutput;
import cc.altius.FASP.model.report.BudgetReportOutputRowMapper;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualInput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualOutput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualOutputRowMapper;
import cc.altius.FASP.model.report.ConsumptionInfo;
import cc.altius.FASP.model.report.ConsumptionInfoRowMapper;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.CostOfInventoryOutput;
import cc.altius.FASP.model.report.CostOfInventoryRowMapper;
import cc.altius.FASP.model.report.ExpiredStockInput;
import cc.altius.FASP.model.report.ExpiredStockOutput;
import cc.altius.FASP.model.report.ExpiredStockOutputResultSetExtractor;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastErrorInputNew;
import cc.altius.FASP.model.report.ForecastErrorOutput;
import cc.altius.FASP.model.report.ForecastErrorOutputListResultSetExtractor;
import cc.altius.FASP.model.report.ForecastMetricsComparisionInput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionOutput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionOutputRowMapper;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyInput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyOutput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyOutputRowMapper;
import cc.altius.FASP.model.report.ForecastSummaryInput;
import cc.altius.FASP.model.report.ForecastSummaryOutput;
import cc.altius.FASP.model.report.ForecastSummaryOutputListResultSetExtractor;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutputRowMapper;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.GlobalConsumptionOutputResultSetExtractor;
import cc.altius.FASP.model.report.InventoryInfo;
import cc.altius.FASP.model.report.InventoryInfoRowMapper;
import cc.altius.FASP.model.report.InventoryTurnsInput;
import cc.altius.FASP.model.report.InventoryTurnsOutput;
import cc.altius.FASP.model.report.InventoryTurnsOutputRowMapper;
import cc.altius.FASP.model.report.MonthlyForecastInput;
import cc.altius.FASP.model.report.MonthlyForecastOutput;
import cc.altius.FASP.model.report.MonthlyForecastOutputListResultSetExtractor;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutputRowMapper;
import cc.altius.FASP.model.report.ProgramAndPlanningUnit;
import cc.altius.FASP.model.report.ProgramLeadTimesInput;
import cc.altius.FASP.model.report.ProgramLeadTimesOutput;
import cc.altius.FASP.model.report.ProgramLeadTimesOutputRowMapper;
import cc.altius.FASP.model.report.ProgramProductCatalogInput;
import cc.altius.FASP.model.report.ProgramProductCatalogOutput;
import cc.altius.FASP.model.report.ProgramProductCatalogOutputRowMapper;
import cc.altius.FASP.model.report.ShipmentDetailsFundingSourceProcurementAgentRowMapper;
import cc.altius.FASP.model.report.ShipmentDetailsInput;
import cc.altius.FASP.model.report.ShipmentDetailsOutput;
import cc.altius.FASP.model.report.ShipmentDetailsListRowMapper;
import cc.altius.FASP.model.report.ShipmentDetailsMonthRowMapper;
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
import cc.altius.FASP.model.report.ShipmentOverviewProcurementAgentSplit;
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
import cc.altius.FASP.model.report.StockStatusVerticalAggregateOutput;
import cc.altius.FASP.model.report.StockStatusVerticalAggregateOutputRowMapper;
import cc.altius.FASP.model.report.StockStatusVerticalIndividualOutput;
import cc.altius.FASP.model.report.StockStatusVerticalIndividualOutputResultSetExtractor;
import cc.altius.FASP.model.report.StockStatusVerticalInput;
import cc.altius.FASP.model.report.WarehouseByCountryInput;
import cc.altius.FASP.model.report.WarehouseByCountryOutput;
import cc.altius.FASP.model.report.WarehouseByCountryOutputRowMapper;
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.model.report.WarehouseCapacityOutput;
import cc.altius.FASP.model.report.WarehouseCapacityOutputResultSetExtractor;
import cc.altius.FASP.model.rowMapper.BatchCostResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramAndPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.StockAdjustmentReportOutputRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.FASP.utils.LogUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private AclService aclService;

    // Report no 1
    // CALL programProductCatalog(2030, -1, -1);
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
        params.put("versionId", gc.getVersionId());
        params.put("equivalencyUnitId", gc.getEquivalencyUnitId());
        params.put("planningUnitIds", gc.getPlanningUnitIdString());
        params.put("viewBy", gc.getViewBy());
        params.put("curUser", curUser.getUserId());
        return this.namedParameterJdbcTemplate.query("CALL globalConsumption(:curUser, :realmId, :realmCountryIds, :equivalencyUnitId, :programIds, :versionId, :planningUnitIds, :startDate, :stopDate, :viewBy)", params, new GlobalConsumptionOutputResultSetExtractor());
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
        params.put("realmCountryIds", String.join(",", fmi.getRealmCountryIds()));
        params.put("programIds", String.join(",", fmi.getProgramIds()));
        params.put("planningUnitIds", String.join(",", fmi.getPlanningUnitIds()));
        params.put("tracerCategoryIds", String.join(",", fmi.getTracerCategoryIds()));
        params.put("approvedSupplyPlanOnly", fmi.isUseApprovedSupplyPlanOnly());
        params.put("curUser", curUser.getUserId());
        return this.namedParameterJdbcTemplate.query("CALL forecastMetricsComparision(:curUser, :realmId, :startDate, :realmCountryIds, :programIds, :tracerCategoryIds, :planningUnitIds, :previousMonths, :approvedSupplyPlanOnly)", params, new ForecastMetricsComparisionOutputRowMapper());
    }

    // Report no 7
    @Override
    public List<WarehouseCapacityOutput> getWarehouseCapacityReport(WarehouseCapacityInput wci, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", curUser.getRealm().getRealmId());
        params.put("realmCountryIds", wci.getRealmCountryIdString());
        params.put("programIds", wci.getProgramIdString());
        params.put("curUser", curUser.getUserId());
        return this.namedParameterJdbcTemplate.query("CALL warehouseCapacityReport(:curUser, :realmId, :realmCountryIds, :programIds)", params, new WarehouseCapacityOutputResultSetExtractor());
    }

    // Report 
    @Override
    public List<WarehouseByCountryOutput> getWarehouseByCountryReport(WarehouseByCountryInput wci, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", curUser.getRealm().getRealmId());
        params.put("realmCountryIds", wci.getRealmCountryIdString());
        params.put("curUser", curUser.getUserId());
        return this.namedParameterJdbcTemplate.query("CALL warehouseByCountryReport(:curUser, :realmId, :realmCountryIds)", params, new WarehouseByCountryOutputRowMapper());
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
    public List<InventoryTurnsOutput> getInventoryTurns(InventoryTurnsInput it, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programIdString", ArrayUtils.convertArrayToString(it.getProgramIds()));
        params.put("productCategoryIdString", ArrayUtils.convertArrayToString(it.getProductCategoryIds()));
        params.put("dt", it.getDt());
        params.put("viewBy", it.getViewBy());
        params.put("includePlannedShipments", it.isIncludePlannedShipments());
        params.put("approvedSupplyPlanOnly", it.isUseApprovedSupplyPlanOnly());
        String sql = "CALL inventoryTurns(:dt, :viewBy, :programIdString, :productCategoryIdString, :includePlannedShipments, :approvedSupplyPlanOnly)";
        return this.namedParameterJdbcTemplate.query(sql, params, new InventoryTurnsOutputRowMapper());
    }

    // Report no 11
    @Override
    public List<ExpiredStockOutput> getExpiredStock(ExpiredStockInput es, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", es.getProgramId());
        params.put("versionId", es.getVersionId());
        params.put("startDate", es.getStartDate());
        params.put("stopDate", es.getStopDate());
        params.put("includePlannedShipments", es.isIncludePlannedShipments());
        String sql = "CALL getExpiredStock(:programId, :versionId, :startDate, :stopDate, :includePlannedShipments)";
        List<ExpiredStockOutput> expiredStockList = this.namedParameterJdbcTemplate.query(sql, params, new ExpiredStockOutputResultSetExtractor());
        String batchIdList = expiredStockList.stream().map(exp -> Integer.toString(exp.getBatchInfo().getBatchId())).collect(Collectors.joining(","));
        if (batchIdList != null && !batchIdList.equals("")) {
            sql = "SELECT stbi.BATCH_ID, st.RATE `COST`, st.VERSION_ID "
                    + "FROM rm_shipment_trans_batch_info stbi "
                    + "LEFT JOIN rm_shipment_trans st ON stbi.SHIPMENT_TRANS_ID=st.SHIPMENT_TRANS_ID AND st.VERSION_ID<=:versionId "
                    + "WHERE stbi.BATCH_ID in (" + batchIdList + ") "
                    + "ORDER BY stbi.BATCH_ID, st.VERSION_ID DESC";
            params.clear();
            params.put("batchIdList", batchIdList);
            params.put("versionId", es.getVersionId());
            Map<Integer, Double> batchMap = this.namedParameterJdbcTemplate.query(sql, params, new BatchCostResultSetExtractor());
            expiredStockList.stream().forEach(exp1 -> {
                if (batchMap.get(exp1.getBatchInfo().getBatchId()) != null) {
                    exp1.setCost(batchMap.get(exp1.getBatchInfo().getBatchId()));
                }
            });
        }
        return expiredStockList;
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
        params.put("procurementAgentIds", pari.getProcurementAgentIdString());
        params.put("startDate", pari.getStartDate());
        params.put("stopDate", pari.getStopDate());
        params.put("programId", pari.getProgramId());
        params.put("versionId", pari.getVersionId());
        params.put("planningUnitIds", pari.getPlanningUnitIdString());
        params.put("includePlannedShipments", pari.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query("CALL procurementAgentShipmentReport(:startDate, :stopDate, :procurementAgentIds, :programId, :versionId, :planningUnitIds, :includePlannedShipments)", params, new ProcurementAgentShipmentReportOutputRowMapper());
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
        params.put("fundingSourceIds", fsri.getFundingSourceIdString());
        params.put("startDate", fsri.getStartDate());
        params.put("stopDate", fsri.getStopDate());
        params.put("programId", fsri.getProgramId());
        params.put("versionId", fsri.getVersionId());
        params.put("planningUnitIds", fsri.getPlanningUnitIdString());
        params.put("includePlannedShipments", fsri.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query("CALL fundingSourceShipmentReport(:startDate, :stopDate, :fundingSourceIds, :programId, :versionId, :planningUnitIds, :includePlannedShipments)", params, new FundingSourceShipmentReportOutputRowMapper());
    }

    // Report no 16
    @Override
    public List<StockStatusVerticalAggregateOutput> getStockStatusVerticalAggregate(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", ssv.getStartDate());
        params.put("stopDate", ssv.getStopDate());
        params.put("reportingUnitIds", ssv.getReportingUnitIdsString());
        params.put("viewBy", ssv.getViewBy());
        params.put("equivalencyUnitId", ssv.getEquivalencyUnitId());
        params.put("programIds", ssv.getProgramIdsString());
        params.put("versionId", (ssv.getProgramIds().length > 1 ? -1 : ssv.getVersionId()));
        params.put("multiplePrograms", ssv.getProgramIds().length > 1 ? (Integer) 1 : (Integer) 0);
        return this.namedParameterJdbcTemplate.query("CALL stockStatusReportVerticalAggregated(:startDate, :stopDate, :programIds, :reportingUnitIds, :viewBy, :equivalencyUnitId, :versionId, :multiplePrograms)", params, new StockStatusVerticalAggregateOutputRowMapper());
    }

    // Report no 16
    @Override
    public StockStatusVerticalIndividualOutput getStockStatusVerticalIndividual(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", ssv.getStartDate());
        params.put("stopDate", ssv.getStopDate());
        params.put("reportingUnitId", ssv.getReportingUnitId());
        params.put("viewBy", ssv.getViewBy());
        params.put("equivalencyUnitId", ssv.getEquivalencyUnitId());
        params.put("programId", ssv.getProgramId());
        params.put("versionId", ssv.getVersionId());
        return this.namedParameterJdbcTemplate.query("CALL stockStatusReportVerticalIndividual(:startDate, :stopDate, :programId, :versionId, :reportingUnitId, :viewBy, :equivalencyUnitId)", params, new StockStatusVerticalIndividualOutputResultSetExtractor());
    }

    @Override
    public List<ProgramAndPlanningUnit> getPlanningUnitListForStockStatusVerticalAggregate(StockStatusVerticalInput ssvi, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        if (ssvi.getViewBy() == 1) { // PU
            sqlStringBuilder.append("SELECT p.PROGRAM_ID, pu.`PLANNING_UNIT_ID` "
                    + "FROM rm_program_planning_unit ppu "
                    + "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                    + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                    + "WHERE FIND_IN_SET(ppu.PROGRAM_ID, :programIds) AND FIND_IN_SET(ppu.PLANNING_UNIT_ID, :ruIds) AND ppu.ACTIVE AND pu.ACTIVE "
                    + "GROUP BY p.PROGRAM_ID, pu.PLANNING_UNIT_ID");
        } else if (ssvi.getViewBy() == 2) {//ARU
            sqlStringBuilder.append("SELECT p.PROGRAM_ID, pu.`PLANNING_UNIT_ID` "
                    + "FROM rm_program_planning_unit ppu "
                    + "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                    + "LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                    + "WHERE FIND_IN_SET(ppu.PROGRAM_ID, :programIds) AND FIND_IN_SET(ppu.PLANNING_UNIT_ID, (SELECT GROUP_CONCAT(DISTINCT rcpu.PLANNING_UNIT_ID) FROM rm_realm_country_planning_unit rcpu WHERE FIND_IN_SET(rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, :ruIds))) AND ppu.ACTIVE AND pu.ACTIVE "
                    + "GROUP BY p.PROGRAM_ID, pu.PLANNING_UNIT_ID");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", ssvi.getProgramIdsString());
        params.put("ruIds", ssvi.getReportingUnitIdsString());
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProgramAndPlanningUnitRowMapper());
    }

    // Report no 16a
    @Override
    public List<ConsumptionInfo> getConsumptionInfoForSSVAggregateReport(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", ssv.getStartDate());
        params.put("stopDate", ssv.getStopDate());
        params.put("viewBy", ssv.getViewBy());
        params.put("versionId", (ssv.getProgramIds().length > 1 ? -1 : ssv.getVersionId()));
        params.put("programIds", ssv.getProgramIdsString());
        params.put("reportingUnitIds", ssv.getReportingUnitIdsString());
        return this.namedParameterJdbcTemplate.query("CALL getConsumptionInfoForSSVAggregateReport(:startDate, :stopDate, :programIds, :reportingUnitIds, :viewBy, :versionId)", params, new ConsumptionInfoRowMapper());
    }

    // Report no 16a
    @Override
    public List<ConsumptionInfo> getConsumptionInfoForSSVIndividualReport(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", ssv.getStartDate());
        params.put("stopDate", ssv.getStopDate());
        params.put("viewBy", ssv.getViewBy());
        params.put("versionId", ssv.getVersionId());
        params.put("programId", ssv.getProgramId());
        params.put("reportingUnitIds", ssv.getReportingUnitId());
        return this.namedParameterJdbcTemplate.query("CALL getConsumptionInfoForSSVIndividualReport(:startDate, :stopDate, :programId, :versionId, :reportingUnitIds, :viewBy)", params, new ConsumptionInfoRowMapper());
    }

    // Report no 16b
    @Override
    public List<InventoryInfo> getInventoryInfoForSSVAggregateReport(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", ssv.getStartDate());
        params.put("stopDate", ssv.getStopDate());
        params.put("viewBy", ssv.getViewBy());
        params.put("versionId", (ssv.getProgramIds().length > 1 ? -1 : ssv.getVersionId()));
        params.put("programIds", ssv.getProgramIdsString());
        params.put("reportingUnitIds", ssv.getReportingUnitIdsString());
        return this.namedParameterJdbcTemplate.query("CALL getInventoryInfoForSSVAggregateReport(:startDate, :stopDate, :programIds, :reportingUnitIds, :viewBy, :versionId)", params, new InventoryInfoRowMapper());
    }

    // Report no 16b
    @Override
    public List<InventoryInfo> getInventoryInfoForSSVIndividualReport(StockStatusVerticalInput ssv, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", ssv.getStartDate());
        params.put("stopDate", ssv.getStopDate());
        params.put("viewBy", ssv.getViewBy());
        params.put("programId", ssv.getProgramId());
        params.put("versionId", ssv.getVersionId());
        params.put("reportingUnitIds", ssv.getReportingUnitId());
        return this.namedParameterJdbcTemplate.query("CALL getInventoryInfoForSSVIndividualReport(:startDate, :stopDate, :programId, :versionId, :reportingUnitIds, :viewBy)", params, new InventoryInfoRowMapper());
    }

    @Override
    public boolean checkIfExistsRuForProgram(int programId, int reportingUnitId, int viewBy) {
        String sqlString = "";
        sqlString = "SELECT IF(COUNT(ppu.PROGRAM_PLANNING_UNIT_ID)>0,true,false) `check` "
                + "FROM rm_program_planning_unit ppu "
                + "LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country_planning_unit rcpu ON rcpu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID "
                + "WHERE "
                + "    ppu.PROGRAM_ID=:programId "
                + "    AND ("
                + "        (:viewBy=1 AND ppu.PLANNING_UNIT_ID=:reportingUnitId) OR "
                + "        (:viewBy=2 AND rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=:reportingUnitId) "
                + "    ) AND ppu.ACTIVE";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("reportingUnitId", reportingUnitId);
        params.put("viewBy", viewBy);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Boolean.class);
    }

    // Report no 17
    // ActualConsumption = 0 -- Forecasted Consumption
    // ActualConsumption = 1 -- Actual Consumption
    // ActualConsumption = null -- No consumption data
    @Override
    public List<StockStatusOverTimeOutput> getStockStatusOverTime(StockStatusOverTimeInput ssot, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sqlString = "CALL stockStatusOverTime(:startDate, :stopDate, :programId, :versionId, :planningUnitIds)";
        params.put("startDate", ssot.getStartDate());
        params.put("stopDate", ssot.getStopDate());
        params.put("programId", ssot.getProgramId());
        params.put("versionId", ssot.getVersionId());
        params.put("planningUnitIds", ssot.getPlanningUnitIdString());
        return this.namedParameterJdbcTemplate.query(sqlString, params, new StockStatusOverTimeOutputRowMapper());
    }

    // Report no 18
    @Override
    public List<StockStatusMatrixOutput> getStockStatusMatrix(StockStatusMatrixInput ssm) {
        String sql = "CALL stockStatusMatrix(:programId, :versionId, :tracerCategoryIds, :planningUnitIds, :startDate, :stopDate, :includePlannedShipments)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("programId", ssm.getProgramId());
        params.put("versionId", ssm.getVersionId());
        params.put("startDate", ssm.getStartDate());
        params.put("stopDate", ssm.getStopDate());
        params.put("includePlannedShipments", ssm.isIncludePlannedShipments());
        params.put("planningUnitIds", String.join(",", ssm.getPlanningUnitIds()));
        params.put("tracerCategoryIds", String.join(",", ssm.getTracerCategoryIds()));
        return this.namedParameterJdbcTemplate.query(sql, params, new StockStatusMatrixOutputRowMapper());
    }

    // Report no 19
    @Override
    public ShipmentDetailsOutput getShipmentDetails(ShipmentDetailsInput sd, CustomUserDetails curUser) {
        ShipmentDetailsOutput sdo = new ShipmentDetailsOutput();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("curUser", curUser.getUserId());
        params.put("startDate", sd.getStartDate());
        params.put("stopDate", sd.getStopDate());
        params.put("realmCountryIds", sd.getRealmCountryIdsString());
        params.put("programIds", sd.getProgramIdsString());
        if (sd.getProgramIds().length > 1) {
            sd.setVersionId(null);
        }
        params.put("versionId", sd.getVersionId());
        params.put("planningUnitIds", sd.getPlanningUnitIdsString());
        params.put("reportView", sd.getReportView());
        params.put("fundingSourceProcurementAgentIds", sd.getFundingSourceProcurementAgentIdsString());
        params.put("budgetIds", sd.getBudgetIdsString());
        sdo.setShipmentDetailsList(this.namedParameterJdbcTemplate.query("CALL shipmentDetails(:curUser, :startDate, :stopDate, :realmCountryIds, :programIds, :versionId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :budgetIds)", params, new ShipmentDetailsListRowMapper()));
        sdo.setShipmentDetailsFundingSourceList(this.namedParameterJdbcTemplate.query("CALL shipmentDetailsFundingSource(:curUser, :startDate, :stopDate, :realmCountryIds, :programIds, :versionId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :budgetIds)", params, new ShipmentDetailsFundingSourceProcurementAgentRowMapper()));
        sdo.setShipmentDetailsMonthList(this.namedParameterJdbcTemplate.query("CALL shipmentDetailsMonth(:curUser, :startDate, :stopDate, :realmCountryIds, :programIds, :versionId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :budgetIds)", params, new ShipmentDetailsMonthRowMapper()));
        return sdo;
    }

    // Report no 20
    @Override
    public ShipmentOverviewOutput getShipmentOverview(ShipmentOverviewInput so, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("realmId", so.getRealmId());
        params.put("startDate", so.getStartDate());
        params.put("stopDate", so.getStopDate());
        params.put("realmCountryIds", so.getRealmCountryIdsString());
        params.put("programIds", so.getProgramIdsString());
        params.put("planningUnitIds", so.getPlanningUnitIdsString());
        params.put("fundingSourceIds", so.getFundingSourceIdsString());
        params.put("shipmentStatusIds", so.getShipmentStatusIdsString());
        params.put("approvedSupplyPlanOnly", so.isUseApprovedSupplyPlanOnly());
        params.put("groupByProcurementAgentType", so.isGroupByProcurementAgentType());
        params.put("curUser", curUser.getUserId());
        ShipmentOverviewOutput soo = new ShipmentOverviewOutput();
        String sql = "";
        if (!so.isGroupByFundingSourceType()) {
            sql = "CALL shipmentOverview_FundingSourceSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds, :approvedSupplyPlanOnly)";
        } else {
            sql = "CALL shipmentOverview_FundingSourceTypeSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds, :approvedSupplyPlanOnly)";
        }
        soo.setFundingSourceSplit(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentOverviewFundindSourceSplitRowMapper()));
        sql = "CALL shipmentOverview_PlanningUnitSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds, :approvedSupplyPlanOnly)";
        soo.setPlanningUnitSplit(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentOverviewPlanningUnitSplitRowMapper()));
        if (!so.isGroupByProcurementAgentType()) {
            sql = "CALL shipmentOverview_ProcurementAgentSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds, :approvedSupplyPlanOnly)";
        } else {
            sql = "CALL shipmentOverview_ProcurementAgentTypeSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :fundingSourceIds, :planningUnitIds, :shipmentStatusIds, :approvedSupplyPlanOnly)";
        }
        List<ShipmentOverviewProcurementAgentSplit> sopList = this.namedParameterJdbcTemplate.query(sql, params, new ShipmentOverviewProcurementAgentSplitRowMapper());
        if (!sopList.isEmpty()) {
            List<String> keyListToRemove = new LinkedList<>();
            for (String key : sopList.get(0).getProcurementAgentQty().keySet()) {
                Double total = sopList.stream()
                        .map(x -> (Double) x.getProcurementAgentQty().get(key))
                        .collect(Collectors.summingDouble(Double::doubleValue));
                if (total.doubleValue() == 0) {
                    // Add to the remove List
                    keyListToRemove.add(key);
                }
            }
            keyListToRemove.forEach(key -> {
                sopList.forEach(sop -> {
                    sop.getProcurementAgentQty().remove(key);
                });
            });
        }
        soo.setProcurementAgentSplit(sopList);
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
        params.put("programIds", sgd.getProgramIdsString());
        params.put("equivalencyUnitId", sgd.getEquivalencyUnitId());
        params.put("planningUnitIds", sgd.getPlanningUnitIdsString());
        params.put("fundingSourceProcurementAgentIds", sgd.getFundingSourceProcurementAgentIdsString());
        params.put("reportView", sgd.getReportView());
        params.put("includePlannedShipments", sgd.isIncludePlannedShipments());
        params.put("curUser", curUser.getUserId());
        ShipmentGlobalDemandOutput sgdo = new ShipmentGlobalDemandOutput();
//        String sql = "CALL shipmentGlobalDemand_ShipmentList(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId, :approvedSupplyPlanOnly, :includePlannedShipments)";
//        sgdo.setShipmentList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandShipmentListRowMapper()));
//        switch (sgd.getReportView()) {
//            case 1: //Funding Source
//                sql = "CALL shipmentGlobalDemand_FundingSourceDateSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId, :approvedSupplyPlanOnly, :includePlannedShipments)";
//                break;
//            case 2: // Procurement Agent
//                sql = "CALL shipmentGlobalDemand_ProcurementAgentDateSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId, :approvedSupplyPlanOnly, :includePlannedShipments)";
//                break;
//            case 3: // Procurement Agent Type
//                sql = "CALL shipmentGlobalDemand_ProcurementAgentTypeDateSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId, :approvedSupplyPlanOnly, :includePlannedShipments)";
//                break;
//            case 4: // Funding Source Type
//                sql = "CALL shipmentGlobalDemand_FundingSourceTypeDateSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :reportView, :fundingSourceProcurementAgentIds, :planningUnitId, :approvedSupplyPlanOnly, :includePlannedShipments)";
//                break;
//        }
//        sgdo.setDateSplitList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandDateSplitRowMapper()));
//        String sql = "";
//        switch (sgd.getReportView()) {
//            case 1: //Funding Source
//                sql = "CALL shipmentGlobalDemand_FundingSourceCountrySplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :equivalencyUnitId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :includePlannedShipments)";
//                break;
//            case 2: // Procurement Agent
//                sql = "CALL shipmentGlobalDemand_ProcurementAgentCountrySplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :equivalencyUnitId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :includePlannedShipments)";
//                break;
//            case 3: // Procurement Agent Type
//                sql = "CALL shipmentGlobalDemand_ProcurementAgentTypeCountrySplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :equivalencyUnitId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :includePlannedShipments)";
//                break;
//            case 4: // Funding Source Type
//                sql = "CALL shipmentGlobalDemand_FundingSourceTypeCountrySplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :equivalencyUnitId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :includePlannedShipments)";
//                break;
//        }
//        sgdo.setCountrySplitList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandCountrySplitRowMapper()));
//        
        String sql = "CALL shipmentGlobalDemand_CountrySplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :equivalencyUnitId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :includePlannedShipments)";
        sgdo.setCountrySplitList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandCountrySplitRowMapper()));
        sql = "CALL shipmentGlobalDemand_CountryShipmentSplit(:curUser, :realmId, :startDate, :stopDate, :realmCountryIds, :programIds, :equivalencyUnitId, :planningUnitIds, :reportView, :fundingSourceProcurementAgentIds, :includePlannedShipments)";
        sgdo.setCountryShipmentSplitList(this.namedParameterJdbcTemplate.query(sql, params, new ShipmentGlobalDemandCountryShipmentSplitRowMapper(sgd.isIncludePlannedShipments())));
        return sgdo;
    }

    // Report no 22
    @Override
    public List<AnnualShipmentCostOutput> getAnnualShipmentCost(AnnualShipmentCostInput asci, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", asci.getStartDate());
        params.put("stopDate", asci.getStopDate());
        params.put("procurementAgentIds", asci.getProcurementAgentIdString());
        params.put("programId", asci.getProgramId());
        params.put("versionId", asci.getVersionId());
        params.put("planningUnitIds", asci.getPlanningUnitIdString());
        params.put("fundingSourceIds", asci.getFundingSourceIdString());
        params.put("shipmentStatusIds", asci.getShipmentStatusIdString());
        params.put("reportBasedOn", asci.getReportBasedOn());
        return this.namedParameterJdbcTemplate.query("CALL annualShipmentCost(:programId, :versionId, :procurementAgentIds, :planningUnitIds, :fundingSourceIds, :shipmentStatusIds, :startDate, :stopDate, :reportBasedOn)", params, new AnnualShipmentCostOutputRowMapper());
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
        params.put("tracerCategoryIds", sspi.getTracerCategoryIdString());
        String sql = "CALL getStockStatusForProgram(:programId, :versionId, :dt, :tracerCategoryIds, :includePlannedShipments)";
        return this.namedParameterJdbcTemplate.query(sql, params, new StockStatusForProgramOutputRowMapper());
    }

    // Report no 29
    @Override
    public List<BudgetReportOutput> getBudgetReport(BudgetReportInput br, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", br.getProgramIdString());
        params.put("startDate", br.getStartDate());
        params.put("stopDate", br.getStopDate());
        params.put("fundingSourceIds", br.getFundingSourceIdString());
        StringBuilder sb = new StringBuilder("SELECT "
                + "	b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID, b.LABEL_EN, b.LABEL_FR, b.LABEL_SP, b.LABEL_PR, "
                + "    fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, "
                + "    fst.FUNDING_SOURCE_TYPE_ID, fst.FUNDING_SOURCE_TYPE_CODE, fst.LABEL_ID `FST_LABEL_ID`, fst.LABEL_EN `FST_LABEL_EN`, fst.LABEL_FR `FST_LABEL_FR`, fst.LABEL_SP `FST_LABEL_SP`, fst.LABEL_PR `FST_LABEL_PR`, "
                + "    p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, p.PROGRAM_CODE, "
                + "    c.CURRENCY_ID, c.CURRENCY_CODE, b.CONVERSION_RATE_TO_USD, c.LABEL_ID `CURRENCY_LABEL_ID`, c.LABEL_EN `CURRENCY_LABEL_EN`, c.LABEL_FR `CURRENCY_LABEL_FR`, c.LABEL_SP `CURRENCY_LABEL_SP`, c.LABEL_PR `CURRENCY_LABEL_PR`, "
                + "    (b.BUDGET_AMT * b.CONVERSION_RATE_TO_USD) `BUDGET_AMT`, IFNULL(stc.PLANNED_BUDGET,0) `PLANNED_BUDGET_AMT`, IFNULL(stc.ORDERED_BUDGET,0) `ORDERED_BUDGET_AMT`, b.START_DATE, b.STOP_DATE "
                + "FROM vw_budget b "
                + "LEFT JOIN rm_budget_program bp ON b.BUDGET_ID=bp.BUDGET_ID "
                + "LEFT JOIN vw_program p ON bp.PROGRAM_ID=p.PROGRAM_ID AND p.ACTIVE "
                + "LEFT JOIN vw_funding_source fs ON b.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
                + "LEFT JOIN vw_funding_source_type fst ON fs.FUNDING_SOURCE_TYPE_ID=fst.FUNDING_SOURCE_TYPE_ID "
                + "LEFT JOIN vw_currency c ON b.CURRENCY_ID=c.CURRENCY_ID "
                + "LEFT JOIN ( "
                + "	SELECT " 
                + "		st.BUDGET_ID, "
                + "        SUM(IF(st.SHIPMENT_STATUS_ID IN (1), ((IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0))*s.CONVERSION_RATE_TO_USD),0)) `PLANNED_BUDGET`, "
                + "        SUM(IF(st.SHIPMENT_STATUS_ID IN (3,4,5,6,7,9), ((IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0))*s.CONVERSION_RATE_TO_USD),0)) `ORDERED_BUDGET` "
                + "FROM rm_shipment s "
                + "LEFT JOIN rm_shipment_trans st ON "
                + "	s.SHIPMENT_ID=st.SHIPMENT_ID "
                + "    AND s.MAX_VERSION_ID=st.VERSION_ID "
                + "    AND st.SHIPMENT_STATUS_ID!=8 "
                + "    AND st.ACCOUNT_FLAG=1 "
                + "    AND st.ACTIVE "
                + "    LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=s.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID "
                + "    LEFT JOIN rm_planning_unit pu ON pu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID "
                + "    WHERE ppu.ACTIVE AND pu.ACTIVE AND (:programIds='' OR FIND_IN_SET(s.PROGRAM_ID, :programIds)) "
                + "GROUP BY st.BUDGET_ID) stc ON stc.BUDGET_ID=b.BUDGET_ID "
                + "WHERE "
                + "	TRUE AND b.ACTIVE "
                + "     AND (:programIds='' OR FIND_IN_SET(bp.PROGRAM_ID, :programIds)) "
                + "     AND stc.BUDGET_ID IS NOT NULL "
                + "     AND (:fundingSourceIds='' OR FIND_IN_SET(b.FUNDING_SOURCE_ID, :fundingSourceIds)) "
                + "     AND (b.START_DATE BETWEEN :startDate AND :stopDate OR b.STOP_DATE BETWEEN :startDate AND :stopDate OR :startDate BETWEEN b.START_DATE AND b.STOP_DATE) ");
        this.aclService.addUserAclForRealm(sb, params, "b", curUser);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" GROUP BY b.BUDGET_ID");
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new BudgetReportOutputRowMapper());
    }

    // Report no 30 - Basic info
    @Override
    public List<StockStatusAcrossProductsOutput> getStockStatusAcrossProductsBasicInfo(StockStatusAcrossProductsInput ssap, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT "
                + "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,"
                + "	p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR` "
                + "FROM vw_program p "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "WHERE "
                + "rc.REALM_ID = :realmId AND ppu.ACTIVE AND pu.ACTIVE ");
        if (ssap.getTracerCategoryIds().length > 0) {
            sb.append(" AND fu.TRACER_CATEGORY_ID IN (" + ssap.getTracerCategoryIdsString() + ")");
        }
        sb.append(" AND ppu.ACTIVE AND p.ACTIVE ");
        if (ssap.getRealmCountryIds().length > 0) {
            sb.append(" AND p.REALM_COUNTRY_ID in (" + ssap.getRealmCountryIdsString() + ") ");
        }
        if (ssap.getProgramIds().length > 0) {
            sb.append(" AND p.PROGRAM_ID IN (" + ssap.getProgramIdsString() + ") ");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", ssap.getRealmId());
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new StockStatusAcrossProductsOutputResultsetExtractor());
    }

    // Report no 30 - Actual data
    @Override
    public StockStatusAcrossProductsForProgram getStockStatusAcrossProductsProgramData(int programId, int planningUnitId, Date dt, boolean useApprovedSupplyPlanOnly) {
        String sql = "CALL stockStatusForProgramPlanningUnit(:programId, -1, :planningUnitId, :dt, :approvedSupplyPlanOnly)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        params.put("dt", dt);
        params.put("approvedSupplyPlanOnly", useApprovedSupplyPlanOnly);
        return this.namedParameterJdbcTemplate.queryForObject(sql, params, new StockStatusAcrossProductsForProgramRowMapper());
    }

    // Report no 31
    @Override
    public List<ForecastErrorOutput> getForecastError(ForecastErrorInput fei, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", fei.getProgramId());
        params.put("versionId", fei.getVersionId());
        params.put("startDate", fei.getStartDate());
        params.put("stopDate", fei.getStopDate());
        params.put("viewBy", fei.getViewBy());
        params.put("unitId", fei.getUnitId());
        params.put("regionIds", fei.getRegionIdString());
        params.put("previousMonths", fei.getPreviousMonths());
        params.put("daysOfStockOut", fei.isDaysOfStockOut());
        params.put("equivalencyUnitId", fei.getEquivalencyUnitId());
        String sql = "CALL getForecastError(:programId, :versionId, :viewBy, :unitId, :startDate, :stopDate, :regionIds, :equivalencyUnitId, :previousMonths, :daysOfStockOut)";
        List<ForecastErrorOutput> feList = this.namedParameterJdbcTemplate.query(sql, params, new ForecastErrorOutputListResultSetExtractor(true));
        return feList;
    }

    // Report no 31
    @Override
    public List<ForecastErrorOutput> getForecastError(ForecastErrorInputNew fei, boolean getRegionalData, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", fei.getProgramId());
        params.put("versionId", fei.getVersionId());
        params.put("startDate", fei.getStartDate());
        params.put("stopDate", fei.getStopDate());
        params.put("viewBy", fei.getViewBy());
        params.put("unitIds", fei.getUnitIdString());
        params.put("regionIds", fei.getRegionIdString());
        params.put("previousMonths", fei.getPreviousMonths());
        params.put("daysOfStockOut", fei.isDaysOfStockOut());
        params.put("equivalencyUnitId", fei.getEquivalencyUnitId());
        String sql = "CALL getForecastErrorNew(:programId, :versionId, :viewBy, :unitIds, :startDate, :stopDate, :regionIds, :equivalencyUnitId, :previousMonths, :daysOfStockOut)";
        List<ForecastErrorOutput> feList = this.namedParameterJdbcTemplate.query(sql, params, new ForecastErrorOutputListResultSetExtractor(getRegionalData));
        return feList;
    }

    // Mod 2 Report 1 -- Monthly Forecast
    @Override
    public List<MonthlyForecastOutput> getMonthlyForecast(MonthlyForecastInput mf, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", mf.getProgramId());
        params.put("versionId", mf.getVersionId());
        params.put("startMonth", mf.getStartDate());
        params.put("stopMonth", mf.getStopDate());
        params.put("reportView", mf.getReportView());
        params.put("unitIds", mf.getUnitIdString());
        String sql = "CALL getMonthlyForecast(:programId, :versionId, :startMonth, :stopMonth, :reportView, :unitIds)";
        return this.namedParameterJdbcTemplate.query(sql, params, new MonthlyForecastOutputListResultSetExtractor(mf.isAggregateByYear()));
    }

    // Mod 2 Report 2 -- Forecast Summary
    @Override
    public List<ForecastSummaryOutput> getForecastSummary(ForecastSummaryInput fs, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", fs.getProgramId());
        params.put("versionId", fs.getVersionId());
        String sql = "CALL getForecastSummary(:programId, :versionId)";
        return this.namedParameterJdbcTemplate.query(sql, params, new ForecastSummaryOutputListResultSetExtractor(fs.getReportView()));
    }

}
