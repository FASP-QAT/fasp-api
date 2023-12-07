/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.IntegrationProgram;
import cc.altius.FASP.model.ManualIntegration;
import cc.altius.FASP.model.report.ManualJsonPushReportInput;
import java.util.List;

/**
 *
 * @author akil
 */
public interface IntegrationProgramDao {

//    public int addIntegrationProgram(IntegrationProgram integrationProgram, CustomUserDetails curUser);

    public int updateIntegrationProgram(IntegrationProgram[] integraionPrograms, CustomUserDetails curUser);
    
    public List<IntegrationProgram> getIntegrationProgramList(CustomUserDetails curUser);

    public IntegrationProgram getIntegrationProgramById(int integrationProgramId, CustomUserDetails curUser);
    
    public List<IntegrationProgram> getIntegrationProgramListForProgramId(int programId, CustomUserDetails curUser);
    
    public int addManualJsonPush(ManualIntegration[] manualIntegrations, CustomUserDetails curUser);

    public List<ManualIntegration> getManualJsonPushReport(ManualJsonPushReportInput mi, CustomUserDetails curUser);

    public List<ManualIntegration> getManualJsonPushForScheduler();

    public int updateManualIntegrationProgramAsProcessed(int manualIntegrationId);
}
