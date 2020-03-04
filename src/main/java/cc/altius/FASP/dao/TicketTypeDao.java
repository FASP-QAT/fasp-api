/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.TicketType;
import java.util.List;

/**
 *
 * @author palash
 */
public interface TicketTypeDao {
   public List<TicketType> getTicketTypeList(); 
}
