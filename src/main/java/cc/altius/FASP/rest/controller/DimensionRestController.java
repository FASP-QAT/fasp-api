/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.altius.FASP.service.DimensionService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api/dimension")
@Tag(
    name = "Dimension",
    description = "Manage system dimensions"
)
public class DimensionRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private UserService userService;

    /**
     * Add Dimension
     *
     * @param dimension
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Dimension",
        description = "Create a new dimension"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The dimension to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dimension.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "The dimension with the given name already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the addition of the dimension")
    public ResponseEntity postDimension(@RequestBody Dimension dimension, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.dimensionService.addDimension(dimension, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Dimension", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update Dimension
     *
     * @param dimension
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Dimension",
        description = "Update an existing dimension"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The dimension to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Dimension.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dimension with the specified ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "The dimension with the given name already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the update of the dimension")
    public ResponseEntity putDimension(@RequestBody Dimension dimension, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.dimensionService.updateDimension(dimension, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Dimension ", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to update Dimension ", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active Dimensions
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @Operation(
        summary = "Get Active Dimensions",
        description = "Retrieve a list of all active dimensions"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Dimension.class))), responseCode = "200", description = "Returns the list of all active dimensions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dimension list")
    public ResponseEntity getDimension(Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionList(true), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all Dimensions
     *
     * @param auth
     * @return
     */
    @GetMapping("/all")
    @Operation(
        summary = "Get Dimensions",
        description = "Retrieve a complete list of all dimensions (active and disabled)"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Dimension.class))), responseCode = "200", description = "Returns the complete list of all dimensions (active and disabled)")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dimension list")
    public ResponseEntity getDimensionAll(Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionList(false), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }



    /**
     * Get Dimension by Id
     *
     * @param dimensionId
     * @param auth
     * @return
     */
    @GetMapping("/{dimensionId}")
    @Operation(
        summary = "Get Dimension",
        description = "Retrieve a dimension by its ID"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Dimension.class)), responseCode = "200", description = "Returns the dimension with the specified ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The dimension with the specified ID was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the dimension")
    public ResponseEntity getDimension(@PathVariable("dimensionId") int dimensionId, Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionById(dimensionId), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
