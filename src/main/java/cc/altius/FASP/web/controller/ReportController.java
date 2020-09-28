/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.BudgetReportInput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualInput;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.ExpiredStockInput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionInput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
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
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/consumption/{realmId}/{programId}/{planningUnitId}/{startDate}/{endDate}")
    public ResponseEntity getConsumptionData(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, @PathVariable("planningUnitId") int planningUnitId, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getConsumptionData(realmId, programId, planningUnitId, startDate, endDate), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Report no 1
    /**
     * <pre>
     * Sample JSON {"productCategoryId": -1, "tracerCategoryId": -1, "programId": 3 }
     * -- Program Id must be a valid Program Id, cannot be -1 (Any)      *
     * -- TracerCategory and ProductCategory are used as Filters for the report and can be = -1 which means Any
     * -- Return the list of Program-Planning Units and their corresponding fields
     * </pre>
     */
    @RequestMapping(value = "/programProductCatalog")
    public ResponseEntity getProgramProductCatalog(@RequestBody ProgramProductCatalogInput ppc, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getProgramProductCatalog(ppc, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 2
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
     * @param ppc
     * @param auth
     * @return
     */
    @RequestMapping(value = "/consumptionForecastVsActual")
    public ResponseEntity getConsumptionForecastVsActual(@RequestBody ConsumptionForecastVsActualInput ppc, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
                return new ResponseEntity(this.reportService.getConsumptionForecastVsActual(ppc, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 3
    /**
     * <pre>
     * Sample JSON
     * { "realmId": 1, "realmCountryIds": [ 51], "programIds": [2535], "planningUnitIds": [778], "startDate": "2019-01-01", "stopDate": "2019-12-01", "reportView": 1}
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
    @RequestMapping(value = "/globalConsumption")
    public ResponseEntity getGlobalConsumption(@RequestBody GlobalConsumptionInput gci, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getGlobalConsumption(gci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 4
    /**
     * <pre>
     * Sample JSON
     * { "startDate":"2019-10-01", "stopDate":"2020-07-01", "programId":3, "versionId":2, "planningUnitId":152, "previousMonths":5}
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
    @RequestMapping(value = "/forecastMetricsMonthly")
    public ResponseEntity getForecastMetricsMonthls(@RequestBody ForecastMetricsMonthlyInput fmi, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getForecastMetricsMonthly(fmi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 5
    /**
     * <pre>
     * Sample JSON
     * { "realmId":1, "realmCountryIds":[5,51], "programIds":[2028,2535], "planningUnitIds":[], "startDate":"2019-11-01", "previousMonths":5}
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
    @RequestMapping(value = "/forecastMetricsComparision")
    public ResponseEntity getForecastMetricsComparision(@RequestBody ForecastMetricsComparisionInput fmi, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getForecastMetricsComparision(fmi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Report no 7
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
    @RequestMapping(value = "/warehouseCapacityReport")
    public ResponseEntity getwarehouseCapacityReport(@RequestBody WarehouseCapacityInput wci, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getWarehouseCapacityReport(wci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 8
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
    @RequestMapping(value = "/costOfInventory")
    public ResponseEntity getCostOfInventory(@RequestBody CostOfInventoryInput cii, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getCostOfInventory(cii, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 9
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "dt":"2020-04-01", "includePlannedShipments":1}
     * -- programId cannot be -1 (All) it must be a valid ProgramId
     * -- versionId can be -1 or a valid VersionId for that Program. If it is -1 then the last committed Version is automatically taken.
     * -- StartDate is the date that you want to run the report for
     * -- Include Planned Shipments = 1 menas that Shipments that are in the Planned, Draft, Submitted stages will also be considered in the report
     * -- Include Planned Shipments = 0 means that Shipments that are in the Planned, Draft, Submitted stages will not be considered in the report
     * -- Inventory Turns = Total Consumption for the last 12 months (including current month) / Avg Stock during that period
     * </pre>
     *
     * @param it
     * @param auth
     * @return
     */
    @RequestMapping(value = "/inventoryTurns")
    public ResponseEntity getInventoryTurns(@RequestBody CostOfInventoryInput it, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getInventoryTurns(it, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Report no 1-
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
    @RequestMapping(value = "/expiredStock")
    public ResponseEntity getExpiredStock(@RequestBody ExpiredStockInput esi, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getExpiredStock(esi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 12
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
    @RequestMapping(value = "/stockAdjustmentReport")
    public ResponseEntity getStockAdjustmentReport(@RequestBody StockAdjustmentReportInput si, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockAdjustmentReport(si, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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
    @RequestMapping(value = "/procurementAgentShipmentReport")
    public ResponseEntity getProcurementAgentShipmentReport(@RequestBody ProcurementAgentShipmentReportInput pari, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getProcurementAgentShipmentReport(pari, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 14
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
    @RequestMapping(value = "/programLeadTimes")
    public ResponseEntity getProgramLeadTimes(@RequestBody ProgramLeadTimesInput plt, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getProgramLeadTimes(plt, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 15
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
    @RequestMapping(value = "/fundingSourceShipmentReport")
    public ResponseEntity getFundingSourceShipmentReport(@RequestBody FundingSourceShipmentReportInput fsri, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getFundingSourceShipmentReport(fsri, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 16
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-07-01", "planningUnitId":152}
     * </pre>
     *
     * @param ssv
     * @param auth
     * @return
     */
    // ActualConsumption = 0 -- Forecasted Consumption
    // ActualConsumption = 1 -- Actual Consumption
    // ActualConsumption = null -- No consumption data
    @RequestMapping(value = "/stockStatusVertical")
    public ResponseEntity getStockStatusVertical(@RequestBody StockStatusVerticalInput ssv, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusVertical(ssv, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 17
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
    @RequestMapping(value = "/stockStatusOverTime")
    public ResponseEntity getStockStatusOverTime(@RequestBody StockStatusOverTimeInput ssot, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusOverTime(ssot, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 18
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-07-01", "planningUnitIds":[152,157], "includePlannedShipments":1}
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
    @RequestMapping(value = "/stockStatusMatrix")
    public ResponseEntity getStockStatusMatrix(@RequestBody StockStatusMatrixInput ssm, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusMatrix(ssm), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Report no 19
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "startDate":"2019-10-01", "stopDate":"2020-07-01", "includePlannedShipments":1}
     * -- Only Month and Year will be considered for StartDate and StopDate
     * -- Only a single ProgramId can be selected
     * -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
     * -- PlanningUnitIds is the list of Planning Units you want to run the report for.
     * -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program
     * </pre>
     *
     * @param sd
     * @param auth
     * @return
     */
    @RequestMapping(value = "/shipmentDetails")
    public ResponseEntity getShipmentDetails(@RequestBody ShipmentDetailsInput sd, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getShipmentDetails(sd, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 20
    /**
     * <pre>
     * Sample JSON
     * {"realmId":1,  "startDate":"2019-10-01", "stopDate":"2020-07-01", "planningUnitIds":[158], "fundingSourceIds":[], "procurementAgentIds":[]}
     * </pre>
     *
     * @param so
     * @param auth
     * @return
     */
    @RequestMapping(value = "/shipmentOverview")
    public ResponseEntity getShipmentOverview(@RequestBody ShipmentOverviewInput so, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getShipmentOverview(so, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 21
    /**
     * <pre>
     * Sample JSON
     * {"realmId":1,  "startDate":"2019-10-01", "stopDate":"2020-07-01", "realmCountryIds":[1,2,3], "planningUnitId":158, "fundingSourceIds":[], "fundingSourceProcurementAgentIds":[], "reportView":1}
     * </pre>
     *
     * @param sgd
     * @param auth
     * @return
     */
    @RequestMapping(value = "/shipmentGlobalDemand")
    public ResponseEntity getShipmentGlobalDemand(@RequestBody ShipmentGlobalDemandInput sgd, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getShipmentGlobalDemand(sgd, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 22
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
    @RequestMapping(value = "/annualShipmentCost")
    public ResponseEntity getAnnualShipmentCost(@RequestBody AnnualShipmentCostInput asci, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getAnnualShipmentCost(asci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
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
    @RequestMapping(value = "/aggregateShipmentByProduct")
    public ResponseEntity getAggregateShipmentByProduct(@RequestBody ShipmentReportInput fsri, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getAggregateShipmentByProduct(fsri, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 28
    /**
     * <pre>
     * Sample JSON
     * {"programId":3, "versionId":2, "dt":"2019-10-01", "includePlannedShipments":1}
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
    @RequestMapping(value = "/stockStatusForProgram")
    public ResponseEntity getStockStatusForProgram(@RequestBody StockStatusForProgramInput sspi, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusForProgram(sspi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 29
    /**
     * Sample JSON 
     * {"programId":3, "versionId":2}
     * @param br
     * @param auth
     * @return 
     */
    @RequestMapping(value = "/budgetReport")
    public ResponseEntity getBudgetReport(@RequestBody BudgetReportInput br, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getBudgetReport(br, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Report no 30
    /**
     * <pre>
     * Sample JSON
     * {"realmId": "1",    "dt": "2020-09-01",    "tracerCategoryId":-1,    "realmCountryIds":[]}
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
    @RequestMapping(value = "/stockStatusAcrossProducts")
    public ResponseEntity getStockStatusAcrossProducts(@RequestBody StockStatusAcrossProductsInput ssap, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusAcrossProducts(ssap, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
