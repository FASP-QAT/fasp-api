package cc.altius.FASP.model.DTO;
import cc.altius.FASP.model.Ticket;
import cc.altius.FASP.model.rowMapper.BaseModelRowMapper;
import cc.altius.FASP.model.rowMapper.TicketStatusRowMapper;
import cc.altius.FASP.model.rowMapper.TicketTypeRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author palash
 */
public class TicketRowMapper implements RowMapper<Ticket>{

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ticket t = new Ticket();
        t.setTicketId(rs.getInt("TICKET_ID"));
        t.setRefferenceId(rs.getInt("REFFERENCE_ID"));
        t.setNote(rs.getString("NOTES"));
        t.setTicketStatus(new TicketStatusRowMapper("TICKET_STATUS_").mapRow(rs, rowNum));
        t.setTicketType(new TicketTypeRowMapper("TICKET_TYPE_").mapRow(rs, rowNum));
//        t.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return t;
    }
    
}
