/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObjectWithType;
import cc.altius.FASP.model.TreeLevel;
import cc.altius.FASP.model.TreeTemplate;
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
public class TreeTemplateListResultSetExtractor implements ResultSetExtractor<List<TreeTemplate>> {

    @Override
    public List<TreeTemplate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<TreeTemplate> ttList = new LinkedList<>();
        while (rs.next()) {
            TreeTemplate tt = new TreeTemplate(rs.getInt("TREE_TEMPLATE_ID"));
            int indx = ttList.indexOf(tt);
            if (indx == -1) {
                // Basic
                tt.setLabel(new LabelRowMapper().mapRow(rs, 1));
                tt.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("R_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                tt.setForecastMethod(new SimpleObjectWithType(rs.getInt("FORECAST_METHOD_ID"), new LabelRowMapper("FM_").mapRow(rs, 1), rs.getInt("FORECAST_METHOD_TYPE_ID")));
                tt.setMonthsInPast(rs.getInt("MONTHS_IN_PAST"));
                if (rs.wasNull()) {
                    tt.setMonthsInPast(null);
                }
                tt.setMonthsInFuture(rs.getInt("MONTHS_IN_FUTURE"));
                if (rs.wasNull()) {
                    tt.setMonthsInFuture(null);
                }
                tt.setNotes(rs.getString("NOTES"));
                tt.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                ttList.add(tt);
            } else {
                tt = ttList.get(indx);
            }
            TreeLevel level = new TreeLevel();
            level.setLevelId(rs.getInt("LEVEL_ID"));
            if (!rs.wasNull()) {
                level.setLevelNo(rs.getInt("LEVEL_NO"));
                int idx = tt.getLevelList().indexOf(level);
                if (idx == -1) {
                    tt.getLevelList().add(level);
                } else {
                    level = tt.getLevelList().get(idx);
                }
                level.setLabel(new LabelRowMapper("TL_").mapRow(rs, 1));
                if (level.getLabel().getLabelId() == null || level.getLabel().getLabelId() == 0) {
                    level.setLabel(null);
                }
                SimpleCodeObject unit = new SimpleCodeObject(rs.getInt("UNIT_ID"), new LabelRowMapper("U_").mapRow(rs, 1), rs.getString("UNIT_CODE"));
                if (unit.getId() == null || unit.getId() == 0) {
                    unit = null;
                }
                level.setUnit(unit);
            }
        }
        return ttList;
    }
}
