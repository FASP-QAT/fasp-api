/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.OrganisationDao;
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
    OrganisationDao organisationDao;

    @Override
    public List<PrgOrganisationDTO> getOrganisationListForSync(String lastSyncDate) {
        return this.organisationDao.getOrganisationListForSync(lastSyncDate);
    }

}
