INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.uatNetworkErrorMessage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Network error. Please check your internet connection or contact your organizational IT department to ensure uat-api.quantificationanalytics.org/# and uat.quantificationanalytics.org/# are whitelisted for firewall access.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur réseau. Veuillez vérifier votre connexion Internet ou contacter le service informatique de votre organisation pour vous assurer que uat-api.quantificationanalytics.org/# et uat.quantificationanalytics.org/# sont sur liste blanche pour l`accès au pare-feu.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de red. Verifique su conexión a Internet o comuníquese con el departamento de TI de su organización para asegurarse de que uat-api.quantificationanalytics.org/# y uat.quantificationanalytics.org/# estén en la lista blanca para el acceso al firewall.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de rede. Verifique sua conexão com a Internet ou entre em contato com o departamento de TI organizacional para garantir que uat-api.quantificationanalytics.org/# e uat.quantificationanalytics.org/# estejam na lista de permissões para acesso ao firewall.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.demoNetworkErrorMessage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Network error. Please check your internet connection or contact your organizational IT department to ensure demo-api.quantificationanalytics.org/# and demo.quantificationanalytics.org/# are whitelisted for firewall access.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur réseau. Veuillez vérifier votre connexion Internet ou contacter le service informatique de votre organisation pour vous assurer que demo-api.quantificationanalytics.org/# et demo.quantificationanalytics.org/# sont sur liste blanche pour l`accès au pare-feu.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de red. Verifique su conexión a Internet o comuníquese con el departamento de TI de su organización para asegurarse de que demo-api.quantificationanalytics.org/# y demo.quantificationanalytics.org/# estén en la lista blanca para el acceso al firewall.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de rede. Verifique sua conexão com a Internet ou entre em contato com o departamento de TI organizacional para garantir que demo-api.quantificationanalytics.org/# e demo.quantificationanalytics.org/# estejam na lista de permissões para acesso ao firewall.');-- pr

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.common.prodNetworkErrorMessage','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Network error. Please check your internet connection or contact your organizational IT department to ensure api.quantificationanalytics.org/# and www.quantificationanalytics.org/# are whitelisted for firewall access.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Erreur réseau. Veuillez vérifier votre connexion Internet ou contacter le service informatique de votre organisation pour vous assurer que api.quantificationanalytics.org/# et www.quantificationanalytics.org/# sont sur liste blanche pour l`accès au pare-feu.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Error de red. Verifique su conexión a Internet o comuníquese con el departamento de TI de su organización para asegurarse de que api.quantificationanalytics.org/# y www.quantificationanalytics.org/# estén en la lista blanca para el acceso al firewall.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Erro de rede. Verifique sua conexão com a Internet ou entre em contato com o departamento de TI organizacional para garantir que api.quantificationanalytics.org/# e www.quantificationanalytics.org/# estejam na lista de permissões para acesso ao firewall.');-- pr


