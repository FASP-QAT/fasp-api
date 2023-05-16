/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DatasetDataJson;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.CommitRequestInput;
import java.util.List;

/**
 *
 * @author akil
 */
public interface CommitRequestDao {

    public int saveProgramData(ProgramData programData, String json, CustomUserDetails curUser) throws CouldNotSaveException;

    public int saveDatasetData(DatasetDataJson programData, int requestedVersionId, String json, CustomUserDetails curUser) throws CouldNotSaveException;

    public CommitRequest getPendingCommitRequestProcessList();

    public Version updateCommitRequest(int programId, int commitRequestId, int status, String message, int versionId);

    public List<CommitRequest> getCommitRequestList(CommitRequestInput spcr, int requestStatus, CustomUserDetails curUser);

    public CommitRequest getCommitRequestByCommitRequestId(int commitRequestId);

    public boolean checkIfCommitRequestExistsForProgram(int programId);

}
