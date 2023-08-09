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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Plan By');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Planifier par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Planificar por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Planejar por');-- pr
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
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Choisissez `quantité` pour les produits pour lesquels la planification des mois de stock (MOS) n`a pas de sens (c`est-à-dire les produits dont la date de péremption est élevée et la faible consommation)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Elija `cantidad` para productos en los que la planificación de meses de existencias (MOS) no tiene sentido (es decir, productos que tienen una caducidad alta, un consumo bajo)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Escolha `quantidade` para produtos em que o planejamento de meses de estoque (MOS) não faz sentido (ou seja, produtos com prazo de validade alto, baixo consumo)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.plannedBy','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Plan By');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Planifier par');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Planificar por');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Planejar por');-- pr

USE `fasp`;
DROP procedure IF EXISTS `getStockStatusForProgram`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getStockStatusForProgram`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `getStockStatusForProgram`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_DT DATE, VAR_TRACER_CATEGORY_IDS TEXT, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
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
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusMatrix`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_TRACER_CATEGORY_IDS VARCHAR(255), VAR_PLANNING_UNIT_IDS TEXT, VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_INCLUDE_PLANNED_SHIPMENTS TINYINT(1))
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
    SET @sqlString = CONCAT(@sqlString, "    IF(ppu.PLAN_BASED_ON=1,ppu.MIN_MONTHS_OF_STOCK,ppu.MIN_QTY) `MIN_MONTHS_OF_STOCK`, ppu.REORDER_FREQUENCY_IN_MONTHS,ppu.PLAN_BASED_ON, AVG(amc.MAX_STOCK_QTY) `MAX_STOCK_QTY`, ");
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

