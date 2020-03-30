/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UnitTypeDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import cc.altius.FASP.model.UnitType;
import cc.altius.FASP.service.UnitTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class UnitTypeServiceImpl implements UnitTypeService {

    @Autowired
    private UnitTypeDao unitTypeDao;

    @Override
    public int addUnitType(UnitType u, CustomUserDetails curUser) {
        return this.unitTypeDao.addUnitType(u, curUser);
    }

    @Override
    public int updateUnitType(UnitType u, CustomUserDetails curUser) {
        UnitType ut = this.unitTypeDao.getUnitTypeById(u.getUnitTypeId());
        if (ut == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return this.unitTypeDao.updateUnitType(u, curUser);
    }

    @Override
    public List<UnitType> getUnitTypeList() {
        return this.unitTypeDao.getUnitTypeList();
    }

    @Override
    public UnitType getUnitTypeById(int unitTypeId) {
        return this.unitTypeDao.getUnitTypeById(unitTypeId);
    }

    @Override
    public List<PrgUnitTypeDTO> getUnitTypeListForSync() {
        return this.unitTypeDao.getUnitTypeListForSync();
    }

}
