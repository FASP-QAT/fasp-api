/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.DataSourceService;
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
@RequestMapping("/api/dataSource")
public class DataSourceRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete DataSource list. Will only return those
     * DataSources that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active DataSources
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete DataSource list. Will only return those DataSources that are marked Active.", summary = "Get active DataSource list", tags = ("dataSource"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSource list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSource list")
    public ResponseEntity getDataSourceList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceService.getDataSourceList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete DataSource list. Will only return those
     * DataSources that are marked Active.
     *
     * @param auth
     * @return returns the complete list of DataSources
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete DataSource list.", summary = "Get DataSource list", tags = ("dataSource"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSource list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSource list")
    public ResponseEntity getDataSourceListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceService.getDataSourceList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the DataSource for a specific DataSourceId
     *
     * @param dataSourceId DataSourceId that you want the DataSource Object for
     * @param auth
     * @return returns the list the DataSource object based on DataSourceId
     * specified
     */
    @GetMapping(value = "/{dataSourceId}")
    @Operation(description = "API used to get the DataSource for a specific DataSourceId", summary = "Get DataSource for a DataSourceId", tags = ("dataSource"))
    @Parameters(
            @Parameter(name = "dataSourceId", description = "DataSourceId that you want to the DataSource for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSource")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm or Program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the DataSourceId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSource")
    public ResponseEntity getDataSourceById(@PathVariable("dataSourceId") int dataSourceId, Authentication auth) {

        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceService.getDataSourceById(dataSourceId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the DataSource for a specific RealmId and ProgramId
     *
     * @param realmId RealmId that you want the DataSource Object for
     * @param programId ProgramId that you want the DataSource Object for
     * @param auth
     * @return returns the list the DataSource object based on RealmId and
     * ProgramId specified
     */
    @GetMapping(value = "/realmId/{realmId}/programId/{programId}")
    @Operation(description = "API used to get the DataSource for a specific RealmId and ProgramId", summary = "Get DataSource for RealmId and ProgramId", tags = ("dataSource"))
    @Parameters(
            {
                @Parameter(name = "realmIdId", description = "RealmIdId that you want to the DataSource for"),
                @Parameter(name = "programIdId", description = "ProgramId that you want to the DataSource for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSource")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm or Program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the DataSourceId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSource")

    public ResponseEntity getDataSourceListForRealmIdProgramId(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceService.getDataSourceForRealmAndProgram(realmId, programId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the DataSource for a specific DataSourceTypeId
     *
     * @param dataSourceTypeId datasourceTypeId that you want the DataSource
     * Object for
     * @param auth
     * @return returns the list the DataSource object based on DataSourceId
     * specified
     */
    @GetMapping(value = "/dataSourceTypeId/{dataSourceTypeId}")
    @Operation(description = "API used to get the DataSource for a specific DataSourceId", summary = "Get DataSource for a DataSourceId", tags = ("dataSource"))
    @Parameters(
            @Parameter(name = "dataSourceTypeId", description = "DataSourceTypeId that you want to the DataSource for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the DataSource")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the DataSourceId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of DataSource")

    public ResponseEntity getDataSourceListForDataSourceTypeId(@PathVariable("dataSourceTypeId") int dataSourceTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.dataSourceService.getDataSourceForDataSourceType(dataSourceTypeId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a DataSource
     *
     * @param dataSource DataSource object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a DataSource", summary = "Add DataSource", tags = ("dataSource"))
    @Parameters(
            @Parameter(name = "dataSource", description = "The DataSource object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm or Program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the DataSource Code supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addDataSource(@RequestBody DataSource dataSource, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dataSourceService.addDataSource(dataSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a DataSource
     *
     * @param dataSource DataSource object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a DataSource", summary = "Update DataSource", tags = ("dataSource"))
    @Parameters(
            @Parameter(name = "dataSource", description = "The DataSource object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm or Program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the DataSourceCode supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateDataSource(@RequestBody DataSource dataSource, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dataSourceService.updateDataSource(dataSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to update DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/dataSource/{lastSyncDate}")
//    public ResponseEntity getDataSourceListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.dataSourceService.getDataSourceListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing dataSource", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing dataSource", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
