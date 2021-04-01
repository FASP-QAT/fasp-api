/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;
import cc.altius.FASP.ARTMIS.dao.ImportArtmisDataDao;
import cc.altius.FASP.model.DTO.ErpBatchDTO;
import cc.altius.FASP.model.DTO.ErpOrderDTO;
import cc.altius.FASP.model.DTO.ErpShipmentDTO;
import cc.altius.FASP.model.DTO.rowMapper.ErpBatchDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderDTOListResultSetExtractor;
import cc.altius.utils.DateUtils;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public List<Integer> importOrderAndShipmentData(File orderFile, File shipmentFile) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
        List<Integer> programList = new LinkedList<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        logger.info("################################################################################################");
        logger.info("Starting import of " + orderFile.getName() + " and " + shipmentFile.getName());
        logger.info("################################################################################################");
        String sqlString = "DROP TEMPORARY TABLE IF EXISTS tmp_erp_order";
//        String sqlString = "DROP TABLE IF EXISTS tmp_erp_order";
        this.jdbcTemplate.execute(sqlString);
        // Create ERO oder table
        sqlString = "CREATE TEMPORARY TABLE `tmp_erp_order` ( "
                //        sqlString = "CREATE TABLE `tmp_erp_order` ( "
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
                + "  `ORDERED_QTY` BIGINT(20) DEFAULT NULL, "
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
                + "  `CHANGE_CODE` int(11) NOT NULL, "
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
                    map.put("CHANGE_CODE", dataRecordElement.getElementsByTagName("change_code").item(0).getTextContent());
                    map.put("PROGRAM_ID", (dataRecordElement.getElementsByTagName("qat_program_id").item(0).getTextContent() != null && dataRecordElement.getElementsByTagName("qat_program_id").item(0).getTextContent() != "" ? dataRecordElement.getElementsByTagName("qat_program_id").item(0).getTextContent() : null));
                    map.put("SHIPMENT_ID", (dataRecordElement.getElementsByTagName("qat_shipment_id").item(0).getTextContent() != null && dataRecordElement.getElementsByTagName("qat_shipment_id").item(0).getTextContent() != "" && !dataRecordElement.getElementsByTagName("qat_shipment_id").item(0).getTextContent().equals("-99") ? dataRecordElement.getElementsByTagName("qat_shipment_id").item(0).getTextContent() : null));
                    batchParams[x] = new MapSqlParameterSource(map);
                    x++;
                }
            }
            sqlString = "INSERT INTO tmp_erp_order (`RO_NO`, `RO_PRIME_LINE_NO`, `ORDER_NO`, `PRIME_LINE_NO`, `ORDER_TYPE_IND`, "
                    + "`ORDER_ENTRY_DATE`, `PARENT_RO`, `PARENT_ORDER_ENTRY_DATE`, `ITEM_ID`, `ORDERED_QTY`, "
                    + "`PO_RELEASED_FOR_FULFILLMENT_DATE`, `LATEST_ESTIMATED_DELIVERY_DATE`, `REQ_DELIVERY_DATE`, `REVISED_AGREED_DELIVERY_DATE`, `ITEM_SUPPLIER_NAME`, "
                    + "`UNIT_PRICE`, `STATUS_NAME`, `EXTERNAL_STATUS_STAGE`, `SHIPPING_CHARGES`, `FREIGHT_ESTIMATE`, "
                    + "`TOTAL_ACTUAL_FREIGHT_COST`, `CARRIER_SERVICE_CODE`, `RECIPIENT_NAME`, `RECIPIENT_COUNTRY`, "
                    + "`CHANGE_CODE`, `PROGRAM_ID`, `SHIPMENT_ID`) VALUES "
                    + "(:RO_NO, :RO_PRIME_LINE_NO, :ORDER_NO, :PRIME_LINE_NO, :ORDER_TYPE_IND, "
                    + ":ORDER_ENTRY_DATE, :PARENT_RO, :PARENT_ORDER_ENTRY_DATE, :ITEM_ID, :ORDERED_QTY, "
                    + ":PO_RELEASED_FOR_FULFILLMENT_DATE, :LATEST_ESTIMATED_DELIVERY_DATE, :REQ_DELIVERY_DATE, :REVISED_AGREED_DELIVERY_DATE, :ITEM_SUPPLIER_NAME, "
                    + ":UNIT_PRICE, :STATUS_NAME, :EXTERNAL_STATUS_STAGE, :SHIPPING_CHARGES, :FREIGHT_ESTIMATE, "
                    + ":TOTAL_ACTUAL_FREIGHT_COST, :CARRIER_SERVICE_CODE, :RECIPIENT_NAME, :RECIPIENT_COUNTRY, "
                    + ":CHANGE_CODE, :PROGRAM_ID, :SHIPMENT_ID)";
            SqlParameterSource[] batchSqlParams = ArrayUtils.toArray(batchParams);
            rows1 = this.namedParameterJdbcTemplate.batchUpdate(sqlString, batchSqlParams);
            logger.info("Successfully inserted into tmp_erp_order records---" + rows1.length);
        }

        sqlString = "DROP TEMPORARY TABLE IF EXISTS tmp_erp_shipment";
