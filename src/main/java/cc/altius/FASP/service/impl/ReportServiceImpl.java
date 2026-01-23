/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.EquivalencyUnitDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.dao.RealmCountryDao;
import cc.altius.FASP.dao.ReportDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.AnnualShipmentCostOutput;
import cc.altius.FASP.model.report.BudgetReportInput;
import cc.altius.FASP.model.report.BudgetReportOutput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualInput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualOutput;
import cc.altius.FASP.model.report.ConsumptionInfo;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.CostOfInventoryOutput;
import cc.altius.FASP.model.report.DropdownsForStockStatusVerticalOutput;
import cc.altius.FASP.model.report.ExpiredStockInput;
import cc.altius.FASP.model.report.ExpiredStockOutput;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastErrorInputNew;
import cc.altius.FASP.model.report.ForecastErrorOutput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionInput;
import cc.altius.FASP.model.report.ForecastMetricsComparisionOutput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyInput;
import cc.altius.FASP.model.report.ForecastMetricsMonthlyOutput;
import cc.altius.FASP.model.report.ForecastSummaryInput;
import cc.altius.FASP.model.report.ForecastSummaryOutput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportOutput;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.report.GlobalConsumptionOutput;
import cc.altius.FASP.model.report.InventoryInfo;
import cc.altius.FASP.model.report.InventoryTurnsInput;
import cc.altius.FASP.model.report.InventoryTurnsOutput;
import cc.altius.FASP.model.report.MonthlyForecastInput;
import cc.altius.FASP.model.report.MonthlyForecastOutput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportOutput;
import cc.altius.FASP.model.report.ProgramAndPlanningUnit;
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
import cc.altius.FASP.model.report.StockStatusAcrossProductsForProgram;
import cc.altius.FASP.model.report.StockStatusAcrossProductsInput;
import cc.altius.FASP.model.report.StockStatusAcrossProductsOutput;
import cc.altius.FASP.model.report.StockStatusOverTimeInput;
import cc.altius.FASP.model.report.StockStatusOverTimeOutput;
import cc.altius.FASP.model.report.StockStatusForProgramInput;
import cc.altius.FASP.model.report.StockStatusForProgramOutput;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
import cc.altius.FASP.model.report.StockStatusMatrixOutput;
import cc.altius.FASP.model.report.StockStatusVerticalAggregateOutput;
import cc.altius.FASP.model.report.StockStatusVerticalDropdownInput;
import cc.altius.FASP.model.report.StockStatusVerticalIndividualOutput;
import cc.altius.FASP.model.report.StockStatusVerticalInput;
import cc.altius.FASP.model.report.WarehouseByCountryInput;
import cc.altius.FASP.model.report.WarehouseByCountryOutput;
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.model.report.WarehouseCapacityOutput;
import cc.altius.FASP.service.ReportService;
import cc.altius.FASP.utils.ArrayUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author ekta
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDao reportDao;
    @Autowired
    ProgramCommonDao programCommonDao;
    @Autowired
    RealmCountryDao realmCountryDao;
    @Autowired
    ProgramDao programDao;
    @Autowired
    EquivalencyUnitDao equivalencyUnitDao;

    @Override
    public List<StockStatusMatrixOutput> getStockStatusMatrix(StockStatusMatrixInput ssm, CustomUserDetails curUser) throws AccessControlFailedException {
        if (ssm.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(ssm.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getStockStatusMatrix(ssm);
    }

    // Report no 1
    @Override
    public List<ProgramProductCatalogOutput> getProgramProductCatalog(ProgramProductCatalogInput ppc, CustomUserDetails curUser) throws AccessControlFailedException {
        if (ppc.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(ppc.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getProgramProductCatalog(ppc, curUser);
    }

    //Report no 2
    @Override
    public List<ConsumptionForecastVsActualOutput> getConsumptionForecastVsActual(ConsumptionForecastVsActualInput cfa, CustomUserDetails curUser) throws AccessControlFailedException {
        if (cfa.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(cfa.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getConsumptionForecastVsActual(cfa, curUser);
    }

    // Report no 3
    @Override
    public List<GlobalConsumptionOutput> getGlobalConsumption(GlobalConsumptionInput gci, CustomUserDetails curUser) throws AccessControlFailedException {
        if (gci.getProgramIds() != null) {
            for (String program : gci.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getGlobalConsumption(gci, curUser);
    }

    // Report no 4
    @Override
    public List<ForecastMetricsMonthlyOutput> getForecastMetricsMonthly(ForecastMetricsMonthlyInput fmi, CustomUserDetails curUser) throws AccessControlFailedException {
        if (fmi.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(fmi.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getForecastMetricsMonthly(fmi, curUser);
    }

    // Report no 5
    @Override
    public List<ForecastMetricsComparisionOutput> getForecastMetricsComparision(ForecastMetricsComparisionInput fmi, CustomUserDetails curUser) throws AccessControlFailedException {
        if (fmi.getProgramIds() != null) {
            for (String program : fmi.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getForecastMetricsComparision(fmi, curUser);
    }

    @Override
    public List<StockStatusOverTimeOutput> getStockStatusOverTime(StockStatusOverTimeInput ssot, CustomUserDetails curUser) throws AccessControlFailedException {
        if (ssot.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(ssot.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getStockStatusOverTime(ssot, curUser);
    }

    @Override
    public List<AnnualShipmentCostOutput> getAnnualShipmentCost(AnnualShipmentCostInput asci, CustomUserDetails curUser) throws AccessControlFailedException {
        if (asci.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(asci.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getAnnualShipmentCost(asci, curUser);
    }

    @Override
    public List<CostOfInventoryOutput> getCostOfInventory(CostOfInventoryInput cii, CustomUserDetails curUser) throws AccessControlFailedException {
        if (cii.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(cii.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getCostOfInventory(cii, curUser);
    }

    @Override
    public List<InventoryTurnsOutput> getInventoryTurns(InventoryTurnsInput it, CustomUserDetails curUser) throws AccessControlFailedException {
        if (it.getProgramIds() != null) {
            for (String program : it.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getInventoryTurns(it, curUser);
    }

    @Override
    public List<ExpiredStockOutput> getExpiredStock(ExpiredStockInput esi, CustomUserDetails curUser) throws AccessControlFailedException {
        if (esi.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(esi.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getExpiredStock(esi, curUser);
    }

    @Override
    public List<StockAdjustmentReportOutput> getStockAdjustmentReport(StockAdjustmentReportInput si, CustomUserDetails curUser) throws AccessControlFailedException {
        if (si.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(si.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getStockAdjustmentReport(si, curUser);
    }

    @Override
    public List<ProcurementAgentShipmentReportOutput> getProcurementAgentShipmentReport(ProcurementAgentShipmentReportInput pari, CustomUserDetails curUser) throws AccessControlFailedException {
        if (pari.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(pari.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getProcurementAgentShipmentReport(pari, curUser);
    }

    @Override
    public List<FundingSourceShipmentReportOutput> getFundingSourceShipmentReport(FundingSourceShipmentReportInput fsri, CustomUserDetails curUser) throws AccessControlFailedException {
        if (fsri.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(fsri.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getFundingSourceShipmentReport(fsri, curUser);
    }

    @Override
    public List<ShipmentReportOutput> getAggregateShipmentByProduct(ShipmentReportInput sri, CustomUserDetails curUser) throws AccessControlFailedException {
        if (sri.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(sri.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getAggregateShipmentByProduct(sri, curUser);
    }

    @Override
    public List<WarehouseCapacityOutput> getWarehouseCapacityReport(WarehouseCapacityInput wci, CustomUserDetails curUser) throws AccessControlFailedException {
        if (wci.getProgramIds() != null) {
            for (String program : wci.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getWarehouseCapacityReport(wci, curUser);
    }

    @Override
    public List<WarehouseByCountryOutput> getWarehouseByCountryReport(WarehouseByCountryInput wci, CustomUserDetails curUser) throws AccessControlFailedException {
        if (wci.getRealmCountryIds() != null) {
            for (String realmCountry : wci.getRealmCountryIds()) {
                try {
                    if (this.realmCountryDao.getRealmCountryById(Integer.parseInt(realmCountry), curUser) == null) {
                        throw new AccessControlFailedException();
                    }
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getWarehouseByCountryReport(wci, curUser);
    }

    @Override
    public List<StockStatusForProgramOutput> getStockStatusForProgram(StockStatusForProgramInput sspi, CustomUserDetails curUser) throws AccessControlFailedException {
        if (sspi.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(sspi.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getStockStatusForProgram(sspi, curUser);
    }

    @Override
    public List<ProgramLeadTimesOutput> getProgramLeadTimes(ProgramLeadTimesInput plt, CustomUserDetails curUser) throws AccessControlFailedException {
        if (plt.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(plt.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getProgramLeadTimes(plt, curUser);
    }

    @Override
    public Map<String, StockStatusVerticalIndividualOutput> getStockStatusVerticalIndividual(StockStatusVerticalInput ssv, CustomUserDetails curUser) throws AccessControlFailedException {
        Map<String, StockStatusVerticalIndividualOutput> map = new HashMap<>();
        for (int programId : ssv.getProgramIds()) {
            if (programId != 0) {
                try {
                    this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        for (int programId : ssv.getProgramIds()) {
            for (int reportingUnitId : ssv.getReportingUnitIds()) {
                if (this.reportDao.checkIfExistsRuForProgram(programId, reportingUnitId, ssv.getViewBy())) {
                    SimpleCodeObject program = this.programCommonDao.getSimpleSupplyPlanProgramById(programId, curUser);
                    ssv.setProgramId(programId);
                    ssv.setReportingUnitId(reportingUnitId);
                    StockStatusVerticalIndividualOutput ssvo = this.reportDao.getStockStatusVerticalIndividual(ssv, curUser);
                    List<ConsumptionInfo> cList = this.reportDao.getConsumptionInfoForSSVIndividualReport(ssv, curUser);
                    cList.forEach(c -> {
                        int idx = ssvo.getConsumptionInfo().indexOf(c);
                        if (idx == -1) {
                            ssvo.getConsumptionInfo().add(c);
                        }
                    });

                    List<InventoryInfo> iList = this.reportDao.getInventoryInfoForSSVIndividualReport(ssv, curUser);
                    iList.forEach(i -> {
                        int idx = ssvo.getInventoryInfo().indexOf(i);
                        if (idx == -1) {
                            ssvo.getInventoryInfo().add(i);
                        }
                    });
                    map.put(program.getId() + "~" + reportingUnitId, ssvo);
                }
            }
        }
        return map;
    }

    @Override
    public List<StockStatusVerticalAggregateOutput> getStockStatusVerticalAggregate(StockStatusVerticalInput ssv, CustomUserDetails curUser) throws AccessControlFailedException {
        if (ssv.getProgramIds() != null) {
            for (int program : ssv.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(program, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        final List<StockStatusVerticalAggregateOutput> ssvoList = new LinkedList<>();
        // Run as Aggregate directly
        ssvoList.addAll(this.reportDao.getStockStatusVerticalAggregate(ssv, curUser));
        List<ConsumptionInfo> cList = this.reportDao.getConsumptionInfoForSSVAggregateReport(ssv, curUser);
        cList.forEach(c -> {
            int idx = ssvoList.indexOf(new StockStatusVerticalAggregateOutput(c.getConsumptionDate()));
            if (idx != -1) {
                ssvoList.get(idx).getConsumptionInfo().add(c);
            }
        });

        List<InventoryInfo> iList = this.reportDao.getInventoryInfoForSSVAggregateReport(ssv, curUser);
        iList.forEach(i -> {
            int idx = ssvoList.indexOf(new StockStatusVerticalAggregateOutput(i.getInventoryDate()));
            if (idx != -1) {
                ssvoList.get(idx).getInventoryInfo().add(i);
            }
        });
        return ssvoList;
    }

    @Override
    public DropdownsForStockStatusVerticalOutput getDropdownsForStockStatusVertical(StockStatusVerticalDropdownInput ssvdi, CustomUserDetails curUser) throws AccessControlFailedException {
        if (ssvdi.getProgramIds() != null) {
            for (String program : ssvdi.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        DropdownsForStockStatusVerticalOutput dd = new DropdownsForStockStatusVerticalOutput();
        dd.setPlanningUnitList(this.programDao.getSimplePlanningUnitAndForecastingUnits(ssvdi, curUser));
        dd.setRealmCountryPlanningUnitList(this.realmCountryDao.getSimpleRealmCountryPlanningUnits(ssvdi, curUser));
        if (ssvdi.getProgramIds() != null && ssvdi.getProgramIds().length == 1) {
            dd.setEquivalencyUnitList(this.equivalencyUnitDao.getSimpleEquivalencyUnits(ArrayUtils.convertArrayToString(ssvdi.getProgramIds()), false, curUser));
        } else {
            dd.setEquivalencyUnitList(this.equivalencyUnitDao.getSimpleEquivalencyUnits(ArrayUtils.convertArrayToString(ssvdi.getProgramIds()), true, curUser));
        }
        return dd;
    }

    @Override
    public List<ProgramAndPlanningUnit> getPlanningUnitListForStockStatusVerticalAggregate(StockStatusVerticalInput ssvi, CustomUserDetails curUser) {
        return this.reportDao.getPlanningUnitListForStockStatusVerticalAggregate(ssvi, curUser);
    }

    @Override
    public ShipmentDetailsOutput getShipmentDetails(ShipmentDetailsInput sd, CustomUserDetails curUser) throws AccessControlFailedException {
        for (String programId : sd.getProgramIds()) {
            try {
                this.programCommonDao.getSimpleProgramById(Integer.parseInt(programId), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getShipmentDetails(sd, curUser);
    }

    @Override
    public ShipmentOverviewOutput getShipmentOverview(ShipmentOverviewInput so, CustomUserDetails curUser) throws AccessControlFailedException {
        if (so.getProgramIds() != null) {
            for (String program : so.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getShipmentOverview(so, curUser);
    }

    @Override
    public ShipmentGlobalDemandOutput getShipmentGlobalDemand(ShipmentGlobalDemandInput sgd, CustomUserDetails curUser) throws AccessControlFailedException {
        if (sgd.getRealmCountryIds() != null) {
            for (String realmCountry : sgd.getRealmCountryIds()) {
                try {
                    if (this.realmCountryDao.getRealmCountryById(Integer.parseInt(realmCountry), curUser) == null) {
                        throw new AccessControlFailedException();
                    }
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getShipmentGlobalDemand(sgd, curUser);
    }

    @Override
    public List<BudgetReportOutput> getBudgetReport(BudgetReportInput br, CustomUserDetails curUser) throws AccessControlFailedException {
        if (br.getProgramIds() != null) {
            for (String program : br.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.reportDao.getBudgetReport(br, curUser);
    }

    // Report no 30
    @Override
    public List<StockStatusAcrossProductsOutput> getStockStatusAcrossProducts(StockStatusAcrossProductsInput ssap, CustomUserDetails curUser) throws AccessControlFailedException {
        if (ssap.getProgramIds() != null) {
            for (String program : ssap.getProgramIds()) {
                try {
                    this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        List<StockStatusAcrossProductsOutput> ssapList = this.reportDao.getStockStatusAcrossProductsBasicInfo(ssap, curUser);
        List<StockStatusAcrossProductsOutput> finalList = new LinkedList<>();
        for (StockStatusAcrossProductsOutput s : ssapList) {
            StockStatusAcrossProductsOutput m = new StockStatusAcrossProductsOutput();
            m.setPlanningUnit(s.getPlanningUnit());
            finalList.add(m);
            for (StockStatusAcrossProductsForProgram progData : s.getProgramData()) {
                StockStatusAcrossProductsForProgram sData = this.reportDao.getStockStatusAcrossProductsProgramData(progData.getProgram().getId(), s.getPlanningUnit().getId(), ssap.getDt(), ssap.isUseApprovedSupplyPlanOnly());
                m.getProgramData().add(sData);
            }
        }
        return finalList;
    }

    // Report no 31 new
    @Override
    public List<ForecastErrorOutput> getForecastError(ForecastErrorInputNew fei, CustomUserDetails curUser) throws AccessControlFailedException {
        if (fei.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(fei.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getForecastError(fei, true, curUser);
    }

    // Mod 2 Report 1 -- Monthly Forecast
    @Override
    public List<MonthlyForecastOutput> getMonthlyForecast(MonthlyForecastInput mf, CustomUserDetails curUser) throws AccessControlFailedException {
        if (mf.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(mf.getProgramId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getMonthlyForecast(mf, curUser);
    }

    // Mod 2 Report 2 -- Forecast Summary
    @Override
    public List<ForecastSummaryOutput> getForecastSummary(ForecastSummaryInput fs, CustomUserDetails curUser) throws AccessControlFailedException {
        if (fs.getProgramId() != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(fs.getProgramId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.reportDao.getForecastSummary(fs, curUser);
    }

}
