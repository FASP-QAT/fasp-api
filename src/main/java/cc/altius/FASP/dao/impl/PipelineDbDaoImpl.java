/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.OrganisationDao;
import cc.altius.FASP.dao.RealmCountryDao;
import cc.altius.FASP.dao.PipelineDbDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.pipeline.QatTempProgram;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.pipeline.Pipeline;
import cc.altius.FASP.model.pipeline.PplConsumption;
import cc.altius.FASP.model.pipeline.PplProduct;
import cc.altius.FASP.model.pipeline.PplPrograminfo;
import cc.altius.FASP.model.pipeline.rowMapper.PipelineProductRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempProgramResultSetExtractor;
import cc.altius.FASP.model.pipeline.QatTempConsumption;
import cc.altius.FASP.model.pipeline.QatTempInventory;
import cc.altius.FASP.model.pipeline.QatTempPlanningUnitInventoryCount;
import cc.altius.FASP.model.pipeline.QatTempProgramPlanningUnit;
import cc.altius.FASP.model.pipeline.QatTempShipment;
import cc.altius.FASP.model.pipeline.rowMapper.PipelineConsumptionRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.PplPrograminfoRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatInventoryRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatTemRegionRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempConsumptionRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempPlanningUnitInventoryCountMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempPlanningUnitRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempShipmentRowMapper;
import cc.altius.FASP.model.rowMapper.VersionRowMapper;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.utils.DateUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cc.altius.FASP.model.pipeline.QatTempDataSource;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempDataSourceRowMapper;
import cc.altius.FASP.model.pipeline.QatTempFundingSource;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempFundingSourceRowMapper;
import cc.altius.FASP.model.pipeline.QatTempProcurementAgent;
import cc.altius.FASP.model.pipeline.rowMapper.QatTemHealthAreaRowMapper;
import cc.altius.FASP.model.pipeline.rowMapper.QatTempProcurementAgentRowMapper;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author akil
 */
@Repository
public class PipelineDbDaoImpl implements PipelineDbDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    static int programExistCount;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private RealmCountryService realmCountryService;

    @Autowired
    private ProgramDataDaoImpl ProgramDataDaoImpl;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private HealthAreaDao healthAreaDao;
    @Autowired
    private OrganisationDao organisationDao;
    @Autowired
    private RealmCountryDao realmCountryDao;

    public String sqlListString = "SELECT  "
            + "      p.PROGRAM_CODE,p.ARRIVED_TO_DELIVERED_LEAD_TIME,p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME,p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME,"
            + "     p.PROGRAM_ID, p.AIR_FREIGHT_PERC, p.SEA_FREIGHT_PERC, p.PLANNED_TO_SUBMITTED_LEAD_TIME,"// p.DRAFT_TO_SUBMITTED_LEAD_TIME,"
            + "     p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.DELIVERED_TO_RECEIVED_LEAD_TIME, p.MONTHS_IN_PAST_FOR_AMC, p.MONTHS_IN_FUTURE_FOR_AMC,p.SHELF_LIFE, "
            + "     p.PROGRAM_NOTES, pm.USERNAME `PROGRAM_MANAGER_USERNAME`, pm.USER_ID `PROGRAM_MANAGER_USER_ID`, "
            + "     pl.LABEL_ID, pl.LABEL_EN, pl.LABEL_FR, pl.LABEL_PR, pl.LABEL_SP, "
            //<<<<<<< HEAD
            //            + "     rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, rc.AIR_FREIGHT_PERC `REALM_COUNTRY_AIR_FREIGHT_PERC`, "
            //            + "rc.SEA_FREIGHT_PERC `REALM_COUNTRY_SEA_FREIGHT_PERC`, "
            //            + "rc.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_AIR_LEAD_TIME`, "
            //            + "rc.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_SEA_LEAD_TIME`, rc.ARRIVED_TO_DELIVERED_LEAD_TIME `REALM_COUNTRY_ARRIVED_TO_DELIVERED_LEAD_TIME`, "
            //=======
            + "     rc.REALM_COUNTRY_ID, r.REALM_ID, r.REALM_CODE, rc.AIR_FREIGHT_PERC `REALM_COUNTRY_AIR_FREIGHT_PERC`, "
            + "rc.SEA_FREIGHT_PERC `REALM_COUNTRY_SEA_FREIGHT_PERC`,"
            + " rc.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_AIR_LEAD_TIME`, "
            + "rc.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME `REALM_COUNTRY_SHIPPED_TO_ARRIVED_SEA_LEAD_TIME`, rc.ARRIVED_TO_DELIVERED_LEAD_TIME `REALM_COUNTRY_ARRIVED_TO_DELIVERED_LEAD_TIME`, "
            //>>>>>>> dev
            + "     rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`, "
            + "     c.COUNTRY_ID, c.COUNTRY_CODE,  "
            + "     cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_PR `COUNTRY_LABEL_PR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, "
            + "     cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD,  "
            + "     cul.LABEL_ID `CURRENCY_LABEL_ID`, cul.LABEL_EN `CURRENCY_LABEL_EN`, cul.LABEL_FR `CURRENCY_LABEL_FR`, cul.LABEL_PR `CURRENCY_LABEL_PR`, cul.LABEL_SP `CURRENCY_LABEL_SP`, "
            + "     o.ORGANISATION_ID, o.ORGANISATION_CODE, "
            + "     ol.LABEL_ID `ORGANISATION_LABEL_ID`, ol.LABEL_EN `ORGANISATION_LABEL_EN`, ol.LABEL_FR `ORGANISATION_LABEL_FR`, ol.LABEL_PR `ORGANISATION_LABEL_PR`, ol.LABEL_SP `ORGANISATION_LABEL_SP`, "
            + "     ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, "
            + "     hal.LABEL_ID `HEALTH_AREA_LABEL_ID`, hal.LABEL_EN `HEALTH_AREA_LABEL_EN`, hal.LABEL_FR `HEALTH_AREA_LABEL_FR`, hal.LABEL_PR `HEALTH_AREA_LABEL_PR`, hal.LABEL_SP `HEALTH_AREA_LABEL_SP`, "
            + "     re.REGION_ID, "
            + "     rel.LABEL_ID `REGION_LABEL_ID`, rel.LABEL_EN `REGION_LABEL_EN`, rel.LABEL_FR `REGION_LABEL_FR`, rel.LABEL_PR `REGION_LABEL_PR`, rel.LABEL_SP `REGION_LABEL_SP`, "
            + "     u.UNIT_ID, u.UNIT_CODE, ul.LABEL_ID `UNIT_LABEL_ID`, ul.LABEL_EN `UNIT_LABEL_EN`, ul.LABEL_FR `UNIT_LABEL_FR`, ul.LABEL_PR `UNIT_LABEL_PR`, ul.LABEL_SP `UNIT_LABEL_SP`, "
            //            + "     pv.VERSION_ID, pv.CREATED_DATE `VERSION_CREATED_DATE`, "
            //            + "pvcmb.USER_ID `VERSION_USER_ID`, pvcmb.USERNAME `VERSION_USERNAME`, "
            //            + "     p.ACTIVE, "
            + "cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, p.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, p.LAST_MODIFIED_DATE "
            + " FROM qat_temp_program p  "
            + " LEFT JOIN qat_temp_ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
            + " LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
            + " LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID "
            + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + " LEFT JOIN ap_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID "
            + " LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
            + " LEFT JOIN rm_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
            + " LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
            //            + " LEFT JOIN rm_health_area ha ON p.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
            //            + " LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
            + " LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID "
            + " LEFT JOIN qat_temp_program_region pr ON p.PIPELINE_ID=pr.PIPELINE_ID "
            + " LEFT JOIN rm_region re ON pr.REGION_ID=re.REGION_ID "
            + " LEFT JOIN ap_label rel ON re.LABEL_ID=rel.LABEL_ID "
            + " LEFT JOIN qat_temp_program_healthArea ph ON p.PIPELINE_ID=ph.PIPELINE_ID "
            + " LEFT JOIN rm_health_area ha ON ha.HEALTH_AREA_ID=ph.HEALTH_AREA_ID "
            + " LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
            + " LEFT JOIN ap_unit u ON rc.PALLET_UNIT_ID=u.UNIT_ID "
            + " LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
            + " LEFT JOIN us_user cb ON p.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON p.LAST_MODIFIED_BY=lmb.USER_ID "
            //            + " LEFT JOIN rm_program_version cpv ON p.PROGRAM_ID=cpv.PROGRAM_ID AND p.CURRENT_VERSION_ID=cpv.VERSION_ID "
            //            + " LEFT JOIN us_user cpvcb ON cpv.CREATED_BY=cpvcb.USER_ID "
            //            + " LEFT JOIN rm_program_version pv ON p.PROGRAM_ID=pv.PROGRAM_ID "
            //            + " LEFT JOIN us_user pvcmb ON pv.CREATED_BY=pvcmb.USER_ID "
            + " WHERE TRUE ";

    @Override
    @Transactional
    public int savePipelineDbData(Pipeline pipeline, CustomUserDetails curUser, String fileName) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        // Save records for adb_pipeline
        if (pipeline.getPrograminfo() != null) {
            pipeline.getPrograminfo().forEach((c) -> {
                String programName = c.getProgramname();
                if (programName != "" && programName != null) {
                    programExistCount = this.jdbcTemplate.queryForObject("SELECT count(*) FROM fasp.adb_programinfo pi  "
                            + "left join adb_pipeline ap on ap.PIPELINE_ID=pi.PIPELINE_ID "
                            + "where pi.ProgramName like '%" + programName + "%'", Integer.class);
                } else {
                    programExistCount = 0;
                }

            });
        }
