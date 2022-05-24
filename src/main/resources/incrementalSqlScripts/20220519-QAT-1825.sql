ALTER TABLE `fasp`.`ap_label_source` 
ADD COLUMN `SOURCE_TEXT` VARCHAR(100) NOT NULL AFTER `SOURCE_DESC`;


UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Country' WHERE (`SOURCE_ID` = '1');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Realm' WHERE (`SOURCE_ID` = '2');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Currency' WHERE (`SOURCE_ID` = '3');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Dimension' WHERE (`SOURCE_ID` = '4');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Program version type' WHERE (`SOURCE_ID` = '5');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Program version status' WHERE (`SOURCE_ID` = '6');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Shipment status' WHERE (`SOURCE_ID` = '7');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Role' WHERE (`SOURCE_ID` = '8');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Health Area' WHERE (`SOURCE_ID` = '9');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Organisation' WHERE (`SOURCE_ID` = '10');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Region' WHERE (`SOURCE_ID` = '11');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Procurement Agent' WHERE (`SOURCE_ID` = '12');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Funding Source' WHERE (`SOURCE_ID` = '13');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Budget' WHERE (`SOURCE_ID` = '14');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Data Source' WHERE (`SOURCE_ID` = '15');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Data Source type' WHERE (`SOURCE_ID` = '16');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Unit' WHERE (`SOURCE_ID` = '17');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Program' WHERE (`SOURCE_ID` = '18');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Problem type' WHERE (`SOURCE_ID` = '19');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Problem status' WHERE (`SOURCE_ID` = '20');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Problem criticality' WHERE (`SOURCE_ID` = '21');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Problem' WHERE (`SOURCE_ID` = '22');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Problem action' WHERE (`SOURCE_ID` = '23');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Business function' WHERE (`SOURCE_ID` = '24');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Product category' WHERE (`SOURCE_ID` = '26');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Tracer category' WHERE (`SOURCE_ID` = '27');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecasting Unit' WHERE (`SOURCE_ID` = '28');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecasting Unit - Generic name' WHERE (`SOURCE_ID` = '29');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Planning Unit' WHERE (`SOURCE_ID` = '30');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Procurement Unit' WHERE (`SOURCE_ID` = '31');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Alternate Reporting Unit' WHERE (`SOURCE_ID` = '32');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Supplier' WHERE (`SOURCE_ID` = '33');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Problem category' WHERE (`SOURCE_ID` = '34');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Language' WHERE (`SOURCE_ID` = '35');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Notification type' WHERE (`SOURCE_ID` = '36');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Organisation type' WHERE (`SOURCE_ID` = '37');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Usage type' WHERE (`SOURCE_ID` = '38');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Node type' WHERE (`SOURCE_ID` = '39');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Usage period' WHERE (`SOURCE_ID` = '40');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Modeling type' WHERE (`SOURCE_ID` = '41');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecast method type' WHERE (`SOURCE_ID` = '42');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecast method' WHERE (`SOURCE_ID` = '43');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Equivalency Unit' WHERE (`SOURCE_ID` = '44');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Tree template' WHERE (`SOURCE_ID` = '45');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Tree template - Node' WHERE (`SOURCE_ID` = '46');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Usage template' WHERE (`SOURCE_ID` = '47');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecast tree' WHERE (`SOURCE_ID` = '48');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecast tree - Node' WHERE (`SOURCE_ID` = '49');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecast tree - Scenario' WHERE (`SOURCE_ID` = '50');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Extrapolation method' WHERE (`SOURCE_ID` = '52');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Forecast tree - Level' WHERE (`SOURCE_ID` = '53');
UPDATE `fasp`.`ap_label_source` SET `SOURCE_TEXT` = 'Tree template - Level' WHERE (`SOURCE_ID` = '54');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.databaseTranslations.relatedTo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Related To');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Relative à');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Relacionada con');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Relacionado a');-- pr