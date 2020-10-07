/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.DTO.QuantimedImportDTO;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.QuantimedImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class QuantimedImportRestController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private QuantimedImportService quantimedImportService;
    
    @PostMapping(value = "/quantimed/quantimedImport/{programId}")
    public ResponseEntity quantimedImport(@RequestParam("file") MultipartFile file, @PathVariable("programId") String programId, Authentication auth) {
        String message = "";
        try {            
            ResponseEntity<String> response;
            
            System.out.println("ProgramId : ================== "+programId);
            System.out.println("Attached Filename : ========== "+file.getOriginalFilename());
            QuantimedImportDTO quantimedImportDTO = this.quantimedImportService.importForecastData(file, programId);            
            return new ResponseEntity(quantimedImportDTO, HttpStatus.OK);
        } catch (Exception e) {     
            logger.error("Error while upload the file", e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 
    
    @PostMapping(value = "/quantimed/addQuantimedImport")
    public ResponseEntity addQuantimedImport(@RequestBody QuantimedImportDTO importDTO, Authentication auth) {
        String message = "";
        try {            
            ResponseEntity<String> response;
            
            System.out.println("ProgramId : ================== "+importDTO);
            
            return new ResponseEntity("", HttpStatus.OK);
        } catch (Exception e) {     
            logger.error("Error while upload the file", e);
            message = "";
            return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
}
