/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 31-Oct-2022
 */

CREATE TABLE `fasp`.`tmp_consumption_extrapolation_data`( `CONSUMPTION_EXTRAPOLATION_DATA_ID` INT ); 

INSERT INTO `tmp_consumption_extrapolation_data` SELECT c.`CONSUMPTION_EXTRAPOLATION_DATA_ID` 
FROM `rm_forecast_consumption_extrapolation_data` c WHERE c.`CONSUMPTION_EXTRAPOLATION_DATA_ID` NOT IN (
SELECT MIN(ct.`CONSUMPTION_EXTRAPOLATION_DATA_ID`) 
FROM `rm_forecast_consumption_extrapolation_data` ct GROUP BY ct.`CONSUMPTION_EXTRAPOLATION_ID`,ct.`MONTH`);


DELETE ct.* FROM `rm_forecast_consumption_extrapolation_data` ct 
WHERE ct.`CONSUMPTION_EXTRAPOLATION_DATA_ID` IN (SELECT * FROM `tmp_consumption_extrapolation_data`);


DROP TABLE `fasp`.`tmp_consumption_extrapolation_data`; 