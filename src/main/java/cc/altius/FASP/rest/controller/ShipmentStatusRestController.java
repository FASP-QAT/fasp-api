/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.ShipmentStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
public class ShipmentStatusRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShipmentStatusService shipmentStatusService;

    /**
     * API used to get the active ShipmentStatus list.
     *
     * @param auth
     * @return returns the active ShipmentStatus list.
     */
    @Operation(description = "API used to get the active ShipmentStatus list.", summary = "Get Active ShipmentStatus list.", tags = ("shipmentStatus"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ShipmentStatus list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ShipmentStatus list")
    @GetMapping(value = "/getShipmentStatusListActive")
    public ResponseEntity getShipmentStatusListActive(Authentication auth) {
        try {
            return new ResponseEntity(this.shipmentStatusService.getShipmentStatusList(true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while listing Shipment status", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
