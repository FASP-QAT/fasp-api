/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.pipeline.QatTempProgram;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.model.pipeline.QatTempConsumption;
import cc.altius.FASP.model.pipeline.QatTempInventory;
import cc.altius.FASP.model.pipeline.QatTempProgramPlanningUnit;
import cc.altius.FASP.model.pipeline.QatTempShipment;
import cc.altius.FASP.service.PipelineDbService;
import cc.altius.FASP.service.UserService;
import java.io.IOException;
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
import cc.altius.FASP.model.pipeline.QatTempDataSource;
import cc.altius.FASP.model.pipeline.QatTempFundingSource;
import cc.altius.FASP.model.pipeline.QatTempProcurementAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

/**
 *
 * @author akil`
 */
@RestController
@RequestMapping("/api")
public class PipelineDbRestController {

    @Autowired
    private PipelineDbService pipelineDbService;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(path = "/pipelineJson/{fileName}")
    public ResponseEntity postOrganisation(@RequestBody Pipeline pipeline, @PathVariable("fileName") String fileName, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        try {
            String msg = "static.message.pipeline.programExists";
            int duplicateCheckCount = this.pipelineDbService.savePipelineDbData(pipeline, curUser, fileName);
            if (duplicateCheckCount == 0) {
                return new ResponseEntity(new ResponseCode(msg), HttpStatus.PRECONDITION_FAILED);
            } else {
                return new ResponseEntity(this.pipelineDbService.savePipelineDbData(pipeline, curUser, fileName), HttpStatus.OK);
            }

        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pipeline")
    public ResponseEntity getPipelineProgramList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineProgramList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Program list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pipeline/programInfo/{pipelineId}")
    public ResponseEntity getProgramInfo(@PathVariable("pipelineId") int pipelineId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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
    public ResponseEntity postQatTempProgram(@RequestBody QatTempProgram program, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineProductListById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pipeline/planningUnit/{pipelineId}")
    public ResponseEntity savePlanningUnitForProgram(@RequestBody QatTempProgramPlanningUnit[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.pipelineDbService.saveQatTempProgramPlanningUnit(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qatTemp/planningUnitList/{pipelineId}")
    public ResponseEntity getQatTempPlanningUnitList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getQatTempPlanningUnitListByPipelienId(pipelineId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pipeline/consumption/{pipelineId}")
    public ResponseEntity getPlanningProgramConsumption(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineConsumptionById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qatTemp/regions/{pipelineId}")
    public ResponseEntity getQatTempProgramRegion(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getQatTempRegionsById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pipeline/consumption/{pipelineId}")
    public ResponseEntity saveConsumptionForProgram(@RequestBody QatTempConsumption[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.pipelineDbService.saveQatTempConsumption(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qatTemp/consumption/{pipelineId}")
    public ResponseEntity getQatTempConsumptionList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getQatTempConsumptionListByPipelienId(pipelineId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/pipeline/shipment/{pipelineId}")
    public ResponseEntity saveShipmentData(@PathVariable("pipelineId") int pipelineId, @RequestBody QatTempShipment[] shipments, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        try {
            return new ResponseEntity(this.pipelineDbService.saveShipmentData(pipelineId, shipments, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/pipeline/programdata/{pipelineId}")
    public ResponseEntity finalSaveProgramData(@PathVariable("pipelineId") int pipelineId, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        try {
            return new ResponseEntity(this.pipelineDbService.finalSaveProgramData(pipelineId, curUser), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Program", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pipeline/inventory/{pipelineId}")
    public ResponseEntity getPipelineProgramInventory(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineInventoryById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pipeline/inventory/{pipelineId}")
    public ResponseEntity saveInventoryForProgram(@RequestBody QatTempInventory[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.pipelineDbService.saveQatTempInventory(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qatTemp/planningUnitListFinalInventry/{pipelineId}")
    public ResponseEntity getQatTempPlanningUnitListInventoryCount(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getQatTempPlanningUnitListInventoryCount(pipelineId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pipeline/datasource/{pipelineId}")
    public ResponseEntity saveDataSourceForProgram(@RequestBody QatTempDataSource[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.pipelineDbService.saveQatTempDataSource(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update DataSource for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update DataSource for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qatTemp/datasource/{pipelineId}")
    public ResponseEntity getQatTempDataSourceList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getQatTempDataSourceListByPipelienId(pipelineId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pipeline/fundingsource/{pipelineId}")
    public ResponseEntity saveFundingSourceForProgram(@RequestBody QatTempFundingSource[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.pipelineDbService.saveQatTempFundingSource(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update FundingSource for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qatTemp/fundingsource/{pipelineId}")
    public ResponseEntity getQatTempFundingSourceList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getQatTempFundingSourceListByPipelienId(pipelineId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list FundingSource", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/pipeline/procurementagent/{pipelineId}")
    public ResponseEntity saveProcurementAgentForProgram(@RequestBody QatTempProcurementAgent[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.pipelineDbService.saveQatTempProcurementAgent(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProcurementAgent for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/qatTemp/procurementagent/{pipelineId}")
    public ResponseEntity getQatTempProcurementAgentList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getQatTempProcurementAgentListByPipelienId(pipelineId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/pipeline/realmCountryPlanningUnit/{pipelineId}/{realmCountryId}")
    public ResponseEntity createRealmCountryPlanningUnits(@PathVariable("pipelineId") int pipelineId, @PathVariable("realmCountryId") int realmCountryId, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        try {
            this.pipelineDbService.createRealmCountryPlanningUnits(pipelineId, curUser, realmCountryId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
