/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ErpLinkingDao;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ArtmisHistory;
import cc.altius.FASP.model.DTO.AutoCompletePuDTO;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ErpAutoCompleteDTO;
import cc.altius.FASP.model.DTO.ErpBatchDTO;
import cc.altius.FASP.model.DTO.ErpOrderDTO;
import cc.altius.FASP.model.DTO.ErpShipmentDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.DTO.NotificationSummaryDTO;
import cc.altius.FASP.model.ShipmentLinkingOutput;
import cc.altius.FASP.model.DTO.rowMapper.ArtmisHistoryErpOrderRowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ArtmisHistoryErpShipmentRowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ERPLinkedShipmentsDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ERPNewBatchDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpBatchDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderDTOListResultSetExtractor;
import cc.altius.FASP.model.DTO.rowMapper.ManualTaggingDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ManualTaggingOrderDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.NotERPLinkedShipmentsRowMapper;
import cc.altius.FASP.model.DTO.rowMapper.NotificationSummaryDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ShipmentNotificationDTORowMapper;
import cc.altius.FASP.model.ExtendedProductCategory;
import cc.altius.FASP.model.LinkedShipmentBatchDetails;
import cc.altius.FASP.model.NotLinkedErpShipmentsInput;
import cc.altius.FASP.model.NotLinkedErpShipmentsInputTab3;
import cc.altius.FASP.model.ProductCategory;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.RoAndRoPrimeLineNo;
import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.ShipmentLinkedToOtherProgramInput;
import cc.altius.FASP.model.ShipmentLinkedToOtherProgramOutput;
import cc.altius.FASP.model.ShipmentSyncInput;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LinkedShipmentBatchDetailsListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.ProductCategoryRowMapper;
import cc.altius.FASP.model.rowMapper.ShipmentLinkedToOtherProgramOutputRowMapper;
import cc.altius.FASP.model.rowMapper.ShipmentLinkingOutputRowMapper;
import cc.altius.FASP.model.rowMapper.ShipmentListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.model.rowMapper.TreeExtendedProductCategoryResultSetExtractor;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.utils.DateUtils;
import cc.altius.utils.TreeUtils.Node;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author akil
 */
@Repository
public class ErpLinkingDaoImpl implements ErpLinkingDao {

    @Autowired
    private ProgramService programService;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ManualTaggingDTO getShipmentDetailsByParentShipmentId(int parentShipmentId) {
        String sql = "SELECT "
                + "        st.SHIPMENT_ID, st.SHIPMENT_TRANS_ID, st.SHIPMENT_QTY, st.EXPECTED_DELIVERY_DATE, st.PRODUCT_COST, st.SKU_CODE, "
                + "        st.PROCUREMENT_AGENT_ID, st.PROCUREMENT_AGENT_CODE, st.`COLOR_HTML_CODE`, st.`PROCUREMENT_AGENT_LABEL_ID`, st.`PROCUREMENT_AGENT_LABEL_EN`, st.`PROCUREMENT_AGENT_LABEL_FR`, st.`PROCUREMENT_AGENT_LABEL_SP`, st.`PROCUREMENT_AGENT_LABEL_PR`, "
                + "        st.FUNDING_SOURCE_ID, st.FUNDING_SOURCE_CODE, st.`FUNDING_SOURCE_LABEL_ID`, st.`FUNDING_SOURCE_LABEL_EN`, st.`FUNDING_SOURCE_LABEL_FR`, st.`FUNDING_SOURCE_LABEL_SP`, st.`FUNDING_SOURCE_LABEL_PR`, "
                + "        st.BUDGET_ID, st.BUDGET_CODE, st.`BUDGET_LABEL_ID`, st.`BUDGET_LABEL_EN`, st.`BUDGET_LABEL_FR`, st.`BUDGET_LABEL_SP`, st.`BUDGET_LABEL_PR`, "
                + "        st.SHIPMENT_STATUS_ID, st.`SHIPMENT_STATUS_LABEL_ID`, st.`SHIPMENT_STATUS_LABEL_EN`, st.`SHIPMENT_STATUS_LABEL_FR`, st.`SHIPMENT_STATUS_LABEL_SP`, st.`SHIPMENT_STATUS_LABEL_PR`, "
                + "        st.PLANNING_UNIT_ID, st.`PLANNING_UNIT_LABEL_ID`, st.`PLANNING_UNIT_LABEL_EN`, st.`PLANNING_UNIT_LABEL_FR`, st.`PLANNING_UNIT_LABEL_SP`, st.`PLANNING_UNIT_LABEL_PR`, "
                + "        st.ORDER_NO, st.PRIME_LINE_NO,st.`NOTES` "
                + "FROM ( "
                + "        SELECT "
                + "            s.SHIPMENT_ID, st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE,st.SHIPMENT_QTY, st.RATE, st.PRODUCT_COST, st.FREIGHT_COST, st.ACCOUNT_FLAG, st.SHIPMENT_TRANS_ID, papu.SKU_CODE, "
                + "            pa.`PROCUREMENT_AGENT_ID`, pa.`PROCUREMENT_AGENT_CODE`, pa.`COLOR_HTML_CODE`, pa.`LABEL_ID` `PROCUREMENT_AGENT_LABEL_ID`, pa.`LABEL_EN` `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, "
                + "            fs.`FUNDING_SOURCE_ID`, fs.`FUNDING_SOURCE_CODE`, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, "
                + "            shs.SHIPMENT_STATUS_ID, shs.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, shs.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, shs.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, shs.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, shs.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, "
                + "            sc.CURRENCY_ID `SHIPMENT_CURRENCY_ID`, sc.`CURRENCY_CODE` `SHIPMENT_CURRENCY_CODE`, s.CONVERSION_RATE_TO_USD `SHIPMENT_CONVERSION_RATE_TO_USD`, "
                + "            sc.LABEL_ID `SHIPMENT_CURRENCY_LABEL_ID`, sc.LABEL_EN `SHIPMENT_CURRENCY_LABEL_EN`, sc.LABEL_FR `SHIPMENT_CURRENCY_LABEL_FR`, sc.LABEL_SP `SHIPMENT_CURRENCY_LABEL_SP`, sc.LABEL_PR `SHIPMENT_CURRENCY_LABEL_PR`, "
                + "            st.ACTIVE, st.`ORDER_NO`, st.`PRIME_LINE_NO`, "
                + "            b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID `BUDGET_LABEL_ID`, b.LABEL_EN `BUDGET_LABEL_EN`, b.LABEL_FR `BUDGET_LABEL_FR`, b.LABEL_SP `BUDGET_LABEL_SP`, b.LABEL_PR `BUDGET_LABEL_PR`, "
                + "            st.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,st.`NOTES` "
                + "FROM ( "
                + "    SELECT st.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE st.`SHIPMENT_ID`=? GROUP BY st.SHIPMENT_ID "
                + ") ts "
                + "    LEFT JOIN rm_shipment s ON ts.SHIPMENT_ID=s.SHIPMENT_ID "
                + "    LEFT JOIN rm_shipment_trans st ON ts.SHIPMENT_ID=st.SHIPMENT_ID AND ts.MAX_VERSION_ID=st.VERSION_ID "
                + "    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "    LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID "
                + "    LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID "
                + "    LEFT JOIN vw_shipment_status shs ON st.SHIPMENT_STATUS_ID=shs.SHIPMENT_STATUS_ID "
                + "    LEFT JOIN vw_currency sc ON s.CURRENCY_ID=sc.CURRENCY_ID "
                + "    LEFT JOIN vw_budget b ON st.BUDGET_ID=b.BUDGET_ID "
                + "    LEFT JOIN rm_manual_tagging mt ON mt.SHIPMENT_ID=ts.SHIPMENT_ID AND mt.ACTIVE "
                + "    LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID AND papu.PLANNING_UNIT_ID=st.PLANNING_UNIT_ID "
                + "    WHERE st.`SHIPMENT_ID`=? group by st.`SHIPMENT_TRANS_ID` "
                + ") st ";
        try {
            return this.jdbcTemplate.queryForObject(sql, new ManualTaggingDTORowMapper(), parentShipmentId, parentShipmentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForManualTagging(ManualTaggingDTO manualTaggingDTO, CustomUserDetails curUser) {
        String sql = "";
        List<ManualTaggingDTO> list = null;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", manualTaggingDTO.getPlanningUnitIdsString());
        params.put("curDate", curDate);
        logger.info("ERP Linking : going to get tab wise list---");
        logger.info("ERP Linking : linking type ---" + manualTaggingDTO.getLinkingType());
        if (manualTaggingDTO.getLinkingType() == 1) {
            // Shipments that need to be linked
            params.put("programId", manualTaggingDTO.getProgramId());
            sql = "CALL getShipmentListForManualLinking(:programId, :planningUnitId, -1)";
            list = this.namedParameterJdbcTemplate.query(sql, params, new ManualTaggingDTORowMapper());
            logger.info("ERP Linking : tab 1 params ---" + params);
            logger.info("ERP Linking : tab 1 list ---" + list);
        } else if (manualTaggingDTO.getLinkingType() == 2) {
            // Shipments that are already linked
            params.put("programId", manualTaggingDTO.getProgramId());
            sql = "CALL getShipmentListForAlreadyLinkedShipments(:programId, :planningUnitId, -1)";
            list = this.namedParameterJdbcTemplate.query(sql, params, new ERPLinkedShipmentsDTORowMapper());
            logger.info("ERP Linking : tab 2 list ---" + list);
        } else {
            sql = "CALL getErpShipmentForNotLinked(:countryId,:productcategoryId, :planningUnitId, :realmId, :curDate)";
            params.put("productcategoryId", manualTaggingDTO.getProductCategoryIdsString());
            params.put("countryId", manualTaggingDTO.getCountryId());
            params.put("realmId", curUser.getRealm().getRealmId());
            list = this.namedParameterJdbcTemplate.query(sql, params, new NotERPLinkedShipmentsRowMapper());
            logger.info("ERP Linking : tab 3 list ---" + list);
        }
        return list;
    }

    @Override
    public List<ManualTaggingDTO> getOrderDetailsByForNotLinkedERPShipments(String roNoOrderNo, int planningUnitId, int linkingType) {
        logger.info("ERP Linking : get order details for not linked ERP shipments ---");
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitId", planningUnitId);
        params.put("roNoOrderNo", roNoOrderNo);
        sql.append(" SELECT sst.`ERP_ORDER_ID`,sst.`RO_NO`,sst.`RO_PRIME_LINE_NO`,sst.`ORDER_NO`,sst.`PRIME_LINE_NO`, "
                + " sst.`QTY`,sst.`STATUS`,sst.PLANNING_UNIT_LABEL_ID,sst.PLANNING_UNIT_LABEL_EN,sst.PLANNING_UNIT_ID, "
                + " sst.PLANNING_UNIT_LABEL_FR,sst.PLANNING_UNIT_LABEL_PR,sst.PLANNING_UNIT_LABEL_SP,sst.EXPECTED_DELIVERY_DATE,sst.SKU_CODE FROM  "
                + " (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`, "
                + " e.`QTY`,e.`STATUS`,l.`LABEL_ID` AS PLANNING_UNIT_LABEL_ID,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS PLANNING_UNIT_LABEL_EN,p.PLANNING_UNIT_ID, "
                + " l.`LABEL_FR` AS PLANNING_UNIT_LABEL_FR,l.`LABEL_PR` PLANNING_UNIT_LABEL_PR,l.`LABEL_SP` AS PLANNING_UNIT_LABEL_SP,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE,pu.SKU_CODE FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ORDER_NO`=e.`ORDER_NO` AND es.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` AND es.file_name=( "
                + " SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=e.`ORDER_NO` AND s.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` "
                + " ) "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.STATUS "
                + " WHERE e.`ERP_ORDER_ID` IN (SELECT a.`ERP_ORDER_ID` FROM (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID,sm.SHIPMENT_STATUS_MAPPING_ID "
                + " FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID`"
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` "
                + " WHERE (mt.SHIPMENT_ID IS NULL  OR mt.ACTIVE=0) ");
        if (planningUnitId != 0) {
            sql.append(" AND pu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        if (roNoOrderNo != null && roNoOrderNo != "" && !roNoOrderNo.equals("0")) {
            sql.append(" AND e.RO_NO=:roNoOrderNo ");
        }
        sql.append(" GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`)  a  "
                + "  ) AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15) AND e.`CHANGE_CODE` !=2 "
                + " GROUP BY e.`ERP_ORDER_ID`) sst "
                + " LEFT JOIN rm_shipment_status_mapping sms ON sms.`EXTERNAL_STATUS_STAGE`=sst.`STATUS` "
                + " WHERE IF(sst.EXPECTED_DELIVERY_DATE < CURDATE() - INTERVAL 6 MONTH, sms.SHIPMENT_STATUS_MAPPING_ID!=2 , sms.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) ");
        logger.info("ERP Linking : get order details for not linked ERP shipments params ---");
        List<ManualTaggingDTO> list = this.namedParameterJdbcTemplate.query(sql.toString(), params, new NotERPLinkedShipmentsRowMapper());
        logger.info("ERP Linking : get order details for not linked ERP shipments list ---" + list);
        return list;
    }

    @Override
    public List<ManualTaggingOrderDTO> getOrderDetailsByOrderNoAndPrimeLineNo(String roNoOrderNo, int programId, int planningUnitId, int linkingType, int parentShipmentId) {
        logger.info("ERP Linking : get order details for not linked & linked QAT shipments ---");
        String reason = "";
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        params.put("roNoOrderNo", roNoOrderNo);
        sql.append("  SELECT sst.`ERP_ORDER_ID`,sst.`RO_NO`,sst.`RO_PRIME_LINE_NO`,sst.`ORDER_NO`,sst.`PRIME_LINE_NO`,sst.`SHIPMENT_ID`,sst.`PLANNING_UNIT_SKU_CODE`,sst.`PROCUREMENT_UNIT_SKU_CODE`,sst.`ORDER_TYPE`,sst.`QTY`,sst.`SUPPLIER_NAME`,sst.`PRICE`,sst.`SHIPPING_COST`,sst.`RECPIENT_COUNTRY`,sst.`STATUS`,sst.`LABEL_ID`,sst.LABEL_EN,sst.`LABEL_FR`,sst.`LABEL_PR`,sst.`LABEL_SP`,sst.CURRENT_ESTIMATED_DELIVERY_DATE,sst.ACTIVE,sst.`NOTES`,sst.`CONVERSION_FACTOR` "
                + "FROM ( ");
        sql.append(" (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`,e.`SHIPMENT_ID`,e.`PLANNING_UNIT_SKU_CODE`,e.`PROCUREMENT_UNIT_SKU_CODE`,e.`ORDER_TYPE`,e.`QTY`,e.`SUPPLIER_NAME`,e.`PRICE`,e.`SHIPPING_COST`,e.`RECPIENT_COUNTRY`,e.`STATUS`,l.`LABEL_ID`,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS LABEL_EN,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS CURRENT_ESTIMATED_DELIVERY_DATE,IFNULL(mt.ACTIVE,0) AS ACTIVE,mt.`NOTES`,mt.`CONVERSION_FACTOR` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ORDER_NO`=e.`ORDER_NO` AND es.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` AND es.file_name=( "
                + " SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=e.`ORDER_NO` AND s.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` "
                + " ) ");
        if (linkingType == 2) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }

        sql.append(" WHERE e.`ERP_ORDER_ID` IN (SELECT a.`ERP_ORDER_ID` FROM (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID,sm.`SHIPMENT_STATUS_MAPPING_ID` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE "
                + "	FROM rm_realm_country rc "
                + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=e.RECPIENT_COUNTRY "
                + " LEFT JOIN rm_program pm ON pm.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID  "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` ");
        if (linkingType == 1) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }
        sql.append(" WHERE pm.`PROGRAM_ID`=:programId ");
        if (planningUnitId != 0) {
            sql.append(" AND pu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        if (roNoOrderNo != null && roNoOrderNo != "" && !roNoOrderNo.equals("0")) {
            sql.append(" AND e.RO_NO=:roNoOrderNo ");
        }
        if (linkingType == 1) {
            sql.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sql.append(" GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`)  a "
                + " ) AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15) AND e.`CHANGE_CODE` !=2 ");
        if (linkingType == 2) {
            params.put("parentShipmentId", parentShipmentId);
            sql.append(" AND (mt.SHIPMENT_ID=:parentShipmentId OR mt.SHIPMENT_ID IS NULL) ");
        }
        sql.append(" GROUP BY e.`ERP_ORDER_ID`) ");
        sql.append(" UNION ");
        sql.append(" (SELECT e.`ERP_ORDER_ID`,e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`,e.`SHIPMENT_ID`,e.`PLANNING_UNIT_SKU_CODE`,e.`PROCUREMENT_UNIT_SKU_CODE`,e.`ORDER_TYPE`,e.`QTY`,e.`SUPPLIER_NAME`,e.`PRICE`,e.`SHIPPING_COST`,e.`RECPIENT_COUNTRY`,e.`STATUS`,l.`LABEL_ID`,IF(l.`LABEL_EN` IS NOT NULL,l.`LABEL_EN`,'') AS LABEL_EN,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,COALESCE(MIN(es.ACTUAL_DELIVERY_DATE),e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS CURRENT_ESTIMATED_DELIVERY_DATE,IFNULL(mt.ACTIVE,0) AS ACTIVE,mt.`NOTES`,mt.`CONVERSION_FACTOR` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + " LEFT JOIN rm_erp_shipment es ON es.`ORDER_NO`=e.`ORDER_NO` AND es.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` AND es.file_name=( "
                + " SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=e.`ORDER_NO` AND s.`PRIME_LINE_NO`=e.`PRIME_LINE_NO` "
                + " ) ");
        if (linkingType == 2) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }

        sql.append(" WHERE e.`ERP_ORDER_ID` IN (SELECT a.`ERP_ORDER_ID` FROM (SELECT MAX(e.`ERP_ORDER_ID`)  AS ERP_ORDER_ID,sm.`SHIPMENT_STATUS_MAPPING_ID` FROM rm_erp_order e "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` "
                + " AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + " LEFT JOIN rm_planning_unit p ON p.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN ap_label l ON l.`LABEL_ID`=p.`LABEL_ID` "
                + " LEFT JOIN (SELECT rc.REALM_COUNTRY_ID, cl.LABEL_EN, c.COUNTRY_CODE "
                + "	FROM rm_realm_country rc "
                + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID) c1 ON c1.LABEL_EN=e.RECPIENT_COUNTRY "
                + " LEFT JOIN rm_program pm ON pm.`REALM_COUNTRY_ID`=c1.REALM_COUNTRY_ID                      "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` ");
        if (linkingType == 1) {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` AND mt.ACTIVE ");
        } else {
            sql.append(" LEFT JOIN rm_manual_tagging mt ON mt.`ORDER_NO`=e.`ORDER_NO` AND e.`PRIME_LINE_NO`=mt.`PRIME_LINE_NO` ");
        }
        sql.append(" WHERE pm.`PROGRAM_ID`=:programId ");
        if (planningUnitId != 0) {
            sql.append(" AND pu.`PLANNING_UNIT_ID`=:planningUnitId ");
        }
        if (roNoOrderNo != null && roNoOrderNo != "" && !roNoOrderNo.equals("0")) {
            sql.append(" AND e.ORDER_NO=:roNoOrderNo ");
        }

