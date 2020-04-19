/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.DimensionDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Dimension;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.DimensionService;

/**
 *
 * @author altius
 */
@Service
public class DimensionServiceImpl implements DimensionService {

    @Autowired
    private DimensionDao dimensionDao;

    @Override
    public int addDimension(Dimension d, CustomUserDetails curUser) {
        return this.dimensionDao.addDimension(d, curUser);
    }

    @Override
    public int updateDimension(Dimension d, CustomUserDetails curUser) {
        Dimension ut = this.dimensionDao.getDimensionById(d.getDimensionId());
        if (ut == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return this.dimensionDao.updateDimension(d, curUser);
    }

    @Override
    public List<Dimension> getDimensionList(boolean active) {
        return this.dimensionDao.getDimensionList(active);
    }

    @Override
    public Dimension getDimensionById(int dimensionId) {
        return this.dimensionDao.getDimensionById(dimensionId);
    }

    @Override
    public List<Dimension> getDimensionListForSync(String lastSyncDate) {
        return this.dimensionDao.getDimensionListForSync(lastSyncDate);
    }

}
