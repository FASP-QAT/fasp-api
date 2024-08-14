/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 08-Aug-2024
 */

INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.tree.month','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'month');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'mês');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'mes');-- sp