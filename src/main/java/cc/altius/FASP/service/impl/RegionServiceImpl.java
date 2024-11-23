/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RegionDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.RegionService;
import java.util.LinkedList;
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
    public int saveRegions(Region[] regions, CustomUserDetails curUser) throws AccessControlFailedException {
        int rowsUpdated = 0;
        for (Region r : regions) {
            if (r.getRegionId() == 0) {
                RealmCountry rc = this.realmCountryService.getRealmCountryById(r.getRealmCountry().getRealmCountryId(), curUser);
                if (this.aclService.checkRealmAccessForUser(curUser, rc.getRealm().getRealmId())) {
                    r.setRealmCountry(rc);
                    if (r.getGln() != null && r.getGln().isEmpty()) {
                        r.setGln(null);
                    }
                    this.regionDao.addRegion(r, curUser);
                    rowsUpdated++;
                } else {
                    throw new AccessControlFailedException();
                }
            } else {
                Region region = this.getRegionById(r.getRegionId(), curUser);
                if (this.aclService.checkRealmAccessForUser(curUser, region.getRealmCountry().getRealm().getRealmId())) {
                    if (r.getGln() != null && r.getGln().isEmpty()) {
                        r.setGln(null);
                    }
                    this.regionDao.updateRegion(r, curUser);
                    rowsUpdated++;
                } else {
                    throw new AccessControlFailedException();
                }
            }
        }
        return rowsUpdated;
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

    @Override
    public List<Region> getRegionListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.regionDao.getRegionListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<Region> getRegionListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        if (programIdsString.length() > 0) {
            return this.regionDao.getRegionListForSyncProgram(programIdsString, curUser);
        } else {
            return new LinkedList<>();
        }
    }

}
