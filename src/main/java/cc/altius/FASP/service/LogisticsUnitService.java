/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.DTO.PrgLogisticsUnitDTO;
import java.util.List;

/**
 *
 * @author altius
 */
public interface LogisticsUnitService {
    
    public List<PrgLogisticsUnitDTO> getLogisticsUnitListForSync(String lastSyncDate,int realmId);
    
}
