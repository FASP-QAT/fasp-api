/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 04-Mar-2021
 */

insert into ap_label values(null,"Upload User Manual","","","",1,now(),1,now(),24);
select max(l.LABEL_ID) into @max1 from ap_label l ;
insert into us_business_function values ('ROLE_BF_UPLOAD_USER_MANUAL',@max1,1,now(),1,now());
insert into us_role_business_function values (null,'ROLE_APPLICATION_ADMIN','ROLE_BF_UPLOAD_USER_MANUAL',1,now(),1,now());

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.dashboard.uploadUserManual','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Upload User Manual');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Télécharger le manuel de l`utilisateur');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Cargar manual de usuario');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Carregar Manual do Usuário');-- pr



INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.userManual.fileinputPdf','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'File Input (must be .pdf format)');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Entrée de fichier (doit être au format .pdf)');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Entrada de archivo (debe tener formato .pdf)');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Entrada de arquivo (deve ser no formato .pdf)');-- pr

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.uploadUserManual.uploadUserManualSuccess','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'User Manual uploaded successfully.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Manuel de l`utilisateur téléchargé avec succès.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Manual de usuario cargado correctamente.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Manual do usuário carregado com sucesso.');-- pr