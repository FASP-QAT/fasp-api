/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Unit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class UnitRowMapper implements RowMapper<Unit> {

    @Override
    public Unit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Unit u = new Unit();
        u.setUnitId(rs.getInt("UNIT_ID"));
        u.setUnitCode(rs.getString("UNIT_CODE"));
        u.setDimension(new SimpleObject(rs.getInt("DIMENSION_ID"),new LabelRowMapper("DIMENSION_").mapRow(rs, rowNum)));
        u.setLabel(new LabelRowMapper().mapRow(rs, rowNum));
        u.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return u;
    }

}
