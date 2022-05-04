/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.EquivalencyUnitDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.EquivalencyUnitMapping;
import cc.altius.FASP.service.EquivalencyUnitService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class EquivalencyUnitServiceImpl implements EquivalencyUnitService {

    @Autowired
    private EquivalencyUnitDao equivalencyUnitDao;

    @Override
    public List<EquivalencyUnit> getEquivalencyUnitList(boolean active, CustomUserDetails curUser) {
        return this.equivalencyUnitDao.getEquivalencyUnitList(active, curUser);
    }

    @Override
    public int addAndUpdateEquivalencyUnit(List<EquivalencyUnit> equivalencyUnitList, CustomUserDetails curUser) {
        return this.equivalencyUnitDao.addAndUpdateEquivalencyUnit(equivalencyUnitList, curUser);
    }

    @Override
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingList(boolean active, CustomUserDetails curUser) {
        return this.equivalencyUnitDao.getEquivalencyUnitMappingList(active, curUser);
    }

    @Override
    public int addAndUpdateEquivalencyUnitMapping(List<EquivalencyUnitMapping> equivalencyUnitMappingList, CustomUserDetails curUser) throws IllegalAccessException, CouldNotSaveException {
        for (EquivalencyUnitMapping eum : equivalencyUnitMappingList) {
            if (curUser.getRealm().getRealmId() != eum.getEquivalencyUnit().getRealm().getId()) {
                throw new IllegalAccessException("Equivalency Unit from a different Realm");
            }
        }
        return this.equivalencyUnitDao.addAndUpdateEquivalencyUnitMapping(equivalencyUnitMappingList, curUser);
    }

    @Override
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingListForSync(String programIdsString, CustomUserDetails curUser) {
        return this.equivalencyUnitDao.getEquivalencyUnitMappingListForSync(programIdsString, curUser);
    }

}
