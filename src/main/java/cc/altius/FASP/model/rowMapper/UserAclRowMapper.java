/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.UserAcl;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class UserAclRowMapper implements RowMapper<UserAcl> {

    @Override
    public UserAcl mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserAcl userAcl = new UserAcl();
        userAcl.setUserAclId(rs.getInt("USER_ACL_ID"));
        userAcl.setUserId(rs.getInt("USER_ID"));
        userAcl.setUsername(rs.getString("USERNAME"));
        userAcl.setOrgAndCountry(rs.getString("ORG_AND_COUNTRY"));
        userAcl.setActive(rs.getInt("ACTIVE"));
        userAcl.setRoleId(rs.getString("ROLE_ID"));
        userAcl.setRoleDesc(new LabelRowMapper("ACL_ROLE_").mapRow(rs, rowNum));
        userAcl.setRealmCountryId(rs.getInt("REALM_COUNTRY_ID"));
        if (userAcl.getRealmCountryId() != -1) {
            userAcl.setCountryName(new LabelRowMapper("ACL_REALM_COUNTRY_").mapRow(rs, rowNum));
        }
        userAcl.setHealthAreaId(rs.getInt("HEALTH_AREA_ID"));
        if (userAcl.getHealthAreaId() != -1) {
            userAcl.setHealthAreaName(new LabelRowMapper("ACL_HEALTH_AREA_").mapRow(rs, rowNum));
        }
        userAcl.setOrganisationId(rs.getInt("ORGANISATION_ID"));
        if (userAcl.getOrganisationId() != -1) {
            userAcl.setOrganisationName(new LabelRowMapper("ACL_ORGANISATION_").mapRow(rs, rowNum));
        }
        userAcl.setProgramId(rs.getInt("PROGRAM_ID"));
        if (userAcl.getProgramId() != -1) {
            userAcl.setProgramName(new LabelRowMapper("ACL_PROGRAM_").mapRow(rs, rowNum));
        }
        userAcl.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        if (userAcl.getFundingSourceId()!= -1) {
            userAcl.setFundingSourceName(new LabelRowMapper("ACL_FUNDING_SOURCE_").mapRow(rs, rowNum));
        }
        userAcl.setProcurementAgentId(rs.getInt("PROCUREMENT_AGENT_ID"));
        if (userAcl.getProcurementAgentId()!= -1) {
            userAcl.setProcurementAgentName(new LabelRowMapper("ACL_PROCUREMENT_AGENT_").mapRow(rs, rowNum));
        }
        
        userAcl.setLastModifiedDate(rs.getDate("LAST_MODIFIED_DATE"));
        return userAcl;
    }

}
