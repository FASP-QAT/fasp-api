/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.AclDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.service.AclService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class AclServiceImpl implements AclService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AclDao aclDao;

    @Override
    public boolean checkAccessForUser(CustomUserDetails curUser, int realmId, int realmCountryId, List<Integer> healthAreaIdList, int organisationId, int programId) {
        logger.info("Going to check if userId:" + curUser.getUserId() + " has access to RealmId:" + realmId + ", realmCountryId:" + realmCountryId + ", healthAreaIdList:" + healthAreaIdList + ", organisationId:" + organisationId + ", programId:" + programId);
        if (curUser.getRealm().getRealmId() != -1 && curUser.getRealm().getRealmId() != realmId) {
            // Is not an Application level user and also does not have access to this Realm
            logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " so cannot get access");
            return false;
        }
        logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " Realm check passed");
        for (UserAcl acl : curUser.getAclList()) {
            logger.info(acl.toString());
            if ((acl.getRealmCountryId() == -1 || acl.getRealmCountryId() == realmCountryId || realmCountryId == 0)
                    && (acl.getHealthAreaId() == -1 || healthAreaIdList == null || healthAreaIdList.isEmpty() || healthAreaIdList.indexOf(acl.getHealthAreaId()) >= 0)
                    && (acl.getOrganisationId() == -1 || acl.getOrganisationId() == organisationId || organisationId == 0)
                    && (acl.getProgramId() == -1 || acl.getProgramId() == programId || programId == 0)) {
                logger.info("Check passed");
                return true;
            }
        }
        logger.info("Access not allowed");
        return false;
    }

    @Override
    public boolean checkRealmAccessForUser(CustomUserDetails curUser, int realmId) {
        logger.info("Going to check if userId:" + curUser.getUserId() + " has access to RealmId:" + realmId);
        if (curUser.getRealm().getRealmId() != -1 && curUser.getRealm().getRealmId() != realmId) {
            // Is not an Application level user and also does not have access to this Realm
            logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " so cannot get access");
            return false;
        }
        logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " Realm check passed");
        return true;
    }

    @Override
    public List<UserAcl> expandUserAccess(UserAcl acl, CustomUserDetails curUser) {
        return this.aclDao.expandUserAccess(acl, curUser);
    }

