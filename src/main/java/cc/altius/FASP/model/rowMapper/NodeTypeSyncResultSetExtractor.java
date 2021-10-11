/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NodeTypeSync;
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
public class NodeTypeSyncResultSetExtractor implements ResultSetExtractor<List<NodeTypeSync>> {

    @Override
    public List<NodeTypeSync> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<NodeTypeSync> ntList = new LinkedList<>();
        while (rs.next()) {
            NodeTypeSync nt = new NodeTypeSync(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, 1), rs.getBoolean("MODELING_ALLOWED"));
            int idx = ntList.indexOf(nt);
            if (idx == -1) {
                ntList.add(nt);
            } else {
                nt = ntList.get(idx);
            }
            Integer childNodeTypeId = rs.getInt("CHILD_NODE_TYPE_ID");
            if (!rs.wasNull()) {
                nt.getAllowedChildList().add(childNodeTypeId);
            }
        }
        return ntList;
    }

}
