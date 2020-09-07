/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.UserService;
import java.util.Arrays;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
public class JiraServiceDeskApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Value("${jira.apiUrl}")
    private String JIRA_API_URL;
    @Value("${jira.apiUsername}")
    private String JIRA_API_USERNAME;
    @Value("${jira.apiToken}")
    private String JIRA_API_TOKEN;

    @PostMapping(value = "/ticket/addIssue")
    public ResponseEntity addIssue(@RequestBody(required = true) String jsonData, Authentication auth) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;           

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", getJiraBasicAuthenticationToken());
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(jsonData, headers);

            response = restTemplate.exchange(
                    JIRA_API_URL + "/issue", HttpMethod.POST, entity, String.class);
            
            return response;

        } catch (Exception e) {
            logger.error("Error while creating issue", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/ticket/addIssueAttachment/{issueId}")
    public ResponseEntity addIssueAttachment(@RequestParam("file") MultipartFile file, @PathVariable("issueId") String issueId, Authentication auth) {
        String message = "";
        try {            
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;            

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", getJiraBasicAuthenticationToken());
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.add("X-Atlassian-Token", "no-check");

            MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
            ContentDisposition contentDisposition = ContentDisposition
                    .builder("form-data")
                    .name("file")
                    .filename(file.getOriginalFilename())
                    .build();
            fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            response = restTemplate.exchange(
                    JIRA_API_URL + "/issue/" + issueId + "/attachments", HttpMethod.POST, requestEntity, String.class);

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
    
    private String getJiraBasicAuthenticationToken() {
        String authStr = JIRA_API_USERNAME + ":" + JIRA_API_TOKEN;
        String base64Creds = "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes());
        return base64Creds;
    }
}
