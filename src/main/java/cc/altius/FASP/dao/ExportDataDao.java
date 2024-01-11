/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ExportDataDao {

    public List<SupplyPlanExportDTO> getSupplyPlanForProgramId(int programId, int versionId, CustomUserDetails curUser);
}
