/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 22-Apr-2021
 */

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.report.version*','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Version (* indicates Final Approved)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Version (* indique lapprobation finale)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Versión (* indica Aprobado final)');
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Versão (* indica aprovado final)');