/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.SimpleCodeObject;

import java.util.List;

/**
 *
 * @author altius
 */
public interface OrganisationService {

    public int addOrganisation(Organisation organisation, CustomUserDetails curUser);

    public int updateOrganisation(Organisation organisation, CustomUserDetails curUser);

    public List<Organisation> getOrganisationList(CustomUserDetails curUser);
    
    public List<SimpleCodeObject> getOrganisationDropdownList(int realmId, CustomUserDetails curUser);
    
    public List<SimpleCodeObject> getOrganisationDropdownListForRealmCountryId(int realmCountryId, CustomUserDetails curUser);

    public List<Organisation> getOrganisationListByRealmId(int realmId, CustomUserDetails curUser);

    public Organisation getOrganisationById(int organisationId, CustomUserDetails curUser);

    public String getDisplayName(int realmId, String name, CustomUserDetails curUser);
    
    public List<Organisation> getOrganisationListForSync(String lastSyncDate, CustomUserDetails curUser);

}
