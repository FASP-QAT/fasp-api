/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 04-May-2022
 */

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Supply planning module',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_SUPPLY_PLANNING_MODULE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_SUPPLY_PLANNING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`ap_label`(`LABEL_ID`,`LABEL_EN`,`LABEL_FR`,`LABEL_SP`,`LABEL_PR`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`,`SOURCE_ID`) VALUES ( NULL,'Forecasting module',NULL,NULL,NULL,'1',NOW(),'1',NOW(),'24');
SELECT MAX(l.LABEL_ID) INTO @MAX FROM ap_label l ;
INSERT INTO `fasp`.`us_business_function`(`BUSINESS_FUNCTION_ID`,`LABEL_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( 'ROLE_BF_FORECASTING_MODULE',@MAX,'1',NOW(),'1',NOW());


INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_REALM_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_INTERNAL_USER','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

INSERT INTO `fasp`.`us_role_business_function`(`ROLE_BUSINESS_FUNCTION_ID`,`ROLE_ID`,`BUSINESS_FUNCTION_ID`,`CREATED_BY`,`CREATED_DATE`,`LAST_MODIFIED_BY`,`LAST_MODIFIED_DATE`) VALUES ( NULL,'ROLE_PROGRAM_USER','ROLE_BF_FORECASTING_MODULE','1',NOW(),'1',NOW());

update ap_label l set l.LABEL_EN='SP - QAT Forecast Import' where l.LABEL_EN='QAT Forecast Import';
update ap_label l set l.LABEL_EN='Forecasting - Add Usage Period' where l.LABEL_EN='Add Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - Edit Usage Period' where l.LABEL_EN='Edit Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - List Usage Period' where l.LABEL_EN='List Usage Period';
update ap_label l set l.LABEL_EN='Forecasting - Add Modeling Type' where l.LABEL_EN='Add Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - Edit Modeling Type' where l.LABEL_EN='Edit Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - List Modeling Type' where l.LABEL_EN='List Modeling Type';
update ap_label l set l.LABEL_EN='Forecasting - Add Forecast Method' where l.LABEL_EN='Add Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Method' where l.LABEL_EN='Edit Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Method' where l.LABEL_EN='List Forecast Method';
update ap_label l set l.LABEL_EN='Forecasting - Add Equivalency Unit' where l.LABEL_EN='Add Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - Edit Equivalency Unit' where l.LABEL_EN='Edit Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - List Equivalency Unit' where l.LABEL_EN='List Equivalency Unit';
update ap_label l set l.LABEL_EN='Forecasting - Add Equivalency Unit Mapping' where l.LABEL_EN='Add Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - Edit Equivalency Unit Mapping' where l.LABEL_EN='Edit Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - List Equivalency Unit Mapping' where l.LABEL_EN='List Equivalency Unit Mapping';
update ap_label l set l.LABEL_EN='Forecasting - Add Forecast Program' where l.LABEL_EN='Add Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Program' where l.LABEL_EN='Edit Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Program' where l.LABEL_EN='List Forecast Program';
update ap_label l set l.LABEL_EN='Forecasting - Add Usage Template' where l.LABEL_EN='Add Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - Edit Usage Template' where l.LABEL_EN='Edit Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - List Usage Template' where l.LABEL_EN='List Usage Template';
update ap_label l set l.LABEL_EN='Forecasting - Add Import From QAT Supply Plan' where l.LABEL_EN='Add Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - Edit Import From QAT Supply Plan' where l.LABEL_EN='Edit Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - List Import From QAT Supply Plan' where l.LABEL_EN='List Import From QAT Supply Plan';
update ap_label l set l.LABEL_EN='Forecasting - Compare and Select Forecast' where l.LABEL_EN='Compare and Select Forecast';
update ap_label l set l.LABEL_EN='Forecasting - Product Validation' where l.LABEL_EN='Product Validation';
update ap_label l set l.LABEL_EN='Forecasting - Modeling Validation' where l.LABEL_EN='Modeling Validation';
update ap_label l set l.LABEL_EN='Forecasting - Compare Version' where l.LABEL_EN='Compare Version';
update ap_label l set l.LABEL_EN='Forecasting - Extrapolation' where l.LABEL_EN='Extrapolation';
update ap_label l set l.LABEL_EN='Forecasting - Commit Dataset' where l.LABEL_EN='Commit Dataset';
update ap_label l set l.LABEL_EN='Forecasting - Consumption data entry and adjustment' where l.LABEL_EN='Consumption data entry and adjustment';
update ap_label l set l.LABEL_EN='Forecasting - Add Planning Unit Setting' where l.LABEL_EN='Add Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - Edit Planning Unit Setting' where l.LABEL_EN='Edit Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - List Planning Unit Setting' where l.LABEL_EN='List Planning Unit Setting';
update ap_label l set l.LABEL_EN='Forecasting - List Monthly Forecast' where l.LABEL_EN='List Monthly Forecast';
update ap_label l set l.LABEL_EN='Forecasting - Edit Forecast Summary' where l.LABEL_EN='Edit Forecast Summary';
update ap_label l set l.LABEL_EN='Forecasting - List Forecast Summary' where l.LABEL_EN='List Forecast Summary';
update ap_label l set l.LABEL_EN='Forecasting - List Tree Template' where l.LABEL_EN='List Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - Add Tree Template' where l.LABEL_EN='Add Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - List Tree' where l.LABEL_EN='List Tree';
update ap_label l set l.LABEL_EN='Forecasting - Add Tree' where l.LABEL_EN='Add Tree';
update ap_label l set l.LABEL_EN='Forecasting - Edit Tree Template' where l.LABEL_EN='Edit Tree Template';
update ap_label l set l.LABEL_EN='Forecasting - Edit Tree' where l.LABEL_EN='Edit Tree';


USE `fasp`;
DROP procedure IF EXISTS `getMonthlyForecast`;

USE `fasp`;
DROP procedure IF EXISTS `fasp`.`getMonthlyForecast`;
;

DELIMITER $$
USE `fasp`$$
CREATE DEFINER=`faspUser`@`%` PROCEDURE `getMonthlyForecast`(VAR_PROGRAM_ID INT(10), VAR_VERSION_ID INT(10), VAR_START_MONTH DATE, VAR_STOP_MONTH DATE, VAR_REPORT_VIEW INT, VAR_UNIT_IDS TEXT)
BEGIN

    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- Mod 2 Report no 1 - MonthlyForecast
    -- %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    -- programId must be a single Program
    -- versionId must be the actual version 
    -- startMonth is the month from which you want the consumption data 
    -- stopMonth is the month till which you want the consumption data 
    -- reportView 1 = PU view, 2 = FU View 
    -- unitIdsList -- List of the PU Id's or FU Id's that you want the report for

    SET @programId = VAR_PROGRAM_ID;
    SET @versionId = VAR_VERSION_ID;
    SET @startMonth = VAR_START_MONTH;
    SET @stopMonth = VAR_STOP_MONTH;
    SET @reportView = VAR_REPORT_VIEW;
    SET @unitIdList = VAR_UNIT_IDS;
    
    IF @reportView = 1 THEN
        SELECT 
            pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
            fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
            r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
            COALESCE(t.LABEL_ID, em.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR), em.LABEL_PR) `SF_LABEL_PR`,
            dpus.TREE_ID, dpus.SCENARIO_ID, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH) `MONTH`, COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT) `CALCULATED_MMD_VALUE`
        FROM rm_dataset_planning_unit dpu 
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
        LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN mn ON mn.MONTH BETWEEN @startMonth AND @stopMonth 
        LEFT JOIN 
            (
            SELECT 
                ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndpu.PLANNING_UNIT_ID
            FROM vw_forecast_tree_node ftn     
            LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
            LEFT JOIN rm_forecast_tree_node_data_pu ftndpu ON ftndpu.NODE_DATA_PU_ID=ftnd.NODE_DATA_PU_ID 
            WHERE ftn.NODE_TYPE_ID=5
        ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND pu.PLANNING_UNIT_ID=tree.PLANNING_UNIT_ID
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH=mn.MONTH
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        LEFT JOIN vw_forecast_tree t ON dpus.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpus.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em on fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH=mn.MONTH
        WHERE 
            dpu.PROGRAM_ID=@programId 
            AND dpu.VERSION_ID=@versionId 
            AND FIND_IN_SET(pu.PLANNING_UNIT_ID, @unitIdList)
	ORDER BY pu.PLANNING_UNIT_ID, r.REGION_ID, mn.MONTH;
    ELSEIF @reportView = 2 THEN
        SELECT 
            pu.PLANNING_UNIT_ID, pu.LABEL_ID `PU_LABEL_ID`, pu.LABEL_EN `PU_LABEL_EN`, pu.LABEL_FR `PU_LABEL_FR`, pu.LABEL_SP `PU_LABEL_SP`, pu.LABEL_PR `PU_LABEL_PR`, pu.MULTIPLIER,
            fu.FORECASTING_UNIT_ID, fu.LABEL_ID `FU_LABEL_ID`, fu.LABEL_EN `FU_LABEL_EN`, fu.LABEL_FR `FU_LABEL_FR`, fu.LABEL_SP `FU_LABEL_SP`, fu.LABEL_PR `FU_LABEL_PR`,
            r.REGION_ID, r.LABEL_ID `R_LABEL_ID`, r.LABEL_EN `R_LABEL_EN`, r.LABEL_FR `R_LABEL_FR`, r.LABEL_SP `R_LABEL_SP`, r.LABEL_PR `R_LABEL_PR`,
            COALESCE(t.LABEL_ID, em.LABEL_ID) `SF_LABEL_ID`, COALESCE(CONCAT(t.LABEL_EN,'-',s.LABEL_EN), em.LABEL_EN) `SF_LABEL_EN`, COALESCE(CONCAT(t.LABEL_FR,'-',s.LABEL_FR), em.LABEL_FR) `SF_LABEL_FR`, COALESCE(CONCAT(t.LABEL_SP,'-',s.LABEL_SP), em.LABEL_SP) `SF_LABEL_SP`, COALESCE(CONCAT(t.LABEL_PR,'-',s.LABEL_PR), em.LABEL_PR) `SF_LABEL_PR`,
            dpus.TREE_ID, dpus.SCENARIO_ID, fce.EXTRAPOLATION_METHOD_ID, COALESCE(mom.MONTH, fced.MONTH) `MONTH`, COALESCE(mom.CALCULATED_MMD_VALUE, fced.AMOUNT) `CALCULATED_MMD_VALUE`
        FROM rm_dataset_planning_unit dpu 
        LEFT JOIN rm_dataset_planning_unit_selected dpus ON dpu.PROGRAM_PLANNING_UNIT_ID=dpus.PROGRAM_PLANNING_UNIT_ID
        LEFT JOIN vw_planning_unit pu ON dpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID
        LEFT JOIN vw_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID
        LEFT JOIN 
            (
            SELECT 
                ftn.TREE_ID, ftn.NODE_ID, ftnd.NODE_DATA_ID, ftnd.SCENARIO_ID, ftndfu.FORECASTING_UNIT_ID
            FROM vw_forecast_tree_node ftn     
            LEFT JOIN rm_forecast_tree_node_data ftnd ON ftnd.NODE_ID=ftn.NODE_ID
            LEFT JOIN rm_forecast_tree_node_data_fu ftndfu ON ftndfu.NODE_DATA_FU_ID=ftnd.NODE_DATA_FU_ID 
            WHERE ftn.NODE_TYPE_ID=4
        ) tree ON dpus.TREE_ID=tree.TREE_ID AND tree.SCENARIO_ID=dpus.SCENARIO_ID AND fu.FORECASTING_UNIT_ID=tree.FORECASTING_UNIT_ID
        LEFT JOIN rm_forecast_tree_node_data_mom mom ON mom.NODE_DATA_ID=tree.NODE_DATA_ID AND mom.MONTH BETWEEN @startMonth AND @stopMonth 
        LEFT JOIN vw_region r ON dpus.REGION_ID=r.REGION_ID
        LEFT JOIN vw_forecast_tree t ON dpus.TREE_ID=t.TREE_ID
        LEFT JOIN vw_scenario s ON dpus.SCENARIO_ID=s.SCENARIO_ID
        LEFT JOIN rm_forecast_consumption_extrapolation fce ON dpus.CONSUMPTION_EXTRAPOLATION_ID=fce.CONSUMPTION_EXTRAPOLATION_ID
        LEFT JOIN vw_extrapolation_method em on fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID
        LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID AND fced.MONTH BETWEEN @startMonth AND @stopMonth
        WHERE 
            dpu.PROGRAM_ID=@programId 
            AND dpu.VERSION_ID=@versionId 
            AND FIND_IN_SET(fu.FORECASTING_UNIT_ID, @unitIdList);
    END IF;
END$$

DELIMITER ;
;



ALTER TABLE `fasp`.`rm_forecast_tree_node_data_extrapolation_option_data` ADD COLUMN `CI` DECIMAL(18,4) NULL AFTER `AMOUNT`;
ALTER TABLE `fasp`.`rm_forecast_consumption_extrapolation_data` ADD COLUMN `CI` DECIMAL(16,2) NULL AFTER `AMOUNT`;
DELETE fced.* FROM rm_forecast_consumption_extrapolation fce LEFT JOIN vw_extrapolation_method em ON fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID LEFT JOIN rm_forecast_consumption_extrapolation_data fced ON fce.CONSUMPTION_EXTRAPOLATION_ID=fced.CONSUMPTION_EXTRAPOLATION_ID WHERE em.ACTIVE=0;
DELETE fce.* FROM rm_forecast_consumption_extrapolation fce LEFT JOIN vw_extrapolation_method em ON fce.EXTRAPOLATION_METHOD_ID=em.EXTRAPOLATION_METHOD_ID WHERE em.ACTIVE=0;

SET FOREIGN_KEY_CHECKS=0;
DELETE l.*, em.* FROM ap_extrapolation_method em LEFT JOIN ap_label l ON em.LABEL_ID=l.LABEL_ID where em.ACTIVE=0;
SET FOREIGN_KEY_CHECKS=1;

