/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.FundingSourceType;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleFundingSourceObject;
import java.util.List;

/**
 *
 * @author altius
 */
public interface FundingSourceService {

    public int addFundingSource(FundingSource f, CustomUserDetails curUser);

    public int updateFundingSource(FundingSource f, CustomUserDetails CurUser);

    public List<FundingSource> getFundingSourceList(CustomUserDetails curUser);

    public List<FundingSource> getFundingSourceList(int realmId, CustomUserDetails curUser);

    public FundingSource getFundingSourceById(int fundingSourceId, CustomUserDetails curUser);

    public List<FundingSource> getFundingSourceListForSync(String lastSyncDate, CustomUserDetails curUser);

    public String getDisplayName(int realmId, String name, CustomUserDetails curUser);

    public List<SimpleFundingSourceObject> getFundingSourceDropdownList(CustomUserDetails curUser);
    
    public List<SimpleCodeObject> getFundingSourceForProgramsDropdownList(int[] programIds, CustomUserDetails curUser);
    
    public List<SimpleCodeObject> getFundingSourceTypeForProgramsDropdownList(int[] programIds, CustomUserDetails curUser);

    public int addFundingSourceType(FundingSourceType fundingSourceType, CustomUserDetails curUser);

    public int updateFundingSourceType(FundingSourceType fundingSourceType, CustomUserDetails curUser);

    public List<FundingSourceType> getFundingSourceTypeList(boolean active, CustomUserDetails curUser);

    public List<FundingSourceType> getFundingSourceTypeByRealm(int realmId, CustomUserDetails curUser);

    public FundingSourceType getFundingSourceTypeById(int fundingSourceTypeId, CustomUserDetails curUser);
    
    public List<FundingSourceType> getFundingSourceTypeListForSync(String lastSyncDate, CustomUserDetails curUser);
}
