/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProductCategoryRowMapper implements RowMapper<ProductCategory>{

    @Override
    public ProductCategory mapRow(ResultSet rs, int i) throws SQLException {
        ProductCategory pc = new ProductCategory(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper().mapRow(rs, i));
        pc.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        pc.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")));
        return pc;
    }
    
}
