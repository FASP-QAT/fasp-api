/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import cc.altius.FASP.service.TracerCategoryService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author altius
 */
@RestController
@Tag(
    name = "Tracer category",
    description = "Manage tracer categories with realm and program-specific filtering"
)
@RequestMapping("/api/tracerCategory")
public class TracerCategoryRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TracerCategoryService tracerCategoryService;
    @Autowired
    private UserService userService;

    /**
     * Add TracerCategory
     *
     * @param tracerCategory
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Tracer Category",
        description = "Add a new tracer category"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The tracer category to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TracerCategory.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding the tracer category")
    public ResponseEntity postTracerCategory(@RequestBody TracerCategory tracerCategory, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.tracerCategoryService.addTracerCategory(tracerCategory, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add TracerCategory", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update TracerCategory
     *
     * @param tracerCategory
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Tracer Category",
        description = "Update an existing tracer category"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The tracer category to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TracerCategory.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the tracer category")
    public ResponseEntity putTracerCategory(@RequestBody TracerCategory tracerCategory, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.tracerCategoryService.updateTracerCategory(tracerCategory, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add TracerCategory", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * List of TracerCategories
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @Operation(
        summary = "Get Tracer Categories",
        description = "Get all tracer categories"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TracerCategory.class))), responseCode = "200", description = "Returns the list of tracer categories")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the tracer category list")
    public ResponseEntity getTracerCategory(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Simple list of TracerCategories
     *
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/simple")
    @Operation(
        summary = "Get Tracer Categories (simplified)",
        description = "Get all tracer categories in a simplified format"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TracerCategory.class))), responseCode = "200", description = "Returns the list of tracer categories in a simplified format")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the tracer category list")
    public ResponseEntity getTracerCategorySimple(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get TracerCategory by Id
     *
     * @param tracerCategoryId
     * @param auth
     * @return
     */
    @GetMapping("/{tracerCategoryId}")
    @Operation(
        summary = "Get Tracer Category",
        description = "Get a specific Tracer Category by ID"
    )
    @Parameter(name = "tracerCategoryId", description = "The ID of the tracer category to get", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = TracerCategory.class)), responseCode = "200", description = "Returns the tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Tracer category not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the tracer category")
    public ResponseEntity getTracerCategory(@PathVariable("tracerCategoryId") int tracerCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryById(tracerCategoryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of TracerCategories for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(
        summary = "Get tracer categories",
        description = "Get tracer categories for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to get tracer categories for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TracerCategory.class))), responseCode = "200", description = "Returns the list of tracer categories for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Tracer categories not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to the realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the tracer category list")
    public ResponseEntity getTracerCategoryForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of TracerCategories for all the PU’s mapped to a SP Program
     *
     * @param realmId
     * @param programId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}/programId/{programId}")
    @Operation(
        summary = "Get Tracer Categories",
        description = "Get list of Tracer Categories for all the Planning Units mapped to a Supply Plan Program"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to get tracer categories for", required = true)
    @Parameter(name = "programId", description = "The ID of the program to get tracer categories for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TracerCategory.class))), responseCode = "200", description = "Returns the list of tracer categories for the specified realm and program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Tracer categories not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to the realm or program")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the tracer category list")
    public ResponseEntity getTracerCategoryForRealmProgram(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, programId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of TracerCategories for all the PU’s mapped to a list of SP
     * Program
     *
     * @param realmId
     * @param programIds
     * @param auth
     * @return
     */
    @PostMapping("/realmId/{realmId}/programIds")
    @Operation(
        summary = "Get Tracer Categories",
        description = "Get list of Tracer Categories for all the Planning Units mapped to a list of Supply Plan Programs"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to get tracer categories for", required = true)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs to get tracer categories for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TracerCategory.class))), responseCode = "200", description = "Returns the list of tracer categories for the specified realm and programs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Tracer categories not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to the realm or programs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the tracer category list")
    public ResponseEntity getTracerCategoryForRealmPrograms(@PathVariable("realmId") int realmId, @RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, programIds, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
