/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ModelingType;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ModelingTypeDao {

    public List<ModelingType> getModelingTypeList(boolean active, CustomUserDetails curUser);

    public List<ModelingType> getModelingTypeListForSync(String lastSyncDate, CustomUserDetails curUser);

    public int addAndUpdateModelingType(List<ModelingType> modelingTypeList, CustomUserDetails curUser);
}
