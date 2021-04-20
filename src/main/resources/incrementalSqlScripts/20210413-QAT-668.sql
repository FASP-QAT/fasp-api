/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 13-Apr-2021
 */

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.realmCountryPlanningUnitList.warningMultiplierChange','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Conversion factor is changed for  planning unit please ask users to commit their local changes for the program otherwise it will raise multiple conflicts on commit version screen.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le facteur de conversion est modifié pour l`unité de planification, veuillez demander aux utilisateurs de valider leurs modifications locales pour le programme, sinon cela provoquera plusieurs conflits sur l`écran de la version de validation.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El factor de conversión se cambia para la unidad de planificación, solicite a los usuarios que confirmen sus cambios locales para el programa; de lo contrario, se generarán múltiples conflictos en la pantalla de la versión de confirmación.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O fator de conversão foi alterado para a unidade de planejamento, por favor, peça aos usuários que confirmem suas alterações locais para o programa, caso contrário, haverá vários conflitos na tela de versão de confirmação.');-- pr