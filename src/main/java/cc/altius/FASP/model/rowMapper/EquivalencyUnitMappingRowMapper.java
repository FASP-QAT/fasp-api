/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.EquivalencyUnitMapping;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class EquivalencyUnitMappingRowMapper implements RowMapper<EquivalencyUnitMapping> {
    
    @Override
    public EquivalencyUnitMapping mapRow(ResultSet rs, int i) throws SQLException {
        EquivalencyUnitMapping eum = new EquivalencyUnitMapping();
        eum.setEquivalencyUnitMappingId(rs.getInt("EQUIVALENCY_UNIT_MAPPING_ID"));
        eum.setEquivalencyUnit(new EquivalencyUnitRowMapper("EU_").mapRow(rs, i));
        eum.setForecastingUnit(new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, i)));
        eum.setUnit(new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("U_").mapRow(rs, i), rs.getString("UNIT_CODE")));
        eum.setTracerCategory(new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, i)));
        int programId = rs.getInt("PROGRAM_ID");
        if (rs.wasNull()) {
            eum.setProgram(null);
        } else {
            eum.setProgram(new SimpleCodeObject(programId, new LabelRowMapper("P_").mapRow(rs, programId), rs.getString("PROGRAM_CODE")));
        }
        eum.setNotes(rs.getString("NOTES"));
        eum.setConvertToFu(rs.getDouble("CONVERT_TO_FU"));
        eum.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return eum;
    }
    
}
