/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.BudgetReportInput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualInput;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.ExpiredStockInput;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastErrorInputNew;
import cc.altius.FASP.model.report.ForecastMetricsComparisionInput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionOutput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyInput;
import cc.altius.FASP.model.report.ForecastSummaryInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.InventoryTurnsInput;
import cc.altius.FASP.model.report.ManualJsonPushReportInput;
import cc.altius.FASP.model.report.MonthlyForecastInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProgramLeadTimesInput;
import cc.altius.FASP.model.report.ProgramProductCatalogInput;
import cc.altius.FASP.model.report.ShipmentDetailsInput;
import cc.altius.FASP.model.report.ShipmentGlobalDemandInput;
import cc.altius.FASP.model.report.ShipmentOverviewInput;
import cc.altius.FASP.model.report.ShipmentReportInput;
import cc.altius.FASP.model.report.StockAdjustmentReportInput;
import cc.altius.FASP.model.report.StockStatusAcrossProductsInput;
import cc.altius.FASP.model.report.StockStatusOverTimeInput;
import cc.altius.FASP.model.report.StockStatusForProgramInput;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
import cc.altius.FASP.model.report.StockStatusVerticalInput;
import cc.altius.FASP.model.report.StockStatusVerticalOutput;
import cc.altius.FASP.model.report.WarehouseByCountryInput;
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.service.IntegrationProgramService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.ReportService;
import cc.altius.FASP.service.UserService;
import cc.altius.FASP.utils.LogUtils;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ekta
 */
