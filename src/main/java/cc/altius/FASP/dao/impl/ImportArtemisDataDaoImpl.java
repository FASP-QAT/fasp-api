/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.ImportArtemisDataDao;
import cc.altius.utils.DateUtils;
import java.util.Date;
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
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        System.out.println("inside dao orderDataFilePath------------------" + orderDataFilePath);
        System.out.println("inside dao shipmentDataFilePath------------------" + shipmentDataFilePath);

        sql = "TRUNCATE TABLE `fasp`.`tmp_erp_order`";
        this.jdbcTemplate.update(sql);
        // Insert into tmp_erp_order
//        sql = "LOAD DATA LOCAL INFILE '" + orderDataFilePath + "' INTO TABLE `temp_erp_order` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES (NULL,`RO_NO`,`RO_PRIME_LINE_NO`,`ORDER_NO`,`PRIME_LINE_NO`,`ORDER_TYPE`,`CREATED_DATE`,`PARENT_RO`,`PARENT_CREATED_DATE`,`PLANNING_UNIT_SKU_CODE`,`PROCUREMENT_UNIT_SKU_CODE`,`QTY`,`ORDERD_DATE`,`CURRENT_ESTIMATED_DELIVERY_DATE`,`REQ_DELIVERY_DATE`,`AGREED_DELIVERY_DATE`,`SUPPLIER_NAME`,`PRICE`,`SHIPPING_COST`,`SHIP_BY`,`RECPIENT_NAME`,`RECPIENT_COUNTRY`,`STATUS`,`PROCUREMENT_AGENT_ID`) ";
        sql = "LOAD DATA LOCAL INFILE '" + orderDataFilePath + "' INTO TABLE `tmp_erp_order` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES (`RO_NO`,`RO_PRIME_LINE_NO`,`ORDER_NUMBER`,`PRIME_LINE_NO`,`ORDER_TYPE_IND`,`ORDER_ENTRY_DATE`,`PARENT_RO`,`PARENT_ORDER_ENTRY_DATE`,`ITEM_ID`,`ORDERED_QTY`,`PO_RELEASED_FOR_FULFILLMENT_DATE`,`LATEST_ESTIMATED_DELIVERY_DATE`,`REQ_DELIVERY_DATE`,`REVISED_AGREED_DELIVERY_DATE`,`ITEM_SUPPLIER_NAME`,`WCS_CATALOG_PRICE`,`UNIT_PRICE`,`STATUS_NAME`,`EXTERNAL_STATUS_STAGE`,`SHIPPING_CHARGES`,`FREIGHT_ESTIMATE`,`TOTAL_ACTUAL_FREIGHT_COST`,`CARRIER_SERVICE_CODE`,`RECIPIENT_NAME`,`RECIPIENT_COUNTRY`) ";
        this.jdbcTemplate.execute(sql);

        sql = "TRUNCATE TABLE `fasp`.`tmp_erp_shipment`";
        this.jdbcTemplate.update(sql);

