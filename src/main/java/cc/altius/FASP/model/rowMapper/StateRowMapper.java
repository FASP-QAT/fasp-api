/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class StateRowMapper implements RowMapper<State> {
    
    @Override
    public State mapRow(ResultSet rs, int i) throws SQLException {
        State s = new State();
        s.setStateId(rs.getInt("STATE_ID"));
        s.setStateName(rs.getString("STATE_NAME"));
        Country c = new Country();
        c.setCountryId(rs.getInt("COUNTRY_ID"));
        s.setCountry(c);
        return s;
        
    }
    
}
