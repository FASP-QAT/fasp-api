/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgCurrencyDTO;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.CurrencyService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
 * @author palash
 */
@RestController
@RequestMapping("/api")

public class CurrencyRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CurrencyService currencyService;

    @PostMapping(value = "/currency")
    public ResponseEntity addCurrency(@RequestBody Currency currency, Authentication auth, HttpServletRequest request) {
        try {
            System.out.println("From the mapping="+currency.getCurrencySymbol());
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.currencyService.addCurrency(currency, curUser);
            return new ResponseEntity("static.message.addSuccess", HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add Currency", e);
            return new ResponseEntity("static.message.addFailed", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Currency", e);
            return new ResponseEntity("static.message.addFailed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/currency")
    public ResponseEntity getCurrencyList(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.currencyService.getCurrencyList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/currency/{currencyId}")
    public ResponseEntity getCurrencyList(@PathVariable("currencyId") int currencyId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.currencyService.getCurrencyById(currencyId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/currency/all")
    public ResponseEntity getCurrencyListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.currencyService.getCurrencyList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Currency list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/currency")
    public ResponseEntity editCurrency(@RequestBody Currency currency, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
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

    @GetMapping(value = "/getCurrencyListForSync")
    public String getCurrencyListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgCurrencyDTO> currencyList = this.currencyService.getCurrencyListForSync(lastSyncDate);
        System.out.println("currencylist==================" + currencyList);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(currencyList, typeList);
        System.out.println("json========================");
        return json;
    }

}
