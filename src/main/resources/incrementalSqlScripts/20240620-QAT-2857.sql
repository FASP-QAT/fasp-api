ALTER TABLE `fasp`.`rm_program_planning_unit` 
ADD COLUMN `FORECAST_ERROR_THRESHOLD` DECIMAL(10,4) NULL AFTER `DISTRIBUTION_LEAD_TIME`,
ADD COLUMN `NOTES` TEXT NULL AFTER `FORECAST_ERROR_THRESHOLD`;

UPDATE `rm_program_planning_unit` ppu SET ppu.FORECAST_ERROR_THRESHOLD = 50, ppu.LAST_MODIFIED_DATE=now();

USE `fasp`;
DROP procedure IF EXISTS `forecastMetricsComparision`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`forecastMetricsComparision`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `forecastMetricsComparision`( 
	VAR_USER_ID INT(10), 
	VAR_REALM_ID INT(10), 
	VAR_START_DATE DATE, 
	VAR_REALM_COUNTRY_IDS TEXT, 
	VAR_PROGRAM_IDS TEXT, 
	VAR_TRACER_CATEGORY_IDS TEXT, 
	VAR_PLANNING_UNIT_IDS TEXT,  
	VAR_PREVIOUS_MONTHS INT(10), 
	VAR_APPROVED_SUPPLY_PLAN_ONLY TINYINT(1))
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 5
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    -- realmId since it is a Global report need to include Realm
    -- startDate - date that the report is to be run for
    -- realmCountryIds list of countries that we need to run the report for
    -- programIds is the list of programs that we need to run the report for
    -- planningUnitIds is the list of planningUnits that we need to run the report for
    -- previousMonths is the number of months that the calculation should go in the past for (excluding the current month) calculation of WAPE formulae
    -- current month is always included in the calculation
    -- only consider those months that have both a Forecasted and Actual consumption
    -- WAPE Formulae
    -- ((Abs(actual consumption month 1-forecasted consumption month 1)+ Abs(actual consumption month 2-forecasted consumption month 2)+ Abs(actual consumption month 3-forecasted consumption month 3)+ Abs(actual consumption month 4-forecasted consumption month 4)+ Abs(actual consumption month 5-forecasted consumption month 5)+ Abs(actual consumption month 6-forecasted consumption month 6)) / (Sum of all actual consumption in the last 6 months)) 
    
    DECLARE curRealmCountryId INT;
    DECLARE curHealthAreaId INT;
    DECLARE curOrganisationId INT;
    DECLARE curProgramId INT;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_acl CURSOR FOR SELECT acl.REALM_COUNTRY_ID, acl.HEALTH_AREA_ID, acl.ORGANISATION_ID, acl.PROGRAM_ID FROM us_user_acl acl WHERE acl.USER_ID=VAR_USER_ID;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    -- INSERT INTO log VALUES (null, now(), "Starting SP");
    SET @aclSqlString = CONCAT("       AND (FALSE ");
    OPEN cursor_acl;
	read_loop: LOOP
        FETCH cursor_acl INTO curRealmCountryId, curHealthAreaId, curOrganisationId, curProgramId;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        SET @aclSqlString = CONCAT(@aclSqlString,"       OR (");
        SET @aclSqlString = CONCAT(@aclSqlString,"           (p.PROGRAM_ID IS NULL OR ",IFNULL(curRealmCountryId,-1),"=-1 OR p.REALM_COUNTRY_ID=",IFNULL(curRealmCountryId,-1),")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curHealthAreaId,-1)  ,"=-1 OR FIND_IN_SET(",IFNULL(curHealthAreaId,-1),",p.HEALTH_AREA_ID))");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curOrganisationId,-1)  ,"=-1 OR FIND_IN_SET(",IFNULL(curOrganisationId,-1),",p.ORGANISATION_ID))");
        SET @aclSqlString = CONCAT(@aclSqlString,"       AND (p.PROGRAM_ID IS NULL OR ",IFNULL(curProgramId,-1)     ,"=-1 OR p.PROGRAM_ID="      ,IFNULL(curProgramId,-1)     ,")");
        SET @aclSqlString = CONCAT(@aclSqlString,"       )");
    END LOOP;
    CLOSE cursor_acl;
    -- INSERT INTO log VALUES (null, now(), "Completed loop");
    SET @aclSqlString = CONCAT(@aclSqlString, ") ");

    SET @varRealmId = VAR_REALM_ID;
    SET @varStopDate = VAR_START_DATE;
    SET @varStartDate = SUBDATE(VAR_START_DATE, INTERVAL VAR_PREVIOUS_MONTHS MONTH);
    SET @varApprovedSupplyPlanOnly = VAR_APPROVED_SUPPLY_PLAN_ONLY;
    SET @varRealmCountryIds = VAR_REALM_COUNTRY_IDS;
    SET @varProgramIds = VAR_PROGRAM_IDS;
    SET @varTracerCategoryIds = VAR_TRACER_CATEGORY_IDS;
    SET @varPlanningUnitIds = VAR_PLANNING_UNIT_IDS;
    
    
    DROP TABLE IF EXISTS tmp_forecastMetrics1;
    CREATE TEMPORARY TABLE tmp_forecastMetrics1 (
        `PROGRAM_ID` int unsigned NOT NULL,
        `VERSION_ID` int unsigned NOT NULL,
        `PLANNING_UNIT_ID` int unsigned NOT NULL,
        `TRANS_DATE` date NOT NULL,
        `ACTUAL` tinyint(1) NOT NULL,
        `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
        `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
        PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

    DROP TABLE IF EXISTS tmp_forecastMetrics2;
    CREATE TEMPORARY TABLE tmp_forecastMetrics2 (
        `PROGRAM_ID` int unsigned NOT NULL,
        `VERSION_ID` int unsigned NOT NULL,
        `PLANNING_UNIT_ID` int unsigned NOT NULL,
        `TRANS_DATE` date NOT NULL,
        `ACTUAL` tinyint(1) NOT NULL,
        `ACTUAL_CONSUMPTION_QTY` int DEFAULT NULL,
        `FORECASTED_CONSUMPTION_QTY` int DEFAULT NULL,
        PRIMARY KEY (`PROGRAM_ID`,`VERSION_ID`,`PLANNING_UNIT_ID`,`TRANS_DATE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
    -- INSERT INTO log VALUES (null, now(), "Temporary tables created");
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "INSERT INTO tmp_forecastMetrics1 ");
    SET @sqlString = CONCAT(@sqlString, "SELECT p1.PROGRAM_ID, p1.MAX_VERSION, spa.PLANNING_UNIT_ID, spa.TRANS_DATE, spa.ACTUAL, spa.ACTUAL_CONSUMPTION_QTY, spa.FORECASTED_CONSUMPTION_QTY");
    SET @sqlString = CONCAT(@sqlString, "   FROM (");
    SET @sqlString = CONCAT(@sqlString, "       SELECT ");
    SET @sqlString = CONCAT(@sqlString, "           pv.PROGRAM_ID, MAX(pv.VERSION_ID) MAX_VERSION ");
    SET @sqlString = CONCAT(@sqlString, "       FROM rm_program_version pv ");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN vw_program p ON pv.PROGRAM_ID=p.PROGRAM_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "       LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "	WHERE ");
    SET @sqlString = CONCAT(@sqlString, "           TRUE ");
    SET @sqlString = CONCAT(@sqlString, "           AND (@varApprovedSupplyPlanOnly=0 OR (@varApprovedSupplyPlanOnly=1 AND pv.VERSION_TYPE_ID=2 AND pv.VERSION_STATUS_ID=2))");
    SET @sqlString = CONCAT(@sqlString, "           AND p.ACTIVE");
    SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@varProgramIds)=0 OR FIND_IN_SET(pv.PROGRAM_ID, @varProgramIds))");
    SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@varRealmCountryIds)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, @varRealmCountryIds))");
    SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@varPlanningUnitIds)=0 OR FIND_IN_SET(pu.PLANNING_UNIT_ID, @varPlanningUnitIds))");
    SET @sqlString = CONCAT(@sqlString, "           AND (LENGTH(@varTracerCategoryIds)=0 OR FIND_IN_SET(fu.TRACER_CATEGORY_ID, @varTracerCategoryIds)) ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "   GROUP BY pv.PROGRAM_ID");
    SET @sqlString = CONCAT(@sqlString, "   ) p1");
    SET @sqlString = CONCAT(@sqlString, "   LEFT JOIN rm_supply_plan_amc spa ON p1.PROGRAM_ID=spa.PROGRAM_ID AND p1.MAX_VERSION=spa.VERSION_ID AND spa.TRANS_DATE BETWEEN @varStartDate AND @varStopDate");
    SET @sqlString = CONCAT(@sqlString, "   LEFT JOIN rm_planning_unit pu ON spa.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "   LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID");
    SET @sqlString = CONCAT(@sqlString, "   WHERE ");
    SET @sqlString = CONCAT(@sqlString, "       TRUE");
    SET @sqlString = CONCAT(@sqlString, "       AND spa.PLANNING_UNIT_ID IS NOT NULL");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@varPlanningUnitIds)=0 OR FIND_IN_SET(spa.PLANNING_UNIT_ID, @varPlanningUnitIds))");
    SET @sqlString = CONCAT(@sqlString, "       AND (LENGTH(@varTracerCategoryIds)=0 OR FIND_IN_SET(fu.TRACER_CATEGORY_ID, @varTracerCategoryIds))");
    PREPARE S2 FROM @sqlString;
    EXECUTE S2;
    -- INSERT INTO log VALUES (null, now(), "tmp_forecastMetrics1 completed");
    INSERT INTO tmp_forecastMetrics2 SELECT * FROM tmp_forecastMetrics1;
    -- INSERT INTO log VALUES (null, now(), "tmp_forecastMetrics2 completed");
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "   fm.TRANS_DATE, ");
    SET @sqlString = CONCAT(@sqlString, "   p.PROGRAM_ID, p.PROGRAM_CODE, p.LABEL_ID `PROGRAM_LABEL_ID`, p.LABEL_EN `PROGRAM_LABEL_EN`, p.LABEL_FR `PROGRAM_LABEL_FR`, p.LABEL_SP `PROGRAM_LABEL_SP`, p.LABEL_PR `PROGRAM_LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, pu.LABEL_PR  `PLANNING_UNIT_LABEL_PR`,ppu.FORECAST_ERROR_THRESHOLD, ");
    SET @sqlString = CONCAT(@sqlString, "   fm.ACTUAL, fm.ACTUAL_CONSUMPTION_QTY `ACTUAL_CONSUMPTION`, fm.FORECASTED_CONSUMPTION_QTY `FORECASTED_CONSUMPTION`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, fm2.ACTUAL_CONSUMPTION_QTY, null)) `ACTUAL_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, ABS(fm2.ACTUAL_CONSUMPTION_QTY-fm2.FORECASTED_CONSUMPTION_QTY), null)) `DIFF_CONSUMPTION_TOTAL`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, 1, 0)) `MONTH_COUNT`, ");
    SET @sqlString = CONCAT(@sqlString, "   SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, ABS(fm2.ACTUAL_CONSUMPTION_QTY-fm2.FORECASTED_CONSUMPTION_QTY), null))*100/SUM(IF(fm2.ACTUAL AND fm2.ACTUAL_CONSUMPTION_QTY IS NOT NULL AND fm2.FORECASTED_CONSUMPTION_QTY IS NOT NULL, fm2.ACTUAL_CONSUMPTION_QTY, null)) `FORECAST_ERROR` ");
    SET @sqlString = CONCAT(@sqlString, "FROM tmp_forecastMetrics1 fm ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON fm.PROGRAM_ID=p.PROGRAM_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_planning_unit pu ON fm.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND pu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN tmp_forecastMetrics2 fm2 ON fm.PROGRAM_ID=fm2.PROGRAM_ID AND fm.VERSION_ID=fm2.VERSION_ID AND fm.PLANNING_UNIT_ID=fm2.PLANNING_UNIT_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE fm.TRANS_DATE=@varStopDate ");
    SET @sqlString = CONCAT(@sqlString, @aclSqlString);
    SET @sqlString = CONCAT(@sqlString, "GROUP BY fm.PROGRAM_ID, fm.VERSION_ID, fm.PLANNING_UNIT_ID;");
    PREPARE S3 FROM @sqlString;
    EXECUTE S3;
    -- INSERT INTO log VALUES (null, now(), "Main query completed");
END$$

DELIMITER ;
;

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.pu.forecastErrorThresholdPercentage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error Threshold (%)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuil d`erreur de prévision (%)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Umbral de error de pronóstico (%)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Limite de erro de previsão (%)');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.pu.puNotesTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add notes about product phase in/out, data sources, primary funders. etc. Notes will be visible in Supply Plan Report and in Supply Plan Version and Review screen.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajoutez des notes sur l`entrée/sortie progressive du produit, les sources de données et les principaux bailleurs de fonds. etc. Les notes seront visibles dans le rapport du plan d`approvisionnement et dans l`écran Version et révision du plan d`approvisionnement.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregue notas sobre la entrada/salida gradual del producto, fuentes de datos y financiadores principales. etc. Las notas serán visibles en el Informe del plan de suministro y en la pantalla Versión y revisión del plan de suministro.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicione notas sobre a entrada/saída progressiva do produto, fontes de dados, financiadores primários. etc. As notas ficarão visíveis no Relatório do Plano de Fornecimento e na tela Versão e Revisão do Plano de Fornecimento.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.planningUnitNotes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Planning Unit Notes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Notes sur l`unité de planification');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de la unidad de planificación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Notas da Unidade de Planejamento');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tree.conversionFUToPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'FU:PU Conversion Factor');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion FU:PU');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'FU:Factor de conversión de PU');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'FU: Fator de conversão PU');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.conversionFactorPU','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'For more details, go to Realm Level Masters > Products > Planning Units. If data looks incorrect, please submit a ticket in the QAT helpdesk.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pour plus de détails, accédez à Maîtres de niveau domaine > Produits > Unités de planification. Si les données semblent incorrectes, veuillez soumettre un ticket au service d`assistance QAT.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Para obtener más detalles, vaya a Realm Level Masters > Productos > Unidades de planificación. Si los datos parecen incorrectos, envíe un ticket al servicio de asistencia técnica de QAT.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Para obter mais detalhes, acesse Mestres de nível de reino > Produtos > Unidades de planejamento. Se os dados parecerem incorretos, envie um ticket no helpdesk do QAT.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.planningUnitSettings','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Admins can update Planning Unit Settings & Notes under Program Management > Update Planning Unit.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les administrateurs du programme peuvent mettre à jour les paramètres et les notes de l`unité de planification sous Gestion du programme > Mettre à jour l`unité de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los administradores del programa pueden actualizar la configuración y las notas de la unidad de planificación en Gestión del programa > Actualizar unidad de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os administradores do programa podem atualizar as configurações e notas da unidade de planejamento em Gerenciamento de programas > Atualizar unidade de planejamento.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.tooltip.planningUnitNotes','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Admins can update Planning Unit Notes under Program Management > Update Planning Unit.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les administrateurs du programme peuvent mettre à jour les notes de l`unité de planification sous Gestion du programme > Mettre à jour l`unité de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los administradores del programa pueden actualizar las notas de la unidad de planificación en Gestión del programa > Actualizar unidad de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Os administradores do programa podem atualizar as notas da unidade de planejamento em Gerenciamento de programas > Atualizar unidade de planejamento.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastErrorReport.planningUnitAboveThreshold','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Calculated Forecast Error is above the Forecast Error Threshold');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'L`erreur de prévision calculée est supérieure au seuil d`erreur de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El error de pronóstico calculado está por encima del umbral de error de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O erro de previsão calculado está acima do limite de erro de previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastErrorReport.forecastErrorThreshold','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Error Threshold');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Seuil d`erreur de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Umbral de error de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Limite de erro de previsão');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastErrorReport.planningUnitAboveThresholdNote','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: The Forecast Error Threshold values will not be highlighted red if viewing this report in Equivalency Units or multiple Forecasting Units/Planning Units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : Les valeurs du seuil d`erreur de prévision ne seront pas surlignées en rouge si ce rapport est affiché en unités déquivalence ou en plusieurs unités de prévision/unités de planification.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: Los valores del Umbral de error de pronóstico no se resaltarán en rojo si se visualiza este informe en Unidades de equivalencia o en varias Unidades de pronóstico/Unidades de planificación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nota: Os valores do Limite de Erro de Previsão não serão destacados em vermelho se este relatório for visualizado em Unidades de Equivalência ou em múltiplas Unidades de Previsão/Unidades de Planejamento');-- pr