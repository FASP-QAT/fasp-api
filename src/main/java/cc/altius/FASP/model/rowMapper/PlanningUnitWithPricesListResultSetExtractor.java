/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.PlanningUnitWithPrices;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectPrice;
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
public class PlanningUnitWithPricesListResultSetExtractor implements ResultSetExtractor<List<PlanningUnitWithPrices>> {

    @Override
    public List<PlanningUnitWithPrices> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<PlanningUnitWithPrices> puList = new LinkedList<>();
        while (rs.next()) {
            PlanningUnitWithPrices pu = new PlanningUnitWithPrices();
            pu.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
            int idx = puList.indexOf(pu);
            if (idx == -1) {
                pu.setForecastingUnit(new ForecastingUnit(
                        rs.getInt("FORECASTING_UNIT_ID"),
                        new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")),
                        new LabelRowMapper("GENERIC_").mapRow(rs, 1),
                        new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, 1),
                        new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, 1)),
                        new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TRACER_CATEGORY_").mapRow(rs, 1))
                ));
                pu.setLabel(new LabelRowMapper().mapRow(rs, 1));
                pu.setUnit(new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("UNIT_").mapRow(rs, 1), rs.getString("UNIT_CODE")));
                pu.setMultiplier(rs.getDouble("MULTIPLIER"));
                pu.getForecastingUnit().setUnit(new SimpleCodeObject(rs.getInt("FU_UNIT_ID"), new LabelRowMapper("FU_UNIT_").mapRow(rs, 1), rs.getString("FU_UNIT_CODE")));
                pu.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                puList.add(pu);
            } else {
                pu = puList.get(idx);
            }
            pu.getProcurementAgentPriceList().add(new SimpleObjectPrice(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PA_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE"), rs.getDouble("CATALOG_PRICE")));
        }
        return puList;
    }

}
