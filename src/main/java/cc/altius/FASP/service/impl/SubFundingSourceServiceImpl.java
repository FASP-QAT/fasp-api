/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.SubFundingSourceDao;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
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

}
