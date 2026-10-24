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
 * Service for interacting with the Jira Service Desk API.
 */
public interface JiraServiceDeskApiService {

    /**
     * Creates a new issue in Jira Service Desk.
     *
     * @param jsonData JSON representation of the issue
     * @param curUser current authenticated user
     * @return response containing the Jira API response
     */
    public ResponseEntity<String> addIssue(String jsonData, CustomUserDetails curUser);

    /**
     * Adds an attachment to an existing Jira Service Desk issue.
     *
     * @param file file to attach to the Jira issue
     * @param issueId Jira issue identifier
     * @return response containing the Jira API response
     */
    public ResponseEntity<String> addIssueAttachment(MultipartFile file, String issueId);

    /**
     * Retrieves a summary of Jira Service Desk issues for the current user.
     *
     * @param curUser current authenticated user
     * @return summary containing the user's open and addressed issues
     */
    public JiraServiceDeskIssuesDTO getIssuesSummary(CustomUserDetails curUser);

    /**
     * Synchronizes Jira account IDs for users associated with the provided email address.
     *
     * @param emailId email address used to find the Jira customer
     * @return information about the synchronized Jira customer accounts
     */
    public String syncUserJiraAccountId(String emailId);

    /**
     * Creates a Jira Service Desk customer for the current user.
     *
     * @param curUser current authenticated user
     * @return Jira account ID of the created customer
     */
    public String addJiraCustomer(CustomUserDetails curUser);
}
