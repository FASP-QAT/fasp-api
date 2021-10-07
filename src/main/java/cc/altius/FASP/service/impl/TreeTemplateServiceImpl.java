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
    public List<TreeTemplate> getTreeTemplateList(boolean nodeData, CustomUserDetails curUser) {
        List<TreeTemplate> ttList = this.treeTemplateDao.getTreeTemplateList(curUser);
        if (nodeData) {
            ttList.forEach(tt -> {
                tt.setTree(this.treeTemplateDao.getTree(tt.getTreeTemplateId()));
            });
        }
        return ttList;
    }

    @Override
    public TreeTemplate getTreeTemplateById(int treeTemplateId, boolean nodeData, CustomUserDetails curUser) {
        TreeTemplate tt = this.treeTemplateDao.getTreeTemplateById(treeTemplateId, curUser);
        if (nodeData) {
            tt.setTree(this.treeTemplateDao.getTree(treeTemplateId));
        }
        return tt;
    }

    @Override
    public int addTreeTemplate(TreeTemplate tt, CustomUserDetails curUser) {
        return this.treeTemplateDao.addTreeTemplate(tt, curUser);
    }

    @Override
    public int updateTreeTemplate(TreeTemplate tt, CustomUserDetails curUser) {
        return this.treeTemplateDao.updateTreeTemplate(tt, curUser);
    }
    
    

}
