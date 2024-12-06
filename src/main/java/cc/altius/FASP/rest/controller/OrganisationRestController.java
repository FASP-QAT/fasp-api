/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.OrganisationService;
import cc.altius.FASP.service.UserService;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/organisation")
@Tag(
    name = "Organisation",
    description = "Manage organizations with realm-based access control"
)
public class OrganisationRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private UserService userService;

    /**
     * Add an Organisation
     *
     * @param organisation
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Organisation",
        description = "Add a new organisation"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The organisation data to add",
        required = true,
        content = @Content(schema = @Schema(implementation = Organisation.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to add this object")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Organisation already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding organisation")
    public ResponseEntity postOrganisation(@RequestBody Organisation organisation, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.organisationService.addOrganisation(organisation, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update an Organisation
     *
     * @param organisation
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Organisation",
        description = "Update an existing organisation"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The organisation data to update",
        required = true,
        content = @Content(schema = @Schema(implementation = Organisation.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update this object")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Another organisation with the same key exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating organisation")
    public ResponseEntity putOrganisation(@RequestBody Organisation organisation, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.organisationService.updateOrganisation(organisation, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to update Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to update Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all Organisations
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @Operation(
        summary = "Get Organisation list",
        description = "Retrieve a list of organisations"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Organisation.class))), responseCode = "200", description = "Returns a list of organisations")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting organisation list")
    public ResponseEntity getOrganisation(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.organisationService.getOrganisationList(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Organisation by Id
     *
     * @param organisationId
     * @param auth
     * @return
     */
    @GetMapping("/{organisationId}")
    @Operation(
        summary = "Get Organisation",
        description = "Retrieve an organisation by its ID"
    )
    @Parameter(name = "organisationId", description = "The ID of the organisation to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Organisation.class)), responseCode = "200", description = "Returns an organisation")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Organisation not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting organisation")
    public ResponseEntity getOrganisation(@PathVariable("organisationId") int organisationId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.organisationService.getOrganisationById(organisationId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Organisation list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get Organisation list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Check if Organisation Display name exists in the same Realm
     *
     * @param realmId
     * @param name
     * @param auth
     * @return
     */
    @GetMapping("/getDisplayName/realmId/{realmId}/name/{name}")
    @Operation(
        summary = "Get Organisation Display Name",
        description = "Retrieve an organisation's funding source suggested display name"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve the organisation from")
    @Parameter(name = "name", description = "The name of the organisation to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns an organisation's funding source suggested display name")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting organisation display name")
    public ResponseEntity getOrganisationDisplayName(@PathVariable("realmId") int realmId, @PathVariable("name") String name, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.organisationService.getDisplayName(realmId, name, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source suggested display name", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
