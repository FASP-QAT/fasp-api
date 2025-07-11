/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 08-Nov-2022
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.deleteThisProgram','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Delete this Program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Supprimer ce programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminar este programa');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Excluir este programa');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.programDeletedSuccessfully','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Program delete successfully.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Programme supprimé avec succès.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Eliminación del programa con éxito.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Programa excluído com sucesso.');-- pr