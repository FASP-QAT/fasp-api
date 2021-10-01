/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObjectWithType;
import cc.altius.FASP.model.TreeTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class TreeTemplateRowMapper implements RowMapper<TreeTemplate> {

    @Override
    public TreeTemplate mapRow(ResultSet rs, int i) throws SQLException {
        TreeTemplate tt = new TreeTemplate(rs.getInt("TREE_TEMPLATE_ID"));
        // Basic
        tt.setLabel(new LabelRowMapper().mapRow(rs, 1));
        tt.setRealm(new SimpleCodeObject(rs.getInt("REALM_ID"), new LabelRowMapper("R_").mapRow(rs, 1), rs.getString("REALM_CODE")));
        tt.setForecastMethod(new SimpleObjectWithType(rs.getInt("FORECAST_METHOD_ID"), new LabelRowMapper("FM_").mapRow(rs, 1), rs.getInt("FORECAST_METHOD_TYPE_ID")));
        tt.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
        return tt;
    }

}
