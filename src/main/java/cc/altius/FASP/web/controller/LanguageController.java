/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.ResponseCode;
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
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io"})
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping(value = "/language")
    public ResponseEntity getLanguageList() {
        try {
            return new ResponseEntity(this.languageService.getLanguageList(true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.language.listFailed"), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/language/all")
    public ResponseEntity getLanguageListAll() {
        try {
            return new ResponseEntity(this.languageService.getLanguageList(false), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.language.listFailed"), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/language")
    public ResponseEntity addLanguage(@RequestBody(required = true) Language language) {
        try {
            int languageId = this.languageService.addLanguage(language);
            if (languageId > 0) {
                return new ResponseEntity(new ResponseCode("static.language.addSuccess"),HttpStatus.OK);
            } else {
                return new ResponseEntity(new ResponseCode("static.language.addFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            return new ResponseEntity(new ResponseCode("static.language.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.language.addFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/language")
    public ResponseEntity editLanguage(@RequestBody(required = true) Language language) {
        try {
            int updatedId = this.languageService.editLanguage(language);
            if (updatedId > 0) {
                return new ResponseEntity(new ResponseCode("static.language.updateSuccess"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ResponseCode("static.language.updateFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.language.updateFailure"), HttpStatus.INTERNAL_SERVER_ERROR);
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
