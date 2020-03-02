/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.OrganisationDao;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.DTO.PrgOrganisationDTO;
import cc.altius.FASP.service.OrganisationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationDao organisationDao;

    @Override
    public int addOrganisation(Organisation organisation, int curUser) {
        return organisationDao.addOrganisation(organisation, curUser);
    }

    @Override
    public int updateOrganisation(Organisation organisation, int curUser) {
        return organisationDao.updateOrganisation(organisation, curUser);
    }

    @Override
    public List<Organisation> getOrganisationList() {
        return organisationDao.getOrganisationList();
    }

    @Override
    public Organisation getOrganisationById(int organisationId) {
        return organisationDao.getOrganisationById(organisationId);
    }

    @Override
    public List<PrgOrganisationDTO> getOrganisationListForSync(String lastSyncDate) {
        return this.organisationDao.getOrganisationListForSync(lastSyncDate);
    }

}
