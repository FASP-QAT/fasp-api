/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.UsagePeriod;
import cc.altius.FASP.model.UsageTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class UsageTemplateRowMapper implements RowMapper<UsageTemplate> {

    @Override
    public UsageTemplate mapRow(ResultSet rs, int i) throws SQLException {
        UsageTemplate ut = new UsageTemplate();
        ut.setUsageTemplateId(rs.getInt("USAGE_TEMPLATE_ID"));
        ut.setLabel(new LabelRowMapper().mapRow(rs, i));
        ut.setRealmId(rs.getInt("REALM_ID"));
        SimpleCodeObject p = new SimpleCodeObject();
        p.setId(rs.getInt("PROGRAM_ID"));
        if (rs.wasNull()) {
            ut.setProgram(null);
        } else {
            p.setLabel(new LabelRowMapper("PROGRAM_").mapRow(rs, i));
            p.setCode(rs.getString("PROGRAM_CODE"));
            ut.setProgram(p);
        }
        ut.setForecastingUnit(new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, i)));
        ut.setUnit(new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("U_").mapRow(rs, i), rs.getString("UNIT_CODE")));
        ut.setTracerCategory(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, i)));
        ut.setLagInMonths(rs.getInt("LAG_IN_MONTHS"));
        ut.setUsageType(new SimpleObject(rs.getInt("USAGE_TYPE_ID"), new LabelRowMapper("UT_").mapRow(rs, i)));
        ut.setNoOfPatients(rs.getInt("NO_OF_PATIENTS"));
        ut.setNoOfForecastingUnits(rs.getInt("NO_OF_FORECASTING_UNITS"));
        ut.setOneTimeUsage(rs.getBoolean("ONE_TIME_USAGE"));
        UsagePeriod up = new UsagePeriod();
        up.setUsagePeriodId(rs.getInt("UF_USAGE_PERIOD_ID"));
        if (rs.wasNull()) {
            ut.setUsageFrequencyUsagePeriod(null);
            ut.setUsageFrequencyCount(null);
        } else {
            up.setLabel(new LabelRowMapper("UF_").mapRow(rs, i));
            up.setConvertToMonth(rs.getDouble("UF_CONVERT_TO_MONTH"));
            ut.setUsageFrequencyUsagePeriod(up);
            ut.setUsageFrequencyCount(rs.getDouble("USAGE_FREQUENCY_COUNT"));
        }
        up = new UsagePeriod();
        up.setUsagePeriodId(rs.getInt("R_USAGE_PERIOD_ID"));
        if (rs.wasNull()) {
            ut.setRepeatUsagePeriod(null);
            ut.setRepeatCount(null);
        } else {
            up.setLabel(new LabelRowMapper("R_").mapRow(rs, i));
            up.setConvertToMonth(rs.getDouble("R_CONVERT_TO_MONTH"));
            ut.setRepeatUsagePeriod(up);
            ut.setRepeatCount(rs.getDouble("REPEAT_COUNT"));
        }
        ut.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return ut;
    }

}
