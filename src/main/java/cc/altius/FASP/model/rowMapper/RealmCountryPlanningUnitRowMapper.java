/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Unit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class RealmCountryPlanningUnitRowMapper implements RowMapper<RealmCountryPlanningUnit> {

    @Override
    public RealmCountryPlanningUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        RealmCountryPlanningUnit rcpu = new RealmCountryPlanningUnit(
                rs.getInt("REALM_COUNTRY_PLANNING_UNIT_ID"),
                new SimpleObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("REALM_COUNTRY_").mapRow(rs, 0)),
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 0))
        );
        rcpu.setSkuCode(rs.getString("SKU_CODE"));
        rcpu.setLabel(new LabelRowMapper().mapRow(rs, 0));
        rcpu.setConversionMethod(rs.getInt("CONVERSION_METHOD"));
        rcpu.setConversionNumber(rs.getDouble("CONVERSION_NUMBER"));
        rcpu.setUnit(new Unit(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, 0), rs.getString("UNIT_CODE")));
        rcpu.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return rcpu;
    }

}
