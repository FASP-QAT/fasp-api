/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.TicketStatus;
import java.util.List;

/**
 *
 * @author palash
 */
public interface TicketStatusService {
    public List<TicketStatus> getTicketStatusList();
}
