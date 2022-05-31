/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 25-May-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.versionNotLoaded','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version is not loaded');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La version n est pas chargée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La versión no está cargada');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A versão não está carregada');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.deleteLocalVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete the local version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer la version locale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar la versión local');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir a versão local');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.forForecastPeriod','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'for forecast period');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'pour la période de prévision');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'para el período de pronóstico');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'para o período de previsão');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.loadDeleteDatasetSctionCancell','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Load delete dataset action cancelled');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Charger l action de suppression de l ensemble de données annulée');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Se canceló la acción de carga y eliminación del conjunto de datos');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Carregar ação de exclusão do conjunto de dados cancelada');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.deleteAllOlderVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Do you want to delete all the older version and keep latest version only.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Voulez-vous supprimer toutes les anciennes versions et conserver uniquement la dernière version.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'¿Desea eliminar toda la versión anterior y conservar solo la última versión?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Você deseja excluir toda a versão mais antiga e manter apenas a versão mais recente.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.datasetDeleteSuccessfully','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Dataset deleted successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Ensemble de données supprimé avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conjunto de datos eliminado con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conjunto de dados excluído com sucesso');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.changesNotSaved','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Changes are not saved still do you want to delete this version.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les modifications ne sont pas enregistrées si vous souhaitez supprimer cette version.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los cambios no se guardan, ¿quieres eliminar esta versión?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'As alterações não são salvas ainda assim você deseja excluir esta versão.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.deleteThisLocalVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete this local version');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer cette version locale');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar esta versión local');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir esta versão local');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.selectAtleastOneDataset','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Select atleast one dataset to load');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélectionnez au moins un jeu de données à charger');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Seleccione al menos un conjunto de datos para cargar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Selecione pelo menos um conjunto de dados para carregar');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.allOlderModifiedVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'All the older modified/non modified versions including latest version will get deleted and new latest version will get loaded do you want to continue?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Toutes les anciennes versions modifiées/non modifiées, y compris la dernière version, seront supprimées et la nouvelle version la plus récente sera chargée. Voulez-vous continuer ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Todas las versiones anteriores modificadas/no modificadas, incluida la última versión, se eliminarán y se cargará la nueva versión más reciente. ¿Desea continuar?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Todas as versões mais antigas modificadas/não modificadas, incluindo a versão mais recente, serão excluídas e a nova versão mais recente será carregada. Deseja continuar?');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.programWithSameVersion','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program with same version already exist do you want to override data.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Un programme avec la même version existe déjà, voulez-vous remplacer les données.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ya existe un programa con la misma versión. ¿Desea anular los datos?');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Já existe um programa com a mesma versão, deseja substituir os dados.');-- pr


INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.loadDelDataset.datasetLoadedSussfully','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Dataset loaded successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Jeu de données chargé avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Conjunto de datos cargado con éxito');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Conjunto de dados carregado com sucesso');-- pr


