/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DataSourceType;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.DataSourceTypeService;
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
 * @author palash
 */
@RestController
@RequestMapping("/api/dataSourceType")
public class DataSourceTypeRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSourceTypeService dataSourceTypeService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete DataSourceType list. Will only return those
     * DataSourceTypes that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active DataSourceTypes
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete DataSourceType list. Will only return those DataSourceTypes that are marked Active.", summary = "Get active DataSourceType list", tags = ("dataSourceType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSourceType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSourceType list")
    public ResponseEntity getDataSourceTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceTypeService.getDataSourceTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete DataSourceType list. Will only return those
     * DataSourceTypes that are marked Active.
     *
     * @param auth
     * @return returns the complete list of DataSourceTypes
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete DataSourceType list.", summary = "Get DataSourceType list", tags = ("dataSourceType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSourceType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSourceType list")
    public ResponseEntity getDataSourceTypeListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceTypeService.getDataSourceTypeList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the DataSourceType for a specific DataSourceTypeId
     *
     * @param dataSourceTypeId DataSourceTypeId that you want the DataSourceType
     * Object for
     * @param auth
     * @return returns the list the DataSourceType object based on
     * DataSourceTypeId specified
     */
    @GetMapping(value = "/{dataSourceTypeId}")
    @Operation(description = "API used to get the DataSourceType for a specific DataSourceTypeId", summary = "Get DataSourceType for a DataSourceTypeId", tags = ("dataSourceType"))
    @Parameters(
            @Parameter(name = "dataSourceTypeId", description = "DataSourceTypeId that you want to the DataSourceType for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSourceType")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the DataSourceTypeId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSourceType")
    public ResponseEntity getDataSourceTypeById(@PathVariable("dataSourceTypeId") int dataSourceTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceTypeService.getDataSourceTypeById(dataSourceTypeId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the DataSourceType for a specific DataSourceTypeId
     *
     * @param realmId RealmId that you want the DataSourceType Object for
     * @param auth
     * @return returns the list the DataSourceType object based on
     * DataSourceTypeId specified
     */
    @GetMapping(value = "/{realmId}")
    @Operation(description = "API used to get the DataSourceType for a specific DataSourceTypeId", summary = "Get DataSourceType for a DataSourceTypeId", tags = ("dataSourceType"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want to the DataSourceType for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSourceType")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSourceType")
    public ResponseEntity getDataSourceTypeListForRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceTypeService.getDataSourceTypeForRealm(realmId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSourceType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a DataSourceType
     *
     * @param dataSourceType DataSourceType object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a DataSourceType", summary = "Add DataSourceType", tags = ("dataSourceType"))
    @Parameters(
            @Parameter(name = "dataSourceType", description = "The DataSourceType object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addDataSourceType(@RequestBody DataSourceType dataSourceType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dataSourceTypeService.addDataSourceType(dataSourceType, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add DataSourceType", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add DataSourceType", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add DataSourceType", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a DataSourceType
     *
     * @param dataSourceType DataSourceType object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a DataSourceType", summary = "Update DataSourceType", tags = ("dataSourceType"))
    @Parameters(
            @Parameter(name = "dataSourceType", description = "The DataSourceType object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the DataSourceId does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")

    public ResponseEntity updateDataSourceType(@RequestBody DataSourceType dataSourceType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dataSourceTypeService.updateDataSourceType(dataSourceType, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update DataSourceType", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to update DataSourceType", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to update DataSourceType", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//@GetMapping(value = "/sync/dataSourceType/{lastSyncDate}")
//    public ResponseEntity getDataSourceTypeListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.dataSourceTypeService.getDataSourceTypeListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing dataSourceType", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing dataSourceType", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
