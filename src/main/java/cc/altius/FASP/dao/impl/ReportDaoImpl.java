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
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.CostOfInventoryOutput;
import cc.altius.FASP.model.report.CostOfInventoryRowMapper;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastErrorOutput;
import cc.altius.FASP.model.report.ForecastErrorOutputRowMapper;
import cc.altius.FASP.model.report.ForecastMetricsInput;
import cc.altius.FASP.model.report.ForecastMetricsOutput;
import cc.altius.FASP.model.report.ForecastMetricsOutputRowMapper;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutputRowMapper;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.GlobalConsumptionOutputRowMapper;
import cc.altius.FASP.model.report.InventoryTurnsOutput;
import cc.altius.FASP.model.report.InventoryTurnsOutputRowMapper;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutputRowMapper;
import cc.altius.FASP.model.report.ProgramAndPlanningUnit;
import cc.altius.FASP.model.report.StockAdjustmentReportInput;
import cc.altius.FASP.model.report.StockAdjustmentReportOutput;
import cc.altius.FASP.model.report.StockOverTimeInput;
import cc.altius.FASP.model.report.StockOverTimeOutput;
import cc.altius.FASP.model.report.StockOverTimeOutputRowMapper;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
import cc.altius.FASP.model.report.StockStatusMatrixOutput;
import cc.altius.FASP.model.report.StockStatusMatrixOutputRowMapper;
import cc.altius.FASP.model.rowMapper.StockAdjustmentReportOutputRowMapper;
import cc.altius.FASP.utils.LogUtils;
import java.text.SimpleDateFormat;
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

    @Override
    public List<Map<String, Object>> getConsumptionData(int realmId, int programId, int planningUnitId, String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();

        String sql = "	SELECT \n"
                + "		DATE_FORMAT(cons.`CONSUMPTION_DATE`,'%m-%Y') consumption_date,SUM(IF(cons.`ACTUAL_FLAG`=1,cons.`CONSUMPTION_QTY`,0)) Actual,SUM(IF(cons.`ACTUAL_FLAG`=0,cons.`CONSUMPTION_QTY`,0)) forcast	FROM  rm_consumption_trans cons \n"
                + "	LEFT JOIN rm_consumption con  ON con.CONSUMPTION_ID=cons.CONSUMPTION_ID\n"
                + "	LEFT JOIN rm_program p ON con.PROGRAM_ID=p.PROGRAM_ID\n"
                + "	LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=p.`REALM_COUNTRY_ID`\n"
                + "	LEFT JOIN rm_region r ON cons.REGION_ID=r.REGION_ID\n"
                + "	LEFT JOIN rm_planning_unit pu ON cons.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID\n"
                + "	LEFT JOIN rm_forecasting_unit fu ON fu.`FORECASTING_UNIT_ID`=pu.`FORECASTING_UNIT_ID`\n"
                + "	LEFT JOIN rm_data_source ds ON cons.DATA_SOURCE_ID=ds.DATA_SOURCE_ID\n"
                + "	WHERE  1";
        //+ "rc.`REALM_ID`=:realmId\n";
        //params.put("realmId", realmId);
        if (programId > 1) {
            sql += "	AND con.`PROGRAM_ID`=:programId";
            params.put("programId", programId);
        }
        // if (planningUnitId != 0) {
        sql += "	AND pu.`PLANNING_UNIT_ID`=:planningUnitId";
        params.put("planningUnitId", planningUnitId);
        // }
        sql += " And cons.`CONSUMPTION_DATE`between :startDate and :endDate	GROUP BY DATE_FORMAT(cons.`CONSUMPTION_DATE`,'%m-%Y') \n"
                + "    ORDER BY DATE_FORMAT(cons.`CONSUMPTION_DATE`,'%Y-%m')";
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("planningUnitId", planningUnitId);
        return this.namedParameterJdbcTemplate.queryForList(sql, params);
    }

    @Override
    public List<StockStatusMatrixOutput> getStockStatusMatrix(StockStatusMatrixInput ssm) {
        String sql = "CALL stockStatusMatrix(:programId, :versionId, :planningUnitIds, :startDate, :stopDate, :includePlannedShipments)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("programId", ssm.getProgramId());
        params.put("versionId", ssm.getVersionId());
        params.put("planningUnitIds", ssm.getPlanningUnitIdsString());
        params.put("startDate", ssm.getStartDate());
        params.put("stopDate", ssm.getStopDate());
        params.put("includePlannedShipments", ssm.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query(sql, params, new StockStatusMatrixOutputRowMapper());
    }

    @Override
    public List<ForecastErrorOutput> getForecastError(ForecastErrorInput fei, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", fei.getStartDate());
        params.put("stopDate", fei.getStopDate());
        params.put("realmCountryId", fei.getRealmCountryId());
        params.put("planningUnitId", fei.getPlanningUnitId());
        return this.namedParameterJdbcTemplate.query("CALL forecastErrorForPlanningUnit(:realmCountryId, :planningUnitId,:startDate,:stopDate, 2)", params, new ForecastErrorOutputRowMapper());

    }

    @Override
    public List<ForecastMetricsOutput> getForecastMetrics(ForecastMetricsInput fmi, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", fmi.getStartDate());
        params.put("previousMonths", fmi.getPreviousMonths());
        params.put("realmCountryId", fmi.getRealmCountryIdString());
        params.put("planningUnitId", fmi.getPlanningUnitIdString());
        params.put("programId", fmi.getProgramIdString());
        return this.namedParameterJdbcTemplate.query("CALL forecastMetrics(:realmCountryId, :programId, :planningUnitId, :startDate, :previousMonths)", params, new ForecastMetricsOutputRowMapper());
    }

    @Override
    public List<GlobalConsumptionOutput> getGlobalConsumption(GlobalConsumptionInput gci, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", gci.getStartDate());
        params.put("stopDate", gci.getStopDate());
        params.put("realmCountryIds", gci.getRealmCountryIdString());
        params.put("programIds", gci.getProgramIdString());
        params.put("planningUnitIds", gci.getPlanningUnitIdString());
        return this.namedParameterJdbcTemplate.query("CALL globalConsumption(:realmCountryIds,:programIds,:planningUnitIds,:startDate,:stopDate)", params, new GlobalConsumptionOutputRowMapper());
    }

    @Override
    public List<List<StockOverTimeOutput>> getStockOverTime(StockOverTimeInput soti, CustomUserDetails curUser) {
        List<List<StockOverTimeOutput>> sList = new LinkedList<>();
        Map<String, Object> params = new HashMap<>();
        String sqlString = "CALL stockOverTime(:programId, :planningUnitId, :startDate, :stopDate, :mosPast, :mosFuture)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ProgramAndPlanningUnit pp : soti.getProgramAndPlanningUnitList()) {
            params.clear();
            params.put("startDate", sdf.format(soti.getStartDate()));
            params.put("stopDate", sdf.format(soti.getStopDate()));
            params.put("mosFuture", soti.getMosFuture());
            params.put("mosPast", soti.getMosPast());
            params.put("programId", pp.getProgramId());
            params.put("planningUnitId", pp.getPlanningUnitId());
            List<StockOverTimeOutput> lst = this.namedParameterJdbcTemplate.query(sqlString, params, new StockOverTimeOutputRowMapper());
            if (lst.size() > 0) {
                sList.add(lst);
            }
        }
        return sList;
    }

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
        params.put("shipmentStatusId", asci.getFundingSourceId());
        params.put("reportBasedOn", asci.getReportBasedOn());
        return this.namedParameterJdbcTemplate.query("CALL annualShipmentCost(:programId, :versionId, :procurementAgentId, :planningUnitId, :fundingSourceId, :shipmentStatusId, :startDate, :stopDate, :reportBasedOn)", params, new AnnualShipmentCostOutputRowMapper());
    }

    @Override
    public List<CostOfInventoryOutput> getCostOfInventory(CostOfInventoryInput cii, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", cii.getProgramId());
        params.put("versionId", cii.getVersionId());
        params.put("dt", cii.getDt());
        params.put("includePlannedShipments", cii.isIncludePlannedShipments());
        return this.namedParameterJdbcTemplate.query("CALL costOfInventory(:programId, :versionId, :dt, :includePlannedShipments)", params, new CostOfInventoryRowMapper());
    }

    @Override
    public List<InventoryTurnsOutput> getInventoryTurns(CostOfInventoryInput it, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", it.getProgramId());
        params.put("versionId", it.getVersionId());
        params.put("dt", it.getDt());
        params.put("includePlannedShipments", it.isIncludePlannedShipments());
        String sql = "CALL inventoryTurns(:programId, :versionId, :dt, :includePlannedShipments)";
        System.out.println(LogUtils.buildStringForLog(sql, params));
        return this.namedParameterJdbcTemplate.query(sql, params, new InventoryTurnsOutputRowMapper());
    }

    @Override
    public List<StockAdjustmentReportOutput> getStockAdjustmentReport(StockAdjustmentReportInput si, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", si.getProgramId());
        params.put("versionId", si.getVersionId());
        params.put("startDate", si.getStartDate());
        params.put("stopDate", si.getStopDate());
        params.put("planningUnitIds", si.getPlanningUnitIdString());
        String sql = "CALL stockAdjustmentReport(:programId, :versionId, :startDate, :stopDate, :planningUnitIds)";
        System.out.println(LogUtils.buildStringForLog(sql, params));
        return this.namedParameterJdbcTemplate.query(sql, params, new StockAdjustmentReportOutputRowMapper());
    }

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

}
