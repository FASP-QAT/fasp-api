/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 18-Jun-2021
 */

CREATE TABLE `fasp`.`rm_erp_tab3_shipments`( `TAB3_SHIPMENT_ID` INT UNSIGNED NOT NULL AUTO_INCREMENT , `SHIPMENT_ID` INT NOT NULL , `ACTIVE` TINYINT NOT NULL DEFAULT '1' , `CREATED_DATE` DATETIME NOT NULL , `CREATED_BY` INT NOT NULL , `LAST_MODIFIED_DATE` DATETIME NOT NULL , `LAST_MODIFIED_BY` INT NOT NULL , PRIMARY KEY (`TAB3_SHIPMENT_ID`) ); 