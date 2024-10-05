/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.OrganisationTypeDao;
import cc.altius.FASP.dao.RealmDao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.OrganisationType;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.OrganisationTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class OrganisationTypeServiceImpl implements OrganisationTypeService {

    @Autowired
    private OrganisationTypeDao organisationTypeDao;

    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public int addOrganisationType(OrganisationType organisationType, CustomUserDetails curUser) {
        return organisationTypeDao.addOrganisationType(organisationType, curUser);
    }

    @Override
    public int updateOrganisationType(OrganisationType organisationType, CustomUserDetails curUser) {
        OrganisationType ot = this.getOrganisationTypeById(organisationType.getOrganisationTypeId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, ot.getRealm().getId())) {
            return organisationTypeDao.updateOrganisationType(organisationType, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<OrganisationType> getOrganisationTypeList(boolean active, CustomUserDetails curUser) {
        return organisationTypeDao.getOrganisationTypeList(active, curUser);
    }

    @Override
    public List<OrganisationType> getOrganisationTypeListByRealmId(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.organisationTypeDao.getOrganisationTypeListByRealmId(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public OrganisationType getOrganisationTypeById(int organisationTypeId, CustomUserDetails curUser) {
        OrganisationType orgTyp = organisationTypeDao.getOrganisationTypeById(organisationTypeId, curUser);
        if (orgTyp != null) {
            return orgTyp;
        } else {
            throw new EmptyResultDataAccessException(1);
        }
    }

    @Override
    public List<OrganisationType> getOrganisationTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.organisationTypeDao.getOrganisationTypeListForSync(lastSyncDate, curUser);
    }
}
