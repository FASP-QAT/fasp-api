/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.exception.CouldNotSaveException;
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
    public int saveProgramData(ProgramData programData, CustomUserDetails curUser) throws CouldNotSaveException {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        // Check which records have changed
        Map<String, Object> params = new HashMap<>();
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
//        String sqlString = "DROP TABLE IF EXISTS `tmp_consumption`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_consumption` ( "
//        sqlString = "CREATE TABLE `tmp_consumption` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `CONSUMPTION_ID` INT UNSIGNED NULL, "
                + "  `REGION_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `CONSUMPTION_DATE` DATE NOT NULL, "
                + "  `ACTUAL_FLAG` TINYINT UNSIGNED NOT NULL, "
                + "  `QTY` DECIMAL(12,2) UNSIGNED NOT NULL, "
                + "  `DAYS_OF_STOCK_OUT` INT UNSIGNED NOT NULL, "
                + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `NOTES` TEXT NULL, "
                + "  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, "
                + "  `VERSION_ID` INT(10) NULL, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_consumption_1_idx` (`CONSUMPTION_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_2_idx` (`REGION_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_3_idx` (`PLANNING_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_4_idx` (`DATA_SOURCE_ID` ASC),"
                + "  INDEX `fk_tmp_consumption_5_idx` (`VERSION_ID` ASC))";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        final List<SqlParameterSource> insertList = new ArrayList<>();
//        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("tmp_consumption");
        programData.getConsumptionList().forEach((c) -> {
            Map<String, Object> tp = new HashMap<>();
            tp.put("CONSUMPTION_ID", (c.getConsumptionId() == 0 ? null : c.getConsumptionId()));
            tp.put("REGION_ID", c.getRegion().getId());
            tp.put("PLANNING_UNIT_ID", c.getPlanningUnit().getId());
            tp.put("CONSUMPTION_DATE", c.getConsumptionDate());
            tp.put("ACTUAL_FLAG", c.isActualFlag());
            tp.put("QTY", c.getConsumptionQty());
            tp.put("DAYS_OF_STOCK_OUT", c.getDayOfStockOut());
            tp.put("DATA_SOURCE_ID", c.getDataSource().getId());
            tp.put("NOTES", c.getNotes());
            tp.put("ACTIVE", c.isActive());
            insertList.add(new MapSqlParameterSource(tp));
        });
        SqlParameterSource[] insertConsumption = new SqlParameterSource[insertList.size()];
//        si.executeBatch(insertList.toArray(insertConsumption));
        sqlString = " INSERT INTO tmp_consumption (CONSUMPTION_ID, REGION_ID, PLANNING_UNIT_ID, CONSUMPTION_DATE, ACTUAL_FLAG, QTY, DAYS_OF_STOCK_OUT, DATA_SOURCE_ID, NOTES, ACTIVE) VALUES (:CONSUMPTION_ID, :REGION_ID, :PLANNING_UNIT_ID, :CONSUMPTION_DATE, :ACTUAL_FLAG, :QTY, :DAYS_OF_STOCK_OUT, :DATA_SOURCE_ID, :NOTES, :ACTIVE)";
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertList.toArray(insertConsumption));

        params.clear();
        params.put("versionId", programData.getRequestedProgramVersion());
        // Update the VersionId's in tmp_consumption with the ones from consumption_trans based on VersionId
        sqlString = "UPDATE tmp_consumption tc LEFT JOIN (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption_trans ct WHERE (ct.VERSION_ID>=:versionId OR :versionId=-1) GROUP BY ct.CONSUMPTION_ID) AS mc ON tc.CONSUMPTION_ID=mc.CONSUMPTION_ID SET tc.VERSION_ID=mc.MAX_VERSION_ID WHERE mc.CONSUMPTION_ID IS NOT NULL";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        // Check if there are any rows that need to be added
        params.clear();
        sqlString = "SELECT COUNT(*) FROM tmp_consumption tc LEFT JOIN rm_consumption c ON tc.CONSUMPTION_ID=c.CONSUMPTION_ID LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.VERSION_ID=ct.VERSION_ID WHERE tc.REGION_ID!=ct.REGION_ID OR tc.PLANNING_UNIT_ID!=ct.PLANNING_UNIT_ID OR tc.CONSUMPTION_DATE!=ct.CONSUMPTION_DATE OR tc.ACTUAL_FLAG!=ct.ACTUAL_FLAG OR tc.QTY!=ct.CONSUMPTION_QTY OR tc.DAYS_OF_STOCK_OUT!=ct.DAYS_OF_STOCK_OUT OR tc.DATA_SOURCE_ID!=ct.DATA_SOURCE_ID OR tc.NOTES!=ct.NOTES OR tc.ACTIVE!=ct.ACTIVE OR tc.CONSUMPTION_ID IS NULL";
        int consumptionRows = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        int versionId = 0;
        if (consumptionRows > 0) {
            params.put("programId", programData.getProgramId());
            params.put("curUser", curUser.getUserId());
            params.put("curDate", curDate);
            sqlString = "CALL getVersionId(:programId, :curUser, :curDate)";
            versionId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
            params.put("versionId", versionId);
            // Insert the rows where Consumption Id is not null
            sqlString = "INSERT INTO rm_consumption_trans SELECT null, tc.CONSUMPTION_ID, tc.REGION_ID, tc.PLANNING_UNIT_ID, tc.CONSUMPTION_DATE, tc.ACTUAL_FLAG, tc.QTY, tc.DAYS_OF_STOCK_OUT, tc.DATA_SOURCE_ID, tc.NOTES, tc.ACTIVE, :curUser, :curDate, :versionId"
                    + " FROM fasp.tmp_consumption tc "
                    + " LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.VERSION_ID=ct.VERSION_ID "
                    + " WHERE "
                    + "    (tc.REGION_ID!=ct.REGION_ID "
                    + "    OR tc.PLANNING_UNIT_ID!=ct.PLANNING_UNIT_ID "
                    + "    OR tc.CONSUMPTION_DATE!=ct.CONSUMPTION_DATE "
                    + "    OR tc.ACTUAL_FLAG!=ct.ACTUAL_FLAG "
                    + "    OR tc.QTY!=ct.CONSUMPTION_QTY "
                    + "    OR tc.DAYS_OF_STOCK_OUT!=ct.DAYS_OF_STOCK_OUT "
                    + "    OR tc.DATA_SOURCE_ID!=ct.DATA_SOURCE_ID "
                    + "    OR tc.NOTES!=ct.NOTES "
                    + "    OR tc.ACTIVE!=ct.ACTIVE) "
                    + "    AND tc.CONSUMPTION_ID IS NOT NULL AND tc.CONSUMPTION_ID!=0";
            consumptionRows = this.namedParameterJdbcTemplate.update(sqlString, params);

            sqlString = "SELECT tc.ID FROM tmp_consumption tc WHERE tc.CONSUMPTION_ID IS NULL OR tc.CONSUMPTION_ID=0";
            List<Integer> idListForInsert = this.namedParameterJdbcTemplate.queryForList(sqlString, params, Integer.class);
            params.put("id", 0);
            for (Integer id : idListForInsert) {
                sqlString = "INSERT INTO rm_consumption (PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES (:programId, :curUser, :curDate, :curUser, :curDate)";
                consumptionRows += this.namedParameterJdbcTemplate.update(sqlString, params);
                params.replace("id", id);
                sqlString = "INSERT INTO rm_consumption_trans SELECT null, LAST_INSERT_ID(), tc.REGION_ID, tc.PLANNING_UNIT_ID, tc.CONSUMPTION_DATE, tc.ACTUAL_FLAG, tc.QTY, tc.DAYS_OF_STOCK_OUT, tc.DATA_SOURCE_ID, tc.NOTES, tc.ACTIVE, :curUser, :curDate, :versionId FROM tmp_consumption tc WHERE tc.ID=:id";
                this.namedParameterJdbcTemplate.update(sqlString, params);
            }
        }
        params.clear();
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_inventory`";
//        sqlString = "DROP TABLE IF EXISTS `tmp_inventory`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_inventory` ( "
//        sqlString = "CREATE TABLE `tmp_inventory` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `INVENTORY_ID` INT UNSIGNED NULL, "
                + "  `INVENTORY_DATE` DATE NOT NULL, "
                + "  `REGION_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `ACTUAL_QTY` DECIMAL(12,2) UNSIGNED NULL, "
                + "  `ADJUSTMENT_QTY` DECIMAL(12,2) NOT NULL, "
                + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `NOTES` TEXT NULL, "
                + "  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, "
                + "  `VERSION_ID` INT(10) NULL, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_inventory_1_idx` (`INVENTORY_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_2_idx` (`REGION_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_3_idx` (`REALM_COUNTRY_PLANNING_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_4_idx` (`DATA_SOURCE_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_5_idx` (`VERSION_ID` ASC))";
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
            tp.put("DATA_SOURCE_ID", i.getDataSource().getId());
            tp.put("NOTES", i.getNotes());
            tp.put("ACTIVE", i.isActive());
            insertList.add(new MapSqlParameterSource(tp));
        });
        SqlParameterSource[] insertInventory = new SqlParameterSource[insertList.size()];
