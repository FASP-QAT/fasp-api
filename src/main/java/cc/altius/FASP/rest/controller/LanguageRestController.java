/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.LanguageService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;import org.springframework.http.HttpStatus;
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
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop", "https://qat.altius.cc"})
public class LanguageRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private LanguageService languageService;

    @GetMapping("/locales/{languageCode}")
    ResponseEntity getLanguageJson(@PathVariable("languageCode") String languageCode) {
        return new ResponseEntity(this.languageService.getLanguageJsonForStaticLabels(languageCode), HttpStatus.OK);
    }
    
    @GetMapping(value = "/language")
    public ResponseEntity getLanguageList(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.languageService.getLanguageList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting language list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/language/all")
    public ResponseEntity getLanguageListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.languageService.getLanguageList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting language list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/language/{languageId}")
    public ResponseEntity getLanguageById(@PathVariable("languageId") int languageId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.languageService.getLanguageById(languageId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while getting languageId="+languageId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while getting languageId="+languageId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/language")
    public ResponseEntity addLanguage(@RequestBody(required = true) Language language, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int languageId = this.languageService.addLanguage(language, curUser);
            if (languageId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"),HttpStatus.OK);
            } else {
                logger.error("Error while adding language no Id returned");
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while adding language", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while adding language", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/language")
    public ResponseEntity editLanguage(@RequestBody(required = true) Language language, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int updatedId = this.languageService.editLanguage(language, curUser);
            if (updatedId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
            } else {
                logger.error("Error while updating language, no rows updated");
                return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while updating language", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while updating language", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
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
