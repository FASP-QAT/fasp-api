/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 25-Apr-2022
 */

SET FOREIGN_KEY_CHECKS=0;
DELETE FROM ap_static_label_languages WHERE STATIC_LABEL_ID IN (SELECT l.`STATIC_LABEL_ID` FROM ap_static_label l WHERE l.`LABEL_CODE` IN ('static.tooltip.scenario','static.tooltip.NodeTitle',
'static.placeholder.modelingType',
'static.placeholder.usageTemplate'
));

DELETE l.* FROM ap_static_label l WHERE l.`LABEL_CODE` IN ('static.tooltip.scenario','static.tooltip.NodeTitle',
'static.placeholder.modelingType',
'static.placeholder.usageTemplate'
);
SET FOREIGN_KEY_CHECKS=1;

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='These Usage Names will appear when adding/editing a forecasting tree node in the \'Copy from Template\' dropdown' WHERE `STATIC_LABEL_LANGUAGE_ID`='200470'; 

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Ces noms d\'utilisation apparaîtront lors de l\'ajout/de la modification d\'un nœud d\'arbre de prévision dans la liste déroulante \"Copier à partir du modèle\".' WHERE `STATIC_LABEL_LANGUAGE_ID`='200471'; 

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Estos nombres de uso aparecerán al agregar/editar un nodo de árbol de pronóstico en el menú desplegable \'Copiar de plantilla\'' WHERE `STATIC_LABEL_LANGUAGE_ID`='200472'; 

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Esses nomes de uso aparecerão ao adicionar/editar um nó de árvore de previsão no menu suspenso \'Copiar do modelo\'' WHERE `STATIC_LABEL_LANGUAGE_ID`='200473'; 

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Link the changes in this node to the changes in another node in the same level. If a row is greyed out, edit on source (or \'from\') node.' WHERE `STATIC_LABEL_LANGUAGE_ID`='200322'; 

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Liez les modifications de ce nœud aux modifications d\'un autre nœud du même niveau. Si une ligne est grisée, modifiez-la sur le nœud source (ou « de »).' WHERE `STATIC_LABEL_LANGUAGE_ID`='200323'; 

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Vincule los cambios en este nodo a los cambios en otro nodo en el mismo nivel. Si una fila está atenuada, edite en el nodo fuente (o \'desde\').' WHERE `STATIC_LABEL_LANGUAGE_ID`='200324'; 

UPDATE `fasp`.`ap_static_label_languages` SET `LABEL_TEXT`='Vincule as mudanças neste nó às mudanças em outro nó no mesmo nível. Se uma linha estiver acinzentada, edite no nó de origem (ou \'de\').' WHERE `STATIC_LABEL_LANGUAGE_ID`='200325'; 

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.importIntoQATSupplyPlan.conversionFactor','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion Factor (Forecast Planning Unit to Supply Plan)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Facteur de conversion (prévision en plan d`approvisionnement)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Fator de conversão (previsão para plano de fornecimento)');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Factor de conversión (pronóstico a plan de suministro)');-- sp
