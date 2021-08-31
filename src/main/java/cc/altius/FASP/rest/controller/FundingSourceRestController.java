/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.FundingSourceService;
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
@RequestMapping("/api/fundingSource")
public class FundingSourceRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FundingSourceService fundingSourceService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete FundingSource list.
     *
     * @param auth
     * @return returns the complete list of FundingSources
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete FundingSource list.", summary = "Get FundingSource list", tags = ("fundingSource"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the FundingSource list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of FundingSource list")
    public ResponseEntity getFundingSource(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the FundingSource for a specific FundingSourceId
     *
     * @param fundingSourceId FundingSourceId that you want the FundingSource
     * Object for
     * @param auth
     * @return returns the list the FundingSource object based on
     * FundingSourceId specified
     */
    @GetMapping(value = "/{fundingSourceId}")
    @Operation(description = "API used to get the FundingSource for a specific FundingSourceId", summary = "Get FundingSource for a FundingSourceId", tags = ("fundingSource"))
    @Parameters(
            @Parameter(name = "fundingSourceId", description = "FundingSourceId that you want to the FundingSource for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the FundingSource")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the FundingSourceId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of FundingSource")
    public ResponseEntity getFundingSource(@PathVariable("fundingSourceId") int fundingSourceId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceById(fundingSourceId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to get Funding source Id=" + fundingSourceId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get Funding source Id=" + fundingSourceId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source Id=" + fundingSourceId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the FundingSource by providing the display name of the
     * FundingSource and the Realm
     *
     * @param realmId RealmId that you want the Funding Source from
     * @param name Display name of the Funding source you want to get
     * @param auth
     * @return returns the complete list of FundingSources
     */
    @GetMapping("/getDisplayName/realmId/{realmId}/name/{name}")
    @Operation(description = "API used to get the complete FundingSource by providing the display name of the FundingSource and the Realm", summary = "Get FundingSource by display name", tags = ("fundingSource"))
    @Parameters({
        @Parameter(name = "realmId", description = "RealmId that you want to the FundingSource for"),
        @Parameter(name = "name", description = "Display name that you want to the FundingSource for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the FundingSource")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of FundingSource")
    public ResponseEntity getFundingSourceByDisplayName(@PathVariable("realmId") int realmId, @PathVariable("name") String name, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.fundingSourceService.getDisplayName(realmId, name, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source suggested display name", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the FundingSource list for a Realm
     *
     * @param realmId RealmId that you want the FundingSource List from
     * @param auth
     * @return returns the complete list of FundingSources
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(description = "API used to get the complete FundingSource list for a Realm", summary = "Get FundingSource list for Realm", tags = ("fundingSource"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want to the FundingSource for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the FundingSource list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of FundingSource list")
    public ResponseEntity getFundingSourceForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.fundingSourceService.getFundingSourceList(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to get Funding source for Realm, RealmId=" + realmId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get Funding source for Realm, RealmId=" + realmId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a FundingSource
     *
     * @param fundingSource FundingSource object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a FundingSource", summary = "Add FundingSource", tags = ("fundingSource"))
    @Parameters(
            @Parameter(name = "fundingSource", description = "The FundingSource object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addFundingSource(@RequestBody FundingSource fundingSource, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.fundingSourceService.addFundingSource(fundingSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Funding source", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Funding source", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a FundingSource
     *
     * @param fundingSource FundingSource object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a FundingSource", summary = "Update FundingSource", tags = ("fundingSource"))
    @Parameters(
            @Parameter(name = "fundingSource", description = "The FundingSource object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the FundingSourceId supplied does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateFundingSource(@RequestBody FundingSource fundingSource, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.fundingSourceService.updateFundingSource(fundingSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Funding source", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Funding source", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to update Funding source", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to update Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/fundingSource/{lastSyncDate}")
//    public ResponseEntity getFundingSourceListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.fundingSourceService.getFundingSourceListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing fundingSource", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing fundingSource", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
