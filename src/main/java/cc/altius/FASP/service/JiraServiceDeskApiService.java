/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.JiraServiceDeskIssuesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author altius
 */
public interface JiraServiceDeskApiService {

    public ResponseEntity addIssue(String jsonData, CustomUserDetails curUser);

    public ResponseEntity addIssueAttachment(MultipartFile file, String issueId);

    public JiraServiceDeskIssuesDTO getIssuesSummary(CustomUserDetails curUser);

    public String syncUserJiraAccountId(String emailId);
}
