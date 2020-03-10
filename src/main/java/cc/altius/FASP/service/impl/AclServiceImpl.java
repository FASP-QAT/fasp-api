/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.service.AclService;
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

    @Override
    public boolean checkAccessForUser(CustomUserDetails curUser, int realmId, int realmCountryId, int healthAreaId, int organisationId, int programId) {
        logger.info("Going to check if userId:" + curUser.getUserId() + " has access to RealmId:" + realmId + ", realmCountryId:" + realmCountryId + ", healthAreaId:" + healthAreaId + ", organisationId:" + organisationId + ", programId:" + programId);
        // Before committing just make sure that this User has the rights to Add to this Realm
//        String sql = "SELECT REALM_ID FROM us_user u WHERE u.USER_ID=?";
        if (curUser.getRealm().getRealmId() != -1 && curUser.getRealm().getRealmId() != realmId) {
            // Is not an Application level user and also does not have access to this Realm
            logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " so cannot get access");
            return false;
        }
        logger.info("UserRealmId:" + curUser.getRealm().getRealmId() + " Realm check passed");

//        sql = "SELECT "
//                + "acl.USER_ID, "
//                + "acl.`REALM_COUNTRY_ID` `REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `COUNTRY_LABEL_ID`, acl_country_lb.`LABEL_EN` `COUNTRY_LABEL_EN`, acl_country_lb.`LABEL_FR` `COUNTRY_LABEL_FR`, acl_country_lb.`LABEL_SP` `COUNTRY_LABEL_SP`, acl_country_lb.`LABEL_PR` `COUNTRY_LABEL_PR`, "
//                + "acl.`HEALTH_AREA_ID` `HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `HEALTH_AREA_LABEL_PR`, "
//                + "acl.`ORGANISATION_ID` `ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ORGANISATION_LABEL_PR`, "
//                + "acl.`PROGRAM_ID` `PROGRAM_ID`, acl_program_lb.`LABEL_ID` `PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `PROGRAM_LABEL_PR` "
//                + "FROM us_user_acl acl "
//                + "LEFT JOIN rm_realm_country acl_realm_country ON acl.`REALM_COUNTRY_ID`=acl_realm_country.`REALM_COUNTRY_ID` "
//                + "LEFT JOIN ap_country acl_country ON acl_realm_country.`COUNTRY_ID`=acl_country.`COUNTRY_ID` "
//                + "LEFT JOIN ap_label acl_country_lb ON acl_country.`LABEL_ID`=acl_country_lb.`LABEL_ID` "
//                + "LEFT JOIN rm_health_area acl_health_area ON acl.`HEALTH_AREA_ID`=acl_health_area.`HEALTH_AREA_ID` "
//                + "LEFT JOIN ap_label acl_health_area_lb ON acl_health_area.`LABEL_ID`=acl_health_area_lb.`LABEL_ID` "
//                + "LEFT JOIN rm_organisation acl_organisation ON acl.`ORGANISATION_ID`=acl_organisation.`ORGANISATION_ID` "
//                + "LEFT JOIN ap_label acl_organisation_lb ON acl_organisation.`LABEL_ID`=acl_organisation_lb.`LABEL_ID` "
//                + "LEFT JOIN rm_program acl_program ON acl.`PROGRAM_ID`=acl_program.`PROGRAM_ID` "
//                + "LEFT JOIN ap_label acl_program_lb on acl_program.`LABEL_ID`=acl_program_lb.`LABEL_ID`"
//                + "WHERE acl.USER_ID=?";
//        List<UserAcl> userAcl = curUser.getAclList();
//                this.jdbcTemplate.query(sql, new UserAclRowMapper(), userId);
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
    public boolean checkProgramAccessForUser(CustomUserDetails curUser, int programId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
