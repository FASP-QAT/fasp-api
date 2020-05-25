/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.service.PipelineDbService;
import java.io.IOException;
import static jxl.biff.BaseCellFeatures.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pipeline")
    public ResponseEntity getPipelineProgramList(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.pipelineDbService.getPipelineProgramList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Program list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pipeline/programInfo/{pipelineId}")
    public ResponseEntity getProgramInfo(@PathVariable("pipelineId") int pipelineId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.pipelineDbService.getPipelineProgramInfoById(pipelineId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get program data Id=" + pipelineId, erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get program data  Id=" + pipelineId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get program data Id=" + pipelineId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     @GetMapping("/pipeline/shipment/{pipelineId}")
    public ResponseEntity getPipelineShipmentdata(@PathVariable("pipelineId") int pipelineId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.pipelineDbService.getPipelineShipmentdataById(pipelineId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get program data Id=" + pipelineId, erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get program data  Id=" + pipelineId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get program data Id=" + pipelineId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/qatTemp/program/{pipelineId}")
    public ResponseEntity postQatTempProgram(@RequestBody Program program, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.pipelineDbService.addQatTempProgram(program, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/qatTemp/program/{pipelineId}")
    public ResponseEntity getQatTempProgram(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.pipelineDbService.getQatTempProgram(curUser, pipelineId), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pipeline/product/{pipelineId}")
    public ResponseEntity getPlanningUnit(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.pipelineDbService.getPipelineProductListById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
      @PostMapping(path = "/pipeline/shipment/{pipelineId}")
    public ResponseEntity saveShipmentData(@PathVariable("pipelineId") int pipelineId,@RequestBody Shipment[]  shipments, Authentication auth) throws IOException {
        CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
        try {
            return new ResponseEntity(this.pipelineDbService.saveShipmentData(pipelineId,shipments ,curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
     @PostMapping(path = "/pipeline/programdata/{pipelineId}")
    public ResponseEntity finalSaveProgramData(@PathVariable("pipelineId") int pipelineId, Authentication auth) throws IOException {
        CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
        try {
            return new ResponseEntity(this.pipelineDbService.finalSaveProgramData(pipelineId ,curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
