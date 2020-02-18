/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.model.DTO.PrgBudgetDTO;
import cc.altius.FASP.model.DTO.PrgConsumptionDTO;
import cc.altius.FASP.model.DTO.PrgInventoryDTO;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.model.DTO.PrgProgramDataDTO;
import cc.altius.FASP.model.DTO.PrgProgramProductDTO;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.DTO.PrgShipmentDTO;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cc.altius.FASP.service.ProgramDataService;

/**
 *
 * @author altius
 */
@Service
public class ProgramDataServiceImpl implements ProgramDataService {

    @Autowired
    ProgramDataDao programDao;

    @Override
    public List<PrgProgramDataDTO> getProgramData(String programId) {
        List<PrgProgramDataDTO> programList = new LinkedList<>();
        programList = this.programDao.getProgramData(programId);
        for (PrgProgramDataDTO p : programList) {
            List<PrgProgramProductDTO> programProductList = this.getProgramProductListByProgramId(p.getProgramId());
            p.setProgramProductList(programProductList);
            for (PrgProgramProductDTO programProduct : programProductList) {
                programProduct.getProduct().setInventoryData(this.getInventoryListByProductId(programProduct.getProduct().getProductId()));
                programProduct.getProduct().setConsumptionData(this.getConsumptionListByProductId(programProduct.getProduct().getProductId()));
                programProduct.getProduct().setShipmentData(this.getShipmentListByProductId(programProduct.getProduct().getProductId()));
            }
            p.setRegionList(this.getRegionListByProgramId(p.getProgramId()));
            p.setBudgetData(this.getBudgetListByProgramId(p.getProgramId()));

        }
        return programList;
    }

    @Override
    public List<PrgProgramProductDTO> getProgramProductListByProgramId(int programId) {
        return this.programDao.getProgramProductListByProgramId(programId);
    }

    @Override
    public List<PrgInventoryDTO> getInventoryListByProductId(int productId) {
        return this.programDao.getInventoryListByProductId(productId);
    }

    @Override
    public List<PrgConsumptionDTO> getConsumptionListByProductId(int productId) {
        return this.programDao.getConsumptionListByProductId(productId);
    }

    @Override
    public List<PrgShipmentDTO> getShipmentListByProductId(int productId) {
        return this.programDao.getShipmentListByProductId(productId);
    }

    @Override
    public List<PrgRegionDTO> getRegionListByProgramId(int programId) {
        return this.programDao.getRegionListByProgramId(programId);
    }

    @Override
    public List<PrgBudgetDTO> getBudgetListByProgramId(int programId) {
        return this.programDao.getBudgetListByProgramId(programId);
    }

}
