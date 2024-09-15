/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.AclDao;
import cc.altius.FASP.model.SecurityRequestMatcher;
import cc.altius.FASP.model.rowMapper.SecurityRequestMatcherRowMapper;
import java.util.HashMap;
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
 * @author akil
 */
@Repository
public class AclDaoImpl implements AclDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void buildSecurity() {
        String sqlString = "SELECT SECURITY_ID, METHOD, URL_LIST, BF_LIST FROM temp_security";
        List<SecurityRequestMatcher> secList = this.jdbcTemplate.query(sqlString, new SecurityRequestMatcherRowMapper());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ap_security");
        for (SecurityRequestMatcher sec : secList) {
            for (String url : sec.getUrlList().split("~")) {
                for (String bf : sec.getBfList().split("~")) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("METHOD", sec.getMethod());
                    params.put("URL", url);
                    params.put("BF", bf);
                    si.execute(params);
                }
            }
        }
    }

}
