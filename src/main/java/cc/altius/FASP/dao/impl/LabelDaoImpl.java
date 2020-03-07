/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.rowMapper.LabelsRowMapper;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class LabelDaoImpl implements LabelDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public int addLabel(Label label, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", label.getLabel_en());
        params.put("LABEL_FR", label.getLabel_fr());
        params.put("LABEL_SP", label.getLabel_sp());
        params.put("LABEL_PR", label.getLabel_pr());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<Label> getLabelsListAll() {
        String sql = "select * from ap_label";
        return this.jdbcTemplate.query(sql, new LabelsRowMapper());
    }

    @Override
    public int updateLabels(Label label, int userId) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LABEL_FR`=?,al.`LABEL_PR`=?,al.`LABEL_SP`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        return this.jdbcTemplate.update(sqlOne, label.getLabel_en(), label.getLabel_fr(), label.getLabel_pr(), label.getLabel_sp(), userId, curDate, label.getLabelId());

    }

}
