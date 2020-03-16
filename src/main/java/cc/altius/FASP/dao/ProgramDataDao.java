/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.DTO.PrgBudgetDTO;
import cc.altius.FASP.model.DTO.PrgConsumptionDTO;
import cc.altius.FASP.model.DTO.PrgInventoryDTO;
import cc.altius.FASP.model.DTO.PrgProgramDataDTO;
import cc.altius.FASP.model.DTO.PrgProgramProductDTO;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.DTO.PrgShipmentDTO;
import java.util.List;

/**
 *
 * @author altius
 */
public interface ProgramDataDao {

    public List<PrgProgramDataDTO> getProgramData(String programId);

    public List<PrgProgramProductDTO> getProgramProductListByProgramId(int programId);

    public List<PrgInventoryDTO> getInventoryListByProductId(int productId);

    public List<PrgConsumptionDTO> getConsumptionListByProductId(int productId);

    public List<PrgShipmentDTO> getShipmentListByProductId(int productId);

    public List<PrgRegionDTO> getRegionListByProgramId(int programId);

    public List<PrgBudgetDTO> getBudgetListByProgramId(int programId);

}
