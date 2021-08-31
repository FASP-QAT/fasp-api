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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class LoadProgramResultSetExtractor implements ResultSetExtractor<LoadProgram> {

    @Override
    public LoadProgram extractData(ResultSet rs) throws SQLException, DataAccessException {
        boolean isFirst = true;
        LoadProgram lp = null;
        while (rs.next()) {
            if (isFirst) {
                lp = new LoadProgram(
                        new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper().mapRow(rs, 1), rs.getString("PROGRAM_CODE")),
                        new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("REALM_COUNTRY_").mapRow(rs, 1), rs.getString("COUNTRY_CODE")),
                        new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")),
                        new SimpleCodeObject(rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, 1), rs.getString("ORGANISATION_CODE")),
                        rs.getInt("MAX_COUNT")
                );
            } else {
                lp.addHealthArea(new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")));
            }
        }
        return lp;
    }
}
