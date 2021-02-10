/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.BudgetService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/budget")
public class BudgetRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete Budget list
     *
     * @param auth
     * @return returns the complete list of Budgets List<Budget>
     */
    @GetMapping("/")
    @Operation(description = "Returns the complete list of Budgets List<Budget>", summary = "Get Budget list", tags = ("budget"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Budget list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Budget list")
    public ResponseEntity getBudgetList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.budgetService.getBudgetList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Budget list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a Budget to the Realm
     *
     * @param budget Budget object that you want to add to the Realm
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(path = "/")
    @Operation(description = "API used to add a Budget to the Realm", summary = "Add Budget", tags = ("budget"))
    @Parameters(
            @Parameter(name = "budget", description = "The Budget object that you want to add to the Realm"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to add the Budget")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match. For instance the Funding Source Id specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addBudget(@RequestBody Budget budget, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.budgetService.addBudget(budget, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a Budget
     *
     * @param budget Budget object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a Budget", summary = "Update Budget", tags = ("budget"))
    @Parameters(
            @Parameter(name = "budget", description = "The Budget object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to add the Budget")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateBudget(@RequestBody Budget budget, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int rows = this.budgetService.updateBudget(budget, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Budget list for a list of Program Ids
     *
     * @param programIds List of ProgramIds that you want to the list of Budgets
     * for
     * @param auth
     * @return returns the list of Budgets based on Program Ids specified
     */
    @PostMapping("/programIds")
    @Operation(description = "API used to get the Budget list for a list of Program Ids", summary = "Get Budget list for Program Ids", tags = ("budget"))
    @Parameters(
            @Parameter(name = "programIds", description = "List of Program Ids that you want to the Budgets for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Budget list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Budget list")
    public ResponseEntity getBudgetForProgramIds(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.budgetService.getBudgetListForProgramIds(programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Budget list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Budget for a specific BudgetId
     *
     * @param budgetId BudgetId that you want the Budget Object for
     * @param auth
     * @return returns the list the Budget object based on BudgetId specified
     */
    @GetMapping("/{budgetId}")
    @Operation(description = "API used to get the Budget for a specific BudgetId", summary = "Get Budget for a BudgetId", tags = ("budget"))
    @Parameters(
            @Parameter(name = "budgetId", description = "BudgetId that you want to the Budget for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Budget")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Budget")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the BudgetId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Budget")
    public ResponseEntity getBudgetById(@PathVariable("budgetId") int budgetId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.budgetService.getBudgetById(budgetId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get Budget Id=" + budgetId, erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get Budget Id=" + budgetId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Budget Id=" + budgetId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get all the Budgets for a specific Realm
     *
     * @param realmId RealmId that you want the List of Budgets for
     * @param auth
     * @return returns the list the Budgets based on RealmId specified
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(description = "API used to get the Budget for a specific BudgetId", summary = "Get Budget for a Realm", tags = ("budget"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the List of Budgets for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the List of Budgets for that Realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to the Realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Budget")
    public ResponseEntity getBudgetForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.budgetService.getBudgetListForRealm(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get Budget list", erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get Budget list", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Budget list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
