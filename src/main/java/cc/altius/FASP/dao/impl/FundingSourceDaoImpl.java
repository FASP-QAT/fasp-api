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
import cc.altius.FASP.model.FundingSourceType;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleFundingSourceObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.FundingSourceListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.FundingSourceResultSetExtractor;
import cc.altius.FASP.model.rowMapper.FundingSourceTypeRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleFundingSourceObjectRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.FASP.utils.SuggestedDisplayName;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
            + "    fs.`LABEL_ID`, fs.`LABEL_EN`, fs.`LABEL_FR`, fs.`LABEL_PR`, fs.`LABEL_SP`,  "
            + "    r.REALM_ID, r.`LABEL_ID` `REALM_LABEL_ID`, r.`LABEL_EN` `REALM_LABEL_EN` , r.`LABEL_FR` `REALM_LABEL_FR`, r.`LABEL_PR` `REALM_LABEL_PR`, r.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE,  "
            + "    fs.ALLOWED_IN_BUDGET, "
            + "    p.PROGRAM_ID `P_ID`, p.PROGRAM_CODE `P_CODE`, p.LABEL_ID `P_LABEL_ID`, p.LABEL_EN `P_LABEL_EN`, p.LABEL_FR `P_LABEL_FR`, p.LABEL_SP `P_LABEL_SP`, p.LABEL_PR `P_LABEL_PR`, "
            + "    fst.`FUNDING_SOURCE_TYPE_ID` `FST_ID`, fst.`LABEL_ID` `FST_LABEL_ID`, fst.`LABEL_EN` `FST_LABEL_EN`, fst.`LABEL_FR` `FST_LABEL_FR`, fst.`LABEL_SP` `FST_LABEL_SP`, fst.`LABEL_PR` `FST_LABEL_PR`, fst.`FUNDING_SOURCE_TYPE_CODE` `FST_CODE`, "
            + "    fs.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, fs.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, fs.LAST_MODIFIED_DATE  "
            + "FROM vw_funding_source fs  "
            + "LEFT JOIN vw_realm r ON fs.`REALM_ID`=r.`REALM_ID`  "
            + "LEFT JOIN vw_funding_source_type fst ON fs.FUNDING_SOURCE_TYPE_ID=fst.FUNDING_SOURCE_TYPE_ID "
            + "LEFT JOIN rm_program_funding_source pfs ON fs.FUNDING_SOURCE_ID=pfs.FUNDING_SOURCE_ID "
            + "LEFT JOIN vw_program p ON pfs.PROGRAM_ID=p.PROGRAM_ID "
            + "LEFT JOIN us_user cb ON fs.CREATED_BY=cb.USER_ID  "
            + "LEFT JOIN us_user lmb ON fs.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    private String fundingSourceTypeSqlString = " SELECT pa.FUNDING_SOURCE_TYPE_ID, pa.FUNDING_SOURCE_TYPE_CODE, "
            + " r.REALM_ID, r.REALM_CODE, r.`LABEL_ID` `REALM_LABEL_ID` ,r.`LABEL_EN` `REALM_LABEL_EN`, r.`LABEL_FR` `REALM_LABEL_FR`, r.`LABEL_PR` `REALM_LABEL_PR`, r.`LABEL_SP` `REALM_LABEL_SP`,"
            + " pa.`LABEL_ID` ,pa.`LABEL_EN`, pa.`LABEL_FR`, pa.`LABEL_PR`, pa.`LABEL_SP`,"
            + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pa.ACTIVE, pa.CREATED_DATE, pa.LAST_MODIFIED_DATE "
            + " FROM vw_funding_source_type pa "
            + "  LEFT JOIN vw_realm r ON pa.REALM_ID=r.REALM_ID "
            + "  LEFT JOIN us_user cb ON pa.CREATED_BY=cb.USER_ID "
            + "  LEFT JOIN us_user lmb ON pa.LAST_MODIFIED_BY=lmb.USER_ID "
            + "  WHERE TRUE ";

    @Override
    @Transactional
    public int addFundingSource(FundingSource f, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_funding_source").usingColumns("FUNDING_SOURCE_CODE", "REALM_ID", "LABEL_ID", "ALLOWED_IN_BUDGET", "FUNDING_SOURCE_TYPE_ID", "ACTIVE", "CREATED_BY", "CREATED_DATE", "LAST_MODIFIED_BY", "LAST_MODIFIED_DATE").usingGeneratedKeyColumns("FUNDING_SOURCE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("FUNDING_SOURCE_CODE", f.getFundingSourceCode());
        params.put("REALM_ID", f.getRealm().getId());
        int labelId = this.labelDao.addLabel(f.getLabel(), LabelConstants.RM_FUNDING_SOURCE, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ALLOWED_IN_BUDGET", true);
        params.put("FUNDING_SOURCE_TYPE_ID", f.getFundingSourceType().getId());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int fundingSourceId = si.executeAndReturnKey(params).intValue();
        MapSqlParameterSource[] batchParams;
        batchParams = new MapSqlParameterSource[f.getProgramList().size()];
        si = null;
        int x = 0;
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_funding_source");
        for (SimpleObject program : f.getProgramList()) {
            params = new HashMap<>();
            params.put("PROGRAM_ID", program.getId());
            params.put("FUNDING_SOURCE_ID", fundingSourceId);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }
        si.executeBatch(batchParams);
        return fundingSourceId;
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
        params.put("allowedInBudget", f.isAllowedInBudget());
        params.put("fundingSourceTypeId", f.getFundingSourceType().getId());
        params.put("active", f.isActive());
        params.put("labelEn", f.getLabel().getLabel_en());
        int linesUpdated = this.namedParameterJdbcTemplate.update("UPDATE rm_funding_source fs LEFT JOIN ap_label fsl on fs.LABEL_ID=fsl.LABEL_ID SET fs.`FUNDING_SOURCE_CODE`=:fundingSourceCode, fs.`ALLOWED_IN_BUDGET`=:allowedInBudget, fs.`FUNDING_SOURCE_TYPE_ID`=:fundingSourceTypeId, fs.`ACTIVE`=:active, fs.`LAST_MODIFIED_BY`=:curUser, fs.`LAST_MODIFIED_DATE`=:curDate, fsl.LABEL_EN=:labelEn, fsl.LAST_MODIFIED_BY=:curUser, fsl.LAST_MODIFIED_DATE=:curDate WHERE fs.FUNDING_SOURCE_ID=:fundingSourceId", params);
        this.namedParameterJdbcTemplate.update("DELETE pfs.* FROM rm_program_funding_source pfs WHERE pfs.FUNDING_SOURCE_ID=:fundingSourceId", params);
        MapSqlParameterSource[] batchParams;
        batchParams = new MapSqlParameterSource[f.getProgramList().size()];
        int x = 0;
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_funding_source");
        for (SimpleObject program : f.getProgramList()) {
            params = new HashMap<>();
            params.put("PROGRAM_ID", program.getId());
            params.put("FUNDING_SOURCE_ID", f.getFundingSourceId());
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }
        si.executeBatch(batchParams);
        return linesUpdated;
    }

    @Override
    public List<FundingSource> getFundingSourceList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceListResultSetExtractor());
    }

    @Override
    public List<FundingSource> getFundingSourceList(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceListResultSetExtractor());
    }

    @Override
    public FundingSource getFundingSourceById(int fundingSourceId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append("AND fs.`FUNDING_SOURCE_ID`=:fundingSourceId");
        Map<String, Object> params = new HashMap<>();
        params.put("fundingSourceId", fundingSourceId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceResultSetExtractor());
    }

    @Override
    public List<FundingSource> getFundingSourceListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND (fs.LAST_MODIFIED_DATE>:lastSyncDate OR EXISTS (SELECT 1 FROM rm_program_funding_source pfs WHERE fs.FUNDING_SOURCE_ID = pfs.FUNDING_SOURCE_ID AND pfs.LAST_MODIFIED_DATE > :lastSyncDate)) ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fs", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceListResultSetExtractor());
    }

    @Override
    public String getDisplayName(int realmId, String name, CustomUserDetails curUser) {
        String extractedName = SuggestedDisplayName.getAlphaNumericString(name, SuggestedDisplayName.FUNDING_SOURCE_LENGTH);
        String sqlString = "SELECT COUNT(*) CNT FROM rm_funding_source fs WHERE fs.REALM_ID=:realmId AND UPPER(LEFT(fs.FUNDING_SOURCE_CODE,:len))=:extractedName";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("len", extractedName.length());
        params.put("extractedName", extractedName);
        int cnt = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        return SuggestedDisplayName.getFinalDisplayName(extractedName, cnt);
    }

    @Override
    public List<SimpleFundingSourceObject> getFundingSourceDropdownList(CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT fs.FUNDING_SOURCE_ID `ID`, fs.LABEL_ID, fs.LABEL_EN, fs.LABEL_FR, fs.LABEL_SP, fs.LABEL_PR, fs.FUNDING_SOURCE_CODE `CODE`, fst.`FUNDING_SOURCE_TYPE_ID` `FST_ID`, fst.`LABEL_ID` `FST_LABEL_ID`, fst.`LABEL_EN` `FST_LABEL_EN`, fst.`LABEL_FR` `FST_LABEL_FR`, fst.`LABEL_SP` `FST_LABEL_SP`, fst.`LABEL_PR` `FST_LABEL_PR`, fst.`FUNDING_SOURCE_TYPE_CODE` `FST_CODE` FROM vw_funding_source fs LEFT JOIN vw_funding_source_type fst ON fs.FUNDING_SOURCE_TYPE_ID=fst.FUNDING_SOURCE_TYPE_ID WHERE fs.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(stringBuilder, params, "fs", curUser);
        stringBuilder.append(" ORDER BY fs.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleFundingSourceObjectRowMapper());
    }

    @Override
    public List<SimpleCodeObject> getFundingSourceForProgramsDropdownList(int[] programIds, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT fs.FUNDING_SOURCE_ID `ID`, fs.LABEL_ID, fs.LABEL_EN, fs.LABEL_FR, fs.LABEL_SP, fs.LABEL_PR, fs.FUNDING_SOURCE_CODE `CODE` FROM vw_program p LEFT JOIN rm_program_funding_source pfs ON p.PROGRAM_ID=pfs.PROGRAM_ID LEFT JOIN vw_funding_source fs ON pfs.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID WHERE FIND_IN_SET(p.PROGRAM_ID, :programIds) ");
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", ArrayUtils.convertArrayToString(programIds));
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        stringBuilder.append(" GROUP BY fs.FUNDING_SOURCE_ID");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<SimpleCodeObject> getFundingSourceTypeForProgramsDropdownList(int[] programIds, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT fst.FUNDING_SOURCE_TYPE_ID `ID`, fst.LABEL_ID, fst.LABEL_EN, fst.LABEL_FR, fst.LABEL_SP, fst.LABEL_PR, fst.FUNDING_SOURCE_TYPE_CODE `CODE` FROM vw_program p LEFT JOIN rm_program_funding_source pfs ON p.PROGRAM_ID=pfs.PROGRAM_ID LEFT JOIN rm_funding_source fs ON pfs.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID LEFT JOIN vw_funding_source_type fst ON fs.FUNDING_SOURCE_TYPE_ID=fst.FUNDING_SOURCE_TYPE_ID WHERE FIND_IN_SET(p.PROGRAM_ID, :programIds) ");
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", ArrayUtils.convertArrayToString(programIds));
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        stringBuilder.append(" GROUP BY fst.FUNDING_SOURCE_TYPE_ID");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    @Transactional
    public int addFundingSourceType(FundingSourceType p, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_funding_source_type").usingGeneratedKeyColumns("FUNDING_SOURCE_TYPE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("FUNDING_SOURCE_TYPE_CODE", p.getFundingSourceTypeCode());
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(p.getLabel(), LabelConstants.RM_FUNDING_SOURCE_TYPE, curUser.getUserId());
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
    public int updateFundingSourceType(FundingSourceType p, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("fundingSourceTypeId", p.getFundingSourceTypeId());
        params.put("labelEn", p.getLabel().getLabel_en());
        params.put("fundingSourceTypeCode", p.getFundingSourceTypeCode());
        params.put("active", p.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update("UPDATE rm_funding_source_type pa "
                + "LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID SET "
                + " pa.FUNDING_SOURCE_TYPE_CODE=:fundingSourceTypeCode, "
                + " pa.ACTIVE=:active, "
                + " pa.LAST_MODIFIED_BY=:curUser, "
                + " pa.LAST_MODIFIED_DATE=:curDate, "
                + " pal.LABEL_EN=:labelEn, "
                + " pal.LAST_MODIFIED_BY=:curUser, "
                + " pal.LAST_MODIFIED_DATE=:curDate "
                + " WHERE pa.FUNDING_SOURCE_TYPE_ID=:fundingSourceTypeId", params);
    }

    @Override
    public List<FundingSourceType> getFundingSourceTypeList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.fundingSourceTypeSqlString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceTypeRowMapper());
    }

    @Override
    public List<FundingSourceType> getFundingSourceTypeByRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.fundingSourceTypeSqlString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceTypeRowMapper());
    }

    @Override
    public FundingSourceType getFundingSourceTypeById(int fundingSourceTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.fundingSourceTypeSqlString).append(" AND pa.FUNDING_SOURCE_TYPE_ID=:fundingSourceTypeId ");
        Map<String, Object> params = new HashMap<>();
        params.put("fundingSourceTypeId", fundingSourceTypeId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new FundingSourceTypeRowMapper());
    }

    @Override
    public List<FundingSourceType> getFundingSourceTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.fundingSourceTypeSqlString).append(" AND pa.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "pa", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new FundingSourceTypeRowMapper());
    }
}
