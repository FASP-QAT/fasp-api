/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.model.DTO.PrgHealthAreaDTO;
import cc.altius.FASP.service.HealthAreaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class HealthAreaServiceImpl implements HealthAreaService {

    @Autowired
    HealthAreaDao healthAreaDao;

    @Override
    public List<PrgHealthAreaDTO> getHealthAreaListForSync(String lastSyncDate) {
        return this.healthAreaDao.getHealthAreaListForSync(lastSyncDate);
    }

}
