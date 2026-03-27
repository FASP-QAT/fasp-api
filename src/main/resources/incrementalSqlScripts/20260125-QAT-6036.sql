INSERT INTO `fasp`.`ap_security` (`METHOD`, `URL`, `BF`) VALUES ('2', '/api/report/stockStatusMatrixGlobal', 'ROLE_BF_STOCK_STATUS_MATRIX_REPORT');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.showInEu','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Show data in equivalency unit');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher les données en unité d''équivalence');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar datos en unidad de equivalencia');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar dados em unidade de equivalência');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.stockStatusMatrixGlobal','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock Status Matrix (Global)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Matrice de l''état des stocks (Global)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Matriz del estado de las existencias (Global)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Matriz de status do estoque (Global)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.stockStatus.showInEuTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT is able to aggregate across different products (different pack sizes, products, etc.), by utilizing Equivalency Units, which are mapped to different forecasting units. View under Realm Masters > Products > Equivalency Units. Realm-level mappings are available to all users. Program admins can also create program-specific mappings.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'QAT est capable d''agréger différents produits (différentes tailles de conditionnement, produits, etc.), en utilisant des unités d''équivalence, qui sont mappées à différentes unités de prévision. Voir sous Maîtres de domaine > Produits > Unités d''équivalence. Les mappages au niveau du domaine sont disponibles pour tous les utilisateurs. Les administrateurs de programme peuvent également créer des mappages spécifiques au programme.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'QAT puede agregar diferentes productos (diferentes tamaños de envases, productos, etc.), utilizando unidades de equivalencia, que están mapeadas a diferentes unidades de pronóstico. Ver en Maestros de Dominio > Productos > Unidades de Equivalencia. Los mapeos a nivel de dominio están disponibles para todos los usuarios. Los administradores de programas también pueden crear mapeos específicos del programa.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O QAT é capaz de agregar diferentes produtos (diferentes tamanhos de embalagens, produtos, etc.), utilizando Unidades de Equivalência, que são mapeadas para diferentes unidades de previsão. Veja em Mestres de Domínio > Produtos > Unidades de Equivalência. Os mapeamentos de nível de domínio estão disponíveis para todos os usuários. Os administradores de programas também podem criar mapeamentos específicos do programa.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.showPUsInAllProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Only show PUs available in ALL programs');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Afficher uniquement les PU disponibles dans TOUS les programmes');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Mostrar solo las PU disponibles en TODOS los programas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Mostrar apenas as PUs disponíveis em TODOS os programas');-- pr


