/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.SupplyPlanCommitRequest;
import cc.altius.FASP.service.FirebaseNotificationService;
import cc.altius.FASP.service.ProgramDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class FirebaseNotificationServiceImpl implements FirebaseNotificationService {

    @Autowired
    ProgramDataService programDataService;

    @Override
    public SupplyPlanCommitRequest getCommitRequestStatusByCommitRequestId(int commitRequestId) {
        SupplyPlanCommitRequest spcr = new SupplyPlanCommitRequest();
        spcr = this.programDataService.getCommitRequestByCommitRequestId(commitRequestId);
        return spcr;
    }

}
