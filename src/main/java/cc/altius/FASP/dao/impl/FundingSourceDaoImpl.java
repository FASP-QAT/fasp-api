/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.FundingSourceDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<PrgFundingSourceDTO> getFundingSourceListForSync(String lastSyncDate,int realmId) {
        String sql = "SELECT fs.`ACTIVE`,fs.`FUNDING_SOURCE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,fs.`REALM_ID` "
                + "FROM rm_funding_source fs  "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=fs.`LABEL_ID` WHERE fs.`REALM_ID`=:realmId";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " AND fs.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
                    params.put("realmId", realmId);
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgFundingSourceDTORowMapper());

    }

    @Override
    @Transactional
    public int addFundingSource(FundingSource f, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_funding_source").usingGeneratedKeyColumns("FUNDING_SOURCE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", f.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(f.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
//        SimpleJdbcInsert sii = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("tk_ticket").usingGeneratedKeyColumns("TICKET_ID");
//        Map<String, Object> paramsTwo = new HashMap<>();
//        paramsTwo.put("TICKET_TYPE_ID", 2);
//        paramsTwo.put("TICKET_STATUS_ID", 1);
//        paramsTwo.put("REFFERENCE_ID", insertedRow);
//        paramsTwo.put("NOTES", "");
//        paramsTwo.put("CREATED_BY", curUser);
//        paramsTwo.put("CREATED_DATE", curDate);
//        paramsTwo.put("LAST_MODIFIED_BY", curUser);
//        paramsTwo.put("LAST_MODIFIED_DATE", curDate);
//        sii.execute(paramsTwo);
    }

    @Override
    @Transactional
    public int updateFundingSource(FundingSource f, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("curDate", curDate);
        params.put("curUser", curUser.getUserId());
        params.put("fundingSourceId", f.getFundingSourceId());
        params.put("active", f.isActive());
        params.put("labelEn", f.getLabel().getLabel_en());
        return this.namedParameterJdbcTemplate.update("UPDATE rm_funding_source fs LEFT JOIN ap_label fsl on fs.LABEL_ID=fsl.LABEL_ID SET fs.`ACTIVE`=:active, fs.`LAST_MODIFIED_BY`=:curUser, fs.`LAST_MODIFIED_DATE`=:curDate, fsl.LABEL_EN=:labelEn, fsl.LAST_MODIFIED_BY=:curUser, fsl.LAST_MODIFIED_DATE=:curDate WHERE fs.FUNDING_SOURCE_ID=:fundingSourceId", params);
    }

    @Override
    public List<FundingSource> getFundingSourceList(CustomUserDetails curUser) {
        String sql = "SELECT  "
                + "    fs.FUNDING_SOURCE_ID,  "
                + "    fsl.`LABEL_ID`, fsl.`LABEL_EN`, fsl.`LABEL_FR`, fsl.`LABEL_PR`, fsl.`LABEL_SP`,  "
                + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE,  "
                + "    fs.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, fs.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, fs.LAST_MODIFIED_DATE  "
                + "FROM rm_funding_source fs  "
                + "LEFT JOIN ap_label fsl ON fs.`LABEL_ID`=fsl.`LABEL_ID`  "
                + "LEFT JOIN rm_realm r ON fs.`REALM_ID`=r.`REALM_ID`  "
                + "LEFT JOIN ap_label rl ON r.`LABEL_ID`=rl.`LABEL_ID` "
                + "LEFT JOIN us_user cb ON fs.CREATED_BY=cb.USER_ID  "
                + "LEFT JOIN us_user lmb ON fs.LAST_MODIFIED_BY=lmb.USER_ID";
        return this.jdbcTemplate.query(sql, new FundingSourceRowMapper());
    }

    @Override
    public FundingSource getFundingSourceById(int fundingSourceId, CustomUserDetails curUser) {
        String sql = "SELECT  "
                + "    fs.FUNDING_SOURCE_ID,  "
                + "    fsl.`LABEL_ID`, fsl.`LABEL_EN`, fsl.`LABEL_FR`, fsl.`LABEL_PR`, fsl.`LABEL_SP`,  "
                + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE,  "
                + "    fs.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, fs.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, fs.LAST_MODIFIED_DATE  "
                + "FROM rm_funding_source fs  "
                + "LEFT JOIN ap_label fsl ON fs.`LABEL_ID`=fsl.`LABEL_ID`  "
                + "LEFT JOIN rm_realm r ON fs.`REALM_ID`=r.`REALM_ID`  "
                + "LEFT JOIN ap_label rl ON r.`LABEL_ID`=rl.`LABEL_ID` "
                + "LEFT JOIN us_user cb ON fs.CREATED_BY=cb.USER_ID  "
                + "LEFT JOIN us_user lmb ON fs.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE fs.`FUNDING_SOURCE_ID`=?";
        return this.jdbcTemplate.queryForObject(sql, new FundingSourceRowMapper(), fundingSourceId);
    }

}
