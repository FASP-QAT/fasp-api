/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DatasetData;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.report.CommitRequestInput;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author akil
 */
public interface CommitRequestService {

    public int saveProgramData(ProgramData programData, String json, CustomUserDetails curUser) throws CouldNotSaveException;

    public int saveDatasetData(DatasetData programData, String json, CustomUserDetails curUser) throws CouldNotSaveException;

    public void processCommitRequest(CustomUserDetails curUser);

    public List<CommitRequest> getCommitRequestList(CommitRequestInput spcr, int requestStatus, CustomUserDetails curUser);

    public CommitRequest getCommitRequestByCommitRequestId(int commitRequestId);
    
    public boolean checkIfCommitRequestExistsForProgram(int programId);
    
    public CompletableFuture<Object> getCommitRequestStatusByCommitRequestId(int commitRequestId);
}
