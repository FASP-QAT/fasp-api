/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

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
                rs.getInt("PU_ID"),
                new LabelRowMapper("PU_").mapRow(rs, 1),
                rs.getDouble("MULTIPLIER"),
                new SimpleObject(rs.getInt("FU_ID"), new LabelRowMapper("FU_").mapRow(rs, 1))
        );
    }

}
