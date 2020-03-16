/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.RegionDao;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgRegionDTORowMapper;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.rowMapper.RegionRowMapper;
import cc.altius.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RegionDaoImpl implements RegionDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
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
    @Transactional
    public int addRegion(Region region, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_region").usingGeneratedKeyColumns("REGION_ID");
        Map<String, Object> params = new HashMap<>();
        int labelId = this.labelDao.addLabel(region.getLabel(), curUser);
        params.put("REALM_COUNTRY_ID", region.getRealmCountry().getRealmCountryId());
        params.put("LABEL_ID", labelId);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("ACTIVE", 1);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int editRegion(Region region, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "UPDATE rm_region r "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID` "
                + "SET l.`LABEL_EN`=:engLabel, "
                + "l.`LAST_MODIFIED_BY`=:lastModifiedBy, "
                + "l.`LAST_MODIFIED_DATE`=:lastModifiedDate, "
                + "r.`REALM_COUNTRY_ID`=:realmCountryId, "
                + "r.`LAST_MODIFIED_BY`=:lastModifiedBy, "
                + "r.`LAST_MODIFIED_DATE`=:lastModifiedDate, "
                + "r.`ACTIVE`=:active "
                + "WHERE r.`REGION_ID`=:regionId;";
        Map<String, Object> map = new HashMap<>();
        map.put("engLabel", region.getLabel().getLabel_en());
        map.put("lastModifiedBy", curUser);
        map.put("lastModifiedDate", curDate);
        map.put("realmCountryId", region.getRealmCountry().getRealmCountryId());
        map.put("active", region.isActive());
        map.put("regionId", region.getRegionId());
        return namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Region> getRegionList(boolean active) {
        String sql = "SELECT rg.`REGION_ID`,lrg.`LABEL_ID` AS RG_LABEL_ID,lrg.`LABEL_EN` AS RG_LABEL_EN "
                + ",lrg.`LABEL_FR` AS RG_LABEL_FR "
                + ",lrg.`LABEL_SP` AS RG_LABEL_SP "
                + ",lrg.`LABEL_PR` AS RG_LABEL_PR,"
                + "lc.`LABEL_ID` AS CU_LABEL_ID, "
                + "lc.`LABEL_EN` AS CU_LABEL_EN "
                + ",lc.`LABEL_FR` AS CU_LABEL_FR "
                + ",lc.`LABEL_SP` AS CU_LABEL_SP "
                + ",lc.`LABEL_PR` AS CU_LABEL_PR,"
                + "lr.`LABEL_ID` AS RM_LABEL_ID, "
                + "lr.`LABEL_EN` AS RM_LABEL_EN "
                + ",lr.`LABEL_FR` AS RM_LABEL_FR "
                + ",lr.`LABEL_SP` AS RM_LABEL_SP "
                + ",lr.`LABEL_PR` AS RM_LABEL_PR, "
                + "rg.`ACTIVE`,rg.`LAST_MODIFIED_DATE`,lastModifiedBy.`USERNAME` AS LAST_MODIFIED_BY,rc.`REALM_COUNTRY_ID`,c.*,r.* "
                + " FROM rm_region rg "
                + "LEFT JOIN ap_label lrg ON lrg.`LABEL_ID`=rg.`LABEL_ID` "
                + "LEFT JOIN rm_realm_country rc ON rc.`REALM_COUNTRY_ID`=rg.`REALM_COUNTRY_ID` "
                + "LEFT JOIN ap_country c ON c.`COUNTRY_ID`=rc.`COUNTRY_ID` "
                + "LEFT JOIN ap_label lc ON lc.`LABEL_ID`=c.`LABEL_ID` "
                + "LEFT JOIN rm_realm r ON r.`REALM_ID`=rc.`REALM_ID` "
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=r.`LABEL_ID` "
                + "LEFT JOIN us_user lastModifiedBy ON lastModifiedBy.`USER_ID`=rg.`LAST_MODIFIED_BY`;";
        return this.jdbcTemplate.query(sql, new RegionRowMapper());

    }

    @Override
    public List<PrgRegionDTO> getRegionListForSync(String lastSyncDate, int realmId) {
        String sql = "SELECT r.`ACTIVE`,r.`CAPACITY_CBM`,r.`LABEL_ID`,r.`REGION_ID`,l.`LABEL_EN` AS `REGION_LABEL_EN`,\n"
                + "l.`LABEL_FR` AS `REGION_LABEL_FR`,l.`LABEL_PR` AS `REGION_LABEL_PR`,l.`LABEL_SP` AS `REGION_LABEL_SP`\n"
                + ",rrc.`REALM_ID`\n"
                + "                FROM rm_region r\n"
                + "                LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID`\n"
                + "                LEFT JOIN rm_realm_country rrc ON rrc.`REALM_COUNTRY_ID`=r.`REALM_COUNTRY_ID`\n"
                + "                WHERE rrc.`REALM_ID`=:realmId";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " AND r.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        params.put("realmId", realmId);
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgRegionDTORowMapper());
    }

}
