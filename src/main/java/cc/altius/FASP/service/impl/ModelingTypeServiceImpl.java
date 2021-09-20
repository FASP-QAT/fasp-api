/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ModelingTypeDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ModelingType;
import cc.altius.FASP.service.ModelingTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class ModelingTypeServiceImpl implements ModelingTypeService {

    @Autowired
    private ModelingTypeDao modelingTypeDao;

    @Override
    public List<ModelingType> getModelingTypeList(boolean active, CustomUserDetails curUser) {
        return this.modelingTypeDao.getModelingTypeList(active, curUser);
    }

    @Override
    public List<ModelingType> getModelingTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.modelingTypeDao.getModelingTypeListForSync(lastSyncDate, curUser);
    }

    @Override
    public int addAndUpdateModelingType(List<ModelingType> modelingTypeList, CustomUserDetails curUser) {
        return this.modelingTypeDao.addAndUpdateModelingType(modelingTypeList, curUser);
    }

}
