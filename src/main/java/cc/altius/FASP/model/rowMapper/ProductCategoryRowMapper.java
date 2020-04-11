/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProductCategoryRowMapper implements RowMapper<ProductCategory> {

    private String prefix;

    public ProductCategoryRowMapper() {
        this.prefix = "";
    }

    public ProductCategoryRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public ProductCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductCategory pc = new ProductCategory(
                rs.getInt(this.prefix + "PRODUCT_CATEGORY_ID"),
                new Realm(rs.getInt(prefix + "REALM_ID"), new LabelRowMapper(this.prefix + "REALM_").mapRow(rs, rowNum), rs.getString(prefix + "REALM_CODE")),
                new LabelRowMapper(prefix).mapRow(rs, rowNum),
                rs.getInt(this.prefix + "LEVEL"),
                rs.getString(this.prefix + "SORT_ORDER")
        );
        pc.setBaseModel(new BaseModelRowMapper(this.prefix).mapRow(rs, rowNum));
        return pc;
    }

}
