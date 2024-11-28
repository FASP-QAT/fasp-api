/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ManualProblem;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.ProblemStatus;
import cc.altius.FASP.service.ProblemService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api")
@Tag(
    name = "Problem",
    description = "Manage system problems and issues with support for manual entries and realm-based reporting"
)
public class ProblemRestController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserService userService;

    /**
     * Used to create a Manual Problem for a Supply Plan Program
     *
     * @param manualProblem
     * @param auth
     * @return
     */
    @PostMapping("/problemReport/createManualProblem")
    @Operation(
        summary = "Create Manual Problem",
        description = "Create a new manual problem entry for a Supply Plan Program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing problem details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ManualProblem.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Integer.class)), responseCode = "200", description = "Returns the created manual problem ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to create a manual problem entry")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No manual problem entry found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while creating a manual problem entry")
    public ResponseEntity createManualProblem(@RequestBody ManualProblem manualProblem, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.problemService.createManualProblem(manualProblem, curUser), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to get Problem list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Problem list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Problem list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Problem list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * List of Problem Stauses
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/problemStatus")
    @Operation(
        summary = "Get Problem Status List",
        description = "Retrieve a complete list of all problem statuses"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProblemStatus.class))), responseCode = "200", description = "Returns a complete list of all problem statuses")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting problem status list")
    public ResponseEntity getProblemStatusList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.problemService.getProblemStatus(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while listing problemStatus", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
