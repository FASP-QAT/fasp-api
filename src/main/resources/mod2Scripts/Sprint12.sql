/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 26-Apr-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.report.versionFinal*','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version (* indicates Final)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Version (* indique Finale)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Versión (* indica Final)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão (* indica Final)');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.person','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Person');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Personne');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Persona');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Pessoa');-- pr

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoQATSupplyPlan.forecastFinalVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast version (Final Versions Only)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Version prévisionnelle (versions finales uniquement)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão de previsão (somente versões finais)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Versión de previsión (solo versiones finales)');-- sp

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoQATSupplyPlan.importIntoQATSupplyPlan','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'QAT Forecast Import');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importation des prévisions QAT');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importación de pronóstico QAT');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Dados do plano de fornecimento importados com sucesso');-- pr
