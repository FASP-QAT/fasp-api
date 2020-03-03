/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class LabelsRowMapper implements RowMapper<Label>{

    @Override
    public Label mapRow(ResultSet rs, int i) throws SQLException {
        Label l= new Label(rs.getInt("LABEL_ID"),rs.getString("LABEL_EN"),rs.getString("LABEL_SP"),rs.getString("LABEL_FR"),rs.getString("LABEL_PR"));
//        l.setActive(rs.getBoolean("ACTIVE"));
        l.setCreatedDate(rs.getDate("CREATED_DATE"));
        l.setLastModifiedDate(rs.getDate("LAST_MODIFIED_DATE"));
        return l;
    }
    
}
