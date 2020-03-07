/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.TicketTypeDao;
import cc.altius.FASP.model.TicketType;
import cc.altius.FASP.service.TicketTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author palash
 */
@Service
public class TicketTypeServiceImpl implements TicketTypeService{

    @Autowired
    private TicketTypeDao ticketTypeDao;
    @Override
    public List<TicketType> getTicketTypeList() {
        return this.ticketTypeDao.getTicketTypeList();
    }
    
}