        if (linkingType == 1) {
            sql.append(" AND (mt.`MANUAL_TAGGING_ID` IS NULL OR mt.ACTIVE =0) ");
        }
        sql.append(" GROUP BY e.`RO_NO`,e.`RO_PRIME_LINE_NO`,e.`ORDER_NO`,e.`PRIME_LINE_NO`) a "
                + " ) AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15) AND e.`CHANGE_CODE` !=2 ");
        if (linkingType == 2) {
            params.put("parentShipmentId", parentShipmentId);
            sql.append(" AND (mt.SHIPMENT_ID=:parentShipmentId OR mt.SHIPMENT_ID IS NULL) ");
        }
        sql.append(" GROUP BY e.`ERP_ORDER_ID`) ");
        sql.append("  ) sst "
                + " LEFT JOIN rm_shipment_status_mapping sms ON sms.`EXTERNAL_STATUS_STAGE`=sst.`STATUS` "
                + " WHERE IF((sst.CURRENT_ESTIMATED_DELIVERY_DATE < CURDATE() - INTERVAL 6 MONTH) && sst.ACTIVE=0, sms.SHIPMENT_STATUS_MAPPING_ID!=2 , sms.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) "
                + " ORDER BY sst.CURRENT_ESTIMATED_DELIVERY_DATE DESC ");
//        System.out.println("params----" + params);
//        System.out.println("query******************************" + sql.toString());
        logger.info("ERP Linking : get order details for not linked & linked QAT shipments params ---" + params);
        List<ManualTaggingOrderDTO> list = this.namedParameterJdbcTemplate.query(sql.toString(), params, new ManualTaggingOrderDTORowMapper());
        logger.info("ERP Linking : get order details for not linked & linked QAT shipments list ---" + list);
        return list;

    }

    @Override
    public int checkIfOrderNoAlreadyTagged(String orderNo, int primeLineNo) {
        int count = 0;
        logger.info("ERP Linking : Going to check manual tagging count ---");
        logger.info("ERP Linking : Going to check manual tagging order no ---" + orderNo);
        logger.info("ERP Linking : Going to check manual tagging prime line no ---" + primeLineNo);
        String sql = "SELECT COUNT(*) FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
        count = this.jdbcTemplate.queryForObject(sql, Integer.class, orderNo, primeLineNo);

        logger.info("ERP Linking : manual tagging count---" + count);
        return count;
    }

    @Override
    public int updateERPLinking(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        //Update conversion factor and notes
        logger.info("ERP Linking : Going to update conversion factor and notes");
        logger.info("ERP Linking : manual tagging object---" + manualTaggingOrderDTO);
        String sql = " SELECT st.`SHIPMENT_TRANS_ID`,st.`RATE` FROM rm_shipment_trans st "
                + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
                + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE` "
                + "ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1;";

        Map<String, Object> map = this.jdbcTemplate.queryForMap(sql, manualTaggingOrderDTO.getParentShipmentId(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
        logger.info("ERP Linking : Get shipment trans info---" + map);

        sql = "UPDATE `rm_manual_tagging` m SET m.`CONVERSION_FACTOR`=?,m.`NOTES`=? "
                + " WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE` AND m.`SHIPMENT_ID`=?; ";
        int rowsUpdated = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getConversionFactor(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), manualTaggingOrderDTO.getParentShipmentId());
        logger.info("ERP Linking : updated conversion factor and notes rows---" + rowsUpdated);

//            System.out.println("conversion factor---" + manualTaggingOrderDTO.getConversionFactor());
//            long convertedQty = ((new BigDecimal(manualTaggingOrderDTO.getQuantity())).multiply(manualTaggingOrderDTO.getConversionFactor())).longValueExact();
        long convertedQty = (long) Math.round((double) manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor());
        logger.info("ERP Linking : convertedQty---" + convertedQty);
        logger.info("ERP Linking : rate---" + map.get("RATE"));

