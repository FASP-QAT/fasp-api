/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleFundingSourceObject;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.TreeTemplate;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
import java.util.Map;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/dropdown")
@Tag(
    name = "Dropdowns",
    description = "Provide filtered dropdown data for UI components across programs, planning units, and other system entities"
)
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

    @JsonView(Views.DropDownView.class)
    @GetMapping("/program/realm/{realmId}/programType/{programTypeId}")
    @Operation(
        summary = "DropDown",
        description = "Manage dropdown data for programs, planning units, forecasting units, organizations, funding sources, and other system entities"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve programs for")
    @Parameter(name = "programTypeId", description = "The ID of the program type to retrieve programs for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleProgram.class))), responseCode = "200", description = "Returns the list of programs for the specified realm and program type")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the program list")
    public ResponseEntity getProgramForDropdown(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, programTypeId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDown2View.class)
    @GetMapping("/program/realm/{realmId}/programType/{programTypeId}/expanded")
    @Operation(
        summary = "Get Programs (Expanded)",
        description = "Retrieve a list of programs with expanded details for a specific realm and program type"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve programs for")
    @Parameter(name = "programTypeId", description = "The ID of the program type to retrieve programs for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleProgram.class))), responseCode = "200", description = "Returns the list of programs with expanded details for the specified realm and program type")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the program list")
    public ResponseEntity getProgramExpandedForDropdown(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, programTypeId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/realm/{realmId}/programType/{programTypeId}/filter/healthAreaAndRealmCountry")
    @Operation(
        summary = "Get Programs (Health Area and Realm Country)",
        description = "Retrieve a list of programs for a specific program type and realm filtered on health area"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input for the program list filtered on health area and realm country",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HealthAreaAndRealmCountryDTO.class))
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve programs for")
    @Parameter(name = "programTypeId", description = "The ID of the program type to retrieve programs for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleProgram.class))), responseCode = "200", description = "Returns the list of programs for the specified realm and program type filtered on health area")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the program list")
    public ResponseEntity getProgramWithFilterForHealthAreaAndRealmCountryForDropdown(@RequestBody HealthAreaAndRealmCountryDTO input, @PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramWithFilterForHealthAreaAndRealmCountryListForDropdown(realmId, programTypeId, input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/programType/{programTypeId}/filter/multipleRealmCountry")
    @Operation(
        summary = "Get Programs (Multiple Realm Countries)",
        description = "Retrieve a filtered list of programs for a specific program type filtered by multiple realm countries"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The IDs of the realm countries to filter the programs by",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @Parameter(name = "programTypeId", description = "The ID of the program type to retrieve programs for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleProgram.class))), responseCode = "200", description = "Returns the list of programs for the specified program type filtered by multiple realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the program list")
    public ResponseEntity getProgramWithFilterForMultipleRealmCountryForDropdown(@RequestBody String[] realmCountryIds, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramWithFilterForMultipleRealmCountryListForDropdown(programTypeId, String.join(",", realmCountryIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/autocomplete")
    @Operation(
        summary = "Get Planning Units",
        description = "Retrieve a list of planning units, filtering on an autocomplete input"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The autocomplete input for the planning unit list",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutoCompleteInput.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of planning units filtered on autocomplete input")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the planning unit list")
    public ResponseEntity getPlanningUnitByAutoComplete(@RequestBody AutoCompleteInput autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForAutoComplete(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDown3View.class)
    @GetMapping("/planningUnit/autocomplete/filter/productCategory/{searchText}/{language}/{productCategorySortOrder}")
    @Operation(
        summary = "Get Planning Units by Product Category",
        description = "Retrieve a sortable list of planning units filtered by product category"
    )
    @Parameter(name = "searchText", description = "The search text for the planning unit list")
    @Parameter(name = "language", description = "The language for the planning unit list")
    @Parameter(name = "productCategorySortOrder", description = "The product category sort order for the planning unit list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of planning units filtered by product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the planning unit list")
    public ResponseEntity getPlanningUnitByAutoCompleteFilterForProductCategory(@PathVariable(value = "searchText", required = true) String searchText, @PathVariable(value = "language", required = true) String language, @PathVariable(value = "productCategorySortOrder", required = true) String productCategorySortOrder, Authentication auth) {
        try {
            AutocompleteInputWithProductCategoryDTO aci = new AutocompleteInputWithProductCategoryDTO();
            aci.setLanguage(language);
            aci.setSearchText(searchText);
            aci.setProductCategorySortOrder(productCategorySortOrder);
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForAutoCompleteFilterForProductCategory(aci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/planningUnit")
    @Operation(
        summary = "Get All Planning Units",
        description = "Retrieve a complete list of planning units"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the complete list of planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the complete list of planning units")
    public ResponseEntity getPlanningUnitDropDownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitDropDownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/filter/productCategory")
    @Operation(
        summary = "Get Planning Units by Product Category",
        description = "Retrieve a filtered list of planning units based on product and tracer category"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input for the planning unit list filtered by product and tracer category",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryAndTracerCategoryDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of planning units filtered by product and tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the planning unit list")
    public ResponseEntity getPlanningUnitFilterForProductCategory(@RequestBody ProductCategoryAndTracerCategoryDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitDropDownListFilterProductCategory(input.getProductCategorySortOrder(), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/forecastingUnit/autocomplete")
    @Operation(
        summary = "Get Forecasting Units (Autocomplete)",
        description = "Retrieve a list of forecasting units filtered on autocomplete input"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The autocomplete input for the forecasting unit list",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = AutoCompleteInput.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of forecasting units filtered on autocomplete input")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the forecasting unit list")
    public ResponseEntity getForecastingUnitByAutoComplete(@RequestBody AutoCompleteInput autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListForAutoComplete(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDown3View.class)
    @GetMapping("/forecastingUnit/autocomplete/filter/tracerCategory/{searchText}/{language}/{tracerCategoryId}")
    @Operation(
        summary = "Get Forecasting Units by Tracer Category",
        description = "Retrieve a list of forecasting units filtered by tracer category (autocomplete input)"
    )
    @Parameter(name = "searchText", description = "The search text for the forecasting unit list")
    @Parameter(name = "language", description = "The language for the forecasting unit list")
    @Parameter(name = "tracerCategoryId", description = "The tracer category ID for the forecasting unit list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of forecasting units filtered by tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the forecasting unit list")
    public ResponseEntity getForecastingUnitByAutoCompleteWithFilterTracerCategory(@PathVariable(value = "searchText", required = true) String searchText, @PathVariable(value = "language", required = true) String language, @PathVariable(value = "tracerCategoryId", required = true) int tracerCategoryId, Authentication auth) {
        try {
            AutocompleteInputWithTracerCategoryDTO aci = new AutocompleteInputWithTracerCategoryDTO();
            aci.setLanguage(language);
            aci.setSearchText(searchText);
            aci.setTracerCategoryId(tracerCategoryId);
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListForAutoCompleteWithFilterTracerCategory(aci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/forecastingUnit")
    @Operation(
        summary = "Get All Forecasting Units",
        description = "Retrieve a complete list of forecasting units"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the complete list of forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the complete list of forecasting units")
    public ResponseEntity getForecastingUnitDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/forecastingUnit/filter/pcAndTc")
    @Operation(
        summary = "Get Forecasting Units by Product and Tracer Categories",
        description = "Retrieve a list of forecasting units filtered on product and tracer categories"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input for the forecasting unit list filtered on product and tracer categories",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryAndTracerCategoryDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the forecasting unit list")
    public ResponseEntity getForecastingUnitDropdownListWithFilterForPcAndTc(@RequestBody ProductCategoryAndTracerCategoryDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitDropdownListWithFilterForPuAndTc(input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/realmCountry/realm/{realmId}")
    @Operation(
        summary = "Get Realm Countries",
        description = "Retrieve a list of countries for a specific realm"
    )
    @Parameter(name = "realmId", description = "The realm ID for the country list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of countries for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of countries")
    public ResponseEntity getRealmCountryDropdownList(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.realmCountryService.getRealmCountryDropdownList(realmId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/healthArea/realm/{realmId}")
    @Operation(
        summary = "Get Health Areas",
        description = "Retrieve a list of health areas for a specific realm"
    )
    @Parameter(name = "realmId", description = "The realm ID for the health area list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of health areas for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of health areas")
    public ResponseEntity getHealthAreaDropdownList(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getHealthAreaDropdownList(realmId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list HealthArea", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/organisation/realm/{realmId}")
    @Operation(
        summary = "Get Organizations",
        description = "Retrieve a list of organizations for a specific realm"
    )
    @Parameter(name = "realmId", description = "The realm ID for the organization list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of organizations for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of organizations")
    public ResponseEntity getOrganisationDropdownList(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getOrganisationDropdownList(realmId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/organisation/realmCountryId/{realmCountryId}")
    @Operation(
        summary = "Get Organizations by Country",
        description = "Retrieve a list of organizations for a specific realm country"
    )
    @Parameter(name = "realmCountryId", description = "The realm country ID for the organization list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of organizations for a specific realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of organizations")
    public ResponseEntity getOrganisationDropdownListForRealmCountryId(@PathVariable(value = "realmCountryId", required = true) int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getOrganisationDropdownListForRealmCountryId(realmCountryId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/tracerCategory")
    @Operation(
        summary = "Get Tracer Categories",
        description = "Retrieve a list of tracer categories"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of tracer categories")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of tracer categories")
    public ResponseEntity getTracerCategoryDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/tracerCategory/filter/multiplePrograms")
    @Operation(
        summary = "Get Tracer Categories by Programs",
        description = "Retrieve a list of tracer categories filtered on a list of program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs for the tracer category list",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of tracer categories filtered on a list of program IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of tracer categories")
    public ResponseEntity getTracerCategoryDropdownList(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryDropdownListForFilterMultiplerPrograms(String.join(",", programIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/fundingSource")
    @Operation(
        summary = "Get Funding Sources",
        description = "Retrieve a list of funding sources"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleFundingSourceObject.class))), responseCode = "200", description = "Returns the list of funding sources")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of funding sources")
    public ResponseEntity getFundingSourceDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding Source", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.DropDownView.class)
    @PostMapping("/fundingSource/programs")
    @Operation(
        summary = "Get Funding Sources by Programs",
        description = "Retrieve a list of funding sources filtered on a list of program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs for the funding source list",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Integer.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleFundingSourceObject.class))), responseCode = "200", description = "Returns the list of funding sources filtered on a list of program IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of funding sources")
    public ResponseEntity getFundingSourceForProgramsDropdownList(@RequestBody int[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceForProgramsDropdownList(programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding Source", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.DropDownView.class)
    @PostMapping("/fundingSourceType/programs")
    @Operation(
        summary = "Get Funding Source Types by Programs",
        description = "Retrieve a list of funding source types filtered on a list of program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs for the funding source type list",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Integer.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of funding source types filtered on a list of program IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of funding source types")
    public ResponseEntity getFundingSourceTypeForProgramsDropdownList(@RequestBody int[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceTypeForProgramsDropdownList(programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding Source Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/procurementAgent")
    @Operation(
        summary = "Get Procurement Agents",
        description = "Retrieve a list of procurement agents"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of procurement agents")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of procurement agents")
    public ResponseEntity getProcurementAgentDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/procurementAgent/filter/multiplePrograms")
    @Operation(
        summary = "Get Procurement Agents by Programs",
        description = "Retrieve a list of procurement agents filtered on a list of program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs for the procurement agent list",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of procurement agents filtered on a list of program IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of procurement agents")
    public ResponseEntity getProcurementAgentDropdownListForFilterMultiplePrograms(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentDropdownListForFilterMultiplePrograms(String.join(",", programIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/equivalencyUnit")
    @Operation(
        summary = "Get Equivalency Units",
        description = "Retrieve a list of equivalency units"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of equivalency units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of equivalency units")
    public ResponseEntity getEquivalencyUnitDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.getEquivalencyUnitDropDownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Equivalency Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/user")
    @Operation(
        summary = "Get Users",
        description = "Retrieve a list of users"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = BasicUser.class))), responseCode = "200", description = "Returns the list of users")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of users")
    public ResponseEntity getUserDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.userService.getUserDropDownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list User", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/program/filter/multipleProgramAndTracerCategory")
    @Operation(
        summary = "Get Planning Units by Program and Tracer Category",
        description = "Retrieve a list of planning units filtered on a list of program IDs and tracer category"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs and tracer category for the planning unit list",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = MultipleProgramAndTracerCategoryDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of planning units filtered on a list of program IDs and tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of planning units")
    public ResponseEntity getProgramPlanningUnitDropdownList(@RequestBody MultipleProgramAndTracerCategoryDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitByProgramAndTracerCategory(input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list User", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/dataset/filter/programAndVersion")
    @Operation(
        summary = "Get Planning Units by Program and Version",
        description = "Retrieve a list of planning units filtered on a program and version"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The program and version for the planning unit list",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgramAndVersionDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the list of planning units filtered on a program and version")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of planning units")
    public ResponseEntity getDatasetPlanningUnitDropdownList(@RequestBody ProgramAndVersionDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitForDatasetByProgramAndVersion(input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list User", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/budget/filter/multipleFundingSources")
    @Operation(
        summary = "Get Budgets by Funding Sources",
        description = "Retrieve a list of budgets filtered on a list of funding sources"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of funding sources for the budget list",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of budgets filtered on a list of funding sources")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of budgets")
    public ResponseEntity getBudgetDropdownFilterMultipleFundingSources(@RequestBody String[] fundingSources, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.budgetService.getBudgetDropdownFilterMultipleFundingSources(String.join(",", fundingSources), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/budget/program/{programId}")
    @Operation(
        summary = "Get Budgets by Program",
        description = "Retrieve a list of budgets for a specific program"
    )
    @Parameter(name = "programId", description = "The program ID for the budget list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of budgets for a specific program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of budgets")
    public ResponseEntity getBudgetDropdownForProgram(@PathVariable(value = "programId", required = true) int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.budgetService.getBudgetDropdownForProgram(programId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/version/filter/programTypeId/{programTypeId}/programId/{programId}")
    @Operation(
        summary = "Get Versions by Program Type and Program",
        description = "Retrieve a list of versions for a specific program type and program"
    )
    @Parameter(name = "programTypeId", description = "The program type ID for the version list")
    @Parameter(name = "programId", description = "The program ID for the version list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Version.class))), responseCode = "200", description = "Returns the list of versions for a specific program type and program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of versions")
    public ResponseEntity getVersionListForProgram(@PathVariable(value = "programTypeId", required = true) int programTypeId, @PathVariable(value = "programId", required = true) int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getVersionListForProgramId(programTypeId, programId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Version", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/version/filter/programTypeId/{programTypeId}/programs")
    @Operation(
        summary = "Get Versions by Program Type and Programs",
        description = "Retrieve a list of versions filtered on a program type and a list of program IDs"
    )
    @Parameter(name = "programTypeId", description = "The program type ID for the version list")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs for the version list",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Map.class))), responseCode = "200", description = "Returns the list of versions filtered on a program type and a list of program IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of versions")
    public ResponseEntity getVersionListForPrograms(@PathVariable(value = "programTypeId", required = true) int programTypeId, @RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getVersionListForPrograms(programTypeId, programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Version", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/treeTemplate")
    @Operation(
        summary = "Get Tree Templates",
        description = "Retrieve a list of tree templates"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TreeTemplate.class))), responseCode = "200", description = "Returns the list of tree templates")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of tree templates")
    public ResponseEntity getTreeTemplateList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateListForDropDown(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/program/versionStatus/{versionStatusIdList}/versionType/{versionTypeIdList}")
    @Operation(
        summary = "Get Programs by Version Status and Version Type",
        description = "Retrieve a list of programs filtered on version status and version type"
    )
    @Parameter(name = "versionStatusIdList", description = "The list of version status IDs for the program list")
    @Parameter(name = "versionTypeIdList", description = "The list of version type IDs for the program list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleCodeObject.class))), responseCode = "200", description = "Returns the list of programs filtered on version status and version type")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the list of programs")
    public ResponseEntity getProgramListByVersionStatusAndVersionType(@PathVariable(value = "versionStatusIdList", required = true) String versionStatusIdList, @PathVariable(value = "versionTypeIdList", required = true) String versionTypeIdList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListByVersionStatusAndVersionType(versionStatusIdList, versionTypeIdList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}