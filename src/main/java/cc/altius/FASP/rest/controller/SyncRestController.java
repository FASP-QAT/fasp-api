/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.MastersSync;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.report.TreeAnchorInput;
import cc.altius.FASP.model.report.TreeAnchorOutput;
import cc.altius.FASP.service.BudgetService;
import cc.altius.FASP.service.CountryService;
import cc.altius.FASP.service.CurrencyService;
import cc.altius.FASP.service.DataSourceService;
import cc.altius.FASP.service.DataSourceTypeService;
import cc.altius.FASP.service.DimensionService;
import cc.altius.FASP.service.EquivalencyUnitService;
import cc.altius.FASP.service.ForecastMethodService;
import cc.altius.FASP.service.ForecastingUnitService;
import cc.altius.FASP.service.FundingSourceService;
import cc.altius.FASP.service.HealthAreaService;
import cc.altius.FASP.service.LanguageService;
import cc.altius.FASP.service.ModelingTypeService;
import cc.altius.FASP.service.OrganisationService;
import cc.altius.FASP.service.OrganisationTypeService;
import cc.altius.FASP.service.PlanningUnitService;
import cc.altius.FASP.service.ProblemService;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.ProcurementUnitService;
import cc.altius.FASP.service.ProductCategoryService;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.RealmService;
import cc.altius.FASP.service.RegionService;
import cc.altius.FASP.service.TracerCategoryService;
import cc.altius.FASP.service.TreeTemplateService;
import cc.altius.FASP.service.UnitService;
import cc.altius.FASP.service.UsagePeriodService;
import cc.altius.FASP.service.UsageTemplateService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import static cc.altius.FASP.utils.CompressUtils.compress;
import static cc.altius.FASP.utils.CompressUtils.isCompress;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import cc.altius.FASP.service.MasterDataService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@Controller
@Tag(
    name = "Sync",
    description = "Manage data synchronization and master data updates across the system"
)
@RequestMapping("/api/sync")
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
    private OrganisationTypeService organisationTypeService;
    @Autowired
    private FundingSourceService fundingSourceService;
    @Autowired
    private ProcurementAgentService procurementAgentService;
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
    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private MasterDataService masterDataService;
    @Autowired
    private UsagePeriodService usagePeriodService;
    @Autowired
    private ModelingTypeService modelingTypeService;
    @Autowired
    private ForecastMethodService forecastMethodService;
    @Autowired
    private UsageTemplateService usageTemplateService;
    @Autowired
    private TreeTemplateService treeTemplateService;
    @Autowired
    private EquivalencyUnitService equivalencyUnitService;

    private String getProgramIds(String[] programIds) {
        if (programIds == null) {
            return "";
        } else {
            String opt = String.join("','", programIds);
            if (programIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

//    @PostMapping(value = "/sync/test/forPrograms/{lastSyncDate}")
    @Operation(
        summary = "Test synchronization",
        description = "Test synchronization with program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The program IDs to test synchronization with",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @Parameter(
        name = "lastSyncDate",
        description = "The last synchronization date",
        required = true
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = MastersSync.class)), responseCode = "200", description = "Returns masters data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Invalid lastSyncDate")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the program planning units")
    public ResponseEntity TestSyncWithProgramIds(@RequestBody String[] programIds, @PathVariable("lastSyncDate") String lastSyncDate, Authentication auth, HttpServletResponse response) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            String programIdsString = getProgramIds(programIds);
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            MastersSync masters = new MastersSync();
//            masters.setExtrapolationMethodList(this.forecastingStaticDataService.getExtrapolationMethodListForSync(lastSyncDate, curUser));
//            masters.setPlanningUnitList(this.planningUnitService.getPlanningUnitListForSyncProgram(programIdsString, curUser)); //programIds, -- Done for Dataset
            masters.setProgramPlanningUnitList(this.programService.getProgramPlanningUnitListForSyncProgram(programIdsString, curUser));//programIds, 
            return new ResponseEntity(masters, HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error in masters sync", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (Exception e) {
            logger.error("Error in masters sync", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
    
    /**
     * API to get the data that needs to be Synced with the Offline machine
     *
     * @param programIds
     * @param lastSyncDate
     * @param auth
     * @param response
     * @return
     */
    @PostMapping(value = "/allMasters/forPrograms/{lastSyncDate}")
    @Operation(
        summary = "Get all Master Data",
        description = "Get all the data that needs to be synced with the offline machine"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The program IDs to get masters for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @Parameter(
        name = "lastSyncDate",
        description = "The last synchronization date",
        required = true
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = MastersSync.class)), responseCode = "200", description = "Returns the master data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Invalid lastSyncDate")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the master data")
    public ResponseEntity allMastersForSyncWithProgramIds(@RequestBody String[] programIds, @PathVariable("lastSyncDate") String lastSyncDate, Authentication auth, HttpServletResponse response) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            String programIdsString = getProgramIds(programIds);
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            MastersSync masters = new MastersSync();
            masters.setCountryList(this.countryService.getCountryListForSyncProgram(programIdsString, curUser));//programIds -- Done for Dataset
            masters.setCurrencyList(this.currencyService.getCurrencyListForSync(lastSyncDate));
            masters.setDimensionList(this.dimensionService.getDimensionListForSync(lastSyncDate));
            masters.setLanguageList(this.languageService.getLanguageListForSync(lastSyncDate));
            masters.setShipmentStatusList(this.masterDataService.getShipmentStatusListForSync(lastSyncDate, curUser));
            masters.setUnitList(this.unitService.getUnitListForSync(lastSyncDate));
            masters.setDataSourceTypeList(this.dataSourceTypeService.getDataSourceTypeListForSync(lastSyncDate, curUser));
            masters.setDataSourceList(this.dataSourceService.getDataSourceListForSync(lastSyncDate, curUser));
            masters.setTracerCategoryList(this.tracerCategoryService.getTracerCategoryListForSync(lastSyncDate, curUser));
            masters.setProductCategoryList(this.productCategoryService.getProductCategoryListForSync(lastSyncDate, curUser));
            masters.setRealmList(this.realmService.getRealmListForSync(lastSyncDate, curUser));
            masters.setHealthAreaList(this.healthAreaService.getHealthAreaListForSync(lastSyncDate, curUser));
            masters.setOrganisationList(this.organisationService.getOrganisationListForSync(lastSyncDate, curUser));
            masters.setOrganisationTypeList(this.organisationTypeService.getOrganisationTypeListForSync(lastSyncDate, curUser));
            masters.setFundingSourceList(this.fundingSourceService.getFundingSourceListForSync(lastSyncDate, curUser));
            masters.setProcurementAgentList(this.procurementAgentService.getProcurementAgentListForSync(lastSyncDate, curUser));
            masters.setForecastingUnitList(this.forecastingUnitService.getForecastingUnitListForSyncProgram(programIdsString, curUser)); // programIds -- Done for Dataset, 
            masters.setPlanningUnitList(this.planningUnitService.getPlanningUnitListForSyncProgram(programIdsString, curUser)); //programIds, -- Done for Dataset
            masters.setProcurementUnitList(this.procurementUnitService.getProcurementUnitListForSyncProgram(programIdsString, curUser));//programIds, 
            masters.setRealmCountryList(this.realmCountryService.getRealmCountryListForSyncProgram(programIdsString, curUser));//programIds,  -- Done for Dataset
            masters.setRealmCountryPlanningUnitList(this.realmCountryService.getRealmCountryPlanningUnitListForSyncProgram(programIdsString, curUser));//programIds , 
            masters.setProcurementAgentPlanningUnitList(this.procurementAgentService.getProcurementAgentPlanningUnitListForSyncProgram(programIdsString, curUser));//programIds, 
            masters.setProcurementAgentForecastingUnitList(this.procurementAgentService.getProcurementAgentForecastingUnitListForSyncProgram(programIdsString, curUser));//programIds, 
            masters.setProcurementAgentProcurementUnitList(this.procurementAgentService.getProcurementAgentProcurementUnitListForSyncProgram(programIdsString, curUser));//programIds, 
            masters.setProgramList(this.programService.getProgramListForSyncProgram(programIdsString, curUser));//programIds,  -- Done for Dataset
            masters.setProgramPlanningUnitList(this.programService.getProgramPlanningUnitListForSyncProgram(programIdsString, curUser));//programIds, 
            masters.setRegionList(this.regionService.getRegionListForSyncProgram(programIdsString, curUser));//programIds,  -- Done for Dataset
            masters.setBudgetList(this.budgetService.getBudgetListForSyncProgram(programIdsString, curUser));//programIds, 
            masters.setProblemStatusList(this.problemService.getProblemStatusForSync(lastSyncDate, curUser));
            masters.setProblemCriticalityList(this.problemService.getProblemCriticalityForSync(lastSyncDate, curUser));
            masters.setProblemCategoryList(this.problemService.getProblemCategoryForSync(lastSyncDate, curUser));
            masters.setRealmProblemList(this.problemService.getProblemListForSync(lastSyncDate, curUser));
            masters.setVersionTypeList(this.masterDataService.getVersionTypeList());
            masters.setVersionStatusList(this.masterDataService.getVersionStatusList());
            masters.setUsageTypeList(this.masterDataService.getUsageTypeListForSync(lastSyncDate, curUser));
            masters.setNodeTypeList(this.masterDataService.getNodeTypeListForSync(lastSyncDate, curUser));
            masters.setForecastMethodTypeList(this.masterDataService.getForecastMethodTypeListForSync(lastSyncDate, curUser));
            masters.setUsagePeriodList(this.usagePeriodService.getUsagePeriodListForSync(lastSyncDate, curUser));
            masters.setModelingTypeList(this.modelingTypeService.getModelingTypeListForSync(lastSyncDate, curUser));
            masters.setForecastMethodList(this.forecastMethodService.getForecastMethodListForSync(lastSyncDate, curUser));
            masters.setUsageTemplateList(this.usageTemplateService.getUsageTemplateListForSync(programIdsString, curUser));
            masters.setTreeTemplateList(this.treeTemplateService.getTreeTemplateListForSync(lastSyncDate, curUser));
            masters.setEquivalencyUnitMappingList(this.equivalencyUnitService.getEquivalencyUnitMappingListForSync(programIdsString, curUser));
            masters.setExtrapolationMethodList(this.masterDataService.getExtrapolationMethodListForSync(lastSyncDate, curUser));
            masters.setProcurementAgentyType(this.procurementAgentService.getProcurementAgentTypeListForSync(lastSyncDate, curUser));
            masters.setFundingSourceType(this.fundingSourceService.getFundingSourceTypeListForSync(lastSyncDate, curUser));
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(masters);
            if (isCompress(jsonString)) {
                return new ResponseEntity(compress(jsonString), HttpStatus.OK);
            }
            return new ResponseEntity(masters, HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error in masters sync", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (Exception e) {
            logger.error("Error in masters sync", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of Languages that are allowed
     *
     * @param lastSyncDate
     * @return
     */
    @GetMapping(value = "/language/{lastSyncDate}")
    @Operation(
        summary = "Get Languages",
        description = "Get language list for synchronization"
    )
    @Parameter(
        name = "lastSyncDate",
        description = "The last synchronization date",
        required = true
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Language.class))), responseCode = "200", description = "Returns the language list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Invalid lastSyncDate")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the language list")
    public ResponseEntity getLanguageListForSync(@PathVariable("lastSyncDate") String lastSyncDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            return new ResponseEntity(this.languageService.getLanguageListForSync(lastSyncDate), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing language", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (Exception e) {
            logger.error("Error while listing language", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get the list of Tree Anchor nodes for a Specific DataSet Program and a
     * list of Trees
     *
     * @param ta
     * @param auth
     * @return
     */
    @PostMapping(value = "/treeAnchor")
    @Operation(
        summary = "Get Tree Anchors",
        description = "Get the list of tree anchor nodes for a specified dataset and list of trees for synchronization"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The tree anchor input",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TreeAnchorInput.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TreeAnchorOutput.class))), responseCode = "200", description = "Returns the tree anchor list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the tree anchor list")
    public ResponseEntity getSyncListForTreeAnchor(@RequestBody TreeAnchorInput ta, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getTreeAnchorForSync(ta, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting Tree Anchor list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
