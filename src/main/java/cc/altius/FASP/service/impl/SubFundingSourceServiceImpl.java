/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.FundingSourceDao;
import cc.altius.FASP.dao.SubFundingSourceDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.SubFundingSource;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.SubFundingSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class SubFundingSourceServiceImpl implements SubFundingSourceService {

    @Autowired
    SubFundingSourceDao subFundingSourceDao;
    @Autowired
    FundingSourceDao fundingSourceDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<PrgSubFundingSourceDTO> getSubFundingSourceListForSync(String lastSyncDate, int realmId) {
        return this.subFundingSourceDao.getSubFundingSourceListForSync(lastSyncDate, realmId);
    }

    @Override
    public SubFundingSource getSubFundingSourceById(int subFundingSourceId, CustomUserDetails curUser) {
        SubFundingSource sf = this.subFundingSourceDao.getSubFundingSourceById(subFundingSourceId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, sf.getFundingSource().getRealm().getRealmId())) {
            return sf;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<SubFundingSource> getSubFundingSourceList(CustomUserDetails curUser) {
        return this.subFundingSourceDao.getSubFundingSourceList(curUser);
    }

    @Override
    public List<SubFundingSource> getSubFundingSourceListByFundingSource(int fundingSourceId, CustomUserDetails curUser) {
        FundingSource fs = this.fundingSourceDao.getFundingSourceById(fundingSourceId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, fs.getRealm().getRealmId())) {
            return this.subFundingSourceDao.getSubFundingSourceListByFundingSource(fundingSourceId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<SubFundingSource> getSubFundingSourceListByRealm(int realmId, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.subFundingSourceDao.getSubFundingSourceListByRealm(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateSubFundingSource(SubFundingSource subFundingSource, CustomUserDetails curUser) {
        SubFundingSource sfs = this.subFundingSourceDao.getSubFundingSourceById(subFundingSource.getSubFundingSourceId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, sfs.getFundingSource().getRealm().getRealmId())) {
            return this.subFundingSourceDao.updateSubFundingSource(subFundingSource, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }

    }

    @Override
    public int addSubFundingSource(SubFundingSource subFundingSource, CustomUserDetails curUser) {
        FundingSource fs = this.fundingSourceDao.getFundingSourceById(subFundingSource.getFundingSource().getFundingSourceId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, fs.getRealm().getRealmId())) {
            return this.subFundingSourceDao.addSubFundingSource(subFundingSource, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
