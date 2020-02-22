/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Region;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class RegionRowMapper implements RowMapper<Region> {
    
    @Override
    public Region mapRow(ResultSet rs, int i) throws SQLException {
        Region r = new Region();
        r.setRegionId(rs.getInt("REGION_ID"));
        return r;
    }
    
}
