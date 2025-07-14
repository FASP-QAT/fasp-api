INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.instructionHeader','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program Selection');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Sélection du programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Selección de programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Seleção do Programa');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.instruction1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'If creating a supply plan report for a single program, the user can select the supply plan version.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Si vous créez un rapport de plan d\'approvisionnement pour un seul programme, l\'utilisateur peut sélectionner la version du plan d\'approvisionnement.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Si se crea un informe de plan de suministro para un solo programa, el usuario puede seleccionar la versión del plan de suministro.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Ao criar um relatório de plano de fornecimento para um único programa, o usuário pode selecionar a versão do plano de fornecimento.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.instruction2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'However, if more than one program is selected, local versions of the program will not be available. The version dropdown will not be available. QAT will automatically select the most recent version of the respective programs available on the server.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Cependant, si plusieurs programmes sont sélectionnés, les versions locales ne seront pas disponibles. La liste déroulante des versions ne sera pas accessible. QAT sélectionnera automatiquement la version la plus récente des programmes concernés disponible sur le serveur.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Sin embargo, si se selecciona más de un programa, las versiones locales no estarán disponibles. El menú desplegable de versiones no estará disponible. QAT seleccionará automáticamente la versión más reciente de cada programa disponible en el servidor.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'No entanto, se mais de um programa for selecionado, as versões locais do programa não estarão disponíveis. O menu suspenso de versões não estará disponível. O QAT selecionará automaticamente a versão mais recente dos respectivos programas disponíveis no servidor.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.supplyPlanFormula.instruction3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'PLEASE NOTE: The merge program feature of the supply plan report is only available online.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'ATTENTION : La fonction de fusion de programme du rapport du plan d\'approvisionnement est uniquement disponible en ligne.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'TENGA EN CUENTA: La función de combinación de programas del informe del plan de suministro solo está disponible en línea.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'OBSERVAÇÃO: O recurso de mesclagem de programas do relatório do plano de suprimentos está disponível somente on-line.');-- pr
