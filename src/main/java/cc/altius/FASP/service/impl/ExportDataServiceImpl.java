/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ExportDataDao;
import cc.altius.FASP.exception.InvalidDataException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.SupplyPlanExportDTO;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.service.ExportDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.DateUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class ExportDataServiceImpl implements ExportDataService {

    @Autowired
    private ExportDataDao exportDataDao;
    @Autowired
    private ProgramService programService;

    @Override
    public SupplyPlanExportDTO getSupplyPlanForProgramId(int programId, int versionId, String startDate, CustomUserDetails curUser) throws EmptyResultDataAccessException, ParseException, AccessDeniedException, InvalidDataException {
        SimpleProgram p = this.programService.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        if (p != null) {
            if (startDate == null || startDate.equals("")) {
                return this.exportDataDao.getSupplyPlanForProgramId(p, versionId, null, curUser);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                sdf.setLenient(false);
                Date dt1 = sdf.parse(startDate);
                Date dt2 = sdf.parse(DateUtils.formatDate(DateUtils.addMonths(DateUtils.getCurrentDateObject(DateUtils.EST), -12), DateUtils.YMD).substring(0, 7));
                if (DateUtils.compareDate(dt1, dt2)<=0) {
                    return this.exportDataDao.getSupplyPlanForProgramId(p, versionId, startDate, curUser);
                } else {
                    throw new InvalidDataException("Start date should be atleast 1 year in the past");
                }
                
            }
        } else {
            // ProgramId not found
            throw new EmptyResultDataAccessException(1);
        }
    }

}
