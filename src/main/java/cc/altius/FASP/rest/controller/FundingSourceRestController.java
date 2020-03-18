/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.FundingSourceService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class FundingSourceRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FundingSourceService fundingSourceService;

    @GetMapping(value = "/getFundingSourceListForSync")
    public String getFundingSourceListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgFundingSourceDTO> fundingSourceList = this.fundingSourceService.getFundingSourceListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(fundingSourceList, typeList);
        return json;
    }

    @PostMapping(path = "/fundingSource")
    public ResponseEntity postFundingSource(@RequestBody FundingSource fundingSource, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.fundingSourceService.addFundingSource(fundingSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.addSucccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Funding source", ae);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.addFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to add Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/fundingSource")
    public ResponseEntity putFundingSource(@RequestBody FundingSource fundingSource, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.fundingSourceService.updateFundingSource(fundingSource, curUser);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.updateSucccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Funding source", ae);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.updateFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to update Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fundingSource")
    public ResponseEntity getFundingSource(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.fundingSourceService.getFundingSourceList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/fundingSource/realmId/{realmId}")
    public ResponseEntity getFundingSourceForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.fundingSourceService.getFundingSourceList(realmId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fundingSource/{fundingSourceId}")
    public ResponseEntity getFundingSource(@PathVariable("fundingSourceId") int fundingSourceId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.fundingSourceService.getFundingSourceById(fundingSourceId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to get Funding source", ae);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.listNotFound"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source", e);
            return new ResponseEntity(new ResponseCode("static.message.fundingSource.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
