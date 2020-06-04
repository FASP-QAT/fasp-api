/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Budget;
import java.util.List;

/**
 *
 * @author akil
 */
public interface BudgetService {

    public int addBudget(Budget b, CustomUserDetails curUser);

    public int updateBudget(Budget b, CustomUserDetails curUser);

    public List<Budget> getBudgetListForProgramIds(String[] programIds, CustomUserDetails curUser);

    public List<Budget> getBudgetList(CustomUserDetails curUser);

    public List<Budget> getBudgetListForRealm(int realmId, CustomUserDetails curUser);

    public Budget getBudgetById(int BudgetId, CustomUserDetails curUser);

    public List<Budget> getBudgetListForSync(String lastSyncDate, CustomUserDetails curUser);
}
