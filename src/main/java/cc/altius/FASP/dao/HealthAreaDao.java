/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.SimpleCodeObject;
import java.util.List;

/**
 *
 * @author akil
 */
public interface HealthAreaDao {

    public int addHealthArea(HealthArea h, CustomUserDetails curUser);

    public int updateHealthArea(HealthArea h, CustomUserDetails CurUser);

    public List<HealthArea> getHealthAreaList(CustomUserDetails curUser);
    
    public List<SimpleCodeObject> getHealthAreaDropdownList(int realmId, CustomUserDetails curUser);
    
    public List<HealthArea> getHealthAreaListByRealmCountry(int realmCountryId, CustomUserDetails curUser);

    public List<HealthArea> getHealthAreaForActiveProgramsList(int realmId, CustomUserDetails curUser);

    public List<HealthArea> getHealthAreaListByRealmId(int realmId, CustomUserDetails curUser);

    public HealthArea getHealthAreaById(int healthAreaId, CustomUserDetails curUser);

    public List<HealthArea> getHealthAreaListForProgramByRealmId(int realmId, CustomUserDetails curUser);

    public String getDisplayName(int realmId, String name, CustomUserDetails curUser);

    public List<HealthArea> getHealthAreaListForSync(String lastSyncDate, CustomUserDetails curUser);
}
