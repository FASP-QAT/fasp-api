/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
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
public class ProgramPlanningUnitResultSetExtractor implements ResultSetExtractor<List<ProgramPlanningUnit>> {

    @Override
    public List<ProgramPlanningUnit> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ProgramPlanningUnit> ppuList = new LinkedList<>();
        while (rs.next()) {
            ProgramPlanningUnit ppu = new ProgramPlanningUnit(
                    rs.getInt("PROGRAM_PLANNING_UNIT_ID"),
                    new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1)),
                    new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1)),
                    rs.getDouble("MULTIPLIER"),
                    new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, 1)),
                    new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, 1)),
                    rs.getInt("REORDER_FREQUENCY_IN_MONTHS"),
                    rs.getDouble("LOCAL_PROCUREMENT_LEAD_TIME"),
                    rs.getInt("SHELF_LIFE"),
                    rs.getDouble("CATALOG_PRICE"),
                    rs.getInt("MONTHS_IN_PAST_FOR_AMC"),
                    rs.getInt("MONTHS_IN_FUTURE_FOR_AMC")
            );
            ppu.setPlanBasedOn(rs.getInt("PLAN_BASED_ON"));
            ppu.setMinMonthsOfStock(rs.getInt("MIN_MONTHS_OF_STOCK"));
            ppu.setMinQty(rs.getInt("MIN_QTY"));
            ppu.setDistributionLeadTime(rs.getDouble("DISTRIBUTION_LEAD_TIME"));
            ppu.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
            int idx = ppuList.indexOf(ppu);
            if (idx == -1) {
                ppuList.add(ppu);
            } else {
                ppu = ppuList.get(idx);
            }
            ProgramPlanningUnitProcurementAgentPrice ppupap = new ProgramPlanningUnitProcurementAgentPrice();
            ppupap.setProgramPlanningUnitProcurementAgentId(rs.getInt("PROGRAM_PLANNING_UNIT_PROCUREMENT_AGENT_ID"));
            if (!rs.wasNull()) {
                ppupap.setProgramPlanningUnitId(ppu.getProgramPlanningUnitId());
                ppupap.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE")));
                ppupap.setPrice(rs.getDouble("PROCUREMENT_AGENT_PRICE"));
                ppupap.setProgram(ppu.getProgram());
                ppupap.setPlanningUnit(ppu.getPlanningUnit());
                ppupap.setBaseModel(new BaseModelRowMapper("PPUPA_").mapRow(rs, 1));
                ppu.getProgramPlanningUnitProcurementAgentPrices().add(ppupap);
            }
        }
        return ppuList;
    }

}
