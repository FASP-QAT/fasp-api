/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author akil
 */
public interface AclService {

    public boolean userHasAccessToResources(CustomUserDetails curUser, int realmId, int realmCountryId, List<Integer> healthAreaId, int organisationId, int programId, List<Integer> fundingSourceIdList, List<Integer> procurementAgentIdList);

    public boolean checkRealmAccessForUser(CustomUserDetails curUser, int realmId);

    public List<UserAcl> expandUserAccess(UserAcl acl, CustomUserDetails curUser) throws AccessControlFailedException;

    public String addUserAclForRealm(String sqlString, Map<String, Object> params, String realmAlias, int realmId, CustomUserDetails curUser);

    public String addUserAclForRealm(String sqlString, Map<String, Object> params, String realmAlias, CustomUserDetails curUser);

    public void addUserAclForRealm(StringBuilder sb, Map<String, Object> params, String realmAlias, int realmId, CustomUserDetails curUser);

    public void addUserAclForRealm(StringBuilder sb, Map<String, Object> params, String realmAlias, CustomUserDetails curUser);

    public void addFullAclForProgram(StringBuilder sb, Map<String, Object> params, String programAlias, CustomUserDetails curUser);

    public void addUserAclForHealthArea(StringBuilder sb, Map<String, Object> params, String haAlias, CustomUserDetails curUser);

    public void addUserAclForOrganisation(StringBuilder sb, Map<String, Object> params, String oAlias, CustomUserDetails curUser);

    public void addUserAclForRealmCountry(StringBuilder sb, Map<String, Object> params, String rcAlias, CustomUserDetails curUser);
    
    public void addUserAclForFundingSource(StringBuilder sb, Map<String, Object> params, String haAlias, CustomUserDetails curUser);
    
    public void addUserAclForProcurementAgent(StringBuilder sb, Map<String, Object> params, String haAlias, CustomUserDetails curUser);

    public void addFullAclAtUserLevel(StringBuilder sb, Map<String, Object> params, String userAclAlias, CustomUserDetails curUser);

    public int buildSecurity();
    
    public boolean canEditUser(User user, CustomUserDetails curUser, Map<String, List<String>> canCreateRoleMap);
}
