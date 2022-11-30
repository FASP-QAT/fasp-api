///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package cc.altius.FASP.rest.controller;
//
//import cc.altius.FASP.service.JiraServiceDeskApiService;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import javax.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// *
// * @author altius
// */
//@RestController
//public class JiraAccountSyncController {
//    
//    @Autowired
//    private JiraServiceDeskApiService jiraServiceDeskApiService;
//    
//    @GetMapping("/jira/syncJiraAccountIds")
//    public String syncUserJiraAccountId(HttpServletResponse response) throws FileNotFoundException, IOException {
//        
//        String result =  this.jiraServiceDeskApiService.syncUserJiraAccountId("");
//        
//        return result;
//    }
//}
