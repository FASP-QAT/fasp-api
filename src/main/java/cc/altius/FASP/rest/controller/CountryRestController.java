/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.CountryService;
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
@RequestMapping("/api/country")
public class CountryRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CountryService countryService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete Country list. Will only return those
     * Countries that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active Countries
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete Country list. Will only return those Countries that are marked Active.", summary = "Get active Country list", tags = ("country"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Country list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Country list")
    public ResponseEntity getCountryList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.countryService.getCountryList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete Country list.
     *
     * @param auth
     * @return returns the complete list of Countries
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete Country list.", summary = "Get Country list", tags = ("country"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Country list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Country list")
    public ResponseEntity getCountryListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.countryService.getCountryList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list all", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Country for a specific CountryId
     *
     * @param countryId CountryId that you want the Country Object for
     * @param auth
     * @return returns the list the Country object based on CountryId specified
     */
    @GetMapping(value = "/{countryId}")
    @Operation(description = "API used to get the Country for a specific CountryId", summary = "Get Country for a CountryId", tags = ("country"))
    @Parameters(
            @Parameter(name = "countryId", description = "CountryId that you want to the Country for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Country")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the CountryId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Country")
    public ResponseEntity getCountryById(@PathVariable("countryId") int countryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.countryService.getCountryById(countryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while getting country id=" + countryId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while getting country id=" + countryId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a Country
     *
     * @param country Country object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a Country", summary = "Add Country", tags = ("country"))
    @Parameters(
            @Parameter(name = "country", description = "The Country object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Country Code supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addCountry(@RequestBody(required = true) Country country, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int countryId = this.countryService.addCountry(country, curUser);
            if (countryId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                logger.error("Error while adding country no Id returned");
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while adding country", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while adding country", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * API used to update a Country
     *
     * @param country Country object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a Country", summary = "Update Country", tags = ("country"))
    @Parameters(
            @Parameter(name = "country", description = "The Country object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the CountryCode supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateCountry(@RequestBody(required = true) Country country, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int updatedId = this.countryService.updateCountry(country, curUser);
            if (updatedId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
            } else {
                logger.error("Error while updating country, 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while updating country", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while updating country", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/country/{lastSyncDate}")
//    public ResponseEntity getCountryListForSync(@PathVariable("lastSyncDate") String lastSyncDate) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            return new ResponseEntity(this.countryService.getCountryListForSync(lastSyncDate), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing country", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing country", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
