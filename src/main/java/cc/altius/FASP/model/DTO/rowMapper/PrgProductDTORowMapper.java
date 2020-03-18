/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgProductDTORowMapper implements RowMapper<PrgProductDTO> {
    
    @Override
    public PrgProductDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgProductDTO p = new PrgProductDTO();
        PrgUnitDTO forecastUnit = new PrgUnitDTO();
        forecastUnit.setUnitId(rs.getInt("FORECASTING_UNIT_ID"));
        p.setForecastUnit(forecastUnit);
        p.setGenericLabel(new PrgLabelDTORowMapper("GL_").mapRow(rs, i));
        p.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        PrgProductCategoryDTO productCategory = new PrgProductCategoryDTO();
        productCategory.setProductCategoryId(rs.getInt("PRODUCT_CATEGORY_ID"));
        p.setProductCategory(productCategory);
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setActive(rs.getBoolean("ACTIVE"));
        p.setRealmId(rs.getInt("REALM_ID"));
        return p;
    }
    
}
