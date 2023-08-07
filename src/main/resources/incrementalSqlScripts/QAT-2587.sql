/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 07-Dec-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realmCountryPlanningUnit.failedToUpdate','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Failed to update data as one of the one is to one mapping is missing');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Échec de la mise à jour des données car l`une des correspondances est manquante');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se pudieron actualizar los datos porque falta una de las asignaciones');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Falha ao atualizar os dados, pois um dos mapeamentos está ausente');-- pr