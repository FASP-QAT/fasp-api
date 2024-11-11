/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DashboardDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.ProgramCount;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.report.DashboardInput;
import cc.altius.FASP.model.report.DashboardBottom;
import cc.altius.FASP.model.report.DashboardForLoadProgram;
import cc.altius.FASP.model.report.DashboardTop;
import cc.altius.FASP.service.DashboardService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.RealmService;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDao dashboardDao;
    @Autowired
    private ProgramService programService;
    @Autowired
    private RealmService realmService;
    @Autowired
    ProgramCommonDao programCommonDao;

    @Override
    public Map<String, Object> getApplicationLevelDashboard(CustomUserDetails curUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("REALM_COUNT", this.dashboardDao.getRealmCount(curUser));
        map.put("LANGUAGE_COUNT", this.dashboardDao.getLanguageCount(curUser));
        return map;
    }

    @Override
    public Map<String, Object> getRealmLevelDashboard(CustomUserDetails curUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("REALM_COUNTRY_COUNT", this.dashboardDao.getRealmCount(curUser));
        map.put("TECHNICAL_AREA_COUNT", this.dashboardDao.getHealthAreaCount(curUser));
        map.put("REGION_COUNT", this.dashboardDao.getRegionCount(curUser));
        map.put("ORGANIZATION_COUNT", this.dashboardDao.getOrganisationCount(curUser));
        ProgramCount programCount = this.dashboardDao.getProgramCount(curUser);
        map.put("PROGRAM_COUNT", programCount.getProgramCount()); // ProgramType = 1 
        map.put("DATASET_COUNT", programCount.getDatasetCount()); // ProgramType = 2
        map.put("SUPPLY_PLAN_COUNT", this.dashboardDao.getSupplyPlanPendingCount(curUser));
        map.put("USER_COUNT", this.dashboardDao);
        map.put("LINKED_ERP_SHIPMENTS_COUNT", this.dashboardDao);
        ProgramCount fullProgramCount = this.dashboardDao.getFullProgramCount(curUser);
        map.put("FULL_PROGRAM_COUNT", programCount.getProgramCount()); // ProgramType = 1 
        return map;
    }

    @Override
    public Map<String, Object> getSupplyPlanReviewerLevelDashboard(CustomUserDetails curUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("SUPPLY_PLAN_COUNT", this.dashboardDao.getSupplyPlanPendingCount(curUser));
        return map;
    }

    @Override
    public List<DashboardUser> getUserListForApplicationLevelAdmin(CustomUserDetails curUser) {
        return this.dashboardDao.getUserListForApplicationLevelAdmin(curUser);
    }

    @Override
    public List<DashboardUser> getUserListForRealmLevelAdmin(CustomUserDetails curUser) {
        return this.dashboardDao.getUserListForRealmLevelAdmin(curUser);
    }

    @Override
    public List<DashboardTop> getDashboardTop(String[] programIds, CustomUserDetails curUser) throws AccessControlFailedException {
        curUser.setRealm(this.realmService.getRealmById(curUser.getRealm().getRealmId(), curUser));
        for (String program : programIds) {
            this.programCommonDao.getSimpleProgramById(Integer.parseInt(program), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        }
        return this.dashboardDao.getDashboardTop(programIds, curUser);
    }

    @Override
    public DashboardBottom getDashboardBottom(DashboardInput ei, CustomUserDetails curUser) throws ParseException, AccessControlFailedException {
        SimpleProgram p = this.programService.getSimpleProgramById(ei.getProgramId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        DashboardBottom db = this.dashboardDao.getDashboardBottom(ei, curUser);
        db.setProgram(p);
        return db;
    }

    @Override
    public DashboardForLoadProgram getDashboardForLoadProgram(int programId, int versionId, int noOfMonthsInPastForBottom, int noOfMonthsInFutureForBottom, int noOfMonthsInPastForTop, int noOfMonthsInFutureForTop, CustomUserDetails curUser) throws ParseException, AccessControlFailedException {
        SimpleProgram p = this.programService.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        DashboardForLoadProgram db = this.dashboardDao.getDashboardForLoadProgram(programId, versionId, noOfMonthsInPastForBottom, noOfMonthsInFutureForBottom, noOfMonthsInPastForTop, noOfMonthsInFutureForTop, curUser);
        db.setProgram(p);
        return db;
    }

}
