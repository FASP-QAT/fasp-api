/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.JiraServiceDeskApiService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/jira")
public class JiraServiceDeskApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JiraServiceDeskApiService jiraServiceDeskApiService;
    @Autowired
    private UserService userService;

    /**
     * API used to sync user's Jira account id in user table
     *
     * @return returns the json object with Jira account details
     */
    @Operation(description = "API used to sync user's Jira account id in user table.", summary = "Sync user's Jira account id", tags = ("jiraServiceDesk"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the json object with Jira account details")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/syncJiraAccountIds")
    public String syncUserJiraAccountId(HttpServletResponse response) {
        return this.jiraServiceDeskApiService.syncUserJiraAccountId("");
    }

    /**
     * API used to add issue in Jira
     *
     * @param jsonData Issue details to add in Jira in json format
     * @param auth
     * @return returns a response which we get after calling Jira API to add
     * issue
     */
    @Operation(description = "API used to add issue in Jira", summary = "Add issue in Jira", tags = ("jiraServiceDesk"))
    @Parameters(
            @Parameter(name = "jsonData", description = "Issue details to add in Jira in json format"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/addIssue")
    public ResponseEntity addIssue(@RequestBody(required = true) String jsonData, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            ResponseEntity<String> response;
            response = this.jiraServiceDeskApiService.addIssue(jsonData, curUser);
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                return new ResponseEntity(response.getBody(), HttpStatus.OK);
            } else {
                return new ResponseEntity(response.getBody(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error while creating issue", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * API used to add attachment in the issue in Jira
     *
     * @param file Attachment which needs to be added in Jira issue
     * @param issueId Issue id for which attachment needs to be added for
     * @param auth
     * @return returns a response which we get after calling Jira API to add
     * attachment in Jira issue issue
     */
    @Operation(description = "API used to add attachment in the issue in Jira", summary = "Add attachment in Jira issue", tags = ("jiraServiceDesk"))
    @Parameters({
        @Parameter(name = "file", description = "Attachment which needs to be added in Jira issue"),
        @Parameter(name = "issueId", description = "Issue id for which attachment needs to be added for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(value = "/addIssueAttachment/{issueId}")
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
                return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error while upload the file", e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return new ResponseEntity(new ResponseCode(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get number of open and addressed issues
     *
     * @param auth
     * @return returns a number of open and addressed issues in json format
     */
    @Operation(description = "API used to get number of open and addressed issues", summary = "Get number of open and addressed issues", tags = ("jiraServiceDesk"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping(value = "/openIssues")
    public ResponseEntity getOpenIssue(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.jiraServiceDeskApiService.getIssuesSummary(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while creating issue", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
