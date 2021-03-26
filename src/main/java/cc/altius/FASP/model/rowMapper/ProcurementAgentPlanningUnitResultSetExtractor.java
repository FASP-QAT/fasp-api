/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentPlanningUnitProgramPrice;
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
public class ProcurementAgentPlanningUnitResultSetExtractor implements ResultSetExtractor<List<ProcurementAgentPlanningUnit>> {

    @Override
    public List<ProcurementAgentPlanningUnit> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ProcurementAgentPlanningUnit> papuList = new LinkedList<>();
        while (rs.next()) {
            ProcurementAgentPlanningUnit papu = new ProcurementAgentPlanningUnit(
                    rs.getInt("PROCUREMENT_AGENT_PLANNING_UNIT_ID"),
                    new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE")),
                    new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1))
            );
            int idx = papuList.indexOf(papu);
            if (idx == -1) {
                papu.setSkuCode(rs.getString("SKU_CODE"));
                papu.setCatalogPrice(rs.getDouble("CATALOG_PRICE"));
                if (rs.wasNull()) {
                    papu.setCatalogPrice(null);
                }
                papu.setMoq(rs.getInt("MOQ"));
                if (rs.wasNull()) {
                    papu.setMoq(null);
                }
                papu.setUnitsPerPalletEuro1(rs.getInt("UNITS_PER_PALLET_EURO1"));
                if (rs.wasNull()) {
                    papu.setUnitsPerPalletEuro1(null);
                }
                papu.setUnitsPerPalletEuro2(rs.getInt("UNITS_PER_PALLET_EURO2"));
                if (rs.wasNull()) {
                    papu.setUnitsPerPalletEuro2(null);
                }
                papu.setUnitsPerContainer(rs.getInt("UNITS_PER_CONTAINER"));
                if (rs.wasNull()) {
                    papu.setUnitsPerContainer(null);
                }
                papu.setVolume(rs.getDouble("VOLUME"));
                if (rs.wasNull()) {
                    papu.setVolume(null);
                }
                papu.setWeight(rs.getDouble("WEIGHT"));
                if (rs.wasNull()) {
                    papu.setWeight(null);
                }
                papu.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                papuList.add(papu);
            } else {
                papu = papuList.get(idx);
            }
            SimpleObject program = new SimpleObject();
            program.setId(rs.getInt("PROGRAM_ID"));
            if (!rs.wasNull()) {
                program.setLabel(new LabelRowMapper("PROGRAM_").mapRow(rs, 1));
                ProcurementAgentPlanningUnitProgramPrice papupp = new ProcurementAgentPlanningUnitProgramPrice();
                papupp.setProcurementAgentPlanningUnitProgramId(rs.getInt("PROCUREMENT_AGENT_PLANNING_UNIT_PROGRAM_ID"));
                papupp.setProcurementAgentPlanningUnitId(papu.getProcurementAgentPlanningUnitId());
                papupp.setProcurementAgent(papu.getProcurementAgent());
                papupp.setPlanningUnit(papu.getPlanningUnit());
                papupp.setProgram(program);
                papupp.setPrice(rs.getDouble("PROGRAM_PRICE"));
                papupp.setBaseModel(new BaseModelRowMapper("PAPUP_").mapRow(rs, 1));
                papu.getProgramPrice().add(papupp);
            }
        }
        return papuList;
    }
}
