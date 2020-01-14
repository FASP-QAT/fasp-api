/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.RegistrationDao;
import cc.altius.FASP.model.Registration;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String curDate = DateUtils.getCurrentDateString(DateUtils.GMT, DateUtils.YMDHMS);
        Map<String, Object> params = new HashMap<>();
        params.put("NAME", registration.getName());
        params.put("ADDRESS", registration.getAddress());
        params.put("CITY_ID", registration.getCity().getCityId());
        params.put("COUNTRY_ID", registration.getCountry().getCountryId());
        params.put("DESIGNATION", registration.getDesignation());
        params.put("EMAIL_ID", registration.getEmailId());
        params.put("ORGANISATION_NAME", registration.getOrganisationName());
        params.put("PHONE_NO", registration.getPhoneNo());
        params.put("STATE_ID", registration.getState().getStateId());
        params.put("CREATED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

}
