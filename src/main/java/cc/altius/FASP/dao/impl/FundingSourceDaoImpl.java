/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.FundingSourceDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgFundingSourceDTORowMapper;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.rowMapper.FundingSourceRowMapper;
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
public class FundingSourceDaoImpl implements FundingSourceDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PrgFundingSourceDTO> getFundingSourceListForSync(String lastSyncDate) {
        String sql = "SELECT fs.`ACTIVE`,fs.`FUNDING_SOURCE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM rm_funding_source fs \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=fs.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE fs.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgFundingSourceDTORowMapper());

    }

    @Override
    @Transactional
    public int addFundingSource(FundingSource f, int curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_funding_source").usingGeneratedKeyColumns("FUNDING_SOURCE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", f.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(f.getLabel(), curUser);
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);

        int insertedRow = si.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert sii = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("tk_ticket").usingGeneratedKeyColumns("TICKET_ID");
        Map<String, Object> paramsTwo = new HashMap<>();
        paramsTwo.put("TICKET_TYPE_ID", 2);
        paramsTwo.put("TICKET_STATUS_ID", 1);
        paramsTwo.put("REFFERENCE_ID", insertedRow);
        paramsTwo.put("NOTES", "");
        paramsTwo.put("CREATED_BY", curUser);
        paramsTwo.put("CREATED_DATE", curDate);
        paramsTwo.put("LAST_MODIFIED_BY", curUser);
        paramsTwo.put("LAST_MODIFIED_DATE", curDate);
        sii.execute(paramsTwo);

        return insertedRow;
    }

    @Override
    @Transactional
    public int updateFundingSource(FundingSource f, int curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, f.getLabel().getLabel_en(), curUser, curDate, f.getLabel().getLabelId());

        String sqlTwo = "UPDATE rm_funding_source dt SET  dt.`REALM_ID`=?,dt.`ACTIVE`=?,dt.`LAST_MODIFIED_BY`=?,dt.`LAST_MODIFIED_DATE`=?"
                + " WHERE dt.`FUNDING_SOURCE_ID`=?;";
        return this.jdbcTemplate.update(sqlTwo, f.getRealm().getRealmId(), f.isActive(), curUser, curDate, f.getFundingSourceId());
    }

    @Override
    public List<FundingSource> getFundingSourceList() {
        String sql = "SELECT rm.*,al.`LABEL_EN`,al.`LABEL_FR`,al.`LABEL_SP`,al.`LABEL_PR`,al.`LABEL_ID`,\n"
                + "rr.`REALM_ID` `RM_REALM_ID`,lr.`LABEL_ID` `RM_LABEL_ID`,\n"
                + "lr.`LABEL_EN` `RM_LABEL_EN`,lr.`LABEL_FR` `RM_LABEL_FR` ,lr.`LABEL_SP` `RM_LABEL_SP`,lr.`LABEL_PR` `RM_LABEL_PR`\n"
                + "  FROM rm_funding_source rm \n"
                + "LEFT JOIN  ap_label al ON al.`LABEL_ID`=rm.`LABEL_ID`\n"
                + "LEFT JOIN rm_realm rr ON rr.`REALM_ID`=rm.`REALM_ID`\n"
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=rr.`LABEL_ID`";
        return this.jdbcTemplate.query(sql, new FundingSourceRowMapper());
    }

    @Override
    public FundingSource getFundingSourceById(int fundingSourceId) {
        String sql = "SELECT rm.*,al.`LABEL_EN`,al.`LABEL_FR`,al.`LABEL_SP`,al.`LABEL_PR`,al.`LABEL_ID`,\n"
                + "rr.`REALM_ID` `RM_REALM_ID`,lr.`LABEL_ID` `RM_LABEL_ID`,\n"
                + "lr.`LABEL_EN` `RM_LABEL_EN`,lr.`LABEL_FR` `RM_LABEL_FR` ,lr.`LABEL_SP` `RM_LABEL_SP`,lr.`LABEL_PR` `RM_LABEL_PR`\n"
                + "  FROM rm_funding_source rm \n"
                + "LEFT JOIN  ap_label al ON al.`LABEL_ID`=rm.`LABEL_ID`\n"
                + "LEFT JOIN rm_realm rr ON rr.`REALM_ID`=rm.`REALM_ID`\n"
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=rr.`LABEL_ID`"
                + "WHERE rm.`FUNDING_SOURCE_ID`=?";
        return this.jdbcTemplate.queryForObject(sql, new FundingSourceRowMapper(), fundingSourceId);
    }

}
