/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.VersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author altius
 */
@Controller
public class CoreUiVersionRestController {

    @Autowired
    private VersionService versionService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value="/api/coreui/version")
    public ResponseEntity getLatestCoreuiVersion() {
        try {
            return new ResponseEntity(this.versionService.getCoreUIVerion(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting core ui version", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
