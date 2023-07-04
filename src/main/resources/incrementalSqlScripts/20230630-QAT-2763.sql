/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 30-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.createTreeFromTemplate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Use this template');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Utilisez ce modèle');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Usa esta plantilla');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Use este modelo');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.monthsInPastTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number of past months (shown as negative) available in the `Node Data` dropdown, where Month -1 is the month immediately before forecast period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois passés (affichés en négatif) disponibles dans la liste déroulante `Données de nœud`, où Mois -1 correspond au mois précédant immédiatement la période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses anteriores (mostrados como negativos) disponibles en el menú desplegable `Datos de nodo`, donde Mes -1 es el mes inmediatamente anterior al período de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses anteriores (mostrado como negativo) disponível no menu suspenso `Dados do nó`, em que Mês -1 é o mês imediatamente anterior ao período de previsão');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.treeTemplate.monthsInFutureTooltip','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Number of future months (show as positive) available in the `Node Data` dropdown, where Month 1 is the first month of the forecast period.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nombre de mois futurs (affichés comme positifs) disponibles dans la liste déroulante `Données de nœud`, où le mois 1 correspond au premier mois de la période de prévision.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Número de meses futuros (mostrar como positivos) disponibles en el menú desplegable `Datos de nodo`, donde el Mes 1 es el primer mes del período de pronóstico.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Número de meses futuros (mostrado como positivo) disponível no menu suspenso `Dados do nó`, em que Mês 1 é o primeiro mês do período de previsão.');-- pr