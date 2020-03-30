/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RegionDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.RegionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionDao regionDao;
    @Autowired
    private RealmCountryService realmCountryService;
    @Autowired
    private AclService aclService;

    @Override
    public int addRegion(Region r, CustomUserDetails curUser) {
        RealmCountry rc = this.realmCountryService.getRealmCountryById(r.getRealmCountry().getRealmCountryId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, rc.getRealm().getRealmId())) {
            return this.regionDao.addRegion(r, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateRegion(Region m, CustomUserDetails curUser) {
        Region region = this.getRegionById(m.getRegionId(), curUser);
        if (region==null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, region.getRealmCountry().getRealm().getRealmId())) {
            return this.regionDao.updateRegion(m, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<PrgRegionDTO> getRegionListForSync(String lastSyncDate, int realmId) {
        return this.regionDao.getRegionListForSync(lastSyncDate, realmId);
    }

    @Override
    public List<Region> getRegionList(CustomUserDetails curUser) {
        return this.regionDao.getRegionList(curUser);
    }

    @Override
    public Region getRegionById(int regionId, CustomUserDetails curUser) {
        return this.regionDao.getRegionById(regionId, curUser);
    }

    @Override
    public List<Region> getRegionListByRealmCountryId(int realmCountryId, CustomUserDetails curUser) {
        RealmCountry rc = this.realmCountryService.getRealmCountryById(realmCountryId, curUser);
        if (rc == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, rc.getRealm().getRealmId())) {
            return this.regionDao.getRegionListByRealmCountryId(realmCountryId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
