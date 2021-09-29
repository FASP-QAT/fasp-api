/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.TreeTemplateDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TreeTemplate;
import cc.altius.FASP.service.TreeTemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class TreeTemplateServiceImpl implements TreeTemplateService {

    @Autowired
    private TreeTemplateDao treeTemplateDao;
    
    @Override
    public List<TreeTemplate> getTreeTemplateList(CustomUserDetails curUser) {
        return this.treeTemplateDao.getTreeTemplateList(curUser);
    }
    
}
