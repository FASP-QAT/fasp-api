/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.model.DTO.PrgHealthAreaDTO;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.service.HealthAreaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class HealthAreaServiceImpl implements HealthAreaService {

    @Autowired
    HealthAreaDao healthAreaDao;

    @Override
    public List<PrgHealthAreaDTO> getHealthAreaListForSync(String lastSyncDate) {
        return this.healthAreaDao.getHealthAreaListForSync(lastSyncDate);
    }
    
    @Override
    public int addHealthArea(HealthArea h, int curUser) {
        return this.healthAreaDao.addHealthArea(h, curUser);
    }

    @Override
    public int updateHealthArea(HealthArea h, int curUser) {
        return this.healthAreaDao.updateHealthArea(h, curUser);
    }

    @Override
    public List<HealthArea> getHealthAreaList() {
        return this.healthAreaDao.getHealthAreaList();
    }

    @Override
    public HealthArea getHealthAreaById(int healthAreaId) {
        return this.healthAreaDao.getHealthAreaById(healthAreaId);
    }
    
}
