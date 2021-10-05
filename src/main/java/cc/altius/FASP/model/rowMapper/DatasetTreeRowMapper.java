/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DatasetTree;
import cc.altius.FASP.model.SimpleObjectWithType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DatasetTreeRowMapper implements RowMapper<DatasetTree> {

    @Override
    public DatasetTree mapRow(ResultSet rs, int rowNum) throws SQLException {
        DatasetTree t = new DatasetTree();
        t.setTreeId(rs.getInt("TREE_ID"));
        t.setForecastMethod(new SimpleObjectWithType(rs.getInt("FORECAST_METHOD_ID"), new LabelRowMapper("FM_").mapRow(rs, 1), rs.getInt("FORECAST_METHOD_TYPE_ID")));
        t.setLabel(new LabelRowMapper().mapRow(rs, 1));
        return t;
    }
    
}
