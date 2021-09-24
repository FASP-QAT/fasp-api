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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

/**
 *
 * @author akil`
 */
@RestController
@RequestMapping("/api/pipeline")
public class PipelineDbRestController {

    @Autowired
    private PipelineDbService pipelineDbService;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * API used to save pipeline db data in to QAT adb(access database) tables
     *
     * @param pipeline Pipeline object which you need to save in adb tables
     * @param fileName Filename to save in database
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save pipeline db data in to QAT adb(access database) tables", summary = "Save Pipeline Data", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "pipeline", description = "Pipeline object which you need to save in adb tables"),
        @Parameter(name = "fileName", description = "Filename to save in database")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if the program specified already exists")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/pipelineJson/{fileName}")
    public ResponseEntity postPipelineData(@RequestBody Pipeline pipeline, @PathVariable("fileName") String fileName, Authentication auth) {
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

    /**
     * API used to get pipeline program list from adb table
     *
     * @param auth
     * @return returns a pipeline program list
     */
    @Operation(description = "API used to get pipeline program list from adb table", summary = "Get Pipeline Program List", tags = ("pipeline"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a pipeline program list if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/")
    public ResponseEntity getPipelineProgramList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineProgramList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Program list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get pipeline program info by pipelineId
     *
     * @param pipelineId PipelineId for which you need program info for
     * @param auth
     * @return returns a PplPrograminfo object in json format
     */
    @Operation(description = "API used to get pipeline program info by pipelineId", summary = "Get Pipeline Program Info", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need program info for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a pipeline program info if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the Program info for the specified pipelineId does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/programInfo/{pipelineId}")
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

    /**
     * API used to get pipeline shipment data by pipelineId from adb or temp
     * table
     *
     * @param pipelineId PipelineId for which you need shipment data for
     * @param auth
     * @return returns a QatTempShipment object in json format
     */
    @Operation(description = "API used to get pipeline shipment data by pipelineId", summary = "Get Pipeline Shipment Data", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need shipment data for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a pipeline shipment data if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the Shipment data for the specified pipelineId does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/shipment/{pipelineId}")
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

    /**
     * API used to save pipeline program data into QAT temp tables
     *
     * @param program Program object which you need to save in temp table
     * @param pipelineId PipelineId for which you need to save program for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save pipeline program data into QAT temp tables", summary = "Save Pipeline Program Data", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "program", description = "Program object which you need to save in temp table"),
        @Parameter(name = "pipelineId", description = "PipelineId for which you need to save program for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to save program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to get pipeline program data from QAT temp tables
     *
     * @param pipelineId PipelineId for which you need to temp program data for
     * @param auth
     * @return returns a QatTempProgram object in json format
     */
    @Operation(description = "API used to get pipeline program data from QAT temp tables", summary = "Get QAT Temp Program", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need to temp program data for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a QatTempProgram object if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to get planning unit list from adb tables
     *
     * @param pipelineId PipelineId for which you need to planning unit list for
     * @param auth
     * @return returns a planning unit list
     */
    @Operation(description = "API used to get planning unit list from adb tables", summary = "Get Planning Unit List", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need to planning unit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a planning unit list if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/product/{pipelineId}")
    public ResponseEntity getPlanningUnit(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineProductListById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to save planning unit for specified program into temp table
     *
     * @param ppu Array of planning unit which needs to be saved
     * @param pipelineId PipelineId for which you need planning units to save
     * for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save planning unit for specified program into temp table", summary = "Save Planning Unit For Program", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "ppu", description = "Array of planning unit which needs to be saved"),
        @Parameter(name = "pipelineId", description = "PipelineId for which you need planning units to save for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/planningUnit/{pipelineId}")
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

    /**
     * API used to get planning unit list from temp tables
     *
     * @param pipelineId PipelineId for which you need to planning unit list for
     * @param auth
     * @return returns a planning unit list
     */
    @Operation(description = "API used to get planning unit list from temp tables", summary = "Get QAT Temp Planning Unit List", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need to planning unit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a planning unit list if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to get consumption data from adb tables
     *
     * @param pipelineId PipelineId for which you need to consumption data for
     * @param auth
     * @return returns a PplConsumption object in json format
     */
    @Operation(description = "API used to get consumption data from adb tables", summary = "Get Consumtion Data", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need to consumption data for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a PplConsumption object if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/consumption/{pipelineId}")
    public ResponseEntity getPlanningProgramConsumption(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineConsumptionById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get program region from temp table
     *
     * @param pipelineId PipelineId for which you need to program region for
     * @param auth
     * @return returns a Region object in json format
     */
    @Operation(description = "API used to get program region from temp table", summary = "Get Program Region", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need to program region for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Region object if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to save consumption for specified program into temp table
     *
     * @param ppu Array of consumption which needs to be saved
     * @param pipelineId PipelineId for which you need consumption to save for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save consumption for specified program into temp table", summary = "Save Consumption For Program", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "ppu", description = "Array of consumption which needs to be saved"),
        @Parameter(name = "pipelineId", description = "PipelineId for which you need consumption to save for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/consumption/{pipelineId}")
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

    /**
     * API used to get consumption list from adb tables
     *
     * @param pipelineId PipelineId for which you need to consumption list for
     * @param auth
     * @return returns a list of QatTempConsumption object in json format
     */
    @Operation(description = "API used to get consumption data from adb tables", summary = "Get Consumtion List", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need to consumption list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of QatTempConsumption object if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to save shipment for specified program into temp table
     *
     * @param pipelineId PipelineId for which you need shipment data to save for
     * @param shipments Array of shipments which needs to be saved
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save shipment for specified program into temp table", summary = "Save Shipment Data For Program", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "pipelineId", description = "PipelineId for which you need shipment to save for"),
        @Parameter(name = "shipments", description = "Array of shipments which needs to be saved")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/shipment/{pipelineId}")
    public ResponseEntity saveShipmentData(@PathVariable("pipelineId") int pipelineId, @RequestBody QatTempShipment[] shipments, Authentication auth) {
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        try {
            return new ResponseEntity(this.pipelineDbService.saveShipmentData(pipelineId, shipments, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to save final program data into QAT tables from temp table for
     * specified pipelineId
     *
     * @param pipelineId PipelineId for which you need program data to save for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save final program data into QAT tables from temp table for specified pipelineId", summary = "Save Final Program Data", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need program data to save for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Program supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/programdata/{pipelineId}")
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

    /**
     * API used to get pipeline inventory data from adb or temp tables
     *
     * @param pipelineId PipelineId for which you need to pipeline inventory
     * data for
     * @param auth
     * @return returns a pipeline inventory data in json format
     */
    @Operation(description = "API used to get pipeline inventory data from adb or temp tables", summary = "Get Pipeline Inventory Data", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need to pipeline inventory data for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a pipeline inventory data object if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/inventory/{pipelineId}")
    public ResponseEntity getPipelineProgramInventory(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.pipelineDbService.getPipelineInventoryById(curUser, pipelineId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to save inventory for specified program into temp table
     *
     * @param ppu Array of inventory which needs to be saved
     * @param pipelineId PipelineId for which you need inventory data to save
     * for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save inventory for specified program into temp table", summary = "Save Inventory Data For Program", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "ppu", description = "Array of inventory which needs to be saved"),
        @Parameter(name = "pipelineId", description = "PipelineId for which you need inventory to save for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/inventory/{pipelineId}")
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

    /**
     * API used to get list of planning unit inventory count from temp tables
     *
     * @param pipelineId PipelineId for which you need the list of planning unit
     * inventory count for
     * @param auth
     * @return returns a list of planning unit inventory count
     */
    @Operation(description = "API used to get list of planning unit inventory count from temp tables", summary = "Get List of Planning Unit Inventory Count", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need the list of planning unit inventory count for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of planning unit inventory count if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to save data source for specified program into temp table
     *
     * @param ppu Array of data source which needs to be saved
     * @param pipelineId PipelineId for which you need data source data to save
     * for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save data source for specified program into temp table", summary = "Save QAT Temp Data Source", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "ppu", description = "Array of data source which needs to be saved"),
        @Parameter(name = "pipelineId", description = "PipelineId for which you need data source to save for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/datasource/{pipelineId}")
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

    /**
     * API used to get list of data source from temp tables
     *
     * @param pipelineId PipelineId for which you need the list of data source
     * for
     * @param auth
     * @return returns a list of data source
     */
    @Operation(description = "API used to get list of data source from temp tables", summary = "Get List of Data Source", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need the list of data source for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of data source if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to save funding source for specified program into temp table
     *
     * @param ppu Array of funding source which needs to be saved
     * @param pipelineId PipelineId for which you need funding source to save
     * for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save funding source for specified program into temp table", summary = "Save QAT Temp Funding Source", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "ppu", description = "Array of funding source which needs to be saved"),
        @Parameter(name = "pipelineId", description = "PipelineId for which you need funding source to save for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/fundingsource/{pipelineId}")
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

    /**
     * API used to get list of funding source from temp tables
     *
     * @param pipelineId PipelineId for which you need the list of funding
     * source for
     * @param auth
     * @return returns a list of funding source
     */
    @Operation(description = "API used to get list of funding source from temp tables", summary = "Get List of Funding Source", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need the list of funding source for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of funding source if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to save procurement agent for specified program into temp table
     *
     * @param ppu Array of procurement agent which needs to be saved
     * @param pipelineId PipelineId for which you need procurement agent to save
     * for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save procurement agent for specified program into temp table", summary = "Save QAT Temp Procurement Agent", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "ppu", description = "Array of procurement agent which needs to be saved"),
        @Parameter(name = "pipelineId", description = "PipelineId for which you need procurement agent to save for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/procurementagent/{pipelineId}")
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

    /**
     * API used to get list of procurement agent from temp tables
     *
     * @param pipelineId PipelineId for which you need the list of procurement
     * agent for
     * @param auth
     * @return returns a list of procurement agent
     */
    @Operation(description = "API used to get list of procurement agent from temp tables", summary = "Get List of Procurement Agent", tags = ("pipeline"))
    @Parameters(
            @Parameter(name = "pipelineId", description = "PipelineId for which you need the list of procurement agent for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of procurement agent if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to save planning unit for specified realm country
     *
     * @param pipelineId PipelineId for which you need planning unit to save for
     * @param realmCountryId realmCountryId for which you need planning unit to
     * save for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save planning unit for specified realm country", summary = "Save Planning Unit For Realm Country", tags = ("pipeline"))
    @Parameters({
        @Parameter(name = "pipelineId", description = "PipelineId for which you need planning unit to save for"),
        @Parameter(name = "realmCountryId", description = "realmCountryId for which you need planning unit to save for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping(path = "/realmCountryPlanningUnit/{pipelineId}/{realmCountryId}")
    public ResponseEntity createRealmCountryPlanningUnits(@PathVariable("pipelineId") int pipelineId, @PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
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
