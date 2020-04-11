/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class DimensionRowMapper implements RowMapper<Dimension> {

    private String prefix;

    public DimensionRowMapper() {
        this.prefix = "";
    }

    public DimensionRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Dimension mapRow(ResultSet rs, int i) throws SQLException {
        Dimension ut = new Dimension();
        ut.setDimensionId(rs.getInt("DIMENSION_ID"));
        ut.setLabel(new LabelRowMapper(prefix).mapRow(rs, i));
        ut.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return ut;
    }

}

