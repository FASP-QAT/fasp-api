/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.FundingSourceDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.rowMapper.FundingSourceRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
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

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListString = "SELECT  "
            + "    fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, "
            + "    fsl.`LABEL_ID`, fsl.`LABEL_EN`, fsl.`LABEL_FR`, fsl.`LABEL_PR`, fsl.`LABEL_SP`,  "
            + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE,  "
            + "    fs.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, fs.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, fs.LAST_MODIFIED_DATE  "
            + "FROM rm_funding_source fs  "
            + "LEFT JOIN ap_label fsl ON fs.`LABEL_ID`=fsl.`LABEL_ID`  "
            + "LEFT JOIN rm_realm r ON fs.`REALM_ID`=r.`REALM_ID`  "
            + "LEFT JOIN ap_label rl ON r.`LABEL_ID`=rl.`LABEL_ID` "
            + "LEFT JOIN us_user cb ON fs.CREATED_BY=cb.USER_ID  "
            + "LEFT JOIN us_user lmb ON fs.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    @Transactional
    public int addFundingSource(FundingSource f, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_funding_source").usingGeneratedKeyColumns("FUNDING_SOURCE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("FUNDING_SOURCE_CODE", f.getFundingSourceCode());
        params.put("REALM_ID", f.getRealm().getId());
        int labelId = this.labelDao.addLabel(f.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    @Transactional
    public int updateFundingSource(FundingSource f, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("curDate", curDate);
        params.put("curUser", curUser.getUserId());
        params.put("fundingSourceCode", f.getFundingSourceCode());
        params.put("fundingSourceId", f.getFundingSourceId());
        params.put("active", f.isActive());
        params.put("labelEn", f.getLabel().getLabel_en());
        return this.namedParameterJdbcTemplate.update("UPDATE rm_funding_source fs LEFT JOIN ap_label fsl on fs.LABEL_ID=fsl.LABEL_ID SET fs.`FUNDING_SOURCE_CODE`=:fundingSourceCode, fs.`ACTIVE`=:active, fs.`LAST_MODIFIED_BY`=:curUser, fs.`LAST_MODIFIED_DATE`=:curDate, fsl.LABEL_EN=:labelEn, fsl.LAST_MODIFIED_BY=:curUser, fsl.LAST_MODIFIED_DATE=:curDate WHERE fs.FUNDING_SOURCE_ID=:fundingSourceId", params);
    }

    @Override
    public List<FundingSource> getFundingSourceList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceRowMapper());
    }

    @Override
    public List<FundingSource> getFundingSourceList(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceRowMapper());
    }

    @Override
    public FundingSource getFundingSourceById(int fundingSourceId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append("AND fs.`FUNDING_SOURCE_ID`=:fundingSourceId");
        Map<String, Object> params = new HashMap<>();
        params.put("fundingSourceId", fundingSourceId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new FundingSourceRowMapper());
    }

    @Override
    public List<FundingSource> getFundingSourceListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append("AND fs.LAST_MODIFIED_DATE>:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceRowMapper());
    }

}
