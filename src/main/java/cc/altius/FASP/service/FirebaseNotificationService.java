/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import java.util.concurrent.CompletableFuture;

/**
 *
 * @author altius
 */
public interface FirebaseNotificationService {
    
    public CompletableFuture<Object> getCommitRequestStatusByCommitRequestId(int commitRequestId);
    
}
