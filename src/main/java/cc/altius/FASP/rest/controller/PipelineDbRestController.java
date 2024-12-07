/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.pipeline.QatTempProgram;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.pipeline.QatTempDataSource;
import cc.altius.FASP.model.pipeline.QatTempFundingSource;
import cc.altius.FASP.model.pipeline.QatTempProcurementAgent;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.model.pipeline.PplConsumption;
import cc.altius.FASP.model.pipeline.PplProduct;
import cc.altius.FASP.model.pipeline.PplPrograminfo;
import cc.altius.FASP.model.pipeline.QatTempConsumption;
import cc.altius.FASP.model.pipeline.QatTempInventory;
import cc.altius.FASP.model.pipeline.QatTempPlanningUnitInventoryCount;
import cc.altius.FASP.model.pipeline.QatTempProgramPlanningUnit;
import cc.altius.FASP.model.pipeline.QatTempShipment;
import cc.altius.FASP.service.PipelineDbService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Map;

/**
 *
 * @author akil`
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Pipeline",
    description = "Manage pipeline data including programs, shipments, consumption, inventory, and funding sources"
)
public class PipelineDbRestController {

    @Autowired
    private PipelineDbService pipelineDbService;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(path = "/pipeline/json/{fileName}")
    @Operation(
        summary = "Add Pipeline Data",
        description = "Add pipeline data from a JSON file"
    )
    @Parameter(name = "fileName", description = "The name of the file containing the pipeline data")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The pipeline data to add",
        required = true,
        content = @Content(schema = @Schema(implementation = Pipeline.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Pipeline data already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding pipeline data")
    public ResponseEntity postOrganisation(@RequestBody Pipeline pipeline, @PathVariable("fileName") String fileName, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        try {
            String msg = "static.message.pipeline.programExists";
            int duplicateCheckCount = this.pipelineDbService.savePipelineDbData(pipeline, curUser, fileName);
            if (duplicateCheckCount == 0) {
                return new ResponseEntity(new ResponseCode(msg), HttpStatus.PRECONDITION_FAILED); // 412
            } else {
                return new ResponseEntity(this.pipelineDbService.savePipelineDbData(pipeline, curUser, fileName), HttpStatus.OK); // 200
            }

        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/pipeline")
    @Operation(
        summary = "Get Pipeline Program List",
        description = "Retrieve a list of pipeline programs from adb table"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Map.class))), responseCode = "200", description = "Returns a list of pipeline programs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline program list")
    public ResponseEntity getPipelineProgramList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getPipelineProgramList(curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to get Program list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/pipeline/programInfo/{pipelineId}")
    @Operation(
        summary = "Get Pipeline Program Info",
        description = "Retrieve pipeline program information by pipeline ID"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the program information from")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = PplPrograminfo.class)), responseCode = "200", description = "Returns a pipeline program information")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this pipeline program information")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Pipeline program information not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline program information")
    public ResponseEntity getProgramInfo(@PathVariable("pipelineId") int pipelineId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getPipelineProgramInfoById(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get program data Id=" + pipelineId, erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get program data  Id=" + pipelineId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get program data Id=" + pipelineId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/pipeline/shipment/{pipelineId}")
    @Operation(
        summary = "Get Pipeline Shipment Data",
        description = "Retrieve pipeline shipment data by pipeline ID"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the shipment data from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = QatTempShipment.class))), responseCode = "200", description = "Returns a pipeline shipment data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this pipeline shipment data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Pipeline shipment data not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline shipment data")
    public ResponseEntity getPipelineShipmentdata(@PathVariable("pipelineId") int pipelineId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getPipelineShipmentdataById(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to get program data Id=" + pipelineId, erda);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to get program data  Id=" + pipelineId, ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get program data Id=" + pipelineId, e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping(path = "/qatTemp/program/{pipelineId}")
    @Operation(
        summary = "Save QAT Temp Program",
        description = "Save pipeline program data into QAT temp tables"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The program data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempProgram.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the program data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this program data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline program data")
    public ResponseEntity postQatTempProgram(@RequestBody QatTempProgram program, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.pipelineDbService.addQatTempProgram(program, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(path = "/qatTemp/program/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Program",
        description = "Retrieve pipeline program data from QAT temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the program data from")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = QatTempProgram.class)), responseCode = "200", description = "Returns a pipeline program data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this pipeline program data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline program data")
    public ResponseEntity getQatTempProgram(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempProgram(curUser, pipelineId), HttpStatus.OK); // 200
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/pipeline/product/{pipelineId}")
    @Operation(
        summary = "Get Pipeline Product List",
        description = "Retrieve a list of pipeline products by pipeline ID"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the product list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PplProduct.class))), responseCode = "200", description = "Returns a list of pipeline products")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline product list")
    public ResponseEntity getPipelineProductList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getPipelineProductListById(curUser, pipelineId), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list PipelineProduct", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping("/pipeline/planningUnit/{pipelineId}")
    @Operation(
        summary = "Save Planning Unit",
        description = "Save planning unit data for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The planning unit data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempProgramPlanningUnit.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the planning unit data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this planning unit data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline planning unit data")
    public ResponseEntity savePlanningUnitForProgram(@RequestBody QatTempProgramPlanningUnit[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.pipelineDbService.saveQatTempProgramPlanningUnit(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/qatTemp/planningUnitList/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Planning Unit List",
        description = "Retrieve a planning unit list by pipeline ID from temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the planning unit list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = QatTempProgramPlanningUnit.class))), responseCode = "200", description = "Returns a list of planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline planning unit list")
    public ResponseEntity getQatTempPlanningUnitList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempPlanningUnitListByPipelienId(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/pipeline/consumption/{pipelineId}")
    @Operation(
        summary = "Get Pipeline Consumption",
        description = "Retrieve consumption data by pipeline ID from adb tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the consumption data from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PplConsumption.class))), responseCode = "200", description = "Returns a list of consumption data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline consumption data")
    public ResponseEntity getPlanningProgramConsumption(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getPipelineConsumptionById(curUser, pipelineId), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list Consumption for Pipeline", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/qatTemp/regions/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Program Region",
        description = "Retrieve a list of program regions from temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the region list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Region.class))), responseCode = "200", description = "Returns a list of program regions")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline program region list")
    public ResponseEntity getQatTempProgramRegion(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempRegionsById(curUser, pipelineId), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list Program Regions", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping("/pipeline/consumption/{pipelineId}")
    @Operation(
        summary = "Save Consumption",
        description = "Save consumption data for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The consumption data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempConsumption.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the consumption data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this consumption data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline consumption data")
    public ResponseEntity saveConsumptionForProgram(@RequestBody QatTempConsumption[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.pipelineDbService.saveQatTempConsumption(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/qatTemp/consumption/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Consumption List",
        description = "Retrieve a list of consumption data from temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the consumption list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = QatTempConsumption.class))), responseCode = "200", description = "Returns a list of consumption data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline consumption list")
    public ResponseEntity getQatTempConsumptionList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempConsumptionListByPipelienId(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping(path = "/pipeline/shipment/{pipelineId}")
    @Operation(
        summary = "Save Shipment Data",
        description = "Save shipment data for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The shipment data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempShipment.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the shipment data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Integer.class)), responseCode = "200", description = "Returns 1")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline shipment data")
    public ResponseEntity saveShipmentData(@PathVariable("pipelineId") int pipelineId, @RequestBody QatTempShipment[] shipments, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        try {
            return new ResponseEntity(this.pipelineDbService.saveShipmentData(pipelineId, shipments, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode("incorrectformat"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping(path = "/pipeline/programdata/{pipelineId}")
    @Operation(
        summary = "Save Final Program Data",
        description = "save final program data into QAT tables from temp table"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the final program data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Integer.class)), responseCode = "200", description = "Returns 1")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Program already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline final program data")
    public ResponseEntity finalSaveProgramData(@PathVariable("pipelineId") int pipelineId, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        try {
            return new ResponseEntity(this.pipelineDbService.finalSaveProgramData(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Program", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/pipeline/inventory/{pipelineId}")
    @Operation(
        summary = "Get Pipeline Inventory Data",
        description = "Retrieve  pipeline inventory data from adb or temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the inventory data from")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns inventory data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline inventory data")
    public ResponseEntity getPipelineProgramInventory(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getPipelineInventoryById(curUser, pipelineId), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list Pipeline Inventory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping("/pipeline/inventory/{pipelineId}")
    @Operation(
        summary = "Save Inventory Data",
        description = "Save inventory data for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The inventory data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempInventory.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the inventory data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this inventory data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline inventory data")
    public ResponseEntity saveInventoryForProgram(@RequestBody QatTempInventory[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.pipelineDbService.saveQatTempInventory(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/qatTemp/planningUnitListFinalInventry/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Planning Unit List",
        description = "Retrieve a list of planning units for inventory count from temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the planning unit list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = QatTempPlanningUnitInventoryCount.class))), responseCode = "200", description = "Returns a list of planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline planning unit list")
    public ResponseEntity getQatTempPlanningUnitListInventoryCount(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempPlanningUnitListInventoryCount(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping("/pipeline/datasource/{pipelineId}")
    @Operation(
        summary = "Save Data Source",
        description = "Save data source data for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The data source data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempDataSource.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the data source data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this data source data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline data source data")
    public ResponseEntity saveDataSourceForProgram(@RequestBody QatTempDataSource[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.pipelineDbService.saveQatTempDataSource(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update DataSource for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update DataSource for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/qatTemp/datasource/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Data Source List",
        description = "Retrieve a list of data source from temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the data source list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = QatTempDataSource.class))), responseCode = "200", description = "Returns a list of data source")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting pipeline data source list")
    public ResponseEntity getQatTempDataSourceList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempDataSourceListByPipelienId(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
    @PutMapping("/pipeline/fundingsource/{pipelineId}")
    @Operation(
        summary = "Save Funding Source",
        description = "Save funding source data for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The funding source data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempFundingSource.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the funding source data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this funding source data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline funding source data")
    public ResponseEntity saveFundingSourceForProgram(@RequestBody QatTempFundingSource[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.pipelineDbService.saveQatTempFundingSource(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update FundingSource for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update FundingSource for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/qatTemp/fundingsource/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Funding Source List",
        description = "Retrieve a list of funding source from temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the funding source list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = QatTempFundingSource.class))), responseCode = "200", description = "Returns a list of funding source")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline funding source list")
    public ResponseEntity getQatTempFundingSourceList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempFundingSourceListByPipelienId(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list FundingSource", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping("/pipeline/procurementagent/{pipelineId}")
    @Operation(
        summary = "Save Procurement Agent",
        description = "Save procurement agent data for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The procurement agent data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempProcurementAgent.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the procurement agent data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this procurement agent data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline procurement agent data")
    public ResponseEntity saveProcurementAgentForProgram(@RequestBody QatTempProcurementAgent[] ppu, Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.pipelineDbService.saveQatTempProcurementAgent(ppu, curUser, pipelineId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProcurementAgent for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update ProcurementAgent for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/qatTemp/procurementagent/{pipelineId}")
    @Operation(
        summary = "Get QAT Temp Procurement Agent List",
        description = "Retrieve a list of procurement agent from temp tables"
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to retrieve the procurement agent list from")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = QatTempProcurementAgent.class))), responseCode = "200", description = "Returns a list of procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting pipeline procurement agent list")
    public ResponseEntity getQatTempProcurementAgentList(Authentication auth, @PathVariable("pipelineId") int pipelineId) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.pipelineDbService.getQatTempProcurementAgentListByPipelienId(pipelineId, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping(path = "/pipeline/realmCountryPlanningUnit/{pipelineId}/{realmCountryId}")
    @Operation(
        summary = "Create Realm Country Planning Units",
        description = "Create realm country planning units for a program"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The realm country planning unit data to save",
        required = true,
        content = @Content(schema = @Schema(implementation = QatTempProgramPlanningUnit.class))
    )
    @Parameter(name = "pipelineId", description = "The ID of the pipeline to save the realm country planning unit data into")
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to save the planning unit data into")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save this realm country planning unit data")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving pipeline realm country planning unit data")
    public ResponseEntity createRealmCountryPlanningUnits(@PathVariable("pipelineId") int pipelineId, @PathVariable("realmCountryId") int realmCountryId, Authentication auth) throws IOException {
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        try {
            this.pipelineDbService.createRealmCountryPlanningUnits(pipelineId, curUser, realmCountryId);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("/api//", e);
            return new ResponseEntity(new ResponseCode(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
