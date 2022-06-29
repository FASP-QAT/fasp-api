/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 28-Jun-2022
 */

ALTER TABLE `fasp`.`rm_procurement_unit` CHANGE `SUPPLIER_ID` `SUPPLIER_ID` INT(10) UNSIGNED NULL COMMENT 'Foreign key to point which Manufacturer this Logistics unit is from'; 