/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.FundingSourceDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.FundingSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AclService aclService;

    @Override
    public List<PrgFundingSourceDTO> getFundingSourceListForSync(String lastSyncDate, int realmId) {
        return this.fundingSourceDao.getFundingSourceListForSync(lastSyncDate, realmId);
    }

    @Override
    public int addFundingSource(FundingSource f, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, f.getRealm().getRealmId())) {
            return this.fundingSourceDao.addFundingSource(f, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateFundingSource(FundingSource f, CustomUserDetails curUser) {
        FundingSource fs = this.fundingSourceDao.getFundingSourceById(f.getFundingSourceId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, fs.getRealm().getRealmId())) {
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
        return this.fundingSourceDao.getFundingSourceList(realmId, curUser);
    }

    @Override
    public FundingSource getFundingSourceById(int fundingSourceId, CustomUserDetails curUser) {
        FundingSource f = this.fundingSourceDao.getFundingSourceById(fundingSourceId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, f.getRealm().getRealmId())) {
            return f;
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
