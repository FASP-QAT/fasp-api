ALTER TABLE `fasp`.`rm_program` ADD COLUMN `ROAD_FREIGHT_PERC` DECIMAL(5,2) UNSIGNED NULL AFTER `SEA_FREIGHT_PERC`, ADD COLUMN `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` DECIMAL(4,2) UNSIGNED NULL AFTER `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`;
UPDATE rm_program p SET p.ROAD_FREIGHT_PERC=10, p.SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME=0.17,p.LAST_MODIFIED_DATE=now();

USE `fasp`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_program` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`ROAD_FREIGHT_PERC` AS `ROAD_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`,
        `p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    WHERE
        (`p`.`PROGRAM_TYPE_ID` = 1)
    GROUP BY `p`.`PROGRAM_ID`;

USE `fasp`;

CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `faspUser`@`localhost` 
    SQL SECURITY DEFINER
VIEW `vw_dataset` AS
    SELECT 
        `p`.`PROGRAM_ID` AS `PROGRAM_ID`,
        `p`.`PROGRAM_CODE` AS `PROGRAM_CODE`,
        `p`.`REALM_COUNTRY_ID` AS `REALM_COUNTRY_ID`,
        GROUP_CONCAT(`pha`.`HEALTH_AREA_ID`
            SEPARATOR ',') AS `HEALTH_AREA_ID`,
        `p`.`ORGANISATION_ID` AS `ORGANISATION_ID`,
        `p`.`LABEL_ID` AS `LABEL_ID`,
        `p`.`PROGRAM_MANAGER_USER_ID` AS `PROGRAM_MANAGER_USER_ID`,
        `p`.`PROGRAM_NOTES` AS `PROGRAM_NOTES`,
        `p`.`AIR_FREIGHT_PERC` AS `AIR_FREIGHT_PERC`,
        `p`.`SEA_FREIGHT_PERC` AS `SEA_FREIGHT_PERC`,
        `p`.`ROAD_FREIGHT_PERC` AS `ROAD_FREIGHT_PERC`,
        `p`.`PLANNED_TO_SUBMITTED_LEAD_TIME` AS `PLANNED_TO_SUBMITTED_LEAD_TIME`,
        `p`.`SUBMITTED_TO_APPROVED_LEAD_TIME` AS `SUBMITTED_TO_APPROVED_LEAD_TIME`,
        `p`.`APPROVED_TO_SHIPPED_LEAD_TIME` AS `APPROVED_TO_SHIPPED_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`,
        `p`.`SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME` AS `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`,
        `p`.`ARRIVED_TO_DELIVERED_LEAD_TIME` AS `ARRIVED_TO_DELIVERED_LEAD_TIME`,
        `p`.`CURRENT_VERSION_ID` AS `CURRENT_VERSION_ID`,
        `p`.`ACTIVE` AS `ACTIVE`,
        `p`.`CREATED_BY` AS `CREATED_BY`,
        `p`.`CREATED_DATE` AS `CREATED_DATE`,
        `p`.`LAST_MODIFIED_BY` AS `LAST_MODIFIED_BY`,
        `p`.`LAST_MODIFIED_DATE` AS `LAST_MODIFIED_DATE`,
        `pl`.`LABEL_EN` AS `LABEL_EN`,
        `pl`.`LABEL_FR` AS `LABEL_FR`,
        `pl`.`LABEL_SP` AS `LABEL_SP`,
        `pl`.`LABEL_PR` AS `LABEL_PR`,
        `p`.`PROGRAM_TYPE_ID` AS `PROGRAM_TYPE_ID`
    FROM
        ((`rm_program` `p`
        LEFT JOIN `rm_program_health_area` `pha` ON ((`p`.`PROGRAM_ID` = `pha`.`PROGRAM_ID`)))
        LEFT JOIN `ap_label` `pl` ON ((`p`.`LABEL_ID` = `pl`.`LABEL_ID`)))
    WHERE
        (`p`.`PROGRAM_TYPE_ID` = 2)
    GROUP BY `p`.`PROGRAM_ID`;


