/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProgramDao;
import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.DTO.rowMapper.ProgramDTORowMapper;
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
public class ProgramDaoImpl implements ProgramDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ProgramDTO> getProgramList() {
        String sql = "SELECT r.`PROGRAM_ID`,label.`LABEL_ID`,label.`LABEL_EN`,label.`LABEL_FR`,label.`LABEL_PR`,label.`LABEL_SP`\n"
                + "FROM rm_program r \n"
                + "LEFT JOIN rm_label label ON label.`LABEL_ID`=r.`LABEL_ID`;";
        return this.jdbcTemplate.query(sql, new ProgramDTORowMapper());
    }

}
