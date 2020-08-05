/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.ARTMIS.dao.impl;

import cc.altius.FASP.ARTMIS.dao.ImportArtemisDataDao;
import cc.altius.FASP.model.DTO.ArtmisShipmentDTO;
import cc.altius.FASP.model.DTO.ErpOrderDTO;
import cc.altius.FASP.model.DTO.rowMapper.ArtmisShipmentDTORowMapper;
import cc.altius.FASP.model.DTO.rowMapper.ErpOrderDTORowMapper;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.TempProgramVersion;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.rowMapper.TempProgramVersionRowMapper;
import cc.altius.FASP.model.rowMapper.VersionRowMapper;
import cc.altius.FASP.service.EmailService;
import cc.altius.utils.DateUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author altius
 */
@Repository
public class ImportArtemisDataDaoImpl implements ImportArtemisDataDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Value("${catalogFilePath}")
    private String CATALOG_FILE_PATH;
    @Value("${catalogBkpFilePath}")
    private String BKP_CATALOG_FILE_PATH;
    @Autowired
    private EmailService emailService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public void importOrderAndShipmentData(String orderDataFilePath, String shipmentDataFilePath) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
        String sql;
        int rows;
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);

        EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(3);
        String[] subjectParam = new String[]{};
        String[] bodyParam = null;
        Emailer emailer = new Emailer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
        String date = simpleDateFormat.format(DateUtils.getCurrentDateObject(DateUtils.EST));
        String filepath, fileList = "", fileList1 = "";

        File dir = new File(CATALOG_FILE_PATH);
        FileFilter fileFilter = new WildcardFileFilter("order_data_*.xml");
        File[] files = dir.listFiles(fileFilter);
//        logger.info("Going to start product catalogue import");
        for (int i = 0; i < files.length; i++) {
            fileList += " " + files[i];
            logger.info("Order file names---" + files[i]);
        }
        FileFilter fileFilter1 = new WildcardFileFilter("shipment_data_*.xml");
        File[] files1 = dir.listFiles(fileFilter1);
