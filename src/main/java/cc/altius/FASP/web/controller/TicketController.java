/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.model.Ticket;
import cc.altius.FASP.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author palash
 */
@RestController
//@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io"})
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/api/ticket")
    public ResponseFormat getTicket() {
        try {
            //System.out.println("listTicket---------->"+this.ticketService.getTicketList());
            return new ResponseFormat("Success", "", this.ticketService.getTicketList());
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/api/ticket")
    public ResponseFormat putTicket(@RequestBody Ticket ticket, Authentication auth) {
        try {
            int curUser = ((CustomUserDetails) auth.getPrincipal()).getUserId();
            int rows = this.ticketService.updateTicket(ticket, curUser);
            return new ResponseFormat("Successfully updated Ticket");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

}
