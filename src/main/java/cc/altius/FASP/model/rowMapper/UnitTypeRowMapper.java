/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.UnitType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class UnitTypeRowMapper implements RowMapper<UnitType> {

    private String prefix;

    public UnitTypeRowMapper() {
        this.prefix = "";
    }

    public UnitTypeRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public UnitType mapRow(ResultSet rs, int i) throws SQLException {
        UnitType ut = new UnitType();
        ut.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));
        ut.setLabel(new LabelRowMapper(prefix).mapRow(rs, i));
        return ut;
    }

}

