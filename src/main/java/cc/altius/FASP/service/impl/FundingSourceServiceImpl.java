/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.FundingSourceDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.FundingSourceType;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleFundingSourceObject;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.FundingSourceService;
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
public class FundingSourceServiceImpl implements FundingSourceService {

    @Autowired
    FundingSourceDao fundingSourceDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public int addFundingSource(FundingSource f, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, f.getRealm().getId())) {
            return this.fundingSourceDao.addFundingSource(f, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateFundingSource(FundingSource f, CustomUserDetails curUser) {
        FundingSource fs = this.fundingSourceDao.getFundingSourceById(f.getFundingSourceId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, fs.getRealm().getId())) {
            return this.fundingSourceDao.updateFundingSource(f, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<FundingSource> getFundingSourceList(CustomUserDetails curUser) {
        return this.fundingSourceDao.getFundingSourceList(curUser);
    }

    @Override
    public List<FundingSource> getFundingSourceList(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.fundingSourceDao.getFundingSourceList(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public FundingSource getFundingSourceById(int fundingSourceId, CustomUserDetails curUser) {
        FundingSource f = this.fundingSourceDao.getFundingSourceById(fundingSourceId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, f.getRealm().getId())) {
            return f;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public String getDisplayName(int realmId, String name, CustomUserDetails curUser) {
        return this.fundingSourceDao.getDisplayName(realmId, name, curUser);
    }

    @Override
    public List<FundingSource> getFundingSourceListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.fundingSourceDao.getFundingSourceListForSync(lastSyncDate, curUser);
    }

    @Override
    public List<SimpleFundingSourceObject> getFundingSourceDropdownList(CustomUserDetails curUser) {
        return this.fundingSourceDao.getFundingSourceDropdownList(curUser);
    }

    @Override
    public List<SimpleCodeObject> getFundingSourceForProgramsDropdownList(int[] programIds, CustomUserDetails curUser) {
        return this.fundingSourceDao.getFundingSourceForProgramsDropdownList(programIds, curUser);
    }

    @Override
    public List<SimpleCodeObject> getFundingSourceTypeForProgramsDropdownList(int[] programIds, CustomUserDetails curUser) {
        return this.fundingSourceDao.getFundingSourceTypeForProgramsDropdownList(programIds, curUser);
    }

    @Override
    public int addFundingSourceType(FundingSourceType fundingSourceType, CustomUserDetails curUser) {
        return this.fundingSourceDao.addFundingSourceType(fundingSourceType, curUser);
    }

    @Override
    public int updateFundingSourceType(FundingSourceType fundingSourceType, CustomUserDetails curUser) {
        FundingSourceType pa = this.fundingSourceDao.getFundingSourceTypeById(fundingSourceType.getFundingSourceTypeId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
            return this.fundingSourceDao.updateFundingSourceType(fundingSourceType, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<FundingSourceType> getFundingSourceTypeList(boolean active, CustomUserDetails curUser) {
        return this.fundingSourceDao.getFundingSourceTypeList(active, curUser);
    }

    @Override
    public FundingSourceType getFundingSourceTypeById(int fundingSourceTypeId, CustomUserDetails curUser) {
        FundingSourceType pa = this.fundingSourceDao.getFundingSourceTypeById(fundingSourceTypeId, curUser);
        if (pa != null && this.aclService.checkRealmAccessForUser(curUser, pa.getRealm().getId())) {
            return pa;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<FundingSourceType> getFundingSourceTypeByRealm(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.fundingSourceDao.getFundingSourceTypeByRealm(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<FundingSourceType> getFundingSourceTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.fundingSourceDao.getFundingSourceTypeListForSync(lastSyncDate, curUser);
    }

}
