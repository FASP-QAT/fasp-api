/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 30-Mar-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.integration','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Integration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Lintégration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Integración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Integração');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.versionStatus','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version Status');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'État de la version');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Estado de la versión');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Status da versão');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.integrationValidName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter integration name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez le nom de lintégration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el nombre de la integración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o nome da integração');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.integrationViewtext','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select integrationView');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez integrationView');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccionar integraciónView');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione integrationView');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.validFolderLocation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter folder location');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez lemplacement du dossier');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese la ubicación de la carpeta');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Insira o local da pasta');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.validFileName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Enter file name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrez le nom du fichier');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ingrese el nombre del archivo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Digite o nome do arquivo');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.integrationViewName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Integration view');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Vue dintégration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Vista de integración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Visão de integração');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.folderLocation','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Folder location');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Emplacement du dossier');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ubicación de la carpeta');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Localização da pasta');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.fileName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'File Name');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Nom de fichier');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nombre del archivo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nome do arquivo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.addBodyParameter','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add body parameter');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter un paramètre de corps');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar parámetro de cuerpo');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar parâmetro de corpo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.clearBodyParameter','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Clear body parameter');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Effacer le paramètre du corps');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Parámetro de cuerpo claro');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Limpar parâmetro de corpo');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.bodyParameter','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Body parameter');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Paramètre corporel');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Parámetro corporal');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Parâmetro do corpo');






INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.Date[YMDHMS]','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Date(years,months,date,hours,minutes,seconds)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date (années, mois, date, heures, minutes, secondes)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha (años, meses, fecha, horas, minutos, segundos)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data (anos, meses, data, horas, minutos, segundos)');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.Date[YMD]','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Date(years,months,date)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Date (années, mois, date)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Fecha (años, meses, fecha)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Data (anos, meses, data)');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.programIntegration','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Integration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intégration de programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Integración del programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Integração de programa');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.addProgramIntegration','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Program Integration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter une intégration de programme');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar integración de programa');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar integração de programa');



INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.listIntegration','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'List Integration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Intégration de liste');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Integración de listas');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Integração de lista');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.addIntegration','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Add Integration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ajouter une intégration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Agregar integración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Adicionar integração');

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.integration.editIntegration','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Edit Integration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Modifier lintégration');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Editar integración');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Editar Integração');
