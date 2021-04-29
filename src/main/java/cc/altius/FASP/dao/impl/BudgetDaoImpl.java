/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.BudgetDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.rowMapper.BudgetRowMapper;
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
 * @author akil
 */
@Repository
public class BudgetDaoImpl implements BudgetDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private final String sqlListString = "SELECT  "
            + "     b.BUDGET_ID, b.BUDGET_CODE,  "
            + "     b.LABEL_ID, b.LABEL_EN, b.LABEL_FR, b.LABEL_SP, b.LABEL_PR,  "
            + "     b.BUDGET_AMT, (b.BUDGET_AMT * b.CONVERSION_RATE_TO_USD) `BUDGET_USD_AMT`, SUM((IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0))*IFNULL(s.CONVERSION_RATE_TO_USD,0)) `USED_USD_AMT`, b.START_DATE, b.STOP_DATE, b.NOTES, "
            + "     p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`,  "
            + "     fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`,  "
            + "     r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_SP `REALM_LABEL_SP`, r.LABEL_PR `REALM_LABEL_PR`, r.`REALM_CODE`,  "
            + "     c.CURRENCY_ID, c.CURRENCY_CODE, b.CONVERSION_RATE_TO_USD, c.LABEL_ID `CURRENCY_LABEL_ID`, c.LABEL_EN `CURRENCY_LABEL_EN`, c.LABEL_FR `CURRENCY_LABEL_FR`, c.LABEL_SP `CURRENCY_LABEL_SP`, c.LABEL_PR `CURRENCY_LABEL_PR`,  "
            + "	 b.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, b.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, b.LAST_MODIFIED_DATE  "
            + "FROM vw_budget b  "
            + "LEFT JOIN vw_program p ON b.PROGRAM_ID=p.PROGRAM_ID  "
            + "LEFT JOIN vw_funding_source fs ON b.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  "
            + "LEFT JOIN vw_realm r ON fs.REALM_ID=r.REALM_ID  "
            + "LEFT JOIN vw_currency c ON b.CURRENCY_ID=c.CURRENCY_ID  "
            + "LEFT JOIN us_user cb ON b.CREATED_BY=cb.USER_ID  "
            + "LEFT JOIN us_user lmb ON b.LAST_MODIFIED_BY=lmb.USER_ID  "
            + "LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID "
            + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID AND st.SHIPMENT_STATUS_ID!=8 AND st.ACCOUNT_FLAG=1 AND st.ACTIVE AND st.BUDGET_ID=b.BUDGET_ID "
            + "WHERE TRUE ";
    private final String sqlGroupByString = " GROUP BY b.BUDGET_ID ";

    @Override
    @Transactional
    public int addBudget(Budget b, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_budget").usingGeneratedKeyColumns("BUDGET_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("PROGRAM_ID", b.getProgram().getId());
        params.put("BUDGET_CODE", b.getBudgetCode());
        params.put("FUNDING_SOURCE_ID", b.getFundingSource().getFundingSourceId());
        int labelId = this.labelDao.addLabel(b.getLabel(), LabelConstants.RM_BUDGET, curUser.getUserId());
        params.put("BUDGET_AMT", b.getBudgetAmt());
        params.put("CURRENCY_ID", b.getCurrency().getCurrencyId());
        params.put("CONVERSION_RATE_TO_USD", b.getCurrency().getConversionRateToUsd());
        params.put("START_DATE", b.getStartDate());
        params.put("STOP_DATE", b.getStopDate());
        params.put("NOTES", b.getNotes());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateBudget(Budget b, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("budgetId", b.getBudgetId());
        params.put("active", b.isActive());
        params.put("budgetAmt", b.getBudgetAmt());
        params.put("budgetCode", b.getBudgetCode());
        params.put("startDate", b.getStartDate());
        params.put("stopDate", b.getStopDate());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("labelEn", b.getLabel().getLabel_en());
        params.put("notes", b.getNotes());
        params.put("fundingSourceId",b.getFundingSource().getFundingSourceId());
        return this.namedParameterJdbcTemplate.update("UPDATE rm_budget b "
                + "LEFT JOIN ap_label bl ON b.LABEL_ID=bl.LABEL_ID SET "
                + "bl.`LABEL_EN`=:labelEn, "
                + "bl.`LAST_MODIFIED_BY`=:curUser, "
                + "bl.`LAST_MODIFIED_DATE`=:curDate, "
                + "b.`BUDGET_CODE`=:budgetCode, b.`BUDGET_AMT`=:budgetAmt, b.`START_DATE`=:startDate, b.`STOP_DATE`=:stopDate, b.ACTIVE=:active, b.NOTES=:notes,b.FUNDING_SOURCE_ID=:fundingSourceId, "
                + "b.LAST_MODIFIED_BY=:curUser, "
                + "b.LAST_MODIFIED_DATE=:curDate "
                + "WHERE b.BUDGET_ID=:budgetId", params);
    }

    @Override
    public List<Budget> getBudgetListForProgramIds(String[] programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        StringBuilder paramBuilder = new StringBuilder();
        for (String pId : programIds) {
            paramBuilder.append("'").append(pId).append("',");
        }
        if (programIds.length > 0) {
            paramBuilder.setLength(paramBuilder.length() - 1);
        }
        sqlStringBuilder.append(" AND p.PROGRAM_ID IN (").append(paramBuilder).append(") ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public List<Budget> getBudgetList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" AND p.ACTIVE");
        sqlStringBuilder.append(sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public List<Budget> getBudgetListForRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public Budget getBudgetById(int budgetId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND b.BUDGET_ID=:budgetId ");
        Map<String, Object> params = new HashMap<>();
        params.put("budgetId", budgetId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        sqlStringBuilder.append(sqlGroupByString);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public List<Budget> getBudgetListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append("AND b.LAST_MODIFIED_DATE>:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        sqlStringBuilder.append(this.sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public List<Budget> getBudgetListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND p.PROGRAM_ID IN (").append(programIdsString).append(") AND b.`BUDGET_ID`  IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(this.sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

}
