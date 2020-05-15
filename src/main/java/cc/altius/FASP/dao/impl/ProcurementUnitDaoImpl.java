/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProcurementUnitDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementUnit;
import cc.altius.FASP.model.rowMapper.ProcurementUnitRowMapper;
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

/**
 *
 * @author akil
 */
@Repository
public class ProcurementUnitDaoImpl implements ProcurementUnitDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private final String sqlListString = "SELECT"
            + "    pru.PROCUREMENT_UNIT_ID, prul.LABEL_ID, prul.LABEL_EN, prul.LABEL_FR, prul.LABEL_SP, prul.LABEL_PR,"
            + "    s.SUPPLIER_ID, sl.LABEL_ID `SUPPLIER_LABEL_ID`, sl.LABEL_EN `SUPPLIER_LABEL_EN`, sl.LABEL_FR `SUPPLIER_LABEL_FR`, sl.LABEL_SP `SUPPLIER_LABEL_SP`, sl.LABEL_PR `SUPPLIER_LABEL_PR`,"
            + "    u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_SP `UNIT_LABEL_SP`, ul.LABEL_PR `UNIT_LABEL_PR`,"
            + "    pu.PLANNING_UNIT_ID, pu.MULTIPLIER `PLANNING_UNIT_MULTIPLIER`, pu.SKU_CODE, fu.FORECASTING_UNIT_ID, "
            + "    pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
            + "    ful.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, ful.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, ful.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, ful.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, ful.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, "
            + "    fugl.LABEL_ID `GENERIC_LABEL_ID`, fugl.LABEL_EN `GENERIC_LABEL_EN`, fugl.LABEL_FR `GENERIC_LABEL_FR`, fugl.LABEL_PR `GENERIC_LABEL_PR`, fugl.LABEL_SP `GENERIC_LABEL_SP`, "
            + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
            + "    pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, "
            + "    tc.TRACER_CATEGORY_ID, tcl.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tcl.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tcl.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tcl.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tcl.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, "
            + "    puu.UNIT_ID `PLANNING_UNIT_UNIT_ID`, puu.UNIT_CODE `PLANNING_UNIT_UNIT_CODE`, puul.LABEL_ID `PLANNING_UNIT_UNIT_LABEL_ID`, puul.LABEL_EN `PLANNING_UNIT_UNIT_LABEL_EN`, puul.LABEL_FR `PLANNING_UNIT_UNIT_LABEL_FR`, puul.LABEL_PR `PLANNING_UNIT_UNIT_LABEL_PR`, puul.LABEL_SP `PLANNING_UNIT_UNIT_LABEL_SP`, "
            + "    pru.HEIGHT_QTY, pru.WIDTH_QTY, pru.LENGTH_QTY, pru.WEIGHT_QTY, pru.LABELING, pru.MULTIPLIER, pru.UNITS_PER_CONTAINER, pru.LABELING,"
            + "    hu.UNIT_ID  `HEIGHT_UNIT_ID`, hu.UNIT_CODE  `HEIGHT_UNIT_CODE`, hul.LABEL_ID  `HEIGHT_UNIT_LABEL_ID`, hul.LABEL_EN  `HEIGHT_UNIT_LABEL_EN`, hul.LABEL_FR  `HEIGHT_UNIT_LABEL_FR`, hul.LABEL_PR  `HEIGHT_UNIT_LABEL_PR`, hul.LABEL_SP  `HEIGHT_UNIT_LABEL_SP`, "
            + "    lu.UNIT_ID  `LENGTH_UNIT_ID`, lu.UNIT_CODE  `LENGTH_UNIT_CODE`, lul.LABEL_ID  `LENGTH_UNIT_LABEL_ID`, lul.LABEL_EN  `LENGTH_UNIT_LABEL_EN`, lul.LABEL_FR  `LENGTH_UNIT_LABEL_FR`, lul.LABEL_PR  `LENGTH_UNIT_LABEL_PR`, lul.LABEL_SP  `LENGTH_UNIT_LABEL_SP`, "
            + "    wu.UNIT_ID   `WIDTH_UNIT_ID`, wu.UNIT_CODE   `WIDTH_UNIT_CODE`, wul.LABEL_ID   `WIDTH_UNIT_LABEL_ID`, wul.LABEL_EN   `WIDTH_UNIT_LABEL_EN`, wul.LABEL_FR   `WIDTH_UNIT_LABEL_FR`, wul.LABEL_PR   `WIDTH_UNIT_LABEL_PR`, wul.LABEL_SP   `WIDTH_UNIT_LABEL_SP`, "
            + "    weu.UNIT_ID `WEIGHT_UNIT_ID`, weu.UNIT_CODE `WEIGHT_UNIT_CODE`, weul.LABEL_ID `WEIGHT_UNIT_LABEL_ID`, weul.LABEL_EN `WEIGHT_UNIT_LABEL_EN`, weul.LABEL_FR `WEIGHT_UNIT_LABEL_FR`, weul.LABEL_PR `WEIGHT_UNIT_LABEL_PR`, weul.LABEL_SP `WEIGHT_UNIT_LABEL_SP`,"
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pru.ACTIVE, pru.CREATED_DATE, pru.LAST_MODIFIED_DATE  "
            + " FROM rm_procurement_unit pru"
            + " LEFT JOIN ap_label prul ON pru.LABEL_ID=prul.LABEL_ID"
            + " LEFT JOIN rm_supplier s ON pru.SUPPLIER_ID=s.SUPPLIER_ID"
            + " LEFT JOIN ap_label sl ON s.LABEL_ID=sl.LABEL_ID"
            + " LEFT JOIN ap_unit u ON pru.UNIT_ID=u.UNIT_ID"
            + " LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID"
            + " LEFT JOIN rm_planning_unit pu ON pru.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID"
            + " LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID"
            + " LEFT JOIN rm_forecasting_unit fu on pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
            + " LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID  "
            + " LEFT JOIN ap_label fugl ON fu.GENERIC_LABEL_ID=fugl.LABEL_ID  "
            + " LEFT JOIN rm_realm r ON fu.REALM_ID=r.REALM_ID  "
            + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID  "
            + " LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
            + " LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID  "
            + " LEFT JOIN rm_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID  "
            + " LEFT JOIN ap_label tcl ON tc.LABEL_ID=tcl.LABEL_ID  "
            + " LEFT JOIN ap_unit puu ON pu.UNIT_ID=puu.UNIT_ID "
            + " LEFT JOIN ap_label puul ON puu.LABEL_ID=puul.LABEL_ID"
            + " LEFT JOIN ap_unit hu ON pru.HEIGHT_UNIT_ID=hu.UNIT_ID"
            + " LEFT JOIN ap_label hul ON hu.LABEL_ID=hul.LABEL_ID"
            + " LEFT JOIN ap_unit lu ON pru.LENGTH_UNIT_ID=lu.UNIT_ID"
            + " LEFT JOIN ap_label lul ON lu.LABEL_ID=lul.LABEL_ID"
            + " LEFT JOIN ap_unit wu ON pru.WIDTH_UNIT_ID=wu.UNIT_ID"
            + " LEFT JOIN ap_label wul ON wu.LABEL_ID=wul.LABEL_ID"
            + " LEFT JOIN ap_unit weu ON pru.WEIGHT_UNIT_ID=weu.UNIT_ID"
            + " LEFT JOIN ap_label weul ON weu.LABEL_ID=weul.LABEL_ID"
            + " LEFT JOIN us_user cb ON pru.CREATED_BY=cb.USER_ID  "
            + " LEFT JOIN us_user lmb ON pru.LAST_MODIFIED_BY=lmb.USER_ID"
            + " WHERE TRUE ";

    @Override
    public List<ProcurementUnit> getProcurementUnitList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder.append(" AND pru.ACTIVE ");
        }
        if (curUser.getRealm().getRealmId() != -1) {
            sqlStringBuilder.append(" AND fu.REALM_ID=:realmId ");
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementUnitRowMapper());
    }

    @Override
    public List<ProcurementUnit> getProcurementUnitListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder.append(" AND pru.ACTIVE ");
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", realmId, curUser);
        params.put("userRealmId", realmId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementUnitRowMapper());
    }

    @Override
    public List<ProcurementUnit> getProcurementUnitListByPlanningUnit(int planningUnitId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder.append(" AND pru.ACTIVE ");
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        sqlStringBuilder.append(" AND pru.PLANNING_UNIT_ID=:planningUnitId ");
        params.put("planningUnitId", planningUnitId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementUnitRowMapper());
    }

    @Override
    public int addProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_procurement_unit").usingGeneratedKeyColumns("PROCUREMENT_UNIT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int labelId = this.labelDao.addLabel(procurementUnit.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("PLANNING_UNIT_ID", procurementUnit.getPlanningUnit().getPlanningUnitId());
        params.put("UNIT_ID", procurementUnit.getUnit().getId());
        params.put("MULTIPLIER", procurementUnit.getMultiplier());
        params.put("SUPPLIER_ID", procurementUnit.getSupplier().getId());
        params.put("HEIGHT_QTY", procurementUnit.getHeightQty());
        params.put("HEIGHT_UNIT_ID", (procurementUnit.getHeightUnit() == null || procurementUnit.getHeightUnit().getId() == 0 ? null : procurementUnit.getHeightUnit().getId()));
        params.put("WIDTH_QTY", procurementUnit.getWidthQty());
        params.put("WIDTH_UNIT_ID", (procurementUnit.getWidthUnit() == null || procurementUnit.getWidthUnit().getId() == 0 ? null : procurementUnit.getWidthUnit().getId()));
        params.put("LENGTH_QTY", procurementUnit.getLengthQty());
        params.put("LENGTH_UNIT_ID", (procurementUnit.getLengthUnit() == null || procurementUnit.getLengthUnit().getId() == 0 ? null : procurementUnit.getLengthUnit().getId()));
        params.put("WEIGHT_QTY", procurementUnit.getWeightQty());
        params.put("WEIGHT_UNIT_ID", (procurementUnit.getWeightUnit() == null || procurementUnit.getWeightUnit().getId() == 0 ? null : procurementUnit.getWeightUnit().getId()));
        params.put("LABELING", procurementUnit.getLabeling());
        params.put("UNITS_PER_CONTAINER", procurementUnit.getUnitsPerContainer());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_procurement_unit pru LEFT JOIN ap_label prul ON pru.LABEL_ID=prul.LABEL_ID "
                + "SET  "
                + "    pru.MULTIPLIER=:multiplier, "
                + "    pru.UNIT_ID=:unitId, "
                + "    pru.HEIGHT_QTY=:heightQty, "
                + "    pru.HEIGHT_UNIT_ID=:heightUnitId, "
                + "    pru.LENGTH_QTY=:lengthQty, "
                + "    pru.LENGTH_UNIT_ID=:lengthUnitId, "
                + "    pru.WIDTH_QTY=:widthQty, "
                + "    pru.WIDTH_UNIT_ID=:widthUnitId, "
                + "    pru.WEIGHT_QTY=:weightQty, "
                + "    pru.WEIGHT_UNIT_ID=:weightUnitId, "
                + "    pru.UNITS_PER_CONTAINER=:unitsPerContainer, "
                + "    pru.LABELING=:labeling, "
                + "    pru.ACTIVE=:active, "
                + "    pru.LAST_MODIFIED_BY=IF(pru.MULTIPLIER!=:multiplier OR pru.UNIT_ID!=:unitId "
                + "         OR pru.HEIGHT_QTY!=:heightQty OR pru.HEIGHT_UNIT_ID!=:heightUnitId "
                + "         OR pru.LENGTH_QTY!=:lengthQty OR pru.LENGTH_UNIT_ID!=:lengthUnitId "
                + "         OR pru.WIDTH_QTY!=:widthQty OR pru.WIDTH_UNIT_ID!=:widthUnitId "
                + "         OR pru.WEIGHT_QTY!=:weightQty OR pru.WEIGHT_UNIT_ID!=:weightUnitId "
                + "         OR pru.UNITS_PER_CONTAINER!=:unitsPerContainer OR pru.LABELING!=:labeling "
                + "         OR pru.ACTIVE!=:active,:curUser, pru.LAST_MODIFIED_BY), "
                + "    pru.LAST_MODIFIED_DATE=IF(pru.MULTIPLIER!=:multiplier OR pru.UNIT_ID!=:unitId "
                + "         OR pru.HEIGHT_QTY!=:heightQty OR pru.HEIGHT_UNIT_ID!=:heightUnitId "
                + "         OR pru.LENGTH_QTY!=:lengthQty OR pru.LENGTH_UNIT_ID!=:lengthUnitId "
                + "         OR pru.WIDTH_QTY!=:widthQty OR pru.WIDTH_UNIT_ID!=:widthUnitId "
                + "         OR pru.WEIGHT_QTY!=:weightQty OR pru.WEIGHT_UNIT_ID!=:weightUnitId "
                + "         OR pru.UNITS_PER_CONTAINER!=:unitsPerContainer OR pru.LABELING!=:labeling "
                + "         OR pru.ACTIVE!=:active,:curDate, pru.LAST_MODIFIED_DATE), "
                + "    prul.LABEL_EN=:labelEn, "
                + "    prul.LAST_MODIFIED_BY=IF(prul.LABEL_EN=:labelEn,:curUser, prul.LAST_MODIFIED_BY), "
                + "    prul.LAST_MODIFIED_DATE=IF(prul.LABEL_EN=:labelEn,:curDate, prul.LAST_MODIFIED_DATE) "
                + "WHERE pru.PROCUREMENT_UNIT_ID=:procurementUnitId";
        Map<String, Object> params = new HashMap<>();
        params.put("procurementUnitId", procurementUnit.getProcurementUnitId());
        params.put("multiplier", procurementUnit.getMultiplier());
        params.put("unitId", procurementUnit.getUnit().getId());
        params.put("heightQty", procurementUnit.getHeightQty());
        params.put("heightUnitId", (procurementUnit.getHeightUnit() == null || procurementUnit.getHeightUnit().getId() == 0 ? null : procurementUnit.getHeightUnit().getId()));
        params.put("lengthQty", procurementUnit.getLengthQty());
        params.put("lengthUnitId", (procurementUnit.getLengthUnit() == null || procurementUnit.getLengthUnit().getId() == 0 ? null : procurementUnit.getLengthUnit().getId()));
        params.put("widthQty", procurementUnit.getWidthQty());
        params.put("widthUnitId", (procurementUnit.getWidthUnit() == null || procurementUnit.getWidthUnit().getId() == 0 ? null : procurementUnit.getWidthUnit().getId()));
        params.put("weightQty", procurementUnit.getWeightQty());
        params.put("weightUnitId", (procurementUnit.getWidthUnit() == null || procurementUnit.getWidthUnit().getId() == 0 ? null : procurementUnit.getWidthUnit().getId()));
        params.put("unitsPerContainer", procurementUnit.getUnitsPerContainer());
        params.put("labeling", procurementUnit.getLabeling());
        params.put("active", procurementUnit.isActive());
        params.put("labelEn", procurementUnit.getLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public ProcurementUnit getProcurementUnitById(int procurementUnitId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        sqlStringBuilder.append(" AND pru.PROCUREMENT_UNIT_ID=:procurementUnitId ");
        params.put("procurementUnitId", procurementUnitId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new ProcurementUnitRowMapper());
    }

    @Override
    public List<ProcurementUnit> getProcurementUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        sqlStringBuilder.append(" AND pru.LAST_MODIFIED_DATE>:lastSyncDate ");
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementUnitRowMapper());
    }

}
