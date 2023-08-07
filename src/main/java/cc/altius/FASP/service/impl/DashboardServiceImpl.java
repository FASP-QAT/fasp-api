/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DashboardDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.ProgramCount;
import cc.altius.FASP.service.DashboardService;
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

}
