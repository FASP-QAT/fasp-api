/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.SubFundingSourceDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.SubFundingSource;
import cc.altius.FASP.service.SubFundingSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class SubFundingSourceServiceImpl implements SubFundingSourceService {

    @Autowired
    SubFundingSourceDao subFundingSourceDao;

    @Override
    public List<PrgSubFundingSourceDTO> getSubFundingSourceListForSync(String lastSyncDate) {
        return this.subFundingSourceDao.getSubFundingSourceListForSync(lastSyncDate);
    }

    @Override
    public SubFundingSource getSubFundingSourceById(int subFundingSourceId, CustomUserDetails curUser) {
        return this.subFundingSourceDao.getSubFundingSourceById(subFundingSourceId, curUser);
    }

    @Override
    public List<SubFundingSource> getSubFundingSourceList(CustomUserDetails curUser) {
        return this.subFundingSourceDao.getSubFundingSourceList(curUser);
    }

    @Override
    public int updateSubFundingSource(SubFundingSource subFundingSource, CustomUserDetails curUser) {
        return this.subFundingSourceDao.updateSubFundingSource(subFundingSource, curUser);
    }

    @Override
    public int addSubFundingSource(SubFundingSource subFundingSource, CustomUserDetails curUser) {
        return this.subFundingSourceDao.addSubFundingSource(subFundingSource, curUser);
    }

}
