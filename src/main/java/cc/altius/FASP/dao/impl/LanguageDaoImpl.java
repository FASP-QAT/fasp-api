/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LanguageDao;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.rowMapper.LanguageRowMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class LanguageDaoImpl implements LanguageDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Language> getLanguageList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM lc_language l ");
        if (active) {
            sb.append(" WHERE l.`ACTIVE` ");
        }
        return this.jdbcTemplate.query(sb.toString(), new LanguageRowMapper());
    }

}
