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
@RequestMapping("/api/ticket")
public class JiraServiceDeskApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @Autowired
    private JiraServiceDeskApiService jiraServiceDeskApiService;
    @Autowired
    private UserService userService;

    /**Used to add a Ticket to Jira
     * 
     * @param jsonData
     * @param auth
     * @return 
     */
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

    /**Used to add a File to an existing Jira Ticket
     * 
     * @param file
     * @param issueId
     * @param auth
     * @return 
     */
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
    
    /**Used to get a summary of Tickets for the current User
     * 
     * @param auth
     * @return 
     */
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
