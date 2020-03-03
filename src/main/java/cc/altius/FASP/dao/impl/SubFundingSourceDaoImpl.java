/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.SubFundingSourceDao;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgSubFundingSourceDTORowMapper;
import cc.altius.FASP.model.SubFundingSource;
import cc.altius.FASP.model.SubFundingSourceRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class SubFundingSourceDaoImpl implements SubFundingSourceDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<PrgSubFundingSourceDTO> getSubFundingSourceListForSync(String lastSyncDate) {
        String sql = "SELECT sfs.`ACTIVE`,sfs.`FUNDING_SOURCE_ID`,sfs.`SUB_FUNDING_SOURCE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM rm_sub_funding_source sfs \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=sfs.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE sfs.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgSubFundingSourceDTORowMapper());
    }

    @Override
    @Transactional
    public int addSubFundingSource(SubFundingSource s, int curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_sub_funding_source").usingGeneratedKeyColumns("SUB_FUNDING_SOURCE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("FUNDING_SOURCE_ID", s.getFundingSource().getFundingSourceId());
        int labelId = this.labelDao.addLabel(s.getLabel(), curUser);
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        int subFundingSourceId = si.executeAndReturnKey(params).intValue();
        return subFundingSourceId;
    }

    @Override
    @Transactional
    public int updateSubFundingSource(SubFundingSource s, int curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("subFundingSourceId", s.getSubFundingSourceId());
        params.put("active", s.isActive());
        params.put("curUser", curUser);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(this.jdbcTemplate);

        int rows = nm.update("UPDATE rm_sub_funding_source s SET s.ACTIVE=:active, s.LAST_MODIFIED_BY=:curUser, s.LAST_MODIFIED_DATE=:curDate WHERE s.SUB_FUNDING_SOURCE_ID=:subFundingSourceId", params);
        return rows;
    }

    @Override
    public SubFundingSource getSubFundingSourceById(int subFundingSourceId) {

        String sql = " SELECT sfs.*,sl.`LABEL_ID` AS SF_LABEL_ID,sl.`LABEL_EN` SF_LABEL_EN,sl.`LABEL_FR` SF_LABEL_FR,sl.`LABEL_PR` SF_LABEL_PR,sl.`LABEL_SP` AS SF_LABEL_SP"
                + "FROM rm_sub_funding_source sfs "
                + "LEFT JOIN ap_label sl ON sfs.LABEL_ID=sl.LABEL_ID "
                + "LEFT JOIN us_user cb ON sfs.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON sfs.LAST_MODIFIED_BY=lmb.USER_ID; "
                + "WHERE sfs.SUB_FUNDING_SOURCE_ID=? ";
        return this.jdbcTemplate.queryForObject(sql, new SubFundingSourceRowMapper(), subFundingSourceId);
    }

    @Override
    public List<SubFundingSource> getSubFundingSourceList() {
        
        String sql = " SELECT sfs.*,sl.`LABEL_ID` AS SF_LABEL_ID,sl.`LABEL_EN` SF_LABEL_EN,sl.`LABEL_FR` SF_LABEL_FR,sl.`LABEL_PR` SF_LABEL_PR,sl.`LABEL_SP` AS SF_LABEL_SP"
                + "FROM rm_sub_funding_source sfs "
                + "LEFT JOIN ap_label sl ON sfs.LABEL_ID=sl.LABEL_ID "
                + "LEFT JOIN us_user cb ON sfs.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON sfs.LAST_MODIFIED_BY=lmb.USER_ID; ";
        return this.jdbcTemplate.query(sql, new SubFundingSourceRowMapper());
    }

}