//        si.executeBatch(insertList.toArray(insertInventory));
        sqlString = "INSERT INTO tmp_inventory (INVENTORY_ID, INVENTORY_DATE, REGION_ID, REALM_COUNTRY_PLANNING_UNIT_ID, ACTUAL_QTY, ADJUSTMENT_QTY, DATA_SOURCE_ID, NOTES, ACTIVE) VALUES (:INVENTORY_ID, :INVENTORY_DATE, :REGION_ID, :REALM_COUNTRY_PLANNING_UNIT_ID, :ACTUAL_QTY, :ADJUSTMENT_QTY, :DATA_SOURCE_ID, :NOTES, :ACTIVE)";
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertList.toArray(insertInventory));

        params.clear();
        params.put("versionId", programData.getRequestedProgramVersion());
        // Update the VersionId's in tmp_inventory with the ones from inventory_trans based on VersionId
        sqlString = "UPDATE tmp_inventory ti LEFT JOIN (SELECT it.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory_trans it WHERE (it.VERSION_ID>=:versionId OR :versionId=-1) GROUP BY it.INVENTORY_ID) AS mi ON ti.INVENTORY_ID=mi.INVENTORY_ID SET ti.VERSION_ID=mi.MAX_VERSION_ID WHERE mi.INVENTORY_ID IS NOT NULL";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        // Check if there are any rows that need to be added
        sqlString = "SELECT COUNT(*) FROM tmp_inventory ti LEFT JOIN rm_inventory i ON ti.INVENTORY_ID=i.INVENTORY_ID LEFT JOIN rm_inventory_trans it ON ti.INVENTORY_ID=it.INVENTORY_ID AND ti.VERSION_ID=it.VERSION_ID WHERE ti.INVENTORY_DATE!=it.INVENTORY_DATE OR ti.REGION_ID!=it.REGION_ID OR ti.REALM_COUNTRY_PLANNING_UNIT_ID!=it.REALM_COUNTRY_PLANNING_UNIT_ID OR ti.ACTUAL_QTY!=it.ACTUAL_QTY OR ti.ADJUSTMENT_QTY!=it.ADJUSTMENT_QTY OR ti.DATA_SOURCE_ID!=it.DATA_SOURCE_ID OR ti.NOTES!=it.NOTES OR ti.ACTIVE!=it.ACTIVE OR ti.INVENTORY_ID IS NULL";
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

            // Insert the rows where Inventory Id is not null
            sqlString = "INSERT INTO rm_inventory_trans SELECT null, ti.INVENTORY_ID, ti.INVENTORY_DATE, ti.REGION_ID, ti.REALM_COUNTRY_PLANNING_UNIT_ID, ti.ACTUAL_QTY, ti.ADJUSTMENT_QTY, ti.DATA_SOURCE_ID, ti.NOTES, ti.ACTIVE, :curUser, :curDate, :versionId"
                    + " FROM tmp_inventory ti "
                    + " LEFT JOIN rm_inventory_trans it ON ti.INVENTORY_ID=it.INVENTORY_ID AND ti.VERSION_ID=it.VERSION_ID "
                    + " WHERE "
                    + "     (ti.INVENTORY_DATE!=it.INVENTORY_DATE"
                    + "     OR ti.REGION_ID!=it.REGION_ID "
                    + "     OR ti.REALM_COUNTRY_PLANNING_UNIT_ID!=it.REALM_COUNTRY_PLANNING_UNIT_ID "
                    + "     OR ti.ACTUAL_QTY!=it.ACTUAL_QTY "
                    + "     OR ti.ADJUSTMENT_QTY!=it.ADJUSTMENT_QTY "
                    + "     OR ti.DATA_SOURCE_ID!=it.DATA_SOURCE_ID "
                    + "     OR ti.NOTES!=it.NOTES "
                    + "     OR ti.ACTIVE!=it.ACTIVE) "
                    + "     AND ti.INVENTORY_ID IS NOT NULL AND ti.INVENTORY_ID !=0";
            this.namedParameterJdbcTemplate.update(sqlString, params);
            sqlString = "SELECT ti.ID FROM tmp_inventory ti WHERE ti.INVENTORY_ID IS NULL OR ti.INVENTORY_ID=0";
            List<Integer> idListForInsert = this.namedParameterJdbcTemplate.queryForList(sqlString, params, Integer.class);
            params.put("id", 0);
            for (Integer id : idListForInsert) {
                sqlString = "INSERT INTO rm_inventory (PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES (:programId, :curUser, :curDate, :curUser, :curDate)";
                consumptionRows += this.namedParameterJdbcTemplate.update(sqlString, params);
                params.replace("id", id);
                sqlString = "INSERT INTO rm_inventory_trans SELECT null, LAST_INSERT_ID(), ti.INVENTORY_DATE, ti.REGION_ID, ti.REALM_COUNTRY_PLANNING_UNIT_ID, ti.ACTUAL_QTY, ti.ADJUSTMENT_QTY, ti.DATA_SOURCE_ID, ti.NOTES, ti.ACTIVE, :curUser, :curDate, :versionId FROM tmp_inventory ti WHERE ti.ID=:id";
                this.namedParameterJdbcTemplate.update(sqlString, params);
            }

        }
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DROP TABLE IF EXISTS `tmp_inventory`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        if (versionId == 0) {
            throw new CouldNotSaveException("No new data to update");
        }
        return versionId;
    }

}
