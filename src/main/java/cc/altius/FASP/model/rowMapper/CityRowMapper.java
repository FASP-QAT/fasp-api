/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.City;
import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class CityRowMapper implements RowMapper<City> {
    
    @Override
    public City mapRow(ResultSet rs, int i) throws SQLException {
        City c = new City();
        c.setCityId(rs.getInt("CITY_ID"));
        c.setCityName(rs.getString("CITY_NAME"));
        Country country = new Country();
        country.setCountryId(rs.getInt("COUNTRY_ID"));
        c.setCountry(country);
        State s = new State();
        s.setStateId(rs.getInt("STATE_ID"));
        c.setState(s);
        return c;
    }
    
}
