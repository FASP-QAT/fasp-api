/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.Version;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class MultipleProgramVersionDropDownResultSetExtractor implements ResultSetExtractor<Map<Integer, List<Version>>> {

    @Override
    public Map<Integer, List<Version>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, List<Version>> pMap = new HashMap<>();
        while (rs.next()) {
//            SimpleProgram p = new SimpleProgram(rs.getInt("ID"), new LabelRowMapper("").mapRow(rs, 1), rs.getString("CODE"), rs.getInt("CURRENT_VERSION_ID"));
            int pId = rs.getInt("ID");
            List<Version> vList = pMap.get(pId);
            if (vList == null) {
                vList = new LinkedList<>();
                Version v = new Version();
                v.setVersionId(rs.getInt("VERSION_ID"));
                v.setVersionType(new SimpleObjectRowMapper("VERSION_TYPE_").mapRow(rs, 1));
                v.setVersionStatus(new SimpleObjectRowMapper("VERSION_STATUS_").mapRow(rs, 1));
                v.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                v.setForecastStartDate(rs.getDate("FORECAST_START_DATE"));
                v.setForecastStopDate(rs.getDate("FORECAST_STOP_DATE"));
                vList.add(v);
                pMap.putIfAbsent(pId, vList);
            } else {
                Version v = new Version();
                v.setVersionId(rs.getInt("VERSION_ID"));
                int idx = vList.indexOf(v);
                if (idx == -1) {
                    v.setVersionType(new SimpleObjectRowMapper("VERSION_TYPE_").mapRow(rs, 1));
                    v.setVersionStatus(new SimpleObjectRowMapper("VERSION_STATUS_").mapRow(rs, 1));
                    v.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                    v.setForecastStartDate(rs.getDate("FORECAST_START_DATE"));
                    v.setForecastStopDate(rs.getDate("FORECAST_STOP_DATE"));
                    vList.add(v);
                    pMap.putIfAbsent(pId, vList);
                }
            }
        }
        return pMap;
    }

}