USE `fasp`;
DROP procedure IF EXISTS `stockStatusMatrixGlobal`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`stockStatusMatrixGlobal`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`localhost` PROCEDURE `stockStatusMatrixGlobal`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_REALM_COUNTRY_IDS TEXT, VAR_PROGRAM_IDS TEXT, VAR_VERSION_ID INT(10), VAR_EQUIVALENCY_UNIT_ID INT(10), VAR_PLANNING_UNIT_IDS TEXT, VAR_STOCK_STATUS_CONDITIONS TEXT, VAR_REMOVE_PLANNED_SHIPMENTS TINYINT(1), VAR_REPORT_VIEW INT(10))
BEGIN
    
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Report no 18b
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
    
    -- startDate and stopDate are the period for which you want to run the report
    -- realmCountryIds is the list of Countries that you want to run the report for; Empty means all countries
    -- programIds is the list of Programs that you want to run the report for; Empty means all programs
    -- versionId Only to be used when a Single Program is selected. So if you get VersionId<>0 it means only the first ProgramId should be used from the list; -1 would mean that use the latest VersionId
    -- equivalencyUnitId When passed as 0 it means that No EquivalencyUnitId was selected and therefore only a single PlanningUnit can be selected; If an EquivalencyUnitId is selected it means that multiple PlanningUnits can be selected since the report is to be showing in terms of EU
    -- planningUnitIds is the list of Planning Units that you want to include in the report; Empty means all PlanningUnitIds; When EU is 0 then only a single PU can be selected
    -- stockStatusIds is the list of Stock Statuses that you want to show in the report; For now this is not possible need to go back to FASP about this option.
    -- removePlannedShipments = 0 means that you want to retain all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 1 means that you want to remove all Shipments in the Planned stage when the Version was saved.
    -- removePlannedShipments = 2 means that you want to remove the shipments that have Funding Source as TBD and are in the Planned stage when the Version was saved.
    -- AMC is calculated based on the MonthsInPastForAMC and MonthsInFutureForAMC from the Program setup
    -- Current month is always included in AMC
    -- reportView=1 means show in terms of MoS and Qty based on the PlannedBasedOn setting, but if even one of them in the group by is plan_based_on=2 then change to Qty; If reportView=2 it means show everything in terms of Qty but retain the color coding and StockStatusId
    
    DECLARE curMn DATE;
    DECLARE done INT DEFAULT FALSE;
    DECLARE cursor_mn CURSOR FOR SELECT mn.MONTH FROM mn WHERE mn.MONTH BETWEEN VAR_START_DATE and VAR_STOP_DATE;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET @varRemovePlannedShipments = VAR_REMOVE_PLANNED_SHIPMENTS;
    SET @varReportView = VAR_REPORT_VIEW;
    SET @mnSqlString = "";
    
    OPEN cursor_mn;
        read_loop: LOOP
        FETCH cursor_mn INTO curMn;
        IF done THEN
            LEAVE read_loop;
        END IF;
        -- For each loop build the sql for acl
        SET @mnSqlString = CONCAT(@mnSqlString, "   	, GROUP_CONCAT(IF(mn.MONTH='",curMn,"', amc2.PLANNING_UNIT_IDS, null)) `PLANNING_UNIT_IDS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.MOS, null)) `MOS_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.AMC, null)) `AMC_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.STOCK_STATUS_ID, null)) `STOCK_STATUS_ID_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.CLOSING_BALANCE, null)) `CLOSING_BALANCE_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.STOCK_MULTIPLIED_QTY, null)) `STOCK_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.SHIPMENT_QTY, null)) `SHIPMENT_QTY_",curMn,"` ");
        SET @mnSqlString = CONCAT(@mnSqlString,"		, SUM(IF(mn.MONTH='",curMn,"', amc2.EXPIRED_STOCK_QTY, null)) `EXPIRED_STOCK_QTY_",curMn,"` ");
    END LOOP;
    CLOSE cursor_mn;
    
    SET @varVersionId = VAR_VERSION_ID;
    IF VAR_VERSION_ID = 0 THEN 
	SET @varVersionId = null;
    ELSEIF VAR_VERSION_ID = -1 THEN
        SELECT MAX(pv.VERSION_ID) INTO @varVersionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=VAR_PROGRAM_ID;
    END IF;

    DROP TABLE IF EXISTS tmp_amc;
    CREATE TEMPORARY TABLE `tmp_amc` (
        `TRANS_DATE` date,
        `PROGRAM_ID` int unsigned NOT NULL DEFAULT '0' COMMENT 'Unique Id for each Program',
        `REALM_COUNTRY_ID` int unsigned NOT NULL COMMENT 'Foreign key to indicate which Realm-Country this Program belongs to',
        `PLANNING_UNIT_ID` int unsigned,
        `PLAN_BASED_ON` int unsigned COMMENT '1- MoS , 2- Qty',
        `MIN_MONTHS_OF_STOCK` int unsigned DEFAULT NULL,
        `REORDER_FREQUENCY_IN_MONTHS` int unsigned COMMENT 'Min number of months of stock that we should have before triggering a reorder',
        `MIN_STOCK_QTY` decimal(24,8) DEFAULT NULL,
        `MIN_STOCK_MOS` decimal(24,8) DEFAULT NULL,
        `MAX_STOCK_QTY` decimal(24,8) DEFAULT NULL,
        `MAX_STOCK_MOS` decimal(24,8) DEFAULT NULL,
        `CLOSING_BALANCE` decimal(24,8) DEFAULT NULL,
        `MOS` decimal(24,8) DEFAULT NULL,
        `SHIPMENT_QTY` decimal(28,8) DEFAULT NULL,
        `EXPIRED_STOCK_QTY` decimal(24,8) DEFAULT NULL,
        `AMC` decimal(24,8) DEFAULT NULL,
        `STOCK_MULTIPLIED_QTY` decimal(24,8) DEFAULT NULL,
        `CONVERSION` decimal(24,6) DEFAULT NULL,
        KEY `idx_tmpAmc_transDate` (`TRANS_DATE`),
        KEY `idx_tmpAmc_programId` (`PROGRAM_ID`),
        KEY `idx_tmpAmc_realmCountryId` (`REALM_COUNTRY_ID`),
        KEY `idx_tmpAmc_planningUnitId` (`PLANNING_UNIT_ID`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
    
    INSERT INTO tmp_amc 
    SELECT 
        amc.TRANS_DATE, p.PROGRAM_ID, p.REALM_COUNTRY_ID,
        amc.PLANNING_UNIT_ID, ppu.PLAN_BASED_ON, ppu.MIN_MONTHS_OF_STOCK, ppu.REORDER_FREQUENCY_IN_MONTHS, amc.MIN_STOCK_QTY, amc.MIN_STOCK_MOS, amc.MAX_STOCK_QTY, amc.MAX_STOCK_MOS,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.CLOSING_BALANCE WHEN 1 THEN amc.CLOSING_BALANCE_WPS WHEN 2 THEN amc.CLOSING_BALANCE_WTBDPS END `CLOSING_BALANCE`,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.MOS WHEN 1 THEN amc.MOS_WPS WHEN 2 THEN amc.MOS_WTBDPS END `MOS`,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.SHIPMENT_QTY WHEN 1 THEN amc.SHIPMENT_QTY-amc.MANUAL_PLANNED_SHIPMENT_QTY-amc.ERP_PLANNED_SHIPMENT_QTY WHEN 2 THEN amc.SHIPMENT_QTY-amc.MANUAL_PLANNED_SHIPMENT_QTY-amc.ERP_PLANNED_SHIPMENT_QTY+amc.MANUAL_PLANNED_SHIPMENT_WTBD_QTY+amc.ERP_PLANNED_SHIPMENT_WTBD_QTY END `SHIPMENT_QTY`,
        CASE @varRemovePlannedShipments WHEN 0 THEN amc.EXPIRED_STOCK WHEN 1 THEN amc.EXPIRED_STOCK_WPS WHEN 2 THEN amc.EXPIRED_STOCK_WTBDPS END `EXPIRED_STOCK_QTY`,
        amc.AMC, amc.STOCK_MULTIPLIED_QTY, IF(VAR_EQUIVALENCY_UNIT_ID=0, 1, pu.MULTIPLIER/COALESCE(eum1.CONVERT_TO_EU, eum2.CONVERT_TO_EU)) `CONVERSION`
    FROM vw_program p 
    LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID AND (LENGTH(VAR_PLANNING_UNIT_IDS)=0 OR FIND_IN_SET(ppu.PLANNING_UNIT_ID, VAR_PLANNING_UNIT_IDS))
    LEFT JOIN rm_supply_plan_amc amc ON amc.PROGRAM_ID=p.PROGRAM_ID AND amc.VERSION_ID=COALESCE(@varVersionId, p.CURRENT_VERSION_ID) AND amc.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID AND amc.TRANS_DATE BETWEEN VAR_START_DATE AND VAR_STOP_DATE
    LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN rm_equivalency_unit_mapping eum1 ON pu.FORECASTING_UNIT_ID=eum1.FORECASTING_UNIT_ID AND eum1.PROGRAM_ID=p.PROGRAM_ID AND eum1.EQUIVALENCY_UNIT_ID=VAR_EQUIVALENCY_UNIT_ID
    LEFT JOIN rm_equivalency_unit_mapping eum2 ON pu.FORECASTING_UNIT_ID=eum2.FORECASTING_UNIT_ID AND eum2.PROGRAM_ID IS NULL AND eum2.EQUIVALENCY_UNIT_ID=VAR_EQUIVALENCY_UNIT_ID
    WHERE TRUE AND p.ACTIVE
        AND (LENGTH(VAR_REALM_COUNTRY_IDS)=0 OR FIND_IN_SET(p.REALM_COUNTRY_ID, VAR_REALM_COUNTRY_IDS))
        AND (LENGTH(VAR_PROGRAM_IDS)=0 OR FIND_IN_SET(p.PROGRAM_ID, VAR_PROGRAM_IDS))
        AND ppu.PLANNING_UNIT_ID is not null;
	
    DROP TABLE IF EXISTS tmp_amc2;
    CREATE TEMPORARY TABLE `tmp_amc2` (
        `ID` int unsigned NOT NULL DEFAULT '0',
        `TRANS_DATE` date DEFAULT NULL,
        `AMC` decimal(65,14) DEFAULT NULL,
        `PLANNING_UNIT_IDS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
        `MOS` decimal(46,8) DEFAULT NULL,
        `PLAN_BASED_ON` int NOT NULL DEFAULT '0',
        `STOCK_STATUS_ID` int NOT NULL DEFAULT '0',
        `CLOSING_BALANCE` decimal(65,14) DEFAULT NULL,
        `STOCK_MULTIPLIED_QTY` decimal(65,14) DEFAULT NULL,
        `SHIPMENT_QTY` decimal(65,14) DEFAULT NULL,
        `EXPIRED_STOCK_QTY` decimal(65,14) DEFAULT NULL,
        KEY `idx_tmpAmc2_id` (`ID`),
        KEY `idx_tmpAmc2_transDate` (`TRANS_DATE`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
    
    INSERT INTO tmp_amc2
    SELECT 
        IF(@varReportView=1, amc.PROGRAM_ID, amc.REALM_COUNTRY_ID) `ID`,
        amc.TRANS_DATE, SUM(amc.AMC*amc.CONVERSION) `AMC`,
        GROUP_CONCAT(DISTINCT amc.PLANNING_UNIT_ID) `PLANNING_UNIT_IDS`,
        SUM(amc.CLOSING_BALANCE*amc.CONVERSION)/SUM(amc.AMC*amc.CONVERSION) `MOS`,
        IF(SUM(amc.PLAN_BASED_ON)/COUNT(amc.PLAN_BASED_ON)=1,1,2) `PLAN_BASED_ON`,
        IF(
            SUM(amc.PLAN_BASED_ON)/COUNT(amc.PLAN_BASED_ON)=1,
            CASE 
                WHEN SUM(amc.CLOSING_BALANCE*amc.CONVERSION)/SUM(amc.AMC*amc.CONVERSION) IS NULL THEN 4 
                WHEN SUM(amc.CLOSING_BALANCE*amc.CONVERSION)/SUM(amc.AMC*amc.CONVERSION) = 0 THEN 0
                WHEN SUM(amc.CLOSING_BALANCE*amc.CONVERSION)/SUM(amc.AMC*amc.CONVERSION) < AVG(amc.MIN_STOCK_MOS) THEN 1
                WHEN SUM(amc.CLOSING_BALANCE*amc.CONVERSION)/SUM(amc.AMC*amc.CONVERSION) <= AVG(amc.MAX_STOCK_MOS) THEN 2
                ELSE 3
            END,
            CASE 
                WHEN SUM(amc.CLOSING_BALANCE*amc.CONVERSION)=0 THEN 0
                WHEN SUM(amc.CLOSING_BALANCE*amc.CONVERSION)<AVG(amc.MIN_STOCK_QTY) THEN 1
                WHEN SUM(amc.CLOSING_BALANCE*amc.CONVERSION)<=AVG(amc.MAX_STOCK_QTY) THEN 2
                ELSE 3
            END
        ) `STOCK_STATUS_ID`,
        SUM(amc.CLOSING_BALANCE*amc.CONVERSION) `CLOSING_BALANCE`,
        CASE WHEN COUNT(amc.STOCK_MULTIPLIED_QTY)=count(*) THEN SUM(amc.STOCK_MULTIPLIED_QTY*amc.CONVERSION) ELSE NULL END `STOCK_MULTIPLIED_QTY`,
        SUM(amc.SHIPMENT_QTY*amc.CONVERSION) `SHIPMENT_QTY`,
        SUM(amc.EXPIRED_STOCK_QTY*amc.CONVERSION) `EXPIRED_STOCK_QTY`
    FROM tmp_amc amc
    group by amc.TRANS_DATE, IF(@varReportView=1, amc.PROGRAM_ID, amc.REALM_COUNTRY_ID);
	
    SET @sqlString = "";
    SET @sqlString = CONCAT(@sqlString, "SELECT ");
    SET @sqlString = CONCAT(@sqlString, "   amc2.`ID`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_ID, c.LABEL_ID) `LABEL_ID`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_EN, c.LABEL_EN) `LABEL_EN`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_FR, c.LABEL_FR) `LABEL_FR`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_SP, c.LABEL_SP) `LABEL_SP`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.LABEL_PR, c.LABEL_PR) `LABEL_PR`, ");
    SET @sqlString = CONCAT(@sqlString, "   COALESCE(p.PROGRAM_CODE, c.COUNTRY_CODE) `CODE` ");
    SET @sqlString = CONCAT(@sqlString, @mnSqlString);
    SET @sqlString = CONCAT(@sqlString, "FROM mn ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN tmp_amc2 amc2 ON mn.MONTH=amc2.TRANS_DATE ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_program p ON amc2.ID=p.PROGRAM_ID AND @varReportView=1 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN rm_realm_country rc ON amc2.ID=rc.REALM_COUNTRY_ID AND @varReportView=2 ");
    SET @sqlString = CONCAT(@sqlString, "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID ");
    SET @sqlString = CONCAT(@sqlString, "WHERE mn.MONTH BETWEEN '",VAR_START_DATE,"' AND '",VAR_STOP_DATE,"' ");
    SET @sqlString = CONCAT(@sqlString, "GROUP BY amc2.ID ");
    PREPARE S1 FROM @sqlString;
    EXECUTE S1;
    
END$$

DELIMITER ;
;

