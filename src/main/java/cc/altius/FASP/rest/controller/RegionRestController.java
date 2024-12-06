/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.RegionService;
import cc.altius.FASP.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 *
 * @author altius
 */
@RestController
@Tag(
    name = "Region",
    description = "Manage region entities within the system"
)
@RequestMapping("/api/region")
public class RegionRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegionService regionService;
    @Autowired
    private UserService userService;

    /**
     * Add or Update Regions
     *
     * @param regions
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Regions",
        description = "Add or update multiple regions in the system"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The regions to update",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Region.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update regions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Region already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating regions")
    public ResponseEntity putRegion(@RequestBody Region[] regions, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.regionService.saveRegions(regions, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update Region", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update Region", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update Region", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to update Region", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of Regions
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @Operation(
        summary = "Get Regions",
        description = "Retrieve a list of regions"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Region.class))), responseCode = "200", description = "Returns the list of regions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list regions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting region list")
    public ResponseEntity getRegion(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.regionService.getRegionList(curUser), HttpStatus.OK);
        } catch (DataAccessException e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Region by Id
     *
     * @param regionId
     * @param auth
     * @return
     */
    @GetMapping("/{regionId}")
    @Operation(
        summary = "Get a Region",
        description = "Retrieve a region by its ID"
    )
    @Parameter(name = "regionId", description = "The ID of the region to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Region.class)), responseCode = "200", description = "Returns the region")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to get a region")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Region not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting region")
    public ResponseEntity getRegion(@PathVariable("regionId") int regionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.regionService.getRegionById(regionId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (DataAccessException e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Regions for a RealmCountry
     *
     * @param realmCountryId
     * @param auth
     * @return
     */
    @GetMapping("/realmCountryId/{realmCountryId}")
    @Operation(
        summary = "Get Regions by Realm Country",
        description = "Retrieve a list of regions by their realm country ID"
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve regions for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Region.class))), responseCode = "200", description = "Returns the list of regions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list regions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Region not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting region list")
    public ResponseEntity getRegionByRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.regionService.getRegionListByRealmCountryId(realmCountryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DataAccessException e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Region", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
