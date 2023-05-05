/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.EquivalencyUnitMapping;
import cc.altius.FASP.model.SimpleObject;
import java.util.List;

/**
 *
 * @author akil
 */
public interface EquivalencyUnitService {

    public List<EquivalencyUnit> getEquivalencyUnitList(boolean active, CustomUserDetails curUser);
    
    public List<SimpleObject> getEquivalencyUnitDropDownList(CustomUserDetails curUser);

    public int addAndUpdateEquivalencyUnit(List<EquivalencyUnit> equivalencyUnitList, CustomUserDetails curUser);

    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingList(boolean active, CustomUserDetails curUser);

    public int addAndUpdateEquivalencyUnitMapping(List<EquivalencyUnitMapping> equivalencyUnitMappingList, CustomUserDetails curUser) throws IllegalAccessException, CouldNotSaveException;
    
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingForForecastingUnit(int fuId, int programId, CustomUserDetails curUser);

    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingListForSync(String programIdsString, CustomUserDetails curUser);
}
