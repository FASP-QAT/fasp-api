/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LoggerDao;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author akil
 */
@Repository
public class LoggerDaoImpl implements LoggerDao {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void createTables() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "CREATE TABLE IF NOT EXISTS logging_event  (timestmp BIGINT NOT NULL, formatted_message TEXT NOT NULL, logger_name VARCHAR(254) NOT NULL, level_string VARCHAR(254) NOT NULL, thread_name VARCHAR(254), reference_flag SMALLINT, arg0 VARCHAR(254), arg1 VARCHAR(254), arg2 VARCHAR(254), arg3 VARCHAR(254), caller_filename VARCHAR(254) NOT NULL, caller_class VARCHAR(254) NOT NULL, caller_method VARCHAR(254) NOT NULL, caller_line CHAR(4) NOT NULL, event_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY)";
        jdbcTemplate.execute(sql);

        sql = "CREATE TABLE IF NOT EXISTS logging_event_property (event_id BIGINT NOT NULL, mapped_key VARCHAR(254) NOT NULL,mapped_value TEXT, PRIMARY KEY(event_id, mapped_key), FOREIGN KEY (event_id) REFERENCES logging_event(event_id))";
        jdbcTemplate.execute(sql);

        sql = "CREATE TABLE IF NOT EXISTS logging_event_exception (event_id BIGINT NOT NULL, i SMALLINT NOT NULL,trace_line VARCHAR(254) NOT NULL, PRIMARY KEY(event_id, i), FOREIGN KEY (event_id) REFERENCES logging_event(event_id))";
        jdbcTemplate.execute(sql);
    }
}
