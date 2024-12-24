INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemReport.pendingapproval','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Pending Approval');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'En attente d`approbation');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Pendiente de aprobación');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Aprovação pendente');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemReport.pendingapprovalDesc','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The submitted supply plan has not been reviewed. Once reviewed, the supply plan will be sent back as “Approved” or “Needs Revision.” If the supply plan does not need to be reviewed, it will be sent back as “No Review Needed.”');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le plan d`approvisionnement soumis n`a pas été examiné. Une fois examiné, le plan d’approvisionnement sera renvoyé comme « Approuvé » ou « Nécessite une révision ». Si le plan d’approvisionnement n’a pas besoin d’être révisé, il sera renvoyé avec la mention « Aucune révision nécessaire ».');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'El plan de suministro presentado no ha sido revisado. Una vez revisado, el plan de suministro se devolverá como ""Aprobado"" o ""Necesita revisión"". Si no es necesario revisar el plan de suministro, se devolverá como ""No se necesita revisión"".');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O plano de fornecimento apresentado não foi revisado. Depois de revisado, o plano de fornecimento será enviado de volta como “Aprovado” ou “Necessita Revisão”. Se o plano de fornecimento não precisar ser revisado, ele será enviado de volta como “Nenhuma revisão necessária”.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemReport.noreviewneeded','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No Review Needed');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Aucun examen nécessaire');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No se necesita revisión');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Nenhuma revisão necessária');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemReport.noreviewneededDesc','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'The submitted supply plan does not need to be reviewed. The Supply Plan Reviewer will use this status when a supply plan has been accidentally uploaded as a duplicate or uploaded outside of the designated supply plan submission period.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Le plan d’approvisionnement soumis n’a pas besoin d’être révisé. L`examinateur du plan d`approvisionnement utilisera ce statut lorsqu`un plan d`approvisionnement a été accidentellement téléchargé en double ou téléchargé en dehors de la période de soumission du plan d`approvisionnement désignée.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No es necesario revisar el plan de suministro presentado. El revisor del plan de suministro utilizará este estado cuando un plan de suministro se cargó accidentalmente como un duplicado o se cargó fuera del período de presentación del plan de suministro designado.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'O plano de fornecimento apresentado não precisa ser revisado. O Revisor do Plano de Fornecimento usará esse status quando um plano de fornecimento tiver sido carregado acidentalmente como duplicado ou fora do período de envio do plano de fornecimento designado.');-- pr