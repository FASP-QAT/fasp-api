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
        PrgLabelDTO genericLabel = new PrgLabelDTO();
        genericLabel.setLabelEn(rs.getString("GL_LABEL_EN"));
        genericLabel.setLabelFr(rs.getString("GL_LABEL_FR"));
        genericLabel.setLabelPr(rs.getString("GL_LABEL_PR"));
        genericLabel.setLabelSp(rs.getString("GL_LABEL_SP"));
        p.setGenericLabel(genericLabel);
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        p.setLabel(label);
        PrgProductCategoryDTO productCategory = new PrgProductCategoryDTO();
        productCategory.setProductCategoryId(rs.getInt("PRODUCT_CATEGORY_ID"));
        p.setProductCategory(productCategory);
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setActive(rs.getBoolean("ACTIVE"));
        return p;
    }
    
}
