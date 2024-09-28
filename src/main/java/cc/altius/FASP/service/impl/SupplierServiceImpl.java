/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.dao.SupplierDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.Supplier;
import cc.altius.FASP.service.AclService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.SupplierService;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author altius
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

    @Override
    public int addSupplier(Supplier m, CustomUserDetails curUser) {
        return this.supplierDao.addSupplier(m, curUser);
    }

    @Override
    public int updateSupplier(Supplier m, CustomUserDetails curUser) {
        Supplier supplier = this.supplierDao.getSupplierById(m.getSupplierId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, supplier.getRealm().getId())) {
            return this.supplierDao.updateSupplier(m, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<Supplier> getSupplierList(boolean active, CustomUserDetails curUser) {
        return this.supplierDao.getSupplierList(active, curUser);
    }

    @Override
    public List<Supplier> getSupplierListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.supplierDao.getSupplierListForRealm(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public Supplier getSupplierById(int supplierId, CustomUserDetails curUser) {
        Supplier supplier = this.supplierDao.getSupplierById(supplierId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, supplier.getRealm().getId())) {
            return this.supplierDao.getSupplierById(supplierId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<Supplier> getSupplierListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.supplierDao.getSupplierListForSync(lastSyncDate, curUser);
    }

}
