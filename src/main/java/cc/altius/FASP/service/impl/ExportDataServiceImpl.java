/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ExportDataDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.service.ExportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class ExportDataServiceImpl implements ExportDataService{

    @Autowired
    private ExportDataDao exportDataDao;
    
    @Override
    public SupplyPlanExportDTO getSupplyPlanForProgramId(SimpleProgram program, int versionId, CustomUserDetails curUser) {
        return this.exportDataDao.getSupplyPlanForProgramId(program, versionId, curUser);
    }
    
}