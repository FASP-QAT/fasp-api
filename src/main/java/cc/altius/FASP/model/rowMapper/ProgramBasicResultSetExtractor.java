/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ProgramBasicResultSetExtractor implements ResultSetExtractor<Program> {

    @Override
    public Program extractData(ResultSet rs) throws SQLException, DataAccessException {
        Program p = new Program();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                p.setProgramId(rs.getInt("PROGRAM_ID"));
                p.setProgramCode(rs.getString("PROGRAM_CODE"));
                p.setRealmCountry(
                        new RealmCountry(
                                rs.getInt("REALM_COUNTRY_ID"),
                                new Country(rs.getInt("COUNTRY_ID"), rs.getString("COUNTRY_CODE"), new LabelRowMapper("COUNTRY_").mapRow(rs, 1)),
                                new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE"), rs.getInt("MIN_MOS_MIN_GAURDRAIL"), rs.getInt("MIN_MOS_MAX_GAURDRAIL"), rs.getInt("MAX_MOS_MAX_GAURDRAIL"), rs.getInt("MIN_QPL_TOLERANCE"), rs.getInt("MIN_QPL_TOLERANCE_CUT_OFF"), rs.getInt("MAX_QPL_TOLERANCE"), rs.getInt("ACTUAL_CONSUMPTION_MONTHS_IN_PAST"), rs.getInt("FORECAST_CONSUMPTION_MONTH_IN_PAST"), rs.getInt("INVENTORY_MONTHS_IN_PAST"))
                        )
                );
                p.getRealmCountry().getCountry().setCountryCode2(rs.getString("COUNTRY_CODE2"));
                p.setLabel(new LabelRowMapper().mapRow(rs, 1));
                p.setOrganisation(new SimpleCodeObject(rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, 1), rs.getString("ORGANISATION_CODE")));
                p.setProgramTypeId(rs.getInt("PROGRAM_TYPE_ID"));
                p.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
            }
            isFirst = false;
        }
        if (!isFirst) {
            return p;
        } else {
            return null;
        }
    }

}
