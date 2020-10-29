/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao.impl;

import cc.altius.FASP.dao.impl.ProgramDataDaoImpl;
import cc.altius.FASP.service.EmailService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;
import cc.altius.FASP.ARTMIS.dao.ImportArtmisDataDao;
import cc.altius.FASP.model.DTO.ErpOrderDTO;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderDTORowMapper;
import cc.altius.utils.DateUtils;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author altius
 */
@Repository
public class ImportArtmisDataDaoImpl implements ImportArtmisDataDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Value("${qat.filePath}")
    private String QAT_FILE_PATH;
    @Value("${catalogFilePath}")
    private String CATALOG_FILE_PATH;
    @Value("${catalogBkpFilePath}")
    private String BKP_CATALOG_FILE_PATH;
    @Value("${email.toList}")
    private String toList;
    @Value("${email.ccList}")
    private String ccList;

    @Autowired
    private EmailService emailService;
    @Autowired
    private ProgramDataDaoImpl programDataDaoImpl;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String getPlanningUnitId = "SELECT pu.`PLANNING_UNIT_ID` "
            + "FROM rm_erp_order o "
            + "LEFT JOIN rm_procurement_agent_planning_unit pu ON LEFT(pu.`SKU_CODE`,12)=o.`PLANNING_UNIT_SKU_CODE` AND pu.`PROCUREMENT_AGENT_ID`=1  "
            + "WHERE o.`ERP_ORDER_ID`=?; ";

    private final String isShipmentErpLinked = "SELECT st.`ERP_FLAG` FROM rm_shipment_trans st "
            + " LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
            + " WHERE st.`SHIPMENT_ID`=? "
            //+ " AND s.`PARENT_SHIPMENT_ID` IS NULL "
            + " ORDER BY st.`VERSION_ID` DESC "
            + " LIMIT 1;";

    private final String getVersionIdOrderNoPrimeLineNo = "SELECT MAX(stt.`VERSION_ID`) FROM rm_shipment_trans stt WHERE stt.`ORDER_NO`=? AND stt.`PRIME_LINE_NO`=?;";
    private final String getMaxVersionFromShipmentTable = "SELECT s.`MAX_VERSION_ID` FROM rm_shipment s WHERE s.`SHIPMENT_ID`=?;";
    private final String updateShipmentBasedOnOnderNoPrimeLineNo = "UPDATE rm_shipment_trans st "
            + "LEFT JOIN rm_erp_order eo ON eo.`ORDER_NO`=st.`ORDER_NO`  AND st.`PRIME_LINE_NO`=eo.`PRIME_LINE_NO` "
            + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=eo.`STATUS`"
            + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
            + "LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=eo.`ERP_ORDER_ID` "
            + "SET "
            + "st.`EXPECTED_DELIVERY_DATE`=eo.`CURRENT_ESTIMATED_DELIVERY_DATE`, "
            + "st.`SHIPMENT_QTY`=eo.`QTY`,st.`RATE`=eo.`PRICE`, "
            + "st.`PRODUCT_COST`=(eo.`QTY`*eo.`PRICE`),st.`SHIPMENT_MODE`=eo.`SHIP_BY`, "
            + "st.`FREIGHT_COST`=eo.`SHIPPING_COST`, "
            + "st.`SUBMITTED_DATE`=COALESCE(eo.`PARENT_CREATED_DATE`,eo.`ORDER_ENTRY_DATE`), "
            + "st.`APPROVED_DATE`=eo.`ORDERD_DATE`, "
            + "st.`SHIPPED_DATE`=MIN(es.`ACTUAL_SHIPMENT_DATE`), "
            + "st.`ARRIVED_DATE`=IF(sm.`SHIPMENT_STATUS_ID`=21,?,NULL), "
            + "st.`RECEIVED_DATE`=MIN(es.`ACTUAL_DELIVERY_DATE`), "
            + "st.`LAST_MODIFIED_BY`=1, "
            + "st.`LAST_MODIFIED_DATE`=?, "
            + "st.`SHIPMENT_STATUS_ID`=sm.`SHIPMENT_STATUS_ID` "
            + "WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND s.`PARENT_SHIPMENT_ID`=? AND st.`VERSION_ID`=?;";

    private final String createNewEntryInShipmentTable = "INSERT INTO  rm_shipment "
            + "SELECT  NULL,s.`PROGRAM_ID`,:qty,s.`CURRENCY_ID`,s.`CONVERSION_RATE_TO_USD`, "
            + "s.`SHIPMENT_ID`,1,:createdDate,1,:lastModifiedDate,s.`MAX_VERSION_ID`,NULL "
            + "FROM rm_shipment s "
            + "WHERE s.`SHIPMENT_ID`=:shipmentId;";

    private final String createNewEntryIntoShipmentTrans = "INSERT INTO rm_shipment_trans "
            + "SELECT NULL,:shipmentId,st.`PLANNING_UNIT_ID`,st.`PROCUREMENT_AGENT_ID`,st.`FUNDING_SOURCE_ID`,st.`BUDGET_ID`, "
            + "COALESCE(eo.`CURRENT_ESTIMATED_DELIVERY_DATE`,st.`EXPECTED_DELIVERY_DATE`),st.`PROCUREMENT_UNIT_ID` ,st.`SUPPLIER_ID`,eo.`QTY`,eo.`PRICE`,(eo.`QTY`*eo.`PRICE`),eo.`SHIP_BY`,eo.`SHIPPING_COST`, "
            + "st.`PLANNED_DATE`,COALESCE(eo.`PARENT_CREATED_DATE`,eo.`ORDER_ENTRY_DATE`,st.`SUBMITTED_DATE`),COALESCE(eo.`ORDERD_DATE`,st.`APPROVED_DATE`),IFNULL(MIN(es.`ACTUAL_SHIPMENT_DATE`),NULL),IF(sm.`SHIPMENT_STATUS_ID`=21,:CURDATE,NULL),IFNULL(MIN(es.`ACTUAL_DELIVERY_DATE`),NULL),sm.`SHIPMENT_STATUS_ID`,NULL,1, "
            + "eo.`ORDER_NO`,eo.`PRIME_LINE_NO`,st.`ACCOUNT_FLAG`,st.`EMERGENCY_ORDER`,st.`LOCAL_PROCUREMENT`,1,st.`DATA_SOURCE_ID`,:CURDATE1,s.`MAX_VERSION_ID`,1 "
            + "FROM rm_shipment_trans st "
            + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
            + "LEFT JOIN rm_erp_order eo ON eo.`ORDER_NO`=:orderNo AND eo.`PRIME_LINE_NO`=:primeLineNo "
            + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=eo.`STATUS` "
            + "LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=eo.`ERP_ORDER_ID` "
            + "WHERE st.`SHIPMENT_ID`=:shipmentId1 "
            + "ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1;";

    private final String createNewEntryInShipmentTableDiff = "INSERT INTO  rm_shipment "
            + "SELECT  NULL,s.`PROGRAM_ID`,s.`SUGGESTED_QTY`,s.`CURRENCY_ID`,s.`CONVERSION_RATE_TO_USD`, "
            + "s.`SHIPMENT_ID`,1,:createdDate,1,:lastModifiedDate,s.`MAX_VERSION_ID`,NULL "
            + "FROM rm_shipment s "
            + "WHERE s.`SHIPMENT_ID`=:shipmentId;";
    //Shipped qty
    private final String insertIntoShipmentTransBatchInfo = "INSERT INTO rm_shipment_trans_batch_info SELECT NULL,?,b.`BATCH_ID`,SUM(s.`DELIVERED_QTY`) FROM rm_erp_shipment s "
            + "LEFT JOIN rm_batch_info b ON b.`BATCH_NO`=s.`BATCH_NO` AND b.`PROGRAM_ID`=? AND b.`PLANNING_UNIT_ID`=? "
            + "WHERE s.`FLAG`=1 AND s.`ERP_ORDER_ID`=? "
            + "GROUP BY s.`BATCH_NO`;";

    private final String updateShipmentTransManuallyTagged = "UPDATE rm_shipment_trans st "
            + "LEFT JOIN rm_erp_order eo ON eo.`ORDER_NO`=st.`ORDER_NO`  AND st.`PRIME_LINE_NO`=eo.`PRIME_LINE_NO` "
            + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=eo.`STATUS` "
            + "SET "
            + "st.`EXPECTED_DELIVERY_DATE`=eo.`CURRENT_ESTIMATED_DELIVERY_DATE`, "
            + "st.`SHIPMENT_QTY`=eo.`QTY`,st.`RATE`=eo.`PRICE`, "
            + "st.`PRODUCT_COST`=(eo.`QTY`*eo.`PRICE`),st.`SHIPMENT_MODE`=eo.`SHIP_BY`, "
            + "st.`FREIGHT_COST`=eo.`SHIPPING_COST`, "
            + "st.`SUBMITTED_DATE`=COALESCE(eo.`PARENT_CREATED_DATE`,eo.`ORDER_ENTRY_DATE`), "
            + "st.`APPROVED_DATE`=eo.`ORDERD_DATE`, "
            + "st.`SHIPPED_DATE`=MIN(es.`ACTUAL_SHIPMENT_DATE`), "
            + "st.`ARRIVED_DATE`=IF(sm.`SHIPMENT_STATUS_ID`=21,?,NULL), "
            + "st.`RECEIVED_DATE`=MIN(es.`ACTUAL_DELIVERY_DATE`), "
            + "st.`LAST_MODIFIED_BY`=1, "
            + "st.`LAST_MODIFIED_DATE`=?, "
            + "st.`SHIPMENT_STATUS_ID`=sm.`SHIPMENT_STATUS_ID` "
            + "WHERE st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=? AND st.`SHIPMENT_ID`=?;";

    private final String deactivateParentAndMarkErpLinked = "UPDATE rm_shipment_trans st "
            + "SET st.`ERP_FLAG`=1,st.`ACTIVE`=0 "
            + "WHERE st.`SHIPMENT_ID`=? AND st.`VERSION_ID`=?";

    @Override
    @Transactional
    public void importOrderAndShipmentData(File orderFile, File shipmentFile) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        logger.info("Starting import of " + orderFile.getName() + " and " + shipmentFile.getName());
