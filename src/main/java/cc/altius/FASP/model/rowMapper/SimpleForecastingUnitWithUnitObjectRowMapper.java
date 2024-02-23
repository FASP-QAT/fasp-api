/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleForecastingUnitWithUnitObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleForecastingUnitWithUnitObjectRowMapper implements RowMapper<SimpleForecastingUnitWithUnitObject> {

    @Override
    public SimpleForecastingUnitWithUnitObject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SimpleForecastingUnitWithUnitObject(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, 1), new SimpleCodeObjectRowMapper("U_").mapRow(rs, rowNum));
    }

}
