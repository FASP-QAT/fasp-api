/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProgramCommonDao;
import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.AnnualTargetCalculator;
import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.BatchData;
import cc.altius.FASP.model.Consumption;
import cc.altius.FASP.model.ConsumptionBatchInfo;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramIntegrationDTO;
import cc.altius.FASP.model.DTO.rowMapper.ProgramIntegrationDTORowMapper;
import cc.altius.FASP.model.DatasetTree;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.ForecastActualConsumption;
import cc.altius.FASP.model.ForecastTree;
import cc.altius.FASP.model.IdByAndDate;
import cc.altius.FASP.model.Inventory;
import cc.altius.FASP.model.InventoryBatchInfo;
import cc.altius.FASP.model.MasterSupplyPlan;
import cc.altius.FASP.model.NewSupplyPlan;
import cc.altius.FASP.model.NotificationUser;
import cc.altius.FASP.model.ProblemReport;
import cc.altius.FASP.model.ProblemReportTrans;
import cc.altius.FASP.model.ProgramData;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.ReviewedProblem;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.ShipmentBatchInfo;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForSupplyPlanObject;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.SupplyPlanBatchInfo;
import cc.altius.FASP.model.CommitRequest;
import cc.altius.FASP.model.DatasetData;
import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.DatasetVersionListInput;
import cc.altius.FASP.model.SupplyPlanDate;
import cc.altius.FASP.model.TreeNode;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.report.ActualConsumptionData;
import cc.altius.FASP.model.report.ActualConsumptionDataOutput;
import cc.altius.FASP.model.rowMapper.ActualConsumptionDataOutputRowMapper;
import cc.altius.FASP.model.rowMapper.BatchRowMapper;
import cc.altius.FASP.model.rowMapper.ConsumptionListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.DatasetTreeResultSetExtractor;
import cc.altius.FASP.model.ForecastConsumptionExtrapolation;
import cc.altius.FASP.model.ExtrapolationData;
import cc.altius.FASP.model.ExtrapolationDataReportingRate;
import cc.altius.FASP.model.ForecastNode;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.ShipmentLinking;
import cc.altius.FASP.model.NodeDataExtrapolation;
import cc.altius.FASP.model.NodeDataExtrapolationOption;
import cc.altius.FASP.model.NodeDataModeling;
import cc.altius.FASP.model.NodeDataMom;
import cc.altius.FASP.model.NodeDataOverride;
import cc.altius.FASP.model.ProgramVersionTrans;
import cc.altius.FASP.model.SimpleProgram;
import cc.altius.FASP.model.TreeAndScenario;
import cc.altius.FASP.model.TreeLevel;
import cc.altius.FASP.model.TreeNodeData;
import cc.altius.FASP.model.TreeScenario;
import cc.altius.FASP.model.UpdateProgramVersion;
import cc.altius.FASP.model.rowMapper.AnnualTargetCalculatorResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ForecastConsumptionExtrapolationListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ForecastActualConsumptionRowMapper;
import cc.altius.FASP.model.rowMapper.IdByAndDateRowMapper;
import cc.altius.FASP.model.rowMapper.InventoryListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ShipmentLinkingRowMapper;
import cc.altius.FASP.model.rowMapper.NewSupplyPlanBatchResultSetExtractor;
import cc.altius.FASP.model.rowMapper.NewSupplyPlanRegionResultSetExtractor;
import cc.altius.FASP.model.rowMapper.NodeDataExtrapolationOptionResultSetExtractor;
import cc.altius.FASP.model.rowMapper.NodeDataExtrapolationResultSetExtractor;
import cc.altius.FASP.model.rowMapper.NodeDataModelingListRowMapper;
import cc.altius.FASP.model.rowMapper.NodeDataMomRowMapper;
import cc.altius.FASP.model.rowMapper.NodeDataOverrideRowMapper;
import cc.altius.FASP.model.rowMapper.NotificationUserRowMapper;
import cc.altius.FASP.model.rowMapper.ProgramVersionResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProgramVersionTransRowMapper;
import cc.altius.FASP.model.rowMapper.VersionRowMapper;
import cc.altius.FASP.model.rowMapper.ShipmentListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimplePlanningUnitForSupplyPlanObjectResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimplifiedSupplyPlanResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SupplyPlanResultSetExtractor;
import cc.altius.FASP.model.rowMapper.TreeNodeResultSetExtractor;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.UserService;
import cc.altius.FASP.utils.LogUtils;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.utils.DateUtils;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class ProgramDataDaoImpl implements ProgramDataDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private AclService aclService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ProgramCommonDao programCommonDao;
    @Autowired
    private UserService userService;
    @Autowired
    private LabelDao labelDao;

    private final Logger logger = LoggerFactory.getLogger(ProgramDaoImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public Version processSupplyPlanCommitRequest(CommitRequest spcr, CustomUserDetails curUser) throws CouldNotSaveException {
        Map<String, Object> params = new HashMap<>();
        params.clear();
        params.put("COMMIT_REQUEST_ID", spcr.getCommitRequestId());
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        this.namedParameterJdbcTemplate.update("UPDATE ct_commit_request spcr SET spcr.STARTED_DATE=:curDate WHERE spcr.COMMIT_REQUEST_ID=:COMMIT_REQUEST_ID", params);
        CustomUserDetails commitUser = this.userService.getCustomUserByUserId(spcr.getCreatedBy().getUserId());
        SimpleProgram sp = this.programCommonDao.getSimpleProgramById(spcr.getProgram().getId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, commitUser);
        ProgramData pd = spcr.getProgramData();

        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);

        // Check if this Program is from the deLinkedPrograms list and if it is it should not have any ERP linked Shipments
        String sqlString = "SELECT count(*) FROM tmp_erp_delinked_programs WHERE PROGRAM_ID=:programId";
        params.clear();
        params.put("programId", sp.getId());
        int count = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        if (count > 0) {
            // This is a delinked Program
            for (Shipment s : pd.getShipmentList()) {
                if (s.isErpFlag()) {
                    throw new CouldNotSaveException("You are trying to commit a program that has ERP linked Shipments but this program has been delinked already");
                }
            }
        }

        // Check which records have changed
        params.clear();
        logger.info("Starting ProgramData Save");
        // ########################### Consumption ############################################
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
//        String sqlString = "DROP TABLE IF EXISTS `tmp_consumption`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("tmp_consumption temporary table dropped");
        sqlString = "CREATE TEMPORARY TABLE `tmp_consumption` ( "
                //        sqlString = "CREATE TABLE `tmp_consumption` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `CONSUMPTION_ID` INT UNSIGNED NULL, "
                + "  `REGION_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `CONSUMPTION_DATE` DATE NOT NULL, "
                + "  `ACTUAL_FLAG` TINYINT UNSIGNED NOT NULL, "
                + "  `RCPU_QTY` DOUBLE UNSIGNED NOT NULL, "
                + "  `QTY` DOUBLE UNSIGNED NOT NULL, "
                + "  `DAYS_OF_STOCK_OUT` INT UNSIGNED NOT NULL, "
                + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `NOTES` TEXT NULL, "
                + "  `CREATED_BY` INT UNSIGNED NOT NULL, "
                + "  `CREATED_DATE` DATETIME NOT NULL, "
                + "  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, "
                + "  `LAST_MODIFIED_DATE` DATETIME NOT NULL, "
                + "  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, "
                + "  `VERSION_ID` INT(10) NULL, "
                + "  `CHANGED` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_consumption_1_idx` (`CONSUMPTION_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_2_idx` (`REGION_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_3_idx` (`PLANNING_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_4_idx` (`DATA_SOURCE_ID` ASC),"
                + "  INDEX `fk_tmp_consumption_5_idx` (`VERSION_ID` ASC)) "
                + "  ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("tmp_consumption temporary table created");
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption_batch_info`";
//        sqlString = "DROP TABLE IF EXISTS `tmp_consumption_batch_info`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("tmp_consumption_batch_info temporary table dropped");
        sqlString = "CREATE TEMPORARY TABLE `tmp_consumption_batch_info` ( "
                //        sqlString = "CREATE TABLE `tmp_consumption_batch_info` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `PARENT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `CONSUMPTION_TRANS_BATCH_INFO_ID` INT(10) UNSIGNED NULL, "
                + "  `CONSUMPTION_TRANS_ID` INT(10) UNSIGNED NULL, "
                + "  `BATCH_ID` INT(10) NOT NULL, "
                + "  `BATCH_QTY` DECIMAL(24,4) UNSIGNED NOT NULL, "
                + "  `CHANGED` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_consumption_1_idx` (`CONSUMPTION_TRANS_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_2_idx` (`CONSUMPTION_TRANS_BATCH_INFO_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_3_idx` (`BATCH_ID` ASC)) "
                + "  ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("tmp_consumption_batch_info temporary table created");
        logger.info("Going to load all the Consumption records into temp table");
        final List<SqlParameterSource> insertList = new ArrayList<>();
        final List<SqlParameterSource> insertBatchList = new ArrayList<>();
        int id = 1;
        for (Consumption c : pd.getConsumptionList()) {
            Map<String, Object> tp = new HashMap<>();
            tp.put("ID", id);
            tp.put("CONSUMPTION_ID", (c.getConsumptionId() == 0 ? null : c.getConsumptionId()));
            tp.put("REGION_ID", c.getRegion().getId());
            tp.put("REALM_COUNTRY_PLANNING_UNIT_ID", c.getRealmCountryPlanningUnit().getId());
            tp.put("PLANNING_UNIT_ID", c.getPlanningUnit().getId());
            tp.put("CONSUMPTION_DATE", c.getConsumptionDate());
            tp.put("ACTUAL_FLAG", c.isActualFlag());
            tp.put("RCPU_QTY", c.getConsumptionRcpuQty());
            tp.put("QTY", c.getConsumptionQty());
            tp.put("DAYS_OF_STOCK_OUT", c.getDayOfStockOut());
            tp.put("DATA_SOURCE_ID", c.getDataSource().getId());
            tp.put("NOTES", c.getNotes());
            tp.put("CREATED_BY", c.getCreatedBy().getUserId());
            tp.put("CREATED_DATE", c.getCreatedDate());
            tp.put("LAST_MODIFIED_BY", c.getLastModifiedBy().getUserId());
            tp.put("LAST_MODIFIED_DATE", c.getLastModifiedDate());
            tp.put("ACTIVE", c.isActive());
            tp.put("VERSION_ID", c.getVersionId());
            insertList.add(new MapSqlParameterSource(tp));
            SimpleJdbcInsert batchInsert = new SimpleJdbcInsert(dataSource).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
            for (ConsumptionBatchInfo b : c.getBatchInfoList()) {
                if (b.getBatch().getBatchId() == 0) {
                    Map<String, Object> batchParams = new HashMap<>();
                    batchParams.put("BATCH_NO", b.getBatch().getBatchNo());
                    batchParams.put("PROGRAM_ID", pd.getProgramId());
                    batchParams.put("PLANNING_UNIT_ID", c.getPlanningUnit().getId());
                    batchParams.put("AUTO_GENERATED", b.getBatch().isAutoGenerated());
                    batchParams.put("EXPIRY_DATE", b.getBatch().getExpiryDate());
                    batchParams.put("CREATED_DATE", b.getBatch().getCreatedDate());
                    try {
                        b.getBatch().setBatchId(this.namedParameterJdbcTemplate.queryForObject("SELECT bi.BATCH_ID FROM rm_batch_info bi WHERE bi.BATCH_NO=:BATCH_NO AND bi.PROGRAM_ID=:PROGRAM_ID AND bi.EXPIRY_DATE=:EXPIRY_DATE", batchParams, Integer.class));
                        logger.info("Batch No + Expiry Dt found for this Program");
                    } catch (DataAccessException d) {
                        logger.info("Batch No + Expiry Dt not found for this Program, so creating it");
                        b.getBatch().setBatchId(batchInsert.executeAndReturnKey(batchParams).intValue());
                        logger.info("Batch Id created");
                    }
                }
                Map<String, Object> tb = new HashMap<>();
                tb.put("CONSUMPTION_TRANS_ID", null);
                tb.put("CONSUMPTION_TRANS_BATCH_INFO_ID", (b.getConsumptionTransBatchInfoId() == 0 ? null : b.getConsumptionTransBatchInfoId()));
                tb.put("PARENT_ID", id);
                tb.put("BATCH_ID", b.getBatch().getBatchId());
                tb.put("BATCH_QTY", b.getConsumptionQty());
                insertBatchList.add(new MapSqlParameterSource(tb));
            }
            id++;
        }
        logger.info(id + " consumption records going to be inserted into the tmp table");

        SqlParameterSource[] insertConsumption = new SqlParameterSource[insertList.size()];
        sqlString = " INSERT INTO tmp_consumption (ID, CONSUMPTION_ID, REGION_ID, PLANNING_UNIT_ID, REALM_COUNTRY_PLANNING_UNIT_ID, CONSUMPTION_DATE, ACTUAL_FLAG, QTY, RCPU_QTY, DAYS_OF_STOCK_OUT, DATA_SOURCE_ID, NOTES, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, ACTIVE, VERSION_ID) VALUES (:ID, :CONSUMPTION_ID, :REGION_ID, :PLANNING_UNIT_ID, :REALM_COUNTRY_PLANNING_UNIT_ID, :CONSUMPTION_DATE, :ACTUAL_FLAG, :QTY, :RCPU_QTY, :DAYS_OF_STOCK_OUT, :DATA_SOURCE_ID, :NOTES, :CREATED_BY, :CREATED_DATE, :LAST_MODIFIED_BY, :LAST_MODIFIED_DATE,  :ACTIVE, :VERSION_ID)";
//        try {
        int cCnt = this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertList.toArray(insertConsumption)).length;
        logger.info(cCnt + " records imported into the tmp table");
//        } catch (Exception e) {
//            logger.info("Could not load the tmp consumption records going to throw a CouldNotSaveException");
//            throw new CouldNotSaveException("Could not save Consumption data - " + e.getMessage());
//        }
        if (insertBatchList.size() > 0) {
            SqlParameterSource[] insertConsumptionBatch = new SqlParameterSource[insertBatchList.size()];
            sqlString = "INSERT INTO tmp_consumption_batch_info (PARENT_ID, CONSUMPTION_TRANS_ID, CONSUMPTION_TRANS_BATCH_INFO_ID, BATCH_ID, BATCH_QTY) VALUES (:PARENT_ID, :CONSUMPTION_TRANS_ID, :CONSUMPTION_TRANS_BATCH_INFO_ID, :BATCH_ID, :BATCH_QTY)";
//            try {
            logger.info(insertBatchList.size() + " consumption batch records going to be inserted into the tmp table");
            cCnt = this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertBatchList.toArray(insertConsumptionBatch)).length;
            logger.info(cCnt + " records imported into the tmp table");
//            } catch (Exception e) {
//                logger.info("Could not load the tmp consumption batch records going to throw a CouldNotSaveException");
//                throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//            }
        }
        params.clear();
        sqlString = "UPDATE tmp_consumption_batch_info tcbi LEFT JOIN rm_consumption_trans_batch_info ctbi ON tcbi.CONSUMPTION_TRANS_BATCH_INFO_ID=ctbi.CONSUMPTION_TRANS_BATCH_INFO_ID SET tcbi.CONSUMPTION_TRANS_ID=ctbi.CONSUMPTION_TRANS_ID WHERE tcbi.CONSUMPTION_TRANS_BATCH_INFO_ID IS NOT NULL";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        params.clear();
        // Flag the rows for changed records
        sqlString = "UPDATE tmp_consumption tc LEFT JOIN rm_consumption c ON tc.CONSUMPTION_ID=c.CONSUMPTION_ID LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND c.MAX_VERSION_ID=ct.VERSION_ID SET tc.CHANGED=1 WHERE tc.REGION_ID!=ct.REGION_ID OR tc.PLANNING_UNIT_ID!=ct.PLANNING_UNIT_ID OR tc.REALM_COUNTRY_PLANNING_UNIT_ID!=ct.REALM_COUNTRY_PLANNING_UNIT_ID OR tc.CONSUMPTION_DATE!=ct.CONSUMPTION_DATE OR tc.ACTUAL_FLAG!=ct.ACTUAL_FLAG OR tc.QTY!=ct.CONSUMPTION_QTY OR tc.RCPU_QTY!=ct.CONSUMPTION_RCPU_QTY OR tc.DAYS_OF_STOCK_OUT!=ct.DAYS_OF_STOCK_OUT OR tc.DATA_SOURCE_ID!=ct.DATA_SOURCE_ID OR tc.NOTES!=ct.NOTES OR tc.ACTIVE!=ct.ACTIVE OR tc.CONSUMPTION_ID IS NULL";
