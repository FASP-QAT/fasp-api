/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleFundingSourceObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleFundingSourceObjectRowMapper implements RowMapper<SimpleFundingSourceObject> {

    @Override
    public SimpleFundingSourceObject mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SimpleFundingSourceObject(rs.getInt("ID"), new LabelRowMapper().mapRow(rs, rowNum), rs.getString("CODE"), new SimpleCodeObjectRowMapper("FST_").mapRow(rs, rowNum));
    }

}