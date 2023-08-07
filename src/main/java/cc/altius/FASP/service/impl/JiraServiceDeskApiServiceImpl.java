/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.JiraServiceDeskIssuesDTO;
import cc.altius.FASP.service.JiraServiceDeskApiService;
import cc.altius.FASP.service.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
@Service
public class JiraServiceDeskApiServiceImpl implements JiraServiceDeskApiService {

    @Value("${jira.apiUrl}")
    private String JIRA_API_URL;
    @Value("${jira.serviceDeskApiUrl}")
    private String JIRA_SERVICE_DESK_API_URL;
    @Value("${jira.apiUsername}")
    private String JIRA_API_USERNAME;
    @Value("#{credentials['jira.apiToken']}")
    private String JIRA_API_TOKEN;
    @Value("${jira.projectName}")
    private String JIRA_PROJECT_NAME;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity addIssue(String jsonData, CustomUserDetails curUser) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        JsonObject jsonObject, fieldsObject, reporterObject;

        HttpHeaders headers = getCommonHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        jsonObject = JsonParser.parseString​(jsonData).getAsJsonObject();
        fieldsObject = jsonObject.getAsJsonObject("fields");

        reporterObject = fieldsObject.getAsJsonObject("reporter");
        reporterObject.addProperty("id", getUserJiraAccountId(curUser));

        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

        response = restTemplate.exchange(
                JIRA_API_URL + "/issue", HttpMethod.POST, entity, String.class);

        return response;
    }

    @Override
    public ResponseEntity addIssueAttachment(MultipartFile file, String issueId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        try {

            HttpHeaders headers = getCommonHeaders();
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

            return response;
        } catch (IOException ex) {
            Logger.getLogger(JiraServiceDeskApiServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return response;
        }
    }

    @Override
    public JiraServiceDeskIssuesDTO getIssuesSummary(CustomUserDetails curUser) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        
        String jiraAccountId = "";
        JiraServiceDeskIssuesDTO issuesDTO = new JiraServiceDeskIssuesDTO();
        jiraAccountId = this.userService.getUserJiraAccountId(curUser.getUserId());

        if (jiraAccountId != null && !jiraAccountId.equals("")) {
            StringBuilder jqlSearchString_Open = new StringBuilder("");
            jqlSearchString_Open.append("project=").append(JIRA_PROJECT_NAME)
                    .append(" AND reporter=").append(jiraAccountId)
                    .append(" AND (statusCategory='To Do' OR statusCategory='In Progress')");
            
            StringBuilder jqlSearchString_Done = new StringBuilder("");
            jqlSearchString_Done.append("project=").append(JIRA_PROJECT_NAME)
                    .append(" AND reporter=").append(jiraAccountId)
                    .append(" AND statusCategory='Done'");

//            System.out.println("URL : " + jqlSearchString.toString());

            HttpHeaders headers = getCommonHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            JSONObject obj = new JSONObject();           
            obj.put("jql", jqlSearchString_Open.toString());
            obj.put("maxResults", 100);
            HttpEntity<String> entity_Open = new HttpEntity<String>(obj.toJSONString(), headers);
            
            response = restTemplate.exchange(
                    JIRA_API_URL + "/search", HttpMethod.POST, entity_Open, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonObject jsonObject = JsonParser.parseString​(response.getBody()).getAsJsonObject();
                JsonElement element = jsonObject.get("total");
                issuesDTO.setOpenIssues(element.getAsInt());                
            }    
            
            obj.put("jql", jqlSearchString_Done.toString());            
            HttpEntity<String> entity_Done = new HttpEntity<String>(obj.toJSONString(), headers);
            
            response = restTemplate.exchange(
                    JIRA_API_URL + "/search", HttpMethod.POST, entity_Done, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonObject jsonObject = JsonParser.parseString​(response.getBody()).getAsJsonObject();
                JsonElement element = jsonObject.get("total");
                issuesDTO.setAddressedIssues(element.getAsInt());                       
            }
            
        } 
        
        return issuesDTO;
    }

    private String getUserJiraAccountId(CustomUserDetails curUser) {

        String jiraAccountId = "";
        jiraAccountId = this.userService.getUserJiraAccountId(curUser.getUserId());

        if (jiraAccountId != null && !jiraAccountId.equals("")) {
            return jiraAccountId;
        } else {
            return this.addJiraCustomer(curUser);
        }
    }

    private String addJiraCustomer(CustomUserDetails curUser) {
        JSONObject obj = new JSONObject();
        String accountId = "";
        obj.put("email", curUser.getEmailId());
        obj.put("displayName", curUser.getUsername());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;

        HttpHeaders headers = getCommonHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(obj.toJSONString(), headers);

        try {
            response = restTemplate.exchange(
                JIRA_SERVICE_DESK_API_URL + "/customer", HttpMethod.POST, entity, String.class);
            JsonObject jsonObject = JsonParser.parseString​(response.getBody()).getAsJsonObject();
            JsonElement element = jsonObject.get("accountId");
            accountId = element.getAsString();
            this.userService.addUserJiraAccountId(curUser.getUserId(), accountId);
            return accountId;
        } catch (Exception e) {            
            this.syncUserJiraAccountId(curUser.getEmailId());
            return this.userService.getUserJiraAccountId(curUser.getUserId());
        }               
    }

    private HttpHeaders getCommonHeaders() {

        String authStr = JIRA_API_USERNAME + ":" + JIRA_API_TOKEN;
        String base64Creds = "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", base64Creds);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;

    }

    @Override
    public String syncUserJiraAccountId(String emailId) {
                
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;  
        int total = 0;
        JsonArray jsonArray = null;
        StringBuilder sb = new StringBuilder();

        HttpHeaders headers = getCommonHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-ExperimentalApi", "opt-in");

        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        response = restTemplate.exchange(
                JIRA_SERVICE_DESK_API_URL + "/servicedesk/" + JIRA_PROJECT_NAME + "/customer?query="+emailId, HttpMethod.GET, entity, String.class);
        
        if (response.getStatusCode() == HttpStatus.OK) {

            JsonObject jsonObject = JsonParser.parseString​(response.getBody()).getAsJsonObject();
            JsonElement element = jsonObject.get("size");
            total = element.getAsInt();
            
            if(total > 0) {
                List<String> userEmails = new ArrayList<>();
                if(!emailId.equals("")) {
                    userEmails.add(emailId);
                } else {
                    userEmails = this.userService.getUserListForUpdateJiraAccountId();
                }
                jsonArray = jsonObject.getAsJsonArray("values");                
                sb.append("{");
                for(int i=0 ; i < total ; i++) {
                    String jiraEmailAddress = "", jiraAccountId = "";
                    JsonObject jsonObject1 = jsonArray.get(i).getAsJsonObject();
                    jiraEmailAddress = jsonObject1.get("emailAddress").getAsString();
                    jiraAccountId = jsonObject1.get("accountId").getAsString();                        
                    for(int j=0 ; j<userEmails.size() ; j++) {                        
                        if(userEmails.get(j).equalsIgnoreCase(jiraEmailAddress)) {
                            this.userService.updateUserJiraAccountId(userEmails.get(j), jiraAccountId);
                            sb.append(jsonObject1);
                        }
                    }
                }
                sb.append("}");
            }            
        }

        return sb.toString() ;
                
    }
}
