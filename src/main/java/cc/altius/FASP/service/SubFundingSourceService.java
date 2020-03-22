/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.SubFundingSource;
import java.util.List;

/**
 *
 * @author altius
 */
public interface SubFundingSourceService {
    
    public List<PrgSubFundingSourceDTO> getSubFundingSourceListForSync(String lastSyncDate,int realmId);

    public SubFundingSource getSubFundingSourceById(int subFundingSourceId, CustomUserDetails curUser);

    public List<SubFundingSource> getSubFundingSourceList(CustomUserDetails curUser);
    
    public List<SubFundingSource> getSubFundingSourceListByFundingSource(int fundingSourceId, CustomUserDetails curUser);
    
    public List<SubFundingSource> getSubFundingSourceListByRealm(int realmId, CustomUserDetails curUser);

    public int updateSubFundingSource(SubFundingSource subFundingSource, CustomUserDetails curUser);

    public int addSubFundingSource(SubFundingSource subFundingSource, CustomUserDetails curUser);
    
}
