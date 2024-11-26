/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.dao.TracerCategoryDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.service.AclService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.TracerCategoryService;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author altius
 */
@Service
public class TracerCategoryServiceImpl implements TracerCategoryService {

    @Autowired
    private TracerCategoryDao tracerCategoryDao;
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;
    @Autowired
    private HealthAreaDao healthAreaDao;

    @Override
    public int addTracerCategory(TracerCategory m, CustomUserDetails curUser) throws AccessControlFailedException {
        if (m.getHealthArea() != null && m.getHealthArea().getId() != null && m.getHealthArea().getId() != 0) {
            try {
                this.healthAreaDao.getHealthAreaById(m.getHealthArea().getId(), curUser);
            } catch (EmptyResultDataAccessException e) {
                throw new AccessControlFailedException();
            }
        }
        return this.tracerCategoryDao.addTracerCategory(m, curUser);
    }

    @Override
    public int updateTracerCategory(TracerCategory m, CustomUserDetails curUser) throws AccessControlFailedException {
        TracerCategory tracerCategory = this.tracerCategoryDao.getTracerCategoryById(m.getTracerCategoryId(), curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, tracerCategory.getRealm().getId())) {
            if (m.getHealthArea() != null && m.getHealthArea().getId() != null && m.getHealthArea().getId() != 0) {
                try {
                    this.healthAreaDao.getHealthAreaById(m.getHealthArea().getId(), curUser);
                } catch (EmptyResultDataAccessException e) {
                    throw new AccessControlFailedException();
                }
            }
            return this.tracerCategoryDao.updateTracerCategory(m, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<TracerCategory> getTracerCategoryList(boolean active, CustomUserDetails curUser) {
        return this.tracerCategoryDao.getTracerCategoryList(active, curUser);
    }

    @Override
    public List<SimpleObject> getTracerCategoryDropdownList(CustomUserDetails curUser) {
        return this.tracerCategoryDao.getTracerCategoryDropdownList(curUser);
    }

    @Override
    public List<SimpleObject> getTracerCategoryDropdownListForFilterMultiplerPrograms(String programIds, CustomUserDetails curUser) {
        return this.tracerCategoryDao.getTracerCategoryDropdownListForFilterMultiplerPrograms(programIds, curUser);
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.tracerCategoryDao.getTracerCategoryListForRealm(realmId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, int programId, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.tracerCategoryDao.getTracerCategoryListForRealm(realmId, programId, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, String[] programIds, boolean active, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (r == null) {
            throw new EmptyResultDataAccessException(1);
        }
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.tracerCategoryDao.getTracerCategoryListForRealm(realmId, programIds, active, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public TracerCategory getTracerCategoryById(int tracerCategoryId, CustomUserDetails curUser) {
        TracerCategory tracerCategory = this.tracerCategoryDao.getTracerCategoryById(tracerCategoryId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, tracerCategory.getRealm().getId())) {
            return this.tracerCategoryDao.getTracerCategoryById(tracerCategoryId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForSync(String lastSyncDate, CustomUserDetails curUser) {
        return this.tracerCategoryDao.getTracerCategoryListForSync(lastSyncDate, curUser);
    }

}
