INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.treeList.confirmAlert','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Server version will take some time to load. Are you sure you want to load the server version ?');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'La version du serveur prendra un certain temps à se charger. Êtes-vous sûr de vouloir charger la version du serveur ?');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'A versão do servidor levará algum tempo para carregar. Tem certeza de que deseja carregar a versão do servidor?');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'La versión del servidor tardará un tiempo en cargarse. ¿Está seguro de que desea cargar la versión del servidor?');-- sp
