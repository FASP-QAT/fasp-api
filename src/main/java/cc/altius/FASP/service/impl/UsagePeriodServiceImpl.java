/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UsagePeriodDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UsagePeriod;
import cc.altius.FASP.service.UsagePeriodService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class UsagePeriodServiceImpl implements UsagePeriodService {

    @Autowired
    private UsagePeriodDao usagePeriodDao;

    @Override
    public List<UsagePeriod> getUsagePeriodList(boolean active, CustomUserDetails curUser) {
        return this.usagePeriodDao.getUsagePeriodList(active, curUser);
    }

    @Override
    public int addAndUpdateUsagePeriod(List<UsagePeriod> usagePeriodList, CustomUserDetails curUser) {
        return this.usagePeriodDao.addAndUpdateUsagePeriod(usagePeriodList, curUser);
    }

}
