/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author akil
 */
public class AclRoleBusinessFunctionResultSetExtractor implements ResultSetExtractor<Map<String, List<String>>> {

    private final List<String> businessFunctionList;

    public AclRoleBusinessFunctionResultSetExtractor(List<SimpleGrantedAuthority> businessFunctionList) {
        this.businessFunctionList = new LinkedList<>();
        businessFunctionList.forEach(bf -> {
            this.businessFunctionList.add(bf.getAuthority());
        });
    }

    @Override
    public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, List<String>> aclBfMap = new HashMap<>();
        while (rs.next()) {
            String roleId = rs.getString("ROLE_ID");
            if (roleId == null) {
                roleId = "";
            }
            if (!aclBfMap.containsKey(roleId)) {
                aclBfMap.put(roleId, new LinkedList<String>());
            }
            aclBfMap.get(roleId).add(rs.getString("BUSINESS_FUNCTION_ID"));
        }
        if (aclBfMap.containsKey("")) {
            aclBfMap.replace("", this.businessFunctionList);
        }
        return aclBfMap;
    }

}
