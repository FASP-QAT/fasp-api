/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.CurrencyService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.servlet.http.HttpServletRequest;
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

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api/currency")

public class CurrencyRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete Currency list. Will only return those
     * Currencies that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active Currencies
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete Currency list. Will only return those Currencies that are marked Active.", summary = "Get active Currency list", tags = ("currency"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Currency list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Currency list")
    public ResponseEntity getCurrency(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.currencyService.getCurrencyList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete Currency list. Will only return those
     * Currencies that are marked Active.
     *
     * @param auth
     * @return returns the complete list of Currencies
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete Currency list.", summary = "Get Currency list", tags = ("currency"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Currency list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Currency list")
    public ResponseEntity getCurrencyListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.currencyService.getCurrencyList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Currency for a specific CurrencyId
     *
     * @param currencyId CurrencyId that you want the Currency Object for
     * @param auth
     * @return returns the list the Currency object based on CurrencyId specified
     */
    @GetMapping(value = "/{currencyId}")
    @Operation(description = "API used to get the Currency for a specific CurrencyId", summary = "Get Currency for a CurrencyId", tags = ("currency"))
    @Parameters(
            @Parameter(name = "currencyId", description = "CurrencyId that you want to the Currency for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Currency")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the CurrencyId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Currency")
    public ResponseEntity getCurrencyById(@PathVariable("currencyId") int currencyId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.currencyService.getCurrencyById(currencyId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a Currency
     *
     * @param currency Currency object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a Currency", summary = "Add Currency", tags = ("currency"))
    @Parameters(
            @Parameter(name = "currency", description = "The Currency object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Currency Code supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addCurrency(@RequestBody Currency currency, Authentication auth, HttpServletRequest request) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.currencyService.addCurrency(currency, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add Currency", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Currency", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        /**
     * API used to update a Currency
     *
     * @param currency Currency object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a Currency", summary = "Update Currency", tags = ("currency"))
    @Parameters(
            @Parameter(name = "currency", description = "The Currency object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the CurrencyCode supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateCurrency(@RequestBody Currency currency, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.currencyService.updateCurrency(currency, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update Currency", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to update Currency", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/currency/{lastSyncDate}")
//    public ResponseEntity getCurrencyListForSync(@PathVariable("lastSyncDate") String lastSyncDate) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            return new ResponseEntity(this.currencyService.getCurrencyListForSync(lastSyncDate), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing currency", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing currency", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
