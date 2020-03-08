/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.ProcurementAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class ProcurementAgentRestController {

    @Autowired
    private ProcurementAgentService procurementAgentService;

    @PostMapping(path = "/procurementAgent")
    public ResponseFormat postProcurementAgent(@RequestBody ProcurementAgent procurementAgent, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int procurementAgentId = this.procurementAgentService.addProcurementAgent(procurementAgent, curUser);
            return new ResponseFormat("Successfully added ProcurementAgent with Id " + procurementAgentId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/procurementAgent")
    public ResponseFormat putProcurementAgent(@RequestBody ProcurementAgent procurementAgent, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.procurementAgentService.updateProcurementAgent(procurementAgent, curUser);
            return new ResponseFormat("Successfully updated ProcurementAgent");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/procurementAgent")
    public ResponseFormat getProcurementAgent(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.procurementAgentService.getProcurementAgentList(true, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/procurementAgent/{procurementAgentId}")
    public ResponseFormat getProcurementAgent(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.procurementAgentService.getProcurementAgentById(procurementAgentId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }   
}
