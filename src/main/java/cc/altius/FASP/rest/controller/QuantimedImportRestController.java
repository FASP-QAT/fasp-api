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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Quantimed Import",
    description = "Manage forecast data imports from Quantimed with program-specific validation"
)
public class QuantimedImportRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuantimedImportService quantimedImportService;
    @Autowired
    private UserService userService;

    /**
     * Used to Import a Quantimed program into QAT
     *
     * @param file
     * @param programId
     * @param auth
     * @return
     */
    @PostMapping(value = "/quantimed/quantimedImport/{programId}")
    @Operation(
        summary = "Quantimed Import",
        description = "Import forecast data from Quantimed for a given program."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The file to import",
        required = true,
        content = @Content(mediaType = "multipart/form-data", schema = @Schema(type = "string", format = "binary"))
    )
    @Parameter(name = "programId", description = "The ID of the program to import forecast data for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = QuantimedImportDTO.class)), responseCode = "200", description = "Returns the QuantimedImportDTO object")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while importing forecast data")
    public ResponseEntity quantimedImport(@RequestParam("file") MultipartFile file, @PathVariable("programId") String programId, Authentication auth) {
        String message = "";
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            QuantimedImportDTO quantimedImportDTO = this.quantimedImportService.importForecastData(file, programId, curUser);
            return new ResponseEntity(quantimedImportDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while upload the file", e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
