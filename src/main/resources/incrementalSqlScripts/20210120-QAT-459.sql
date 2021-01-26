/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 20-Jan-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.submitted','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Submitted (Submitted, Approved)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Soumis (soumis, approuvé)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Enviado (Enviado, Aprobado)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Enviado (Enviado, Aprovado)');

UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planned (Planned, On-hold)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3733';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planificado (planificado, en espera)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3734';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planifié (planifié, en attente)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3735';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planejado (planejado, em espera)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3736';

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Remove all Submitted Shipments that are not within lead time' WHERE `STATIC_LABEL_LANGUAGE_ID`='4337';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Eliminar todos los envíos enviados que no estén dentro del plazo de entrega' WHERE `STATIC_LABEL_LANGUAGE_ID`='4338';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Supprimer tous les envois soumis qui ne sont pas dans les délais' WHERE `STATIC_LABEL_LANGUAGE_ID`='4339';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Remova todas as remessas enviadas que não estão dentro do prazo de entrega' WHERE `STATIC_LABEL_LANGUAGE_ID`='4340';


USE `fasp`;
DROP PROCEDURE IF EXISTS `procurementAgentShipmentReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `procurementAgentShipmentReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 13
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- procurementAgentIds is a list of the ProcurementAgents that you want to run the report for. Empty for all.
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
    -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
	SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
	SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
	SET @sqlString = CONCAT(@sqlString,"	pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
	SET @sqlString = CONCAT(@sqlString,"FROM ");
	SET @sqlString = CONCAT(@sqlString,"	(");
	SET @sqlString = CONCAT(@sqlString,"	SELECT ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"	WHERE ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,") AS s ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_procurement_agent pa ON st.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString,"WHERE ");
	SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
	SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) OR @includePlannedShipments=1) ");
	SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
		SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
	END IF;
    IF LENGTH(VAR_PROCUREMENT_AGENT_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString,"	AND (st.PROCUREMENT_AGENT_ID IN (",VAR_PROCUREMENT_AGENT_IDS,")) ");
    END IF;
	
	SET @sqlString = CONCAT(@sqlString,"GROUP BY st.PROCUREMENT_AGENT_ID, st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;


USE `fasp`;
DROP PROCEDURE IF EXISTS `fundingSourceShipmentReport`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `fundingSourceShipmentReport`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_FUNDING_SOURCE_IDS VARCHAR(255), VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 15
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
	-- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- fundingSourceIds is a list of the FundingSources that you want to run the report for. Empty for all.
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means the report will include all shipments that are Active and not Cancelled
    -- includePlannedShipments = 0 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
	SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
	SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
	SET @sqlString = CONCAT(@sqlString,"	fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
	SET @sqlString = CONCAT(@sqlString,"FROM ");
	SET @sqlString = CONCAT(@sqlString,"	(");
	SET @sqlString = CONCAT(@sqlString,"	SELECT ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"	WHERE ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,") AS s ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_funding_source fs ON st.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString,"WHERE ");
	SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
	SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) OR @includePlannedShipments=1) ");
	SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_FUNDING_SOURCE_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString,"	AND (st.FUNDING_SOURCE_ID IN (",VAR_FUNDING_SOURCE_IDS,")) ");
    END IF;
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
		SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
	END IF;
	
	SET @sqlString = CONCAT(@sqlString,"GROUP BY st.FUNDING_SOURCE_ID, st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;


USE `fasp`;
DROP PROCEDURE IF EXISTS `aggregateShipmentByProduct`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `aggregateShipmentByProduct`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT, VAR_VERSION_ID INT, VAR_PLANNING_UNIT_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT)
BEGIN
	
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 24 
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- programId must be a single Program cannot be muti-program select or -1 for all programs
    -- versionId must be the actual version that you want to refer to for this report or -1 in which case it will automatically take the latest version (not approved or final just latest)
    -- report will be run using startDate and stopDate based on Delivered Date or Expected Delivery Date
    -- planningUnitIds is provided as a list of planningUnitId's or empty for all
    -- includePlannedShipments = 1 means only Approve, Shipped, Arrived, Delivered statuses will be included in the report
    -- includePlannedShipments = 0 means the report will include all shipments that are Active and not Cancelled
    -- FreightCost and ProductCost are converted to USD
    -- FreightPerc is in SUM(FREIGHT_COST)/SUM(PRODUCT_COST) for that ProcurementAgent and that PlanningUnit
    
    SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
	SET @startDate = CONCAT(VAR_START_DATE,' 00:00:00');
	SET @stopDate = CONCAT(VAR_STOP_DATE, ' 23:59:59');
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;

	SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString,"SELECT ");
	SET @sqlString = CONCAT(@sqlString,"	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
	SET @sqlString = CONCAT(@sqlString,"	SUM(st.SHIPMENT_QTY) QTY, SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD) `PRODUCT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD) `FREIGHT_COST`, SUM(st.FREIGHT_COST*s.CONVERSION_RATE_TO_USD)/SUM(st.PRODUCT_COST*s.CONVERSION_RATE_TO_USD)*100 `FREIGHT_PERC` ");
	SET @sqlString = CONCAT(@sqlString,"FROM ");
	SET @sqlString = CONCAT(@sqlString,"	(");
	SET @sqlString = CONCAT(@sqlString,"	SELECT ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID, s.SHIPMENT_ID, s.CONVERSION_RATE_TO_USD, MAX(st.VERSION_ID) MAX_VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"	FROM rm_shipment s ");
	SET @sqlString = CONCAT(@sqlString,"	LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"	WHERE ");
	SET @sqlString = CONCAT(@sqlString,"		s.PROGRAM_ID=@programId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.VERSION_ID<=@versionId ");
	SET @sqlString = CONCAT(@sqlString,"		AND st.SHIPMENT_TRANS_ID IS NOT NULL ");
	SET @sqlString = CONCAT(@sqlString,"	GROUP BY s.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,") AS s ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment s1 ON s.SHIPMENT_ID=s1.SHIPMENT_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID ");
	SET @sqlString = CONCAT(@sqlString,"LEFT JOIN vw_planning_unit pu ON st.PLANNING_UNIT_ID = pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString,"LEFT JOIN rm_program_planning_unit ppu ON st.PLANNING_UNIT_ID = ppu.PLANNING_UNIT_ID AND s1.PROGRAM_ID=ppu.PROGRAM_ID ");
	SET @sqlString = CONCAT(@sqlString,"WHERE ");
	SET @sqlString = CONCAT(@sqlString,"	st.ACTIVE AND ppu.ACTIVE AND pu.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString,"	AND st.SHIPMENT_STATUS_ID != 8 ");
	SET @sqlString = CONCAT(@sqlString,"	AND ((@includePlannedShipments=0 && st.SHIPMENT_STATUS_ID in (3,4,5,6,7)) OR @includePlannedShipments=1) ");
	SET @sqlString = CONCAT(@sqlString,"	AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN 
		SET @sqlString = CONCAT(@sqlString,"	AND (st.PLANNING_UNIT_ID IN (",VAR_PLANNING_UNIT_IDS,")) ");
	END IF;
	SET @sqlString = CONCAT(@sqlString,"GROUP BY st.PLANNING_UNIT_ID");
    
    PREPARE s1 FROM @sqlString;
    EXECUTE s1;
END$$

DELIMITER ;