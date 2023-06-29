/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.rest.controller;

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

    @JsonView(Views.DropDownView.class)
    @GetMapping("/program/realm/{realmId}/programType/{programTypeId}")
    public ResponseEntity getProgramForDropdown(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, programTypeId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDown2View.class)
    @GetMapping("/program/realm/{realmId}/programType/{programTypeId}/expanded")
    public ResponseEntity getProgramExpandedForDropdown(@PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForDropdown(realmId, programTypeId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/realm/{realmId}/programType/{programTypeId}/filter/healthAreaAndRealmCountry")
    public ResponseEntity getProgramWithFilterForHealthAreaAndRealmCountryForDropdown(@RequestBody HealthAreaAndRealmCountryDTO input, @PathVariable(value = "realmId", required = true) int realmId, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramWithFilterForHealthAreaAndRealmCountryListForDropdown(realmId, programTypeId, input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/program/programType/{programTypeId}/filter/multipleRealmCountry")
    public ResponseEntity getProgramWithFilterForMultipleRealmCountryForDropdown(@RequestBody String[] realmCountryIds, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramWithFilterForMultipleRealmCountryListForDropdown(programTypeId, String.join(",", realmCountryIds), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/autocomplete")
    public ResponseEntity getPlanningUnitByAutoComplete(@RequestBody AutoCompleteInput autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForAutoComplete(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/autocomplete/filter/productCategory")
    public ResponseEntity getPlanningUnitByAutoCompleteFilterForProductCategory(@RequestBody AutocompleteInputWithProductCategoryDTO autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForAutoCompleteFilterForProductCategory(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/planningUnit")
    public ResponseEntity getPlanningUnitDropDownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitDropDownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/planningUnit/filter/productCategory")
    public ResponseEntity getPlanningUnitFilterForProductCategory(@RequestBody ProductCategoryAndTracerCategoryDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitDropDownListFilterProductCategory(input.getProductCategorySortOrder(), curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/forecastingUnit/autocomplete")
    public ResponseEntity getForecastingUnitByAutoComplete(@RequestBody AutoCompleteInput autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListForAutoComplete(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/forecastingUnit/autocomplete/filter/tracerCategory")
    public ResponseEntity getForecastingUnitByAutoCompleteWithFilterTracerCategory(@RequestBody AutocompleteInputWithTracerCategoryDTO autoCompleteInput, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListForAutoCompleteWithFilterTracerCategory(autoCompleteInput, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @GetMapping("/forecastingUnit")
    public ResponseEntity getForecastingUnitDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/forecastingUnit/filter/pcAndTc")
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
    @GetMapping("/procurementAgent")
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
    public ResponseEntity getTreeTemplateList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateListForDropDown(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
