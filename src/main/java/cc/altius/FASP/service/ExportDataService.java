/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.InvalidDataException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import java.text.ParseException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;

/**
 *
 * @author akil
 */
public interface ExportDataService {

    public SupplyPlanExportDTO getSupplyPlanForProgramId(int programId, int versionId, String startDate, CustomUserDetails curUser) throws EmptyResultDataAccessException, ParseException, AccessDeniedException, InvalidDataException;

}
