/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ManualIntegration;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ManualIntegrationRowMapper implements RowMapper<ManualIntegration> {

    private final boolean extraFields;

    public ManualIntegrationRowMapper(boolean extraFields) {
        this.extraFields = extraFields;
    }

    @Override
    public ManualIntegration mapRow(ResultSet rs, int rowNum) throws SQLException {
        ManualIntegration mi = new ManualIntegration(
                rs.getInt("MANUAL_INTEGRATION_ID"),
                new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, rowNum), rs.getString("PROGRAM_CODE")),
                rs.getInt("VERSION_ID"),
                rs.getInt("INTEGRATION_ID"),
                rs.getString("INTEGRATION_NAME"),
                new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")),
                rs.getTimestamp("CREATED_DATE"),
                rs.getTimestamp("COMPLETED_DATE")
        );
        if (extraFields) {
            mi.setFileName(rs.getString("FILE_NAME"));
            mi.setFolderName(rs.getString("FOLDER_LOCATION"));
            mi.setIntegrationViewId(rs.getInt("INTEGRATION_VIEW_ID"));
            mi.setIntegrationViewName(rs.getString("INTEGRATION_VIEW_NAME"));
        }
        return mi;
    }

}
