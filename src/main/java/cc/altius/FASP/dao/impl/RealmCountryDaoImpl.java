/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.RealmCountryDao;
import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.rowMapper.RealmCountryPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.RealmCountryRowMapper;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class RealmCountryDaoImpl implements RealmCountryDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListString = "SELECT "
            + "    rc.REALM_COUNTRY_ID, rc.AIR_FREIGHT_PERC, rc.SEA_FREIGHT_PERC, rc.SHIPPED_TO_ARRIVED_AIR_LEAD_TIME, rc.SHIPPED_TO_ARRIVED_SEA_LEAD_TIME, rc.ARRIVED_TO_DELIVERED_LEAD_TIME, "
            + "    r.REALM_ID, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, r.REALM_CODE, "
            + "    c.COUNTRY_ID, c.COUNTRY_CODE, cl.LABEL_ID `COUNTRY_LABEL_ID`,cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_PR `COUNTRY_LABEL_PR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, "
            + "    cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CURRENCY_SYMBOL, cu.CONVERSION_RATE_TO_USD, cul.LABEL_ID `CURRENCY_LABEL_ID`, cul.LABEL_EN `CURRENCY_LABEL_EN`, cul.LABEL_FR `CURRENCY_LABEL_FR`, cul.LABEL_PR `CURRENCY_LABEL_PR`, cul.LABEL_SP `CURRENCY_LABEL_SP`, "
            + "    un.UNIT_ID, un.UNIT_CODE, unl.LABEL_ID `UNIT_LABEL_ID`, unl.LABEL_EN `UNIT_LABEL_EN`, unl.LABEL_FR `UNIT_LABEL_FR`, unl.LABEL_PR `UNIT_LABEL_PR`, unl.LABEL_SP `UNIT_LABEL_SP`, "
            + "    rc.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, rc.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, rc.LAST_MODIFIED_DATE "
            + "FROM rm_realm_country rc "
            + "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + "LEFT JOIN ap_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID "
            + "LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
            + "LEFT JOIN ap_unit un ON rc.PALLET_UNIT_ID=un.UNIT_ID "
            + "LEFT JOIN ap_label unl ON un.LABEL_ID=unl.LABEL_ID "
            + "LEFT JOIN us_user cb ON rc.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON rc.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int addRealmCountry(RealmCountry realmCountry, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_realm_country").usingGeneratedKeyColumns("REALM_COUNTRY_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", realmCountry.getRealm().getRealmId());
        params.put("COUNTRY_ID", realmCountry.getCountry().getCountryId());
        params.put("DEFAULT_CURRENCY_ID", realmCountry.getDefaultCurrency().getCurrencyId());
        params.put("PALLET_UNIT_ID", realmCountry.getPalletUnit().getUnitId());
        params.put("AIR_FREIGHT_PERC", realmCountry.getAirFreightPercentage());
        params.put("SEA_FREIGHT_PERC", realmCountry.getSeaFreightPercentage());
        params.put("ARRIVED_TO_DELIVERED_LEAD_TIME", realmCountry.getArrivedToDeliveredLeadTime());
        params.put("SHIPPED_TO_ARRIVED_AIR_LEAD_TIME", realmCountry.getShippedToArrivedAirLeadTime());
        params.put("SHIPPED_TO_ARRIVED_SEA_LEAD_TIME", realmCountry.getShippedToArrivedSeaLeadTime());
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("ACTIVE", true);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int updateRealmCountry(RealmCountry realmCountry, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_realm_country rc SET "
                + "DEFAULT_CURRENCY_ID=:defaultCurrencyId, "
//                + "PALLET_UNIT_ID=:palletUnitId, "
                + "AIR_FREIGHT_PERC=:airFreightPerc, "
                + "SEA_FREIGHT_PERC=:seaFreightPerc, "
                + "ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime, "
                + "SHIPPED_TO_ARRIVED_AIR_LEAD_TIME=:shippedToArrivedAirLeadTime, "
                + "SHIPPED_TO_ARRIVED_SEA_LEAD_TIME=:shippedToArrivedSeaLeadTime, "
                + "ACTIVE=:active, "
                + "LAST_MODIFIED_BY=:curUser, "
                + "LAST_MODIFIED_DATE=:curDate "
                + "WHERE REALM_COUNTRY_ID=:realmCountryId "
                + "AND ("
                + "DEFAULT_CURRENCY_ID!=:defaultCurrencyId OR "
//                + "PALLET_UNIT_ID!=:palletUnitId OR "
                + "AIR_FREIGHT_PERC!=:airFreightPerc OR "
                + "SEA_FREIGHT_PERC!=:seaFreightPerc OR "
                + "ARRIVED_TO_DELIVERED_LEAD_TIME!=:arrivedToDeliveredLeadTime OR "
                + "SHIPPED_TO_ARRIVED_AIR_LEAD_TIME!=:shippedToArrivedAirLeadTime OR "
                + "SHIPPED_TO_ARRIVED_SEA_LEAD_TIME!=:shippedToArrivedSeaLeadTime OR "
                + "ACTIVE!=:active) ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", realmCountry.getRealmCountryId());
        params.put("defaultCurrencyId", realmCountry.getDefaultCurrency().getCurrencyId());