//        logger.info("Going to start product catalogue import");
        for (int i = 0; i < files1.length; i++) {
            fileList1 += " " + files1[i];
            logger.info("Shipment file names---" + files1[i]);
        }
        if (files.length > 1) {
            subjectParam = new String[]{"Order/Shipment", "Multiple files found in source folder"};
            bodyParam = new String[]{"Order/Shipment", date, "Multiple files found in source folder", fileList};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("Multiple files found in source folder");
        } else if (files.length < 1) {
            subjectParam = new String[]{"Order", "File not found"};
            bodyParam = new String[]{"Order", date, "File not found", "File not found"};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("File not found");
        } else if (files1.length > 1) {
            subjectParam = new String[]{"Shipment", "Multiple files found in source folder"};
            bodyParam = new String[]{"Shipment", date, "Multiple files found in source folder", fileList};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("Multiple files found in source folder");
        } else if (files1.length < 1) {
            subjectParam = new String[]{"Shipment", "File not found"};
            bodyParam = new String[]{"Shipment", date, "File not found", "File not found"};
            emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
            logger.info("File not found");
        } else {
            File fXmlFile = new File(fileList.trim());
            File fXmlFile1 = new File(fileList1.trim());
            String extension1 = "";
            String extension2 = "";
            int i = fXmlFile.getName().lastIndexOf('.');
            int i1 = fXmlFile1.getName().lastIndexOf('.');
            if (i > 0) {
                extension1 = fXmlFile.getName().substring(i + 1);
            }
            if (i1 > 0) {
                extension2 = fXmlFile1.getName().substring(i1 + 1);
            }
            if (!extension1.equalsIgnoreCase("xml")) {
                subjectParam = new String[]{"Order", "File is not an xml"};
                bodyParam = new String[]{"Order", date, "File is not an xml", fileList};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("File is not an xml");
            } else if (!extension2.equalsIgnoreCase("xml")) {
                subjectParam = new String[]{"Shipment", "File is not an xml"};
                bodyParam = new String[]{"Shipment", date, "File is not an xml", fileList};
                emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                int emailerId = this.emailService.saveEmail(emailer);
                emailer.setEmailerId(emailerId);
                this.emailService.sendMail(emailer);
                logger.error("File is not an xml");
            } else {

//        sql = "DROP TEMPORARY TABLE IF EXISTS tmp_erp_order";
                sql = "DROP TABLE IF EXISTS tmp_erp_order";
                this.jdbcTemplate.execute(sql);

//        sql = "CREATE TEMPORARY TABLE `tmp_erp_order` ( "
                sql = "CREATE TABLE `tmp_erp_order` ( "
                        + "  `TEMP_ID` int(11) NOT NULL AUTO_INCREMENT, "
                        + "  `RO_NO` varchar(45) COLLATE utf8_bin NOT NULL, "
                        + "  `RO_PRIME_LINE_NO` int(11) NOT NULL, "
                        + "  `ORDER_NUMBER` varchar(45) COLLATE utf8_bin NOT NULL, "
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
                        //                        + "  `WCS_CATALOG_PRICE` double DEFAULT NULL, "
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
                this.jdbcTemplate.execute(sql);

//        sql = "LOAD DATA LOCAL INFILE '/home/altius/Documents/FASP/ARTEMISDATA/202005121226_orderdata.csv' INTO TABLE `tmp_erp_order` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\\\"' LINES TERMINATED BY '\\n' IGNORE 1 LINES (`RO_NO`,`RO_PRIME_LINE_NO`,`ORDER_NUMBER`,`PRIME_LINE_NO`,`ORDER_TYPE_IND`,`ORDER_ENTRY_DATE`,`PARENT_RO`,`PARENT_ORDER_ENTRY_DATE`,`ITEM_ID`,`ORDERED_QTY`,`PO_RELEASED_FOR_FULFILLMENT_DATE`,`LATEST_ESTIMATED_DELIVERY_DATE`,`REQ_DELIVERY_DATE`,`REVISED_AGREED_DELIVERY_DATE`,`ITEM_SUPPLIER_NAME`,`WCS_CATALOG_PRICE`,`UNIT_PRICE`,`STATUS_NAME`,`EXTERNAL_STATUS_STAGE`,`SHIPPING_CHARGES`,`FREIGHT_ESTIMATE`,`TOTAL_ACTUAL_FREIGHT_COST`,`CARRIER_SERVICE_CODE`,`RECIPIENT_NAME`,`RECIPIENT_COUNTRY`)";
//        this.jdbcTemplate.execute(sql);
//----------------Read order xml--------------------start----------------
//                File fXmlFile = new File(orderDataFilePath);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                FileReader fr = new FileReader(fXmlFile1);
                Document doc;
                NodeList nList1;
                MapSqlParameterSource[] batchParams;
                Map<String, Object> map = new HashedMap<String, Object>();
                int[] rows1;
                int x;
                if (fr.read() == -1) {
                    //file is empty
                    logger.info("Order file is empty");
                } else {
                    doc = dBuilder.parse(fXmlFile);
                    doc.getDocumentElement().normalize();

                    nList1 = doc.getElementsByTagName("orderdata");
                    batchParams = new MapSqlParameterSource[nList1.getLength()];
                    x = 0;

                    sql = "INSERT INTO tmp_erp_order VALUES(null,:roNo,:roPrimeLineNo,:orderNo,:primeLineNo,:orderTypeInd,:orderEntryDate,:parentRo,:parentOrderEntryDate,:itemId,:orderedQty,:poReleasedForFulfillmentDate,:latestEstimatedDeliveryDate,:reqDeliveryDate,:revisedAgreedDeliveryDate,:itemSupplierName,:unitPrice,:statusName,:externalStatusStage,:shippingCharges,:freightEstimate,:totalActualFreightCost,:carrierServiceCode,:recipientName,:recipientCountry,null,:programId,:shipmentId)";
                    for (int temp2 = 0; temp2 < nList1.getLength(); temp2++) {
                        Node nNode1 = nList1.item(temp2);
                        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                            Element dataRecordElement = (Element) nNode1;
                            map.put("roNo", dataRecordElement.getElementsByTagName("ro_number").item(0).getTextContent());
                            map.put("roPrimeLineNo", dataRecordElement.getElementsByTagName("ro_prime_line_no").item(0).getTextContent());
                            map.put("orderNo", dataRecordElement.getElementsByTagName("order_number").item(0).getTextContent());
                            map.put("primeLineNo", dataRecordElement.getElementsByTagName("prime_line_no").item(0).getTextContent());
                            map.put("orderTypeInd", dataRecordElement.getElementsByTagName("order_type_ind").item(0).getTextContent());
                            map.put("orderEntryDate", dataRecordElement.getElementsByTagName("order_entry_date").item(0).getTextContent());
                            map.put("parentRo", dataRecordElement.getElementsByTagName("parent_ro").item(0).getTextContent());
                            map.put("parentOrderEntryDate", dataRecordElement.getElementsByTagName("parent_ro_entry_date").item(0).getTextContent());
                            map.put("itemId", dataRecordElement.getElementsByTagName("item_id").item(0).getTextContent());
                            map.put("orderedQty", dataRecordElement.getElementsByTagName("ordered_qty").item(0).getTextContent());
                            map.put("poReleasedForFulfillmentDate", dataRecordElement.getElementsByTagName("po_released_for_fulfillment_date").item(0).getTextContent());
                            map.put("latestEstimatedDeliveryDate", dataRecordElement.getElementsByTagName("latest_estimated_delivery_date").item(0).getTextContent());
                            map.put("reqDeliveryDate", dataRecordElement.getElementsByTagName("req_delivery_date").item(0).getTextContent());
                            map.put("revisedAgreedDeliveryDate", dataRecordElement.getElementsByTagName("revised_agreed_delivery_date").item(0).getTextContent());
                            map.put("itemSupplierName", dataRecordElement.getElementsByTagName("item_supplier_name").item(0).getTextContent());
//                        map.put("wcsCatalogPrice", dataRecordElement.getElementsByTagName("WCS_CATALOG_PRICE").item(0).getTextContent());
                            map.put("unitPrice", dataRecordElement.getElementsByTagName("unit_price").item(0).getTextContent());
                            map.put("statusName", dataRecordElement.getElementsByTagName("status_name").item(0).getTextContent());
                            map.put("externalStatusStage", dataRecordElement.getElementsByTagName("external_status_stage").item(0).getTextContent());
                            String shippingCharges = dataRecordElement.getElementsByTagName("shipping_charges").item(0).getTextContent();
                            map.put("shippingCharges", (shippingCharges != null && shippingCharges != "" ? shippingCharges : null));
                            String freightEstimate = dataRecordElement.getElementsByTagName("freight_estimate").item(0).getTextContent();
                            map.put("freightEstimate", (freightEstimate != null && freightEstimate != "" ? freightEstimate : null));
                            String totalActualFreightCost = dataRecordElement.getElementsByTagName("total_actual_freight_cost").item(0).getTextContent();
                            map.put("totalActualFreightCost", (totalActualFreightCost != null && totalActualFreightCost != "" ? totalActualFreightCost : null));
                            map.put("carrierServiceCode", dataRecordElement.getElementsByTagName("carrier_service_code").item(0).getTextContent());
                            map.put("recipientName", dataRecordElement.getElementsByTagName("recipient_name").item(0).getTextContent());
                            map.put("recipientCountry", dataRecordElement.getElementsByTagName("recipient_country").item(0).getTextContent());
                            map.put("programId", dataRecordElement.getElementsByTagName("program_id").item(0).getTextContent());
                            map.put("shipmentId", dataRecordElement.getElementsByTagName("shipment_id").item(0).getTextContent());
                            logger.info("map--------" + map);
                            batchParams[x] = new MapSqlParameterSource(map);
                            x++;
                        }
                    }
                    rows1 = namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
                    logger.info("Successfully inserted into tmp_erp_order records---" + rows1.length);
//---------------------End-----------------------------------------------
                }
                sql = "SELECT COUNT(*) FROM tmp_erp_order;";
                logger.info("Total rows inserted in tmp_erp_order---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "UPDATE tmp_erp_order teo SET teo.LATEST_ESTIMATED_DELIVERY_DATE = NULL WHERE teo.LATEST_ESTIMATED_DELIVERY_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_erp_order teo SET teo.ORDER_ENTRY_DATE = NULL WHERE teo.ORDER_ENTRY_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_erp_order teo SET teo.PARENT_ORDER_ENTRY_DATE = NULL WHERE teo.PARENT_ORDER_ENTRY_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_erp_order teo SET teo.PO_RELEASED_FOR_FULFILLMENT_DATE = NULL WHERE teo.PO_RELEASED_FOR_FULFILLMENT_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_erp_order teo SET teo.REQ_DELIVERY_DATE = NULL WHERE teo.REQ_DELIVERY_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_erp_order teo SET teo.REVISED_AGREED_DELIVERY_DATE = NULL WHERE teo.REVISED_AGREED_DELIVERY_DATE='';";
                this.jdbcTemplate.update(sql);

//        sql = "DROP TEMPORARY TABLE IF EXISTS tmp_erp_shipment";
                sql = "DROP TABLE IF EXISTS tmp_erp_shipment";
                this.jdbcTemplate.execute(sql);

//        sql = "CREATE TEMPORARY TABLE `tmp_erp_shipment` ( "
                sql = "CREATE TABLE `tmp_erp_shipment` ( "
                        + "  `TEMP_SHIPMENT_ID` int(11) NOT NULL AUTO_INCREMENT, "
                        + "  `KN_SHIPMENT_NUMBER` varchar(45) COLLATE utf8_bin NOT NULL, "
                        + "  `ORDER_NUMBER` varchar(45) COLLATE utf8_bin DEFAULT NULL, "
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
                this.jdbcTemplate.execute(sql);

//        sql = "LOAD DATA LOCAL INFILE '/home/altius/Documents/FASP/ARTEMISDATA/202005121409_shipmentdata.csv' INTO TABLE `tmp_erp_shipment` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\\\"' LINES TERMINATED BY '\\n' IGNORE 1 LINES (`KN_SHIPMENT_NUMBER`,`ORDER_NUMBER`,`PRIME_LINE_NO`,`BATCH_NO`,`ITEM_ID`,`EXPIRATION_DATE`,`SHIPPED_QUANTITY`,`DELIVERED_QUANTITY`,`STATUS_NAME`,`EXTERNAL_STATUS_STAGE`,`ACTUAL_SHIPMENT_DATE`,`ACTUAL_DELIVERY_DATE`);";
//        this.jdbcTemplate.execute(sql);
//----------------Read shipment xml--------------------start----------------
//                fXmlFile = new File(shipmentDataFilePath);
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();
                fr = new FileReader(fXmlFile1);
                if (fr.read() == -1) {
                    //file is empty
                    logger.info("Shipment file is empty");
                } else {
                    doc = dBuilder.parse(fXmlFile1);
                    doc.getDocumentElement().normalize();
                    System.out.println("fXmlFile1 name---" + fXmlFile1.getName());
                    System.out.println("fXmlFile1 path---" + fXmlFile1.getPath());
                    NodeList nList2 = doc.getElementsByTagName("shipmentdata");
                    batchParams = new MapSqlParameterSource[nList2.getLength()];
                    map.clear();
                    x = 0;
                    System.out.println("list length---" + nList2.getLength());
                    sql = "INSERT INTO tmp_erp_shipment VALUES(null,:knShipmentNo,:orderNo,:primeLineNo,:batchNo,:itemId,"
                            + ":expirationDate,:shippedQty,:deliveredQty,"
                            + ":statusName,:externalStatusStage,:actualShipmentDate,:actualDeliveryDate,null)";
                    for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
                        Node nNode1 = nList2.item(temp2);
                        if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                            Element dataRecordElement = (Element) nNode1;
                            System.out.println("dataRecordElement---" + dataRecordElement);
                            map.put("knShipmentNo", dataRecordElement.getElementsByTagName("kn_shipment_number").item(0).getTextContent());
                            System.out.println("order no shipment---" + dataRecordElement.getElementsByTagName("order_number").item(0).getTextContent());
                            map.put("orderNo", dataRecordElement.getElementsByTagName("order_number").item(0).getTextContent());
                            map.put("primeLineNo", dataRecordElement.getElementsByTagName("prime_line_no").item(0).getTextContent());
                            map.put("batchNo", dataRecordElement.getElementsByTagName("batch_no").item(0).getTextContent());
                            map.put("itemId", dataRecordElement.getElementsByTagName("item_id").item(0).getTextContent());
                            map.put("expirationDate", dataRecordElement.getElementsByTagName("expiration_date").item(0).getTextContent());
                            map.put("shippedQty", dataRecordElement.getElementsByTagName("shipped_quantity").item(0).getTextContent());
                            String deliveredQty = dataRecordElement.getElementsByTagName("delivered_quantity").item(0).getTextContent();
                            map.put("deliveredQty", (deliveredQty != null && deliveredQty != "" ? deliveredQty : 0));
                            map.put("statusName", dataRecordElement.getElementsByTagName("status_name").item(0).getTextContent());
                            map.put("externalStatusStage", dataRecordElement.getElementsByTagName("external_status_stage").item(0).getTextContent());
                            map.put("actualShipmentDate", dataRecordElement.getElementsByTagName("actual_shipment_date").item(0).getTextContent());
                            map.put("actualDeliveryDate", dataRecordElement.getElementsByTagName("actual_delivery_date").item(0).getTextContent());
                            batchParams[x] = new MapSqlParameterSource(map);
                            x++;
                        }
                    }
                    rows1 = namedParameterJdbcTemplate.batchUpdate(sql, batchParams);
                    logger.info("Successfully inserted into tmp_erp_shipment records---" + rows1.length);
//---------------------End-----------------------------------------------
                }
                sql = "SELECT COUNT(*) FROM tmp_erp_shipment;";
                logger.info("Total rows inserted in tmp_erp_shipment---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "UPDATE rm_erp_order o SET o.`FLAG`=0";
                this.jdbcTemplate.update(sql);

                sql = "SELECT COUNT(*) FROM rm_erp_order;";
                logger.info("Total rows present in rm_erp_order---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "UPDATE tmp_erp_order t "
                        + "LEFT JOIN rm_erp_order o ON t.`ORDER_NUMBER`=o.`ORDER_NO` AND t.`PRIME_LINE_NO`=o.`PRIME_LINE_NO` "
                        + "SET "
                        + "o.`RO_NO`=t.`RO_NO`, "
                        + "o.`RO_PRIME_LINE_NO`=t.`RO_PRIME_LINE_NO`, "
                        + "o.`ORDER_TYPE`=t.`ORDER_TYPE_IND`, "
                        + "o.`CREATED_DATE`=IFNULL(CONCAT(LEFT(t.`ORDER_ENTRY_DATE`,10),' ',REPLACE(MID(t.`ORDER_ENTRY_DATE`,12,8),'.',':')),NULL), "
                        + "o.`PARENT_RO`=t.`PARENT_RO`, "
                        + "o.`PARENT_CREATED_DATE`=IFNULL(CONCAT(LEFT(t.`PARENT_ORDER_ENTRY_DATE`,10),' ',REPLACE(MID(t.`PARENT_ORDER_ENTRY_DATE`,12,8),'.',':')),NULL), "
                        + "o.`PLANNING_UNIT_SKU_CODE`=LEFT(t.`ITEM_ID`,12), "
                        + "o.`PROCUREMENT_UNIT_SKU_CODE`=IF(LENGTH(t.`ITEM_ID`)=15,t.`ITEM_ID`,NULL), "
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
                        + "o.`LAST_MODIFIED_DATE`=?, "
                        + "o.`PROGRAM_ID`=t.`PROGRAM_ID`, "
                        + "o.`SHIPMENT_ID`=t.`SHIPMENT_ID`, "
                        + "o.`FLAG`=1;";
//sql = "UPDATE tmp_erp_order t "
//                + "LEFT JOIN rm_erp_order o ON t.`ORDER_NUMBER`=o.`ORDER_NO` AND t.`PRIME_LINE_NO`=o.`PRIME_LINE_NO` "
//                + "SET "
//                + "o.`RO_NO`=t.`RO_NO`, "
//                + "o.`RO_PRIME_LINE_NO`=t.`RO_PRIME_LINE_NO`, "
//                + "o.`ORDER_TYPE`=t.`ORDER_TYPE_IND`, "
//                + "o.`CREATED_DATE`=STR_TO_DATE(t.`ORDER_ENTRY_DATE`,'%Y-%m-%d'), "
//                + "o.`PARENT_RO`=t.`PARENT_RO`, "
//                + "o.`PARENT_CREATED_DATE`=STR_TO_DATE(t.`PARENT_ORDER_ENTRY_DATE`,'%Y-%m-%d'), "
//                + "o.`PLANNING_UNIT_SKU_CODE`=LEFT(t.`ITEM_ID`,12), "
//                + "o.`PROCUREMENT_UNIT_SKU_CODE`=IF(LENGTH(t.`ITEM_ID`)=15,t.`ITEM_ID`,NULL), "
//                + "o.`QTY`=t.`ORDERED_QTY`, "
//                + " o.`ORDERD_DATE`=STR_TO_DATE(t.`PO_RELEASED_FOR_FULFILLMENT_DATE`,'%Y-%m-%d'), "
//                + " o.`CURRENT_ESTIMATED_DELIVERY_DATE`=STR_TO_DATE(t.`LATEST_ESTIMATED_DELIVERY_DATE`,'%Y-%m-%d'), "
//                + "o.`REQ_DELIVERY_DATE`=STR_TO_DATE(t.`REQ_DELIVERY_DATE`,'%Y-%m-%d'), "
//                + "o.`AGREED_DELIVERY_DATE`=STR_TO_DATE(t.`REVISED_AGREED_DELIVERY_DATE`,'%Y-%m-%d'), "
//                + "o.`SUPPLIER_NAME`=t.`ITEM_SUPPLIER_NAME`, "
//                + "o.`PRICE`=t.`UNIT_PRICE`, "
//                + "o.`SHIPPING_COST`=COALESCE(t.`TOTAL_ACTUAL_FREIGHT_COST`,t.`FREIGHT_ESTIMATE`,t.`SHIPPING_CHARGES`), "
//                + "o.`SHIP_BY`=t.`CARRIER_SERVICE_CODE`, "
//                + "o.`RECPIENT_NAME`=t.`RECIPIENT_NAME`, "
//                + "o.`RECPIENT_COUNTRY`=t.`RECIPIENT_COUNTRY`, "
//                + "o.`STATUS`=t.`EXTERNAL_STATUS_STAGE`, "
//                + "o.`LAST_MODIFIED_DATE`=?, "
//                + "o.`FLAG`=1;";
                rows = this.jdbcTemplate.update(sql, curDate);
                logger.info("No of rows updated in rm_erp_order---" + rows);

                sql = "INSERT IGNORE INTO rm_erp_order "
                        + " SELECT NULL,RO_NO,RO_PRIME_LINE_NO,ORDER_NUMBER,PRIME_LINE_NO, "
                        + " ORDER_TYPE_IND,IFNULL(DATE_FORMAT(ORDER_ENTRY_DATE,'%Y-%m-%d'),NULL),PARENT_RO,IFNULL(DATE_FORMAT(PARENT_ORDER_ENTRY_DATE,'%Y-%m-%d %H:%i:%s'),NULL),LEFT(ITEM_ID, 12), "
                        + " IF(LENGTH(ITEM_ID)=15,ITEM_ID,NULL),ORDERED_QTY,IFNULL(DATE_FORMAT(PO_RELEASED_FOR_FULFILLMENT_DATE,'%Y-%m-%d'),NULL), "
                        + " IFNULL(DATE_FORMAT(LATEST_ESTIMATED_DELIVERY_DATE,'%Y-%m-%d'),NULL), "
                        + " IFNULL(DATE_FORMAT(REQ_DELIVERY_DATE,'%Y-%m-%d'),NULL), "
                        + " IFNULL(DATE_FORMAT(REVISED_AGREED_DELIVERY_DATE,'%Y-%m-%d'),NULL),ITEM_SUPPLIER_NAME,UNIT_PRICE, "
                        + " COALESCE(TOTAL_ACTUAL_FREIGHT_COST,FREIGHT_ESTIMATE,SHIPPING_CHARGES),CARRIER_SERVICE_CODE,RECIPIENT_NAME, "
                        + " RECIPIENT_COUNTRY,STATUS_NAME,1,?,1,NULL,PROGRAM_ID,SHIPMENT_ID "
                        + " FROM tmp_erp_order t ";

                rows = this.jdbcTemplate.update(sql, curDate);
                logger.info("No of rows inserted into rm_erp_order---" + rows);

                sql = "UPDATE tmp_erp_shipment teo SET teo.EXPIRATION_DATE = NULL WHERE teo.EXPIRATION_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_erp_shipment teo SET teo.ACTUAL_SHIPMENT_DATE = NULL WHERE teo.ACTUAL_SHIPMENT_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "UPDATE tmp_erp_shipment teo SET teo.ACTUAL_DELIVERY_DATE = NULL WHERE teo.ACTUAL_DELIVERY_DATE='';";
                this.jdbcTemplate.update(sql);

                sql = "SELECT COUNT(*) FROM rm_erp_shipment;";
                logger.info("Total rows present in rm_erp_shipment---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

                sql = "UPDATE tmp_erp_shipment t "
                        + "LEFT JOIN rm_erp_shipment s ON t.`KN_SHIPMENT_NUMBER`=s.`KN_SHIPMENT_NO` "
                        + "AND t.`ORDER_NUMBER`=s.`ORDER_NO` "
                        + "AND t.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` "
                        + "AND t.`BATCH_NO`=s.`BATCH_NO` "
                        + "SET s.`FLAG`=1, "
                        + "s.`LAST_MODIFIED_DATE`=?, "
                        + "s.`EXPIRY_DATE`=IFNULL(LEFT(t.`EXPIRATION_DATE`,10),NULL), "
                        + "s.`PROCUREMENT_UNIT_SKU_CODE`=t.`ITEM_ID`, "
                        + "s.`SHIPPED_QTY`=t.`SHIPPED_QUANTITY`, "
                        + "s.`DELIVERED_QTY`=t.`DELIVERED_QUANTITY`, "
                        + "s.`ACTUAL_SHIPMENT_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_SHIPMENT_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_SHIPMENT_DATE`,12,8),'.',':')),NULL), "
                        + "s.`ACTUAL_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_DELIVERY_DATE`,12,8),'.',':')),NULL), "
                        + "s.`STATUS`=t.`EXTERNAL_STATUS_STAGE`;";

                rows = this.jdbcTemplate.update(sql, curDate);
                logger.info("No of rows updated in  rm_erp_shipment---" + rows);

                sql = "INSERT IGNORE INTO rm_erp_shipment "
                        + "SELECT  NULL,NULL,KN_SHIPMENT_NUMBER,ORDER_NUMBER,PRIME_LINE_NO,BATCH_NO,IFNULL(DATE_FORMAT(EXPIRATION_DATE,'%Y-%m-%d %H:%i:%s'),NULL),ITEM_ID,SHIPPED_QUANTITY, "
                        + "DELIVERED_QUANTITY,IFNULL(DATE_FORMAT(ACTUAL_SHIPMENT_DATE,'%Y-%m-%d %H:%i:%s'),NULL),IFNULL(DATE_FORMAT(ACTUAL_DELIVERY_DATE,'%Y-%m-%d %H:%i:%s'),NULL),EXTERNAL_STATUS_STAGE,?,1 "
                        + "FROM  tmp_erp_shipment t;";
                rows = this.jdbcTemplate.update(sql, curDate);
                logger.info("No of rows inserted into rm_erp_shipment---" + rows);

                sql = "UPDATE rm_erp_shipment t "
                        + "LEFT JOIN rm_erp_order o ON o.`ORDER_NO`=t.`ORDER_NO` AND o.`PRIME_LINE_NO`=t.`PRIME_LINE_NO` "
                        + "SET t.`ERP_ORDER_ID`=o.`ERP_ORDER_ID`;";
                rows = this.jdbcTemplate.update(sql);

                logger.info("update erp order id in erp shipment table---" + rows);

                sql = "SELECT COUNT(*) FROM rm_erp_shipment where erp_order_id is null and flag=1;";
                logger.info("Total rows without erp_order_id in rm_erp_shipment---" + this.jdbcTemplate.queryForObject(sql, Integer.class));

//                sql = "SELECT s.`PROGRAM_ID`,m.`ERP_ORDER_ID` "
//                        + "FROM rm_shipment_trans_erp_order_mapping m "
//                        + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=m.`SHIPMENT_ID` "
//                        + "LEFT JOIN rm_erp_order o ON o.`ERP_ORDER_ID`=m.`ERP_ORDER_ID`"
//                        + "WHERE o.`FLAG`=1 "
//                        + "GROUP BY m.`SHIPMENT_ID`;";
//                List<TempProgramVersion> list = this.jdbcTemplate.query(sql, new TempProgramVersionRowMapper());
//                for (TempProgramVersion t : list) {
//                    sql = "CALL getVersionId(?,1,1,'NA',1,?);";
//                    Version version = this.jdbcTemplate.queryForObject(sql, new VersionRowMapper(), t.getProgramId(), curDate);
//                    int versionId = version.getVersionId();
//                    sql = "UPDATE rm_erp_order o SET o.`VERSION_ID`=? WHERE o.`ERP_ORDER_ID`=?;";
//                    rows = this.jdbcTemplate.update(sql, versionId, t.getErpOrderId());
//                }
                sql = "SELECT o.`ERP_ORDER_ID`,o.`SHIPMENT_ID` FROM rm_erp_order o WHERE o.`FLAG`=1;";
                List<ErpOrderDTO> erpOrderDTOList = this.jdbcTemplate.query(sql, new ErpOrderDTORowMapper());
//                List<Integer> erpOrderIdList = this.jdbcTemplate.queryForList(sql, Integer.class);
                int shipmentTransId = 0;
                KeyHolder keyHolder1 = new GeneratedKeyHolder();
                MapSqlParameterSource params1 = new MapSqlParameterSource();
                logger.info("erpOrderDTO---" + erpOrderDTOList.size());
                for (ErpOrderDTO erpOrderDTO : erpOrderDTOList) {
                    try {
                        if (erpOrderDTO.getShipmentId() != 0) {

                        } else {
                        }
                    } catch (Exception e) {
                        logger.info("Error occured while creating shipment---" + e);
                        e.printStackTrace();
                    }

                }
                logger.info("Order/Shipment file imported successfully");
                File directory = new File(BKP_CATALOG_FILE_PATH);
                if (directory.isDirectory()) {
                    fXmlFile.renameTo(new File(BKP_CATALOG_FILE_PATH + fXmlFile.getName()));
                    fXmlFile1.renameTo(new File(BKP_CATALOG_FILE_PATH + fXmlFile1.getName()));
                    logger.info("Order/Shipment files moved into processed folder");
                } else {
                    subjectParam = new String[]{"Order/Shipment", "Backup directory does not exists"};
                    bodyParam = new String[]{"Order/Shipment", date, "Backup directory does not exists", "Backup directory does not exists"};
                    emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), "anchal.c@altius.cc,shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", "shubham.y@altius.cc,priti.p@altius.cc,sameer.g@altiusbpo.com", subjectParam, bodyParam);
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                    this.emailService.sendMail(emailer);
                    logger.error("Backup directory does not exists");
                }
            }
        }
    }

}
