/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.OrganisationDao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgOrganisationDTO;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.OrganisationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationDao organisationDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<PrgOrganisationDTO> getOrganisationListForSync(String lastSyncDate) {
        return this.organisationDao.getOrganisationListForSync(lastSyncDate);
    }

    @Override
    public int addOrganisation(Organisation organisation, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, organisation.getRealm().getRealmId())) {
            return organisationDao.addOrganisation(organisation, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateOrganisation(Organisation organisation, CustomUserDetails curUser) {
        Organisation o = this.getOrganisationById(organisation.getOrganisationId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, o.getRealm().getRealmId())) {
            return organisationDao.updateOrganisation(organisation, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<Organisation> getOrganisationList(CustomUserDetails curUser) {
        return organisationDao.getOrganisationList(curUser);
    }

    @Override
    public List<Organisation> getOrganisationListByRealmId(int realmId, CustomUserDetails curUser) {
        return this.organisationDao.getOrganisationListByRealmId(realmId, curUser);
    }

    @Override
    public Organisation getOrganisationById(int organisationId, CustomUserDetails curUser) {
        return organisationDao.getOrganisationById(organisationId, curUser);
    }

}
