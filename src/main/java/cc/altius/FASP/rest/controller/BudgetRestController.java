/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.BudgetService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/budget")
@Tag(
    name = "Budget",
    description = "Manages program budgets for a realm"
)
public class BudgetRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private UserService userService;

    /**
     * Add Budget
     *
     * @param budget
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Budget",
        description = "Create a new budget for the realm"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The budget to create",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Budget.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the creation of the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Duplicate key error that prevented the creation of the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have access to the funding source or currency referenced by the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Unable to find the funding source or currency referenced by the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")

    public ResponseEntity postBudget(@RequestBody Budget budget, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.budgetService.addBudget(budget, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailedDuplicate"), HttpStatus.NOT_ACCEPTABLE); //406
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); //403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_FOUND); //404
        } catch (Exception e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }

    /**
     * Update Budget
     *
     * @param budget
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Budget",
        description = "Update an existing budget for a realm"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The budget to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Budget.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the update of the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have access to the funding source or currency referenced by the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Unable to find the funding source or currency referenced by the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Duplicate name error that prevented the update of the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")
    public ResponseEntity putBudget(@RequestBody Budget budget, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int rows = this.budgetService.updateBudget(budget, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT); // 409
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailedDuplicate"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to update Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to update Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * GetBudgetList for ProgramIds
     *
     * @param programIds
     * @param auth
     * @return
     */
    @PostMapping("/programIds")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Budgets by Programs",
        description = "Retrieve a list of budgets for a given list of program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs to retrieve budgets for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Budget.class))), responseCode = "200", description = "Returns the list of budgets for the given program IDs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the budget list")
    public ResponseEntity getBudget(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.budgetService.getBudgetListForProgramIds(programIds, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get Budget list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Budget List
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Budgets",
        description = "Retrieve a complete list of all budgets"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Budget.class))), responseCode = "200", description = "Returns the complete list of budgets")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the budget list")
    public ResponseEntity getBudget(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.budgetService.getBudgetList(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get Budget list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Budget for Id
     *
     * @param budgetId
     * @param auth
     * @return
     */
    @GetMapping("/{budgetId}")
    @Operation(
        summary = "Get Budget",
        description = "Retrieve a specific budget by its ID"
    )
    @Parameter(name = "budgetId", description = "The ID of the budget to retrieve")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Budget.class)), responseCode = "200", description = "Returns the specific budget by its ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Unable to find the budget by its ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have access to the budget")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "409", description = "The user has partial acccess to the request")

    public ResponseEntity getBudget(@PathVariable("budgetId") int budgetId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.budgetService.getBudgetById(budgetId, curUser), HttpStatus.OK); // 200
        } catch (AccessControlFailedException ae) {
            logger.error("Error while trying to get Budget Id=" + budgetId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT); // 409
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get Budget Id=" + budgetId, erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get Budget Id=" + budgetId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Budget Id=" + budgetId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Budgets for Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Budgets by Realm",
        description = "Retrieve a list of budgets for a given realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve budgets for")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Budget.class))), responseCode = "200", description = "Returns the list of budgets for the given realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retrieval of the budget list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Unable to find the budget list for the given realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have access to the budget list for the given realm")
    public ResponseEntity getBudgetForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.budgetService.getBudgetListForRealm(realmId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get Budget list", erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get Budget list", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Budget list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
