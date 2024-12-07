/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.report.DashboardBottom;
import cc.altius.FASP.model.report.DashboardForLoadProgram;
import cc.altius.FASP.model.report.DashboardInput;
import cc.altius.FASP.model.report.DashboardTop;
import cc.altius.FASP.service.DashboardService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Map;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/dashboard")
@Tag(
    name = "Dashboard",
    description = "Get dashboard metrics across different administrative levels"
)
public class DashboardRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private RealmService realmService;

    /**
     * Dashboard information for Application level users
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/applicationLevel")
    @Operation(
        summary = "Get Application Level Dashboard",
        description = "Retrieve a dashboard summary for application-level administrators"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns the dashboard summary for application-level administrators")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dashboard summary")
    public ResponseEntity applicationLevelDashboard(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dashboardService.getApplicationLevelDashboard(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Dashboard information for Realm level users
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/realmLevel")
    @Operation(
        summary = "Get Realm Level Dashboard",
        description = "Retrieve a dashboard summary for realm-level administrators"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns the dashboard summary for realm-level administrators")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dashboard summary")
    public ResponseEntity realmLevelDashboard(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dashboardService.getRealmLevelDashboard(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }
    
    /**
     * Dashboard information for Supply Plan
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/supplyPlanReviewerLevel")
    @Operation(
        summary = "Get Supply Plan Reviewer Level Dashboard",
        description = "Retrieve a dashboard summary for supply plan reviewers"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns the dashboard summary for supply plan reviewers")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dashboard summary")
    public ResponseEntity supplyPlanReviewerLevelDashboard(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dashboardService.getSupplyPlanReviewerLevelDashboard(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * User count for Application level Users
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/applicationLevel/userList")
    @Operation(
        summary = "Get Application Level Dashboard User List",
        description = "Retrieve a list of users for application-level administrators"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DashboardUser.class))), responseCode = "200", description = "Returns the list of users for application-level administrators")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the user list")
    public ResponseEntity applicationLevelDashboardUserList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dashboardService.getUserListForApplicationLevelAdmin(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * User count for Realm level Users
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/realmLevel/userList")
    @Operation(
        summary = "Get Realm Level Dashboard User List",
        description = "Retrieve a list of users for realm-level administrators"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DashboardUser.class))), responseCode = "200", description = "Returns the list of users for realm-level administrators")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the user list")
    public ResponseEntity realmLevelDashboardUserList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            logger.info("curUser realmLevelDashboardUserList", curUser);
            return new ResponseEntity(this.dashboardService.getUserListForRealmLevelAdmin(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    @PostMapping(value = "/supplyPlanTop")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Supply Plans (top))",
        description = "Retrieve a dashboard summary for the top part of the supply plan dashboard"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The program IDs for the supply plans to retrieve",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(type = "string")))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DashboardTop.class))), responseCode = "200", description = "Returns the dashboard summary for supply plans")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to the supply plans")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The supply plans were not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dashboard summary")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    public ResponseEntity getDashboardTop(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dashboardService.getDashboardTop(programIds, curUser), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to get dashboard supply plan top", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get dashboard supply plan top", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get dashboard supply plan top", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);     
        }
    }

    @PostMapping(value = "/supplyPlanBottom")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Supply Plans (bottom)",
        description = "Retrieve a dashboard summary for the bottom part of the supply plan dashboard"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The dashboard input for the supply plans to retrieve",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DashboardInput.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = DashboardBottom.class)), responseCode = "200", description = "Returns the dashboard summary for supply plans bottom")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to the supply plans")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The supply plans were not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dashboard summary")
    public ResponseEntity getDashboardBottom(@RequestBody DashboardInput ei, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dashboardService.getDashboardBottom(ei, curUser), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to get dashboard supply plan bottom", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get dashboard supply plan bottom", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get dashboard supply plan bottom", ae);
            return new ResponseEntity(HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get dashboard supply plan bottom", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = "/db/{programId}/{versionId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Dashboard for Load Program",
        description = "Retrieve a dashboard summary for the load program"
    )
    @Parameter(name = "programId", description = "The program ID for the program to retrieve")
    @Parameter(name = "versionId", description = "The version ID for the program to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = DashboardForLoadProgram.class)), responseCode = "200", description = "Returns the dashboard")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to the program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The program was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dashboard")
    public ResponseEntity getDashboardForLoadProgram(@PathVariable("programId") Integer programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            Program p = this.programService.getFullProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            Realm r = this.realmService.getRealmById(curUser.getRealm().getRealmId(), curUser);
            int noOfMonthsInPastForBottom, noOfMonthsInFutureForBottom, noOfMonthsInPastForTop = r.getNoOfMonthsInPastForTopDashboard(), noOfMonthsInFutureForTop = r.getNoOfMonthsInFutureForTopDashboard();
            if (p.getNoOfMonthsInPastForBottomDashboard() == null) {
                noOfMonthsInPastForBottom = r.getNoOfMonthsInPastForBottomDashboard();
            } else {
                noOfMonthsInPastForBottom = p.getNoOfMonthsInPastForBottomDashboard();
            }
            if (p.getNoOfMonthsInFutureForBottomDashboard() == null) {
                noOfMonthsInFutureForBottom = r.getNoOfMonthsInFutureForBottomDashboard();
            } else {
                noOfMonthsInFutureForBottom = p.getNoOfMonthsInFutureForBottomDashboard();
            }
            return new ResponseEntity(this.dashboardService.getDashboardForLoadProgram(programId, versionId, noOfMonthsInPastForBottom, noOfMonthsInFutureForBottom, noOfMonthsInPastForTop, noOfMonthsInFutureForTop, curUser), HttpStatus.OK);
        } catch (AccessControlFailedException ae) {
            logger.error("Error while getting Dashboard", ae);
            return new ResponseEntity(HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while getting Dashboard", ae);
            return new ResponseEntity(HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while getting Dashboard", ae);
            return new ResponseEntity(HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while getting Dashboard", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
