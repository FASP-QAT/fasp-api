/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.RealmCountryHealthArea;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RealmCountryService {

    public int addRealmCountry(List<RealmCountry> realmCountryList, CustomUserDetails curUser);

    public int updateRealmCountry(List<RealmCountry> realmCountryList, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryList(CustomUserDetails curUser);

    public RealmCountry getRealmCountryById(int realmCountryId, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryListByRealmId(int realmId, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getPlanningUnitListForRealmCountryId(int realmCountryId, boolean active, CustomUserDetails curUser);

    public int savePlanningUnitForCountry(RealmCountryPlanningUnit[] realmCountryPlanningUnits, CustomUserDetails curUser);

    public List<RealmCountryHealthArea> getRealmCountryListByRealmIdForActivePrograms(int realmId, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<RealmCountry> getRealmCountryListForSyncProgram(String programIdsString, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser);
    
    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);

}
