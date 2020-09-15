/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ExtendedProductCategory;
import cc.altius.FASP.model.SimpleCodeObject;
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
public class TreeExtendedProductCategoryResultSetExtractor implements ResultSetExtractor<Tree<ExtendedProductCategory>> {

    private final String prefix;

    public TreeExtendedProductCategoryResultSetExtractor() {
        this.prefix = "";
    }

    public TreeExtendedProductCategoryResultSetExtractor(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Tree<ExtendedProductCategory> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Tree<ExtendedProductCategory> productCategoryTree = null;
        boolean isFirst = true;
        int count = 1;
        try {
            while (rs.next()) {
                Integer parentProductCategoryId = rs.getInt(this.prefix + "PARENT_PRODUCT_CATEGORY_ID");
                if (rs.wasNull()) {
                    parentProductCategoryId = null;
                }
                ExtendedProductCategory pc = new ExtendedProductCategory(
                        rs.getInt(this.prefix + "PRODUCT_CATEGORY_ID"),
                        new SimpleCodeObject(rs.getInt(prefix + "REALM_ID"), new LabelRowMapper(this.prefix + "REALM_").mapRow(rs, 1), rs.getString(prefix + "REALM_CODE")),
                        new LabelRowMapper(prefix).mapRow(rs, 1),
                        parentProductCategoryId,
                        ""
                );
                pc.setBaseModel(new BaseModelRowMapper(this.prefix).mapRow(rs, 1));
                if (isFirst) {
                    Node<ExtendedProductCategory> n = new Node(count, null, pc, pc.getProductCategoryId());
                    productCategoryTree = new Tree<>(n);
                    isFirst = false;
                } else {
                    Node<ExtendedProductCategory> parentNode = productCategoryTree.findNodeByPayloadId(pc.getParentProductCategoryId());
                    Node<ExtendedProductCategory> n = new Node<>(count, parentNode.getId(), pc, pc.getProductCategoryId());
                    productCategoryTree.addNode(n);
                }
                count++;
            }
        } catch (Exception e) {
            throw new DataAccessResourceFailureException(e.getMessage());
        }
        if (productCategoryTree == null) {
            productCategoryTree = new Tree<>(new Node(count, null, 1, 1));
        }
        return productCategoryTree;
    }
}
