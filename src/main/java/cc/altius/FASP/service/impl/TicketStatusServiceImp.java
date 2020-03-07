/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.TicketStatusDao;
import cc.altius.FASP.model.TicketStatus;
import cc.altius.FASP.service.TicketStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class TicketStatusServiceImp  implements TicketStatusService{

    @Autowired
    private TicketStatusDao ticketStatusDao;
    @Override
    public List<TicketStatus> getTicketStatusList() {
        return this.ticketStatusDao.getTicketStatusList();
    }
    
}
