/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.UnitType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class UnitTypeRowMapper implements RowMapper<UnitType>{

    @Override
    public UnitType mapRow(ResultSet rs, int i) throws SQLException {
       UnitType ut = new UnitType();
       ut.setUnitTypeId(rs.getInt("UNIT_TYPE_ID"));
       ut.setLabel(new Label(rs.getInt("LABEL_ID"), rs.getString("LABEL_EN"), rs.getString("LABEL_SP"), rs.getString("LABEL_FR"), rs.getString("LABEL_PR")));
       ut.setCreatedDate(rs.getDate("CREATED_DATE"));
       ut.setLastModifiedDate(rs.getDate("LAST_MODIFIED_DATE"));
       return ut;
    }
    
}
