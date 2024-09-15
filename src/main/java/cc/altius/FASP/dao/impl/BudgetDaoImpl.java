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
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.model.rowMapper.BudgetListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.BudgetResultSetExtractor;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private final String sqlListString = "SELECT "
            + "    b.BUDGET_ID, b.BUDGET_CODE, "
            + "    b.LABEL_ID, b.LABEL_EN, b.LABEL_FR, b.LABEL_SP, b.LABEL_PR, "
            + "    b.BUDGET_AMT, (b.BUDGET_AMT * b.CONVERSION_RATE_TO_USD) `BUDGET_USD_AMT`, "
            + "    IFNULL(stc.USED_AMT,0) `USED_AMT`, IFNULL(stc.USED_AMT_USD,0) `USED_USD_AMT`, "
            + "    b.START_DATE, b.STOP_DATE, b.NOTES, "
            + "    p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, p.PROGRAM_CODE, "
            + "    fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, "
            + "    r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_SP `REALM_LABEL_SP`, r.LABEL_PR `REALM_LABEL_PR`, r.`REALM_CODE`, "
            + "    c.CURRENCY_ID, c.CURRENCY_CODE, b.CONVERSION_RATE_TO_USD, c.LABEL_ID `CURRENCY_LABEL_ID`, c.LABEL_EN `CURRENCY_LABEL_EN`, c.LABEL_FR `CURRENCY_LABEL_FR`, c.LABEL_SP `CURRENCY_LABEL_SP`, c.LABEL_PR `CURRENCY_LABEL_PR`, "
            + "    b.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, b.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, b.LAST_MODIFIED_DATE  "
            + "FROM vw_budget b "
            + "LEFT JOIN rm_budget_program bp ON b.BUDGET_ID=bp.BUDGET_ID "
            + "LEFT JOIN vw_program p ON bp.PROGRAM_ID=p.PROGRAM_ID "
            + "LEFT JOIN vw_funding_source fs ON b.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
            + "LEFT JOIN vw_realm r ON fs.REALM_ID=r.REALM_ID "
            + "LEFT JOIN vw_currency c ON b.CURRENCY_ID=c.CURRENCY_ID "
            + "LEFT JOIN us_user cb ON b.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON b.LAST_MODIFIED_BY=lmb.USER_ID "
            + "LEFT JOIN (SELECT "
            + "st.BUDGET_ID, s.CONVERSION_RATE_TO_USD * SUM(IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0)) USED_AMT_USD, SUM(IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0)) USED_AMT "
            + "FROM rm_shipment s "
            + "LEFT JOIN rm_shipment_trans st ON "
            + "	s.SHIPMENT_ID=st.SHIPMENT_ID "
            + "    AND s.MAX_VERSION_ID=st.VERSION_ID "
            + "    AND st.SHIPMENT_STATUS_ID!=8 "
            + "    AND st.ACCOUNT_FLAG=1 "
            + "    AND st.ACTIVE "
            + "GROUP BY st.BUDGET_ID) stc ON stc.BUDGET_ID=b.BUDGET_ID "
            + "WHERE "
            + "	TRUE  ";
