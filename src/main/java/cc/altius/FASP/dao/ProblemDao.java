/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmProblem;
import java.util.List;

/**
 *
 * @author akil
 */
public interface ProblemDao {

    public List<RealmProblem> getProblemListByRealmId(int realmId, CustomUserDetails curUser);
    
    public List<RealmProblem> getProblemListForSync(int realmId, String lastModifiedDate, CustomUserDetails curUser);
}
