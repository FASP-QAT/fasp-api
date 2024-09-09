CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getDashboardShipmentDetailsReportBy`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT, VAR_DISPLAY_SHIPMENTS_BY INT)
BEGIN

    SELECT p.CURRENT_VERSION_ID INTO @varVersionId FROM vw_program p WHERE p.PROGRAM_ID = VAR_PROGRAM_ID;
    
    IF VAR_DISPLAY_SHIPMENTS_BY = 1 THEN -- FundingSource
        SELECT 
            fs.`FUNDING_SOURCE_ID` `REPORT_BY_ID`, fs.FUNDING_SOURCE_CODE `REPORT_BY_CODE`, fs.LABEL_ID `RB_LABEL_ID`, fs.LABEL_EN `RB_LABEL_EN`, fs.LABEL_FR `RB_LABEL_FR`, fs.LABEL_SP `RB_LABEL_SP`, fs.LABEL_PR `RB_LABEL_PR`, 
            COUNT(st.SHIPMENT_ID) `ORDER_COUNT`, 
            SUM(st.SHIPMENT_QTY) `QUANTITY`, 
            SUM((IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD) `COST` 
        FROM 
            ( 
            SELECT 
                s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD 
            FROM rm_shipment s 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID 
            WHERE 
                s.PROGRAM_ID=VAR_PROGRAM_ID
                AND st.VERSION_ID<=@varVersionId
                AND st.SHIPMENT_TRANS_ID IS NOT NULL 
            GROUP BY s.SHIPMENT_ID 
        ) AS s 
        LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID 
        LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
        LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID 
        WHERE 
            st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE 
            AND st.SHIPMENT_STATUS_ID!=8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN VAR_START_DATE AND VAR_STOP_DATE 
        GROUP BY st.FUNDING_SOURCE_ID;
    ELSEIF VAR_DISPLAY_SHIPMENTS_BY = 2 THEN -- ProcurementAgent
        SELECT 
            pa.`PROCUREMENT_AGENT_ID` `REPORT_BY_ID`, pa.PROCUREMENT_AGENT_CODE `REPORT_BY_CODE`, pa.LABEL_ID `RB_LABEL_ID`, pa.LABEL_EN `RB_LABEL_EN`, pa.LABEL_FR `RB_LABEL_FR`, pa.LABEL_SP `RB_LABEL_SP`, pa.LABEL_PR `RB_LABEL_PR`, 
            COUNT(st.SHIPMENT_ID) `ORDER_COUNT`, 
            SUM(st.SHIPMENT_QTY) `QUANTITY`, 
            SUM((IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD) `COST` 
        FROM 
            ( 
            SELECT 
                s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD 
            FROM rm_shipment s 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID 
            WHERE 
                s.PROGRAM_ID=VAR_PROGRAM_ID
                AND st.VERSION_ID<=@varVersionId
                AND st.SHIPMENT_TRANS_ID IS NOT NULL 
            GROUP BY s.SHIPMENT_ID 
        ) AS s 
        LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID 
        LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
        LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID 
        WHERE 
            st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE 
            AND st.SHIPMENT_STATUS_ID!=8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN VAR_START_DATE AND VAR_STOP_DATE 
        GROUP BY st.PROCUREMENT_AGENT_ID;
    ELSEIF VAR_DISPLAY_SHIPMENTS_BY = 3 THEN -- Status
        SELECT 
            ss.`SHIPMENT_STATUS_ID` `REPORT_BY_ID`, ss.LABEL_EN `REPORT_BY_CODE`, ss.LABEL_ID `RB_LABEL_ID`, ss.LABEL_EN `RB_LABEL_EN`, ss.LABEL_FR `RB_LABEL_FR`, ss.LABEL_SP `RB_LABEL_SP`, ss.LABEL_PR `RB_LABEL_PR`, 
            COUNT(st.SHIPMENT_ID) `ORDER_COUNT`, 
            SUM(st.SHIPMENT_QTY) `QUANTITY`, 
            SUM((IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD) `COST` 
        FROM 
            ( 
            SELECT 
                s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD 
            FROM rm_shipment s 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID 
            WHERE 
                s.PROGRAM_ID=VAR_PROGRAM_ID
                AND st.VERSION_ID<=@varVersionId
                AND st.SHIPMENT_TRANS_ID IS NOT NULL 
            GROUP BY s.SHIPMENT_ID 
        ) AS s 
        LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID 
        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
        LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID 
        LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
        LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID 
        WHERE 
            st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE 
            AND st.SHIPMENT_STATUS_ID!=8 
            AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN VAR_START_DATE AND VAR_STOP_DATE 
        GROUP BY st.SHIPMENT_STATUS_ID;
    END IF;
END