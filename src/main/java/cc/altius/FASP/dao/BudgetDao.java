/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.AddBudgetResponse;
import cc.altius.FASP.model.SimpleCodeObject;
import java.util.List;

/**
 *
 * @author akil
 */
public interface BudgetDao {

    public AddBudgetResponse addBudget(Budget b, CustomUserDetails curUser);

    public int updateBudget(Budget b, CustomUserDetails curUser);

    public List<Budget> getBudgetListForProgramIds(String[] programIds, CustomUserDetails curUser);

    public List<Budget> getBudgetList(CustomUserDetails curUser);

    public List<Budget> getBudgetListForRealm(int realmId, CustomUserDetails curUser);

    public Budget getBudgetById(int BudgetId, CustomUserDetails curUser);
    
    public List<SimpleCodeObject> getBudgetDropdownFilterMultipleFundingSources(String fundingSourceIds, CustomUserDetails curUser);
    
    public List<SimpleCodeObject> getBudgetDropdownForProgram(int programId, CustomUserDetails curUser);

    public List<Budget> getBudgetListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<Budget> getBudgetListForSyncProgram(String programIdsString, CustomUserDetails curUser);

}
