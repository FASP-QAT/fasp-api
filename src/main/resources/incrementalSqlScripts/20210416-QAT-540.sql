/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 16-Apr-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.shipmentDataEntry.expiryDateMustBeGreaterThanEDD','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Expiry date must be greater than received date');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La date d`expiration doit être postérieure à la date de réception');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La fecha de vencimiento debe ser mayor que la fecha de recepción');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A data de expiração deve ser maior que a data de recebimento');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.confirmBatchInfo','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Confirm batch information to continue');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Confirmer les informations sur le lot pour continuer');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Confirme la información del lote para continuar');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Confirme as informações do lote para continuar');