//        sqlString = "DROP TABLE IF EXISTS tmp_erp_shipment";
        this.jdbcTemplate.execute(sqlString);
        sqlString = "CREATE TEMPORARY TABLE `tmp_erp_shipment` ( "
                //        sqlString = "CREATE TABLE `tmp_erp_shipment` ( "
                + "  `TEMP_SHIPMENT_ID` int(11) NOT NULL AUTO_INCREMENT, "
                + "  `KN_SHIPMENT_NO` varchar(45) COLLATE utf8_bin NOT NULL, "
                + "  `ORDER_NO` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `PRIME_LINE_NO` int(11) DEFAULT NULL, "
                + "  `BATCH_NO` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ITEM_ID` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `EXPIRATION_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `SHIPPED_QUANTITY` BIGINT(20) DEFAULT NULL, "
                + "  `DELIVERED_QUANTITY` BIGINT(20) DEFAULT NULL, "
                + "  `STATUS_NAME` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `EXTERNAL_STATUS_STAGE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ACTUAL_SHIPMENT_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ACTUAL_DELIVERY_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `ARRIVAL_AT_DESTINATION_DATE` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
                + "  `STATUS` int(11) DEFAULT NULL, "
                + "  `CHANGE_CODE` int(11) NOT NULL, "
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
                    + "`ACTUAL_SHIPMENT_DATE`, `ACTUAL_DELIVERY_DATE`, `ARRIVAL_AT_DESTINATION_DATE`, `CHANGE_CODE`) "
                    + "VALUES (:KN_SHIPMENT_NO, :ORDER_NO, :PRIME_LINE_NO, :BATCH_NO, :ITEM_ID, "
                    + ":EXPIRATION_DATE, :SHIPPED_QUANTITY, :DELIVERED_QUANTITY, :STATUS_NAME, :EXTERNAL_STATUS_STAGE, "
                    + ":ACTUAL_SHIPMENT_DATE, :ACTUAL_DELIVERY_DATE, :ARRIVAL_AT_DESTINATION_DATE, :CHANGE_CODE)";
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
                    map.put("ARRIVAL_AT_DESTINATION_DATE", dataRecordElement.getElementsByTagName("arrival_at_destination_date").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("arrival_at_destination_date").item(0).getTextContent());
                    map.put("CHANGE_CODE", dataRecordElement.getElementsByTagName("change_code").item(0).getTextContent());
                    batchParams[x] = new MapSqlParameterSource(map);
                    x++;
                }
            }
            SqlParameterSource[] batchSqlParams = ArrayUtils.toArray(batchParams);
            rows1 = this.namedParameterJdbcTemplate.batchUpdate(sqlString, batchSqlParams);
            logger.info("Successfully inserted into tmp_erp_shipment records---" + rows1.length);
        }
        sqlString = "UPDATE rm_erp_order o SET o.`FLAG`=0";
        this.jdbcTemplate.update(sqlString);

        sqlString = "SELECT COUNT(*) FROM rm_erp_order;";
        logger.info("Total rows present in rm_erp_order---" + this.jdbcTemplate.queryForObject(sqlString, Integer.class));

//        sqlString = "UPDATE tmp_erp_order t "
//                + "LEFT JOIN rm_erp_order o ON t.`ORDER_NO`=o.`ORDER_NO` AND t.`PRIME_LINE_NO`=o.`PRIME_LINE_NO` "
//                + "SET "
//                + "o.`RO_NO`=t.`RO_NO`, "
//                + "o.`RO_PRIME_LINE_NO`=t.`RO_PRIME_LINE_NO`, "
//                + "o.`ORDER_TYPE`=t.`ORDER_TYPE_IND`, "
//                + "o.`CREATED_DATE`=IFNULL(CONCAT(LEFT(t.`ORDER_ENTRY_DATE`,10),' ',REPLACE(MID(t.`ORDER_ENTRY_DATE`,12,8),'.',':')),NULL), "
//                + "o.`PARENT_RO`=t.`PARENT_RO`, "
//                + "o.`PARENT_CREATED_DATE`=IFNULL(CONCAT(LEFT(t.`PARENT_ORDER_ENTRY_DATE`,10),' ',REPLACE(MID(t.`PARENT_ORDER_ENTRY_DATE`,12,8),'.',':')),NULL), "
//                + "o.`PLANNING_UNIT_SKU_CODE`=LEFT(t.`ITEM_ID`,12), "
//                + "o.`PROCUREMENT_UNIT_SKU_CODE`=IF(LENGTH(t.`ITEM_ID`)>=15,t.`ITEM_ID`,NULL), "
//                + "o.`QTY`=t.`ORDERED_QTY`, "
//                + "o.`ORDERD_DATE`=IFNULL(CONCAT(LEFT(t.`PO_RELEASED_FOR_FULFILLMENT_DATE`,10),' ',REPLACE(MID(t.`PO_RELEASED_FOR_FULFILLMENT_DATE`,12,8),'.',':')),NULL), "
//                + "o.`CURRENT_ESTIMATED_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`LATEST_ESTIMATED_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`LATEST_ESTIMATED_DELIVERY_DATE`,12,8),'.',':')),NULL), "
//                + "o.`REQ_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`REQ_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`REQ_DELIVERY_DATE`,12,8),'.',':')),NULL), "
//                + "o.`AGREED_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`REVISED_AGREED_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`REVISED_AGREED_DELIVERY_DATE`,12,8),'.',':')),NULL), "
//                + "o.`SUPPLIER_NAME`=t.`ITEM_SUPPLIER_NAME`, "
//                + "o.`PRICE`=t.`UNIT_PRICE`, "
//                + "o.`SHIPPING_COST`=COALESCE(IF(t.`TOTAL_ACTUAL_FREIGHT_COST`=0,NULL,t.`TOTAL_ACTUAL_FREIGHT_COST`),IF(t.`FREIGHT_ESTIMATE`=0,NULL,t.`FREIGHT_ESTIMATE`),IF(t.`SHIPPING_CHARGES`=0,NULL,t.`SHIPPING_CHARGES`)), "
//                + "o.`SHIP_BY`=t.`CARRIER_SERVICE_CODE`, "
//                + "o.`RECPIENT_NAME`=t.`RECIPIENT_NAME`, "
//                + "o.`RECPIENT_COUNTRY`=t.`RECIPIENT_COUNTRY`, "
//                + "o.`STATUS`=t.`EXTERNAL_STATUS_STAGE`, "
//                + "o.`LAST_MODIFIED_DATE`=:curDate, "
//                + "o.`PROGRAM_ID`=t.`PROGRAM_ID`, "
//                + "o.`SHIPMENT_ID`=t.`SHIPMENT_ID`, "
//                + "o.`FLAG`=1 "
//                + "WHERE o.ERP_ORDER_ID IS NOT NULL";
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        params.put("orderFileName", orderFile.getName());
//        int rows = this.namedParameterJdbcTemplate.update(sqlString, params);
//        logger.info("No of rows updated in rm_erp_order---" + rows);

        sqlString = "INSERT INTO rm_erp_order (`RO_NO`, `RO_PRIME_LINE_NO`, `ORDER_NO`, `PRIME_LINE_NO`, `ORDER_TYPE`, "
                + "`PARENT_RO`, `PARENT_CREATED_DATE`, `PLANNING_UNIT_SKU_CODE`, `PROCUREMENT_UNIT_SKU_CODE`, `QTY`, "
                + "`ORDERD_DATE`, `CURRENT_ESTIMATED_DELIVERY_DATE`, `REQ_DELIVERY_DATE`, `AGREED_DELIVERY_DATE`, `SUPPLIER_NAME`, "
                + "`PRICE`, `SHIPPING_COST`, `SHIP_BY`, `RECPIENT_NAME`, `RECPIENT_COUNTRY`, "
                + "`STATUS`, `CHANGE_CODE`, `PROCUREMENT_AGENT_ID`, `LAST_MODIFIED_DATE`, `FLAG`, `VERSION_ID`, `PROGRAM_ID`, `SHIPMENT_ID`, `CREATED_DATE`, `FILE_NAME`) "
                + " SELECT t.RO_NO, t.RO_PRIME_LINE_NO, t.ORDER_NO, t.PRIME_LINE_NO, t.ORDER_TYPE_IND,"
                + " t.PARENT_RO, DATE_FORMAT(t.PARENT_ORDER_ENTRY_DATE,'%Y-%m-%d %H:%i:%s'), LEFT(t.ITEM_ID, 12), IF(LENGTH(t.ITEM_ID)=15,t.ITEM_ID,NULL), t.ORDERED_QTY,"
                + " IFNULL(DATE_FORMAT(t.PO_RELEASED_FOR_FULFILLMENT_DATE,'%Y-%m-%d'),NULL), IFNULL(DATE_FORMAT(t.LATEST_ESTIMATED_DELIVERY_DATE,'%Y-%m-%d'),NULL), IFNULL(DATE_FORMAT(t.REQ_DELIVERY_DATE,'%Y-%m-%d'),NULL), IFNULL(DATE_FORMAT(t.REVISED_AGREED_DELIVERY_DATE,'%Y-%m-%d'),NULL), t.ITEM_SUPPLIER_NAME, "
                + " t.UNIT_PRICE,COALESCE(t.TOTAL_ACTUAL_FREIGHT_COST,t.SHIPPING_CHARGES,t.FREIGHT_ESTIMATE), t.CARRIER_SERVICE_CODE, t.RECIPIENT_NAME, t.RECIPIENT_COUNTRY,"
                + " t.EXTERNAL_STATUS_STAGE, t.CHANGE_CODE, 1, :curDate, 1, NULL, t.PROGRAM_ID, t.SHIPMENT_ID, DATE_FORMAT(t.ORDER_ENTRY_DATE,'%Y-%m-%d'), :orderFileName "
                + " FROM tmp_erp_order t ";
        int rows = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("No of rows inserted into rm_erp_order---" + rows);

        // Update erp shipment table
