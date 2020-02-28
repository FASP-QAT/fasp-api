/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UnitDao;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.Unit;
import cc.altius.FASP.service.UnitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitDao unitDao;

    @Override
    public List<PrgUnitDTO> getUnitListForSync(String lastSyncDate) {
        return this.unitDao.getUnitListForSync(lastSyncDate);
    }

    @Override
    public int addUnit(Unit h, int curUser) {
        return this.unitDao.addUnit(h, curUser);
    }

    @Override
    public int updateUnit(Unit h, int curUser) {
        return this.unitDao.updateUnit(h, curUser);
    }

    @Override
    public List<Unit> getUnitList() {
        return this.unitDao.getUnitList();
    }

    @Override
    public Unit getUnitById(int UnitId) {
        return this.unitDao.getUnitById(UnitId);
    }
}
