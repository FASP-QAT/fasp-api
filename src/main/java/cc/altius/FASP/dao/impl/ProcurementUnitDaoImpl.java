/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProcurementUnitDao;
import cc.altius.FASP.exception.DuplicateNameException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LabelConstants;
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

    private final String sqlListString = "SELECT "
            + "    pru.PROCUREMENT_UNIT_ID, pru.LABEL_ID, pru.LABEL_EN, pru.LABEL_FR, pru.LABEL_SP, pru.LABEL_PR, "
            + "    s.SUPPLIER_ID, s.LABEL_ID `SUPPLIER_LABEL_ID`, s.LABEL_EN `SUPPLIER_LABEL_EN`, s.LABEL_FR `SUPPLIER_LABEL_FR`, s.LABEL_SP `SUPPLIER_LABEL_SP`, s.LABEL_PR `SUPPLIER_LABEL_PR`, "
            + "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP `UNIT_LABEL_SP`, u.LABEL_PR `UNIT_LABEL_PR`, "
            + "    pu.PLANNING_UNIT_ID, pu.MULTIPLIER `PLANNING_UNIT_MULTIPLIER`, fu.FORECASTING_UNIT_ID,  "
            + "    pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,  "
            + "    fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`,  "
            + "    fugl.LABEL_ID `GENERIC_LABEL_ID`, fugl.LABEL_EN `GENERIC_LABEL_EN`, fugl.LABEL_FR `GENERIC_LABEL_FR`, fugl.LABEL_PR `GENERIC_LABEL_PR`, fugl.LABEL_SP `GENERIC_LABEL_SP`,  "
            + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`,  "
            + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`,  "
            + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`,  "
            + "    puu.UNIT_ID `PLANNING_UNIT_UNIT_ID`, puu.UNIT_CODE `PLANNING_UNIT_UNIT_CODE`, puu.LABEL_ID `PLANNING_UNIT_UNIT_LABEL_ID`, puu.LABEL_EN `PLANNING_UNIT_UNIT_LABEL_EN`, puu.LABEL_FR `PLANNING_UNIT_UNIT_LABEL_FR`, puu.LABEL_PR `PLANNING_UNIT_UNIT_LABEL_PR`, puu.LABEL_SP `PLANNING_UNIT_UNIT_LABEL_SP`,  "
            + "    pru.HEIGHT_QTY, pru.WIDTH_QTY, pru.LENGTH_QTY, pru.WEIGHT_QTY, pru.VOLUME_QTY, pru.LABELING, pru.MULTIPLIER, pru.UNITS_PER_CASE, pru.UNITS_PER_PALLET_EURO1, pru.UNITS_PER_PALLET_EURO2, pru.UNITS_PER_CONTAINER, pru.LABELING, "
            + "    lu.UNIT_ID  `LENGTH_UNIT_ID`, lu.UNIT_CODE  `LENGTH_UNIT_CODE`, lu.LABEL_ID  `LENGTH_UNIT_LABEL_ID`, lu.LABEL_EN  `LENGTH_UNIT_LABEL_EN`, lu.LABEL_FR  `LENGTH_UNIT_LABEL_FR`, lu.LABEL_PR  `LENGTH_UNIT_LABEL_PR`, lu.LABEL_SP  `LENGTH_UNIT_LABEL_SP`,  "
            + "    weu.UNIT_ID `WEIGHT_UNIT_ID`, weu.UNIT_CODE `WEIGHT_UNIT_CODE`, weu.LABEL_ID `WEIGHT_UNIT_LABEL_ID`, weu.LABEL_EN `WEIGHT_UNIT_LABEL_EN`, weu.LABEL_FR `WEIGHT_UNIT_LABEL_FR`, weu.LABEL_PR `WEIGHT_UNIT_LABEL_PR`, weu.LABEL_SP `WEIGHT_UNIT_LABEL_SP`, "
            + "    vu.UNIT_ID `VOLUME_UNIT_ID`, vu.UNIT_CODE `VOLUME_UNIT_CODE`, vu.LABEL_ID `VOLUME_UNIT_LABEL_ID`, vu.LABEL_EN `VOLUME_UNIT_LABEL_EN`, vu.LABEL_FR `VOLUME_UNIT_LABEL_FR`, vu.LABEL_PR `VOLUME_UNIT_LABEL_PR`, vu.LABEL_SP `VOLUME_UNIT_LABEL_SP`, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pru.ACTIVE, pru.CREATED_DATE, pru.LAST_MODIFIED_DATE   "
            + " FROM vw_procurement_unit pru "
            + " LEFT JOIN vw_supplier s ON pru.SUPPLIER_ID=s.SUPPLIER_ID "
            + " LEFT JOIN vw_unit u ON pru.UNIT_ID=u.UNIT_ID "
            + " LEFT JOIN vw_planning_unit pu ON pru.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
            + " LEFT JOIN vw_forecasting_unit fu on pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
            + " LEFT JOIN vw_unit fugl ON fu.GENERIC_LABEL_ID=fugl.LABEL_ID "
            + " LEFT JOIN vw_realm r ON fu.REALM_ID=r.REALM_ID   "
            + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID   "
            + " LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID   "
            + " LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID  "
            + " LEFT JOIN vw_unit lu ON pru.LENGTH_UNIT_ID=lu.UNIT_ID "
            + " LEFT JOIN vw_unit weu ON pru.WEIGHT_UNIT_ID=weu.UNIT_ID "
            + " LEFT JOIN vw_unit vu ON pru.VOLUME_UNIT_ID=vu.UNIT_ID "
            + " LEFT JOIN us_user cb ON pru.CREATED_BY=cb.USER_ID   "
            + " LEFT JOIN us_user lmb ON pru.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE";

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
    public int addProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) throws DuplicateNameException {
        String sqlString = "SELECT COUNT(*) FROM vw_procurement_unit pu WHERE LOWER(pu.LABEL_EN)=:procurementUnitName";
        Map<String, Object> params = new HashMap<>();
        params.put("procurementUnitName", procurementUnit.getLabel().getLabel_en().toLowerCase());
        int count = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        if (count > 0) {
            throw new DuplicateNameException("Procurement unit with same name already exists");
        } else {
            SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_procurement_unit").usingGeneratedKeyColumns("PROCUREMENT_UNIT_ID");
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
            int labelId = this.labelDao.addLabel(procurementUnit.getLabel(), LabelConstants.RM_PROCUREMENT_UNIT, curUser.getUserId());
            params.clear();
            params.put("LABEL_ID", labelId);
            params.put("PLANNING_UNIT_ID", procurementUnit.getPlanningUnit().getPlanningUnitId());
            params.put("UNIT_ID", procurementUnit.getUnit().getId());
            params.put("MULTIPLIER", procurementUnit.getMultiplier());
            params.put("SUPPLIER_ID", procurementUnit.getSupplier().getId());
            params.put("HEIGHT_QTY", procurementUnit.getHeightQty());
            params.put("WIDTH_QTY", procurementUnit.getWidthQty());
            params.put("LENGTH_QTY", procurementUnit.getLengthQty());
            params.put("LENGTH_UNIT_ID", (procurementUnit.getLengthUnit().getId() == null ? null : procurementUnit.getLengthUnit().getId()));
            params.put("WEIGHT_QTY", procurementUnit.getWeightQty());
            params.put("WEIGHT_UNIT_ID", (procurementUnit.getWeightUnit().getId() == null ? null : procurementUnit.getWeightUnit().getId()));
            params.put("VOLUME_QTY", procurementUnit.getVolumeQty());
            params.put("VOLUME_UNIT_ID", (procurementUnit.getVolumeUnit().getId() == null ? null : procurementUnit.getVolumeUnit().getId()));
            params.put("LABELING", procurementUnit.getLabeling());
            params.put("UNITS_PER_CASE", procurementUnit.getUnitsPerCase());
            params.put("UNITS_PER_PALLET_EURO1", procurementUnit.getUnitsPerPalletEuro1());
            params.put("UNITS_PER_PALLET_EURO2", procurementUnit.getUnitsPerPalletEuro1());
            params.put("UNITS_PER_CONTAINER", procurementUnit.getUnitsPerContainer());
            params.put("ACTIVE", true);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            return si.executeAndReturnKey(params).intValue();
        }
    }

    @Override
    public int updateProcurementUnit(ProcurementUnit procurementUnit, CustomUserDetails curUser) {
        String sqlString = "SELECT COUNT(*) FROM vw_procurement_unit pu WHERE LOWER(pu.LABEL_EN)=:procurementUnitName AND pu.PROCUREMENT_UNIT_ID!=:procurementUnitId";
        Map<String, Object> params = new HashMap<>();
        params.put("procurementUnitName", procurementUnit.getLabel().getLabel_en().toLowerCase());
        params.put("procurementUnitId", procurementUnit.getProcurementUnitId());
        int count = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        if (count > 0) {
            throw new DuplicateNameException("Procurement unit with same name already exists");
        } else {
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
            sqlString = "UPDATE rm_procurement_unit pru LEFT JOIN ap_label prul ON pru.LABEL_ID=prul.LABEL_ID "
                    + "SET  "
                    + "    pru.MULTIPLIER=:multiplier, "
                    + "    pru.UNIT_ID=:unitId, "
                    + "    pru.HEIGHT_QTY=:heightQty, "
                    + "    pru.LENGTH_QTY=:lengthQty, "
                    + "    pru.LENGTH_UNIT_ID=:lengthUnitId, "
                    + "    pru.WIDTH_QTY=:widthQty, "
                    + "    pru.WEIGHT_QTY=:weightQty, "
                    + "    pru.WEIGHT_UNIT_ID=:weightUnitId, "
                    + "    pru.VOLUME_QTY=:volumeQty, "
                    + "    pru.VOLUME_UNIT_ID=:volumeUnitId, "
                    + "    pru.UNITS_PER_CASE=:unitsPerCase, "
                    + "    pru.UNITS_PER_PALLET_EURO1=:unitsPerPalletEuro1, "
                    + "    pru.UNITS_PER_PALLET_EURO2=:unitsPerPalletEuro2, "
                    + "    pru.UNITS_PER_CONTAINER=:unitsPerContainer, "
                    + "    pru.LABELING=:labeling, "
                    + "    pru.ACTIVE=:active, "
                    + "    pru.LAST_MODIFIED_BY=:curUser, "
                    + "    pru.LAST_MODIFIED_DATE=:curDate, "
                    + "    prul.LABEL_EN=:labelEn, "
                    + "    prul.LAST_MODIFIED_BY=:curUser, "
                    + "    prul.LAST_MODIFIED_DATE=:curDate "
                    + "WHERE pru.PROCUREMENT_UNIT_ID=:procurementUnitId";
            params.clear();
            params.put("procurementUnitId", procurementUnit.getProcurementUnitId());
            params.put("multiplier", procurementUnit.getMultiplier());
            params.put("unitId", procurementUnit.getUnit().getId());
            params.put("heightQty", procurementUnit.getHeightQty());
            params.put("lengthQty", procurementUnit.getLengthQty());
            params.put("lengthUnitId", (procurementUnit.getLengthUnit().getId() == null || procurementUnit.getLengthUnit().getId() == 0 ? null : procurementUnit.getLengthUnit().getId()));
            params.put("widthQty", procurementUnit.getWidthQty());
            params.put("weightQty", procurementUnit.getWeightQty());
            params.put("weightUnitId", (procurementUnit.getWeightUnit().getId() == null || procurementUnit.getWeightUnit().getId() == 0 ? null : procurementUnit.getWeightUnit().getId()));
            params.put("volumeQty", procurementUnit.getVolumeQty());
            params.put("volumeUnitId", (procurementUnit.getVolumeUnit().getId() == null || procurementUnit.getVolumeUnit().getId() == 0 ? null : procurementUnit.getVolumeUnit().getId()));
            params.put("unitsPerCase", procurementUnit.getUnitsPerCase());
            params.put("unitsPerPalletEuro1", procurementUnit.getUnitsPerPalletEuro1());
            params.put("unitsPerPalletEuro2", procurementUnit.getUnitsPerPalletEuro2());
            params.put("unitsPerContainer", procurementUnit.getUnitsPerContainer());
            params.put("labeling", procurementUnit.getLabeling());
            params.put("active", procurementUnit.isActive());
            params.put("labelEn", procurementUnit.getLabel().getLabel_en());
            params.put("curUser", curUser.getUserId());
            params.put("curDate", curDate);
            return this.namedParameterJdbcTemplate.update(sqlString, params);
        }
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

    @Override
    public List<ProcurementUnit> getProcurementUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT  "
                + "    pru.PROCUREMENT_UNIT_ID, pru.LABEL_ID, pru.LABEL_EN, pru.LABEL_FR, pru.LABEL_SP, pru.LABEL_PR,  "
                + "    s.SUPPLIER_ID, s.LABEL_ID `SUPPLIER_LABEL_ID`, s.LABEL_EN `SUPPLIER_LABEL_EN`, s.LABEL_FR `SUPPLIER_LABEL_FR`, s.LABEL_SP `SUPPLIER_LABEL_SP`, s.LABEL_PR `SUPPLIER_LABEL_PR`,  "
                + "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP `UNIT_LABEL_SP`, u.LABEL_PR `UNIT_LABEL_PR`,  "
                + "    pu.PLANNING_UNIT_ID, pu.MULTIPLIER `PLANNING_UNIT_MULTIPLIER`, fu.FORECASTING_UNIT_ID,   "
                + "    pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,   "
                + "    fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`,   "
                + "    fugl.LABEL_ID `GENERIC_LABEL_ID`, fugl.LABEL_EN `GENERIC_LABEL_EN`, fugl.LABEL_FR `GENERIC_LABEL_FR`, fugl.LABEL_PR `GENERIC_LABEL_PR`, fugl.LABEL_SP `GENERIC_LABEL_SP`,   "
                + "    r.REALM_ID, r.REALM_CODE, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`,   "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`,   "
                + "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`,   "
                + "    puu.UNIT_ID `PLANNING_UNIT_UNIT_ID`, puu.UNIT_CODE `PLANNING_UNIT_UNIT_CODE`, puu.LABEL_ID `PLANNING_UNIT_UNIT_LABEL_ID`, puu.LABEL_EN `PLANNING_UNIT_UNIT_LABEL_EN`, puu.LABEL_FR `PLANNING_UNIT_UNIT_LABEL_FR`, puu.LABEL_PR `PLANNING_UNIT_UNIT_LABEL_PR`, puu.LABEL_SP `PLANNING_UNIT_UNIT_LABEL_SP`,   "
                + "    pru.HEIGHT_QTY, pru.WIDTH_QTY, pru.LENGTH_QTY, pru.WEIGHT_QTY, pru.VOLUME_QTY, pru.LABELING, pru.MULTIPLIER, pru.UNITS_PER_CASE, pru.UNITS_PER_PALLET_EURO1, pru.UNITS_PER_PALLET_EURO2, pru.UNITS_PER_CONTAINER, pru.LABELING,  "
                + "    lu.UNIT_ID  `LENGTH_UNIT_ID`, lu.UNIT_CODE  `LENGTH_UNIT_CODE`, lu.LABEL_ID  `LENGTH_UNIT_LABEL_ID`, lu.LABEL_EN  `LENGTH_UNIT_LABEL_EN`, lu.LABEL_FR  `LENGTH_UNIT_LABEL_FR`, lu.LABEL_PR  `LENGTH_UNIT_LABEL_PR`, lu.LABEL_SP  `LENGTH_UNIT_LABEL_SP`,   "
                + "    weu.UNIT_ID `WEIGHT_UNIT_ID`, weu.UNIT_CODE `WEIGHT_UNIT_CODE`, weu.LABEL_ID `WEIGHT_UNIT_LABEL_ID`, weu.LABEL_EN `WEIGHT_UNIT_LABEL_EN`, weu.LABEL_FR `WEIGHT_UNIT_LABEL_FR`, weu.LABEL_PR `WEIGHT_UNIT_LABEL_PR`, weu.LABEL_SP `WEIGHT_UNIT_LABEL_SP`,  "
                + "    vu.UNIT_ID `VOLUME_UNIT_ID`, vu.UNIT_CODE `VOLUME_UNIT_CODE`, vu.LABEL_ID `VOLUME_UNIT_LABEL_ID`, vu.LABEL_EN `VOLUME_UNIT_LABEL_EN`, vu.LABEL_FR `VOLUME_UNIT_LABEL_FR`, vu.LABEL_PR `VOLUME_UNIT_LABEL_PR`, vu.LABEL_SP `VOLUME_UNIT_LABEL_SP`,  "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pru.ACTIVE, pru.CREATED_DATE, pru.LAST_MODIFIED_DATE    "
                + " FROM vw_program p "
                + " LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
                + " LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
                + " LEFT JOIN vw_procurement_unit pru ON pu.PLANNING_UNIT_ID=pru.PLANNING_UNIT_ID "
                + " LEFT JOIN vw_supplier s ON pru.SUPPLIER_ID=s.SUPPLIER_ID  "
                + " LEFT JOIN vw_unit u ON pru.UNIT_ID=u.UNIT_ID  "
                + " LEFT JOIN vw_forecasting_unit fu on pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID   "
                + " LEFT JOIN vw_unit fugl ON fu.GENERIC_LABEL_ID=fugl.LABEL_ID  "
                + " LEFT JOIN vw_realm r ON fu.REALM_ID=r.REALM_ID    "
                + " LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID    "
                + " LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID    "
                + " LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID   "
                + " LEFT JOIN vw_unit lu ON pru.LENGTH_UNIT_ID=lu.UNIT_ID  "
                + " LEFT JOIN vw_unit weu ON pru.WEIGHT_UNIT_ID=weu.UNIT_ID  "
                + " LEFT JOIN vw_unit vu ON pru.VOLUME_UNIT_ID=vu.UNIT_ID  "
                + " LEFT JOIN us_user cb ON pru.CREATED_BY=cb.USER_ID    "
                + " LEFT JOIN us_user lmb ON pru.LAST_MODIFIED_BY=lmb.USER_ID  "
                + " WHERE p.PROGRAM_ID in (").append(programIdsString).append(") AND pru.`PROCUREMENT_UNIT_ID`  IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "fu", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY pru.PROCUREMENT_UNIT_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProcurementUnitRowMapper());
    }

}
