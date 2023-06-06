/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DatasetTree;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithType;
import cc.altius.FASP.model.TreeLevel;
import cc.altius.FASP.model.TreeScenario;
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
public class DatasetTreeResultSetExtractor implements ResultSetExtractor<List<DatasetTree>> {

    @Override
    public List<DatasetTree> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<DatasetTree> treeList = new LinkedList<>();
        while (rs.next()) {
            DatasetTree t = new DatasetTree();
            t.setTreeId(rs.getInt("TREE_ID"));
            t.setNotes(rs.getString("NOTES"));
            int idx = treeList.indexOf(t);
            if (idx == -1) {
                t.setForecastMethod(new SimpleObjectWithType(rs.getInt("FORECAST_METHOD_ID"), new LabelRowMapper("FM_").mapRow(rs, 1), rs.getInt("FORECAST_METHOD_TYPE_ID")));
                t.setLabel(new LabelRowMapper().mapRow(rs, 1));
                t.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                treeList.add(t);
            } else {
                t = treeList.get(idx);
            }
            TreeLevel level = new TreeLevel();
            level.setLevelId(rs.getInt("LEVEL_ID"));
            if (!rs.wasNull()) {
                level.setLevelNo(rs.getInt("LEVEL_NO"));
                idx = t.getLevelList().indexOf(level);
                if (idx == -1) {
                    t.getLevelList().add(level);
                } else {
                    level = t.getLevelList().get(idx);
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
            TreeScenario scenario = new TreeScenario(rs.getInt("SCENARIO_ID"), new LabelRowMapper("S_").mapRow(rs, 1), rs.getString("S_NOTES"), rs.getBoolean("S_ACTIVE"));
            idx = t.getScenarioList().indexOf(scenario);
            if (idx == -1) {
                t.getScenarioList().add(scenario);
            }
            SimpleObject region = new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REG_").mapRow(rs, 1));
            idx = t.getRegionList().indexOf(region);
            if (idx == -1) {
                t.getRegionList().add(region);
            }
            t.setBaseModel(new BaseModelRowMapper("").mapRow(rs, 1));
        }
        return treeList;
    }

}
