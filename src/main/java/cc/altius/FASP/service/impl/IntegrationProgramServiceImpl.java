/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.IntegrationProgramDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.IntegrationProgram;
import cc.altius.FASP.model.ManualIntegration;
import cc.altius.FASP.model.report.ManualJsonPushReportInput;
import cc.altius.FASP.service.IntegrationProgramService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class IntegrationProgramServiceImpl implements IntegrationProgramService {

    @Autowired
    private IntegrationProgramDao integrationProgramDao;

//    @Override
//    public int addIntegrationProgram(IntegrationProgram integrationProgram, CustomUserDetails curUser) {
//        return this.integrationProgramDao.addIntegrationProgram(integrationProgram, curUser);
//    }
    @Override
    public int updateIntegrationProgram(IntegrationProgram[] integrationPrograms, CustomUserDetails curUser) {
        return this.integrationProgramDao.updateIntegrationProgram(integrationPrograms, curUser);
    }

    @Override
    public List<IntegrationProgram> getIntegrationProgramList(CustomUserDetails curUser) {
        return this.integrationProgramDao.getIntegrationProgramList(curUser);
    }

    @Override
    public IntegrationProgram getIntegrationProgramById(int integrationProgramId, CustomUserDetails curUser) {
        return this.integrationProgramDao.getIntegrationProgramById(integrationProgramId, curUser);
    }

    @Override
    public List<IntegrationProgram> getIntegrationProgramListForProgramId(int programId, CustomUserDetails curUser) {
        return this.integrationProgramDao.getIntegrationProgramListForProgramId(programId, curUser);
    }

    @Override
    public int addManualJsonPush(ManualIntegration[] manualIntegrations, CustomUserDetails curUser) {
        return this.integrationProgramDao.addManualJsonPush(manualIntegrations, curUser);
    }

    @Override
    public List<ManualIntegration> getManualJsonPushReport(ManualJsonPushReportInput mi, CustomUserDetails curUser) {
        return this.integrationProgramDao.getManualJsonPushReport(mi, curUser);
    }

    @Override
    public List<ManualIntegration> getManualJsonPushForScheduler() {
        return this.integrationProgramDao.getManualJsonPushForScheduler();
    }

    @Override
    public int updateManualIntegrationProgramAsProcessed(int manualIntegrationId) {
        return this.integrationProgramDao.updateManualIntegrationProgramAsProcessed(manualIntegrationId);
    }

}
