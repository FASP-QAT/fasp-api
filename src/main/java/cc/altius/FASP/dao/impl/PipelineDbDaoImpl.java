/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.PipelineDbDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.pipeline.Pipeline;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author akil
 */
@Repository
public class PipelineDbDaoImpl implements PipelineDbDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public int savePipelineDbData(Pipeline pipeline, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        // Save records for adb_pipeline
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("adb_pipeline").usingGeneratedKeyColumns("PIPELINE_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
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
