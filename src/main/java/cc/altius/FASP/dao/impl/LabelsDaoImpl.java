/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelsDao;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.rowMapper.LabelsRowMapper;
import cc.altius.utils.DateUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author palash
 */
@Repository
public class LabelsDaoImpl implements LabelsDao {
    private JdbcTemplate jdbcTemplate;
    private javax.sql.DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Label> getLabelsListAll() {
        String sql = "select * from ap_label";
        return this.jdbcTemplate.query(sql, new LabelsRowMapper());
    }

    @Override
    public int updateLabels(Label label,int userId) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LABEL_FR`=?,al.`LABEL_PR`=?,al.`LABEL_SP`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        return this.jdbcTemplate.update(sqlOne,label.getLabel_en(),label.getLabel_fr(), label.getLabel_pr(), label.getLabel_sp(), userId, curDate, label.getLabelId());

    }

}
