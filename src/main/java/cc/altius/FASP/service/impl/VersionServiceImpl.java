/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.VersionDao;
import cc.altius.FASP.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionDao versionDao;

    @Override
    public int getCoreUIVerion() {
        return versionDao.getCoreUIVerion();
    }

}