//        try {
        cCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(cCnt + " records updated in tmp as changed where a direct consumption record has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//        }
        sqlString = "UPDATE tmp_consumption_batch_info tcbi LEFT JOIN rm_consumption_trans_batch_info ctbi ON tcbi.CONSUMPTION_TRANS_BATCH_INFO_ID=ctbi.CONSUMPTION_TRANS_BATCH_INFO_ID SET `CHANGED`=1 WHERE tcbi.CONSUMPTION_TRANS_BATCH_INFO_ID IS NULL OR tcbi.BATCH_ID!=ctbi.BATCH_ID OR tcbi.BATCH_QTY!=ctbi.CONSUMPTION_QTY";
//        try {
        cCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(cCnt + " records updated in tmp as changed where a batch record has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//        }
        sqlString = "UPDATE tmp_consumption tc LEFT JOIN tmp_consumption_batch_info tcbi ON tc.ID = tcbi.PARENT_ID SET tc.CHANGED=1 WHERE tcbi.CHANGED=1";
//        try {
        cCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(cCnt + " records updated in tmp as changed where a Batch Id has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//        }

        // Check if there are any rows that need to be added
        params.clear();
        sqlString = "SELECT COUNT(*) FROM tmp_consumption tc WHERE tc.CHANGED=1";
        int consumptionRows = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        logger.info(consumptionRows + " total consumption records that have changed");
        Version version = null;
        if (consumptionRows > 0) {
            params.put("programId", pd.getProgramId());
            params.put("curUser", commitUser.getUserId());
            params.put("curDate", curDate);
            params.put("versionTypeId", pd.getVersionType().getId());
            params.put("versionStatusId", pd.getVersionStatus().getId());
            params.put("notes", pd.getNotes());
            sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, null, null, null, null, :curUser, :curDate, 0)";
//            try {
            version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
            logger.info(version + " is the new version no");
//            } catch (Exception e) {
//                logger.info("Failed to get a new version no for this program");
//                throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//            }
            params.put("versionId", version.getVersionId());
            // Insert the rows where Consumption Id is not null
            sqlString = "INSERT INTO rm_consumption_trans SELECT null, tc.CONSUMPTION_ID, tc.REGION_ID, tc.PLANNING_UNIT_ID, tc.CONSUMPTION_DATE, tc.REALM_COUNTRY_PLANNING_UNIT_ID, tc.ACTUAL_FLAG, tc.QTY, tc.RCPU_QTY, tc.DAYS_OF_STOCK_OUT, tc.DATA_SOURCE_ID, tc.NOTES, tc.ACTIVE, tc.LAST_MODIFIED_BY, tc.LAST_MODIFIED_DATE, :versionId"
                    + " FROM fasp.tmp_consumption tc "
                    + " WHERE tc.CHANGED=1 AND tc.CONSUMPTION_ID!=0";
//            try {
            consumptionRows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info(consumptionRows + " added to the consumption_trans table");
//            } catch (Exception e) {
//                logger.info("Failed to add to the consumption_trans table");
//                throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//            }
            params.clear();
            params.put("versionId", version.getVersionId());
            // Update the rm_consumption table with the latest versionId
            sqlString = "UPDATE tmp_consumption tc LEFT JOIN rm_consumption c ON c.CONSUMPTION_ID=tc.CONSUMPTION_ID SET c.MAX_VERSION_ID=:versionId, c.LAST_MODIFIED_BY=tc.LAST_MODIFIED_BY, c.LAST_MODIFIED_DATE=tc.LAST_MODIFIED_DATE WHERE tc.CONSUMPTION_ID IS NOT NULL AND tc.CHANGED=1";
//            try {
            this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("Updated the Version no in the consumption table");
//            } catch (Exception e) {
//                logger.info("Failed to update the Version no in the consumption table");
//                throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//            }
            // Insert into rm_consumption_trans_batch_info where the consumption record was already existing but has changed
            sqlString = "INSERT INTO rm_consumption_trans_batch_info SELECT null, ct.CONSUMPTION_TRANS_ID, tcbi.BATCH_ID, tcbi.BATCH_QTY from tmp_consumption tc left join tmp_consumption_batch_info tcbi ON tcbi.PARENT_ID=tc.ID LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND ct.VERSION_ID=:versionId WHERE tc.CHANGED=1 AND tc.CONSUMPTION_ID IS NOT NULL AND tcbi.PARENT_ID IS NOT NULL";
//            try {
            consumptionRows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info(consumptionRows + " rows inserted into the consumption_trans_batch_info table");
//            } catch (Exception e) {
//                logger.info("Failed to insert into the consumption_trans_batch_info table");
//                throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//            }
            sqlString = "SELECT tc.ID, null TEMP_ID, null TEMP_PARENT_ID, null TEMP_PARENT_LINKED_ID, tc.CREATED_BY, tc.CREATED_DATE, tc.LAST_MODIFIED_BY, tc.LAST_MODIFIED_DATE FROM tmp_consumption tc WHERE tc.CONSUMPTION_ID IS NULL OR tc.CONSUMPTION_ID=0";
            List<IdByAndDate> idListForInsert = this.namedParameterJdbcTemplate.query(sqlString, params, new IdByAndDateRowMapper());
            params.put("versionId", version.getVersionId());
            params.put("programId", pd.getProgramId());
            params.put("id", 0);
            params.put("createdBy", commitUser.getUserId());
            params.put("createdDate", curDate);
            params.put("lastModifiedBy", commitUser.getUserId());
            params.put("lastModifiedDate", curDate);
            consumptionRows = 0;
            for (IdByAndDate tmpId : idListForInsert) {
                params.replace("id", tmpId.getId());
                params.replace("createdBy", tmpId.getCreatedBy());
                params.replace("createdDate", tmpId.getCreatedDate());
                params.replace("lastModifiedBy", tmpId.getLastModifiedBy());
                params.replace("lastModifiedDate", tmpId.getLastModifiedDate());
                sqlString = "INSERT INTO rm_consumption (PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, MAX_VERSION_ID) VALUES (:programId, :createdBy, :createdDate, :lastModifiedBy, :lastModifiedDate, :versionId)";
//                try {
                consumptionRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the consumption table");
//                    throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//                }
                sqlString = "INSERT INTO rm_consumption_trans SELECT null, LAST_INSERT_ID(), tc.REGION_ID, tc.PLANNING_UNIT_ID, tc.CONSUMPTION_DATE, tc.REALM_COUNTRY_PLANNING_UNIT_ID, tc.ACTUAL_FLAG, tc.QTY, tc.RCPU_QTY, tc.DAYS_OF_STOCK_OUT, tc.DATA_SOURCE_ID, tc.NOTES, tc.ACTIVE, :lastModifiedBy, :lastModifiedDate, :versionId FROM tmp_consumption tc WHERE tc.ID=:id";
//                try {
                consumptionRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the consumption_trans table");
//                    throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//                }
                sqlString = "INSERT INTO rm_consumption_trans_batch_info SELECT null, LAST_INSERT_ID(), tcbi.BATCH_ID, tcbi.BATCH_QTY from tmp_consumption_batch_info tcbi WHERE tcbi.PARENT_ID=:id";
//                try {
                consumptionRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the consumption_trans_batch_info table");
//                    throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//                }
            }
            logger.info(consumptionRows + " records inserted into the consumption, consumption_trans and consumption_trans_batch_info tables");
        }
        // ########################### Consumption ############################################

        // ###########################  Inventory  ############################################
        params.clear();
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_inventory`";
//        sqlString = "DROP TABLE IF EXISTS `tmp_inventory`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_inventory` ( "
                //                        sqlString = "CREATE TABLE `tmp_inventory` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `INVENTORY_ID` INT UNSIGNED NULL, "
                + "  `INVENTORY_DATE` DATE NOT NULL, "
                + "  `REGION_ID` INT(10) UNSIGNED NULL, "
                + "  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `ACTUAL_QTY` BIGINT(20) UNSIGNED NULL, "
                + "  `ADJUSTMENT_QTY` BIGINT(20) NULL, "
                + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `NOTES` TEXT NULL, "
                + "  `CREATED_BY` INT UNSIGNED NOT NULL, "
                + "  `CREATED_DATE` DATETIME NOT NULL, "
                + "  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, "
                + "  `LAST_MODIFIED_DATE` DATETIME NOT NULL, "
                + "  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, "
                + "  `VERSION_ID` INT(10) NULL, "
                + "  `CHANGED` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_inventory_1_idx` (`INVENTORY_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_2_idx` (`REGION_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_3_idx` (`REALM_COUNTRY_PLANNING_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_4_idx` (`DATA_SOURCE_ID` ASC), "
                + "  INDEX `fk_tmp_inventory_5_idx` (`VERSION_ID` ASC)) "
                + "  ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_inventory_batch_info`";
//        sqlString = "DROP TABLE IF EXISTS `tmp_inventory_batch_info`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_inventory_batch_info` ( "
                //                        sqlString = "CREATE TABLE `tmp_inventory_batch_info` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `PARENT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `INVENTORY_TRANS_BATCH_INFO_ID` INT(10) UNSIGNED NULL, "
                + "  `INVENTORY_TRANS_ID` INT(10) UNSIGNED NULL, "
                + "  `BATCH_ID` INT(10) NOT NULL, "
                + "  `ACTUAL_QTY` BIGINT(20) UNSIGNED NULL, "
                + "  `ADJUSTMENT_QTY` BIGINT(20) NULL, "
                + "  `CHANGED` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_consumption_1_idx` (`INVENTORY_TRANS_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_2_idx` (`INVENTORY_TRANS_BATCH_INFO_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_3_idx` (`BATCH_ID` ASC)) "
                + "  ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        insertList.clear();
        insertBatchList.clear();
        id = 1;
        for (Inventory i : pd.getInventoryList()) {
            Map<String, Object> tp = new HashMap<>();
            tp.put("ID", id);
            tp.put("INVENTORY_ID", (i.getInventoryId() == 0 ? null : i.getInventoryId()));
            tp.put("INVENTORY_DATE", i.getInventoryDate());
            tp.put("REGION_ID", i.getRegion().getId());
            tp.put("REALM_COUNTRY_PLANNING_UNIT_ID", i.getRealmCountryPlanningUnit().getId());
            tp.put("ACTUAL_QTY", i.getActualQty());
            tp.put("ADJUSTMENT_QTY", i.getAdjustmentQty());
            tp.put("DATA_SOURCE_ID", i.getDataSource().getId());
            tp.put("NOTES", i.getNotes());
            tp.put("CREATED_BY", i.getCreatedBy().getUserId());
            tp.put("CREATED_DATE", i.getCreatedDate());
            tp.put("LAST_MODIFIED_BY", i.getLastModifiedBy().getUserId());
            tp.put("LAST_MODIFIED_DATE", i.getLastModifiedDate());
            tp.put("ACTIVE", i.isActive());
            tp.put("VERSION_ID", i.getVersionId());
            insertList.add(new MapSqlParameterSource(tp));
            SimpleJdbcInsert batchInsert = new SimpleJdbcInsert(dataSource).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
            for (InventoryBatchInfo b : i.getBatchInfoList()) {
                if (b.getBatch().getBatchId() == 0) {
                    Map<String, Object> batchParams = new HashMap<>();
                    batchParams.put("BATCH_NO", b.getBatch().getBatchNo());
                    batchParams.put("PROGRAM_ID", pd.getProgramId());
                    batchParams.put("PLANNING_UNIT_ID", i.getPlanningUnit().getId());
                    batchParams.put("AUTO_GENERATED", b.getBatch().isAutoGenerated());
                    batchParams.put("EXPIRY_DATE", b.getBatch().getExpiryDate());
                    batchParams.put("CREATED_DATE", b.getBatch().getCreatedDate());
                    try {
                        b.getBatch().setBatchId(this.namedParameterJdbcTemplate.queryForObject("SELECT bi.BATCH_ID FROM rm_batch_info bi WHERE bi.BATCH_NO=:BATCH_NO AND bi.PROGRAM_ID=:PROGRAM_ID AND bi.EXPIRY_DATE=:EXPIRY_DATE", batchParams, Integer.class));
                        logger.info("Batch No + Expiry Dt found for this Program");
                    } catch (DataAccessException d) {
                        logger.info("Batch No + Expiry Dt not found for this Program, so creating it");
                        b.getBatch().setBatchId(batchInsert.executeAndReturnKey(batchParams).intValue());
                        logger.info("Batch Id created");
                    }
                }
                Map<String, Object> tb = new HashMap<>();
                tb.put("INVENTORY_TRANS_ID", null);
                tb.put("INVENTORY_TRANS_BATCH_INFO_ID", (b.getInventoryTransBatchInfoId() == 0 ? null : b.getInventoryTransBatchInfoId()));
                tb.put("PARENT_ID", id);
                tb.put("BATCH_ID", b.getBatch().getBatchId());
                tb.put("ACTUAL_QTY", b.getActualQty());
                tb.put("ADJUSTMENT_QTY", b.getAdjustmentQty());
                insertBatchList.add(new MapSqlParameterSource(tb));
            }
            id++;
        }
        logger.info(id + " inventory records going to be inserted into the tmp table");

        SqlParameterSource[] insertInventory = new SqlParameterSource[insertList.size()];
        sqlString = " INSERT INTO tmp_inventory (ID, INVENTORY_ID, REGION_ID, REALM_COUNTRY_PLANNING_UNIT_ID, INVENTORY_DATE, ACTUAL_QTY, ADJUSTMENT_QTY, DATA_SOURCE_ID, NOTES, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, ACTIVE, VERSION_ID) VALUES (:ID, :INVENTORY_ID, :REGION_ID, :REALM_COUNTRY_PLANNING_UNIT_ID, :INVENTORY_DATE, :ACTUAL_QTY, :ADJUSTMENT_QTY, :DATA_SOURCE_ID, :NOTES, :CREATED_BY, :CREATED_DATE, :LAST_MODIFIED_BY, :LAST_MODIFIED_DATE, :ACTIVE, :VERSION_ID)";
//        try {
        int iCnt = this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertList.toArray(insertInventory)).length;
        logger.info(iCnt + " records imported into the tmp table");
//        } catch (Exception e) {
//            logger.info("Could not load the tmp inventory records going to throw a CouldNotSaveException");
//            throw new CouldNotSaveException("Could not save Inventory data - " + e.getMessage());
//        }
        if (insertBatchList.size() > 0) {
            SqlParameterSource[] insertInventoryBatch = new SqlParameterSource[insertBatchList.size()];
            sqlString = "INSERT INTO tmp_inventory_batch_info (PARENT_ID, INVENTORY_TRANS_ID, INVENTORY_TRANS_BATCH_INFO_ID, BATCH_ID, ACTUAL_QTY, ADJUSTMENT_QTY) VALUES (:PARENT_ID, :INVENTORY_TRANS_ID, :INVENTORY_TRANS_BATCH_INFO_ID, :BATCH_ID, :ACTUAL_QTY, :ADJUSTMENT_QTY)";
//            try {
            logger.info(insertBatchList.size() + " inventory batch records going to be inserted into the tmp table");
            iCnt = this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertBatchList.toArray(insertInventoryBatch)).length;
            logger.info(iCnt + " records imported into the tmp table");
//            } catch (Exception e) {
//                logger.info("Could not load the tmp inventory batch records going to throw a CouldNotSaveException");
//                throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//            }
        }
        params.clear();
        sqlString = "UPDATE tmp_inventory_batch_info tibi LEFT JOIN rm_inventory_trans_batch_info itbi ON tibi.INVENTORY_TRANS_BATCH_INFO_ID=itbi.INVENTORY_TRANS_BATCH_INFO_ID SET tibi.INVENTORY_TRANS_ID=itbi.INVENTORY_TRANS_ID WHERE tibi.INVENTORY_TRANS_BATCH_INFO_ID IS NOT NULL";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        params.clear();
        // Flag the rows for changed records
        sqlString = "UPDATE tmp_inventory ti LEFT JOIN rm_inventory i ON ti.INVENTORY_ID=i.INVENTORY_ID LEFT JOIN rm_inventory_trans it ON ti.INVENTORY_ID=it.INVENTORY_ID AND i.MAX_VERSION_ID=it.VERSION_ID SET ti.CHANGED=1 WHERE ti.REGION_ID!=it.REGION_ID OR ti.REALM_COUNTRY_PLANNING_UNIT_ID!=it.REALM_COUNTRY_PLANNING_UNIT_ID OR ti.INVENTORY_DATE!=it.INVENTORY_DATE OR ti.ACTUAL_QTY!=it.ACTUAL_QTY OR ti.ADJUSTMENT_QTY!=it.ADJUSTMENT_QTY OR ti.DATA_SOURCE_ID!=it.DATA_SOURCE_ID OR ti.NOTES!=it.NOTES OR ti.ACTIVE!=it.ACTIVE OR ti.INVENTORY_ID IS NULL";
//        try {
        iCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(iCnt + " records updated in tmp as changed where a direct inventory record has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//        }
        sqlString = "UPDATE tmp_inventory_batch_info tibi LEFT JOIN rm_inventory_trans_batch_info itbi ON tibi.INVENTORY_TRANS_BATCH_INFO_ID=itbi.INVENTORY_TRANS_BATCH_INFO_ID SET `CHANGED`=1 WHERE tibi.INVENTORY_TRANS_BATCH_INFO_ID IS NULL OR tibi.BATCH_ID!=itbi.BATCH_ID OR tibi.ACTUAL_QTY!=itbi.ACTUAL_QTY OR tibi.ADJUSTMENT_QTY!=itbi.ADJUSTMENT_QTY";
//        try {
        iCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(iCnt + " records updated in tmp as changed where a batch record has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//        }
        sqlString = "UPDATE tmp_inventory ti LEFT JOIN tmp_inventory_batch_info tibi ON ti.ID = tibi.PARENT_ID SET ti.CHANGED=1 WHERE tibi.CHANGED=1";
//        try {
        iCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(iCnt + " records updated in tmp as changed where a Batch Id has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//        }

        // Check if there are any rows that need to be added
        params.clear();
        sqlString = "SELECT COUNT(*) FROM tmp_inventory ti WHERE ti.CHANGED=1";
        int inventoryRows = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        if (inventoryRows > 0) {
            if (version == null) {
                params.put("programId", pd.getProgramId());
                params.put("curUser", commitUser.getUserId());
                params.put("curDate", curDate);
                params.put("versionTypeId", pd.getVersionType().getId());
                params.put("versionStatusId", pd.getVersionStatus().getId());
                params.put("notes", pd.getNotes());
                sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, null, null, null, null, :curUser, :curDate, 0)";
//                try {
                version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
                logger.info(version + " is the new version no");
//                } catch (Exception e) {
//                    logger.info("Failed to get a new version no for this program");
//                    throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//                }
            }
            params.put("versionId", version.getVersionId());
            // Insert the rows where Inventory Id is not null
            sqlString = "INSERT INTO rm_inventory_trans SELECT null, ti.INVENTORY_ID, ti.INVENTORY_DATE, ti.REGION_ID, ti.REALM_COUNTRY_PLANNING_UNIT_ID, ti.ACTUAL_QTY, ti.ADJUSTMENT_QTY, ti.DATA_SOURCE_ID, ti.NOTES, ti.ACTIVE, ti.LAST_MODIFIED_BY, ti.LAST_MODIFIED_DATE, :versionId"
                    + " FROM tmp_inventory ti "
                    + " WHERE ti.CHANGED=1 AND ti.INVENTORY_ID!=0";
//            try {
            inventoryRows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info(consumptionRows + " added to the inventory_trans table");
//            } catch (Exception e) {
//                logger.info("Failed to add to the inventory_trans table");
//                throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//            }
            params.clear();
            params.put("versionId", version.getVersionId());
            // Update the rm_inventory table with the latest versionId
            sqlString = "UPDATE tmp_inventory ti LEFT JOIN rm_inventory i ON i.INVENTORY_ID=ti.INVENTORY_ID SET i.MAX_VERSION_ID=:versionId, i.LAST_MODIFIED_BY=ti.LAST_MODIFIED_BY, i.LAST_MODIFIED_DATE=ti.LAST_MODIFIED_DATE WHERE ti.INVENTORY_ID IS NOT NULL AND ti.CHANGED=1";
//            try {
            this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("Updated the Version no in the inventory table");
//            } catch (Exception e) {
//                logger.info("Failed to update the Version no in the inventory table");
//                throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//            }
            // Insert into rm_inventory_trans_batch_info where the inventory record was already existing but has changed
            sqlString = "INSERT INTO rm_inventory_trans_batch_info SELECT null, it.INVENTORY_TRANS_ID, tibi.BATCH_ID, tibi.ACTUAL_QTY, tibi.ADJUSTMENT_QTY from tmp_inventory ti left join tmp_inventory_batch_info tibi ON tibi.PARENT_ID=ti.ID LEFT JOIN rm_inventory_trans it ON ti.INVENTORY_ID=it.INVENTORY_ID AND it.VERSION_ID=:versionId WHERE ti.CHANGED=1 AND ti.INVENTORY_ID IS NOT NULL AND tibi.PARENT_ID IS NOT NULL";
//            try {
            inventoryRows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info(inventoryRows + " rows inserted into the inventory_trans_batch_info table");
//            } catch (Exception e) {
//                logger.info("Failed to insert into the inventory_trans_batch_info table");
//                throw new CouldNotSaveException("Could not save Inventory Batch data - " + e.getMessage());
//            }

            sqlString = "SELECT ti.ID, null TEMP_ID, null TEMP_PARENT_ID, null TEMP_PARENT_LINKED_ID, ti.CREATED_BY, ti.CREATED_DATE, ti.LAST_MODIFIED_DATE, ti.LAST_MODIFIED_BY FROM tmp_inventory ti WHERE ti.INVENTORY_ID IS NULL OR ti.INVENTORY_ID=0";
            List<IdByAndDate> idListForInsert = this.namedParameterJdbcTemplate.query(sqlString, params, new IdByAndDateRowMapper());
            params.put("id", 0);
            params.put("versionId", version.getVersionId());
            params.put("programId", pd.getProgramId());
            params.put("createdBy", commitUser.getUserId());
            params.put("createdDate", curDate);
            params.put("lastModifiedBy", commitUser.getUserId());
            params.put("lastModifiedDate", curDate);
            inventoryRows = 0;
            for (IdByAndDate tmpId : idListForInsert) {
                params.replace("id", tmpId.getId());
                params.replace("createdBy", tmpId.getCreatedBy());
                params.replace("createdDate", tmpId.getCreatedDate());
                params.replace("lastModifiedBy", tmpId.getLastModifiedBy());
                params.replace("lastModifiedDate", tmpId.getLastModifiedDate());
                sqlString = "INSERT INTO rm_inventory (PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, MAX_VERSION_ID) VALUES (:programId, :createdBy, :createdDate, :lastModifiedBy, :lastModifiedDate, :versionId)";
//                try {
                inventoryRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the consumption table");
//                    throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//                }
                sqlString = "INSERT INTO rm_inventory_trans SELECT null, LAST_INSERT_ID(), ti.INVENTORY_DATE, ti.REGION_ID, ti.REALM_COUNTRY_PLANNING_UNIT_ID, ti.ACTUAL_QTY, ti.ADJUSTMENT_QTY, ti.DATA_SOURCE_ID, ti.NOTES, ti.ACTIVE, :lastModifiedBy, :lastModifiedDate, :versionId FROM tmp_inventory ti WHERE ti.ID=:id";
//                try {
                inventoryRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the consumption_trans table");
//                    throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//                }
                sqlString = "INSERT INTO rm_inventory_trans_batch_info SELECT null, LAST_INSERT_ID(), tibi.BATCH_ID, tibi.ACTUAL_QTY, tibi.ADJUSTMENT_QTY from tmp_inventory_batch_info tibi WHERE tibi.PARENT_ID=:id";
//                try {
                inventoryRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the consumption_trans_batch_info table");
//                    throw new CouldNotSaveException("Could not save Consumption Batch data - " + e.getMessage());
//                }
            }
            logger.info(inventoryRows + " records inserted into the inventory, inventory_trans and inventory_trans_batch_info tables");
        }

        // ###########################  Inventory  ############################################
        // ###########################  Shipment  #############################################
        params.clear();
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment`";
//        sqlString = "DROP TABLE IF EXISTS `tmp_shipment`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_shipment` ( "
                //        sqlString = "CREATE TABLE `tmp_shipment` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `TEMP_SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `PARENT_SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `TEMP_PARENT_SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `PARENT_LINKED_SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `TEMP_PARENT_LINKED_SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `SUGGESTED_QTY` BIGINT(20) UNSIGNED NULL, "
                + "  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NULL, "
                + "  `FUNDING_SOURCE_ID` INT(10) UNSIGNED NULL, "
                + "  `BUDGET_ID` INT(10) UNSIGNED NULL, "
                + "  `ACCOUNT_FLAG` TINYINT(1) UNSIGNED NULL, "
                + "  `ERP_FLAG` TINYINT(1) UNSIGNED NULL, "
                + "  `CURRENCY_ID` INT(10) UNSIGNED NULL, "
                + "  `CONVERSION_RATE_TO_USD` DECIMAL(12,2) UNSIGNED NULL, "
                + "  `EMERGENCY_ORDER` TINYINT(1) UNSIGNED NOT NULL, "
                + "  `LOCAL_PROCUREMENT` TINYINT(1) UNSIGNED NOT NULL, "
                + "  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `EXPECTED_DELIVERY_DATE` DATE NOT NULL, "
                + "  `PROCUREMENT_UNIT_ID` INT(10) UNSIGNED NULL, "
                + "  `SUPPLIER_ID` INT(10) UNSIGNED NULL, "
                + "  `SHIPMENT_QTY` BIGINT(20) UNSIGNED NULL, "
                + "  `SHIPMENT_RCPU_QTY` BIGINT(20) UNSIGNED NULL, "
                + "  `RATE` DECIMAL(12,4) NOT NULL, "
                + "  `PRODUCT_COST` DECIMAL(24,4) UNSIGNED NOT NULL, "
                + "  `SHIPMENT_MODE` VARCHAR(4) NOT NULL, "
                + "  `FREIGHT_COST` DECIMAL(24,4) UNSIGNED NOT NULL, "
                + "  `AUTO_GENERATED_FREIGHT` TINYINT(1) UNSIGNED NULL, "
                + "  `PLANNED_DATE` DATE NULL, "
                + "  `SUBMITTED_DATE` DATE NULL, "
                + "  `APPROVED_DATE` DATE NULL, "
                + "  `SHIPPED_DATE` DATE NULL, "
                + "  `ARRIVED_DATE` DATE NULL, "
                + "  `RECEIVED_DATE` DATE NULL, "
                + "  `SHIPMENT_STATUS_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `NOTES` TEXT NULL, "
                + "  `ORDER_NO` VARCHAR(50) NULL, "
                + "  `PRIME_LINE_NO` VARCHAR(10) NULL, "
                + "  `CREATED_BY` INT UNSIGNED NOT NULL, "
                + "  `CREATED_DATE` DATETIME NOT NULL, "
                + "  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, "
                + "  `LAST_MODIFIED_DATE` DATETIME NOT NULL, "
                + "  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1, "
                + "  `VERSION_ID` INT(10) NULL, "
                + "  `CHANGED` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_shipment_1_idx` (`SHIPMENT_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_2_idx` (`PLANNING_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_3_idx` (`PROCUREMENT_UNIT_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_4_idx` (`SUPPLIER_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_5_idx` (`SHIPMENT_STATUS_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_6_idx` (`ORDER_NO` ASC), "
                + "  INDEX `fk_tmp_shipment_7_idx` (`PRIME_LINE_NO` ASC), "
                + "  INDEX `fk_tmp_shipment_8_idx` (`DATA_SOURCE_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_9_idx` (`VERSION_ID` ASC),"
                + "  INDEX `fk_tmp_shipment_10_idx` (`PROCUREMENT_AGENT_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_11_idx` (`FUNDING_SOURCE_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_12_idx` (`BUDGET_ID` ASC) ) "
                + "  ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment_batch_info`";
//        sqlString = "DROP TABLE IF EXISTS `tmp_shipment_batch_info`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_shipment_batch_info` ( "
                //        sqlString = "CREATE TABLE `tmp_shipment_batch_info` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `PARENT_ID` INT(10) UNSIGNED NOT NULL, "
                + "  `SHIPMENT_TRANS_BATCH_INFO_ID` INT(10) UNSIGNED NULL, "
                + "  `SHIPMENT_TRANS_ID` INT(10) UNSIGNED NULL, "
                + "  `BATCH_ID` INT(10) NOT NULL, "
                + "  `BATCH_SHIPMENT_QTY` BIGINT(20) UNSIGNED NOT NULL, "
                + "  `CHANGED` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_consumption_1_idx` (`SHIPMENT_TRANS_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_2_idx` (`SHIPMENT_TRANS_BATCH_INFO_ID` ASC), "
                + "  INDEX `fk_tmp_consumption_3_idx` (`BATCH_ID` ASC)) "
                + "  ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        Map<Integer, Integer> tempAndNewShipmentId = new HashMap<>();
        insertList.clear();
        insertBatchList.clear();
        id = 1;
        int sCnt = 0;
        for (Shipment s : pd.getShipmentList()) {
            Map<String, Object> tp = new HashMap<>();
            tp.put("ID", id);
            tp.put("SHIPMENT_ID", (s.getShipmentId() == 0 ? null : s.getShipmentId()));
            tp.put("TEMP_SHIPMENT_ID", (s.getTempShipmentId() == null || s.getTempShipmentId() == 0 ? null : s.getTempShipmentId()));
            tp.put("PARENT_SHIPMENT_ID", (s.getParentShipmentId() == null || s.getParentShipmentId() == 0 ? null : s.getParentShipmentId()));
            tp.put("TEMP_PARENT_SHIPMENT_ID", (s.getTempParentShipmentId() == null || s.getTempParentShipmentId() == 0 ? null : s.getTempParentShipmentId()));
            tp.put("PARENT_LINKED_SHIPMENT_ID", (s.getParentLinkedShipmentId() == null || s.getParentLinkedShipmentId() == 0 ? null : s.getParentLinkedShipmentId()));
            tp.put("TEMP_PARENT_LINKED_SHIPMENT_ID", (s.getTempParentLinkedShipmentId() == null || s.getTempParentLinkedShipmentId() == 0 ? null : s.getTempParentLinkedShipmentId()));
//            Integer parentShipmentId = null;
//            if (s.getParentShipmentId() != null && s.getParentShipmentId() != 0) {
//                // Parent Shipment Id is not null, therefore this is an old linking
//                parentShipmentId = s.getParentShipmentId();
//            } else if (s.getTempParentShipmentId() != null && s.getTempParentShipmentId() != 0) {
//                // Parent Shipment Id was already null and not tempParentShipmentId is not null so it is a new Linking
//                // Calculate the ParentShipemnt Id from the Map
//                parentShipmentId = tempAndNewShipmentId.get(s.getTempParentShipmentId());
//            } else {
//                // Both of them are null therefore there is no linking and that means the parentShipmentId = null
//                parentShipmentId = null;
//            }
            tp.put("SUGGESTED_QTY", s.getSuggestedQty());
            tp.put("PROCUREMENT_AGENT_ID", (s.getProcurementAgent() == null || s.getProcurementAgent().getId() == null || s.getProcurementAgent().getId() == 0 ? null : s.getProcurementAgent().getId()));
            tp.put("FUNDING_SOURCE_ID", (s.getFundingSource() == null || s.getFundingSource().getId() == null || s.getFundingSource().getId() == 0 ? null : s.getFundingSource().getId()));
            tp.put("BUDGET_ID", (s.getBudget() == null || s.getBudget().getId() == null || s.getBudget().getId() == 0 ? null : s.getBudget().getId()));
            tp.put("ACCOUNT_FLAG", s.isAccountFlag());
            tp.put("ERP_FLAG", s.isErpFlag());
            tp.put("CURRENCY_ID", s.getCurrency().getCurrencyId());
            tp.put("CONVERSION_RATE_TO_USD", s.getCurrency().getConversionRateToUsd());
            tp.put("EMERGENCY_ORDER", s.isEmergencyOrder());
            tp.put("LOCAL_PROCUREMENT", s.isLocalProcurement());
            tp.put("PLANNING_UNIT_ID", s.getPlanningUnit().getId());
            tp.put("REALM_COUNTRY_PLANNING_UNIT_ID", s.getRealmCountryPlanningUnit().getId());
            tp.put("EXPECTED_DELIVERY_DATE", s.getExpectedDeliveryDate());
            tp.put("PROCUREMENT_UNIT_ID", (s.getProcurementUnit() == null || s.getProcurementUnit().getId() == null || s.getProcurementUnit().getId() == 0 ? null : s.getProcurementUnit().getId()));
            tp.put("SUPPLIER_ID", (s.getSupplier() == null || s.getSupplier().getId() == null || s.getSupplier().getId() == 0 ? null : s.getSupplier().getId()));
            tp.put("SHIPMENT_QTY", s.getShipmentQty());
            tp.put("SHIPMENT_RCPU_QTY", s.getShipmentRcpuQty());
            tp.put("RATE", s.getRate());
            tp.put("PRODUCT_COST", s.getProductCost());
            tp.put("SHIPMENT_MODE", s.getShipmentMode());
            tp.put("FREIGHT_COST", s.getFreightCost());
            if (s.isAutoGeneratedFreight() == null) {
                if (s.isErpFlag() || !s.isActive()) {
                    tp.put("AUTO_GENERATED_FREIGHT", 0);
                } else {
                    Double freightPerc = this.getFreightPerc(sp.getId(), s.getPlanningUnit().getId(), s.getProcurementAgent().getId(), s.getShipmentMode());
                    Double calculatedFreightCost = s.getProductCost() * freightPerc / 100.0;
                    DecimalFormat df = new DecimalFormat("#.00");
                    if (df.format(calculatedFreightCost).equals(df.format(s.getFreightCost()))) {
                        tp.put("AUTO_GENERATED_FREIGHT", 1);
                    } else {
                        tp.put("AUTO_GENERATED_FREIGHT", 0);
                    }
                }
            } else {
                tp.put("AUTO_GENERATED_FREIGHT", s.isAutoGeneratedFreight());
            }
            tp.put("PLANNED_DATE", s.getPlannedDate());
            tp.put("SUBMITTED_DATE", s.getSubmittedDate());
            tp.put("APPROVED_DATE", s.getApprovedDate());
            tp.put("SHIPPED_DATE", s.getShippedDate());
            tp.put("ARRIVED_DATE", s.getArrivedDate());
            tp.put("RECEIVED_DATE", s.getReceivedDate());
            tp.put("SHIPMENT_STATUS_ID", s.getShipmentStatus().getId());
            tp.put("DATA_SOURCE_ID", s.getDataSource().getId());
            tp.put("NOTES", s.getNotes());
            tp.put("ORDER_NO", (s.getOrderNo() == null || s.getOrderNo().isBlank() ? null : s.getOrderNo()));
            tp.put("PRIME_LINE_NO", (s.getPrimeLineNo() == null || s.getPrimeLineNo().isBlank() ? null : s.getPrimeLineNo()));
            tp.put("CREATED_BY", s.getCreatedBy().getUserId());
            tp.put("CREATED_DATE", s.getCreatedDate());
            tp.put("LAST_MODIFIED_BY", s.getLastModifiedBy().getUserId());
            tp.put("LAST_MODIFIED_DATE", s.getLastModifiedDate());
            tp.put("ACTIVE", s.isActive());
            tp.put("VERSION_ID", s.getVersionId());

            sqlString = "INSERT INTO tmp_shipment (`ID`, `SHIPMENT_ID`, `TEMP_SHIPMENT_ID`, `PARENT_SHIPMENT_ID`, `TEMP_PARENT_SHIPMENT_ID`, `PARENT_LINKED_SHIPMENT_ID`, `TEMP_PARENT_LINKED_SHIPMENT_ID`, `SUGGESTED_QTY`, `PROCUREMENT_AGENT_ID`, `ACCOUNT_FLAG`, `ERP_FLAG`, `CURRENCY_ID`, `CONVERSION_RATE_TO_USD`, `EMERGENCY_ORDER`, `PLANNING_UNIT_ID`,`REALM_COUNTRY_PLANNING_UNIT_ID`, `EXPECTED_DELIVERY_DATE`, `PROCUREMENT_UNIT_ID`, `SUPPLIER_ID`, `SHIPMENT_QTY`,`SHIPMENT_RCPU_QTY`, `RATE`, `PRODUCT_COST`, `SHIPMENT_MODE`, `FREIGHT_COST`, `AUTO_GENERATED_FREIGHT`, `PLANNED_DATE`, `SUBMITTED_DATE`, `APPROVED_DATE`, `SHIPPED_DATE`, `ARRIVED_DATE`, `RECEIVED_DATE`, `SHIPMENT_STATUS_ID`, `DATA_SOURCE_ID`, `NOTES`, `ORDER_NO`, `PRIME_LINE_NO`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`, `FUNDING_SOURCE_ID`, `BUDGET_ID`,LOCAL_PROCUREMENT, VERSION_ID) "
                    + "VALUES (:ID, :SHIPMENT_ID, :TEMP_SHIPMENT_ID, :PARENT_SHIPMENT_ID, :TEMP_PARENT_SHIPMENT_ID, :PARENT_LINKED_SHIPMENT_ID, :TEMP_PARENT_LINKED_SHIPMENT_ID, :SUGGESTED_QTY, :PROCUREMENT_AGENT_ID, :ACCOUNT_FLAG, :ERP_FLAG, :CURRENCY_ID, :CONVERSION_RATE_TO_USD, :EMERGENCY_ORDER, :PLANNING_UNIT_ID, :REALM_COUNTRY_PLANNING_UNIT_ID, :EXPECTED_DELIVERY_DATE, :PROCUREMENT_UNIT_ID, :SUPPLIER_ID, :SHIPMENT_QTY, :SHIPMENT_RCPU_QTY, :RATE, :PRODUCT_COST, :SHIPMENT_MODE, :FREIGHT_COST, :AUTO_GENERATED_FREIGHT, :PLANNED_DATE, :SUBMITTED_DATE, :APPROVED_DATE, :SHIPPED_DATE, :ARRIVED_DATE, :RECEIVED_DATE, :SHIPMENT_STATUS_ID, :DATA_SOURCE_ID, :NOTES, :ORDER_NO, :PRIME_LINE_NO, :CREATED_BY, :CREATED_DATE, :LAST_MODIFIED_BY, :LAST_MODIFIED_DATE, :ACTIVE, :FUNDING_SOURCE_ID, :BUDGET_ID ,:LOCAL_PROCUREMENT, :VERSION_ID)";
            this.namedParameterJdbcTemplate.update(sqlString, tp);
            sCnt++;

            SimpleJdbcInsert batchInsert = new SimpleJdbcInsert(dataSource).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
            for (ShipmentBatchInfo b : s.getBatchInfoList()) {
                if (b.getBatch().getBatchId() == 0) {
                    Map<String, Object> batchParams = new HashMap<>();
                    batchParams.put("BATCH_NO", b.getBatch().getBatchNo());
                    batchParams.put("PROGRAM_ID", pd.getProgramId());
                    batchParams.put("PLANNING_UNIT_ID", s.getPlanningUnit().getId());
                    batchParams.put("EXPIRY_DATE", b.getBatch().getExpiryDate());
                    batchParams.put("AUTO_GENERATED", b.getBatch().isAutoGenerated());
                    batchParams.put("CREATED_DATE", b.getBatch().getCreatedDate());
                    try {
                        b.getBatch().setBatchId(this.namedParameterJdbcTemplate.queryForObject("SELECT bi.BATCH_ID FROM rm_batch_info bi WHERE bi.BATCH_NO=:BATCH_NO AND bi.PROGRAM_ID=:PROGRAM_ID AND bi.EXPIRY_DATE=:EXPIRY_DATE", batchParams, Integer.class));
                        sqlString = "UPDATE rm_batch_info bi SET bi.CREATED_DATE=:CREATED_DATE  WHERE bi.BATCH_NO=:BATCH_NO AND bi.PROGRAM_ID=:PROGRAM_ID AND bi.EXPIRY_DATE=:EXPIRY_DATE ";
                        this.namedParameterJdbcTemplate.update(sqlString, batchParams);
                        logger.info("Batch No + Expiry Dt found for this Program");
                    } catch (DataAccessException d) {
                        logger.info("Batch No + Expiry Dt not found for this Program, so creating it");
                        b.getBatch().setBatchId(batchInsert.executeAndReturnKey(batchParams).intValue());
                        logger.info("Batch Id created");
                    }
                } else {
                    Map<String, Object> batchParams = new HashMap<>();
                    batchParams.put("BATCH_NO", b.getBatch().getBatchNo());
                    batchParams.put("AUTO_GENERATED", b.getBatch().isAutoGenerated());
                    batchParams.put("EXPIRY_DATE", b.getBatch().getExpiryDate());
                    batchParams.put("CREATED_DATE", b.getBatch().getCreatedDate());
                    batchParams.put("BATCH_ID", b.getBatch().getBatchId());
                    sqlString = "UPDATE rm_batch_info bi SET bi.CREATED_DATE=:CREATED_DATE,bi.BATCH_NO=:BATCH_NO,bi.EXPIRY_DATE=:EXPIRY_DATE,bi.AUTO_GENERATED=:AUTO_GENERATED  WHERE bi.BATCH_ID=:BATCH_ID";
                    this.namedParameterJdbcTemplate.update(sqlString, batchParams);
                }
                Map<String, Object> tb = new HashMap<>();
                tb.put("SHIPMENT_TRANS_ID", null);
                tb.put("SHIPMENT_TRANS_BATCH_INFO_ID", (b.getShipmentTransBatchInfoId() == 0 ? null : b.getShipmentTransBatchInfoId()));
                tb.put("PARENT_ID", id);
                tb.put("BATCH_ID", b.getBatch().getBatchId());
                tb.put("BATCH_SHIPMENT_QTY", b.getShipmentQty());
                insertBatchList.add(new MapSqlParameterSource(tb));
            }
            id++;
        }

//        try {
        logger.info(sCnt + " records imported into the tmp table");
        sqlString = "UPDATE tmp_shipment s LEFT JOIN rm_realm_country_planning_unit rcpu ON rcpu.REALM_COUNTRY_ID=:realmCountryId AND rcpu.PLANNING_UNIT_ID=s.PLANNING_UNIT_ID AND IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0))=1 SET s.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, s.SHIPMENT_RCPU_QTY=s.SHIPMENT_QTY WHERE s.REALM_COUNTRY_PLANNING_UNIT_ID IS NULL OR s.REALM_COUNTRY_PLANNING_UNIT_ID=0";
        params.clear();
        params.put("realmCountryId", sp.getRealmCountry().getId());
        int rcpuFixCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("RCPU fixed for " + rcpuFixCnt);

//        sqlString = "SELECT COUNT(*) FROM tmp_shipment s WHERE s.REALM_COUNTRY_PLANNING_UNIT_ID IS NULL OR s.REALM_COUNTRY_PLANNING_UNIT_ID=0";
//        int stillMissingSCPU = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
//        logger.info("Still found " + stillMissingSCPU + " RCPUs cannot proceed");
//        if (stillMissingSCPU > 0) {
//            throw new CouldNotSaveException("Still found " + stillMissingSCPU + " RCPUs cannot proceed");
//        }
//        } catch (Exception e) {
//            logger.info("Could not load the tmp shipment records going to throw a CouldNotSaveException");
//            throw new CouldNotSaveException("Could not save Shipment data - " + e.getMessage());
//        }
        if (insertBatchList.size() > 0) {
            SqlParameterSource[] insertShipmentBatch = new SqlParameterSource[insertBatchList.size()];
            sqlString = "INSERT INTO tmp_shipment_batch_info (PARENT_ID, SHIPMENT_TRANS_ID, SHIPMENT_TRANS_BATCH_INFO_ID, BATCH_ID, BATCH_SHIPMENT_QTY) VALUES (:PARENT_ID, :SHIPMENT_TRANS_ID, :SHIPMENT_TRANS_BATCH_INFO_ID, :BATCH_ID, :BATCH_SHIPMENT_QTY)";
//            try {
            sCnt = this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertBatchList.toArray(insertShipmentBatch)).length;
            logger.info(sCnt + " records imported into the tmp table");
//            } catch (Exception e) {
//                logger.info("Could not load the tmp shipment batch records going to throw a CouldNotSaveException");
//                throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//            }
        }
        params.clear();
        sqlString = "UPDATE tmp_shipment_batch_info tsbi LEFT JOIN rm_shipment_trans_batch_info stbi ON tsbi.SHIPMENT_TRANS_BATCH_INFO_ID=stbi.SHIPMENT_TRANS_BATCH_INFO_ID SET tsbi.SHIPMENT_TRANS_ID=stbi.SHIPMENT_TRANS_ID WHERE tsbi.SHIPMENT_TRANS_BATCH_INFO_ID IS NOT NULL";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        params.clear();
        // Flag the rows for changed records
        // Added condition for null dates
        sqlString = "UPDATE tmp_shipment ts LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID SET ts.CHANGED=1 WHERE ts.SHIPMENT_ID!=st.SHIPMENT_ID OR ts.SUGGESTED_QTY!=s.SUGGESTED_QTY OR ts.CURRENCY_ID!=s.CURRENCY_ID OR ts.PARENT_SHIPMENT_ID!=s.PARENT_SHIPMENT_ID OR ts.PARENT_LINKED_SHIPMENT_ID!=st.PARENT_LINKED_SHIPMENT_ID OR ts.PROCUREMENT_AGENT_ID!=st.PROCUREMENT_AGENT_ID OR ts.FUNDING_SOURCE_ID!=st.FUNDING_SOURCE_ID OR ts.BUDGET_ID!=st.BUDGET_ID OR (ts.BUDGET_ID IS NOT NULL AND st.BUDGET_ID IS NULL) OR (ts.BUDGET_ID IS NULL AND st.BUDGET_ID IS NOT NULL) OR ts.ACCOUNT_FLAG!=st.ACCOUNT_FLAG OR ts.ERP_FLAG!=st.ERP_FLAG OR ts.CONVERSION_RATE_TO_USD!=s.CONVERSION_RATE_TO_USD OR ts.EMERGENCY_ORDER!=st.EMERGENCY_ORDER OR ts.PLANNING_UNIT_ID!=st.PLANNING_UNIT_ID OR ts.REALM_COUNTRY_PLANNING_UNIT_ID!=st.REALM_COUNTRY_PLANNING_UNIT_ID OR ts.EXPECTED_DELIVERY_DATE!=st.EXPECTED_DELIVERY_DATE OR ts.PROCUREMENT_UNIT_ID!=st.PROCUREMENT_UNIT_ID OR ts.SUPPLIER_ID!=st.SUPPLIER_ID OR ts.SHIPMENT_QTY!=st.SHIPMENT_QTY OR ts.SHIPMENT_RCPU_QTY!=st.SHIPMENT_RCPU_QTY OR ts.RATE!=st.RATE OR ts.PRODUCT_COST!=st.PRODUCT_COST OR ts.SHIPMENT_MODE!=st.SHIPMENT_MODE OR ts.FREIGHT_COST!=st.FREIGHT_COST OR ts.PLANNED_DATE!=st.PLANNED_DATE OR (ts.PLANNED_DATE IS NULL AND st.PLANNED_DATE IS NOT NULL) OR (ts.PLANNED_DATE IS NOT NULL AND st.PLANNED_DATE IS NULL) OR ts.SUBMITTED_DATE!=st.SUBMITTED_DATE OR (ts.SUBMITTED_DATE IS NULL AND st.SUBMITTED_DATE IS NOT NULL) OR (ts.SUBMITTED_DATE IS NOT NULL AND st.SUBMITTED_DATE IS NULL) OR ts.APPROVED_DATE!=st.APPROVED_DATE OR (ts.APPROVED_DATE IS NULL AND st.APPROVED_DATE IS NOT NULL) OR (ts.APPROVED_DATE IS NOT NULL AND st.APPROVED_DATE IS NULL) OR ts.SHIPPED_DATE!=st.SHIPPED_DATE OR (ts.SHIPPED_DATE IS NULL AND st.SHIPPED_DATE IS NOT NULL) OR (ts.SHIPPED_DATE IS NOT NULL AND st.SHIPPED_DATE IS NULL) OR ts.ARRIVED_DATE!=st.ARRIVED_DATE OR (ts.ARRIVED_DATE IS NULL AND st.ARRIVED_DATE IS NOT NULL) OR (ts.ARRIVED_DATE IS NOT NULL AND st.ARRIVED_DATE IS NULL) OR ts.RECEIVED_DATE!=st.RECEIVED_DATE OR (ts.RECEIVED_DATE IS NULL AND st.RECEIVED_DATE IS NOT NULL) OR (ts.RECEIVED_DATE IS NOT NULL AND st.RECEIVED_DATE IS NULL) OR ts.SHIPMENT_STATUS_ID!=st.SHIPMENT_STATUS_ID OR ts.DATA_SOURCE_ID!=st.DATA_SOURCE_ID OR ts.NOTES!=st.NOTES OR ts.ORDER_NO!=st.ORDER_NO OR ts.PRIME_LINE_NO!=st.PRIME_LINE_NO OR ts.ACTIVE!=st.ACTIVE OR ts.LOCAL_PROCUREMENT!=st.LOCAL_PROCUREMENT OR ts.SHIPMENT_ID IS NULL";
//        try {
        sCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(sCnt + " records updated in tmp as changed where a direct shipment record has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//        }
        sqlString = "UPDATE tmp_shipment_batch_info tsbi LEFT JOIN rm_shipment_trans_batch_info stbi ON tsbi.SHIPMENT_TRANS_BATCH_INFO_ID=stbi.SHIPMENT_TRANS_BATCH_INFO_ID SET `CHANGED`=1 WHERE tsbi.SHIPMENT_TRANS_BATCH_INFO_ID IS NULL OR tsbi.BATCH_ID!=stbi.BATCH_ID OR tsbi.BATCH_SHIPMENT_QTY!=stbi.BATCH_SHIPMENT_QTY";
//        try {
        sCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(sCnt + " records updated in tmp as changed where a batch record has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//        }
        sqlString = "UPDATE tmp_shipment ts LEFT JOIN tmp_shipment_batch_info tsbi ON ts.ID = tsbi.PARENT_ID SET ts.CHANGED=1 WHERE tsbi.CHANGED=1";
//        try {
        sCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(sCnt + " records updated in tmp as changed where a Batch Id has changed");
//        } catch (Exception e) {
//            throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//        }

        // Check if there are any rows that need to be added
        params.clear();
        sqlString = "SELECT COUNT(*) FROM tmp_shipment ts WHERE ts.CHANGED=1";
        int shipmentRows = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        if (shipmentRows > 0) {
            // **********************************************************************************
            // TODO VIMP
            // I think its better to handle the new inserts first and then do handle the updates
            // **********************************************************************************
            if (version == null) {
                params.put("programId", pd.getProgramId());
                params.put("curUser", commitUser.getUserId());
                params.put("curDate", curDate);
                params.put("versionTypeId", pd.getVersionType().getId());
                params.put("versionStatusId", pd.getVersionStatus().getId());
                params.put("notes", pd.getNotes());
                sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, null, null, null, null, :curUser, :curDate, 0)";
//                try {
                version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
                logger.info(version + " is the new version no");
//                } catch (Exception e) {
//                    logger.info("Failed to get a new version no for this program");
//                    throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//                }
            }
            params.put("versionId", version.getVersionId());
            // Insert the rows where Shipment Id is not null
            sqlString = "INSERT INTO rm_shipment_trans (SHIPMENT_ID, PARENT_LINKED_SHIPMENT_ID, PLANNING_UNIT_ID,REALM_COUNTRY_PLANNING_UNIT_ID, EXPECTED_DELIVERY_DATE, PROCUREMENT_UNIT_ID, SUPPLIER_ID, SHIPMENT_QTY,SHIPMENT_RCPU_QTY, RATE, PRODUCT_COST, SHIPMENT_MODE, FREIGHT_COST, AUTO_GENERATED_FREIGHT, PLANNED_DATE, SUBMITTED_DATE, APPROVED_DATE, SHIPPED_DATE, ARRIVED_DATE, RECEIVED_DATE, SHIPMENT_STATUS_ID, DATA_SOURCE_ID, NOTES, ORDER_NO, PRIME_LINE_NO, ACTIVE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, VERSION_ID, PROCUREMENT_AGENT_ID, FUNDING_SOURCE_ID, BUDGET_ID, ACCOUNT_FLAG, ERP_FLAG, EMERGENCY_ORDER, LOCAL_PROCUREMENT) SELECT ts.SHIPMENT_ID,ts.PARENT_LINKED_SHIPMENT_ID, ts.PLANNING_UNIT_ID, ts.REALM_COUNTRY_PLANNING_UNIT_ID, ts.EXPECTED_DELIVERY_DATE, IF(ts.PROCUREMENT_UNIT_ID=0,null,ts.PROCUREMENT_UNIT_ID), IF(ts.SUPPLIER_ID=0,null,ts.SUPPLIER_ID), ts.SHIPMENT_QTY, ts.SHIPMENT_RCPU_QTY, ts.RATE, ts.PRODUCT_COST, ts.SHIPMENT_MODE, ts.FREIGHT_COST, ts.AUTO_GENERATED_FREIGHT, ts.PLANNED_DATE, ts.SUBMITTED_DATE, ts.APPROVED_DATE, ts.SHIPPED_DATE, ts.ARRIVED_DATE, ts.RECEIVED_DATE, ts.SHIPMENT_STATUS_ID, ts.DATA_SOURCE_ID, ts.NOTES, ts.ORDER_NO, ts.PRIME_LINE_NO, ts.ACTIVE, ts.LAST_MODIFIED_BY, ts.LAST_MODIFIED_DATE, :versionId, ts.PROCUREMENT_AGENT_ID, ts.FUNDING_SOURCE_ID, ts.BUDGET_ID, ts.ACCOUNT_FLAG, ts.ERP_FLAG, ts.EMERGENCY_ORDER, ts.LOCAL_PROCUREMENT"
                    + " FROM tmp_shipment ts "
                    + "WHERE ts.CHANGED=1 AND ts.SHIPMENT_ID IS NOT NULL";
//            try {
            shipmentRows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info(shipmentRows + " added to the shipment_trans table");
//            } catch (Exception e) {
//                logger.info("Failed to add to the shipment_trans table");
//                throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//            }
            params.clear();
            params.put("versionId", version.getVersionId());
            // Update the rm_shipment table with the latest versionId
            sqlString = "UPDATE tmp_shipment ts LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID SET s.SUGGESTED_QTY=ts.SUGGESTED_QTY, s.CURRENCY_ID=ts.CURRENCY_ID, s.CONVERSION_RATE_TO_USD=ts.CONVERSION_RATE_TO_USD, s.MAX_VERSION_ID=:versionId, s.LAST_MODIFIED_BY=ts.LAST_MODIFIED_BY, s.LAST_MODIFIED_DATE=ts.LAST_MODIFIED_DATE WHERE ts.SHIPMENT_ID IS NOT NULL AND ts.CHANGED=1";
//            try {
            this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("Updated the Version no in the shipment table");
//            } catch (Exception e) {
//                logger.info("Failed to update the Version no in the shipment table");
//                throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//            }
            // Insert into rm_shipment_trans_batch_info where the shipment record was already existing but has changed
            sqlString = "INSERT INTO rm_shipment_trans_batch_info SELECT null, st.SHIPMENT_TRANS_ID, tsbi.BATCH_ID, tsbi.BATCH_SHIPMENT_QTY FROM tmp_shipment ts left join tmp_shipment_batch_info tsbi ON tsbi.PARENT_ID=ts.ID LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID=:versionId WHERE ts.SHIPMENT_ID IS NOT NULL AND ts.CHANGED=1 AND tsbi.PARENT_ID IS NOT NULL";
//            try {
            shipmentRows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info(shipmentRows + " rows inserted into the shipment_trans_batch_info table");
//            } catch (Exception e) {
//                logger.info("Failed to insert into the shipment_trans_batch_info table");
//                throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//            }

            // Now handle the rows that have ShipmentId = 0 or null which means they are new
            sqlString = "SELECT ts.ID, ts.TEMP_SHIPMENT_ID TEMP_ID, ts.TEMP_PARENT_SHIPMENT_ID TEMP_PARENT_ID, ts.TEMP_PARENT_LINKED_SHIPMENT_ID TEMP_PARENT_LINKED_ID, ts.CREATED_BY, ts.CREATED_DATE, ts.LAST_MODIFIED_BY, ts.LAST_MODIFIED_DATE FROM tmp_shipment ts WHERE ts.SHIPMENT_ID IS NULL OR ts.SHIPMENT_ID=0";
            List<IdByAndDate> idListForInsert = this.namedParameterJdbcTemplate.query(sqlString, params, new IdByAndDateRowMapper());
            params.put("id", 0);
            params.put("parentLinkedShipmentId", null);
            params.put("versionId", version.getVersionId());
            params.put("programId", pd.getProgramId());
            params.put("createdBy", commitUser.getUserId());
            params.put("createdDate", curDate);
            params.put("lastModifiedBy", commitUser.getUserId());
            params.put("lastModifiedDate", curDate);
            for (IdByAndDate tmpId : idListForInsert) {
                params.replace("id", tmpId.getId());
                params.put("createdBy", tmpId.getCreatedBy());
                params.put("createdDate", tmpId.getCreatedDate());
                params.put("lastModifiedBy", tmpId.getLastModifiedBy());
                params.put("lastModifiedDate", tmpId.getLastModifiedDate());
                params.put("tempParentShipmentId", tempAndNewShipmentId.get(tmpId.getTempParentId()));
                params.put("tempParentLinkedShipmentId", tempAndNewShipmentId.get(tmpId.getTempParentLinkedId()));
                sqlString = "INSERT INTO rm_shipment (PROGRAM_ID, PARENT_SHIPMENT_ID, SUGGESTED_QTY, CURRENCY_ID, CONVERSION_RATE_TO_USD, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, MAX_VERSION_ID) SELECT :programId, IF(ts.TEMP_PARENT_SHIPMENT_ID IS NULL,ts.PARENT_SHIPMENT_ID,:tempParentShipmentId), ts.SUGGESTED_QTY, ts.CURRENCY_ID, ts.CONVERSION_RATE_TO_USD, :createdBy, :createdDate, :lastModifiedBy, :lastModifiedDate, :versionId FROM tmp_shipment ts WHERE ts.ID=:id";
//                try {
                shipmentRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the shipment table");
//                    throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//                }
                sqlString = "SELECT LAST_INSERT_ID()";
                int shipmentId = this.jdbcTemplate.queryForObject(sqlString, Integer.class);
                // Save the ShipmentId against the tmpShipmentId in the Map
                tempAndNewShipmentId.put(tmpId.getTempId(), shipmentId);
                sqlString = "INSERT INTO rm_shipment_trans (SHIPMENT_ID, PLANNING_UNIT_ID, REALM_COUNTRY_PLANNING_UNIT_ID, EXPECTED_DELIVERY_DATE, PROCUREMENT_UNIT_ID, SUPPLIER_ID, SHIPMENT_QTY, SHIPMENT_RCPU_QTY, RATE, PRODUCT_COST, SHIPMENT_MODE, FREIGHT_COST, PLANNED_DATE, SUBMITTED_DATE, APPROVED_DATE, SHIPPED_DATE, ARRIVED_DATE, RECEIVED_DATE, SHIPMENT_STATUS_ID, DATA_SOURCE_ID, NOTES, ORDER_NO, PRIME_LINE_NO, ACTIVE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, VERSION_ID, PROCUREMENT_AGENT_ID, FUNDING_SOURCE_ID, BUDGET_ID, ACCOUNT_FLAG, ERP_FLAG, EMERGENCY_ORDER, LOCAL_PROCUREMENT) SELECT LAST_INSERT_ID(), ts.PLANNING_UNIT_ID, ts.REALM_COUNTRY_PLANNING_UNIT_ID, ts.EXPECTED_DELIVERY_DATE, IF(ts.PROCUREMENT_UNIT_ID=0,null,ts.PROCUREMENT_UNIT_ID), IF(ts.SUPPLIER_ID=0,null,ts.SUPPLIER_ID), ts.SHIPMENT_QTY, ts.SHIPMENT_RCPU_QTY, ts.RATE, ts.PRODUCT_COST, ts.SHIPMENT_MODE, ts.FREIGHT_COST, ts.PLANNED_DATE, ts.SUBMITTED_DATE, ts.APPROVED_DATE, ts.SHIPPED_DATE, ts.ARRIVED_DATE, ts.RECEIVED_DATE, ts.SHIPMENT_STATUS_ID, ts.DATA_SOURCE_ID, ts.NOTES, ts.ORDER_NO, ts.PRIME_LINE_NO, ts.ACTIVE, :lastModifiedBy, :lastModifiedDate, :versionId, ts.PROCUREMENT_AGENT_ID, ts.FUNDING_SOURCE_ID, ts.BUDGET_ID, ts.ACCOUNT_FLAG, ts.ERP_FLAG, ts.EMERGENCY_ORDER, ts.LOCAL_PROCUREMENT FROM tmp_shipment ts WHERE ts.ID=:id";
                sqlString = "INSERT INTO rm_shipment_trans (SHIPMENT_ID, PARENT_LINKED_SHIPMENT_ID, PLANNING_UNIT_ID, REALM_COUNTRY_PLANNING_UNIT_ID, EXPECTED_DELIVERY_DATE, PROCUREMENT_UNIT_ID, SUPPLIER_ID, SHIPMENT_QTY, SHIPMENT_RCPU_QTY,RATE, PRODUCT_COST, SHIPMENT_MODE, FREIGHT_COST, PLANNED_DATE, SUBMITTED_DATE, APPROVED_DATE, SHIPPED_DATE, ARRIVED_DATE, RECEIVED_DATE, SHIPMENT_STATUS_ID, DATA_SOURCE_ID, NOTES, ORDER_NO, PRIME_LINE_NO, ACTIVE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, VERSION_ID, PROCUREMENT_AGENT_ID, FUNDING_SOURCE_ID, BUDGET_ID, ACCOUNT_FLAG, ERP_FLAG, EMERGENCY_ORDER, LOCAL_PROCUREMENT) SELECT LAST_INSERT_ID(), COALESCE(ts.PARENT_LINKED_SHIPMENT_ID, :parentLinkedShipmentId), ts.PLANNING_UNIT_ID, ts.REALM_COUNTRY_PLANNING_UNIT_ID, ts.EXPECTED_DELIVERY_DATE, IF(ts.PROCUREMENT_UNIT_ID=0,null,ts.PROCUREMENT_UNIT_ID), IF(ts.SUPPLIER_ID=0,null,ts.SUPPLIER_ID), ts.SHIPMENT_QTY, ts.SHIPMENT_RCPU_QTY, ts.RATE, ts.PRODUCT_COST, ts.SHIPMENT_MODE, ts.FREIGHT_COST, ts.PLANNED_DATE, ts.SUBMITTED_DATE, ts.APPROVED_DATE, ts.SHIPPED_DATE, ts.ARRIVED_DATE, ts.RECEIVED_DATE, ts.SHIPMENT_STATUS_ID, ts.DATA_SOURCE_ID, ts.NOTES, ts.ORDER_NO, ts.PRIME_LINE_NO, ts.ACTIVE, :lastModifiedBy, :lastModifiedDate, :versionId, ts.PROCUREMENT_AGENT_ID, ts.FUNDING_SOURCE_ID, ts.BUDGET_ID, ts.ACCOUNT_FLAG, ts.ERP_FLAG, ts.EMERGENCY_ORDER, ts.LOCAL_PROCUREMENT FROM tmp_shipment ts WHERE ts.ID=:id";
                params.replace("parentLinkedShipmentId", tempAndNewShipmentId.get(tmpId.getTempParentLinkedId()));
//                try {
                shipmentRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the shipment_trans table");
//                    throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//                }
                sqlString = "INSERT INTO rm_shipment_trans_batch_info (SHIPMENT_TRANS_ID, BATCH_ID, BATCH_SHIPMENT_QTY) SELECT LAST_INSERT_ID(), tsbi.BATCH_ID, tsbi.BATCH_SHIPMENT_QTY from tmp_shipment_batch_info tsbi WHERE tsbi.PARENT_ID=:id";
//                try {
                shipmentRows += this.namedParameterJdbcTemplate.update(sqlString, params);
//                } catch (Exception e) {
//                    logger.info("Failed to insert into the shipment_trans_batch_info table");
//                    throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//                }
            }
        }

        // ###########################  Shipment  ############################################
        // ###########################  Shipment Linking  ####################################
        params.clear();
        int slCnt = 0;
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment_linking`";
//        sqlString = "DROP TABLE IF EXISTS `tmp_shipment_linking`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_shipment_linking` ( "
                //                        sqlString = "CREATE TABLE `tmp_shipment_linking` ( "
                + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                + "  `SHIPMENT_LINKING_ID` INT(10) UNSIGNED NULL, "
                + "  `PROCUREMENT_AGENT_ID` INT(10) UNSIGNED NULL, "
                + "  `PARENT_SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `CHILD_SHIPMENT_ID` INT(10) UNSIGNED NULL, "
                + "  `RO_NO` VARCHAR(15) NULL, "
                + "  `RO_PRIME_LINE_NO` INT(10) UNSIGNED NULL, "
                + "  `ORIGINAL_PLANNING_UNIT_ID` INT(10) UNSIGNED NULL, "
                + "  `CONVERSION_FACTOR` DECIMAL(16,4) UNSIGNED NULL, "
                + "  `ORDER_NO` VARCHAR(15) NULL, "
                + "  `PRIME_LINE_NO` INT(10) NULL, "
                + "  `CREATED_BY` INT UNSIGNED NOT NULL, "
                + "  `CREATED_DATE` DATETIME NOT NULL, "
                + "  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, "
                + "  `LAST_MODIFIED_DATE` DATETIME NOT NULL, "
                + "  `ACTIVE` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1, "
                + "  `KN_SHIPMENT_NO` VARCHAR(45) NULL, "
                + "  `VERSION_ID` INT(10) NULL, "
                + "  `CHANGED` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, "
                + "  PRIMARY KEY (`ID`), "
                + "  INDEX `fk_tmp_shipment_linking_1_idx` (`SHIPMENT_LINKING_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_2_idx` (`PROCUREMENT_AGENT_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_3_idx` (`PARENT_SHIPMENT_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_4_idx` (`CHILD_SHIPMENT_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_5_idx` (`RO_NO` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_6_idx` (`RO_PRIME_LINE_NO` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_7_idx` (`ORDER_NO` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_8_idx` (`PRIME_LINE_NO` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_9_idx` (`KN_SHIPMENT_NO` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_10_idx` (`VERSION_ID` ASC), "
                + "  INDEX `fk_tmp_shipment_linking_11_idx` (`ORIGINAL_PLANNING_UNIT_ID` ASC))"
                + "  ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        insertList.clear();
        insertBatchList.clear();
        id = 1;
        for (ShipmentLinking s : pd.getShipmentLinkingList()) {
            Map<String, Object> tp = new HashMap<>();
            tp.put("ID", id);
            tp.put("SHIPMENT_LINKING_ID", (s.getShipmentLinkingId() == 0 ? null : s.getShipmentLinkingId()));
            tp.put("PROCUREMENT_AGENT_ID", s.getProcurementAgent().getId());
            Integer parentShipmentId = null;
            Integer childShipmentId = null;
            if (s.getParentShipmentId() != 0) {
                // Parent Shipment Id is not null, therefore this is an old linking
                parentShipmentId = s.getParentShipmentId();
            } else if (s.getTempParentShipmentId() != null && s.getTempParentShipmentId() != 0) {
                // Parent Shipment Id was already 0 and not tempParentShipmentId is not null so it is a new Linking
                // Calculate the ParentShipemnt Id from the Map
                parentShipmentId = tempAndNewShipmentId.get(s.getTempParentShipmentId());
            } else {
                // Both of them are null therefore there is no linking and that means the parentShipmentId = null
                parentShipmentId = null;
            }
            if (s.getChildShipmentId() != 0) {
                // Child Shipment Id is not null, therefore this is an old linking
                childShipmentId = s.getChildShipmentId();
            } else if (s.getTempChildShipmentId() != null && s.getTempChildShipmentId() != 0) {
                // Child Shipment Id was already 0 and not tempChildShipmentId is not null so it is a new Linking
                // Calculate the ChildShipemnt Id from the Map
                childShipmentId = tempAndNewShipmentId.get(s.getTempChildShipmentId());
            } else {
                // Both of them are null therefore there is no linking and that means the parentShipmentId = null
                childShipmentId = null;
            }
            tp.put("PARENT_SHIPMENT_ID", parentShipmentId);
            tp.put("CHILD_SHIPMENT_ID", childShipmentId);
            tp.put("ORIGINAL_PLANNING_UNIT_ID", s.getErpPlanningUnit().getId());
            tp.put("RO_NO", s.getRoNo());
            tp.put("RO_PRIME_LINE_NO", s.getRoPrimeLineNo());
            tp.put("CONVERSION_FACTOR", s.getConversionFactor());
            tp.put("ORDER_NO", (s.getOrderNo() == null || s.getOrderNo().isBlank() ? null : s.getOrderNo()));
            tp.put("PRIME_LINE_NO", (s.getPrimeLineNo() == null || s.getPrimeLineNo().isBlank() ? null : s.getPrimeLineNo()));
            tp.put("CREATED_BY", s.getCreatedBy().getUserId());
            tp.put("CREATED_DATE", s.getCreatedDate());
            tp.put("LAST_MODIFIED_BY", s.getLastModifiedBy().getUserId());
            tp.put("LAST_MODIFIED_DATE", s.getLastModifiedDate());
            tp.put("ACTIVE", s.isActive());
            tp.put("KN_SHIPMENT_NO", (s.getKnShipmentNo() == null || s.getKnShipmentNo().isBlank() ? null : s.getKnShipmentNo()));
            tp.put("VERSION_ID", s.getVersionId());
            insertList.add(new MapSqlParameterSource(tp));
            id++;
        }

        if (insertList.size() > 0) {
            SqlParameterSource[] insertShipmentLinking = new SqlParameterSource[insertList.size()];
            sqlString = "INSERT INTO tmp_shipment_linking (`ID`, `SHIPMENT_LINKING_ID`, `PROCUREMENT_AGENT_ID`, `PARENT_SHIPMENT_ID`, `CHILD_SHIPMENT_ID`, `RO_NO`, `RO_PRIME_LINE_NO`, `ORIGINAL_PLANNING_UNIT_ID`, `CONVERSION_FACTOR`, `ORDER_NO`, `PRIME_LINE_NO`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `ACTIVE`, `KN_SHIPMENT_NO`, `VERSION_ID`) VALUES (:ID, :SHIPMENT_LINKING_ID, :PROCUREMENT_AGENT_ID, :PARENT_SHIPMENT_ID, :CHILD_SHIPMENT_ID, :RO_NO, :RO_PRIME_LINE_NO, :ORIGINAL_PLANNING_UNIT_ID, :CONVERSION_FACTOR, :ORDER_NO, :PRIME_LINE_NO, :CREATED_BY, :CREATED_DATE, :LAST_MODIFIED_BY, :LAST_MODIFIED_DATE, :ACTIVE, :KN_SHIPMENT_NO, :VERSION_ID)";
            slCnt = this.namedParameterJdbcTemplate.batchUpdate(sqlString, insertList.toArray(insertShipmentLinking)).length;
            logger.info(slCnt + " records imported into the tmp table");
        }
        params.clear();
        // Flag the rows for changed records
//        sqlString = "UPDATE tmp_shipment_linking ts LEFT JOIN rm_shipment_linking s ON ts.SHIPMENT_LINKING_ID=s.SHIPMENT_LINKING_ID LEFT JOIN rm_shipment_linking_trans st ON ts.SHIPMENT_LINKING_ID=st.SHIPMENT_LINKING_ID AND ts.VERSION_ID=st.VERSION_ID SET ts.CHANGED=1 WHERE ts.SHIPMENT_LINKING_ID!=st.SHIPMENT_LINKING_ID OR ts.PROCUREMENT_AGENT_ID!=s.PROCUREMENT_AGENT_ID OR ts.PARENT_SHIPMENT_ID!=s.PARENT_SHIPMENT_ID OR ts.CHILD_SHIPMENT_ID!=s.CHILD_SHIPMENT_ID OR ts.RO_NO!=s.RO_NO OR ts.RO_PRIME_LINE_NO!=s.RO_PRIME_LINE_NO OR ts.CONVERSION_FACTOR!=s.CONVERSION_FACTOR OR ts.ORDER_NO!=s.ORDER_NO OR ts.PRIME_LINE_NO!=s.PRIME_LINE_NO OR ts.ACTIVE!=st.ACTIVE OR ts.KN_SHIPMENT_NO!=st.KN_SHIPMENT_NO OR ts.SHIPMENT_ID IS NULL";
        sqlString = "UPDATE tmp_shipment_linking ts LEFT JOIN rm_shipment_linking s ON ts.SHIPMENT_LINKING_ID=s.SHIPMENT_LINKING_ID LEFT JOIN rm_shipment_linking_trans st ON ts.SHIPMENT_LINKING_ID=st.SHIPMENT_LINKING_ID AND s.MAX_VERSION_ID=st.VERSION_ID SET ts.CHANGED=1 WHERE ts.ACTIVE!=st.ACTIVE OR ts.SHIPMENT_LINKING_ID IS NULL";
        slCnt = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info(slCnt + " records updated in tmp as changed where a direct shipment record has changed");
        // Check if there are any rows that need to be added
        params.clear();
        sqlString = "SELECT COUNT(*) FROM tmp_shipment_linking ts WHERE ts.CHANGED=1";
        int shipmentLinkingRows = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        if (shipmentLinkingRows > 0) {
            if (version == null) {
                params.put("programId", pd.getProgramId());
                params.put("curUser", commitUser.getUserId());
                params.put("curDate", curDate);
                params.put("versionTypeId", pd.getVersionType().getId());
                params.put("versionStatusId", pd.getVersionStatus().getId());
                params.put("notes", pd.getNotes());
                sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, null, null, null, null, :curUser, :curDate, 0)";
                version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
                logger.info(version + " is the new version no");
            }
            params.put("versionId", version.getVersionId());
            // Insert the rows where Shipment Id is not null
            sqlString = "INSERT INTO rm_shipment_linking_trans (SHIPMENT_LINKING_ID, CONVERSION_FACTOR, ACTIVE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, VERSION_ID) SELECT ts.SHIPMENT_LINKING_ID, ts.CONVERSION_FACTOR, ts.ACTIVE, ts.LAST_MODIFIED_BY, ts.LAST_MODIFIED_DATE, :versionId"
                    + " FROM tmp_shipment_linking ts "
                    + "WHERE ts.CHANGED=1 AND ts.SHIPMENT_LINKING_ID IS NOT NULL";
            shipmentLinkingRows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info(shipmentLinkingRows + " added to the shipment_trans table");
            params.clear();
            params.put("versionId", version.getVersionId());
            // Update the rm_shipment table with the latest versionId
            sqlString = "UPDATE tmp_shipment_linking ts LEFT JOIN rm_shipment_linking s ON ts.SHIPMENT_LINKING_ID=s.SHIPMENT_LINKING_ID SET s.PROCUREMENT_AGENT_ID=ts.PROCUREMENT_AGENT_ID, s.PARENT_SHIPMENT_ID=ts.PARENT_SHIPMENT_ID, s.CHILD_SHIPMENT_ID=ts.CHILD_SHIPMENT_ID, s.RO_NO=ts.RO_NO, s.RO_PRIME_LINE_NO=ts.RO_PRIME_LINE_NO, s.ORDER_NO=ts.ORDER_NO, s.PRIME_LINE_NO=ts.PRIME_LINE_NO, s.KN_SHIPMENT_NO=ts.KN_SHIPMENT_NO, s.CONVERSION_FACTOR=ts.CONVERSION_FACTOR, s.ACTIVE=ts.ACTIVE, s.MAX_VERSION_ID=:versionId, s.LAST_MODIFIED_BY=ts.LAST_MODIFIED_BY, s.LAST_MODIFIED_DATE=ts.LAST_MODIFIED_DATE WHERE ts.SHIPMENT_LINKING_ID IS NOT NULL AND ts.CHANGED=1";
            this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("Updated the Version no in the shipment table");
            sqlString = "SELECT ts.ID, null TEMP_ID, null TEMP_PARENT_ID, null TEMP_PARENT_LINKED_ID, ts.CREATED_BY, ts.CREATED_DATE, ts.LAST_MODIFIED_BY, ts.LAST_MODIFIED_DATE FROM tmp_shipment_linking ts WHERE ts.SHIPMENT_LINKING_ID IS NULL OR ts.SHIPMENT_LINKING_ID=0";
            List<IdByAndDate> idListForInsert = this.namedParameterJdbcTemplate.query(sqlString, params, new IdByAndDateRowMapper());
            params.put("id", 0);
            params.put("versionId", version.getVersionId());
            params.put("programId", pd.getProgramId());
            params.put("createdBy", commitUser.getUserId());
            params.put("createdDate", curDate);
            params.put("lastModifiedBy", commitUser.getUserId());
            params.put("lastModifiedDate", curDate);
            for (IdByAndDate tmpId : idListForInsert) {
                params.replace("id", tmpId.getId());
                params.put("createdBy", tmpId.getCreatedBy());
                params.put("createdDate", tmpId.getCreatedDate());
                params.put("lastModifiedBy", tmpId.getLastModifiedBy());
                params.put("lastModifiedDate", tmpId.getLastModifiedDate());
                sqlString = "INSERT INTO rm_shipment_linking (PROGRAM_ID, PROCUREMENT_AGENT_ID, PARENT_SHIPMENT_ID, CHILD_SHIPMENT_ID, RO_NO, RO_PRIME_LINE_NO, ORIGINAL_PLANNING_UNIT_ID, CONVERSION_FACTOR, ACTIVE, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, ORDER_NO, PRIME_LINE_NO, KN_SHIPMENT_NO, MAX_VERSION_ID) SELECT :programId, ts.PROCUREMENT_AGENT_ID, ts.PARENT_SHIPMENT_ID, ts.CHILD_SHIPMENT_ID, ts.RO_NO, ts.RO_PRIME_LINE_NO, ts.ORIGINAL_PLANNING_UNIT_ID, ts.CONVERSION_FACTOR, ts.ACTIVE,  :createdBy, :createdDate, :lastModifiedBy, :lastModifiedDate, ts.ORDER_NO, ts.PRIME_LINE_NO, ts.KN_SHIPMENT_NO, :versionId FROM tmp_shipment_linking ts WHERE ts.ID=:id";
                shipmentRows += this.namedParameterJdbcTemplate.update(sqlString, params);
                sqlString = "INSERT INTO rm_shipment_linking_trans (SHIPMENT_LINKING_ID, CONVERSION_FACTOR, ACTIVE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE, VERSION_ID) SELECT LAST_INSERT_ID(), ts.CONVERSION_FACTOR, ts.ACTIVE, :lastModifiedBy, :lastModifiedDate, :versionId FROM tmp_shipment_linking ts WHERE ts.ID=:id";
                shipmentRows += this.namedParameterJdbcTemplate.update(sqlString, params);
            }
        }
        // ###########################  Shipment Linking  ####################################
        // #########################  Problem Report #########################################
        insertList.clear();
        insertBatchList.clear();
        id = 1;
        boolean updatedProblemAction = false;
        boolean updatedProblemActionRowLevel;
        for (ProblemReport pr : pd.getProblemReportList()) {
            updatedProblemActionRowLevel = false;
            Map<String, Object> tp = new HashMap<>();
            tp.put("PROBLEM_REPORT_ID", (pr.getProblemReportId() == 0 ? null : pr.getProblemReportId()));
            tp.put("REALM_PROBLEM_ID", pr.getRealmProblem().getRealmProblemId());
            tp.put("PROGRAM_ID", pr.getProgram().getId());
            tp.put("VERSION_ID", (version == null ? pr.getVersionId() : version.getVersionId()));
            tp.put("PROBLEM_TYPE_ID", pr.getProblemType().getId());
            tp.put("PROBLEM_STATUS_ID", pr.getProblemStatus().getId());
            tp.put("DATA1", pr.getDt()); // Dt
            tp.put("DATA2", (pr.getRegion() != null ? pr.getRegion().getId() : null)); // RegionId
            tp.put("DATA3", (pr.getPlanningUnit() != null ? pr.getPlanningUnit().getId() : null)); // PlanningUnitId
            tp.put("DATA4", pr.getShipmentId()); // ShipmentId
            tp.put("DATA5", pr.getData5());
//            tp.put("REVIWED", pr.isReviewed());
            tp.put("REVIWED", pd.getVersionType().getId() == 2 && (pr.getProblemStatus().getId() == 3 || pr.getProblemStatus().getId() == 1) ? false : pr.isReviewed());
            tp.put("CREATED_BY", pr.getCreatedBy().getUserId());
            tp.put("CREATED_DATE", pr.getCreatedDate());
            tp.put("LAST_MODIFIED_BY", pr.getLastModifiedBy().getUserId());
            tp.put("LAST_MODIFIED_DATE", pr.getLastModifiedDate());
            if (pr.getProblemReportId() == 0) {
                this.namedParameterJdbcTemplate.update("INSERT INTO `rm_problem_report` (`REALM_PROBLEM_ID`, `PROGRAM_ID`, `VERSION_ID`, `PROBLEM_TYPE_ID`, `PROBLEM_STATUS_ID`, `DATA1`, `DATA2`, `DATA3`, `DATA4`, `DATA5`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`,`REVIEWED`) VALUES (:REALM_PROBLEM_ID, :PROGRAM_ID, :VERSION_ID, :PROBLEM_TYPE_ID, :PROBLEM_STATUS_ID, :DATA1, :DATA2, :DATA3, :DATA4, :DATA5, :CREATED_BY, :CREATED_DATE, :LAST_MODIFIED_BY, :LAST_MODIFIED_DATE,:REVIWED)", tp);
                updatedProblemActionRowLevel = true;
                sqlString = "SELECT LAST_INSERT_ID()";
                pr.setProblemReportId(this.jdbcTemplate.queryForObject(sqlString, Integer.class));
                for (ProblemReportTrans prt : pr.getProblemTransList()) {
                    if (prt.getCreatedDate() != null) {
                        Map<String, Object> transParams = new HashMap<>();
                        transParams.put("PROBLEM_REPORT_ID", pr.getProblemReportId());
                        transParams.put("PROBLEM_STATUS_ID", prt.getProblemStatus().getId());
                        transParams.put("NOTES", prt.getNotes());
//                    transParams.put("REVIEWED", prt.isReviewed());
                        transParams.put("REVIEWED", pd.getVersionType().getId() == 2 && (prt.getProblemStatus().getId() == 3 || prt.getProblemStatus().getId() == 1) ? false : prt.isReviewed());
                        transParams.put("CREATED_BY", prt.getCreatedBy().getUserId());
                        transParams.put("CREATED_DATE", prt.getCreatedDate());
                        this.namedParameterJdbcTemplate.update("INSERT INTO `rm_problem_report_trans` (`PROBLEM_REPORT_ID`, `PROBLEM_STATUS_ID`, `NOTES`, `REVIEWED`, `CREATED_BY`, `CREATED_DATE`) VALUES (:PROBLEM_REPORT_ID, :PROBLEM_STATUS_ID, :NOTES, :REVIEWED, :CREATED_BY, :CREATED_DATE)", transParams);
                    }
                }
            } else {
                for (ProblemReportTrans prt : pr.getProblemTransList()) {
                    if (prt.getProblemReportTransId() == 0 && prt.getCreatedDate() != null) {
                        Map<String, Object> transParams = new HashMap<>();
                        transParams.put("PROBLEM_REPORT_ID", pr.getProblemReportId());
                        transParams.put("PROBLEM_STATUS_ID", prt.getProblemStatus().getId());
                        transParams.put("NOTES", prt.getNotes());
                        transParams.put("REVIEWED", pd.getVersionType().getId() == 2 && (prt.getProblemStatus().getId() == 3 || prt.getProblemStatus().getId() == 1) ? false : prt.isReviewed());
                        transParams.put("CREATED_BY", prt.getCreatedBy().getUserId());
                        transParams.put("CREATED_DATE", prt.getCreatedDate());
                        this.namedParameterJdbcTemplate.update("INSERT INTO `rm_problem_report_trans` (`PROBLEM_REPORT_ID`, `PROBLEM_STATUS_ID`, `NOTES`, `REVIEWED`, `CREATED_BY`, `CREATED_DATE`) VALUES (:PROBLEM_REPORT_ID, :PROBLEM_STATUS_ID, :NOTES, :REVIEWED, :CREATED_BY, :CREATED_DATE)", transParams);
                        updatedProblemActionRowLevel = true;
                    }
                }
                if (updatedProblemActionRowLevel) {
//                    sqlString = "UPDATE rm_problem_report pr SET pr.PROBLEM_STATUS_ID=:PROBLEM_STATUS_ID, pr.LAST_MODIFIED_BY=:LAST_MODIFIED_BY, pr.LAST_MODIFIED_DATE=:LAST_MODIFIED_DATE WHERE pr.PROBLEM_REPORT_ID=:PROBLEM_REPORT_ID AND (pr.PROBLEM_STATUS_ID!=:PROBLEM_STATUS_ID OR pr.LAST_MODIFIED_BY!=:LAST_MODIFIED_BY OR pr.LAST_MODIFIED_DATE!=:LAST_MODIFIED_DATE)";
                    sqlString = "UPDATE rm_problem_report pr  "
                            + "SET pr.PROBLEM_STATUS_ID=:PROBLEM_STATUS_ID,  "
                            + "pr.REVIEWED=:REVIWED, "
                            + "pr.LAST_MODIFIED_BY=:LAST_MODIFIED_BY,  "
                            + "pr.LAST_MODIFIED_DATE=:LAST_MODIFIED_DATE  "
                            + "WHERE pr.PROBLEM_REPORT_ID=:PROBLEM_REPORT_ID  "
                            + "AND (pr.PROBLEM_STATUS_ID!=:PROBLEM_STATUS_ID OR pr.REVIEWED!=:REVIWED OR pr.LAST_MODIFIED_BY!=:LAST_MODIFIED_BY OR pr.LAST_MODIFIED_DATE!=:LAST_MODIFIED_DATE);";
                    this.namedParameterJdbcTemplate.update(sqlString, tp);
                }

                sqlString = "UPDATE rm_problem_report pr  "
                        + "SET pr.DATA5=:DATA5,  "
                        + "pr.DATA1=:DATA1, "
                        + "pr.LAST_MODIFIED_BY=:LAST_MODIFIED_BY,  "
                        + "pr.LAST_MODIFIED_DATE=:LAST_MODIFIED_DATE  "
                        + "WHERE pr.PROBLEM_REPORT_ID=:PROBLEM_REPORT_ID  "
                        + "AND (pr.DATA5!=:DATA5 OR pr.DATA1!=:DATA1 OR pr.LAST_MODIFIED_BY!=:LAST_MODIFIED_BY OR pr.LAST_MODIFIED_DATE!=:LAST_MODIFIED_DATE);";
                this.namedParameterJdbcTemplate.update(sqlString, tp);
            }
            updatedProblemAction |= updatedProblemActionRowLevel;
        }
//        }
        // #########################  Problem Report #########################################
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption_trans_batch_info`";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_inventory`";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_inventory_trans_batch_info`";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment`";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment_trans_batch_info`";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment_linking`";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        if (version == null) {
            if (updatedProblemAction) {
                params.put("programId", pd.getProgramId());
                params.put("curUser", commitUser.getUserId());
                params.put("curDate", curDate);
                params.put("versionTypeId", pd.getVersionType().getId());
                params.put("versionStatusId", pd.getVersionStatus().getId());
                params.put("notes", pd.getNotes());
                sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, null, null, null, null, :curUser, :curDate, 0)";
//                try {
                version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
                logger.info(version + " is the new version no");
//                } catch (Exception e) {
//                    logger.info("Failed to get a new version no for this program");
//                    throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//                }
//                        return version;
            } else {
                if (pd.getCurrentVersion().getVersionType().getId().equals(pd.getVersionType().getId())) {
                    version = new Version(0, null, null, null, null, null, null, null);
                } else {
                    params.put("programId", pd.getProgramId());
                    params.put("curUser", commitUser.getUserId());
                    params.put("curDate", curDate);
                    params.put("versionTypeId", pd.getVersionType().getId());
                    params.put("versionStatusId", pd.getVersionStatus().getId());
                    params.put("notes", pd.getNotes());
                    sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, null, null, null, null, :curUser, :curDate, 0)";
//                    try {
                    version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
                    logger.info(version + " is the new version no");
//                    } catch (Exception e) {
//                        logger.info("Failed to get a new version no for this program");
//                        throw new CouldNotSaveException("Could not save Shipment Batch data - " + e.getMessage());
//                    }
//                            return version;
                }
            }
        } else {
//                    return version;
        }
        params.clear();
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        params.put("versionId", version.getVersionId());
        params.put("COMMIT_REQUEST_ID", spcr.getCommitRequestId());
        return version;
    }

    @Override
    @Transactional
    public Version processDatasetCommitRequest(CommitRequest spcr, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        // Get the Dataset that needs to be committed
        DatasetData dd = spcr.getDatasetData();
        Map<String, Map<String, Integer>> oldAndNewIdMap = new HashMap<>();

        // Mark the CommitRequest as Started
        String sqlString = "UPDATE ct_commit_request cr SET cr.STARTED_DATE=:curDate WHERE cr.COMMIT_REQUEST_ID=:commitRequestId";
        Map<String, Object> params = new HashMap<>();
        params.put("commitRequestId", spcr.getCommitRequestId());
        params.put("curDate", curDate);
        this.namedParameterJdbcTemplate.update(sqlString, params);

        // Get the new VersionId
        sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, :forecastStartDate, :forecastStopDate, :daysInMonth, :freightPerc, :forecastThresholdHighPerc, :forecastThresholdLowPerc, :curUser, :curDate, 0)";
        params.clear();
        params.put("programId", spcr.getProgram().getId());
        params.put("versionTypeId", spcr.getVersionType().getId());
        params.put("versionStatusId", 2);
        params.put("notes", spcr.getNotes());
        params.put("forecastStartDate", dd.getCurrentVersion().getForecastStartDate());
        params.put("forecastStopDate", dd.getCurrentVersion().getForecastStopDate());
        params.put("daysInMonth", dd.getCurrentVersion().getDaysInMonth());
        params.put("freightPerc", dd.getCurrentVersion().getFreightPerc());
        params.put("forecastThresholdHighPerc", dd.getCurrentVersion().getForecastThresholdHighPerc());
        params.put("forecastThresholdLowPerc", dd.getCurrentVersion().getForecastThresholdLowPerc());
        params.put("curUser", spcr.getCreatedBy().getUserId());
        params.put("curDate", spcr.getCreatedDate());
        Version version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());

        params.clear();
        // Step 1 -- Insert Actual Consumption
        final List<SqlParameterSource> batchList = new ArrayList<>();
        for (ForecastActualConsumption fac : dd.getActualConsumptionList()) {
            Map<String, Object> batchParams = new HashMap<>();
            batchParams.put("PROGRAM_ID", spcr.getProgram().getId());
            batchParams.put("PLANNING_UNIT_ID", fac.getPlanningUnit().getId());
            batchParams.put("REGION_ID", fac.getRegion().getId());
            batchParams.put("MONTH", fac.getMonth());
            batchParams.put("AMOUNT", fac.getAmount());
            batchParams.put("REPORTING_RATE", fac.getReportingRate());
            batchParams.put("DAYS_OF_STOCK_OUT", fac.getDaysOfStockOut());
            batchParams.put("ADJUSTED_AMOUNT", fac.getAdjustedAmount());
            batchParams.put("PU_AMOUNT", fac.getPuAmount());
            batchParams.put("VERSION_ID", version.getVersionId());
            batchParams.put("CREATED_BY", fac.getCreatedBy().getUserId());
            batchParams.put("CREATED_DATE", fac.getCreatedDate());
            batchList.add(new MapSqlParameterSource(batchParams));
        }
        SqlParameterSource[] batchArray = new SqlParameterSource[batchList.size()];
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_actual_consumption");
        si.executeBatch(batchList.toArray(batchArray));

        batchList.clear();
        params.clear();
        batchArray = null;
        si = null;

        // Step 2 -- Insert Consumption Extrapolation and Data
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_consumption_extrapolation").usingGeneratedKeyColumns("CONSUMPTION_EXTRAPOLATION_ID");
        SimpleJdbcInsert siData = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_consumption_extrapolation_data");
        for (ForecastConsumptionExtrapolation fce : dd.getConsumptionExtrapolation()) {
            batchList.clear();
            params.clear();
            params.put("PROGRAM_ID", spcr.getProgram().getId());
            params.put("PLANNING_UNIT_ID", fce.getPlanningUnit().getId());
            params.put("REGION_ID", fce.getRegion().getId());
            params.put("EXTRAPOLATION_METHOD_ID", fce.getExtrapolationMethod().getId());
            params.put("NOTES", fce.getNotes());
            params.put("VERSION_ID", version.getVersionId());
            params.put("JSON_PROPERTIES", fce.getJsonPropertiesString());
            params.put("CREATED_BY", fce.getCreatedBy().getUserId());
            params.put("CREATED_DATE", fce.getCreatedDate());
            int conspuptionExtrapolationId = si.executeAndReturnKey(params).intValue();
            updateOldAndNewId(oldAndNewIdMap, "rm_forecast_consumption_extrapolation", Integer.toString(fce.getConsumptionExtrapolationId()), conspuptionExtrapolationId);
            for (ExtrapolationData fced : fce.getExtrapolationDataList()) {
                Map<String, Object> batchParams = new HashMap<>();
                batchParams.put("CONSUMPTION_EXTRAPOLATION_ID", conspuptionExtrapolationId);
                batchParams.put("MONTH", fced.getMonth());
                batchParams.put("AMOUNT", fced.getAmount());
                batchParams.put("CI", fced.getCi());
                batchList.add(new MapSqlParameterSource(batchParams));
            }
            batchArray = new SqlParameterSource[batchList.size()];
            siData.executeBatch(batchList.toArray(batchArray));
        }

        params.clear();
        batchList.clear();
        batchArray = null;
        si = null;
        siData = null;

        //Step 3 -- Insert ForecastTree and related tables
        for (DatasetTree dt : dd.getTreeList()) {
            si = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree").usingGeneratedKeyColumns("TREE_ID");
            // Step 3Ai -- Insert the Forecast Tree
            params.put("TREE_ANCHOR_ID", (dt.getTreeAnchorId() == 0 ? null : dt.getTreeAnchorId()));
            params.put("PROGRAM_ID", spcr.getProgram().getId());
            params.put("VERSION_ID", version.getVersionId());
            int labelId = this.labelDao.addLabel(dt.getLabel(), LabelConstants.RM_FORECAST_TREE, spcr.getCreatedBy().getUserId());
            params.put("LABEL_ID", labelId);
            params.put("FORECAST_METHOD_ID", dt.getForecastMethod().getId());
            params.put("CREATED_BY", dt.getCreatedBy().getUserId());
            params.put("CREATED_DATE", dt.getCreatedDate());
            params.put("LAST_MODIFIED_BY", dt.getLastModifiedBy().getUserId());
            params.put("LAST_MODIFIED_DATE", dt.getLastModifiedDate());
            params.put("ACTIVE", dt.isActive());
            params.put("NOTES", dt.getNotes());
            int treeId = si.executeAndReturnKey(params).intValue();
            // Check if the TreeAnchorId is available if not then add it to the table and get the value here.
            if (dt.getTreeAnchorId() == 0) {
                SimpleJdbcInsert sita = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_anchor").usingGeneratedKeyColumns("TREE_ANCHOR_ID");
                params.clear();
                params.put("PROGRAM_ID", spcr.getProgram().getId());
                params.put("TREE_NAME", dt.getLabel().getLabel_en());
                params.put("CREATED_DATE", dt.getCreatedDate());
                params.put("TREE_ID", treeId);
                dt.setTreeAnchorId(sita.executeAndReturnKey(params).intValue());
                params.clear();
                params.put("treeId", treeId);
                params.put("treeAnchorId", dt.getTreeAnchorId());
                this.namedParameterJdbcTemplate.update("UPDATE rm_forecast_tree t SET t.TREE_ANCHOR_ID=:treeAnchorId WHERE t.TREE_ID=:treeId", params);
            }
            updateOldAndNewId(oldAndNewIdMap, "rm_forecast_tree", Integer.toString(dt.getTreeId()), treeId);
            SimpleJdbcInsert ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node").usingGeneratedKeyColumns("NODE_ID");
            SimpleJdbcInsert n2 = null;
            SimpleJdbcInsert n3 = null;
            // Step 3Aii -- Insert the Region list for the Forecast Tree
            batchList.clear();
            for (SimpleObject region : dt.getRegionList()) {
                Map<String, Object> batchParams = new HashMap<>();
                batchParams.put("TREE_ID", treeId);
                batchParams.put("REGION_ID", region.getId());
                batchList.add(new MapSqlParameterSource(batchParams));
            }
            batchArray = new SqlParameterSource[batchList.size()];
            si = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_region");
            si.executeBatch(batchList.toArray(batchArray));

            // Step 3Aiii -- Insert the Level list for the Forecast Tree
            batchList.clear();
            for (TreeLevel level : dt.getLevelList()) {
                Map<String, Object> batchParams = new HashMap<>();
                batchParams.put("TREE_ID", treeId);
                batchParams.put("LEVEL_NO", level.getLevelNo());
                int treeLevelLabelId = this.labelDao.addLabel(level.getLabel(), LabelConstants.RM_FORECAST_TREE_LEVEL, spcr.getCreatedBy().getUserId());
                batchParams.put("LABEL_ID", treeLevelLabelId);
                batchParams.put("UNIT_ID", (level.getUnit() == null || level.getUnit().getId() == null || level.getUnit().getId() == 0 ? null : level.getUnit().getId()));
                batchList.add(new MapSqlParameterSource(batchParams));
            }
            batchArray = new SqlParameterSource[batchList.size()];
            si = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_level");
            si.executeBatch(batchList.toArray(batchArray));

            // Step 3B -- Insert all the Nodes for the Tree
            for (ForecastNode<TreeNode> n : dt.getTree().getFlatList()) {
                Map<String, Object> nodeParams = new HashMap<>();
                nodeParams.put("TREE_ID", treeId);
                nodeParams.put("SORT_ORDER", n.getSortOrder());
                nodeParams.put("LEVEL_NO", n.getLevel() + 1);
                nodeParams.put("COLLAPSED", n.getPayload().isCollapsed());
                nodeParams.put("NODE_TYPE_ID", n.getPayload().getNodeType().getId());
                nodeParams.put("UNIT_ID", (n.getPayload().getNodeUnit() == null ? null : (n.getPayload().getNodeUnit().getId() == null || n.getPayload().getNodeUnit().getId() == 0 ? null : n.getPayload().getNodeUnit().getId())));
                int nodeLabelId = this.labelDao.addLabel(n.getPayload().getLabel(), LabelConstants.RM_FORECAST_TREE_NODE, spcr.getCreatedBy().getUserId());
                nodeParams.put("LABEL_ID", nodeLabelId);
                nodeParams.put("CREATED_BY", dt.getCreatedBy().getUserId());
                nodeParams.put("CREATED_DATE", dt.getCreatedDate());
                nodeParams.put("LAST_MODIFIED_BY", dt.getCreatedBy().getUserId());
                nodeParams.put("LAST_MODIFIED_DATE", dt.getCreatedDate());
                nodeParams.put("ACTIVE", 1);
                int nodeId = ni.executeAndReturnKey(nodeParams).intValue();
                updateOldAndNewId(oldAndNewIdMap, "rm_forecast_tree_node", Integer.toString(n.getPayload().getNodeId()), nodeId);
                nodeParams.clear();
            }

            // Step 3C -- Update the Parent Node Id for the Tree Nodes that you just inserted
            params.clear();
            params.put("treeId", treeId);
            this.namedParameterJdbcTemplate.update("UPDATE rm_forecast_tree_node ttn LEFT JOIN rm_forecast_tree_node ttn2 ON ttn.TREE_ID=ttn2.TREE_ID AND left(ttn.SORT_ORDER, length(ttn.SORT_ORDER)-3)=ttn2.SORT_ORDER SET ttn.PARENT_NODE_ID=ttn2.NODE_ID WHERE ttn.TREE_ID=:treeId", params);
            params.clear();
            ni = null;

            // Step 3D -- Insert the Scenarios for the Tree
            si = new SimpleJdbcInsert(dataSource).withTableName("rm_scenario").usingGeneratedKeyColumns("SCENARIO_ID");
            for (TreeScenario ts : dt.getScenarioList()) {
                Map<String, Object> nodeParams = new HashMap<>();
                nodeParams.put("TREE_ID", treeId);
                int scenarioLabelId = this.labelDao.addLabel(ts.getLabel(), LabelConstants.RM_SCENARIO, spcr.getCreatedBy().getUserId());
                nodeParams.put("LABEL_ID", scenarioLabelId);
                nodeParams.put("CREATED_BY", dt.getCreatedBy().getUserId());
                nodeParams.put("CREATED_DATE", dt.getCreatedDate());
                nodeParams.put("LAST_MODIFIED_BY", dt.getCreatedBy().getUserId());
                nodeParams.put("LAST_MODIFIED_DATE", dt.getCreatedDate());
                nodeParams.put("ACTIVE", ts.isActive());
                nodeParams.put("NOTES", ts.getNotes());
                int scenarioId = si.executeAndReturnKey(nodeParams).intValue();
                updateOldAndNewId(oldAndNewIdMap, "rm_scenario", dt.getTreeId() + "-" + ts.getId(), scenarioId);

                // Step 3E Insert the NodeData
                for (ForecastNode<TreeNode> n : dt.getTree().getFlatList()) {
                    for (TreeNodeData tnd : n.getPayload().getNodeDataMap().get(ts.getId())) {
                        Map<String, Object> nodeDataParams = new HashMap<>();
                        Integer nodeDataFuId = null;
                        Integer nodeDataPuId = null;
                        if (n.getPayload().getNodeType().getId() == 4) { // FU node //TODO change to GlobalConstants
                            // Step 3F -- If it is a FU Node then insert that first
                            nodeDataParams.clear();
                            nodeDataParams.put("FORECASTING_UNIT_ID", tnd.getFuNode().getForecastingUnit().getId());
                            nodeDataParams.put("LAG_IN_MONTHS", tnd.getFuNode().getLagInMonths());
                            nodeDataParams.put("USAGE_TYPE_ID", tnd.getFuNode().getUsageType().getId());
                            nodeDataParams.put("NO_OF_PERSONS", tnd.getFuNode().getNoOfPersons());
                            nodeDataParams.put("FORECASTING_UNITS_PER_PERSON", tnd.getFuNode().getNoOfForecastingUnitsPerPerson());
                            nodeDataParams.put("ONE_TIME_USAGE", tnd.getFuNode().getUsageType().getId() == GlobalConstants.USAGE_TEMPLATE_CONTINUOUS ? false : tnd.getFuNode().isOneTimeUsage());
                            nodeDataParams.put("ONE_TIME_DISPENSING", tnd.getFuNode().getUsageType().getId() == GlobalConstants.USAGE_TEMPLATE_CONTINUOUS ? true : tnd.getFuNode().getOneTimeDispensing() == null ? true : tnd.getFuNode().getOneTimeDispensing());
                            nodeDataParams.put("USAGE_FREQUENCY", tnd.getFuNode().getUsageFrequency());
                            nodeDataParams.put("USAGE_FREQUENCY_USAGE_PERIOD_ID", (tnd.getFuNode().getUsagePeriod() == null ? null : tnd.getFuNode().getUsagePeriod().getUsagePeriodId()));
                            nodeDataParams.put("REPEAT_COUNT", tnd.getFuNode().getRepeatCount());
                            nodeDataParams.put("REPEAT_USAGE_PERIOD_ID", (tnd.getFuNode().getRepeatUsagePeriod() == null ? null : (tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId() == 0 ? null : tnd.getFuNode().getRepeatUsagePeriod().getUsagePeriodId())));
                            nodeDataParams.put("CREATED_BY", dt.getCreatedBy().getUserId());
                            nodeDataParams.put("CREATED_DATE", dt.getCreatedDate());
                            nodeDataParams.put("LAST_MODIFIED_BY", dt.getCreatedBy().getUserId());
                            nodeDataParams.put("LAST_MODIFIED_DATE", dt.getCreatedDate());
                            nodeDataParams.put("ACTIVE", 1);
                            ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_fu").usingGeneratedKeyColumns("NODE_DATA_FU_ID");
                            nodeDataFuId = ni.executeAndReturnKey(nodeDataParams).intValue();
                        }
                        if (n.getPayload().getNodeType().getId() == 5) { // PU node //TODO change to GlobalConstants
                            // Step 3G -- If it is a PU Node then insert that first
                            nodeDataParams.clear();
                            nodeDataParams.put("PLANNING_UNIT_ID", tnd.getPuNode().getPlanningUnit().getId());
                            nodeDataParams.put("SHARE_PLANNING_UNIT", tnd.getPuNode().isSharePlanningUnit());
                            nodeDataParams.put("REFILL_MONTHS", tnd.getPuNode().getRefillMonths());
                            nodeDataParams.put("PU_PER_VISIT", tnd.getPuNode().getPuPerVisit());
                            nodeDataParams.put("CREATED_BY", dt.getCreatedBy().getUserId());
                            nodeDataParams.put("CREATED_DATE", dt.getCreatedDate());
                            nodeDataParams.put("LAST_MODIFIED_BY", dt.getCreatedBy().getUserId());
                            nodeDataParams.put("LAST_MODIFIED_DATE", dt.getCreatedDate());
                            nodeDataParams.put("ACTIVE", 1);
                            ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_pu").usingGeneratedKeyColumns("NODE_DATA_PU_ID");
                            nodeDataPuId = ni.executeAndReturnKey(nodeDataParams).intValue();
                        }
                        nodeDataParams.clear();
                        nodeDataParams.put("NODE_ID", getNewId(oldAndNewIdMap, "rm_forecast_tree_node", Integer.toString(n.getPayload().getNodeId())));
                        nodeDataParams.put("SCENARIO_ID", getNewId(oldAndNewIdMap, "rm_scenario", dt.getTreeId() + "-" + ts.getId()));
                        nodeDataParams.put("MONTH", tnd.getMonth());
                        nodeDataParams.put("DATA_VALUE", tnd.getDataValue());
                        nodeDataParams.put("NODE_DATA_FU_ID", nodeDataFuId);
                        nodeDataParams.put("NODE_DATA_PU_ID", nodeDataPuId);
                        nodeDataParams.put("MANUAL_CHANGES_EFFECT_FUTURE", tnd.isManualChangesEffectFuture());
                        if (n.getPayload().getNodeType().getId() != GlobalConstants.NODE_TYPE_NUMBER) {
                            tnd.setExtrapolation(false);
                        }
                        nodeDataParams.put("IS_EXTRAPOLATION", tnd.isExtrapolation());
                        nodeDataParams.put("NOTES", tnd.getNotes());
                        nodeDataParams.put("CREATED_BY", dt.getCreatedBy().getUserId());
                        nodeDataParams.put("CREATED_DATE", dt.getCreatedDate());
                        nodeDataParams.put("LAST_MODIFIED_BY", dt.getCreatedBy().getUserId());
                        nodeDataParams.put("LAST_MODIFIED_DATE", dt.getCreatedDate());
                        nodeDataParams.put("ACTIVE", 1);
                        ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data").usingGeneratedKeyColumns("NODE_DATA_ID");
                        int nodeDataId = ni.executeAndReturnKey(nodeDataParams).intValue();
                        updateOldAndNewId(oldAndNewIdMap, "rm_forecast_tree_node_data", Integer.toString(tnd.getNodeDataId()), nodeDataId);

                        // Step 3H -- Add the Node Data Modelling values
                        if ((n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER
                                || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PERCENTAGE
                                || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_FU
                                || n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_PU)
                                && tnd.isExtrapolation() == Boolean.FALSE) {
                            ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_modeling").usingGeneratedKeyColumns("NODE_DATA_MODELING_ID");
                            n2 = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_annual_target_calculator").usingGeneratedKeyColumns("NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID");
                            n3 = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_annual_target_calculator_data");
                            for (NodeDataModeling ndm : tnd.getNodeDataModelingList()) {
                                nodeDataParams.clear();
                                nodeDataParams.put("NODE_DATA_ID", nodeDataId);
                                nodeDataParams.put("START_DATE", ndm.getStartDate());
                                nodeDataParams.put("STOP_DATE", ndm.getStopDate());
                                nodeDataParams.put("MODELING_TYPE_ID", ndm.getModelingType().getId());
                                nodeDataParams.put("DATA_VALUE", ndm.getDataValue());
                                nodeDataParams.put("INCREASE_DECREASE", ndm.getIncreaseDecrease());
                                nodeDataParams.put("TRANSFER_NODE_DATA_ID", null); // Null over here because we go back and update it later
                                nodeDataParams.put("NOTES", ndm.getNotes());
                                nodeDataParams.put("MODELING_SOURCE", ndm.getModelingSource());
                                nodeDataParams.put("CREATED_BY", spcr.getCreatedBy().getUserId());
                                nodeDataParams.put("CREATED_DATE", spcr.getCreatedDate());
                                nodeDataParams.put("LAST_MODIFIED_BY", spcr.getCreatedBy().getUserId());
                                nodeDataParams.put("LAST_MODIFIED_DATE", spcr.getCreatedDate());
                                nodeDataParams.put("ACTIVE", 1);
                                ndm.setNodeDataModelingId(ni.executeAndReturnKey(nodeDataParams).intValue());
                            }
                            if (tnd.getAnnualTargetCalculator() != null && !tnd.getAnnualTargetCalculator().getActualOrTargetValueList().isEmpty()) {
                                nodeDataParams.clear();
                                nodeDataParams.put("NODE_DATA_ID", nodeDataId);
                                nodeDataParams.put("CALCULATOR_FIRST_MONTH", tnd.getAnnualTargetCalculator().getFirstMonthOfTarget());
                                nodeDataParams.put("CALCULATOR_YEARS_OF_TARGET", tnd.getAnnualTargetCalculator().getYearsOfTarget());
                                nodeDataParams.put("CREATED_DATE", spcr.getCreatedBy().getUserId());
                                nodeDataParams.put("CREATED_BY", spcr.getCreatedBy().getUserId());
                                nodeDataParams.put("CREATED_DATE", spcr.getCreatedDate());
                                nodeDataParams.put("LAST_MODIFIED_BY", spcr.getCreatedBy().getUserId());
                                nodeDataParams.put("LAST_MODIFIED_DATE", spcr.getCreatedDate());
                                int nodeDataAnnualTargetCalculatorId = n2.executeAndReturnKey(nodeDataParams).intValue();

                                batchList.clear();
                                for (int actualOrTargetValue : tnd.getAnnualTargetCalculator().getActualOrTargetValueList()) {
                                    Map<String, Object> batchParams = new HashMap<>();
                                    batchParams.put("NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID", nodeDataAnnualTargetCalculatorId);
                                    batchParams.put("ACTUAL_OR_TARGET_VALUE", actualOrTargetValue);
                                    batchList.add(new MapSqlParameterSource(batchParams));
                                }
                                batchArray = null;
                                batchArray = new SqlParameterSource[batchList.size()];
                                n3.executeBatch(batchList.toArray(batchArray));
                            }
                        }

                        // Step 3I -- Add the Node Data Extrapolation Option values 
                        if (n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER && tnd.isExtrapolation() == Boolean.TRUE) {
                            ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_extrapolation_option").usingGeneratedKeyColumns("NODE_DATA_EXTRAPOLATION_OPTION_ID");
                            SimpleJdbcInsert nid = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_extrapolation_option_data");

                            for (NodeDataExtrapolationOption ndeo : tnd.getNodeDataExtrapolationOptionList()) {
                                nodeDataParams.clear();
                                nodeDataParams.put("NODE_DATA_ID", nodeDataId);
                                nodeDataParams.put("EXTRAPOLATION_METHOD_ID", ndeo.getExtrapolationMethod().getId());
                                nodeDataParams.put("JSON_PROPERTIES", ndeo.getJsonPropertiesString());
                                int nodeDataExtrapolationOptionId = ni.executeAndReturnKey(nodeDataParams).intValue();
                                /*for (ExtrapolationData ed : ndeo.getExtrapolationOptionDataList()) {
                                    nodeDataParams.clear();
                                    nodeDataParams.put("NODE_DATA_EXTRAPOLATION_OPTION_ID", nodeDataExtrapolationOptionId);
                                    nodeDataParams.put("MONTH", ed.getMonth());
                                    nodeDataParams.put("AMOUNT", ed.getAmount());
                                    nodeDataParams.put("CI", ed.getCi());
                                    nid.execute(nodeDataParams);
                                }*/
                            }
                        }

                        // Step 3J -- Add the Node Data Extrapolation and Data values
                        if (n.getPayload().getNodeType().getId() == GlobalConstants.NODE_TYPE_NUMBER && tnd.isExtrapolation()) {
                            ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_extrapolation").usingGeneratedKeyColumns("NODE_DATA_EXTRAPOLATION_ID");
                            NodeDataExtrapolation nde = tnd.getNodeDataExtrapolation();
                            nodeDataParams.clear();
                            nodeDataParams.put("NODE_DATA_ID", nodeDataId);
                            nodeDataParams.put("EXTRAPOLATION_METHOD_ID", nde.getExtrapolationMethod().getId());
                            nodeDataParams.put("NOTES", nde.getNotes());
                            nodeDataParams.put("START_DATE", nde.getStartDate());
                            nodeDataParams.put("STOP_DATE", nde.getStopDate());
                            int ndeId = ni.executeAndReturnKey(nodeDataParams).intValue();
                            SimpleJdbcInsert di = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_forecast_tree_node_data_extrapolation_data");
                            for (ExtrapolationDataReportingRate edrr : nde.getExtrapolationDataList()) {
                                nodeParams.clear();
                                nodeParams.put("NODE_DATA_EXTRAPOLATION_ID", ndeId);
                                nodeParams.put("MONTH", edrr.getMonth());
                                nodeParams.put("AMOUNT", (edrr.getAmount() == null ? null : (edrr.getAmount() < 0 ? 0 : edrr.getAmount())));
                                nodeParams.put("REPORTING_RATE", edrr.getReportingRate());
                                nodeParams.put("MANUAL_CHANGE", edrr.getManualChange());
                                di.execute(nodeParams);
                            }
                        }

                        // Step 3K -- Add the Node Data Override
                        ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_override");
                        for (NodeDataOverride ndo : tnd.getNodeDataOverrideList()) {
                            nodeDataParams.clear();
                            nodeDataParams.put("NODE_DATA_ID", nodeDataId);
                            nodeDataParams.put("MONTH", ndo.getMonth());
                            nodeDataParams.put("MANUAL_CHANGE", ndo.getManualChange());
                            nodeDataParams.put("SEASONALITY_PERC", ndo.getSeasonalityPerc());
                            nodeDataParams.put("CREATED_DATE", spcr.getCreatedBy().getUserId());
                            nodeDataParams.put("CREATED_BY", spcr.getCreatedBy().getUserId());
                            nodeDataParams.put("CREATED_DATE", spcr.getCreatedDate());
                            nodeDataParams.put("LAST_MODIFIED_BY", spcr.getCreatedBy().getUserId());
                            nodeDataParams.put("LAST_MODIFIED_DATE", spcr.getCreatedDate());
                            nodeDataParams.put("ACTIVE", 1);
                            ni.execute(nodeDataParams);
                        }

                        // Step 3L -- Add the Node Data MOM
                        ni = new SimpleJdbcInsert(dataSource).withTableName("rm_forecast_tree_node_data_mom");
                        for (NodeDataMom ndo : tnd.getNodeDataMomList()) {
                            nodeDataParams.clear();
                            nodeDataParams.put("NODE_DATA_ID", nodeDataId);
                            nodeDataParams.put("MONTH", ndo.getMonth());
                            nodeDataParams.put("START_VALUE", ndo.getStartValue());
                            nodeDataParams.put("END_VALUE", ndo.getEndValue());
                            nodeDataParams.put("CALCULATED_VALUE", ndo.getCalculatedValue());
                            nodeDataParams.put("CALCULATED_MMD_VALUE", ndo.getCalculatedMmdValue());
                            nodeDataParams.put("DIFFERENCE", ndo.getDifference());
                            nodeDataParams.put("SEASONALITY_PERC", ndo.getSeasonalityPerc());
                            nodeDataParams.put("MANUAL_CHANGE", ndo.getManualChange());
                            ni.execute(nodeDataParams);
                        }
                    }
                }

                batchList.clear();
                String sql = "UPDATE rm_forecast_tree_node_data_modeling tndm SET tndm.TRANSFER_NODE_DATA_ID=:transferNodeDataId WHERE tndm.NODE_DATA_MODELING_ID=:nodeDataModelingId";
                // go back and update the TransferNodeDataId's
                for (ForecastNode<TreeNode> n : dt.getTree().getFlatList()) {
                    for (TreeNodeData tnd : n.getPayload().getNodeDataMap().get(ts.getId())) {
                        for (NodeDataModeling tndm : tnd.getNodeDataModelingList()) {
                            if (tndm.getTransferNodeDataId() != null) {
                                Map<String, Object> batchParams = new HashMap<>();
                                batchParams.put("transferNodeDataId", oldAndNewIdMap.get("rm_forecast_tree_node_data").get(Integer.toString(tndm.getTransferNodeDataId())));
                                batchParams.put("nodeDataModelingId", tndm.getNodeDataModelingId());
                                batchList.add(new MapSqlParameterSource(batchParams));
                            }
                        }
                    }
                }
                if (batchList.size() > 0) {
                    batchArray = new SqlParameterSource[batchList.size()];
                    namedParameterJdbcTemplate.batchUpdate(sql, batchList.toArray(batchArray));
                }
            }

        }

        //Step 4 -- Insert the Planning Units and the Selected Forecasts
        si = null;
        params.clear();
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_dataset_planning_unit").usingGeneratedKeyColumns("PROGRAM_PLANNING_UNIT_ID");
        siData = null;
        siData = new SimpleJdbcInsert(dataSource).withTableName("rm_dataset_planning_unit_selected");
        SimpleJdbcInsert siDataTl = new SimpleJdbcInsert(dataSource).withTableName("rm_dataset_planning_unit_selected_tree_list");
        for (DatasetPlanningUnit dpu : dd.getPlanningUnitList()) {
            params.put("PROGRAM_ID", dd.getProgramId());
            params.put("VERSION_ID", version.getVersionId());
            params.put("PLANNING_UNIT_ID", dpu.getPlanningUnit().getId());
            params.put("CONSUMPTION_FORECAST", dpu.isConsuptionForecast());
            params.put("TREE_FORECAST", dpu.isTreeForecast());
            params.put("STOCK", dpu.getStock());
            params.put("EXISTING_SHIPMENTS", dpu.getExistingShipments());
            params.put("MONTHS_OF_STOCK", dpu.getMonthsOfStock());
            params.put("PROCUREMENT_AGENT_ID", (dpu.getProcurementAgent() == null || dpu.getProcurementAgent().getId() == 0 ? null : dpu.getProcurementAgent().getId()));
            params.put("PRICE", dpu.getPrice());
            params.put("LOWER_THEN_CONSUMPTION_THRESHOLD", dpu.getLowerThenConsumptionThreshold());
            params.put("HIGHER_THEN_CONSUMPTION_THRESHOLD", dpu.getHigherThenConsumptionThreshold());
            params.put("PLANNING_UNIT_NOTES", dpu.getPlanningUnitNotes());
            params.put("CONSUMPTION_NOTES", dpu.getConsumptionNotes());
            params.put("CONSUMPTION_DATA_TYPE_ID", dpu.getConsumptionDataType());
            if (dpu.getOtherUnit() != null) {
                int ouLabelId = this.labelDao.addLabel(dpu.getOtherUnit().getLabel(), LabelConstants.RM_DATASET_PLANNING_UNIT, spcr.getCreatedBy().getUserId());
                params.put("OTHER_LABEL_ID", ouLabelId);
                params.put("OTHER_MULTIPLIER", (dpu.getOtherUnit() == null ? null : dpu.getOtherUnit().getMultiplier()));
            }
            params.put("CREATED_BY", spcr.getCreatedBy().getUserId());
            params.put("CREATED_DATE", spcr.getCreatedDate());
            params.put("ACTIVE", dpu.isActive());
            int programPlanningUnitId = si.executeAndReturnKey(params).intValue();
            updateOldAndNewId(oldAndNewIdMap, "rm_dataset_planning_unit", Integer.toString(dpu.getProgramPlanningUnitId()), programPlanningUnitId);

            // Now store the selected Forecast for this Planning Unit
            if (dpu.getSelectedForecastMap() != null) {
                batchList.clear();
                final List<SqlParameterSource> batchListTl = new ArrayList<>();
                batchArray = null;
                for (int regionId : dpu.getSelectedForecastMap().keySet()) {
                    Map<String, Object> batchParams = new HashMap<>();
                    batchParams.put("PROGRAM_PLANNING_UNIT_ID", programPlanningUnitId);
                    batchParams.put("REGION_ID", regionId);
                    if (dpu.getSelectedForecastMap().get(regionId).getTreeAndScenario() != null && !dpu.getSelectedForecastMap().get(regionId).getTreeAndScenario().isEmpty()) {
                        Map<String, Object> batchParamsTl = new HashMap<>();
                        for (TreeAndScenario ts : dpu.getSelectedForecastMap().get(regionId).getTreeAndScenario()) {
                            batchParamsTl.put("PROGRAM_PLANNING_UNIT_ID", programPlanningUnitId);
                            batchParamsTl.put("REGION_ID", regionId);
                            batchParamsTl.put("TREE_ID", getNewId(oldAndNewIdMap, "rm_forecast_tree", Integer.toString(ts.getTreeId())));
                            batchParamsTl.put("SCENARIO_ID", getNewId(oldAndNewIdMap, "rm_scenario", Integer.toString(ts.getTreeId()) + "-" + Integer.toString(ts.getScenarioId())));
                            batchListTl.add(new MapSqlParameterSource(batchParamsTl));
                        }
                    }
                    if (dpu.getSelectedForecastMap().get(regionId).getConsumptionExtrapolationId() != null) {
                        batchParams.put("CONSUMPTION_EXTRAPOLATION_ID", getNewId(oldAndNewIdMap, "rm_forecast_consumption_extrapolation", Integer.toString(dpu.getSelectedForecastMap().get(regionId).getConsumptionExtrapolationId())));
                    }
                    batchParams.put("TOTAL_FORECAST", dpu.getSelectedForecastMap().get(regionId).getTotalForecast());
                    batchParams.put("NOTES", dpu.getSelectedForecastMap().get(regionId).getNotes());
                    batchList.add(new MapSqlParameterSource(batchParams));
                }
                batchArray = new SqlParameterSource[batchList.size()];
                siData.executeBatch(batchList.toArray(batchArray));
                batchArray = new SqlParameterSource[batchListTl.size()];
                siDataTl.executeBatch(batchListTl.toArray(batchArray));
            }
        }

        // Step 5 -- Update the Version no on the Program table
        /**
         * skip this step for now and do it once the compile is completed
         * sqlString = "UPDATE rm_program p SET p.CURRENT_VERSION_ID=:versionId
         * WHERE p.PROGRAM_ID=:programId"; params.clear();
         * params.put("programId", spcr.getProgram().getId());
         * params.put("versionId", version.getVersionId());
         * this.namedParameterJdbcTemplate.update(sqlString, params);
         *
         */
        // Step 6 -- Remove duplicates from rm_forecast_extrapolation_data
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption_extrapolation_data`;";
        this.jdbcTemplate.update(sqlString);
        sqlString = "CREATE TEMPORARY TABLE `tmp_consumption_extrapolation_data`( `CONSUMPTION_EXTRAPOLATION_DATA_ID` INT ); ";
        this.jdbcTemplate.update(sqlString);
        sqlString = "INSERT INTO `tmp_consumption_extrapolation_data` "
                + "SELECT fced.`CONSUMPTION_EXTRAPOLATION_DATA_ID` "
                + "FROM `rm_forecast_consumption_extrapolation_data` fced "
                + "LEFT JOIN rm_forecast_consumption_extrapolation fce "
                + "ON fced.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID "
                + "WHERE fce.PROGRAM_ID=:programId AND fce.VERSION_ID=:versionId "
                + "AND fced.`CONSUMPTION_EXTRAPOLATION_DATA_ID` NOT IN ( "
                + "SELECT MIN(fced.`CONSUMPTION_EXTRAPOLATION_DATA_ID`) "
                + "FROM `rm_forecast_consumption_extrapolation_data` fced "
                + "LEFT JOIN rm_forecast_consumption_extrapolation fce "
                + "ON fced.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID "
                + "WHERE fce.PROGRAM_ID=:programId AND fce.VERSION_ID=:versionId "
                + "GROUP BY fced.`CONSUMPTION_EXTRAPOLATION_ID`,fced.`MONTH`);";
        params.clear();
        params.put("programId", spcr.getProgram().getId());
        params.put("versionId", version.getVersionId());
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DELETE fced.* FROM `rm_forecast_consumption_extrapolation_data` fced  "
                + "WHERE fced.`CONSUMPTION_EXTRAPOLATION_DATA_ID` IN (SELECT * FROM `tmp_consumption_extrapolation_data`);";
        this.jdbcTemplate.update(sqlString);
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption_extrapolation_data`; ";
        this.jdbcTemplate.update(sqlString);
        return version;
    }

    private void updateOldAndNewId(Map<String, Map<String, Integer>> oldAndNewId, String tableName, String oldId, int newId) {
        Map<String, Integer> tableData = oldAndNewId.get(tableName);
        if (tableData == null) {
            tableData = new HashMap<String, Integer>();
            oldAndNewId.put(tableName, tableData);
        }
        tableData.put(oldId, newId);
    }

    private Integer getNewId(Map<String, Map<String, Integer>> oldAndNewId, String tableName, String oldId) {
        Map<String, Integer> tableData = oldAndNewId.get(tableName);
        if (tableData == null) {
            return null;
        } else {
            return tableData.get(oldId);
        }
    }

    @Override
    public Version getVersionInfo(int programId, int versionId) {
        if (versionId == -1) {
            String sqlString = "SELECT MAX(pv.VERSION_ID) FROM rm_program_version pv WHERE pv.PROGRAM_ID=:programId";
            Map<String, Object> params = new HashMap<>();
            params.put("programId", programId);
            versionId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        }
        String sqlString = "SELECT pv.VERSION_ID, pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, pv.DAYS_IN_MONTH, pv.FREIGHT_PERC, pv.FORECAST_THRESHOLD_HIGH_PERC, pv.FORECAST_THRESHOLD_LOW_PERC, "
                + "    pv.PROGRAM_ID, pv.NOTES, pv.LAST_MODIFIED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pv.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,  "
                + "    vt.VERSION_TYPE_ID, vtl.LABEL_ID `VERSION_TYPE_LABEL_ID`, vtl.LABEL_EN `VERSION_TYPE_LABEL_EN`, vtl.LABEL_FR `VERSION_TYPE_LABEL_FR`, vtl.LABEL_SP `VERSION_TYPE_LABEL_SP`, vtl.LABEL_PR `VERSION_TYPE_LABEL_PR`, "
                + "    vs.VERSION_STATUS_ID, vsl.LABEL_ID `VERSION_STATUS_LABEL_ID`, vsl.LABEL_EN `VERSION_STATUS_LABEL_EN`, vsl.LABEL_FR `VERSION_STATUS_LABEL_FR`, vsl.LABEL_SP `VERSION_STATUS_LABEL_SP`, vsl.LABEL_PR `VERSION_STATUS_LABEL_PR` "
                + "FROM rm_program_version pv  "
                + "LEFT JOIN ap_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
                + "LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID "
                + "LEFT JOIN ap_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID "
                + "LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID "
                + "LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE pv.PROGRAM_ID=:programId AND pv.VERSION_ID=:versionId";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
    }

    @Override
    public List<ProgramVersionTrans> getProgramVersionTrans(int programId, int versionId, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT "
                + "    pv.VERSION_ID, "
                + "    vs.VERSION_STATUS_ID `VS_ID`, vs.LABEL_ID `VS_LABEL_ID`, vs.LABEL_EN `VS_LABEL_EN`, vs.LABEL_FR `VS_LABEL_FR`, vs.LABEL_SP `VS_LABEL_SP`, vs.LABEL_PR `VS_LABEL_PR`,"
                + "    vt.VERSION_TYPE_ID `VT_ID`, vt.LABEL_ID `VT_LABEL_ID`, vt.LABEL_EN `VT_LABEL_EN`, vt.LABEL_FR `VT_LABEL_FR`, vt.LABEL_SP `VT_LABEL_SP`, vt.LABEL_PR `VT_LABEL_PR`,"
                + "    pvt.NOTES, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pvt.LAST_MODIFIED_DATE "
                + "FROM rm_program_version pv "
                + "LEFT JOIN rm_program_version_trans pvt ON pv.PROGRAM_VERSION_ID=pvt.PROGRAM_VERSION_ID "
                + "LEFT JOIN vw_version_status vs ON pvt.VERSION_STATUS_ID=vs.VERSION_STATUS_ID "
                + "LEFT JOIN vw_version_type vt ON pvt.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
                + "LEFT JOIN us_user lmb ON pvt.LAST_MODIFIED_BY=lmb.USER_ID "
                + "LEFT JOIN vw_program p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + "WHERE pv.PROGRAM_ID=:programId AND (:versionId=-1 OR pv.VERSION_ID=:versionId) ");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        sb.append(" ORDER BY pv.VERSION_ID, pvt.LAST_MODIFIED_DATE");
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ProgramVersionTransRowMapper());
    }

    @Override
    public List<Consumption> getConsumptionList(int programId, int versionId, boolean planningUnitActive, String cutOffDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("cutOffDate", (cutOffDate == null || cutOffDate.equals("") ? null : cutOffDate));
        params.put("planningUnitActive", planningUnitActive);
        return this.namedParameterJdbcTemplate.query("CALL getConsumptionDataNew(:programId, :versionId, :planningUnitActive, :cutOffDate)", params, new ConsumptionListResultSetExtractor());
    }

    @Override
    public List<Inventory> getInventoryList(int programId, int versionId, boolean planningUnitActive, String cutOffDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("cutOffDate", (cutOffDate == null || cutOffDate.equals("") ? null : cutOffDate));
        params.put("planningUnitActive", planningUnitActive);
        return this.namedParameterJdbcTemplate.query("CALL getInventoryDataNew(:programId, :versionId, :planningUnitActive, :cutOffDate)", params, new InventoryListResultSetExtractor());
    }

    @Override
    public List<Shipment> getShipmentList(int programId, int versionId, boolean shipmentActive, boolean planningUnitActive, String cutOffDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("shipmentActive", shipmentActive);
        params.put("planningUnitActive", planningUnitActive);
        params.put("cutOffDate", (cutOffDate == null || cutOffDate.equals("") ? null : cutOffDate));
        return this.namedParameterJdbcTemplate.query("CALL getShipmentDataNew(:programId, :versionId, :shipmentActive, :planningUnitActive, :cutOffDate)", params, new ShipmentListResultSetExtractor());
    }

    @Override
    public List<ShipmentLinking> getShipmentLinkingList(int programId, int versionId, String cutOffDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("cutOffDate", (cutOffDate == null || cutOffDate.equals("") ? null : cutOffDate));
        return this.namedParameterJdbcTemplate.query("CALL getShipmentLinkingDataNew(:programId, :versionId, :cutOffDate)", params, new ShipmentLinkingRowMapper());
    }

    @Override
    public List<SimpleObject> getVersionTypeList() {
        String sqlString = "SELECT vt.VERSION_TYPE_ID `ID`, vtl.LABEL_ID, vtl.LABEL_EN, vtl.LABEL_FR, vtl.LABEL_SP, vtl.LABEL_PR  FROM ap_version_type vt LEFT JOIN ap_label vtl ON vt.LABEL_ID=vtl.LABEL_ID";
        return this.namedParameterJdbcTemplate.query(sqlString, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getVersionStatusList() {
        String sqlString = "SELECT vs.VERSION_STATUS_ID `ID`, vsl.LABEL_ID, vsl.LABEL_EN, vsl.LABEL_FR, vsl.LABEL_SP, vsl.LABEL_PR  FROM ap_version_status vs LEFT JOIN ap_label vsl ON vs.LABEL_ID=vsl.LABEL_ID";
        return this.namedParameterJdbcTemplate.query(sqlString, new SimpleObjectRowMapper());
    }

    @Override
    public List<Batch> getBatchList(int programId, int versionId, boolean planningUnitActive, String cutOffDate) {
        String sqlString = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.PROGRAM_ID, bi.PLANNING_UNIT_ID `BATCH_PLANNING_UNIT_ID`, bi.`AUTO_GENERATED`, bi.EXPIRY_DATE, bi.CREATED_DATE FROM rm_batch_info bi LEFT JOIN rm_program_planning_unit ppu ON bi.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=:programId WHERE bi.PROGRAM_ID=:programId AND (:planningUnitActive = FALSE OR ppu.ACTIVE)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitActive", planningUnitActive);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new BatchRowMapper());
    }

    @Override
    public List<ProgramVersion> getProgramVersionList(int programId, int versionId, int realmCountryId, int healthAreaId, int organisationId, int versionTypeId, int versionStatusId, String startDate, String stopDate, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("realmCountryId", realmCountryId);
        params.put("healthAreaId", healthAreaId);
        params.put("organisationId", organisationId);
        params.put("versionId", versionId);
        params.put("versionTypeId", versionTypeId);
        params.put("versionStatusId", versionStatusId);
        params.put("startDate", startDate + " 00:00:00");
        params.put("stopDate", stopDate + " 23:59:59");
        StringBuilder sb = new StringBuilder("SELECT  "
                + "     pv.PROGRAM_VERSION_ID, pv.VERSION_ID, pv.NOTES, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, pv.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pv.LAST_MODIFIED_DATE, "
                + "     p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, p.PROGRAM_CODE, "
                + "     rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, "
                + "     ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, "
                + "     o.ORGANISATION_ID, o.ORGANISATION_CODE, o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_SP `ORGANISATION_LABEL_SP`, o.LABEL_PR `ORGANISATION_LABEL_PR`, "
                + "     vt.VERSION_TYPE_ID, vt.LABEL_ID `VERSION_TYPE_LABEL_ID`, vt.LABEL_EN `VERSION_TYPE_LABEL_EN`, vt.LABEL_FR `VERSION_TYPE_LABEL_FR`, vt.LABEL_SP `VERSION_TYPE_LABEL_SP`, vt.LABEL_PR `VERSION_TYPE_LABEL_PR`, "
                + "     vs.VERSION_STATUS_ID, vs.LABEL_ID `VERSION_STATUS_LABEL_ID`, vs.LABEL_EN `VERSION_STATUS_LABEL_EN`, vs.LABEL_FR `VERSION_STATUS_LABEL_FR`, vs.LABEL_SP `VERSION_STATUS_LABEL_SP`, vs.LABEL_PR `VERSION_STATUS_LABEL_PR`, "
                + "     pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, pv.`DAYS_IN_MONTH`, pv.`FREIGHT_PERC`, pv.`FORECAST_THRESHOLD_HIGH_PERC`, pv.`FORECAST_THRESHOLD_LOW_PERC` "
                + "FROM rm_program_version pv  "
                + "LEFT JOIN vw_program p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN vw_health_area ha ON FIND_IN_SET(ha.HEALTH_AREA_ID, p.HEALTH_AREA_ID) "
                + "LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID "
                + "LEFT JOIN vw_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
                + "LEFT JOIN vw_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID "
                + "WHERE TRUE "
                + "AND (:programId = -1 OR p.PROGRAM_ID = :programId ) "
                + "AND (:versionId = -1 OR pv.VERSION_ID = :versionId ) "
                + "AND (:realmCountryId = -1 OR p.REALM_COUNTRY_ID= :realmCountryId ) "
                + "AND (:healthAreaId = -1 OR FIND_IN_SET(:healthAreaId, p.HEALTH_AREA_ID)) "
                + "AND (:organisationId = -1 OR p.ORGANISATION_ID = :organisationId) "
                + "AND (:versionTypeId = -1 OR pv.VERSION_TYPE_ID = :versionTypeId) "
                + "AND (:versionStatusId = -1 OR pv.VERSION_STATUS_ID = :versionStatusId) "
                + "AND pv.CREATED_DATE BETWEEN :startDate AND :stopDate "
                + "AND p.PROGRAM_TYPE_ID=" + GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ProgramVersionResultSetExtractor());

    }

    @Override
    @Transactional
    public Version updateProgramVersion(int programId, int versionId, int versionStatusId, UpdateProgramVersion updateProgramVersion, CustomUserDetails curUser) {
        String programVersionUpdateSql = "UPDATE rm_program_version pv SET pv.VERSION_STATUS_ID=:versionStatusId, "
                + "pv.NOTES=:notes, pv.LAST_MODIFIED_DATE=:curDate, "
                + "pv.LAST_MODIFIED_BY=:curUser ";
        programVersionUpdateSql += " WHERE pv.PROGRAM_ID=:programId AND pv.VERSION_ID=:versionId;";
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("versionStatusId", versionStatusId);
        params.put("notes", updateProgramVersion.getNotes());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        this.namedParameterJdbcTemplate.update(programVersionUpdateSql, params);
        String programVersionTransSql = "INSERT INTO rm_program_version_trans SELECT NULL,pv.PROGRAM_VERSION_ID,pv.VERSION_TYPE_ID,?,?,?,? FROM  rm_program_version pv  "
                + "WHERE pv.`PROGRAM_ID`=? AND pv.`VERSION_ID`=? ";
        this.jdbcTemplate.update(programVersionTransSql, versionStatusId, updateProgramVersion.getNotes(), curUser.getUserId(), DateUtils.getCurrentDateObject(DateUtils.EST), programId, versionId);
        String problemReportUpdateSql = "UPDATE rm_problem_report pr set pr.`REVIEW_NOTES`=:reviewedNotes, pr.`REVIEWED_DATE`=IF(:reviewed,:curDate,pr.`REVIEWED_DATE`),pr.REVIEWED=:reviewed,pr.PROBLEM_STATUS_ID=:problemStatusId, pr.LAST_MODIFIED_BY=:curUser, pr.LAST_MODIFIED_DATE=:curDate WHERE pr.PROBLEM_REPORT_ID=:problemReportId";
        String problemReportTransInsertSql = "INSERT INTO rm_problem_report_trans SELECT null, :problemReportId, :problemStatusId, :reviewed, :reviewedNotes, :curUser, :curDate FROM rm_problem_report pr WHERE pr.PROBLEM_REPORT_ID=:problemReportId";
        SimpleJdbcInsert problemReportInsert = new SimpleJdbcInsert(this.dataSource).withTableName("rm_problem_report").usingGeneratedKeyColumns("PROBLEM_REPORT_ID");
        SimpleJdbcInsert problemReportTransInsert = new SimpleJdbcInsert(this.dataSource).withTableName("rm_problem_report_trans");
        final List<SqlParameterSource> updateParamList = new ArrayList<>();
        final List<SqlParameterSource> insertParamList = new ArrayList<>();
        for (ReviewedProblem rp : updateProgramVersion.getReviewedProblemList()) {
            if (rp.getProblemReportId() != 0) {
                Map<String, Object> updateParams = new HashMap<>();
                updateParams.put("reviewed", rp.isReviewed());
                updateParams.put("curUser", curUser.getUserId());
                updateParams.put("curDate", curDate);
                updateParams.put("reviewedNotes", rp.getReviewedNotes());
                updateParams.put("problemStatusId", rp.getProblemStatus().getId());
                updateParams.put("problemReportId", rp.getProblemReportId());
                updateParamList.add(new MapSqlParameterSource(updateParams));
            } else if (rp.getProblemReportId() == 0) {
                // This is a new record so you cannot update you have to insert
                Map<String, Object> insertParams = new HashMap<>();
                insertParams.put("REALM_PROBLEM_ID", rp.getRealmProblem().getRealmProblemId());
                insertParams.put("PROGRAM_ID", programId);
                insertParams.put("VERSION_ID", versionId);
                insertParams.put("PROBLEM_TYPE_ID", rp.getRealmProblem().getProblemType().getId());
                insertParams.put("PROBLEM_STATUS_ID", rp.getProblemStatus().getId());
                insertParams.put("REVIEWED", rp.isReviewed());
                insertParams.put("REVIEW_NOTES", (rp.isReviewed() ? rp.getReviewedNotes() : null));
                insertParams.put("REVIEWED_DATE", (rp.isReviewed() ? curDate : null));
                insertParams.put("DATA1", rp.getDt());
                insertParams.put("DATA2", rp.getRegion().getId());
                insertParams.put("DATA3", rp.getPlanningUnit().getId());
                insertParams.put("DATA4", rp.getShipmentId());
                insertParams.put("DATA5", rp.getData5());
                insertParams.put("CREATED_DATE", curDate);
                insertParams.put("LAST_MODIFIED_DATE", curDate);
                insertParams.put("CREATED_BY", curUser.getUserId());
                insertParams.put("LAST_MODIFIED_BY", curUser.getUserId());
                int problemReportId = problemReportInsert.executeAndReturnKey(insertParams).intValue();
                insertParams.put("PROBLEM_REPORT_ID", problemReportId);
                insertParams.put("NOTES", (rp.isReviewed() ? rp.getReviewedNotes() : null));
                problemReportTransInsert.execute(insertParams);
            }
        }
        if (updateParamList.size() > 0) {
            SqlParameterSource[] updateArray = new SqlParameterSource[updateParamList.size()];
            this.namedParameterJdbcTemplate.batchUpdate(problemReportUpdateSql, updateParamList.toArray(updateArray));
            this.namedParameterJdbcTemplate.batchUpdate(problemReportTransInsertSql, updateArray);
        }

        // If Version is approved then reset the Problem list
        if (versionStatusId == 2) {
            updateParamList.clear();
            String sql = "SELECT p.PROBLEM_REPORT_ID FROM rm_problem_report p where p.PROGRAM_ID=? and p.VERSION_ID<=? and p.PROBLEM_STATUS_ID=3;";
            List<Integer> problemReportIds = this.jdbcTemplate.queryForList(sql, Integer.class, programId, versionId);
            problemReportUpdateSql = "UPDATE rm_problem_report pr set pr.PROBLEM_STATUS_ID=1, pr.LAST_MODIFIED_BY=:curUser, pr.LAST_MODIFIED_DATE=:curDate WHERE pr.PROBLEM_REPORT_ID=:problemReportId";
            problemReportTransInsertSql = "INSERT INTO rm_problem_report_trans SELECT null, :problemReportId, 1,pr.REVIEWED , pr.REVIEW_NOTES, :curUser, :curDate FROM rm_problem_report pr WHERE pr.PROBLEM_REPORT_ID=:problemReportId";
            for (Integer rp : problemReportIds) {
                Map<String, Object> updateParams = new HashMap<>();
                updateParams.put("curUser", curUser.getUserId());
                updateParams.put("curDate", curDate);
                updateParams.put("problemReportId", rp);
                updateParamList.add(new MapSqlParameterSource(updateParams));
            }
            if (updateParamList.size() > 0) {
                SqlParameterSource[] updateArray = new SqlParameterSource[updateParamList.size()];
                this.namedParameterJdbcTemplate.batchUpdate(problemReportUpdateSql, updateParamList.toArray(updateArray));
                this.namedParameterJdbcTemplate.batchUpdate(problemReportTransInsertSql, updateArray);
            }
        }
        // when version is rejcted
        if (versionStatusId == 3) {
            SimpleProgram sp = this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            List<NotificationUser> toEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 3, "To");
            List<NotificationUser> ccEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 3, "Cc");
            List<NotificationUser> bccEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 3, "BCc");
            StringBuilder sbToEmails = new StringBuilder();
            StringBuilder sbCcEmails = new StringBuilder();
            StringBuilder sbBccEmails = new StringBuilder();
            if (toEmailIdsList.size() > 0) {
                for (NotificationUser ns : toEmailIdsList) {
                    sbToEmails.append(ns.getEmailId()).append(",");
                }
            }
            if (ccEmailIdsList.size() > 0) {
                for (NotificationUser ns : ccEmailIdsList) {
                    sbCcEmails.append(ns.getEmailId()).append(",");
                }
            }
            if (bccEmailIdsList.size() > 0) {
                for (NotificationUser ns : bccEmailIdsList) {
                    sbBccEmails.append(ns.getEmailId()).append(",");
                }
            }
            EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(7);
            String[] subjectParam = new String[]{};
            String[] bodyParam = null;
            Emailer emailer = new Emailer();
            subjectParam = new String[]{sp.getCode()};
            bodyParam = new String[]{sp.getCode(), String.valueOf(versionId), updateProgramVersion.getNotes()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), sbToEmails.length() != 0 ? sbToEmails.deleteCharAt(sbToEmails.length() - 1).toString() : "", sbCcEmails.length() != 0 ? sbCcEmails.deleteCharAt(sbCcEmails.length() - 1).toString() : "", sbBccEmails.length() != 0 ? sbBccEmails.deleteCharAt(sbBccEmails.length() - 1).toString() : "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
        } else if (versionStatusId == 2) {
            // when version is approved
            SimpleProgram sp = this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            List<NotificationUser> toEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 2, "To");
            List<NotificationUser> ccEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 2, "Cc");
            List<NotificationUser> bccEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 2, "BCc");
            StringBuilder sbToEmails = new StringBuilder();
            StringBuilder sbCcEmails = new StringBuilder();
            StringBuilder sbBccEmails = new StringBuilder();
            if (toEmailIdsList.size() > 0) {
                for (NotificationUser ns : toEmailIdsList) {
                    sbToEmails.append(ns.getEmailId()).append(",");
                }
            }
            if (ccEmailIdsList.size() > 0) {
                for (NotificationUser ns : ccEmailIdsList) {
                    sbCcEmails.append(ns.getEmailId()).append(",");
                }
            }
            if (bccEmailIdsList.size() > 0) {
                for (NotificationUser ns : bccEmailIdsList) {
                    sbBccEmails.append(ns.getEmailId()).append(",");
                }
            }
            EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(5);
            String[] subjectParam = new String[]{};
            String[] bodyParam = null;
            Emailer emailer = new Emailer();
            subjectParam = new String[]{sp.getCode()};
            bodyParam = new String[]{sp.getCode(), String.valueOf(versionId), updateProgramVersion.getNotes()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), sbToEmails.length() != 0 ? sbToEmails.deleteCharAt(sbToEmails.length() - 1).toString() : "", sbCcEmails.length() != 0 ? sbCcEmails.deleteCharAt(sbCcEmails.length() - 1).toString() : "", sbBccEmails.length() != 0 ? sbBccEmails.deleteCharAt(sbBccEmails.length() - 1).toString() : "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
        } else if (versionStatusId == 4) { // No review needed
            SimpleProgram sp = this.programCommonDao.getSimpleProgramById(programId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser);
            List<NotificationUser> toEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 4, "To");
            List<NotificationUser> ccEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 4, "Cc");
            List<NotificationUser> bccEmailIdsList = this.getSupplyPlanNotificationList(programId, versionId, 4, "BCc");
            StringBuilder sbToEmails = new StringBuilder();
            StringBuilder sbCcEmails = new StringBuilder();
            StringBuilder sbBccEmails = new StringBuilder();
            if (toEmailIdsList.size() > 0) {
                for (NotificationUser ns : toEmailIdsList) {
                    sbToEmails.append(ns.getEmailId()).append(",");
                }
            }
            if (ccEmailIdsList.size() > 0) {
                for (NotificationUser ns : ccEmailIdsList) {
                    sbCcEmails.append(ns.getEmailId()).append(",");
                }
            }
            if (bccEmailIdsList.size() > 0) {
                for (NotificationUser ns : bccEmailIdsList) {
                    sbBccEmails.append(ns.getEmailId()).append(",");
                }
            }
            EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(8);
            String[] subjectParam = new String[]{};
            String[] bodyParam = null;
            Emailer emailer = new Emailer();
            subjectParam = new String[]{sp.getCode()};
            bodyParam = new String[]{sp.getCode(), String.valueOf(versionId), updateProgramVersion.getNotes()};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), sbToEmails.length() != 0 ? sbToEmails.deleteCharAt(sbToEmails.length() - 1).toString() : "", sbCcEmails.length() != 0 ? sbCcEmails.deleteCharAt(sbCcEmails.length() - 1).toString() : "", sbBccEmails.length() != 0 ? sbBccEmails.deleteCharAt(sbBccEmails.length() - 1).toString() : "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
        }

        return this.getVersionInfo(programId, versionId);
    }

    @Override
    @Transactional
    public void resetProblemListForPrograms(int[] programIds, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        StringBuilder stringBuilder = new StringBuilder("SELECT pr.PROBLEM_REPORT_ID FROM vw_program p LEFT JOIN rm_problem_report pr ON p.PROGRAM_ID=pr.PROGRAM_ID AND pr.VERSION_ID<p.CURRENT_VERSION_ID WHERE FIND_IN_SET(p.PROGRAM_ID, '" + ArrayUtils.convertArrayToString(programIds) + "') AND pr.PROBLEM_STATUS_ID=3");
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", ArrayUtils.convertArrayToString(programIds));
        this.aclService.addFullAclForProgram(stringBuilder, params, "p", curUser);
        List<Integer> problemReportIds = this.namedParameterJdbcTemplate.queryForList(stringBuilder.toString(), params, Integer.class);
        String problemReportUpdateSql = "UPDATE rm_problem_report pr set pr.PROBLEM_STATUS_ID=1, pr.LAST_MODIFIED_BY=:curUser, pr.LAST_MODIFIED_DATE=:curDate WHERE pr.PROBLEM_REPORT_ID=:problemReportId";
        String problemReportTransInsertSql = "INSERT INTO rm_problem_report_trans SELECT null, :problemReportId, 1,pr.REVIEWED , pr.REVIEW_NOTES, :curUser, :curDate FROM rm_problem_report pr WHERE pr.PROBLEM_REPORT_ID=:problemReportId";
        List<MapSqlParameterSource> updateParamList = new LinkedList<>();
        for (Integer rp : problemReportIds) {
            Map<String, Object> updateParams = new HashMap<>();
            updateParams.put("curUser", curUser.getUserId());
            updateParams.put("curDate", curDate);
            updateParams.put("problemReportId", rp);
            updateParamList.add(new MapSqlParameterSource(updateParams));
        }
        if (updateParamList.size() > 0) {
            SqlParameterSource[] updateArray = new SqlParameterSource[updateParamList.size()];
            int[] result = this.namedParameterJdbcTemplate.batchUpdate(problemReportUpdateSql, updateParamList.toArray(updateArray));
            this.namedParameterJdbcTemplate.batchUpdate(problemReportTransInsertSql, updateArray);
        }
    }

    /**
     *
     * @param orderNo
     * @param primeLineNo
     * @param realmCountryId
     * @param planningUnitId
     * @return 0-Okay to go ahead link 1- Order not found, 2- Already linked
     * 3-Order not for this Country, 4-Order not for this Planning Unit
     *
     */
    @Override
    public int checkErpOrder(String orderNo, String primeLineNo, int realmCountryId, int planningUnitId) {
        String sqlString = "SELECT IF(steop.SHIPMENT_TRANS_ERP_ORDER_ID IS NOT NULL, 2, IF(c1.REALM_COUNTRY_ID!=:realmCountryId, 3, IF (papu.PLANNING_UNIT_ID!=:planningUnitId, 4, 0))) `REASON` "
                + "FROM rm_erp_order eo "
                + "LEFT JOIN rm_shipment_trans_erp_order_mapping steop ON eo.ERP_ORDER_ID=steop.ERP_ORDER_ID "
                + "LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE FROM rm_realm_country rc LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=eo.RECPIENT_COUNTRY "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON eo.PLANNING_UNIT_SKU_CODE=papu.SKU_CODE AND papu.PROCUREMENT_AGENT_ID=1 "
                + "WHERE eo.ORDER_NO=:orderNo AND eo.PRIME_LINE_NO=:primeLineNo";

        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", orderNo);
        params.put("primeLineNo", primeLineNo);
        params.put("realmCountryId", realmCountryId);
        params.put("planningUnitId", planningUnitId);
        try {
            return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class
            );
        } catch (DataAccessException de) {
            return 1; // Order not found
        }
    }

    @Override
    public List<SimplifiedSupplyPlan> updateSupplyPlanBatchInfo(SupplyPlan sp) {
        String sqlString = "UPDATE rm_supply_plan_batch_info spbi "
                + "SET "
                + "spbi.EXPIRED_STOCK=:expired, spbi.CALCULATED_CONSUMPTION=:calculatedConsumption, "
                + "spbi.FINAL_OPENING_BALANCE=:openingBalance, spbi.FINAL_CLOSING_BALANCE=:closingBalance, "
                + "spbi.UNMET_DEMAND=:unmetDemand, "
                + "spbi.EXPIRED_STOCK_WPS=:expiredWps, spbi.CALCULATED_CONSUMPTION_WPS=:calculatedConsumptionWps, "
                + "spbi.FINAL_OPENING_BALANCE_WPS=:openingBalanceWps, spbi.FINAL_CLOSING_BALANCE_WPS=:closingBalanceWps, "
                + "spbi.UNMET_DEMAND_WPS=:unmetDemandWps "
                + "WHERE spbi.SUPPLY_PLAN_BATCH_INFO_ID=:supplyPlanBatchInfoId";
        List<SqlParameterSource> batchParams = new ArrayList<>();
        for (SupplyPlanDate sd : sp.getSupplyPlanDateList()) {
            for (SupplyPlanBatchInfo sbi : sd.getBatchList()) {
                if (sbi.hasData()) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("expired", sbi.getExpiredStock());
                    params.put("calculatedConsumption", sbi.getCalculatedConsumption());
                    params.put("openingBalance", sbi.getOpeningBalance());
                    params.put("closingBalance", sbi.getClosingBalance());
                    params.put("unmetDemand", sbi.getUnmetDemand());
                    params.put("expiredWps", sbi.getExpiredStockWps());
                    params.put("calculatedConsumptionWps", sbi.getCalculatedConsumptionWps());
                    params.put("openingBalanceWps", sbi.getOpeningBalanceWps());
                    params.put("closingBalanceWps", sbi.getClosingBalanceWps());
                    params.put("unmetDemandWps", sbi.getUnmetDemandWps());
                    params.put("supplyPlanBatchInfoId", sbi.getSupplyPlanId());
                    batchParams.add(new MapSqlParameterSource(params));
                }
            }
        }
        SqlParameterSource[] updateParams = new SqlParameterSource[batchParams.size()];
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, batchParams.toArray(updateParams));
        Map<String, Object> params = new HashMap<>();
        params.put("programId", sp.getProgramId());
        params.put("versionId", sp.getVersionId());
