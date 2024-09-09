/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DashboardPuWithCountRowMapper implements RowMapper<DashboardPuWithCount> {

    @Override
    public DashboardPuWithCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DashboardPuWithCount(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("").mapRow(rs, rowNum)), rs.getInt("COUNT"));
    }

}
