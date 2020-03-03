/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.PlanningUnitDao;
import cc.altius.FASP.model.DTO.PrgPlanningUnitDTO;
import cc.altius.FASP.service.PlanningUnitService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class PlanningUnitServiceImpl implements PlanningUnitService {

    @Autowired
    PlanningUnitDao planningUnitDao;

    @Override
    public List<PrgPlanningUnitDTO> getPlanningUnitListForSync(String lastSyncDate) {
        return this.planningUnitDao.getPlanningUnitListForSync(lastSyncDate);
    }

}
