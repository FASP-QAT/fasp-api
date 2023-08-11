/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.dao.TreeTemplateDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TreeTemplate;
import cc.altius.FASP.service.TreeTemplateService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private ProgramDataDao programDataDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<TreeTemplate> getTreeTemplateList(boolean showNodeData, CustomUserDetails curUser) {
        List<TreeTemplate> ttList = this.treeTemplateDao.getTreeTemplateList(false, curUser);
        if (showNodeData) {
            ttList.forEach(tt -> {
                tt.setTree(this.treeTemplateDao.getTree(tt.getTreeTemplateId()));
                tt.getFlatList().forEach(n -> {
                    n.getPayload().getNodeDataMap().values().forEach(s -> {
                        s.forEach(nd -> {
                            if (n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PERCENTAGE || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_FU || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PU) {
                                nd.setNodeDataModelingList(this.programDataDao.getModelingDataForNodeDataId(nd.getNodeDataId(), true));
                                logger.info("ModelingData retrieved");
                                nd.setAnnualTargetCalculator(this.programDataDao.getAnnualTargetCalculatorForNodeDataId(nd.getNodeDataId(), false));
                                logger.info("AnnualTargetCalculator retrieved");
                                nd.setNodeDataOverrideList(this.programDataDao.getOverrideDataForNodeDataId(nd.getNodeDataId(), true));
                                logger.info("Override data retrieved");
                            }
                        });
                    });
                });
            });
        }
        return ttList;
    }
    
    public List<TreeTemplate> getTreeTemplateListForDropDown(CustomUserDetails curUser) {
        return this.treeTemplateDao.getTreeTemplateList(false, curUser);
    }

    @Override
    public TreeTemplate getTreeTemplateById(int treeTemplateId, boolean nodeData, CustomUserDetails curUser) {
        TreeTemplate tt = this.treeTemplateDao.getTreeTemplateById(treeTemplateId, curUser);
        if (nodeData) {
            tt.setTree(this.treeTemplateDao.getTree(treeTemplateId));
            tt.getFlatList().forEach(n -> {
                n.getPayload().getNodeDataMap().values().forEach(s -> {
                    s.forEach(nd -> {
                        if (n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PERCENTAGE || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_FU || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PU) {
                            nd.setNodeDataModelingList(this.programDataDao.getModelingDataForNodeDataId(nd.getNodeDataId(), true));
                            logger.info("ModelingData retrieved");
                            nd.setAnnualTargetCalculator(this.programDataDao.getAnnualTargetCalculatorForNodeDataId(nd.getNodeDataId(), false));
                            logger.info("AnnualTargetCalculator retrieved");
                            nd.setNodeDataOverrideList(this.programDataDao.getOverrideDataForNodeDataId(nd.getNodeDataId(), true));
                            logger.info("Override data retrieved");
                        }
                    });
                });
            });
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

    @Override
    public List<TreeTemplate> getTreeTemplateListForSync(String lastSyncDate, CustomUserDetails curUser) {
        List<TreeTemplate> ttList = this.treeTemplateDao.getTreeTemplateListForSync(lastSyncDate, curUser);
        ttList.forEach(tt -> {
            tt.setTree(this.treeTemplateDao.getTree(tt.getTreeTemplateId()));
            tt.getFlatList().forEach(n -> {
                n.getPayload().getNodeDataMap().values().forEach(s -> {
                    s.forEach(nd -> {
                        if (n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PERCENTAGE || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_FU || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PU) {
                            nd.setNodeDataModelingList(this.programDataDao.getModelingDataForNodeDataId(nd.getNodeDataId(), true));
                            logger.info("ModelingData retrieved");
                            nd.setAnnualTargetCalculator(this.programDataDao.getAnnualTargetCalculatorForNodeDataId(nd.getNodeDataId(), false));
                            logger.info("AnnualTargetCalculator retrieved");
                            nd.setNodeDataOverrideList(this.programDataDao.getOverrideDataForNodeDataId(nd.getNodeDataId(), true));
                            logger.info("Override data retrieved");
                        }
                    });
                });
            });
        });
        return ttList;
    }

}
