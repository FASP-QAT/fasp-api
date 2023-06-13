/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 08-Jun-2023
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.importantPleaseRead','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'**IMPORTANT: PLEASE READ**');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'**IMPORTANT : VEUILLEZ LIRE**');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'**IMPORTANTE: FAVOR DE LEER**');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'**IMPORTANTE: LEIA**');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.clickOkTo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click `OK` to:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur `OK` pour :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en `Aceptar` para:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique em `OK` para:');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.deleteLocalVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'- DELETE local version {{programCodeLocal}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'- SUPPRIMER la version locale {{programCodeLocal}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'- ELIMINAR versión local {{programCodeLocal}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'- EXCLUIR versão local {{programCodeLocal}}');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.loseUnsubmittedChanges','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'- Lose unsubmitted changes from local version, and');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'- Perdre les modifications non soumises de la version locale, et');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'- Perder los cambios no enviados de la versión local, y');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'- Perder alterações não enviadas da versão local e');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.loadLatestVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'- Load latest version {{programCodeServer}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'- Charger la dernière version {{programCodeServer}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'- Cargar la última versión {{programCodeServer}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'- Carregar a versão mais recente {{programCodeServer}}');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.clickCancelTo','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Click `Cancel` to:');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cliquez sur `Annuler` pour :');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Haga clic en `Cancelar` para:');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Clique em `Cancelar` para:');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.sync.keepLocalVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'- Keep loaded version {{programCodeLocal}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'- Conserver la version chargée {{programCodeLocal}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'- Mantener la versión cargada {{programCodeLocal}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'- Mantenha a versão carregada {{programCodeLocal}}');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.forecasting','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecasting');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'previsão');-- pr