//        this.namedParameterJdbcTemplate.update("DELETE spbi.* FROM rm_supply_plan_batch_info spbi WHERE spbi.PROGRAM_ID=:programId AND !(spbi.SHIPMENT_QTY!=0 OR spbi.FORECASTED_CONSUMPTION_QTY!=0 OR spbi.ACTUAL_CONSUMPTION_QTY!=0 OR spbi.ADJUSTMENT_MULTIPLIED_QTY!=0 OR spbi.FINAL_OPENING_BALANCE!=0 OR spbi.FINAL_CLOSING_BALANCE!=0 OR spbi.FINAL_OPENING_BALANCE_WPS!=0 OR spbi.FINAL_CLOSING_BALANCE_WPS!=0 OR spbi.BATCH_ID=0)", params);
        sqlString = "DELETE spa.* FROM rm_supply_plan_amc spa WHERE spa.PROGRAM_ID=:programId AND spa.VERSION_ID=:versionId";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "INSERT INTO `rm_supply_plan_amc` ( "
                + "    `PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `TRANS_DATE`,  "
                + "    `OPENING_BALANCE`, `OPENING_BALANCE_WPS`,  "
                + "    `MANUAL_PLANNED_SHIPMENT_QTY`, `MANUAL_SUBMITTED_SHIPMENT_QTY`, `MANUAL_APPROVED_SHIPMENT_QTY`, `MANUAL_SHIPPED_SHIPMENT_QTY`, `MANUAL_RECEIVED_SHIPMENT_QTY`, `MANUAL_ONHOLD_SHIPMENT_QTY`, "
                + "    `ERP_PLANNED_SHIPMENT_QTY`, `ERP_SUBMITTED_SHIPMENT_QTY`, `ERP_APPROVED_SHIPMENT_QTY`, `ERP_SHIPPED_SHIPMENT_QTY`, `ERP_RECEIVED_SHIPMENT_QTY`, `ERP_ONHOLD_SHIPMENT_QTY`, "
                + "     `SHIPMENT_QTY`, `FORECASTED_CONSUMPTION_QTY`, `ACTUAL_CONSUMPTION_QTY`, `ACTUAL`,  "
                + "    `ADJUSTMENT_MULTIPLIED_QTY`, `STOCK_MULTIPLIED_QTY`, `EXPIRED_STOCK`, `EXPIRED_STOCK_WPS`,  "
                + "    `CLOSING_BALANCE`, `CLOSING_BALANCE_WPS`,  "
                + "    `UNMET_DEMAND`, `UNMET_DEMAND_WPS`)  "
                + "SELECT  "
                + "    spbi.PROGRAM_ID, spbi.VERSION_ID, spbi.PLANNING_UNIT_ID, spbi.TRANS_DATE,  "
                + "    SUM(spbi.FINAL_OPENING_BALANCE), SUM(spbi.FINAL_OPENING_BALANCE_WPS), "
                + "    SUM(spbi.MANUAL_PLANNED_SHIPMENT_QTY), SUM(spbi.MANUAL_SUBMITTED_SHIPMENT_QTY), SUM(spbi.MANUAL_APPROVED_SHIPMENT_QTY), SUM(spbi.MANUAL_SHIPPED_SHIPMENT_QTY), SUM(spbi.MANUAL_RECEIVED_SHIPMENT_QTY), SUM(spbi.MANUAL_ONHOLD_SHIPMENT_QTY), "
                + "    SUM(spbi.ERP_PLANNED_SHIPMENT_QTY), SUM(spbi.ERP_SUBMITTED_SHIPMENT_QTY), SUM(spbi.ERP_APPROVED_SHIPMENT_QTY), SUM(spbi.ERP_SHIPPED_SHIPMENT_QTY), SUM(spbi.ERP_RECEIVED_SHIPMENT_QTY), SUM(spbi.ERP_ONHOLD_SHIPMENT_QTY), "
                + "     SUM(spbi.SHIPMENT_QTY), SUM(spbi.FORECASTED_CONSUMPTION_QTY), SUM(spbi.ACTUAL_CONSUMPTION_QTY), BIT_OR(spbi.ACTUAL), "
                + "    SUM(spbi.ADJUSTMENT_MULTIPLIED_QTY), SUM(spbi.STOCK_MULTIPLIED_QTY), SUM(spbi.EXPIRED_STOCK), SUM(spbi.EXPIRED_STOCK_WPS), "
                + "    SUM(spbi.FINAL_CLOSING_BALANCE), SUM(spbi.FINAL_CLOSING_BALANCE_WPS), "
                + "    SUM(spbi.UNMET_DEMAND), SUM(spbi.UNMET_DEMAND_WPS) "
                + "FROM fasp.rm_supply_plan_batch_info spbi WHERE spbi.PROGRAM_ID=:programId AND spbi.VERSION_ID=:versionId group by spbi.PLANNING_UNIT_ID, spbi.TRANS_DATE";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "UPDATE rm_supply_plan_amc spa SET spa.ACTUAL = null, spa.FORECASTED_CONSUMPTION_QTY = null WHERE spa.ACTUAL=0 AND spa.FORECASTED_CONSUMPTION_QTY =0 AND spa.PROGRAM_ID=:programId AND spa.VERSION_ID=:versionId";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "UPDATE rm_supply_plan_amc spa  "
                + "LEFT JOIN rm_program_planning_unit ppu ON spa.PROGRAM_ID=ppu.PROGRAM_ID AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_program p ON spa.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ( "
                + "    SELECT spa.PROGRAM_ID, spa.VERSION_ID, spa.PLANNING_UNIT_ID, spa.TRANS_DATE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC, SUBDATE(spa.TRANS_DATE, INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH), ADDDATE(spa.TRANS_DATE, INTERVAL CAST(ppu.MONTHS_IN_FUTURE_FOR_AMC AS SIGNED)-1 MONTH), "
                + "        SUM(IF(spa2.ACTUAL, spa2.ACTUAL_CONSUMPTION_QTY,spa2.FORECASTED_CONSUMPTION_QTY)) AMC_SUM, "
                + "        AVG(IF(spa2.ACTUAL, spa2.ACTUAL_CONSUMPTION_QTY,spa2.FORECASTED_CONSUMPTION_QTY)) AMC, COUNT(IF(spa2.ACTUAL, spa2.ACTUAL_CONSUMPTION_QTY,spa2.FORECASTED_CONSUMPTION_QTY)) AMC_COUNT "
                + "    FROM rm_supply_plan_amc spa  "
                + "    LEFT JOIN rm_program_planning_unit ppu ON spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND spa.PROGRAM_ID=ppu.PROGRAM_ID "
                + "    LEFT JOIN rm_supply_plan_amc spa2 ON  "
                + "        spa.PROGRAM_ID=spa2.PROGRAM_ID  "
                + "        AND spa.VERSION_ID=spa2.VERSION_ID "
                + "        AND spa.PLANNING_UNIT_ID=spa2.PLANNING_UNIT_ID  "
                + "        AND spa2.TRANS_DATE BETWEEN SUBDATE(spa.TRANS_DATE, INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH) AND ADDDATE(spa.TRANS_DATE, INTERVAL CAST(ppu.MONTHS_IN_FUTURE_FOR_AMC AS SIGNED)-1 MONTH) "
                + "    WHERE spa.PROGRAM_ID=@programId AND spa.VERSION_ID=@versionId "
                + "GROUP BY spa.PLANNING_UNIT_ID, spa.TRANS_DATE) amc ON spa.PROGRAM_ID=amc.PROGRAM_ID AND spa.VERSION_ID=amc.VERSION_ID AND spa.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID AND spa.TRANS_DATE=amc.TRANS_DATE "
                + "SET  "
                + "    spa.AMC=IF(amc.AMC_COUNT=0,null,amc.AMC),  "
                + "    spa.AMC_COUNT=amc.AMC_COUNT,  "
                + "    spa.MOS=IF(amc.AMC IS NULL OR amc.AMC=0, null, spa.CLOSING_BALANCE_WPS/amc.AMC), "
                + "    spa.MIN_STOCK_MOS = IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, IF(ppu.MIN_MONTHS_OF_STOCK>r.MIN_MOS_MAX_GAURDRAIL, r.MIN_MOS_MAX_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)), "
                + "    spa.MAX_STOCK_MOS = IF(ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS>r.MAX_MOS_MAX_GAURDRAIL, r.MAX_MOS_MAX_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS), "
                + "    spa.MIN_STOCK_QTY = IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, IF(ppu.MIN_MONTHS_OF_STOCK>r.MIN_MOS_MAX_GAURDRAIL, r.MIN_MOS_MAX_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)) * amc.AMC, "
                + "    spa.MAX_STOCK_QTY = IF(ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS>r.MAX_MOS_MAX_GAURDRAIL, r.MAX_MOS_MAX_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS) * amc.AMC "
                + "WHERE spa.PROGRAM_ID=@programId and spa.VERSION_ID=@versionId";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        return this.getSimplifiedSupplyPlan(sp.getProgramId(), sp.getVersionId(), false, null);
    }

    public List<SimplifiedSupplyPlan> getSimplifiedSupplyPlan(int programId, int versionId, boolean planningUnitActive, String cutOffDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        cutOffDate = (cutOffDate == null || cutOffDate.equals("") ? null : cutOffDate);
        params.put("cutOffDate", cutOffDate);
        boolean useCutOff = false;
        if (cutOffDate != null) {
            useCutOff = true;
        }
        params.put("useCutOff", useCutOff);
        params.put("planningUnitActive", planningUnitActive);
        String sqlString = "SELECT  "
                + "    spa.`SUPPLY_PLAN_AMC_ID` `SUPPLY_PLAN_ID`, spa.`PROGRAM_ID`, spa.`VERSION_ID`, "
                + "    spa.`PLANNING_UNIT_ID`, pu.`MULTIPLIER` `CONVERSION_FACTOR`, spa.`TRANS_DATE`,  "
                + "    spa.`OPENING_BALANCE`, spa.`OPENING_BALANCE_WPS`, "
                + "    spa.`ACTUAL` `ACTUAL_FLAG`, IF(spa.`ACTUAL`, spa.`ACTUAL_CONSUMPTION_QTY`, spa.`FORECASTED_CONSUMPTION_QTY`) `CONSUMPTION_QTY`, "
                + "    spa.`ADJUSTMENT_MULTIPLIED_QTY`, spa.`STOCK_MULTIPLIED_QTY`, "
                + "    spa.`REGION_COUNT`, spa.`REGION_COUNT_FOR_STOCK`, "
                + "    spa.`MANUAL_PLANNED_SHIPMENT_QTY`, spa.`MANUAL_SUBMITTED_SHIPMENT_QTY`, spa.`MANUAL_APPROVED_SHIPMENT_QTY`, spa.`MANUAL_SHIPPED_SHIPMENT_QTY`, spa.`MANUAL_RECEIVED_SHIPMENT_QTY`, spa.`MANUAL_ONHOLD_SHIPMENT_QTY`, "
                + "    spa.`ERP_PLANNED_SHIPMENT_QTY`, spa.`ERP_SUBMITTED_SHIPMENT_QTY`, spa.`ERP_APPROVED_SHIPMENT_QTY`, spa.`ERP_SHIPPED_SHIPMENT_QTY`, spa.`ERP_RECEIVED_SHIPMENT_QTY`, spa.`ERP_ONHOLD_SHIPMENT_QTY`, "
                + "    spa.`EXPIRED_STOCK`, spa.`EXPIRED_STOCK_WPS`, "
                + "    spa.`NATIONAL_ADJUSTMENT`, spa.`NATIONAL_ADJUSTMENT_WPS`, "
                + "    spa.`CLOSING_BALANCE`, spa.`CLOSING_BALANCE_WPS`, "
                + "    spa.`UNMET_DEMAND`, spa.`UNMET_DEMAND_WPS`, "
                + "    spa.`AMC`, spa.`AMC_COUNT`, spa.`MOS`, spa.`MOS_WPS`, spa.`MIN_STOCK_MOS`, spa.`MIN_STOCK_QTY`, spa.`MAX_STOCK_MOS`, spa.`MAX_STOCK_QTY`, "
                + "    b2.`BATCH_ID`, b2.`BATCH_NO`, b2.`EXPIRY_DATE`, b2.`AUTO_GENERATED`, b2.`BATCH_OPENING_BALANCE`, b2.`BATCH_OPENING_BALANCE_WPS`, b2.`BATCH_CALCULATED_CONSUMPTION_QTY`, b2.`BATCH_CALCULATED_CONSUMPTION_QTY_WPS`, b2.`BATCH_CONSUMPTION_QTY`, b2.`BATCH_STOCK_MULTIPLIED_QTY`, b2.`BATCH_ADJUSTMENT_MULTIPLIED_QTY`, b2.`BATCH_SHIPMENT_QTY`, b2.`BATCH_SHIPMENT_QTY_WPS`, b2.`BATCH_EXPIRED_STOCK`, b2.`BATCH_EXPIRED_STOCK_WPS`, b2.`BATCH_CLOSING_BALANCE`, b2.`BATCH_CLOSING_BALANCE_WPS`, bi.CREATED_DATE `BATCH_CREATED_DATE` "
                + "FROM rm_supply_plan_amc spa  "
                + "LEFT JOIN rm_program_planning_unit ppu ON spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND ppu.PROGRAM_ID=:programId "
                + "LEFT JOIN rm_planning_unit pu ON spa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN (SELECT spbq.`PLANNING_UNIT_ID`, spbq.`TRANS_DATE`, spbq.`BATCH_ID`, bi.`BATCH_NO`, bi.`EXPIRY_DATE`, bi.`AUTO_GENERATED`, SUM(spbq.`OPENING_BALANCE`) `BATCH_OPENING_BALANCE`, SUM(spbq.`OPENING_BALANCE_WPS`) `BATCH_OPENING_BALANCE_WPS`, SUM(spbq.`CALCULATED_CONSUMPTION`) `BATCH_CALCULATED_CONSUMPTION_QTY`, SUM(spbq.`CALCULATED_CONSUMPTION_WPS`) `BATCH_CALCULATED_CONSUMPTION_QTY_WPS`, SUM(spbq.`ACTUAL_CONSUMPTION_QTY`) BATCH_CONSUMPTION_QTY, SUM(spbq.`STOCK_MULTIPLIED_QTY`) `BATCH_STOCK_MULTIPLIED_QTY`, SUM(spbq.`ADJUSTMENT_MULTIPLIED_QTY`) `BATCH_ADJUSTMENT_MULTIPLIED_QTY`, SUM(spbq.`SHIPMENT_QTY`) `BATCH_SHIPMENT_QTY`, SUM(spbq.`SHIPMENT_QTY_WPS`) `BATCH_SHIPMENT_QTY_WPS`, SUM(spbq.`EXPIRED_STOCK_WPS`) `BATCH_EXPIRED_STOCK_WPS`, SUM(spbq.`EXPIRED_STOCK`) `BATCH_EXPIRED_STOCK`, SUM(spbq.`CLOSING_BALANCE`) `BATCH_CLOSING_BALANCE`, SUM(spbq.`CLOSING_BALANCE_WPS`) `BATCH_CLOSING_BALANCE_WPS` FROM rm_supply_plan_batch_qty spbq LEFT JOIN rm_batch_info bi ON spbq.`BATCH_ID`=bi.`BATCH_ID` WHERE spbq.`PROGRAM_ID`=:programId and spbq.`VERSION_ID`=:versionId GROUP by spbq.`PLANNING_UNIT_ID`, spbq.`TRANS_DATE`, spbq.`BATCH_ID`) b2 ON spa.`PLANNING_UNIT_ID`=b2.`PLANNING_UNIT_ID` AND spa.`TRANS_DATE`=b2.`TRANS_DATE` "
                + "LEFT JOIN rm_batch_info bi ON b2.BATCH_ID=bi.BATCH_ID "
                + "WHERE spa.`PROGRAM_ID`=:programId AND spa.`VERSION_ID`=:versionId AND (:planningUnitActive = FALSE OR ppu.ACTIVE) AND (:useCutOff = FALSE OR (:useCutOff = TRUE AND spa.TRANS_DATE>=:cutOffDate))";
        return this.namedParameterJdbcTemplate.query(sqlString, params, new SimplifiedSupplyPlanResultSetExtractor());
    }

    @Override
    public SupplyPlan getSupplyPlan(int programId, int versionId) {
//        System.out.println("Going to call buildSimpleSupplyPlan SP");
//        System.out.println(new Date());
        String sqlString = "CALL buildSimpleSupplyPlan(:programId, :versionId)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        SupplyPlan sp = this.namedParameterJdbcTemplate.query(sqlString, params, new SupplyPlanResultSetExtractor());
//        System.out.println("SP call completed");
//        System.out.println(new Date());
//        System.out.println("Going through the loops to calculate on basis of FEFO");
//        System.out.println(new Date());
//        System.out.println("PLANNING_UNIT_ID\tBATCH_ID\tTRANS_DATE\tEXPIRY_DATE\tSHIPMENT_QTY\tSHIPMENT_QTY_WPS\tCONSUMPTION\tADJUSTMENT\tEXPIRED\tUNALLOCATED\tCALCULCATED\tOPEN_BAL\tCLOSE_BAL\tUNMET DEMAND\tEXPIRED_WPS\tUNALLOCATED_WPS\tCALCULCATED_WPS\tOPEN_BAL_WPS\tCLOSE_BAL_WPS\tUNMET DEMAND_WPS");
        for (SupplyPlanDate sd : sp.getSupplyPlanDateList()) {
            for (SupplyPlanBatchInfo spbi : sd.getBatchList()) {
                int prevCB = sp.getPrevClosingBalance(sd.getPlanningUnitId(), spbi.getBatchId(), sd.getPrevTransDate());
                spbi.setOpeningBalance(prevCB, sd.getTransDate());
                sd.setUnallocatedConsumption(spbi.updateUnAllocatedCountAndExpiredStock(sd.getTransDate(), sd.getUnallocatedConsumption()));
                int prevCBWps = sp.getPrevClosingBalanceWps(sd.getPlanningUnitId(), spbi.getBatchId(), sd.getPrevTransDate());
                spbi.setOpeningBalanceWps(prevCBWps, sd.getTransDate());
                sd.setUnallocatedConsumptionWps(spbi.updateUnAllocatedCountAndExpiredStockWps(sd.getTransDate(), sd.getUnallocatedConsumptionWps()));
            }
            int unallocatedConsumption = sd.getUnallocatedConsumption();
            int unallocatedConsumptionWps = sd.getUnallocatedConsumptionWps();
            for (SupplyPlanBatchInfo spbi : sd.getBatchList()) {
                sd.setUnallocatedConsumption(unallocatedConsumption);
                sd.setUnallocatedConsumptionWps(unallocatedConsumptionWps);
                unallocatedConsumption = spbi.updateCB(unallocatedConsumption);
                unallocatedConsumptionWps = spbi.updateCBWps(unallocatedConsumptionWps);
                if (spbi.getBatchId() == 0 && unallocatedConsumption > 0) {
                    spbi.setUnmetDemand(unallocatedConsumption);
                    sd.setUnallocatedConsumption(0);
                }
                if (spbi.getBatchId() == 0 && unallocatedConsumptionWps > 0) {
                    spbi.setUnmetDemandWps(unallocatedConsumptionWps);
                    sd.setUnallocatedConsumptionWps(0);
                }
//                System.out.println(sd.getPlanningUnitId() + "\t\t" + spbi.getBatchId() + "\t\t" + sd.getTransDate() + "\t\t" + spbi.getExpiryDate() + "\t\t" + spbi.getShipmentQty() + "\t\t" + (spbi.getShipmentQty() - spbi.getManualPlannedShipmentQty()) + "\t\t" + spbi.getConsumption() + "\t\t" + spbi.getAdjustment() + "\t\t" + spbi.getExpiredStock() + "\t\t" + sd.getUnallocatedConsumption() + "\t\t" + spbi.getCalculatedConsumption() + "\t\t" + spbi.getOpeningBalance() + "\t\t" + spbi.getClosingBalance() + "\t\t" + spbi.getUnmetDemand() + "\t\t" + spbi.getExpiredStockWps() + "\t\t" + sd.getUnallocatedConsumptionWps() + "\t\t" + spbi.getCalculatedConsumptionWps() + "\t\t" + spbi.getOpeningBalanceWps() + "\t\t" + spbi.getClosingBalanceWps() + "\t\t" + spbi.getUnmetDemandWps());
            }
        }
//        System.out.println("Completed loops");
//        System.out.println(new Date());
        return sp;
    }

    /*newBatchSubstituteMap takes ExpiryDate and Planning Unit as key*/
    @Override
    @Transactional
    public List<SimplifiedSupplyPlan> getNewSupplyPlanList(int programId, int versionId, boolean rebuild, boolean returnSupplyPlan) throws ParseException {
        Map<String, Integer> newBatchSubstituteMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        if (versionId == -1) {
            String sqlString = "SELECT p.CURRENT_VERSION_ID FROM rm_program p WHERE p.PROGRAM_ID=:programId";
            versionId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
            params.replace("versionId", versionId);
        }
        logger.info("Version Id is " + versionId);
        if (rebuild == true) {
            logger.info("Going to start rebuild process");
            MasterSupplyPlan msp = new MasterSupplyPlan(programId, versionId);
            // SP to get the SupplyPlan grouped on PlanningUnit and Region
            String sqlString = "CALL buildNewSupplyPlanRegion(:programId, :versionId)";
            List<NewSupplyPlan> spList = this.namedParameterJdbcTemplate.query(sqlString, params, new NewSupplyPlanRegionResultSetExtractor());
            logger.info("Got the Supply Plan Region list from SP");
            sqlString = "CALL buildNewSupplyPlanBatch(:programId, :versionId)";
            // Add the Batch information to the SupplyPlan
            msp.setNspList(this.namedParameterJdbcTemplate.query(sqlString, params, new NewSupplyPlanBatchResultSetExtractor(spList)));
            logger.info("Got the Supply Plan Batch list from SP");
            // Build the Supply Plan over here
            msp.buildPlan();
            logger.info("Rebuilding done");
            // Store the data in rm_supply_plan_amc and rm_supply_plan_batch_info
            List<MapSqlParameterSource> amcParams = new LinkedList<>();
            List<MapSqlParameterSource> batchParams = new LinkedList<>();
            int i = 0;
            logger.info("Building the records fpr batch insert");
            for (NewSupplyPlan nsp : msp.getNspList()) {
                MapSqlParameterSource a1 = new MapSqlParameterSource();
                a1.addValue("PROGRAM_ID", msp.getProgramId());
                a1.addValue("VERSION_ID", msp.getVersionId());
                a1.addValue("PLANNING_UNIT_ID", nsp.getPlanningUnitId());
                a1.addValue("TRANS_DATE", nsp.getTransDate());
                a1.addValue("OPENING_BALANCE", nsp.getOpeningBalance());
                a1.addValue("OPENING_BALANCE_WPS", nsp.getOpeningBalanceWps());
                a1.addValue("MANUAL_PLANNED_SHIPMENT_QTY", nsp.getPlannedShipmentsTotalData());
                a1.addValue("MANUAL_SUBMITTED_SHIPMENT_QTY", nsp.getSubmittedShipmentsTotalData());
                a1.addValue("MANUAL_APPROVED_SHIPMENT_QTY", nsp.getApprovedShipmentsTotalData());
                a1.addValue("MANUAL_SHIPPED_SHIPMENT_QTY", nsp.getShippedShipmentsTotalData());
                a1.addValue("MANUAL_RECEIVED_SHIPMENT_QTY", nsp.getReceivedShipmentsTotalData());
                a1.addValue("MANUAL_ONHOLD_SHIPMENT_QTY", nsp.getOnholdShipmentsTotalData());
                a1.addValue("ERP_PLANNED_SHIPMENT_QTY", nsp.getPlannedErpShipmentsTotalData());
                a1.addValue("ERP_SUBMITTED_SHIPMENT_QTY", nsp.getSubmittedErpShipmentsTotalData());
                a1.addValue("ERP_APPROVED_SHIPMENT_QTY", nsp.getApprovedErpShipmentsTotalData());
                a1.addValue("ERP_SHIPPED_SHIPMENT_QTY", nsp.getShippedErpShipmentsTotalData());
                a1.addValue("ERP_RECEIVED_SHIPMENT_QTY", nsp.getReceivedErpShipmentsTotalData());
                a1.addValue("ERP_ONHOLD_SHIPMENT_QTY", nsp.getOnholdErpShipmentsTotalData());
                a1.addValue("SHIPMENT_QTY", nsp.getManualShipmentTotal() + nsp.getErpShipmentTotal());
                a1.addValue("FORECASTED_CONSUMPTION_QTY", nsp.getForecastedConsumptionQty());
                a1.addValue("ACTUAL_CONSUMPTION_QTY", nsp.getActualConsumptionQty());
                a1.addValue("ADJUSTED_CONSUMPTION_QTY", nsp.getAdjustedConsumptionQty());
                a1.addValue("ACTUAL", nsp.isActualConsumptionFlag());
                a1.addValue("ADJUSTMENT_MULTIPLIED_QTY", nsp.getAdjustmentQty());
                a1.addValue("STOCK_MULTIPLIED_QTY", nsp.getStockQty());
                a1.addValue("REGION_COUNT", nsp.getRegionCount());
                a1.addValue("REGION_COUNT_FOR_STOCK", nsp.getRegionCountForStock());
                a1.addValue("NATIONAL_ADJUSTMENT", nsp.getNationalAdjustment());
                a1.addValue("NATIONAL_ADJUSTMENT_WPS", nsp.getNationalAdjustmentWps());
                a1.addValue("EXPIRED_STOCK", nsp.getExpiredStock());
                a1.addValue("EXPIRED_STOCK_WPS", nsp.getExpiredStockWps());
                a1.addValue("CLOSING_BALANCE", nsp.getClosingBalance());
                a1.addValue("CLOSING_BALANCE_WPS", nsp.getClosingBalanceWps());
                a1.addValue("UNMET_DEMAND", nsp.getUnmetDemand());
                a1.addValue("UNMET_DEMAND_WPS", nsp.getUnmetDemandWps());
                amcParams.add(a1);
                for (BatchData bd : nsp.getBatchDataList()) {
                    int batchId;
                    if (bd.getBatchId() < 0) {
                        // This is a new Batch so check if it has just been created if not then create it
                        batchId = newBatchSubstituteMap.getOrDefault(bd.getExpiryDate() + "-" + nsp.getPlanningUnitId(), 0);
                        if (batchId == 0) {
                            String sql = "SELECT bi.BATCH_ID BATCH_ID FROM rm_batch_info bi WHERE bi.PROGRAM_ID=:programId AND bi.PLANNING_UNIT_ID=:planningUnitId AND DATE(bi.CREATED_DATE)=DATE(:curDate) AND bi.EXPIRY_DATE=:expiryDate ORDER BY bi.BATCH_ID DESC LIMIT 1";
                            Map<String, Object> newBatchParams = new HashMap<>();
                            newBatchParams.put("programId", msp.getProgramId());
                            newBatchParams.put("planningUnitId", nsp.getPlanningUnitId());
                            newBatchParams.put("transDate", nsp.getTransDate());
                            newBatchParams.put("curDate", nsp.getTransDate());
                            newBatchParams.put("expiryDate", bd.getExpiryDate());
                            try {
//                                System.out.println(LogUtils.buildStringForLog(sql, newBatchParams));
                                batchId = this.namedParameterJdbcTemplate.queryForObject(sql, newBatchParams, Integer.class);
                            } catch (EmptyResultDataAccessException erda) {
                                sql = "INSERT INTO `rm_batch_info` SELECT "
                                        + "    null, "
                                        + "    ppu.PROGRAM_ID, "
                                        + "    ppu.PLANNING_UNIT_ID, "
                                        + "    CONCAT('QAT',LPAD(ppu.PROGRAM_ID, 6,'0'), LPAD(ppu.PLANNING_UNIT_ID, 8,'0'), date_format(CONCAT(LEFT(ADDDATE(:transDate, INTERVAL ppu.SHELF_LIFE MONTH),7),'-01'), '%y%m%d'), SUBSTRING('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', RAND()*36+1, 1), SUBSTRING('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', RAND()*36+1, 1), SUBSTRING('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789', RAND()*36+1, 1)), "
                                        + "    CONCAT(LEFT(ADDDATE(:transDate, INTERVAL ppu.SHELF_LIFE MONTH),7),'-01'), "
                                        + "    :curDate, "
                                        + "    null, 1 FROM rm_program_planning_unit ppu where ppu.PROGRAM_ID=:programId AND ppu.PLANNING_UNIT_ID=:planningUnitId";
                                this.namedParameterJdbcTemplate.update(sql, newBatchParams);
                                batchId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
                            }
                            newBatchSubstituteMap.put(bd.getExpiryDate() + "-" + nsp.getPlanningUnitId(), batchId);
                        }
                        bd.setBatchId(batchId);
                        bd.setExpiryDate(this.jdbcTemplate.queryForObject("SELECT EXPIRY_DATE FROM rm_batch_info WHERE BATCH_ID=?", String.class, bd.getBatchId()));
                    }
                    MapSqlParameterSource b1 = new MapSqlParameterSource();
                    b1.addValue("PROGRAM_ID", msp.getProgramId());
                    b1.addValue("VERSION_ID", msp.getVersionId());
                    b1.addValue("PLANNING_UNIT_ID", nsp.getPlanningUnitId());
                    b1.addValue("TRANS_DATE", nsp.getTransDate());
                    b1.addValue("BATCH_ID", bd.getBatchId());
                    b1.addValue("EXPIRY_DATE", bd.getExpiryDate());
                    b1.addValue("ACTUAL_CONSUMPTION_QTY", bd.getActualConsumption());
                    b1.addValue("ACTUAL", bd.isUseActualConsumption());
                    b1.addValue("SHIPMENT_QTY", bd.getShipment());
                    b1.addValue("SHIPMENT_QTY_WPS", bd.getShipmentWps());
                    b1.addValue("ADJUSTMENT_MULTIPLIED_QTY", bd.getAdjustment());
                    b1.addValue("STOCK_MULTIPLIED_QTY", bd.getStock());
                    b1.addValue("ALL_REGIONS_REPORTED_STOCK", bd.isAllRegionsReportedStock());
                    b1.addValue("OPENING_BALANCE", bd.getOpeningBalance());
                    b1.addValue("OPENING_BALANCE_WPS", bd.getOpeningBalanceWps());
                    b1.addValue("EXPIRED_STOCK", bd.getExpiredStock());
                    b1.addValue("EXPIRED_STOCK_WPS", bd.getExpiredStockWps());
                    b1.addValue("CALCULATED_CONSUMPTION", bd.getCalculatedFEFO() + bd.getCalculatedLEFO());
                    b1.addValue("CALCULATED_CONSUMPTION_WPS", bd.getCalculatedFEFOWps() + bd.getCalculatedLEFOWps());
                    b1.addValue("CLOSING_BALANCE", bd.getClosingBalance());
                    b1.addValue("CLOSING_BALANCE_WPS", bd.getClosingBalanceWps());
                    batchParams.add(b1);
                }
                i++;
            }
            sqlString = "DROP TABLE IF EXISTS tmp_supply_plan_amc1";
            this.namedParameterJdbcTemplate.update(sqlString, params);
            sqlString = "DROP TABLE IF EXISTS tmp_supply_plan_amc2";
            this.namedParameterJdbcTemplate.update(sqlString, params);

            sqlString = "CREATE TEMPORARY TABLE `tmp_supply_plan_amc1` ( "
                    + "  `SUPPLY_PLAN_AMC_ID` int unsigned NOT NULL AUTO_INCREMENT, `PROGRAM_ID` int unsigned NOT NULL, `VERSION_ID` int unsigned NOT NULL, `PLANNING_UNIT_ID` int unsigned NOT NULL, `TRANS_DATE` date NOT NULL, "
                    + "  `AMC` decimal(24,8) DEFAULT NULL, `AMC_COUNT` int DEFAULT NULL, `MOS` decimal(24,8) DEFAULT NULL, `MOS_WPS` decimal(24,8) DEFAULT NULL, `MIN_STOCK_QTY` decimal(24,8) DEFAULT NULL, "
                    + "  `MIN_STOCK_MOS` decimal(24,8) DEFAULT NULL, `MAX_STOCK_QTY` decimal(24,8) DEFAULT NULL, `MAX_STOCK_MOS` decimal(24,8) DEFAULT NULL, `OPENING_BALANCE` bigint DEFAULT NULL, `OPENING_BALANCE_WPS` bigint DEFAULT NULL, "
                    + "  `MANUAL_PLANNED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_SUBMITTED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_APPROVED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_SHIPPED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_RECEIVED_SHIPMENT_QTY` bigint DEFAULT NULL, "
                    + "  `MANUAL_ONHOLD_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_PLANNED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_SUBMITTED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_APPROVED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_SHIPPED_SHIPMENT_QTY` bigint DEFAULT NULL, "
                    + "  `ERP_RECEIVED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_ONHOLD_SHIPMENT_QTY` bigint DEFAULT NULL, `SHIPMENT_QTY` bigint DEFAULT NULL, `FORECASTED_CONSUMPTION_QTY` bigint DEFAULT NULL, `ACTUAL_CONSUMPTION_QTY` bigint DEFAULT NULL, "
                    + "  `ADJUSTED_CONSUMPTION_QTY` bigint DEFAULT NULL, `ACTUAL` tinyint(1) DEFAULT NULL, `ADJUSTMENT_MULTIPLIED_QTY` bigint DEFAULT NULL, `STOCK_MULTIPLIED_QTY` bigint DEFAULT NULL, `REGION_COUNT` int unsigned NOT NULL, "
                    + "  `REGION_COUNT_FOR_STOCK` int unsigned NOT NULL, `EXPIRED_STOCK` bigint DEFAULT NULL, `EXPIRED_STOCK_WPS` bigint DEFAULT NULL, `CLOSING_BALANCE` bigint DEFAULT NULL, `CLOSING_BALANCE_WPS` bigint DEFAULT NULL, "
                    + "  `UNMET_DEMAND` bigint DEFAULT NULL, `UNMET_DEMAND_WPS` bigint DEFAULT NULL, `NATIONAL_ADJUSTMENT` bigint DEFAULT NULL, `NATIONAL_ADJUSTMENT_WPS` bigint DEFAULT NULL, PRIMARY KEY (`SUPPLY_PLAN_AMC_ID`)) ENGINE=InnoDB";
            // Create table
            this.namedParameterJdbcTemplate.update(sqlString, params);

            sqlString = "CREATE TEMPORARY TABLE `tmp_supply_plan_amc2` ( "
                    + "  `SUPPLY_PLAN_AMC_ID` int unsigned NOT NULL AUTO_INCREMENT, `PROGRAM_ID` int unsigned NOT NULL, `VERSION_ID` int unsigned NOT NULL, `PLANNING_UNIT_ID` int unsigned NOT NULL, `TRANS_DATE` date NOT NULL, "
                    + "  `AMC` decimal(24,8) DEFAULT NULL, `AMC_COUNT` int DEFAULT NULL, `MOS` decimal(24,8) DEFAULT NULL, `MOS_WPS` decimal(24,8) DEFAULT NULL, `MIN_STOCK_QTY` decimal(24,8) DEFAULT NULL, "
                    + "  `MIN_STOCK_MOS` decimal(24,8) DEFAULT NULL, `MAX_STOCK_QTY` decimal(24,8) DEFAULT NULL, `MAX_STOCK_MOS` decimal(24,8) DEFAULT NULL, `OPENING_BALANCE` bigint DEFAULT NULL, `OPENING_BALANCE_WPS` bigint DEFAULT NULL, "
                    + "  `MANUAL_PLANNED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_SUBMITTED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_APPROVED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_SHIPPED_SHIPMENT_QTY` bigint DEFAULT NULL, `MANUAL_RECEIVED_SHIPMENT_QTY` bigint DEFAULT NULL, "
                    + "  `MANUAL_ONHOLD_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_PLANNED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_SUBMITTED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_APPROVED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_SHIPPED_SHIPMENT_QTY` bigint DEFAULT NULL, "
                    + "  `ERP_RECEIVED_SHIPMENT_QTY` bigint DEFAULT NULL, `ERP_ONHOLD_SHIPMENT_QTY` bigint DEFAULT NULL, `SHIPMENT_QTY` bigint DEFAULT NULL, `FORECASTED_CONSUMPTION_QTY` bigint DEFAULT NULL, `ACTUAL_CONSUMPTION_QTY` bigint DEFAULT NULL, "
                    + "  `ADJUSTED_CONSUMPTION_QTY` bigint DEFAULT NULL, `ACTUAL` tinyint(1) DEFAULT NULL, `ADJUSTMENT_MULTIPLIED_QTY` bigint DEFAULT NULL, `STOCK_MULTIPLIED_QTY` bigint DEFAULT NULL, `REGION_COUNT` int unsigned NOT NULL, "
                    + "  `REGION_COUNT_FOR_STOCK` int unsigned NOT NULL, `EXPIRED_STOCK` bigint DEFAULT NULL, `EXPIRED_STOCK_WPS` bigint DEFAULT NULL, `CLOSING_BALANCE` bigint DEFAULT NULL, `CLOSING_BALANCE_WPS` bigint DEFAULT NULL, "
                    + "  `UNMET_DEMAND` bigint DEFAULT NULL, `UNMET_DEMAND_WPS` bigint DEFAULT NULL, `NATIONAL_ADJUSTMENT` bigint DEFAULT NULL, `NATIONAL_ADJUSTMENT_WPS` bigint DEFAULT NULL, PRIMARY KEY (`SUPPLY_PLAN_AMC_ID`)) ENGINE=InnoDB";
            // Create table
            this.namedParameterJdbcTemplate.update(sqlString, params);

            logger.info("Delete the existing records from supply plan amc");
            this.namedParameterJdbcTemplate.update("DELETE sma.* FROM rm_supply_plan_amc sma WHERE sma.PROGRAM_ID=:programId AND sma.VERSION_ID=:versionId", params);
            logger.info("Delete done");
            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_supply_plan_amc").usingColumns("PROGRAM_ID", "VERSION_ID", "PLANNING_UNIT_ID", "TRANS_DATE", "OPENING_BALANCE", "OPENING_BALANCE_WPS", "MANUAL_PLANNED_SHIPMENT_QTY", "MANUAL_SUBMITTED_SHIPMENT_QTY", "MANUAL_APPROVED_SHIPMENT_QTY", "MANUAL_SHIPPED_SHIPMENT_QTY", "MANUAL_RECEIVED_SHIPMENT_QTY", "MANUAL_ONHOLD_SHIPMENT_QTY", "ERP_PLANNED_SHIPMENT_QTY", "ERP_SUBMITTED_SHIPMENT_QTY", "ERP_APPROVED_SHIPMENT_QTY", "ERP_SHIPPED_SHIPMENT_QTY", "ERP_RECEIVED_SHIPMENT_QTY", "ERP_ONHOLD_SHIPMENT_QTY", "SHIPMENT_QTY", "FORECASTED_CONSUMPTION_QTY", "ACTUAL_CONSUMPTION_QTY", "ADJUSTED_CONSUMPTION_QTY", "ACTUAL", "ADJUSTMENT_MULTIPLIED_QTY", "STOCK_MULTIPLIED_QTY", "REGION_COUNT", "REGION_COUNT_FOR_STOCK", "NATIONAL_ADJUSTMENT", "NATIONAL_ADJUSTMENT_WPS", "EXPIRED_STOCK", "EXPIRED_STOCK_WPS", "CLOSING_BALANCE", "CLOSING_BALANCE_WPS", "UNMET_DEMAND", "UNMET_DEMAND_WPS");
            SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("tmp_supply_plan_amc").usingColumns("PROGRAM_ID", "VERSION_ID", "PLANNING_UNIT_ID", "TRANS_DATE", "OPENING_BALANCE", "OPENING_BALANCE_WPS", "MANUAL_PLANNED_SHIPMENT_QTY", "MANUAL_SUBMITTED_SHIPMENT_QTY", "MANUAL_APPROVED_SHIPMENT_QTY", "MANUAL_SHIPPED_SHIPMENT_QTY", "MANUAL_RECEIVED_SHIPMENT_QTY", "MANUAL_ONHOLD_SHIPMENT_QTY", "ERP_PLANNED_SHIPMENT_QTY", "ERP_SUBMITTED_SHIPMENT_QTY", "ERP_APPROVED_SHIPMENT_QTY", "ERP_SHIPPED_SHIPMENT_QTY", "ERP_RECEIVED_SHIPMENT_QTY", "ERP_ONHOLD_SHIPMENT_QTY", "SHIPMENT_QTY", "FORECASTED_CONSUMPTION_QTY", "ACTUAL_CONSUMPTION_QTY", "ADJUSTED_CONSUMPTION_QTY", "ACTUAL", "ADJUSTMENT_MULTIPLIED_QTY", "STOCK_MULTIPLIED_QTY", "REGION_COUNT", "REGION_COUNT_FOR_STOCK", "NATIONAL_ADJUSTMENT", "NATIONAL_ADJUSTMENT_WPS", "EXPIRED_STOCK", "EXPIRED_STOCK_WPS", "CLOSING_BALANCE", "CLOSING_BALANCE_WPS", "UNMET_DEMAND", "UNMET_DEMAND_WPS");
            MapSqlParameterSource[] amcParamsArray = new MapSqlParameterSource[amcParams.size()];
            amcParams.toArray(amcParamsArray);
            si.executeBatch(amcParamsArray);
            this.namedParameterJdbcTemplate.batchUpdate("INSERT INTO tmp_supply_plan_amc1 (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `TRANS_DATE`, `OPENING_BALANCE`, `OPENING_BALANCE_WPS`, `MANUAL_PLANNED_SHIPMENT_QTY`, `MANUAL_SUBMITTED_SHIPMENT_QTY`, `MANUAL_APPROVED_SHIPMENT_QTY`, `MANUAL_SHIPPED_SHIPMENT_QTY`, `MANUAL_RECEIVED_SHIPMENT_QTY`, `MANUAL_ONHOLD_SHIPMENT_QTY`, `ERP_PLANNED_SHIPMENT_QTY`, `ERP_SUBMITTED_SHIPMENT_QTY`, `ERP_APPROVED_SHIPMENT_QTY`, `ERP_SHIPPED_SHIPMENT_QTY`, `ERP_RECEIVED_SHIPMENT_QTY`, `ERP_ONHOLD_SHIPMENT_QTY`, `SHIPMENT_QTY`, `FORECASTED_CONSUMPTION_QTY`, `ACTUAL_CONSUMPTION_QTY`, `ADJUSTED_CONSUMPTION_QTY`, `ACTUAL`, `ADJUSTMENT_MULTIPLIED_QTY`, `STOCK_MULTIPLIED_QTY`, `REGION_COUNT`, `REGION_COUNT_FOR_STOCK`, `NATIONAL_ADJUSTMENT`, `NATIONAL_ADJUSTMENT_WPS`, `EXPIRED_STOCK`, `EXPIRED_STOCK_WPS`, `CLOSING_BALANCE`, `CLOSING_BALANCE_WPS`, `UNMET_DEMAND`, `UNMET_DEMAND_WPS`) VALUES (:PROGRAM_ID, :VERSION_ID, :PLANNING_UNIT_ID, :TRANS_DATE, :OPENING_BALANCE, :OPENING_BALANCE_WPS, :MANUAL_PLANNED_SHIPMENT_QTY, :MANUAL_SUBMITTED_SHIPMENT_QTY, :MANUAL_APPROVED_SHIPMENT_QTY, :MANUAL_SHIPPED_SHIPMENT_QTY, :MANUAL_RECEIVED_SHIPMENT_QTY, :MANUAL_ONHOLD_SHIPMENT_QTY, :ERP_PLANNED_SHIPMENT_QTY, :ERP_SUBMITTED_SHIPMENT_QTY, :ERP_APPROVED_SHIPMENT_QTY, :ERP_SHIPPED_SHIPMENT_QTY, :ERP_RECEIVED_SHIPMENT_QTY, :ERP_ONHOLD_SHIPMENT_QTY, :SHIPMENT_QTY, :FORECASTED_CONSUMPTION_QTY, :ACTUAL_CONSUMPTION_QTY, :ADJUSTED_CONSUMPTION_QTY, :ACTUAL, :ADJUSTMENT_MULTIPLIED_QTY, :STOCK_MULTIPLIED_QTY, :REGION_COUNT, :REGION_COUNT_FOR_STOCK, :NATIONAL_ADJUSTMENT, :NATIONAL_ADJUSTMENT_WPS, :EXPIRED_STOCK, :EXPIRED_STOCK_WPS, :CLOSING_BALANCE, :CLOSING_BALANCE_WPS, :UNMET_DEMAND, :UNMET_DEMAND_WPS)", amcParamsArray);
            this.namedParameterJdbcTemplate.batchUpdate("INSERT INTO tmp_supply_plan_amc2 (`PROGRAM_ID`, `VERSION_ID`, `PLANNING_UNIT_ID`, `TRANS_DATE`, `OPENING_BALANCE`, `OPENING_BALANCE_WPS`, `MANUAL_PLANNED_SHIPMENT_QTY`, `MANUAL_SUBMITTED_SHIPMENT_QTY`, `MANUAL_APPROVED_SHIPMENT_QTY`, `MANUAL_SHIPPED_SHIPMENT_QTY`, `MANUAL_RECEIVED_SHIPMENT_QTY`, `MANUAL_ONHOLD_SHIPMENT_QTY`, `ERP_PLANNED_SHIPMENT_QTY`, `ERP_SUBMITTED_SHIPMENT_QTY`, `ERP_APPROVED_SHIPMENT_QTY`, `ERP_SHIPPED_SHIPMENT_QTY`, `ERP_RECEIVED_SHIPMENT_QTY`, `ERP_ONHOLD_SHIPMENT_QTY`, `SHIPMENT_QTY`, `FORECASTED_CONSUMPTION_QTY`, `ACTUAL_CONSUMPTION_QTY`, `ADJUSTED_CONSUMPTION_QTY`, `ACTUAL`, `ADJUSTMENT_MULTIPLIED_QTY`, `STOCK_MULTIPLIED_QTY`, `REGION_COUNT`, `REGION_COUNT_FOR_STOCK`, `NATIONAL_ADJUSTMENT`, `NATIONAL_ADJUSTMENT_WPS`, `EXPIRED_STOCK`, `EXPIRED_STOCK_WPS`, `CLOSING_BALANCE`, `CLOSING_BALANCE_WPS`, `UNMET_DEMAND`, `UNMET_DEMAND_WPS`) VALUES (:PROGRAM_ID, :VERSION_ID, :PLANNING_UNIT_ID, :TRANS_DATE, :OPENING_BALANCE, :OPENING_BALANCE_WPS, :MANUAL_PLANNED_SHIPMENT_QTY, :MANUAL_SUBMITTED_SHIPMENT_QTY, :MANUAL_APPROVED_SHIPMENT_QTY, :MANUAL_SHIPPED_SHIPMENT_QTY, :MANUAL_RECEIVED_SHIPMENT_QTY, :MANUAL_ONHOLD_SHIPMENT_QTY, :ERP_PLANNED_SHIPMENT_QTY, :ERP_SUBMITTED_SHIPMENT_QTY, :ERP_APPROVED_SHIPMENT_QTY, :ERP_SHIPPED_SHIPMENT_QTY, :ERP_RECEIVED_SHIPMENT_QTY, :ERP_ONHOLD_SHIPMENT_QTY, :SHIPMENT_QTY, :FORECASTED_CONSUMPTION_QTY, :ACTUAL_CONSUMPTION_QTY, :ADJUSTED_CONSUMPTION_QTY, :ACTUAL, :ADJUSTMENT_MULTIPLIED_QTY, :STOCK_MULTIPLIED_QTY, :REGION_COUNT, :REGION_COUNT_FOR_STOCK, :NATIONAL_ADJUSTMENT, :NATIONAL_ADJUSTMENT_WPS, :EXPIRED_STOCK, :EXPIRED_STOCK_WPS, :CLOSING_BALANCE, :CLOSING_BALANCE_WPS, :UNMET_DEMAND, :UNMET_DEMAND_WPS)", amcParamsArray);
            logger.info("Batch insert for supply plan amc completed");
            logger.info("Delete the existing records from supply plan batch");
            this.namedParameterJdbcTemplate.update("DELETE smq.* FROM rm_supply_plan_batch_qty smq WHERE smq.PROGRAM_ID=:programId AND smq.VERSION_ID=:versionId", params);
            MapSqlParameterSource[] batchParamsArray = new MapSqlParameterSource[batchParams.size()];
            batchParams.toArray(batchParamsArray);
            si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_supply_plan_batch_qty");
            si.executeBatch(batchParamsArray);
            logger.info("Batch insert for supply plan batch completed");
            params.clear();

            sqlString = "UPDATE rm_supply_plan_amc spa "
                    + "    LEFT JOIN rm_program_planning_unit ppu ON spa.PROGRAM_ID=ppu.PROGRAM_ID AND spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID "
                    + "    LEFT JOIN rm_program p ON spa.PROGRAM_ID=p.PROGRAM_ID "
                    + "    LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                    + "    LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
                    + "    LEFT JOIN ( "
                    + "        SELECT spa.PROGRAM_ID, spa.VERSION_ID, spa.PLANNING_UNIT_ID, spa.TRANS_DATE, ppu.MONTHS_IN_PAST_FOR_AMC, ppu.MONTHS_IN_FUTURE_FOR_AMC, SUBDATE(spa.TRANS_DATE, INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH), ADDDATE(spa.TRANS_DATE, INTERVAL CAST(ppu.MONTHS_IN_FUTURE_FOR_AMC AS SIGNED)-1 MONTH), "
                    + "            SUM(IF(spa2.ACTUAL, spa2.ADJUSTED_CONSUMPTION_QTY,spa2.FORECASTED_CONSUMPTION_QTY)) AMC_SUM, "
                    + "            AVG(IF(spa2.ACTUAL, spa2.ADJUSTED_CONSUMPTION_QTY,spa2.FORECASTED_CONSUMPTION_QTY)) AMC, COUNT(IF(spa2.ACTUAL, spa2.ADJUSTED_CONSUMPTION_QTY,spa2.FORECASTED_CONSUMPTION_QTY)) AMC_COUNT "
                    + "        FROM tmp_supply_plan_amc1 spa "
                    + "        LEFT JOIN rm_program_planning_unit ppu ON spa.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND spa.PROGRAM_ID=ppu.PROGRAM_ID "
                    + "        LEFT JOIN (SELECT * FROM tmp_supply_plan_amc2 spa2) spa2 ON "
                    + "            spa.PLANNING_UNIT_ID=spa2.PLANNING_UNIT_ID "
                    + "            AND spa2.TRANS_DATE BETWEEN SUBDATE(spa.TRANS_DATE, INTERVAL ppu.MONTHS_IN_PAST_FOR_AMC MONTH) AND ADDDATE(spa.TRANS_DATE, INTERVAL CAST(ppu.MONTHS_IN_FUTURE_FOR_AMC AS SIGNED)-1 MONTH) "
                    + "        GROUP BY spa.PLANNING_UNIT_ID, spa.TRANS_DATE "
                    + "    ) amc ON spa.PROGRAM_ID=amc.PROGRAM_ID AND spa.VERSION_ID=amc.VERSION_ID AND spa.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID AND spa.TRANS_DATE=amc.TRANS_DATE "
                    + "    SET "
                    + "        spa.AMC=amc.AMC, "
                    + "        spa.AMC_COUNT=amc.AMC_COUNT, "
                    + "        spa.MOS=IF(amc.AMC IS NULL OR amc.AMC=0, null, spa.CLOSING_BALANCE/amc.AMC), "
                    + "        spa.MOS_WPS=IF(amc.AMC IS NULL OR amc.AMC=0, null, spa.CLOSING_BALANCE_WPS/amc.AMC), "
                    + "        spa.MIN_STOCK_MOS = IF(ppu.PLAN_BASED_ON=1, IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK), IF(amc.AMC IS NULL OR amc.AMC=0, null, ppu.MIN_QTY/amc.AMC)), "
                    + "        spa.MAX_STOCK_MOS = IF(ppu.PLAN_BASED_ON=1, IF( "
                    + "                                IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS<r.MIN_MOS_MAX_GAURDRAIL, "
                    + "                                r.MIN_MOS_MAX_GAURDRAIL, "
                    + "                                IF ( "
                    + "                                    IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)>r.MAX_MOS_MAX_GAURDRAIL, "
                    + "                                    r.MAX_MOS_MAX_GAURDRAIL, "
                    + "                                    IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS "
                    + "                                ) "
                    + "                            ), IF(amc.AMC IS NULL OR amc.AMC=0, null, ppu.MIN_QTY/amc.AMC + ppu.REORDER_FREQUENCY_IN_MONTHS)),"
                    + "        spa.MIN_STOCK_QTY = IF(ppu.PLAN_BASED_ON=1, IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK) * amc.AMC, ppu.MIN_QTY), "
                    + "        spa.MAX_STOCK_QTY = IF(ppu.PLAN_BASED_ON=1, IF( "
                    + "                                IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS<r.MIN_MOS_MAX_GAURDRAIL, "
                    + "                                r.MIN_MOS_MAX_GAURDRAIL, "
                    + "                                IF ( "
                    + "                                    IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)>r.MAX_MOS_MAX_GAURDRAIL, "
                    + "                                    r.MAX_MOS_MAX_GAURDRAIL, "
                    + "                                    IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS "
                    + "                                ) "
                    + "                            )* amc.AMC, ppu.MIN_QTY + ppu.REORDER_FREQUENCY_IN_MONTHS*amc.AMC)  "
                    + "        WHERE spa.PROGRAM_ID=@programId and spa.VERSION_ID=@versionId";
            logger.info("Going to update the supply plan amc records");
            this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("Update completed, now dropping tmp table");
            this.namedParameterJdbcTemplate.update("DROP TABLE IF EXISTS tmp_supply_plan_amc1", params);
            this.namedParameterJdbcTemplate.update("DROP TABLE IF EXISTS tmp_supply_plan_amc2", params);
            logger.info("Table dropped");
//            msp.printSupplyPlan();
        }

        if (returnSupplyPlan) {
            logger.info("Going to get the new supply plan batch");
            List<SimplifiedSupplyPlan> sp = getSimplifiedSupplyPlan(programId, versionId, false, null);
            logger.info("Records retrieved");
            return sp;
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public int updateSentToARTMISFlag(String programVersionIds) {
        String sql = "UPDATE rm_program_version p SET p.`SENT_TO_ARTMIS`=1 WHERE p.`PROGRAM_VERSION_ID` IN (" + programVersionIds + ");";
        return this.jdbcTemplate.update(sql);
    }

    @Override
    public List<Shipment> getShipmentListForSync(int programId, int versionId, String lastSyncDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query("CALL getShipmentDataForSync(:programId, :versionId, :lastSyncDate)", params, new ShipmentListResultSetExtractor());
    }

    @Override
    public List<Batch> getBatchListForSync(int programId, int versionId, String lastSyncDate) {
        String sqlString = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.PROGRAM_ID, bi.PLANNING_UNIT_ID `BATCH_PLANNING_UNIT_ID`, bi.`AUTO_GENERATED`, bi.EXPIRY_DATE, bi.CREATED_DATE FROM rm_batch_info bi WHERE bi.PROGRAM_ID=:programId AND bi.CREATED_DATE > :lastSyncDate";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new BatchRowMapper());
    }

    @Override
    public List<ProgramIntegrationDTO> getSupplyPlanToExportList() {
        String sqlString = "SELECT "
                + "	ipc.PROGRAM_VERSION_TRANS_ID, p.PROGRAM_ID, p.PROGRAM_CODE, pv.VERSION_ID, pvt.VERSION_TYPE_ID, "
                + "    pvt.VERSION_STATUS_ID, ipc.INTEGRATION_PROGRAM_ID, i.INTEGRATION_ID, i.INTEGRATION_NAME, i.FILE_NAME, i.FOLDER_LOCATION, "
                + "    i.INTEGRATION_VIEW_ID, iv.INTEGRATION_VIEW_NAME "
                + "FROM rm_integration_program_completed ipc "
                + "LEFT JOIN rm_program_version_trans pvt on `ipc`.PROGRAM_VERSION_TRANS_ID=pvt.PROGRAM_VERSION_TRANS_ID "
                + "LEFT JOIN rm_program_version pv ON pvt.PROGRAM_VERSION_ID=pv.PROGRAM_VERSION_ID "
                + "LEFT JOIN vw_program p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN ap_integration i ON ipc.INTEGRATION_ID=i.INTEGRATION_ID "
                + "LEFT JOIN ap_integration_view iv ON i.INTEGRATION_VIEW_ID=iv.INTEGRATION_VIEW_ID "
                + "WHERE ipc.PROGRAM_VERSION_TRANS_ID IN (SELECT max(pvt.PROGRAM_VERSION_TRANS_ID) PVT FROM rm_integration_program_completed `ipc` LEFT JOIN rm_program_version_trans pvt on `ipc`.PROGRAM_VERSION_TRANS_ID=pvt.PROGRAM_VERSION_TRANS_ID LEFT JOIN rm_program_version pv ON pvt.PROGRAM_VERSION_ID=pv.PROGRAM_VERSION_ID WHERE `ipc`.COMPLETED_DATE IS NULL GROUP BY pv.PROGRAM_ID, `ipc`.INTEGRATION_ID)";
        return this.jdbcTemplate.query(sqlString, new ProgramIntegrationDTORowMapper());
    }

    @Override
    public boolean updateSupplyPlanAsExported(int programVersionTransId, int integrationId) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("programVersionTransId", programVersionTransId);
        params.put("integrationId", integrationId);
        params.put("curDate", curDate);
        String sql = "UPDATE rm_integration_program_completed ipc SET ipc.COMPLETED_DATE=:curDate WHERE ipc.COMPLETED_DATE IS NULL AND ipc.PROGRAM_VERSION_TRANS_ID=:programVersionTransId AND ipc.INTEGRATION_ID=:integrationId";
        int rowsEffected = this.namedParameterJdbcTemplate.update(sql, params);
        return (rowsEffected > 0);
    }

    @Override
    public String getSupplyPlanReviewerEmialList(int realmCountryId) {
        String sql = "select group_concat(u.EMAIL_ID) "
                + "from us_user u "
                + "left join us_user_role ur on u.USER_ID=ur.USER_ID "
                + "left join us_user_acl ua on ua.USER_ID=u.USER_ID "
                + "where ur.ROLE_ID='ROLE_SUPPLY_PLAN_REVIEWER' and "
                + "(ua.REALM_COUNTRY_ID is null OR ua.REALM_COUNTRY_ID=?)";
        return this.jdbcTemplate.queryForObject(sql, String.class, realmCountryId);
    }

    @Override
    public List<SimplePlanningUnitForSupplyPlanObject> getPlanningUnitListForProgramData(int programId, CustomUserDetails curUser, boolean planningUnitActive) {
        StringBuilder sql = new StringBuilder("SELECT "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ppu.ACTIVE `PROGRAM_PLANNING_UNIT_ACTIVE`, pu.MULTIPLIER, "
                + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, fu.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, fu.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, fu.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, fu.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, "
                + "    pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, "
                + "    papu.SKU_CODE, papu.ACTIVE `PROCUREMENT_AGENT_PLANNING_UNIT_ACTIVE`,"
                + "    ppu.`MONTHS_IN_FUTURE_FOR_AMC`, ppu.`MONTHS_IN_PAST_FOR_AMC`, ppu.`MIN_MONTHS_OF_STOCK`, ppu.`REORDER_FREQUENCY_IN_MONTHS`, ppu.`NOTES`, "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pc.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pc.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pc.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, pc.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR` "
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON ppu.PLANNING_UNIT_ID=papu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_procurement_agent pa ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
                + "WHERE ppu.PROGRAM_ID=:programId AND (:planningUnitActive = FALSE OR ppu.ACTIVE)");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitActive", planningUnitActive);
        this.aclService.addFullAclForProgram(sql, params, "p", curUser);
        sql.append("ORDER BY pu.PLANNING_UNIT_ID, papu.PROCUREMENT_AGENT_ID");
        return namedParameterJdbcTemplate.query(sql.toString(), params, new SimplePlanningUnitForSupplyPlanObjectResultSetExtractor());
    }

    @Override
    public List<NotificationUser> getSupplyPlanNotificationList(int programId, int versionId, int statusType, String toCc) {
        String sqlString = "CALL getSupplyPlanNotification" + toCc + "List(:programId,:versionId,:statusType)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("statusType", statusType);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new NotificationUserRowMapper());
    }

    @Override
    public String getLastModifiedDateForProgram(int programId, int versionId) {
        String sql = "select MAX(st.LAST_MODIFIED_DATE) from rm_shipment s "
                + "left join rm_shipment_trans st on s.SHIPMENT_ID=st.SHIPMENT_ID and s.MAX_VERSION_ID=st.VERSION_ID "
                + "where s.PROGRAM_ID=? and st.VERSION_ID<=?";
        return this.jdbcTemplate.queryForObject(sql, String.class, programId, versionId);
    }

    @Override
    public List<DatasetTree> getTreeListForDataset(int programId, int versionId, CustomUserDetails curUser) {
        String sql = "SELECT "
                + "ft.TREE_ID, ft.TREE_ANCHOR_ID, ft.PROGRAM_ID, ft.VERSION_ID, ft.LABEL_ID, ft.LABEL_EN, ft.LABEL_FR, ft.LABEL_SP, ft.LABEL_PR, "
                + "fm.FORECAST_METHOD_ID, fm.FORECAST_METHOD_TYPE_ID, "
                + "fm.LABEL_ID `FM_LABEL_ID`, fm.LABEL_EN `FM_LABEL_EN`, fm.LABEL_FR `FM_LABEL_FR`, fm.LABEL_SP `FM_LABEL_SP`, fm.LABEL_PR `FM_LABEL_PR`, "
                + "tl.TREE_LEVEL_ID `LEVEL_ID`, tl.LEVEL_NO, tl.LABEL_ID `TL_LABEL_ID`, tl.LABEL_EN `TL_LABEL_EN`, tl.LABEL_FR `TL_LABEL_FR`, tl.LABEL_SP `TL_LABEL_SP`, tl.LABEL_PR `TL_LABEL_PR`, "
                + "u.UNIT_ID, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, u.`UNIT_CODE`, "
                + "s.SCENARIO_ID, s.LABEL_ID `S_LABEL_ID`, s.LABEL_EN `S_LABEL_EN`, s.LABEL_FR `S_LABEL_FR`, s.LABEL_SP `S_LABEL_SP`, s.LABEL_PR `S_LABEL_PR`, s.ACTIVE `S_ACTIVE`, s.NOTES `S_NOTES`, "
                + "r.REGION_ID, r.LABEL_ID `REG_LABEL_ID`, r.LABEL_EN `REG_LABEL_EN`, r.LABEL_FR `REG_LABEL_FR`, r.LABEL_SP `REG_LABEL_SP`, r.LABEL_PR `REG_LABEL_PR`, "
                + "ft.CREATED_DATE, ft.LAST_MODIFIED_DATE, ft.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ft.`NOTES` "
                + "FROM vw_forecast_tree ft "
                + "LEFT JOIN vw_forecast_method fm ON ft.FORECAST_METHOD_ID=fm.FORECAST_METHOD_ID "
                + "LEFT JOIN vw_tree_level tl ON ft.TREE_ID=tl.TREE_ID "
                + "LEFT JOIN vw_unit u ON tl.UNIT_ID=u.UNIT_ID "
                + "LEFT JOIN vw_scenario s ON ft.TREE_ID=s.TREE_ID "
                + "LEFT JOIN rm_forecast_tree_region ftr ON ft.TREE_ID=ftr.TREE_ID "
                + "LEFT JOIN vw_region r ON ftr.REGION_ID=r.REGION_ID "
                + "LEFT JOIN us_user cb ON ft.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ft.LAST_MODIFIED_BY=lmb.USER_ID "
                + "where ft.PROGRAM_ID=:programId AND ft.VERSION_ID=:versionId";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        return this.namedParameterJdbcTemplate.query(sql, params, new DatasetTreeResultSetExtractor());
    }

    @Override
    public ForecastTree<TreeNode> getTreeData(int treeId, CustomUserDetails curUser) {
        String sql = "SELECT "
                + "          ttn.NODE_ID, ttn.TREE_ID, ttn.PARENT_NODE_ID, ttn.COLLAPSED, "
                + "          ttn.LABEL_ID, ttn.LABEL_EN, ttn.LABEL_FR, ttn.LABEL_SP, ttn.LABEL_PR, "
                + "          nt.NODE_TYPE_ID `NODE_TYPE_ID`, nt.MODELING_ALLOWED, nt.EXTRAPOLATION_ALLOWED, nt.TREE_TEMPLATE_ALLOWED, nt.FORECAST_TREE_ALLOWED, nt.LABEL_ID `NT_LABEL_ID`, nt.LABEL_EN `NT_LABEL_EN`, nt.LABEL_FR `NT_LABEL_FR`, nt.LABEL_SP `NT_LABEL_SP`, nt.LABEL_PR `NT_LABEL_PR`, "
                + "          u.UNIT_ID `U_UNIT_ID`, u.UNIT_CODE `U_UNIT_CODE`, u.LABEL_ID `U_LABEL_ID`, u.LABEL_EN `U_LABEL_EN`, u.LABEL_FR `U_LABEL_FR`, u.LABEL_SP `U_LABEL_SP`, u.LABEL_PR `U_LABEL_PR`, "
                + "          ttnd.`SCENARIO_ID`, ttnd.NODE_DATA_ID, ttnd.MONTH, ttnd.DATA_VALUE, ttnd.NOTES, ttnd.MANUAL_CHANGES_EFFECT_FUTURE, ttnd.IS_EXTRAPOLATION, "
                + "          ttndf.NODE_DATA_FU_ID, ttndf.LAG_IN_MONTHS, ttndf.NO_OF_PERSONS, ttndf.FORECASTING_UNITS_PER_PERSON, "
                + "          fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, "
                + "          fuu.UNIT_ID `FUU_UNIT_ID`, fuu.UNIT_CODE `FUU_UNIT_CODE`, fuu.LABEL_ID `FUU_LABEL_ID`, fuu.LABEL_EN `FUU_LABEL_EN`, fuu.LABEL_FR `FUU_LABEL_FR`, fuu.LABEL_SP `FUU_LABEL_SP`, fuu.LABEL_PR `FUU_LABEL_PR`, "
                + "          tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TC_LABEL_ID`, tc.LABEL_EN `TC_LABEL_EN`, tc.LABEL_FR `TC_LABEL_FR`, tc.LABEL_SP `TC_LABEL_SP`, tc.LABEL_PR `TC_LABEL_PR`, "
                + "          pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, "
                + "          ut.USAGE_TYPE_ID, ut.LABEL_ID `UT_LABEL_ID`, ut.LABEL_EN `UT_LABEL_EN`, ut.LABEL_FR `UT_LABEL_FR`, ut.LABEL_SP `UT_LABEL_SP`, ut.LABEL_PR `UT_LABEL_PR`, "
                + "          ttndf.ONE_TIME_USAGE, ttndf.ONE_TIME_DISPENSING, ttndf.USAGE_FREQUENCY, upf.USAGE_PERIOD_ID `UPF_USAGE_PERIOD_ID`, upf.CONVERT_TO_MONTH `UPF_CONVERT_TO_MONTH`, upf.LABEL_ID `UPF_LABEL_ID`, upf.LABEL_EN `UPF_LABEL_EN`, upf.LABEL_FR `UPF_LABEL_FR`, upf.LABEL_SP `UPF_LABEL_SP`, upf.LABEL_PR `UPF_LABEL_PR`, "
                + "          ttndf.REPEAT_COUNT, upr.USAGE_PERIOD_ID `UPR_USAGE_PERIOD_ID`, upr.CONVERT_TO_MONTH `UPR_CONVERT_TO_MONTH`, upr.LABEL_ID `UPR_LABEL_ID`, upr.LABEL_EN `UPR_LABEL_EN`, upr.LABEL_FR `UPR_LABEL_FR`, upr.LABEL_SP `UPR_LABEL_SP`, upr.LABEL_PR `UPR_LABEL_PR`, "
                + "          ttndp.NODE_DATA_PU_ID, ttndp.REFILL_MONTHS, ttndp.PU_PER_VISIT, ttndp.SHARE_PLANNING_UNIT, "
                + "          pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER `PU_MULTIPLIER`, "
                + "          puu.UNIT_ID `PUU_UNIT_ID`, puu.UNIT_CODE `PUU_UNIT_CODE`, puu.LABEL_ID `PUU_LABEL_ID`, puu.LABEL_EN `PUU_LABEL_EN`, puu.LABEL_FR `PUU_LABEL_FR`, puu.LABEL_SP `PUU_LABEL_SP`, puu.LABEL_PR `PUU_LABEL_PR` "
                + "      FROM vw_forecast_tree_node ttn "
                + "      LEFT JOIN vw_node_type nt ON ttn.NODE_TYPE_ID=nt.NODE_TYPE_ID "
                + "      LEFT JOIN vw_unit u ON ttn.UNIT_ID=u.UNIT_ID "
                + "      LEFT JOIN rm_forecast_tree_node_data ttnd ON ttn.NODE_ID=ttnd.NODE_ID "
                + "      LEFT JOIN rm_forecast_tree_node_data_fu ttndf on ttndf.NODE_DATA_FU_ID=ttnd.NODE_DATA_FU_ID "
                + "      LEFT JOIN vw_forecasting_unit fu ON ttndf.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "      LEFT JOIN vw_unit fuu ON fu.UNIT_ID=fuu.UNIT_ID "
                + "      LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID "
                + "      LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "      LEFT JOIN vw_usage_type ut ON ttndf.USAGE_TYPE_ID=ut.USAGE_TYPE_ID "
                + "      LEFT JOIN vw_usage_period upf ON ttndf.USAGE_FREQUENCY_USAGE_PERIOD_ID=upf.USAGE_PERIOD_ID "
                + "      LEFT JOIN vw_usage_period upr ON ttndf.REPEAT_USAGE_PERIOD_ID=upr.USAGE_PERIOD_ID "
                + "      LEFT JOIN rm_forecast_tree_node_data_pu ttndp ON ttndp.NODE_DATA_PU_ID=ttnd.NODE_DATA_PU_ID "
                + "      LEFT JOIN vw_planning_unit pu ON ttndp.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "      LEFT JOIN vw_unit puu ON pu.UNIT_ID=puu.UNIT_ID "
                + "      WHERE ttn.TREE_ID=? "
                + "      ORDER BY ttn.SORT_ORDER, ttnd.NODE_DATA_ID";
//        Map<String, Object> params = new HashMap<>();
//        params.put("treeId", treeId);
        return this.jdbcTemplate.query(sql, new TreeNodeResultSetExtractor(false), treeId);
    }

    @Override
    public List<ForecastActualConsumption> getForecastActualConsumptionData(int programId, int versionId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        StringBuilder sqlBuilder = new StringBuilder("SELECT "
                + "    fac.ACTUAL_CONSUMPTION_ID, fac.MONTH, fac.AMOUNT, fac.REPORTING_RATE, fac.DAYS_OF_STOCK_OUT, fac.ADJUSTED_AMOUNT, fac.PU_AMOUNT, fac.VERSION_ID, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, fac.CREATED_DATE, "
                + "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, "
                + "    fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`, pu.MULTIPLIER, "
                + "    pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`, "
                + "    r.REGION_ID, r.LABEL_ID `REG_LABEL_ID`, r.LABEL_EN `REG_LABEL_EN`, r.LABEL_FR `REG_LABEL_FR`, r.LABEL_SP `REG_LABEL_SP`, r.LABEL_PR `REG_LABEL_PR` "
                + "FROM rm_forecast_actual_consumption fac "
                + "LEFT JOIN vw_dataset p ON fac.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN vw_planning_unit pu ON fac.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_forecasting_unit fu on pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN vw_region r ON fac.REGION_ID=r.REGION_ID "
                + "LEFT JOIN us_user cb ON fac.CREATED_BY=cb.USER_ID "
                + "WHERE fac.PROGRAM_ID=:programId AND fac.VERSION_ID=:versionId ");
        this.aclService.addFullAclForProgram(sqlBuilder, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sqlBuilder.toString(), params, new ForecastActualConsumptionRowMapper());
    }

    @Override
    public List<ForecastConsumptionExtrapolation> getForecastConsumptionExtrapolation(int programId, int versionId, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT "
                + "     fce.CONSUMPTION_EXTRAPOLATION_ID, "
                + "     pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, "
                + "     r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`, "
                + "     em.EXTRAPOLATION_METHOD_ID, em.LABEL_ID `EM_LABEL_ID`, em.LABEL_EN `EM_LABEL_EN`, em.LABEL_FR `EM_LABEL_FR`, em.LABEL_SP `EM_LABEL_SP`, em.LABEL_PR `EM_LABEL_PR`, "
                + "     fce.JSON_PROPERTIES, fce.NOTES, fce.CREATED_DATE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, fced.MONTH, fced.AMOUNT, fced.CI "
                + "FROM rm_forecast_consumption_extrapolation fce "
                + "LEFT JOIN vw_dataset p ON fce.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN vw_planning_unit pu ON fce.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_region r ON fce.REGION_ID=r.REGION_ID "
                + "LEFT JOIN vw_extrapolation_method em ON fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID "
                + "LEFT JOIN us_user cb ON fce.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID "
                + "WHERE fce.PROGRAM_ID=:programId and fce.VERSION_ID=:versionId");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ForecastConsumptionExtrapolationListResultSetExtractor());
    }

    @Override
    public List<ActualConsumptionDataOutput> getActualConsumptionDataInput(ActualConsumptionData acd, Date startDate, Date stopDate, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("programId", acd.getProgramId());
        params.put("versionId", acd.getVersionId());
        params.put("startDate", startDate);
        params.put("stopDate", stopDate);
        params.put("planningUnitListString", acd.getPlanningUnitIdString());
        params.put("regionListString", acd.getRegionIdString());
        return this.namedParameterJdbcTemplate.query("CALL getSupplyPlanActualConsumption(:programId, :versionId, :planningUnitListString, :regionListString, :startDate, :stopDate)", params, new ActualConsumptionDataOutputRowMapper());
    }

    @Override
    public int addSupplyPlanCommitRequest(CommitRequest spcr, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("ct_commit_request").usingGeneratedKeyColumns("ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("PROGRAM_ID", spcr.getProgram().getId());
        params.put("COMMITTED_VERSION_ID", spcr.getCommittedVersionId());
        params.put("VERSION_TYPE_ID", spcr.getVersionType() != null ? spcr.getVersionType().getId() : null);
        params.put("NOTES", spcr.getNotes());
        params.put("SAVE_DATA", spcr.isSaveData());
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("STATUS", 1); // New request
        int commitRequestId = si.executeAndReturnKey(params).intValue();
        return commitRequestId;
    }

    @Override
    public List<ProgramVersion> getDatasetVersionList(DatasetVersionListInput datasetVersionListInput, CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder("SELECT  "
                + "     pv.PROGRAM_VERSION_ID, pv.VERSION_ID, pv.NOTES, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, pv.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pv.LAST_MODIFIED_DATE, "
                + "     p.PROGRAM_ID, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, p.PROGRAM_CODE, "
                + "     rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, "
                + "     ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR`, "
                + "     o.ORGANISATION_ID, o.ORGANISATION_CODE, o.LABEL_ID `ORGANISATION_LABEL_ID`, o.LABEL_EN `ORGANISATION_LABEL_EN`, o.LABEL_FR `ORGANISATION_LABEL_FR`, o.LABEL_SP `ORGANISATION_LABEL_SP`, o.LABEL_PR `ORGANISATION_LABEL_PR`, "
                + "     vt.VERSION_TYPE_ID, vt.LABEL_ID `VERSION_TYPE_LABEL_ID`, vt.LABEL_EN `VERSION_TYPE_LABEL_EN`, vt.LABEL_FR `VERSION_TYPE_LABEL_FR`, vt.LABEL_SP `VERSION_TYPE_LABEL_SP`, vt.LABEL_PR `VERSION_TYPE_LABEL_PR`, "
                + "     vs.VERSION_STATUS_ID, vs.LABEL_ID `VERSION_STATUS_LABEL_ID`, vs.LABEL_EN `VERSION_STATUS_LABEL_EN`, vs.LABEL_FR `VERSION_STATUS_LABEL_FR`, vs.LABEL_SP `VERSION_STATUS_LABEL_SP`, vs.LABEL_PR `VERSION_STATUS_LABEL_PR`,"
                + "     pv.FORECAST_START_DATE, pv.FORECAST_STOP_DATE, pv.`DAYS_IN_MONTH`, pv.`FREIGHT_PERC`, pv.`FORECAST_THRESHOLD_HIGH_PERC`, pv.`FORECAST_THRESHOLD_LOW_PERC` "
                + "FROM rm_program_version pv  "
                + "LEFT JOIN vw_dataset p ON pv.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN vw_health_area ha ON FIND_IN_SET(ha.HEALTH_AREA_ID, p.HEALTH_AREA_ID) "
                + "LEFT JOIN vw_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
                + "LEFT JOIN us_user cb ON pv.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON pv.LAST_MODIFIED_BY=lmb.USER_ID "
                + "LEFT JOIN vw_version_type vt ON pv.VERSION_TYPE_ID=vt.VERSION_TYPE_ID "
                + "LEFT JOIN vw_version_status vs ON pv.VERSION_STATUS_ID=vs.VERSION_STATUS_ID "
                + "WHERE TRUE "
                + "AND (FIND_IN_SET(pv.PROGRAM_ID, :programIds)) "
                + "AND (pv.VERSION_TYPE_ID=:versionTypeId OR :versionTypeId=-1) "
                + "AND (pv.CREATED_DATE BETWEEN :startDate AND :stopDate) "
                + "AND p.PROGRAM_TYPE_ID=" + GlobalConstants.PROGRAM_TYPE_DATASET);
        Map<String, Object> params = new HashMap<>();
        params.put("programIds", String.join(",", datasetVersionListInput.getProgramIds()));
        params.put("versionTypeId", datasetVersionListInput.getVersionTypeId());
        params.put("startDate", datasetVersionListInput.getStartDate());
        params.put("stopDate", datasetVersionListInput.getStopDate());
        this.aclService.addFullAclForProgram(sb, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new ProgramVersionResultSetExtractor());
    }

    @Override
    public List<NodeDataModeling> getModelingDataForNodeDataId(int nodeDataId, boolean isTemplate) {
        String sql = "";
        if (isTemplate) {
            sql = "SELECT "
                    + "     ttndm.`NODE_DATA_MODELING_ID`, ttndm.`DATA_VALUE` `MODELING_DATA_VALUE`, ttndm.`INCREASE_DECREASE`, ttndm.`START_DATE` `MODELING_START_DATE`, ttndm.`STOP_DATE` `MODELING_STOP_DATE`, ttndm.`NOTES` `MODELING_NOTES`, ttndm.`MODELING_SOURCE`, ttndm.`TRANSFER_NODE_DATA_ID` `MODELING_TRANSFER_NODE_DATA_ID`, "
                    + "     mt.`MODELING_TYPE_ID`, mt.`LABEL_ID` `MODELING_TYPE_LABEL_ID`, mt.`LABEL_EN` `MODELING_TYPE_LABEL_EN`, mt.`LABEL_FR` `MODELING_TYPE_LABEL_FR`, mt.`LABEL_SP` `MODELING_TYPE_LABEL_SP`, mt.`LABEL_PR` `MODELING_TYPE_LABEL_PR` "
                    //                    + "     ttndmc.`NODE_DATA_MODELING_CALCULATOR_ID`, ttndm.`CALCULATOR_FIRST_MONTH`, ttndm.`CALCULATOR_YEARS_OF_TARGET`, ttndmc.`ACTUAL_OR_TARGET_VALUE` "
                    + "FROM rm_tree_template_node_data_modeling ttndm "
                    //                    + "LEFT JOIN rm_tree_template_node_data_modeling_calculator ttndmc ON ttndm.`NODE_DATA_MODELING_ID`=ttndmc.`NODE_DATA_MODELING_ID` "
                    + "LEFT JOIN vw_modeling_type mt ON ttndm.MODELING_TYPE_ID=mt.MODELING_TYPE_ID "
                    + "WHERE ttndm.NODE_DATA_ID = ?";
        } else {
            sql = "SELECT "
                    + "     ttndm.`NODE_DATA_MODELING_ID`, ttndm.`DATA_VALUE` `MODELING_DATA_VALUE`, ttndm.`INCREASE_DECREASE`, ttndm.`START_DATE` `MODELING_START_DATE`, ttndm.`STOP_DATE` `MODELING_STOP_DATE`, ttndm.`NOTES` `MODELING_NOTES`, ttndm.`MODELING_SOURCE`, ttndm.`TRANSFER_NODE_DATA_ID` `MODELING_TRANSFER_NODE_DATA_ID`, "
                    + "     mt.`MODELING_TYPE_ID`, mt.`LABEL_ID` `MODELING_TYPE_LABEL_ID`, mt.`LABEL_EN` `MODELING_TYPE_LABEL_EN`, mt.`LABEL_FR` `MODELING_TYPE_LABEL_FR`, mt.`LABEL_SP` `MODELING_TYPE_LABEL_SP`, mt.`LABEL_PR` `MODELING_TYPE_LABEL_PR` "
                    //                    + "     ttndmc.`NODE_DATA_MODELING_CALCULATOR_ID`, ttndm.`CALCULATOR_FIRST_MONTH`, ttndm.`CALCULATOR_YEARS_OF_TARGET`, ttndmc.`ACTUAL_OR_TARGET_VALUE` "
                    + "FROM rm_forecast_tree_node_data_modeling ttndm "
                    //                    + "LEFT JOIN rm_forecast_tree_node_data_modeling_calculator ftndmc ON ttndm.`NODE_DATA_MODELING_ID`=ttndmc.`NODE_DATA_MODELING_ID` "
                    + "LEFT JOIN vw_modeling_type mt ON ttndm.MODELING_TYPE_ID=mt.MODELING_TYPE_ID "
                    + "WHERE ttndm.NODE_DATA_ID = ?";
        }
        return this.jdbcTemplate.query(sql, new NodeDataModelingListRowMapper(isTemplate), nodeDataId);
    }

    @Override
    public AnnualTargetCalculator getAnnualTargetCalculatorForNodeDataId(int nodeDataId, boolean isTemplate) {
        String sql = "";
        if (isTemplate) {
            sql = "SELECT "
                    + " atc.NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID, atc.CALCULATOR_FIRST_MONTH, atc.CALCULATOR_YEARS_OF_TARGET, atcd.ACTUAL_OR_TARGET_VALUE "
                    + "FROM rm_tree_template_node_data_annual_target_calculator atc "
                    + "LEFT JOIN rm_tree_template_node_data_annual_target_calculator_data atcd ON atc.`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`=atcd.`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` "
                    + "WHERE atc.NODE_DATA_ID = ?";
        } else {
            sql = "SELECT "
                    + " atc.NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID, atc.CALCULATOR_FIRST_MONTH, atc.CALCULATOR_YEARS_OF_TARGET, atcd.ACTUAL_OR_TARGET_VALUE "
                    + "FROM rm_forecast_tree_node_data_annual_target_calculator atc "
                    + "LEFT JOIN rm_forecast_tree_node_data_annual_target_calculator_data atcd ON atc.`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID`=atcd.`NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID` "
                    + "WHERE atc.NODE_DATA_ID = ?";
        }
        return this.jdbcTemplate.query(sql, new AnnualTargetCalculatorResultSetExtractor(), nodeDataId);

    }

    @Override
    public List<NodeDataMom> getMomDataForNodeDataId(int nodeDataId) {
        String sql = "SELECT "
                + "	ndm.NODE_DATA_MOM_ID, ndm.MONTH `NDM_MONTH`, ndm.START_VALUE `NDM_START_VALUE`, ndm.END_VALUE `NDM_END_VALUE`, ndm.CALCULATED_VALUE `NDM_CALCULATED_VALUE`, ndm.CALCULATED_MMD_VALUE `NDM_CALCULATED_MMD_VALUE`, ndm.DIFFERENCE `NDM_DIFFERENCE`, ndm.SEASONALITY_PERC `NDM_SEASONALITY_PERC`, ndm.MANUAL_CHANGE `NDM_MANUAL_CHANGE` "
                + "FROM rm_forecast_tree_node_data_mom ndm "
                + "WHERE ndm.NODE_DATA_ID=?";
        return this.jdbcTemplate.query(sql, new NodeDataMomRowMapper(), nodeDataId);
    }

    @Override
    public List<NodeDataOverride> getOverrideDataForNodeDataId(int nodeDataId, boolean isTemplate) {
        String sql = "";
        if (isTemplate) {
            sql = "SELECT "
                    + "ttndo.`NODE_DATA_OVERRIDE_ID`, ttndo.`MONTH_NO` `OVERRIDE_MONTH_NO`, date_add(CONCAT(LEFT(NOW(),7),'-01'), INTERVAL ttndo.`MONTH_NO` MONTH) `OVERRIDE_MONTH`, ttndo.`MANUAL_CHANGE` `OVERRIDE_MANUAL_CHANGE`, ttndo.`SEASONALITY_PERC` `OVERRIDE_SEASONALITY_PERC` "
                    + "FROM rm_tree_template_node_data_override ttndo "
                    + "WHERE ttndo.NODE_DATA_ID=?";
        } else {
            sql = "SELECT ndo.`NODE_DATA_OVERRIDE_ID`, ndo.`MONTH` `OVERRIDE_MONTH`, ndo.`MANUAL_CHANGE` `OVERRIDE_MANUAL_CHANGE`, ndo.`SEASONALITY_PERC` `OVERRIDE_SEASONALITY_PERC` FROM rm_forecast_tree_node_data_override ndo WHERE ndo.NODE_DATA_ID=?";
        }
        return this.jdbcTemplate.query(sql, new NodeDataOverrideRowMapper(isTemplate), nodeDataId);
    }

    @Override
    public NodeDataExtrapolation getNodeDataExtrapolationForNodeDataId(int nodeDataId) {
        String sql = "SELECT "
                + "     nde.NODE_DATA_EXTRAPOLATION_ID, em.EXTRAPOLATION_METHOD_ID, em.LABEL_ID `EM_LABEL_ID`, em.LABEL_EN `EM_LABEL_EN`, em.LABEL_FR `EM_LABEL_FR`, em.LABEL_SP `EM_LABEL_SP`, em.LABEL_PR `EM_LABEL_PR`, nde.`NOTES` `EM_NOTES`, nde.`START_DATE` `EXTRAPOLATION_START_DATE`, nde.`STOP_DATE` `EXTRAPOLATION_STOP_DATE`, "
                + "     nded.NODE_DATA_EXTRAPOLATION_DATA_ID, nded.MONTH `EM_MONTH`, nded.AMOUNT `EM_AMOUNT`, nded.REPORTING_RATE `EM_REPORTING_RATE`, nded.MANUAL_CHANGE `EM_MANUAL_CHANGE` "
                + "FROM rm_forecast_tree_node_data_extrapolation nde "
                + "LEFT JOIN rm_forecast_tree_node_data_extrapolation_data nded ON nde.NODE_DATA_EXTRAPOLATION_ID=nded.NODE_DATA_EXTRAPOLATION_ID "
                + "LEFT JOIN vw_extrapolation_method em ON nde.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID "
                + "WHERE nde.NODE_DATA_ID=?";
        return this.jdbcTemplate.query(sql, new NodeDataExtrapolationResultSetExtractor(), nodeDataId);
    }

    @Override
    public List<NodeDataExtrapolationOption> getNodeDataExtrapolationOptionForNodeDataId(int nodeDataId) {
        String sql = "SELECT "
                + "     ndeo.NODE_DATA_EXTRAPOLATION_OPTION_ID, eo.EXTRAPOLATION_METHOD_ID `EO_EXTRAPOLATION_METHOD_ID`, eo.LABEL_ID `EO_LABEL_ID`, eo.LABEL_EN `EO_LABEL_EN`, eo.LABEL_FR `EO_LABEL_FR`, eo.LABEL_SP `EO_LABEL_SP`, eo.LABEL_PR `EO_LABEL_PR`, ndeo.JSON_PROPERTIES "
                //                + "     ndeod.NODE_DATA_EXTRAPOLATION_OPTION_DATA_ID, ndeod.MONTH `EO_MONTH`, ndeod.AMOUNT `EO_AMOUNT`, ndeod.CI `EO_CI` "
                + "FROM rm_forecast_tree_node_data_extrapolation_option ndeo "
                //                + "LEFT JOIN rm_forecast_tree_node_data_extrapolation_option_data ndeod ON ndeo.NODE_DATA_EXTRAPOLATION_OPTION_ID=ndeod.NODE_DATA_EXTRAPOLATION_OPTION_ID "
                + "LEFT JOIN vw_extrapolation_method eo ON ndeo.EXTRAPOLATION_METHOD_ID=eo.EXTRAPOLATION_METHOD_ID "
                + "WHERE ndeo.NODE_DATA_ID=?";
        return this.jdbcTemplate.query(sql, new NodeDataExtrapolationOptionResultSetExtractor(), nodeDataId);
    }

    private Double getFreightPerc(int programId, int planningUnitId, int procurementAgentId, String shipmentMode) {
        String sqlFreight = "SELECT "
                + "    COALESCE( "
                + "        CASE "
                + "            WHEN :shipmentMode='Air' THEN ppupa1.`AIR_FREIGHT_PERC` "
                + "            WHEN :shipmentMode='Sea' THEN ppupa1.`SEA_FREIGHT_PERC` "
                + "            WHEN :shipmentMode='Road' THEN ppupa1.`ROAD_FREIGHT_PERC` "
                + "            ELSE NULL "
                + "        END, "
                + "        CASE "
                + "            WHEN :shipmentMode='Air' THEN ppupa2.`AIR_FREIGHT_PERC` "
                + "            WHEN :shipmentMode='Sea' THEN ppupa2.`SEA_FREIGHT_PERC` "
                + "            WHEN :shipmentMode='Road' THEN ppupa2.`ROAD_FREIGHT_PERC` "
                + "            ELSE NULL "
                + "        END, "
                + "        CASE "
                + "            WHEN :shipmentMode='Air' THEN p.`AIR_FREIGHT_PERC` "
                + "            WHEN :shipmentMode='Sea' THEN p.`SEA_FREIGHT_PERC` "
                + "            WHEN :shipmentMode='Road' THEN p.`ROAD_FREIGHT_PERC` "
                + "            ELSE NULL "
                + "        END"
                + "    ) `FREIGHT_PERC`"
                + "FROM vw_program p "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=:planningUnitId "
                + "LEFT JOIN rm_program_planning_unit_procurement_agent ppupa1 ON ppu.PROGRAM_ID=ppupa1.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=ppupa1.PLANNING_UNIT_ID AND ppupa1.PROCUREMENT_AGENT_ID=:procurementAgentId "
                + "LEFT JOIN rm_program_planning_unit_procurement_agent ppupa2 ON ppu.PROGRAM_ID=ppupa2.PROGRAM_ID AND ppupa2.PLANNING_UNIT_ID IS NULL AND ppupa2.PROCUREMENT_AGENT_ID=:procurementAgentId "
                + "WHERE p.PROGRAM_ID=:programId";
        Map<String, Object> freightParams = new HashMap<>();
        freightParams.put("programId", programId);
        freightParams.put("planningUnitId", planningUnitId);
        freightParams.put("procurementAgentId", procurementAgentId);
        freightParams.put("shipmentMode", shipmentMode);
        return this.namedParameterJdbcTemplate.queryForObject(sqlFreight, freightParams, Double.class);
    }

}
