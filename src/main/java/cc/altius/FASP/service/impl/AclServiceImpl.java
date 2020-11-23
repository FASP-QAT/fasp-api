/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.service.AclService;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class AclServiceImpl implements AclService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private ProgramService programService;

    @Override
    public boolean checkAccessForUser(CustomUserDetails curUser, int realmId, int realmCountryId, int healthAreaId, int organisationId, int programId) {
        logger.info("Going to check if userId:" + curUser.getUserId() + " has access to RealmId:" + realmId + ", realmCountryId:" + realmCountryId + ", healthAreaId:" + healthAreaId + ", organisationId:" + organisationId + ", programId:" + programId);
        if (curUser.getRealm().getRealmId() != -1 && curUser.getRealm().getRealmId() != realmId) {
            // Is not an Application level user and also does not have access to this Realm
            logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " so cannot get access");
            return false;
        }
        logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " Realm check passed");
        boolean hasAccess = false;
        for (UserAcl acl : curUser.getAclList()) {
            logger.info(acl.toString());
            if ((acl.getRealmCountryId() == -1 || acl.getRealmCountryId() == realmCountryId || realmCountryId == 0)
                    && (acl.getHealthAreaId() == -1 || acl.getHealthAreaId() == healthAreaId || healthAreaId == 0)
                    && (acl.getOrganisationId() == -1 || acl.getOrganisationId() == organisationId || organisationId == 0)
                    && (acl.getProgramId() == -1 || acl.getProgramId() == programId || programId == 0)) {
                logger.info("Check passed");
                hasAccess = true;
            } else {
                logger.info("Check failed");
                return false;
            }
        }
        logger.info("Access allowed");
        return hasAccess;
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
    public boolean checkHealthAreaAccessForUser(CustomUserDetails curUser, int healthAreaId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkOrganisationAccessForUser(CustomUserDetails curUser, int organisationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkProgramAccessForUser(CustomUserDetails curUser, int realmId, int programId, int healthAreaId, int organisationId) {
        logger.info("Going to check if userId:" + curUser.getUserId() + " has access to ProgramId:" + programId);
//        Program p = this.programService.getProgramById(programId, curUser);
        if (curUser.getRealm().getRealmId() != -1 && curUser.getRealm().getRealmId() != realmId) {
            // Is not an Application level user and also does not have access to this Realm
            logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " so cannot get access");
            return false;
        } else {
            logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " Realm check passed");
            for (UserAcl ua : curUser.getAclList()) {
                if ((ua.getHealthAreaId() == -1 || ua.getHealthAreaId() == healthAreaId)
                        && (ua.getOrganisationId() == -1 || ua.getOrganisationId() == organisationId)
                        && (ua.getProgramId() == -1 || ua.getProgramId() == programId)) {
                    logger.info("Access allowed since he has access to " + ua);
                    return true;
                }
            }
        }
        return false;
    }

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
    public void addFullAclForProgram(StringBuilder sb, Map<String, Object> params, String programAlias, CustomUserDetails curUser) {
        int count = 1;
        StringBuilder localSb = new StringBuilder();
        localSb.append(" AND (FALSE ");
        for (UserAcl userAcl : curUser.getAclList()) {
            localSb.append(" OR (")
                    .append("(").append(programAlias).append(".PROGRAM_ID IS NULL OR :realmCountryId").append(count).append("=-1 OR ").append(programAlias).append(".REALM_COUNTRY_ID=:realmCountryId").append(count).append(")")
                    .append("AND (").append(programAlias).append(".PROGRAM_ID IS NULL OR :healthAreaId").append(count).append("=-1 OR ").append(programAlias).append(".HEALTH_AREA_ID=:healthAreaId").append(count).append(")")
                    .append("AND (").append(programAlias).append(".PROGRAM_ID IS NULL OR :organisationId").append(count).append("=-1 OR ").append(programAlias).append(".ORGANISATION_ID=:organisationId").append(count).append(")")
                    .append("AND (").append(programAlias).append(".PROGRAM_ID IS NULL OR :programId").append(count).append("=-1 OR ").append(programAlias).append(".PROGRAM_ID=:programId").append(count).append(")")
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

}