USE `fasp`;
DROP procedure IF EXISTS `programLeadTimes`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`programLeadTimes`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `programLeadTimes`(VAR_PROGRAM_ID INT(10), VAR_PROCUREMENT_AGENT_IDS VARCHAR(255), VAR_PLANNING_UNIT_IDS TEXT)
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	-- Report no 14
	-- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- VAR_PROGRAM_ID is the program that you want to run the report for
    -- VAR_PROCUREMENT_AGENT_IDS is the list of Procurement Agents you want to include in the report
    -- VAR_PLANNING_UNIT_IDS is the list of Planning Units that you want to see the report for
    SET @programId = VAR_PROGRAM_ID;

    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT * FROM (SELECT ");
    SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, "); 
    SET @sqlString = CONCAT(@sqlString, "   p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, "); 
    SET @sqlString = CONCAT(@sqlString, "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, "); 
    SET @sqlString = CONCAT(@sqlString, "	0 `PROCUREMENT_AGENT_ID`, 'Local' `PROCUREMENT_AGENT_CODE`, l.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, l.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, l.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, l.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, l.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	null `PLANNED_TO_SUBMITTED_LEAD_TIME`, null `SUBMITTED_TO_APPROVED_LEAD_TIME`, null `APPROVED_TO_SHIPPED_LEAD_TIME`, null `SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME`, null `SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME`, null `SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME`, null `ARRIVED_TO_DELIVERED_LEAD_TIME`, ppu.LOCAL_PROCUREMENT_LEAD_TIME ");
    SET @sqlString = CONCAT(@sqlString, "FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ap_label l ON l.LABEL_ID=108 ");
    SET @sqlString = CONCAT(@sqlString, "WHERE p.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "AND ppu.PLANNING_UNIT_ID IN (" , VAR_PLANNING_UNIT_IDS , ") ");
    END IF;
    
    SET @sqlString = CONCAT(@sqlString, "UNION  ");
	
    SET @sqlString = CONCAT(@sqlString, "SELECT  ");
    SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	0 `PROCUREMENT_AGENT_ID`, 'Not selected' `PROCUREMENT_AGENT_CODE`, l.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, l.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, l.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, l.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, l.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	p.PLANNED_TO_SUBMITTED_LEAD_TIME, p.SUBMITTED_TO_APPROVED_LEAD_TIME, p.APPROVED_TO_SHIPPED_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME, p.ARRIVED_TO_DELIVERED_LEAD_TIME, null `LOCAL_PROCUREMENT_LEAD_TIME` ");
    SET @sqlString = CONCAT(@sqlString, "FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN ap_label l ON l.LABEL_ID=453 ");
    SET @sqlString = CONCAT(@sqlString, "WHERE p.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
    	SET @sqlString = CONCAT(@sqlString, "AND ppu.PLANNING_UNIT_ID IN (" , VAR_PLANNING_UNIT_IDS , ") ");
    END IF;
    
    SET @sqlString = CONCAT(@sqlString, "UNION  ");
    
    SET @sqlString = CONCAT(@sqlString, "SELECT  ");
    SET @sqlString = CONCAT(@sqlString, "	rc.REALM_COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2, c.LABEL_ID `COUNTRY_LABEL_ID`, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "	p.PLANNED_TO_SUBMITTED_LEAD_TIME, pa.SUBMITTED_TO_APPROVED_LEAD_TIME, pa.APPROVED_TO_SHIPPED_LEAD_TIME, ");
    SET @sqlString = CONCAT(@sqlString, "	p.SHIPPED_TO_ARRIVED_BY_AIR_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_SEA_LEAD_TIME, p.SHIPPED_TO_ARRIVED_BY_ROAD_LEAD_TIME, p.ARRIVED_TO_DELIVERED_LEAD_TIME, null LOCAL_PROCUREMENT_LEAD_TIME ");
    SET @sqlString = CONCAT(@sqlString, "FROM vw_program p ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_procurement_agent pa ON pa.ACTIVE ");
    SET @sqlString = CONCAT(@sqlString, "WHERE p.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(VAR_PLANNING_UNIT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "AND ppu.PLANNING_UNIT_ID IN (" , VAR_PLANNING_UNIT_IDS , ") ");
    END IF;
    IF LENGTH(VAR_PROCUREMENT_AGENT_IDS)>0 THEN
		SET @sqlString = CONCAT(@sqlString, "AND pa.PROCUREMENT_AGENT_ID IN (" , VAR_PROCUREMENT_AGENT_IDS , ") ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, ") p1 ORDER BY p1.PROGRAM_ID, p1.PLANNING_UNIT_ID, IFNULL(p1.PROCUREMENT_AGENT_ID,0) ");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.validroadfreighttext','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter road freight percentage');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Saisir le pourcentage de fret routier');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Introduzca el porcentaje de transporte por carretera');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira a porcentagem de frete rodoviário');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.shippedToArrivedByRoadtext','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter `Shipped to Arrived` road lead time (in months)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez le délai de livraison de la route ""Expédié à Arrivé"" (en mois)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el tiempo de entrega en carretera de `Enviado a Llegado` (en meses)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o prazo de entrega da estrada `Shipped to Arrived` (em meses)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.program.roadfreightperc','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Road freight percentage');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pourcentage de fret routier');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Porcentaje de transporte por carretera');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Porcentagem de frete rodoviário');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realmcountry.shippedToArrivedRoadLeadTime','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'`Shipped to Arrived` Road Lead Time (months)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'`Expédié à Arrivé` Délai de Route (mois)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'`Enviado a Llegado` Tiempo de entrega por carretera (meses)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Prazo de entrega na estrada `Shipped to Arrived` (meses)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataentry.road','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Road');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Route');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Camino');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estrada');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.shippedToArrivedRoadLeadTime','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipped to Arrived (Road)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Expédié à arrivé (route)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Enviado a Llegado (carretera)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Enviado para Chegou (Estrada)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.totalRoadLeadTime','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned to Received (Road)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévu à reçu (route)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Planificado a recibido (carretera)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Planejado para Recebido (Estrada)');-- pr

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Sea freight percentage'
where l.LABEL_CODE='static.program.seafreightperc' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Pourcentage de fret maritime'
where l.LABEL_CODE='static.program.seafreightperc' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Porcentaje de flete marítimo'
where l.LABEL_CODE='static.program.seafreightperc' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Porcentagem de frete marítimo'
where l.LABEL_CODE='static.program.seafreightperc' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Sea Freight Percentage'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentage' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Pourcentage de fret maritime'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentage' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Porcentaje de flete marítimo'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentage' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Porcentagem de Frete Marítimo'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentage' and ll.LANGUAGE_ID=4;


update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Sea freight percentage'
where l.LABEL_CODE='static.program.seafreightperctext' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Pourcentage de fret maritime'
where l.LABEL_CODE='static.program.seafreightperctext' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Porcentaje de flete marítimo'
where l.LABEL_CODE='static.program.seafreightperctext' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Porcentagem de frete marítimo'
where l.LABEL_CODE='static.program.seafreightperctext' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Enter sea freight percentage'
where l.LABEL_CODE='static.program.validseafreighttext' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Entrez le pourcentage de fret maritime'
where l.LABEL_CODE='static.program.validseafreighttext' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ingrese el porcentaje de flete marítimo'
where l.LABEL_CODE='static.program.validseafreighttext' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Insira a porcentagem de frete marítimo'
where l.LABEL_CODE='static.program.validseafreighttext' and ll.LANGUAGE_ID=4;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Enter sea freight percentage'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentagetext' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Entrez le pourcentage de fret maritime'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentagetext' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Ingrese el porcentaje de flete marítimo'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentagetext' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Insira a porcentagem de frete marítimo'
where l.LABEL_CODE='static.realmcountry.seaFreightPercentagetext' and ll.LANGUAGE_ID=4;