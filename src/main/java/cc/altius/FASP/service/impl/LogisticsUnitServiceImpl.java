/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.LogisticsUnitDao;
import cc.altius.FASP.model.DTO.PrgLogisticsUnitDTO;
import cc.altius.FASP.service.LogisticsUnitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class LogisticsUnitServiceImpl implements LogisticsUnitService {

    @Autowired
    LogisticsUnitDao logisticsUnitDao;

    @Override
    public List<PrgLogisticsUnitDTO> getLogisticsUnitListForSync(String lastSyncDate) {
        return this.logisticsUnitDao.getLogisticsUnitListForSync(lastSyncDate);
    }

}
