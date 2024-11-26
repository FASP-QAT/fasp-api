/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.ShipmentStatus;
import cc.altius.FASP.service.ShipmentStatusService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Shipment status",
    description = "Manage shipment status data and synchronization"
)
public class ShipmentStatusRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShipmentStatusService shipmentStatusService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/sync/shipmentStatus/{lastSyncDate}")
    @Operation(
        summary = "Get Shipment Status List for Sync",
        description = "Retrieve the shipment status list for synchronization"
    )
    @Parameter(
        name = "lastSyncDate",
        description = "The last synchronization date",
        required = true
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ShipmentStatus.class))), responseCode = "200", description = "Returns the shipment status list for synchronization")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Error parsing the last synchronization date")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while synchronizing the shipment status")
    public ResponseEntity getShipmentStatusListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.shipmentStatusService.getShipmentStatusListForSync(lastSyncDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing Shipment status", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (Exception e) {
            logger.error("Error while listing Shipment status", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = "/getShipmentStatusListActive")
    @Operation(
        summary = "Get Shipment Status List Active",
        description = "Retrieve the active shipment status list"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ShipmentStatus.class))), responseCode = "200", description = "Returns the active shipment status list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while retrieving the active shipment status list")
    public ResponseEntity getShipmentStatusListActive(Authentication auth) {
        try {
            return new ResponseEntity(this.shipmentStatusService.getShipmentStatusList(true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while listing Shipment status", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}