/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.EquivalencyUnitMapping;
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
public class EquivalencyUnitMappingResultSetExtractor implements ResultSetExtractor<List<EquivalencyUnitMapping>> {

    @Override
    public List<EquivalencyUnitMapping> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<EquivalencyUnitMapping> eqMappingList = new LinkedList<>();
        while (rs.next()) {
            EquivalencyUnitMapping eum = new EquivalencyUnitMapping();
            eum.setEquivalencyUnitMappingId(rs.getInt("EQUIVALENCY_UNIT_MAPPING_ID"));
            int idx = eqMappingList.indexOf(eum);
            if (idx == -1) {
                eum.setEquivalencyUnit(new EquivalencyUnit(
                        rs.getInt("EQUIVALENCY_UNIT_ID"),
                        new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")),
                        new LabelRowMapper().mapRow(rs, 1)));
                eum.setForecastingUnit(new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, 1)));
                eum.setUnit(new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("U_").mapRow(rs, 1), rs.getString("UNIT_CODE")));
                eum.setTracerCategory(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, 1)));
                int programId = rs.getInt("PROGRAM_ID");
                if (rs.wasNull()) {
                    eum.setProgram(null);
                } else {
                    eum.setProgram(new SimpleCodeObject(programId, new LabelRowMapper("P_").mapRow(rs, programId), rs.getString("PROGRAM_CODE")));
                }
                eum.setNotes(rs.getString("NOTES"));
                eum.setConvertToEu(rs.getDouble("CONVERT_TO_EU"));
                eum.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                eqMappingList.add(eum);
            } else {
                eum = eqMappingList.get(idx);
            }
            eum.getEquivalencyUnit().getHealthAreaList().add(new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HA_").mapRow(rs, 1), rs.getString("HEALTH_AREA_CODE")));
        }
        return eqMappingList;
    }

}
