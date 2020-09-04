/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Arrays;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
public class JiraServiceDeskApiController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Value("${jira.apiUrl}")
    private String JIRA_API_URL;
    @Value("${jira.apiUsername}")
    private String JIRA_API_USERNAME;
    @Value("${jira.apiToken}")
    private String JIRA_APU_TOKEN;
    
    @PostMapping(value = "/ticket/addIssue")
    public ResponseEntity addCountry(@RequestBody(required = true) String jsonData, Authentication auth) {
        try {                        
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response ;
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());            
            
            String authStr = JIRA_API_USERNAME+":"+JIRA_APU_TOKEN;
            String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());                                   
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Creds);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity <String> entity = new HttpEntity<String>(jsonData, headers);
            
            response = restTemplate.exchange(
                JIRA_API_URL+"/issue", HttpMethod.POST, entity, String.class);            

            return response;
            
        } catch (Exception e) {
            logger.error("Error while creating issue", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
