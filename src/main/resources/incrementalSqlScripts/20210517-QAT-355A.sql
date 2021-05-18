/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 17-May-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.showDetails','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Details');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.purposeOfEachScreen','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Purpose of Each Screen: ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.tab1DetailPurpose','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this screen to link QAT shipments with ERP orders for the first time. Users will see a list of QAT shipments, and when a user clicks on a single QAT shipment, they can then choose which ERP orders that QAT shipment should be linked with.  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.tab2DetailPurpose','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Users will see a list of currently linked shipments and they can update the conversion factor or notes of already linked shipments, add ERP lines to already linked QAT shipments, or de-link ERP lines from QAT shipment. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.tab3DetailPurpose','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this screen to link ERP orders to QAT for the first time. Users will see a list of ERP shipments, and when a user clicks on a single ERP shipment, they can then choose which QAT shipment that ERP shipment should be linked with.  For any orders that are in the ERP that are not already in QAT, you can create a new QAT shipment in this screen.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Reminders:');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you make any changes to ERP linkages, you will need to click “master data sync” for those changes to be seen throughout the tool. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Linking: ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT shipments available for linking:  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2B','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ERP orders available for linking: ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2C','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If there is a note in a QAT shipment, this will carry over when you link to the ERP shipment. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor –');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'While linked:  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders4','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'De-Linking:  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders3A','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Once linked, shipments will be non-editable other than the notes field and conversion factor field, which are editable only on the ERP linking screens. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders4A','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If you delink a shipment, the QAT shipment will revert to the original shipment’s note and not carry over any updates you made after linking. For this, we recommend copying the note before de-linking and pasting it into the QAT shipment after.');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipments in “Submitted”, “Approved”, “Arrived”, “Shipped” status (all dates) ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Shipments in “Received” status (within last 6 months) ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2A3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: “planned” or “cancelled” are not available');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2B1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Orders after “Approved” status');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2B2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Orders in “Delivered” status (within last 6 months) ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D1a','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D1b','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'ERP shipment quantity');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D1c','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' is the number flowing from the ERP system, and is based on the ERP unit, which may be different than your QAT planning unit. ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D2a','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D2b','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'converted QAT shipment quantity');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D2c','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' is the number that will be used in your supply plan for the linked shipment.  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');


INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Your order in the ERP system is 20 packs (10 chocolate bars per pack), but your supply plan is in packs of 100 chocolate bars. To calculate the conversion factor, consider how many single units you have in the ERP pack size (10) and divide by how many single units you have in your QAT planning unit (100). In this example, the conversion factor is 10 bars / 100 bars = 0.1. Multiplying the ERP shipment quantity by 0.1 results in the converted QAT shipment quantity of 2, which is the number that will be used in your supply plan.  ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4a','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Pack of 10 chocolate bars ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4b','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'20');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4c','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'= 10/100 = 0.1 ');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4d','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'2');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.mt.reminders2D4e','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Pack of 100 chocolate bars');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Des détails');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Detalles');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Detalhes');