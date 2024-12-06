/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.exception.InvalidDataException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.service.ExportDataService;
import cc.altius.FASP.service.PlanningUnitService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonView;
import java.text.ParseException;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@Controller
@RequestMapping("/api/export")
@Tag(
    name = "Export API",
    description = "Export supply plan data"
)
public class ExportApiRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private PlanningUnitService planningUnitService;
    @Autowired
    private ExportDataService exportDataService;

//    @JsonView(Views.ExportApiView.class)
//    @GetMapping("/productCatalog/programId/{programId}/versionId/{versionId}")
//    public ResponseEntity getPlanningUnitForProgram(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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
    @GetMapping(value = {
        "/supplyPlan/programId/{programId}/versionId/{versionId}",
        "/supplyPlan/programId/{programId}/versionId/{versionId}/",
        "/supplyPlan/programId/{programId}/versionId/{versionId}/startDate/{startDate}"})
    @Operation(
        summary = "Get Supply Plan Data",
        description = "Retrieve supply plan data for a specific program and version, with optional start date filtering"
    )
    @Parameter(name = "startDate", description = "The start date for the supply plan data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = SupplyPlan.class)), responseCode = "200", description = "Returns the supply plan data for the specified program and version, with optional start date filtering")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have rights to the Program requested")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the supply plan data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Data is invalid")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Specified program or version does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    public ResponseEntity getSupplyPlanForProgram(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, @PathVariable(value = "startDate", required = false) String startDate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.exportDataService.getSupplyPlanForProgramId(programId, versionId, startDate, curUser), HttpStatus.OK);
        } catch (InvalidDataException ie) {
            logger.error(ie.getMessage(), ie);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (ParseException pe) {
            logger.error("Incorrect format for startDate provided", pe);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to get Export of Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Export of Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Export of Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Export of Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

//    @JsonView(Views.ExportApiView.class)
//    @GetMapping("/dataset/programId/{programId}/versionId/{versionId}")
//    public ResponseEntity getForecastForProgram(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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
