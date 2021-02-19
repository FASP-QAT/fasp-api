/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Region;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RegionService {

//    public int addRegion(Region region, CustomUserDetails curUser);

    public int saveRegions(Region[] regions, CustomUserDetails curUser);

    public List<Region> getRegionList(CustomUserDetails curUser);

    public Region getRegionById(int regionId, CustomUserDetails curUser);

    public List<Region> getRegionListByRealmCountryIds(List<Integer> realmCountryIds, CustomUserDetails curUser);
    
    public List<Region> getRegionListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<Region> getRegionListForSyncProgram(String programIdsString, CustomUserDetails curUser);
}
