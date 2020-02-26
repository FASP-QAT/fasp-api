/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.rowMapper.RealmCountryRowMapper;
import cc.altius.FASP.model.rowMapper.RealmRowMapper;
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
public class RealmDaoImpl implements RealmDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Realm> getRealmList(boolean active) {
        String sql = " SELECT r.*,lb.`LABEL_ID` AS RM_LABEL_ID,lb.`LABEL_EN` RM_LABEL_EN,lb.`LABEL_FR` RM_LABEL_FR,lb.`LABEL_PR` RM_LABEL_PR,lb.`LABEL_SP` AS RM_LABEL_SP FROM rm_realm r\n"
                + " LEFT JOIN ap_label lb ON lb.`LABEL_ID`=r.`LABEL_ID` ";
        if (active) {
            sql += " WHERE r.`ACTIVE`";
        }
        return this.jdbcTemplate.query(sql, new RealmRowMapper());
    }

    @Override
    public List<RealmCountry> getRealmCountryList(boolean active) {
        String sql = "SELECT rc.`REALM_COUNTRY_ID`, "
                + "lc.`LABEL_ID` AS CU_LABEL_ID, "
                + "lc.`LABEL_EN` AS CU_LABEL_EN  "
                + ",lc.`LABEL_FR` AS CU_LABEL_FR  "
                + ",lc.`LABEL_SP` AS CU_LABEL_SP  "
                + ",lc.`LABEL_PR` AS CU_LABEL_PR, "
                + "lr.`LABEL_ID` AS RM_LABEL_ID,  "
                + "lr.`LABEL_EN` AS RM_LABEL_EN  "
                + ",lr.`LABEL_FR` AS RM_LABEL_FR "
                + ",lr.`LABEL_SP` AS RM_LABEL_SP "
                + ",lr.`LABEL_PR` AS RM_LABEL_PR, c.*,r.* "
                + " FROM rm_realm_country rc "
                + "LEFT JOIN ap_country c ON c.`COUNTRY_ID`=rc.`COUNTRY_ID` "
                + "LEFT JOIN ap_label lc ON lc.`LABEL_ID`=c.`LABEL_ID` "
                + "LEFT JOIN rm_realm r ON r.`REALM_ID`=rc.`REALM_ID` "
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=r.`LABEL_ID` ";
        if (active) {
            sql += "WHERE rc.`ACTIVE`;";
        }
        return this.jdbcTemplate.query(sql, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountry> getRealmCountryListByRealmId(int realmId) {
        String sql = "SELECT rc.`REALM_COUNTRY_ID`, "
                + "lc.`LABEL_ID` AS CU_LABEL_ID, "
                + "lc.`LABEL_EN` AS CU_LABEL_EN  "
                + ",lc.`LABEL_FR` AS CU_LABEL_FR  "
                + ",lc.`LABEL_SP` AS CU_LABEL_SP  "
                + ",lc.`LABEL_PR` AS CU_LABEL_PR, "
                + "lr.`LABEL_ID` AS RM_LABEL_ID,  "
                + "lr.`LABEL_EN` AS RM_LABEL_EN  "
                + ",lr.`LABEL_FR` AS RM_LABEL_FR "
                + ",lr.`LABEL_SP` AS RM_LABEL_SP "
                + ",lr.`LABEL_PR` AS RM_LABEL_PR, c.*,r.* "
                + " FROM rm_realm_country rc "
                + "LEFT JOIN ap_country c ON c.`COUNTRY_ID`=rc.`COUNTRY_ID` "
                + "LEFT JOIN ap_label lc ON lc.`LABEL_ID`=c.`LABEL_ID` "
                + "LEFT JOIN rm_realm r ON r.`REALM_ID`=rc.`REALM_ID` "
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=r.`LABEL_ID` WHERE rc.`ACTIVE` AND rc.`REALM_ID`=? ";

        return this.jdbcTemplate.query(sql, new RealmCountryRowMapper(), realmId);
    }

}
