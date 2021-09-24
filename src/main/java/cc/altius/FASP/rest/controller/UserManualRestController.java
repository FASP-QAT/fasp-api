/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.service.UserManualService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/api/userManual")
public class UserManualRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserManualService userManualService;

    /**
     * API used to upload UserManual
     *
     * @param file user manual file to upload
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to upload UserManual", summary = "Upload UserManual", tags = ("userManual"))
    @Parameters(
            @Parameter(name = "file", description = "User manual file to upload"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access to upload the user manual")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/uploadUserManual")
    public ResponseEntity uploadUserManual(@RequestParam("file") MultipartFile file, Authentication auth) {
        try {
            this.userManualService.uploadUserManual(file);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