@RestController
@RequestMapping("/api/report/")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private IntegrationProgramService integrationProgramService;

    private final Logger logger = LoggerFactory.getLogger(ReportController.class);

    // Report no 1
    // Reports -> Program Catalog
    /**
     * <pre>
     * Sample JSON {"productCategoryId": -1, "tracerCategoryId": -1, "programId": 2028 }
     * -- Program Id must be a valid Program Id, cannot be -1 (Any)      *
     * -- TracerCategory and ProductCategory are used as Filters for the report and can be = -1 which means Any
     * -- Return the list of Program-Planning Units and their corresponding fields
     * </pre>
     *
     * @param ProgramProductCatalogInput
     * @param auth Authentication object from JWT
     * @return ProgramProductCatalogOutput
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/programProductCatalog")
    public ResponseEntity getProgramProductCatalog(@RequestBody ProgramProductCatalogInput ppc, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getProgramProductCatalog(ppc, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/programProductCatalog", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 2
    // Reports -> Consumption Reports -> Consumption (Forecast vs Actual)
    /**
     * <pre>
     * Sample JSON
     * {"programId":2535, "versionId":1, "startDate":"2019-01-01", "stopDate":"2019-12-01", "planningUnitId":778, "reportView":1}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- planningUnitId must be a valid PlanningUnitId
     * -- startDate and stopDate are the date range for which you want to run the report
     * -- reportView = 1 - Data is reported in terms of Planning Unit
     * -- reportView = 2 - Data is reported in terms of Forecasting Unit
     * </pre>
     *
     * @param ConsumptionForecastVsActualInput
     * @param auth Authentication object from JWT
     * @return ConsumptionForecastVsActualOutput
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/consumptionForecastVsActual")
    public ResponseEntity getConsumptionForecastVsActual(@RequestBody ConsumptionForecastVsActualInput ppc, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getConsumptionForecastVsActual(ppc, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/consumptionForecastVsActual", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 3
    // Reports -> Consumption Reports -> Consumption (Global)
    // Global Report
    /**
     * <pre>
     * Sample JSON
     * { "realmId": 1, "realmCountryIds": [5,51], "programIds": [2028,2029,2535], "planningUnitIds": [778,2692], "startDate": "2019-01-01", "stopDate": "2019-12-01", "reportView": 1, "useApprovedSupplyPlanOnly":0}
     * -- realmId must be a valid realm that you want to run this Global report for
     * -- RealmCountryIds is the list of Countries that you want to run the report for. Empty means all Countries
     * -- ProgramIds is the list of Programs that you want to run the report for. Empty means all Programs
     * -- PlanningUnitIds is the list of PlanningUnits that you want to run the report for. Empty means all Planning Units
     * -- startDate and stopDate are the range between which you want to run the report for`
     * -- reportView = 1 shows the Consumption in PlanningUnits
     * -- reportView = 2 shows the Consumption in ForecastingUnits
     * </pre>
     *
     * @param gci
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/globalConsumption")
    public ResponseEntity getGlobalConsumption(@RequestBody GlobalConsumptionInput gci, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getGlobalConsumption(gci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/globalConsumption", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 4
    // Reports -> Consumption Reports -> Forecast Error (Monthly)
    /**
     * <pre>
     * Sample JSON
     * { "programId": 2003, "versionId":2, "planningUnitId": 772, "startDate": "2020-01-01", "stopDate": "2020-05-01", "previousMonths": 6}
     * -- startDate and stopDate are the range that you want to run the report for
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- planningUnitIt must be a single Planning Unit cannot be multi-select or -1 for all
     * -- previousMonths is the number of months that the calculation should go in the past for (excluding the current month) calculation of WAPE formulae
     * -- current month is always included in the calculation
     * -- WAPE Formulae
     * -- ((Abs(actual consumption month 1-forecasted consumption month 1)+ Abs(actual consumption month 2-forecasted consumption month 2)+ Abs(actual consumption month 3-forecasted consumption month 3)+ Abs(actual consumption month 4-forecasted consumption month 4)+ Abs(actual consumption month 5-forecasted consumption month 5)+ Abs(actual consumption month 6-forecasted consumption month 6)) / (Sum of all actual consumption in the last 6 months))
     * *
     * </pre>
     *
     * @param fmi
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/forecastMetricsMonthly")
    public ResponseEntity getForecastMetricsMonthly(@RequestBody ForecastMetricsMonthlyInput fmi, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getForecastMetricsMonthly(fmi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/forecastMetricsMonthly", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 5
    // Global Report
    // Reports -> Consumption Reports -> Forecast Error (by Planning Unit)
    /**
     * <pre>
     * Sample JSON
     *{"realmId": 1, "realmCountryIds": ["5","51"],"programIds": ["2531","2544","2563","2564","2565"],"planningUnitIds": [],"tracerCategoryIds": [],"startDate": "2022-10-01","previousMonths": 3,"useApprovedSupplyPlanOnly": true,"curUser": 9}
     * -- realmId since it is a Global report need to include Realm
     * -- startDate - date that the report is to be run for
     * -- realmCountryIds list of countries that we need to run the report for
     * -- programIds is the list of programs that we need to run the report for
     * -- planningUnitIds is the list of planningUnits that we need to run the report for
     * -- previousMonths is the number of months that the calculation should go in the past for (excluding the current month) calculation of WAPE formulae
     * -- current month is always included in the calculation
     * -- only consider those months that have both a Forecasted and Actual consumption
     * -- WAPE Formulae
     * -- ((Abs(actual consumption month 1-forecasted consumption month 1)+ Abs(actual consumption month 2-forecasted consumption month 2)+ Abs(actual consumption month 3-forecasted consumption month 3)+ Abs(actual consumption month 4-forecasted consumption month 4)+ Abs(actual consumption month 5-forecasted consumption month 5)+ Abs(actual consumption month 6-forecasted consumption month 6)) / (Sum of all actual consumption in the last 6 months))
     * </pre>
     *
     * @param fmi
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/forecastMetricsComparision")
    public ResponseEntity getForecastMetricsComparision(@RequestBody ForecastMetricsComparisionInput fmi, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getForecastMetricsComparision(fmi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/forecastMetricsComparision", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Report no 7
    // Reports -> Inventory Reports -> Warehouse Capacity (by Program)
    /**
     * <pre>
     * Sample JSON
     * { "realmCountryId":2, "programIds":[3]}
     * -- RealmCountryId cannot be -1 it must be a valid RealmCountryId
     * -- ProgramIds are a list of the Programs that you want to run the report for
     * -- ProgramIds blank means you want to run it for all Programs
     * -- List of all the Regions for the Programs selected and their capacity
     * </pre>
     *
     * @param wci
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/warehouseCapacityReport")
    public ResponseEntity getWarehouseCapacityReport(@RequestBody WarehouseCapacityInput wci, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getWarehouseCapacityReport(wci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/warehouseCapacityReport", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Report no 
    // Reports -> Inventory Reports -> Warehouse Capcity (By Country)
    /**
     *
     */
    @JsonView(Views.ReportView.class)
    @PostMapping("/warehouseByCountry")
    public ResponseEntity getWarehouseByCountry(@RequestBody WarehouseByCountryInput wbc, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getWarehouseByCountryReport(wbc, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (DataAccessException e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 8
    // Reports -> Inventory Reports -> Cost of Inventory
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "dt":"2020-04-01", "includePlannedShipments":1}
     * -- ProgramId cannot be -1 (All) must be a valid ProgramId
     * -- Version Id can be -1 or a Valid Version Id. If it is -1 then the Most recent committed Version is automatically taken.
     * -- Dt is the date that you want to run the report for
     * -- Include Planned shipments = 1 means that Shipments that are in the Draft, Planned or Submitted stage will also be considered in the calculations
     * -- Include Planned shipments = 0 means that Shipments that are in the Draft, Planned or Submitted stage will not be considered in the calculations
     * -- Price per unit is taken from the ProgramPlanningUnit level
     * -- Cost = Closing inventory for that Planning Unit x Catalog Price
     * </pre>
     *
     * @param cii
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/costOfInventory")
    public ResponseEntity getCostOfInventory(@RequestBody CostOfInventoryInput cii, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getCostOfInventory(cii, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/costOfInventory", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 9
    // Reports -> Inventory Reports -> Inventory Turns
    /**
     * <pre>
     * Sample JSON
     * {"programIds":"2030,2034,2526,2527,2531,2533,2534,2535,2536,2537,2540,2541,2542,2543,2544,2545,2557,2558,2559,2560,2563,2564,2565,2570", "productCategoryIds":"1", "viewBy":1, "dt":"2022-04-01", "includePlannedShipments":1}
     * {"programIds":"2030,2034,2526,2527,2531,2533,2534,2535,2536,2537,2540,2541,2542,2543,2544,2545,2557,2558,2559,2560,2563,2564,2565,2570", "productCategoryIds":"0", "viewBy":2, "dt":"2022-04-01", "includePlannedShipments":1}
     * -- StartDate is the date that you want to run the report for
     * -- ViewBy = 1 View by RealmCountry, ViewBy = 2 View by ProductCategory
     * -- RealmCountryIds is the list of RealmCountryIds that should be included in the final output, cannot be empty you must pass the RealmCountryIds that you want to view it by
     * -- ProductCategoryIds is the list of ProductCategoryIds that should be included in the final output, cannot be empty if you want to select all pass '0'
     * -- Include Planned Shipments = 1 means that Shipments that are in the Planned, Draft, Submitted stages will also be considered in the report
     * -- Include Planned Shipments = 0 means that Shipments that are in the Planned, Draft, Submitted stages will not be considered in the report
     * -- Inventory Turns = Total Consumption for the last 12 months (including current month) / Avg Stock during that period
     * </pre>
     *
     * @param it
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/inventoryTurns")
    public ResponseEntity getInventoryTurns(@RequestBody InventoryTurnsInput it, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getInventoryTurns(it, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/inventoryTurns", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 11
    // Reports -> Inventory Reports -> Expiries
    /**
     * <pre>
     * Sample JSON
     * {"programId":2535, "versionId":1, "startDt":"2017-01-01", "stopDt":"2021-12-01", "includePlannedShipments":1}
     * -- programId cannot be -1 (All) it must be a valid ProgramId
     * -- versionId can be -1 or a valid VersionId for that Program. If it is -1 then the last committed Version is automatically taken.
     * -- StartDate is the start date that you want to run the report for
     * -- StopDate is the stop date that you want to run the report for
     * -- Include Planned Shipments = 1 menas that Shipments that are in the Planned stages will also be considered in the report
     * -- Include Planned Shipments = 0 means that Shipments that are in the Planned stages will not be considered in the report
     * </pre>
     *
     * @param it
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/expiredStock")
    public ResponseEntity getExpiredStock(@RequestBody ExpiredStockInput esi, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getExpiredStock(esi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/expiredStock", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 12
    // Reports -> Inventory Reports -> Stock Adjustment
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-10-01", "planningUnitIds":[152,157]}
     * -- VAR_PROGRAM_ID must be a valid Program cannot be All i.e. -1
     * -- VAR_VERSION_ID must be a valid Version for the PROGRAM_ID or can be -1 in which case it will default to the latest Version of the Program,
     * -- VAR_START_DATE AND VAR_STOP_DATE are the Date range between which the Stock Adjustment will be run. Only the month and year are considered while running the report
     * -- VAR_PLANNING_UNIT_IDS are the Quoted, Comma separated list of the Planning Unit Ids that you want to run the report for. If you want to run it for all Planning Units in the Program leave it empty
     * </pre>
     *
     * @param si
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/stockAdjustmentReport")
    public ResponseEntity getStockAdjustmentReport(@RequestBody StockAdjustmentReportInput si, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getStockAdjustmentReport(si, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/stockAdjustmentReport", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 13
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-10-01", "planningUnitIds":[152,157], "includePlannedShipments":1, "procurementAgentId":1}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- procurementAgentId can be a particular procurementAgentId or -1 for all
     * -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
     * -- planningUnitIds is provided as a list of planningUnitId's or empty for all
     * -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
     * -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
     * -- FreightCost and ProductCost are converted to USD
     * -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
     *
     * </pre>
     *
     * @param pari
     * @param auth
     * @return
     */
    // Report -> Shipment Reports -> Shipment Cost Details (Procurement Agent view)
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/procurementAgentShipmentReport")
    public ResponseEntity getProcurementAgentShipmentReport(@RequestBody ProcurementAgentShipmentReportInput pari, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getProcurementAgentShipmentReport(pari, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/procurementAgentShipmentReport", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 14
    // Report -> Shipment Reports -> Procurement Agent Lead Times
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "procurementAgentIds":[1,3], "planningUnitIds":[157,152]}
     * -- VAR_PROGRAM_ID is the program that you want to run the report for
     * -- VAR_PROCUREMENT_AGENT_IDS is the list of Procurement Agents you want to include in the report
     * -- VAR_PLANNING_UNIT_IDS is the list of Planning Units that you want to see the report for
     * </pre>
     *
     * @param plt
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/programLeadTimes")
    public ResponseEntity getProgramLeadTimes(@RequestBody ProgramLeadTimesInput plt, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            System.out.println("parameters ---" + plt);
//            System.out.println("program lead times ---" + this.reportService.getProgramLeadTimes(plt, curUser));
            return new ResponseEntity(this.reportService.getProgramLeadTimes(plt, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/programLeadTimes", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 15
    // Report -> Shipment Reports -> Shipment Cost Details (Funding Source view)
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-10-01", "planningUnitIds":[152,157], "includePlannedShipments":1, "fundingSourceId":3}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- fundingSourceId can be a particular fundingSource or -1 for all
     * -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
     * -- planningUnitIds is provided as a list of planningUnitId's or empty for all
     * -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
     * -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
     * -- FreightCost and ProductCost are converted to USD
     * -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
     * </pre>
     *
     * @param fsri
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/fundingSourceShipmentReport")
    public ResponseEntity getFundingSourceShipmentReport(@RequestBody FundingSourceShipmentReportInput fsri, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getFundingSourceShipmentReport(fsri, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/fundingSourceShipmentReport", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 16
    // Supply Planning -> Supply Plan Report
    /**
     * <pre>
     * Sample JSON
     * {"programId":2164, "versionId":1, "startDate":"2019-10-01", "stopDate":"2020-07-01", "unitIds":["152"], viewBy:1}
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-07-01", "planningUnitIds":["152"]}
     * </pre>
     *
     * @param ssv
     * @param auth
     * @return
     */
    // ActualConsumption = 0 -- Forecasted Consumption
    // ActualConsumption = 1 -- Actual Consumption
    // ActualConsumption = null -- No consumption data
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/stockStatusVertical")
    public ResponseEntity getStockStatusVertical(@RequestBody StockStatusVerticalInput ssv, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getStockStatusVertical(ssv, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/stockStatusVertical", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 17
    // Reports -> Stock Status -> Stock Status Over Time
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-07-01", "planningUnitIds":[152,157], "mosPast":3, "mosFuture":6}
     * </pre> -- mosPast is the number of months you want to go in the past to
     * calculate AMC -- mosFuture is the number of the months you want to go in
     * the future including the current month -- for e.g. If you are in Jan and
     * you give mosFuture = 6 it will take the AMC for Jan, Feb, Mar, Apr, May
     * and Jun
     *
     * @param ssot
     * @param auth
     * @return
     */
    // ActualConsumption = 0 -- Forecasted Consumption
    // ActualConsumption = 1 -- Actual Consumption
    // ActualConsumption = null -- No consumption data
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/stockStatusOverTime")
    public ResponseEntity getStockStatusOverTime(@RequestBody StockStatusOverTimeInput ssot, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getStockStatusOverTime(ssot, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/stockStatusOverTime", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 18
    // Reports -> Stock Status -> Stock Status Matrix
    /**
     * <pre>
     * Sample JSON
     * {"programId":2030, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-07-01", "planningUnitIds":[4115], "tracerCategoryIds":[21,28], "includePlannedShipments":1}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- planningUnitList is the list of Planning Units that you want to include in the report. If you want to include all the Planning Units in this Program leave it as empty
     * -- startDate and stopDate are the period for which you want to run the report
     * -- includePlannedShipments = 1 means that you want to include the shipments that are still in the Planned stage while running this report.
     * -- includePlannedShipments = 0 means that you want to exclude the shipments that are still in the Planned stage while running this report.
     * -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
     * -- Current month is always included in AMC
     * </pre>
     *
     * @param ssm
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/stockStatusMatrix")
    public ResponseEntity getStockStatusMatrix(@RequestBody StockStatusMatrixInput ssm, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getStockStatusMatrix(ssm), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/stockStatusMatrix", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Report no 19
    /**
     * <pre>
     * Sample JSON
     * {    "programId": 2030,    "versionId": 9,    "startDate": "2019-10-01",    "stopDate": "2020-07-01",    "planningUnitIds": [1354],    "fundingSourceIds": [1,4],    "budgetIds": [8,9],    "reportView": 1}
     * -- Only Month and Year will be considered for StartDate and StopDate
     * -- Only a single ProgramId can be selected
     * -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
     * -- PlanningUnitIds is the list of Planning Units you want to run the report for.
     * -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program
     * -- Report view 1 = Planning Units, 2 = Forecasting Units
     * </pre>
     *
     * @param sd
     * @param auth
     * @return
     */
    // Report -> Shipment Reports -> Shipment Details
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/shipmentDetails")
    public ResponseEntity getShipmentDetails(@RequestBody ShipmentDetailsInput sd, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getShipmentDetails(sd, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/shipmentDetails", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 20
    // Report -> Shipment Reports -> Shipments Overview
    // Global Report
    /**
     * <pre>
     * Sample JSON
     * {"curUser":20, "realmId":1,  "startDate":"2019-10-01", "stopDate":"2021-07-01", "shipmentStatusIds":[],"planningUnitIds":[], "fundingSourceIds":[], "procurementAgentIds":[], "useApprovedSupplyPlansOnly":0}
     * </pre>
     *
     * @param so
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/shipmentOverview")
    public ResponseEntity getShipmentOverview(@RequestBody ShipmentOverviewInput so, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getShipmentOverview(so, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/shipmentOverview", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 21
    // Report -> Shipment Reports -> Shipments (Global)
    // Global Report
    /**
     * <pre>
     * Sample JSON
     * {"curUser": 20,"realmId": 1,"realmCountryIds": [5,51],"programIds": [2028,2029,2535],"planningUnitId": 2692,"startDate": 2019-01-01","stopDate": "2019-12-01","fundingSourceProcurementAgentIds": [],"reportView": 1,"useApprovedSupplyPlanOnly": 0,"includePlannedShipments": 1}
     * </pre>
     *
     * @param sgd
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/shipmentGlobalDemand")
    public ResponseEntity getShipmentGlobalDemand(@RequestBody ShipmentGlobalDemandInput sgd, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getShipmentGlobalDemand(sgd, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/shipmentGlobalDemand", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 22
    // Report -> Shipment Reports -> Shipment Cost Overview
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-07-01", "planningUnitId":152, "procurementAgentId":-1, "fundingSourceId":-1, "shipmentStatusId":-1}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- reportBasedOn = 1 means the report will be run using startDate and stopDate based on Shipped Date
     * -- reportBasedOn = 1 means the report will be run using startDate and stopDate based on Delivered date if available or Expected Delivery Date
     * -- If ProcurementAgent has not been selected as yet in the Shipment, that Shipment will be excluded
     * </pre>
     *
     * @param asci
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/annualShipmentCost")
    public ResponseEntity getAnnualShipmentCost(@RequestBody AnnualShipmentCostInput asci, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getAnnualShipmentCost(asci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/annualShipmentCost", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 24
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-10-01", "planningUnitIds":[152,157], "includePlannedShipments":1}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
     * -- planningUnitIds is provided as a list of planningUnitId's or empty for all
     * -- includePlannedShipments = 1 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
     * -- includePlannedShipments = 0 means the report will include all shipments that are Active and not Cancelled
     * -- FreightCost and ProductCost are converted to USD
     * -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
     * </pre>
     *
     * @param fsri
     * @param auth
     * @return
     */
    // Report -> Shipment Reports -> Shipment Cost Details (Planning Unit view)
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/aggregateShipmentByProduct")
    public ResponseEntity getAggregateShipmentByProduct(@RequestBody ShipmentReportInput fsri, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getAggregateShipmentByProduct(fsri, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/aggregateShipmentByProduct", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 28
    // Reports -> Stock Status -> Stock Status Snapshot
    /**
     * <pre>
     * Sample JSON
     * {"programId":2028, "versionId":1, "dt":"2019-10-01", "includePlannedShipments":1, "tracerCategoryIds":[]}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- dt is the month for which you want to run the report
     * -- includePlannedShipments = 1 means that you want to include the shipments that are still in the Planned stage while running this report.
     * -- includePlannedShipments = 0 means that you want to exclude the shipments that are still in the Planned stage while running this report.
     * -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
     * -- Current month is always included in AMC
     * -- if a Month does not have Consumption then it is excluded from the AMC calculations
     * -- MinMonthsOfStock is Max of MinMonth of Stock taken from the Program-planning Unit and 3
     * -- MaxMonthsOfStock is Min of Min of MinMonthOfStock+ReorderFrequency and 15
     * </pre>
     *
     * @param sspi
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/stockStatusForProgram")
    public ResponseEntity getStockStatusForProgram(@RequestBody StockStatusForProgramInput sspi, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getStockStatusForProgram(sspi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/stockStatusForProgram", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 29
    // Report -> Shipment Reports -> Budget reports
    /**
     * Sample JSON {"programId":2028, "versionId":1, "startDate":"2019-01-01",
     * "stopDate":"2021-12-01", "fundingSourceIds":[], "shippingStatusIds":[]}
     *
     * @param br
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/budgetReport")
    public ResponseEntity getBudgetReport(@RequestBody BudgetReportInput br, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getBudgetReport(br, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/budgetReport", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 30
    // Reports -> Stock Status -> Stock Status Snapshot (Global)
    // Global Report
    /**
     * <pre>
     * Sample JSON
     * {    "curUser": 20,    "realmId": 1,    "realmCountryIds": [        5,        51    ],    "tracerCategoryIds":[], "dt":"2020-09-01"}
     * -- programId must be a single Program cannot be muti-program select or -1 for all programs
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- dt is the month for which you want to run the report
     * -- includePlannedShipments = 1 means that you want to include the shipments that are still in the Planned stage while running this report.
     * -- includePlannedShipments = 0 means that you want to exclude the shipments that are still in the Planned stage while running this report.
     * -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
     * -- Current month is always included in AMC
     * -- if a Month does not have Consumption then it is excluded from the AMC calculations
     * -- MinMonthsOfStock is Max of MinMonth of Stock taken from the Program-planning Unit and 3
     * -- MaxMonthsOfStock is Min of Min of MinMonthOfStock+ReorderFrequency and 15
     * </pre>
     *
     * @param sspi
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/stockStatusAcrossProducts")
    public ResponseEntity getStockStatusAcrossProducts(@RequestBody StockStatusAcrossProductsInput ssap, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getStockStatusAcrossProducts(ssap, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/stockStatusAcrossProducts", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 31
    // Reports -> Consumption Reports -> Forecast Error Report
    /**
     * <pre>
     * Sample JSON
     * {    "programId":2175,    "versionId":50,    "viewBy":1,    "unitId":1353,    "startDate":"2022-12-01",    "stopDate":"2024-06-01",    "equivalencyUnitId":0,    "regionIds":[    ],    "daysOfStockOut":1,     "previousMonths":5}
     * -- programId must be a valid single Supply Plan Program
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- viewBy 1 for PU, 2 for FU
     * -- unitId Either the PU or FU that you want the report for based on the viewBy
     * -- startDate and stopDate that you want to run the report for
     * -- equivalencyUnitId 0 if you do not want to display the report in EquivalencyUnits, or the value of the EquivalencyUnitId
     * -- regionIds list of region ids that the report should run for or blank for all
     * -- daysOfStockOut 1 if you want to consider the daysOfStockOut, 0 if you do not want to consider the daysOfStockOut
     * -- previousMonths the value of the previousMonths that you want to consider while calculating WAPE. Current month is always included. So if you want only for current month then pass 0
     * </pre>
     *
     * @param sspi
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/forecastError")
    public ResponseEntity getForecastError(@RequestBody ForecastErrorInput fei, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getForecastError(fei, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/forecastError", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 31 new
    // Reports -> Consumption Reports -> Forecast Error Report
    /**
     * <pre>
     * Sample JSON
     * {    "programId":2175,    "versionId":50,    "viewBy":1,    "unitId":1353,    "startDate":"2022-12-01",    "stopDate":"2024-06-01",    "equivalencyUnitId":0,    "regionIds":[    ],    "daysOfStockOut":1,     "previousMonths":5}
     * -- programId must be a valid single Supply Plan Program
     * -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
     * -- viewBy 1 for PU, 2 for FU
     * -- unitId Either the PU or FU that you want the report for based on the viewBy
     * -- startDate and stopDate that you want to run the report for
     * -- equivalencyUnitId 0 if you do not want to display the report in EquivalencyUnits, or the value of the EquivalencyUnitId
     * -- regionIds list of region ids that the report should run for or blank for all
     * -- daysOfStockOut 1 if you want to consider the daysOfStockOut, 0 if you do not want to consider the daysOfStockOut
     * -- previousMonths the value of the previousMonths that you want to consider while calculating WAPE. Current month is always included. So if you want only for current month then pass 0
     * -- If the view is EquivalencyUnit then all PU or FU selected must be from the same EU
     * -- If the view is FU then the PU's selected must be from the same FU
     * -- If the view is PU then you cannot multi-select
     * </pre>
     *
     * @param sspi
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/forecastErrorNew")
    public ResponseEntity getForecastError(@RequestBody ForecastErrorInputNew fei, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getForecastError(fei, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/forecastError", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Mod 2 Report 1
     * <pre>
     * -- Monthly Forecast
     * -- programId must be a single Program
     * -- versionId must be the actual version
     * -- startDate is the month from which you want the consumption data
     * -- stopDate is the month till which you want the consumption data
     * -- reportView 1 = PU view, 2 = FU View
     * -- unitIdsList -- List of the PU Id's or FU Id's that you want the report for
     * </pre>
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/monthlyForecast")
    public ResponseEntity getMonthlyForecast(@RequestBody MonthlyForecastInput mf, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getMonthlyForecast(mf, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/monthlyForecast", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Mod 2 Report 2
     * <pre>
     * -- Forecast Summary
     * -- programId must be a single Program
     * -- versionId must be the actual version
     * </pre>
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/forecastSummary")
    public ResponseEntity getForecastSummary(@RequestBody ForecastSummaryInput fs, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.reportService.getForecastSummary(fs, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api/report/forecastSummary", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Mod 1 Report no 32 API used to get the report for Manual Json push
     * <pre>
     * -- Manual Json push report
     * -- startDate is the date from which you want the data
     * -- stopDate is the date till which you want the data
     * -- realmCountryIds is provided as a list of realmCountry's to filter the report on or empty for all
     * -- programIds is provided as a list of program's to filter the report on or empty for all
     * </pre>
     *
     * @param startDate Start date that you want to report for
     * @param stopDate Stop date that you want to report for
     * @param realmCountryIds list of realmCountry's to filter the report on or
     * empty for all
     * @param programIds list of program's to filter the report on or empty for
     * all
     * @param auth
     * @return returns the list the Manual Json push based on the date variables
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/manualJson")
    @Operation(description = "API used to get the report for Manual Json push", summary = "API used to get the report for Manual Json push", tags = ("integrationProgram"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the report")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Integration Program")
    public ResponseEntity getManualJsonReport(@RequestBody ManualJsonPushReportInput mi, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.integrationProgramService.getManualJsonPushReport(mi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get report", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Mod 1 or Mod 2 Report UpdateProgramInfo
     * <pre>
     * -- programTypeId : 1 for SupplyPlan and 2 for Forecast
     * -- realmCountryId: -1 for all and value for that RealmCountry
     * -- active: 1 for Active, 0 for Disabled, -1 for Any
     * </pre>
     */
    @JsonView(Views.ReportView.class)
    @GetMapping(value = "/updateProgramInfo/programTypeId/{programTypeId}/realmCountryId/{realmCountryId}/active/{active}")
    @Operation(description = "API used to get the list of Programs that feeds the UpdateProgramInfo page", summary = "API used to get the list of Programs that feeds the UpdateProgramInfo page")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the report")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Program list")
    public ResponseEntity getUpdateProgramInfoList(@PathVariable(value = "programTypeId", required = true) int programTypeId, @PathVariable(value = "realmCountryId", required = true) int realmCountryId, @PathVariable(value = "active", required = true) int active, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getUpdateProgramInfoReport(programTypeId, realmCountryId, active, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get report", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
