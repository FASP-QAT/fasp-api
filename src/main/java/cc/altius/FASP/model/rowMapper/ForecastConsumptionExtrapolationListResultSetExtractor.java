/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ForecastConsumptionExtrapolation;
import cc.altius.FASP.model.ExtrapolationData;
import cc.altius.FASP.model.SimpleObject;
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
public class ForecastConsumptionExtrapolationListResultSetExtractor implements ResultSetExtractor<List<ForecastConsumptionExtrapolation>> {
    
    @Override
    public List<ForecastConsumptionExtrapolation> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ForecastConsumptionExtrapolation> fcesList = new LinkedList<>();
        while (rs.next()) {
            ForecastConsumptionExtrapolation fces = new ForecastConsumptionExtrapolation(rs.getInt("CONSUMPTION_EXTRAPOLATION_ID"));
            int idx = fcesList.indexOf(fces);
            if (idx == -1) {
                fcesList.add(fces);
                fces.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, 1)));
                fces.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("R_").mapRow(rs, 1)));
                fces.setExtrapolationMethod(new SimpleObject(rs.getInt("EXTRAPOLATION_METHOD_ID"), new LabelRowMapper("EM_").mapRow(rs, 1)));
                fces.setJsonProperties(rs.getString("JSON_PROPERTIES"));
                fces.setNotes(rs.getString("NOTES"));
                fces.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
                fces.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
            } else {
                fces = fcesList.get(idx);
            }
            ExtrapolationData fced = new ExtrapolationData();
            fced.setMonth(rs.getDate("MONTH"));
            if (!rs.wasNull()) {
                idx = fces.getExtrapolationDataList().indexOf(fced);
                if (idx == -1) {
                    fces.getExtrapolationDataList().add(fced);
                } else {
                    fced = fces.getExtrapolationDataList().get(idx);
                }
                fced.setAmount(rs.getDouble("AMOUNT"));
                if (rs.wasNull()) {
                    fced.setAmount(null);
                }
                fced.setCi(rs.getDouble("CI"));
                if (rs.wasNull()) {
                    fced.setCi(null);
                }
            }
        }
        return fcesList;
    }
    
}
