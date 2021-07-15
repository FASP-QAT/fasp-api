/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.OrganisationType;

import java.util.List;

/**
 *
 * @author altius
 */
public interface OrganisationTypeService {

    public int addOrganisationType(OrganisationType organisationType, CustomUserDetails curUser);

    public int updateOrganisationType(OrganisationType organisationType, CustomUserDetails curUser);

    public List<OrganisationType> getOrganisationTypeList(boolean active, CustomUserDetails curUser);

    public List<OrganisationType> getOrganisationTypeListByRealmId(int realmId, CustomUserDetails curUser);

    public OrganisationType getOrganisationTypeById(int organisationTypeId, CustomUserDetails curUser);

//    public List<OrganisationType> getOrganisationTypeListForSync(String lastSyncDate, CustomUserDetails curUser);
}