//        sqlString = "UPDATE tmp_erp_shipment t "
//                + "LEFT JOIN rm_erp_shipment s ON t.`KN_SHIPMENT_NO`=s.`KN_SHIPMENT_NO` AND t.`ORDER_NO`=s.`ORDER_NO` AND t.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` AND t.`BATCH_NO`=s.`BATCH_NO` "
//                + "SET "
//                + " s.`FLAG`=1, "
//                + " s.`LAST_MODIFIED_DATE`=:curDate, "
//                + " s.`EXPIRY_DATE`=IFNULL(LEFT(t.`EXPIRATION_DATE`,10),NULL), "
//                + " s.`PROCUREMENT_UNIT_SKU_CODE`=t.`ITEM_ID`, "
//                + " s.`SHIPPED_QTY`=t.`SHIPPED_QUANTITY`, "
//                + " s.`DELIVERED_QTY`=t.`DELIVERED_QUANTITY`, "
//                + " s.`ACTUAL_SHIPMENT_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_SHIPMENT_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_SHIPMENT_DATE`,12,8),'.',':')),NULL), "
//                + " s.`ACTUAL_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_DELIVERY_DATE`,12,8),'.',':')),NULL), "
//                + " s.`ARRIVAL_AT_DESTINATION_DATE`=IFNULL(CONCAT(LEFT(t.`ARRIVAL_AT_DESTINATION_DATE`,10),' ',REPLACE(MID(t.`ARRIVAL_AT_DESTINATION_DATE`,12,8),'.',':')),NULL), "
//                + " s.`STATUS`=t.`EXTERNAL_STATUS_STAGE` "
//                + " WHERE s.ERP_SHIPMENT_ID IS NOT NULL";
//        rows = this.namedParameterJdbcTemplate.update(sqlString, params);
//
//        logger.info("No of rows updated in  rm_erp_shipment---" + rows);
        params.put("shipmentFileName", shipmentFile.getName());
        //Insert into erp shipment table
        sqlString = "INSERT INTO rm_erp_shipment (KN_SHIPMENT_NO, ORDER_NO, PRIME_LINE_NO, BATCH_NO, "
                + "EXPIRY_DATE, PROCUREMENT_UNIT_SKU_CODE, SHIPPED_QTY, DELIVERED_QTY, ACTUAL_SHIPMENT_DATE, "
                + "ACTUAL_DELIVERY_DATE, ARRIVAL_AT_DESTINATION_DATE, STATUS, CHANGE_CODE, LAST_MODIFIED_DATE, FLAG, FILE_NAME)"
                + "SELECT t.KN_SHIPMENT_NO, t.ORDER_NO, t.PRIME_LINE_NO, t.BATCH_NO,"
                + "DATE_FORMAT(t.EXPIRATION_DATE,'%Y-%m-%d %H:%i:%s'), IF(LENGTH(t.ITEM_ID)=15,t.ITEM_ID, null), t.SHIPPED_QUANTITY, t.DELIVERED_QUANTITY, DATE_FORMAT(t.ACTUAL_SHIPMENT_DATE,'%Y-%m-%d %H:%i:%s'), "
                + "DATE_FORMAT(t.ACTUAL_DELIVERY_DATE,'%Y-%m-%d %H:%i:%s'), DATE_FORMAT(t.ARRIVAL_AT_DESTINATION_DATE,'%Y-%m-%d %H:%i:%s'), t.EXTERNAL_STATUS_STAGE, t.CHANGE_CODE, :curDate, 1, :shipmentFileName "
                + "FROM  tmp_erp_shipment t";
        rows = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("No of rows inserted into rm_erp_shipment---" + rows);

        sqlString = "UPDATE rm_erp_shipment t LEFT JOIN rm_erp_order o ON o.FILE_NAME=:orderFileName AND o.`ORDER_NO`=t.`ORDER_NO` AND o.`PRIME_LINE_NO`=t.`PRIME_LINE_NO` SET t.`ERP_ORDER_ID`=o.`ERP_ORDER_ID` WHERE t.FILE_NAME=:shipmentFileName";
        rows = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("update erp order id in erp shipment table---" + rows);

        sqlString = "SELECT eo.SUPPLIER_NAME FROM rm_erp_order eo LEFT JOIN vw_supplier s on eo.SUPPLIER_NAME=s.LABEL_EN WHERE eo.FILE_NAME=? AND s.SUPPLIER_ID IS NULL AND eo.SUPPLIER_NAME!='' group by eo.SUPPLIER_NAME";
        List<String> supplierList = this.jdbcTemplate.query(sqlString, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int i) throws SQLException {
                return rs.getString("SUPPLIER_NAME");
            }
        }, orderFile.getName());
        params.clear();
        params.put("curDate", curDate);
        params.put("supplierName", "");
        params.put("supLabelId", 0);
        rows = 0;
        for (String sup : supplierList) {
            sqlString = "INSERT INTO `ap_label` (`LABEL_EN`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`, `SOURCE_ID`) VALUES (:supplierName, 1, :curDate, 1, :curDate, 33)"; // Supplier
            params.replace("supplierName", sup);
            this.namedParameterJdbcTemplate.update(sqlString, params);
            int supplierId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            params.replace("supLabelId", supplierId);
            sqlString = "INSERT INTO `rm_supplier` (`REALM_ID`, `LABEL_ID`, `ACTIVE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (1, :supLabelId, 1, 1, :curDate, 1, :curDate)";
            this.namedParameterJdbcTemplate.update(sqlString, params);
            rows++;
        }
        logger.info("Additional Suppliers created " + rows);

        // ##############################################################
        // Completed till here
        // ##############################################################
