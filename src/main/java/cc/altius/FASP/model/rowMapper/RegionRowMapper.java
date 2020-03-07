/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.RealmCountry;
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
        r.setLabel(new LabelRowMapper("RG_").mapRow(rs, 1));
        r.setRealmCountry(new RealmCountryRowMapper().mapRow(rs, i));
        BasicUser b = new BasicUser(rs.getString("LAST_MODIFIED_BY"));
        r.setCreatedBy(b);
        r.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        r.setActive(rs.getBoolean("ACTIVE"));
        return r;
    }
    
}
