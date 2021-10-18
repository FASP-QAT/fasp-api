/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DatasetTree;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithType;
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
            int idx = treeList.indexOf(t);
            if (idx == -1) {
                t.setForecastMethod(new SimpleObjectWithType(rs.getInt("FORECAST_METHOD_ID"), new LabelRowMapper("FM_").mapRow(rs, 1), rs.getInt("FORECAST_METHOD_TYPE_ID")));
                t.setLabel(new LabelRowMapper().mapRow(rs, 1));
                t.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                treeList.add(t);
            } else {
                t = treeList.get(idx);
            }
            SimpleObject scenario = new SimpleObject(rs.getInt("SCENARIO_ID"), new LabelRowMapper("S_").mapRow(rs, 1));
            idx = t.getScenarioList().indexOf(scenario);
            if (idx == -1) {
                t.getScenarioList().add(scenario);
            }
            SimpleObject region = new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REG_").mapRow(rs, 1));
            idx = t.getRegionList().indexOf(region);
            if (idx == -1) {
                t.getRegionList().add(region);
            }
        }
        return treeList;
    }

}
