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
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author altius
 */
public class RoleRowMapper implements ResultSetExtractor<List<Role>> {

    @Override
    public List<Role> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Role> roleList = new LinkedList<>();
        Role role;
        BusinessFunction businessFunction;
        CanCreateRole canCreateRole;
        while (rs.next()) {
            role = new Role();
            role.setRoleId(rs.getString("ROLE_ID"));
            if (roleList.indexOf(role) == -1) {
                Label l = new Label();
                l.setLabelId(rs.getInt("LABEL_ID"));
                l.setEngLabel(rs.getString("LABEL_EN"));
                l.setSpaLabel(rs.getString("LABEL_SP"));
                l.setFreLabel(rs.getString("LABEL_FR"));
                l.setPorLabel(rs.getString("LABEL_PR"));
                role.setLabel(l);
                roleList.add(role);
            }
            role = roleList.get(roleList.indexOf(role));
            businessFunction = new BusinessFunction();
            businessFunction.setBusinessFunctionId(rs.getString("BUSINESS_FUNCTION_ID"));
            if (role.getBusinessFunctionList().indexOf(businessFunction) == -1) {
                role.getBusinessFunctionList().add(businessFunction);
            }
            canCreateRole = new CanCreateRole();
            canCreateRole.setRoleId(rs.getString("CAN_CREATE_ROLE"));
            if (role.getCanCreateRoles().indexOf(canCreateRole) == -1) {
                role.getCanCreateRoles().add(canCreateRole);
            }
        }
        return roleList;
    }

//    @Override
//    public Role extractData(ResultSet rs) throws SQLException, DataAccessException {
//        Role role = new Role();
//        boolean isFirst = true;
//        while (rs.next()) {
//            if (isFirst) {
//                role.setRoleId(rs.getString("ROLE_ID"));
//                Label l = new Label();
//                l.setLabelId(rs.getInt(""));
//                l.setEngLabel(rs.getString("LABEL_EN"));
//                l.setSpaLabel(rs.getString("LABEL_SP"));
//                l.setFreLabel(rs.getString("LABEL_FR"));
//                l.setPorLabel(rs.getString("LABEL_PR"));
//                role.setLabel(l);
//                role.setBusinessFunctionList(new LinkedList<>());
//                role.setCanCreateRoles(new LinkedList<>());
//            }
//            BusinessFunction b = new BusinessFunction();
//            b.setBusinessFunctionId(rs.getString("BUSINESS_FUNCTION_ID"));
//            if (role.getBusinessFunctionList().indexOf(b) == -1) {
//                role.getBusinessFunctionList().add(b);
//            }
//
//            CanCreateRole c = new CanCreateRole();
//            c.setRoleId(rs.getString("ROLE_ID"));
//            if (role.getCanCreateRoles().indexOf(c) == -1) {
//                role.getCanCreateRoles().add(c);
//            }
//            isFirst = false;
//
//        }
////        if (isFirst) {
////            return null;
////        } else {
//            return role;
////        }
//    }
}
