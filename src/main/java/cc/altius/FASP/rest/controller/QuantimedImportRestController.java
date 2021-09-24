/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.QuantimedImportDTO;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.QuantimedImportService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/quantimed")
public class QuantimedImportRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuantimedImportService quantimedImportService;
    @Autowired
    private UserService userService;

    /**
     * API used to format forecast data to import in a specified program
     *
     * @param file file containing forecast data to format
     * @param programId programId that you want to format forecast data for
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to format forecast data to import in a specified program ", summary = "Format forecast data to import", tags = ("quantimed"))
    @Parameters({
        @Parameter(name = "file", description = "File containing forecast data to format for import"),
        @Parameter(name = "programId", description = "ProgramId that you want to format forecast data to import for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a QuantimedImportDTO object if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/import/{programId}")
    public ResponseEntity quantimedImport(@RequestParam("file") MultipartFile file, @PathVariable("programId") String programId, Authentication auth) {
        String message = "";
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            QuantimedImportDTO quantimedImportDTO = this.quantimedImportService.importForecastData(file, programId, curUser);
            return new ResponseEntity(quantimedImportDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while upload the file", e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
