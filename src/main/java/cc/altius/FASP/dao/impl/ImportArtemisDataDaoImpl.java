/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ImportArtemisDataDao;
import cc.altius.FASP.model.TempProgramVersion;
import cc.altius.FASP.model.rowMapper.TempProgramVersionRowMapper;
import cc.altius.utils.DateUtils;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public int importOrderAndShipmentData(String orderDataFilePath, String shipmentDataFilePath) {
        String sql;
        int rows;
//        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);

        sql = "DROP TABLE IF EXISTS tmp_erp_order";
        this.jdbcTemplate.execute(sql);

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
                + "  `WCS_CATALOG_PRICE` double DEFAULT NULL, "
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
                + "  PRIMARY KEY (`TEMP_ID`), "
                + "  UNIQUE KEY `TEMP_ID_UNIQUE` (`TEMP_ID`) "
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin";
        this.jdbcTemplate.execute(sql);

//        sql = "TRUNCATE TABLE `tmp_erp_order`";
//        this.jdbcTemplate.update(sql);
        sql = "LOAD DATA LOCAL INFILE '/home/akil/Desktop/Data/Software/FHI360/Artmis\\ Data\\ Import/202005121226_orderdata.csv' INTO TABLE `tmp_erp_order` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\\\"' LINES TERMINATED BY '\\n' IGNORE 1 LINES (`RO_NO`,`RO_PRIME_LINE_NO`,`ORDER_NUMBER`,`PRIME_LINE_NO`,`ORDER_TYPE_IND`,`ORDER_ENTRY_DATE`,`PARENT_RO`,`PARENT_ORDER_ENTRY_DATE`,`ITEM_ID`,`ORDERED_QTY`,`PO_RELEASED_FOR_FULFILLMENT_DATE`,`LATEST_ESTIMATED_DELIVERY_DATE`,`REQ_DELIVERY_DATE`,`REVISED_AGREED_DELIVERY_DATE`,`ITEM_SUPPLIER_NAME`,`WCS_CATALOG_PRICE`,`UNIT_PRICE`,`STATUS_NAME`,`EXTERNAL_STATUS_STAGE`,`SHIPPING_CHARGES`,`FREIGHT_ESTIMATE`,`TOTAL_ACTUAL_FREIGHT_COST`,`CARRIER_SERVICE_CODE`,`RECIPIENT_NAME`,`RECIPIENT_COUNTRY`)";
        this.jdbcTemplate.execute(sql);

        sql = "DROP TABLE IF EXISTS tmp_erp_shipment";
        this.jdbcTemplate.execute(sql);
        
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

        sql = "LOAD DATA LOCAL INFILE '/home/akil/Desktop/Data/Software/FHI360/Artmis\\ Data\\ Import/202005121409_shipmentdata.csv' INTO TABLE `tmp_erp_shipment` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\\\"' LINES TERMINATED BY '\\n' IGNORE 1 LINES (`KN_SHIPMENT_NUMBER`,`ORDER_NUMBER`,`PRIME_LINE_NO`,`BATCH_NO`,`ITEM_ID`,`EXPIRATION_DATE`,`SHIPPED_QUANTITY`,`DELIVERED_QUANTITY`,`STATUS_NAME`,`EXTERNAL_STATUS_STAGE`,`ACTUAL_SHIPMENT_DATE`,`ACTUAL_DELIVERY_DATE`);";
        this.jdbcTemplate.execute(sql);

        sql = "UPDATE rm_erp_order o SET o.`FLAG`=0";
        this.jdbcTemplate.update(sql);

//        sql = "UPDATE tmp_erp_order t "
//                + "LEFT JOIN rm_erp_order o ON t.`ORDER_NUMBER`=o.`ORDER_NO` AND t.`PRIME_LINE_NO`=o.`PRIME_LINE_NO` "
//                + "SET "
//                + "o.`RO_NO`=t.`RO_NO`, "
//                + "o.`RO_PRIME_LINE_NO`=t.`RO_PRIME_LINE_NO`, "
//                + "o.`ORDER_TYPE`=t.`ORDER_TYPE_IND`, "
//                + "o.`CREATED_DATE`=NOW(), "
//                + "o.`PARENT_RO`=t.`PARENT_RO`, "
//                + "o.`PARENT_CREATED_DATE`=NOW(), "
//                + "o.`PLANNING_UNIT_SKU_CODE`=LEFT(t.`ITEM_ID`,12), "
//                + "o.`PROCUREMENT_UNIT_SKU_CODE`=IF(LENGTH(t.`ITEM_ID`)=15,t.`ITEM_ID`,NULL), "
//                + "o.`QTY`=t.`ORDERED_QTY`, "
//                + " o.`ORDERD_DATE`=NOW(), "
//                + " o.`CURRENT_ESTIMATED_DELIVERY_DATE`=NOW(), "
//                + "o.`REQ_DELIVERY_DATE`=NOW(), "
//                + "o.`AGREED_DELIVERY_DATE`=NOW(), "
//                + "o.`SUPPLIER_NAME`=t.`ITEM_SUPPLIER_NAME`, "
//                + "o.`PRICE`=t.`UNIT_PRICE`, "
//                + "o.`SHIPPING_COST`=COALESCE(t.`TOTAL_ACTUAL_FREIGHT_COST`,t.`FREIGHT_ESTIMATE`,t.`SHIPPING_CHARGES`), "
//                + "o.`SHIP_BY`=t.`CARRIER_SERVICE_CODE`, "
//                + "o.`RECPIENT_NAME`=t.`RECIPIENT_NAME`, "
//                + "o.`RECPIENT_COUNTRY`=t.`RECIPIENT_COUNTRY`, "
//                + "o.`STATUS`=t.`EXTERNAL_STATUS_STAGE`, "
//                + "o.`LAST_MODIFIED_DATE`=?, "
//                + "o.`FLAG`=1;";
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
                + "o.`SHIPPING_COST`=COALESCE(t.`TOTAL_ACTUAL_FREIGHT_COST`,t.`FREIGHT_ESTIMATE`,t.`SHIPPING_CHARGES`), "
                + "o.`SHIP_BY`=t.`CARRIER_SERVICE_CODE`, "
                + "o.`RECPIENT_NAME`=t.`RECIPIENT_NAME`, "
                + "o.`RECPIENT_COUNTRY`=t.`RECIPIENT_COUNTRY`, "
                + "o.`STATUS`=t.`EXTERNAL_STATUS_STAGE`, "
                + "o.`LAST_MODIFIED_DATE`=?, "
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
        System.out.println("rows1 update---" + rows);

        sql = "INSERT IGNORE INTO rm_erp_order "
                + " SELECT NULL,RO_NO,RO_PRIME_LINE_NO,ORDER_NUMBER,PRIME_LINE_NO, "
                + " ORDER_TYPE_IND,IFNULL(DATE_FORMAT(ORDER_ENTRY_DATE,'%Y-%m-%d'),NULL),PARENT_RO,IFNULL(DATE_FORMAT(PARENT_ORDER_ENTRY_DATE,'%Y-%m-%d %H:%i:%s'),NULL),LEFT(ITEM_ID, 12), "
                + " IF(LENGTH(ITEM_ID)=15,ITEM_ID,NULL),ORDERED_QTY,IFNULL(DATE_FORMAT(PO_RELEASED_FOR_FULFILLMENT_DATE,'%Y-%m-%d'),NULL), "
                + " IFNULL(DATE_FORMAT(LATEST_ESTIMATED_DELIVERY_DATE,'%Y-%m-%d'),NULL), "
                + " IFNULL(DATE_FORMAT(REQ_DELIVERY_DATE,'%Y-%m-%d'),NULL), "
                + " IFNULL(DATE_FORMAT(REVISED_AGREED_DELIVERY_DATE,'%Y-%m-%d'),NULL),ITEM_SUPPLIER_NAME,UNIT_PRICE, "
                + " COALESCE(TOTAL_ACTUAL_FREIGHT_COST,FREIGHT_ESTIMATE,SHIPPING_CHARGES),CARRIER_SERVICE_CODE,RECIPIENT_NAME, "
                + " RECIPIENT_COUNTRY,EXTERNAL_STATUS_STAGE,1,?,1,NULL "
                + " FROM tmp_erp_order t ";

        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rows1---" + rows);
        sql = "UPDATE tmp_erp_shipment t "
                + "LEFT JOIN rm_erp_shipment s ON t.`KN_SHIPMENT_NUMBER`=s.`KN_SHIPMENT_NO` "
                + "AND t.`ORDER_NUMBER`=s.`ORDER_NO` "
                + "AND t.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` "
                + "AND t.`BATCH_NO`=s.`BATCH_NO` "
                + "SET s.`FLAG`=1, "
                + "s.`LAST_MODIFIED_DATE`=?, "
                + "s.`EXPIRY_DATE`=IF(t.`EXPIRATION_DATE` IS NOT NULL AND t.`EXPIRATION_DATE` != '',DATE_FORMAT(t.`EXPIRATION_DATE`,'%Y-%m-%d %H:%i:%s'),NULL), "
                + "s.`EXPIRY_DATE`=IFNULL(LEFT(t.`EXPIRATION_DATE`,10),NULL), "
                + "s.`PROCUREMENT_UNIT_SKU_CODE`=t.`ITEM_ID`, "
                + "s.`SHIPPED_QTY`=t.`SHIPPED_QUANTITY`, "
                + "s.`DELIVERED_QTY`=t.`DELIVERED_QUANTITY`, "
                + "s.`ACTUAL_SHIPMENT_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_SHIPMENT_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_SHIPMENT_DATE`,12,8),'.',':')),NULL), "
                + "s.`ACTUAL_DELIVERY_DATE`=IFNULL(CONCAT(LEFT(t.`ACTUAL_DELIVERY_DATE`,10),' ',REPLACE(MID(t.`ACTUAL_DELIVERY_DATE`,12,8),'.',':')),NULL), "
                + "s.`STATUS`=t.`EXTERNAL_STATUS_STAGE`;";

        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rows2 update---" + rows);
        sql = "INSERT IGNORE INTO rm_erp_shipment "
                + "SELECT  NULL,NULL,KN_SHIPMENT_NUMBER,ORDER_NUMBER,PRIME_LINE_NO,BATCH_NO,IFNULL(DATE_FORMAT(EXPIRATION_DATE,'%Y-%m-%d %H:%i:%s'),NULL),ITEM_ID,SHIPPED_QUANTITY, "
                + "DELIVERED_QUANTITY,IFNULL(DATE_FORMAT(ACTUAL_SHIPMENT_DATE,'%Y-%m-%d %H:%i:%s'),NULL),IFNULL(DATE_FORMAT(ACTUAL_DELIVERY_DATE,'%Y-%m-%d %H:%i:%s'),NULL),EXTERNAL_STATUS_STAGE,?,1 "
                + "FROM  tmp_erp_shipment t;";
        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rows2---" + rows);

        sql = "UPDATE rm_erp_shipment t "
                + "LEFT JOIN rm_erp_order o ON o.`ORDER_NO`=t.`ORDER_NO` AND o.`PRIME_LINE_NO`=t.`PRIME_LINE_NO` "
                + "SET t.`ERP_ORDER_ID`=o.`ERP_ORDER_ID`;";

        rows = this.jdbcTemplate.update(sql);
        System.out.println("rows3---" + rows);
        sql = "SELECT s.`PROGRAM_ID`,m.`ERP_ORDER_ID` "
                + "FROM rm_shipment_trans_erp_order_mapping m "
                + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=m.`SHIPMENT_ID` "
                + "LEFT JOIN rm_erp_order o ON o.`ERP_ORDER_ID`=m.`ERP_ORDER_ID`"
                + "WHERE o.`FLAG`=1 "
                + "GROUP BY m.`SHIPMENT_ID`;";
        List<TempProgramVersion> list = this.jdbcTemplate.query(sql, new TempProgramVersionRowMapper());
        for (TempProgramVersion t : list) {
            sql = "CALL getVersionId(?,1,?);";
            int versionId = this.jdbcTemplate.queryForObject(sql, Integer.class, t.getProgramId(), curDate);
            sql = "UPDATE rm_erp_order o SET o.`VERSION_ID`=? WHERE o.`ERP_ORDER_ID`=?;";
            rows = this.jdbcTemplate.update(sql, versionId, t.getErpOrderId());
        }
        sql = "INSERT INTO rm_shipment_trans  "
                + " SELECT NULL,m.`SHIPMENT_ID`,pu.`PLANNING_UNIT_ID`,o.`CURRENT_ESTIMATED_DELIVERY_DATE`,  "
                + " pru.`PROCUREMENT_UNIT_ID`,sup.`SUPPLIER_ID`,o.`QTY`,o.`PRICE`,(o.`QTY`*o.`PRICE`),o.`SHIP_BY`,o.`SHIPPING_COST`,o.`ORDERD_DATE`,  "
                + " min(es.`ACTUAL_SHIPMENT_DATE`),min(es.`ACTUAL_DELIVERY_DATE`),sm.`SHIPMENT_STATUS_ID`,NULL,10,o.`ORDER_NO`,o.`PRIME_LINE_NO`,1,?,o.`VERSION_ID`,1  "
                + " FROM rm_erp_order o  "
                + " LEFT JOIN rm_shipment_trans_erp_order_mapping m ON m.`ERP_ORDER_ID`=o.`ERP_ORDER_ID`  "
                + " LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=m.`SHIPMENT_ID`  "
                + " LEFT JOIN rm_procurement_agent_planning_unit pu ON pu.`SKU_CODE`=o.`PLANNING_UNIT_SKU_CODE` AND pu.`PROCUREMENT_AGENT_ID`=1  "
                + " LEFT JOIN rm_procurement_agent_procurement_unit pru ON pru.`SKU_CODE`=o.`PROCUREMENT_UNIT_SKU_CODE` AND pru.`PROCUREMENT_AGENT_ID`=1  "
                + " LEFT JOIN ap_label l ON l.`LABEL_EN`=o.`SUPPLIER_NAME`  "
                + " LEFT JOIN rm_supplier sup ON sup.`LABEL_ID`=l.`LABEL_ID`  "
                + " LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=o.`ERP_ORDER_ID`  "
                + " LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=o.`STATUS` "
                + " WHERE m.`SHIPMENT_ID` IS NOT NULL AND o.`FLAG`=1 "
                + " GROUP BY o.`ERP_ORDER_ID`";
        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rm_shipment_trans---" + rows);

        return rows;
    }

}
