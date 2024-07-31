/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import cc.altius.FASP.model.SimpleProgram;

/**
 *
 * @author akil
 */
public interface ExportDataDao {

    public SupplyPlanExportDTO getSupplyPlanForProgramId(SimpleProgram program, int versionId, String startDate, CustomUserDetails curUser);
}
