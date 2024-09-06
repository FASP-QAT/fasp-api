/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgentForecastingUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementAgentForecastingUnitRowMapper implements RowMapper<ProcurementAgentForecastingUnit> {

    @Override
    public ProcurementAgentForecastingUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcurementAgentForecastingUnit pafu = new ProcurementAgentForecastingUnit(
                rs.getInt("PROCUREMENT_AGENT_FORECASTING_UNIT_ID"),
                new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, rowNum), rs.getString("PROCUREMENT_AGENT_CODE")),
                new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, rowNum))
        );
        pafu.setSkuCode(rs.getString("SKU_CODE"));
        pafu.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
        return pafu;
    }
}
