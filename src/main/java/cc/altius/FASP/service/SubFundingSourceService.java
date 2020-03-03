/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.SubFundingSource;
import java.util.List;

/**
 *
 * @author altius
 */
public interface SubFundingSourceService {
    
    public List<PrgSubFundingSourceDTO> getSubFundingSourceListForSync(String lastSyncDate);

    public SubFundingSource getSubFundingSourceById(int subFundingSourceId);

    public List<SubFundingSource> getSubFundingSourceList();

    public int updateSubFundingSource(SubFundingSource subFundingSource, int curUser);

    public int addSubFundingSource(SubFundingSource subFundingSource, int curUser);
    
}
