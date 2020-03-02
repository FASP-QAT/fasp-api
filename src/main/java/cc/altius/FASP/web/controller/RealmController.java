/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.service.RealmService;
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
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io"})
public class RealmController {

    @Autowired
    private RealmService realmService;

    @GetMapping(value = "/getRealmList")
    public String getRealmList() throws UnsupportedEncodingException {
        String json = null;
        try {
            List<Realm> realmList = this.realmService.getRealmList(true);
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(realmList, typeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping(value = "/getRealmCountryList")
    public String getRealmCountryList() throws UnsupportedEncodingException {
        String json = null;
        try {
            List<RealmCountry> realmCountryList = this.realmService.getRealmCountryList(true);
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(realmCountryList, typeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping(value = "/getRealmCountryListByRealmId/{realmId}")
    public String getRealmCountryListByRealmId(@PathVariable("realmId") int realmId) throws UnsupportedEncodingException {
        String json = null;
        try {
            List<RealmCountry> realmCountryList = this.realmService.getRealmCountryListByRealmId(realmId);
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(realmCountryList, typeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
