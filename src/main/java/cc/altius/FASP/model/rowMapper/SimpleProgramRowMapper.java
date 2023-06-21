/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleProgram;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleProgramRowMapper implements RowMapper<SimpleProgram> {

    private final String prefix;

    public SimpleProgramRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public SimpleProgram mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SimpleProgram(rs.getInt(prefix + "ID"), new LabelRowMapper(prefix).mapRow(rs, rowNum), rs.getString(prefix + "CODE"), rs.getInt("CURRENT_VERSION_ID"));
    }

}
