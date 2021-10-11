/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NodeType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class NodeTypeRowMapper implements RowMapper<NodeType> {

    @Override
    public NodeType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new NodeType(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, rowNum), rs.getBoolean("MODELING_ALLOWED"));
    }

}
