/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Supplier;
import cc.altius.FASP.model.ResponseCode;
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
import cc.altius.FASP.service.SupplierService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author altius
 */
@RestController
@Tag(
    name = "Supplier",
    description = "Manage supplier data with realm-specific access control"
)
@RequestMapping("/api/supplier")
public class SupplierRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private UserService userService;

    /**
     * Add Supplier
     *
     * @param supplier
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Supplier",
        description = "Add a new supplier to the system"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The supplier to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Supplier.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding the supplier")
    public ResponseEntity postSupplier(@RequestBody Supplier supplier, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.supplierService.addSupplier(supplier, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Supplier", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add Supplier", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update Supplier
     *
     * @param supplier
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Supplier",
        description = "Update an existing supplier in the system"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The supplier to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Supplier.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the supplier")
    public ResponseEntity putSupplier(@RequestBody Supplier supplier, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.supplierService.updateSupplier(supplier, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Supplier", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add Supplier", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Supplier list
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @Operation(
        summary = "Get all Suppliers",
        description = "Retrieve a list of all suppliers"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Supplier.class))), responseCode = "200", description = "Returns the list of suppliers")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the supplier list")
    public ResponseEntity getSupplier(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.supplierService.getSupplierList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Supplier list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Supplier based on Id
     *
     * @param supplierId
     * @param auth
     * @return
     */
    @GetMapping("/{supplierId}")
    @Operation(
        summary = "Get Supplier",
        description = "Retrieve a supplier by their ID"
    )
    @Parameter(
        name = "supplierId",
        description = "The ID of the supplier to retrieve",
        required = true
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Supplier.class)), responseCode = "200", description = "Returns the supplier with the specified ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Supplier not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to this supplier")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the supplier")
    public ResponseEntity getSupplier(@PathVariable("supplierId") int supplierId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.supplierService.getSupplierById(supplierId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Supplier list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get Supplier list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Supplier list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Supplier list for Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(
        summary = "Get Suppliers for Realm",
        description = "Retrieve a list of suppliers for a specific realm"
    )
    @Parameter(
        name = "realmId",
        description = "The ID of the realm to retrieve suppliers for",
        required = true
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Supplier.class))), responseCode = "200", description = "Returns the list of suppliers for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No suppliers found for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have access to this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the supplier list")
    public ResponseEntity getSupplierForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.supplierService.getSupplierListForRealm(realmId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Supplier list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get Supplier list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Supplier list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
