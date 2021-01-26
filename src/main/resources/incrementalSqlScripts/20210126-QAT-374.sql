/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 26-Jan-2021
 */

DELIMITER $$

USE `fasp`$$

DROP PROCEDURE IF EXISTS `budgetReport`$$

CREATE DEFINER=`faspUser`@`%` PROCEDURE `budgetReport`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_FUNDING_SOURCE_IDS TEXT)
BEGIN

   
SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
   
    SELECT IF(@versionId=-1, MAX(pv.VERSION_ID), @versionId) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
   
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
SET @sqlString = CONCAT(@sqlString, " b.BUDGET_ID, b.BUDGET_CODE, b.LABEL_ID, b.LABEL_EN, b.LABEL_FR, b.LABEL_SP, b.LABEL_PR, ");
SET @sqlString = CONCAT(@sqlString, " fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
SET @sqlString = CONCAT(@sqlString, " p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    c.CURRENCY_ID, c.CURRENCY_CODE, c.LABEL_ID `CURRENCY_LABEL_ID`, c.LABEL_EN `CURRENCY_LABEL_EN`, c.LABEL_FR `CURRENCY_LABEL_FR`, c.LABEL_SP `CURRENCY_LABEL_SP`, c.LABEL_PR `CURRENCY_LABEL_PR`, ");
SET @sqlString = CONCAT(@sqlString, " b.BUDGET_AMT/1 `BUDGET_AMT`, IFNULL(ua.PLANNED_BUDGET,0)/b.CONVERSION_RATE_TO_USD `PLANNED_BUDGET_AMT`, IFNULL(ua.ORDERED_BUDGET,0)/b.CONVERSION_RATE_TO_USD `ORDERED_BUDGET_AMT`,  b.START_DATE, b.STOP_DATE ");
SET @sqlString = CONCAT(@sqlString, "FROM vw_budget b ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON b.PROGRAM_ID=p.PROGRAM_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_funding_source fs ON b.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_currency c ON c.CURRENCY_ID=b.CURRENCY_ID ");
SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ");
SET @sqlString = CONCAT(@sqlString, " ( ");
SET @sqlString = CONCAT(@sqlString, " SELECT ");
SET @sqlString = CONCAT(@sqlString, " st.BUDGET_ID, ");
SET @sqlString = CONCAT(@sqlString, " SUM(IF(st.SHIPMENT_STATUS_ID IN (1), ((IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0))*s1.CONVERSION_RATE_TO_USD),0))/1 `PLANNED_BUDGET`, ");
SET @sqlString = CONCAT(@sqlString, " SUM(IF(st.SHIPMENT_STATUS_ID IN (3,4,5,6,7,9), ((IFNULL(st.FREIGHT_COST,0)+IFNULL(st.PRODUCT_COST,0))*s1.CONVERSION_RATE_TO_USD),0))/1 `ORDERED_BUDGET` ");
SET @sqlString = CONCAT(@sqlString, " FROM ");
SET @sqlString = CONCAT(@sqlString, " ( ");
SET @sqlString = CONCAT(@sqlString, " SELECT ");
SET @sqlString = CONCAT(@sqlString, " s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID, s.CONVERSION_RATE_TO_USD ");
SET @sqlString = CONCAT(@sqlString, " FROM rm_shipment s ");
SET @sqlString = CONCAT(@sqlString, " LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
SET @sqlString = CONCAT(@sqlString, " WHERE ");
SET @sqlString = CONCAT(@sqlString, " s.PROGRAM_ID=@programId ");
SET @sqlString = CONCAT(@sqlString, " AND st.VERSION_ID<=@versionId ");
SET @sqlString = CONCAT(@sqlString, " AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
SET @sqlString = CONCAT(@sqlString, " GROUP BY s.SHIPMENT_ID ");
SET @sqlString = CONCAT(@sqlString, " ) s1 ");
SET @sqlString = CONCAT(@sqlString, " LEFT JOIN rm_shipment_trans st ON s1.SHIPMENT_ID=st.SHIPMENT_ID AND s1.MAX_VERSION_ID=st.VERSION_ID ");
SET @sqlString = CONCAT(@sqlString, " WHERE st.ACTIVE AND st.SHIPMENT_STATUS_ID !=8 ");
SET @sqlString = CONCAT(@sqlString, " GROUP BY st.BUDGET_ID ");
SET @sqlString = CONCAT(@sqlString, ") as ua ON ua.BUDGET_ID=b.BUDGET_ID ");
SET @sqlString = CONCAT(@sqlString, "WHERE ");
SET @sqlString = CONCAT(@sqlString, " b.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, " AND (b.START_DATE BETWEEN @startDate AND @stopDate OR b.STOP_DATE BETWEEN @startDate AND @stopDate OR @startDate BETWEEN b.START_DATE AND b.STOP_DATE) ");
    IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "   AND b.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,") ");
    END IF;
SET @sqlString = CONCAT(@sqlString, " AND b.ACTIVE");
   
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
   
END$$

DELIMITER ;


-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
-- Label script
-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.million','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Million');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Million');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Millón');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Milhão');-- pr


