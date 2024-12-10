/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.UserManualService;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "User manual",
    description = "Manage system documentation file uploads"
)
public class UserManualRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserManualService userManualService;

    /**
     * Api Used to push the upload the UserManual
     *
     * @param file
     * @param auth
     * @return
     */
    @PostMapping(path = "/userManual/uploadUserManual")
    @Operation(
        summary = "Upload user manual",
        description = "Upload user manual"
    )
    @Parameter(name = "file", description = "The file to upload", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Return a success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "The user does not have permission to upload the user manual")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "The user manual was not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while uploading the user manual")
    public ResponseEntity uploadUserManual(@RequestParam("file") MultipartFile file, Authentication auth) {
        try {
            this.userManualService.uploadUserManual(file);
            return new ResponseEntity(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to upload user manual", e);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
