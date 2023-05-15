/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.HealthAreaAndRealmCountryDTO;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
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
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
    private UserService userService;
    
    @JsonView(Views.InternalView.class)
    @GetMapping("/program/programType/{programTypeId}")
    public ResponseEntity getProgramForDropdown(@PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForDropdown(programTypeId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.InternalView.class)
    @PostMapping("/program/programType/{programTypeId}/filter/healthAreaAndRealmCountry")
    public ResponseEntity getProgramWithFilterForHealthAreaAndRealmCountryForDropdown(@RequestBody HealthAreaAndRealmCountryDTO input, @PathVariable(value = "programTypeId", required = true) int programTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramWithFilterForHealthAreaAndRealmCountryListForDropdown(programTypeId, input, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
    @GetMapping("/realmCountry")
    public ResponseEntity getRealmCountryDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.realmCountryService.getRealmCountryDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.InternalView.class)
    @GetMapping("/healthArea")
    public ResponseEntity getHealthAreaDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getHealthAreaDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list HealthArea", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.InternalView.class)
    @GetMapping("/organisation")
    public ResponseEntity getOrganisationDropdownList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getOrganisationDropdownList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
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
    
    @JsonView(Views.InternalView.class)
    @GetMapping("/planningUnit/programType/{programTypeId}/programId/{programId}")
    public ResponseEntity getProgramPlanningUnitDropdownList(@PathVariable(value = "programTypeId") int programTypeId, @PathVariable(value = "programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitProgramDropDownList(programTypeId, programId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list User", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
