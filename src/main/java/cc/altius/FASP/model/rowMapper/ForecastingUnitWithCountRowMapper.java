/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ForecastingUnitWithCount;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ForecastingUnitWithCountRowMapper implements RowMapper<ForecastingUnitWithCount> {

    @Override
    public ForecastingUnitWithCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForecastingUnitWithCount fuc = new ForecastingUnitWithCount(
                rs.getInt("FORECASTING_UNIT_ID"),
                new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")),
                new LabelRowMapper("GENERIC_").mapRow(rs, rowNum),
                new LabelRowMapper().mapRow(rs, rowNum),
                new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, rowNum), rs.getString("UNIT_CODE")),
                new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, rowNum)),
                new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TRACER_CATEGORY_").mapRow(rs, rowNum))
        );
        fuc.setCountOfPrograms(rs.getInt("COUNT_OF_PROGRAMS"));
        fuc.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return fuc;
    }

}
