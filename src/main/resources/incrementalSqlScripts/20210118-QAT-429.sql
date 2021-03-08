/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 18-Jan-2021
 */
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.pipeline.productToPlanningUnit','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'PipeLine product to Planning Unit');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Produit PipeLine à lunité de planification');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Producto PipeLine a la unidad de planificación');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Produto PipeLine para Unidade de Planejamento');
