/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ProgramVersionTrans;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.ShipmentSync;
import cc.altius.FASP.model.UpdateProgramVersion;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.ActualConsumptionDataInput;
import cc.altius.FASP.model.report.LoadProgramInput;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import static cc.altius.FASP.utils.CompressUtils.compress;
import static cc.altius.FASP.utils.CompressUtils.isCompress;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
@Tag(
    name = "Program Data",
    description = "Manage program data with version control, consumption tracking, and shipment synchronization"
)
public class ProgramDataRestController {

    private final Logger logger = LoggerFactory.getLogger(ProgramDataRestController.class);

    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private ProgramService programService;
    @Autowired
    private UserService userService;

    /**
     * Get SupplyPlan Data for a ProgramId and VersionId
     *
     * @param programId
     * @param versionId
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/programData/programId/{programId}/versionId/{versionId}")
    @Operation(
        summary = "Get Supply Plan Data",
        description = "Retrieve supply plan data for a specific program and version"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve program data for", required = true)
    @Parameter(name = "versionId", description = "The ID of the version to retrieve program data for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ProgramData.class)), responseCode = "200", description = "Returns program data for a specific program and version")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting program data")
    public ResponseEntity getProgramData(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getProgramData(programId, versionId, curUser, false, false), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get SupplyPlan Data for a list of ProgramId and VersionId
     *
     * @param loadProgramInputList
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @PostMapping("/programData")
    @Operation(
        summary = "Get Supply Plan Data",
        description = "Retrieve supply plan data for a list of programs and versions"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing program data details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoadProgramInput.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ProgramData.class)), responseCode = "200", description = "Returns program data for a list of programs and versions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Program data not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting program data")
    public ResponseEntity getLoadProgramData(@RequestBody List<LoadProgramInput> loadProgramInputList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            List<ProgramData> masters = this.programDataService.getProgramData(loadProgramInputList, curUser);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(masters);
            if (isCompress(jsonString)) {
                return new ResponseEntity(compress(jsonString), HttpStatus.OK);
            } else {
                return new ResponseEntity(masters, HttpStatus.OK);
            }
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of Program Versions filtered on various parameters
     *
     * @param programId
     * @param versionId
     * @param realmCountryId
     * @param healthAreaId
     * @param organisationId
     * @param versionTypeId
     * @param versionStatusId
     * @param startDate
     * @param stopDate
     * @param auth
     * @return
     */
    @GetMapping("/programVersion/programId/{programId}/versionId/{versionId}/realmCountryId/{realmCountryId}/healthAreaId/{healthAreaId}/organisationId/{organisationId}/versionTypeId/{versionTypeId}/versionStatusId/{versionStatusId}/dates/{startDate}/{stopDate}")
    @Operation(
        summary = "Get Program Version List",
        description = "Retrieve a list of program versions based on various filters"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve program versions for", required = true)
    @Parameter(name = "versionId", description = "The ID of the version to retrieve program versions for", required = true)
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve program versions for", required = true)
    @Parameter(name = "healthAreaId", description = "The ID of the health area to retrieve program versions for", required = true)
    @Parameter(name = "organisationId", description = "The ID of the organisation to retrieve program versions for", required = true)
    @Parameter(name = "versionTypeId", description = "The ID of the version type to retrieve program versions for", required = true)
    @Parameter(name = "versionStatusId", description = "The ID of the version status to retrieve program versions for", required = true)
    @Parameter(name = "startDate", description = "The start date to retrieve program versions for", required = true)
    @Parameter(name = "stopDate", description = "The stop date to retrieve program versions for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProgramVersion.class))), responseCode = "200", description = "Returns a list of program versions based on various filters")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Program version not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting program version list")
    public ResponseEntity getProgramVersionList(
            @PathVariable(value = "programId", required = true) int programId,
            @PathVariable(value = "versionId", required = true) int versionId,
            @PathVariable(value = "realmCountryId", required = true) int realmCountryId,
            @PathVariable(value = "healthAreaId", required = true) int healthAreaId,
            @PathVariable(value = "organisationId", required = true) int organisationId,
            @PathVariable(value = "versionTypeId", required = true) int versionTypeId,
            @PathVariable(value = "versionStatusId", required = true) int versionStatusId,
            @PathVariable(value = "startDate", required = true) String startDate,
            @PathVariable(value = "stopDate", required = true) String stopDate,
            Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getProgramVersionList(programId, versionId, realmCountryId, healthAreaId, organisationId, versionTypeId, versionStatusId, startDate, stopDate, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramData", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update the status of a Program Version
     *
     * @param updateProgramVersion
     * @param programId
     * @param versionId
     * @param versionStatusId
     * @param auth
     * @return
     */
    @PutMapping("/programVersion/programId/{programId}/versionId/{versionId}/versionStatusId/{versionStatusId}")
    @Operation(
        summary = "Update Program Version",
        description = "Update the status of a program version"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing program version details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateProgramVersion.class))
    )
    @Parameter(name = "programId", description = "The ID of the program to update the version status for", required = true)
    @Parameter(name = "versionId", description = "The ID of the version to update the status for", required = true)
    @Parameter(name = "versionStatusId", description = "The ID of the version status to update the status for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Version.class)), responseCode = "200", description = "Updates the status of a program version")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Program version not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating program version")
    public ResponseEntity updateProgramVersion(@RequestBody UpdateProgramVersion updateProgramVersion, @PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, @PathVariable(value = "versionStatusId", required = true) int versionStatusId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.updateProgramVersion(programId, versionId, versionStatusId, updateProgramVersion, curUser), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Resets the Problem Reports for all the list of ProgramIds provided
     *
     * @param programIds
     * @param auth
     * @return
     */
    @PutMapping("/programVersion/resetProblem")
    @Operation(
        summary = "Reset Problem Reports for Programs",
        description = "Reset the problem reports for a list of program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs to reset the problem list for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Integer.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Resets the problem list for a list of program IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Program not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while resetting program problem list")
    public ResponseEntity resetProblemForProgramIds(@RequestBody int[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.programDataService.resetProblemListForPrograms(programIds, curUser);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to reset problem", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

//    @GetMapping("/programData/checkErpOrder/orderNo/{orderNo}/primeLineNo/{primeLineNo}/realmCountryId/{realmCountryId}/planningUnitId/{planningUnitId}")
//    public ResponseEntity checkErpOrder(
//            @PathVariable(value = "orderNo", required = true) String orderNo,
//            @PathVariable(value = "primeLineNo", required = true) String primeLineNo,
//            @PathVariable(value = "realmCountryId", required = true) int realmCountryId,
//            @PathVariable(value = "planningUnitId", required = true) int planningUnitId,
//            Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
//            return new ResponseEntity(this.programDataService.checkErpOrder(orderNo, primeLineNo, realmCountryId, planningUnitId), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to update ProgramVersion", e);
//            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * Get Shipment list for Sync
     *
     * @param programId
     * @param versionId
     * @param userId
     * @param lastSyncDate
     * @param auth
     * @return
     */
    @GetMapping("/programData/shipmentSync/programId/{programId}/versionId/{versionId}/userId/{userId}/lastSyncDate/{lastSyncDate}")
    @Operation(
        summary = "Get Shipment List for Sync",
        description = "Retrieve a list of shipments for synchronization, for given programId and versionId which are modified after given date"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve shipments for synchronization for", required = true)
    @Parameter(name = "versionId", description = "The ID of the version to retrieve shipments for synchronization for", required = true)
    @Parameter(name = "userId", description = "The ID of the user to retrieve shipments for synchronization for", required = true)
    @Parameter(name = "lastSyncDate", description = "The last synchronization date to retrieve shipments for synchronization for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ShipmentSync.class)), responseCode = "200", description = "Returns a list of shipments for synchronization")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Invalid date format")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting shipment sync list")
    public ResponseEntity shipmentSync(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId, @PathVariable(value = "userId", required = true) int userId, @PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getShipmentListForSync(programId, versionId, userId, lastSyncDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while getting Sync list for Shipments", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (Exception e) {
            logger.error("Error while getting Sync list for Shipments", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Checks if newer versions are available for a list of ProgramId and
     * VersionId s Sample JSON
     * [{"programId":2535,"versionId":3},{"programId":2001,"versionId":5}]
     *
     * @param programVersionList
     * @param auth
     * @return
     */
    @PostMapping("/programData/checkNewerVersions")
    @Operation(
        summary = "Check for Newer Versions",
        description = "Check if newer versions are available for a list of program IDs and versions"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs and versions to check for newer versions",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ProgramIdAndVersionId.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Boolean.class)), responseCode = "200", description = "Returns true if there are newer versions, false otherwise")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while checking for newer versions")
    public ResponseEntity checkNewerVersions(@RequestBody List<ProgramIdAndVersionId> programVersionList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.checkNewerVersions(programVersionList, curUser), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to check ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to check ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to check ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to check ProgramVersion", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/programData/getLatestVersionForProgram/{programId}")
    @Operation(
        summary = "Get Latest Version for Program",
        description = "Retrieve the latest version for a specific program"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve the latest version for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Integer.class)), responseCode = "200", description = "Returns the latest version for a specific program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting latest version for program")
    public ResponseEntity getLatestVersionForProgram(@PathVariable(value = "programId", required = true) int programId) {
        try {
            return new ResponseEntity(this.programService.getLatestVersionForPrograms("" + programId).get(0).getVersionId(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/programData/getLastModifiedDateForProgram/{programId}/{versionId}")
    @Operation(
        summary = "Get Last Modified Date for Program",
        description = "Retrieve the last modified date for a specific program and version"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve the last modified date for", required = true)
    @Parameter(name = "versionId", description = "The ID of the version to retrieve the last modified date for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the last modified date for a specific program and version")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting last modified date for program")
    public ResponseEntity getLastModifiedDateForProgram(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId) {
        try {
            return new ResponseEntity(this.programDataService.getLastModifiedDateForProgram(programId, versionId), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get last modified date for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get last modified date for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    // Used in Forecasting Unit import data from Supply Plan
    /**
     * <pre>
     * Sample JSON {"programVersionList": [{"programId": "2476", "versionId": "100"},{"programId": "2486", "versionId": "115"}], "planningUnitIds": ["1150", "1477", "1483"], "startDate": "2018-01-01", "stopDate":"2021-12-01", "regionIds":["70", "73", "74"]}
     * -- Program Id must be a valid Supply Plan Program Id, cannot be -1 (Any)      *
     * -- versionId must be a valid VersionId of that Program
     * -- forecastingUnitIds must be a list of ForecastingUnits whose PlanningUnits you want the Consumption data for
     * -- startDate and stopDate are required fields and indicate the start and stop dates that you want the consumption from
     * -- regionIdList is the list of regionIds that you want the data from
     * -- Return the list of Actual Consumption data for the given filters
     * </pre>
     *
     * @param ActualConsumptionDataInput
     * @param auth Authentication object from JWT
     * @return ProgramProductCatalogOutput
     */
    @JsonView(Views.ReportView.class)
    @PostMapping(value = "/program/actualConsumptionReport")
    @Operation(
        summary = "Get Actual Consumption Report",
        description = "Retrieve actual consumption data for a specific program and version"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing actual consumption data",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActualConsumptionDataInput.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns actual consumption data for a specific program and version")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting actual consumption data")
    public ResponseEntity getProgramProductCatalog(@RequestBody ActualConsumptionDataInput acd, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.programDataService.getActualConsumptionDataInput(acd, curUser), HttpStatus.OK);
        } catch (AccessControlFailedException ade) {
            logger.error("/api/program/actualConsumptionReport", ade);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException ade) {
            logger.error("/api/program/actualConsumptionReport", ade);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ade) {
            logger.error("/api/program/actualConsumptionReport", ade);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("/api/program/actualConsumptionReport", e);
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get latest ProgramId and VersionId for a list of ProgramIds
     *
     * @param programIds
     * @return
     */
    @PostMapping("/programData/getLatestVersionForPrograms")
    @Operation(
        summary = "Get Latest Version for Programs",
        description = "Retrieve the latest version for a list of programs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs to retrieve the latest version for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProgramIdAndVersionId.class))), responseCode = "200", description = "Returns the latest version for a list of programs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting latest version for program")
    public ResponseEntity getLatestVersionForProgram(@RequestBody String[] programIds) {
        try {
            String programIdsString = getProgramIds(programIds);
            return new ResponseEntity(this.programService.getLatestVersionForPrograms(programIdsString), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Gets the list of Version trans notes for a Program Id includes all
     * Versions //For (/program/data/version/trans/programId/{programId}) this
     * URL
     *
     * Gets the list of Version trans notes for a Program Id and VersionId //For
     * (/program/data/version/trans/programId/{programId}/versionId/{versionId})
     * this URL
     *
     * @param programId
     * @param versionId
     * @param auth
     * @return
     */
    @JsonView(Views.ReportView.class)
    @GetMapping({"/program/data/version/trans/programId/{programId}", "/program/data/version/trans/programId/{programId}/versionId/{versionId}"})
    @Operation(
        summary = "Get Program Version Transactions",
        description = "Retrieve transactions for a specific program and version"
    )
    @Parameter(name = "programId", description = "The ID of the program to retrieve transactions for", required = true)
    @Parameter(name = "versionId", description = "The ID of the version to retrieve transactions for", required = false)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProgramVersionTrans.class))), responseCode = "200", description = "Returns transactions for a specific program and version")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting transactions for program")
    public ResponseEntity getProgramVersionTrans(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = false) int versionId, Authentication auth) {
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        try {
            if (versionId == 0) {
                versionId = -1;
            }
            return new ResponseEntity(this.programDataService.getProgramVersionTrans(programId, versionId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get latest version for program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
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
