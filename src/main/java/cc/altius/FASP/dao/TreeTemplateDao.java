/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastTree;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.TreeTemplate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public interface TreeTemplateDao {

    public List<TreeTemplate> getTreeTemplateList(boolean active, CustomUserDetails curUser);

    public TreeTemplate getTreeTemplateById(int treeTemplateId, CustomUserDetails curUser);

    public ForecastTree<TreeNode> getTree(int treeTemplateId);

    public Map<String, Object> getConsumption(int treeTemplateId);

    public int addTreeTemplate(TreeTemplate tt, CustomUserDetails curUser);

    public int updateTreeTemplate(TreeTemplate tt, CustomUserDetails curUser);

    public List<TreeTemplate> getTreeTemplateListForSync(String lastSyncDate, CustomUserDetails curUser);
}
