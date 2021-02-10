/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.HealthAreaService;
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
 * @author akil
 */
@RestController
@RequestMapping("/api/healthArea")
public class HealthAreaRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HealthAreaService healthAreaService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete HealthArea list.
     *
     * @param auth
     * @return returns the complete list of HealthAreas
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete HealthArea list.", summary = "Get HealthArea list", tags = ("healthArea"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the HealthArea list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of HealthArea list")
    public ResponseEntity getHealthArea(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getHealthAreaList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the HealthArea for a specific HealthAreaId
     *
     * @param healthAreaId HealthAreaId that you want the HealthArea
     * Object for
     * @param auth
     * @return returns the HealthArea object based on
     * HealthAreaId specified
     */
    @GetMapping(value = "/{healthAreaId}")
    @Operation(description = "API used to get the HealthArea for a specific HealthAreaId", summary = "Get HealthArea for a HealthAreaId", tags = ("healthArea"))
    @Parameters(
            @Parameter(name = "healthAreaId", description = "HealthAreaId that you want to the HealthArea for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the HealthArea")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the HealthAreaId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of HealthArea")
    public ResponseEntity getHealthAreaById(@PathVariable("healthAreaId") int healthAreaId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getHealthAreaById(healthAreaId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Health Area list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the HealthArea list for a Realm
     *
     * @param realmId RealmId that you want the HealthArea List from
     * @param auth
     * @return returns the complete list of HealthAreas
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(description = "API used to get the complete HealthArea list for a Realm", summary = "Get HealthArea list for Realm", tags = ("healthArea"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the HealthArea list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the HealthArea list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of HealthArea list")
    public ResponseEntity getHealthAreaByRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getHealthAreaListByRealmId(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the HealthArea list for a RealmCountry
     *
     * @param realmCountryId RealmCountryId that you want the HealthArea
     * List from
     * @param auth
     * @return returns the complete list of HealthAreas
     */
    @GetMapping("/realmCountryId/{realmCountryId}")
    @Operation(description = "API used to get the complete HealthArea list for a RealmCountry", summary = "Get HealthArea list for RealmCountry", tags = ("healthArea"))
    @Parameters(
            @Parameter(name = "realmCountryId", description = "RealmCountryId that you want to the HealthArea list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the HealthArea list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the RealmCountryId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of HealthArea list")
    public ResponseEntity getHealthAreaByRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getHealthAreaListByRealmCountry(realmCountryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the HealthArea list that are linked to active Programs
     *
     * @param auth
     * @return returns the complete list of HealthAreas that are linked to
     * active Programs
     */
    @GetMapping("/program")
    @Operation(description = "API used to get the complete HealthArea list that are linked to active Programs", summary = "Get HealthArea list for active Programs", tags = ("healthArea"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the HealthArea list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the User has access to multiple Realms")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of HealthArea list")
    public ResponseEntity getHealthAreaByForProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            if (curUser.getRealm().getRealmId() == -1) {
                logger.error("A User with access to multiple Realms tried to access a HealthArea Program list without specifying a Realm");
                return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
            }
            return new ResponseEntity(this.healthAreaService.getHealthAreaListForProgramByRealmId(curUser.getRealm().getRealmId(), curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the HealthArea list that are linked to active Programs
     *
     * @param realmId RealmId that you want the HealthArea List for
     * @param auth
     * @return returns the complete list of HealthAreas that are linked to
     * active Programs
     */
    @GetMapping("/program/realmId/{realmId}")
    @Operation(description = "API used to get the complete HealthArea list that are linked to active Programs", summary = "Get HealthArea list for active Programs", tags = ("healthArea"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want to the HealthArea list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the HealthArea list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of HealthArea list")
    public ResponseEntity getHealthAreaForProgramByRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getHealthAreaListForProgramByRealmId(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the HealthArea by providing the display name of the
     * HealthArea and the Realm
     *
     * @param realmId RealmId that you want the HealthArea from
     * @param name Display name of the Funding source you want to get
     * @param auth
     * @return returns the complete list of HealthAreas
     */
    @GetMapping("/getDisplayName/realmId/{realmId}/name/{name}")
    @Operation(description = "API used to get the complete HealthArea by providing the display name of the HealthArea and the Realm", summary = "Get HealthArea by display name", tags = ("healthArea"))
    @Parameters({
        @Parameter(name = "realmId", description = "RealmId that you want to the HealthArea for"),
        @Parameter(name = "name", description = "Display name that you want to the HealthArea for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the HealthArea")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of HealthArea")
    public ResponseEntity getHealthAreaByDisplayName(@PathVariable("realmId") int realmId, @PathVariable("name") String name, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.healthAreaService.getDisplayName(realmId, name, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source suggested display name", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a HealthArea
     *
     * @param healthArea HealthArea object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a HealthArea", summary = "Add HealthArea", tags = ("healthArea"))
    @Parameters(
            @Parameter(name = "healthArea", description = "The HealthArea object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addHealthArea(@RequestBody HealthArea healthArea, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.healthAreaService.addHealthArea(healthArea, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Health Area", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a HealthArea
     *
     * @param healthArea HealthArea object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a HealthArea", summary = "Update HealthArea", tags = ("healthArea"))
    @Parameters(
            @Parameter(name = "healthArea", description = "The HealthArea object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the HealthAreaId supplied does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateHealhArea(@RequestBody HealthArea healthArea, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.healthAreaService.updateHealthArea(healthArea, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to update Health Area", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to update Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
