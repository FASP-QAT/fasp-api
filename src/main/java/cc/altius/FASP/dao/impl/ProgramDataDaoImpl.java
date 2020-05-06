/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.rowMapper.ConsumptionRowMapper;
import cc.altius.FASP.model.rowMapper.InventoryRowMapper;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class ProgramDataDaoImpl implements ProgramDataDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Consumption> getConsumptionList(int programId, int versionId) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        return this.namedParameterJdbcTemplate.query("CALL getConsumptionData(:programId, :versionId)", params, new ConsumptionRowMapper());
    }

    @Override
    public List<Inventory> getInventoryList(int programId, int versionId) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        return this.namedParameterJdbcTemplate.query("CALL getInventoryData(:programId, :versionId)", params, new InventoryRowMapper());
    }

    @Override
    @Transactional
    public int saveProgramData(ProgramData programData, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        // Check which records have changed
        Map<String, Object> params = new HashMap<>();
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_consumption` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `CONSUMPTION_ID` INT UNSIGNED NULL, "
                + "  `REGION_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `START_DATE` DATE NOT NULL, "
                + "  `STOP_DATE` DATE NOT NULL, "
                + "  `ACTUAL_FLAG` TINYINT UNSIGNED NOT NULL, "
                + "  `QTY` DECIMAL(12,2) UNSIGNED NOT NULL, "
                + "  `DAYS_OF_STOCK_OUT` INT UNSIGNED NOT NULL, "
                + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_consumption_1_idx` (`CONSUMPTION_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_2_idx` (`REGION_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_3_idx` (`PLANNING_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_4_idx` (`DATA_SOURCE_ID` ASC))";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        final List<SqlParameterSource> insertList = new ArrayList<>();
//        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("tmp_consumption");
        programData.getConsumptionList().forEach((c) -> {
            Map<String, Object> tp = new HashMap<>();
            tp.put("CONSUMPTION_ID", (c.getConsumptionId() == 0 ? null : c.getConsumptionId()));
            tp.put("REGION_ID", c.getRegion().getId());
            tp.put("PLANNING_UNIT_ID", c.getPlanningUnit().getId());
            tp.put("START_DATE", c.getStartDate());
            tp.put("STOP_DATE", c.getStopDate());
            tp.put("ACTUAL_FLAG", c.isActualFlag());
            tp.put("QTY", c.getConsumptionQty());
            tp.put("DAYS_OF_STOCK_OUT", c.getDayOfStockOut());
            tp.put("DATA_SOURCE_ID", c.getDataSource().getId());
            tp.put("ACTIVE", c.isActive());
            insertList.add(new MapSqlParameterSource(tp));
        });
        SqlParameterSource[] insertConsumption = new SqlParameterSource[insertList.size()];
//        si.executeBatch(insertList.toArray(insertConsumption));
        sqlString = " INSERT INTO tmp_consumption (CONSUMPTION_ID, REGION_ID, PLANNING_UNIT_ID, START_DATE, STOP_DATE, ACTUAL_FLAG, QTY, DAYS_OF_STOCK_OUT, DATA_SOURCE_ID, ACTIVE) VALUES (:CONSUMPTION_ID, :REGION_ID, :PLANNING_UNIT_ID, :START_DATE, :STOP_DATE, :ACTUAL_FLAG, :QTY, :DAYS_OF_STOCK_OUT, :DATA_SOURCE_ID, :ACTIVE)";
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertList.toArray(insertConsumption));

        // Check if there are any rows that need to be added
        sqlString = "SELECT COUNT(*) FROM tmp_consumption tc LEFT JOIN rm_consumption c ON tc.CONSUMPTION_ID=c.CONSUMPTION_ID WHERE tc.REGION_ID!=c.REGION_ID OR tc.PLANNING_UNIT_ID!=c.PLANNING_UNIT_ID OR tc.START_DATE!=c.START_DATE OR tc.STOP_DATE!=c.STOP_DATE OR tc.ACTUAL_FLAG!=c.ACTUAL_FLAG OR tc.QTY!=c.CONSUMPTION_QTY OR tc.DAYS_OF_STOCK_OUT!=c.DAYS_OF_STOCK_OUT OR tc.DATA_SOURCE_ID!=c.DATA_SOURCE_ID OR tc.ACTIVE!=c.ACTIVE OR tc.CONSUMPTION_ID IS NULL";
        int consumptionRows = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        int versionId = 0;
        if (consumptionRows > 0) {
            params.put("programId", programData.getProgramId());
            params.put("curUser", curUser.getUserId());
            params.put("curDate", curDate);
            sqlString = "CALL getVersionId(:programId, :curUser, :curDate)";
            versionId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
            params.put("versionId", versionId);
            sqlString = "INSERT INTO rm_consumption SELECT null, :programId, tc.REGION_ID, tc.PLANNING_UNIT_ID, tc.START_DATE, tc.STOP_DATE, tc.ACTUAL_FLAG, tc.QTY, tc.DAYS_OF_STOCK_OUT, tc.DATA_SOURCE_ID, tc.ACTIVE, :curUser, :curDate, :curUser, :curDate, :versionId"
                    + " FROM fasp.tmp_consumption tc  "
                    + " LEFT JOIN rm_consumption c ON tc.CONSUMPTION_ID=c.CONSUMPTION_ID  "
                    + " WHERE "
                    + "    tc.REGION_ID!=c.REGION_ID "
                    + "    OR tc.PLANNING_UNIT_ID!=c.PLANNING_UNIT_ID "
                    + "    OR tc.START_DATE!=c.START_DATE "
                    + "    OR tc.STOP_DATE!=c.STOP_DATE "
                    + "    OR tc.ACTUAL_FLAG!=c.ACTUAL_FLAG "
                    + "    OR tc.QTY!=c.CONSUMPTION_QTY "
                    + "    OR tc.DAYS_OF_STOCK_OUT!=c.DAYS_OF_STOCK_OUT "
                    + "    OR tc.DATA_SOURCE_ID!=c.DATA_SOURCE_ID "
                    + "    OR tc.ACTIVE!=c.ACTIVE"
                    + "    OR tc.CONSUMPTION_ID IS NULL";
            consumptionRows = this.namedParameterJdbcTemplate.update(sqlString, params);
        }

        params.clear();
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_inventory`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_inventory` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `INVENTORY_ID` INT UNSIGNED NULL, "
                + "  `INVENTORY_DATE` DATE NOT NULL, "
                + "  `REGION_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `ACTUAL_QTY` DECIMAL(12,2) UNSIGNED NULL, "
                + "  `ADJUSTMENT_QTY` DECIMAL(12,2) NOT NULL, "
                + "  `BATCH_NO` VARCHAR(25) NULL, "
                + "  `EXPIRY_DATE` DATE NULL, "
                + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_inventory_1_idx` (`INVENTORY_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_2_idx` (`REGION_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_3_idx` (`REALM_COUNTRY_PLANNING_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_4_idx` (`DATA_SOURCE_ID` ASC))";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        insertList.clear();
//        si = new SimpleJdbcInsert(dataSource).withTableName("tmp_inventory");
        programData.getInventoryList().forEach((i) -> {
            Map<String, Object> tp = new HashMap<>();
            tp.put("INVENTORY_ID", (i.getInventoryId() == 0 ? null : i.getInventoryId()));
            tp.put("INVENTORY_DATE", i.getInventoryDate());
            tp.put("REGION_ID", i.getRegion().getId());
            tp.put("REALM_COUNTRY_PLANNING_UNIT_ID", i.getRealmCountryPlanningUnit().getId());
            tp.put("ACTUAL_QTY", i.getActualQty());
            tp.put("ADJUSTMENT_QTY", i.getAdjustmentQty());
            tp.put("BATCH_NO", i.getBatchNo());
            tp.put("EXPIRY_DATE", i.getExpiryDate());
            tp.put("DATA_SOURCE_ID", i.getDataSource().getId());
            tp.put("ACTIVE", i.isActive());
            insertList.add(new MapSqlParameterSource(tp));
        });
        SqlParameterSource[] insertInventory = new SqlParameterSource[insertList.size()];
//        si.executeBatch(insertList.toArray(insertInventory));
        sqlString = "INSERT INTO tmp_inventory (INVENTORY_ID, INVENTORY_DATE, REGION_ID, REALM_COUNTRY_PLANNING_UNIT_ID, ACTUAL_QTY, ADJUSTMENT_QTY, BATCH_NO, EXPIRY_DATE, DATA_SOURCE_ID, ACTIVE) VALUES (:INVENTORY_ID, :INVENTORY_DATE, :REGION_ID, :REALM_COUNTRY_PLANNING_UNIT_ID, :ACTUAL_QTY, :ADJUSTMENT_QTY, :BATCH_NO, :EXPIRY_DATE, :DATA_SOURCE_ID, :ACTIVE)";
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertList.toArray(insertInventory));
        // Check if there are any rows that need to be added
        sqlString = "SELECT COUNT(*) FROM tmp_inventory tc LEFT JOIN rm_inventory c ON tc.INVENTORY_ID=c.INVENTORY_ID WHERE tc.INVENTORY_DATE!=c.INVENTORY_DATE OR tc.REGION_ID!=c.REGION_ID OR tc.REALM_COUNTRY_PLANNING_UNIT_ID!=c.REALM_COUNTRY_PLANNING_UNIT_ID OR tc.ACTUAL_QTY!=c.ACTUAL_QTY OR tc.ADJUSTMENT_QTY!=c.ADJUSTMENT_QTY OR tc.BATCH_NO!=c.BATCH_NO OR tc.EXPIRY_DATE!=c.EXPIRY_DATE OR tc.DATA_SOURCE_ID!=c.DATA_SOURCE_ID OR tc.ACTIVE!=c.ACTIVE OR tc.INVENTORY_ID IS NULL";
        int inventoryRows = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        if (inventoryRows > 0) {
            params.put("programId", programData.getProgramId());
            params.put("curUser", curUser.getUserId());
            params.put("curDate", curDate);
            if (versionId == 0) {
                sqlString = "CALL getVersionId(:programId, :curUser, :curDate)";
                versionId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
                params.put("versionId", versionId);
            }
            params.clear();
            params.put("programId", programData.getProgramId());
            params.put("curUser", curUser.getUserId());
            params.put("curDate", curDate);
            params.put("versionId", versionId);
            sqlString = "INSERT INTO rm_inventory SELECT null, :programId, c.INVENTORY_DATE, tc.REGION_ID, tc.REALM_COUNTRY_PLANNING_UNIT_ID, tc.ACTUAL_QTY, tc.ADJUSTMENT_QTY, tc.BATCH_NO, tc.EXPIRY_DATE, tc.DATA_SOURCE_ID, tc.ACTIVE, :curUser, :curDate, :curUser, :curDate, :versionId"
                    + " FROM tmp_inventory tc "
                    + " LEFT JOIN rm_inventory c ON tc.INVENTORY_ID=c.INVENTORY_ID "
                    + " WHERE "
                    + "     tc.INVENTORY_DATE!=c.INVENTORY_DATE"
                    + "     OR tc.REGION_ID!=c.REGION_ID "
                    + "     OR tc.REALM_COUNTRY_PLANNING_UNIT_ID!=c.REALM_COUNTRY_PLANNING_UNIT_ID "
                    + "     OR tc.ACTUAL_QTY!=c.ACTUAL_QTY "
                    + "     OR tc.ADJUSTMENT_QTY!=c.ADJUSTMENT_QTY "
                    + "     OR tc.BATCH_NO!=c.BATCH_NO "
                    + "     OR tc.EXPIRY_DATE!=c.EXPIRY_DATE "
                    + "     OR tc.DATA_SOURCE_ID!=c.DATA_SOURCE_ID "
                    + "     OR tc.ACTIVE!=c.ACTIVE "
                    + "     OR tc.INVENTORY_ID IS NULL";
            this.namedParameterJdbcTemplate.update(sqlString, params);
        }
        versionId = 0;
        if (versionId == 0) {
            throw new NumberFormatException("Nothing to update");
        }
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_inventory`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        return versionId;
    }

}
