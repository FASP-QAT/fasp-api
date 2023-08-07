INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.programProcurementAgentPrice.success','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Procurement Agent Prices added successfully');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Les prix de l`agent d`approvisionnement ont été ajoutés avec succès');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Preços do Agente de Compras adicionados com sucesso');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Los precios del agente de compras se agregaron con éxito');-- sp
