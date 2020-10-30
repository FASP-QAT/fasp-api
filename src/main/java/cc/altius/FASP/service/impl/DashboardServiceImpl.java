/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DashboardDao;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.service.DashboardService;
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
    public Map<String, Object> getApplicationLevelDashboard() {
        return this.dashboardDao.getApplicationLevelDashboard();
    }

    @Override
    public Map<String, Object> getRealmLevelDashboard(int realmId) {
        return this.dashboardDao.getRealmLevelDashboard(realmId);
    }

    @Override
    public List<DashboardUser> getUserListForApplicationLevelAdmin() {
        return this.dashboardDao.getUserListForApplicationLevelAdmin();
    }

    @Override
    public List<DashboardUser> getUserListForRealmLevelAdmin(int realmId) {
        return this.dashboardDao.getUserListForRealmLevelAdmin(realmId);
    }

}
