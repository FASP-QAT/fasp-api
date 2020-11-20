/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.TicketDao;
import cc.altius.FASP.model.DTO.TicketRowMapper;
import cc.altius.FASP.model.Ticket;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author palash
 */
@Repository
public class TicketDaoImpl implements TicketDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Ticket> getTicketList() {
        String sql = "SELECT tt.*,ttt.`ACTIVE`,ttt.`LABEL_ID` `TICKET_TYPE_LABEL_ID`,al.`LABEL_EN` `TICKET_TYPE_LABEL_EN`,al.`LABEL_FR` `TICKET_TYPE_LABEL_FR`,al.`LABEL_SP` `TICKET_TYPE_LABEL_SP`,al.`LABEL_PR` `TICKET_TYPE_LABEL_PR`,"
                + "ttt.`TICKET_LEVEL`, "
                + "tts.`TICKET_STATUS_ID`,tts.`LABEL_ID` `TICKET_STATUS_LABEL_ID`,alTwo.`LABEL_EN` `TICKET_STATUS_LABEL_EN`,alTwo.`LABEL_FR` `TICKET_STATUS_LABEL_FR`,alTwo.`LABEL_SP` `TICKET_STATUS_LABEL_SP`,alTwo.`LABEL_PR` `TICKET_STATUS_LABEL_PR`,"
                + "ttt.`TICKET_LEVEL` "
                + "FROM tk_ticket tt  "
                + "LEFT JOIN tk_ticket_type ttt ON ttt.`TICKET_TYPE_ID`=tt.`TICKET_TYPE_ID` "
                + "LEFT JOIN ap_label al ON al.`LABEL_ID`=ttt.`LABEL_ID` "
                + "LEFT JOIN tk_ticket_status tts ON tts.`TICKET_STATUS_ID`=tt.`TICKET_STATUS_ID` "
                + "LEFT JOIN ap_label alTwo ON alTwo.`LABEL_ID`=tts.`LABEL_ID`;";
        return this.jdbcTemplate.query(sql, new TicketRowMapper());
    }

    @Override
    public int updateTicket(Ticket ticket, int curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("ticketId", ticket.getTicketId());
        params.put("reffrenceId", ticket.getRefferenceId());
        params.put("note", ticket.getNote());
        params.put("statusId", ticket.getTicketStatus().getTicketStatusId());
        params.put("curUser", curUser);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        return nm.update("UPDATE tk_ticket tt SET tt.`NOTES`=:note ,tt.`TICKET_STATUS_ID`=:statusId,tt.`LAST_MODIFIED_BY`=:curUser,tt.`LAST_MODIFIED_DATE`=:curDate  "
                + " WHERE tt.`TICKET_ID`=:ticketId AND tt.`REFFERENCE_ID`=:reffrenceId", params);
    }

}
