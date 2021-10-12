/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.SupplyPlanCommitRequest;
import cc.altius.FASP.service.FirebaseNotificationService;
import cc.altius.FASP.service.ProgramDataService;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Async
    public CompletableFuture<Object> getCommitRequestStatusByCommitRequestId(int commitRequestId) {
        System.out.println("in check if commit rquest");
        SupplyPlanCommitRequest spcr=new SupplyPlanCommitRequest();
        spcr.setStatus(0);
        while (spcr.getStatus() != 2 && spcr.getStatus() != 3) {
            System.out.println("in status while");
            try {
                spcr = this.programDataService.getCommitRequestByCommitRequestId(commitRequestId);
                System.out.println("status+++" + spcr);
                Thread.sleep(5000L);
            } catch (InterruptedException ex) {
                Logger.getLogger(FirebaseNotificationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return CompletableFuture.completedFuture(spcr);
    }

}
