/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleObjectRowMapper implements RowMapper<SimpleObject> {

    private final String prefix;

    public SimpleObjectRowMapper() {
        this.prefix = "";
    }

    public SimpleObjectRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public SimpleObject mapRow(ResultSet rs, int i) throws SQLException {
        return new SimpleObject(rs.getInt(prefix + "ID"), new LabelRowMapper(prefix).mapRow(rs, i));
    }

}
