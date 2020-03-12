/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Product;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.Unit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProductRowMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product p = new Product(
                rs.getInt("PRODUCT_ID"), 
                new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")), 
                new LabelRowMapper("GENERIC_").mapRow(rs, rowNum), 
                new LabelRowMapper().mapRow(rs, rowNum), 
                new ProductCategory(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, rowNum)),
                new Unit(rs.getInt("UNIT_ID"), new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, rowNum), rs.getString("UNIT_CODE"))
        );
        p.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return p;
    }
    
}
