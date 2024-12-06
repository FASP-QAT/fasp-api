/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.HealthAreaService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/api/healthArea")
@Tag(
    name = "Health Area",
    description = "Manages health area definitions"
)
public class HealthAreaRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HealthAreaService healthAreaService;
    @Autowired
    private UserService userService;

    /**
     * Add HealthArea
     *
     * @param healthArea
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Health Area",
        description = "Add a new health area."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The HealthArea to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HealthArea.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to add this object")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Health area already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding health area")
    public ResponseEntity postHealthArea(@RequestBody HealthArea healthArea, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.healthAreaService.addHealthArea(healthArea, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Health Area", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update HealthArea
     *
     * @param healthArea
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Health Area",
        description = "Update an existing health area."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The HealthArea to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = HealthArea.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success message")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update this object")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Health area not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Another health area with the same key exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating health area")
    public ResponseEntity putHealhArea(@RequestBody HealthArea healthArea, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.healthAreaService.updateHealthArea(healthArea, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to update Health Area", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to update Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active HealthAreas
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @Operation(
        summary = "Get Health Areas",
        description = "Retrieve a complete list of all health areas."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = HealthArea.class))), responseCode = "200", description = "Returns the list of HealthAreas")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting health area list")
    public ResponseEntity getHealthArea(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.healthAreaService.getHealthAreaList(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of HealthAreas for a RealmCountry
     *
     * @param realmCountryId
     * @param auth
     * @return
     */
    @GetMapping("/realmCountryId/{realmCountryId}")
    @Operation(
        summary = "Get Health Areas for Realm Country",
        description = "Retrieve a list of health areas for a given realm country, identified by its ID."
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve health areas for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = HealthArea.class))), responseCode = "200", description = "Returns the list of HealthAreas")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Health area not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting health area list")
    public ResponseEntity getHealthAreaByRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.healthAreaService.getHealthAreaListByRealmCountry(realmCountryId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

//    @GetMapping("/healthArea/program/realmId/{realmId}")
//    public ResponseEntity getHealthAreaForRealmCountry(@PathVariable("realmId") int realmId, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.healthAreaService.getHealthAreaForActiveProgramsList(realmId, curUser), HttpStatus.OK);
//        } catch (EmptyResultDataAccessException e) {
//            logger.error("Error while trying to get Health Area list", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to get Health Area list", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to get Health Area list", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**
     * Get list of HealthAreas for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(
        summary = "Get Health Areas for Realm",
        description = "Retrieve a list of health areas for a given realm, identified by its ID."
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve health areas for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = HealthArea.class))), responseCode = "200", description = "Returns the list of HealthAreas")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Health area not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting health area list")
    public ResponseEntity getHealthAreaByRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.healthAreaService.getHealthAreaListByRealmId(realmId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get HealthArea by Id
     *
     * @param healthAreaId
     * @param auth
     * @return
     */
    @GetMapping("/{healthAreaId}")
    @Operation(
        summary = "Get Health Area",
        description = "Retrieve a health area by its ID."
    )
    @Parameter(name = "healthAreaId", description = "The ID of the health area to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = HealthArea.class)), responseCode = "200", description = "Returns the HealthArea for the given ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Health area not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this health area")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting health area")
    public ResponseEntity getHealthArea(@PathVariable("healthAreaId") int healthAreaId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.healthAreaService.getHealthAreaById(healthAreaId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Health Area list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get HealthArea list for Programs based on the Realm Id of the Logged in
     * user
     *
     * @param auth
     * @return
     */
    @GetMapping("/program")
    @Operation(
        summary = "Get Health Areas for Program",
        description = "Retrieve a list of health areas that are associated with active programs."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = HealthArea.class))), responseCode = "200", description = "Returns the list of HealthAreas")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Health areas not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "User tried to access a HealthArea Program list without specifying a Realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting health area list")
    public ResponseEntity getHealthAreaByForProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            if (curUser.getRealm().getRealmId() == -1) {
                logger.error("A User with access to multiple Realms tried to access a HealthArea Program list without specifying a Realm");
                return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
            }
            return new ResponseEntity(this.healthAreaService.getHealthAreaListForProgramByRealmId(curUser.getRealm().getRealmId(), curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get HealthArea list for Programs based on the Realm Id provided
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/program/realmId/{realmId}")
    @Operation(
        summary = "Get Health Areas for Program by Realm",
        description = "Retrieve a complete list of health areas that are associated with the given realm, identified by its ID."
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve health areas for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = HealthArea.class))), responseCode = "200", description = "Returns the list of HealthAreas")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Health area not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting health area list")
    public ResponseEntity getHealthAreaForProgramByRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.healthAreaService.getHealthAreaListForProgramByRealmId(realmId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Check if HealthArea Display name exists in the same Realm
     *
     * @param realmId
     * @param name
     * @param auth
     * @return
     */
    @GetMapping("/getDisplayName/realmId/{realmId}/name/{name}")
    @Operation(
        summary = "Get Health Area by Display Name",
        description = "Retrieve the health area based on the given display name and realm, identified by its ID."
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve health areas for")
    @Parameter(name = "name", description = "The display name of the health area to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the HealthArea for the given display name and realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting health area")
    public ResponseEntity getHealthAreaDisplayName(@PathVariable("realmId") int realmId, @PathVariable("name") String name, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.healthAreaService.getDisplayName(realmId, name, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source suggested display name", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
