/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.EquivalencyUnitDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.EquivalencyUnitMapping;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.EquivalencyUnitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class EquivalencyUnitServiceImpl implements EquivalencyUnitService {

    @Autowired
    private EquivalencyUnitDao equivalencyUnitDao;
    @Autowired
    private ProgramCommonDao programCommonDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<EquivalencyUnit> getEquivalencyUnitList(boolean active, CustomUserDetails curUser) {
        return this.equivalencyUnitDao.getEquivalencyUnitList(active, curUser);
    }

    @Override
    public List<SimpleObject> getEquivalencyUnitDropDownList(CustomUserDetails curUser) {
        return this.equivalencyUnitDao.getEquivalencyUnitDropDownList(curUser);
    }

    @Override
    public int addAndUpdateEquivalencyUnit(List<EquivalencyUnit> equivalencyUnitList, CustomUserDetails curUser) {
        for (EquivalencyUnit eu : equivalencyUnitList) {
            if (eu.getProgram() != null && eu.getProgram().getId() != null && eu.getProgram().getId() != 0) {
                try {
                    SimpleProgram p = this.programCommonDao.getSimpleProgramById(eu.getProgram().getId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
                } catch (EmptyResultDataAccessException erda) {
                    throw new AccessDeniedException("You do not have the rights to Program " + eu.getProgram().getId());
                }
            }
        }
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
            if (eum.getProgram() != null && eum.getProgram().getId() != null && eum.getProgram().getId() != 0) {
                try {
                    SimpleProgram p = this.programCommonDao.getSimpleProgramById(eum.getProgram().getId(), GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
                } catch (EmptyResultDataAccessException erda) {
                    throw new AccessDeniedException("You do not have the rights to Program " + eum.getProgram().getId());
                }
            }
        }
        return this.equivalencyUnitDao.addAndUpdateEquivalencyUnitMapping(equivalencyUnitMappingList, curUser);
    }

    @Override
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingForForecastingUnit(int fuId, int programId, CustomUserDetails curUser) {
        if (programId != 0) {
            try {
                this.programCommonDao.getSimpleProgramById(programId, 0, curUser);
            } catch (EmptyResultDataAccessException erda) {
                throw new AccessDeniedException("You do not have access to this resource");
            }
        }
        return this.equivalencyUnitDao.getEquivalencyUnitMappingForForecastingUnit(fuId, programId, curUser);
    }

    @Override
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingListForSync(String programIdsString, CustomUserDetails curUser) {
        return this.equivalencyUnitDao.getEquivalencyUnitMappingListForSync(programIdsString, curUser);
    }

}