//        double rate = Double.parseDouble(map.get("RATE").toString());
        sql = "SELECT e.`PRICE` FROM rm_erp_order e WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? "
                + " ORDER BY e.`ERP_ORDER_ID` DESC LIMIT 1;";
        double rate = this.jdbcTemplate.queryForObject(sql, Double.class, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
        logger.info("ERP Linking : calculated rate---" + rate);
        double finalRate = (rate / manualTaggingOrderDTO.getConversionFactor());
        logger.info("ERP Linking : calculated final rate---" + finalRate);
        double productCost = finalRate * (double) convertedQty;
        logger.info("ERP Linking : final product cost---" + productCost);
        sql = "UPDATE rm_shipment_trans st  SET st.`SHIPMENT_QTY`=?,st.`RATE`=?,st.`PRODUCT_COST`=?, "
                + "st.`LAST_MODIFIED_DATE`=?,st.`LAST_MODIFIED_BY`=?,st.`NOTES`=? "
                + "WHERE st.`SHIPMENT_TRANS_ID`=?;";
        rowsUpdated = this.jdbcTemplate.update(sql, Math.round(convertedQty), finalRate, productCost, curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), (long) map.get("SHIPMENT_TRANS_ID"));
        logger.info("ERP Linking : updated shipment trans---" + rowsUpdated);
        return -1;
    }

    @Override
    @Transactional
    public int linkShipmentWithARTMIS(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        int rows;
        int row = 0;
//        int count = 0;
        boolean goAhead = false;
        logger.info("ERP Linking : link shipment with QAT object ---" + manualTaggingOrderDTO);
        logger.info("ERP Linking : link shipment with QAT curUser ---" + curUser.getUsername());
        String sql;
//                = "SELECT COUNT(*) FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
//        count = this.jdbcTemplate.queryForObject(sql, Integer.class, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());

//        logger.info("ERP Linking : manual tagging count---" + count);
//        if (count == 0) {
        try {
            logger.info("ERP Linking : going to create entry in manual tagging table---");
            sql = "INSERT INTO rm_manual_tagging VALUES (NULL,?,?,?,?,?,?,?,1,?,?);";
            row = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), (manualTaggingOrderDTO.getParentShipmentId() != 0 ? manualTaggingOrderDTO.getParentShipmentId() : manualTaggingOrderDTO.getShipmentId()), curDate, curUser.getUserId(), curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getConversionFactor());
            logger.info("ERP Linking : entry created in manual tagging table---");
            goAhead = true;
        } catch (Exception e) {
            logger.info("ERP Linking : Can't go ahead and link shipment bcoz it's duplicate---" + manualTaggingOrderDTO.getOrderNo() + "-" + manualTaggingOrderDTO.getPrimeLineNo());
        }
        if (goAhead) {
            logger.info("ERP Linking : going to get erp order object---");

            String filename = this.getMaxERPOrderIdFromERPShipment(manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
            logger.info("ERP Linking : max erp order id from shipment table to get the batch details---" + filename);
            // Get shipment details from erp order table
            sql = " SELECT "
                    + " eo.ERP_ORDER_ID, eo.RO_NO, eo.RO_PRIME_LINE_NO, eo.ORDER_NO, eo.PRIME_LINE_NO , "
                    + " eo.ORDER_TYPE, eo.CREATED_DATE, eo.PARENT_RO, eo.PARENT_CREATED_DATE, eo.PLANNING_UNIT_SKU_CODE,  "
                    + " eo.PROCUREMENT_UNIT_SKU_CODE, eo.QTY, eo.ORDERD_DATE,eo.CURRENT_ESTIMATED_DELIVERY_DATE, eo.REQ_DELIVERY_DATE,  "
                    + " eo.AGREED_DELIVERY_DATE, eo.SUPPLIER_NAME, eo.PRICE, eo.SHIPPING_COST, eo.SHIP_BY,  "
                    + " eo.RECPIENT_NAME, eo.RECPIENT_COUNTRY, eo.`STATUS`, eo.`CHANGE_CODE`, ssm.SHIPMENT_STATUS_ID, eo.MANUAL_TAGGING, eo.CONVERSION_FACTOR, "
                    + " es.ACTUAL_DELIVERY_DATE, es.ACTUAL_SHIPMENT_DATE, es.ARRIVAL_AT_DESTINATION_DATE, "
                    + " es.BATCH_NO, IF(es.DELIVERED_QTY !=0,COALESCE(es.DELIVERED_QTY, es.SHIPPED_QTY),es.SHIPPED_QTY) `BATCH_QTY`, es.`EXPIRY_DATE`, "
                    + " st.PLANNING_UNIT_ID,papu1.PLANNING_UNIT_ID AS ERP_PLANNING_UNIT_ID, papu2.PROCUREMENT_UNIT_ID, pu2.SUPPLIER_ID, ppu.SHELF_LIFE, "
                    + " sh.SHIPMENT_ID, sh.PROGRAM_ID, sh.PARENT_SHIPMENT_ID, "
                    + " st.SHIPMENT_TRANS_ID, st.VERSION_ID, st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.BUDGET_ID, st.ACTIVE, st.ERP_FLAG, st.ACCOUNT_FLAG, st.DATA_SOURCE_ID,eo.CONVERSION_FACTOR  "
                    + " FROM ( "
                    + " SELECT  "
                    + " e.ERP_ORDER_ID, e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO , "
                    + " e.ORDER_TYPE, e.CREATED_DATE, e.PARENT_RO, e.PARENT_CREATED_DATE, e.PLANNING_UNIT_SKU_CODE,  "
                    + " e.PROCUREMENT_UNIT_SKU_CODE, e.QTY, e.ORDERD_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE, e.REQ_DELIVERY_DATE,  "
                    + " e.AGREED_DELIVERY_DATE, e.SUPPLIER_NAME, e.PRICE, e.SHIPPING_COST, e.SHIP_BY, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, TRUE, FALSE) `MANUAL_TAGGING`, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, mt.CONVERSION_FACTOR, 1) `CONVERSION_FACTOR`,  "
                    + " e.RECPIENT_NAME, e.RECPIENT_COUNTRY, e.STATUS, e.CHANGE_CODE, COALESCE(mts.PROGRAM_ID,e.PROGRAM_ID) `PROGRAM_ID`, COALESCE(mt.SHIPMENT_ID,e.SHIPMENT_ID) `SHIPMENT_ID` "
                    + " FROM ( "
                    + " SELECT MAX(e.`ERP_ORDER_ID`) AS ERP_ORDER_ID FROM rm_erp_order e "
                    + " WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? "
                    + " ) es "
                    + " LEFT JOIN rm_erp_order e  ON e.`ERP_ORDER_ID`=es.`ERP_ORDER_ID` "
                    + " LEFT JOIN rm_manual_tagging mt ON e.ORDER_NO=mt.ORDER_NO AND e.PRIME_LINE_NO=mt.PRIME_LINE_NO AND mt.ACTIVE "
                    + " LEFT JOIN rm_shipment mts ON mt.SHIPMENT_ID=mts.SHIPMENT_ID "
                    + " ) eo "
                    + " LEFT JOIN (SELECT sx1.SHIPMENT_ID, sx1.PROGRAM_ID, sx1.PARENT_SHIPMENT_ID, MAX(st1.VERSION_ID) MAX_VERSION_ID FROM rm_shipment sx1 LEFT JOIN rm_shipment_trans st1 ON sx1.SHIPMENT_ID=st1.SHIPMENT_ID GROUP BY st1.SHIPMENT_ID) sh ON sh.SHIPMENT_ID=eo.SHIPMENT_ID AND sh.PROGRAM_ID=eo.PROGRAM_ID "
                    + " LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=sh.SHIPMENT_ID AND st.VERSION_ID=sh.MAX_VERSION_ID "
                    + " LEFT JOIN vw_planning_unit pu ON st.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                    + " LEFT JOIN rm_procurement_agent_planning_unit papu ON st.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID` AND papu.`PROCUREMENT_AGENT_ID`=1  "
                    + " LEFT JOIN rm_procurement_agent_planning_unit papu1 ON eo.PLANNING_UNIT_SKU_CODE=LEFT(papu1.SKU_CODE,12) AND papu1.PROCUREMENT_AGENT_ID=1 "
                    + " LEFT JOIN rm_procurement_agent_procurement_unit papu2 ON eo.PROCUREMENT_UNIT_SKU_CODE=LEFT(papu2.SKU_CODE,15) AND papu2.PROCUREMENT_AGENT_ID=1 "
                    + " LEFT JOIN rm_procurement_unit pu2 ON papu2.PROCUREMENT_UNIT_ID=pu2.PROCUREMENT_UNIT_ID "
                    //                    + " LEFT JOIN rm_erp_shipment es ON es.ERP_ORDER_ID=eo.ERP_ORDER_ID "
                    + " LEFT JOIN rm_erp_shipment es ON es.FILE_NAME=? AND es.ORDER_NO=eo.ORDER_NO AND es.PRIME_LINE_NO=eo.PRIME_LINE_NO "
                    + " LEFT JOIN rm_shipment_status_mapping ssm ON eo.`STATUS`=ssm.EXTERNAL_STATUS_STAGE "
                    + " LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=sh.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ";
//                    + " GROUP BY eo.`ERP_ORDER_ID`; ";
            List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sql, new ErpOrderDTOListResultSetExtractor(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), filename);
            logger.info("ERP Linking : found erp order object---" + erpOrderDTOList);
            if (erpOrderDTOList.size() == 1) {
                ErpOrderDTO erpOrderDTO = erpOrderDTOList.get(0);
                try {
                    // Shipment id found in file
                    logger.info("-----------------------------------------------------------");
                    logger.info("ERP Linking : ERP Order - " + erpOrderDTO);
                    logger.info("ERP Linking : Order no - " + erpOrderDTO.getEoOrderNo());
                    logger.info("ERP Linking : Prime line no - " + erpOrderDTO.getEoPrimeLineNo());
                    logger.info("ERP Linking : Active - " + erpOrderDTO.getShActive());
                    logger.info("ERP Linking : ERP Flag - " + erpOrderDTO.getShErpFlag());
                    logger.info("ERP Linking : ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                    logger.info("ERP Linking : Shipment Id - " + erpOrderDTO.getShShipmentId());
                    logger.info("ERP Linking : Change code - " + erpOrderDTO.getEoChangeCode());
                    logger.info("ERP Linking : ManualTagging - " + erpOrderDTO.isManualTagging());
                    logger.info("ERP Linking : Program Id - " + erpOrderDTO.getShProgramId());
                    logger.info("ERP Linking : Shipment id - " + erpOrderDTO.getShShipmentId());
                    if (erpOrderDTO.getEoChangeCode() == 2) {
                        logger.info("ERP Linking : Change code is 2 --- ");
                        // This is the Delete code so go ahead and delete this Order
                        logger.info("Change code is 2 so therefore delete this line item where shipmentId=" + erpOrderDTO.getShShipmentId());
                        sql = "UPDATE rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID SET st.ACTIVE=0, st.LAST_MODIFIED_BY=:curUser, st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=:curUser, s.LAST_MODIFIED_DATE=:curDate WHERE s.PARENT_SHIPMENT_ID=:shipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ACTIVE AND st.ERP_FLAG";
                        params.clear();
//                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                        params.put("shipmentId", erpOrderDTO.getShShipmentId());
                        params.put("orderNo", erpOrderDTO.getEoOrderNo());
                        params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                        params.put("curDate", curDate);
                        params.put("curUser", curUser.getUserId());
                        logger.info("ERP Linking : Change code is 2 params--- " + params);
                        rows = this.namedParameterJdbcTemplate.update(sql, params);
                        logger.info(rows + " rows updated");

                    } else if (erpOrderDTO.isShErpFlag() && erpOrderDTO.getShParentShipmentId() == null) {
//                        System.out.println("---------------3--------------");
                        // The ERP Flag is true and the Parent Shipment Id is null
                        logger.info("ERP Linking : ERP Flag is true and Parent Shipment Id is null");
                        logger.info("ERP Linking : Find all Shipments whose Parent Shipment Id is :parentShipmentId and :orderNo and :primeLineNo are matching");
                        // Find all Shipments whose Parent Shipment Id is :parentShipmentId and :orderNo and :primeLineNo are matching
                        params.clear();
                        params.put("parentShipmentId", erpOrderDTO.getShShipmentId());
                        params.put("orderNo", erpOrderDTO.getEoOrderNo());
                        params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                        logger.info("ERP Linking : Find all Shipments params---" + params);
                        sql = "SELECT  st.SHIPMENT_TRANS_ID "
                                + "    FROM rm_shipment s "
                                + "LEFT JOIN (SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s left join rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo group by st.SHIPMENT_ID) sm ON sm.SHIPMENT_ID=s.SHIPMENT_ID "
                                + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID=sm.MAX_VERSION_ID "
                                + "WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ERP_FLAG=1 AND st.ACTIVE";
                        try {
                            logger.info("ERP Linking : Trying to see if the ShipmentTrans exists with the same orderNo, primeLineNo and parentShipmentId");
                            int shipmentTransId = this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
                            logger.info("ERP Linking : ShipmentTransId " + shipmentTransId + " found so going to update that with latest information");
                            // TODO shipment found therefore update it with all the information
                            sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID "
                                    + "SET  "
                                    + "    st.EXPECTED_DELIVERY_DATE=:expectedDeliveryDate, st.FREIGHT_COST=:freightCost, st.PRODUCT_COST=:productCost, "
                                    + "    st.RATE=:price, st.SHIPMENT_MODE=:shipBy, st.SHIPMENT_QTY=:qty, "
                                    + "    st.SHIPMENT_STATUS_ID=:shipmentStatusId, st.SUPPLIER_ID=:supplierId, st.PLANNED_DATE=:plannedDate, "
                                    + "    st.SUBMITTED_DATE=:submittedDate, st.APPROVED_DATE=:approvedDate, st.SHIPPED_DATE=:shippedDate, "
                                    + "    st.ARRIVED_DATE=:arrivedDate, st.RECEIVED_DATE=:receivedDate, st.LAST_MODIFIED_BY=:curUser, "
                                    + "    st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=:curUser, s.LAST_MODIFIED_DATE=:curDate, st.NOTES=:notes "
                                    + "WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                            params.clear();
//                            params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                            params.put("shipmentTransId", shipmentTransId);
                            params.put("expectedDeliveryDate", erpOrderDTO.getExpectedDeliveryDate());
                            params.put("freightCost", erpOrderDTO.getEoShippingCost());
                            params.put("productCost", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * erpOrderDTO.getEoPrice() : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                            params.put("price", erpOrderDTO.getEoPrice());
                            params.put("shipBy", (erpOrderDTO.getEoShipBy().equals("Land") ? "Road" : (erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : (erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"))));
                            params.put("qty", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (Math.round(erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor())) : erpOrderDTO.getEoQty()));
                            params.put("shipmentStatusId", erpOrderDTO.getEoShipmentStatusId());
                            params.put("supplierId", erpOrderDTO.getEoSupplierId());
                            params.put("plannedDate", erpOrderDTO.getEoCreatedDate());
                            params.put("submittedDate", erpOrderDTO.getEoCreatedDate());
                            params.put("approvedDate", erpOrderDTO.getEoOrderedDate());
                            params.put("shippedDate", erpOrderDTO.getMinActualShipmentDate());
                            params.put("arrivedDate", erpOrderDTO.getMinArrivalAtDestinationDate());
                            params.put("receivedDate", erpOrderDTO.getMinActualDeliveryDate());
                            params.put("curDate", curDate);
                            params.put("curUser", curUser.getUserId());
                            params.put("notes", "Auto updated from shipment linking");
                            logger.info("ERP Linking : params---" + params);
                            this.namedParameterJdbcTemplate.update(sql, params);
                            logger.info("ERP Linking : Updated the already existing Shipment Trans record (" + shipmentTransId + ") with new data");
                            logger.info("ERP Linking : Now need to update the Batch information");
                            sql = "SELECT bi.BATCH_ID, stbi.SHIPMENT_TRANS_BATCH_INFO_ID, bi.BATCH_NO, bi.EXPIRY_DATE, stbi.BATCH_SHIPMENT_QTY FROM rm_shipment_trans_batch_info stbi LEFT JOIN rm_batch_info bi ON stbi.BATCH_ID=bi.BATCH_ID where stbi.SHIPMENT_TRANS_ID=:shipmentTransId group by stbi.BATCH_ID";
                            params.clear();
                            params.put("shipmentTransId", shipmentTransId);
                            List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ErpBatchDTORowMapper());
                            logger.info("ERP Linking : erpBatchList---" + erpBatchList);
                            if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                                logger.info("ERP Linking : Some batch information exists so need to check if it matches with what was already created");
                                logger.info("ERP Linking : erp shipment batch List---" + erpOrderDTO.getEoShipmentList());
                                for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                    try {
                                        logger.info("ERP Linking : erp shipment batch object---" + es);
                                        if (es.isAutoGenerated()) {
                                            // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                            logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                            boolean found = false;
                                            for (ErpBatchDTO eb : erpBatchList) {
                                                if (es.getExpiryDate() != null) {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no need to do anything
                                                        logger.info("ERP Linking : match found so no need to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                        found = true;
                                                        break;
                                                    }
                                                } else {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no need to do anything
                                                        logger.info("ERP Linking : match found so no need to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                        found = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (found == false) {
                                                logger.info("ERP Linking : found false---");
                                                es.setStatus(2); // Insert
                                            }
                                        } else {
                                            // This is not an autogenerated batch which means that we can match it on BatchNo
                                            logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                            ErpBatchDTO tempB = new ErpBatchDTO();
                                            tempB.setBatchNo(es.getBatchNo());
                                            int index = erpBatchList.indexOf(tempB);
                                            logger.info("ERP Linking : batch index---" + index);
                                            if (index == -1) {
                                                // Batch not found
                                                logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                                // therefore need to insert 
                                                es.setStatus(2); // Insert
                                            } else {
                                                // Batch found now check for Expiry date and Qty
                                                logger.info("ERP Linking : Batch found now check for Expiry date and Qty---");
                                                ErpBatchDTO eb = erpBatchList.get(index);
                                                logger.info("ERP Linking : Batch eb---" + eb);
                                                logger.info("ERP Linking : eb.getExpiryDate()---" + eb.getExpiryDate());
                                                logger.info("ERP Linking : es.getExpiryDate()---" + es.getExpiryDate());
                                                logger.info("ERP Linking : eb.getQty()---" + eb.getQty());
                                                logger.info("ERP Linking : es.getBatchQty()---" + es.getBatchQty());
                                                if (es.getExpiryDate() != null) {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) <= 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so no nneed to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                    } else if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) < 0 && eb.getQty() != es.getBatchQty()) {
                                                        es.setStatus(3); // Update
                                                        eb.setStatus(3); // Update shipment trans batch info
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    } else {
                                                        logger.info("ERP Linking : match not found---");
                                                        es.setStatus(1); // Update
                                                        eb.setStatus(1); // Update
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    }
                                                } else {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) <= 0 && eb.getQty() == es.getBatchQty()) {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so no nneed to do anything---");
                                                        eb.setStatus(0); // Leave alone
                                                        es.setStatus(0); // Leave alone
                                                    } else if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) < 0 && eb.getQty() != es.getBatchQty()) {
                                                        es.setStatus(3); // Update
                                                        eb.setStatus(3); // Update shipment trans batch info
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    } else {
                                                        logger.info("ERP Linking : match not found---");
                                                        es.setStatus(1); // Update
                                                        eb.setStatus(1); // Update
                                                        es.setExistingBatchId(eb.getBatchId());
                                                        es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
                                                    }
                                                }
                                            }
                                        }
                                        logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                        logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                        switch (es.getStatus()) {
                                            case 0: // Do nothing
                                                logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                                break;
                                            case 1: // update
                                                logger.info("ERP Linking : Need to update this Batch");
                                                sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                                params.clear();
                                                params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("batchId", es.getExistingBatchId());
                                                logger.info("ERP Linking : case 1 batch info params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                sql = "UPDATE rm_shipment_trans_batch_info stbi SET stbi.BATCH_SHIPMENT_QTY=:qty WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                                params.clear();
                                                params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                                params.put("qty", es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty());
                                                logger.info("ERP Linking : case 1 shipment trans batch info params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                break;
                                            case -1: // Delete
                                                logger.info("ERP Linking : case -1 Need to delete this Batch");
                                                sql = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                                params.clear();
                                                params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                                logger.info("ERP Linking : case -1 params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                break;
                                            case 2: // Insert
                                                logger.info("ERP Linking : case 2 Need to insert this Batch");
                                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                                params.clear();
                                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                                params.put("AUTO_GENERATED", es.isAutoGenerated());
                                                logger.info("ERP Linking : case 2 batch info params---" + params);
                                                int batchId = sib.executeAndReturnKey(params).intValue();
                                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                                params.clear();
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", batchId);
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                            case 3: // Update shipment trans batch info
                                                logger.info("Need to update this Batch case 3");
                                                sql = "UPDATE rm_shipment_trans_batch_info stbi SET stbi.BATCH_SHIPMENT_QTY=:qty WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                                params.clear();
                                                params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                                params.put("qty", es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty());
                                                logger.info("Params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
                                                break;
                                        }
                                    } catch (Exception e) {
                                        logger.info("ERP Linking : Error occured for batch---> " + es);
                                        e.printStackTrace();
                                    }
                                }
                                logger.info("ERP Linking : Checking if any old batches need to be deleted");
                                for (ErpBatchDTO eb : erpBatchList) {
                                    logger.info("ERP Linking : old batch objects---" + eb);
                                    if (eb.getStatus() == -1) {
                                        logger.info("ERP Linking : Batch no: " + eb.getBatchNo() + " Qty:" + eb.getQty() + " is going to be deleted");
                                        sql = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                        params.clear();
                                        params.put("shipmentTransBatchInfoId", eb.getShipmentTransBatchInfoId());
                                        logger.info("ERP Linking : old batch shipment trans batch info---" + params);
                                        this.namedParameterJdbcTemplate.update(sql, params);
                                    }
                                }
                            }
                        } catch (EmptyResultDataAccessException erda) {
                            // Counldn't find a record that matches the Order no and Prime Line no so go ahead and
                            logger.info("ERP Linking : Counldn't find a record that matches the Order no and Prime Line no so go ahead and");
                            logger.info("ERP Linking : Couldn't find a Shipment Trans so this is a new record going ahead with creation");
                            // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                            // All other details to be taken from ARTMIS
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("SUGGESTED_QTY", null);
                            params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                            params.put("CONVERSION_RATE_TO_USD", 1);
                            params.put("PARENT_SHIPMENT_ID", (erpOrderDTO.getShParentShipmentId() != null ? erpOrderDTO.getShParentShipmentId() : erpOrderDTO.getShShipmentId()));
                            params.put("CREATED_BY", curUser.getUserId()); //Default auto user in QAT
                            params.put("CREATED_DATE", curDate);
                            params.put("LAST_MODIFIED_BY", curUser.getUserId()); //Default auto user in QAT
                            params.put("LAST_MODIFIED_DATE", curDate);
                            params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                            logger.info("ERP Linking : shipment data params---" + params);
                            int newShipmentId = si.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Shipment Id " + newShipmentId + " created");
                            SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                            params.clear();
                            params.put("SHIPMENT_ID", newShipmentId);
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                            params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                            params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                            params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                            params.put("PROCUREMENT_UNIT_ID", (erpOrderDTO.getEoProcurementUnitId() != 0 ? erpOrderDTO.getEoProcurementUnitId() : null));
                            params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                            logger.info("ERP Linking : qty---" + erpOrderDTO.getEoQty());
                            logger.info("ERP Linking : conversion factor---" + erpOrderDTO.getConversionFactor());
                            params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (Math.round(erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor())) : erpOrderDTO.getEoQty()));
                            params.put("RATE", (erpOrderDTO.getEoPrice() / erpOrderDTO.getConversionFactor()));
                            params.put("PRODUCT_COST", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * (erpOrderDTO.getEoPrice() / manualTaggingOrderDTO.getConversionFactor()) : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                            params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") ? "Road" : (erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : (erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"))));
                            params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                            params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                            params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                            params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                            params.put("SHIPPED_DATE", erpOrderDTO.getMinActualShipmentDate());
                            params.put("ARRIVED_DATE", erpOrderDTO.getMinArrivalAtDestinationDate());
                            params.put("RECEIVED_DATE", erpOrderDTO.getMinActualDeliveryDate());
                            params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                            params.put("NOTES", (manualTaggingOrderDTO.getNotes() != null && manualTaggingOrderDTO.getNotes() != "" ? manualTaggingOrderDTO.getNotes() : "Auto created from shipment linking"));
                            params.put("ERP_FLAG", 1);
                            params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                            params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                            params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                            params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                            params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                            params.put("LAST_MODIFIED_BY", curUser.getUserId()); // Default user
                            params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                            params.put("LAST_MODIFIED_DATE", curDate);
                            params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                            params.put("ACTIVE", true);
                            logger.info("ERP Linking : shipment trans data params---" + params);
                            int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Shipment Trans Id " + shipmentTransId + " created");
                            if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                                logger.info("ERP Linking : Some batch information exists so going to create Batches---" + erpOrderDTO.getEoShipmentList());
                                for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                    try {
                                        logger.info("ERP Linking : batch data object---" + es);
                                        //New code for batch start
                                        if (es.isAutoGenerated()) {
                                            // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                            logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                            es.setStatus(2); // Insert
                                        } else {
                                            // This is not an autogenerated batch which means that we can match it on BatchNo
                                            logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                            sql = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.EXPIRY_DATE "
                                                    + "FROM rm_batch_info bi WHERE  bi.`PROGRAM_ID`=:programId AND bi.`PLANNING_UNIT_ID`=:planningUnitId AND bi.`BATCH_NO`=:batchNo;";
                                            params.clear();
                                            params.put("programId", manualTaggingOrderDTO.getProgramId());
                                            params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                                            params.put("batchNo", es.getBatchNo());
//                                        params.put("expiryDate", es.getExpiryDate());
                                            List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ERPNewBatchDTORowMapper());
                                            logger.info("ERP Linking : erpBatchList---" + erpBatchList);

                                            if (erpBatchList.size() > 0) {
                                                ErpBatchDTO tempB = new ErpBatchDTO();
                                                tempB.setBatchNo(es.getBatchNo());
                                                int index = erpBatchList.indexOf(tempB);
                                                ErpBatchDTO eb = erpBatchList.get(index);
                                                logger.info("ERP Linking : Batch eb---" + eb);
                                                logger.info("ERP Linking : batch index---" + index);
                                                if (es.getExpiryDate() != null) {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) > 0) {
                                                        // Update the batch table with less es.expiry date
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(1); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    } else {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(3); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    }
                                                } else {
                                                    if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) > 0) {
                                                        // Update the batch table with less es.expiry date
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(1); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    } else {
                                                        // match found so no nneed to do anything
                                                        logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                        es.setStatus(3); // Leave alone
                                                        es.setExistingBatchId(eb.getBatchId());
                                                    }
                                                }
                                            } else {
                                                // Batch not found
                                                logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                                es.setStatus(2); // Insert
                                            }
                                        }

                                        logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                        logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                        switch (es.getStatus()) {
                                            case 0: // Do nothing
                                                logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                                break;
                                            case 1: // update
                                                logger.info("ERP Linking : Need to update this Batch");
                                                sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                                params.clear();
                                                params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("batchId", es.getExistingBatchId());
                                                logger.info("ERP Linking : case 1 batch info params---" + params);
                                                this.namedParameterJdbcTemplate.update(sql, params);
//                                             sib = null;
                                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", es.getExistingBatchId());
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                            case 2: // Insert
                                                logger.info("ERP Linking : case 2 Need to insert this Batch");
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                                params.clear();
                                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                                params.put("AUTO_GENERATED", es.isAutoGenerated());
                                                logger.info("ERP Linking : case 2 batch info params---" + params);
                                                int batchId = sib.executeAndReturnKey(params).intValue();
                                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                                params.clear();
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", batchId);
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                            case 3: // Insert
                                                logger.info("ERP Linking : case 3 Need to insert into shipment trans Batch info");
                                                params.clear();
                                                sib = null;
                                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                                params.put("BATCH_ID", es.getExistingBatchId());
                                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                                logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                                sib.execute(params);
                                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                                break;
                                        }
//                                    // Insert into Batch info for each record
//                                    SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
//                                    params.clear();
//                                    params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
//                                    params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
//                                    params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
//                                    params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
//                                    params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
//                                    params.put("AUTO_GENERATED", es.isAutoGenerated());
//                                    logger.info("ERP Linking : batch info params---" + params);
//                                    int batchId = sib.executeAndReturnKey(params).intValue();
//                                    logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
//                                    params.clear();
//                                    sib = null;
//                                    sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
//                                    params.put("SHIPMENT_TRANS_ID", shipmentTransId);
//                                    params.put("BATCH_ID", batchId);
//                                    params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
//                                    logger.info("ERP Linking :shipment trans batch info params---" + params);
//                                    sib.execute(params);
//                                    logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                    } catch (Exception e) {
                                        logger.info("ERP Linking : Error occured for batch---> " + es);
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                // Insert into Batch info for each record
                                logger.info("ERP Linking : No Batch information exists so creating one automatically");
                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                params.clear();
                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                                params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                params.put("AUTO_GENERATED", true);
                                logger.info("ERP Linking :batch info params---" + params);
                                int batchId = sib.executeAndReturnKey(params).intValue();
                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                params.clear();
                                sib = null;
                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                params.put("BATCH_ID", batchId);
                                params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                                logger.info("ERP Linking :shipment trans batch info params---" + params);
                                sib.execute(params);
                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                            }
                        }
//                        if (erpOrderDTO.isShipmentCancelled() || (erpOrderDTO.getErpPlanningUnitId() != this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()))) {
//                            System.out.println("Inside notification------------------------------------------------------------");
//                            System.out.println("Is shipment cancelled-------------------------" + erpOrderDTO.isShipmentCancelled());
//                            System.out.println("Is sku changed--------------------------------------" + erpOrderDTO.isSkuChanged());
//                            System.out.println("previous erp order------------" + this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()));
//                            System.out.println("Current erp planning unit---" + erpOrderDTO.getErpPlanningUnitId());
//                            this.createERPNotification(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo(), erpOrderDTO.getShShipmentId(), (erpOrderDTO.isShipmentCancelled() ? 1 : 2));
//                        }
                    } else {
//                        System.out.println("---------------4--------------");
                        // This is a new Link request coming through
                        // So make the Shipment, Active = fasle and ERPFlag = true
                        logger.info("ERP Linking : This is a new Link request coming through.So make the Shipment, Active = fasle and ERPFlag = true");
                        logger.info("ERP Linking : This is a first time linking attempt");
                        logger.info("ERP Linking : Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo.All other details to be taken from ARTMIS + Current Shipment");
                        // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                        // All other details to be taken from ARTMIS + Current Shipment
//                        sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID SET st.`PLANNING_UNIT_ID`=:planningUnitId,st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                        sql = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID "
                                + " SET st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=:curUser, "
                                + " s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=:curUser, st.LAST_MODIFIED_DATE=:curDate "
                                + " WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                        params.clear();
//                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
//                        System.out.println("shipment trans id-------------------------" + erpOrderDTO.getShShipmentTransId());
                        params.put("curUser", curUser.getUserId());
                        params.put("curDate", curDate);
                        params.put("shipmentTransId", erpOrderDTO.getShShipmentTransId());
                        logger.info("ERP Linking : update shipment trans params---" + params);
                        this.namedParameterJdbcTemplate.update(sql, params);
                        logger.info("ERP Linking : Existing Shipment has been marked as ERP_FLAG=true and ACTIVE=false");
                        params.clear();
                        params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                        params.put("SUGGESTED_QTY", null);
                        params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                        params.put("CONVERSION_RATE_TO_USD", 1);
                        params.put("PARENT_SHIPMENT_ID", (erpOrderDTO.getShParentShipmentId() != null ? erpOrderDTO.getShParentShipmentId() : erpOrderDTO.getShShipmentId()));
                        params.put("CREATED_BY", curUser.getUserId()); //Default auto user in QAT
                        params.put("CREATED_DATE", curDate);
                        params.put("LAST_MODIFIED_BY", curUser.getUserId()); //Default auto user in QAT
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                        logger.info("ERP Linking : create entry in shipment params---" + params);
                        int newShipmentId = si.executeAndReturnKey(params).intValue();
                        logger.info("ERP Linking : Shipment Id " + newShipmentId + " created");
                        SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                        params.clear();
                        params.put("SHIPMENT_ID", newShipmentId);
                        params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                        params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                        params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                        params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                        params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                        params.put("PROCUREMENT_UNIT_ID", (erpOrderDTO.getEoProcurementUnitId() != 0 ? erpOrderDTO.getEoProcurementUnitId() : null));
                        params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                        params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (Math.round(erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor())) : erpOrderDTO.getEoQty()));
                        params.put("RATE", (erpOrderDTO.getEoPrice() / erpOrderDTO.getConversionFactor()));
                        params.put("PRODUCT_COST", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getConversionFactor() * erpOrderDTO.getEoQty()) * erpOrderDTO.getEoPrice() : (erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty())));
                        params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") ? "Road" : (erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : (erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"))));
                        params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                        params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                        params.put("SHIPPED_DATE", erpOrderDTO.getMinActualShipmentDate());
                        params.put("ARRIVED_DATE", erpOrderDTO.getMinArrivalAtDestinationDate());
                        params.put("RECEIVED_DATE", erpOrderDTO.getMinActualDeliveryDate());
                        params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                        params.put("NOTES", (manualTaggingOrderDTO.getNotes() != null && manualTaggingOrderDTO.getNotes() != "" ? manualTaggingOrderDTO.getNotes() : "Auto created from shipment linking"));
                        params.put("ERP_FLAG", 1);
                        params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                        params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                        params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                        params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                        params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                        params.put("LAST_MODIFIED_BY", curUser.getUserId()); // Default user
                        params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                        params.put("ACTIVE", true);
                        logger.info("ERP Linking : create entry in shipment trans params---" + params);
                        int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                        logger.info("ERP Linking : Shipment Trans Id " + shipmentTransId + " created");
                        if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                            logger.info("ERP Linking : Some batch information exists so going to create Batches");
                            for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                try {
                                    //New code for batch start
                                    if (es.isAutoGenerated()) {
                                        // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                        logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                        es.setStatus(2); // Insert
                                    } else {
                                        // This is not an autogenerated batch which means that we can match it on BatchNo
                                        logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                        sql = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.EXPIRY_DATE "
                                                + "FROM rm_batch_info bi WHERE  bi.`PROGRAM_ID`=:programId AND bi.`PLANNING_UNIT_ID`=:planningUnitId AND bi.`BATCH_NO`=:batchNo;";
                                        params.clear();
                                        params.put("programId", manualTaggingOrderDTO.getProgramId());
                                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                                        params.put("batchNo", es.getBatchNo());
//                                        params.put("expiryDate", es.getExpiryDate());
                                        List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ERPNewBatchDTORowMapper());
                                        logger.info("ERP Linking : erpBatchList---" + erpBatchList.toString());

                                        if (erpBatchList.size() > 0) {
                                            ErpBatchDTO tempB = new ErpBatchDTO();
                                            tempB.setBatchNo(es.getBatchNo());
                                            int index = erpBatchList.indexOf(tempB);
                                            logger.info("ERP Linking : tempB---" + tempB);
                                            logger.info("ERP Linking : batch no---" + es.getBatchNo());
                                            logger.info("ERP Linking : batch index---" + index);
                                            ErpBatchDTO eb = erpBatchList.get(index);
                                            logger.info("ERP Linking : Batch eb---" + eb);
                                            if (es.getExpiryDate() != null) {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            } else {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            }
                                        } else {
                                            // Batch not found
                                            logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                            es.setStatus(2); // Insert
                                        }
                                    }

                                    logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                    logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                    switch (es.getStatus()) {
                                        case 0: // Do nothing
                                            logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                            break;
                                        case 1: // update
                                            logger.info("ERP Linking : Need to update this Batch");
                                            sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                            params.clear();
                                            params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("batchId", es.getExistingBatchId());
                                            logger.info("ERP Linking : case 1 batch info params---" + params);
                                            this.namedParameterJdbcTemplate.update(sql, params);
//                                             sib = null;
                                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 2: // Insert
                                            logger.info("ERP Linking : case 2 Need to insert this Batch");
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                            params.clear();
                                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                            params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                            params.put("AUTO_GENERATED", es.isAutoGenerated());
                                            logger.info("ERP Linking : case 2 batch info params---" + params);
                                            int batchId = sib.executeAndReturnKey(params).intValue();
                                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", batchId);
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 3: // Insert
                                            logger.info("ERP Linking : case 3 Need to insert into shipment trans Batch info");
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 3 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                    }
                                    // New code for batch end
                                    // Insert into Batch info for each record
                                    // Old code
//                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
//                                params.clear();
//                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
//                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
//                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
//                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
//                                params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
//                                params.put("AUTO_GENERATED", es.isAutoGenerated());
//                                logger.info("ERP Linking : create entry in batch info params---" + params);
//                                int batchId = sib.executeAndReturnKey(params).intValue();
//                                logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
//                                params.clear();
//                                sib = null;
//                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
//                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
//                                params.put("BATCH_ID", batchId);
//                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
//                                logger.info("ERP Linking : create entry in shipment trans batch info params---" + params);
//                                sib.execute(params);
//                                logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                } catch (Exception e) {
                                    logger.info("ERP Linking : Error occured for batch---> " + es);
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            // Insert into Batch info for each record
                            logger.info("ERP Linking : No Batch information exists so creating one automatically");
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                            params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                            params.put("AUTO_GENERATED", true);
                            logger.info("ERP Linking : create entry in batch info params---" + params);
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                            logger.info("ERP Linking : create entry in shipment trans batch info params---" + params);
                            sib.execute(params);
                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                        }
//                        if (erpOrderDTO.isShipmentCancelled() || erpOrderDTO.isSkuChanged() || (erpOrderDTO.getErpPlanningUnitId() != this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()))) {
//                        if (erpOrderDTO.isShipmentCancelled() || (erpOrderDTO.getErpPlanningUnitId() != this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()))) {
//                            System.out.println("Inside notification------------------------------------------------------------");
//                            System.out.println("Is shipment cancelled-------------------------" + erpOrderDTO.isShipmentCancelled());
//                            System.out.println("Is sku changed--------------------------------------" + erpOrderDTO.isSkuChanged());
//                            System.out.println("previous erp order------------" + this.checkPreviousARTMISPlanningUnitId(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo()));
//                            System.out.println("Current erp planning unit---" + erpOrderDTO.getErpPlanningUnitId());
//                            this.createERPNotification(erpOrderDTO.getEoOrderNo(), erpOrderDTO.getEoPrimeLineNo(), erpOrderDTO.getShShipmentId(), (erpOrderDTO.isShipmentCancelled() ? 1 : 2));
//                        }
                    }

                } catch (Exception e) {
                    logger.info("ERP Linking : Error occurred while trying to import Shipment ", e);
                }

            }
        }
        return row;
//        } else {
//            //Update conversion factor and notes
//            logger.info("ERP Linking : Going to update conversion factor and notes");
//            logger.info("ERP Linking : manual tagging object---" + manualTaggingOrderDTO);
//            sql = " SELECT st.`SHIPMENT_TRANS_ID`,st.`RATE` FROM rm_shipment_trans st "
//                    + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
//                    + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE` "
//                    + "ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1;";
//
//            Map<String, Object> map = this.jdbcTemplate.queryForMap(sql, manualTaggingOrderDTO.getParentShipmentId(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
//            logger.info("ERP Linking : Get shipment trans info---" + map);
//
//            sql = "UPDATE `rm_manual_tagging` m SET m.`CONVERSION_FACTOR`=?,m.`NOTES`=? "
//                    + " WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE` AND m.`SHIPMENT_ID`=?; ";
//            int rowsUpdated = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getConversionFactor(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), manualTaggingOrderDTO.getParentShipmentId());
//            logger.info("ERP Linking : updated conversion factor and notes rows---" + rowsUpdated);
//
////            System.out.println("conversion factor---" + manualTaggingOrderDTO.getConversionFactor());
//            sql = "UPDATE rm_shipment_trans st  SET st.`SHIPMENT_QTY`=?,st.`PRODUCT_COST`=?, "
//                    + "st.`LAST_MODIFIED_DATE`=?,st.`LAST_MODIFIED_BY`=?,st.`NOTES`=? "
//                    + "WHERE st.`SHIPMENT_TRANS_ID`=?;";
////            long convertedQty = ((new BigDecimal(manualTaggingOrderDTO.getQuantity())).multiply(manualTaggingOrderDTO.getConversionFactor())).longValueExact();
//
//            long convertedQty = (long) Math.round((double) manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor());
//            logger.info("ERP Linking : convertedQty---" + convertedQty);
//            logger.info("ERP Linking : rate---" + map.get("RATE"));
//            logger.info("ERP Linking : product cost---" + Double.parseDouble(map.get("RATE").toString()));
//            double rate = Double.parseDouble(map.get("RATE").toString());
//            double productCost = rate * (double) convertedQty;
//            logger.info("ERP Linking : final product cost---" + productCost);
//            rowsUpdated = this.jdbcTemplate.update(sql, Math.round(convertedQty), productCost, curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), (long) map.get("SHIPMENT_TRANS_ID"));
//            logger.info("ERP Linking : updated shipment trans---" + rowsUpdated);
//            return -1;
//        }
    }

    @Override
    @Transactional
    public int linkShipmentWithARTMISWithoutShipmentid(ManualTaggingOrderDTO manualTaggingOrderDTO, CustomUserDetails curUser) {
        logger.info("ERP Linking : link Shipment With ARTMIS Without Shipmentid---");
        logger.info("ERP Linking : manualTaggingOrderDTO---" + manualTaggingOrderDTO);
        logger.info("ERP Linking : Curuser---" + curUser);
        String sql;
        int count = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int parentShipmentId = 0;
        Map<String, Object> params = new HashMap<>();
        sql = "SELECT COUNT(*) FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
        count = this.jdbcTemplate.queryForObject(sql, Integer.class, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());

        logger.info("ERP Linking : manual tagging order no---" + manualTaggingOrderDTO.getOrderNo());
        logger.info("ERP Linking : manual tagging prime line no.---" + manualTaggingOrderDTO.getPrimeLineNo());
        logger.info("ERP Linking : manual tagging count---" + count);

        if (count == 0) {
            for (int i = 1; i <= 2; i++) {
                logger.info("ERP Linking : Going to create shipment table entry---");
                SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                params.put("SUGGESTED_QTY", null);
                params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                params.put("CONVERSION_RATE_TO_USD", 1);
                params.put("PARENT_SHIPMENT_ID", (i == 1 ? null : parentShipmentId));
                params.put("CREATED_BY", curUser.getUserId()); //Default auto user in QAT
                params.put("CREATED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId()); //Default auto user in QAT
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("MAX_VERSION_ID", 1); // Same as the Current Version that is already present
                logger.info("ERP Linking : Going to create shipment table entry params---" + params);
                int newShipmentId = si.executeAndReturnKey(params).intValue();
                logger.info("ERP Linking : Shipment Id " + newShipmentId + " created");
                if (i == 1) {
                    parentShipmentId = newShipmentId;
                    logger.info("ERP Linking : Going to create manual tagging entry parent shipment id---" + parentShipmentId);
                    sql = "INSERT INTO rm_manual_tagging VALUES (NULL,?,?,?,?,?,?,?,1,?,?);";
                    int row = this.jdbcTemplate.update(sql, manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), newShipmentId, curDate, curUser.getUserId(), curDate, curUser.getUserId(), manualTaggingOrderDTO.getNotes(), manualTaggingOrderDTO.getConversionFactor());
                    logger.info("ERP Linking : rows inserted in manual tagging---" + row);

                    logger.info("ERP Linking : Going to do entry in tab3 shipment table---");
                    this.tab3ShipmentCreation(newShipmentId, curUser);
                }
                String filename = this.getMaxERPOrderIdFromERPShipment(manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo());
                sql = " SELECT "
                        + " eo.ERP_ORDER_ID, eo.RO_NO, eo.RO_PRIME_LINE_NO, eo.ORDER_NO, eo.PRIME_LINE_NO , "
                        + " eo.ORDER_TYPE, eo.CREATED_DATE, eo.PARENT_RO, eo.PARENT_CREATED_DATE, eo.PLANNING_UNIT_SKU_CODE,  "
                        + " eo.PROCUREMENT_UNIT_SKU_CODE, eo.QTY, eo.ORDERD_DATE,eo.CURRENT_ESTIMATED_DELIVERY_DATE, eo.REQ_DELIVERY_DATE,  "
                        + " eo.AGREED_DELIVERY_DATE, eo.SUPPLIER_NAME, eo.PRICE, eo.SHIPPING_COST, eo.SHIP_BY,  "
                        + " eo.RECPIENT_NAME, eo.RECPIENT_COUNTRY, eo.`STATUS`, eo.`CHANGE_CODE`, ssm.SHIPMENT_STATUS_ID, eo.MANUAL_TAGGING, eo.CONVERSION_FACTOR, "
                        + " es.ACTUAL_DELIVERY_DATE, es.ACTUAL_SHIPMENT_DATE, es.ARRIVAL_AT_DESTINATION_DATE, "
                        + " es.BATCH_NO, IF(es.DELIVERED_QTY !=0,COALESCE(es.DELIVERED_QTY, es.SHIPPED_QTY),es.SHIPPED_QTY) `BATCH_QTY`, es.`EXPIRY_DATE`, "
                        + " st.PLANNING_UNIT_ID,papu.PLANNING_UNIT_ID AS ERP_PLANNING_UNIT_ID, papu2.PROCUREMENT_UNIT_ID, pu2.SUPPLIER_ID, ppu.SHELF_LIFE, "
                        + " sh.SHIPMENT_ID, sh.PROGRAM_ID, sh.PARENT_SHIPMENT_ID, "
                        + " st.SHIPMENT_TRANS_ID, st.VERSION_ID, st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.BUDGET_ID, st.ACTIVE, st.ERP_FLAG, st.ACCOUNT_FLAG, st.DATA_SOURCE_ID,eo.CONVERSION_FACTOR  "
                        + " FROM ( "
                        + " SELECT   "
                        + " e.ERP_ORDER_ID, e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO ,  "
                        + " e.ORDER_TYPE, e.CREATED_DATE, e.PARENT_RO, e.PARENT_CREATED_DATE, e.PLANNING_UNIT_SKU_CODE,   "
                        + " e.PROCUREMENT_UNIT_SKU_CODE, e.QTY, e.ORDERD_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE, e.REQ_DELIVERY_DATE,   "
                        + " e.AGREED_DELIVERY_DATE, e.SUPPLIER_NAME, e.PRICE, e.SHIPPING_COST, e.SHIP_BY, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, TRUE, FALSE) `MANUAL_TAGGING`, IF(mt.MANUAL_TAGGING_ID IS NOT NULL, mt.CONVERSION_FACTOR, 1) `CONVERSION_FACTOR`,   "
                        + " e.RECPIENT_NAME, e.RECPIENT_COUNTRY, e.STATUS, e.CHANGE_CODE, COALESCE(e.PROGRAM_ID, mts.PROGRAM_ID) `PROGRAM_ID`, COALESCE(mt.SHIPMENT_ID,e.SHIPMENT_ID) `SHIPMENT_ID`  "
                        + " FROM (  "
                        + " SELECT MAX(e.`ERP_ORDER_ID`) AS ERP_ORDER_ID FROM rm_erp_order e  "
                        + " WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=?  "
                        + " ) es  "
                        + " LEFT JOIN rm_erp_order e  ON e.`ERP_ORDER_ID`=es.`ERP_ORDER_ID`  "
                        + " LEFT JOIN rm_manual_tagging mt ON e.ORDER_NO=mt.ORDER_NO AND e.PRIME_LINE_NO=mt.PRIME_LINE_NO AND mt.ACTIVE AND mt.SHIPMENT_ID=?  "
                        + " LEFT JOIN rm_shipment mts ON mt.SHIPMENT_ID=mts.SHIPMENT_ID  "
                        + " ) eo  "
                        + " LEFT JOIN (SELECT sx1.SHIPMENT_ID, sx1.PROGRAM_ID, sx1.PARENT_SHIPMENT_ID, MAX(st1.VERSION_ID) MAX_VERSION_ID FROM rm_shipment sx1 LEFT JOIN rm_shipment_trans st1 ON sx1.SHIPMENT_ID=st1.SHIPMENT_ID GROUP BY st1.SHIPMENT_ID) sh ON sh.SHIPMENT_ID=eo.SHIPMENT_ID AND sh.PROGRAM_ID=eo.PROGRAM_ID  "
                        + " LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=sh.SHIPMENT_ID AND st.VERSION_ID=sh.MAX_VERSION_ID  "
                        + " LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=eo.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1   "
                        + " LEFT JOIN vw_planning_unit pu ON st.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                        + " LEFT JOIN rm_procurement_agent_procurement_unit papu2 ON eo.PROCUREMENT_UNIT_SKU_CODE=LEFT(papu2.SKU_CODE,15) AND papu2.PROCUREMENT_AGENT_ID=1  "
                        + " LEFT JOIN rm_procurement_unit pu2 ON papu2.PROCUREMENT_UNIT_ID=pu2.PROCUREMENT_UNIT_ID "
                        //                    + "                     LEFT JOIN rm_erp_shipment es ON es.ERP_ORDER_ID=eo.ERP_ORDER_ID  "
                        + " LEFT JOIN rm_erp_shipment es ON es.FILE_NAME=? AND es.ORDER_NO=eo.ORDER_NO AND es.PRIME_LINE_NO=eo.PRIME_LINE_NO "
                        + " LEFT JOIN rm_shipment_status_mapping ssm ON eo.`STATUS`=ssm.EXTERNAL_STATUS_STAGE "
                        + " LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=sh.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  ";
//                    + "                     GROUP BY eo.`ERP_ORDER_ID`;";
                List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sql, new ErpOrderDTOListResultSetExtractor(), manualTaggingOrderDTO.getOrderNo(), manualTaggingOrderDTO.getPrimeLineNo(), (i == 1 ? newShipmentId : parentShipmentId), filename);
                logger.info("ERP Linking : erp order---" + erpOrderDTOList);
                if (erpOrderDTOList.size() == 1) {
                    ErpOrderDTO erpOrderDTO = erpOrderDTOList.get(0);
                    try {
                        // Shipment id found in file
                        logger.info("-----------------------------------------------------------");
                        logger.info("ERP Linking : ERP Order - " + erpOrderDTO);
                        logger.info("ERP Linking : Order no - " + erpOrderDTO.getEoOrderNo());
                        logger.info("ERP Linking : Prime line no - " + erpOrderDTO.getEoPrimeLineNo());
                        logger.info("ERP Linking : Active - " + erpOrderDTO.getShActive());
                        logger.info("ERP Linking : ERP Flag - " + erpOrderDTO.getShErpFlag());
                        logger.info("ERP Linking : ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                        logger.info("ERP Linking : Shipment Id - " + erpOrderDTO.getShShipmentId());
                        logger.info("ERP Linking : Change code - " + erpOrderDTO.getEoChangeCode());
                        logger.info("ERP Linking : ManualTagging - " + erpOrderDTO.isManualTagging());
                        logger.info("ERP Linking : Program Id - " + erpOrderDTO.getShProgramId());
                        logger.info("ERP Linking : Shipment id - " + erpOrderDTO.getShShipmentId());
                        SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                        params.clear();
                        params.put("SHIPMENT_ID", newShipmentId);
                        params.put("PLANNING_UNIT_ID", manualTaggingOrderDTO.getPlanningUnitId());
                        params.put("PROCUREMENT_AGENT_ID", 1);
                        params.put("FUNDING_SOURCE_ID", manualTaggingOrderDTO.getFundingSourceId());
                        params.put("BUDGET_ID", manualTaggingOrderDTO.getBudgetId());
                        params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                        params.put("PROCUREMENT_UNIT_ID", (erpOrderDTO.getEoProcurementUnitId() != 0 ? erpOrderDTO.getEoProcurementUnitId() : null));
                        params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                        params.put("SHIPMENT_QTY", (manualTaggingOrderDTO.getConversionFactor() != 0 && manualTaggingOrderDTO.getConversionFactor() != 0.0 ? (Math.round(manualTaggingOrderDTO.getQuantity() * manualTaggingOrderDTO.getConversionFactor())) : manualTaggingOrderDTO.getQuantity()));
                        params.put("RATE", (erpOrderDTO.getEoPrice() / manualTaggingOrderDTO.getConversionFactor()));
                        params.put("PRODUCT_COST", (manualTaggingOrderDTO.getConversionFactor() != 0 && manualTaggingOrderDTO.getConversionFactor() != 0.0 ? (manualTaggingOrderDTO.getConversionFactor() * manualTaggingOrderDTO.getQuantity()) * (erpOrderDTO.getEoPrice() / manualTaggingOrderDTO.getConversionFactor()) : (erpOrderDTO.getEoPrice() * manualTaggingOrderDTO.getQuantity())));
//                    params.put("PRODUCT_COST", manualTaggingOrderDTO.getQuantity() * erpOrderDTO.getEoPrice());
                        params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") ? "Road" : (erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : (erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"))));
                        params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                        params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                        params.put("SHIPPED_DATE", erpOrderDTO.getMinActualShipmentDate());
                        params.put("ARRIVED_DATE", erpOrderDTO.getMinArrivalAtDestinationDate());
                        params.put("RECEIVED_DATE", erpOrderDTO.getMinActualDeliveryDate());
                        params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
//                    params.put("NOTES", "Auto created from shipment linking...");
                        params.put("NOTES", (i == 1 ? "Auto created from not linked(ERP)..." : manualTaggingOrderDTO.getNotes()));
                        params.put("ERP_FLAG", 1);
                        params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                        params.put("PRIME_LINE_NO", (i == 1 ? null : erpOrderDTO.getEoPrimeLineNo()));
                        params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                        params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                        params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                        params.put("LAST_MODIFIED_BY", curUser.getUserId()); // Default user
                        params.put("DATA_SOURCE_ID", 17);
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("VERSION_ID", 1);
                        params.put("ACTIVE", (i == 1 ? false : true));
                        logger.info("ERP Linking : shipment trans params---" + params);
                        int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                        logger.info("ERP Linking : Shipment Trans Id " + shipmentTransId + " created");
                        if (!erpOrderDTO.getEoShipmentList().isEmpty() && i != 1) {
                            logger.info("ERP Linking : Some batch information exists so going to create Batches---" + erpOrderDTO.getEoShipmentList());
                            for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                try {
                                    //New code for batch
                                    if (es.isAutoGenerated()) {
                                        // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                        logger.info("ERP Linking : This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date");
                                        es.setStatus(2); // Insert
                                    } else {
                                        // This is not an autogenerated batch which means that we can match it on BatchNo
                                        logger.info("ERP Linking : This is not an autogenerated batch which means that we can match it on BatchNo---");
                                        sql = "SELECT bi.BATCH_ID, bi.BATCH_NO, bi.EXPIRY_DATE "
                                                + "FROM rm_batch_info bi WHERE  bi.`PROGRAM_ID`=:programId AND bi.`PLANNING_UNIT_ID`=:planningUnitId AND bi.`BATCH_NO`=:batchNo;";
                                        params.clear();
                                        params.put("programId", manualTaggingOrderDTO.getProgramId());
                                        params.put("planningUnitId", manualTaggingOrderDTO.getPlanningUnitId());
                                        params.put("batchNo", es.getBatchNo());
//                                        params.put("expiryDate", es.getExpiryDate());
                                        List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sql, params, new ERPNewBatchDTORowMapper());
                                        logger.info("ERP Linking : erpBatchList---" + erpBatchList);

                                        if (erpBatchList.size() > 0) {
                                            ErpBatchDTO tempB = new ErpBatchDTO();
                                            tempB.setBatchNo(es.getBatchNo());
                                            int index = erpBatchList.indexOf(tempB);
                                            ErpBatchDTO eb = erpBatchList.get(index);
                                            logger.info("ERP Linking : Batch eb---" + eb);
                                            logger.info("ERP Linking : batch index---" + index);
                                            if (es.getExpiryDate() != null) {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            } else {
                                                if (DateUtils.compareDate(eb.getExpiryDate(), erpOrderDTO.getCalculatedExpiryDate()) > 0) {
                                                    // Update the batch table with less es.expiry date
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(1); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                } else {
                                                    // match found so no nneed to do anything
                                                    logger.info("ERP Linking : match found so do entry in shipment trans batch info---");
                                                    es.setStatus(3); // Leave alone
                                                    es.setExistingBatchId(eb.getBatchId());
                                                }
                                            }
                                        } else {
                                            // Batch not found
                                            logger.info("ERP Linking : Batch not found therefore need to insert ---");
                                            es.setStatus(2); // Insert
                                        }
                                    }

                                    logger.info("ERP Linking : Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
                                    logger.info("ERP Linking : es.getStatus()---" + es.getStatus());
                                    switch (es.getStatus()) {
                                        case 0: // Do nothing
                                            logger.info("ERP Linking : case 0 This Batch matched with what was already there so do nothing");
                                            break;
                                        case 1: // update
                                            logger.info("ERP Linking : Need to update this Batch");
                                            sql = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                            params.clear();
                                            params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("batchId", es.getExistingBatchId());
                                            logger.info("ERP Linking : case 1 batch info params---" + params);
                                            this.namedParameterJdbcTemplate.update(sql, params);
//                                             sib = null;
                                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 2: // Insert
                                            logger.info("ERP Linking : case 2 Need to insert this Batch");
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                            params.clear();
                                            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                                            params.put("PLANNING_UNIT_ID", manualTaggingOrderDTO.getPlanningUnitId());
                                            params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                                            params.put("AUTO_GENERATED", es.isAutoGenerated());
                                            logger.info("ERP Linking : case 2 batch info params---" + params);
                                            int batchId = sib.executeAndReturnKey(params).intValue();
                                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", batchId);
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                        case 3: // Insert
                                            logger.info("ERP Linking : case 3 Need to insert into shipment trans Batch info");
                                            params.clear();
                                            sib = null;
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                            params.put("BATCH_ID", es.getExistingBatchId());
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                            logger.info("ERP Linking : case 2 shipment trans batch info params---" + params);
                                            sib.execute(params);
                                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                            break;
                                    }
                                    // Insert into Batch info for each record
//                            logger.info("ERP Linking : Insert into Batch info for each record---" + es);
//                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
//                            params.clear();
//                            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
//                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
//                            params.put("BATCH_NO", (es.isAutoGenerated() || i == 1 ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
//                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null || i == 1 ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
//                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
//                            params.put("AUTO_GENERATED", es.isAutoGenerated());
//                            logger.info("ERP Linking :  Batch info params---" + params);
//                            int batchId = sib.executeAndReturnKey(params).intValue();
//                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
//                            params.clear();
//                            sib = null;
//                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
//                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
//                            params.put("BATCH_ID", batchId);
//                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? manualTaggingOrderDTO.getQuantity() : es.getBatchQty()));
//                            logger.info("ERP Linking :  shipment trans Batch info params---" + params);
//                            sib.execute(params);
//                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                } catch (Exception e) {
                                    logger.info("ERP Linking : Error occured for batch---> " + es);
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            // Insert into Batch info for each record
                            logger.info("ERP Linking : No Batch information exists so creating one automatically");
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", manualTaggingOrderDTO.getProgramId());
                            params.put("PLANNING_UNIT_ID", manualTaggingOrderDTO.getPlanningUnitId());
                            params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                            params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                            params.put("CREATED_DATE", (erpOrderDTO.getMinActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getMinActualDeliveryDate()));
                            params.put("AUTO_GENERATED", true);
                            logger.info("ERP Linking :  Batch info params---" + params);
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("ERP Linking : Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", manualTaggingOrderDTO.getQuantity());
                            logger.info("ERP Linking :  shipment trans Batch info params---" + params);
                            sib.execute(params);
                            logger.info("ERP Linking : Pushed into shipmentBatchTrans with Qty " + manualTaggingOrderDTO.getQuantity());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("ERP Linking : Error occured while creating fresh shipment " + e);
                    }
                }
            }
        } else {
            parentShipmentId = -2;
        }
        return parentShipmentId;
    }

    @Override
    public List<ManualTaggingDTO> getShipmentListForDelinking(int programId, int planningUnitId) {
        String sql = "CALL getShipmentListForDeLinking(:programId, :planningUnitId, -1)";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("planningUnitId", planningUnitId);
        List<ManualTaggingDTO> list = this.namedParameterJdbcTemplate.query(sql, params, new ManualTaggingDTORowMapper());
        return list;
    }

    @Override
    public List<ManualTaggingDTO> getNotLinkedShipments(int programId, int linkingTypeId) {
        String sql = "";
        List<ManualTaggingDTO> list = null;
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        if (linkingTypeId == 3) {
            sql = "CALL getNotLinkedShipments(:programId, -1)";
            list = this.namedParameterJdbcTemplate.query(sql, params, new ManualTaggingDTORowMapper());
        }

        return list;
    }

    @Override
    @Transactional
    public void delinkShipment(ManualTaggingOrderDTO erpOrderDTO, CustomUserDetails curUser) {
        logger.info("ERP Linking : Going to delink shipments---" + erpOrderDTO);
        logger.info("ERP Linking : Curuser---" + curUser);
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sql;
        int maxTransId;
        int parentShipmentId = erpOrderDTO.getParentShipmentId();
        logger.info("ERP Linking : parentShipmentId---" + parentShipmentId);

        sql = "SELECT b.SHIPMENT_ID FROM (SELECT a.* FROM ( "
                + "SELECT st.`SHIPMENT_TRANS_ID`,s.`SHIPMENT_ID`,st.`ACTIVE` FROM rm_shipment s "
                + "LEFT JOIN rm_shipment_trans st ON st.`SHIPMENT_ID`=s.`SHIPMENT_ID` "
                + "WHERE s.`PARENT_SHIPMENT_ID`=? ORDER BY st.`SHIPMENT_TRANS_ID` DESC) AS a "
                + "GROUP BY a.SHIPMENT_ID) AS b WHERE b.active;";
        logger.info("delinking parenshipment id----------" + parentShipmentId);
        List<Integer> shipmentIdList = this.jdbcTemplate.queryForList(sql, Integer.class, parentShipmentId);
        logger.info("ERP Linking : shipment id list to delink---" + shipmentIdList);
//        if (shipmentIdList.size() == 1 && erpOrderDTO.getShipmentId() == shipmentIdList.get(0)) {
        if (shipmentIdList.size() == 1) {
            logger.info("ERP Linking : one shipment id found to delink---");
//            for (int shipmentId1 : shipmentIdList) {
            if (this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo()) != null) {
                logger.info("inside if--------------------" + erpOrderDTO);
                logger.info("ERP Linking : Going to check tab3 shipments table for parent--------------------" + parentShipmentId);
                sql = "SELECT COUNT(*) FROM rm_erp_tab3_shipments s WHERE s.`SHIPMENT_ID`=? AND s.`ACTIVE`;";
                int count = this.jdbcTemplate.queryForObject(sql, Integer.class, parentShipmentId);
                logger.info("ERP Linking : Going to check tab3 shipments count--------------------" + count);
//                    logger.info("ERP Linking : Going to check tab3 shipments count is 0 so activate parent--------------------");
                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`SHIPMENT_ID`=?", Integer.class, parentShipmentId);
                logger.info("ERP Linking : 1st max trans id---" + maxTransId);
                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=?,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";
                this.jdbcTemplate.update(sql, (count == 0 ? 1 : 0), curUser.getUserId(), curDate, maxTransId);
                if (count > 0) {
                    logger.info("ERP Linking : Going to check tab3 shipments count is greater than 0 so leave parent as is and deactivate in tab3 shipment table--------------------");
                    sql = "UPDATE rm_erp_tab3_shipments s SET s.`ACTIVE`=0,s.`LAST_MODIFIED_BY`=?,s.`LAST_MODIFIED_DATE`=? WHERE s.`SHIPMENT_ID`=? AND s.`ACTIVE`; ";
                    this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, parentShipmentId);
                    logger.info("ERP Linking : Updation of tab 3 shiment completed--------------------");
                }

                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
                logger.info("ERP Linking : 2nd max trans id---" + maxTransId);

                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=0,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=?,`NOTES`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";
                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, erpOrderDTO.getNotes(), maxTransId);
                logger.info("ERP Linking : Update completed---");
            } else {
//                System.out.println("delinking inside else----------" + parentShipmentId);
            }
        } else {
            logger.info("ERP Linking : Multiple child shipments found---");
            if (this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo()) != null) {
                maxTransId = this.jdbcTemplate.queryForObject("SELECT MAX(st.`SHIPMENT_TRANS_ID`) FROM rm_shipment_trans st WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`ACTIVE`;", Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
                logger.info("ERP Linking : max trans id---" + maxTransId);
                sql = "UPDATE rm_shipment_trans SET `ERP_FLAG`=0,`ACTIVE`=0,`PRIME_LINE_NO`=NULL,`LAST_MODIFIED_BY`=?,`LAST_MODIFIED_DATE`=?,`NOTES`=? "
                        + "WHERE `SHIPMENT_TRANS_ID`=?;";
                this.jdbcTemplate.update(sql, curUser.getUserId(), curDate, erpOrderDTO.getNotes(), maxTransId);
//                System.out.println("delinking inside else else----------" + maxTransId);
            }
        }
