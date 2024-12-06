/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DataSource;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.DataSourceService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @author palash
 */
@RestController
@RequestMapping("/api/dataSource")
@Tag(
    name = "Data Source",
    description = "Manage data sources with filtering by realm, program, and source type"
)
public class DataSourceRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private UserService userService;

    /**
     * Add DataSource
     *
     * @param dataSource
     * @param auth
     * @return
     */
    @PostMapping(value = "")
    @Operation(
        summary = "Add Data Source",
        description = "Create a new data source"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The data source to create",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataSource.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success message")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "The data source with the given name already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to add this data source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Unable to find the data referenced in the data source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the creation of the data source")
    public ResponseEntity addDataSource(@RequestBody DataSource dataSource, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.dataSourceService.addDataSource(dataSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            // FIMXE: This is not the correct status code for this exception, it should be 403
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active DataSources
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "")
    @Operation(
        summary = "Get Active Data Sources",
        description = "Retrieve a list of all active data sources"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DataSource.class))), responseCode = "200", description = "Returns the list of active data sources")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the data sources")
    public ResponseEntity getDataSourceList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dataSourceService.getDataSourceList(true, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all DataSources
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/all")
    @Operation(
        summary = "Get All Data Sources",
        description = "Retrieve a complete list of all data sources (active and disabled)"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DataSource.class))), responseCode = "200", description = "Returns the complete list of data sources")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the data sources")
    public ResponseEntity getDataSourceListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dataSourceService.getDataSourceList(false, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get DataSource by Id
     *
     * @param dataSourceId
     * @param auth
     * @return
     */
    @GetMapping(value = "/{dataSourceId}")
    @Operation(
        summary = "Get Data Source",
        description = "Retrieve a data source by its ID"
    )
    @Parameter(name = "dataSourceId", description = "The ID of the data source to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = DataSource.class)), responseCode = "200", description = "Returns the data source with the specified ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this data source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The data source with the specified ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the data source")
    public ResponseEntity getDataSourcebyId(@PathVariable("dataSourceId") int dataSourceId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dataSourceService.getDataSourceById(dataSourceId, curUser), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get DataSource list for a specific ProgramId
     *
     * @param realmId
     * @param programId
     * @param auth
     * @return
     */
    @GetMapping(value = "/realmId/{realmId}/programId/{programId}")
    @Operation(
        summary = "Get Data Source for Realm and Program",
        description = "Retrieve a list of data sources for a specific realm and program"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve data sources for")
    @Parameter(name = "programId", description = "The ID of the program to retrieve data sources for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DataSource.class))), responseCode = "200", description = "Returns the list of data sources for the specified realm and program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this data source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The data source with the specified realm and program was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the data source")
    public ResponseEntity getDataSourceListForRealmIdProgramId(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dataSourceService.getDataSourceForRealmAndProgram(realmId, programId, true, curUser), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            // FIMXE: This is not the correct status code for this exception, it should be 403 (409 conflict should not be thrown in a GET request)
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT); // 409
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get DataSource list by DataSourceTypeId
     *
     * @param dataSourceTypeId
     * @param auth
     * @return
     */
    @GetMapping(value = "/dataSourceTypeId/{dataSourceTypeId}")
    @Operation(
        summary = "Get Data Source by Type",
        description = "Retrieve a list of data sources for a specific data source type"
    )
    @Parameter(name = "dataSourceTypeId", description = "The ID of the data source type to retrieve data sources for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = DataSource.class))), responseCode = "200", description = "Returns the list of data sources for the specified data source type")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to access this data source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The data source with the specified data source type was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the data source")
    public ResponseEntity getDataSourceListForDataSourceTypeId(@PathVariable("dataSourceTypeId") int dataSourceTypeId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.dataSourceService.getDataSourceForDataSourceType(dataSourceTypeId, true, curUser), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to get DataSource list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }


    /**
     * Update DataSource
     *
     * @param dataSource
     * @param auth
     * @return
     */
    @PutMapping(value = "")
    @Operation(
        summary = "Update Data Source",
        description = "Update an existing data source"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The data source to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataSource.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success message")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to update this data source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "The data source with the given name already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "There was a conflict while updating the data source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the update of the data source")
    public ResponseEntity editDataSource(@RequestBody DataSource dataSource, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.dataSourceService.updateDataSource(dataSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            // FIMXE: This is not the correct status code for this exception, it should be 403
            logger.error("Error while trying to add DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to update DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to update DataSource", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
