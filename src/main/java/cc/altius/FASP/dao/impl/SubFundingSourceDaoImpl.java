/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.SubFundingSourceDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgSubFundingSourceDTORowMapper;
import cc.altius.FASP.model.SubFundingSource;
import cc.altius.FASP.model.rowMapper.SubFundingSourceRowMapper;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<PrgSubFundingSourceDTO> getSubFundingSourceListForSync(String lastSyncDate, int realmId) {
        String sql = "SELECT fs.`REALM_ID`,sfs.`ACTIVE`,sfs.`FUNDING_SOURCE_ID`,sfs.`SUB_FUNDING_SOURCE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "                FROM rm_sub_funding_source sfs \n"
                + "                LEFT JOIN ap_label l ON l.`LABEL_ID`=sfs.`LABEL_ID`\n"
                + "                LEFT JOIN rm_funding_source fs ON fs.`FUNDING_SOURCE_ID`=sfs.`FUNDING_SOURCE_ID`\n"
                + "                WHERE fs.`REALM_ID`=:realmId";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " AND sfs.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        params.put("realmId", realmId);
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgSubFundingSourceDTORowMapper());
    }

    @Override
    @Transactional
    public int addSubFundingSource(SubFundingSource s, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_sub_funding_source").usingGeneratedKeyColumns("SUB_FUNDING_SOURCE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("FUNDING_SOURCE_ID", s.getFundingSource().getFundingSourceId());
        int labelId = this.labelDao.addLabel(s.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int subFundingSourceId = si.executeAndReturnKey(params).intValue();
        return subFundingSourceId;
    }

    @Override
    @Transactional
    public int updateSubFundingSource(SubFundingSource sfs, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("subFundingSourceId", sfs.getSubFundingSourceId());
        params.put("active", sfs.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("labelEn", sfs.getLabel().getLabel_en());
        return this.namedParameterJdbcTemplate.update("UPDATE rm_sub_funding_source sfs LEFT JOIN ap_label sfsl ON sfs.LABEL_ID=sfsl.LABEL_ID SET sfs.ACTIVE=:active, sfs.LAST_MODIFIED_BY=:curUser, sfs.LAST_MODIFIED_DATE=:curDate, sfsl.LABEL_EN=:labelEn, sfsl.LAST_MODIFIED_BY=:curUser, sfsl.LAST_MODIFIED_DATE=:curDate WHERE sfs.SUB_FUNDING_SOURCE_ID=:subFundingSourceId", params);
    }

    @Override
    public SubFundingSource getSubFundingSourceById(int subFundingSourceId, CustomUserDetails curUser) {
        String sql = "SELECT sfs.SUB_FUNDING_SOURCE_ID, sfsl.`LABEL_ID`, sfsl.`LABEL_EN` , sfsl.`LABEL_FR`, sfsl.`LABEL_PR`, sfsl.`LABEL_SP`, "
                + "     fs.FUNDING_SOURCE_ID, fsl.`LABEL_ID` `FUNDING_SOURCE_LABEL_ID`, fsl.`LABEL_EN` `FUNDING_SOURCE_LABEL_EN` , fsl.`LABEL_FR` `FUNDING_SOURCE_LABEL_FR`, fsl.`LABEL_PR` `FUNDING_SOURCE_LABEL_PR`, fsl.`LABEL_SP` `FUNDING_SOURCE_LABEL_SP`, "
                + "     r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
                + "	sfs.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, sfs.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, sfs.LAST_MODIFIED_DATE "
                + "FROM rm_sub_funding_source sfs "
                + "LEFT JOIN ap_label sfsl ON sfs.LABEL_ID=sfsl.LABEL_ID "
                + "LEFT JOIN rm_funding_source fs on sfs.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
                + "LEFT JOIN ap_label fsl ON fs.LABEL_ID=fsl.LABEL_ID "
                + "LEFT JOIN rm_realm r on fs.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON sfs.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON sfs.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE sfs.SUB_FUNDING_SOURCE_ID=? ";
        return this.jdbcTemplate.queryForObject(sql, new SubFundingSourceRowMapper(), subFundingSourceId);
    }

    @Override
    public List<SubFundingSource> getSubFundingSourceList(CustomUserDetails curUser) {

        String sql = "SELECT sfs.SUB_FUNDING_SOURCE_ID, sfsl.`LABEL_ID`, sfsl.`LABEL_EN` , sfsl.`LABEL_FR`, sfsl.`LABEL_PR`, sfsl.`LABEL_SP`, "
                + "     fs.FUNDING_SOURCE_ID, fsl.`LABEL_ID` `FUNDING_SOURCE_LABEL_ID`, fsl.`LABEL_EN` `FUNDING_SOURCE_LABEL_EN` , fsl.`LABEL_FR` `FUNDING_SOURCE_LABEL_FR`, fsl.`LABEL_PR` `FUNDING_SOURCE_LABEL_PR`, fsl.`LABEL_SP` `FUNDING_SOURCE_LABEL_SP`, "
                + "     r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
                + "	sfs.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, sfs.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, sfs.LAST_MODIFIED_DATE "
                + "FROM rm_sub_funding_source sfs "
                + "LEFT JOIN ap_label sfsl ON sfs.LABEL_ID=sfsl.LABEL_ID "
                + "LEFT JOIN rm_funding_source fs on sfs.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
                + "LEFT JOIN ap_label fsl ON fs.LABEL_ID=fsl.LABEL_ID "
                + "LEFT JOIN rm_realm r on fs.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON sfs.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON sfs.LAST_MODIFIED_BY=lmb.USER_ID ";
        return this.jdbcTemplate.query(sql, new SubFundingSourceRowMapper());
    }

}
