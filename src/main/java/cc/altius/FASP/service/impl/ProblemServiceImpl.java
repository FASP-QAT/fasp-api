/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.ProblemDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ManualProblem;
import cc.altius.FASP.model.ProblemReport;
import cc.altius.FASP.model.ProblemStatus;
import cc.altius.FASP.model.RealmProblem;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.service.ProblemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemDao problemDao;
    @Autowired
    private ProgramCommonDao programCommonDao;

    @Override
    public List<RealmProblem> getProblemListByRealmId(int realmId, CustomUserDetails curUser) {
        return this.problemDao.getProblemListByRealmId(realmId, curUser);
    }

    @Override
    public List<ProblemReport> getProblemReportList(int problemId, int versionId, CustomUserDetails curUser) {
        return this.problemDao.getProblemReportList(problemId, versionId, curUser);
    }

    @Override
    public List<RealmProblem> getProblemListForSync(String lastModifiedDate, CustomUserDetails curUser) {
        return this.problemDao.getProblemListForSync(lastModifiedDate, curUser);
    }

    @Override
    public List<ProblemStatus> getProblemStatusForSync(String lastModifiedDate, CustomUserDetails curUser) {
        return this.problemDao.getProblemStatusForSync(lastModifiedDate, curUser);
    }

    @Override
    public List<SimpleObject> getProblemCriticalityForSync(String lastModifiedDate, CustomUserDetails curUser) {
        return this.problemDao.getProblemCriticalityForSync(lastModifiedDate, curUser);
    }

    @Override
    public List<SimpleObject> getProblemCategoryForSync(String lastModifiedDate, CustomUserDetails curUser) {
        return this.problemDao.getProblemCategoryForSync(lastModifiedDate, curUser);
    }

    @Override
    public List<ProblemReport> getProblemReportListForSync(int programId, int versionId, String lastSyncDate) {
        return this.problemDao.getProblemReportListForSync(programId, versionId, lastSyncDate);
    }

    @Override
    public List<ProblemStatus> getProblemStatus(CustomUserDetails curUser) {
        return this.problemDao.getProblemStatus(curUser);
    }

    @Override
    public int createManualProblem(ManualProblem manualProblem, CustomUserDetails curUser) throws AccessControlFailedException {
        this.programCommonDao.getSimpleProgramById(manualProblem.getProgram().getId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
        return this.problemDao.createManualProblem(manualProblem, curUser);
    }

}