//            Map<String, Object> params = new HashMap<>();
        sqlString = "SELECT  "
                + "    eo.ERP_ORDER_ID, eo.RO_NO, eo.RO_PRIME_LINE_NO, eo.ORDER_NO, eo.PRIME_LINE_NO , "
                + "    eo.ORDER_TYPE, eo.CREATED_DATE, eo.PARENT_RO, eo.PARENT_CREATED_DATE, eo.PLANNING_UNIT_SKU_CODE,  "
                + "    eo.PROCUREMENT_UNIT_SKU_CODE, eo.QTY, eo.ORDERD_DATE, eo.CURRENT_ESTIMATED_DELIVERY_DATE, eo.REQ_DELIVERY_DATE,  "
                + "    eo.AGREED_DELIVERY_DATE, eo.SUPPLIER_NAME, eo.PRICE, eo.SHIPPING_COST, eo.SHIP_BY,  "
                + "    eo.RECPIENT_NAME, eo.RECPIENT_COUNTRY, eo.`STATUS`, eo.`CHANGE_CODE`, ssm.SHIPMENT_STATUS_ID, eo.MANUAL_TAGGING, eo.CONVERSION_FACTOR, "
                + "    MIN(es.ACTUAL_DELIVERY_DATE) `ACTUAL_DELIVERY_DATE`, MIN(es.ACTUAL_SHIPMENT_DATE) `ACTUAL_SHIPMENT_DATE`, MIN(es.ARRIVAL_AT_DESTINATION_DATE) `ARRIVAL_AT_DESTINATION_DATE`, "
                + "    es.BATCH_NO, IF(es.DELIVERED_QTY !=0,COALESCE(es.DELIVERED_QTY, es.SHIPPED_QTY),es.SHIPPED_QTY)  `BATCH_QTY`, es.`EXPIRY_DATE`, "
                + "    st.PLANNING_UNIT_ID, papu2.PROCUREMENT_UNIT_ID, pu2.SUPPLIER_ID, ppu.SHELF_LIFE, "
                + "    sh.SHIPMENT_ID, sh.PROGRAM_ID, sh.PARENT_SHIPMENT_ID, "
                + "    st.SHIPMENT_TRANS_ID, st.VERSION_ID, st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.BUDGET_ID, st.ACTIVE, st.ERP_FLAG, st.ACCOUNT_FLAG, st.DATA_SOURCE_ID,eo.CONVERSION_FACTOR "
                + "FROM ( "
                + "    SELECT  "
                + "        e.ERP_ORDER_ID, e.RO_NO, e.RO_PRIME_LINE_NO, e.ORDER_NO, e.PRIME_LINE_NO , "
                + "        e.ORDER_TYPE, e.CREATED_DATE, e.PARENT_RO, e.PARENT_CREATED_DATE, e.PLANNING_UNIT_SKU_CODE,  "
                + "        e.PROCUREMENT_UNIT_SKU_CODE, e.QTY, e.ORDERD_DATE, e.CURRENT_ESTIMATED_DELIVERY_DATE, e.REQ_DELIVERY_DATE,  "
                + "        e.AGREED_DELIVERY_DATE, e.SUPPLIER_NAME, e.PRICE, e.SHIPPING_COST, e.SHIP_BY, IF(mt.MANUAL_TAGGING_ID IS not null, true, false) `MANUAL_TAGGING`, IF(mt.MANUAL_TAGGING_ID IS not null, mt.CONVERSION_FACTOR, 1) `CONVERSION_FACTOR`, "
                + "        e.RECPIENT_NAME, e.RECPIENT_COUNTRY, e.STATUS, e.CHANGE_CODE, COALESCE(e.PROGRAM_ID, mts.PROGRAM_ID) `PROGRAM_ID`, COALESCE(e.SHIPMENT_ID, mt.SHIPMENT_ID) `SHIPMENT_ID` "
                + "    FROM rm_erp_order e   "
                + "    LEFT JOIN rm_shipment s ON e.SHIPMENT_ID=s.SHIPMENT_ID  "
                + "    LEFT JOIN rm_manual_tagging mt ON e.ORDER_NO=mt.ORDER_NO AND e.PRIME_LINE_NO=mt.PRIME_LINE_NO and mt.ACTIVE "
                + "    LEFT JOIN rm_shipment mts ON mt.SHIPMENT_ID=mts.SHIPMENT_ID   "
                + "    WHERE e.`FILE_NAME`=:orderFileName AND (e.SHIPMENT_ID IS NOT NULL OR mt.MANUAL_TAGGING_ID IS NOT NULL) AND (s.SHIPMENT_ID IS NOT null OR mts.SHIPMENT_ID IS NOT NULL)  "
                + ") eo "
                + " LEFT JOIN (SELECT sx1.SHIPMENT_ID, sx1.PROGRAM_ID, sx1.PARENT_SHIPMENT_ID, MAX(st1.VERSION_ID) MAX_VERSION_ID FROM rm_shipment sx1 LEFT JOIN rm_shipment_trans st1 ON sx1.SHIPMENT_ID=st1.SHIPMENT_ID GROUP BY st1.SHIPMENT_ID) sh ON sh.SHIPMENT_ID=eo.SHIPMENT_ID AND sh.PROGRAM_ID=eo.PROGRAM_ID "
                + " LEFT JOIN rm_shipment_trans st ON st.SHIPMENT_ID=sh.SHIPMENT_ID AND st.VERSION_ID=sh.MAX_VERSION_ID "
                + " LEFT JOIN vw_planning_unit pu ON st.`PLANNING_UNIT_ID`=pu.`PLANNING_UNIT_ID` "
                + " LEFT JOIN rm_procurement_agent_planning_unit papu ON st.`PLANNING_UNIT_ID`=papu.`PLANNING_UNIT_ID` AND papu.`PROCUREMENT_AGENT_ID`=1  "
                + " LEFT JOIN rm_procurement_agent_procurement_unit papu2 ON eo.PROCUREMENT_UNIT_SKU_CODE=LEFT(papu2.SKU_CODE,15) AND papu2.PROCUREMENT_AGENT_ID=1 "
                + " LEFT JOIN rm_procurement_unit pu2 ON papu2.PROCUREMENT_UNIT_ID=pu2.PROCUREMENT_UNIT_ID "
                + " LEFT JOIN rm_erp_shipment es ON es.ERP_ORDER_ID=eo.ERP_ORDER_ID "
                + " LEFT JOIN rm_shipment_status_mapping ssm ON eo.`STATUS`=ssm.EXTERNAL_STATUS_STAGE "
                + " LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=sh.PROGRAM_ID AND ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + " GROUP BY eo.`ERP_ORDER_ID`; ";
        params.clear();
        params.put("orderFileName", orderFile.getName());
        List<ErpOrderDTO> erpOrderDTOList = this.namedParameterJdbcTemplate.query(sqlString, params, new ErpOrderDTOListResultSetExtractor());
        logger.info("");
        logger.info("");
        logger.info("erpOrderDTO---" + erpOrderDTOList.size());

        for (ErpOrderDTO erpOrderDTO : erpOrderDTOList) {
            try {
                // Shipment id found in file
                logger.info("-----------------------------------------------------------");
                logger.info("ERP Order - " + erpOrderDTO);
                logger.info("Order no - " + erpOrderDTO.getEoOrderNo());
                logger.info("Prime line no - " + erpOrderDTO.getEoPrimeLineNo());
                logger.info("Active - " + erpOrderDTO.getShActive());
                logger.info("ERP Flag - " + erpOrderDTO.getShErpFlag());
                logger.info("ParentShipmentId - " + erpOrderDTO.getShParentShipmentId());
                logger.info("Shipment Id - " + erpOrderDTO.getShShipmentId());
                logger.info("Change code - " + erpOrderDTO.getEoChangeCode());
                logger.info("ManualTagging - " + erpOrderDTO.isManualTagging());
                logger.info("Program Id - " + erpOrderDTO.getShProgramId());
                logger.info("Shipment id - " + erpOrderDTO.getShShipmentId());
                if (erpOrderDTO.getShProgramId() == 0 || erpOrderDTO.getShShipmentId() == 0) {
                    System.out.println("---------------1--------------");
                    logger.info("Either Program Id is 0 or Shipment Id is 0 so skipping this record");
                } else if (erpOrderDTO.getEoChangeCode() == 2) {
                    System.out.println("---------------2--------------");
                    // This is the Delete code so go ahead and delete this Order
                    logger.info("Change code is 2 so therefore delete this line item where shipmentId=" + erpOrderDTO.getShShipmentId());
                    sqlString = "UPDATE rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID SET st.ACTIVE=0, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate WHERE s.PARENT_SHIPMENT_ID=:shipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ACTIVE AND st.ERP_FLAG";
                    params.clear();
//                    params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                    params.put("shipmentId", erpOrderDTO.getShShipmentId());
                    params.put("orderNo", erpOrderDTO.getEoOrderNo());
                    params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                    params.put("curDate", curDate);
                    rows = this.namedParameterJdbcTemplate.update(sqlString, params);
                    logger.info(rows + " rows updated");

                } else if (erpOrderDTO.isShErpFlag() && erpOrderDTO.getShParentShipmentId() == null) {
                    System.out.println("---------------3--------------");
                    // The ERP Flag is true and the Parent Shipment Id is null
                    logger.info("ERP Flag is true and Parent Shipment Id is null");
                    // Find all Shipments whose Parent Shipment Id is :parentShipmentId and :orderNo and :primeLineNo are matching
                    params.clear();
                    params.put("parentShipmentId", erpOrderDTO.getShShipmentId());
                    params.put("orderNo", erpOrderDTO.getEoOrderNo());
                    params.put("primeLineNo", erpOrderDTO.getEoPrimeLineNo());
                    sqlString = "SELECT  st.SHIPMENT_TRANS_ID "
                            + "    FROM rm_shipment s "
                            + "LEFT JOIN (SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s left join rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo group by st.SHIPMENT_ID) sm ON sm.SHIPMENT_ID=s.SHIPMENT_ID "
                            + "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID=sm.MAX_VERSION_ID "
                            + "WHERE s.PARENT_SHIPMENT_ID=:parentShipmentId AND st.ORDER_NO=:orderNo AND st.PRIME_LINE_NO=:primeLineNo AND st.ERP_FLAG=1 AND st.ACTIVE";
                    try {
                        logger.info("Trying to see if the ShipmentTrans exists with the same orderNo, primeLineNo and parentShipmentId");
                        int shipmentTransId = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
                        logger.info("ShipmentTransId " + shipmentTransId + " found so going to update that with latest information");
                        // TODO shipment found therefore update it with all the information
                        sqlString = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID "
                                + "SET  "
                                + "    st.EXPECTED_DELIVERY_DATE=:expectedDeliveryDate, st.FREIGHT_COST=:freightCost, st.PRODUCT_COST=:productCost, "
                                + "    st.RATE=:price, st.SHIPMENT_MODE=:shipBy, st.SHIPMENT_QTY=:qty, "
                                + "    st.SHIPMENT_STATUS_ID=:shipmentStatusId, st.SUPPLIER_ID=:supplierId, st.PLANNED_DATE=:plannedDate, "
                                + "    st.SUBMITTED_DATE=:submittedDate, st.APPROVED_DATE=:approvedDate, st.SHIPPED_DATE=:shippedDate, "
                                + "    st.ARRIVED_DATE=:arrivedDate, st.RECEIVED_DATE=:receivedDate, st.LAST_MODIFIED_BY=1, "
                                + "    st.LAST_MODIFIED_DATE=:curDate, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate, st.NOTES=:notes "
                                + "WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                        params.clear();
//                        params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                        params.put("shipmentTransId", shipmentTransId);
                        params.put("expectedDeliveryDate", erpOrderDTO.getExpectedDeliveryDate());
                        params.put("freightCost", erpOrderDTO.getEoShippingCost());
                        params.put("productCost", erpOrderDTO.getEoPrice() * erpOrderDTO.getEoQty());
                        params.put("price", erpOrderDTO.getEoPrice());
                        params.put("shipBy", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                        params.put("qty", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor()) : erpOrderDTO.getEoQty()));
                        params.put("shipmentStatusId", erpOrderDTO.getEoShipmentStatusId());
                        params.put("supplierId", erpOrderDTO.getEoSupplierId());
                        params.put("plannedDate", erpOrderDTO.getEoCreatedDate());
                        params.put("submittedDate", erpOrderDTO.getEoCreatedDate());
                        params.put("approvedDate", erpOrderDTO.getEoOrderedDate());
                        params.put("shippedDate", erpOrderDTO.getEoActualShipmentDate());
                        params.put("arrivedDate", erpOrderDTO.getEoArrivalAtDestinationDate());
                        params.put("receivedDate", erpOrderDTO.getEoActualDeliveryDate());
                        params.put("curDate", curDate);
                        params.put("notes", "Auto updated from ERP Data");
                        this.namedParameterJdbcTemplate.update(sqlString, params);
                        logger.info("Updated the already existing Shipment Trans record (" + shipmentTransId + ") with new data");
                        logger.info("Now need to update the Batch information");
                        sqlString = "SELECT bi.BATCH_ID, stbi.SHIPMENT_TRANS_BATCH_INFO_ID, bi.BATCH_NO, bi.EXPIRY_DATE, stbi.BATCH_SHIPMENT_QTY FROM rm_shipment_trans_batch_info stbi LEFT JOIN rm_batch_info bi ON stbi.BATCH_ID=bi.BATCH_ID where stbi.SHIPMENT_TRANS_ID=:shipmentTransId group by stbi.BATCH_ID";
                        params.clear();
                        params.put("shipmentTransId", shipmentTransId);
                        List<ErpBatchDTO> erpBatchList = this.namedParameterJdbcTemplate.query(sqlString, params, new ErpBatchDTORowMapper());
                        if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                            logger.info("Some batch information exists so need to check if it matches with what was already created");
                            for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                if (es.isAutoGenerated()) {
                                    // This is an autogenerated batch therefore cannot match with Batch no, try to match with Qty and Expiry Date
                                    boolean found = false;
                                    for (ErpBatchDTO eb : erpBatchList) {
                                        if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                            // match found so no need to do anything
                                            eb.setStatus(0); // Leave alone
                                            es.setStatus(0); // Leave alone
                                            found = true;
//                                            System.out.println("---------Batch 1---------------");
                                            break;
                                        }
                                    }
                                    if (found == false) {
                                        es.setStatus(2); // Insert
//                                        System.out.println("---------Batch 2---------------");
                                    }
                                } else {
                                    // This is not an autogenerated batch which means that we can match it on BatchNo
                                    ErpBatchDTO tempB = new ErpBatchDTO();
                                    tempB.setBatchNo(es.getBatchNo());
                                    int index = erpBatchList.indexOf(tempB);
                                    if (index == -1) {
//                                        System.out.println("---------Batch 3---------------");
                                        // Batch not found
                                        // therefore need to insert 
                                        es.setStatus(2); // Insert
                                    } else {
                                        // Batch found now check for Expiry date and Qty
                                        ErpBatchDTO eb = erpBatchList.get(index);
//                                        System.out.println("eb---"+eb);
//                                        System.out.println("eb.getExpiryDate()---" + eb.getExpiryDate());
//                                        System.out.println("es.getExpiryDate()---" + es.getExpiryDate());
//                                        System.out.println("eb.getQty()---" + eb.getQty());
//                                        System.out.println("es.getBatchQty()---" + es.getBatchQty());
                                        if (DateUtils.compareDate(eb.getExpiryDate(), es.getExpiryDate()) == 0 && eb.getQty() == es.getBatchQty()) {
                                            // match found so no nneed to do anything
                                            eb.setStatus(0); // Leave alone
                                            es.setStatus(0); // Leave alone
//                                            System.out.println("---------Batch 4---------------");
                                        } else {
                                            es.setStatus(1); // Update
                                            eb.setStatus(1); // Update
                                            es.setExistingBatchId(eb.getBatchId());
                                            es.setExistingShipmentTransBatchInfoId(eb.getShipmentTransBatchInfoId());
//                                            System.out.println("---------Batch 5---------------");
                                        }
                                    }
                                }
                                logger.info("Looping through Batch no: " + es.getBatchNo() + " Qty:" + es.getBatchQty());
//                                System.out.println("-swicth case batch status---" + es.getStatus());
                                switch (es.getStatus()) {
                                    case 0: // Do nothing
//                                        System.out.println("---------Batch case 1---------");
                                        logger.info("This Batch matched with what was already there so do nothing");
                                        break;
                                    case 1: // update
                                        logger.info("Need to update this Batch");
                                        sqlString = "UPDATE rm_batch_info bi SET bi.EXPIRY_DATE=:expiryDate WHERE bi.BATCH_ID=:batchId";
                                        params.clear();
                                        params.put("expiryDate", (es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                        params.put("batchId", es.getExistingBatchId());
                                        this.namedParameterJdbcTemplate.update(sqlString, params);
                                        sqlString = "UPDATE rm_shipment_trans_batch_info stbi SET stbi.SHIPMENT_QTY=:qty WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                        params.clear();
                                        params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                        params.put("qty", es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty());
                                        this.namedParameterJdbcTemplate.update(sqlString, params);
//                                        System.out.println("---------Batch case 2---------");
                                        break;
                                    case -1: // Delete
                                        logger.info("Need to delete this Batch");
                                        sqlString = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                        params.clear();
                                        params.put("shipmentTransBatchInfoId", es.getExistingShipmentTransBatchInfoId());
                                        this.namedParameterJdbcTemplate.update(sqlString, params);
//                                        System.out.println("---------Batch case 3---------");
                                    case 2: // Insert
                                        try {
//                                            System.out.println("---------Batch case 4 start---------");
                                            logger.info("Need to insert this Batch");
                                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
//                                            System.out.println("---------Batch case 4 start 1---------");
                                            params.clear();
//                                            System.out.println("---------Batch case 4 start 2---------");
                                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
//                                            System.out.println("---------Batch case 4 start 3---------");
                                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
//                                            System.out.println("---------Batch case 4 start 4---------");
                                            params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
//                                            System.out.println("---------Batch case 4 start 5---------");
                                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
//                                            System.out.println("---------Batch case 4 start 6---------");
                                            params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
//                                            System.out.println("---------Batch case 4 start 7---------");
                                            params.put("AUTO_GENERATED", es.isAutoGenerated());
//                                            System.out.println("---------Batch case 4 start 8---------");
                                            int batchId = sib.executeAndReturnKey(params).intValue();
//                                            System.out.println("---------Batch case 4 start 9---------");
                                            logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
//                                            System.out.println("---------Batch case 4 start 10---------");
                                            params.clear();
//                                            System.out.println("---------Batch case 4 start 11---------");
                                            sib = null;
//                                            System.out.println("---------Batch case 4 start 12---------");
                                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
//                                            System.out.println("---------Batch case 4 start 13---------");
                                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
//                                            System.out.println("---------Batch case 4 start 14---------");
                                            params.put("BATCH_ID", batchId);
//                                            System.out.println("---------Batch case 4 start 15---------" + erpOrderDTO.getEoQty() + " qty2-----" + es.getBatchQty());
//                                            System.out.println("erp order dto---" + erpOrderDTO);
                                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
//                                            System.out.println("---------Batch case 4 start 16---------");
                                            sib.execute(params);
//                                            System.out.println("---------Batch case 4 start 17---------");
//                                            System.out.println("---------Batch case 4 end---------");

                                            logger.info("Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                            logger.info("Checking if any old batches need to be deleted");
                            for (ErpBatchDTO eb : erpBatchList) {
                                if (eb.getStatus() == -1) {
                                    logger.info("Batch no: " + eb.getBatchNo() + " Qty:" + eb.getQty() + " is going to be deleted");
                                    sqlString = "DELETE stbi.* FROM rm_shipment_trans_batch_info stbi WHERE stbi.SHIPMENT_TRANS_BATCH_INFO_ID=:shipmentTransBatchInfoId";
                                    params.clear();
                                    params.put("shipmentTransBatchInfoId", eb.getShipmentTransBatchInfoId());
                                    this.namedParameterJdbcTemplate.update(sqlString, params);
                                    System.out.println("---------Batch case 5---------");
                                }
                            }
                        }
                    } catch (EmptyResultDataAccessException erda) {
                        // Counldn't find a record that matches the Order no and Prime Line no so go ahead and
                        logger.info("Couldn't find a Shipment Trans so this is a new record going ahead with creation");
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
                        params.put("LAST_MODIFIED_BY", 1); //Default auto user in QAT
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                        int newShipmentId = si.executeAndReturnKey(params).intValue();
                        logger.info("Shipment Id " + newShipmentId + " created");
                        SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                        params.clear();
                        params.put("SHIPMENT_ID", newShipmentId);
                        params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                        params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                        params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                        params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                        params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                        params.put("PROCUREMENT_UNIT_ID", erpOrderDTO.getEoProcurementUnitId());
                        params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                        params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor()) : erpOrderDTO.getEoQty()));
                        params.put("RATE", erpOrderDTO.getEoPrice());
                        params.put("PRODUCT_COST", erpOrderDTO.getEoQty() * erpOrderDTO.getEoPrice());
                        params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                        params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                        params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                        params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                        params.put("SHIPPED_DATE", erpOrderDTO.getEoActualShipmentDate());
                        params.put("ARRIVED_DATE", erpOrderDTO.getEoArrivalAtDestinationDate());
                        params.put("RECEIVED_DATE", erpOrderDTO.getEoActualDeliveryDate());
                        params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                        params.put("NOTES", "Auto created from ERP data");
                        params.put("ERP_FLAG", 1);
                        params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                        params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                        params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                        params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                        params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                        params.put("LAST_MODIFIED_BY", 1); // Default user
                        params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                        params.put("LAST_MODIFIED_DATE", curDate);
                        params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                        params.put("ACTIVE", true);
                        int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                        logger.info("Shipment Trans Id " + shipmentTransId + " created");
                        if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                            logger.info("Some batch information exists so going to create Batches");
                            for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                                // Insert into Batch info for each record
                                SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                                params.clear();
                                params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                                params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                                params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                                params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                                params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                                params.put("AUTO_GENERATED", es.isAutoGenerated());
                                int batchId = sib.executeAndReturnKey(params).intValue();
                                logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                                params.clear();
                                sib = null;
                                sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                                params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                                params.put("BATCH_ID", batchId);
                                params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                                sib.execute(params);
                                logger.info("Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                            }
                        } else {
                            // Insert into Batch info for each record
                            logger.info("No Batch information exists so creating one automatically");
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                            params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                            params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                            params.put("AUTO_GENERATED", true);
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                            sib.execute(params);
                            logger.info("Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                        }
                    }

                } else {
                    System.out.println("---------------4--------------");
                    // This is a new Link request coming through
                    // So make the Shipment, Active = fasle and ERPFlag = true
                    logger.info("This is a first time linking attempt");
                    // Create a new Shipment with Parent Shipment Id = :shipmentId and OrderNo=:orderNo and PrimeLineNo=:primeLineNo
                    // All other details to be taken from ARTMIS + Current Shipment
//                    sqlString = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID SET st.`PLANNING_UNIT_ID`=:planningUnitId,st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                    sqlString = "UPDATE rm_shipment_trans st LEFT JOIN rm_shipment s ON st.SHIPMENT_ID=s.SHIPMENT_ID SET st.ERP_FLAG=1, st.ACTIVE=0, s.LAST_MODIFIED_BY=1, s.LAST_MODIFIED_DATE=:curDate, st.LAST_MODIFIED_BY=1, st.LAST_MODIFIED_DATE=:curDate WHERE st.SHIPMENT_TRANS_ID=:shipmentTransId";
                    params.clear();
//                    params.put("planningUnitId", erpOrderDTO.getEoPlanningUnitId());
                    params.put("curDate", curDate);
                    params.put("shipmentTransId", erpOrderDTO.getShShipmentTransId());
                    this.namedParameterJdbcTemplate.update(sqlString, params);
                    logger.info("Existing Shipment has been marked as ERP_FLAG=true and ACTIVE=false");
                    params.clear();
                    params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                    params.put("SUGGESTED_QTY", null);
                    params.put("CURRENCY_ID", 1); // USD as default from ARTMIS
                    params.put("CONVERSION_RATE_TO_USD", 1);
                    params.put("PARENT_SHIPMENT_ID", erpOrderDTO.getShShipmentId());
                    params.put("CREATED_BY", 1); //Default auto user in QAT
                    params.put("CREATED_DATE", curDate);
                    params.put("LAST_MODIFIED_BY", 1); //Default auto user in QAT
                    params.put("LAST_MODIFIED_DATE", curDate);
                    params.put("MAX_VERSION_ID", erpOrderDTO.getShVersionId()); // Same as the Current Version that is already present
                    SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment").usingGeneratedKeyColumns("SHIPMENT_ID");
                    int newShipmentId = si.executeAndReturnKey(params).intValue();
                    logger.info("Shipment Id " + newShipmentId + " created");
                    SimpleJdbcInsert sit = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans").usingGeneratedKeyColumns("SHIPMENT_TRANS_ID");
                    params.clear();
                    params.put("SHIPMENT_ID", newShipmentId);
                    params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                    params.put("PROCUREMENT_AGENT_ID", erpOrderDTO.getShProcurementAgentId());
                    params.put("FUNDING_SOURCE_ID", erpOrderDTO.getShFundingSourceId());
                    params.put("BUDGET_ID", erpOrderDTO.getShBudgetId());
                    params.put("EXPECTED_DELIVERY_DATE", erpOrderDTO.getExpectedDeliveryDate());
                    params.put("PROCUREMENT_UNIT_ID", erpOrderDTO.getEoProcurementUnitId());
                    params.put("SUPPLIER_ID", erpOrderDTO.getEoSupplierId());
                    params.put("SHIPMENT_QTY", (erpOrderDTO.getConversionFactor() != 0 && erpOrderDTO.getConversionFactor() != 0.0 ? (erpOrderDTO.getEoQty() * erpOrderDTO.getConversionFactor()) : erpOrderDTO.getEoQty()));
                    params.put("RATE", erpOrderDTO.getEoPrice());
                    params.put("PRODUCT_COST", erpOrderDTO.getEoQty() * erpOrderDTO.getEoPrice());
                    params.put("SHIPMENT_MODE", (erpOrderDTO.getEoShipBy().equals("Land") || erpOrderDTO.getEoShipBy().equals("Ship") ? "Sea" : erpOrderDTO.getEoShipBy().equals("Air") ? "Air" : "Sea"));
                    params.put("FREIGHT_COST", erpOrderDTO.getEoShippingCost());
                    params.put("PLANNED_DATE", erpOrderDTO.getEoCreatedDate());
                    params.put("SUBMITTED_DATE", erpOrderDTO.getEoCreatedDate());
                    params.put("APPROVED_DATE", erpOrderDTO.getEoOrderedDate());
                    params.put("SHIPPED_DATE", erpOrderDTO.getEoActualShipmentDate());
                    params.put("ARRIVED_DATE", erpOrderDTO.getEoArrivalAtDestinationDate());
                    params.put("RECEIVED_DATE", erpOrderDTO.getEoActualDeliveryDate());
                    params.put("SHIPMENT_STATUS_ID", erpOrderDTO.getEoShipmentStatusId());
                    params.put("NOTES", "Auto created from ERP data");
                    params.put("ERP_FLAG", 1);
                    params.put("ORDER_NO", erpOrderDTO.getEoOrderNo());
                    params.put("PRIME_LINE_NO", erpOrderDTO.getEoPrimeLineNo());
                    params.put("ACCOUNT_FLAG", erpOrderDTO.getShAccountFlag());
                    params.put("EMERGENCY_ORDER", false);   // Cannot determine 
                    params.put("LOCAL_PROCUREMENT", false); // Cannot determine
                    params.put("LAST_MODIFIED_BY", 1); // Default user
                    params.put("DATA_SOURCE_ID", erpOrderDTO.getShDataSourceId());
                    params.put("LAST_MODIFIED_DATE", curDate);
                    params.put("VERSION_ID", erpOrderDTO.getShVersionId());
                    params.put("ACTIVE", true);
                    int shipmentTransId = sit.executeAndReturnKey(params).intValue();
                    logger.info("Shipment Trans Id " + shipmentTransId + " created");
                    if (!erpOrderDTO.getEoShipmentList().isEmpty()) {
                        logger.info("Some batch information exists so going to create Batches");
                        for (ErpShipmentDTO es : erpOrderDTO.getEoShipmentList()) {
                            // Insert into Batch info for each record
                            SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                            params.clear();
                            params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                            params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                            params.put("BATCH_NO", (es.isAutoGenerated() ? erpOrderDTO.getAutoGeneratedBatchNo() : es.getBatchNo()));
                            params.put("EXPIRY_DATE", (es.isAutoGenerated() || es.getExpiryDate() == null ? erpOrderDTO.getCalculatedExpiryDate() : es.getExpiryDate()));
                            params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                            params.put("AUTO_GENERATED", es.isAutoGenerated());
                            int batchId = sib.executeAndReturnKey(params).intValue();
                            logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                            params.clear();
                            sib = null;
                            sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                            params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                            params.put("BATCH_ID", batchId);
                            params.put("BATCH_SHIPMENT_QTY", (es.isAutoGenerated() ? erpOrderDTO.getEoQty() : es.getBatchQty()));
                            sib.execute(params);
                            logger.info("Pushed into shipmentBatchTrans with Qty " + es.getBatchQty());
                        }
                    } else {
                        // Insert into Batch info for each record
                        logger.info("No Batch information exists so creating one automatically");
                        SimpleJdbcInsert sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_batch_info").usingGeneratedKeyColumns("BATCH_ID");
                        params.clear();
                        params.put("PROGRAM_ID", erpOrderDTO.getShProgramId());
                        params.put("PLANNING_UNIT_ID", erpOrderDTO.getEoPlanningUnitId());
                        params.put("BATCH_NO", erpOrderDTO.getAutoGeneratedBatchNo());
                        params.put("EXPIRY_DATE", erpOrderDTO.getCalculatedExpiryDate());
                        params.put("CREATED_DATE", (erpOrderDTO.getEoActualDeliveryDate() == null ? erpOrderDTO.getExpectedDeliveryDate() : erpOrderDTO.getEoActualDeliveryDate()));
                        params.put("AUTO_GENERATED", true);
                        int batchId = sib.executeAndReturnKey(params).intValue();
                        logger.info("Batch " + params.get("BATCH_NO") + " created with Exp dt " + params.get("EXPIRY_DATE"));
                        params.clear();
                        sib = null;
                        sib = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_shipment_trans_batch_info");
                        params.put("SHIPMENT_TRANS_ID", shipmentTransId);
                        params.put("BATCH_ID", batchId);
                        params.put("BATCH_SHIPMENT_QTY", erpOrderDTO.getEoQty());
                        sib.execute(params);
                        logger.info("Pushed into shipmentBatchTrans with Qty " + erpOrderDTO.getEoQty());
                    }
                }
                System.out.println("erpOrderDTO.getShProgramId()---" + erpOrderDTO.getShProgramId());
                System.out.println("programList----" + programList);
                if (programList.indexOf(erpOrderDTO.getShProgramId()) == -1) {
                    programList.add(erpOrderDTO.getShProgramId());
                }
            } catch (Exception e) {
                logger.info("Error occurred while trying to import Shipment ", e);
            }
        }
        logger.info("--------------------------------------------------------------------------------------");
        return programList;
    }
}
