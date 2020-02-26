/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UnitTypeDao;
import cc.altius.FASP.model.UnitType;
import cc.altius.FASP.service.UnitTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class UnitTypeServiceImpl implements UnitTypeService {

    @Autowired
    UnitTypeDao unitTypeDao;

    @Override
    public List<UnitType> getUnitTypeListForSync() {
        return this.unitTypeDao.getUnitTypeListForSync();
    }

}
