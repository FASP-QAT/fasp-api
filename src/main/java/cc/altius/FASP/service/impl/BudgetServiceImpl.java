/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.BudgetDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Budget;
import cc.altius.FASP.service.BudgetService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetDao budgetDao;

    @Override
    public int addBudget(Budget h, CustomUserDetails curUser) {
        return this.budgetDao.addBudget(h, curUser);
    }

    @Override
    public int updateBudget(Budget h, CustomUserDetails curUser) {
        return this.budgetDao.updateBudget(h, curUser);
    }

    @Override
    public List<Budget> getBudgetList(CustomUserDetails curUser) {
        return this.budgetDao.getBudgetList(curUser);
    }

    @Override
    public Budget getBudgetById(int BudgetId, CustomUserDetails curUser) {
        return this.budgetDao.getBudgetById(BudgetId, curUser);
    }
}
