/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TracerCategory;
import java.util.List;

/**
 *
 * @author altius
 */
public interface TracerCategoryDao {

//    public List<PrgTracerCategoryDTO> getTracerCategoryListForSync(String lastSyncDate,int realmId);

    public int addTracerCategory(TracerCategory m, CustomUserDetails curUser);

    public int updateTracerCategory(TracerCategory m, CustomUserDetails curUser);

    public List<TracerCategory> getTracerCategoryList(boolean active, CustomUserDetails curUser);
    
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, boolean active, CustomUserDetails curUser);

    public TracerCategory getTracerCategoryById(int tracerCategoryId, CustomUserDetails curUser);

}
