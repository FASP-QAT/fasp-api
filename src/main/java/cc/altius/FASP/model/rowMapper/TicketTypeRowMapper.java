/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.TicketType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class TicketTypeRowMapper implements RowMapper<TicketType> {

    private String prefix;

    public TicketTypeRowMapper() {
        this.prefix = "";
    }

    public TicketTypeRowMapper(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public TicketType mapRow(ResultSet rs, int i) throws SQLException {
        TicketType ts = new TicketType();
        ts.setTicketTypeId(rs.getInt("TICKET_TYPE_ID"));
        ts.setTicketLevel(rs.getInt("TICKET_LEVEL"));
        ts.setLabel(new LabelRowMapper(prefix).mapRow(rs, i));
        return ts;
    }

}
