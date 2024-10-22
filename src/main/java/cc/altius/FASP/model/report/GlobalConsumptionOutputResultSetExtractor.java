/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class GlobalConsumptionOutputResultSetExtractor implements ResultSetExtractor<List<GlobalConsumptionOutput>> {

    @Override
    public List<GlobalConsumptionOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {

        List<GlobalConsumptionOutput> gcoList = new LinkedList<>();
        while (rs.next()) {
            Date dt = rs.getDate("TRANS_DATE");
            GlobalConsumptionOutput gc = new GlobalConsumptionOutput();
            gc.setTransDate(dt);
            int idx = gcoList.indexOf(gc);
            if (idx == -1) {
                gcoList.add(gc);
            } else {
                gc = gcoList.get(idx);
            }
            gc.getCountryConsumption().put(rs.getString("COUNTRY_CODE"),
                    new CountryConsumptionData(
                            new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1), rs.getString("COUNTRY_CODE")),
                            rs.getDouble("FORECASTED_CONSUMPTION"),
                            rs.getDouble("ACTUAL_CONSUMPTION")
                    )
            );
        }

        return gcoList;
    }

}




