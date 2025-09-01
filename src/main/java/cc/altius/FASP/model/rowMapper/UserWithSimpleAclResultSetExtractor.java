/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.UserWithSimpleAcl;
import cc.altius.FASP.model.SimpleUserAcl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class UserWithSimpleAclResultSetExtractor implements ResultSetExtractor<List<UserWithSimpleAcl>> {

    @Override
    public List<UserWithSimpleAcl> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<UserWithSimpleAcl> userList = new LinkedList<>();
        while (rs.next()) {
            UserWithSimpleAcl u = new UserWithSimpleAcl();
            u.setUserId(rs.getInt("USER_ID"));
            int idx = userList.indexOf(u);
            if (idx == -1) {
                u.setUsername(rs.getString("USERNAME"));
                u.setOrgAndCountry(rs.getString("ORG_AND_COUNTRY"));
                userList.add(u);
            } else {
                u = userList.get(idx);
            }
            SimpleUserAcl acl = new SimpleUserAcl();
            acl.setRoleId(rs.getString("ROLE_ID"));
            acl.setRoleDesc(new LabelRowMapper("").mapRow(rs, idx));
            acl.setRealmCountryId(rs.getInt("REALM_COUNTRY_ID"));
            if (rs.wasNull()) {
                acl.setRealmCountryId(-1);
            } else {
                acl.setCountryName(new LabelRowMapper("C_").mapRow(rs, idx));
            }
            acl.setHealthAreaId(rs.getInt("HEALTH_AREA_ID"));
            if (rs.wasNull()) {
                acl.setHealthAreaId(-1);
            } else {
                acl.setHealthAreaName(new LabelRowMapper("HA_").mapRow(rs, idx));
            }
            acl.setOrganisationId(rs.getInt("ORGANISATION_ID"));
            if (rs.wasNull()) {
                acl.setOrganisationId(-1);
            } else {
                acl.setOrganisationName(new LabelRowMapper("O_").mapRow(rs, idx));
            }
            acl.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
            if (rs.wasNull()) {
                acl.setFundingSourceId(-1);
            } else {
                acl.setFundingSourceName(new LabelRowMapper("FS_").mapRow(rs, idx));
            }
            acl.setProcurementAgentId(rs.getInt("PROCUREMENT_AGENT_ID"));
            if (rs.wasNull()) {
                acl.setProcurementAgentId(-1);
            } else {
                acl.setProcurementAgentName(new LabelRowMapper("PA_").mapRow(rs, idx));
            }
            acl.setProgramId(rs.getInt("PROGRAM_ID"));
            if (rs.wasNull()) {
                acl.setProgramId(-1);
            } else {
                acl.setProgramName(new LabelRowMapper("P_").mapRow(rs, idx));
                acl.setProgramCode(rs.getString("PROGRAM_CODE"));
            }
            u.getAclList().add(acl);
        }
        return userList;
    }
}