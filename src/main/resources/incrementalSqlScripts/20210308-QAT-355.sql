/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 08-Mar-2021
 */

INSERT INTO `fasp`.`ap_export`(`EXPORT_ID`,`ERP_CODE`,`JOB_NAME`,`LAST_DATE`) VALUES ( NULL,'ARTMIS','QAT_Shipment_Linking',NOW()); 
UPDATE `fasp`.`em_email_template` SET `SUBJECT`='QAT data export error : <%ERROR_NAME%>' WHERE `EMAIL_TEMPLATE_ID`='4'; 