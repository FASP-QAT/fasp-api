USE `fasp`;
DROP procedure IF EXISTS `shipmentDetails`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`shipmentDetails`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetails`(VAR_USER_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS TEXT, VAR_BUDGET_IDS TEXT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 19 Part 1
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
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "   pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   s.SHIPMENT_ID, ");
    SET @sqlString = CONCAT(@sqlString, "   p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID `BUDGET_LABEL_ID`, b.LABEL_EN `BUDGET_LABEL_EN`, b.LABEL_FR `BUDGET_LABEL_FR`, b.LABEL_SP `BUDGET_LABEL_SP`, b.LABEL_PR `BUDGET_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_FR `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   st.SHIPMENT_QTY, st.ORDER_NO, st.LOCAL_PROCUREMENT, st.ERP_FLAG, st.EMERGENCY_ORDER, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, ");
    SET @sqlString = CONCAT(@sqlString, "   (IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   (IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   (IFNULL(st.PRODUCT_COST,0) * s.CONVERSION_RATE_TO_USD + IFNULL(st.FREIGHT_COST,0) * s.CONVERSION_RATE_TO_USD) `TOTAL_COST`, ");
    SET @sqlString = CONCAT(@sqlString, "   st.NOTES ");
    SET @sqlString = CONCAT(@sqlString, "FROM ");
    SET @sqlString = CONCAT(@sqlString, "   ( ");
    SET @sqlString = CONCAT(@sqlString, "   SELECT ");
    SET @sqlString = CONCAT(@sqlString, "       p.PROGRAM_ID, s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
    SET @sqlString = CONCAT(@sqlString, "   FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "   LEFT JOIN rm_shipment s ON p.PROGRAM_ID=s.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "   LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.VERSION_ID<=CAST(COALESCE(IF(@versionId=-1,p.CURRENT_VERSION_ID, @versionId), p.CURRENT_VERSION_ID) AS UNSIGNED) ");
    SET @sqlString = CONCAT(@sqlString, "   WHERE ");
    SET @sqlString = CONCAT(@sqlString, "       TRUE AND p.ACTIVE AND ");
    SET @sqlString = CONCAT(@sqlString, "       (LENGTH(@realmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @realmCountryIds)) AND ");
    SET @sqlString = CONCAT(@sqlString, "       (LENGTH(@programIds)=0 OR FIND_IN_SET(p.PROGRAM_ID, @programIds)) ");
    SET @sqlString = CONCAT(@sqlString, "   GROUP BY s.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString, ") AS s ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON s.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_shipment_status ss on st.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa on st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_budget b ON st.BUDGET_ID=b.BUDGET_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=p.PROGRAM_ID AND st.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "   TRUE ");
    SET @sqlString = CONCAT(@sqlString, "   AND st.ACTIVE AND st.ACCOUNT_FLAG AND ppu.ACTIVE AND pu.ACTIVE AND st.SHIPMENT_STATUS_ID!=8 ");
    SET @sqlString = CONCAT(@sqlString, "   AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    SET @sqlString = CONCAT(@sqlString, "   AND (LENGTH(@planningUnitIds)=0  OR FIND_IN_SET(st.PLANNING_UNIT_ID, @planningUnitIds)) ");
    SET @sqlString = CONCAT(@sqlString, "   AND (");
    SET @sqlString = CONCAT(@sqlString, "       (@reportView=1 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.FUNDING_SOURCE_ID, @fundingSourceProcurementAgentIds))) OR ");
    SET @sqlString = CONCAT(@sqlString, "       (@reportView=2 AND (LENGTH(@fundingSourceProcurementAgentIds)=0 OR FIND_IN_SET(st.PROCUREMENT_AGENT_ID, @fundingSourceProcurementAgentIds)))");
    SET @sqlString = CONCAT(@sqlString, "   )");
    SET @sqlString = CONCAT(@sqlString, "   AND ((@reportView=1 AND (LENGTH(@budgetIds)=0 OR FIND_IN_SET(st.BUDGET_ID, @budgetIds))) OR @reportView=2) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "GROUP BY s.SHIPMENT_ID");
    
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `shipmentDetailsFundingSource`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`shipmentDetailsFundingSource`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetailsFundingSource`(VAR_USER_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS TEXT, VAR_BUDGET_IDS TEXT)
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
    SET @sqlString = CONCAT(@sqlString, "   AND ((@reportView=1 AND (LENGTH(@budgetIds)=0 OR FIND_IN_SET(st.BUDGET_ID, @budgetIds))) OR @reportView=2) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "GROUP BY IF(@reportView=1, fs.FUNDING_SOURCE_ID, pa.PROCUREMENT_AGENT_ID) ");

    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
     
END$$

DELIMITER ;
;

USE `fasp`;
DROP procedure IF EXISTS `shipmentDetailsMonth`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`shipmentDetailsMonth`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `shipmentDetailsMonth`(VAR_USER_ID INT(10), VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_REPORT_VIEW INT(10), VAR_FUNDING_SOURCE_PROCUREMENT_AGENT_IDS TEXT, VAR_BUDGET_IDS TEXT)
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
    SET @sqlString = CONCAT(@sqlString, "       AND ((@reportView=1 AND (LENGTH(@budgetIds)=0 OR FIND_IN_SET(st.BUDGET_ID, @budgetIds))) OR @reportView=2) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, ") AS s1 ON mn.MONTH =s1.DT  ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN @startDate AND @stopDate  ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY mn.MONTH ");
    
    PREPARE S1 FROM @sqlString; 
    EXECUTE S1;
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDetails.erpLinkedShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ERP-linked Shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expéditions liées au système ERP');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Envíos vinculados a ERP');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Remessas ligadas ao ERP');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipementDetails.quantityOfShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Quantity of Shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité d\'envois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de envíos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade de remessas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipementDetails.costOfShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Cost of Shipments');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût des expéditions');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo de los envíos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo dos Remessas');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDetails.planningUnitInfo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'When PU is single‑select, the graph shows shipment quantity by status. When PU is multi‑select, it shows shipment cost by status.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'En mode de sélection unique, le graphique affiche la quantité expédiée par statut. En mode de sélection multiple, il affiche le coût de l\'expédition par statut.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'En mode de sélection unique, le graphique affiche la quantité expédiée par statut. En mode de sélection multiple, il affiche le coût de l\'expédition par statut.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quando a unidade de compra (PU) é de seleção única, o gráfico mostra a quantidade de envios por estado. Quando a unidade de compra (PU) é de seleção múltipla, mostra o custo da expedição por estado.');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDetails.costUSDFS','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total cost per Funding Source across all programs and PUs selected');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total par source de financement pour l'ensemble des programmes et des unités de programme sélectionnés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total por fuente de financiamiento en todos los programas y unidades de gestión seleccionadas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total por fonte de financiamento em todos os programas e unidades de investigação selecionadas.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDetails.costUSDPA','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total cost per Procurement Agent across all programs and PUs selected');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total par agent d\'approvisionnement pour l\'ensemble des programmes et unités de production sélectionnés');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total por agente de adquisiciones en todos los programas y PU seleccionados');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total por agente de compras em todos os programas e unidades de compras selecionadas.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDetails.totalCostUSD','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Total Cost = Planning Unit Cost + Freight Cost');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Coût total = Coût unitaire de planification + Frais de transport');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Costo total = Costo unitario de planificación + Costo de flete');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Custo total = Custo unitário de planeamento + Custo do frete');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDetails.noData','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'There is no available shipment data for the planning unit(s) during the selected report period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucune donnée d\'expédition n\'est disponible pour la ou les unités de planification pendant la période de rapport sélectionnée.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No hay datos de envío disponibles para las unidades de planificación durante el período del informe seleccionado');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Não existem dados de expedição disponíveis para a(s) unidade(s) de planeamento durante o período de relatório selecionado.');-- pr"