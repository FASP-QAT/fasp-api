/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TreeTemplate;
import java.util.List;

/**
 *
 * @author akil
 */
public interface TreeTemplateService {

    public List<TreeTemplate> getTreeTemplateList(boolean nodeData, CustomUserDetails curUser);
    
    public List<TreeTemplate> getTreeTemplateListForDropDown(CustomUserDetails curUser);

    public TreeTemplate getTreeTemplateById(int treeTemplateId, boolean nodeData, CustomUserDetails curUser);

    public int addTreeTemplate(TreeTemplate tt, CustomUserDetails curUser);

    public int updateTreeTemplate(TreeTemplate tt, CustomUserDetails curUser);

    public List<TreeTemplate> getTreeTemplateListForSync(String lastSyncDate, CustomUserDetails curUser);

}