update rm_program_planning_unit pu SET pu.PLAN_BASED_ON=1,pu.LAST_MODIFIED_DATE=now();

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.maxQty','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Quantity');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité maximale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.maxQtyFormula1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min qty (planning unit setting) = 7,000');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté min (réglage de l`unité de planification) = 7,000');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima (configuración de la unidad de planificación) = 7,000');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima (configuração da unidade de planejamento) = 7,000');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.maxQtyFormula2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Reorder Frequency = 3');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Fréquence de réapprovisionnement = 3');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Frecuencia de pedido = 3');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Frequência de Reordenamento = 3');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.maxQtyFormula3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty = Min Qty + Reorder Frequency * AMC');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté Max = Qté Min + Fréquence de réapprovisionnement * AMC');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cant. máx. = Cant. mín. + Frecuencia de pedido * AMC');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima = Quantidade mínima + Frequência de reordenamento * AMC');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.maxQtyFormula4','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty = 7,000 + 3 * 6,392');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté max = 7,000 + 3 * 6,392');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima = 7,000 + 3 * 6,392');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima = 7,000 + 3 * 6,392');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.maxQtyFormula5','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty = 7,000 + 19,176');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté max = 7,000 + 19,176');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima = 7,000 + 19,176');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima = 7,000 + 19,176');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.maxQtyFormula6','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Qty = 26,176');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté max = 26,176');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima = 26,176');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima = 26,176');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.whenToSuggestQty1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'a. If Plan Based on is MOS');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'un. Si le plan basé sur est MOS');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'una. Si el plan basado en es MOS');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'uma. Se o plano baseado em é MOS');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.whenToSuggestQty2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'b. If Plan Based on is Quantity');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'b. Si le plan basé sur est la quantité');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'b. Si el plan se basa en la cantidad');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'b. Se o plano baseado na quantidade');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.whenToSuggestQty3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Closing Balance for current month + X months (Distribution Lead Time) = 7,000');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde de clôture pour le mois en cours + X mois (Délai de distribution) = 7,000');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo de cierre del mes actual + X meses (Tiempo de entrega de distribución) = 7,000');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo de fechamento do mês atual + X meses (prazo de distribuição) = 7,000');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.whenToSuggestQty4','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Clsoing Balance for 1st month = 6,000');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde de clôture pour le 1er mois = 6,000');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo de cierre del primer mes = 6,000');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo de Fechamento do 1º mês = 6,0000');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.whenToSuggestQty5','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Clsoing Balance for 2nd month = 6,500');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde de clôture pour le 2ème mois = 6,500');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo de cierre del segundo mes = 6,500');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo de fechamento do 2º mês = 6,500');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.whenToSuggestQty6','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Min Quantity= 8,000');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité minimale = 8,000');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad mínima = 8,000');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade mínima = 8,000');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.whenToSuggestQty7','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Since Closing Balance for all the three months is less than Min Quantity , therefore');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Étant donné que le solde de clôture pour les trois mois est inférieur à la quantité minimale, par conséquent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Dado que el Saldo de cierre para los tres meses es menor que la Cantidad mínima, por lo tanto');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Como o saldo final para todos os três meses é menor que a quantidade mínima, portanto');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.suggestedMaxQty1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max Quantity for current month + X months (Distribution Lead Time) = 44,744');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité maximale pour le mois en cours + X mois (Délai de distribution) = 44,744');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad máxima para el mes actual + X meses (Tiempo de entrega de distribución) = 44,744');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade máxima para o mês atual + X meses (prazo de distribuição) = 44,744');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.suggestedMaxQty2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Suggested Order Qty = (Max Qty - Ending balance + Unmet Demand)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Quantité de commande suggérée = (Qté max - Solde final + Demande non satisfaite)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cantidad de pedido sugerida = (cantidad máxima - saldo final + demanda no satisfecha)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantidade sugerida do pedido = (quantidade máxima - saldo final + demanda não atendida)');-- pr
USE `fasp`;
DROP procedure IF EXISTS `stockStatusReportVertical`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusReportVertical`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusReportVertical`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_ID INT(10))
BEGIN
    
    
    
    
    
    
    SET @startDate = VAR_START_DATE;
    SET @stopDate = VAR_STOP_DATE;
    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @planningUnitId = VAR_PLANNING_UNIT_ID;

    IF @versionId = -1 THEN 
	SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
    END IF;
    SET @prvMonthClosingBal = 0;
    SELECT 
        s2.`TRANS_DATE`, 
        s2.`PLANNING_UNIT_ID`, s2.`PLANNING_UNIT_LABEL_ID`, s2.`PLANNING_UNIT_LABEL_EN`, s2.`PLANNING_UNIT_LABEL_FR`, s2.`PLANNING_UNIT_LABEL_SP`, s2.`PLANNING_UNIT_LABEL_PR`,
        COALESCE(s2.`FINAL_OPENING_BALANCE`, @prvMonthClosingBal) `FINAL_OPENING_BALANCE`,
        s2.`ACTUAL_CONSUMPTION_QTY`, s2.`FORECASTED_CONSUMPTION_QTY`, 
        IF(s2.`ACTUAL`, s2.`ACTUAL_CONSUMPTION_QTY`,s2.`FORECASTED_CONSUMPTION_QTY`) `FINAL_CONSUMPTION_QTY`,
        s2.`ACTUAL`,
        s2.`SQTY` ,
        s2.`ADJUSTMENT`,
        s2.`NATIONAL_ADJUSTMENT`,
        s2.EXPIRED_STOCK,
        COALESCE(s2.`FINAL_CLOSING_BALANCE`, @prvMonthClosingBal) `FINAL_CLOSING_BALANCE`,
        s2.AMC, s2.UNMET_DEMAND,s2.REGION_COUNT,s2.REGION_COUNT_FOR_STOCK,
        s2.`MoS`,
        s2.`MIN_MONTHS_OF_STOCK`,
        s2.`MAX_MONTHS_OF_STOCK`,
        s2.MIN_STOCK_QTY,
        s2.MAX_STOCK_QTY,
        s2.PLAN_BASED_ON,s2.DISTRIBUTION_LEAD_TIME,
        s2.`SHIPMENT_ID`, s2.`SHIPMENT_QTY`, s2.`EDD`, s2.`NOTES`,
        s2.`FUNDING_SOURCE_ID`, s2.`FUNDING_SOURCE_CODE`, s2.`FUNDING_SOURCE_LABEL_ID`, s2.`FUNDING_SOURCE_LABEL_EN`, s2.`FUNDING_SOURCE_LABEL_FR`, s2.`FUNDING_SOURCE_LABEL_SP`, s2.`FUNDING_SOURCE_LABEL_PR`, 
        s2.PROCUREMENT_AGENT_ID, s2.PROCUREMENT_AGENT_CODE, s2.`PROCUREMENT_AGENT_LABEL_ID`, s2.`PROCUREMENT_AGENT_LABEL_EN`, s2.`PROCUREMENT_AGENT_LABEL_FR`, s2.`PROCUREMENT_AGENT_LABEL_SP`, s2.`PROCUREMENT_AGENT_LABEL_PR`, 
        s2.DATA_SOURCE_ID, s2.`DATA_SOURCE_LABEL_ID`, s2.`DATA_SOURCE_LABEL_EN`, s2.`DATA_SOURCE_LABEL_FR`, s2.`DATA_SOURCE_LABEL_SP`, s2.`DATA_SOURCE_LABEL_PR`, 
        s2.SHIPMENT_STATUS_ID, s2.`SHIPMENT_STATUS_LABEL_ID`, s2.`SHIPMENT_STATUS_LABEL_EN`, s2.`SHIPMENT_STATUS_LABEL_FR`, s2.`SHIPMENT_STATUS_LABEL_SP`, s2.`SHIPMENT_STATUS_LABEL_PR`,
        @prvMonthClosingBal:=COALESCE(s2.`FINAL_CLOSING_BALANCE`, s2.`FINAL_OPENING_BALANCE`, @prvMonthClosingBal) `PRV_CLOSING_BAL`
    FROM (
	SELECT 
        mn.MONTH `TRANS_DATE`, 
        pu.`PLANNING_UNIT_ID`, pu.`LABEL_ID` `PLANNING_UNIT_LABEL_ID`, pu.`LABEL_EN` `PLANNING_UNIT_LABEL_EN`, pu.`LABEL_FR` `PLANNING_UNIT_LABEL_FR`, pu.`LABEL_SP` `PLANNING_UNIT_LABEL_SP`, pu.`LABEL_PR` `PLANNING_UNIT_LABEL_PR`,
        sma.OPENING_BALANCE `FINAL_OPENING_BALANCE`, 
        sma.ACTUAL_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY, 
        sma.ACTUAL,
        sma.SHIPMENT_QTY SQTY,
        sma.ADJUSTMENT_MULTIPLIED_QTY `ADJUSTMENT`,
        sma.`NATIONAL_ADJUSTMENT`,
        sma.EXPIRED_STOCK,
        sma.CLOSING_BALANCE `FINAL_CLOSING_BALANCE`,
        sma.AMC, sma.UNMET_DEMAND,sma.REGION_COUNT,sma.REGION_COUNT_FOR_STOCK,
        sma.MOS `MoS`,
        ppu.PLAN_BASED_ON,
        ppu.DISTRIBUTION_LEAD_TIME,
        IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK) `MIN_MONTHS_OF_STOCK`, 
        IF(
            IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS<r.MIN_MOS_MAX_GAURDRAIL, 
            r.MIN_MOS_MAX_GAURDRAIL, 
            IF(
                IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS>r.MAX_MOS_MAX_GAURDRAIL,
                r.MAX_MOS_MAX_GAURDRAIL,
                IF(ppu.MIN_MONTHS_OF_STOCK<r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MIN_GAURDRAIL, ppu.MIN_MONTHS_OF_STOCK)+ppu.REORDER_FREQUENCY_IN_MONTHS
            )
        ) `MAX_MONTHS_OF_STOCK`,
        sma.MIN_STOCK_QTY,
        sma.MAX_STOCK_QTY,
        sh.SHIPMENT_ID, sh.SHIPMENT_QTY, sh.`EDD`, sh.`NOTES`,
        fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, 
        pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, 
        ds.DATA_SOURCE_ID, ds.LABEL_ID `DATA_SOURCE_LABEL_ID`, ds.LABEL_EN `DATA_SOURCE_LABEL_EN`, ds.LABEL_FR `DATA_SOURCE_LABEL_FR`, ds.LABEL_SP `DATA_SOURCE_LABEL_SP`, ds.LABEL_PR `DATA_SOURCE_LABEL_PR`, 
        ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_Fr `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`
    FROM
        mn 
        LEFT JOIN rm_supply_plan_amc sma ON 
            mn.MONTH=sma.TRANS_DATE 
            AND sma.PROGRAM_ID = @programId
            AND sma.VERSION_ID = @versionId
            AND sma.PLANNING_UNIT_ID = @planningUnitId
        LEFT JOIN 
            (
            SELECT COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, s.SHIPMENT_ID, st.SHIPMENT_QTY , st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_STATUS_ID, st.NOTES, st.DATA_SOURCE_ID
            FROM 
                (
                SELECT s.SHIPMENT_ID, MAX(st.VERSION_ID) MAX_VERSION_ID FROM rm_shipment s LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID WHERE s.PROGRAM_ID=@programId AND st.VERSION_ID<=@versionId AND st.SHIPMENT_TRANS_ID IS NOT NULL GROUP BY s.SHIPMENT_ID 
            ) AS s 
            LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND s.MAX_VERSION_ID=st.VERSION_ID 
            WHERE 
                st.ACTIVE 
                AND st.SHIPMENT_STATUS_ID != 8 
                AND st.ACCOUNT_FLAG
                AND COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) BETWEEN @startDate AND @stopDate 
                AND st.PLANNING_UNIT_ID =@planningUnitId
        ) sh ON LEFT(sma.TRANS_DATE,7)=LEFT(sh.EDD,7)
        LEFT JOIN vw_funding_source fs ON sh.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        LEFT JOIN vw_procurement_agent pa ON sh.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        LEFT JOIN vw_data_source ds ON sh.DATA_SOURCE_ID=ds.DATA_SOURCE_ID
        LEFT JOIN vw_shipment_status ss ON sh.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID
        LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId AND ppu.PLANNING_UNIT_ID=@planningUnitId
        LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_program p ON p.PROGRAM_ID=@programId
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID
    WHERE
        mn.MONTH BETWEEN @startDate AND @stopDate
    ORDER BY mn.MONTH, sh.sHIPMENT_ID) AS s2;
    
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.minMaxNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT converts max/min MOS into quantities by multiplying the number by the AMC for the current month.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT convertit le MOS max/min en quantités en multipliant le nombre par l`AMC pour le mois en cours.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT convierte el MOS máximo/mínimo en cantidades multiplicando el número por el AMC para el mes actual.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'QAT converte MOS max/min em quantidades multiplicando o número pelo AMC para o mês atual.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.planByTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Should minimum and maximum inventory parameters be based on quantity or months of stock (MOS)? Most products are better planned by MOS, while some low consumption, higher expiry products are better planned by quantity.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les paramètres d`inventaire minimum et maximum doivent-ils être basés sur la quantité ou les mois de stock (MOS) ? La plupart des produits sont mieux planifiés par MOS, tandis que certains produits à faible consommation et à péremption plus élevée sont mieux planifiés par quantité.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Los parámetros de inventario mínimo y máximo deben basarse en la cantidad o los meses de existencias (MOS)? La mayoría de los productos están mejor planificados por MOS, mientras que algunos productos de bajo consumo y mayor vencimiento están mejor planificados por cantidad.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os parâmetros de estoque mínimo e máximo devem ser baseados na quantidade ou meses de estoque (MOS)? A maioria dos produtos é melhor planejada pelo MOS, enquanto alguns produtos de baixo consumo e prazo de validade mais alto são melhor planejados pela quantidade.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.reorderFrequencyTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How many months between shipments?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Combien de mois entre les expéditions ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Cuántos meses entre envíos?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantos meses entre os envios?');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.minMonthsOfStockTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Required if planning by MOS. Note that maximum MOS = min MOS + reorder interval.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Obligatoire si planification par MOS. Notez que MOS maximum = MOS min + intervalle de commande.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Obligatorio si está planificado por MOS. Tenga en cuenta que MOS máximo = MOS mínimo + intervalo de reorden.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Necessário se planejamento por MOS. Observe que MOS máximo = MOS mínimo + intervalo de reordenação.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.minQtyTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Required if planning by quantity. Note that maximum MOS = min MOS + reorder interval. * AMC');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Obligatoire si planification par quantité. Notez que MOS maximum = MOS min + intervalle de commande. * AMC');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Obligatorio si se planifica por cantidad. Tenga en cuenta que MOS máximo = MOS mínimo + intervalo de reorden. * AMC');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Obrigatório se planejar por quantidade. Observe que MOS máximo = MOS mínimo + intervalo de reordenação. * AMC');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.monthsInFutureTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How many months should be used for calculating the average monthly consumption (AMC)? Include more months in the past if the past data is more reliable and predicts the future.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Combien de mois faut-il utiliser pour calculer la consommation mensuelle moyenne (AMC) ? Inclure plus de mois dans le passé si les données passées sont plus fiables et prédisent l`avenir.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Cuántos meses se deben usar para calcular el consumo promedio mensual (AMC)? Incluya más meses en el pasado si los datos del pasado son más confiables y predicen el futuro.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantos meses devem ser usados ​​para calcular o consumo médio mensal (AMC)? Inclua mais meses no passado se os dados passados ​​forem mais confiáveis ​​e preverem o futuro.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.monthsInPastTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'How many months should be used for calculating the average monthly consumption (AMC)? Include more months in the future if the forecast data is better basis for stock calculations, such as in the case of seasonal products.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Combien de mois faut-il utiliser pour calculer la consommation mensuelle moyenne (AMC) ? Inclure plus de mois à l`avenir si les données prévisionnelles constituent une meilleure base pour les calculs de stock, comme dans le cas des produits saisonniers.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Cuántos meses se deben usar para calcular el consumo promedio mensual (AMC)? Incluya más meses en el futuro si los datos de pronóstico son una mejor base para los cálculos de existencias, como en el caso de los productos de temporada.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Quantos meses devem ser usados ​​para calcular o consumo médio mensal (AMC)? Inclua mais meses no futuro se os dados de previsão forem uma melhor base para cálculos de estoque, como no caso de produtos sazonais.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.distributionLeadTimeTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Required if planning by quantity. How many months does it take between shipment receipt and the product to be distributed down to the lowest level? Used for suggested shipments ahead of understock.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Obligatoire si planification par quantité. Combien de mois s`écoule-t-il entre la réception de l`expédition et le produit à distribuer jusqu`au niveau le plus bas ? Utilisé pour les expéditions suggérées avant le sous-stock.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Obligatorio si se planifica por cantidad. ¿Cuántos meses transcurren entre la recepción del envío y la distribución del producto hasta el nivel más bajo? Se utiliza para envíos sugeridos antes de que se agoten las existencias.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Obrigatório se planejar por quantidade. Quantos meses leva entre o recebimento da remessa e o produto a ser distribuído até o nível mais baixo? Usado para remessas sugeridas antes do estoque insuficiente.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.stockStatusMatrixMaxTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If planning by quantity, the max value is calculated every month by min + reorder interval* AMC. The value displayed here is the average for the year.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si planification par quantité, la valeur max est calculée chaque mois par min + intervalle de commande* AMC. La valeur affichée ici est la moyenne de l`année.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si se planifica por cantidad, el valor máximo se calcula cada mes por intervalo mínimo de pedido* AMC. El valor que se muestra aquí es el promedio del año.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Se estiver planejando por quantidade, o valor máximo é calculado todo mês por mín + intervalo de reabastecimento* AMC. O valor exibido aqui é a média do ano.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.localProcurementAgentTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Lead time from planned to received. Used for shipments where `local procurement` is selected.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Délai de livraison entre prévu et reçu. Utilisé pour les expéditions où `l`approvisionnement local` est sélectionné.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Délai de livraison entre prévu et reçu. Utilisé pour les expéditions où `l`approvisionnement local` est sélectionné.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lead time do planejado ao recebido. Usado para remessas em que `aquisição local` é selecionada.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.shelfLifeTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Time from shipment receipt (status = received) to expiry. Used for new batches created via shipments, but actual expiry date for any shipment can be updated by the user.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Délai entre la réception de l`envoi (statut = reçu) et l`expiration. Utilisé pour les nouveaux lots créés via des expéditions, mais la date d`expiration réelle de toute expédition peut être mise à jour par l`utilisateur.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Délai entre la réception de l`envoi (statut = reçu) et l`expiration. Utilisé pour les nouveaux lots créés via des expéditions, mais la date d`expiration réelle de toute expédition peut être mise à jour par l`utilisateur.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tempo desde o recebimento da remessa (status = recebido) até o vencimento. Usado para novos lotes criados por meio de remessas, mas a data de validade real de qualquer remessa pode ser atualizada pelo usuário.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.programPU.catalogPriceTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'This default catalog price is used for any new shipments created, unless the procurement agent specific price is provided. The actual price for any shipment can be by the user.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ce prix catalogue par défaut est utilisé pour toute nouvelle expédition créée, sauf si le prix spécifique à l`agent d`approvisionnement est fourni. Le prix réel pour tout envoi peut être par l`utilisateur.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ce prix catalogue par défaut est utilisé pour toute nouvelle expédition créée, sauf si le prix spécifique à l`agent d`approvisionnement est fourni. Le prix réel pour tout envoi peut être par l`utilisateur.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Esse preço de catálogo padrão é usado para quaisquer novas remessas criadas, a menos que o preço específico do agente de compras seja fornecido. O preço real de qualquer remessa pode ser pelo usuário.');-- pr



update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='You might have an older version. You should clear site and control+F5 now.'
where l.LABEL_CODE='static.coreui.oldVersion' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Vous avez peut-être une version plus ancienne. Vous devez effacer le site et contrôler + F5 maintenant.'
where l.LABEL_CODE='static.coreui.oldVersion' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Es posible que tenga una versión anterior. Debería borrar el sitio y controlar + F5 ahora.'
where l.LABEL_CODE='static.coreui.oldVersion' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Você pode ter uma versão mais antiga. Você deve limpar o site e controlar + F5 agora.'
where l.LABEL_CODE='static.coreui.oldVersion' and ll.LANGUAGE_ID=4;
