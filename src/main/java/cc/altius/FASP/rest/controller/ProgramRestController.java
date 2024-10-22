/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramPlanningUnitProcurementAgentInput;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api")
public class ProgramRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProgramService programService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProcurementAgentService procurementAgentService;
    @Autowired
    private RealmCountryService realmCountryService;

    /**
     * Update Program
     *
     * @param program
     * @param auth
     * @return
     */
    @PutMapping(path = "/program")
    public ResponseEntity putProgram(@RequestBody ProgramInitialize program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            program.setProgramTypeId(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN); // Supply Plan Program
            this.programService.updateProgram(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/program/programIds")
//    public ResponseEntity getProgram(@RequestBody String[] programIds, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
//            return new ResponseEntity(this.programService.getProgramListForProgramIds(programIds, curUser), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * Get list of active SP Programs
     *
     * @param auth
     * @return
     */
    @GetMapping("/program")
    public ResponseEntity getProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of all SP Programs
     *
     * @param auth
     * @return
     */
    @GetMapping("/program/all")
    public ResponseEntity getProgramAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, false), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of active PU’s mapped to a SP Program
     *
     * @param programId
     * @param auth
     * @return
     */
    @GetMapping("/program/{programId}/planningUnit")
    public ResponseEntity getPlanningUnitForProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramId(programId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of PU’s mapped to a SP Program filtered by TracerCateogryIds
     *
     * @param programId
     * @param tracerCategoryIds
     * @param auth
     * @return
     */
    @PostMapping("/program/{programId}/tracerCategory/planningUnit")
    public ResponseEntity getPlanningUnitForProgramTracerCategory(@PathVariable("programId") int programId, @RequestBody String[] tracerCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramIdAndTracerCategoryIds(programId, true, tracerCategoryIds, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Simple list of PU’s mapped to a SP Program filtered by
     * TracerCateogryIds
     *
     * @param programId
     * @param tracerCategoryIds
     * @param auth
     * @return
     */
    @PostMapping("/program/{programId}/tracerCategory/simple/planningUnit")
    public ResponseEntity getSimplePlanningUnitForProgramTracerCategory(@PathVariable("programId") int programId, @RequestBody String[] tracerCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getSimplePlanningUnitListForProgramIdAndTracerCategoryIds(programId, true, tracerCategoryIds, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of all PU’s mapped to a SP Program
     *
     * @param programId
     * @param auth
     * @return
     */
    @GetMapping("/program/{programId}/planningUnit/all")
    public ResponseEntity getPlanningUnitForProgramAll(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramId(programId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add and Update Planning Units for a Program
     *
     * @param ppu
     * @param auth
     * @return
     */
    @PutMapping("/program/planningUnit")
    public ResponseEntity savePlanningUnitForProgram(@RequestBody ProgramPlanningUnit[] ppu, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.programService.saveProgramPlanningUnit(ppu, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Procurement Agent specific data for Program Planning Unit
     *
     * @param ppupa
     * @param auth
     * @return
     */
    // List of Programs and List of PlanningUnitIds instead of single select
    @PostMapping("/program/planningUnit/procurementAgent/")
    public ResponseEntity getProgramPlanningUnitProcurementAgent(@RequestBody ProgramPlanningUnitProcurementAgentInput ppupa, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramPlanningUnitProcurementAgentList(ppupa, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get ProgramPrice list for Program Planning Unit Procurement Agent", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramPrice list for Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update Procurement Agent specific data for Program Planning Unit
     *
     * @param programPlanningUnitProcurementAgentPrices
     * @param auth
     * @return
     */
//    Allow for -1 in PlanningUnit
    @PutMapping("/program/planningUnit/procurementAgent")
    public ResponseEntity saveProgramPlanningUnitProcurementAgentPrices(@RequestBody ProgramPlanningUnitProcurementAgentPrice[] programPlanningUnitProcurementAgentPrices, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.programService.saveProgramPlanningUnitProcurementAgentPrice(programPlanningUnitProcurementAgentPrices, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update ProgramPlanningUnit ProcurementAgent Prices", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProgramPlanningUnit ProcurementAgent Prices", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to update ProgramPlanningUnit ProcurementAgent Prices", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Simple list of PU’s mapped to a list of SP Programs
     *
     * @param programIds
     * @param auth
     * @return
     */
    @PostMapping("/planningUnit/programs")
    public ResponseEntity getPlanningUnitForProgramList(@RequestBody Integer[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramIds(programIds, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            logger.error("Error while trying to get PlanningUnit list for Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to get PlanningUnit list for Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @JsonView(Views.DropDownView.class)
    @PostMapping("/programAndPlanningUnit/programs")
    public ResponseEntity getProgramAndPlanningUnitForProgramList(@RequestBody Integer[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramAndPlanningUnitListForProgramIds(programIds, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            logger.error("Error while trying to get PlanningUnit list for Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to get PlanningUnit list for Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of SP Programs for a RealmId
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/program/realmId/{realmId}")
    public ResponseEntity getProgramForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getProgramListForRealmId(realmId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get SP Program based on ID
     *
     * @param programId
     * @param auth
     * @return
     */
    @GetMapping("/program/{programId}")
    public ResponseEntity getProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            Program p = this.programService.getFullProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            ProgramInitialize pi = new ProgramInitialize(p);
            pi.setFundingSources(this.programService.getFundingSourceIdsForProgramId(programId, curUser));
            pi.setProcurementAgents(this.programService.getProcurementAgentIdsForProgramId(programId, curUser));
            return new ResponseEntity(pi, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of all PU’s mapped to a SP Program filtered by Product Category
     *
     * @param programId
     * @param productCategoryId
     * @param auth
     * @return
     */
    @GetMapping("/program/{programId}/{productCategory}/planningUnit/all")
    public ResponseEntity getPlanningUnitForProgramAndProductCategory(@PathVariable("programId") int programId, @PathVariable("productCategory") int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramAndCategoryId(programId, productCategoryId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Setup a new SP program
     *
     * @param program
     * @param auth
     * @return
     */
    @PostMapping(path = "/program/initialize")
    public ResponseEntity postProgramInitialize(@RequestBody ProgramInitialize program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.programService.addProgramInitialize(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Program", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the list of all SP Programs for the Load Program page
     *
     * @param auth
     * @return
     */
    @GetMapping("/loadProgram")
    @JsonView(Views.InternalView.class)
    public ResponseEntity getLoadProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            Map<String, Object> params = new HashMap<>();
            params.put("realmCountryList", this.realmCountryService.getRealmCountryListByRealmIdForActivePrograms(curUser.getRealm().getRealmId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser));
            params.put("programList", this.programService.getLoadProgram(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser));
            return new ResponseEntity(params, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets the Version list for a specific Program for the Loan Program page
     *
     * @param programId
     * @param page
     * @param auth
     * @return
     */
    @GetMapping("/loadProgram/programId/{programId}/page/{page}")
    @JsonView(Views.InternalView.class)
    public ResponseEntity getLoadProgram(@PathVariable("programId") int programId, @PathVariable("page") int page, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getLoadProgram(programId, page, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Used to confirm if the ProgarmCode is not a duplicate for this Realm
     *
     * @param realmId
     * @param programId
     * @param programCode
     * @param auth
     * @return
     */
    /*
    * returns true if the ProgramCode is not present and is a valid entry
    * returns false if the ProgramCode exists and cannot be used again
     */
//    @GetMapping("program/validate/realmId/{realmId}/programId/{programId}/programCode/{programCode}")
//    public ResponseEntity validateProgramCode(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, @PathVariable("programCode") String programCode, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
//            return new ResponseEntity(this.programService.validateProgramCode(realmId, programId, programCode, curUser), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Programs", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Programs", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("program/supplyPlanReviewer/programId/{programId}")
//    public ResponseEntity getSupplyPlanReviewerListForProgram(@PathVariable("programId") int programId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
//            return new ResponseEntity(this.programService.getSupplyPlanReviewerList(programId, curUser), HttpStatus.OK);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Programs", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Programs", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * Get Simple list of Programs for list fo a RealmCountries
     *
     * @param realmCountryIds
     * @param auth
     * @return
     */
    @PostMapping("/program/realmCountryList")
    public ResponseEntity getProgramListByRealmCountryIdList(@RequestBody String[] realmCountryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getSimpleProgramListByRealmCountryIdList(realmCountryIds, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get Simple list of Programs for list fo a ProductCategories
     *
     * @param productCategoryIds
     * @param auth
     * @return
     */
    @PostMapping("/program/productCategoryList")
    public ResponseEntity getProgramListByProductCategoryIdList(@RequestBody String[] productCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programService.getSimpleProgramListByProductCategoryIdList(productCategoryIds, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

//    @PostMapping("/program/{programId}/updateProcurementAgents")
//    public ResponseEntity updateProcurementAgentsForProgram(@PathVariable("programId") int programId, @RequestBody Integer[] procurementAgentIds, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
//            return new ResponseEntity(this.procurementAgentService.updateProcurementAgentsForProgram(programId, procurementAgentIds, curUser), HttpStatus.OK);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to update Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.updatedFailed"), HttpStatus.FORBIDDEN);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to updated Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.updatedFailed"), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            logger.error("Error while trying to update Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.updatedFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/program/{programId}/updateProcurementAgents")
//    @JsonView(Views.InternalView.class)
//    public ResponseEntity getDataForUpdateProcurementAgentsForProgram(@PathVariable("programId") int programId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
//            Map<String, Object> outputData = new HashMap<>();
//            outputData.put("program", this.programService.getSimpleSupplyPlanProgramByProgramId(programId, curUser));
//            outputData.put("selectedProcurementAgentList", this.procurementAgentService.getProcurementAgentDropdownListForFilterMultiplePrograms(Integer.toString(programId), curUser));
//            outputData.put("procurementAgentList", this.procurementAgentService.getProcurementAgentDropdownList(curUser));
//            return new ResponseEntity(outputData, HttpStatus.OK);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
