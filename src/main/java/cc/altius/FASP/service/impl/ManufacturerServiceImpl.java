/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ManufacturerDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgManufacturerDTO;
import cc.altius.FASP.model.Manufacturer;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.ManufacturerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    ManufacturerDao manufacturerDao;
    @Autowired
    private AclService aclService;

    @Override
    public List<PrgManufacturerDTO> getManufacturerListForSync(String lastSyncDate, int realmId) {
        return this.manufacturerDao.getManufacturerListForSync(lastSyncDate, realmId);
    }

    @Override
    public int addManufacturer(Manufacturer m, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, m.getRealm().getRealmId())) {
            return this.manufacturerDao.addManufacturer(m, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public int updateManufacturer(Manufacturer m, CustomUserDetails curUser) {
        Manufacturer manufacturer = this.manufacturerDao.getManufacturerById(m.getManufacturerId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, manufacturer.getRealm().getRealmId())) {
            return this.manufacturerDao.updateManufacturer(m, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<Manufacturer> getManufacturerList(boolean active, CustomUserDetails curUser) {
        return this.manufacturerDao.getManufacturerList(active, curUser);
    }

    @Override
    public List<Manufacturer> getManufacturerListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.manufacturerDao.getManufacturerListForRealm(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Manufacturer getManufacturerById(int manufacturerId, CustomUserDetails curUser) {
        Manufacturer manufacturer = this.manufacturerDao.getManufacturerById(manufacturerId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, manufacturer.getRealm().getRealmId())) {
            return this.manufacturerDao.getManufacturerById(manufacturerId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
