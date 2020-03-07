/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.TicketTypeDao;
import cc.altius.FASP.model.TicketType;
import cc.altius.FASP.model.rowMapper.TicketTypeRowMapper;
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
public class TicketTypeDaoImpl implements TicketTypeDao {


    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<TicketType> getTicketTypeList() {
        String sql = "SELECT tt.*,l.* FROM tk_ticket_type tt LEFT JOIN ap_label l ON l.`LABEL_ID`=tt.`LABEL_ID`;";
        return this.jdbcTemplate.query(sql, new TicketTypeRowMapper());
    }


}
