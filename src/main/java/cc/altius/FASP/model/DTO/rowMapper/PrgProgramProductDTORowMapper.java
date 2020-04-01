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
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import cc.altius.FASP.model.Dimension;
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
        forecastUnit.setLabel(new PrgLabelDTORowMapper("FORECAST_UNIT_").mapRow(rs, i));
        forecastUnit.setUnitCode(rs.getString("FORECAST_UNIT_CODE"));
        forecastUnit.setUnitId(rs.getInt("FORECAST_UNIT_ID"));
        PrgUnitTypeDTO forecastUnitType = new PrgUnitTypeDTO();
        forecastUnitType.setLabel(new PrgLabelDTORowMapper("FORECAST_UNIT_TYPE_").mapRow(rs, i));
        forecastUnitType.setUnitTypeId(rs.getInt("FORECAST_UNIT_TYPE_ID"));
        forecastUnit.setUnitType(forecastUnitType);
        product.setForecastUnit(forecastUnit);
        product.setGenericLabel(new PrgLabelDTORowMapper("GENERIC_").mapRow(rs, i));
        product.setLabel(new PrgLabelDTORowMapper("PRODUCT_").mapRow(rs, i));
        product.setProductId(rs.getInt("PRODUCT_ID"));
        PrgProductCategoryDTO productCategory = new PrgProductCategoryDTO();
        productCategory.setLabel(new PrgLabelDTORowMapper("PRODUCT_CATEGORY_").mapRow(rs, i));
        productCategory.setProductCategoryId(rs.getInt("PRODUCT_CATEGORY_ID"));
        product.setProductCategory(productCategory);
        pp.setProduct(product);
        return pp;
    }

}
