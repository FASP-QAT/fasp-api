/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ForecastConsumptionExtrapolationSettingsListResultSetExtractor implements ResultSetExtractor<List<ForecastConsumptionExtrapolationSettings>> {

    @Override
    public List<ForecastConsumptionExtrapolationSettings> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ForecastConsumptionExtrapolationSettings> fcesList = new LinkedList<>();
        while (rs.next()) {
            ForecastConsumptionExtrapolationSettings fces = new ForecastConsumptionExtrapolationSettings();
            fces.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, 1)));
            fces.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("R_").mapRow(rs, 1)));
            int idx = fcesList.indexOf(fces);
            if (idx == -1) {
                fcesList.add(fces);
            } else {
                fces = fcesList.get(idx);
            }
            SimpleObject extrapolationMethod = new SimpleObject(rs.getInt("EXTRAPOLATION_METHOD_ID"), new LabelRowMapper("EM_").mapRow(rs, 1));
            if (fces.getExtrapolationProperties() == null) {
                fces.setExtrapolationProperties(new HashMap<SimpleObject, String>());
            }
            if (fces.getExtrapolationProperties().get(extrapolationMethod) == null) {
                fces.getExtrapolationProperties().put(extrapolationMethod, rs.getString("JSON_PROPERTIES"));
            }
        }
        return fcesList;
    }

}
