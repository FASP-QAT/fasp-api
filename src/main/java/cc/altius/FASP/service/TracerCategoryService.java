/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.TracerCategory;
import java.util.List;

/**
 *
 * @author altius
 */
public interface TracerCategoryService {

    public int addTracerCategory(TracerCategory m, CustomUserDetails curUser);

    public int updateTracerCategory(TracerCategory m, CustomUserDetails curUser);

    public List<TracerCategory> getTracerCategoryList(boolean active, CustomUserDetails curUser);
    
    public List<SimpleObject> getTracerCategoryDropdownList(CustomUserDetails curUser);
    
    public List<SimpleObject> getTracerCategoryDropdownListForFilterMultiplerPrograms(String programIds, CustomUserDetails curUser);

    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, boolean active, CustomUserDetails curUser);

    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, int programId, boolean active, CustomUserDetails curUser);
    
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, String[] programIds, boolean active, CustomUserDetails curUser);

    public TracerCategory getTracerCategoryById(int tracerCategoryId, CustomUserDetails curUser);

    public List<TracerCategory> getTracerCategoryListForSync(String lastSyncDate, CustomUserDetails curUser);
}