//    private final String sqlGroupByString = " GROUP BY b.BUDGET_ID ";

    @Override
    @Transactional
    public int addBudget(Budget b, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_budget").usingColumns("BUDGET_CODE", "FUNDING_SOURCE_ID", "REALM_ID", "BUDGET_AMT", "CURRENCY_ID", "CONVERSION_RATE_TO_USD", "START_DATE", "STOP_DATE", "NOTES", "LABEL_ID", "ACTIVE", "CREATED_BY", "CREATED_DATE", "LAST_MODIFIED_BY", "LAST_MODIFIED_DATE").usingGeneratedKeyColumns("BUDGET_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("BUDGET_CODE", b.getBudgetCode());
        params.put("FUNDING_SOURCE_ID", b.getFundingSource().getFundingSourceId());
        int labelId = this.labelDao.addLabel(b.getLabel(), LabelConstants.RM_BUDGET, curUser.getUserId());
        params.put("REALM_ID", b.getFundingSource().getRealm().getId());
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
        int budgetId = si.executeAndReturnKey(params).intValue();
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_budget_program");
        List<SqlParameterSource> paramList = new LinkedList<>();
        Map<String, Object> map = new HashedMap<String, Object>();
        for (SimpleCodeObject p : b.getPrograms()) {
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("BUDGET_ID", budgetId);
            paramMap.addValue("PROGRAM_ID", p.getId());
            paramList.add(paramMap);
        }
        si.executeBatch(paramList.toArray(new MapSqlParameterSource[paramList.size()]));
        return budgetId;
    }

    /**
     * Not updating the Programs because you cannot add a Program to a Budget
     * assign the Shipment to that Budget and then remove the Program. Therefore
     * Programs cannot be added or removed from when the Budget was created
     *
     * @param b
     * @param curUser
     * @return
     */
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
        params.put("fundingSourceId", b.getFundingSource().getFundingSourceId());
        int rowsEffected = this.namedParameterJdbcTemplate.update("UPDATE rm_budget b "
                + "LEFT JOIN ap_label bl ON b.LABEL_ID=bl.LABEL_ID SET "
                + "bl.`LABEL_EN`=:labelEn, "
                + "bl.`LAST_MODIFIED_BY`=:curUser, "
                + "bl.`LAST_MODIFIED_DATE`=:curDate, "
                + "b.`BUDGET_CODE`=:budgetCode, b.`BUDGET_AMT`=:budgetAmt, b.`START_DATE`=:startDate, b.`STOP_DATE`=:stopDate, b.ACTIVE=:active, b.NOTES=:notes,b.FUNDING_SOURCE_ID=:fundingSourceId, "
                + "b.LAST_MODIFIED_BY=:curUser, "
                + "b.LAST_MODIFIED_DATE=:curDate "
                + "WHERE b.BUDGET_ID=:budgetId", params);

        String programIdString = "";

        List<SqlParameterSource> paramList = new LinkedList<>();
        Map<String, Object> map = new HashedMap<String, Object>();
        for (SimpleCodeObject p : b.getPrograms()) {
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("BUDGET_ID", b.getBudgetId());
            paramMap.addValue("PROGRAM_ID", p.getId());
            paramList.add(paramMap);
            if (programIdString.length() == 0) {
                programIdString += p.getIdString();
            } else {
                programIdString += "," + p.getIdString();
            }
        }
        params.clear();
        params.put("budgetId", b.getBudgetId());
        params.put("programIdString", programIdString);
        StringBuilder sb = new StringBuilder("DELETE bp.* FROM rm_budget_program bp LEFT JOIN vw_program p ON bp.PROGRAM_ID=p.PROGRAM_ID WHERE bp.BUDGET_ID=:budgetId AND NOT FIND_IN_SET(bp.PROGRAM_ID ,:programIdString)");
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        rowsEffected += this.namedParameterJdbcTemplate.update(sb.toString(), params);
        int[] insertedRows = this.namedParameterJdbcTemplate.batchUpdate("INSERT IGNORE INTO rm_budget_program (BUDGET_ID, PROGRAM_ID) VALUES (:BUDGET_ID, :PROGRAM_ID)", paramList.toArray(new MapSqlParameterSource[paramList.size()]));
        for (int i : insertedRows) {
            rowsEffected += i;
        }
        return rowsEffected;
    }

    @Override
    public List<Budget> getBudgetListForProgramIds(String[] programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        StringBuilder paramBuilder = new StringBuilder();
        if (programIds.length > 0) {
            for (String pId : programIds) {
                paramBuilder.append("'").append(pId).append("',");
            }
            paramBuilder.setLength(paramBuilder.length() - 1);
            sqlStringBuilder.append(" AND bp.PROGRAM_ID IN (").append(paramBuilder).append(") ");
        }
        Map<String, Object> params = new HashMap<>();
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetListResultSetExtractor());
    }

    @Override
    public List<Budget> getBudgetList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
//        sqlStringBuilder.append(sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetListResultSetExtractor());
    }

    @Override
    public List<Budget> getBudgetListForRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
//        sqlStringBuilder.append(sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetListResultSetExtractor());
    }

    @Override
    public Budget getBudgetById(int budgetId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND b.BUDGET_ID=:budgetId ");
        Map<String, Object> params = new HashMap<>();
        params.put("budgetId", budgetId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
//        sqlStringBuilder.append(sqlGroupByString);
//        System.out.println(sqlStringBuilder.toString());
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetResultSetExtractor());
    }

    @Override
    public List<SimpleCodeObject> getBudgetDropdownFilterMultipleFundingSources(String fundingSourceIds, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT b.BUDGET_ID `ID`, b.BUDGET_CODE `CODE`, b.LABEL_ID, b.LABEL_EN, b.LABEL_FR, b.LABEL_SP, b.LABEL_PR  FROM vw_budget b WHERE b.ACTIVE AND FIND_IN_SET(b.FUNDING_SOURCE_ID, :fundingSourceIds) ");
        Map<String, Object> params = new HashMap<>();
        params.put("fundingSourceIds", fundingSourceIds);
        this.aclService.addUserAclForRealm(stringBuilder, params, "b", curUser);
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<SimpleCodeObject> getBudgetDropdownForProgram(int programId, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT b.BUDGET_ID `ID`, b.BUDGET_CODE `CODE`, b.LABEL_ID, b.LABEL_EN, b.LABEL_FR, b.LABEL_SP, b.LABEL_PR  FROM vw_budget b LEFT JOIN rm_budget_program bp ON b.BUDGET_ID=bp.BUDGET_ID LEFT JOIN vw_program p ON bp.PROGRAM_ID=p.PROGRAM_ID WHERE b.ACTIVE AND (bp.PROGRAM_ID=:programId OR :programId=-1) AND p.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        this.aclService.addUserAclForRealm(stringBuilder, params, "b", curUser);
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        stringBuilder.append(" GROUP BY b.BUDGET_ID");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<Budget> getBudgetListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append("AND b.LAST_MODIFIED_DATE>:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
//        sqlStringBuilder.append(this.sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetListResultSetExtractor());
    }

    @Override
    public List<Budget> getBudgetListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND p.PROGRAM_ID IN (").append(programIdsString).append(") AND b.`BUDGET_ID`  IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
//        sqlStringBuilder.append(this.sqlGroupByString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetListResultSetExtractor());
    }

}
