/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.MastersSync;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.BudgetService;
import cc.altius.FASP.service.CountryService;
import cc.altius.FASP.service.CurrencyService;
import cc.altius.FASP.service.DataSourceService;
import cc.altius.FASP.service.DataSourceTypeService;
import cc.altius.FASP.service.DimensionService;
import cc.altius.FASP.service.ForecastingUnitService;
import cc.altius.FASP.service.FundingSourceService;
import cc.altius.FASP.service.HealthAreaService;
import cc.altius.FASP.service.LanguageService;
import cc.altius.FASP.service.OrganisationService;
import cc.altius.FASP.service.PlanningUnitService;
import cc.altius.FASP.service.ProblemService;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.ProcurementUnitService;
import cc.altius.FASP.service.ProductCategoryService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.RealmService;
import cc.altius.FASP.service.RegionService;
import cc.altius.FASP.service.ShipmentStatusService;
import cc.altius.FASP.service.SupplierService;
import cc.altius.FASP.service.TracerCategoryService;
import cc.altius.FASP.service.UnitService;
import cc.altius.FASP.service.UserService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author akil
 */
@Controller
@RequestMapping("/api")
public class SyncRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CountryService countryService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private ShipmentStatusService shipmentStatusService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DataSourceTypeService dataSourceTypeService;
    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private TracerCategoryService tracerCategoryService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private RealmService realmService;
    @Autowired
    private HealthAreaService healthAreaService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private FundingSourceService fundingSourceService;
    @Autowired
    private ProcurementAgentService procurementAgentService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private ForecastingUnitService forecastingUnitService;
    @Autowired
    private PlanningUnitService planningUnitService;
    @Autowired
    private ProcurementUnitService procurementUnitService;
    @Autowired
    private RealmCountryService realmCountryService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private RegionService regionService;
    @Autowired 
    private BudgetService budgetService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/sync/allMasters/{lastSyncDate}")
    public ResponseEntity getAllMastersForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth, HttpServletResponse response) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            MastersSync masters = new MastersSync();
            masters.setCountryList(this.countryService.getCountryListForSync(lastSyncDate));
            masters.setCurrencyList(this.currencyService.getCurrencyListForSync(lastSyncDate));
            masters.setDimensionList(this.dimensionService.getDimensionListForSync(lastSyncDate));
            masters.setLanguageList(this.languageService.getLanguageListForSync(lastSyncDate));
            masters.setShipmentStatusList(this.shipmentStatusService.getShipmentStatusListForSync(lastSyncDate, curUser));
            masters.setUnitList(this.unitService.getUnitListForSync(lastSyncDate));
            masters.setDataSourceTypeList(this.dataSourceTypeService.getDataSourceTypeListForSync(lastSyncDate, curUser));
            masters.setDataSourceList(this.dataSourceService.getDataSourceListForSync(lastSyncDate, curUser));
            masters.setTracerCategoryList(this.tracerCategoryService.getTracerCategoryListForSync(lastSyncDate, curUser));
            masters.setProductCategoryList(this.productCategoryService.getProductCategoryListForSync(lastSyncDate, curUser));
            masters.setRealmList(this.realmService.getRealmListForSync(lastSyncDate, curUser));
            masters.setHealthAreaList(this.healthAreaService.getHealthAreaListForSync(lastSyncDate, curUser));
            masters.setOrganisationList(this.organisationService.getOrganisationListForSync(lastSyncDate, curUser));
            masters.setFundingSourceList(this.fundingSourceService.getFundingSourceListForSync(lastSyncDate, curUser));
            masters.setProcurementAgentList(this.procurementAgentService.getProcurementAgentListForSync(lastSyncDate, curUser));
            masters.setSupplierList(this.supplierService.getSupplierListForSync(lastSyncDate, curUser));
            masters.setForecastingUnitList(this.forecastingUnitService.getForecastingUnitListForSync(lastSyncDate, curUser));
            masters.setPlanningUnitList(this.planningUnitService.getPlanningUnitListForSync(lastSyncDate, curUser));
            masters.setProcurementUnitList(this.procurementUnitService.getProcurementUnitListForSync(lastSyncDate, curUser));
            masters.setRealmCountryList(this.realmCountryService.getRealmCountryListForSync(lastSyncDate, curUser));
            masters.setRealmCountryPlanningUnitList(this.realmCountryService.getRealmCountryPlanningUnitListForSync(lastSyncDate, curUser));
            masters.setProcurementAgentPlanningUnitList(this.procurementAgentService.getProcurementAgentPlanningUnitListForSync(lastSyncDate, curUser));
            masters.setProcurementAgentProcurementUnitList(this.procurementAgentService.getProcurementAgentProcurementUnitListForSync(lastSyncDate, curUser));
            masters.setProgramList(this.programService.getProgramListForSync(lastSyncDate, curUser));
            masters.setProgramPlanningUnitList(this.programService.getProgramPlanningUnitListForSync(lastSyncDate, curUser));
            masters.setRegionList(this.regionService.getRegionListForSync(lastSyncDate, curUser));
            masters.setBudgetList(this.budgetService.getBudgetListForSync(lastSyncDate, curUser));
            masters.setProblemStatusList(this.problemService.getProblemStatusForSync(lastSyncDate, curUser));
            masters.setProblemCriticalityList(this.problemService.getProblemCriticalityForSync(lastSyncDate, curUser));
            masters.setProblemCategoryList(this.problemService.getProblemCategoryForSync(lastSyncDate, curUser));
            masters.setRealmProblemList(this.problemService.getProblemListForSync(lastSyncDate, curUser));
            return new ResponseEntity(masters, HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error in masters sync", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error in masters sync", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