//        }
        logger.info("ERP Linking : Going to update manual tagging table---");
//        sql = "UPDATE rm_manual_tagging m SET m.`ACTIVE`=0,m.`NOTES`=?,m.`LAST_MODIFIED_DATE`=?,m.`LAST_MODIFIED_BY`=? WHERE m.`SHIPMENT_ID`=? AND m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=?;";
//        this.jdbcTemplate.update(sql, erpOrderDTO.getNotes(), curDate, curUser.getUserId(), erpOrderDTO.getParentShipmentId(), erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
        sql = "DELETE FROM rm_manual_tagging WHERE `SHIPMENT_ID`=? AND `ORDER_NO`=? AND `PRIME_LINE_NO`=?;";
        this.jdbcTemplate.update(sql, erpOrderDTO.getParentShipmentId(), erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
//        DELETE FROM rm_manual_tagging WHERE active = 0;""
//ALTER TABLE `fasp`.`rm_manual_tagging` ADD UNIQUE `uniqueOrder` (`ORDER_NO`, `PRIME_LINE_NO`, `SHIPMENT_ID`, `ACTIVE`); 
    }

    @Override
    public int createERPNotification(String orderNo, int primeLineNo, int shipmentId, int notificationTypeId) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        String sql;
        int notificationId = 0;
