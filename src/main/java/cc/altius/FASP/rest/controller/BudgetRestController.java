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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class BudgetRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BudgetService budgetService;

    @PostMapping(path = "/budget")
    public ResponseEntity postBudget(@RequestBody Budget budget, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.budgetService.addBudget(budget, curUser);
            return new ResponseEntity(new ResponseCode("static.message.budget.addSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to add Budget", e);
            return new ResponseEntity(new ResponseCode("static.message.budget.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/budget")
    public ResponseEntity putBudget(@RequestBody Budget budget, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.budgetService.updateBudget(budget, curUser);
            return new ResponseEntity(new ResponseCode("static.message.budget.updateSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.budget.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/budget")
    public ResponseEntity getBudget(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.budgetService.getBudgetList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.budget.notFound"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/budget/{budgetId}")
    public ResponseEntity getBudget(@PathVariable("budgetId") int budgetId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.budgetService.getBudgetById(budgetId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException erda) {
            return new ResponseEntity(new ResponseCode("static.message.budget.notFound"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.message.budget.notFound"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
