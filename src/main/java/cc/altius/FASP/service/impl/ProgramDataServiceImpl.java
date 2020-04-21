/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.service.AclService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author altius
 */
@Service
public class ProgramDataServiceImpl implements ProgramDataService {

    @Autowired
    private ProgramDataDao programDataDao;
    @Autowired
    private ProgramService programService;
    @Autowired
    private AclService aclService;

    @Override
    public ProgramData getProgramData(int programId, int versionId, CustomUserDetails curUser) {
        ProgramData pd = new ProgramData(this.programService.getProgramById(programId, curUser));
        if (pd.getCurrentVersion().getVersionId() < versionId) {
            throw new EmptyResultDataAccessException("Incorrect VersionId requested", versionId);
        }
        pd.setConsumptionList(this.programDataDao.getConsumptionList(programId, versionId));
        pd.setInventoryList(this.programDataDao.getInventoryList(programId, versionId));
        return pd;
    }

//    @Override
//    public List<PrgProgramProductDTO> getProgramProductListByProgramId(int programId) {
//        return this.programDao.getProgramProductListByProgramId(programId);
//    }
//
//    @Override
//    public List<PrgInventoryDTO> getInventoryListByProductId(int productId) {
//        return this.programDao.getInventoryListByProductId(productId);
//    }
//
//    @Override
//    public List<PrgConsumptionDTO> getConsumptionListByProductId(int productId) {
//        return this.programDao.getConsumptionListByProductId(productId);
//    }
//
//    @Override
//    public List<PrgShipmentDTO> getShipmentListByProductId(int productId) {
//        return this.programDao.getShipmentListByProductId(productId);
//    }
//
//    @Override
//    public List<PrgRegionDTO> getRegionListByProgramId(int programId) {
//        return this.programDao.getRegionListByProgramId(programId);
//    }
//
//    @Override
//    public List<PrgBudgetDTO> getBudgetListByProgramId(int programId) {
//        return this.programDao.getBudgetListByProgramId(programId);
//    }
}
