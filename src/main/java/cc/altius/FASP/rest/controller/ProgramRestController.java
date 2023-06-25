/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
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
    private RealmCountryService realmCountryService;

    @PostMapping(path = "/program")
    public ResponseEntity postProgram(@RequestBody Program program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            program.setProgramTypeId(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN); // Supply Plan Program
            this.programService.addProgram(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
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

    @PutMapping(path = "/program")
    public ResponseEntity putProgram(@RequestBody Program program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            program.setProgramTypeId(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN); // Supply Plan Program
            this.programService.updateProgram(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
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

    @PostMapping("/program/programIds")
    public ResponseEntity getProgram(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForProgramIds(programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/program")
    public ResponseEntity getProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/program/all")
    public ResponseEntity getProgramAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, false), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/program/{programId}/planningUnit")
    public ResponseEntity getPlanningUnitForProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @PostMapping("/program/{programId}/tracerCategory/planningUnit")
    public ResponseEntity getPlanningUnitForProgramTracerCategory(@PathVariable("programId") int programId, @RequestBody String[] tracerCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @PostMapping("/program/{programId}/tracerCategory/simple/planningUnit")
    public ResponseEntity getSimplePlanningUnitForProgramTracerCategory(@PathVariable("programId") int programId, @RequestBody String[] tracerCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @GetMapping("/program/{programId}/planningUnit/all")
    public ResponseEntity getPlanningUnitForProgramAll(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @PutMapping("/program/planningUnit")
    public ResponseEntity savePlanningUnitForProgram(@RequestBody ProgramPlanningUnit[] ppu, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.saveProgramPlanningUnit(ppu, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
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

    @GetMapping("/program/planningUnit/procurementAgent/{programPlanningUnitId}")
    public ResponseEntity getProgramPlanningUnitProcurementAgent(@PathVariable("programPlanningUnitId") int programPlanningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramPlanningUnitProcurementAgentList(programPlanningUnitId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get ProgramPrice list for Program Planning Unit Id" + programPlanningUnitId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramPrice list for Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/program/planningingUnit/procurementAgent")
    public ResponseEntity saveProgramPlanningUnitProcurementAgentPrices(@RequestBody ProgramPlanningUnitProcurementAgentPrice[] programPlanningUnitProcurementAgentPrices, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.saveProgramPlanningUnitProcurementAgentPrice(programPlanningUnitProcurementAgentPrices, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProgramPlanningUnit ProcurementAgent Prices", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to update ProgramPlanningUnit ProcurementAgent Prices", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/planningUnit/programs")
    public ResponseEntity getPlanningUnitForProgramList(@RequestBody Integer[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @GetMapping("/program/realmId/{realmId}")
    public ResponseEntity getProgramForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @GetMapping("/program/{programId}")
    public ResponseEntity getProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser), HttpStatus.OK);
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

//    @GetMapping(value = "/sync/program/{lastSyncDate}")
//    public ResponseEntity getProgramListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getProgramListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing program", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping(value = "/sync/programPlanningUnit/{lastSyncDate}")
//    public ResponseEntity getProgramPlanningUnitListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userServiceApplicationConfiguration.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getProgramPlanningUnitListForSyncProgram(getProgramIds(new String[]{"2030"}), curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing program planning unit", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing program planning unit", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @GetMapping("/program/{programId}/{productCategory}/planningUnit/all")
    public ResponseEntity getPlanningUnitForProgramAndProductCategory(@PathVariable("programId") int programId, @PathVariable("productCategory") int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @PostMapping(path = "/program/initialize")
    public ResponseEntity postProgramInitialize(@RequestBody ProgramInitialize program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.addProgramInitialize(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
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

//    @PostMapping("/getShipmentDetailsByParentShipmentId")
//    public ResponseEntity getShipmentDetailsByParentShipmentId(@RequestBody ManualTaggingDTO manualTaggingDTO, Authentication auth) {
////        System.out.println("parentShipmentId--------" + manualTaggingDTO);
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getShipmentDetailsByParentShipmentId(manualTaggingDTO.getParentShipmentId()), HttpStatus.OK);
////            return new ResponseEntity("", HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/manualTagging")
//    public ResponseEntity getShipmentListForManualTagging(@RequestBody ManualTaggingDTO manualTaggingDTO, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getShipmentListForManualTagging(manualTaggingDTO, curUser), HttpStatus.OK);
////            return new ResponseEntity("", HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 1---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 2---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 3---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/shipmentLinkingNotification")
//    public ResponseEntity shipmentLinkingNotification(@RequestBody ERPNotificationDTO eRPNotificationDTO, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getNotificationList(eRPNotificationDTO), HttpStatus.OK);
////            return new ResponseEntity("", HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/updateNotification")
//    public ResponseEntity updateNotification(@RequestBody ERPNotificationDTO[] eRPNotificationDTO, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            for (ERPNotificationDTO e : eRPNotificationDTO) {
////                System.out.println("e-------------------*********************" + e);
//                this.programService.updateNotification(e, curUser);
//            }
//            this.programDataService.getNewSupplyPlanList(eRPNotificationDTO[0].getProgramId(), -1, true, false);
//            return new ResponseEntity(true, HttpStatus.OK);
////            return new ResponseEntity("", HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            e.printStackTrace();
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            e.printStackTrace();
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/getNotificationCount")
//    public ResponseEntity getNotificationCount(Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getNotificationCount(curUser), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/getNotificationSummary")
//    public ResponseEntity getNotificationSummary(Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getNotificationSummary(curUser), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/manualTagging/notLinkedShipments/{programId}/{linkingType}")
//    public ResponseEntity getNotLinkedShipmentListForManualTagging(@PathVariable("programId") int programId, @PathVariable("linkingType") int linkingType, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getNotLinkedShipments(programId, linkingType), HttpStatus.OK);
////            return new ResponseEntity("", HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/artmisHistory/{orderNo}/{primeLineNo}")
//    public ResponseEntity artmisHistory(@PathVariable("orderNo") String orderNo, @PathVariable("primeLineNo") int primeLineNo, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getARTMISHistory(orderNo, primeLineNo), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/searchErpOrderData/{term}/{programId}/{erpPlanningUnitId}/{linkingType}")
//    public ResponseEntity searchErpOrderData(@PathVariable("term") String term, @PathVariable("programId") int programId, @PathVariable("erpPlanningUnitId") int planningUnitId, @PathVariable("linkingType") int linkingType, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getErpOrderSearchData(term, programId, planningUnitId, linkingType), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/orderDetails/{roNoOrderNo}/{programId}/{erpPlanningUnitId}/{linkingType}/{parentShipmentId}")
//    public ResponseEntity getOrderDetailsByOrderNoAndPrimeLineNo(@PathVariable("roNoOrderNo") String roNoOrderNo, @PathVariable("programId") int programId, @PathVariable("erpPlanningUnitId") int planningUnitId, @PathVariable("linkingType") int linkingType, @PathVariable("parentShipmentId") int parentShipmentId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            if (linkingType == 3) {
//                return new ResponseEntity(this.programService.getOrderDetailsByForNotLinkedERPShipments(roNoOrderNo, planningUnitId, linkingType), HttpStatus.OK);
//            } else {
//                return new ResponseEntity(this.programService.getOrderDetailsByOrderNoAndPrimeLineNo(roNoOrderNo, programId, planningUnitId, linkingType, parentShipmentId), HttpStatus.OK);
//            }
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("ERP Linking : Error while trying to get order details for Manual Tagging 1---", e);
//            return new ResponseEntity(new ResponseCode("static.mt.noDetailsFound"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("ERP Linking : Error while trying to get order details for Manual Tagging 2---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("ERP Linking : Error while trying to  get order details for Manual Tagging 3---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/linkShipmentWithARTMIS/")
//    @Transactional
//    public ResponseEntity linkShipmentWithARTMIS(@RequestBody ManualTaggingOrderDTO[] erpOrderDTO, Authentication auth) {
//        try {
////            System.out.println("erpOrderDTO%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + Arrays.toString(erpOrderDTO));
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            List<Integer> result = this.programService.linkShipmentWithARTMIS(erpOrderDTO, curUser);
//            logger.info("ERP Linking : Going to get new supply plan list ");
//            CommitRequest s = new CommitRequest();
//            SimpleCodeObject program = new SimpleCodeObject();
//            program.setId(erpOrderDTO[0].getProgramId());
//            s.setProgram(program);
//            s.setCommittedVersionId(-1);
//            s.setSaveData(false);
//            s.setNotes("ERP Linking Supply Plan Rebuild");
//            int commitRequestId = this.programDataService.addSupplyPlanCommitRequest(s,curUser);
////            this.programDataService.getNewSupplyPlanList(erpOrderDTO[0].getProgramId(), -1, true, false);
//            logger.info("ERP Linking : supply plan rebuild done ");
//            return new ResponseEntity(result, HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("ERP Linking : Linking error 1---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("ERP Linking : Linking error 2---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("ERP Linking : Linking error 3---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/shipmentListForDelinking/{programId}/{planningUnitId}")
//    public ResponseEntity getShipmentListForDelinking(@PathVariable("programId") int programId, @PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getShipmentListForDelinking(programId, planningUnitId), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PostMapping("/delinkShipment/")
//    public ResponseEntity delinkShipment(@RequestBody ManualTaggingOrderDTO erpOrderDTO, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            this.programService.delinkShipment(erpOrderDTO, curUser);
//            logger.info("ERP Linking : Going to get new supply plan list ");
//            this.programDataService.getNewSupplyPlanList(erpOrderDTO.getProgramId(), -1, true, false);
//            logger.info("ERP Linking : new supply plan rebuild done ");
//            return new ResponseEntity(new ResponseCode("success"), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("ERP Linking : Delinking error 1---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("ERP Linking : Delinking error 2---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("ERP Linking : Delinking error 3---", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @GetMapping("loadProgram")
    @JsonView(Views.InternalView.class)
    public ResponseEntity getLoadProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    @GetMapping("loadProgram/programId/{programId}/page/{page}")
    @JsonView(Views.InternalView.class)
    public ResponseEntity getLoadProgram(@PathVariable("programId") int programId, @PathVariable("page") int page, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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

    /*
    * returns true if the ProgramCode is not present and is a valid entry
    * returns false if the ProgramCode exists and cannot be used again
     */
    @GetMapping("program/validate/realmId/{realmId}/programId/{programId}/programCode/{programCode}")
    public ResponseEntity validateProgramCode(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, @PathVariable("programCode") String programCode, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.validateProgramCode(realmId, programId, programCode, curUser), HttpStatus.OK);
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

    @GetMapping("program/supplyPlanReviewer/programId/{programId}")
    public ResponseEntity getSupplyPlanReviewerListForProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getSupplyPlanReviewerList(programId, curUser), HttpStatus.OK);
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
}
