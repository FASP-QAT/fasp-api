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

    private final String sqlListString = "SELECT "
            + "     b.BUDGET_ID, bl.LABEL_ID, bl.LABEL_EN, bl.LABEL_FR, bl.LABEL_SP, bl.LABEL_PR, "
            + "     b.BUDGET_AMT, b.START_DATE, b.STOP_DATE, "
            + "     p.PROGRAM_ID, pl.LABEL_ID `PROGRAM_LABEL_ID`, pl.LABEL_EN `PROGRAM_LABEL_EN`, pl.LABEL_FR `PROGRAM_LABEL_FR`, pl.LABEL_SP `PROGRAM_LABEL_SP`, pl.LABEL_PR `PROGRAM_LABEL_PR`, "
            + "     sfs.SUB_FUNDING_SOURCE_ID, sfsl.LABEL_ID `SUB_FUNDING_SOURCE_LABEL_ID`, sfsl.LABEL_EN `SUB_FUNDING_SOURCE_LABEL_EN`, sfsl.LABEL_FR `SUB_FUNDING_SOURCE_LABEL_FR`, sfsl.LABEL_SP `SUB_FUNDING_SOURCE_LABEL_SP`, sfsl.LABEL_PR `SUB_FUNDING_SOURCE_LABEL_PR`, "
            + "     fs.FUNDING_SOURCE_ID, fsl.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fsl.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fsl.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fsl.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fsl.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, "
            + "     r.REALM_ID, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, r.`REALM_CODE`, "
            + "	b.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, b.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, b.LAST_MODIFIED_DATE "
            + "FROM rm_budget b "
            + "LEFT JOIN ap_label bl ON b.LABEL_ID=bl.LABEL_ID "
            + "LEFT JOIN rm_program p ON b.PROGRAM_ID=p.PROGRAM_ID "
            + "LEFT JOIN ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
            + "LEFT JOIN rm_sub_funding_source sfs ON b.SUB_FUNDING_SOURCE_ID=sfs.SUB_FUNDING_SOURCE_ID "
            + "LEFT JOIN ap_label sfsl ON sfs.LABEL_ID=sfsl.LABEL_ID "
            + "LEFT JOIN rm_funding_source fs ON sfs.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
            + "LEFT JOIN ap_label fsl ON fs.LABEL_ID=fsl.LABEL_ID "
            + "LEFT JOIN rm_realm r ON fs.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + "LEFT JOIN us_user cb ON b.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON b.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    @Transactional
    public int addBudget(Budget b, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_budget").usingGeneratedKeyColumns("BUDGET_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("PROGRAM_ID", b.getProgram().getProgramId());
        params.put("SUB_FUNDING_SOURCE_ID", b.getSubFundingSource().getSubFundingSourceId());
        int labelId = this.labelDao.addLabel(b.getLabel(), curUser.getUserId());
        params.put("BUDGET_AMT", b.getBudgetAmt());
        params.put("START_DATE", b.getStartDate());
        params.put("STOP_DATE", b.getStopDate());
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
        params.put("startDate", b.getStartDate());
        params.put("stopDate", b.getStopDate());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("labelEn", b.getLabel().getLabel_en());
        return this.namedParameterJdbcTemplate.update("UPDATE rm_budget b "
                + "LEFT JOIN ap_label bl ON b.LABEL_ID=bl.LABEL_ID SET "
                + "bl.`LABEL_EN`=:labelEn, "
                + "bl.`LAST_MODIFIED_BY`=IF(bl.`LABEL_EN`!=:labelEn, :curUser, bl.LAST_MODIFIED_BY), "
                + "bl.`LAST_MODIFIED_DATE`=IF(bl.`LABEL_EN`!=:labelEn, :curDate, bl.LAST_MODIFIED_DATE), "
                + "b.BUDGET_AMT=:budgetAmt, b.START_DATE=:startDate, b.STOP_DATE=:stopDate, b.ACTIVE=:active, "
                + "b.LAST_MODIFIED_BY=IF(b.BUDGET_AMT!=:budgetAmt OR b.START_DATE!=:startDate OR b.STOP_DATE!=:stopDate OR b.ACTIVE!=:active, :curUser, b.LAST_MODIFIED_BY), "
                + "b.LAST_MODIFIED_DATE=IF(b.BUDGET_AMT!=:budgetAmt OR b.START_DATE!=:startDate OR b.STOP_DATE!=:stopDate OR b.ACTIVE!=:active, :curDate, b.LAST_MODIFIED_DATE) "
                + "WHERE b.BUDGET_ID=:budgetId", params);
    }

    @Override
    public List<Budget> getBudgetList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public List<Budget> getBudgetListForRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public Budget getBudgetById(int budgetId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND b.BUDGET_ID=:budgetId ");
        Map<String, Object> params = new HashMap<>();
        params.put("budgetId", budgetId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

    @Override
    public List<Budget> getBudgetListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" b.LAST_MODIFIED_DATE>:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new BudgetRowMapper());
    }

}
