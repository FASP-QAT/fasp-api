/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.SupplyPlanCommitRequest;

/**
 *
 * @author altius
 */
public interface FirebaseNotificationService {
    
    public SupplyPlanCommitRequest getCommitRequestStatusByCommitRequestId(int commitRequestId);
    
}
