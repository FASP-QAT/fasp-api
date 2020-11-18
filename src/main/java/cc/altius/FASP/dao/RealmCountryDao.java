/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.RealmCountryHealthArea;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RealmCountryDao {

    public int addRealmCountry(RealmCountry realmCountry, CustomUserDetails curUser);

    public int updateRealmCountry(RealmCountry realmCountry, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryList(CustomUserDetails curUser);

    public RealmCountry getRealmCountryById(int realmCountryId, CustomUserDetails curUser);

    public RealmCountry getRealmCountryByRealmAndCountry(int realmId, int countryId, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryListByRealmId(int realmId, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getPlanningUnitListForRealmCountryId(int realmCountryId, boolean active, CustomUserDetails curUser);

    public int savePlanningUnitForCountry(RealmCountryPlanningUnit[] realmCountryPlanningUnits, CustomUserDetails curUser);

    public List<RealmCountryHealthArea> getRealmCountryListByRealmIdForActivePrograms(int realmId, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser);

}