//        String sql = "select count(*) from rm_erp_notification n where n.`ORDER_NO`=? and n.`PRIME_LINE_NO`=? and n.`SHIPMENT_ID`=?;";
//        int count = this.jdbcTemplate.queryForObject(sql, Integer.class, orderNo, primeLineNo, shipmentId);
//        System.out.println("create notificatioon count---" + count);
        logger.info("ERP Notification : create notificatioon orderNo---" + orderNo);
        logger.info("ERP Notification : create notificatioon primeLineNo---" + primeLineNo);
        logger.info("ERP Notification : create notificatioon shipmentId---" + shipmentId);
//        if (count == 0) {
        try {
            sql = "SELECT m.`MANUAL_TAGGING_ID` FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`SHIPMENT_ID`=? AND m.`ACTIVE`;";
            int manualTaggingId = this.jdbcTemplate.queryForObject(sql, Integer.class, orderNo, primeLineNo, shipmentId);
            logger.info("ERP Notification : manualTaggingId---" + manualTaggingId);

            sql = "SELECT MAX(e.`ERP_ORDER_ID`) AS ERP_ORDER_ID FROM rm_erp_order e WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=?;";
            int erpOrderId = this.jdbcTemplate.queryForObject(sql, Integer.class, orderNo, primeLineNo);
            logger.info("ERP Notification : erpOrderId---" + erpOrderId);

            sql = "SELECT MAX(st.`SHIPMENT_ID`) AS SHIPMENT_ID FROM rm_shipment_trans st "
                    + "LEFT JOIN rm_shipment  s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
                    + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=?;";
            int childShipmentId = this.jdbcTemplate.queryForObject(sql, Integer.class, shipmentId, orderNo, primeLineNo);
            logger.info("ERP Notification : childShipmentId---" + childShipmentId);
            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_erp_notification").usingGeneratedKeyColumns("NOTIFICATION_ID");
            params.put("ORDER_NO", orderNo);
            params.put("PRIME_LINE_NO", primeLineNo);
            params.put("NOTIFICATION_TYPE_ID", notificationTypeId);
            params.put("SHIPMENT_ID", shipmentId);
            params.put("ADDRESSED", 0);
            params.put("ACTIVE", 1);
            params.put("CREATED_BY", 1);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", 1);
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ERP_ORDER_ID", erpOrderId);
            params.put("CHILD_SHIPMENT_ID", childShipmentId);
            params.put("MANUAL_TAGGING_ID", manualTaggingId);
            logger.info("ERP Notification : params---" + params);
            notificationId = si.executeAndReturnKey(params).intValue();
        } catch (Exception e) {
            logger.info("ERP Notification : Notification creation error---" + e);
            e.printStackTrace();
        }
        logger.info("ERP Notification : NotificationId Id " + notificationId + " created");
        return notificationId;
