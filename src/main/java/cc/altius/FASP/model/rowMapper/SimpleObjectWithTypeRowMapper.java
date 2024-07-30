/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObjectWithType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleObjectWithTypeRowMapper implements RowMapper<SimpleObjectWithType> {

    @Override
    public SimpleObjectWithType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SimpleObjectWithType(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, rowNum), rs.getInt("TYPE_ID"));
    }

}