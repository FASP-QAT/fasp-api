/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class NotificationSummaryDTORowMapper implements RowMapper<NotificationSummaryDTO> {
    
    @Override
    public NotificationSummaryDTO mapRow(ResultSet rs, int i) throws SQLException {
        NotificationSummaryDTO n = new NotificationSummaryDTO();
        n.setProgramId(rs.getInt(""));
        n.setNotificationCount(rs.getInt(""));
        n.setLabel(new LabelRowMapper("").mapRow(rs, i));
        return n;
    }
    
}
