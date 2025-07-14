/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 29-Sep-2021
 */

DELETE FROM rm_manual_tagging WHERE active = 0;
ALTER TABLE `fasp`.`rm_manual_tagging` ADD UNIQUE `uniqueOrder` (`ORDER_NO`, `PRIME_LINE_NO`, `SHIPMENT_ID`, `ACTIVE`);
DELIMITER $$

USE `fasp`$$

DROP TRIGGER /*!50032 IF EXISTS */ `rm_manual_tagging_BEFORE_DELETE`$$

CREATE
    /*!50017 DEFINER = 'faspUser'@'%' */
    TRIGGER `rm_manual_tagging_BEFORE_DELETE` BEFORE DELETE ON `rm_manual_tagging` 
    FOR EACH ROW BEGIN
INSERT INTO rm_manual_tagging_trans (`MANUAL_TAGGING_TRANS_ID`,`MANUAL_TAGGING_ID`,`ORDER_NO`,`PRIME_LINE_NO`,`SHIPMENT_ID`,`LAST_MODIFIED_DATE`,`LAST_MODIFIED_BY`,`ACTIVE`,`NOTES`,`CONVERSION_FACTOR`) 
    VALUES(NULL,old.`MANUAL_TAGGING_ID`,old.ORDER_NO,old.PRIME_LINE_NO,old.SHIPMENT_ID,NOW(),old.LAST_MODIFIED_BY,0,old.NOTES,old.CONVERSION_FACTOR);
END;
$$

DELIMITER ;