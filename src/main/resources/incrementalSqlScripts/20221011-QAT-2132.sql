/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 05-Oct-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.versionSettings.confirmUpdate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Note: If you changed the forecast period, you will need to update one node per every scenario for every tree for tree forecasts to properly extend.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Remarque : si vous avez modifié la période de prévision, vous devrez mettre à jour un nœud par scénario pour chaque arbre pour que les prévisions d`arbre s`étendent correctement.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Nota: si cambió el período de pronóstico, deberá actualizar un nodo por cada escenario para cada árbol para que los pronósticos de árboles se extiendan correctamente.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Observação: se você alterou o período de previsão, precisará atualizar um nó por cada cenário para cada árvore para que as previsões de árvore sejam estendidas adequadamente.');-- pr