//         Insert into tmp_erp_shipment
        sql = "LOAD DATA LOCAL INFILE '" + shipmentDataFilePath + "' INTO TABLE `tmp_erp_shipment` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES (`KN_SHIPMENT_NUMBER`,`ORDER_NUMBER`,`PRIME_LINE_NO`,`BATCH_NO`,`ITEM_ID`,`EXPIRATION_DATE`,`SHIPPED_QUANTITY`,`DELIVERED_QUANTITY`,`STATUS_NAME`,`EXTERNAL_STATUS_STAGE`,`ACTUAL_SHIPMENT_DATE`,`ACTUAL_DELIVERY_DATE`) ";
        this.jdbcTemplate.execute(sql);

        sql = "UPDATE rm_erp_order o "
                + "SET o.`FLAG`=0 "
                + "WHERE o.`FLAG`=1;";
        this.jdbcTemplate.update(sql);

        // Update parent RO created date
        sql = "INSERT IGNORE INTO rm_erp_order "
                + " SELECT NULL,RO_NO,RO_PRIME_LINE_NO,ORDER_NUMBER,PRIME_LINE_NO, "
                + " ORDER_TYPE_IND,IFNULL(DATE_FORMAT(ORDER_ENTRY_DATE,'%Y-%m-%d'),NULL),PARENT_RO,IFNULL(DATE_FORMAT(PARENT_ORDER_ENTRY_DATE,'%Y-%m-%d %H:%i:%s'),NULL),LEFT(ITEM_ID, 12), "
                + " IF(LENGTH(ITEM_ID)=15,ITEM_ID,NULL),ORDERED_QTY,IFNULL(DATE_FORMAT(PO_RELEASED_FOR_FULFILLMENT_DATE,'%Y-%m-%d'),NULL), "
                + " IFNULL(DATE_FORMAT(LATEST_ESTIMATED_DELIVERY_DATE,'%Y-%m-%d'),NULL), "
                + " IFNULL(DATE_FORMAT(REQ_DELIVERY_DATE,'%Y-%m-%d'),NULL), "
                + " IFNULL(DATE_FORMAT(REVISED_AGREED_DELIVERY_DATE,'%Y-%m-%d'),NULL),ITEM_SUPPLIER_NAME,UNIT_PRICE, "
                + " COALESCE(TOTAL_ACTUAL_FREIGHT_COST,FREIGHT_ESTIMATE,SHIPPING_CHARGES),CARRIER_SERVICE_CODE,RECIPIENT_NAME, "
                + " RECIPIENT_COUNTRY,EXTERNAL_STATUS_STAGE,1,?,1 "
                + " FROM tmp_erp_order t ";

        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rows1---" + rows);

        sql = "UPDATE rm_erp_order o "
                + "LEFT JOIN tmp_erp_order t ON t.`ORDER_NUMBER`=o.`ORDER_NO` AND t.`PRIME_LINE_NO`=o.`PRIME_LINE_NO` "
                + "SET "
                + "o.`RO_NO`=t.`RO_NO`, "
                + "o.`RO_PRIME_LINE_NO`=t.`RO_PRIME_LINE_NO`, "
                + "o.`ORDER_TYPE`=t.`ORDER_TYPE_IND`, "
                + "o.`CREATED_DATE`=IFNULL(DATE_FORMAT(t.`ORDER_ENTRY_DATE`,'%Y-%m-%d'),NULL), "
                + "o.`PARENT_RO`=t.`PARENT_RO`, "
                + "o.`PARENT_CREATED_DATE`=IFNULL(DATE_FORMAT(t.`PARENT_ORDER_ENTRY_DATE`,'%Y-%m-%d'),NULL), "
                + "o.`PLANNING_UNIT_SKU_CODE`=LEFT(t.`ITEM_ID`,12), "
                + "o.`PROCUREMENT_UNIT_SKU_CODE`=IF(LENGTH(t.`ITEM_ID`)=15,t.`ITEM_ID`,NULL), "
                + "o.`QTY`=t.`ORDERED_QTY`, "
                + "o.`ORDERD_DATE`=IFNULL(DATE_FORMAT(t.`PO_RELEASED_FOR_FULFILLMENT_DATE`,'%Y-%m-%d'),NULL), "
                + "o.`CURRENT_ESTIMATED_DELIVERY_DATE`=IFNULL(DATE_FORMAT(t.`LATEST_ESTIMATED_DELIVERY_DATE`,'%Y-%m-%d'),NULL), "
                + "o.`REQ_DELIVERY_DATE`=IFNULL(DATE_FORMAT(t.`REQ_DELIVERY_DATE`,'%Y-%m-%d'),NULL), "
                + "o.`AGREED_DELIVERY_DATE`=IFNULL(DATE_FORMAT(t.`REVISED_AGREED_DELIVERY_DATE`,'%Y-%m-%d'),NULL), "
                + "o.`SUPPLIER_NAME`=t.`ITEM_SUPPLIER_NAME`, "
                + "o.`PRICE`=t.`UNIT_PRICE`, "
                + "o.`SHIPPING_COST`=COALESCE(t.`TOTAL_ACTUAL_FREIGHT_COST`,t.`FREIGHT_ESTIMATE`,t.`SHIPPING_CHARGES`), "
                + "o.`SHIP_BY`=t.`CARRIER_SERVICE_CODE`, "
                + "o.`RECPIENT_NAME`=t.`RECIPIENT_NAME`, "
                + "o.`RECPIENT_COUNTRY`=t.`RECIPIENT_COUNTRY`, "
                + "o.`STATUS`=t.`EXTERNAL_STATUS_STAGE`, "
                + "o.`LAST_MODIFIED_DATE`=NOW(), "
                + "o.`FLAG`=1;";

        rows = this.jdbcTemplate.update(sql);
        System.out.println("rows1 update---" + rows);

        sql = "INSERT IGNORE INTO rm_erp_shipment "
                + "SELECT  NULL,NULL,KN_SHIPMENT_NUMBER,ORDER_NUMBER,PRIME_LINE_NO,BATCH_NO,IFNULL(DATE_FORMAT(EXPIRATION_DATE,'%Y-%m-%d %H:%i:%s'),NULL),ITEM_ID,SHIPPED_QUANTITY, "
                + "DELIVERED_QUANTITY,IFNULL(DATE_FORMAT(ACTUAL_SHIPMENT_DATE,'%Y-%m-%d %H:%i:%s'),NULL),IFNULL(DATE_FORMAT(ACTUAL_DELIVERY_DATE,'%Y-%m-%d %H:%i:%s'),NULL),EXTERNAL_STATUS_STAGE,?,1 "
                + "FROM  tmp_erp_shipment t;";
