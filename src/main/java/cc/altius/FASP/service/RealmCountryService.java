/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.RealmCountryHealthArea;
import cc.altius.FASP.model.SimpleCodeObject;
import java.util.List;

/**
 *
 * @author altius
 */
public interface RealmCountryService {

    public int addRealmCountry(List<RealmCountry> realmCountryList, CustomUserDetails curUser) throws AccessControlFailedException;

    public int updateRealmCountry(List<RealmCountry> realmCountryList, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryList(CustomUserDetails curUser);

    public List<SimpleCodeObject> getRealmCountryDropdownList(int realmId, CustomUserDetails curUser);

    public RealmCountry getRealmCountryById(int realmCountryId, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryListByRealmId(int realmId, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getPlanningUnitListForRealmCountryId(int realmCountryId, boolean active, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForProgramList(String[] programIds, CustomUserDetails curUser);

    public int savePlanningUnitForCountry(RealmCountryPlanningUnit[] realmCountryPlanningUnits, CustomUserDetails curUser) throws CouldNotSaveException, AccessControlFailedException;

    public List<RealmCountryHealthArea> getRealmCountryListByRealmIdForActivePrograms(int realmId, int programTypeId, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<RealmCountry> getRealmCountryListForSyncProgram(String programIdsString, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser);

    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser);

}
