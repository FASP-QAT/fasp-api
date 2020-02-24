/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.LanguageService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4202")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping(value = "/getLanguageList")
    public String getLanguageList() throws UnsupportedEncodingException {
        String json;
        List<Language> languageList = this.languageService.getLanguageList(true);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(languageList, typeList);
        return json;
    }

    @GetMapping(value = "/getLanguageListAll")
    public String getLanguageListAll() throws UnsupportedEncodingException {
        String json;
        List<Language> languageList = this.languageService.getLanguageList(false);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(languageList, typeList);
        return json;
    }

    @PutMapping(value = "/addLanguage")
    public ResponseEntity addLanguage(@RequestBody(required = true) String json) {

        Gson g = new Gson();
        Language language = g.fromJson(json, Language.class);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            int languageId = this.languageService.addLanguage(language);
            //System.out.println("languageId inserted--------->" + languageId);
            if (languageId > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Language added successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Error accured");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Language already exists");
            return new ResponseEntity(responseFormat, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PutMapping(value = "/editLanguage")
    public ResponseEntity editLanguage(@RequestBody(required = true) String json) {
        Gson g = new Gson();
        Language language = g.fromJson(json, Language.class);
        //System.out.println("language json--->" + json);
        //System.out.println("language json--->" + language);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            int updatedId = this.languageService.editLanguage(language);
            if (updatedId > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Language updated successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Error accured.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);

            }

        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("Update failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping(value = "/getLanguageListForSync")
    public String getLanguageListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgLanguageDTO> languageList = this.languageService.getLanguageListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(languageList, typeList);
        return json;
    }

}
