/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Dimension;
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
import cc.altius.FASP.service.DimensionService;
import cc.altius.FASP.service.UserService;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api/dimension")
public class DimensionRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private UserService userService;

    /**Add Dimension
     * 
     * @param dimension
     * @param auth
     * @return 
     */
    @PostMapping(path = "")
    public ResponseEntity postDimension(@RequestBody Dimension dimension, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dimensionService.addDimension(dimension, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Dimension", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Update Dimension
     * 
     * @param dimension
     * @param auth
     * @return 
     */
    @PutMapping(path = "")
    public ResponseEntity putDimension(@RequestBody Dimension dimension, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dimensionService.updateDimension(dimension, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Dimension ", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to update Dimension ", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of active Dimensions
     * 
     * @param auth
     * @return 
     */
    @GetMapping("")
    public ResponseEntity getDimension(Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionList(false), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get list of all Dimensions
     * 
     * @param auth
     * @return 
     */
    @GetMapping("/all")
    public ResponseEntity getDimensionAll(Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionList(true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**Get Dimension by Id
     * 
     * @param dimensionId
     * @param auth
     * @return 
     */
    @GetMapping("/{dimensionId}")
    public ResponseEntity getDimension(@PathVariable("dimensionId") int dimensionId, Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionById(dimensionId), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
