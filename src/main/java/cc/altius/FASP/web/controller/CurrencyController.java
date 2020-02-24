/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.CurrencyService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4202")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PutMapping(value = "/addCurrency")
    public ResponseEntity addCurrency(@RequestBody(required = true) String json) {
        //System.out.println("json--->" + json);
        Gson g = new Gson();
        Currency currency = g.fromJson(json, Currency.class);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            int currencyId = this.currencyService.addCurrency(currency);
            //System.out.println("currencyId inserted--------->" + currencyId);
            if (currencyId > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Currency added successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Error accured");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (DuplicateKeyException e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Currency already exists");
            return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            //e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping(value = "/getCurrencyList")
    public String getCurrencyList() throws UnsupportedEncodingException {
        String json;
        List<Currency> dataSourceTypeList = this.currencyService.getCurrencyList(false);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(dataSourceTypeList, typeList);
        return json;
    }

    @GetMapping(value = "/getCurrencyListActive")
    public String getCurrencyListActive() throws UnsupportedEncodingException {
        String json;
        List<Currency> dataSourceTypeList = this.currencyService.getCurrencyList(true);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(dataSourceTypeList, typeList);
        return json;
    }

    @PutMapping(value = "/editCurrency")
    public ResponseEntity editDataSourceType(@RequestBody(required = true) String json) {

        //System.out.println("jsoon--<------json---"+json);
        Gson g = new Gson();
        Currency currency = g.fromJson(json, Currency.class);
        //System.out.println("currency--class--->"+currency);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            int updateRow = this.currencyService.updateCurrency(currency);
            if (updateRow > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Currency updated successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Error accured");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (DuplicateKeyException e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Currency already exists");
            return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