//        String sqlString = "DROP TEMPORARY TABLE IF EXISTS tmp_erp_order";
        String sqlString = "DROP TABLE IF EXISTS tmp_erp_order";
        this.jdbcTemplate.execute(sqlString);
        // Create ERO oder table
//        sqlString = "CREATE TEMPORARY TABLE `tmp_erp_order` ( "
        sqlString = "CREATE TABLE `tmp_erp_order` ( "
                + "  `TEMP_ID` int(11) NOT NULL AUTO_INCREMENT, "
                + "  `RO_NO` varchar(45) COLLATE utf8_bin NOT NULL, "
                + "  `RO_PRIME_LINE_NO` int(11) NOT NULL, "
                + "  `ORDER_NO` varchar(45) COLLATE utf8_bin NOT NULL, "
                + "  `PRIME_LINE_NO` int(11) DEFAULT NULL, "
                + "  `ORDER_TYPE_IND` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ORDER_ENTRY_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `PARENT_RO` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `PARENT_ORDER_ENTRY_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ITEM_ID` varchar(100) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ORDERED_QTY` int(11) DEFAULT NULL, "
                + "  `PO_RELEASED_FOR_FULFILLMENT_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `LATEST_ESTIMATED_DELIVERY_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `REQ_DELIVERY_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `REVISED_AGREED_DELIVERY_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ITEM_SUPPLIER_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL, "
                + "  `UNIT_PRICE` double DEFAULT NULL, "
                + "  `STATUS_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL, "
                + "  `EXTERNAL_STATUS_STAGE` varchar(100) COLLATE utf8_bin DEFAULT NULL, "
                + "  `SHIPPING_CHARGES` double DEFAULT NULL, "
                + "  `FREIGHT_ESTIMATE` double DEFAULT NULL, "
                + "  `TOTAL_ACTUAL_FREIGHT_COST` double DEFAULT NULL, "
                + "  `CARRIER_SERVICE_CODE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `RECIPIENT_NAME` varchar(100) COLLATE utf8_bin DEFAULT NULL, "
                + "  `RECIPIENT_COUNTRY` varchar(100) COLLATE utf8_bin DEFAULT NULL, "
                + "  `STATUS` int(11) DEFAULT NULL, "
                + "  `PROGRAM_ID` int(11) DEFAULT NULL, "
                + "  `SHIPMENT_ID` int(11) DEFAULT NULL, "
                + "  PRIMARY KEY (`TEMP_ID`), "
                + "  UNIQUE KEY `TEMP_ID_UNIQUE` (`TEMP_ID`) "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.execute(sqlString);

        //----------------Read order xml--------------------start----------------
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        FileReader fr = new FileReader(orderFile);
        Document doc;
        NodeList nList1;
        MapSqlParameterSource[] batchParams;
        Map<String, Object> map = new HashedMap<String, Object>();
        int[] rows1;
        int x;

        if (fr.read() == -1) {
            //file is empty
            throw new IOException("Order file is empty");
        } else {
            doc = dBuilder.parse(orderFile);
            doc.getDocumentElement().normalize();

            nList1 = doc.getElementsByTagName("orderdata");
            batchParams = new MapSqlParameterSource[nList1.getLength()];
            x = 0;
            for (int temp2 = 0; temp2 < nList1.getLength(); temp2++) {
                Node nNode1 = nList1.item(temp2);
                if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                    Element dataRecordElement = (Element) nNode1;
                    map.put("RO_NO", dataRecordElement.getElementsByTagName("ro_number").item(0).getTextContent());
                    map.put("RO_PRIME_LINE_NO", dataRecordElement.getElementsByTagName("ro_prime_line_no").item(0).getTextContent());
                    map.put("ORDER_NO", dataRecordElement.getElementsByTagName("order_number").item(0).getTextContent());
                    map.put("PRIME_LINE_NO", dataRecordElement.getElementsByTagName("prime_line_no").item(0).getTextContent());
                    map.put("ORDER_TYPE_IND", dataRecordElement.getElementsByTagName("order_type_ind").item(0).getTextContent());
                    map.put("ORDER_ENTRY_DATE", dataRecordElement.getElementsByTagName("order_entry_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("order_entry_date").item(0).getTextContent());
                    map.put("PARENT_RO", dataRecordElement.getElementsByTagName("parent_ro").item(0).getTextContent());
                    map.put("PARENT_ORDER_ENTRY_DATE", dataRecordElement.getElementsByTagName("parent_ro_entry_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("parent_ro_entry_date").item(0).getTextContent());
                    map.put("ITEM_ID", dataRecordElement.getElementsByTagName("item_id").item(0).getTextContent());
                    map.put("ORDERED_QTY", dataRecordElement.getElementsByTagName("ordered_qty").item(0).getTextContent());
                    map.put("PO_RELEASED_FOR_FULFILLMENT_DATE", dataRecordElement.getElementsByTagName("po_released_for_fulfillment_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("po_released_for_fulfillment_date").item(0).getTextContent());
                    map.put("LATEST_ESTIMATED_DELIVERY_DATE", dataRecordElement.getElementsByTagName("latest_estimated_delivery_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("latest_estimated_delivery_date").item(0).getTextContent());
                    map.put("REQ_DELIVERY_DATE", dataRecordElement.getElementsByTagName("req_delivery_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("req_delivery_date").item(0).getTextContent());
                    map.put("REVISED_AGREED_DELIVERY_DATE", dataRecordElement.getElementsByTagName("revised_agreed_delivery_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("revised_agreed_delivery_date").item(0).getTextContent());
                    map.put("ITEM_SUPPLIER_NAME", dataRecordElement.getElementsByTagName("item_supplier_name").item(0).getTextContent());
                    map.put("UNIT_PRICE", dataRecordElement.getElementsByTagName("unit_price").item(0).getTextContent());
                    map.put("STATUS_NAME", dataRecordElement.getElementsByTagName("status_name").item(0).getTextContent());
                    map.put("EXTERNAL_STATUS_STAGE", dataRecordElement.getElementsByTagName("external_status_stage").item(0).getTextContent());
                    map.put("SHIPPING_CHARGES", dataRecordElement.getElementsByTagName("shipping_charges").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("shipping_charges").item(0).getTextContent());
                    map.put("FREIGHT_ESTIMATE", dataRecordElement.getElementsByTagName("freight_estimate").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("freight_estimate").item(0).getTextContent());
                    map.put("TOTAL_ACTUAL_FREIGHT_COST", dataRecordElement.getElementsByTagName("total_actual_freight_cost").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("total_actual_freight_cost").item(0).getTextContent());
                    map.put("CARRIER_SERVICE_CODE", dataRecordElement.getElementsByTagName("carrier_service_code").item(0).getTextContent());
                    map.put("RECIPIENT_NAME", dataRecordElement.getElementsByTagName("recipient_name").item(0).getTextContent());
                    map.put("RECIPIENT_COUNTRY", dataRecordElement.getElementsByTagName("recipient_country").item(0).getTextContent());
                    map.put("PROGRAM_ID", (dataRecordElement.getElementsByTagName("qat_program_id").item(0).getTextContent() != null && dataRecordElement.getElementsByTagName("qat_program_id").item(0).getTextContent() != "" ? dataRecordElement.getElementsByTagName("qat_program_id").item(0).getTextContent() : null));
                    map.put("SHIPMENT_ID", (dataRecordElement.getElementsByTagName("qat_shipment_id").item(0).getTextContent() != null && dataRecordElement.getElementsByTagName("qat_shipment_id").item(0).getTextContent() != "" ? dataRecordElement.getElementsByTagName("qat_shipment_id").item(0).getTextContent() : null));
                    batchParams[x] = new MapSqlParameterSource(map);
                    x++;
                }
            }
            sqlString = "INSERT INTO tmp_erp_order (`RO_NO`, `RO_PRIME_LINE_NO`, `ORDER_NO`, `PRIME_LINE_NO`, `ORDER_TYPE_IND`, "
                    + "`ORDER_ENTRY_DATE`, `PARENT_RO`, `PARENT_ORDER_ENTRY_DATE`, `ITEM_ID`, `ORDERED_QTY`, "
                    + "`PO_RELEASED_FOR_FULFILLMENT_DATE`, `LATEST_ESTIMATED_DELIVERY_DATE`, `REQ_DELIVERY_DATE`, `REVISED_AGREED_DELIVERY_DATE`, `ITEM_SUPPLIER_NAME`, "
                    + "`UNIT_PRICE`, `STATUS_NAME`, `EXTERNAL_STATUS_STAGE`, `SHIPPING_CHARGES`, `FREIGHT_ESTIMATE`, "
                    + "`TOTAL_ACTUAL_FREIGHT_COST`, `CARRIER_SERVICE_CODE`, `RECIPIENT_NAME`, `RECIPIENT_COUNTRY`, "
                    + "`PROGRAM_ID`, `SHIPMENT_ID`) VALUES "
                    + "(:RO_NO, :RO_PRIME_LINE_NO, :ORDER_NO, :PRIME_LINE_NO, :ORDER_TYPE_IND, "
                    + ":ORDER_ENTRY_DATE, :PARENT_RO, :PARENT_ORDER_ENTRY_DATE, :ITEM_ID, :ORDERED_QTY, "
                    + ":PO_RELEASED_FOR_FULFILLMENT_DATE, :LATEST_ESTIMATED_DELIVERY_DATE, :REQ_DELIVERY_DATE, :REVISED_AGREED_DELIVERY_DATE, :ITEM_SUPPLIER_NAME, "
                    + ":UNIT_PRICE, :STATUS_NAME, :EXTERNAL_STATUS_STAGE, :SHIPPING_CHARGES, :FREIGHT_ESTIMATE, "
                    + ":TOTAL_ACTUAL_FREIGHT_COST, :CARRIER_SERVICE_CODE, :RECIPIENT_NAME, :RECIPIENT_COUNTRY, "
                    + ":PROGRAM_ID, :SHIPMENT_ID)";
            SqlParameterSource[] batchSqlParams = ArrayUtils.toArray(batchParams);
            rows1 = this.namedParameterJdbcTemplate.batchUpdate(sqlString, batchSqlParams);
            logger.info("Successfully inserted into tmp_erp_order records---" + rows1.length);
        }

//        sqlString = "DROP TEMPORARY TABLE IF EXISTS tmp_erp_shipment";
        sqlString = "DROP TABLE IF EXISTS tmp_erp_shipment";
        this.jdbcTemplate.execute(sqlString);
//        sqlString = "CREATE TEMPORARY TABLE `tmp_erp_shipment` ( "
        sqlString = "CREATE TABLE `tmp_erp_shipment` ( "
                + "  `TEMP_SHIPMENT_ID` int(11) NOT NULL AUTO_INCREMENT, "
                + "  `KN_SHIPMENT_NO` varchar(45) COLLATE utf8_bin NOT NULL, "
                + "  `ORDER_NO` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `PRIME_LINE_NO` int(11) DEFAULT NULL, "
                + "  `BATCH_NO` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ITEM_ID` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `EXPIRATION_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `SHIPPED_QUANTITY` int(11) DEFAULT NULL, "
                + "  `DELIVERED_QUANTITY` int(11) DEFAULT NULL, "
                + "  `STATUS_NAME` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `EXTERNAL_STATUS_STAGE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ACTUAL_SHIPMENT_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ACTUAL_DELIVERY_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `STATUS` int(11) DEFAULT NULL, "
                + "  PRIMARY KEY (`TEMP_SHIPMENT_ID`), "
                + "  UNIQUE KEY `TEMP_SHIPMENT_ID_UNIQUE` (`TEMP_SHIPMENT_ID`) "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.execute(sqlString);

        //----------------Read shipment xml--------------------start----------------
        dbFactory = DocumentBuilderFactory.newInstance();
        dBuilder = dbFactory.newDocumentBuilder();
        fr = new FileReader(shipmentFile);

        if (fr.read()
                == -1) {
            //file is empty
            logger.info("Shipment file is empty");
        } else {
            doc = dBuilder.parse(shipmentFile);
            doc.getDocumentElement().normalize();
            NodeList nList2 = doc.getElementsByTagName("shipmentdata");
            batchParams = new MapSqlParameterSource[nList2.getLength()];
//            map.clear();
            x = 0;
            sqlString = "INSERT INTO tmp_erp_shipment (`KN_SHIPMENT_NO`, `ORDER_NO`, `PRIME_LINE_NO`, `BATCH_NO`, `ITEM_ID`, "
                    + "`EXPIRATION_DATE`, `SHIPPED_QUANTITY`, `DELIVERED_QUANTITY`, `STATUS_NAME`, `EXTERNAL_STATUS_STAGE`, "
                    + "`ACTUAL_SHIPMENT_DATE`, `ACTUAL_DELIVERY_DATE`) "
                    + "VALUES (:KN_SHIPMENT_NO, :ORDER_NO, :PRIME_LINE_NO, :BATCH_NO, :ITEM_ID, "
                    + ":EXPIRATION_DATE, :SHIPPED_QUANTITY, :DELIVERED_QUANTITY, :STATUS_NAME, :EXTERNAL_STATUS_STAGE, "
                    + ":ACTUAL_SHIPMENT_DATE, :ACTUAL_DELIVERY_DATE)";
            for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
                Node nNode1 = nList2.item(temp2);
                if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                    Element dataRecordElement = (Element) nNode1;
                    map.put("KN_SHIPMENT_NO", dataRecordElement.getElementsByTagName("kn_shipment_number").item(0).getTextContent());
                    map.put("ORDER_NO", dataRecordElement.getElementsByTagName("order_number").item(0).getTextContent());
                    map.put("PRIME_LINE_NO", dataRecordElement.getElementsByTagName("prime_line_no").item(0).getTextContent());
                    map.put("BATCH_NO", dataRecordElement.getElementsByTagName("batch_no").item(0).getTextContent());
                    map.put("ITEM_ID", dataRecordElement.getElementsByTagName("item_id").item(0).getTextContent());
                    map.put("EXPIRATION_DATE", dataRecordElement.getElementsByTagName("expiration_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("expiration_date").item(0).getTextContent());
                    map.put("SHIPPED_QUANTITY", dataRecordElement.getElementsByTagName("shipped_quantity").item(0).getTextContent());
                    map.put("DELIVERED_QUANTITY", dataRecordElement.getElementsByTagName("delivered_quantity").item(0).getTextContent().isBlank() ? 0 : dataRecordElement.getElementsByTagName("delivered_quantity").item(0).getTextContent());
                    map.put("STATUS_NAME", dataRecordElement.getElementsByTagName("status_name").item(0).getTextContent());
                    map.put("EXTERNAL_STATUS_STAGE", dataRecordElement.getElementsByTagName("external_status_stage").item(0).getTextContent());
                    map.put("ACTUAL_SHIPMENT_DATE", dataRecordElement.getElementsByTagName("actual_shipment_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("actual_shipment_date").item(0).getTextContent());
                    map.put("ACTUAL_DELIVERY_DATE", dataRecordElement.getElementsByTagName("actual_delivery_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("actual_delivery_date").item(0).getTextContent());
                    batchParams[x] = new MapSqlParameterSource(map);
                    x++;
                }
            }
            SqlParameterSource[] batchSqlParams = ArrayUtils.toArray(batchParams);
            rows1 = this.namedParameterJdbcTemplate.batchUpdate(sqlString, batchSqlParams);
            logger.info("Successfully inserted into tmp_erp_shipment records---" + rows1.length);

            sqlString = "UPDATE rm_erp_order o SET o.`FLAG`=0";
            this.jdbcTemplate.update(sqlString);

            sqlString = "SELECT COUNT(*) FROM rm_erp_order;";
            logger.info("Total rows present in rm_erp_order---" + this.jdbcTemplate.queryForObject(sqlString, Integer.class));

            sqlString = "UPDATE tmp_erp_order t "
                    + "LEFT JOIN rm_erp_order o ON t.`ORDER_NO`=o.`ORDER_NO` AND t.`PRIME_LINE_NO`=o.`PRIME_LINE_NO` "
                    + "SET "
                    + "o.`RO_NO`=t.`RO_NO`, "
                    + "o.`RO_PRIME_LINE_NO`=t.`RO_PRIME_LINE_NO`, "
                    + "o.`ORDER_TYPE`=t.`ORDER_TYPE_IND`, "
                    + "o.`CREATED_DATE`=IFNULL(CONCAT(LEFT(t.`ORDER_ENTRY_DATE`,10),' ',REPLACE(MID(t.`ORDER_ENTRY_DATE`,12,8),'.',':')),NULL), "
                    + "o.`PARENT_RO`=t.`PARENT_RO`, "
                    + "o.`PARENT_CREATED_DATE`=IFNULL(CONCAT(LEFT(t.`PARENT_ORDER_ENTRY_DATE`,10),' ',REPLACE(MID(t.`PARENT_ORDER_ENTRY_DATE`,12,8),'.',':')),NULL), "
                    + "o.`PLANNING_UNIT_SKU_CODE`=LEFT(t.`ITEM_ID`,12), "
                    + "o.`PROCUREMENT_UNIT_SKU_CODE`=IF(LENGTH(t.`ITEM_ID`)>=15,t.`ITEM_ID`,NULL), "
                    + "o.`QTY`=t.`ORDERED_QTY`, "
                    + "o.`ORDERD_DATE`=IFNULL(CONCAT(LEFT(t.`PO_RELEASED_FOR_FULFILLMENT_DATE`,10),' ',REPLACE(MID(t.`PO_RELEASED_FOR_FULFILLMENT_DATE`,12,8),'.',':')),NULL), "
                    + "o.`CURRENT_ESTIMATED_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`LATEST_ESTIMATED_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`LATEST_ESTIMATED_DELIVERY_DATE`,12,8),'.',':')),NULL), "
                    + "o.`REQ_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`REQ_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`REQ_DELIVERY_DATE`,12,8),'.',':')),NULL), "
                    + "o.`AGREED_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`REVISED_AGREED_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`REVISED_AGREED_DELIVERY_DATE`,12,8),'.',':')),NULL), "
                    + "o.`SUPPLIER_NAME`=t.`ITEM_SUPPLIER_NAME`, "
                    + "o.`PRICE`=t.`UNIT_PRICE`, "
                    + "o.`SHIPPING_COST`=COALESCE(IF(t.`TOTAL_ACTUAL_FREIGHT_COST`=0,NULL,t.`TOTAL_ACTUAL_FREIGHT_COST`),IF(t.`FREIGHT_ESTIMATE`=0,NULL,t.`FREIGHT_ESTIMATE`),IF(t.`SHIPPING_CHARGES`=0,NULL,t.`SHIPPING_CHARGES`)), "
                    + "o.`SHIP_BY`=t.`CARRIER_SERVICE_CODE`, "
                    + "o.`RECPIENT_NAME`=t.`RECIPIENT_NAME`, "
                    + "o.`RECPIENT_COUNTRY`=t.`RECIPIENT_COUNTRY`, "
                    + "o.`STATUS`=t.`EXTERNAL_STATUS_STAGE`, "
                    + "o.`LAST_MODIFIED_DATE`=:curDate, "
                    + "o.`PROGRAM_ID`=t.`PROGRAM_ID`, "
                    + "o.`SHIPMENT_ID`=t.`SHIPMENT_ID`, "
                    + "o.`FLAG`=1 "
                    + "WHERE o.ERP_ORDER_ID IS NOT NULL";
            Map<String, Object> params = new HashMap<>();
            params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
            int rows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("No of rows updated in rm_erp_order---" + rows);

            sqlString = "INSERT IGNORE INTO rm_erp_order (`RO_NO`, `RO_PRIME_LINE_NO`, `ORDER_NO`, `PRIME_LINE_NO`, `ORDER_TYPE`, "
                    + "`PARENT_RO`, `PARENT_CREATED_DATE`, `PLANNING_UNIT_SKU_CODE`, `PROCUREMENT_UNIT_SKU_CODE`, `QTY`, "
                    + "`ORDERD_DATE`, `CURRENT_ESTIMATED_DELIVERY_DATE`, `REQ_DELIVERY_DATE`, `AGREED_DELIVERY_DATE`, `SUPPLIER_NAME`, "
                    + "`PRICE`, `SHIPPING_COST`, `SHIP_BY`, `RECPIENT_NAME`, `RECPIENT_COUNTRY`, "
                    + "`STATUS`, `PROCUREMENT_AGENT_ID`, `LAST_MODIFIED_DATE`, `FLAG`, `VERSION_ID`, `PROGRAM_ID`, `SHIPMENT_ID`, `CREATED_DATE`) "
                    + " SELECT t.RO_NO, t.RO_PRIME_LINE_NO, t.ORDER_NO, t.PRIME_LINE_NO, t.ORDER_TYPE_IND,"
                    + " t.PARENT_RO, DATE_FORMAT(t.PARENT_ORDER_ENTRY_DATE,'%Y-%m-%d %H:%i:%s'), LEFT(t.ITEM_ID, 12), IF(LENGTH(t.ITEM_ID)=15,t.ITEM_ID,NULL), t.ORDERED_QTY,"
                    + " IFNULL(DATE_FORMAT(t.PO_RELEASED_FOR_FULFILLMENT_DATE,'%Y-%m-%d'),NULL), IFNULL(DATE_FORMAT(t.LATEST_ESTIMATED_DELIVERY_DATE,'%Y-%m-%d'),NULL), IFNULL(DATE_FORMAT(t.REQ_DELIVERY_DATE,'%Y-%m-%d'),NULL), IFNULL(DATE_FORMAT(t.REVISED_AGREED_DELIVERY_DATE,'%Y-%m-%d'),NULL), t.ITEM_SUPPLIER_NAME, "
                    + " t.UNIT_PRICE,COALESCE(t.TOTAL_ACTUAL_FREIGHT_COST,t.FREIGHT_ESTIMATE,SHIPPING_CHARGES), t.CARRIER_SERVICE_CODE, t.RECIPIENT_NAME, t.RECIPIENT_COUNTRY,"
                    + " t.STATUS_NAME, 1, :curDate, 1, NULL, t.PROGRAM_ID, t.SHIPMENT_ID, DATE_FORMAT(t.ORDER_ENTRY_DATE,'%Y-%m-%d') "
                    + " FROM tmp_erp_order t "
                    + " LEFT JOIN rm_erp_order o ON t.`ORDER_NO`=o.`ORDER_NO` AND t.`PRIME_LINE_NO`=o.`PRIME_LINE_NO` "
                    + " WHERE o.FLAG IS NULL OR o.FLAG=0";
            rows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("No of rows inserted into rm_erp_order---" + rows);

            // Update erp shipment table
            sqlString = "UPDATE tmp_erp_shipment t "
                    + "LEFT JOIN rm_erp_shipment s ON t.`KN_SHIPMENT_NO`=s.`KN_SHIPMENT_NO` AND t.`ORDER_NO`=s.`ORDER_NO` AND t.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` AND t.`BATCH_NO`=s.`BATCH_NO` "
                    + "SET "
                    + " s.`FLAG`=1, "
                    + " s.`LAST_MODIFIED_DATE`=:curDate, "
                    + " s.`EXPIRY_DATE`=IFNULL(LEFT(t.`EXPIRATION_DATE`,10),NULL), "
                    + " s.`PROCUREMENT_UNIT_SKU_CODE`=t.`ITEM_ID`, "
                    + " s.`SHIPPED_QTY`=t.`SHIPPED_QUANTITY`, "
                    + " s.`DELIVERED_QTY`=t.`DELIVERED_QUANTITY`, "
                    + " s.`ACTUAL_SHIPMENT_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_SHIPMENT_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_SHIPMENT_DATE`,12,8),'.',':')),NULL), "
                    + " s.`ACTUAL_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_DELIVERY_DATE`,12,8),'.',':')),NULL), "
                    + " s.`STATUS`=t.`EXTERNAL_STATUS_STAGE` "
                    + " WHERE s.ERP_SHIPMENT_ID IS NOT NULL";
            rows = this.namedParameterJdbcTemplate.update(sqlString, params);

            logger.info("No of rows updated in  rm_erp_shipment---" + rows);

            //Insert into erp shipment table
            sqlString = "INSERT IGNORE INTO rm_erp_shipment (KN_SHIPMENT_NO, ORDER_NO, PRIME_LINE_NO, BATCH_NO, "
                    + "EXPIRY_DATE, PROCUREMENT_UNIT_SKU_CODE, SHIPPED_QTY, DELIVERED_QTY, ACTUAL_SHIPMENT_DATE, "
                    + "ACTUAL_DELIVERY_DATE, STATUS, LAST_MODIFIED_DATE, FLAG)"
                    + "SELECT t.KN_SHIPMENT_NO, t.ORDER_NO, t.PRIME_LINE_NO, t.BATCH_NO,"
                    + "DATE_FORMAT(t.EXPIRATION_DATE,'%Y-%m-%d %H:%i:%s'), IF(LENGTH(t.ITEM_ID)=15,t.ITEM_ID, null), t.SHIPPED_QUANTITY, t.DELIVERED_QUANTITY, DATE_FORMAT(t.ACTUAL_SHIPMENT_DATE,'%Y-%m-%d %H:%i:%s'), "
                    + "DATE_FORMAT(t.ACTUAL_DELIVERY_DATE,'%Y-%m-%d %H:%i:%s'), t.EXTERNAL_STATUS_STAGE, :curDate, 1 "
                    + "FROM  tmp_erp_shipment t "
                    + "LEFT JOIN rm_erp_shipment s ON t.`KN_SHIPMENT_NO`=s.`KN_SHIPMENT_NO` AND t.`ORDER_NO`=s.`ORDER_NO` AND t.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` AND t.`BATCH_NO`=s.`BATCH_NO` "
                    + "WHERE s.FLAG IS NULL OR s.FLAG=0";
            rows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("No of rows inserted into rm_erp_shipment---" + rows);

            sqlString = "UPDATE rm_erp_shipment t LEFT JOIN rm_erp_order o ON o.`ORDER_NO`=t.`ORDER_NO` AND o.`PRIME_LINE_NO`=t.`PRIME_LINE_NO` SET t.`ERP_ORDER_ID`=o.`ERP_ORDER_ID`;";
            rows = this.namedParameterJdbcTemplate.update(sqlString, params);
            logger.info("update erp order id in erp shipment table---" + rows);

            // ##############################################################
            // Completed till here
            // ##############################################################
/*            sqlString = "SELECT e.ERP_ORDER_ID, e.SHIPMENT_ID, e.ORDER_NO, e.PRIME_LINE_NO ,pu.`LABEL_ID`,IFNULL(pu.`LABEL_EN`,'') AS LABEL_EN, pu.`LABEL_FR`,pu.`LABEL_PR`,pu.`LABEL_SP`, "
                    + "e.QTY, e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_TYPE, e.PLANNING_UNIT_SKU_CODE, e.PROCUREMENT_UNIT_SKU_CODE, e.CURRENT_ESTIMATED_DELIVERY_DATE, e.SUPPLIER_NAME, e.PRICE, e.SHIPPING_COST, e.STATUS, e.RECPIENT_COUNTRY, "
                    + "COALESCE(s.PROGRAM_ID, s1.PROGRAM_ID) `PROGRAM_ID`, st.VERSION_ID, COALESCE(s.SHIPMENT_ID, s1.SHIPMENT_ID) `SHIPMENT_SHIPMENT_ID`, pu.PLANNING_UNIT_ID, papu2.PROCUREMENT_UNIT_ID, pu2.SUPPLIER_ID, mt.MANUAL_TAGGING_ID, st.ACTIVE, st.ERP_FLAG, sm1.PARENT_SHIPMENT_ID "
                    + "st.FUNDING_SOURCE_ID "
                    + "FROM rm_erp_order e "
                    + "LEFT JOIN rm_procurement_agent_planning_unit papu ON LEFT(papu.`SKU_CODE`,12)=e.`PLANNING_UNIT_SKU_CODE` AND papu.`PROCUREMENT_AGENT_ID`=1 "
                    + "LEFT JOIN vw_planning_unit pu ON papu.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                    + "LEFT JOIN rm_shipment s ON e.SHIPMENT_ID=s.SHIPMENT_ID "
                    + "LEFT JOIN rm_manual_tagging mt ON e.ORDER_NO=mt.ORDER_NO AND e.PRIME_LINE_NO=mt.PRIME_LINE_NO "
                    + "LEFT JOIN rm_shipment s1 ON mt.SHIPMENT_ID=s1.SHIPMENT_ID "
                    + "LEFT JOIN (SELECT sx1.SHIPMENT_ID, sx1.PARENT_SHIPMENT_ID, MAX(st1.VERSION_ID) MAX_VERSION_ID FROM rm_shipment sx1 left join rm_shipment_trans st1 on sx1.SHIPMENT_ID=st1.SHIPMENT_ID group by st1.SHIPMENT_ID) sm1 ON sm1.SHIPMENT_ID=e.SHIPMENT_ID OR sm1.SHIPMENT_ID=mt.SHIPMENT_ID "
                    + "LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=sm1.SHIPMENT_ID AND st.VERSION_ID=sm1.MAX_VERSION_ID  "
                    + "LEFT JOIN rm_procurement_agent_procurement_unit papu2 ON e.PROCUREMENT_UNIT_SKU_CODE=LEFT(papu2.SKU_CODE,15) AND papu2.PROCUREMENT_AGENT_ID=1 "
                    + "LEFT JOIN rm_procurement_unit pu2 ON papu2.PROCUREMENT_UNIT_ID=pu2.PROCUREMENT_UNIT_ID "
                    + "WHERE e.`FLAG`=1 AND (e.SHIPMENT_ID IS NOT NULL OR mt.ACTIVE)";
            List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sqlString, new ErpOrderDTORowMapper());
//            int versionId = 0;
            logger.info("");
            logger.info("");
            logger.info("erpOrderDTO---" + erpOrderDTOList.size());
            for (ErpOrderDTO erpOrderDTO : erpOrderDTOList) {
                try {
                    // Shipment id found in file
                    logger.info("Active - " + erpOrderDTO.getShActive());
                    logger.info("ERP Flag - " + erpOrderDTO.getShErpFlag());
                    logger.info("ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                    logger.info("Shipment Id - " + erpOrderDTO.getShShipmentId());
                    logger.info("ManualTagging - " + erpOrderDTO.isManualTagging());
                    if (erpOrderDTO.isShErpFlag() && erpOrderDTO.getShParentShipmentId() != null) {
                        // The ERP Flag is true and the Parent Shipment Id exists
                        // Find all Shipments whose Parent Shipment Id is :parentShipmentId and :orderNo and :primeLineNo are matching
                        params.clear();
                        params.put("parentShipmentId", erpOrderDTO.getShParentShipmentId());
                        params.put("orderNo", erpOrderDTO.getEoOrderNo());
                        params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                        sqlString = "SELECT  st.SHIPMENT_TRANS_ID "
                                + "    FROM rm_shipment s "
                                + "LEFT JOIN (SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s left join rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo group by st.SHIPMENT_ID) sm ON sm.SHIPMENT_ID=s.SHIPMENT_ID "
                                + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID=sm.MAX_VERSION_ID "
                                + "WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo";
                        try {
                            int shipmentTransId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
                        } catch (EmptyResultDataAccessException erda) {
                            // Counldn't find a record that matches the Order no and Prime Line no so go ahead and
                            // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                            // All other details to be taken from ARTMIS
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("SUGGESTED_QTY", null);
                            params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                            params.put("CONVERSION_RATE_TO_USD", 1);
                            params.put("PARENT_SHIPMENT_ID", erpOrderDTO.getShShipmentId());
                            params.put("CREATED_BY", 1); //Default auto user in QAT
                            params.put("CREATED_DATE", curDate);
                            params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                            SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                            int newShipmentId = si.executeAndReturnKey(params).intValue();
                            params.clear();
                            params.put("SHIPMENT_ID", newShipmentId);
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("PROCUREMENT_AGENT_ID", 1); // USAID since it is fixed for the ARTMIS import
                            
                            params.put("ACTIVE", true);
                            params.put("ERP_FLAG", true);
                            params.put("LOCAL_PROCUREMENT", false);
                            
                            
                        }

                    } else {
                        // This is a new Link request coming through
                        // So make the Shipment, Active = fasle and ERPFlag = true
                        // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                        // All other details to be taken from ARTMIS

                    }
                } catch (Exception e) {

                }
            }*/

//                        // Check if contains child shipments
//                        sqlString = "SELECT COUNT(*) FROM rm_shipment_trans st LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` AND st.`VERSION_ID`=(SELECT MAX(stt.`VERSION_ID`) FROM rm_shipment_trans stt WHERE stt.`SHIPMENT_ID`=st.`SHIPMENT_ID`) "
//                                + " WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ACTIVE` AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=?";
//                        found = this.jdbcTemplate.queryForObject(hasChildShipments, Integer.class, erpOrderDTO.getShipmentId(), erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
//                        if (found > 0) {
//                            //Found Update shipment
//                            versionId = this.jdbcTemplate.queryForObject(getVersionIdOrderNoPrimeLineNo, Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
//                            this.jdbcTemplate.update(updateShipmentBasedOnOnderNoPrimeLineNo, curDate, curDate, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo(), erpOrderDTO.getShipmentId(), versionId);
//                            this.programDataDaoImpl.getNewSupplyPlanList(programId, versionId, true);
//                        } else {
//                            //Insert into shipment table
//                            params1 = new MapSqlParameterSource();
////                                    params1.addValue("qty", erpOrderDTO.getQuantity());
//                            params1.addValue("createdDate", curDate);
//                            params1.addValue("lastModifiedDate", curDate);
//                            params1.addValue("shipmentId", erpOrderDTO.getShipmentId());
//                            namedParameterJdbcTemplate.update(createNewEntryInShipmentTableDiff, params1, keyHolder1);
//                            if (keyHolder1.getKey() != null) {
//                                shipmentId = keyHolder1.getKey().intValue();
//                            }
//
//                            //Shipment Trans
//                            params1 = new MapSqlParameterSource();
//                            params1.addValue("shipmentId1", erpOrderDTO.getShipmentId());
//                            params1.addValue("shipmentId", shipmentId);
//                            params1.addValue("CURDATE", curDate);
//                            params1.addValue("CURDATE1", curDate);
//                            params1.addValue("orderNo", erpOrderDTO.getOrderNo());
//                            params1.addValue("primeLineNo", erpOrderDTO.getPrimeLineNo());
//                            namedParameterJdbcTemplate.update(createNewEntryIntoShipmentTrans, params1, keyHolder2);
//                            if (keyHolder2.getKey() != null) {
//                                shipmentTransId = keyHolder2.getKey().intValue();
//                            }
//
//                            //Shipment trans batch info
//                            this.jdbcTemplate.update(insertIntoShipmentTransBatchInfo, shipmentTransId, programId, planningUnitId, erpOrderDTO.getErpOrderId());
//                            versionId = this.jdbcTemplate.queryForObject(getMaxVersionFromShipmentTable, Integer.class, shipmentId);
//                            this.programDataDaoImpl.getNewSupplyPlanList(programId, versionId, true);
//                        }
//
//                    } else {
//                        // Not erp linked
//                        sql = "SELECT MAX(st1.`VERSION_ID`) FROM rm_shipment_trans st1 WHERE st1.`SHIPMENT_ID`=?";
//                        versionId = this.jdbcTemplate.queryForObject(sql, Integer.class, erpOrderDTO.getShipmentId());
//                        System.out.println("version id---" + versionId);
//
//                        this.jdbcTemplate.update(deactivateParentAndMarkErpLinked, erpOrderDTO.getShipmentId(), versionId);
//                        //Shipment Table
//                        params1.addValue("createdDate", curDate);
//                        params1.addValue("lastModifiedDate", curDate);
//                        params1.addValue("shipmentId", erpOrderDTO.getShipmentId());
//                        namedParameterJdbcTemplate.update(createNewEntryInShipmentTableDiff, params1, keyHolder1);
//                        if (keyHolder1.getKey() != null) {
//                            shipmentId = keyHolder1.getKey().intValue();
//                        }
//                        //Shipment Trans
//                        params1 = new MapSqlParameterSource();
//                        params1.addValue("shipmentId", shipmentId);
//                        params1.addValue("shipmentId1", erpOrderDTO.getShipmentId());
//                        params1.addValue("CURDATE", curDate);
//                        params1.addValue("CURDATE1", curDate);
//                        params1.addValue("orderNo", erpOrderDTO.getOrderNo());
//                        params1.addValue("primeLineNo", erpOrderDTO.getPrimeLineNo());
//                        namedParameterJdbcTemplate.update(createNewEntryIntoShipmentTrans, params1, keyHolder2);
//                        if (keyHolder2.getKey() != null) {
//                            shipmentTransId = keyHolder2.getKey().intValue();
//                        }
//                        //insert Into Shipment Trans Batch Info
//                        this.jdbcTemplate.update(insertIntoShipmentTransBatchInfo, shipmentTransId, programId, planningUnitId, erpOrderDTO.getErpOrderId());
//                        this.programDataDaoImpl.getNewSupplyPlanList(programId, versionId, true);
//                    }
//                } // manual tagging--------------------------------------------------------------
//                else {
//                    // Find manually tagged shipment id
//                    logger.info("Going to check manual tagging---Order No" + erpOrderDTO.getOrderNo() + " Prime line no---" + erpOrderDTO.getPrimeLineNo());
//                    sql = "SELECT m.`SHIPMENT_ID` FROM rm_manual_tagging m WHERE m.`ORDER_NO`=? AND m.`PRIME_LINE_NO`=? AND m.`ACTIVE`=1;";
//                    try {
//                        shipmentId = this.jdbcTemplate.queryForObject(sql, Integer.class, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
//                    } catch (Exception e) {
//                        shipmentId = 0;
//                    }
//                    logger.info("Manual tagging shipment id---" + shipmentId);
//                    if (shipmentId != 0) {
//                        // get program id
//                        programId = this.jdbcTemplate.queryForObject(getProgramId, Integer.class, shipmentId);
//                        //Get planning unit
//                        planningUnitId = this.jdbcTemplate.queryForObject(getPlanningUnitId, Integer.class, erpOrderDTO.getErpOrderId());
//
//                        // Insert into batch
//                        this.jdbcTemplate.update(insertIntoBatch, programId, planningUnitId, curDate, erpOrderDTO.getErpOrderId());
//                        // Is ERP linked
//                        isErpLinked = this.jdbcTemplate.queryForObject(isShipmentErpLinked, Boolean.class, shipmentId);
//                        if (isErpLinked) {
////                                    sql = "SELECT COUNT(*) FROM rm_shipment_trans st "
////                                            + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID` "
////                                            + "WHERE s.`PARENT_SHIPMENT_ID`=? AND st.`ACTIVE` AND st.`ORDER_NO`=? AND st.`PRIME_LINE_NO`=?;";
//                            found = this.jdbcTemplate.queryForObject(hasChildShipments, Integer.class, erpOrderDTO.getShipmentId(), erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo());
//                            if (found > 0) {
//                                //Found Update shipment
//                                // Update shipment trans
//                                versionId = this.jdbcTemplate.queryForObject(getVersionIdOrderNoPrimeLineNo, Integer.class, shipmentId);
//                                this.jdbcTemplate.update(updateShipmentBasedOnOnderNoPrimeLineNo, curDate, erpOrderDTO.getOrderNo(), erpOrderDTO.getPrimeLineNo(), shipmentId, versionId);
//                                this.programDataDaoImpl.getNewSupplyPlanList(programId, versionId, true);
//                            } else {
//                                //Not found-Insert shipment
//                                //Insert into shipment table
//                                params1 = new MapSqlParameterSource();
////                                    params1.addValue("qty", erpOrderDTO.getQuantity());
//                                params1.addValue("createdDate", curDate);
//                                params1.addValue("lastModifiedDate", curDate);
//                                params1.addValue("shipmentId", shipmentId);
//                                namedParameterJdbcTemplate.update(createNewEntryInShipmentTableDiff, params1, keyHolder1);
//                                int newShipId = 0;
//                                if (keyHolder1.getKey() != null) {
//                                    newShipId = keyHolder1.getKey().intValue();
//                                }
//
//                                //Shipment Trans
//                                params1 = new MapSqlParameterSource();
//                                params1.addValue("shipmentId", newShipId);
//                                params1.addValue("shipmentId1", shipmentId);
//                                params1.addValue("CURDATE", curDate);
//                                params1.addValue("CURDATE1", curDate);
//                                params1.addValue("orderNo", erpOrderDTO.getOrderNo());
//                                params1.addValue("primeLineNo", erpOrderDTO.getPrimeLineNo());
//                                namedParameterJdbcTemplate.update(createNewEntryIntoShipmentTrans, params1, keyHolder2);
//                                if (keyHolder2.getKey() != null) {
//                                    shipmentTransId = keyHolder2.getKey().intValue();
//                                }
//
//                                //Insert Into Shipment Trans Batch Info
//                                this.jdbcTemplate.update(insertIntoShipmentTransBatchInfo, shipmentTransId, programId, planningUnitId, erpOrderDTO.getErpOrderId());
//                                versionId = this.jdbcTemplate.queryForObject(getMaxVersionFromShipmentTable, Integer.class, shipmentId);
//                                this.programDataDaoImpl.getNewSupplyPlanList(programId, versionId, true);
//                            }
//                        } else {
//                            sql = "SELECT MAX(st1.`VERSION_ID`) FROM rm_shipment_trans st1 WHERE st1.`SHIPMENT_ID`=?";
//                            versionId = this.jdbcTemplate.queryForObject(sql, Integer.class, erpOrderDTO.getShipmentId());
//                            this.jdbcTemplate.update(deactivateParentAndMarkErpLinked, shipmentId, versionId);
//                            //Shipment Table
//                            params1 = new MapSqlParameterSource();
////                                    params1.addValue("qty", erpOrderDTO.getQuantity());
//                            params1.addValue("createdDate", curDate);
//                            params1.addValue("lastModifiedDate", curDate);
//                            params1.addValue("shipmentId", shipmentId);
//                            System.out.println("params1 manual tagging ---" + params1);
//                            System.out.println("createNewEntryInShipmentTable manual tagging ---" + createNewEntryInShipmentTable);
//                            namedParameterJdbcTemplate.update(createNewEntryInShipmentTableDiff, params1, keyHolder1);
//                            int newShipid = 0;
//                            if (keyHolder1.getKey() != null) {
//                                System.out.println("inside mt shipment");
//                                newShipid = keyHolder1.getKey().intValue();
//                            }
//                            System.out.println("my manual tagging shipment id---" + shipmentId);
//                            //Shipment Trans
//                            params1 = new MapSqlParameterSource();
//                            params1.addValue("shipmentId", newShipid);
//                            params1.addValue("shipmentId1", shipmentId);
//                            params1.addValue("CURDATE", curDate);
//                            params1.addValue("CURDATE1", curDate);
//                            params1.addValue("orderNo", erpOrderDTO.getOrderNo());
//                            params1.addValue("primeLineNo", erpOrderDTO.getPrimeLineNo());
//                            System.out.println("params1---------" + params1);
//                            namedParameterJdbcTemplate.update(createNewEntryIntoShipmentTrans, params1, keyHolder2);
//                            if (keyHolder2.getKey() != null) {
//                                shipmentTransId = keyHolder2.getKey().intValue();
//                            }
//                            System.out.println("manual tagging shipment trans---" + shipmentTransId);
//                            //insert Into Shipment Trans Batch Info
//                            this.jdbcTemplate.update(insertIntoShipmentTransBatchInfo, shipmentTransId, programId, planningUnitId, erpOrderDTO.getErpOrderId());
//                            versionId = this.jdbcTemplate.queryForObject(getMaxVersionFromShipmentTable, Integer.class, newShipid);
//                            this.programDataDaoImpl.getNewSupplyPlanList(programId, versionId, true);
//                        }
//                    }
//
//                }
//            } catch (Exception e) {
//                logger.info("Error occured while creating shipment---" + e);
//                e.printStackTrace();
//            }
//
//        }
//
//        logger.info(
//                "Order/Shipment file imported successfully");
//        File directory = new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH);
//
//        if (directory.isDirectory()) {
//            fXmlFile.renameTo(new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH + fXmlFile.getName()));
//            fXmlFile1.renameTo(new File(QAT_FILE_PATH + BKP_CATALOG_FILE_PATH + fXmlFile1.getName()));
//            logger.info("Order/Shipment files moved into processed folder");
//        } else {
//            subjectParam = new String[]{"Order/Shipment", "Backup directory does not exists"};
//            bodyParam = new String[]{"Order/Shipment", date, "Backup directory does not exists", "Backup directory does not exists"};
//            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), toList, ccList, subjectParam, bodyParam);
//            int emailerId = this.emailService.saveEmail(emailer);
//            emailer.setEmailerId(emailerId);
//            this.emailService.sendMail(emailer);
//            logger.error("Backup directory does not exists");
//        }
//    }
//}
//}
//    SELECT * FROM rm_shipment_trans st 
//LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=st.`SHIPMENT_ID`
//WHERE s.`PROGRAM_ID`=1 AND st.`PLANNING_UNIT_ID`=1191
//ORDER BY st.`SHIPMENT_TRANS_ID` DESC LIMIT 1
        }
    }
}
