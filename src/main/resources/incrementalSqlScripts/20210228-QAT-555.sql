/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 28-Feb-2021
 */

ALTER TABLE `fasp`.`ap_language` ADD COLUMN `COUNTRY_CODE` VARCHAR(5) NOT NULL AFTER `LANGUAGE_NAME`; 