/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.TicketStatusDao;
import cc.altius.FASP.model.TicketStatus;
import cc.altius.FASP.model.rowMapper.TicketStatusRowMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author palash
 */
@Repository
public class TicketStatusDaoImpl implements TicketStatusDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<TicketStatus> getTicketStatusList() {
        String sql = "SELECT ts.*,l.* FROM tk_ticket_status ts LEFT JOIN ap_label l ON l.`LABEL_ID`=ts.`LABEL_ID`;";
        return this.jdbcTemplate.query(sql, new TicketStatusRowMapper());
    }

}
