/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.altius.FASP.service.TracerCategoryService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/tracerCategory")
public class TracerCategoryRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TracerCategoryService tracerCategoryService;
    @Autowired
    private UserService userService;

    /**Add TracerCategory
     * 
     * @param tracerCategory
     * @param auth
     * @return 
     */
    @PostMapping(path = "")
    public ResponseEntity postTracerCategory(@RequestBody TracerCategory tracerCategory, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.tracerCategoryService.addTracerCategory(tracerCategory, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add TracerCategory", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Update TracerCategory
     * 
     * @param tracerCategory
     * @param auth
     * @return 
     */
    @PutMapping(path = "")
    public ResponseEntity putTracerCategory(@RequestBody TracerCategory tracerCategory, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.tracerCategoryService.updateTracerCategory(tracerCategory, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add TracerCategory", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**List of TracerCategories
     * 
     * @param auth
     * @return 
     */
    @GetMapping("")
    public ResponseEntity getTracerCategory(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Simple list of TracerCategories
     * 
     * @param auth
     * @return 
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/simple")
    public ResponseEntity getTracerCategorySimple(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get TracerCategory by Id
     * 
     * @param tracerCategoryId
     * @param auth
     * @return 
     */
    @GetMapping("/{tracerCategoryId}")
    public ResponseEntity getTracerCategory(@PathVariable("tracerCategoryId") int tracerCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryById(tracerCategoryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of TracerCategories for a Realm
     * 
     * @param realmId
     * @param auth
     * @return 
     */
    @GetMapping("/realmId/{realmId}")
    public ResponseEntity getTracerCategoryForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of TracerCategories for all the PU’s mapped to a SP Program
     * 
     * @param realmId
     * @param programId
     * @param auth
     * @return 
     */
    @GetMapping("/realmId/{realmId}/programId/{programId}")
    public ResponseEntity getTracerCategoryForRealmProgram(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, programId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of TracerCategories for all the PU’s mapped to a list of SP Program
     * 
     * @param realmId
     * @param programIds
     * @param auth
     * @return 
     */
    @PostMapping("/realmId/{realmId}/programIds")
    public ResponseEntity getTracerCategoryForRealmPrograms(@PathVariable("realmId") int realmId, @RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, programIds, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
