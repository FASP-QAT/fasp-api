/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.Region;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RegionService {

    public List<PrgRegionDTO> getRegionListForSync(String lastSyncDate);

    public int addRegion(Region region, CustomUserDetails curUser);

    public int updateRegion(Region region, CustomUserDetails curUser);

    public List<Region> getRegionList(CustomUserDetails curUser);

    public Region getRegionById(int regionId, CustomUserDetails curUser);
    
    public List<Region> getRegionListByRealmCountryId(int realmCountryId, CustomUserDetails curUser);
}
