/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import java.util.List;

/**
 *
 * @author altius
 */
public interface HealthAreaService {

    public int addHealthArea(HealthArea h, CustomUserDetails curUser);

    public int updateHealthArea(HealthArea h, CustomUserDetails CurUser);

    public List<HealthArea> getHealthAreaList(CustomUserDetails curUser);

    public List<HealthArea> getHealthAreaListByRealmId(int realmId, CustomUserDetails curUser);

    public HealthArea getHealthAreaById(int healthAreaId, CustomUserDetails curUser);

    public List<HealthArea> getHealthAreaListForSync(String lastSyncDate, CustomUserDetails curUser);
}
