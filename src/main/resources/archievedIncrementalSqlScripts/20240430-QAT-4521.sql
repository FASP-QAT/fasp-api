INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.typeAtleast3','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Type at least 3 characters to search'); -- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Tapez au moins 3 caractères pour rechercher'); -- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Escribe al menos 3 caracteres para buscar'); -- sp 
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Digite pelo menos 3 caracteres para pesquisar');
