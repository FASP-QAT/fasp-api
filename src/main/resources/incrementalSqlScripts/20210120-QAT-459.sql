/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 20-Jan-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlan.submitted','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Submitted (Submitted, Approved)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Soumis (soumis, approuvé)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Enviado (Enviado, Aprobado)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Enviado (Enviado, Aprovado)');

UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planned (Planned, On-hold)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3733';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planificado (planificado, en espera)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3734';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planifié (planifié, en attente)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3735';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Planejado (planejado, em espera)' WHERE `STATIC_LABEL_LANGUAGE_ID`='3736';

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Remove all Submitted Shipments that are not within lead time' WHERE `STATIC_LABEL_LANGUAGE_ID`='4337';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Eliminar todos los envíos enviados que no estén dentro del plazo de entrega' WHERE `STATIC_LABEL_LANGUAGE_ID`='4338';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Supprimer tous les envois soumis qui ne sont pas dans les délais' WHERE `STATIC_LABEL_LANGUAGE_ID`='4339';
UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Remova todas as remessas enviadas que não estão dentro do prazo de entrega' WHERE `STATIC_LABEL_LANGUAGE_ID`='4340';