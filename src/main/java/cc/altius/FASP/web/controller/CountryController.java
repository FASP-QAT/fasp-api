/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.CountryService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4202")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping(value = "/getCountryListAll")
    public String getCountryList() throws UnsupportedEncodingException {
        String json;
        List<Country> countryList = this.countryService.getCountryList(false);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(countryList, typeList);
        return json;
    }

    @GetMapping(value = "/getCountryListActive")
    public String getCountryListActive() throws UnsupportedEncodingException {
        String json;
        List<Country> countryList = this.countryService.getCountryList(true);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(countryList, typeList);
        return json;
    }

    @PutMapping(value = "/addCountry")
    public ResponseEntity addCountry(@RequestBody(required = true) String json) {
        Gson g = new Gson();
        Country c = g.fromJson(json, Country.class);
        try {
            int countryId = this.countryService.addCountry(c);
            if (countryId > 0) {
                ResponseFormat responseFormat = new ResponseFormat();
                responseFormat.setMessage("Country Added successfully");
                responseFormat.setStatus("Success");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                ResponseFormat responseFormat = new ResponseFormat();
                responseFormat.setMessage("Error Accured");
                responseFormat.setStatus("Failed");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            }
        } catch (Exception e) {
            ResponseFormat responseFormat = new ResponseFormat();
            responseFormat.setMessage("Error Accured");
            responseFormat.setStatus("Failed");
            return new ResponseEntity(responseFormat, HttpStatus.OK);
        }

    }

    @PutMapping(value = "/editCountry")
    public ResponseEntity editDataSource(@RequestBody(required = true) String json) {
        Gson g = new Gson();
        Country country = g.fromJson(json, Country.class);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            int updateRow = this.countryService.updateCountry(country);
            if (updateRow > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Country Details Updated successfully");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("Failed");
                responseFormat.setMessage("Updated failed");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
