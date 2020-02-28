/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.City;
import cc.altius.FASP.service.CityService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4202")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io/palashSprint1/"})

public class CityController {

    @Autowired
    CityService cityService;

    @GetMapping(value = "/getCityListByStateIdAndCountryId/{countryId}/{stateId}")
    public String getCityListByStateIdAndCountryId(@PathVariable int countryId, @PathVariable int stateId) throws UnsupportedEncodingException {
        String json;
        List<City> cityList = this.cityService.getCityListByStateIdAndCountryId(countryId, stateId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(cityList, typeList);
        return json;
    }

    @GetMapping(value = "/getCityList")
    public String getCityList() throws UnsupportedEncodingException {
        String json;
        List<City> cityList = this.cityService.getAllCityList();
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(cityList, typeList);
        return json;
    }

}
