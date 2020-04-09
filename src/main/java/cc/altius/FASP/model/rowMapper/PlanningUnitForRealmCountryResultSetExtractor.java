/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.PlanningUnitForRealmCountryMapping;
import cc.altius.FASP.model.Unit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class PlanningUnitForRealmCountryResultSetExtractor implements ResultSetExtractor<RealmCountryPlanningUnit> {

    @Override
    public RealmCountryPlanningUnit extractData(ResultSet rs) throws SQLException, DataAccessException {
        RealmCountryPlanningUnit rcpu = new RealmCountryPlanningUnit();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                rcpu.setRealmCountryId(rs.getInt("REALM_COUNTRY_ID"));
                rcpu.setLabel(new LabelRowMapper().mapRow(rs, 1));
                rcpu.setPlanningUnitList(new LinkedList<>());
            }
            if (rs.getInt("PLANNING_UNIT_ID") > 0) {
                PlanningUnitForRealmCountryMapping sp = new PlanningUnitForRealmCountryMapping();
                sp.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
                sp.setLabel(new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1));
                sp.setSkuCode(rs.getString("SKU_CODE"));
                sp.setCountrySku(new LabelRowMapper("COUNTRY_SKU_").mapRow(rs, 0));
                sp.setGtin(rs.getString("GTIN"));
                sp.setMultiplier(rs.getDouble("MULTIPLIER"));
                sp.setUnit(new Unit(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, 0), rs.getString("UNIT_CODE")));
                sp.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                if (rcpu.getPlanningUnitList().indexOf(sp) == -1) {
                    rcpu.getPlanningUnitList().add(sp);
                }
            }
            isFirst = false;
        }
        return rcpu;
    }
    
}
