/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.service.UserManualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
public class UserManualRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserManualService userManualService;

    /**
     * Api Used to push the upload the UserManual
     *
     * @param file
     * @param auth
     * @return
     */
    @PostMapping(path = "/userManual/uploadUserManual")
    public ResponseEntity uploadUserManual(@RequestParam("file") MultipartFile file, Authentication auth) {
        try {
            this.userManualService.uploadUserManual(file);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
