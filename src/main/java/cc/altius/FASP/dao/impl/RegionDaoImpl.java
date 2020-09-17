/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.RegionDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.rowMapper.RegionRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.utils.DateUtils;
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
public class RegionDaoImpl implements RegionDao {

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
            + "    re.REGION_ID, rc.REALM_COUNTRY_ID, re.GLN, re.CAPACITY_CBM, "
            + "    rel.LABEL_ID, rel.LABEL_EN, rel.LABEL_FR, rel.LABEL_SP, rel.LABEL_PR, "
            + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
            + "    c.COUNTRY_ID, cl.`LABEL_ID` `COUNTRY_LABEL_ID`, cl.`LABEL_EN` `COUNTRY_LABEL_EN` , cl.`LABEL_FR` `COUNTRY_LABEL_FR`, cl.`LABEL_PR` `COUNTRY_LABEL_PR`, cl.`LABEL_SP` `COUNTRY_LABEL_SP`, c.COUNTRY_CODE, "
            + "    re.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, re.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, re.LAST_MODIFIED_DATE "
            + " FROM rm_region re "
            + " LEFT JOIN ap_label rel ON re.LABEL_ID=rel.LABEL_ID "
            + " LEFT JOIN rm_realm_country rc on re.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
            + " LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
            + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + " LEFT JOIN us_user cb ON re.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON re.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE ";

    @Override
    @Transactional
    public int addRegion(Region region, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_region").usingGeneratedKeyColumns("REGION_ID");
        Map<String, Object> params = new HashMap<>();
        int labelId = this.labelDao.addLabel(region.getLabel(), LabelConstants.RM_REGION, curUser.getUserId());
        params.put("REALM_COUNTRY_ID", region.getRealmCountry().getRealmCountryId());
        params.put("LABEL_ID", labelId);
        params.put("GLN", region.getGln());
        params.put("CAPACITY_CBM", region.getCapacityCbm());
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("ACTIVE", true);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateRegion(Region region, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "UPDATE rm_region re LEFT JOIN ap_label rel ON re.`LABEL_ID`=rel.`LABEL_ID` "
                + "SET "
                + "re.`ACTIVE`=:active, "
                + "re.`GLN`=:gln, "
                + "re.`CAPACITY_CBM`=:capacityCbm, "
                + "re.`LAST_MODIFIED_BY`=IF(re.`ACTIVE`!=:active OR re.`GLN`!=:gln OR re.`CAPACITY_CBM`!=:capacityCbm, :curUser, re.LAST_MODIFIED_BY), "
                + "re.`LAST_MODIFIED_DATE`=IF(re.`ACTIVE`!=:active OR re.`GLN`!=:gln OR re.`CAPACITY_CBM`!=:capacityCbm, :curDate, re.LAST_MODIFIED_DATE), "
                + "rel.LABEL_EN=:labelEn, "
                + "rel.`LAST_MODIFIED_BY`=IF(rel.LABEL_EN!=:labelEn, :curUser, rel.LAST_MODIFIED_BY), "
                + "rel.`LAST_MODIFIED_DATE`=IF(rel.LABEL_EN!=:labelEn, :curDate, rel.LAST_MODIFIED_DATE) "
                + "WHERE re.`REGION_ID`=:regionId;";
        Map<String, Object> map = new HashMap<>();
        map.put("labelEn", region.getLabel().getLabel_en());
        map.put("gln", region.getGln());
        map.put("capacityCbm", region.getCapacityCbm());
        map.put("curUser", curUser.getUserId());
        map.put("curDate", curDate);
        map.put("active", region.isActive());
        map.put("regionId", region.getRegionId());
        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Region> getRegionList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RegionRowMapper());
    }

    @Override
    public List<Region> getRegionListByRealmCountryId(int realmCountryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND re.REALM_COUNTRY_ID=:realmCountryId");
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", realmCountryId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RegionRowMapper());
    }

    @Override
    public Region getRegionById(int regionId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND re.REGION_ID=:regionId");
        Map<String, Object> params = new HashMap<>();
        params.put("regionId", regionId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new RegionRowMapper());
    }

    @Override
    public List<Region> getRegionListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND re.LAST_MODIFIED_DATE>:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RegionRowMapper());
    }

}
