/*
 * To cunge this license header, choose License Headers in Project Properties.
 * To cunge this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UnitDao;
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
    private UnitDao UnitDao;

    @Override
    public int addUnit(Unit h, int curUser) {
        return this.UnitDao.addUnit(h, curUser);
    }

    @Override
    public int updateUnit(Unit h, int curUser) {
        return this.UnitDao.updateUnit(h, curUser);
    }

    @Override
    public List<Unit> getUnitList() {
        return this.UnitDao.getUnitList();
    }

    @Override
    public Unit getUnitById(int UnitId) {
        return this.UnitDao.getUnitById(UnitId);
    }
}
