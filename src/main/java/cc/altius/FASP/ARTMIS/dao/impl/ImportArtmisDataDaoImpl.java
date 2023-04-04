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
import cc.altius.FASP.service.ProgramService;
import cc.altius.utils.DateUtils;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    @Autowired
    private ProgramService programService;

    @Override
    @Transactional
    public List<Integer> importOrderAndShipmentData(File orderFile, File shipmentFile) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
        List<Integer> programList = new LinkedList<>();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
                    + ":CHANGE_CODE, null, null)";
            SqlParameterSource[] batchSqlParams = ArrayUtils.toArray(batchParams);
            rows1 = this.namedParameterJdbcTemplate.batchUpdate(sqlString, batchSqlParams);
            int rows1Cnt = 0;
            for (int i : rows1) {
                rows1Cnt += i;
            }
            logger.info("Successfully inserted into tmp_erp_order records---" + rows1Cnt);
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
                    map.put("SHIPPED_QUANTITY", dataRecordElement.getElementsByTagName("shipped_quantity").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("shipped_quantity").item(0).getTextContent());
                    map.put("DELIVERED_QUANTITY", dataRecordElement.getElementsByTagName("delivered_quantity").item(0).getTextContent().isBlank() ? null : dataRecordElement.getElementsByTagName("delivered_quantity").item(0).getTextContent());
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
            int rows1Cnt = 0;
            for (int i : rows1) {
                rows1Cnt += i;
            }
            logger.info("Successfully inserted into tmp_erp_shipment records---" + rows1Cnt);
        }
        sqlString = "UPDATE rm_erp_order o SET o.`FLAG`=0";
        this.jdbcTemplate.update(sqlString);

        sqlString = "SELECT COUNT(*) FROM rm_erp_order;";
        logger.info("Total rows present in rm_erp_order---" + this.jdbcTemplate.queryForObject(sqlString, Integer.class));

        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        params.put("orderFileName", orderFile.getName());

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

        sqlString = "SELECT COUNT(*) FROM rm_erp_order WHERE FLAG=1;";
        logger.info("Total changed rows present in rm_erp_order---" + this.jdbcTemplate.queryForObject(sqlString, Integer.class));

        sqlString = "UPDATE rm_erp_shipment o SET o.`FLAG`=0";
        this.jdbcTemplate.update(sqlString);
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

        sqlString = "SELECT COUNT(*) FROM rm_erp_shipment WHERE FLAG=1;";
        logger.info("Total changed rows present in rm_erp_shipment---" + this.jdbcTemplate.queryForObject(sqlString, Integer.class));

        sqlString = "UPDATE rm_erp_order eo LEFT JOIN ( "
                + "SELECT e1.ORDER_NO, e1.PRIME_LINE_NO, MAX(eo2.FILE_NAME) `FILE_NAME` FROM ( "
                + "    SELECT e.ORDER_NO, e.PRIME_LINE_NO FROM "
                + "        ( "
                + "        SELECT eo.ORDER_NO, eo.PRIME_LINE_NO FROM rm_erp_order eo WHERE eo.FLAG "
                + "        UNION "
                + "        SELECT es.ORDER_NO, es.PRIME_LINE_NO FROM rm_erp_shipment es WHERE es.FLAG "
                + "    ) AS e GROUP BY e.ORDER_NO, e.PRIME_LINE_NO "
                + ") AS e1 "
                + "LEFT JOIN rm_erp_order eo1 ON e1.ORDER_NO=eo1.ORDER_NO AND e1.PRIME_LINE_NO=eo1.PRIME_LINE_NO AND eo1.FILE_NAME=:orderFileName "
                + "LEFT JOIN rm_erp_order eo2 ON e1.ORDER_NO=eo2.ORDER_NO AND e1.PRIME_LINE_NO=eo2.PRIME_LINE_NO AND eo2.FILE_NAME< :orderFileName "
                + "WHERE eo1.FILE_NAME IS NULL "
                + "GROUP BY e1.ORDER_NO, e1.PRIME_LINE_NO) e3 ON eo.ORDER_NO=e3.ORDER_NO AND eo.PRIME_LINE_NO=e3.PRIME_LINE_NO AND eo.FILE_NAME=e3.FILE_NAME "
                + "SET eo.FLAG=1 "
                + "WHERE e3.ORDER_NO IS NOT NULL;";
        params.put("orderFileName", orderFile.getName());
        rows = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("Update erp order table with not matching orders---" + rows);

        sqlString = "UPDATE rm_erp_shipment eo LEFT JOIN ( "
                + "SELECT e1.ORDER_NO, e1.PRIME_LINE_NO, MAX(eo2.FILE_NAME) `FILE_NAME` FROM ( "
                + "    SELECT e.ORDER_NO, e.PRIME_LINE_NO FROM "
                + "        ( "
                + "        SELECT eo.ORDER_NO, eo.PRIME_LINE_NO FROM rm_erp_order eo where eo.FLAG "
                + "        UNION "
                + "        SELECT es.ORDER_NO, es.PRIME_LINE_NO FROM rm_erp_shipment es where es.FLAG "
                + "    ) as e GROUP BY e.ORDER_NO, e.PRIME_LINE_NO "
                + ") as e1 "
                + "LEFT JOIN rm_erp_shipment eo1 ON e1.ORDER_NO=eo1.ORDER_NO AND e1.PRIME_LINE_NO=eo1.PRIME_LINE_NO AND eo1.FILE_NAME=:shipmentFileName "
                + "LEFT JOIN rm_erp_shipment eo2 ON e1.ORDER_NO=eo2.ORDER_NO AND e1.PRIME_LINE_NO=eo2.PRIME_LINE_NO AND eo2.FILE_NAME< :shipmentFileName "
                + "WHERE eo1.FILE_NAME IS NULL "
                + "GROUP BY e1.ORDER_NO, e1.PRIME_LINE_NO) e3 ON eo.ORDER_NO=e3.ORDER_NO AND eo.PRIME_LINE_NO=e3.PRIME_LINE_NO AND eo.FILE_NAME=e3.FILE_NAME "
                + "SET eo.FLAG=1 "
                + "WHERE e3.ORDER_NO IS NOT NULL;";
        params.put("shipmentFileName", shipmentFile.getName());
        rows = this.namedParameterJdbcTemplate.update(sqlString, params);
        logger.info("Update erp shipment table with not matching orders---" + rows);

        sqlString = "UPDATE rm_erp_shipment t LEFT JOIN rm_erp_order o ON o.FLAG=1 AND o.`ORDER_NO`=t.`ORDER_NO` AND o.`PRIME_LINE_NO`=t.`PRIME_LINE_NO` SET t.`ERP_ORDER_ID`=o.`ERP_ORDER_ID` WHERE t.FLAG=1 AND t.`ERP_ORDER_ID` IS NULL";
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
        logger.info("--------------------------------------------------------------------------------------");
        params.clear();
        params.put("curDate", sdf.format(curDate));
        this.namedParameterJdbcTemplate.update("CALL buildErpOrder(:curDate)", params);
        logger.info("Completed buildErpOrder");
        this.namedParameterJdbcTemplate.update("CALL buildErpShipment(:curDate)", params);
        logger.info("Completed buildErpShipment");
        return programList;
    }

}