//        } else {
//            return -1;
//        }
    }

    @Override
    public List<ERPNotificationDTO> getNotificationList(int programId, int versionId) {
        String sql = "";
        List<ERPNotificationDTO> list = null;
        sql = "CALL getShipmentLinkingNotifications(?,?)";
        list = this.jdbcTemplate.query(sql, new ShipmentNotificationDTORowMapper(), programId, versionId);
//        System.out.println("list---" + list);
        return list;
    }

    @Override
    @Transactional
    public int updateNotification(ERPNotificationDTO eRPNotificationDTO, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sql = " UPDATE rm_erp_notification n "
                + " SET n.`ADDRESSED`=:addressed,n.`LAST_MODIFIED_BY`=:lastModifiedBy,n.`LAST_MODIFIED_DATE`=:lastModifiedDate "
                + " WHERE n.`NOTIFICATION_ID`=:notificationId;";
        Map<String, Object> params = new HashMap<>();
        params.put("addressed", eRPNotificationDTO.isAddressed());
        params.put("lastModifiedDate", curDate);
        params.put("lastModifiedBy", curUser.getUserId());
        params.put("notificationId", eRPNotificationDTO.getNotificationId());
        return this.namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public int getNotificationCount(CustomUserDetails curUser) {
        String programIds = "", sql;
        List<Program> programList = this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, true);
        for (Program p : programList) {
            programIds = programIds + p.getProgramId() + ",";
        }
        programIds = programIds.substring(0, programIds.lastIndexOf(","));
//        System.out.println("ids%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + programIds);
        sql = "SELECT COUNT(*) FROM ( "
                + "SELECT n.`NOTIFICATION_ID` FROM rm_erp_notification n "
                + " LEFT JOIN rm_shipment_linking s ON s.SHIPMENT_LINKING_ID=n.SHIPMENT_LINKING_ID "
                + " WHERE n.`ACTIVE` AND n.`ADDRESSED`=0 AND s.`PROGRAM_ID` IN (" + programIds + ") GROUP BY n.`NOTIFICATION_ID` ) AS a;";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int checkPreviousARTMISPlanningUnitId(String orderNo, int primeLineNo) {
        String sql = "SELECT papu.`PLANNING_UNIT_ID` FROM ( "
                + "SELECT e.`ERP_ORDER_ID`,e.`PLANNING_UNIT_SKU_CODE` FROM rm_erp_order e "
                + "WHERE e.`ORDER_NO`=? AND e.`PRIME_LINE_NO`=? "
                + "ORDER BY e.`ERP_ORDER_ID` DESC "
                + "LIMIT 2) t "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=t.PLANNING_UNIT_SKU_CODE "
                + "ORDER BY t.ERP_ORDER_ID ASC LIMIT 1;";
        return this.jdbcTemplate.queryForObject(sql, Integer.class, orderNo, primeLineNo);
    }

    @Override
    public List<NotificationSummaryDTO> getNotificationSummary(CustomUserDetails curUser) {
        String programIds = "", sql;
        List<Program> programList = this.programService.getProgramList(GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser, true);
        for (Program p : programList) {
            programIds = programIds + p.getProgramId() + ",";
        }
        programIds = programIds.substring(0, programIds.lastIndexOf(","));
//        System.out.println("ids 1%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + programIds);
        sql = "SELECT s.`PROGRAM_ID`,p.`LABEL_ID`,p.`LABEL_EN`,p.`LABEL_FR`,p.`LABEL_SP`,p.`LABEL_PR`, "
                + " COUNT(DISTINCT(n.`NOTIFICATION_ID`)) as NOTIFICATION_COUNT "
                + " FROM rm_erp_notification n "
                + " LEFT JOIN rm_shipment_linking s ON s.SHIPMENT_LINKING_ID=n.SHIPMENT_LINKING_ID "
                + " LEFT JOIN vw_program p ON p.`PROGRAM_ID`=s.`PROGRAM_ID` "
                + " WHERE n.`ACTIVE` AND n.`ADDRESSED`=0 "
                + " AND s.`PROGRAM_ID` IN (" + programIds + ") "
                + " GROUP BY s.`PROGRAM_ID` ;";
        return this.jdbcTemplate.query(sql, new NotificationSummaryDTORowMapper());
    }

    public String getMaxERPOrderIdFromERPShipment(String orderNo, int primeLineNo) {
        String sql = "SELECT MAX(s.FILE_NAME) AS FILE_NAME FROM rm_erp_shipment s WHERE s.`ORDER_NO`=? AND s.`PRIME_LINE_NO`=?;";
        return this.jdbcTemplate.queryForObject(sql, String.class, orderNo, primeLineNo);
    }

    @Override
    @Transactional
    public int tab3ShipmentCreation(int shipmentId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        logger.info("ERP Linking : Tab3 new shipment creation---" + shipmentId);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_erp_tab3_shipments").usingGeneratedKeyColumns("TAB3_SHIPMENT_ID");
        params.put("SHIPMENT_ID", shipmentId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        logger.info("ERP Linking : Tab3 new shipment creation params---" + params);
        int id = si.executeAndReturnKey(params).intValue();
        logger.info("ERP Linking : Tab3 new shipment creation completed---" + id);
        return id;
    }

    // ################################## New functions ###########################################
    @Override
    public List<Shipment> getNotLinkedQatShipments(int programId, int versionId, String[] planningUnitIds, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("planningUnitIds", ArrayUtils.convertArrayToString(planningUnitIds));
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("procurementAgentId", 1); // HardCoded as PSM since we are only matching with ARTMS orders
        String sql = "CALL getNotLinkedQatShipments(:programId, :versionId, :procurementAgentId, :planningUnitIds)";
        return this.namedParameterJdbcTemplate.query(sql, params, new ShipmentListResultSetExtractor());
    }

    @Transactional
    @Override
    public List<String> autoCompleteOrder(ErpAutoCompleteDTO erpAutoCompleteDTO, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sqlString = "CREATE TEMPORARY TABLE `tmp_delinked_list` (`RO_NO` VARCHAR(45) NOT NULL, `RO_PRIME_LINE_NO` VARCHAR(45) NOT NULL, PRIMARY KEY (`RO_NO`, `RO_PRIME_LINE_NO`))";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "CREATE TEMPORARY TABLE `tmp_delinked_list_copy` (`RO_NO` VARCHAR(45) NOT NULL, `RO_PRIME_LINE_NO` VARCHAR(45) NOT NULL, PRIMARY KEY (`RO_NO`, `RO_PRIME_LINE_NO`))";
        this.namedParameterJdbcTemplate.update(sqlString, params);

        List<SqlParameterSource> paramList = new LinkedList<>();
        for (RoAndRoPrimeLineNo roAndRoPrimeLineNo : erpAutoCompleteDTO.getDelinkedList()) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("roNo", roAndRoPrimeLineNo.getRoNo());
            param.addValue("roPrimeLineNo", roAndRoPrimeLineNo.getRoPrimeLineNo());
            paramList.add(param);
        }
        if (paramList.size() > 0) {
            sqlString = "INSERT INTO tmp_delinked_list VALUES (:roNo, :roPrimeLineNo)";
            this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
            sqlString = "INSERT INTO tmp_delinked_list_copy VALUES (:roNo, :roPrimeLineNo)";
            this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
        }
        StringBuilder sb = new StringBuilder("Select DISTINCT(RO_NO) FROM  (SELECT DISTINCT(e.`RO_NO`) `RO_NO` "
                + "FROM rm_erp_order_consolidated e "
                + "LEFT JOIN rm_erp_shipment_consolidated s ON e.ORDER_NO=s.ORDER_NO AND e.PRIME_LINE_NO=s.PRIME_LINE_NO "
                + "LEFT JOIN vw_program p ON p.PROGRAM_ID=:programId "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1 "
                + "LEFT JOIN rm_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN rm_planning_unit spu ON spu.PLANNING_UNIT_ID=:qatPlanningUnitId "
                + "LEFT JOIN rm_forecasting_unit sfu ON spu.FORECASTING_UNIT_ID=sfu.FORECASTING_UNIT_ID "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + "LEFT JOIN rm_shipment_linking sl ON sl.RO_NO=e.RO_NO and sl.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO AND sl.ACTIVE "
                + "LEFT JOIN rm_shipment_linking_trans slt ON slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID AND slt.VERSION_ID=sl.MAX_VERSION_ID  AND slt.ACTIVE "
                + "LEFT JOIN tmp_delinked_list dl ON sl.RO_NO=dl.RO_NO AND sl.RO_PRIME_LINE_NO=dl.RO_PRIME_LINE_NO "
                + "WHERE  "
                + "    e.RECPIENT_COUNTRY=c.LABEL_EN "
                + "    AND e.ACTIVE "
                + "    AND ("
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) < CURDATE() - INTERVAL 6 MONTH AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,2,3,5,7,9,10,13,15) OR "
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) >= CURDATE() - INTERVAL 6 MONTH AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) "
                + "    AND (sfu.TRACER_CATEGORY_ID=fu.TRACER_CATEGORY_ID) "
        );
        if (erpAutoCompleteDTO.getRoPo() != null) {
            sb.append("    AND (e.RO_NO LIKE '%").append(erpAutoCompleteDTO.getRoPo()).append("%' OR e.ORDER_NO LIKE '%").append(erpAutoCompleteDTO.getRoPo()).append("%') ");
        }

        sb.append("    AND (slt.SHIPMENT_LINKING_TRANS_ID IS NULL OR dl.RO_NO IS NOT NULL) "
                + "    AND (:erpPlanningUnitId=0 OR papu.PLANNING_UNIT_ID=:erpPlanningUnitId) "
                + " UNION "
                + " (SELECT DISTINCT(e.`ORDER_NO`) `RO_NO` "
                + "FROM rm_erp_order_consolidated e "
                + "LEFT JOIN rm_erp_shipment_consolidated s ON e.ORDER_NO=s.ORDER_NO AND e.PRIME_LINE_NO=s.PRIME_LINE_NO "
                + "LEFT JOIN vw_program p ON p.PROGRAM_ID=:programId "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1 "
                + "LEFT JOIN rm_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "LEFT JOIN rm_planning_unit spu ON spu.PLANNING_UNIT_ID=:qatPlanningUnitId "
                + "LEFT JOIN rm_forecasting_unit sfu ON spu.FORECASTING_UNIT_ID=sfu.FORECASTING_UNIT_ID "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + "LEFT JOIN rm_shipment_linking sl ON sl.RO_NO=e.RO_NO and sl.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO AND sl.ACTIVE "
                + "LEFT JOIN rm_shipment_linking_trans slt ON slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID AND slt.VERSION_ID=sl.MAX_VERSION_ID  AND slt.ACTIVE "
                + "LEFT JOIN tmp_delinked_list_copy dl ON sl.RO_NO=dl.RO_NO AND sl.RO_PRIME_LINE_NO=dl.RO_PRIME_LINE_NO "
                + "WHERE  "
                + "    e.RECPIENT_COUNTRY=c.LABEL_EN "
                + "    AND e.ACTIVE "
                + "    AND ("
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) < CURDATE() - INTERVAL 6 MONTH AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,2,3,5,7,9,10,13,15) OR "
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) >= CURDATE() - INTERVAL 6 MONTH AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) "
                + "    AND (sfu.TRACER_CATEGORY_ID=fu.TRACER_CATEGORY_ID) "
        );
        if (erpAutoCompleteDTO.getRoPo() != null) {
            sb.append("    AND (e.RO_NO LIKE '%").append(erpAutoCompleteDTO.getRoPo()).append("%' OR e.ORDER_NO LIKE '%").append(erpAutoCompleteDTO.getRoPo()).append("%') ");
        }
        sb.append("    AND (slt.SHIPMENT_LINKING_TRANS_ID IS NULL OR dl.RO_NO IS NOT NULL) "
                + "    AND (:erpPlanningUnitId=0 OR papu.PLANNING_UNIT_ID=:erpPlanningUnitId))"
                + " ) a");
        params.put("programId", erpAutoCompleteDTO.getProgramId());
        params.put("erpPlanningUnitId", erpAutoCompleteDTO.getErpPlanningUnitId());
        params.put("qatPlanningUnitId", erpAutoCompleteDTO.getQatPlanningUnitId());
        List<String> outputList = this.namedParameterJdbcTemplate.queryForList(sb.toString(), params, String.class);
        sqlString = "DROP TABLE IF EXISTS `tmp_delinked_list`";
        params.clear();
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DROP TABLE IF EXISTS `tmp_delinked_list_copy`";
        params.clear();
        this.namedParameterJdbcTemplate.update(sqlString, params);
        return outputList;
    }

    @Transactional
    @Override
    public List<SimpleCodeObject> autoCompletePu(AutoCompletePuDTO autoCompletePuDTO, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sqlString = "CREATE TEMPORARY TABLE `tmp_delinked_list` (`RO_NO` VARCHAR(45) NOT NULL, `RO_PRIME_LINE_NO` VARCHAR(45) NOT NULL, PRIMARY KEY (`RO_NO`, `RO_PRIME_LINE_NO`))";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "INSERT INTO tmp_delinked_list VALUES (:roNo, :roPrimeLineNo)";
        List<SqlParameterSource> paramList = new LinkedList<>();
        for (RoAndRoPrimeLineNo roAndRoPrimeLineNo : autoCompletePuDTO.getDelinkedList()) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("roNo", roAndRoPrimeLineNo.getRoNo());
            param.addValue("roPrimeLineNo", roAndRoPrimeLineNo.getRoPrimeLineNo());
            paramList.add(param);
        }
        if (paramList.size() > 0) {
            this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
        }
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT pu.PLANNING_UNIT_ID `ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_PR, pu.LABEL_SP, papu.SKU_CODE `CODE`"
                + "                FROM rm_erp_order_consolidated e "
                + "                LEFT JOIN rm_erp_shipment_consolidated s ON e.ORDER_NO=s.ORDER_NO AND e.PRIME_LINE_NO=s.PRIME_LINE_NO "
                + "                LEFT JOIN vw_program p ON p.PROGRAM_ID=:programId"
                + "                LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "                LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "                LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1 "
                + "                LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "                LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "                LEFT JOIN rm_planning_unit spu ON spu.PLANNING_UNIT_ID=:planningUnitId "
                + "                LEFT JOIN rm_forecasting_unit sfu ON spu.FORECASTING_UNIT_ID=sfu.FORECASTING_UNIT_ID "
                + "                LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + "                LEFT JOIN rm_shipment_linking sl ON sl.RO_NO=e.RO_NO and sl.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO AND sl.ACTIVE"
                + "                LEFT JOIN rm_shipment_linking_trans slt ON slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID AND slt.VERSION_ID=sl.MAX_VERSION_ID  AND slt.ACTIVE "
                + "                LEFT JOIN tmp_delinked_list dl ON sl.RO_NO=dl.RO_NO AND sl.RO_PRIME_LINE_NO=dl.RO_PRIME_LINE_NO "
                + "                WHERE  "
                + "                    e.RECPIENT_COUNTRY=c.LABEL_EN "
                + "                    AND e.ACTIVE "
                + "                    AND ("
                + "                    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) < CURDATE() - INTERVAL 6 MONTH AND "
                + "                    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,2,3,5,7,9,10,13,15) OR "
                + "                    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) >= CURDATE() - INTERVAL 6 MONTH AND "
                + "                    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) "
                + "                    AND (sfu.TRACER_CATEGORY_ID=fu.TRACER_CATEGORY_ID) "
                + "     AND (pu.`LABEL_EN` LIKE '%").append(autoCompletePuDTO.getPuName()).append("%' OR papu.`SKU_CODE` LIKE '%").append(autoCompletePuDTO.getPuName()).append("%') AND (slt.SHIPMENT_LINKING_TRANS_ID IS NULL OR dl.RO_NO IS NOT NULL) group by pu.PLANNING_UNIT_ID");
        params.put("planningUnitId", autoCompletePuDTO.getPlanningUnitId());
        params.put("programId", autoCompletePuDTO.getProgramId());
        List<SimpleCodeObject> outputList = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_delinked_list`";
        params.clear();
        this.namedParameterJdbcTemplate.update(sqlString, params);
        return outputList;
    }

    @Override
    @Transactional
    public List<ShipmentLinkingOutput> getNotLinkedErpShipmentsTab1AndTab3(NotLinkedErpShipmentsInput input, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        String sqlString = "CREATE TEMPORARY TABLE `tmp_delinked_list` (`RO_NO` VARCHAR(45) NOT NULL, `RO_PRIME_LINE_NO` VARCHAR(45) NOT NULL, PRIMARY KEY (`RO_NO`, `RO_PRIME_LINE_NO`))";
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "INSERT INTO tmp_delinked_list VALUES (:roNo, :roPrimeLineNo)";
        List<SqlParameterSource> paramList = new LinkedList<>();
        for (RoAndRoPrimeLineNo roAndRoPrimeLineNo : input.getDelinkedList()) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("roNo", roAndRoPrimeLineNo.getRoNo());
            param.addValue("roPrimeLineNo", roAndRoPrimeLineNo.getRoPrimeLineNo());
            paramList.add(param);
        }
        if (paramList.size() > 0) {
            this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramList.toArray(new MapSqlParameterSource[paramList.size()]));
        }
        String setDateQuery = "SET @dt = CURDATE() - INTERVAL 6 MONTH;";
        String setCountryIdQuery = "SET @realmCountryId = ?;";
        this.jdbcTemplate.update(setDateQuery);
        this.jdbcTemplate.update(setCountryIdQuery, input.getRealmCountryId());
        String getCountryNameQuery = "SELECT c.LABEL_EN INTO @countryName FROM rm_realm_country rc LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID WHERE rc.REALM_COUNTRY_ID=@realmCountryId";
        this.jdbcTemplate.execute(getCountryNameQuery);
        StringBuilder sqlStringBuilder = new StringBuilder(""
                + "SELECT   "
                + "    e.`RO_NO`, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO, e.`ACTIVE` `ORDER_ACTIVE`,   "
                + "    COALESCE(s.STATUS, e.STATUS) `ERP_SHIPMENT_STATUS`,   "
                + "    COALESCE(s.DELIVERED_QTY, s.SHIPPED_QTY, e.QTY) `ERP_QTY`,   "
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE,   "
                + "    s.KN_SHIPMENT_NO, s.BATCH_NO, s.EXPIRY_DATE, s.`ACTIVE` `SHIPMENT_ACTIVE`,   "
                + "    pu.PLANNING_UNIT_ID `ERP_PLANNING_UNIT_ID`, pu.LABEL_ID `ERP_PU_LABEL_ID`, pu.LABEL_EN `ERP_PU_LABEL_EN`, pu.LABEL_FR `ERP_PU_LABEL_FR`, pu.LABEL_SP `ERP_PU_LABEL_SP`, pu.LABEL_PR `ERP_PU_LABEL_PR`,   "
                + "    null `QAT_PLANNING_UNIT_ID`,   "
                + "    e.PRICE, e.SHIPPING_COST,  "
                + "    ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SS_LABEL_ID`, ss.LABEL_EN `SS_LABEL_EN`, ss.LABEL_FR `SS_LABEL_FR`, ss.LABEL_SP `SS_LABEL_SP`, ss.LABEL_PR  `SS_LABEL_PR`,   "
                + "    sl.PARENT_SHIPMENT_ID, sht.PARENT_LINKED_SHIPMENT_ID, sl.CHILD_SHIPMENT_ID, null NOTES, e.SHIP_BY, null CONVERSION_FACTOR,fu.TRACER_CATEGORY_ID   "
                + "FROM rm_erp_order_consolidated e  "
                + "LEFT JOIN (SELECT o.RO_NO FROM rm_erp_order_consolidated o WHERE o.RO_NO = :roNo OR o.ORDER_NO = :roNo OR :roNo='' GROUP BY o.RO_NO) o1 ON e.RO_NO=o1.RO_NO  "
                + "LEFT JOIN rm_erp_shipment_consolidated s ON e.ORDER_NO=s.ORDER_NO AND e.PRIME_LINE_NO=s.PRIME_LINE_NO AND s.ACTIVE   "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=COALESCE(s.STATUS, e.STATUS)   "
                + "LEFT JOIN vw_shipment_status ss ON sm.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID   "
                + "LEFT JOIN rm_shipment_linking sl ON sl.RO_NO=e.RO_NO and sl.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO  AND sl.ACTIVE "
                + "LEFT JOIN rm_shipment_linking_trans slt ON slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID AND slt.VERSION_ID=sl.MAX_VERSION_ID AND slt.ACTIVE   "
                + "LEFT JOIN tmp_delinked_list dl ON sl.RO_NO=dl.RO_NO AND sl.RO_PRIME_LINE_NO=dl.RO_PRIME_LINE_NO "
                + "LEFT JOIN rm_shipment sh ON sl.CHILD_SHIPMENT_ID=sh.SHIPMENT_ID "
                + "LEFT JOIN rm_shipment_trans sht on sh.SHIPMENT_ID=sht.SHIPMENT_ID AND sh.MAX_VERSION_ID=sht.VERSION_ID "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu on left(papu.SKU_CODE,12)=e.PLANNING_UNIT_SKU_CODE   "
                + "LEFT JOIN vw_planning_unit pu ON pu.PLANNING_UNIT_ID=papu.PLANNING_UNIT_ID   "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID   "
                + "LEFT JOIN vw_planning_unit spu ON spu.PLANNING_UNIT_ID=:shipmentPlanningUnitId   "
                + "LEFT JOIN rm_forecasting_unit sfu ON spu.FORECASTING_UNIT_ID=sfu.FORECASTING_UNIT_ID   "
                + "WHERE   "
                + "	o1.RO_NO IS NOT NULL  "
                + "    AND (papu.PLANNING_UNIT_ID = :filterPlanningUnitId OR :filterPlanningUnitId = 0) AND e.RECPIENT_COUNTRY=@countryName  "
                + "    AND e.ACTIVE   "
                + "    AND ("
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) < @dt AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,2,3,5,7,9,10,13,15) OR "
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) >= @dt AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15))  "
                + "    AND (slt.SHIPMENT_LINKING_TRANS_ID IS NULL OR dl.RO_NO IS NOT NULL)  "
                + "    AND sfu.TRACER_CATEGORY_ID=fu.TRACER_CATEGORY_ID   "
                + "ORDER BY e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO");
        params.put("versionId", input.getVersionId());
        params.put("shipmentProgramId", input.getProgramId());
        params.put("realmCountryId", input.getRealmCountryId());
        params.put("shipmentPlanningUnitId", input.getShipmentPlanningUnitId());
        params.put("roNo", input.getRoNo());
        params.put("filterPlanningUnitId", input.getFilterPlanningUnitId());
        List<ShipmentLinkingOutput> shipmentList = this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ShipmentLinkingOutputRowMapper());
        sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_delinked_list`";
        params.clear();
        this.namedParameterJdbcTemplate.update(sqlString, params);
        return shipmentList;
    }

    @Override
    public List<ShipmentLinkingOutput> getNotLinkedErpShipmentsTab3(NotLinkedErpShipmentsInputTab3 input, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", input.getRealmCountryId());
        params.put("planningUnitIds", ArrayUtils.convertArrayToString(input.getPlanningUnitIds()));
        StringBuilder sqlStringBuilder = new StringBuilder(""
                + "SELECT  "
                + "    e.RECPIENT_COUNTRY, e.`RO_NO`, e.RO_PRIME_LINE_NO, COALESCE(s.STATUS, e.STATUS) `ERP_SHIPMENT_STATUS`, e.`ACTIVE` `ORDER_ACTIVE`,  "
                + "    e.ORDER_NO, e.PRIME_LINE_NO, COALESCE(s.DELIVERED_QTY, s.SHIPPED_QTY, e.QTY) `ERP_QTY`,  "
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE,  "
                + "    s.KN_SHIPMENT_NO, s.BATCH_NO, s.EXPIRY_DATE, s.`ACTIVE` `SHIPMENT_ACTIVE`,  "
                + "    pu.PLANNING_UNIT_ID `ERP_PLANNING_UNIT_ID`, pu.LABEL_ID `ERP_PU_LABEL_ID`, pu.LABEL_EN `ERP_PU_LABEL_EN`, pu.LABEL_FR `ERP_PU_LABEL_FR`, pu.LABEL_SP `ERP_PU_LABEL_SP`, pu.LABEL_PR `ERP_PU_LABEL_PR`,  "
                + "    null `QAT_PLANNING_UNIT_ID`,  "
                + "    e.PRICE, e.SHIPPING_COST,  "
                + "    ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SS_LABEL_ID`, ss.LABEL_EN `SS_LABEL_EN`, ss.LABEL_FR `SS_LABEL_FR`, ss.LABEL_SP `SS_LABEL_SP`, ss.LABEL_PR  `SS_LABEL_PR`,  "
                + "    sl.PARENT_SHIPMENT_ID, sht.PARENT_LINKED_SHIPMENT_ID, sl.CHILD_SHIPMENT_ID, null NOTES, e.SHIP_BY, null CONVERSION_FACTOR ,fu.TRACER_CATEGORY_ID  "
                + "FROM rm_realm_country rc  "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  "
                + "LEFT JOIN rm_erp_order_consolidated e ON c.LABEL_EN=e.RECPIENT_COUNTRY  "
                + "LEFT JOIN rm_erp_shipment_consolidated s ON e.ORDER_NO=s.ORDER_NO AND e.PRIME_LINE_NO=s.PRIME_LINE_NO AND s.ACTIVE  "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON (FIND_IN_SET(papu.PLANNING_UNIT_ID,:planningUnitIds) OR :planningUnitIds='') AND LEFT(papu.SKU_CODE,12)=e.PLANNING_UNIT_SKU_CODE  "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=COALESCE(s.STATUS, e.STATUS)  "
                + "LEFT JOIN rm_shipment_linking sl ON sl.RO_NO=e.RO_NO and sl.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO  AND sl.ACTIVE "
                + "LEFT JOIN rm_shipment_linking_trans slt ON slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID AND slt.VERSION_ID=sl.MAX_VERSION_ID AND slt.ACTIVE  "
                + "LEFT JOIN rm_shipment sh ON sl.CHILD_SHIPMENT_ID=sh.SHIPMENT_ID "
                + "LEFT JOIN rm_shipment_trans sht ON sh.SHIPMENT_ID=sht.SHIPMENT_ID AND sh.MAX_VERSION_ID=sht.VERSION_ID "
                + "LEFT JOIN vw_shipment_status ss ON sm.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  "
                + "LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  "
                + "LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID  "
                + "WHERE  "
                + "    rc.REALM_COUNTRY_ID=:realmCountryId  "
                + "    AND e.ACTIVE  "
                + "    AND ("
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) < CURDATE() - INTERVAL 6 MONTH AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,2,3,5,7,9,10,13,15) OR "
                + "    COALESCE(s.ACTUAL_DELIVERY_DATE, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) >= CURDATE() - INTERVAL 6 MONTH AND "
                + "    sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15)) "
                + "    AND slt.SHIPMENT_LINKING_TRANS_ID IS NULL  "
                + "    AND pu.PLANNING_UNIT_ID IS NOT NULL  ");
        int count = 1;
        for (String pcSortOrder : input.getProductCategorySortOrders()) {
            if (count == 1) {
                sqlStringBuilder.append("AND (");
                sqlStringBuilder.append("pc.SORT_ORDER LIKE CONCAT(:pcSortOrder").append(count).append(", '%')");
            }
            if (count > 1) {
                sqlStringBuilder.append(" OR pc.SORT_ORDER LIKE CONCAT(:pcSortOrder").append(count).append(", '%')");
            }
            params.put("pcSortOrder" + count, pcSortOrder);
            count++;
        }
        if (count > 1) {
            sqlStringBuilder.append(") ");
        }
        sqlStringBuilder.append("ORDER BY e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ShipmentLinkingOutputRowMapper());
    }

    @Override
    public List<ShipmentLinkingOutput> getLinkedQatShipments(int programId, int versionId, String[] planningUnitIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT "
                + "                    e.`RO_NO`, e.RO_PRIME_LINE_NO, coalesce(s.STATUS,e.STATUS) `ERP_SHIPMENT_STATUS`, e.`ACTIVE` `ORDER_ACTIVE`, "
                + "                    e.ORDER_NO, e.PRIME_LINE_NO, coalesce(SUM(s.DELIVERED_QTY),SUM(s.SHIPPED_QTY),e.QTY) `ERP_QTY`, "
                + "                    COALESCE( s.ACTUAL_DELIVERY_DATE,e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE, "
                + "                    sl.KN_SHIPMENT_NO, null as BATCH_NO, NULL as EXPIRY_DATE, TRUE `SHIPMENT_ACTIVE`, "
                + "                    pu.PLANNING_UNIT_ID `ERP_PLANNING_UNIT_ID`, pu.LABEL_ID `ERP_PU_LABEL_ID`, pu.LABEL_EN `ERP_PU_LABEL_EN`, pu.LABEL_FR `ERP_PU_LABEL_FR`, pu.LABEL_SP `ERP_PU_LABEL_SP`, pu.LABEL_PR `ERP_PU_LABEL_PR`, "
                + "                    pu2.PLANNING_UNIT_ID `QAT_PLANNING_UNIT_ID`, pu2.LABEL_ID `QAT_PU_LABEL_ID`, pu2.LABEL_EN `QAT_PU_LABEL_EN`, pu2.LABEL_FR `QAT_PU_LABEL_FR`, pu2.LABEL_SP `QAT_PU_LABEL_SP`, pu2.LABEL_PR `QAT_PU_LABEL_PR`, "
                + "                    rcpu.REALM_COUNTRY_PLANNING_UNIT_ID `QAT_RCPU_ID`, rcpu.LABEL_ID `QAT_RCPU_LABEL_ID`, rcpu.LABEL_EN `QAT_RCPU_LABEL_EN`, rcpu.LABEL_FR `QAT_RCPU_LABEL_FR`, rcpu.LABEL_SP `QAT_RCPU_LABEL_SP`, rcpu.LABEL_PR `QAT_RCPU_LABEL_PR`, IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)) `QAT_RCPU_MULTIPLIER`, "
                + "                    e.PRICE, e.SHIPPING_COST, "
                + "                    ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SS_LABEL_ID`, ss.LABEL_EN `SS_LABEL_EN`, ss.LABEL_FR `SS_LABEL_FR`, ss.LABEL_SP `SS_LABEL_SP`, ss.LABEL_PR  `SS_LABEL_PR`, "
                + "                    sl.PARENT_SHIPMENT_ID, group_concat(DISTINCT(s2t.SHIPMENT_ID)) as PARENT_LINKED_SHIPMENT_ID, sl.CHILD_SHIPMENT_ID, s2t.NOTES, e.SHIP_BY, slt.`CONVERSION_FACTOR` CONVERSION_FACTOR,null `TRACER_CATEGORY_ID`                      "
                + "                FROM  rm_shipment_linking sl"
                + "                LEFT JOIN rm_erp_order_consolidated e ON sl.RO_NO=e.RO_NO AND sl.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO and sl.ORDER_NO=e.ORDER_NO and sl.PRIME_LINE_NO=e.PRIME_LINE_NO AND e.ACTIVE"
                + "                LEFT JOIN rm_erp_shipment_consolidated s ON s.ORDER_NO=sl.ORDER_NO AND s.PRIME_LINE_NO=sl.PRIME_LINE_NO AND s.KN_SHIPMENT_NO=sl.KN_SHIPMENT_NO AND s.ACTIVE "
                + "                LEFT JOIN rm_shipment_linking_trans slt ON slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID AND slt.VERSION_ID=sl.MAX_VERSION_ID AND slt.ACTIVE "
                + "                LEFT JOIN rm_procurement_agent_planning_unit papu on papu.PROCUREMENT_AGENT_ID=1 AND LEFT(papu.SKU_CODE,12)=e.PLANNING_UNIT_SKU_CODE "
                + "                LEFT JOIN rm_program p ON p.PROGRAM_ID=sl.PROGRAM_ID and p.PROGRAM_ID=:programId "
                + "                LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "                LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=e.`STATUS` "
                + "                LEFT JOIN vw_shipment_status ss ON sm.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID                 "
                + "                LEFT JOIN rm_shipment s2 ON sl.PARENT_SHIPMENT_ID=s2.SHIPMENT_ID  "
                + "                LEFT JOIN rm_shipment_trans s2t ON s2t.PARENT_LINKED_SHIPMENT_ID=s2.SHIPMENT_ID AND s2.MAX_VERSION_ID=s2t.VERSION_ID "
                + "                LEFT JOIN rm_shipment cs2 ON sl.CHILD_SHIPMENT_ID=cs2.SHIPMENT_ID  "
                + "                LEFT JOIN rm_shipment_trans cs2t ON cs2t.SHIPMENT_ID=cs2.SHIPMENT_ID AND cs2.MAX_VERSION_ID=cs2t.VERSION_ID "
                + "                LEFT JOIN vw_planning_unit pu2 ON cs2t.PLANNING_UNIT_ID=pu2.PLANNING_UNIT_ID "
                + "                LEFT JOIN vw_realm_country_planning_unit rcpu ON cs2t.REALM_COUNTRY_PLANNING_UNIT_ID=rcpu.REALM_COUNTRY_PLANNING_UNIT_ID "
                + "                WHERE "
                + "                     p.PROGRAM_ID IS NOT NULL "
                + "    AND (FIND_IN_SET(pu2.PLANNING_UNIT_ID, :planningUnitIds) OR :planningUnitIds='') "
                + "    AND slt.SHIPMENT_LINKING_TRANS_ID IS NOT NULL group by sl.CHILD_SHIPMENT_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        params.put("versionId", versionId);
        params.put("planningUnitIds", ArrayUtils.convertArrayToString(planningUnitIds));
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ShipmentLinkingOutputRowMapper());
    }

    @Override
    public List<ShipmentLinkingOutput> getShipmentListForSync(ShipmentSyncInput shipmentSyncInput, CustomUserDetails curUser) {
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment_sync`";
        this.jdbcTemplate.update(sqlString);
        sqlString = "CREATE TEMPORARY TABLE `tmp_shipment_sync` ("
                + "  `RO_NO` VARCHAR(20) NOT NULL, "
                + "  `RO_PRIME_LINE_NO` VARCHAR(45) NOT NULL, "
                + "  PRIMARY KEY (`RO_NO`, `RO_PRIME_LINE_NO`))";
        this.jdbcTemplate.update(sqlString);
        List<SqlParameterSource> paramList = new LinkedList<>();
        shipmentSyncInput.getRoAndRoPrimeLineNoList().stream().collect(Collectors.toList()).forEach(ut -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("roNo", ut.getRoNo());
            param.addValue("roPrimeLineNo", ut.getRoPrimeLineNo());
            paramList.add(param);
        }
        );
        sqlString = "INSERT IGNORE INTO tmp_shipment_sync VALUES (:roNo, :roPrimeLineNo)";
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramList.toArray(new MapSqlParameterSource[paramList.size()]));

        StringBuilder sqlStringBuilder = new StringBuilder("SELECT  "
                + "    e.`RO_NO`, e.`RO_PRIME_LINE_NO`, COALESCE(s.`STATUS`, e.`STATUS`) `ERP_SHIPMENT_STATUS`,  "
                + "    e.`ORDER_NO`, e.`PRIME_LINE_NO`, COALESCE(s.`DELIVERED_QTY`, s.`SHIPPED_QTY`, e.`QTY`) `ERP_QTY`,  "
                + "    COALESCE(s.`ACTUAL_DELIVERY_DATE`, e.`CURRENT_ESTIMATED_DELIVERY_DATE`,e.`AGREED_DELIVERY_DATE`,e.`REQ_DELIVERY_DATE`) AS `EXPECTED_DELIVERY_DATE`, e.`ACTIVE` `ORDER_ACTIVE`, "
                + "    s.`KN_SHIPMENT_NO`, s.`BATCH_NO`, s.`EXPIRY_DATE`, COALESCE(s.`ACTIVE`,e.`ACTIVE`) `SHIPMENT_ACTIVE`, "
                + "    pu.`PLANNING_UNIT_ID` `ERP_PLANNING_UNIT_ID`, pu.`LABEL_ID` `ERP_PU_LABEL_ID`, pu.`LABEL_EN` `ERP_PU_LABEL_EN`, pu.`LABEL_FR` `ERP_PU_LABEL_FR`, pu.`LABEL_SP` `ERP_PU_LABEL_SP`, pu.`LABEL_PR` `ERP_PU_LABEL_PR`,  "
                + "    e.`PRICE`, e.`SHIPPING_COST`, null `PARENT_SHIPMENT_ID`, null `PARENT_LINKED_SHIPMENT_ID`, null `CHILD_SHIPMENT_ID`, null `NOTES`, e.`SHIP_BY`,  "
                + "    ss.`SHIPMENT_STATUS_ID`, ss.`LABEL_ID` `SS_LABEL_ID`, ss.`LABEL_EN` `SS_LABEL_EN`, ss.`LABEL_FR` `SS_LABEL_FR`, ss.`LABEL_SP` `SS_LABEL_SP`, ss.`LABEL_PR`  `SS_LABEL_PR`, null CONVERSION_FACTOR ,null AS TRACER_CATEGORY_ID  "
                + "FROM ("
                + "	SELECT e.RO_NO, e.RO_PRIME_LINE_NO "
                + "     FROM tmp_shipment_sync ts "
                + "	LEFT JOIN rm_erp_order_consolidated e ON ts.`RO_NO`=e.`RO_NO` AND ts.`RO_PRIME_LINE_NO`=e.`RO_PRIME_LINE_NO` "
                + "	LEFT JOIN rm_erp_shipment_consolidated s ON e.`ORDER_NO`=s.`ORDER_NO` AND e.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` "
                + "	WHERE (e.`LAST_MODIFIED_DATE` > :lastSyncDate OR s.`LAST_MODIFIED_DATE`>:lastSyncDate)  group by e.RO_NO, e.RO_PRIME_LINE_NO "
                + ") t "
                + "LEFT JOIN rm_erp_order_consolidated e ON t.`RO_NO`=e.`RO_NO` AND t.`RO_PRIME_LINE_NO`=e.`RO_PRIME_LINE_NO`   "
                + "LEFT JOIN rm_erp_shipment_consolidated s ON e.`ORDER_NO`=s.`ORDER_NO` AND e.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu on papu.`PROCUREMENT_AGENT_ID`=1 AND LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE`  "
                + "LEFT JOIN vw_planning_unit pu ON pu.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID`  "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=COALESCE(s.`STATUS`, e.`STATUS`)  "
                + "LEFT JOIN vw_shipment_status ss ON sm.`SHIPMENT_STATUS_ID`=ss.`SHIPMENT_STATUS_ID`  "
                + "WHERE  "
                + "    pu.`PLANNING_UNIT_ID` IS NOT NULL "
        //                + "AND (e.`LAST_MODIFIED_DATE` > :lastSyncDate OR s.`LAST_MODIFIED_DATE`>:lastSyncDate)"
        );
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", shipmentSyncInput.getLastSyncDate());
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ShipmentLinkingOutputRowMapper());
    }

    @Override
    public List<ShipmentLinkedToOtherProgramOutput> getShipmentLinkedToOtherProgram(ShipmentLinkedToOtherProgramInput shipmentInput, CustomUserDetails curUser) {
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_shipment_linked_to_other_programs`";
        this.jdbcTemplate.update(sqlString);
        sqlString = "CREATE TEMPORARY TABLE `tmp_shipment_linked_to_other_programs` ("
                + "  `PROGRAM_ID` INT(10) NOT NULL, "
                + "  `RO_NO` VARCHAR(20) NOT NULL, "
                + "  `RO_PRIME_LINE_NO` VARCHAR(45) NOT NULL, "
                + "  PRIMARY KEY (`PROGRAM_ID`, `RO_NO`, `RO_PRIME_LINE_NO`))";
        this.jdbcTemplate.update(sqlString);
        List<SqlParameterSource> paramList = new LinkedList<>();
        shipmentInput.getRoAndRoPrimeLineNoList().stream().collect(Collectors.toList()).forEach(ut -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("programId", shipmentInput.getProgramId());
            param.addValue("roNo", ut.getRoNo());
            param.addValue("roPrimeLineNo", ut.getRoPrimeLineNo());
            paramList.add(param);
        }
        );
        sqlString = "INSERT IGNORE INTO tmp_shipment_linked_to_other_programs VALUES (:programId, :roNo, :roPrimeLineNo)";
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramList.toArray(new MapSqlParameterSource[paramList.size()]));

        sqlString = "SELECT "
                + "    p.PROGRAM_ID, p.LABEL_ID, p.LABEL_EN, p.LABEL_FR, p.LABEL_SP, p.LABEL_PR, p.PROGRAM_CODE, "
                + "    sl.RO_NO, sl.RO_PRIME_LINE_NO, sl.PARENT_SHIPMENT_ID,  group_concat(DISTINCT(st.SHIPMENT_ID)) as PARENT_LINKED_SHIPMENT_ID, slt.CONVERSION_FACTOR, "
                + "    pm.USER_ID, pm.USERNAME ,st2.PLANNING_UNIT_ID,st2.REALM_COUNTRY_PLANNING_UNIT_ID,pu.LABEL_ID `PU_LABEL_ID`,pu.LABEL_EN `PU_LABEL_EN`,pu.LABEL_FR `PU_LABEL_FR`,pu.LABEL_SP `PU_LABEL_SP`,pu.LABEL_PR `PU_LABEL_PR`,rcpu.LABEL_ID `RCPU_LABEL_ID`,rcpu.LABEL_EN `RCPU_LABEL_EN`,rcpu.LABEL_FR `RCPU_LABEL_FR`,rcpu.LABEL_SP `RCPU_LABEL_SP`,rcpu.LABEL_PR `RCPU_LABEL_PR`,IF(rcpu.CONVERSION_METHOD IS NULL OR rcpu.CONVERSION_METHOD=1, rcpu.CONVERSION_NUMBER, IF(rcpu.CONVERSION_METHOD=2,1/rcpu.CONVERSION_NUMBER,0)) `RCPU_MULTIPLIER`,"
                + "    lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, sl.LAST_MODIFIED_DATE, st.SHIPMENT_QTY "
                + "FROM tmp_shipment_linked_to_other_programs ts "
                + "LEFT JOIN rm_shipment_linking sl ON ts.PROGRAM_ID!=sl.PROGRAM_ID AND ts.RO_NO=sl.RO_NO AND ts.RO_PRIME_LINE_NO=sl.RO_PRIME_LINE_NO "
                + "LEFT JOIN rm_shipment_linking_trans slt ON sl.SHIPMENT_LINKING_ID=slt.SHIPMENT_LINKING_ID AND sl.MAX_VERSION_ID=slt.VERSION_ID "
                + "LEFT JOIN rm_shipment s ON sl.PARENT_SHIPMENT_ID=s.SHIPMENT_ID "
                + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.PARENT_LINKED_SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID  "
                + "LEFT JOIN rm_shipment s2 ON sl.CHILD_SHIPMENT_ID=s2.SHIPMENT_ID "
                + "LEFT JOIN rm_shipment_trans st2 ON s2.SHIPMENT_ID=st2.SHIPMENT_ID AND s2.MAX_VERSION_ID=st2.VERSION_ID  "
                + "LEFT JOIN vw_planning_unit pu on pu.PLANNING_UNIT_ID=st2.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_realm_country_planning_unit rcpu on rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=st2.REALM_COUNTRY_PLANNING_UNIT_ID "
                + "LEFT JOIN vw_program p ON sl.PROGRAM_ID=p.PROGRAM_ID "
                + "LEFT JOIN us_user pm ON p.PROGRAM_MANAGER_USER_ID=pm.USER_ID "
                + "LEFT JOIN us_user lmb ON sl.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE slt.ACTIVE";
        return this.namedParameterJdbcTemplate.query(sqlString, new ShipmentLinkedToOtherProgramOutputRowMapper());
    }

    @Override
    public ArtmisHistory getArtmisHistory(String roNo, int roPrimeLineNo) {
        ArtmisHistory history = new ArtmisHistory();
        Map<String, Object> params = new HashMap<>();
        params.put("roNo", roNo);
        params.put("roPrimeLineNo", roPrimeLineNo);
        String sql = "SELECT  "
                + "    CONCAT(o.RO_NO, ' - ', o.RO_PRIME_LINE_NO, IF(o.RO_NO!=o.ORDER_NO AND o.ORDER_NO is not null AND o.ORDER_NO!='', CONCAT(' | ', o.ORDER_NO, ' - ', o.PRIME_LINE_NO), '')) `PROCUREMENT_AGENT_ORDER_NO`, "
                + "    pu.LABEL_EN `PLANNING_UNIT_NAME`,  "
                + "    COALESCE(o.`CURRENT_ESTIMATED_DELIVERY_DATE`,o.`AGREED_DELIVERY_DATE`,o.`REQ_DELIVERY_DATE`) AS EXPECTED_DELIVERY_DATE, "
                + "    o.STATUS, o.QTY, ((o.`QTY` * o.PRICE)+o.SHIPPING_COST) AS TOTAL_COST, "
                + "    CASE WHEN o.CHANGE_CODE=1 THEN 'Created' WHEN o.CHANGE_CODE=2 THEN 'Deleted' WHEN o.CHANGE_CODE=3 THEN 'Updated' END `CHANGE_CODE`, "
                + "    CONCAT(MID(o.FILE_NAME, 12, 4),'-', MID(o.FILE_NAME, 16, 2),'-', MID(o.FILE_NAME, 18, 2)) DATA_RECEIVED_DATE "
                + "FROM rm_erp_order o  "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON papu.PROCUREMENT_AGENT_ID=1 AND LEFT(papu.SKU_CODE,12)=o.PLANNING_UNIT_SKU_CODE "
                + "LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "WHERE o.RO_NO=:roNo and o.RO_PRIME_LINE_NO=:roPrimeLineNo  "
                + "ORDER BY o.FILE_NAME, o.ERP_ORDER_ID";

        history.setErpOrderList(this.namedParameterJdbcTemplate.query(sql, params, new ArtmisHistoryErpOrderRowMapper()));
        sql = "SELECT  "
                + "	CONCAT(s.ORDER_NO, ' - ', s.PRIME_LINE_NO,  "
                + "			IF (s.KN_SHIPMENT_NO is not null AND s.KN_SHIPMENT_NO!='', CONCAT(' | ', s.KN_SHIPMENT_NO),'')) `PROCUREMENT_AGENT_SHIPMENT_NO`, "
                + "    COALESCE(s.`ACTUAL_DELIVERY_DATE`,s.`ACTUAL_SHIPMENT_DATE`) `DELIVERY_DATE`, COALESCE(s.`DELIVERED_QTY`, s.`SHIPPED_QTY`) `QTY`, "
                + "    CASE WHEN s.CHANGE_CODE=1 THEN 'Created' WHEN s.CHANGE_CODE=2 THEN 'Deleted' WHEN s.CHANGE_CODE=3 THEN 'Updated' END `CHANGE_CODE`, "
                + "    s.EXPIRY_DATE, s.BATCH_NO, CONCAT(MID(FILE_NAME, 15, 4),'-', MID(FILE_NAME, 19, 2),'-', MID(FILE_NAME, 21, 2)) DATA_RECEIVED_DATE "
                + "FROM rm_erp_shipment s "
                + "WHERE CONCAT(s.ORDER_NO ,' - ', s.PRIME_LINE_NO) IN (SELECT CONCAT(o.ORDER_NO, ' - ', o.PRIME_LINE_NO) FROM rm_erp_order o WHERE o.RO_NO=:roNo and o.RO_PRIME_LINE_NO=:roPrimeLineNo) "
                + "ORDER BY s.FILE_NAME, s.ERP_SHIPMENT_ID";
        history.setErpShipmentList(this.namedParameterJdbcTemplate.query(sql, params, new ArtmisHistoryErpShipmentRowMapper()));
        return history;
    }

    @Override
    public List<LinkedShipmentBatchDetails> getBatchDetails(List<RoAndRoPrimeLineNo> roAndRoPrimeLineNoList, CustomUserDetails curUser) {
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_ro`";
        this.jdbcTemplate.update(sqlString);
        sqlString = "CREATE TEMPORARY TABLE `tmp_ro` ("
                + "  `RO_NO` VARCHAR(20) NOT NULL, "
                + "  `RO_PRIME_LINE_NO` VARCHAR(45) NOT NULL, "
                + "  PRIMARY KEY (`RO_NO`, `RO_PRIME_LINE_NO`))";
        this.jdbcTemplate.update(sqlString);
        List<SqlParameterSource> paramList = new LinkedList<>();
        roAndRoPrimeLineNoList.stream().collect(Collectors.toList()).forEach(ut -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("roNo", ut.getRoNo());
            param.addValue("roPrimeLineNo", ut.getRoPrimeLineNo());
            paramList.add(param);
        }
        );
        sqlString = "INSERT IGNORE INTO tmp_ro VALUES (:roNo, :roPrimeLineNo)";
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramList.toArray(new MapSqlParameterSource[paramList.size()]));

        StringBuilder sqlStringBuilder = new StringBuilder("SELECT e.RO_NO, e.RO_PRIME_LINE_NO, e.QTY, s.BATCH_NO, COALESCE(s.DELIVERED_QTY,s.SHIPPED_QTY) SHIPPED_QTY "
                + "FROM tmp_ro r "
                + "LEFT JOIN rm_erp_order_consolidated e ON r.RO_NO=e.RO_NO AND r.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO "
                + "LEFT JOIN rm_erp_shipment_consolidated s ON e.ORDER_NO=s.ORDER_NO AND e.PRIME_LINE_NO=s.PRIME_LINE_NO");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), new LinkedShipmentBatchDetailsListResultSetExtractor());
    }

    @Override
    public List<ProductCategory> getProductCategoryListForRealmCountryForErpLinking(CustomUserDetails curUser, int realmCountryId) {

        StringBuilder sqlStringBuilder;
        String setDateQuery = "SET @dt = CURDATE() - INTERVAL 6 MONTH;";
        String setCountryIdQuery = "SET @realmCountryId = ?;";
        this.jdbcTemplate.update(setDateQuery);
        this.jdbcTemplate.update(setCountryIdQuery, realmCountryId);
        String getCountryNameQuery = "SELECT c.LABEL_EN INTO @countryName FROM rm_realm_country rc LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID WHERE rc.REALM_COUNTRY_ID=@realmCountryId";
        this.jdbcTemplate.execute(getCountryNameQuery);
        sqlStringBuilder = new StringBuilder("SELECT pc.PRODUCT_CATEGORY_ID, pc.SORT_ORDER, pcl.LABEL_ID, pcl.LABEL_EN, pcl.LABEL_FR, pcl.LABEL_PR, pcl.LABEL_SP,r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_PR `REALM_LABEL_PR`, rl.LABEL_SP `REALM_LABEL_SP`,cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, pc.ACTIVE, pc.CREATED_DATE, pc.LAST_MODIFIED_DATE  "
                + "FROM rm_erp_order_consolidated e  "
                + "LEFT JOIN rm_erp_shipment_consolidated s ON e.ORDER_NO=s.ORDER_NO AND e.PRIME_LINE_NO=s.PRIME_LINE_NO AND s.ACTIVE   "
                + "LEFT JOIN rm_procurement_agent_planning_unit papu ON (FIND_IN_SET(papu.PLANNING_UNIT_ID,'') OR ''='') AND LEFT(papu.SKU_CODE,12)=e.PLANNING_UNIT_SKU_CODE   "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.EXTERNAL_STATUS_STAGE=COALESCE(s.STATUS, e.STATUS)   "
                + "LEFT JOIN rm_shipment_linking sl ON sl.RO_NO=e.RO_NO and sl.RO_PRIME_LINE_NO=e.RO_PRIME_LINE_NO  AND sl.ACTIVE  "
                + "LEFT JOIN rm_shipment_linking_trans slt ON slt.SHIPMENT_LINKING_ID=sl.SHIPMENT_LINKING_ID AND slt.VERSION_ID=sl.MAX_VERSION_ID AND slt.ACTIVE   "
                + "LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID   "
                + "LEFT JOIN rm_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID "
                + "LEFT JOIN ap_label pcl ON pc.LABEL_ID=pcl.LABEL_ID  "
                + "LEFT JOIN rm_realm r ON pc.REALM_ID=r.REALM_ID  "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID  "
                + "LEFT JOIN us_user cb ON pc.CREATED_BY=cb.USER_ID  "
                + "LEFT JOIN us_user lmb ON pc.LAST_MODIFIED_BY=lmb.USER_ID  "
                + "WHERE e.RECPIENT_COUNTRY=@countryName AND e.ACTIVE "
                + "AND ( "
                + "            ( "
                + "                COALESCE(s.ACTUAL_DELIVERY_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE,e.AGREED_DELIVERY_DATE,e.REQ_DELIVERY_DATE) < @dt "
                + "                AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,2,3,5,7,9,10,13,15)  "
                + "            ) "
                + "        OR  "
                + "            ( "
                + "            COALESCE(s.ACTUAL_DELIVERY_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE,e.AGREED_DELIVERY_DATE,e.REQ_DELIVERY_DATE) >= @dt "
                + "            AND sm.SHIPMENT_STATUS_MAPPING_ID NOT IN (1,3,5,7,9,10,13,15) "
                + "            ) "
                + "        ) "
                + "    AND slt.SHIPMENT_LINKING_TRANS_ID IS NULL  "
                + "    and pu.ACTIVE AND fu.ACTIVE  and pc.ACTIVE "
                + "    GROUP BY pc.PRODUCT_CATEGORY_ID  ORDER BY pc.SORT_ORDER;");
        Map<String, Object> params = new HashMap<>();
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new ProductCategoryRowMapper());
    }

}
