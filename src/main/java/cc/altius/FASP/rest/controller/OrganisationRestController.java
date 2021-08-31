/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.OrganisationService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
 * @author altius
 */
@RestController
@RequestMapping("/api/organisation")
public class OrganisationRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete Organisation list.
     *
     * @param auth
     * @return returns the complete list of Organisations
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete Organisation list.", summary = "Get Organisation list", tags = ("organisation"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Organisation list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Organisation list")
    public ResponseEntity getOrganisation(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getOrganisationList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Organisation for a specific OrganisationId
     *
     * @param organisationId OrganisationId that you want the Organisation
     * Object for
     * @param auth
     * @return returns the list the Organisation object based on OrganisationId
     * specified
     */
    @GetMapping(value = "/{organisationId}")
    @Operation(description = "API used to get the Organisation for a specific OrganisationId", summary = "Get Organisation for a OrganisationId", tags = ("organisation"))
    @Parameters(
            @Parameter(name = "organisationId", description = "OrganisationId that you want to the Organisation for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Organisation")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the OrganisationId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Organisation")
    public ResponseEntity getOrganisation(@PathVariable("organisationId") int organisationId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getOrganisationById(organisationId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Organisation list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get Organisation list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Organisation list for a Realm
     *
     * @param realmId RealmId that you want the Funding Source List from
     * @param auth
     * @return returns the complete list of Organisations
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(description = "API used to get the complete Organisation list for a Realm", summary = "Get Organisation list for Realm", tags = ("organisation"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the Organisation list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Organisation list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Organisation list")
    public ResponseEntity getOrganisationByRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getOrganisationListByRealmId(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Organisation list for a RealmCountry
     *
     * @param realmCountryId RealmCountryId that you want the Funding Source
     * List from
     * @param auth
     * @return returns the complete list of Organisations
     */
    @GetMapping("/realmCountryId/{realmCountryId}")
    @Operation(description = "API used to get the complete Organisation list for a RealmCountry", summary = "Get Organisation list for RealmCountry", tags = ("organisation"))
    @Parameters(
            @Parameter(name = "realmCountryId", description = "RealmCountryId that you want to the Organisation list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Organisation list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the RealmCountryId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Organisation list")
    public ResponseEntity getOrganisationByRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getOrganisationListByRealmCountry(realmCountryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while trying to get Organisation list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Organisation by providing the display name of the
     * Organisation and the Realm
     *
     * @param realmId RealmId that you want the Funding Source from
     * @param name Display name of the Funding source you want to get
     * @param auth
     * @return returns the complete list of Organisations
     */
    @GetMapping("/getDisplayName/realmId/{realmId}/name/{name}")
    @Operation(description = "API used to get the complete Organisation by providing the display name of the Organisation and the Realm", summary = "Get Organisation by display name", tags = ("organisation"))
    @Parameters({
        @Parameter(name = "realmId", description = "RealmId that you want to the Organisation for"),
        @Parameter(name = "name", description = "Display name that you want to the Organisation for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Organisation")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Organisation")
    public ResponseEntity getOrganisationDisplayName(@PathVariable("realmId") int realmId, @PathVariable("name") String name, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.organisationService.getDisplayName(realmId, name, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source suggested display name", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add an Organisation
     *
     * @param organisation Organisation object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add an Organisation", summary = "Add Organisation", tags = ("organisation"))
    @Parameters(
            @Parameter(name = "organisation", description = "The Organisation object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addOrganisation(@RequestBody Organisation organisation, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.organisationService.addOrganisation(organisation, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update an Organisation
     *
     * @param organisation Organisation object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update an Organisation", summary = "Update Organisation", tags = ("organisation"))
    @Parameters(
            @Parameter(name = "organisation", description = "The Organisation object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the OrganisationId supplied does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateOrganisation(@RequestBody Organisation organisation, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.organisationService.updateOrganisation(organisation, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to update Organisation", ae);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to update Organisation", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/organisation/{lastSyncDate}")
//    public ResponseEntity getOrganisatiionListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.organisationService.getOrganisationListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing organisation", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing organisation", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
