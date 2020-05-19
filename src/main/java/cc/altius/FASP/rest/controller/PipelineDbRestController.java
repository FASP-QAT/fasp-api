/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.service.PipelineDbService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil`
 */
@RestController
@RequestMapping("/api")
public class PipelineDbRestController {

    @Autowired
    private PipelineDbService pipelineDbService;

    @PostMapping(path = "/pipelineJson")
    public ResponseEntity postOrganisation(@RequestBody Pipeline pipeline, Authentication auth) throws IOException {
        CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
        try {
            return new ResponseEntity(this.pipelineDbService.savePipelineDbData(pipeline, curUser), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
