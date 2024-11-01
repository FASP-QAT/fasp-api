/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.UsageTemplateDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UsageTemplate;
import cc.altius.FASP.service.UsageTemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class UsageTemplateServiceImpl implements UsageTemplateService {

    @Autowired
    private UsageTemplateDao usageTemplateDao;
    @Autowired
    private ProgramCommonDao programCommonDao;

    @Override
    public List<UsageTemplate> getUsageTemplateList(boolean active, CustomUserDetails curUser) {
        return this.usageTemplateDao.getUsageTemplateList(active, curUser);
    }

    @Override
    public List<UsageTemplate> getUsageTemplateList(int tracerCategoryId, CustomUserDetails curUser) {
        return this.usageTemplateDao.getUsageTemplateList(tracerCategoryId, curUser);
    }

    @Override
    public int addAndUpdateUsageTemplate(List<UsageTemplate> usageTemplateList, CustomUserDetails curUser) throws AccessControlFailedException {
        for (UsageTemplate usageTemplate : usageTemplateList) {
            if (usageTemplate.getProgram() != null && usageTemplate.getProgram().getId() != null && usageTemplate.getProgram().getId() != 0) {
                try {
                    this.programCommonDao.getSimpleProgramById(usageTemplate.getProgram().getId(), 0, curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
        }
        return this.usageTemplateDao.addAndUpdateUsageTemplate(usageTemplateList, curUser);
    }

    @Override
    public List<UsageTemplate> getUsageTemplateListForSync(String programIdsString, CustomUserDetails curUser) {
        return this.usageTemplateDao.getUsageTemplateListForSync(programIdsString, curUser);
    }

}
