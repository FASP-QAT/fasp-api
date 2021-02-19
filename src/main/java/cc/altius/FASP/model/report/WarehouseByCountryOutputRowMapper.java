/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleRealmCountryObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class WarehouseByCountryOutputRowMapper implements RowMapper<WarehouseByCountryOutput> {

    @Override
    public WarehouseByCountryOutput mapRow(ResultSet rs, int i) throws SQLException {
        WarehouseByCountryOutput wc = new WarehouseByCountryOutput();
        wc.setRealmCountry(new SimpleRealmCountryObject(rs.getInt("REALM_COUNTRY_ID"), rs.getString("COUNTRY_CODE"), rs.getString("COUNTRY_CODE2"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1)));
        wc.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 1)));
        wc.setGln(rs.getString("GLN"));
        wc.setCapacityCbm(rs.getDouble("CAPACITY_CBM"));
        if (rs.wasNull()) {
            wc.setCapacityCbm(null);
        }
        wc.setActive(rs.getBoolean("ACTIVE"));
        return wc;
    }

}
