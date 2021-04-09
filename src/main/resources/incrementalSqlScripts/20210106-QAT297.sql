USE `fasp`;
DROP procedure IF EXISTS `stockStatusReportVertical`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `stockStatusReportVertical`(VAR_START_DATE DATE, VAR_STOP_DATE DATE, VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT, VAR_PLANNING_UNIT_ID INT(10))
BEGIN
	-- %%%%%%%%%%%%%%%%%%%%%
    -- Report no 16
	-- %%%%%%%%%%%%%%%%%%%%%
	
        SET @startDate = VAR_START_DATE;
	SET @stopDate = VAR_STOP_DATE;
	SET @programId = VAR_PROGRAM_ID;
	SET @versionId = VAR_VERSION_ID;
	SET @planningUnitId = VAR_PLANNING_UNIT_ID;

	IF @versionId = -1 THEN 
		SELECT MAX(pv.VERSION_ID) INTO @versionId FROM rm_program_version pv WHERE pv.PROGRAM_ID=@programId;
	END IF;
    
	SELECT 
        s2.`TRANS_DATE`,  
        s2.`PLANNING_UNIT_ID`, s2.`PLANNING_UNIT_LABEL_ID`, s2.`PLANNING_UNIT_LABEL_EN`, s2.`PLANNING_UNIT_LABEL_FR`, s2.`PLANNING_UNIT_LABEL_SP`, s2.`PLANNING_UNIT_LABEL_PR`,
        COALESCE(s2.`FINAL_OPENING_BALANCE`, @prvMonthClosingBal) `FINAL_OPENING_BALANCE`,
        s2.`ACTUAL_CONSUMPTION_QTY`, s2.`FORECASTED_CONSUMPTION_QTY`, 
        IF(s2.`ACTUAL`, s2.`ACTUAL_CONSUMPTION_QTY`,s2.`FORECASTED_CONSUMPTION_QTY`) `FINAL_CONSUMPTION_QTY`,
        s2.`ACTUAL`,
        s2.`SQTY` ,
        s2.`ADJUSTMENT`,
        s2.EXPIRED_STOCK,
        COALESCE(s2.`FINAL_CLOSING_BALANCE`, @prvMonthClosingBal) `FINAL_CLOSING_BALANCE`,
        s2.UNMET_DEMAND,
        s2.AMC,
        s2.`MoS`,
        s2.`MIN_MONTHS_OF_STOCK`,
        s2.`MAX_MONTHS_OF_STOCK`,
        s2.`SHIPMENT_ID`, s2.`SHIPMENT_QTY`, 
        s2.`FUNDING_SOURCE_ID`, s2.`FUNDING_SOURCE_CODE`, s2.`FUNDING_SOURCE_LABEL_ID`, s2.`FUNDING_SOURCE_LABEL_EN`, s2.`FUNDING_SOURCE_LABEL_FR`, s2.`FUNDING_SOURCE_LABEL_SP`, s2.`FUNDING_SOURCE_LABEL_PR`, 
        s2.PROCUREMENT_AGENT_ID, s2.PROCUREMENT_AGENT_CODE, s2.`PROCUREMENT_AGENT_LABEL_ID`, s2.`PROCUREMENT_AGENT_LABEL_EN`, s2.`PROCUREMENT_AGENT_LABEL_FR`, s2.`PROCUREMENT_AGENT_LABEL_SP`, s2.`PROCUREMENT_AGENT_LABEL_PR`, 
        s2.SHIPMENT_STATUS_ID, s2.`SHIPMENT_STATUS_LABEL_ID`, s2.`SHIPMENT_STATUS_LABEL_EN`, s2.`SHIPMENT_STATUS_LABEL_FR`, s2.`SHIPMENT_STATUS_LABEL_SP`, s2.`SHIPMENT_STATUS_LABEL_PR`,
        @prvMonthClosingBal:=COALESCE(s2.`FINAL_CLOSING_BALANCE`, s2.`FINAL_OPENING_BALANCE`, @prvMonthClosingBal) `PRV_CLOSING_BAL`,
        s2.EDD,
        s2.NOTES,
        s2.CONSUMPTION_DATE,
        s2.CONSUMPTION_NOTES,
        s2.CONSUMPTION_ID,
        s2.INVENTORY_DATE,
        s2.INVENTORY_NOTES,
        s2.INVENTORY_ID,
        s2.DATA_SOURCE_ID,s2.DATA_SOURCE_LABEL_ID,s2.DATA_SOURCE_LABEL_EN,s2.DATA_SOURCE_LABEL_FR,s2.DATA_SOURCE_LABEL_SP,s2.DATA_SOURCE_LABEL_PR,
		s2.CONSUMPTION_DATA_SOURCE_ID,s2.CONSUMPTION_DATA_SOURCE_LABEL_ID,s2.CONSUMPTION_DATA_SOURCE_LABEL_EN,s2.CONSUMPTION_DATA_SOURCE_LABEL_FR,s2.CONSUMPTION_DATA_SOURCE_LABEL_SP,s2.CONSUMPTION_DATA_SOURCE_LABEL_PR,        
        s2.INVENTORY_DATA_SOURCE_ID,s2.INVENTORY_DATA_SOURCE_LABEL_ID,s2.INVENTORY_DATA_SOURCE_LABEL_EN,s2.INVENTORY_DATA_SOURCE_LABEL_FR,s2.INVENTORY_DATA_SOURCE_LABEL_SP,s2.INVENTORY_DATA_SOURCE_LABEL_PR,        
        s2.CONSUMPTION_REGION_ID,s2.CONSUMPTION_REGION_LABEL_ID,s2.CONSUMPTION_REGION_LABEL_EN,s2.CONSUMPTION_REGION_LABEL_FR,s2.CONSUMPTION_REGION_LABEL_SP,s2.CONSUMPTION_REGION_LABEL_PR,        
        s2.INVENTORY_REGION_ID,s2.INVENTORY_REGION_LABEL_ID,s2.INVENTORY_REGION_LABEL_EN,s2.INVENTORY_REGION_LABEL_FR,s2.INVENTORY_REGION_LABEL_SP,s2.INVENTORY_REGION_LABEL_PR,        
        s2.ACTUAL_FLAG,
        s2.ADJUSTMENT_QTY,
		s2.ACTUAL_QTY        
    FROM (
	SELECT 
        mn.MONTH `TRANS_DATE`, 
        pu.`PLANNING_UNIT_ID`, pu.`LABEL_ID` `PLANNING_UNIT_LABEL_ID`, pu.`LABEL_EN` `PLANNING_UNIT_LABEL_EN`, pu.`LABEL_FR` `PLANNING_UNIT_LABEL_FR`, pu.`LABEL_SP` `PLANNING_UNIT_LABEL_SP`, pu.`LABEL_PR` `PLANNING_UNIT_LABEL_PR`,
        sma.OPENING_BALANCE `FINAL_OPENING_BALANCE`, 
        sma.ACTUAL_CONSUMPTION_QTY, sma.FORECASTED_CONSUMPTION_QTY, 
        sma.ACTUAL,
        sma.SHIPMENT_QTY SQTY,
        sma.ADJUSTMENT_MULTIPLIED_QTY `ADJUSTMENT`,
        sma.EXPIRED_STOCK,
        sma.CLOSING_BALANCE `FINAL_CLOSING_BALANCE`,
        sma.UNMET_DEMAND,
        sma.AMC,
        sma.MOS `MoS`,
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
        sh.SHIPMENT_ID, sh.SHIPMENT_QTY, sh.NOTES,sh.EDD,
        fs.FUNDING_SOURCE_ID, fs.FUNDING_SOURCE_CODE, fs.LABEL_ID `FUNDING_SOURCE_LABEL_ID`, fs.LABEL_EN `FUNDING_SOURCE_LABEL_EN`, fs.LABEL_FR `FUNDING_SOURCE_LABEL_FR`, fs.LABEL_SP `FUNDING_SOURCE_LABEL_SP`, fs.LABEL_PR `FUNDING_SOURCE_LABEL_PR`, 
        pa.PROCUREMENT_AGENT_ID, pa.PROCUREMENT_AGENT_CODE, pa.LABEL_ID `PROCUREMENT_AGENT_LABEL_ID`, pa.LABEL_EN `PROCUREMENT_AGENT_LABEL_EN`, pa.LABEL_FR `PROCUREMENT_AGENT_LABEL_FR`, pa.LABEL_SP `PROCUREMENT_AGENT_LABEL_SP`, pa.LABEL_PR `PROCUREMENT_AGENT_LABEL_PR`, 
        ss.SHIPMENT_STATUS_ID, ss.LABEL_ID `SHIPMENT_STATUS_LABEL_ID`, ss.LABEL_EN `SHIPMENT_STATUS_LABEL_EN`, ss.LABEL_Fr `SHIPMENT_STATUS_LABEL_FR`, ss.LABEL_SP `SHIPMENT_STATUS_LABEL_SP`, ss.LABEL_PR `SHIPMENT_STATUS_LABEL_PR`,
        sh.DATA_SOURCE_ID,
        shds.LABEL_ID `DATA_SOURCE_LABEL_ID`,shds.LABEL_EN `DATA_SOURCE_LABEL_EN`, shds.LABEL_FR `DATA_SOURCE_LABEL_FR`, shds.LABEL_SP `DATA_SOURCE_LABEL_SP`, shds.LABEL_PR `DATA_SOURCE_LABEL_PR`,
        conds.LABEL_ID `CONSUMPTION_DATA_SOURCE_LABEL_ID`,conds.LABEL_EN `CONSUMPTION_DATA_SOURCE_LABEL_EN`, conds.LABEL_FR `CONSUMPTION_DATA_SOURCE_LABEL_FR`, conds.LABEL_SP `CONSUMPTION_DATA_SOURCE_LABEL_SP`, conds.LABEL_PR `CONSUMPTION_DATA_SOURCE_LABEL_PR`,
        invds.LABEL_ID `INVENTORY_DATA_SOURCE_LABEL_ID`,invds.LABEL_EN `INVENTORY_DATA_SOURCE_LABEL_EN`, invds.LABEL_FR `INVENTORY_DATA_SOURCE_LABEL_FR`, invds.LABEL_SP `INVENTORY_DATA_SOURCE_LABEL_SP`, invds.LABEL_PR `INVENTORY_DATA_SOURCE_LABEL_PR`,
		conr.LABEL_ID `CONSUMPTION_REGION_LABEL_ID`,conr.LABEL_EN `CONSUMPTION_REGION_LABEL_EN`, conr.LABEL_FR `CONSUMPTION_REGION_LABEL_FR`, conr.LABEL_SP `CONSUMPTION_REGION_LABEL_SP`, conr.LABEL_PR `CONSUMPTION_REGION_LABEL_PR`,
        invr.LABEL_ID `INVENTORY_REGION_LABEL_ID`,invr.LABEL_EN `INVENTORY_REGION_LABEL_EN`, invr.LABEL_FR `INVENTORY_REGION_LABEL_FR`, invr.LABEL_SP `INVENTORY_REGION_LABEL_SP`, invr.LABEL_PR `INVENTORY_REGION_LABEL_PR`,        
        con.*,
        inv.*
    FROM
        mn 
        LEFT JOIN rm_supply_plan_amc sma ON 
            mn.MONTH=sma.TRANS_DATE 
            AND sma.PROGRAM_ID = @programId
            AND sma.VERSION_ID = @versionId
            AND sma.PLANNING_UNIT_ID = @planningUnitId
        LEFT JOIN 
            (
            SELECT COALESCE(st.RECEIVED_DATE, st.EXPECTED_DELIVERY_DATE) `EDD`, s.SHIPMENT_ID, st.SHIPMENT_QTY , st.FUNDING_SOURCE_ID, st.PROCUREMENT_AGENT_ID, st.SHIPMENT_STATUS_ID,st.NOTES,st.DATA_SOURCE_ID
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
        LEFT JOIN 
            (
            SELECT ct.CONSUMPTION_DATE, c.CONSUMPTION_ID, ct.NOTES AS CONSUMPTION_NOTES,ct.DATA_SOURCE_ID AS CONSUMPTION_DATA_SOURCE_ID,ct.REGION_ID AS CONSUMPTION_REGION_ID,ct.ACTUAL_FLAG
            FROM 
                (
                SELECT c.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE c.PROGRAM_ID=@programId AND ct.VERSION_ID<=@versionId AND ct.CONSUMPTION_TRANS_ID IS NOT NULL GROUP BY c.CONSUMPTION_ID 
            ) AS c
            LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID AND c.MAX_VERSION_ID=ct.VERSION_ID 
            WHERE 
                ct.ACTIVE 
                AND ct.CONSUMPTION_DATE BETWEEN @startDate AND @stopDate 
                AND ct.PLANNING_UNIT_ID =@planningUnitId
        ) con ON LEFT(sma.TRANS_DATE,7)=LEFT(con.CONSUMPTION_DATE,7)
        LEFT JOIN 
            (
            SELECT it.INVENTORY_DATE, i.INVENTORY_ID, it.NOTES AS INVENTORY_NOTES,it.DATA_SOURCE_ID AS INVENTORY_DATA_SOURCE_ID,it.REGION_ID AS INVENTORY_REGION_ID,it.ACTUAL_QTY,it.ADJUSTMENT_QTY
            FROM 
                (
                SELECT i.INVENTORY_ID, MAX(it.VERSION_ID) MAX_VERSION_ID FROM rm_inventory i LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID WHERE i.PROGRAM_ID=@programId AND it.VERSION_ID<=@versionId AND it.INVENTORY_TRANS_ID IS NOT NULL GROUP BY i.INVENTORY_ID 
            ) AS i
            LEFT JOIN rm_inventory_trans it ON i.INVENTORY_ID=it.INVENTORY_ID AND i.MAX_VERSION_ID=it.VERSION_ID 
	    LEFT JOIN rm_realm_country_planning_unit rcpu ON rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=it.REALM_COUNTRY_PLANNING_UNIT_ID	
            WHERE 
                it.ACTIVE 
                AND it.INVENTORY_DATE BETWEEN @startDate AND @stopDate 
                AND rcpu.PLANNING_UNIT_ID =@planningUnitId
        ) inv ON LEFT(sma.TRANS_DATE,7)=LEFT(inv.INVENTORY_DATE,7)
        LEFT JOIN vw_funding_source fs ON sh.FUNDING_SOURCE_ID=fs.FUNDING_SOURCE_ID
        LEFT JOIN vw_procurement_agent pa ON sh.PROCUREMENT_AGENT_ID=pa.PROCUREMENT_AGENT_ID
        LEFT JOIN vw_shipment_status ss ON sh.SHIPMENT_STATUS_ID=ss.SHIPMENT_STATUS_ID
        LEFT JOIN rm_program_planning_unit ppu ON ppu.PROGRAM_ID=@programId AND ppu.PLANNING_UNIT_ID=@planningUnitId
        LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN rm_program p ON p.PROGRAM_ID=@programId
        LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID
        LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID
        left join vw_data_source shds on shds.DATA_SOURCE_ID=sh.DATA_SOURCE_ID
        left join vw_data_source conds on conds.DATA_SOURCE_ID=con.CONSUMPTION_DATA_SOURCE_ID
        left join vw_data_source invds on invds.DATA_SOURCE_ID=inv.INVENTORY_DATA_SOURCE_ID
        left join vw_region conr on conr.REGION_ID=con.CONSUMPTION_REGION_ID
        left join vw_region invr on invr.REGION_ID=inv.INVENTORY_REGION_ID
    WHERE
        mn.MONTH BETWEEN @startDate AND @stopDate
    ORDER BY mn.MONTH) AS s2;
    
END$$

DELIMITER ;




INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.runDate','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Run Date:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date d`exécution:');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha de ejecución:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data de execução:');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.runTime','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Run Time:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Durée:');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Tiempo de ejecución:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Tempo de execução:');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.v','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'v');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'v');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'v');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'v');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.approved','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Ordered (Submitted, Approved)​');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Commandé (soumis, approuvé)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pedido (enviado, aprobado)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Solicitado (enviado, aprovado)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.qty','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Qty');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Qté');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cant.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Qty');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.funding','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Funding');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Financement');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fondos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Financiamento');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.procAgent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Proc Agent');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Agent Proc');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agente de proceso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Agente Proc');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.adj','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Adj');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Adj');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Adj.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adj');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.max','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Max');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Max');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Max');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Máx.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.past','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'past');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'passée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'pasada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'passado');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.currentAndFuture','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'current + future');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'actuel + futur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'presente + futuro');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'atual + futuro');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.consumptionMsg','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Consumption (* indicates actual)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Consommation (* indique le réel)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Consumo (* indica real)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Consumo (* indica real)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.inventoryMsg','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Stock (* indicates adjustment)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock (* indique l`ajustement)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Stock (* indica ajuste)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque (* indica ajuste)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.notesDetailsCon','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'DD-MMM-YY (Region | Data Source) Notes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'JJ-MMM-AA (Région | Source de données) Remarques');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'DD-MMM-YY (Región | Fuente de datos) Notas');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'DD-MMM-AA (Região | Fonte de Dados) Notas');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.notesDetailsShip','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'DD-MMM-YY (Data Source) Notes');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'JJ-MMM-AA (source de données) Remarques');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Notas de DD-MMM-YY (fuente de datos)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'DD-MMM-AA (fonte de dados) Notas');-- pr