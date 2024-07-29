/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.ExportDataService;
import cc.altius.FASP.service.PlanningUnitService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
@RequestMapping("/api/export")
public class ExportApiRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private PlanningUnitService planningUnitService;
    @Autowired
    private ExportDataService exportDataService;

//    @JsonView(Views.ExportApiView.class)
//    @GetMapping("/productCatalog/programId/{programId}/versionId/{versionId}")
//    public ResponseEntity getPlanningUnitForProgram(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            SimpleProgram p = this.programService.getSimpleProgramById(programId, curUser);
//            if (p != null) {
//                if (p.getProgramTypeId() == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
//                    return new ResponseEntity<>(this.exportDataService.getPlanningUnitListForProgramId(programId, true, curUser), HttpStatus.OK);
//                } else {
//                    return new ResponseEntity<>(this.planningUnitService.getPlanningUnitListForDatasetId(programId, (versionId == -1 ? p.getCurrentVersionId() : versionId), curUser), HttpStatus.OK);
//                }
//            } else {
//                throw new EmptyResultDataAccessException(1);
//            }
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list PlanningUnit for Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list PlanningUnit for Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list PlanningUnit for Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @JsonView(Views.ExportApiView.class)
    @GetMapping("/supplyPlan/programId/{programId}/versionId/{versionId}")
    public ResponseEntity getSupplyPlanForProgram(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            SimpleProgram p = this.programService.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            if (p != null) {
                return new ResponseEntity(this.exportDataService.getSupplyPlanForProgramId(p, versionId, curUser), HttpStatus.OK);
            } else {
                // ProgramId not found
                throw new EmptyResultDataAccessException(1);
            }
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

//    @JsonView(Views.ExportApiView.class)
//    @GetMapping("/forecast/programId/{programId}/versionId/{versionId}")
//    public ResponseEntity getForecastForProgram(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            SimpleProgram p = this.programService.getSimpleProgramById(programId, curUser);
//            if (p != null) {
//                if (p.getProgramTypeId() == GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN) {
//                    return new ResponseEntity(this.planningUnitService.getPlanningUnitListForProgramId(programId, true, curUser), HttpStatus.OK);
//                } else {
//                    return new ResponseEntity(this.planningUnitService.getPlanningUnitListForDatasetId(programId, (versionId == -1 ? p.getCurrentVersionId() : versionId), curUser), HttpStatus.OK);
//                }
//            } else {
//                throw new EmptyResultDataAccessException(1);
//            }
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to list PlanningUnit for Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to list PlanningUnit for Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to list PlanningUnit for Program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
