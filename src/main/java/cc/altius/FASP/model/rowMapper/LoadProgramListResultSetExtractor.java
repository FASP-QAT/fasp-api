/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.SimpleCodeObject;
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
public class LoadProgramListResultSetExtractor implements ResultSetExtractor<List<LoadProgram>> {

    @Override
    public List<LoadProgram> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<LoadProgram> lpList = new LinkedList<>();
        while (rs.next()) {
            LoadProgram lp = new LoadProgram(
                    new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper().mapRow(rs, 1), rs.getString("PROGRAM_CODE")),
                    new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("REALM_COUNTRY_").mapRow(rs, 1), rs.getString("COUNTRY_CODE")),
                    new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")),
                    new SimpleCodeObject(rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, 1), rs.getString("ORGANISATION_CODE")),
                    rs.getInt("MAX_COUNT")
            );
            int idx = lpList.indexOf(lp);
            if (idx == -1) {
                lpList.add(lp);
            } else {
                lpList.get(idx).addHealthArea(new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")));
            }
        }
        return lpList;
    }
}