//        sql = "REPLACE INTO rm_erp_shipment "
//                + "SELECT  NULL,NULL,KN_SHIPMENT_NUMBER,ORDER_NUMBER,PRIME_LINE_NO,BATCH_NO,IFNULL('2020-05-24 23:56:45',NULL),ITEM_ID,SHIPPED_QUANTITY, "
//                + "DELIVERED_QUANTITY,IFNULL('2020-05-24 23:56:45',NULL),IFNULL('2020-05-24 23:56:45',NULL),EXTERNAL_STATUS_STAGE "
//                + "FROM  tmp_erp_shipment t;";
        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rows2---" + rows);

        sql = "UPDATE rm_erp_shipment t "
                + "LEFT JOIN rm_erp_order o ON o.`ORDER_NO`=t.`ORDER_NO` AND o.`PRIME_LINE_NO`=t.`PRIME_LINE_NO` "
                + "SET t.`ERP_ORDER_ID`=o.`ERP_ORDER_ID`;";

        rows = this.jdbcTemplate.update(sql);
        System.out.println("rows3---" + rows);

        sql = "UPDATE rm_erp_shipment s "
                + "LEFT JOIN tmp_erp_shipment t ON t.`KN_SHIPMENT_NUMBER`=s.`KN_SHIPMENT_NO` "
                + "AND t.`ORDER_NUMBER`=s.`ORDER_NO` "
                + "AND t.`PRIME_LINE_NO`=s.`PRIME_LINE_NO` "
                + "AND t.`BATCH_NO`=s.`BATCH_NO` "
                + "SET s.`FLAG`=1, "
                + "s.`LAST_MODIFIED_DATE`=?, "
                + "s.`EXPIRY_DATE`=IFNULL(DATE_FORMAT(t.`EXPIRATION_DATE`,'%Y-%m-%d %H:%i:%s'),NULL), "
                + "s.`PROCUREMENT_UNIT_SKU_CODE`=t.`ITEM_ID`, "
                + "s.`SHIPPED_QTY`=t.`SHIPPED_QUANTITY`, "
                + "s.`DELIVERED_QTY`=t.`DELIVERED_QUANTITY`, "
                + "s.`ACTUAL_SHIPMENT_DATE`=IFNULL(DATE_FORMAT(t.`ACTUAL_SHIPMENT_DATE`,'%Y-%m-%d %H:%i:%s'),NULL), "
                + "s.`ACTUAL_DELIVERY_DATE`=IFNULL(DATE_FORMAT(t.`ACTUAL_DELIVERY_DATE`,'%Y-%m-%d %H:%i:%s'),NULL), "
                + "s.`STATUS`=t.`STATUS`;";

        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rows2 update---" + rows);

        sql = "INSERT INTO rm_shipment_trans "
                + "SELECT NULL,m.`SHIPMENT_ID`,pu.`PLANNING_UNIT_ID`,o.`CURRENT_ESTIMATED_DELIVERY_DATE`,s.`SUGGESTED_QTY`,1, "
                + "pru.`PROCUREMENT_UNIT_ID`,sup.`SUPPLIER_ID`,o.`QTY`,o.`PRICE`,(o.`QTY`*o.`PRICE`),o.`SHIP_BY`,o.`SHIPPING_COST`,o.`ORDERD_DATE`, "
                + "es.`ACTUAL_SHIPMENT_DATE`,es.`ACTUAL_DELIVERY_DATE`,sm.`SHIPMENT_STATUS_ID`,NULL,10,0,1,1,?,1,1,o.`ORDER_NO`,o.`PRIME_LINE_NO` "
                + "FROM rm_erp_order o "
                + "LEFT JOIN rm_shipment_trans_erp_order_mapping m ON m.`ERP_ORDER_ID`=o.`ERP_ORDER_ID` "
                + "LEFT JOIN rm_shipment s ON s.`SHIPMENT_ID`=m.`SHIPMENT_ID` "
                + "LEFT JOIN rm_procurement_agent_planning_unit pu ON pu.`SKU_CODE`=o.`PLANNING_UNIT_SKU_CODE` AND pu.`PROCUREMENT_AGENT_ID`=1 "
                + "LEFT JOIN rm_procurement_agent_procurement_unit pru ON pru.`SKU_CODE`=o.`PROCUREMENT_UNIT_SKU_CODE` AND pru.`PROCUREMENT_AGENT_ID`=1 "
                + "LEFT JOIN ap_label l ON l.`LABEL_EN`=o.`SUPPLIER_NAME` "
                + "LEFT JOIN rm_supplier sup ON sup.`LABEL_ID`=l.`LABEL_ID` "
                + "LEFT JOIN rm_erp_shipment es ON es.`ERP_ORDER_ID`=o.`ERP_ORDER_ID` "
                + "LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=o.`STATUS` "
                + "WHERE m.`SHIPMENT_ID` IS NOT NULL "
                + "GROUP BY o.`ERP_ORDER_ID`";
        rows = this.jdbcTemplate.update(sql, curDate);
        System.out.println("rm_shipment_trans---" + rows);

        return rows;
    }

}
