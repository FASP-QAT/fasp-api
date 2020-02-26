/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.FundingSourceDao;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.service.FundingSourceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class FundingSourceServiceImpl implements FundingSourceService {

    @Autowired
    FundingSourceDao fundingSourceDao;

    @Override
    public List<PrgFundingSourceDTO> getFundingSourceListForSync(String lastSyncDate) {
        return this.fundingSourceDao.getFundingSourceListForSync(lastSyncDate);
    }

}
