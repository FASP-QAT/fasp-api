CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getDashboardShipmentWithFundingSourceTbd`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT)
BEGIN

    SELECT p.CURRENT_VERSION_ID INTO @varVersionId FROM vw_program p WHERE p.PROGRAM_ID = VAR_PROGRAM_ID;
    
    SELECT 
        pu.`PLANNING_UNIT_ID`, pu.LABEL_ID, pu.LABEL_EN, pu.LABEL_FR, pu.LABEL_SP, pu.LABEL_PR, 
        COUNT(st.SHIPMENT_ID) `COUNT`
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
    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID 
    LEFT JOIN rm_program_planning_unit ppu ON s1.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID 
    WHERE 
        st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE 
        AND st.SHIPMENT_STATUS_ID!=8 
        AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN VAR_START_DATE AND VAR_STOP_DATE 
        AND st.FUNDING_SOURCE_ID=8
    GROUP BY st.PLANNING_UNIT_ID;
END