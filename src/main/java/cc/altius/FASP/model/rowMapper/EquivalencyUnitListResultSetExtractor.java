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
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class EquivalencyUnitListResultSetExtractor implements ResultSetExtractor<List<EquivalencyUnit>> {

    private String prefix;

    public EquivalencyUnitListResultSetExtractor(String prefix) {
        this.prefix = prefix;
    }

    public EquivalencyUnitListResultSetExtractor() {
        prefix = "";
    }

    @Override
    public List<EquivalencyUnit> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<EquivalencyUnit> eqList = new LinkedList<>();
        while (rs.next()) {
            EquivalencyUnit eq = new EquivalencyUnit(
                    rs.getInt("EQUIVALENCY_UNIT_ID"),
                    new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")),
                    new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE")),
                    new LabelRowMapper().mapRow(rs, 1),
                    rs.getString("NOTES"));
            int idx = eqList.indexOf(eq);
            if (idx == -1) {
                // Equivalency Unit is not found
                eqList.add(eq);
                eq.setBaseModel(new BaseModelRowMapper(prefix).mapRow(rs, 1));
            } else {
                eq = eqList.get(idx);
            }
            eq.getHealthAreaList().add(new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")));
        }
        return eqList;
    }

}
