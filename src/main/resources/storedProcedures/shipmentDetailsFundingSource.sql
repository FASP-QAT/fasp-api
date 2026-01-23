CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `shipmentDetailsFundingSource`(VAR_USER_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS TEXT, VAR_BUDGET_IDS TEXT)
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19 Part 2
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
    SET @sqlString = CONCAT(@sqlString, "   IF(@reportView=1, fs.FUNDING_SOURCE_ID, pa.PROCUREMENT_AGENT_ID) `FSPA_ID`,  ");
    SET @sqlString = CONCAT(@sqlString, "   IF(@reportView=1, fs.FUNDING_SOURCE_CODE, pa.PROCUREMENT_AGENT_CODE) `FSPA_CODE`, ");
    SET @sqlString = CONCAT(@sqlString, "   IF(@reportView=1, fs.LABEL_ID, pa.LABEL_ID) `FSPA_LABEL_ID`,  ");
    SET @sqlString = CONCAT(@sqlString, "   IF(@reportView=1, fs.LABEL_EN, pa.LABEL_EN) `FSPA_LABEL_EN`,  ");
    SET @sqlString = CONCAT(@sqlString, "   IF(@reportView=1, fs.LABEL_FR, pa.LABEL_FR) `FSPA_LABEL_FR`,  ");
    SET @sqlString = CONCAT(@sqlString, "   IF(@reportView=1, fs.LABEL_SP, pa.LABEL_SP) `FSPA_LABEL_SP`,  ");
    SET @sqlString = CONCAT(@sqlString, "   IF(@reportView=1, fs.LABEL_PR, pa.LABEL_PR) `FSPA_LABEL_PR`,  ");
    SET @sqlString = CONCAT(@sqlString, "   COUNT(DISTINCT p.REALM_COUNTRY_ID) `COUNTRIES`,  ");
    SET @sqlString = CONCAT(@sqlString, "   COUNT(DISTINCT p.PROGRAM_ID) `PROGRAMS`,  ");
    SET @sqlString = CONCAT(@sqlString, "   COUNT(st.SHIPMENT_ID) `SHIPMENTS`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM((IFNULL(st.PRODUCT_COST,0) + IFNULL(st.FREIGHT_COST,0)) * s.CONVERSION_RATE_TO_USD) `COST`  ");
    SET @sqlString = CONCAT(@sqlString, "FROM  ");
    SET @sqlString = CONCAT(@sqlString, "   (  ");
    SET @sqlString = CONCAT(@sqlString, "   SELECT  ");
    SET @sqlString = CONCAT(@sqlString, "       p.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD  ");
    SET @sqlString = CONCAT(@sqlString, "   FROM vw_program p  ");
    SET @sqlString = CONCAT(@sqlString, "   LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "   LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=CAST(COALESCE(IF(@versionId=-1,p.CURRENT_VERSION_ID, @versionId), p.CURRENT_VERSION_ID) AS UNSIGNED)  ");
    SET @sqlString = CONCAT(@sqlString, "   WHERE  ");
    SET @sqlString = CONCAT(@sqlString, "       TRUE AND p.ACTIVE AND  ");
    SET @sqlString = CONCAT(@sqlString, "       (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) AND  ");
    SET @sqlString = CONCAT(@sqlString, "       (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds))  ");
    SET @sqlString = CONCAT(@sqlString, "   GROUP BY s.SHIPMENT_ID     	 ");
    SET @sqlString = CONCAT(@sqlString, ") AS s  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON s.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_shipment_status ss ON st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "WHERE  ");
    SET @sqlString = CONCAT(@sqlString, "   TRUE  ");
    SET @sqlString = CONCAT(@sqlString, "   AND st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE AND st.SHIPMENT_STATUS_ID!=8  ");
    SET @sqlString = CONCAT(@sqlString, "   AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate  ");
    SET @sqlString = CONCAT(@sqlString, "   AND (LENGTH(@planningUnitIds)=0  OR FIND_IN_SET(st.PLANNING_UNIT_ID, @planningUnitIds))  ");
    SET @sqlString = CONCAT(@sqlString, "   AND ( ");
    SET @sqlString = CONCAT(@sqlString, "       (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) OR  ");
    SET @sqlString = CONCAT(@sqlString, "       (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds))) ");
    SET @sqlString = CONCAT(@sqlString, "   ) ");
    SET @sqlString = CONCAT(@sqlString, "   AND (@reportView=1 AND (LENGTH(@budgetIds)=0 OR FIND_IN_SET(st.BUDGET_ID, @budgetIds))) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "GROUP BY st.FUNDING_SOURCE_ID ");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END