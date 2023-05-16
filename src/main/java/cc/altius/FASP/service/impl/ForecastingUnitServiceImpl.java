/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.service.AclService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.ForecastingUnitService;
import cc.altius.FASP.dao.ForecastingUnitDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.AutoCompleteInput;
import cc.altius.FASP.model.DTO.AutocompleteInputWithTracerCategoryDTO;
import cc.altius.FASP.model.DTO.PlanningUnitAndTracerCategoryDTO;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.SimpleObject;
import java.util.LinkedList;

/**
 *
 * @author altius
 */
@Service
public class ForecastingUnitServiceImpl implements ForecastingUnitService {

    @Autowired
    private ForecastingUnitDao forecastingUnitDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;
    @Autowired
    private ProgramCommonDao programCommonDao;

    @Override
    public List<ForecastingUnit> getForecastingUnitList(boolean active, CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitList(active, curUser);
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitList(int realmId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.forecastingUnitDao.getForecastingUnitList(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }

    }

    @Override
    public int addForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, forecastingUnit.getRealm().getId())) {
            return this.forecastingUnitDao.addForecastingUnit(forecastingUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateForecastingUnit(ForecastingUnit forecastingUnit, CustomUserDetails curUser) {
        ForecastingUnit pr = this.getForecastingUnitById(forecastingUnit.getForecastingUnitId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pr.getRealm().getId())) {
            return this.forecastingUnitDao.updateForecastingUnit(forecastingUnit, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public ForecastingUnit getForecastingUnitById(int forecastingUnitId, CustomUserDetails curUser) {
        ForecastingUnit pr = this.forecastingUnitDao.getForecastingUnitById(forecastingUnitId, curUser);
        List<Integer> emptyList = new LinkedList<Integer>();
        emptyList.add(0);
        if (this.aclService.checkAccessForUser(curUser, pr.getRealm().getId(), 0, emptyList, 0, pr.getForecastingUnitId())) {
            return pr;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        if (programIdsString.length() > 0) {
            return this.forecastingUnitDao.getForecastingUnitListForSyncProgram(programIdsString, curUser);
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitListByTracerCategory(int tracerCategoryId, boolean active, CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitListByTracerCategory(tracerCategoryId, active, curUser);
    }

    @Override
    public List<ForecastingUnit> getForecastingUnitListByTracerCategoryIds(String[] tracerCategoryIds, boolean active, CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitListByTracerCategoryIds(tracerCategoryIds, active, curUser);
    }

    @Override
    public List<SimpleObject> getForecastingUnitListForDataset(int programId, int versionId, CustomUserDetails curUser) {
        Program p = this.programCommonDao.getProgramById(programId, GlobalConstants.PROGRAM_TYPE_DATASET, curUser);
        if (this.aclService.checkProgramAccessForUser(curUser, p.getRealmCountry().getRealm().getRealmId(), programId, p.getHealthAreaIdList(), p.getOrganisation().getId())) {
            return this.forecastingUnitDao.getForecastingUnitListForDataset(programId, versionId, curUser);
        } else {
            throw new AccessDeniedException("You do not have access to this Program");
        }
    }

    @Override
    public List<SimpleObject> getForecastingUnitListForAutoComplete(AutoCompleteInput autoCompleteInput, CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitListForAutoComplete(autoCompleteInput, curUser);
    }

    @Override
    public List<SimpleObject> getForecastingUnitListForAutoCompleteWithFilterTracerCategory(AutocompleteInputWithTracerCategoryDTO autoCompleteInput, CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitListForAutoCompleteWithFilterTracerCategory(autoCompleteInput, curUser);
    }

    @Override
    public List<SimpleObject> getForecastingUnitDropdownList(CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitDropdownList(curUser);
    }

    @Override
    public List<SimpleObject> getForecastingUnitDropdownListWithFilterForPuAndTc(PlanningUnitAndTracerCategoryDTO input, CustomUserDetails curUser) {
        return this.forecastingUnitDao.getForecastingUnitDropdownListWithFilterForPuAndTc(input, curUser);
    }

}
