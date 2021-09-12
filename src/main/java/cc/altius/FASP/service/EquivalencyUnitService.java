/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.EquivalencyUnitMapping;
import java.util.List;

/**
 *
 * @author akil
 */
public interface EquivalencyUnitService {

    public List<EquivalencyUnit> getEquivalencyUnitList(boolean active, CustomUserDetails curUser);

    public int addAndUpdateEquivalencyUnit(List<EquivalencyUnit> equivalencyUnitList, CustomUserDetails curUser);
    
    public List<EquivalencyUnitMapping> getEquivalencyUnitMappingList(boolean active, CustomUserDetails curUser);

    public int addAndUpdateEquivalencyUnitMapping(List<EquivalencyUnitMapping> equivalencyUnitMappingList, CustomUserDetails curUser);
}
