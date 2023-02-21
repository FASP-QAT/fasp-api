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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class EquivalencyUnitResultSetExtractor implements ResultSetExtractor<EquivalencyUnit> {

    private String prefix;

    public EquivalencyUnitResultSetExtractor(String prefix) {
        this.prefix = prefix;
    }

    public EquivalencyUnitResultSetExtractor() {
        prefix = "";
    }

    @Override
    public EquivalencyUnit extractData(ResultSet rs) throws SQLException, DataAccessException {
        boolean isFirst = true;
        EquivalencyUnit eq = new EquivalencyUnit();
        while (rs.next()) {
            if (isFirst) {
                eq = new EquivalencyUnit(
                        rs.getInt("EQUIVALENCY_UNIT_ID"),
                        new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")),
                        new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE")),
                        new LabelRowMapper().mapRow(rs, 1),
                        rs.getString("NOTES"));
                eq.setBaseModel(new BaseModelRowMapper(prefix).mapRow(rs, 1));
                isFirst = false;
            }
            eq.getHealthAreaList().add(new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")));
        }
        return eq;
    }

}
