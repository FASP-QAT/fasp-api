/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Region;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RegionService {

//    public int addRegion(Region region, CustomUserDetails curUser);
    public int saveRegions(Region[] regions, CustomUserDetails curUser) throws AccessControlFailedException;

    public List<Region> getRegionList(CustomUserDetails curUser);

    public Region getRegionById(int regionId, CustomUserDetails curUser);

    public List<Region> getRegionListByRealmCountryId(int realmCountryId, CustomUserDetails curUser);

    public List<Region> getRegionListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<Region> getRegionListForSyncProgram(String programIdsString, CustomUserDetails curUser);
}
