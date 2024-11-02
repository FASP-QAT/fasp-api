/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentGlobalDemandCountrySplitRowMapper implements RowMapper<ShipmentGlobalDemandCountrySplit> {
    
    @Override
    public ShipmentGlobalDemandCountrySplit mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentGlobalDemandCountrySplit sgd = new ShipmentGlobalDemandCountrySplit();
        sgd.setCountry(new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")));
        sgd.setAmount(new HashMap<>());
        ResultSetMetaData md = rs.getMetaData();
        for (int x = 1; x <= md.getColumnCount(); x++) {
            String colName = md.getColumnName(x);
            if (colName.startsWith("FSPA_")) {
                sgd.getAmount().put(colName.substring(5), rs.getDouble(x));
            }
        }
        return sgd;
    }
    
}
