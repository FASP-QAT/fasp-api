/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 01-Mar-2021
 */

UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Projected Inventory = Opening balance + Adjustments + Shipments in account - Consumptions - Expired stock' WHERE `STATIC_LABEL_LANGUAGE_ID`='6105';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Inventario proyectado = Saldo inicial + Ajustes + Envíos en cuenta - Consumos - Stock vencido' WHERE `STATIC_LABEL_LANGUAGE_ID`='6106';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Inventaire projeté = solde d`ouverture + ajustements + livraisons en compte - consommations - stock périmé' WHERE `STATIC_LABEL_LANGUAGE_ID`='6107';
UPDATE `ap_static_label_languages` SET `LABEL_TEXT`='Estoque projetado = saldo inicial + ajustes + embarques na conta - consumos - estoque expirado' WHERE `STATIC_LABEL_LANGUAGE_ID`='6108';

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.na','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'N/A');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'N/A');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'N/A');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'N/D');-- pr

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.expiredStockEx1','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Opening Balance for Batch123 (expiry March 3, 2021) = 23');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde d`ouverture pour Batch123 (expiration le 3 mars 2021) = 23');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo inicial para Batch123 (vencimiento el 3 de marzo de 2021) = 23');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo inicial para o lote123 (vencimento em 3 de março de 2021) = 23');-- pr

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.expiredStockEx2','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Opening Balance for Batch456 (expiry March 21, 2021) = 53');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Solde d`ouverture du lot 456 (expiration le 21 mars 2021) = 53');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Saldo inicial para Batch456 (vencimiento el 21 de marzo de 2021) = 53');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Saldo inicial para o lote 456 (expiração em 21 de março de 2021) = 53');-- pr

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.expiredStockEx3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Expired Stock for March 2021 = 23 + 53 = 76');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock expiré pour mars 2021 = 23 + 53 = 76');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Existencias vencidas para marzo de 2021 = 23 + 53 = 76');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque expirado para março de 2021 = 23 + 53 = 76');-- pr


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.expiredStockNote','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All batches begin with shipments. All expiries assumed to happen at beginning of the month regardless of expiry date. For more details on batch calculations, please see User Manual – Annex on Business Rules.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tous les lots commencent par les expéditions. Toutes les expirations sont supposées se produire au début du mois, quelle que soit la date d`expiration. Pour plus de détails sur les calculs par lots, veuillez consulter le Manuel de l`utilisateur - Annexe sur les règles commerciales.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todos los lotes comienzan con los envíos. Se supone que todos los vencimientos ocurren a principios de mes, independientemente de la fecha de vencimiento. Para obtener más detalles sobre los cálculos de lotes, consulte el Manual del usuario - Anexo sobre reglas comerciales.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todos os lotes começam com remessas. Presume-se que todos os vencimentos ocorram no início do mês, independentemente da data de vencimento. Para mais detalhes sobre os cálculos dos lotes, consulte o Manual do Usuário - Anexo sobre Regras de Negócios.');-- pr


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.expiredStockEx','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Expired Stock = Sum of this month’s opening balance of batches, which are expiring this month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Stock expiré = Somme du solde d`ouverture des lots de ce mois-ci, qui expirent ce mois-ci');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Existencias vencidas = Suma del saldo inicial de lotes de este mes, que vencen este mes');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Estoque expirado = Soma do saldo inicial dos lotes deste mês, que estão expirando neste mês');-- pr