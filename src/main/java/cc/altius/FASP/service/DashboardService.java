/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DashboardUser;
import cc.altius.FASP.model.report.DashboardInput;
import cc.altius.FASP.model.report.DashboardBottom;
import cc.altius.FASP.model.report.DashboardForLoadProgram;
import cc.altius.FASP.model.report.DashboardTop;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface DashboardService {

    Map<String, Object> getApplicationLevelDashboard(CustomUserDetails curUser);

    Map<String, Object> getRealmLevelDashboard(CustomUserDetails curUser);

    Map<String, Object> getSupplyPlanReviewerLevelDashboard(CustomUserDetails curUser);

    List<DashboardUser> getUserListForApplicationLevelAdmin(CustomUserDetails curUser);

    List<DashboardUser> getUserListForRealmLevelAdmin(CustomUserDetails curUser);

    List<DashboardTop> getDashboardTop(String[] programIds, CustomUserDetails curUser);

    DashboardBottom getDashboardBottom(DashboardInput ei, CustomUserDetails curUser) throws ParseException;
    
    DashboardForLoadProgram getDashboardForLoadProgram(int programId, int versionId, int noOfMonthsInPastForBottom, int noOfMonthsInFutureForTop, CustomUserDetails curUser) throws ParseException;
}
