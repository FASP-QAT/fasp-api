/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.AutocompleteInputWithProductCategoryDTO;
import cc.altius.FASP.model.DTO.AutocompleteInputWithTracerCategoryDTO;
import cc.altius.FASP.model.DTO.HealthAreaAndRealmCountryDTO;
import cc.altius.FASP.model.DTO.MultipleProgramAndTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProductCategoryAndTracerCategoryDTO;
import cc.altius.FASP.model.DTO.ProgramAndVersionDTO;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.BudgetService;
import cc.altius.FASP.service.EquivalencyUnitService;
import cc.altius.FASP.service.ForecastingUnitService;
import cc.altius.FASP.service.FundingSourceService;
import cc.altius.FASP.service.HealthAreaService;
import cc.altius.FASP.service.OrganisationService;
import cc.altius.FASP.service.PlanningUnitService;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.TracerCategoryService;
import cc.altius.FASP.service.TreeTemplateService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/dropdown")
public class DropDownRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProgramService programService;
    @Autowired
    private PlanningUnitService planningUnitService;
    @Autowired
    private ForecastingUnitService forecastingUnitService;
    @Autowired
    private RealmCountryService realmCountryService;
    @Autowired
    private HealthAreaService healthAreaService;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private TracerCategoryService tracerCategoryService;
    @Autowired
    private FundingSourceService fundingSourceService;
    @Autowired
    private ProcurementAgentService procurementAgentService;
    @Autowired
    private EquivalencyUnitService equivalencyUnitService;
    @Autowired
    private TreeTemplateService treeTemplateService;
    @Autowired
    private UserService userService;
    @Autowired
    private BudgetService budgetService;

    /**Get Program list for Dropdown based on Realm
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/supplyPlan/program/realm/{realmId}")
    public ResponseEntity getProgramForDropdownSupplyPlan(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.DropDownView.class)
    @GetMapping("/dataset/program/realm/{realmId}")
    public ResponseEntity getProgramForDropdownDataset(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Program list for Dropdown based on Realm and with additional details
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDown2View.class)
    @GetMapping("/program/all/expanded/realm/{realmId}")
    public ResponseEntity getProgramExpandedForAllDropdown(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, 0, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**Get Program list for Dropdown based on Realm and with additional details
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDown2View.class)
    @GetMapping("/program/sp/expanded/realm/{realmId}")
    public ResponseEntity getProgramExpandedForSpDropdown(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**Get Program list for Dropdown based on Realm and with additional details
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDown2View.class)
    @GetMapping("/program/fc/expanded/realm/{realmId}")
    public ResponseEntity getProgramExpandedForFcDropdown(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Program list for Dropdown based on Realm and RealmCountry and HealthArea
     * 
     * @param input
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/sp/filter/healthAreaAndRealmCountry/realm/{realmId}")
    public ResponseEntity getProgramWithFilterForHealthAreaAndRealmCountryForSpDropdown(@RequestBody HealthAreaAndRealmCountryDTO input, @PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramWithFilterForHealthAreaAndRealmCountryListForDropdown(realmId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**Get Program list for Dropdown based on Realm and RealmCountry and HealthArea
     * 
     * @param input
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/fc/filter/healthAreaAndRealmCountry/realm/{realmId}")
    public ResponseEntity getProgramWithFilterForHealthAreaAndRealmCountryForFcDropdown(@RequestBody HealthAreaAndRealmCountryDTO input, @PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramWithFilterForHealthAreaAndRealmCountryListForDropdown(realmId, GlobalConstants.PROGRAM_TYPE_DATASET, input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Program list for Dropdown based on list of RealmCountryIds
     * 
     * @param realmCountryIds
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/sp/filter/multipleRealmCountry")
    public ResponseEntity getProgramWithFilterForMultipleRealmCountryForSpDropdown(@RequestBody String[] realmCountryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramWithFilterForMultipleRealmCountryListForDropdown(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, String.join(",", realmCountryIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**Get Program list for Dropdown based on list of RealmCountryIds
     * 
     * @param realmCountryIds
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/fc/filter/multipleRealmCountry")
    public ResponseEntity getProgramWithFilterForMultipleRealmCountryForFcDropdown(@RequestBody String[] realmCountryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramWithFilterForMultipleRealmCountryListForDropdown(GlobalConstants.PROGRAM_TYPE_DATASET, String.join(",", realmCountryIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Used to find the Planning Unit based on partial name
     * 
     * @param autoCompleteInput
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/autocomplete")
    public ResponseEntity getPlanningUnitByAutoComplete(@RequestBody AutoCompleteInput autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForAutoComplete(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Used to find the Planning Unit based on partial name and additional filters. Only returns list of Id’s
     * 
     * @param searchText
     * @param language
     * @param productCategorySortOrder
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDown3View.class)
    @GetMapping("/planningUnit/autocomplete/filter/productCategory/{searchText}/{language}/{productCategorySortOrder}")
    public ResponseEntity getPlanningUnitByAutoCompleteFilterForProductCategory(@PathVariable(value = "searchText", required = true) String searchText, @PathVariable(value = "language", required = true) String language, @PathVariable(value = "productCategorySortOrder", required = true) String productCategorySortOrder, Authentication auth) {
        try {
            AutocompleteInputWithProductCategoryDTO aci = new AutocompleteInputWithProductCategoryDTO();
            aci.setLanguage(language);
            aci.setSearchText(searchText);
            aci.setProductCategorySortOrder(productCategorySortOrder);
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForAutoCompleteFilterForProductCategory(aci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of all Planning Units for Dropdown 
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/planningUnit")
    public ResponseEntity getPlanningUnitDropDownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitDropDownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Used to find the Forecasting Unit based on partial name
     * 
     * @param autoCompleteInput
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/forecastingUnit/autocomplete")
    public ResponseEntity getForecastingUnitByAutoComplete(@RequestBody AutoCompleteInput autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListForAutoComplete(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Used to find the Forecasting Unit based on partial name and additional filters. Only returns list of Id’s
     * 
     * @param searchText
     * @param language
     * @param tracerCategoryId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDown3View.class)
    @GetMapping("/forecastingUnit/autocomplete/filter/tracerCategory/{searchText}/{language}/{tracerCategoryId}")
    public ResponseEntity getForecastingUnitByAutoCompleteWithFilterTracerCategory(@PathVariable(value = "searchText", required = true) String searchText, @PathVariable(value = "language", required = true) String language, @PathVariable(value = "tracerCategoryId", required = true) int tracerCategoryId, Authentication auth) {
        try {
            AutocompleteInputWithTracerCategoryDTO aci = new AutocompleteInputWithTracerCategoryDTO();
            aci.setLanguage(language);
            aci.setSearchText(searchText);
            aci.setTracerCategoryId(tracerCategoryId);
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListForAutoCompleteWithFilterTracerCategory(aci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of all Forecasting Units for Dropdown 
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/forecastingUnit")
    public ResponseEntity getForecastingUnitDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of all Forecasting Units for Dropdown filtered on ProductCategory and TracerCategory
     * 
     * @param input
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/forecastingUnit/filter/pcAndTc")
    public ResponseEntity getForecastingUnitDropdownListWithFilterForPcAndTc(@RequestBody ProductCategoryAndTracerCategoryDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitDropdownListWithFilterForPuAndTc(input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of RealmCountries based on Realm
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/realmCountry/realm/{realmId}")
    public ResponseEntity getRealmCountryDropdownList(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getRealmCountryDropdownList(realmId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of HealthAreas based on Realm
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/healthArea/realm/{realmId}")
    public ResponseEntity getHealthAreaDropdownList(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.healthAreaService.getHealthAreaDropdownList(realmId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list HealthArea", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of Organisations based on Realm
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/organisation/realm/{realmId}")
    public ResponseEntity getOrganisationDropdownList(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.organisationService.getOrganisationDropdownList(realmId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of Organisations based on RealmCountry
     * 
     * @param realmCountryId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/organisation/realmCountryId/{realmCountryId}")
    public ResponseEntity getOrganisationDropdownListForRealmCountryId(@PathVariable(value = "realmCountryId", required = true) int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.organisationService.getOrganisationDropdownListForRealmCountryId(realmCountryId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of TracerCategories for a Dropdown
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/tracerCategory")
    public ResponseEntity getTracerCategoryDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of TracerCategories based on a list of Programs for a Dropdown
     * 
     * @param programIds
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/tracerCategory/filter/multiplePrograms")
    public ResponseEntity getTracerCategoryDropdownList(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryDropdownListForFilterMultiplerPrograms(String.join(",", programIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of FundingSources for a Dropdown
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/fundingSource")
    public ResponseEntity getFundingSourceDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding Source", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/fundingSource/programs")
    public ResponseEntity getFundingSourceForProgramsDropdownList(@RequestBody int[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceForProgramsDropdownList(programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding Source", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/fundingSourceType/programs")
    public ResponseEntity getFundingSourceTypeForProgramsDropdownList(@RequestBody int[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceTypeForProgramsDropdownList(programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding Source Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of ProcurementAgents for a Dropdown
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/procurementAgent")
    public ResponseEntity getProcurementAgentDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of ProcurementAgents based on a list of Programs for a Dropdown
     * 
     * @param programIds
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/procurementAgent/filter/multiplePrograms")
    public ResponseEntity getProcurementAgentDropdownListForFilterMultiplePrograms(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentDropdownListForFilterMultiplePrograms(String.join(",", programIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of EquivalencyUnits for a Dropdown
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/equivalencyUnit")
    public ResponseEntity getEquivalencyUnitDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.equivalencyUnitService.getEquivalencyUnitDropDownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Equivalency Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Gets the list of Users for a Dropdown
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/user")
    public ResponseEntity getUserDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.userService.getUserDropDownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list User", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of all Planning Units for Dropdown filtered on Multiple ProductCategories and TracerCategories
     * 
     * @param input
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/program/filter/multipleProgramAndTracerCategory")
    public ResponseEntity getProgramPlanningUnitDropdownList(@RequestBody MultipleProgramAndTracerCategoryDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitByProgramAndTracerCategory(input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list User", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Planning Unit list for Dataset Program for Dropdown
     * 
     * @param input
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/dataset/filter/programAndVersion")
    public ResponseEntity getDatasetPlanningUnitDropdownList(@RequestBody ProgramAndVersionDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitForDatasetByProgramAndVersion(input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list User", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of Budgets filtered by FundingSources
     * 
     * @param fundingSources
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/budget/filter/multipleFundingSources")
    public ResponseEntity getBudgetDropdownFilterMultipleFundingSources(@RequestBody String[] fundingSources, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.budgetService.getBudgetDropdownFilterMultipleFundingSources(String.join(",", fundingSources), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of Budgets for a Program
     * 
     * @param programId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/budget/program/{programId}")
    public ResponseEntity getBudgetDropdownForProgram(@PathVariable(value = "programId", required = true) int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.budgetService.getBudgetDropdownForProgram(programId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Version list for Program
     * 
     * @param programId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/version/filter/fc/programId/{programId}")
    public ResponseEntity getVersionListForFcProgram(@PathVariable(value = "programId", required = true) int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getVersionListForProgramId(GlobalConstants.PROGRAM_TYPE_DATASET, programId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Version", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**Get Version list for Program for SupplyPlan
     * 
     * @param programId
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/version/filter/sp/programId/{programId}")
    public ResponseEntity getVersionListForSpProgram(@PathVariable(value = "programId", required = true) int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getVersionListForProgramId(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, programId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Version", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Version list for multiple Programs for Dataset
     * 
     * @param programIds
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/version/filter/fc/programs")
    public ResponseEntity getVersionListForFcPrograms(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getVersionListForPrograms(GlobalConstants.PROGRAM_TYPE_DATASET, programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Version", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**Get Version list for multiple Programs for Supply Plan
     * 
     * @param programIds
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @PostMapping("/version/filter/sp/programs")
    public ResponseEntity getVersionListForSpPrograms(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getVersionListForPrograms(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Version", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of Tree templates for Dropdown
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/treeTemplate")
    public ResponseEntity getTreeTemplateList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateListForDropDown(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of SP Programs for Dropdown based on Current Program Version and Current Program Status
     * 
     * @param versionStatusIdList
     * @param versionTypeIdList
     * @param auth
     * @return 
     */
    @JsonView(Views.DropDownView.class)
    @GetMapping("/program/versionStatus/{versionStatusIdList}/versionType/{versionTypeIdList}")
    public ResponseEntity getProgramListByVersionStatusAndVersionType(@PathVariable(value = "versionStatusIdList", required = true) String versionStatusIdList, @PathVariable(value = "versionTypeIdList", required = true) String versionTypeIdList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListByVersionStatusAndVersionType(versionStatusIdList, versionTypeIdList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of active SimpleObject of PU with FU
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/planningUnit/basic")
    public ResponseEntity getPlanningUnitListBasic(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListBasic(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
