/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DashboardTopRowMapper implements RowMapper<DashboardTop> {

    @Override
    public DashboardTop mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DashboardTop(
                new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("P_").mapRow(rs, rowNum), rs.getString("PROGRAM_CODE")),
                rs.getInt("ACTIVE_PPU"), rs.getInt("DISABLED_PPU"), rs.getDate("LAST_MODIFIED_DATE"), rs.getDate("COMMIT_DATE"),
                new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VT_").mapRow(rs, rowNum)),
                new SimpleObject(rs.getInt("VERSION_STATUS_ID"), new LabelRowMapper("VS_").mapRow(rs, rowNum)),
                rs.getInt("COUNT_OF_OPEN_PROBLEM"), rs.getInt("VERSION_ID"), new SimpleObject(rs.getInt("LATEST_FINAL_VERSION_STATUS_ID"), new LabelRowMapper("LATEST_FINAL_VS_").mapRow(rs, rowNum)), rs.getDate("LATEST_FINAL_VERSION_LAST_MODIFIED_DATE")
        );

    }

}
