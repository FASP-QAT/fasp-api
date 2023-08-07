INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.loadDeleteLocalVersion','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Load/Delete Local Version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Charger/Supprimer la version locale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cargar/Eliminar versión local');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Carregar/Excluir Versão Local');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.exportDataset','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Export Dataset');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Exporter l`ensemble de données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Exportar conjunto de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Exportar conjunto de dados');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.importDataset','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Import Dataset');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Importer un jeu de données');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Importar conjunto de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Importar conjunto de dados');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.userList','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User List');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'liste d`utilisateur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Lista de usuarios');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lista de usuários');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.accessControlList','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Access Control List');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Liste de contrôle d`accès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Lista de control de acceso');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Lista de controle de acesso');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dataset.version1Settings','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version 1 Settings');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Paramètres de la version 1');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Configuración de la versión 1');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Configurações da versão 1');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importIntoQAT.dateRange','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Range to Import Forecast Consumption');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Plage d`importation de la consommation prévue');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Rango para importar consumo de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Intervalo para Importar Previsão de Consumo');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.importIntoQAT.forecastTracerCategory','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Forecast Tracer Category');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Catégorie de traceur de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Categoría del rastreador de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Categoria do Rastreador de Previsão');-- pr

