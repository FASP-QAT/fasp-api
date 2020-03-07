/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgOrganisationDTO;
import cc.altius.FASP.model.Organisation;

import java.util.List;

/**
 *
 * @author altius
 */
public interface OrganisationService {

    public List<PrgOrganisationDTO> getOrganisationListForSync(String lastSyncDate);

    public int addOrganisation(Organisation organisation, CustomUserDetails curUser);

    public int updateOrganisation(Organisation organisation, CustomUserDetails curUser);

    public List<Organisation> getOrganisationList(CustomUserDetails curUser);

    public Organisation getOrganisationById(int organisationId, CustomUserDetails curUser);


}
