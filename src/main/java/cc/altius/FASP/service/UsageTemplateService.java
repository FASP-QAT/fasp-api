/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UsageTemplate;
import java.util.List;

/**
 *
 * @author akil
 */
public interface UsageTemplateService {

    public List<UsageTemplate> getUsageTemplateList(boolean active, CustomUserDetails curUser);

    public List<UsageTemplate> getUsageTemplateList(int tracerCategoryId, CustomUserDetails curUser);

    public int addAndUpdateUsageTemplate(List<UsageTemplate> usageTemplateList, CustomUserDetails curUser) throws AccessControlFailedException;

    public List<UsageTemplate> getUsageTemplateListForSync(String programIdsString, CustomUserDetails curUser);
}
