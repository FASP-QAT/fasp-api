ALTER TABLE `fasp`.`rm_program_planning_unit` 
ADD COLUMN `PLAN_BASED_ON` INT UNSIGNED NOT NULL COMMENT '1- MoS , 2- Qty' AFTER `MONTHS_IN_PAST_FOR_AMC`,
ADD COLUMN `MIN_QTY` INT UNSIGNED NULL AFTER `PLAN_BASED_ON`,
ADD COLUMN `DISTRIBUTION_LEAD_TIME` DOUBLE(6,2) UNSIGNED NULL AFTER `MIN_QTY`,
CHANGE COLUMN `MIN_MONTHS_OF_STOCK` `MIN_MONTHS_OF_STOCK` INT UNSIGNED NULL ;


insert into ap_label values(null,"In the next 1-6 months (<%RANGE_1TO6_MONTHS%>), inventory is…
  * over the max stock qty + distribution lead time for <%MOSABOVE_MAX_IN6MONTHS%> month(s) 	
  * under the min stock qty for <%MOSLESS_MIN_IN6MONTHS%> month(s). 
 
In the next 7-18 months (<%RANGE_7TO18_MONTHS%>), inventory is… 
  * over the max stock qty + distribution lead time for <%MOSABOVE_MAX_IN7TO18MONTHS%> month(s) 
  * under the min stock qty for <%MOSLESS_MIN_IN7TO18MONTHS%> month(s) 

Note: There are <%SHIPMENTS_IN6MONTHS%> shipment(s) in the next 1-6 months, and there are <%SHIPMENTS_IN7TO18MONTHS%> shipment(s) in the next 7-18 months.",null,null,null,1,now(),1,now(),22);

set @maxid1=(select max(al.LABEL_ID) from ap_label al);

insert into ap_problem values(null,@maxid1,3,"/supplyPlan",28836,1,1,1,1,1,1,1,now(),1,now());
set @maxid3=(select max(ap.PROBLEM_ID) from ap_problem ap);
select @maxid3;
insert into  rm_realm_problem values(null,1,@maxid3,3,6,18,null,1,1,1,now(),1,now());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.planBasedOn','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Plan Based On');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Plan basé sur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plan basado en');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Plano baseado em');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.product.minQuantity','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Quantity');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité minimale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.product.distributionLeadTime','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Distribution Lead Time (months)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Délai de distribution (mois)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Plazo de entrega de distribución (meses)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lead Time de Distribuição (meses)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.minQty','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté minimale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.planBasedOnTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Choose `quantity` for products where months of stock (MOS) planning doesn’t make sense (ie products that are high expiry, low consumption)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez ""quantité"" pour les produits pour lesquels la planification des mois de stock (MOS) n'a pas de sens (c'est-à-dire les produits dont la date de péremption est élevée et la faible consommation)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija 'cantidad' para productos en los que la planificación de meses de existencias (MOS) no tiene sentido (es decir, productos que tienen una caducidad alta, un consumo bajo)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha 'quantidade' para produtos em que o planejamento de meses de estoque (MOS) não faz sentido (ou seja, produtos com prazo de validade alto, baixo consumo)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.plannedBy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planned By');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Planifié par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'planeado por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Planejado por');-- pr

