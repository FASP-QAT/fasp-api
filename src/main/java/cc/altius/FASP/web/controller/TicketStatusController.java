/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.TicketStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author palash
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop", "https://qat.altius.cc"})
public class TicketStatusController {

    @Autowired
    private TicketStatusService ticketStatusService;

    @GetMapping("/api/ticketStatus")
    public ResponseFormat getTicketSatusList() {
        try {
            //System.out.println("listTicket---------->"+this.ticketService.getTicketList());
            return new ResponseFormat("Success", "", this.ticketStatusService.getTicketStatusList());
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
}
