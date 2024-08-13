/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObjectWithFu;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleObjectWithFutRowMapper implements RowMapper<SimpleObjectWithFu> {

    @Override
    public SimpleObjectWithFu mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SimpleObjectWithFu(
                rs.getInt("ID"),
                new LabelRowMapper().mapRow(rs, rowNum),
                rs.getInt("FORECASTING_UNIT_ID")
        );
    }

}
