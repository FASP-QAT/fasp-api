/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.DashboardUser;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface DashboardDao {

    Map<String, Object> getApplicationLevelDashboard();

    Map<String, Object> getRealmLevelDashboard(int realmId);

    List<DashboardUser> getUserListForApplicationLevelAdmin();

    List<DashboardUser> getUserListForRealmLevelAdmin(int realmId);

}
