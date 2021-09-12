/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class EquivalencyUnitRowMapper implements RowMapper<EquivalencyUnit> {

    private String prefix;

    public EquivalencyUnitRowMapper(String prefix) {
        this.prefix = prefix;
    }

    public EquivalencyUnitRowMapper() {
        prefix = "";
    }

    @Override
    public EquivalencyUnit mapRow(ResultSet rs, int i) throws SQLException {
        EquivalencyUnit eq = new EquivalencyUnit(rs.getInt("EQUIVALENCY_UNIT_ID"), new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, i), rs.getString("REALM_CODE")), new LabelRowMapper().mapRow(rs, i));
        eq.setBaseModel(new BaseModelRowMapper(prefix).mapRow(rs, i));
        return eq;
    }

}
