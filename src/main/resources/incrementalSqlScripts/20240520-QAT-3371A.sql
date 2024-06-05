/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  akil
 * Created: 20-May-2024
 */
ALTER TABLE `fasp`.`rm_usage_template` CHANGE COLUMN `NO_OF_FORECASTING_UNITS` `NO_OF_FORECASTING_UNITS` DECIMAL(16,4) UNSIGNED NOT NULL COMMENT '# of Forecasting Units ';

