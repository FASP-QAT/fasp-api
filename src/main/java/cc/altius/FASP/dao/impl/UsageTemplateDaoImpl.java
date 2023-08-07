/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.UsageTemplateDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.UsageTemplate;
import cc.altius.FASP.model.rowMapper.UsageTemplateRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
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
public class UsageTemplateDaoImpl implements UsageTemplateDao {

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

    private static String usageTemplateString = "SELECT  "
            + "    ut.USAGE_TEMPLATE_ID, ut.LABEL_ID, ut.LABEL_EN, ut.LABEL_FR, ut.LABEL_SP, ut.LABEL_PR, ut.NOTES, "
            + "    ut.REALM_ID, ut.LAG_IN_MONTHS, ut.NO_OF_PATIENTS, ut.NO_OF_FORECASTING_UNITS, ut.ONE_TIME_USAGE, ut.USAGE_FREQUENCY_COUNT, ut.REPEAT_COUNT, ut.ACTIVE, "
            + "    p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, "
            + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
            + "    fuu.UNIT_ID `FUU_UNIT_ID`, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, fuu.UNIT_CODE `FUU_UNIT_CODE`, "
            + "    u.UNIT_ID `U_UNIT_ID`, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, u.UNIT_CODE `U_UNIT_CODE`, "
            + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`, "
            + "    uty.USAGE_TYPE_ID, uty.LABEL_ID `UT_LABEL_ID`, uty.LABEL_EN `UT_LABEL_EN`, uty.LABEL_FR `UT_LABEL_FR`, uty.LABEL_SP `UT_LABEL_SP`, uty.LABEL_PR `UT_LABEL_PR`, "
            + "    upf.USAGE_PERIOD_ID `UF_USAGE_PERIOD_ID`, upf.CONVERT_TO_MONTH `UF_CONVERT_TO_MONTH`, upf.LABEL_ID `UF_LABEL_ID`, upf.LABEL_EN `UF_LABEL_EN`, upf.LABEL_FR `UF_LABEL_FR`, upf.LABEL_SP `UF_LABEL_SP`, upf.LABEL_PR `UF_LABEL_PR`,  "
            + "    upr.USAGE_PERIOD_ID `R_USAGE_PERIOD_ID`, upr.CONVERT_TO_MONTH `R_CONVERT_TO_MONTH`, upr.LABEL_ID `R_LABEL_ID`, upr.LABEL_EN `R_LABEL_EN`, upr.LABEL_FR `R_LABEL_FR`, upr.LABEL_SP `R_LABEL_SP`, upr.LABEL_PR `R_LABEL_PR`, "
            + "    cb.USER_ID CB_USER_ID, cb.USERNAME CB_USERNAME, ut.CREATED_DATE, "
            + "    lmb.USER_ID LMB_USER_ID, lmb.USERNAME LMB_USERNAME, ut.LAST_MODIFIED_DATE "
            + "FROM vw_usage_template ut "
            + "LEFT JOIN vw_dataset p ON ut.PROGRAM_ID=p.PROGRAM_ID "
            + "LEFT JOIN vw_forecasting_unit fu ON ut.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
            + "LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID "
            + "LEFT JOIN vw_unit u ON ut.UNIT_ID=u.UNIT_ID "
            + "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
            + "LEFT JOIN vw_usage_type uty ON ut.USAGE_TYPE_ID=uty.USAGE_TYPE_ID "
            + "LEFT JOIN vw_usage_period upf ON ut.USAGE_FREQUENCY_USAGE_PERIOD_ID=upf.USAGE_PERIOD_ID "
            + "LEFT JOIN vw_usage_period upr ON ut.REPEAT_USAGE_PERIOD_ID=upr.USAGE_PERIOD_ID "
            + "LEFT JOIN us_user cb ON ut.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON ut.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    public List<UsageTemplate> getUsageTemplateList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlString = new StringBuilder(usageTemplateString);
        if (active) {
            sqlString.append(" AND ut.ACTIVE ");
        }
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlString, params, "ut", curUser);
        this.aclService.addFullAclForProgram(sqlString, params, "p", curUser);
        sqlString.append("ORDER BY fu.LABEL_EN ");
        return namedParameterJdbcTemplate.query(sqlString.toString(), params, new UsageTemplateRowMapper());
    }

    @Override
    public List<UsageTemplate> getUsageTemplateList(int tracerCategoryId, CustomUserDetails curUser) {
        StringBuilder sqlString = new StringBuilder(usageTemplateString).append(" AND ut.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        sqlString.append(" AND (fu.TRACER_CATEGORY_ID=:tracerCategoryId OR :tracerCategoryId=0)");
        params.put("tracerCategoryId", tracerCategoryId);
        this.aclService.addUserAclForRealm(sqlString, params, "ut", curUser);
        this.aclService.addFullAclForProgram(sqlString, params, "p", curUser);
        sqlString.append("ORDER BY fu.LABEL_EN ");
        return namedParameterJdbcTemplate.query(sqlString.toString(), params, new UsageTemplateRowMapper());
    }

    @Override
    public List<UsageTemplate> getUsageTemplateListForSync(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlString = new StringBuilder(usageTemplateString);
        if (programIdsString.length() > 0) {
            sqlString.append(" AND (ut.PROGRAM_ID IS NULL OR ut.PROGRAM_ID IN (").append(programIdsString).append("))");
        } else {
            sqlString.append(" AND ut.PROGRAM_ID IS NULL");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("programIdsString", programIdsString);
        this.aclService.addUserAclForRealm(sqlString, params, "ut", curUser);
        this.aclService.addFullAclForProgram(sqlString, params, "p", curUser);
        return namedParameterJdbcTemplate.query(sqlString.toString(), params, new UsageTemplateRowMapper());
    }

    @Override
    @Transactional
    public int addAndUpdateUsageTemplate(List<UsageTemplate> usageTemplateList, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_usage_template");
        Date dt = DateUtils.getCurrentDateObject(DateUtils.EST);
        List<SqlParameterSource> paramList = new LinkedList<>();
        usageTemplateList.stream().filter(ut -> ut.getUsageTemplateId() == 0).collect(Collectors.toList()).forEach(ut -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("REALM_ID", curUser.getRealm().getRealmId());
            int labelId = this.labelDao.addLabel(ut.getLabel(), LabelConstants.RM_USAGE_TEMPLATE, curUser.getUserId());
            param.addValue("LABEL_ID", labelId);
            param.addValue("PROGRAM_ID", (ut.getProgram() == null ? null : (ut.getProgram().getId() == null || ut.getProgram().getId() == 0 ? null : ut.getProgram().getId())));
            param.addValue("FORECASTING_UNIT_ID", ut.getForecastingUnit().getId());
            param.addValue("UNIT_ID", ut.getUnit().getId());
            param.addValue("LAG_IN_MONTHS", ut.getLagInMonths());
            param.addValue("USAGE_TYPE_ID", ut.getUsageType().getId());
            param.addValue("NO_OF_PATIENTS", ut.getNoOfPatients());
            param.addValue("NO_OF_FORECASTING_UNITS", ut.getNoOfForecastingUnits());
            param.addValue("NOTES", ut.getNotes());
            if (ut.getUsageType().getId() == GlobalConstants.USAGE_TEMPLATE_DISCRETE) {
                // Discrete
                param.addValue("ONE_TIME_USAGE", ut.isOneTimeUsage());
                if (!ut.isOneTimeUsage()) {
                    // Not a one time usage
                    param.addValue("USAGE_FREQUENCY_USAGE_PERIOD_ID", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyUsagePeriod().getUsagePeriodId())));
                    param.addValue("USAGE_FREQUENCY_COUNT", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyCount())));
                    param.addValue("REPEAT_USAGE_PERIOD_ID", (ut.getRepeatUsagePeriod() == null ? null : (ut.getRepeatUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getRepeatUsagePeriod().getUsagePeriodId())));
                    param.addValue("REPEAT_COUNT", (ut.getRepeatUsagePeriod() == null ? null : (ut.getRepeatUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getRepeatCount())));
                }
            } else {
                // Continuous
                param.addValue("ONE_TIME_USAGE", 0); // Always false
                param.addValue("USAGE_FREQUENCY_USAGE_PERIOD_ID", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyUsagePeriod().getUsagePeriodId())));
                param.addValue("USAGE_FREQUENCY_COUNT", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyCount())));
            }
            param.addValue("ACTIVE", ut.isActive());
            param.addValue("CREATED_BY", curUser.getUserId());
            param.addValue("CREATED_DATE", dt);
            param.addValue("LAST_MODIFIED_BY", curUser.getUserId());
            param.addValue("LAST_MODIFIED_DATE", dt);
            paramList.add(param);
        }
        );
        int rows = 0;
        int[] updatedRows;
        if (paramList.size() > 0) {
            updatedRows = si.executeBatch(paramList.toArray(new MapSqlParameterSource[paramList.size()]));
            rows += Arrays.stream(updatedRows).filter(i -> i == 1).count();
            paramList.clear();
        }
        usageTemplateList.stream().filter(ut -> ut.getUsageTemplateId() != 0).collect(Collectors.toList()).forEach(ut -> {
            Map<String, Object> param = new HashMap<>();
            param.put("usageTemplateId", ut.getUsageTemplateId());
            param.put("labelEn", ut.getLabel().getLabel_en());
            param.put("programId", (ut.getProgram() == null ? null : (ut.getProgram().getId() == null || ut.getProgram().getId() == 0 ? null : ut.getProgram().getId())));
            param.put("unitId", ut.getUnit().getId());
            param.put("forecastingUnitId", ut.getForecastingUnit().getId());
            param.put("lagInMonths", ut.getLagInMonths());
            param.put("noOfPatients", ut.getNoOfPatients());
            param.put("noOfForecastingUnits", ut.getNoOfForecastingUnits());
            param.put("usageTypeId", ut.getUsageType().getId());
            param.put("notes", ut.getNotes());
            if (ut.getUsageType().getId() == GlobalConstants.USAGE_TEMPLATE_DISCRETE) {
                // Discrete
                param.put("oneTimeUsage", ut.isOneTimeUsage());
                if (!ut.isOneTimeUsage()) {
                    param.put("usageFrequencyUsagePeriodId", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyUsagePeriod().getUsagePeriodId())));
                    param.put("usageFrequencyCount", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyCount())));
                    param.put("repeatUsagePeriodId", (ut.getRepeatUsagePeriod() == null ? null : (ut.getRepeatUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getRepeatUsagePeriod().getUsagePeriodId())));
                    param.put("repeatCount", (ut.getRepeatUsagePeriod() == null ? null : (ut.getRepeatUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getRepeatCount())));
                } else {
                    param.put("usageFrequencyUsagePeriodId", null);
                    param.put("usageFrequencyCount", null);
                    param.put("repeatUsagePeriodId", null);
                    param.put("repeatCount", null);
                }
            } else {
                // Continuous
                param.put("oneTimeUsage", 0); // Always false
                param.put("usageFrequencyUsagePeriodId", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyUsagePeriod().getUsagePeriodId())));
                param.put("usageFrequencyCount", (ut.getUsageFrequencyUsagePeriod() == null ? null : (ut.getUsageFrequencyUsagePeriod().getUsagePeriodId() == 0 ? null : ut.getUsageFrequencyCount())));
                param.put("repeatUsagePeriodId", null);
                param.put("repeatCount", null);
            }
            param.put("active", ut.isActive());
            param.put("curUser", curUser.getUserId());
            param.put("dt", dt);
            paramList.add(new MapSqlParameterSource(param));
        }
        );
        String sql = "UPDATE rm_usage_template ut LEFT JOIN ap_label l ON ut.LABEL_ID=l.LABEL_ID "
                + "SET "
                + "l.LABEL_EN=:labelEn, "
                + "ut.PROGRAM_ID=:programId, "
                + "ut.UNIT_ID=:unitId, "
                + "ut.FORECASTING_UNIT_ID=:forecastingUnitId, "
                + "ut.LAG_IN_MONTHS=:lagInMonths, "
                + "ut.USAGE_TYPE_ID=:usageTypeId, "
                + "ut.NO_OF_PATIENTS=:noOfPatients, "
                + "ut.NO_OF_FORECASTING_UNITS=:noOfForecastingUnits, "
                + "ut.ONE_TIME_USAGE=:oneTimeUsage, "
                + "ut.USAGE_FREQUENCY_USAGE_PERIOD_ID=:usageFrequencyUsagePeriodId, "
                + "ut.USAGE_FREQUENCY_COUNT=:usageFrequencyCount, "
                + "ut.REPEAT_USAGE_PERIOD_ID=:repeatUsagePeriodId, "
                + "ut.REPEAT_COUNT=:repeatCount, "
                + "ut.NOTES=:notes, "
                + "ut.ACTIVE=:active, "
                + "ut.LAST_MODIFIED_DATE=:dt, "
                + "ut.LAST_MODIFIED_BY=:curUser "
                + "WHERE "
                + " ut.USAGE_TEMPLATE_ID=:usageTemplateId AND "
                + " ("
                + "     l.LABEL_EN!=:labelEn OR "
                + "     ut.PROGRAM_ID!=:programId OR "
                + "     ut.UNIT_ID!=:unitId OR "
                + "     ut.FORECASTING_UNIT_ID!=:forecastingUnitId OR "
                + "     ut.LAG_IN_MONTHS!=:lagInMonths OR "
                + "     ut.USAGE_TYPE_ID!=:usageTypeId OR "
                + "     ut.NO_OF_PATIENTS!=:noOfPatients OR "
                + "     ut.NO_OF_FORECASTING_UNITS!=:noOfForecastingUnits OR "
                + "     ut.ONE_TIME_USAGE!=:oneTimeUsage OR "
                + "     (ut.USAGE_FREQUENCY_USAGE_PERIOD_ID is null AND :usageFrequencyUsagePeriodId is not null) OR "
                + "     (ut.USAGE_FREQUENCY_USAGE_PERIOD_ID is not null AND :usageFrequencyUsagePeriodId is null) OR "
                + "     ut.USAGE_FREQUENCY_USAGE_PERIOD_ID!=:usageFrequencyUsagePeriodId OR "
                + "     (ut.USAGE_FREQUENCY_COUNT is null AND :usageFrequencyCount is not null) OR "
                + "     (ut.USAGE_FREQUENCY_COUNT is not null AND :usageFrequencyCount is null) OR "
                + "     ut.USAGE_FREQUENCY_COUNT!=:usageFrequencyCount OR "
                + "     (ut.REPEAT_USAGE_PERIOD_ID is null AND :repeatUsagePeriodId is not null) OR "
                + "     (ut.REPEAT_USAGE_PERIOD_ID is not null AND :repeatUsagePeriodId is null) OR "
                + "     ut.REPEAT_USAGE_PERIOD_ID!=:repeatUsagePeriodId OR "
                + "     (ut.REPEAT_COUNT is null AND :repeatCount is not null) OR "
                + "     (ut.REPEAT_COUNT is not null AND :repeatCount is null) OR "
                + "     ut.REPEAT_COUNT!=:repeatCount OR "
                + "     (ut.NOTES is null AND :notes is not null) OR "
                + "     (ut.NOTES is not null AND :notes is null) OR "
                + "     ut.NOTES!=:notes OR "
                + "     ut.ACTIVE!=:active "
                + " )";
        if (paramList.size() > 0) {
            updatedRows = namedParameterJdbcTemplate.batchUpdate(sql, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
            rows += Arrays.stream(updatedRows).filter(i -> i > 0).count();
        }
        return rows;
    }

}
