/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 09-Jun-2021
 */

UPDATE `fasp`.`rm_manual_tagging` SET `ACTIVE`='1' WHERE `MANUAL_TAGGING_ID`='68'; 
UPDATE `fasp`.`rm_manual_tagging` SET `ACTIVE`='0' WHERE `MANUAL_TAGGING_ID`='97'; 
UPDATE `fasp`.`rm_shipment_trans` SET `ACTIVE`='0' WHERE `SHIPMENT_ID`='17233' AND `LAST_MODIFIED_BY`='1' AND `LAST_MODIFIED_DATE`='2021-06-01 00:00:02' AND `ERP_FLAG`='1' AND `ACCOUNT_FLAG`='1' AND `ACTIVE`='1' AND `ORDER_NO`='PO10018077' AND `PRIME_LINE_NO`='1'; 
UPDATE `fasp`.`rm_shipment_trans` SET `ERP_FLAG`='0' WHERE `SHIPMENT_ID`='17233' AND `LAST_MODIFIED_BY`='1' AND `LAST_MODIFIED_DATE`='2021-06-01 00:00:02' AND `ERP_FLAG`='1' AND `ACCOUNT_FLAG`='1' AND `ACTIVE`='0' AND `ORDER_NO`='PO10018077' AND `PRIME_LINE_NO`='1'; 