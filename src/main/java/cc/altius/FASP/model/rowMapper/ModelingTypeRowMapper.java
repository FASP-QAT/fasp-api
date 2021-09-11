/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ModelingType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ModelingTypeRowMapper implements RowMapper<ModelingType> {

    @Override
    public ModelingType mapRow(ResultSet rs, int i) throws SQLException {
        ModelingType up = new ModelingType(rs.getInt("MODELING_TYPE_ID"), new LabelRowMapper().mapRow(rs, i));
        up.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return up;
    }

}
