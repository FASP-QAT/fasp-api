/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.FundingSource;
import java.util.List;

/**
 *
 * @author altius
 */
public interface FundingSourceService {

    public List<PrgFundingSourceDTO> getFundingSourceListForSync(String lastSyncDate);

    public int addFundingSource(FundingSource f, CustomUserDetails curUser);

    public int updateFundingSource(FundingSource f, CustomUserDetails CurUser);

    public List<FundingSource> getFundingSourceList(CustomUserDetails curUser);

    public FundingSource getFundingSourceById(int unitId, CustomUserDetails curUser);

}
