INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.forecastValidation.editMe','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit me');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifiez-moi');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'editarme');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Edite-me');-- pr

USE `fasp`;
DROP procedure IF EXISTS `getSupplyPlanActualConsumption`;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getSupplyPlanActualConsumption`(PROGRAM_ID INT(10), VERSION_ID INT (10), PLANNING_UNIT_LIST TEXT, REGION_LIST VARCHAR(255), START_DATE DATE, STOP_DATE DATE)
BEGIN
	SET @programId = PROGRAM_ID;
    SET @versionId = VERSION_ID;
    SET @planningUnitList = PLANNING_UNIT_LIST;
    SET @regionList = REGION_LIST;
    SET @startDate = START_DATE;
    SET @stopDate = STOP_DATE;
    
    SELECT 
        ct.CONSUMPTION_DATE, ct.CONSUMPTION_QTY, 
        pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`,
        fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
        pc.PRODUCT_CATEGORY_ID, pc.LABEL_ID `PC_LABEL_ID`, pc.LABEL_EN `PC_LABEL_EN`, pc.LABEL_FR `PC_LABEL_FR`, pc.LABEL_SP `PC_LABEL_SP`, pc.LABEL_PR `PC_LABEL_PR`,
        r.REGION_ID, r.LABEL_ID `REG_LABEL_ID`, r.LABEL_EN `REG_LABEL_EN`, r.LABEL_FR `REG_LABEL_FR`, r.LABEL_SP `REG_LABEL_SP`, r.LABEL_PR `REG_LABEL_PR`
    FROM (SELECT ct.CONSUMPTION_ID, MAX(ct.VERSION_ID) MAX_VERSION_ID FROM rm_consumption c LEFT JOIN rm_consumption_trans ct ON c.CONSUMPTION_ID=ct.CONSUMPTION_ID WHERE (ct.VERSION_ID<=@versionId) AND c.PROGRAM_ID=@programId GROUP BY ct.CONSUMPTION_ID) tc 
    LEFT JOIN rm_consumption cons ON tc.CONSUMPTION_ID=cons.CONSUMPTION_ID
    LEFT JOIN rm_consumption_trans ct ON tc.CONSUMPTION_ID=ct.CONSUMPTION_ID AND tc.MAX_VERSION_ID=ct.VERSION_ID
    LEFT JOIN vw_planning_unit pu ON ct.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
    LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
    LEFT JOIN vw_product_category pc ON fu.PRODUCT_CATEGORY_ID=pc.PRODUCT_CATEGORY_ID
    LEFT JOIN vw_region r ON ct.REGION_ID=r.REGION_ID
    WHERE ct.CONSUMPTION_DATE BETWEEN @startDate AND @stopDate   
    AND FIND_IN_SET(ct.PLANNING_UNIT_ID, @planningUnitList) 
    AND ct.ACTIVE AND ct.ACTUAL_FLAG AND FIND_IN_SET(ct.REGION_ID, @regionList)
    ORDER BY ct.PLANNING_UNIT_ID, ct.REGION_ID, ct.CONSUMPTION_DATE;
END$$

DELIMITER ;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Before committing, remember to select a forecast for each planning unit in the `Compare and Select` screen'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=1;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Avant de vous engager, n`oubliez pas de sélectionner une prévision pour chaque unité de planification dans l`écran `Comparer et sélectionner`'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=2;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Antes de comprometerse, recuerde seleccionar un pronóstico para cada unidad de planificación en la pantalla `Comparar y seleccionar`.'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=3;

update ap_static_label l 
left join ap_static_label_languages ll on l.STATIC_LABEL_ID=ll.STATIC_LABEL_ID
set ll.LABEL_TEXT='Antes de confirmar, lembre-se de selecionar uma previsão para cada unidade de planejamento na tela `Compare and Select`'
where l.LABEL_CODE='static.commitTree.note' and ll.LANGUAGE_ID=4;
