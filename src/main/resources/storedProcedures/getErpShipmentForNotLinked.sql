CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getErpShipmentForNotLinked`(
    VAR_REALM_COUNTRY_ID INT(10), 
    VAR_PRODUCT_CATEGORY_IDS TEXT, 
    VAR_PLANNING_UNIT_IDS TEXT, 
    VAR_REALM_ID INT(10)
    )
BEGIN
    SET @productCategoryIds = VAR_PRODUCT_CATEGORY_IDS;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @realmId = VAR_REALM_ID;
    SET @realmCountryId = VAR_REALM_COUNTRY_ID;
    
    SELECT GROUP_CONCAT(pc2.PRODUCT_CATEGORY_ID) INTO @finalProductCategoryIds FROM rm_product_category pc LEFT JOIN rm_product_category pc2 ON pc2.SORT_ORDER LIKE CONCAT(pc.SORT_ORDER,"%") WHERE FIND_IN_SET(pc.PRODUCT_CATEGORY_ID, @productCategoryIds);
    
    SELECT group_concat(COALESCE(ac.RECEPIENT_NAME, c.LABEL_EN)) INTO @recepientCountryList
    FROM rm_realm_country rc
    LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID
    LEFT JOIN tr_artmis_country ac ON rc.REALM_COUNTRY_ID=ac.REALM_COUNTRY_ID
    WHERE (rc.REALM_COUNTRY_ID=@realmCountryId OR @realmCountryId=-1) AND rc.REALM_ID=@realmId;
    
    SELECT 
        o.ORDER_NO, o.PRIME_LINE_NO, o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ERP_ORDER_ID,
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`,
        COALESCE(o.CURRENT_ESTIMATED_DELIVERY_DATE,o.AGREED_DELIVERY_DATE, o.REQ_DELIVERY_DATE) `EXPECTED_DELIVERY_DATE`, o.`STATUS`, o.QTY,papu.SKU_CODE
    FROM rm_erp_order o 
    LEFT JOIN (SELECT MAX(o.ERP_ORDER_ID) as ERP_ORDER_ID FROM rm_erp_order o GROUP BY o.`RO_NO`,o.`RO_PRIME_LINE_NO`,o.ORDER_NO, o.PRIME_LINE_NO) o1 ON o.ERP_ORDER_ID=o1.ERP_ORDER_ID 
    LEFT JOIN rm_manual_tagging mt ON mt.ORDER_NO=o.ORDER_NO AND mt.PRIME_LINE_NO=o.PRIME_LINE_NO AND mt.ACTIVE 
    LEFT JOIN rm_procurement_agent_planning_unit papu ON o.PLANNING_UNIT_SKU_CODE=LEFT(papu.SKU_CODE,12) 
    LEFT JOIN vw_planning_unit pu ON papu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID 
    LEFT JOIN rm_shipment_status_mapping sm ON sm.`EXTERNAL_STATUS_STAGE`=o.`STATUS`
    WHERE  
        o1.ERP_ORDER_ID IS NOT NULL  
        AND mt.SHIPMENT_ID IS NULL  
        AND o.RECPIENT_COUNTRY!=''  
        AND find_in_set(o.RECPIENT_COUNTRY,@recepientCountryList)
        AND sm.`SHIPMENT_STATUS_MAPPING_ID` NOT IN (1,3,5,7,9,10,13,15)
        AND (@finalProductCategoryIds IS NULL OR LENGTH(@finalProductCategoryIds)=0 OR FIND_IN_SET(fu.PRODUCT_CATEGORY_ID, @finalProductCategoryIds))
        AND (LENGTH(@planningUnitIds)=0 OR FIND_IN_SET(papu.PLANNING_UNIT_ID, @planningUnitIds));
END
