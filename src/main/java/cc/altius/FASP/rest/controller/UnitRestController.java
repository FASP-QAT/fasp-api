/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Unit;
import cc.altius.FASP.service.UnitService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Unit",
    description = "Manage measurement units with dimension-based categorization"
)
public class UnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitService unitService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/unit")
    @Operation(
        summary = "Add a new unit",
        description = "Add a new unit"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The unit to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Unit already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding the unit")
    public ResponseEntity postUnit(@RequestBody Unit unit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.unitService.addUnit(unit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Unit", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping(path = "/unit")
    @Operation(
        summary = "Update an existing unit",
        description = "Update an existing unit"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The unit to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the unit")
    public ResponseEntity putUnit(@RequestBody Unit unit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.unitService.updateUnit(unit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException ae) {
            // FIXME: how do you get a duplicate key exception when updating?
            logger.error("Error while trying to update Unit ", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/unit")
    @Operation(
        summary = "Get all units",
        description = "Get all units"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Unit.class))), responseCode = "200", description = "Returns the list of units")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting the unit list")
    public ResponseEntity getUnit(Authentication auth) {
        try {
            return new ResponseEntity(this.unitService.getUnitList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/unit/dimension/{dimensionId}")
    @Operation(
        summary = "Get units by dimension ID",
        description = "Get units by dimension ID"
    )
    @Parameter(name = "dimensionId", description = "The dimension ID", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = Unit.class))), responseCode = "200", description = "Returns the list of units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the unit list")
    public ResponseEntity getUnitByDimension(@PathVariable("dimensionId") int dimensionId, Authentication auth) {
        try {
            return new ResponseEntity(this.unitService.getUnitListByDimensionId(dimensionId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/unit/{unitId}")
    @Operation(
        summary = "Get a unit by ID",
        description = "Get a unit by ID"
    )
    @Parameter(name = "unitId", description = "The unit ID", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Unit.class)), responseCode = "200", description = "Returns the unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting the unit")
    public ResponseEntity getUnit(@PathVariable("unitId") int unitId, Authentication auth) {
        try {
            return new ResponseEntity(this.unitService.getUnitById(unitId), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

//    @GetMapping(value = "/sync/unit/{lastSyncDate}")
//    public ResponseEntity getUnitListForSync(@PathVariable("lastSyncDate") String lastSyncDate) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            return new ResponseEntity(this.unitService.getUnitListForSync(lastSyncDate), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing unit", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing unit", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
