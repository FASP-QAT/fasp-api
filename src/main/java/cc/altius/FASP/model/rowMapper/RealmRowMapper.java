/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class RealmRowMapper implements RowMapper<Realm> {

    @Override
    public Realm mapRow(ResultSet rs, int i) throws SQLException {
        Realm r = new Realm();
        r.setRealmId(rs.getInt("REALM_ID"));
        r.setRealmCode(rs.getString("REALM_CODE"));
        r.setDefaultRealm(rs.getBoolean("DEFAULT_REALM"));
        r.setMinMosMinGaurdrail(rs.getInt("MIN_MOS_MIN_GAURDRAIL"));
        r.setMinMosMaxGaurdrail(rs.getInt("MIN_MOS_MAX_GAURDRAIL"));
        r.setMaxMosMaxGaurdrail(rs.getInt("MAX_MOS_MAX_GAURDRAIL"));
        r.setMinQplTolerance(rs.getInt("MIN_QPL_TOLERANCE"));
        r.setMinQplToleranceCutOff(rs.getInt("MIN_QPL_TOLERANCE_CUT_OFF"));
        r.setMaxQplTolerance(rs.getInt("MAX_QPL_TOLERANCE"));
        r.setActualConsumptionMonthsInPast(rs.getInt("ACTUAL_CONSUMPTION_MONTHS_IN_PAST"));
        r.setForecastConsumptionMonthsInPast(rs.getInt("FORECAST_CONSUMPTION_MONTH_IN_PAST"));
        r.setInventoryMonthsInPast(rs.getInt("INVENTORY_MONTHS_IN_PAST"));
        r.setMinCountForMode(rs.getInt("MIN_COUNT_FOR_MODE"));
        r.setMinPercForMode(rs.getDouble("MIN_PERC_FOR_MODE"));
        r.setActive(rs.getBoolean("ACTIVE"));
        r.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
        r.setLabel(new LabelRowMapper().mapRow(rs, i));
        return r;
    }

}
