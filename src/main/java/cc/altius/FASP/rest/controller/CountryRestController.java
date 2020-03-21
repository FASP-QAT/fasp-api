/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.DTO.PrgCountryDTO;
import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.CountryService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class CountryRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    CountryService countryService;

    @GetMapping(value = "/country")
    public ResponseEntity getCountryList(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.countryService.getCountryList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.country.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/country/all")
    public ResponseEntity getCountryListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.countryService.getCountryList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list all", e);
            return new ResponseEntity(new ResponseCode("static.country.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/country/{countryId}")
    public ResponseEntity getCountryById(@PathVariable("countryId") int countryId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.countryService.getCountryById(countryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while getting country id="+countryId, er);
            return new ResponseEntity(new ResponseCode("static.country.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while getting country id="+countryId, e);
            return new ResponseEntity(new ResponseCode("static.country.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/country")
    public ResponseEntity addCountry(@RequestBody(required = true) Country country, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int countryId = this.countryService.addCountry(country, curUser);
            if (countryId > 0) {
                return new ResponseEntity(new ResponseCode("static.country.addSuccess"),HttpStatus.OK);
            } else {
                logger.error("Error while adding country no Id returned");
                return new ResponseEntity(new ResponseCode("static.country.addFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while adding country", e);
            return new ResponseEntity(new ResponseCode("static.country.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while adding country", e);
            return new ResponseEntity(new ResponseCode("static.country.addFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/country")
    public ResponseEntity editCountry(@RequestBody(required = true) Country country, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int updatedId = this.countryService.updateCountry(country, curUser);
            if (updatedId > 0) {
                return new ResponseEntity(new ResponseCode("static.country.updateSuccess"), HttpStatus.OK);
            } else {
                logger.error("Error while updating country, 0 rows updated");
                return new ResponseEntity(new ResponseCode("static.country.updateFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while updating country", e);
            return new ResponseEntity(new ResponseCode("static.country.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while updating country", e);
            return new ResponseEntity(new ResponseCode("static.country.updateFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getCountryListForSync")
    public String getCountryListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgCountryDTO> countryList = this.countryService.getCountryListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(countryList, typeList);
        return json;
    }
}
