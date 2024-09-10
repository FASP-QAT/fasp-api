/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.RegistrationDao;
import cc.altius.FASP.model.Registration;
import cc.altius.FASP.model.rowMapper.RegistrationRowMapper;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class RegistrationDaoImpl implements RegistrationDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int saveRegistration(Registration registration) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("registration").usingGeneratedKeyColumns("REGISTRATION_ID");
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        Map<String, Object> params = new HashMap<>();
        params.put("FIRST_NAME", registration.getFirstName());
        params.put("LAST_NAME", registration.getLastName());
        params.put("EMAIL_ID", registration.getEmailId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", 1);
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<Registration> getUserApprovalList() {
        String sql = "SELECT * FROM registration r WHERE r.`STATUS` IS NULL;";
        List<Registration> r = new LinkedList<>();
        try {
            r = this.jdbcTemplate.query(sql, new RegistrationRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public int updateRegistration(Registration registration) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "UPDATE registration r SET r.`LAST_MODIFIED_BY`=:curUser,r.`LAST_MODIFIED_DATE`=:curDate,r.`NOTES`=:notes,r.`STATUS`=:status WHERE r.`REGISTRATION_ID`=:regId";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("curUser", 1);
        params.put("curDate", curDate);
        params.put("notes", registration.getNotes());
        params.put("status", registration.isStatus());
        params.put("regId", registration.getRegistrationId());
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(dataSource);
        return jdbc.update(sql, params);
    }

}
