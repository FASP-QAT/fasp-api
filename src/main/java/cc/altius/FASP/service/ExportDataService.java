/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import cc.altius.FASP.model.SimpleProgram;

/**
 *
 * @author akil
 */
public interface ExportDataService {
    
    public SupplyPlanExportDTO getSupplyPlanForProgramId(SimpleProgram program, int versionId, CustomUserDetails curUser);
    
}
