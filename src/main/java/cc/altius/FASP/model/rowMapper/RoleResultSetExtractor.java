/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CanCreateRole;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author altius
 */
public class RoleResultSetExtractor implements ResultSetExtractor<Role> {

    @Override
    public Role extractData(ResultSet rs) throws SQLException, DataAccessException {
        Role role = new Role();
        BusinessFunction businessFunction;
        CanCreateRole canCreateRole;
        while (rs.next()) {
            role.setRoleId(rs.getString("ROLE_ID"));
            role.setLabel(new Label(rs.getInt("LABEL_ID"), rs.getString("LABEL_EN"), rs.getString("LABEL_SP"), rs.getString("LABEL_FR"), rs.getString("LABEL_PR")));
            businessFunction = new BusinessFunction();
            businessFunction.setBusinessFunctionId(rs.getString("BUSINESS_FUNCTION_ID"));
            if (businessFunction.getBusinessFunctionId() != null && role.getBusinessFunctionList().indexOf(businessFunction) == -1) {
                role.getBusinessFunctionList().add(businessFunction);
            }
            canCreateRole = new CanCreateRole();
            canCreateRole.setRoleId(rs.getString("CAN_CREATE_ROLE"));
            if (canCreateRole.getRoleId() != null && role.getCanCreateRoleList().indexOf(canCreateRole) == -1) {
                role.getCanCreateRoleList().add(canCreateRole);
            }
        }
        return role;
    }
}
