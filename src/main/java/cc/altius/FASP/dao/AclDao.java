/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UserAcl;
import java.util.List;

/**
 *
 * @author akil
 */
public interface AclDao {

    public int buildSecurity();
    
    public List<UserAcl> expandUserAccess(UserAcl acl, CustomUserDetails curUser);
}
