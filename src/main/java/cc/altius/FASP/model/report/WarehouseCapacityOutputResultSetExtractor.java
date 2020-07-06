/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleRealmCountryObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class WarehouseCapacityOutputResultSetExtractor implements ResultSetExtractor<List<WarehouseCapacityOutput>> {

    @Override
    public List<WarehouseCapacityOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<WarehouseCapacityOutput> wcoList = new LinkedList<>();
        while (rs.next()) {
            WarehouseCapacityOutput wco = new WarehouseCapacityOutput();
            wco.setRealmCountry(new SimpleRealmCountryObject(rs.getInt("REALM_COUNTRY_ID"), rs.getString("COUNTRY_CODE"), rs.getString("COUNTRY_CODE2"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1)));
            wco.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REGION_").mapRow(rs, 1)));
            if (wcoList.indexOf(wco) == -1) {
                wco.setGln(rs.getString("GLN"));
                wco.setCapacityCbm(rs.getDouble("CAPACITY_CBM"));
                if (rs.wasNull()) {
                    wco.setCapacityCbm(null);
                }
                wcoList.add(wco);
            }
            SimpleCodeObject program = new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE"));
            wcoList.get(wcoList.indexOf(wco)).getProgramList().add(program);
        }
        return wcoList;
    }

}
