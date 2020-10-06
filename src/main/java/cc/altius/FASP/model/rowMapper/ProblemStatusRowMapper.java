/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProblemStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProblemStatusRowMapper implements RowMapper<ProblemStatus> {

    @Override
    public ProblemStatus mapRow(ResultSet rs, int i) throws SQLException {
        return new ProblemStatus(rs.getBoolean("USER_MANAGED"), rs.getInt("ID"), new LabelRowMapper().mapRow(rs, i));
    }

}
