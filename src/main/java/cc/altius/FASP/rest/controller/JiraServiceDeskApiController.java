/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.DTO.JiraServiceDeskIssuesDTO;
import cc.altius.FASP.service.JiraServiceDeskApiService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/api/ticket")
@Tag(
    name = "Jira Service Desk",
    description = "Manage Jira Service Desk tickets and their attachments"
)
public class JiraServiceDeskApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JiraServiceDeskApiService jiraServiceDeskApiService;
    @Autowired
    private UserService userService;

    /**
     * Used to add a Ticket to Jira
     *
     * @param jsonData
     * @param auth
     * @return
     */
    @PostMapping(value = "/addIssue")
    @Operation(
        summary = "Add Issue",
        description = "Create a new Jira Service Desk ticket."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The JSON data for the new issue",
        required = true
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while creating issue")
    public ResponseEntity addIssue(@RequestBody(required = true) String jsonData, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            ResponseEntity<String> response;
            response = this.jiraServiceDeskApiService.addIssue(jsonData, curUser);
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                return new ResponseEntity(response.getBody(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), HttpStatus.INTERNAL_SERVER_ERROR); // 500
            }

        } catch (Exception e) {
            logger.error("Error while creating issue", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }

    }

    /**
     * Used to add a File to an existing Jira Ticket
     *
     * @param file
     * @param issueId
     * @param auth
     * @return
     */
    @PostMapping(value = "/addIssueAttachment/{issueId}")
    @Operation(
        summary = "Add Issue Attachment",
        description = "Add a file attachment to an existing Jira Service Desk ticket."
    )
    @Parameter(name = "file", description = "The file to upload")
    @Parameter(name = "issueId", description = "The ID of the issue to attach the file to")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while adding issue attachment")
    public ResponseEntity addIssueAttachment(@RequestParam("file") MultipartFile file, @PathVariable("issueId") String issueId, Authentication auth) {
        String message = "";
        try {
            ResponseEntity<String> response;
            response = this.jiraServiceDeskApiService.addIssueAttachment(file, issueId);
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return new ResponseEntity(new ResponseCode(message), HttpStatus.OK);
            } else {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR); // 500
            }

        } catch (Exception e) {
            logger.error("Error while upload the file", e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }     
    
    /**
     * Used to get a summary of Tickets for the current User
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/openIssues")
    @Operation(
        summary = "Get Open Issues",
        description = "Retrieve a summary of open Jira Service Desk tickets for the current user."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = JiraServiceDeskIssuesDTO.class))), responseCode = "200", description = "Returns the list of open issues")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting open issues")
    public ResponseEntity getOpenIssue(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.jiraServiceDeskApiService.getIssuesSummary(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while creating issue", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}