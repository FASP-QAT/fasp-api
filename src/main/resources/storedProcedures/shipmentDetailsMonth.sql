CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `shipmentDetailsMonth`(VAR_USER_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS TEXT, VAR_BUDGET_IDS TEXT)
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19 Part 3
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- Only Month and Year will be considered for StartDate and StopDate 
    -- One of more RealmCountries and also one or more Programs can be selected
    -- versionId is only to be considerd if a single Program is selected
    -- VersionId can be a valid Version Id for the Program or -1 for last submitted VersionId
    -- PlanningUnitIds is the list of Planning Units you want to run the report for. 
    -- Empty PlanningUnitIds means you want to run the report for all the Planning Units in that Program
    -- ReportView=1 means Funding Sources are selected
    -- ReportView=2 means Procurement Agents are selected
    -- Empty FundingSourceProcurementAgentIds means you want to run the report for all the Funding Sources and all Procurement Agents
    -- BudgetIds is the list of Budgets that you want to filter the report on only to be considerd if reportView = 1
    -- Empty BudgetIds means you want to run the report for all the Budgets
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE finished INTEGER DEFAULT 0;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
        read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR FIND_IN_SET(",IFNULL(curHealthAreaId,-1),",p.HEALTH_AREA_ID))");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1),"=-1 OR p.ORGANISATION_ID=" ,IFNULL(curOrganisationId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");

    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @realmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @programIds = VAR_PROGRAM_IDS;
    SET @versionId = VAR_VERSION_ID;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @reportView = VAR_REPORT_VIEW;
    SET @fundingSourceProcurementAgentIds = VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS;
    SET @budgetIds = VAR_BUDGET_IDS;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT  ");
    SET @sqlString = CONCAT(@sqlString, "   mn.MONTH,  ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`PLANNED_COST`,0)) `PLANNED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`SUBMITTED_COST`,0)) `SUBMITTED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`APPROVED_COST`,0)) `APPROVED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`SHIPPED_COST`,0)) `SHIPPED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`ARRIVED_COST`,0)) `ARRIVED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`RECEIVED_COST`,0)) `RECEIVED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IFNULL(s1.`ONHOLD_COST`,0)) `ONHOLD_COST`  ");
    SET @sqlString = CONCAT(@sqlString, "FROM mn  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN  ");
    SET @sqlString = CONCAT(@sqlString, "   (  ");
    SET @sqlString = CONCAT(@sqlString, "   SELECT  ");
    SET @sqlString = CONCAT(@sqlString, "       CONCAT(LEFT(COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE),7),'-01') `DT`,  ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=1, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `PLANNED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=3, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `SUBMITTED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=4, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `APPROVED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=5, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `SHIPPED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=6, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `ARRIVED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=7, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `RECEIVED_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "       IF(st.SHIPMENT_STATUS_ID=9, (IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD, 0) `ONHOLD_COST`,  ");
    SET @sqlString = CONCAT(@sqlString, "       st.SHIPMENT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "    FROM  ");
    SET @sqlString = CONCAT(@sqlString, "        (  ");
    SET @sqlString = CONCAT(@sqlString, "        SELECT   ");
    SET @sqlString = CONCAT(@sqlString, "            p.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD   ");
    SET @sqlString = CONCAT(@sqlString, "        FROM vw_program p   ");
    SET @sqlString = CONCAT(@sqlString, "        LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID   ");
    SET @sqlString = CONCAT(@sqlString, "        LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=CAST(COALESCE(IF(@versionId=-1,p.CURRENT_VERSION_ID, @versionId), p.CURRENT_VERSION_ID) AS UNSIGNED)   ");
    SET @sqlString = CONCAT(@sqlString, "        WHERE   ");
    SET @sqlString = CONCAT(@sqlString, "            TRUE AND p.ACTIVE AND   ");
    SET @sqlString = CONCAT(@sqlString, "            (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) AND   ");
    SET @sqlString = CONCAT(@sqlString, "            (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds))   ");
    SET @sqlString = CONCAT(@sqlString, "        GROUP BY s.SHIPMENT_ID     	  ");
    SET @sqlString = CONCAT(@sqlString, "    ) AS s  ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_program p ON s.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID  ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "    WHERE  ");
    SET @sqlString = CONCAT(@sqlString, "       TRUE ");
    SET @sqlString = CONCAT(@sqlString, "       AND st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE AND st.SHIPMENT_STATUS_ID!=8 ");
    SET @sqlString = CONCAT(@sqlString, "       AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@planningUnitIds)=0  OR FIND_IN_SET(st.PLANNING_UNIT_ID, @planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "       AND ( ");
    SET @sqlString = CONCAT(@sqlString, "           (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) OR ");
    SET @sqlString = CONCAT(@sqlString, "           (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds))) ");
    SET @sqlString = CONCAT(@sqlString, "       ) ");
    SET @sqlString = CONCAT(@sqlString, "       AND (@reportView=1 AND (LENGTH(@budgetIds)=0 OR FIND_IN_SET(st.BUDGET_ID, @budgetIds))) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, ") AS s1 ON mn.MONTH =s1.DT  ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN @startDate AND @stopDate  ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY mn.MONTH ");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END