INSERT INTO fasp.ap_static_label(STATIC_LABEL_ID,LABEL_CODE,ACTIVE) VALUES ( NULL,'static.procurementAgent.procurementAgentNotMappedWithProgram','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No Procurement Agent mapped with this Program');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucun agent d`approvisionnement associé à ce programme');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhum Agente de Compras mapeado com este Programa');-- pr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Ningún Agente de Adquisiciones asignado con este Programa');-- sp
