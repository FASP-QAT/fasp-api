/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.LanguageService;
import cc.altius.FASP.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class LanguageRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LanguageService languageService;
    @Autowired
    private UserService userService;

    /**Get the entire list of Static labels for the Language
     * 
     * @param languageCode
     * @return 
     */
    @GetMapping("/locales/{languageCode}")
    ResponseEntity getLanguageJson(@PathVariable("languageCode") String languageCode) {
        return new ResponseEntity(this.languageService.getLanguageJsonForStaticLabels(languageCode), HttpStatus.OK);
    }

    /**Get list of active Languages
     * 
     * @param auth
     * @return 
     */
    @GetMapping(value = "/language")
    public ResponseEntity getLanguageList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.languageService.getLanguageList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting language list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of all Languages
     * 
     * @param auth
     * @return 
     */
    @GetMapping(value = "/language/all")
    public ResponseEntity getLanguageListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.languageService.getLanguageList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting language list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Language by Id
     * 
     * @param languageId
     * @param auth
     * @return 
     */
    @GetMapping(value = "/language/{languageId}")
    public ResponseEntity getLanguageById(@PathVariable("languageId") int languageId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.languageService.getLanguageById(languageId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while getting languageId=" + languageId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while getting languageId=" + languageId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Add a Language
     * 
     * @param language
     * @param auth
     * @return 
     */
    @PostMapping(value = "/language")
    public ResponseEntity addLanguage(@RequestBody(required = true) Language language, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int languageId = this.languageService.addLanguage(language, curUser);
            if (languageId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                logger.error("Error while adding language no Id returned");
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while adding language", e);
            return new ResponseEntity(new ResponseCode("static.message.languageCodeAlreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while adding language", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**Update a Language
     * 
     * @param language
     * @param auth
     * @return 
     */
    @PutMapping(value = "/language")
    public ResponseEntity editLanguage(@RequestBody(required = true) Language language, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int updatedId = this.languageService.editLanguage(language, curUser);
            if (updatedId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
            } else {
                logger.error("Error while updating language, no rows updated");
                return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DuplicateKeyException e) {
            logger.error("Error while updating language", e);
            return new ResponseEntity(new ResponseCode("static.message.languageCodeAlreadyExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while updating language", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
