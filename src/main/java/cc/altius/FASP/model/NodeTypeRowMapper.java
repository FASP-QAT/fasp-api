/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.model.rowMapper.LabelRowMapper;
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
        return new NodeType(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, rowNum), rs.getBoolean("MODELING_ALLOWED"), rs.getBoolean("TREE_TEMPLATE_ALLOWED"), rs.getBoolean("FORECAST_TREE_ALLOWED"));
    }

}
