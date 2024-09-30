/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DashboardQplRowMapper implements RowMapper<DashboardQpl> {

    @Override
    public DashboardQpl mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DashboardQpl(rs.getInt("PU_COUNT"), rs.getInt("GOOD_COUNT"));
    }

}
