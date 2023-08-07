/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ManualProblem;
import cc.altius.FASP.model.ProblemReport;
import cc.altius.FASP.model.ProblemStatus;
import cc.altius.FASP.model.RealmProblem;
import cc.altius.FASP.model.SimpleObject;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ProblemDao {

    public List<RealmProblem> getProblemListByRealmId(int realmId, CustomUserDetails curUser);

    public List<ProblemReport> getProblemReportList(int programId, int versionId, CustomUserDetails curUser);

    public List<RealmProblem> getProblemListForSync(String lastModifiedDate, CustomUserDetails curUser);

    public List<ProblemStatus> getProblemStatusForSync(String lastModifiedDate, CustomUserDetails curUser);

    public List<SimpleObject> getProblemCriticalityForSync(String lastModifiedDate, CustomUserDetails curUser);

    public List<SimpleObject> getProblemCategoryForSync(String lastModifiedDate, CustomUserDetails curUser);

    public List<ProblemReport> getProblemReportListForSync(int programId, int versionId, String lastSyncDate);

    public List<ProblemStatus> getProblemStatus(CustomUserDetails curUser);
    
    public int createManualProblem(ManualProblem manualProblem, CustomUserDetails curUser);
}