//        params.put("palletUnitId", realmCountry.getPalletUnit().getUnitId());
        params.put("airFreightPerc", realmCountry.getAirFreightPercentage());
        params.put("seaFreightPerc", realmCountry.getSeaFreightPercentage());
        params.put("arrivedToDeliveredLeadTime", realmCountry.getArrivedToDeliveredLeadTime());
        params.put("shippedToArrivedAirLeadTime", realmCountry.getShippedToArrivedAirLeadTime());
        params.put("shippedToArrivedSeaLeadTime", realmCountry.getShippedToArrivedSeaLeadTime());
        params.put("lastModifiedDate", curDate);
        params.put("lastModifiedBy", curUser.getUserId());
        params.put("active", realmCountry.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<RealmCountry> getRealmCountryList(CustomUserDetails curUser) {
        String sqlString = this.sqlListString;
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", curUser.getRealm().getRealmId());
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND rc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new RealmCountryRowMapper());
    }

    @Override
    public RealmCountry getRealmCountryById(int realmCountryId, CustomUserDetails curUser) {
        String sqlString = this.sqlListString + " AND rc.REALM_COUNTRY_ID=:realmCountryId ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND rc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        params.put("realmCountryId", realmCountryId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new RealmCountryRowMapper());
    }

    @Override
    public RealmCountry getRealmCountryByRealmAndCountry(int realmId, int countryId, CustomUserDetails curUser) {
        String sqlString = this.sqlListString + " AND rc.REALM_ID=:realmId AND rc.COUNTRY_ID=:countryId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("countryId", countryId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountry> getRealmCountryListByRealmId(int realmId, CustomUserDetails curUser) {
        String sqlString = this.sqlListString + " AND rc.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND rc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountryPlanningUnit> getPlanningUnitListForRealmCountryId(int realmCountryId, boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,   "
                + "      rc.REALM_COUNTRY_ID, cl.LABEL_ID `REALM_COUNTRY_LABEL_ID`, cl.LABEL_EN `REALM_COUNTRY_LABEL_EN`, cl.LABEL_FR `REALM_COUNTRY_LABEL_FR`, cl.LABEL_PR `REALM_COUNTRY_LABEL_PR`, cl.LABEL_SP `REALM_COUNTRY_LABEL_SP`,  "
                + "      pu.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`,  "
                + "      rcpu.SKU_CODE, rcpu.MULTIPLIER, rcpu.GTIN,  "
                + "      rcpul.LABEL_ID, rcpul.LABEL_EN, rcpul.LABEL_FR, rcpul.LABEL_SP, rcpul.LABEL_PR, "
                + "      u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_SP  `UNIT_LABEL_SP`, ul.LABEL_PR  `UNIT_LABEL_PR`, "
                + "      rcpu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, rcpu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, rcpu.LAST_MODIFIED_DATE "
                + "      FROM rm_realm_country_planning_unit rcpu "
                + "      LEFT JOIN rm_realm_country rc  ON rc.REALM_COUNTRY_ID=rcpu.REALM_COUNTRY_ID "
                + "      LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "      LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID  "
                + "      LEFT JOIN ap_label rcpul ON rcpu.LABEL_ID=rcpul.LABEL_ID  "
                + "      LEFT JOIN rm_planning_unit pu ON rcpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
                + "      LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID  "
                + "      LEFT JOIN ap_unit u ON rcpu.UNIT_ID=u.UNIT_ID "
                + "      LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "      LEFT JOIN us_user cb ON rcpu.CREATED_BY=cb.USER_ID "
                + "      LEFT JOIN us_user lmb ON rcpu.LAST_MODIFIED_BY=lmb.USER_ID "
                + "      WHERE rcpu.REALM_COUNTRY_ID=:realmCountryId ";
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlString += " AND rcpu.ACTIVE ";
        }

        params.put("realmCountryId", realmCountryId);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new RealmCountryPlanningUnitRowMapper());
    }

    @Override
    @Transactional
    public int savePlanningUnitForCountry(RealmCountryPlanningUnit[] realmCountryPlanningUnits, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_realm_country_planning_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (RealmCountryPlanningUnit rcpu : realmCountryPlanningUnits) {
            if (rcpu.getRealmCountryPlanningUnitId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("PLANNING_UNIT_ID", rcpu.getPlanningUnit().getId());
                params.put("REALM_COUNTRY_ID", rcpu.getRealmCountry().getId());
                params.put("SKU_CODE", rcpu.getSkuCode());
                int labelId = this.labelDao.addLabel(rcpu.getLabel(), curUser.getUserId());
                params.put("LABEL_ID", labelId);
                params.put("MULTIPLIER", rcpu.getMultiplier());
                params.put("GTIN", rcpu.getGtin());
                params.put("UNIT_ID", rcpu.getUnit().getUnitId());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                // Update
                params = new HashMap<>();
                params.put("realmCountryPlanningUnitId", rcpu.getRealmCountryPlanningUnitId());
                params.put("skuCode", rcpu.getSkuCode());
                params.put("multiplier", rcpu.getMultiplier());
                params.put("gtin", rcpu.getGtin());
                params.put("label_en", rcpu.getLabel().getLabel_en());
                params.put("unitId", rcpu.getUnit().getUnitId());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", rcpu.isActive());
                updateList.add(new MapSqlParameterSource(params));
            }
        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        if (updateList.size() > 0) {
            SqlParameterSource[] updateParams = new SqlParameterSource[updateList.size()];
            String sqlString = "UPDATE "
                    + "rm_realm_country_planning_unit rcpu "
                    + "LEFT JOIN ap_label rcpul ON rcpu.LABEL_ID=rcpul.LABEL_ID "
                    + "SET "
                    + "rcpu.SKU_CODE=:skuCode, "
                    + "rcpu.MULTIPLIER=:multiplier, "
                    + "rcpu.GTIN=:gtin, "
                    + "rcpul.LABEL_EN=:label_en, "
                    + "rcpu.UNIT_ID=:unitId, "
                    + "rcpu.ACTIVE=:active, "
                    + "rcpu.LAST_MODIFIED_DATE=IF(rcpu.ACTIVE!=:active OR rcpu.SKU_CODE!=:skuCode OR rcpu.MULTIPLIER!=:multiplier OR rcpu.GTIN!=:gtin OR rcpul.LABEL_EN!=:label_en OR rcpu.UNIT_ID!=:unitId, :curDate, rcpu.LAST_MODIFIED_DATE), "
                    + "rcpu.LAST_MODIFIED_BY=IF(rcpu.ACTIVE!=:active OR rcpu.SKU_CODE!=:skuCode OR rcpu.MULTIPLIER!=:multiplier OR rcpu.GTIN!=:gtin OR rcpul.LABEL_EN!=:label_en OR rcpu.UNIT_ID!=:unitId, :curUser, rcpu.LAST_MODIFIED_BY), "
                    + "rcpul.LAST_MODIFIED_DATE=IF(rcpul.LABEL_EN!=:label_en, :curDate, rcpul.LAST_MODIFIED_DATE), "
                    + "rcpul.LAST_MODIFIED_BY=IF(rcpul.LABEL_EN!=:label_en, :curUser, rcpul.LAST_MODIFIED_BY) "
                    + "WHERE rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=:realmCountryPlanningUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<RealmCountry> getRealmCountryListForSync(String lastSyncDate, CustomUserDetails curUser) {
        String sqlString = this.sqlListString;
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND rc.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new RealmCountryRowMapper());
    }

}
