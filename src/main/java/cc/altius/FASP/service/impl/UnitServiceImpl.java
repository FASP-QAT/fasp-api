/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UnitDao;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.service.UnitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitDao unitDao;

    @Override
    public List<PrgUnitDTO> getUnitListForSync(String lastSyncDate) {
        return this.unitDao.getUnitListForSync(lastSyncDate);
    }

}
