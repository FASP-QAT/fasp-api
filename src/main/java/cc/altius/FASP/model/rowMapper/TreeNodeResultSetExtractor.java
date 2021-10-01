/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.TreeNode;
import cc.altius.utils.TreeUtils.Node;
import cc.altius.utils.TreeUtils.Tree;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class TreeNodeResultSetExtractor implements ResultSetExtractor<Tree<TreeNode>>{

    @Override
    public Tree<TreeNode> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Tree<TreeNode> tree = null;
        boolean isFirst = true;
        int count = 1;
        try {
            while (rs.next()) {
                Integer parentNodeId = rs.getInt("PARENT_NODE_ID");
                if (rs.wasNull()) {
                    parentNodeId = null;
                }
                TreeNode tn = new TreeNode(
                        rs.getInt("NODE_ID"),
                        parentNodeId,
                        rs.getString("SORT_ORDER"),
                        rs.getInt("LEVEL_NO"),
                        new SimpleObject(rs.getInt("NODE_TYPE_ID"), new LabelRowMapper("NT_").mapRow(rs, 1)),
                        new SimpleCodeObject(rs.getInt("U_UNIT_ID"), new LabelRowMapper("U_").mapRow(rs, 1), rs.getString("U_UNIT_CODE")),
                        new LabelRowMapper().mapRow(rs, 1)
                );
                tn.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                if (isFirst) {
                    Node<TreeNode> n = new Node(count, null, tn, tn.getNodeId());
                    tree = new Tree<>(n);
                    isFirst = false;
                } else {
                    Node<TreeNode> parentNode = tree.findNodeByPayloadId(tn.getParentNodeId());
                    Node<TreeNode> n = new Node<>(count, parentNode.getId(), tn, tn.getNodeId());
                    tree.addNode(n);
                }
                count++;
            }
        } catch (Exception e) {
            throw new DataAccessResourceFailureException(e.getMessage());
        }
        return tree;
    }
    
}