//        System.out.println("=======>" + programExistCount);
        if (programExistCount > 0) {
            return 0;
        } else {
            SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("adb_pipeline").usingGeneratedKeyColumns("PIPELINE_ID");
            Map<String, Object> params = new HashMap<>();
            params.put("FILE_NAME", fileName);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("STATUS", 0);
            int pipelineId = si.executeAndReturnKeyHolder(params).getKey().intValue();

            // Save records for adb_commodityprice
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_commodityprice");
            final List<SqlParameterSource> batchParams = new ArrayList<>();
            if (pipeline.getCommodityprice() != null) {
                pipeline.getCommodityprice().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProductID", c.getProductid());
                    tp.put("SupplierID", c.getSupplierid());
                    tp.put("dtmEffective", c.getDtmeffective());
                    tp.put("UnitPrice", c.getUnitprice());
                    tp.put("dtmChanged", c.getDtmchanged());
                    tp.put("User", c.getUser());
                    tp.put("Note", c.getNote());
                    tp.put("fUserDefined", c.getFuserdefined());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_consumption
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_consumption");
            batchParams.clear();
            if (pipeline.getConsumption() != null) {
                pipeline.getConsumption().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProductID", c.getProductid());
                    tp.put("ConsStartYear", c.getConsstartyear());
                    tp.put("ConsStartMonth", c.getConsstartmonth());
                    tp.put("ConsActualFlag", c.getConsactualflag());
                    tp.put("ConsNumMonths", c.getConsnummonths());
                    tp.put("ConsAmount", c.getConsamount());
                    tp.put("ConsDataSourceID", c.getConsdatasourceid());
                    tp.put("ConsIflator", c.getConsiflator());
                    tp.put("ConsNote", c.getConsnote());
                    tp.put("ConsDateChanged", c.getConsdatechanged());
                    tp.put("ConsID", c.getConsid());
                    tp.put("ConsDisplayNote", c.getConsdisplaynote());
                    tp.put("Old consumption", c.getOld_consumption());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_datasource
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_datasource");
            batchParams.clear();
            if (pipeline.getDatasource() != null) {
                pipeline.getDatasource().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("DataSourceID", c.getDatasourceid());
                    tp.put("DataSourceName", c.getDatasourcename());
                    tp.put("DataSourceTypeID", c.getDatasourcetypeid());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }
            // Save records for adb_fundingsource
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_fundingsource");
            batchParams.clear();
            if (pipeline.getFundingsource() != null) {
                pipeline.getFundingsource().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("FundingSourceID", c.getFundingsourceid());
                    tp.put("FundingSourceName", c.getFundingsourcename());
                    tp.put("FundingNote", c.getFundingnote());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_inventory
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_inventory");
            batchParams.clear();
            if (pipeline.getInventory() != null) {
                pipeline.getInventory().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProductID", c.getProductid());
                    tp.put("Period", c.getPeriod());
                    tp.put("InvAmount", c.getInvamount());
                    tp.put("InvTransferFlag", c.getInvtransferflag());
                    tp.put("InvNote", c.getInvnote());
                    tp.put("InvDateChanged", c.getInvdatechanged());
                    tp.put("ctrIndex", c.getCtrindex());
                    tp.put("InvDisplayNote", c.getInvdisplaynote());
                    tp.put("InvDataSourceID", c.getInvdatasourceid());
                    tp.put("fImported", c.getFimported());
                    tp.put("Old_Inventory", c.getOld_inventory());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_method
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_method");
            batchParams.clear();
            if (pipeline.getMethod() != null) {
                pipeline.getMethod().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("MethodID", c.getMethodid());
                    tp.put("MethodName", c.getMethodname());
                    tp.put("CYPFactor", c.getCypfactor());
                    tp.put("MethodNote", c.getMethodnote());
                    tp.put("ParentID", c.getParentid());
                    tp.put("CategoryID", c.getCategoryid());
                    tp.put("fRollup", c.getFrollup());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_monthlystockarchive
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_monthlystockarchive");
            batchParams.clear();
            if (pipeline.getMonthlystockarchive() != null) {
                pipeline.getMonthlystockarchive().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProductID", c.getProductid());
                    tp.put("EOYBalance", c.getEoybalance());
                    tp.put("StockYear", c.getStockyear());
                    tp.put("StockMonth", c.getStockmonth());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }
            // Save records for adb_paste_errors
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_paste_errors");
            batchParams.clear();
            if (pipeline.getPaste_errors() != null) {
                pipeline.getPaste_errors().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("F1", c.getF1());
                    tp.put("F2", c.getF2());
                    tp.put("F3", c.getF3());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_product
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_product");
            batchParams.clear();
            if (pipeline.getProduct() != null) {
                pipeline.getProduct().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProductID", c.getProductid());
                    tp.put("ProductName", c.getProductname());
                    tp.put("ProductMinMonths", c.getProductminmonths());
                    tp.put("ProductMaxMonths", c.getProductmaxmonths());
                    tp.put("SupplierID", c.getSupplierid());
                    tp.put("MethodID", c.getMethodid());
                    tp.put("ProductActiveFlag", c.getProductactiveflag());
                    tp.put("ProductActiveDate", c.getProductactivedate());
                    tp.put("DefaultCaseSize", c.getDefaultcasesize());
                    tp.put("ProductNote", c.getProductnote());
                    tp.put("ProdCMax", c.getProdcmax());
                    tp.put("ProdCMin", c.getProdcmin());
                    tp.put("ProdDesStock", c.getProddesstock());
                    tp.put("txtInnovatorDrugName", c.getTxtinnovatordrugname());
                    tp.put("dblLowestUnitQty", c.getDbllowestunitqty());
                    tp.put("txtLowestUnitMeasure", c.getTxtlowestunitmeasure());
                    tp.put("txtSubstitutionList", c.getTxtsubstitutionlist());
                    tp.put("fPermittedInCountry", c.getFpermittedincountry());
                    tp.put("memAvailabilityNotes", c.getMemavailabilitynotes());
                    tp.put("fAvailabilityStatus", c.getFavailabilitystatus());
                    tp.put("fUserDefined", c.getFuserdefined());
                    tp.put("strImportSource", c.getStrimportsource());
                    tp.put("BUConversion", c.getBuconversion());
                    tp.put("txtPreferenceNotes", c.getTxtpreferencenotes());
                    tp.put("lngAMCStart", c.getLngamcstart());
                    tp.put("lngAMCMonths", c.getLngamcmonths());
                    tp.put("fAMCChanged", c.getFamcchanged());
                    tp.put("txtMigrationStatus", c.getTxtmigrationstatus());
                    tp.put("txtMigrationStatusDate", c.getTxtmigrationstatusdate());
                    tp.put("strType", c.getStrtype());
                    tp.put("OldProductID", c.getOldproductid());
                    tp.put("OldProductName", c.getOldproductname());
                    tp.put("lngBatch", c.getLngbatch());
                    tp.put("OldMethodID", c.getOldmethodid());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_productfreightcost
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_productfreightcost");
            batchParams.clear();
            if (pipeline.getProductfreightcost() != null) {
                pipeline.getProductfreightcost().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProductID", c.getProductid());
                    tp.put("SupplierID", c.getSupplierid());
                    tp.put("FreightCost", c.getFreightcost());
                    tp.put("dtmChanged", c.getDtmchanged());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_productsuppliercasesize
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_productsuppliercasesize");
            batchParams.clear();
            if (pipeline.getProductsuppliercasesize() != null) {
                pipeline.getProductsuppliercasesize().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProductID", c.getProductid());
                    tp.put("SupplierID", c.getSupplierid());
                    tp.put("dtmEffective", c.getDtmeffective());
                    tp.put("intCaseSize", c.getIntcasesize());
                    tp.put("dtmChanged", c.getDtmchanged());
                    tp.put("User", c.getUser());
                    tp.put("Note", c.getNote());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_programinfo
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_programinfo");
            batchParams.clear();
            if (pipeline.getPrograminfo() != null) {
                pipeline.getPrograminfo().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ProgramName", c.getProgramname());
                    tp.put("DataDirectory", c.getDatadirectory());
                    tp.put("Language", c.getLanguage());
                    tp.put("DefaultLeadTimePlan", c.getDefaultleadtimeplan());
                    tp.put("DefaultLeadTimeOrder", c.getDefaultleadtimeorder());
                    tp.put("DefaultLeadTimeShip", c.getDefaultleadtimeship());
                    tp.put("DefaultShipCost", c.getDefaultshipcost());
                    tp.put("ProgramContact", c.getProgramcontact());
                    tp.put("Telephone", c.getTelephone());
                    tp.put("Fax", c.getFax());
                    tp.put("Email", c.getEmail());
                    tp.put("CountryCode", c.getCountrycode());
                    tp.put("CountryName", c.getCountryname());
                    tp.put("IsCurrent", c.getIscurrent());
                    tp.put("Note", c.getNote());
                    tp.put("ProgramCode", c.getProgramcode());
                    tp.put("IsActive", c.getIsactive());
                    tp.put("StartSize", c.getStartsize());
                    tp.put("IsDefault", c.getIsdefault());
                    tp.put("ArchiveDate", c.getArchivedate());
                    tp.put("ArchiveYear", c.getArchiveyear());
                    tp.put("ArchiveInclude", c.getArchiveinclude());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_shipment
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_shipment");
            batchParams.clear();
            if (pipeline.getShipment() != null) {
                pipeline.getShipment().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("ShipmentID", c.getShipmentid());
                    tp.put("ProductID", c.getProductid());
                    tp.put("SupplierID", c.getSupplierid());
                    tp.put("ShipDataSourceID", c.getShipdatasourceid());
                    tp.put("ShipAmount", c.getShipamount());
                    tp.put("ShipPlannedDate", c.getShipplanneddate());
                    tp.put("ShipOrderedDate", c.getShipordereddate());
                    tp.put("ShipShippedDate", c.getShipshippeddate());
                    tp.put("ShipReceivedDate", c.getShipreceiveddate());
                    tp.put("ShipStatusCode", c.getShipstatuscode());
                    tp.put("ShipNote", c.getShipnote());
                    tp.put("ShipDateChanged", c.getShipdatechanged());
                    tp.put("ShipFreightCost", c.getShipfreightcost());
                    tp.put("ShipValue", c.getShipvalue());
                    tp.put("ShipCaseLot", c.getShipcaselot());
                    tp.put("ShipDisplayNote", c.getShipdisplaynote());
                    tp.put("ShipPO", c.getShippo());
                    tp.put("Old Shipment", c.getOld_shipment());
                    tp.put("ShipFundingSourceID", c.getShipfundingsourceid());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_source
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_source");
            batchParams.clear();
            if (pipeline.getSource() != null) {
                pipeline.getSource().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("SupplierID", c.getSupplierid());
                    tp.put("SupplierName", c.getSuppliername());
                    tp.put("SupplierLeadTimePlan", c.getSupplierleadtimeplan());
                    tp.put("SupplierLeadTimeOrder", c.getSupplierleadtimeorder());
                    tp.put("SupplierLeadTimeShip", c.getSupplierleadtimeship());
                    tp.put("SupplierNote", c.getSuppliernote());
                    tp.put("Freight", c.getFreight());
                    tp.put("DefaultSupplier", c.getDefaultsupplier());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_tblbe_version
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_tblbe_version");
            batchParams.clear();
            if (pipeline.getTblbe_version() != null) {
                pipeline.getTblbe_version().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("sBE_Version", c.getSbe_version());
                    tp.put("dtmUpdated", c.getDtmupdated());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_tblimportproducts
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_tblimportproducts");
            batchParams.clear();
            if (pipeline.getTblimportproducts() != null) {
                pipeline.getTblimportproducts().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("strProductID", c.getStrproductid());
                    tp.put("strName", c.getStrname());
                    tp.put("strDose", c.getStrdose());
                    tp.put("lngCYP", c.getLngcyp());
                    tp.put("dtmExport", c.getDtmexport());
                    tp.put("fProcessed", c.getFprocessed());
                    tp.put("lngID", c.getLngid());
                    tp.put("strSource", c.getStrsource());
                    tp.put("strMapping", c.getStrmapping());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }

            // Save records for adb_tblimportrecords
            si = null;
            si = new SimpleJdbcInsert(dataSource).withTableName("adb_tblimportrecords");
            batchParams.clear();
            if (pipeline.getTblimportrecords() != null) {
                pipeline.getTblimportrecords().forEach((c) -> {
                    Map<String, Object> tp = new HashMap<>();
                    tp.put("strProductID", c.getStrproductid());
                    tp.put("dtmPeriod", c.getDtmperiod());
                    tp.put("lngconsumption", c.getLngconsumption());
                    tp.put("lngAdjustment", c.getLngadjustment());
                    tp.put("dblDataInterval", c.getDbldatainterval());
                    tp.put("lngParentID", c.getLngparentid());
                    tp.put("PIPELINE_ID", pipelineId);
                    batchParams.add(new MapSqlParameterSource(tp));
                });
                SqlParameterSource[] batchSqlSource = new SqlParameterSource[batchParams.size()];
                si.executeBatch(batchParams.toArray(batchSqlSource));
            }
            return pipelineId;
        }
    }

    @Override
    public List<Map<String, Object>> getPipelineProgramList(CustomUserDetails curUser) {
        String sql = "select ap.*,u.USERNAME from adb_pipeline ap  "
                + "left join us_user u on u.USER_ID=ap.CREATED_BY "
                + "where ap.CREATED_BY=:userId and ap.`STATUS`=0";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", curUser.getUserId());
        return this.namedParameterJdbcTemplate.queryForList(sql, params);
    }

    @Override
    public PplPrograminfo getPipelineProgramInfoById(int pipelineId, CustomUserDetails curUser) {
        String sql = "SELECT If(rc.REALM_COUNTRY_ID IS NULL,ap.CountryName,rc.REALM_COUNTRY_ID) as CountryName,ap.Note,ap.ProgramName,ap.DefaultLeadTimeOrder,ap.DefaultLeadTimePlan,ap.DefaultLeadTimeShip   "
                + "FROM fasp.adb_programinfo ap "
                + "left join ap_label al on upper(al.LABEL_EN)=upper(ap.CountryName)  "
                + "OR upper(al.LABEL_FR)=upper(ap.CountryName) OR upper(al.LABEL_SP)=upper(ap.CountryName)  "
                + "OR upper(al.LABEL_PR)=upper(ap.CountryName) "
                + "left join ap_country ac on ac.LABEL_ID=al.LABEL_ID "
                + "left join rm_realm_country rc on rc.COUNTRY_ID=ac.COUNTRY_ID  "
                + "AND rc.REALM_ID=:realmId AND rc.COUNTRY_ID IS NOT NULL "
                + "where ap.PIPELINE_ID=:pipelineId ;";
        Map<String, Object> params = new HashMap<>();
        params.put("pipelineId", pipelineId);
        params.put("realmId", 1);
        return this.namedParameterJdbcTemplate.queryForObject(sql, params, new PplPrograminfoRowMapper());
    }

    @Override
    @Transactional
    public int addQatTempProgram(QatTempProgram p, CustomUserDetails curUser, int pipelineId) {

//        if (p.getProgramId() != 0) {
//            Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
//            Map<String, Object> params = new HashMap<>();
//            params.put("programId", p.getProgramId());
//            params.put("labelEn", p.getLabel().getLabel_en());
//            params.put("programManagerUserId", p.getProgramManager().getUserId());
//            params.put("programNotes", p.getProgramNotes());
//            params.put("airFreightPerc", p.getAirFreightPerc());
//            params.put("seaFreightPerc", p.getSeaFreightPerc());
//            params.put("plannedToSubmittedLeadTime", p.getPlannedToSubmittedLeadTime());
//            params.put("submittedToApprovedLeadTime", p.getSubmittedToApprovedLeadTime());
//            params.put("approvedToShippedLeadTime", p.getApprovedToShippedLeadTime());
////            params.put("deliveredToReceivedLeadTime", p.getDeliveredToReceivedLeadTime());
////            params.put("monthsInPastForAmc", p.getMonthsInPastForAmc());
////            params.put("monthsInFutureForAmc", p.getMonthsInFutureForAmc());
//
//            params.put("arrivedToDeliveredLeadTime", p.getArrivedToDeliveredLeadTime());
//            params.put("shippedToArrivedBySeaLeadTime", p.getShippedToArrivedBySeaLeadTime());
//            params.put("shippedToArrivedByAirLeadTime", p.getShippedToArrivedByAirLeadTime());
//
////            params.put("active", true);
//            params.put("curUser", curUser.getUserId());
//            params.put("curDate", curDate);
//            String sqlString = "UPDATE qat_temp_program p "
//                    + "LEFT JOIN qat_temp_ap_label pl ON p.LABEL_ID=pl.LABEL_ID "
//                    + "SET "
//                    + "p.PROGRAM_MANAGER_USER_ID=:programManagerUserId, "
//                    + "p.PROGRAM_NOTES=:programNotes, "
//                    + "p.AIR_FREIGHT_PERC=:airFreightPerc, "
//                    + "p.SEA_FREIGHT_PERC=:seaFreightPerc, "
//                    + "p.PLANNED_TO_SUBMITTED_LEAD_TIME=:plannedToSubmittedLeadTime, "
//                    //                    + "p.DRAFT_TO_SUBMITTED_LEAD_TIME=:draftToSubmittedLeadTime, "
//                    + "p.SUBMITTED_TO_APPROVED_LEAD_TIME=:submittedToApprovedLeadTime, "
//                    + "p.APPROVED_TO_SHIPPED_LEAD_TIME=:approvedToShippedLeadTime, "
//                    + "p.ARRIVED_TO_DELIVERED_LEAD_TIME=:arrivedToDeliveredLeadTime, "
//                    + "p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME=:shippedToArrivedByAirLeadTime, "
//                    + "p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME=:shippedToArrivedBySeaLeadTime, "
//                    + "p.LAST_MODIFIED_BY=:curUser, "
//                    + "p.LAST_MODIFIED_DATE=:curDate, "
//                    + "pl.LABEL_EN=:labelEn, "
//                    + "pl.LAST_MODIFIED_BY=:curUser, "
//                    + "pl.LAST_MODIFIED_DATE=:curDate "
//                    + "WHERE p.PROGRAM_ID=:programId ";
//            int rows = this.namedParameterJdbcTemplate.update(sqlString, params);
//            params.clear();
//            params.put("programId", p.getProgramId());
//            this.namedParameterJdbcTemplate.update("DELETE FROM qat_temp_program_region WHERE PROGRAM_ID=:programId", params);
//            SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("qat_temp_program_region");
//            SqlParameterSource[] paramList = new SqlParameterSource[p.getRegionArray().length];
//            int i = 0;
//            for (String regionId : p.getRegionArray()) {
//                params = new HashMap<>();
//                params.put("PROGRAM_ID", p.getProgramId());
//                params.put("REGION_ID", regionId);
//                params.put("CREATED_BY", curUser.getUserId());
//                params.put("CREATED_DATE", curDate);
//                params.put("LAST_MODIFIED_BY", curUser.getUserId());
//                params.put("LAST_MODIFIED_DATE", curDate);
//                params.put("ACTIVE", true);
//                paramList[i] = new MapSqlParameterSource(params);
//                i++;
//            }
//            si.executeBatch(paramList);
//            return rows;
//
//        } else {
        Map<String, Object> params = new HashMap<>();
        params.put("pipelineId", pipelineId);
        this.namedParameterJdbcTemplate.update("DELETE FROM qat_temp_program WHERE PIPELINE_ID=:pipelineId", params);
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
//        int labelId = this.labelDao.addLabel(p.getLabel(), curUser.getUserId());
        int labelId = this.addQatTempLabel(p.getLabel(), curUser.getUserId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_program").usingGeneratedKeyColumns("PROGRAM_ID");
        params.put("PROGRAM_CODE", p.getProgramCode());
        params.put("REALM_COUNTRY_ID", p.getRealmCountry().getRealmCountryId());
        params.put("ORGANISATION_ID", p.getOrganisation().getId());
//        params.put("HEALTH_AREA_ID", p.getHealthArea().getId());
        params.put("LABEL_ID", labelId);
        params.put("PROGRAM_MANAGER_USER_ID", p.getProgramManager().getUserId());
        params.put("PROGRAM_NOTES", p.getProgramNotes());
        params.put("AIR_FREIGHT_PERC", p.getAirFreightPerc());
        params.put("SEA_FREIGHT_PERC", p.getSeaFreightPerc());
        params.put("PLANNED_TO_SUBMITTED_LEAD_TIME", p.getPlannedToSubmittedLeadTime());
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
        params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
        params.put("CURRENT_VERSION_ID", null);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("PIPELINE_ID", pipelineId);
        params.put("MONTHS_IN_PAST_FOR_AMC", p.getMonthsInPastForAmc());
        params.put("MONTHS_IN_FUTURE_FOR_AMC", p.getMonthsInFutureForAmc());
        params.put("SHELF_LIFE", p.getShelfLife());

        params.put("ARRIVED_TO_DELIVERED_LEAD_TIME", p.getArrivedToDeliveredLeadTime());
        params.put("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME", p.getShippedToArrivedByAirLeadTime());
        params.put("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME", p.getShippedToArrivedBySeaLeadTime());

        int programId = si.executeAndReturnKey(params).intValue();

        this.namedParameterJdbcTemplate.update("DELETE FROM qat_temp_program_healthArea WHERE PIPELINE_ID=:pipelineId", params);
        si = new SimpleJdbcInsert(this.dataSource).withTableName("qat_temp_program_healthArea");
        SqlParameterSource[] paramListha = new SqlParameterSource[p.getHealthAreaArray().length];
        int iha = 0;
        for (String ha : p.getHealthAreaArray()) {
            params = new HashMap<>();
            params.put("HEALTH_AREA_ID", ha);
            params.put("pipelineId", pipelineId);
//            params.put("CREATED_BY", curUser.getUserId());
//            params.put("CREATED_DATE", curDate);
//            params.put("LAST_MODIFIED_BY", curUser.getUserId());
//            params.put("LAST_MODIFIED_DATE", curDate);
//            params.put("ACTIVE", true);
            paramListha[iha] = new MapSqlParameterSource(params);
            iha++;
        }
        si.executeBatch(paramListha);
//        params.clear();
//        System.out.println("params===>" + params);
        this.namedParameterJdbcTemplate.update("DELETE FROM qat_temp_program_region WHERE PIPELINE_ID=:pipelineId", params);
        si = new SimpleJdbcInsert(this.dataSource).withTableName("qat_temp_program_region");
        SqlParameterSource[] paramList = new SqlParameterSource[p.getRegionArray().length];
        int i = 0;
        for (String rId : p.getRegionArray()) {
            params = new HashMap<>();
            params.put("REGION_ID", rId);
            params.put("PIPELINE_ID", pipelineId);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ACTIVE", true);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        params.clear();
        return programId;

    }

    @Override
    public QatTempProgram getQatTempProgram(CustomUserDetails curUser, int pipelineId) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND p.PIPELINE_ID=:pipelineId");
        Map<String, Object> params = new HashMap<>();
        params.put("pipelineId", pipelineId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new QatTempProgramResultSetExtractor());
    }

    @Override
    public int addQatTempLabel(Label label, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("qat_temp_ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", label.getLabel_en());
        params.put("LABEL_FR", label.getLabel_fr());
        params.put("LABEL_SP", label.getLabel_sp());
        params.put("LABEL_PR", label.getLabel_pr());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public List<PplProduct> getPipelineProductListById(CustomUserDetails curUser, int pipelineId) {
        Map<String, Object> params = new HashMap<>();
        String sql = "SELECT p.ProductId,p.ProductMinMonths, "
                + "if(pu.PLANNING_UNIT_ID IS NULL,p.ProductName,pu.PLANNING_UNIT_ID) as planningUnitId, "
                + "if(fu.FORECASTING_UNIT_ID IS NULL,'',fu.PRODUCT_CATEGORY_ID) as productCategoryId "
                + "FROM fasp.adb_product p  "
                + "left join ap_label al on al.LABEL_EN=p.ProductName OR al.LABEL_FR=p.ProductName  "
                + "or al.LABEL_PR=p.ProductName or al.LABEL_SP=p.ProductName "
                + "left join rm_planning_unit pu on pu.LABEL_ID=al.LABEL_ID "
                + "left join rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID  "
                + "AND pu.PLANNING_UNIT_ID IS NOT NULL "
                + "where p.PIPELINE_ID=:pipelineId;";
        params.put("pipelineId", pipelineId);
        return this.namedParameterJdbcTemplate.query(sql, params, new PipelineProductRowMapper());
    }

    @Override
    public List<QatTempProgramPlanningUnit> getQatTempPlanningUnitListByPipelienId(int pipelineId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sql = "SELECT concat(ap.ProductName,'~',ap.ProductId) as PIPELINE_PRODUCT_NAME, "
                + "m.MethodName as PIPELINE_PRODUCT_CATEGORY, "
                + "p.PIPELINE_PRODUCT_ID, "
                + "p.PLANNING_UNIT_ID,p.MULTIPLIER, "
                + "COALESCE(p.REORDER_FREQUENCY_IN_MONTHS,ap.ProdDesStock-ap.ProductMinMonths)REORDER_FREQUENCY_IN_MONTHS, "
                + "p.MIN_MONTHS_OF_STOCK, "
                + "fu.PRODUCT_CATEGORY_ID,  "
                + " COALESCE(p.LOCAL_PROCUREMENT_LEAD_TIME,-1) LOCAL_PROCUREMENT_LEAD_TIME, "
                + " p.SHELF_LIFE, "
                + " COALESCE(p.CATALOG_PRICE,-1) CATALOG_PRICE, "
                + " p.MONTHS_IN_PAST_FOR_AMC, "
                + " p.MONTHS_IN_FUTURE_FOR_AMC,p.ACTIVE "
                + "FROM fasp.qat_temp_program_planning_unit p  "
                + "left join adb_product ap on ap.ProductID=p.PIPELINE_PRODUCT_ID and ap.PIPELINE_ID=:pipelineId "
                + "left join adb_method m on m.MethodID=ap.MethodID and m.PIPELINE_ID=:pipelineId "
                + "left join rm_planning_unit pu on p.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID "
                + "left join rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID AND pu.PLANNING_UNIT_ID IS NOT NULL  "
                + "where p.PIPELINE_ID=:pipelineId;";
        params.put("pipelineId", pipelineId);
        List<QatTempProgramPlanningUnit> qatList = this.namedParameterJdbcTemplate.query(sql, params, new QatTempPlanningUnitRowMapper());
        if (qatList.size() == 0) {
            String sql1 = "SELECT   "
                    + "  m.MethodName as PIPELINE_PRODUCT_CATEGORY, "
                    + "  concat(p.ProductName,'~',p.ProductId) as PIPELINE_PRODUCT_NAME, "
                    + "  p.ProductId as PIPELINE_PRODUCT_ID, "
                    + "  p.ProductMinMonths as MIN_MONTHS_OF_STOCK, "
                    + "  if(pu.PLANNING_UNIT_ID IS NULL,p.ProductName,pu.PLANNING_UNIT_ID) as PLANNING_UNIT_ID, "
                    + "  if(fu.FORECASTING_UNIT_ID IS NULL,'',fu.PRODUCT_CATEGORY_ID) as PRODUCT_CATEGORY_ID, "
                    + "   (p.ProdDesStock-p.ProductMinMonths) as REORDER_FREQUENCY_IN_MONTHS, "
                    //                    + "  (p.ProductMaxMonths-p.ProductMinMonths) as REORDER_FREQUENCY_IN_MONTHS, "
                    + "  '-1' as LOCAL_PROCUREMENT_LEAD_TIME, "
                    + "  COALESCE(qtp.SHELF_LIFE,'') as SHELF_LIFE, "
                    + "  IFNULL(price.UnitPrice,-1) as CATALOG_PRICE, "
                    + "  1 as MULTIPLIER, "
                    + "  COALESCE(qtp.MONTHS_IN_PAST_FOR_AMC,'') as MONTHS_IN_PAST_FOR_AMC, "
                    + "  COALESCE(qtp.MONTHS_IN_FUTURE_FOR_AMC,'') as MONTHS_IN_FUTURE_FOR_AMC, p.ProductActiveFlag as ACTIVE "
                    + "FROM adb_product p "
                    + "left join qat_temp_program qtp on  qtp.PIPELINE_ID=:pipelineId "
                    + "left join adb_method m on m.MethodID=p.MethodID and m.PIPELINE_ID=:pipelineId "
                    + "left join ap_label al on (al.LABEL_EN=p.ProductName OR al.LABEL_FR=p.ProductName or al.LABEL_PR=p.ProductName or al.LABEL_SP=p.ProductName) and al.SOURCE_ID=30 "
                    + "left join rm_planning_unit pu on pu.LABEL_ID=al.LABEL_ID "
                    + "left join rm_forecasting_unit fu on fu.FORECASTING_UNIT_ID=pu.FORECASTING_UNIT_ID AND pu.PLANNING_UNIT_ID IS NOT NULL "
                    + "LEFT JOIN (SELECT cp.ProductID, cp.UnitPrice FROM adb_commodityprice cp LEFT JOIN (SELECT p.ProductID, cp.SupplierID, Max(cp.dtmEffective) effectiveDt FROM adb_product p LEFT JOIN adb_commodityprice cp ON p.ProductID=cp.ProductID AND p.SupplierID=cp.SupplierID AND cp.PIPELINE_ID=:pipelineId GROUP BY p.ProductID, cp.SupplierID) cp1 ON cp.ProductID=cp1.ProductID AND cp.SupplierID=cp1.SupplierID AND cp.dtmEffective=cp1.effectiveDt WHERE cp1.ProductID IS NOT NULL) price ON p.ProductID=price.ProductID "
                    + "where p.PIPELINE_ID=:pipelineId";
            params.put("pipelineId", pipelineId);
            return this.namedParameterJdbcTemplate.query(sql1, params, new QatTempPlanningUnitRowMapper());
        } else {
            return qatList;
        }
    }

    @Override
    public List<QatTempShipment> getPipelineShipmentdataById(int pipelineId, CustomUserDetails curUser) {

        Map<String, Object> params = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        params.put("pipelineId", pipelineId);
        String sql = "SELECT "
                + "		st.SHIPMENT_ID, st.EXPECTED_DELIVERY_DATE, "
                + "st.ORDERED_DATE, st.SHIPPED_DATE, st.RECEIVED_DATE,"
                + "st.PLANNED_DATE, "
                + "now() SUBMITTED_DATE,"
                + "now() APPROVED_DATE,"
                + "now() ARRIVED_DATE,"
                + "st.QUANTITY, st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.SHIPPING_MODE, st.SUGGESTED_QTY, '0' ACCOUNT_FLAG, '0'ERP_FLAG, st.NOTES, "
                + "		0 VERSION_ID , "
                + "		st.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pal.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pal.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pal.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pal.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pal.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, "
                + "		st.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, "
                + "		fu.FORECASTING_UNIT_ID, ful.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, ful.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, ful.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, ful.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, ful.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, "
                + "		pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, "
                + "		st.PROCUREMENT_UNIT_ID, prul.LABEL_ID `PROCUREMENT_UNIT_LABEL_ID`, prul.LABEL_EN `PROCUREMENT_UNIT_LABEL_EN`, prul.LABEL_FR `PROCUREMENT_UNIT_LABEL_FR`, prul.LABEL_SP `PROCUREMENT_UNIT_LABEL_SP`, prul.LABEL_PR `PROCUREMENT_UNIT_LABEL_PR`, "
                + "        st.SUPPLIER_ID, sul.LABEL_ID `SUPPLIER_LABEL_ID`, sul.LABEL_EN `SUPPLIER_LABEL_EN`, sul.LABEL_FR `SUPPLIER_LABEL_FR`, sul.LABEL_SP `SUPPLIER_LABEL_SP`, sul.LABEL_PR `SUPPLIER_LABEL_PR`, "
                + "        st.SHIPMENT_STATUS_ID, shsl.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shsl.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shsl.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shsl.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shsl.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, "
                + "        st.DATA_SOURCE_ID, dsl.LABEL_ID `DATA_SOURCE_LABEL_ID`, dsl.LABEL_EN `DATA_SOURCE_LABEL_EN`, dsl.LABEL_FR `DATA_SOURCE_LABEL_FR`, dsl.LABEL_SP `DATA_SOURCE_LABEL_SP`, dsl.LABEL_PR `DATA_SOURCE_LABEL_PR`, "
                + "        st.FUNDING_SOURCE_ID, fsl.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fsl.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fsl.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fsl.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fsl.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, "
                + "		cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, st.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, st.LAST_MODIFIED_DATE, st.ACTIVE "
                + "   	FROM  qat_temp_shipment st  "
                + "	LEFT JOIN rm_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
                + "	LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                + "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "	LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID "
                + "	LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "	LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID "
                + "	LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "	LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "    LEFT JOIN rm_procurement_unit pru ON st.PROCUREMENT_UNIT_ID=pru.PROCUREMENT_UNIT_ID "
                + "    LEFT JOIN ap_label prul ON pru.LABEL_ID=prul.LABEL_ID "
                + "	LEFT JOIN rm_supplier su ON st.SUPPLIER_ID=su.SUPPLIER_ID "
                + "    LEFT JOIN ap_label sul ON su.LABEL_ID=sul.LABEL_ID "
                + "    LEFT JOIN ap_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID "
                + "    LEFT JOIN ap_label shsl ON shs.LABEL_ID=shsl.LABEL_ID  "
                + "    LEFT JOIN rm_data_source ds ON st.DATA_SOURCE_ID=ds.DATA_SOURCE_ID "
                + "	LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "    LEFT JOIN rm_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
                + "	LEFT JOIN ap_label fsl ON fs.LABEL_ID=fsl.LABEL_ID "
                + "LEFT JOIN us_user cb ON st.CREATED_BY=cb.USER_ID "
                + "	LEFT JOIN us_user lmb ON st.LAST_MODIFIED_BY=lmb.USER_ID"
                + "	WHERE st.`PIPELINE_ID`=:pipelineId";
        List<QatTempShipment> result = this.namedParameterJdbcTemplate.query(sql, params, new QatTempShipmentRowMapper());

        if (result.size() == 0) {

//            sql = "SELECT ash.ShipmentID SHIPMENT_ID,qtp.`PLANNING_UNIT_ID`,ash.`ShipAmount`,ash.`ShipReceivedDate` EXPECTED_DELIVERY_DATE,ash.ShipAmount SUGGESTED_QTY,ash.ShipAmount QUANTITY,'0.0' RATE,ShipValue PRODUCT_COST,'' SHIPPING_MODE,ash.`ShipOrderedDate` ORDERED_DATE, "
//                    + "     ash.ShipPlannedDate   PLANNED_DATE,null ARRIVED_DATE,null APPROVED_DATE,null SUBMITTED_DATE,         ash.`ShipShippedDate` SHIPPED_DATE,ash.`ShipReceivedDate` RECEIVED_DATE,ash.ShipStatusCode SHIPMENT_STATUS_ID,ash.`ShipNote` NOTES,ash.`ShipFreightCost` FREIGHT_COST,ash.`ShipPO`,COALESCE(rds.`DATA_SOURCE_ID`,ds.`DataSourceName`) DATA_SOURCE_ID, "
//                    + "                 '1'ACCOUNT_FLAG,'1'ERP_FLAG,'0'VERSION_ID ,COALESCE(rpa.`PROCUREMENT_AGENT_ID`,ads.`SupplierName`) PROCUREMENT_AGENT_ID, '' PROCUREMENT_UNIT_ID,'' SUPPLIER_ID ,COALESCE(rfs.`FUNDING_SOURCE_ID`,afs.`FundingSourceName`) FUNDING_SOURCE_ID,'1' ACTIVE  "
//                    + "                 FROM adb_shipment ash  "
//            sql = "SELECT ash.ShipmentID SHIPMENT_ID, "
//                    + "qtp.`PLANNING_UNIT_ID`, "
//                    + "ash.`ShipAmount`, "
//                    + "ash.`ShipReceivedDate` EXPECTED_DELIVERY_DATE, "
//                    + "ash.ShipAmount SUGGESTED_QTY, "
//                    + "ash.ShipAmount QUANTITY, "
//                    + "COALESCE(facp.UnitPrice,'0.0') RATE, "
//                    + "coalesce(ash.ShipAmount*facp.UnitPrice,'0.0' )PRODUCT_COST, "
//                    + "'' SHIPPING_MODE, "
//                    + "ash.`ShipPlannedDate` PLANNED_DATE,  "
//                    + "ash.`ShipShippedDate` SHIPPED_DATE, "
//                    + "ash.`ShipReceivedDate` RECEIVED_DATE, "
//                    + "now() SUBMITTED_DATE, "
//                    + "now() APPROVED_DATE, "
//                    + "now() ARRIVED_DATE, "
//                    + "COALESCE(ass.QAT_SHIPMENT_STATUS_ID,ash.ShipStatusCode) SHIPMENT_STATUS_ID, "
//                    + "ash.`ShipNote` NOTES, "
//                    + "coalesce(ash.ShipAmount*facp.UnitPrice*ads.Freight/100,'0.0') FREIGHT_COST, "
//                    + "ash.`ShipPO`,COALESCE(rds.`DATA_SOURCE_ID`,ds.`DataSourceName`) DATA_SOURCE_ID,  "
//                    + "'1'ACCOUNT_FLAG,'1'ERP_FLAG,'0'VERSION_ID ,COALESCE(rpa.`PROCUREMENT_AGENT_ID`,ads.`SupplierName`) PROCUREMENT_AGENT_ID, '' PROCUREMENT_UNIT_ID,'' SUPPLIER_ID ,COALESCE(rfs.`FUNDING_SOURCE_ID`,afs.`FundingSourceName`) FUNDING_SOURCE_ID,'1' ACTIVE   "
//                    + "FROM adb_shipment ash   "
//                    + "LEFT JOIN  qat_temp_program_planning_unit qtp ON qtp.PIPELINE_PRODUCT_ID = ash.ProductID  AND  qtp.`PIPELINE_ID`=:pipelineId "
//                    + "LEFT JOIN adb_datasource ds ON ds.`DataSourceID`=ash.`ShipDataSourceID`AND ds.`PIPELINE_ID`=:pipelineId "
//                    + "LEFT JOIN qat_temp_data_source qtds ON qtds.PIPELINE_DATA_SOURCE_ID=ds.DataSourceID AND qtds.PIPELINE_ID=:pipelineId "
//                    + "LEFT JOIN rm_data_source rds ON qtds.DATA_SOURCE_ID=rds.DATA_SOURCE_ID     "
//                    + "LEFT JOIN adb_source ads ON ads.`SupplierID`=ash.`SupplierID` AND ads.`PIPELINE_ID`=:pipelineId "
//                    + "LEFT JOIN qat_temp_procurement_agent qtpa ON qtpa.PIPELINE_PROCUREMENT_AGENT_ID=ads.SupplierID AND qtpa.PIPELINE_ID=:pipelineId "
//                    + "LEFT JOIN rm_procurement_agent rpa ON rpa.`PROCUREMENT_AGENT_ID`=qtpa.`PROCUREMENT_AGENT_ID`  "
//                    + "LEFT JOIN adb_fundingsource afs ON afs.`FundingSourceID`=ash.`ShipFundingSourceID` AND afs.`PIPELINE_ID`=:pipelineId "
//                    + "LEFT JOIN qat_temp_funding_source qtfs ON qtfs.PIPELINE_FUNDING_SOURCE_ID=afs.FundingSourceID AND qtfs.PIPELINE_ID=:pipelineId "
//                    + "LEFT JOIN rm_funding_source rfs ON rfs.`FUNDING_SOURCE_ID`=qtfs.`FUNDING_SOURCE_ID`   "
//                    + "LEFT JOIN adb_shipmentstatus ass ON ash.`ShipStatusCode`=ass.`PipelineShipmentStatusCode`   "
//                    + "LEFT JOIN (SELECT a.*,MAX(a.dtmEffective) effective_date FROM adb_commodityprice a WHERE  a.`PIPELINE_ID`=:pipelineId GROUP BY a.`ProductID`,a.`SupplierID`   )acp ON acp.`ProductID`=qtp.`PIPELINE_PRODUCT_ID` AND acp.SupplierID=qtpa.PIPELINE_PROCUREMENT_AGENT_ID  "
//                    + "LEFT JOIN   adb_commodityprice acp1 ON acp.`ProductID`=acp1.`ProductID` AND acp.SupplierID=acp1.SupplierID AND acp.effective_date=acp1.`dtmEffective` "
//                    + "left join adb_commodityprice facp on facp.ProductID=ash.ProductID and facp.SupplierID=ash.SupplierID and facp.dtmEffective < ash.ShipReceivedDate "
//                    + "WHERE ash.`PIPELINE_ID`=:pipelineId";
            sql = "SELECT ash.ShipmentID SHIPMENT_ID, "
                    + "    qtp.`PLANNING_UNIT_ID`, ash.`ShipAmount`, "
                    + "     ash.`ShipReceivedDate` EXPECTED_DELIVERY_DATE, "
                    //                    + "ash.ShipAmount SUGGESTED_QTY, ash.ShipAmount QUANTITY, "
                    + "ash.ShipAmount * qtp.MULTIPLIER SUGGESTED_QTY, ash.ShipAmount * qtp.MULTIPLIER QUANTITY, "
                    + "    COALESCE(facp.UnitPrice,'0.0') RATE, coalesce(ash.ShipAmount*facp.UnitPrice,'0.0' )PRODUCT_COST, '' SHIPPING_MODE, ash.`ShipPlannedDate` PLANNED_DATE, ash.`ShipShippedDate` SHIPPED_DATE, "
                    + "    ash.`ShipReceivedDate` RECEIVED_DATE, null SUBMITTED_DATE, null APPROVED_DATE, null ARRIVED_DATE, COALESCE(ass.QAT_SHIPMENT_STATUS_ID,ash.ShipStatusCode) SHIPMENT_STATUS_ID, "
                    //                    + "    ash.`ShipNote` NOTES, coalesce(ash.ShipAmount*facp.UnitPrice*ads.Freight/100,'0.0') FREIGHT_COST, "
                    + "    ash.`ShipNote` NOTES, coalesce(ash.ShipAmount*qtp.MULTIPLIER*facp.UnitPrice*ads.Freight/100,'0.0') FREIGHT_COST, "
                    + "ash.`ShipPO`,COALESCE(rds.`DATA_SOURCE_ID`,ds.`DataSourceName`) DATA_SOURCE_ID, '1'ACCOUNT_FLAG,'1'ERP_FLAG, "
                    + "    '0'VERSION_ID ,COALESCE(rpa.`PROCUREMENT_AGENT_ID`,ads.`SupplierName`) PROCUREMENT_AGENT_ID, '' PROCUREMENT_UNIT_ID,'' SUPPLIER_ID ,COALESCE(rfs.`FUNDING_SOURCE_ID`,afs.`FundingSourceName`) FUNDING_SOURCE_ID,'1' ACTIVE   "
                    + "FROM adb_shipment ash   "
                    + "LEFT JOIN  qat_temp_program_planning_unit qtp ON qtp.PIPELINE_PRODUCT_ID = ash.ProductID  AND  qtp.`PIPELINE_ID`=:pipelineId "
                    + "LEFT JOIN adb_datasource ds ON ds.`DataSourceID`=ash.`ShipDataSourceID`AND ds.`PIPELINE_ID`=:pipelineId "
                    + "LEFT JOIN qat_temp_data_source qtds ON qtds.PIPELINE_DATA_SOURCE_ID=ds.DataSourceID AND qtds.PIPELINE_ID=:pipelineId "
                    + "LEFT JOIN rm_data_source rds ON qtds.DATA_SOURCE_ID=rds.DATA_SOURCE_ID     "
                    + "LEFT JOIN adb_source ads ON ads.`SupplierID`=ash.`SupplierID` AND ads.`PIPELINE_ID`=:pipelineId "
                    + "LEFT JOIN qat_temp_procurement_agent qtpa ON qtpa.PIPELINE_PROCUREMENT_AGENT_ID=ads.SupplierID AND qtpa.PIPELINE_ID=:pipelineId "
                    + "LEFT JOIN rm_procurement_agent rpa ON rpa.`PROCUREMENT_AGENT_ID`=qtpa.`PROCUREMENT_AGENT_ID` "
                    + "LEFT JOIN adb_fundingsource afs ON afs.`FundingSourceID`=ash.`ShipFundingSourceID` AND afs.`PIPELINE_ID`=:pipelineId "
                    + "LEFT JOIN qat_temp_funding_source qtfs ON qtfs.PIPELINE_FUNDING_SOURCE_ID=afs.FundingSourceID AND qtfs.PIPELINE_ID=:pipelineId "
                    + "LEFT JOIN rm_funding_source rfs ON rfs.`FUNDING_SOURCE_ID`=qtfs.`FUNDING_SOURCE_ID`   "
                    + "LEFT JOIN adb_shipmentstatus ass ON ash.`ShipStatusCode`=ass.`PipelineShipmentStatusCode`   "
                    + "LEFT JOIN (SELECT s.ShipmentID, MAX(cp.dtmEffective) maxEffectiveDate FROM adb_shipment s LEFT JOIN adb_commodityprice cp ON s.SupplierID=cp.SupplierID AND s.ProductID=cp.ProductID AND cp.dtmEffective<=s.ShipReceivedDate group by s.ShipmentID) acp ON acp.ShipmentID=ash.ShipmentID "
                    + "LEFT JOIN adb_commodityprice facp on facp.ProductID=ash.ProductID and facp.SupplierID=ash.SupplierID and facp.dtmEffective = acp.maxEffectiveDate "
                    + "WHERE ash.`PIPELINE_ID`=:pipelineId";

            result = this.namedParameterJdbcTemplate.query(sql, params, new QatTempShipmentRowMapper());
        }

        return result;

    }

    @Override
    public int saveShipmentData(int pipelineId, QatTempShipment[] shipments, CustomUserDetails curUser) {
        SqlParameterSource[] paramList = new SqlParameterSource[shipments.length];
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params1 = new HashMap<>();
        params1.put("pipelineId", pipelineId);
        String sql = "SELECT COUNT(*) FROM qat_temp_shipment q WHERE q.`PIPELINE_ID`=:pipelineId";
        int cnt = this.namedParameterJdbcTemplate.queryForObject(sql, params1, Integer.class);

        if (cnt > 0) {
            this.namedParameterJdbcTemplate.update("DELETE FROM qat_temp_shipment WHERE PIPELINE_ID=:pipelineId", params1);
        }
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");

        int i = 0;
        for (QatTempShipment s : shipments) {
            Map<String, Object> params = new HashMap<>();

            params.put("PLANNING_UNIT_ID", s.getPlanningUnit());
            params.put("EXPECTED_DELIVERY_DATE", s.getExpectedDeliveryDate());
            params.put("SUGGESTED_QTY", s.getSuggestedQty());
            params.put("PROCUREMENT_AGENT_ID", s.getProcurementAgent());
            params.put("PROCUREMENT_UNIT_ID", null);
            params.put("SUPPLIER_ID", s.getSupplier());
            params.put("QUANTITY", s.getQuantity());
            params.put("RATE", s.getRate());
            params.put("PRODUCT_COST", s.getProductCost());
            params.put("SHIPPING_MODE", s.getShipmentMode());
            params.put("FREIGHT_COST", s.getFreightCost());

            params.put("PLANNED_DATE", curDate);
            params.put("SUBMITTED_DATE", curDate);
            params.put("APPROVED_DATE", curDate);
            params.put("ARRIVED_DATE", curDate);

            params.put("SHIPPED_DATE", s.getShippedDate());
            params.put("RECEIVED_DATE", s.getReceivedDate());
            params.put("SHIPMENT_STATUS_ID", s.getShipmentStatus());
            params.put("DATA_SOURCE_ID", s.getDataSource());
            params.put("NOTES", s.getNotes());

            params.put("ACTIVE", true);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("PIPELINE_ID", pipelineId);
            params.put("FUNDING_SOURCE_ID", s.getFundingSource());
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        int[] batchResult = si.executeBatch(paramList);
//        for (int j : batchResult) {
//            System.out.println("shipments" + j);
//        }
        return 1;
    }

    @Override
    @Transactional
    public int saveQatTempProgramPlanningUnit(QatTempProgramPlanningUnit[] programPlanningUnits, CustomUserDetails curUser, int pipelineId) {
        String sql = " delete from qat_temp_program_planning_unit  where PIPELINE_ID=?";
        this.jdbcTemplate.update(sql, pipelineId);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_program_planning_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (QatTempProgramPlanningUnit ppu : programPlanningUnits) {
            // Insert
            params = new HashMap<>();
            params.put("PLANNING_UNIT_ID", ppu.getPlanningUnitId());
            params.put("MULTIPLIER", ppu.getMultiplier());
            params.put("PROGRAM_ID", ppu.getProgram().getId());
            params.put("REORDER_FREQUENCY_IN_MONTHS", ppu.getReorderFrequencyInMonths());
            params.put("MIN_MONTHS_OF_STOCK", ppu.getMinMonthsOfStock());
            params.put("MONTHS_IN_FUTURE_FOR_AMC", ppu.getMonthsInFutureForAmc());
            params.put("MONTHS_IN_PAST_FOR_AMC", ppu.getMonthsInPastForAmc());
            params.put("ACTIVE", ppu.isActive());
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("PIPELINE_ID", pipelineId);
            params.put("PIPELINE_PRODUCT_ID", ppu.getProgramPlanningUnitId());
            params.put("LOCAL_PROCUREMENT_LEAD_TIME", ppu.getLocalProcurmentLeadTime());
            params.put("SHELF_LIFE", ppu.getShelfLife());
            params.put("CATALOG_PRICE", ppu.getCatalogPrice());
            insertList.add(new MapSqlParameterSource(params));

        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<PplConsumption> getPipelineConsumptionById(CustomUserDetails curUser, int pipelineId) {
        Map<String, Object> params = new HashMap<>();
        String sql = "SELECT  "
                + "If(rds.DATA_SOURCE_ID IS NULL,ad.DataSourceName,rds.DATA_SOURCE_ID) as DataSourceName, "
                + "date_format(CONCAT(c.ConsStartYear,\"-\",c.ConsStartMonth,\"-01\"),\"%Y-%m-%d\") as consumptionDate, "
                + "c.ConsActualFlag,c.ConsAmount,c.ConsNote,qtp.PLANNING_UNIT_ID "
                + "FROM fasp.adb_consumption c "
                + "left join adb_datasource ad on ad.DataSourceID=c.ConsDataSourceID AND ad.PIPELINE_ID=:pipelineId "
                + "left join qat_temp_data_source qtds on qtds.PIPELINE_DATA_SOURCE_ID=ad.DataSourceID AND qtds.PIPELINE_ID=:pipelineId "
                + "left join qat_temp_program_planning_unit qtp on qtp.PIPELINE_PRODUCT_ID=c.ProductID "
                + "left join rm_data_source rds on qtds.DATA_SOURCE_ID=rds.DATA_SOURCE_ID  "
                + "where c.PIPELINE_ID=:pipelineId "
                + ";";
        params.put("pipelineId", pipelineId);
        return this.namedParameterJdbcTemplate.query(sql, params, new PipelineConsumptionRowMapper());

    }

    @Override
    public List<QatTempConsumption> getQatTempConsumptionListByPipelienId(int pipelineId, CustomUserDetails curUser) {
//        String sql = "select * from qat_temp_consumption c where c.PIPELINE_ID=:pipelineId";
        Map<String, Object> params = new HashMap<>();
        params.put("pipelineId", pipelineId);
//        return this.namedParameterJdbcTemplate.query(sql, params, new QatTempConsumptionRowMapper());
//left join qat_temp_program tp on tp.PIPELINE_ID=:pipelineId
//left join rm_realm_country_planning_unit rcpu on rcpu.PLANNING_UNIT_ID=c.PLANNING_UNIT_ID and rcpu.REALM_COUNTRY_ID=tp.REALM_COUNTRY_ID
//if(c.REALM_COUNTRY_PLANNING_UNIT_ID=0,coalesce(rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,0),c.REALM_COUNTRY_PLANNING_UNIT_ID)
        String sqlQatTemp = "select "
                + "c.REGION_ID,"
                + "c.PLANNING_UNIT_ID,"
                + "c.CONSUMPTION_DATE, "
                + "c.DAYS_OF_STOCK_OUT,"
                + "c.DATA_SOURCE_ID,"
                + "c.NOTES, "
                + "c.CONSUMPTION_QUANTITY,"
                + "c.ACTUAL_FLAG ,"
                + "c.REALM_COUNTRY_PLANNING_UNIT_ID,"
                + "c.MULTIPLIER ,"
                + "1 as ConsNumMonths "
                + "from qat_temp_consumption c "
                + "where c.PIPELINE_ID=:pipelineId;";
        List<QatTempConsumption> consumptionList = this.namedParameterJdbcTemplate.query(sqlQatTemp, params, new QatTempConsumptionRowMapper());
        if (consumptionList.size() == 0) {
            String sql = "SELECT  "
                    + "If(rds.DATA_SOURCE_ID IS NULL,ad.DataSourceName,rds.DATA_SOURCE_ID) as DATA_SOURCE_ID, "
                    + "date_format(CONCAT(c.ConsStartYear,\"-\",c.ConsStartMonth,\"-01\"),\"%Y-%m-%d\") as CONSUMPTION_DATE, "
                    + "c.ConsActualFlag as ACTUAL_FLAG,"
                    //                    + "c.ConsAmount as CONSUMPTION_QUANTITY,"
                    + "(c.ConsAmount*qtp.MULTIPLIER) as CONSUMPTION_QUANTITY,"
                    + "c.ConsNote as NOTES,"
                    + "0 as DAYS_OF_STOCK_OUT,"
                    + " '' as REGION_ID,"
                    + "qtp.PLANNING_UNIT_ID as PLANNING_UNIT_ID "
                    + " , c.ConsNumMonths, "
                    + " COALESCE(rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,0) as REALM_COUNTRY_PLANNING_UNIT_ID,"
                    + " COALESCE(rcpu.MULTIPLIER,1) as MULTIPLIER "
                    + "FROM fasp.adb_consumption c "
                    + "left join adb_datasource ad on ad.DataSourceID=c.ConsDataSourceID AND ad.PIPELINE_ID=:pipelineId "
                    + "left join qat_temp_data_source qtds on qtds.PIPELINE_DATA_SOURCE_ID=ad.DataSourceID AND qtds.PIPELINE_ID=:pipelineId "
                    + "left join qat_temp_program_planning_unit qtp on qtp.PIPELINE_PRODUCT_ID=c.ProductID and qtp.PIPELINE_ID=:pipelineId "
                    + "left join rm_data_source rds on qtds.DATA_SOURCE_ID=rds.DATA_SOURCE_ID  "
                    + "left join qat_temp_program tp on tp.PIPELINE_ID=:pipelineId  "
                    + "left join rm_realm_country_planning_unit rcpu on rcpu.PLANNING_UNIT_ID=qtp.PLANNING_UNIT_ID and  rcpu.REALM_COUNTRY_ID=tp.REALM_COUNTRY_ID"
                    + " where c.PIPELINE_ID=:pipelineId ;";
            params.put("pipelineId", pipelineId);
            return this.namedParameterJdbcTemplate.query(sql, params, new QatTempConsumptionRowMapper());
        } else {
            return consumptionList;
        }
    }

    @Override
    public List<Region> getQatTempRegionsById(CustomUserDetails curUser, int pipelineId) {
        Map<String, Object> params = new HashMap<>();
        String sql = "SELECT r.REGION_ID,al.LABEL_EN,al.LABEL_FR,al.LABEL_SP,al.LABEL_PR "
                + "FROM fasp.qat_temp_program_region pr "
                + "left join rm_region r on r.REGION_ID=pr.REGION_ID "
                + "left join ap_label al on al.LABEL_ID=r.LABEL_ID "
                + "where pr.PIPELINE_ID=:pipelineId";
        params.put("pipelineId", pipelineId);
        return this.namedParameterJdbcTemplate.query(sql, params, new QatTemRegionRowMapper());
    }

    @Override
    @Transactional
    public int saveQatTempConsumption(QatTempConsumption[] consumption, CustomUserDetails curUser, int pipelineId) {
        String sql = " delete from qat_temp_consumption  where PIPELINE_ID=?";
        this.jdbcTemplate.update(sql, pipelineId);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_consumption");
        List<SqlParameterSource> insertList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (QatTempConsumption ppu : consumption) {
            // Insert
            params = new HashMap<>();
            params.put("REGION_ID", ppu.getRegionId());
            params.put("PLANNING_UNIT_ID", ppu.getPlanningUnitId());
            params.put("DATA_SOURCE_ID", ppu.getDataSourceId());
            params.put("CONSUMPTION_DATE", ppu.getConsumptionDate());
            params.put("DAYS_OF_STOCK_OUT", ppu.getDayOfStockOut());
            params.put("NOTES", ppu.getNotes());
            params.put("CONSUMPTION_QUANTITY", ppu.getConsumptionQty());
            params.put("PIPELINE_ID", pipelineId);
            params.put("ACTUAL_FLAG", ppu.isActualFlag());
            params.put("REALM_COUNTRY_PLANNING_UNIT_ID", ppu.getRealmCountryPlanningUnitId());
            params.put("MULTIPLIER", ppu.getMultiplier());

            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            insertList.add(new MapSqlParameterSource(params));

        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public String getPipelineInventoryById(CustomUserDetails curUser, int pipelineId) {
        Map<String, Object> params = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        params.put("pipelineId", pipelineId);
        String sql1 = "SELECT "
                + "i.ACTUAL_QTY,"
                + "i.ADJUSTMENT_QTY,"
                + "i.DATA_SOURCE_ID, "
                + "i.INVENTORY_DATE,"
                + "i.REGION_ID,"
                + "i.NOTES, "
                + "i.PLANNING_UNIT_ID,"
                + "i.REGION_ID , "
                + "i.REALM_COUNTRY_PLANNING_UNIT_ID,"
                + "i.MULTIPLIER "
                + "FROM fasp.qat_temp_inventory i where i.PIPELINE_ID=:pipelineId;";

        List<QatTempInventory> result = this.namedParameterJdbcTemplate.query(sql1, params, new QatInventoryRowMapper());

        if (result.size() == 0) {
            String sql = "select "
                    + "If(rds.DATA_SOURCE_ID IS NULL,ad.DataSourceName,rds.DATA_SOURCE_ID) as DATA_SOURCE_ID, "
                    //                    + "ad.DataSourceName,"
                    //                    + "i.ProductID,"
                    + "qtp.PLANNING_UNIT_ID as PLANNING_UNIT_ID,"
                    + "i.InvNote as NOTES,"
                    //                    + "if(InvTransferFlag=0,i.InvAmount,null) as ACTUAL_QTY , "
                    //                    + "if(InvTransferFlag=1,i.InvAmount,null) as ADJUSTMENT_QTY , "
                    + "if(InvTransferFlag=0,i.InvAmount * qtp.MULTIPLIER,null) as ACTUAL_QTY , "
                    + "if(InvTransferFlag=1,i.InvAmount * qtp.MULTIPLIER,null) as ADJUSTMENT_QTY , "
                    + "date_format(CONCAT(i.Period),\"%Y-%m-%d\") as INVENTORY_DATE,'' as REGION_ID,  "
                    + " COALESCE(rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,0) as REALM_COUNTRY_PLANNING_UNIT_ID,"
                    + " COALESCE(rcpu.MULTIPLIER,1) as MULTIPLIER "
                    + "from adb_inventory i "
                    + "left join adb_datasource ad on ad.DataSourceID=i.InvDataSourceID and ad.PIPELINE_ID=:pipelineId "
                    + "left join qat_temp_data_source qtds on qtds.PIPELINE_DATA_SOURCE_ID=ad.DataSourceID AND qtds.PIPELINE_ID=:pipelineId "
                    + "left join rm_data_source rds on qtds.DATA_SOURCE_ID=rds.DATA_SOURCE_ID  "
                    + "left join qat_temp_program_planning_unit qtp on qtp.PIPELINE_PRODUCT_ID=i.ProductID AND qtp.PIPELINE_ID=:pipelineId "
                    + "left join qat_temp_program tp on tp.PIPELINE_ID=:pipelineId  "
                    + "left join rm_realm_country_planning_unit rcpu on rcpu.PLANNING_UNIT_ID=qtp.PLANNING_UNIT_ID and  rcpu.REALM_COUNTRY_ID=tp.REALM_COUNTRY_ID"
                    + " where i.PIPELINE_ID=:pipelineId";
            params.put("pipelineId", pipelineId);
            return gson.toJson(this.namedParameterJdbcTemplate.query(sql, params, new QatInventoryRowMapper()));
        } else {
            return gson.toJson(result);
        }

    }

    @Override
    @Transactional
    public int saveQatTempInventory(QatTempInventory[] inventory, CustomUserDetails curUser, int pipelineId) {
        String sql = " delete from qat_temp_inventory  where PIPELINE_ID=?";
        this.jdbcTemplate.update(sql, pipelineId);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_inventory");
        List<SqlParameterSource> insertList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (QatTempInventory ppu : inventory) {
            // Insert
            params = new HashMap<>();
            params.put("REGION_ID", ppu.getRegionId());
            params.put("PLANNING_UNIT_ID", ppu.getPlanningUnitId());
            params.put("DATA_SOURCE_ID", ppu.getDataSourceId());
            params.put("INVENTORY_DATE", ppu.getInventoryDate());
            params.put("NOTES", ppu.getNotes());
            params.put("ACTUAL_QTY", ppu.getInventory());
            params.put("ADJUSTMENT_QTY", ppu.getManualAdjustment());
            params.put("PIPELINE_ID", pipelineId);
            params.put("REALM_COUNTRY_PLANNING_UNIT_ID", ppu.getRealmCountryPlanningUnitId());
            params.put("MULTIPLIER", ppu.getMultiplier());

            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            insertList.add(new MapSqlParameterSource(params));

        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<QatTempPlanningUnitInventoryCount> getQatTempPlanningUnitListInventoryCount(int pipelineId, CustomUserDetails curUser) {
        String sql = "SELECT al.LABEL_ID,pu.PLANNING_UNIT_ID,al.LABEL_EN,al.LABEL_FR, "
                + "al.LABEL_PR,al.LABEL_SP, "
                + "(if(i.ACTUAL_QTY=0,coalesce(SUM(i.ADJUSTMENT_QTY),0),0)+ coalesce(SUM(i.ACTUAL_QTY),0) "
                + "+coalesce(SUM(s.QUANTITY),0)- "
                + "coalesce(SUM(c.consumptionQty),0)) as finalInventory "
                + "FROM fasp.qat_temp_program_planning_unit pu "
                + "left join rm_planning_unit rmp on rmp.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "left join ap_label al on al.LABEL_ID=rmp.LABEL_ID "
                + "left join qat_temp_inventory i on i.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "left join qat_temp_shipment s on s.PLANNING_UNIT_ID =pu.PLANNING_UNIT_ID  "
                + "left join (select a.PLANNING_UNIT_ID,coalesce(if(a.co=2,(select qc.CONSUMPTION_QUANTITY from qat_temp_consumption qc where qc.PIPELINE_ID=:pipelineId "
                + "and qc.CONSUMPTION_DATE=a.CONSUMPTION_DATE and qc.PLANNING_UNIT_ID=a.PLANNING_UNIT_ID "
                + "and qc.ACTUAL_FLAG=1),(select qc.CONSUMPTION_QUANTITY from qat_temp_consumption qc where qc.PIPELINE_ID=:pipelineId  "
                + "and qc.CONSUMPTION_DATE=a.CONSUMPTION_DATE and qc.PLANNING_UNIT_ID=a.PLANNING_UNIT_ID)),0) as consumptionQty "
                + "from (select count(*) as co ,c.CONSUMPTION_DATE,c.ACTUAL_FLAG,c.PLANNING_UNIT_ID from qat_temp_consumption  "
                + "c where c.PIPELINE_ID=:pipelineId  "
                + "group by c.PLANNING_UNIT_ID,c.CONSUMPTION_DATE order by c.CONSUMPTION_DATE)as a) c on c.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID "
                + "where pu.PIPELINE_ID=:pipelineId "
                + "group by pu.PLANNING_UNIT_ID;";

        Map<String, Object> params = new HashMap<>();
        params.put("pipelineId", pipelineId);
        return this.namedParameterJdbcTemplate.query(sql, params, new QatTempPlanningUnitInventoryCountMapper());
    }

    @Override
    @Transactional
    public int finalSaveProgramData(int pipelineId, CustomUserDetails curUser) {
        QatTempProgram p = this.getQatTempProgram(curUser, pipelineId);
//        String pCode = p.getProgramCode();
//        RealmCountry rc = this.realmCountryService.getRealmCountryById(p.getRealmCountry().getRealmCountryId(), curUser);
//        String programCode = !"".equals(pCode) ? rc.getCountry().getCountryCode() + "-" + this.healthAreaDao.getHealthAreaById(p.getHealthArea().getId(), curUser).getHealthAreaCode() + "-" + this.organisationDao.getOrganisationById(p.getOrganisation().getId(), curUser).getOrganisationCode() + "-" + p.getProgramCode() : rc.getCountry().getCountryCode() + "-" + this.healthAreaDao.getHealthAreaById(p.getHealthArea().getId(), curUser).getHealthAreaCode() + "-" + this.organisationDao.getOrganisationById(p.getOrganisation().getId(), curUser).getOrganisationCode();
//        p.setProgramCode(programCode);
        RealmCountry rc = this.realmCountryService.getRealmCountryById(p.getRealmCountry().getRealmCountryId(), curUser);
        StringBuilder healthAreaCode = new StringBuilder();
        for (int haId : p.getHealthAreaIdList()) {
            healthAreaCode.append(this.healthAreaDao.getHealthAreaById(haId, curUser).getHealthAreaCode() + "/");
        }
        StringBuilder programCode = new StringBuilder(rc.getCountry().getCountryCode()).append("-").append(healthAreaCode.substring(0, healthAreaCode.length() - 1)).append("-").append(this.organisationDao.getOrganisationById(p.getOrganisation().getId(), curUser).getOrganisationCode());
        if (p.getProgramCode() != null && !p.getProgramCode().isBlank()) {
            programCode.append("-").append(p.getProgramCode());
        }
        p.setProgramCode(programCode.toString());

        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int labelId = this.labelDao.addLabel(p.getLabel(), LabelConstants.RM_PROGRAM, curUser.getUserId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_program").usingColumns("PROGRAM_CODE", "REALM_ID", "REALM_COUNTRY_ID", "ORGANISATION_ID", "LABEL_ID", "PROGRAM_MANAGER_USER_ID", "PROGRAM_NOTES", "AIR_FREIGHT_PERC", "SEA_FREIGHT_PERC", "PLANNED_TO_SUBMITTED_LEAD_TIME", "SUBMITTED_TO_APPROVED_LEAD_TIME", "APPROVED_TO_SHIPPED_LEAD_TIME", "SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME", "SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME", "ARRIVED_TO_DELIVERED_LEAD_TIME", "CURRENT_VERSION_ID", "ACTIVE", "CREATED_BY", "CREATED_DATE", "LAST_MODIFIED_BY", "LAST_MODIFIED_DATE").usingGeneratedKeyColumns("PROGRAM_ID");
        params.put("PROGRAM_CODE", p.getProgramCode());
        params.put("REALM_ID", rc.getRealm().getRealmId());
        params.put("REALM_COUNTRY_ID", p.getRealmCountry().getRealmCountryId());
        params.put("ORGANISATION_ID", p.getOrganisation().getId());
//        params.put("HEALTH_AREA_ID", p.getHealthArea().getId());
        params.put("LABEL_ID", labelId);
        params.put("PROGRAM_MANAGER_USER_ID", p.getProgramManager().getUserId());
        params.put("PROGRAM_NOTES", p.getProgramNotes());
        params.put("AIR_FREIGHT_PERC", p.getAirFreightPerc());
        params.put("SEA_FREIGHT_PERC", p.getSeaFreightPerc());
        params.put("PLANNED_TO_SUBMITTED_LEAD_TIME", p.getPlannedToSubmittedLeadTime());
        params.put("SUBMITTED_TO_APPROVED_LEAD_TIME", p.getSubmittedToApprovedLeadTime());
        params.put("APPROVED_TO_SHIPPED_LEAD_TIME", p.getApprovedToShippedLeadTime());
        params.put("SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME", p.getShippedToArrivedBySeaLeadTime());
        params.put("SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME", p.getShippedToArrivedByAirLeadTime());
        params.put("ARRIVED_TO_DELIVERED_LEAD_TIME", p.getArrivedToDeliveredLeadTime());
        params.put("CURRENT_VERSION_ID", null);
        params.put("ACTIVE", true);
        params.put("PROGRAM_TYPE_ID", GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN); // Hard Coded for SupplyPlan Program
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int programId = si.executeAndReturnKey(params).intValue();
//        System.out.println("" + programId);
        si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_region");
        SqlParameterSource[] paramList = new SqlParameterSource[p.getRegionArray().length];
        int i = 0;
        for (String rId : p.getRegionArray()) {
            params = new HashMap<>();
            params.put("REGION_ID", rId);
            params.put("PROGRAM_ID", programId);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ACTIVE", true);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
//        params.clear();

        si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_program_health_area");
        SqlParameterSource[] paramListHA = new SqlParameterSource[p.getHealthAreaArray().length];
        int iha = 0;
//        System.out.println("p.getHealthAreaArray()" + Arrays.toString(p.getHealthAreaArray()));
        for (String rId : p.getHealthAreaArray()) {
            params = new HashMap<>();
            params.put("HEALTH_AREA_ID", rId);
            params.put("PROGRAM_ID", programId);
//            params.put("CREATED_BY", curUser.getUserId());
//            params.put("CREATED_DATE", curDate);
//            params.put("LAST_MODIFIED_BY", curUser.getUserId());
//            params.put("LAST_MODIFIED_DATE", curDate);
//            params.put("ACTIVE", true);
            paramListHA[iha] = new MapSqlParameterSource(params);
            iha++;
        }
        si.executeBatch(paramListHA);
        params.clear();

        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("programId", programId);
        params.put("versionTypeId", 1);
        params.put("versionStatusId", 1);
        params.put("notes", "testing.............");
        Version version = this.namedParameterJdbcTemplate.queryForObject("CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, null, null, null, null, :curUser, :curDate, 1)", params, new VersionRowMapper());

        params.put("versionId", version.getVersionId());

        this.namedParameterJdbcTemplate.update("UPDATE rm_program SET CURRENT_VERSION_ID=:versionId WHERE PROGRAM_ID=:programId", params);

        List<QatTempProgramPlanningUnit> programPlanningUnits = this.getQatTempPlanningUnitListByPipelienId(pipelineId, curUser);
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_program_planning_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        int rowsEffected = 0;
        for (QatTempProgramPlanningUnit ppu : programPlanningUnits) {
            params.put("PLANNING_UNIT_ID", ppu.getPlanningUnitId());
            params.put("PROGRAM_ID", programId);
            params.put("REORDER_FREQUENCY_IN_MONTHS", ppu.getReorderFrequencyInMonths());
            params.put("MIN_MONTHS_OF_STOCK", ppu.getMinMonthsOfStock());
            params.put("LOCAL_PROCUREMENT_LEAD_TIME", ppu.getLocalProcurmentLeadTime()); //ppu.getLocalProcurementLeadTime());
            params.put("SHELF_LIFE", ppu.getShelfLife());
            params.put("CATALOG_PRICE", ppu.getCatalogPrice());
            params.put("MONTHS_IN_PAST_FOR_AMC", ppu.getMonthsInPastForAmc());
            params.put("MONTHS_IN_FUTURE_FOR_AMC", ppu.getMonthsInFutureForAmc());
            params.put("SHELF_LIFE", ppu.getShelfLife());
            params.put("CATALOG_PRICE", ppu.getCatalogPrice());
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("ACTIVE", ppu.isActive());
            insertList.add(new MapSqlParameterSource(params));

        }
        SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
        rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        rowsEffected = 0;
        params.clear();
        /**
         * *****************Consumption Insert*******************
         */

        String sqlQatTemp = "select "
                + "c.REGION_ID,"
                + "c.PLANNING_UNIT_ID,"
                + "c.CONSUMPTION_DATE, "
                + "c.DAYS_OF_STOCK_OUT,"
                + "c.DATA_SOURCE_ID,"
                + "c.NOTES, "
                //                + " (c.CONSUMPTION_QUANTITY*qtp.MULTIPLIER) CONSUMPTION_QUANTITY,"
                + " c.CONSUMPTION_QUANTITY CONSUMPTION_QUANTITY,"
                + "c.ACTUAL_FLAG ,"
                + "rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,"
                + "c.MULTIPLIER,  "
                + "1 as ConsNumMonths "
                + "from qat_temp_consumption c"
                + " left join rm_realm_country_planning_unit rcpu on rcpu.PLANNING_UNIT_ID=c.PLANNING_UNIT_ID and rcpu.REALM_COUNTRY_ID=:realmCountryId"
                + " left join qat_temp_program_planning_unit qtp on qtp.PLANNING_UNIT_ID=c.PLANNING_UNIT_ID and qtp.PIPELINE_ID =:pipelineId "
                + "where c.PIPELINE_ID=:pipelineId;";
        params.put("pipelineId", pipelineId);
        params.put("realmCountryId", p.getRealmCountry().getRealmCountryId());
        List<QatTempConsumption> pipelineConsumptions = this.namedParameterJdbcTemplate.query(sqlQatTemp, params, new QatTempConsumptionRowMapper());
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_consumption");
        SimpleJdbcInsert si_trans = new SimpleJdbcInsert(dataSource).withTableName("rm_consumption_trans");
        for (QatTempConsumption c : pipelineConsumptions) {
            params.put("PROGRAM_ID", programId);
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("ACTIVE", true);
            params.put("MAX_VERSION_ID", version.getVersionId());
            int result = si.execute(params);
            String sqlString = "SELECT LAST_INSERT_ID()";
            params.put("CONSUMPTION_ID", this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class));
            params.put("REALM_COUNTRY_PLANNING_UNIT_ID", c.getRealmCountryPlanningUnitId());
            params.put("MULTIPLIER", c.getMultiplier());

            params.put("REGION_ID", c.getRegionId());
            params.put("PLANNING_UNIT_ID", c.getPlanningUnitId());
            params.put("CONSUMPTION_DATE", c.getConsumptionDate());
            params.put("CONSUMPTION_QTY", c.getConsumptionQty());
            params.put("CONSUMPTION_RCPU_QTY", c.getConsumptionQty());
            params.put("ACTUAL_FLAG", c.isActualFlag());
            params.put("DAYS_OF_STOCK_OUT", c.getDayOfStockOut());
            params.put("DATA_SOURCE_ID", c.getDataSourceId());
            params.put("NOTES", c.getNotes());
            params.put("ACTIVE", true);
            params.put("VERSION_ID", version.getVersionId());
            rowsEffected = +si_trans.execute(params);
            params.clear();
        }
        /**
         * *************************Shipment,budget and shipment-budget
         * Insert**********************************
         */

        String sql = "SELECT s.`FUNDING_SOURCE_ID`,fs.`FUNDING_SOURCE_CODE`,SUM(IFNULL(s.`FREIGHT_COST`,0)+IFNULL(s.`PRODUCT_COST`,0)) budget,EXTRACT(YEAR FROM MAX(now())) `year` FROM qat_temp_shipment s "
                + " LEFT JOIN  rm_funding_source fs ON fs.`FUNDING_SOURCE_ID`=s.`FUNDING_SOURCE_ID`"
                + " WHERE s.`PIPELINE_ID`=:pipelineId GROUP BY s.`FUNDING_SOURCE_ID`";
        params.put("pipelineId", pipelineId);
        List<Map<String, Object>> budgetList = this.namedParameterJdbcTemplate.queryForList(sql, params);
//        System.out.println("budget list=======>" + budgetList);
        List<Map<String, Object>> newList = new LinkedList<>();
        params.clear();
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_budget").usingGeneratedKeyColumns("BUDGET_ID");

        for (Map<String, Object> budget : budgetList) {
//            String BudgetName = this.realmCountryService.getRealmCountryById(p.getRealmCountry().getRealmCountryId(), curUser).getCountry().getLabel().getLabel_en() + "-" + this.healthAreaDao.getHealthAreaById(p.getHealthArea().getId(), curUser).getHealthAreaCode() + "-" + budget.get("FUNDING_SOURCE_CODE").toString();
            String BudgetName = this.realmCountryService.getRealmCountryById(p.getRealmCountry().getRealmCountryId(), curUser).getCountry().getLabel().getLabel_en() + "-" + budget.get("FUNDING_SOURCE_CODE").toString();
            Label l = new Label();
            l.setLabel_en(BudgetName);
            labelId = this.labelDao.addLabel(l, LabelConstants.RM_BUDGET, curUser.getUserId());
            params.put("BUDGET_CODE", "ABC");
            params.put("PROGRAM_ID", programId);
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("FUNDING_SOURCE_ID", budget.get("FUNDING_SOURCE_ID"));
            params.put("LABEL_ID", labelId);
            params.put("START_DATE", budget.get("year").toString() + "-01-01");
            params.put("STOP_DATE", budget.get("year").toString() + "-12-31");
            params.put("CURRENCY_ID", 1);
            params.put("BUDGET_AMT", budget.get("budget"));
            params.put("CONVERSION_RATE_TO_USD", 1);
            params.put("NOTES", "");
            params.put("ACTIVE", true);
            int result = si.executeAndReturnKey(params).intValue();

            String sqlString = "update rm_budget rb  "
                    + "left join rm_program p on p.PROGRAM_ID=rb.PROGRAM_ID "
                    + "left join rm_realm_country rc on rc.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID "
                    + "left join ap_country ac on ac.COUNTRY_ID=rc.COUNTRY_ID "
                    + "set rb.BUDGET_CODE=concat(ac.COUNTRY_CODE,rb.BUDGET_ID) "
                    + "where rb.BUDGET_ID=?;";
            this.jdbcTemplate.update(sqlString, result);

            budget.put("budgetId", result);
            newList.add(budget);
        }
        params.clear();

        rowsEffected = 0;

        params.put("pipelineId", pipelineId);
        sql = "SELECT "
                + "		st.SHIPMENT_ID, st.EXPECTED_DELIVERY_DATE, "
                + "st.ORDERED_DATE, st.SHIPPED_DATE, st.RECEIVED_DATE,"
                + "st.PLANNED_DATE, "
                + "now() SUBMITTED_DATE,"
                + "now() APPROVED_DATE,"
                + "now() ARRIVED_DATE,"
                //                + " (st.QUANTITY * qtp.MULTIPLIER) QUANTITY, "
                + " st.QUANTITY  QUANTITY, "
                + "st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.SHIPPING_MODE,"
                //                + "( st.SUGGESTED_QTY * qtp.MULTIPLIER) SUGGESTED_QTY, "
                + "st.SUGGESTED_QTY  SUGGESTED_QTY, "
                + "'0' ACCOUNT_FLAG, '0'ERP_FLAG, st.NOTES, "
                + "		0 VERSION_ID , "
                + "		st.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pal.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pal.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pal.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pal.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pal.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, "
                + "		st.PLANNING_UNIT_ID, pul.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pul.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pul.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pul.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pul.LABEL_PR `PLANNING_UNIT_LABEL_PR`, "
                + "		fu.FORECASTING_UNIT_ID, ful.LABEL_ID `FORECASTING_UNIT_LABEL_ID`, ful.LABEL_EN `FORECASTING_UNIT_LABEL_EN`, ful.LABEL_FR `FORECASTING_UNIT_LABEL_FR`, ful.LABEL_SP `FORECASTING_UNIT_LABEL_SP`, ful.LABEL_PR `FORECASTING_UNIT_LABEL_PR`, "
                + "		pc.PRODUCT_CATEGORY_ID, pcl.LABEL_ID `PRODUCT_CATEGORY_LABEL_ID`, pcl.LABEL_EN `PRODUCT_CATEGORY_LABEL_EN`, pcl.LABEL_FR `PRODUCT_CATEGORY_LABEL_FR`, pcl.LABEL_SP `PRODUCT_CATEGORY_LABEL_SP`, pcl.LABEL_PR `PRODUCT_CATEGORY_LABEL_PR`, "
                + "		st.PROCUREMENT_UNIT_ID, prul.LABEL_ID `PROCUREMENT_UNIT_LABEL_ID`, prul.LABEL_EN `PROCUREMENT_UNIT_LABEL_EN`, prul.LABEL_FR `PROCUREMENT_UNIT_LABEL_FR`, prul.LABEL_SP `PROCUREMENT_UNIT_LABEL_SP`, prul.LABEL_PR `PROCUREMENT_UNIT_LABEL_PR`, "
                + "        st.SUPPLIER_ID, sul.LABEL_ID `SUPPLIER_LABEL_ID`, sul.LABEL_EN `SUPPLIER_LABEL_EN`, sul.LABEL_FR `SUPPLIER_LABEL_FR`, sul.LABEL_SP `SUPPLIER_LABEL_SP`, sul.LABEL_PR `SUPPLIER_LABEL_PR`, "
                + "        st.SHIPMENT_STATUS_ID, shsl.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shsl.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shsl.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shsl.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shsl.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, "
                + "        st.DATA_SOURCE_ID, dsl.LABEL_ID `DATA_SOURCE_LABEL_ID`, dsl.LABEL_EN `DATA_SOURCE_LABEL_EN`, dsl.LABEL_FR `DATA_SOURCE_LABEL_FR`, dsl.LABEL_SP `DATA_SOURCE_LABEL_SP`, dsl.LABEL_PR `DATA_SOURCE_LABEL_PR`, "
                + "        st.FUNDING_SOURCE_ID, fsl.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fsl.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fsl.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fsl.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fsl.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, "
                + "		cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, st.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, st.LAST_MODIFIED_DATE, st.ACTIVE "
                + "   	FROM  qat_temp_shipment st  "
                + "	LEFT JOIN rm_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
                + "	LEFT JOIN ap_label pal ON pa.LABEL_ID=pal.LABEL_ID "
                + "	LEFT JOIN rm_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "	LEFT JOIN ap_label pul ON pu.LABEL_ID=pul.LABEL_ID "
                + "	LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "	LEFT JOIN ap_label ful ON fu.LABEL_ID=ful.LABEL_ID "
                + "	LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "	LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID "
                + "    LEFT JOIN rm_procurement_unit pru ON st.PROCUREMENT_UNIT_ID=pru.PROCUREMENT_UNIT_ID "
                + "    LEFT JOIN ap_label prul ON pru.LABEL_ID=prul.LABEL_ID "
                + "	LEFT JOIN rm_supplier su ON st.SUPPLIER_ID=su.SUPPLIER_ID "
                + "    LEFT JOIN ap_label sul ON su.LABEL_ID=sul.LABEL_ID "
                + "    LEFT JOIN ap_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID "
                + "    LEFT JOIN ap_label shsl ON shs.LABEL_ID=shsl.LABEL_ID  "
                + "    LEFT JOIN rm_data_source ds ON st.DATA_SOURCE_ID=ds.DATA_SOURCE_ID "
                + "	LEFT JOIN ap_label dsl ON ds.LABEL_ID=dsl.LABEL_ID "
                + "    LEFT JOIN rm_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
                + "	LEFT JOIN ap_label fsl ON fs.LABEL_ID=fsl.LABEL_ID "
                + "LEFT JOIN us_user cb ON st.CREATED_BY=cb.USER_ID "
                + "	LEFT JOIN us_user lmb ON st.LAST_MODIFIED_BY=lmb.USER_ID"
                + " left join qat_temp_program_planning_unit qtp on qtp.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID and qtp.PIPELINE_ID =:pipelineId "
                + "	WHERE st.`PIPELINE_ID`=:pipelineId";
        List<QatTempShipment> pipelineShipments = this.namedParameterJdbcTemplate.query(sql, params, new QatTempShipmentRowMapper());

        si = new SimpleJdbcInsert(dataSource).withTableName("rm_shipment");
        si_trans = new SimpleJdbcInsert(dataSource).withTableName("rm_shipment_trans");
        SimpleJdbcInsert si_batchInfo = new SimpleJdbcInsert(dataSource).withTableName("rm_batch_info");
        SimpleJdbcInsert si_batchInfo_trans = new SimpleJdbcInsert(dataSource).withTableName("rm_shipment_trans_batch_info");
// SimpleJdbcInsert si_shipment_budget = new SimpleJdbcInsert(dataSource).withTableName("rm_shipment_budget");
        int ShipmentIds[] = new int[pipelineShipments.size()];
        int j = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyMMdd");

        for (QatTempShipment s : pipelineShipments) {

            params.put("PROGRAM_ID", programId);
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("FUNDING_SOURCE_ID", s.getFundingSource());
            params.put("SUGGESTED_QTY", s.getSuggestedQty());
            params.put("CURRENCY_ID", 1);
            params.put("CONVERSION_RATE_TO_USD", 1);
            params.put("ACTIVE", true);
            params.put("MAX_VERSION_ID", version.getVersionId());
            int result = si.execute(params);
            String sqlString = "SELECT LAST_INSERT_ID()";
            int shipmentId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
            String batchNumbers = "QAT" + String.format("%06d", programId) + String.format("%07d", Integer.parseInt(s.getPlanningUnit())) + df.format(curDate) + getAlphaNumeric(4);
//            System.out.println("=====>" + batchNumbers);
            params.put("BATCH_NO", batchNumbers);
            params.put("PLANNING_UNIT_ID", s.getPlanningUnit());
            params.put("pipelineId", pipelineId);
            sql = "SELECT SHELF_LIFE FROM qat_temp_program_planning_unit WHERE  PLANNING_UNIT_ID=:PLANNING_UNIT_ID AND PIPELINE_ID=:pipelineId";
            int shelfLife = this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);

            Calendar expiryDate = Calendar.getInstance();
            expiryDate.setTime(s.getExpectedDeliveryDate());
            expiryDate.add(Calendar.MONTH, shelfLife);
            expiryDate.set(Calendar.DAY_OF_MONTH, 1);
            params.put("EXPIRY_DATE", expiryDate.getTime());
            params.put("AUTO_GENERATED", true);
            si_batchInfo.execute(params);
            sqlString = "SELECT LAST_INSERT_ID()";
            int batchId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
            params.put("SHIPMENT_ID", shipmentId);
            params.put("EXPECTED_DELIVERY_DATE", s.getExpectedDeliveryDate());
            params.put("PROCUREMENT_AGENT_ID", s.getProcurementAgent());
            params.put("PROCUREMENT_UNIT_ID", s.getProcurementUnit());
            params.put("SUPPLIER_ID", s.getSupplier());
            params.put("SHIPMENT_QTY", s.getQuantity());
            params.put("RATE", s.getRate());
            params.put("PRODUCT_COST", s.getProductCost());
            params.put("SHIPMENT_MODE", s.getShipmentMode() == "Air" ? s.getShipmentMode() : "Sea");
            params.put("FREIGHT_COST", s.getFreightCost());

            params.put("PLANNED_DATE", s.getPlannedDate());
            params.put("SUBMITTED_DATE", s.getSubmittedDate());
            params.put("APPROVED_DATE", s.getApprovedDate());

            params.put("SHIPPED_DATE", s.getShippedDate());
            params.put("ARRIVED_DATE", s.getArrivedDate());
            params.put("RECEIVED_DATE", s.getReceivedDate());
            params.put("SHIPMENT_STATUS_ID", s.getShipmentStatus());
            params.put("NOTES", s.getNotes());
            params.put("DATA_SOURCE_ID", s.getDataSource());
            params.put("ACCOUNT_FLAG", 1);
            params.put("ERP_FLAG", 0);
            params.put("EMERGENCY_ORDER", false);
            params.put("ORDER_NO", null);
            params.put("PRIME_LINE_NO", null);
            params.put("ACTIVE", true);
            params.put("VERSION_ID", version.getVersionId());

//            params.clear();
            params.put("SHIPMENT_ID", shipmentId);
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            for (Map<String, Object> b : newList) {
                if (b.get("FUNDING_SOURCE_ID").toString().equalsIgnoreCase(s.getFundingSource())) {
                    params.put("BUDGET_ID", b.get("budgetId"));
                    break;
                }

            }
            params.put("BUDGET_AMT", s.getFreightCost() + s.getProductCost());
            params.put("CURRENCY_ID", 1);
            params.put("CONVERSION_RATE_TO_USD", 1);
            params.put("VERSION_ID", version.getVersionId());
            params.put("ACTIVE", true);
            params.put("LOCAL_PROCUREMENT", false);
            rowsEffected = +si_trans.execute(params);
//            result = si_shipment_budget.execute(params);
            sqlString = "SELECT LAST_INSERT_ID()";
            int shipmentTransId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
            params.put("BATCH_ID", batchId);
            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
            params.put("BATCH_SHIPMENT_QTY", s.getQuantity());
            si_batchInfo_trans.execute(params);
            params.clear();
        }
        /**
         * *****************Inventory Insert*******************
         */
        rowsEffected = 0;
        String sql1 = "SELECT "
                //                + "(i.ACTUAL_QTY*qtp.MULTIPLIER) ACTUAL_QTY,"
                + "i.ACTUAL_QTY ACTUAL_QTY,"
                //                + "(i.ADJUSTMENT_QTY*qtp.MULTIPLIER) ADJUSTMENT_QTY,"
                + "i.ADJUSTMENT_QTY ADJUSTMENT_QTY,"
                + "i.DATA_SOURCE_ID, "
                + "i.INVENTORY_DATE,"
                + "i.REGION_ID,"
                + "i.PLANNING_UNIT_ID,"
                + "i.NOTES, "
                + "rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,"
                + "i.REGION_ID,  "
                + "i.MULTIPLIER "
                + "FROM fasp.qat_temp_inventory i"
                + " left join rm_realm_country_planning_unit rcpu on rcpu.PLANNING_UNIT_ID=i.PLANNING_UNIT_ID and rcpu.REALM_COUNTRY_ID=:realmCountryId"
                + " left join qat_temp_program_planning_unit qtp on qtp.PLANNING_UNIT_ID=i.PLANNING_UNIT_ID and qtp.PIPELINE_ID =:pipelineId "
                + " where i.PIPELINE_ID=:pipelineId;";

        params.put("pipelineId", pipelineId);
        params.put("realmCountryId", p.getRealmCountry().getRealmCountryId());
        List<QatTempInventory> pipelineInventorys = this.namedParameterJdbcTemplate.query(sql1, params, new QatInventoryRowMapper());
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_inventory");
        si_trans = new SimpleJdbcInsert(dataSource).withTableName("rm_inventory_trans");
        for (QatTempInventory inv : pipelineInventorys) {
            params.put("PROGRAM_ID", programId);
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("ACTIVE", true);
            params.put("MAX_VERSION_ID", version.getVersionId());
            int result = si.execute(params);
            String sqlString = "SELECT LAST_INSERT_ID()";
            params.put("INVENTORY_ID", this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class));
            params.put("REGION_ID", inv.getRegionId());
            params.put("REALM_COUNTRY_PLANNING_UNIT_ID", inv.getRealmCountryPlanningUnitId());
            params.put("MULTIPLIER", inv.getMultiplier());
            params.put("INVENTORY_DATE", inv.getInventoryDate());
            params.put("ACTUAL_QTY", inv.getInventory());
            params.put("ADJUSTMENT_QTY", inv.getManualAdjustment());
            params.put("DATA_SOURCE_ID", inv.getDataSourceId());
            params.put("NOTES", inv.getNotes());
            params.put("ACTIVE", true);
            params.put("VERSION_ID", version.getVersionId());
//            System.out.println("param" + params);
            rowsEffected = +si_trans.execute(params);
            params.clear();
        }

        sql = "UPDATE`adb_pipeline` p SET p.`STATUS`=1 WHERE p.`PIPELINE_ID`=?";
        rowsEffected = this.jdbcTemplate.update(sql, pipelineId);

        sql = "update rm_realm_country r set r.ACTIVE=1,r.LAST_MODIFIED_BY=?,r.LAST_MODIFIED_DATE=? where r.REALM_COUNTRY_ID=? and r.ACTIVE=0";
        this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, p.getRealmCountry().getRealmCountryId());

        sql = "update rm_organisation o set o.ACTIVE=1,o.LAST_MODIFIED_BY=?,o.LAST_MODIFIED_DATE=? where o.ORGANISATION_ID=? and o.ACTIVE=0;";
        this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, p.getOrganisation().getId());

        sql = "select count(*) from rm_organisation_country roc where roc.ORGANISATION_ID=? and roc.REALM_COUNTRY_ID=?";
        int oCount = this.jdbcTemplate.queryForObject(sql, Integer.class, p.getOrganisation().getId(), p.getRealmCountry().getRealmCountryId());
        if (oCount > 0) {
            sql = "update rm_organisation_country roc set roc.ACTIVE=1,roc.LAST_MODIFIED_BY=?,roc.LAST_MODIFIED_DATE=?  where roc.ORGANISATION_ID=? and roc.REALM_COUNTRY_ID=? and roc.ACTIVE=0";
            this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, p.getOrganisation().getId(), p.getRealmCountry().getRealmCountryId());
        } else {
            sql = "insert into rm_organisation_country values(null,?,?,1,?,?,?,?)";
            this.jdbcTemplate.update(sql, p.getOrganisation().getId(), p.getRealmCountry().getRealmCountryId(), curUser.getUserId(), curDate, curUser.getUserId(), curDate);
        }

        for (String haId : p.getHealthAreaArray()) {
            sql = "update rm_health_area ha set ha.ACTIVE=1,ha.LAST_MODIFIED_BY=?,ha.LAST_MODIFIED_DATE=? where ha.HEALTH_AREA_ID=? and ha.ACTIVE=0;";
            this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, haId);
        }

        for (String haId : p.getHealthAreaArray()) {
            sql = "select count(*) from rm_health_area_country hac where hac.HEALTH_AREA_ID=? and hac.REALM_COUNTRY_ID=?;";
            int haCount = this.jdbcTemplate.queryForObject(sql, Integer.class, haId, p.getRealmCountry().getRealmCountryId());
            if (haCount > 0) {
                sql = "update rm_health_area_country hac set hac.ACTIVE=1,hac.LAST_MODIFIED_BY=?,hac.LAST_MODIFIED_DATE=?  where hac.HEALTH_AREA_ID=? and hac.REALM_COUNTRY_ID=? and hac.ACTIVE=0;";
                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, haId, p.getRealmCountry().getRealmCountryId());
            } else {
                sql = "insert into rm_health_area_country values(null,?,?,1,?,?,?,?)";
                this.jdbcTemplate.update(sql, haId, p.getRealmCountry().getRealmCountryId(), curUser.getUserId(), curDate, curUser.getUserId(), curDate);
            }
        }

        try {
            this.ProgramDataDaoImpl.getNewSupplyPlanList(programId, version.getVersionId(), true, false);
        } catch (ParseException ex) {
            Logger.getLogger(PipelineDbDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return programId;
    }

    public String getAlphaNumeric(int len) {
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'};

        char[] c = new char[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }

        return new String(c);
    }

    @Override
    public List<QatTempDataSource> getQatTempDataSourceListByPipelienId(int pipelineId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sql1 = "SELECT  ads.DataSourceName PIPELINE_DATA_SOURCE,ads.DataSourceTypeID PIPELINE_DATA_SOURCE_TYPE, ds.DATA_SOURCE_ID,"
                + " ds.PIPELINE_DATA_SOURCE_ID,  rds.DATA_SOURCE_TYPE_ID  "
                + "FROM qat_temp_data_source ds  "
                + " left join adb_datasource ads on ds.PIPELINE_DATA_SOURCE_ID=ads.DataSourceID and ads.PIPELINE_ID=:pipelineId"
                + " left join rm_data_source rds on rds.DATA_SOURCE_ID=ds.DATA_SOURCE_ID "
                + "where ds.PIPELINE_ID=:pipelineId";
        params.put("pipelineId", pipelineId);
        List<QatTempDataSource> qatList = this.namedParameterJdbcTemplate.query(sql1, params, new QatTempDataSourceRowMapper());
        if (qatList.size() == 0) {
            String sql = "SELECT  ds.`DataSourceName`PIPELINE_DATA_SOURCE,ds.`DataSourceTypeID` PIPELINE_DATA_SOURCE_TYPE,ds.DataSourceID PIPELINE_DATA_SOURCE_ID "
                    + ",al2.DATA_SOURCE_TYPE_ID,COALESCE(rds.DATA_SOURCE_ID,'') DATA_SOURCE_ID "
                    + "FROM `adb_datasource` ds  "
                    + "left join rm_data_source_type al2 on al2.DATA_SOURCE_TYPE_ID=if(ds.DataSourceTypeID='ACTCON',1,if(ds.DataSourceTypeID='FORCON',2,if(ds.DataSourceTypeID='INV',3,if(ds.DataSourceTypeID='SHIP',4,'')))) and ds.PIPELINE_ID=:pipelineId  "
                    + "left join ap_label al on upper(al.LABEL_EN)=upper(ds.DataSourceName) OR upper(al.LABEL_FR)=upper(ds.DataSourceName) "
                    + "OR upper(al.LABEL_SP)=upper(ds.DataSourceName) OR upper(al.LABEL_PR)=upper(ds.DataSourceName) "
                    + "left join rm_data_source rds on rds.DATA_SOURCE_TYPE_ID=al2.DATA_SOURCE_TYPE_ID and al.LABEL_ID=rds.LABEL_ID  "
                    + "where (rds.DATA_SOURCE_ID is not null and al.LABEL_ID is not null) or (rds.DATA_SOURCE_ID is null and al.LABEL_ID is null) and ds.PIPELINE_ID=:pipelineId;";
//            String sql = "SELECT  ds.`DataSourceName`PIPELINE_DATA_SOURCE,ds.`DataSourceTypeID` PIPELINE_DATA_SOURCE_TYPE,COALESCE(rds.DATA_SOURCE_ID,'') DATA_SOURCE_ID, "
//                    + "ds.DataSourceID PIPELINE_DATA_SOURCE_ID, COALESCE(dst.DATA_SOURCE_TYPE_ID,'') DATA_SOURCE_TYPE_ID "
//                    + "FROM `adb_datasource` ds  "
//                    + "left join ap_label a on a.LABEL_EN= ds.QATDataSourceLabel and a.SOURCE_ID=16 and a.LABEL_ID is not null "
//                    + "left join rm_data_source_type dst on dst.LABEL_ID=a.LABEL_ID "
//                    + "left join ap_label al on upper(al.LABEL_EN)=upper(ds.DataSourceName)  "
//                    + "OR upper(al.LABEL_FR)=upper(ds.DataSourceName)   "
//                    + "OR upper(al.LABEL_SP)=upper(ds.DataSourceName)   "
//                    + "OR upper(al.LABEL_PR)=upper(ds.DataSourceName)  "
//                    + "left join rm_data_source rds on rds.LABEL_ID=al.LABEL_ID AND al.LABEL_ID IS NOT NULL  "
//                    + "and rds.DATA_SOURCE_TYPE_ID=dst.DATA_SOURCE_TYPE_ID  "
//                    + "where ds.PIPELINE_ID=:pipelineId group by ds.`DataSourceID`";
            params.put("pipelineId", pipelineId);

            return this.namedParameterJdbcTemplate.query(sql, params, new QatTempDataSourceRowMapper());
        } else {
            return qatList;
        }
    }

    @Override
    @Transactional
    public int saveQatTempDataSource(QatTempDataSource[] dataSources, CustomUserDetails curUser, int pipelineId) {
        String sql = " delete from qat_temp_data_source  where PIPELINE_ID=?";
        this.jdbcTemplate.update(sql, pipelineId);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_data_source");
        List<SqlParameterSource> insertList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (QatTempDataSource ds : dataSources) {
            // Insert
            params = new HashMap<>();
            params.put("PIPELINE_DATA_SOURCE_TYPE", ds.getPipelineDataSourceType());
            params.put("PIPELINE_DATA_SOURCE", ds.getPipelineDataSource());
            params.put("DATA_SOURCE_ID", ds.getDataSourceId());
            params.put("PIPELINE_DATA_SOURCE_ID", ds.getPipelineDataSourceId());
            params.put("PIPELINE_ID", pipelineId);
            insertList.add(new MapSqlParameterSource(params));

        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<QatTempFundingSource> getQatTempFundingSourceListByPipelienId(int pipelineId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sql1 = "SELECT  ads.FundingSourceName PIPELINE_FUNDING_SOURCE, ds.FUNDING_SOURCE_ID,"
                + " ds.PIPELINE_FUNDING_SOURCE_ID  "
                + "FROM qat_temp_funding_source ds  "
                + " left join adb_fundingsource ads on ds.PIPELINE_FUNDING_SOURCE_ID=ads.FundingSourceID and ads.PIPELINE_ID=:pipelineId"
                + " left join rm_funding_source rds on rds.FUNDING_SOURCE_ID=ds.FUNDING_SOURCE_ID "
                + "where ds.PIPELINE_ID=:pipelineId";
        params.put("pipelineId", pipelineId);
        List<QatTempFundingSource> qatList = this.namedParameterJdbcTemplate.query(sql1, params, new QatTempFundingSourceRowMapper());
        if (qatList.size() == 0) {
            String sql = "SELECT  ds.`FundingSourceName`PIPELINE_FUNDING_SOURCE,COALESCE(rds.FUNDING_SOURCE_ID,'') FUNDING_SOURCE_ID,"
                    + " ds.FundingSourceID PIPELINE_FUNDING_SOURCE_ID  "
                    + "FROM `adb_fundingsource` ds   "
                    + "left join ap_label al on upper(al.LABEL_EN)=upper(ds.FundingSourceName) "
                    + "OR upper(al.LABEL_FR)=upper(ds.FundingSourceName)  "
                    + "OR upper(al.LABEL_SP)=upper(ds.FundingSourceName)  "
                    + "OR upper(al.LABEL_PR)=upper(ds.FundingSourceName) "
                    + "left join rm_funding_source rds on (rds.LABEL_ID=al.LABEL_ID AND al.LABEL_ID IS NOT NULL) or upper(rds.FUNDING_SOURCE_CODE)=upper(ds.FundingSourceName) "
                    + "where ds.PIPELINE_ID=:pipelineId group by ds.FundingSourceID;";
            params.put("pipelineId", pipelineId);

            return this.namedParameterJdbcTemplate.query(sql, params, new QatTempFundingSourceRowMapper());
        } else {
            return qatList;
        }
    }

    @Override
    @Transactional
    public int saveQatTempFundingSource(QatTempFundingSource[] fundingSources, CustomUserDetails curUser, int pipelineId) {
        String sql = " delete from qat_temp_funding_source  where PIPELINE_ID=?";
        this.jdbcTemplate.update(sql, pipelineId);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_funding_source");
        List<SqlParameterSource> insertList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (QatTempFundingSource ds : fundingSources) {
            // Insert
            params = new HashMap<>();
            params.put("PIPELINE_FUNDING_SOURCE", ds.getPipelineFundingSource());
            params.put("FUNDING_SOURCE_ID", ds.getFundingSourceId());
            params.put("PIPELINE_FUNDING_SOURCE_ID", ds.getPipelineFundingSourceId());
            params.put("PIPELINE_ID", pipelineId);
            insertList.add(new MapSqlParameterSource(params));

        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        return rowsEffected;
    }

    @Override
    public List<QatTempProcurementAgent> getQatTempProcurementAgentListByPipelienId(int pipelineId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sql1 = "SELECT  ads.SupplierName PIPELINE_PROCUREMENT_AGENT, ds.PROCUREMENT_AGENT_ID,"
                + " ds.PIPELINE_PROCUREMENT_AGENT_ID  "
                + "FROM qat_temp_procurement_agent ds  "
                + " left join adb_source ads on ds.PIPELINE_PROCUREMENT_AGENT_ID=ads.SupplierID and ads.PIPELINE_ID=:pipelineId"
                + " left join rm_procurement_agent rds on rds.PROCUREMENT_AGENT_ID=ds.PROCUREMENT_AGENT_ID "
                + "where ds.PIPELINE_ID=:pipelineId";
        params.put("pipelineId", pipelineId);
        List<QatTempProcurementAgent> qatList = this.namedParameterJdbcTemplate.query(sql1, params, new QatTempProcurementAgentRowMapper());
        if (qatList.size() == 0) {
            String sql = "SELECT  ds.`SupplierName`PIPELINE_PROCUREMENT_AGENT,COALESCE(rds.PROCUREMENT_AGENT_ID,'') PROCUREMENT_AGENT_ID,"
                    + " ds.SupplierID PIPELINE_PROCUREMENT_AGENT_ID  "
                    + "FROM `adb_source` ds   "
                    + "left join ap_label al on upper(al.LABEL_EN)=upper(ds.SupplierName) "
                    + "OR upper(al.LABEL_FR)=upper(ds.SupplierName)  "
                    + "OR upper(al.LABEL_SP)=upper(ds.SupplierName)  "
                    + "OR upper(al.LABEL_PR)=upper(ds.SupplierName) "
                    + "left join rm_procurement_agent rds on (rds.LABEL_ID=al.LABEL_ID AND al.LABEL_ID IS NOT NULL) or upper(rds.PROCUREMENT_AGENT_CODE)=upper(ds.SupplierName) "
                    + "where ds.PIPELINE_ID=:pipelineId group by ds.SupplierID;";
            params.put("pipelineId", pipelineId);

            return this.namedParameterJdbcTemplate.query(sql, params, new QatTempProcurementAgentRowMapper());
        } else {
            return qatList;
        }
    }

    @Override
    @Transactional
    public int saveQatTempProcurementAgent(QatTempProcurementAgent[] procurementAgents, CustomUserDetails curUser, int pipelineId) {
        String sql = " delete from qat_temp_procurement_agent  where PIPELINE_ID=?";
        this.jdbcTemplate.update(sql, pipelineId);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("qat_temp_procurement_agent");
        List<SqlParameterSource> insertList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        for (QatTempProcurementAgent ds : procurementAgents) {
            // Insert
            params = new HashMap<>();
            params.put("PIPELINE_PROCUREMENT_AGENT", ds.getPipelineProcurementAgent());
            params.put("PROCUREMENT_AGENT_ID", ds.getProcurementAgentId());
            params.put("PIPELINE_PROCUREMENT_AGENT_ID", ds.getPipelineProcurementAgentId());
            params.put("PIPELINE_ID", pipelineId);
            insertList.add(new MapSqlParameterSource(params));

        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        return rowsEffected;
    }

    @Override
    @Transactional
    public void createRealmCountryPlanningUnits(int pipelineId, CustomUserDetails curUser, int realmCountryId) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sql = "update rm_realm_country r set r.ACTIVE=1,r.LAST_MODIFIED_BY=?,r.LAST_MODIFIED_DATE=? where r.REALM_COUNTRY_ID=? and r.ACTIVE=0";
        this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, realmCountryId);

        String haCodes = "";
        String sqlHa = "select group_concat(ha.HEALTH_AREA_CODE) from qat_temp_program_healthArea ph  "
                + "left join rm_health_area ha on ha.HEALTH_AREA_ID=ph.HEALTH_AREA_ID "
                + "where ph.PIPELINE_ID=?";
        haCodes = this.jdbcTemplate.queryForObject(sqlHa, String.class, pipelineId).replaceAll(",", "");
//        System.out.println("haCodes+++" + haCodes);

        String sql1 = "insert into rm_realm_country_planning_unit (SELECT null,qtpu.PLANNING_UNIT_ID,qtp.REALM_COUNTRY_ID,rpu.LABEL_ID "
                + ",concat(ac.COUNTRY_CODE2,\"-\",?,\"-\",qtpu.PIPELINE_PRODUCT_ID,\"-\",qtpu.PLANNING_UNIT_ID)  "
                + ",1,1,null,1,1,now(),1,now()  "
                + "FROM fasp.qat_temp_program_planning_unit qtpu  "
                + "left join rm_planning_unit rpu on rpu.PLANNING_UNIT_ID=qtpu.PLANNING_UNIT_ID  "
                + "left join fasp.qat_temp_program qtp on qtp.PIPELINE_ID=qtpu.PIPELINE_ID "
                + "left join fasp.rm_realm_country_planning_unit rcpu on rcpu.PLANNING_UNIT_ID=qtpu.PLANNING_UNIT_ID  "
                + "and rcpu.MULTIPLIER=1 and rcpu.REALM_COUNTRY_ID=qtp.REALM_COUNTRY_ID "
                + "left join fasp.rm_realm_country rrc on rrc.REALM_COUNTRY_ID=qtp.REALM_COUNTRY_ID "
                + "left join ap_country ac on ac.COUNTRY_ID=rrc.COUNTRY_ID "
                + "left join fasp.rm_health_area rha on rha.HEALTH_AREA_ID=qtp.HEALTH_AREA_ID "
                + "where qtpu.PIPELINE_ID=? and rcpu.REALM_COUNTRY_PLANNING_UNIT_ID is null);";
//        String sql1 = "insert into rm_realm_country_planning_unit (SELECT null,qtpu.PLANNING_UNIT_ID,qtp.REALM_COUNTRY_ID,rpu.LABEL_ID "
//                + ",concat(ac.COUNTRY_CODE2,\"-\",qtpu.PIPELINE_PRODUCT_ID,\"-\",qtpu.PLANNING_UNIT_ID)  "
//                + ",1,1,null,1,1,now(),1,now()  "
//                + "FROM fasp.qat_temp_program_planning_unit qtpu  "
//                + "left join rm_planning_unit rpu on rpu.PLANNING_UNIT_ID=qtpu.PLANNING_UNIT_ID  "
//                + "left join fasp.qat_temp_program qtp on qtp.PIPELINE_ID=qtpu.PIPELINE_ID "
//                + "left join fasp.rm_realm_country_planning_unit rcpu on rcpu.PLANNING_UNIT_ID=qtpu.PLANNING_UNIT_ID  "
//                + "and rcpu.MULTIPLIER=1 and rcpu.REALM_COUNTRY_ID=qtp.REALM_COUNTRY_ID "
//                + "left join fasp.rm_realm_country rrc on rrc.REALM_COUNTRY_ID=qtp.REALM_COUNTRY_ID "
//                + "left join ap_country ac on ac.COUNTRY_ID=rrc.COUNTRY_ID "
//                + "where qtpu.PIPELINE_ID=? and rcpu.REALM_COUNTRY_PLANNING_UNIT_ID is null);";
        this.jdbcTemplate.update(sql1, haCodes, pipelineId);
    }

}
