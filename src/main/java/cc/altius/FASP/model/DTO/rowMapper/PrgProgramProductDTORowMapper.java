/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import cc.altius.FASP.model.DTO.PrgProductDTO;
import cc.altius.FASP.model.DTO.PrgProgramProductDTO;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.UnitType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgProgramProductDTORowMapper implements RowMapper<PrgProgramProductDTO> {

    @Override
    public PrgProgramProductDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgProgramProductDTO pp = new PrgProgramProductDTO();
        pp.setMaxMonths(rs.getInt("MAX_MONTHS"));
        pp.setMinMonths(rs.getInt("MIN_MONTHS"));
        pp.setProgramProductId(rs.getInt("PROGRAM_PRODUCT_ID"));

        PrgProductDTO product = new PrgProductDTO();
        PrgUnitDTO forecastUnit = new PrgUnitDTO();
        PrgLabelDTO forecastUnitLabel = new PrgLabelDTO();
        forecastUnitLabel.setLabelEn(rs.getString("FORECAST_UNIT_NAME_EN"));
        forecastUnitLabel.setLabelFr(rs.getString("FORECAST_UNIT_NAME_FR"));
        forecastUnitLabel.setLabelPr(rs.getString("FORECAST_UNIT_NAME_PR"));
        forecastUnitLabel.setLabelSp(rs.getString("FORECAST_UNIT_NAME_SP"));
        forecastUnit.setLabel(forecastUnitLabel);
        forecastUnit.setUnitCode(rs.getString("FORECAST_UNIT_CODE"));
        forecastUnit.setUnitId(rs.getInt("FORECAST_UNIT_ID"));
        UnitType forecastUnitType = new UnitType();
        PrgLabelDTO forecastUnitTypeLabel = new PrgLabelDTO();
        forecastUnitTypeLabel.setLabelEn(rs.getString("FORECAST_UNIT_TYPE_NAME_EN"));
        forecastUnitTypeLabel.setLabelFr(rs.getString("FORECAST_UNIT_TYPE_NAME_FR"));
        forecastUnitTypeLabel.setLabelPr(rs.getString("FORECAST_UNIT_TYPE_NAME_PR"));
        forecastUnitTypeLabel.setLabelSp(rs.getString("FORECAST_UNIT_TYPE_NAME_SP"));
        forecastUnitType.setLabel(forecastUnitTypeLabel);
        forecastUnitType.setUnitTypeId(rs.getInt("FORECAST_UNIT_TYPE_ID"));
        forecastUnit.setUnitType(forecastUnitType);
        product.setForecastUnit(forecastUnit);
        PrgLabelDTO genericLabel = new PrgLabelDTO();
        genericLabel.setLabelEn(rs.getString("GENERIC_LABEL_EN"));
        genericLabel.setLabelFr(rs.getString("GENERIC_LABEL_FR"));
        genericLabel.setLabelPr(rs.getString("GENERIC_LABEL_PR"));
        genericLabel.setLabelSp(rs.getString("GENERIC_LABEL_SP"));
        product.setGenericLabel(genericLabel);
        PrgLabelDTO productLabel = new PrgLabelDTO();
        productLabel.setLabelEn(rs.getString("PRODUCT_NAME_EN"));
        productLabel.setLabelFr(rs.getString("PRODUCT_NAME_FR"));
        productLabel.setLabelPr(rs.getString("PRODUCT_NAME_PR"));
        productLabel.setLabelSp(rs.getString("PRODUCT_NAME_SP"));
        product.setLabel(productLabel);
        product.setProductId(rs.getInt("PRODUCT_ID"));
        PrgProductCategoryDTO productCategory = new PrgProductCategoryDTO();
        PrgLabelDTO productCategoryLabel = new PrgLabelDTO();
        productCategoryLabel.setLabelEn(rs.getString("PRODUCT_CATEGORY_NAME_EN"));
        productCategoryLabel.setLabelFr(rs.getString("PRODUCT_CATEGORY_NAME_FR"));
        productCategoryLabel.setLabelPr(rs.getString("PRODUCT_CATEGORY_NAME_PR"));
        productCategoryLabel.setLabelSp(rs.getString("PRODUCT_CATEGORY_NAME_SP"));
        productCategory.setLabel(productCategoryLabel);
        productCategory.setProductCategoryId(rs.getInt("PRODUCT_CATEGORY_ID"));
        product.setProductCategory(productCategory);
        pp.setProduct(product);
        return pp;
    }

}
