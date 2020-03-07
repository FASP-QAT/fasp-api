/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.TicketDao;
import cc.altius.FASP.model.Ticket;
import cc.altius.FASP.service.TicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class TicketServiceImp implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Override
    public List<Ticket> getTicketList() {
        return this.ticketDao.getTicketList();
    }

    @Override
    public int updateTicket(Ticket ticket, int curUser) {
       return this.ticketDao.updateTicket(ticket, curUser);
    }

}
