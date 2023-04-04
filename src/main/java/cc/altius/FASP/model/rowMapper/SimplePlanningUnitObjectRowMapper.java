/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleForecastingUnitObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitObjectRowMapper implements RowMapper<SimplePlanningUnitObject> {

    @Override
    public SimplePlanningUnitObject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SimplePlanningUnitObject(
                new SimpleCodeObject(rs.getInt("PU_UNIT_ID"), new LabelRowMapper("PUU_").mapRow(rs, 1), rs.getString("PU_UNIT_CODE")),
                rs.getInt("PU_ID"),
                new LabelRowMapper("PU_").mapRow(rs, 1),
                rs.getDouble("MULTIPLIER"),
                new SimpleForecastingUnitObject(
                        new SimpleCodeObject(rs.getInt("FU_UNIT_ID"), new LabelRowMapper("FUU_").mapRow(rs, 1), rs.getString("FU_UNIT_CODE")),
                        rs.getInt("FU_ID"), new LabelRowMapper("FU_").mapRow(rs, 1),
                        new SimpleObject(rs.getInt("TC_ID"), new LabelRowMapper("TC_").mapRow(rs, 1)),
                        new SimpleObject(rs.getInt("PC_ID"), new LabelRowMapper("PC_").mapRow(rs, 1))
                )
        );
    }

}