//    @Override
//    public boolean checkProgramAccessForUser(CustomUserDetails curUser, int realmId, int realmCountryId, int programId, List<Integer> healthAreaIdList, int organisationId) {
//        boolean hasAccess = false;
//        for (Integer ha : healthAreaIdList) {
//            logger.info("Going to check if userId:" + curUser.getUserId() + " has access to ProgramId:" + programId);
////        Program p = this.programService.getProgramById(programId, curUser);
//            if (curUser.getRealm().getRealmId() != -1 && curUser.getRealm().getRealmId() != realmId) {
//                // Is not an Application level user and also does not have access to this Realm
//                logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " so cannot get access");
//                return false;
//            } else {
//                logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " Realm check passed");
//                for (UserAcl ua : curUser.getAclList()) {
//                    if (
//                            (ua.getRealmCountryId() == -1 || ua.getRealmCountryId() == realmCountryId)
//                            && (ua.getHealthAreaId() == -1 || ua.getHealthAreaId() == ha)
//                            && (ua.getOrganisationId() == -1 || ua.getOrganisationId() == organisationId)
//                            && (ua.getProgramId() == -1 || ua.getProgramId() == programId)) {
//                        logger.info("Access allowed since he has access to " + ua);
//                        return true;
//                    } else {
//                        hasAccess = false;
//                    }
//                }
//            }
//        }
//        return hasAccess;
//    }
    @Override
    public String addUserAclForRealm(String sqlString, Map<String, Object> params, String realmAlias, int realmId, CustomUserDetails curUser) {
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND " + realmAlias + ".REALM_ID=:aclRealmId0 ";
            params.put("aclRealmId0", realmId);
        }
        return sqlString;
    }

    @Override
    public String addUserAclForRealm(String sqlString, Map<String, Object> params, String realmAlias, CustomUserDetails curUser) {
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += " AND " + realmAlias + ".REALM_ID=:aclRealmId1 ";
            params.put("aclRealmId1", curUser.getRealm().getRealmId());
        }
        return sqlString;
    }

    @Override
    public void addUserAclForRealm(StringBuilder sb, Map<String, Object> params, String realmAlias, int realmId, CustomUserDetails curUser) {
        if (realmId != -1) {
            sb.append(" AND ").append(realmAlias).append(".REALM_ID=:aclRealmId0 ");
            params.put("aclRealmId0", realmId);
        }
    }

    @Override
    public void addUserAclForRealm(StringBuilder sb, Map<String, Object> params, String realmAlias, CustomUserDetails curUser) {
        if (curUser.getRealm().getRealmId() != -1) {
            sb.append(" AND ").append(realmAlias).append(".REALM_ID=:aclRealmId1 ");
            params.put("aclRealmId1", curUser.getRealm().getRealmId());
        }
    }

    @Override
    // Can only be called with the vw_program view  or vw_dataset or vw_all_program view and not the rm_program table
    public void addFullAclForProgram(StringBuilder sb, Map<String, Object> params, String programAlias, CustomUserDetails curUser) {
        int count = 1;
        StringBuilder localSb = new StringBuilder();
        localSb.append(" AND (FALSE ");
        logger.info("cur user acl"+curUser.getAclList());
        for (UserAcl userAcl : curUser.getAclList()) {
            localSb.append(" OR (")
                    .append("(").append(programAlias).append(".PROGRAM_ID IS NULL OR :realmCountryId").append(count).append("=-1 OR ").append(programAlias).append(".REALM_COUNTRY_ID=:realmCountryId").append(count).append(")")
                    .append(" AND (").append(programAlias).append(".PROGRAM_ID IS NULL OR :healthAreaId").append(count).append("=-1 OR FIND_IN_SET(:healthAreaId").append(count).append(", ").append(programAlias).append(".HEALTH_AREA_ID))")
                    .append(" AND (").append(programAlias).append(".PROGRAM_ID IS NULL OR :organisationId").append(count).append("=-1 OR ").append(programAlias).append(".ORGANISATION_ID=:organisationId").append(count).append(")")
                    .append(" AND (").append(programAlias).append(".PROGRAM_ID IS NULL OR :programId").append(count).append("=-1 OR ").append(programAlias).append(".PROGRAM_ID=:programId").append(count).append(")")
                    .append(")");
            params.put("realmCountryId" + count, userAcl.getRealmCountryId());
            params.put("healthAreaId" + count, userAcl.getHealthAreaId());
            params.put("organisationId" + count, userAcl.getOrganisationId());
            params.put("programId" + count, userAcl.getProgramId());
            count++;
        }
        localSb.append(")");
        sb.append(localSb);
    }

    @Override
    public void addUserAclForHealthArea(StringBuilder sb, Map<String, Object> params, String haAlias, CustomUserDetails curUser) {
        int count = 1;
        StringBuilder localSb = new StringBuilder();
        localSb.append(" AND (FALSE ");
        for (UserAcl userAcl : curUser.getAclList()) {
            localSb.append(" OR (")
                    .append("(").append(haAlias).append(".HEALTH_AREA_ID IS NULL OR :healthAreaIdHa").append(count).append("=-1 OR ").append(haAlias).append(".HEALTH_AREA_ID=:healthAreaIdHa").append(count).append(")")
                    .append(")");
            params.put("healthAreaIdHa" + count, userAcl.getHealthAreaId());
            count++;
        }
        localSb.append(")");
        sb.append(localSb);
    }

    @Override
    public void addUserAclForOrganisation(StringBuilder sb, Map<String, Object> params, String oAlias, CustomUserDetails curUser) {
        int count = 1;
        StringBuilder localSb = new StringBuilder();
        localSb.append(" AND (FALSE ");
        for (UserAcl userAcl : curUser.getAclList()) {
            localSb.append(" OR (")
                    .append("(").append(oAlias).append(".ORGANISATION_ID IS NULL OR :organisationIdO").append(count).append("=-1 OR ").append(oAlias).append(".ORGANISATION_ID=:organisationIdO").append(count).append(")")
                    .append(")");
            params.put("organisationIdO" + count, userAcl.getOrganisationId());
            count++;
        }
        localSb.append(")");
        sb.append(localSb);
    }

    @Override
    public void addUserAclForRealmCountry(StringBuilder sb, Map<String, Object> params, String rcAlias, CustomUserDetails curUser) {
        int count = 1;
        StringBuilder localSb = new StringBuilder();
        localSb.append(" AND (FALSE ");
        for (UserAcl userAcl : curUser.getAclList()) {
            localSb.append(" OR (")
                    .append("(").append(rcAlias).append(".REALM_COUNTRY_ID IS NULL OR :realmCountryIdRc").append(count).append("=-1 OR ").append(rcAlias).append(".REALM_COUNTRY_ID=:realmCountryIdRc").append(count).append(")")
                    .append(")");
            params.put("realmCountryIdRc" + count, userAcl.getRealmCountryId());
            count++;
        }
        localSb.append(")");
        sb.append(localSb);
    }

    @Override
    public void addFullAclAtUserLevel(StringBuilder sb, Map<String, Object> params, String userAclAlias, CustomUserDetails curUser) {
        int count = 1;
        StringBuilder localSb = new StringBuilder();
        localSb.append(" AND (FALSE ");
        for (UserAcl userAcl : curUser.getAclList()) {
            localSb.append(" OR (")
                    .append(userAclAlias).append(".ROLE_ID IN (SELECT ccr.CAN_CREATE_ROLE FROM us_can_create_role ccr WHERE ccr.ROLE_ID=:roleId").append(count).append(")")
                    .append(" AND (:realmCountryId").append(count).append("=-1 OR ").append(userAclAlias).append(".REALM_COUNTRY_ID=:realmCountryId").append(count).append(")")
                    .append(" AND (:healthAreaId").append(count).append("=-1 OR ").append(userAclAlias).append(".HEALTH_AREA_ID=:healthAreaId").append(count).append(")")
                    .append(" AND (:organisationId").append(count).append("=-1 OR ").append(userAclAlias).append(".ORGANISATION_ID=:organisationId").append(count).append(")")
                    .append(" AND (:programId").append(count).append("=-1 OR ").append(userAclAlias).append(".PROGRAM_ID=:programId").append(count).append(")")
                    .append(")");
            params.put("roleId" + count, userAcl.getRoleId());
            params.put("realmCountryId" + count, userAcl.getRealmCountryId());
            params.put("healthAreaId" + count, userAcl.getHealthAreaId());
            params.put("organisationId" + count, userAcl.getOrganisationId());
            params.put("programId" + count, userAcl.getProgramId());
            count++;
        }

        localSb.append(")");
        sb.append(localSb);
    }

    @Override
    public int buildSecurity() {
        return this.aclDao.buildSecurity();
    }

}
