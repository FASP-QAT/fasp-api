/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 14-Jun-2022
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.budget.duplicateDisplayName','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Combination of budget name and display name already exists');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La combinaison du nom du budget et du nom à afficher existe déjà');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ya existe una combinación de nombre de presupuesto y nombre para mostrar');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A combinação do nome do orçamento e do nome de exibição já existe');-- pr