/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.TicketStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class TicketStatusRowMapper implements RowMapper<TicketStatus> {

    private String prefix;

    public TicketStatusRowMapper() {
        this.prefix = "";
    }

    public TicketStatusRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public TicketStatus mapRow(ResultSet rs, int i) throws SQLException {
        TicketStatus ts = new TicketStatus();
        ts.setTicketStatusId(rs.getInt("TICKET_STATUS_ID"));
        ts.setLabel(new LabelRowMapper(prefix).mapRow(rs, i));
        return ts;
    }

}
