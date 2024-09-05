/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ForecastingUnitArtmisPull;
import cc.altius.FASP.model.rowMapper.BaseModelRowMapper;
import cc.altius.FASP.model.rowMapper.ForecastingUnitRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ForecastingUnitArtmisPullRowMapper implements RowMapper<ForecastingUnitArtmisPull> {
    
    @Override
    public ForecastingUnitArtmisPull mapRow(ResultSet rs, int i) throws SQLException {
        ForecastingUnitArtmisPull fu = new ForecastingUnitArtmisPull();
        fu.setForecastingUnit(new ForecastingUnitRowMapper().mapRow(rs, i));
        fu.setSkuCode(rs.getString("SKU_CODE"));
        return fu;
    }
    
}
