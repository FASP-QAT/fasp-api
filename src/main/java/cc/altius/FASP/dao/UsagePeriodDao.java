/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UsagePeriod;
import java.util.List;

/**
 *
 * @author akil
 */
public interface UsagePeriodDao {

    public List<UsagePeriod> getUsagePeriodList(boolean active, CustomUserDetails curUser);

    public List<UsagePeriod> getUsagePeriodListForSync(String lastSyncDate, CustomUserDetails curUser);

    public int addAndUpdateUsagePeriod(List<UsagePeriod> usagePeriodList, CustomUserDetails curUser);
}