USE `fasp`;
DROP procedure IF EXISTS `getStockStatusForProgram`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getStockStatusForProgram`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getStockStatusForProgram`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_DT DATE, VAR_TRACER_CATEGORY_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    
    
    

    
    
    
    
    
    
    
    
    
    
    
	    
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @dt = VAR_DT;
	SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
    IF @versionId = -1 THEN
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    tc.TRACER_CATEGORY_ID, tc.LABEL_ID `TRACER_CATEGORY_LABEL_ID`, tc.LABEL_EN `TRACER_CATEGORY_LABEL_EN`, tc.LABEL_FR `TRACER_CATEGORY_LABEL_FR`, tc.LABEL_SP `TRACER_CATEGORY_LABEL_SP`, tc.LABEL_PR `TRACER_CATEGORY_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    IF(ppu.PLAN_BASED_ON=1,ppu.MIN_MONTHS_OF_STOCK,ppu.MIN_QTY) `MIN_MONTHS_OF_STOCK`, IF(ppu.PLAN_BASED_ON=1,(ppu.MIN_MONTHS_OF_STOCK+ppu.REORDER_FREQUENCY_IN_MONTHS),ROUND(amc.MAX_STOCK_QTY)) `MAX_MONTHS_OF_STOCK`, ");
    SET @sqlString = CONCAT(@sqlString, "    IF(@includePlannedShipments, IFNULL(amc.CLOSING_BALANCE,0), IFNULL(amc.CLOSING_BALANCE_WPS,0)) `STOCK`,  ");
    SET @sqlString = CONCAT(@sqlString, "    amc.AMC `AMC`,  ");
    SET @sqlString = CONCAT(@sqlString, "    IF(@includePlannedShipments, IFNULL(amc.MOS,NULL), IFNULL(amc.MOS_WPS,NULL)) `MoS`, ");
    SET @sqlString = CONCAT(@sqlString, "    a3.LAST_STOCK_DATE `STOCK_COUNT_DATE`,ppu.PLAN_BASED_ON ");
    SET @sqlString = CONCAT(@sqlString, "FROM rm_program_planning_unit ppu  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc amc ON amc.PROGRAM_ID=@programId AND amc.VERSION_ID=@versionId AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID AND amc.TRANS_DATE=@dt ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN (SELECT a2.PLANNING_UNIT_ID, MAX(a2.TRANS_DATE) LAST_STOCK_DATE FROM rm_supply_plan_amc a2 WHERE a2.PROGRAM_ID=@programId AND a2.VERSION_ID=@versionId AND a2.TRANS_DATE<=@dt AND a2.REGION_COUNT=a2.REGION_COUNT_FOR_STOCK GROUP BY a2.PLANNING_UNIT_ID) a3 ON amc.PLANNING_UNIT_ID=a3.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ppu.PROGRAM_ID=@programId AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(VAR_TRACER_CATEGORY_IDS)>0 THEN
        SET @sqlString = CONCAT(@sqlString, " AND fu.TRACER_CATEGORY_ID IN (",VAR_TRACER_CATEGORY_IDS,") ");
    END IF;
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;
;



USE `fasp`;
DROP procedure IF EXISTS `stockStatusMatrix`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusMatrix`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusMatrix`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_TRACER_CATEGORY_IDS VARCHAR(255), VAR_PLANNING_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
BEGIN
    
    
    
 
    
    
    
    
    
    
    
    
    
    
    

    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @planningUnitIds = VAR_PLANNING_UNIT_IDS;
    SET @tracerCategoryIds = VAR_TRACER_CATEGORY_IDS;
    SET @includePlannedShipments = VAR_INCLUDE_PLANNED_SHIPMENTS;
    
    IF @versionId = -1 THEN
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "    YEAR(mn.MONTH) YR, ");
    SET @sqlString = CONCAT(@sqlString, "    pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    fu.TRACER_CATEGORY_ID, pu.MULTIPLIER, ");
    SET @sqlString = CONCAT(@sqlString, "    u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP `UNIT_LABEL_SP`, u.LABEL_PR `UNIT_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "    IF(ppu.PLAN_BASED_ON=1,ppu.MIN_MONTHS_OF_STOCK,ppu.MIN_QTY) `MIN_MONTHS_OF_STOCK`, ppu.REORDER_FREQUENCY_IN_MONTHS,ppu.PLAN_BASED_ON, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=1, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Jan Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=2, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Feb Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=3, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Mar Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=4, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Apr Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=5, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `May Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=6, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Jun Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=7, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Jul Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=8, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Aug Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=9, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Sep Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=10, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Oct Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=11, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Nov Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=12, IF(@includePlannedShipments, amc.CLOSING_BALANCE, amc.CLOSING_BALANCE_WPS),null)) `Dec Stock`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=1, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Jan`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=2, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Feb`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=3, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Mar`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=4, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Apr`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=5, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `May`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=6, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Jun`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=7, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Jul`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=8, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Aug`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=9, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Sep`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=10, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Oct`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=11, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Nov`, ");
    SET @sqlString = CONCAT(@sqlString, "    SUM(IF(MONTH(mn.MONTH)=12, IF(@includePlannedShipments, IF(ppu.PLAN_BASED_ON=1,amc.MOS,amc.MAX_STOCK_QTY), IF(ppu.PLAN_BASED_ON=1,amc.MOS_WPS,amc.MAX_STOCK_QTY)),null)) `Dec` ");
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_supply_plan_amc amc ON ppu.PROGRAM_ID=amc.PROGRAM_ID AND amc.VERSION_ID=@versionId AND mn.MONTH=amc.TRANS_DATE AND ppu.PLANNING_UNIT_ID=amc.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID  ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_unit u ON pu.UNIT_ID=u.UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE ");
    SET @sqlString = CONCAT(@sqlString, "    mn.MONTH BETWEEN @startDate and @stopDate AND ppu.ACTIVE AND pu.ACTIVE ");
    IF LENGTH(@planningUnitIds)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "    AND FIND_IN_SET(ppu.PLANNING_UNIT_ID, @planningUnitIds) ");
    END IF;
    IF LENGTH(@tracerCategoryIds)>0 THEN
        SET @sqlString = CONCAT(@sqlString, "    AND FIND_IN_SET(fu.TRACER_CATEGORY_ID, @tracerCategoryIds) ");
    END IF;
    SET @sqlString = CONCAT(@sqlString, "GROUP BY ppu.PLANNING_UNIT_ID, YEAR(mn.MONTH)");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.minMosOrQty','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min (months or qty)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Min (mois ou quantité)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Min (meses o cantidad)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Min (meses ou qtd)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.maxMosOrQty','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max (months or qty)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Max (mois ou quantité)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Max (meses o cantidad)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Max (meses ou qtd)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.variable','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Variable');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Variable');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Variable');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Variável');-- pr
