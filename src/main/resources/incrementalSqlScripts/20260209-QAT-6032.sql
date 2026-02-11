update rm_realm_problem r set r.ACTIVE=0,r.LAST_MODIFIED_DATE=now() where r.REALM_PROBLEM_ID IN (22,23,28);

update rm_problem_report r set r.PROBLEM_STATUS_ID=2,r.LAST_MODIFIED_DATE=now() where r.REALM_PROBLEM_ID IN (22,23,28);

insert into ap_label values (NULL,"In the next 18 months, this supply plan
<%PROBLEM_DESCRIPTION%>","Au cours des 18 prochains mois, ce plan d'approvisionnement
<%PROBLEM_DESCRIPTION%>","En los próximos 18 meses, usted
<%PROBLEM_DESCRIPTION%>","Nos próximos 18 meses, este plano de fornecimento
<%PROBLEM_DESCRIPTION%>",1,now(),1,now(),22);

set @maxid4=(select max(al.LABEL_ID) from ap_label al);

insert into ap_label values (NULL,"<%PROBLEM_SUGGESTION%>","<%PROBLEM_SUGGESTION%>","<%PROBLEM_SUGGESTION%>","<%PROBLEM_SUGGESTION%>",1,now(),1,now(),22);

set @maxid5=(select max(al.LABEL_ID) from ap_label al);

insert into ap_problem values(null,@maxid4,3,"/supplyPlan",@maxid5,1,1,1,1,1,1,1,now(),1,now());
set @maxid6=(select max(ap.PROBLEM_ID) from ap_problem ap);
select @maxid6;

insert into  rm_realm_problem values(null,1,@maxid6,2,18,null,null,1,1,1,now(),1,now());

INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.minMaxProblemDescOverMax','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' * is over max for {{count}} month(s) ({{monthNames}})\n');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' * dépasse le maximum pour {{count}} mois) ({{months Names}})\n');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' * supera el máximo durante {{count}} meses) ({{months Names}})\n');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' * está acima do máximo durante {{count}} meses) ({{months Names}})\n');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.minMaxProblemDescUnderMin','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' * is under min for {{count}} month(s) ({{monthNames}})\n');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' * est inférieur au minimum pour {{count}} mois ({{monthNames}})\n');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' * está por debajo del mínimo para {{count}} mes(es) ({{monthNames}})\n');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' * está abaixo do mínimo por {{count}} mês(es) ({{monthNames}})\n');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.minMaxProblemDescStockedOut','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' * is stocked out for {{count}} month(s) ({{monthNames}})\n');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' * est en rupture de stock depuis {{count}} mois ({{monthNames}})\n');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' * está agotado desde hace {{count}} mes(es) ({{monthNames}})\n');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' * está esgotado há {{count}} mês(es) ({{monthNames}})\n');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.minMaxProblemDescShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' * has shipments in {{monthNames}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' * a des livraisons en {{monthNames}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' * tiene envíos en {{monthNames}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' * tem remessas em {{monthNames}}');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.suggestion1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please consider delaying/canceling shipment(s), reviewing future expiries, or donating stock. Indicate if any of these actions have been/will be taken, and if you face any obstacles.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez envisager de reporter ou d\'annuler les envois, de vérifier les dates de péremption à venir ou de faire don de vos stocks. Indiquez si l\'une de ces actions a été ou sera entreprise et si vous rencontrez des difficultés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Considere retrasar o cancelar envíos, revisar vencimientos futuros o donar existencias. Indique si ha tomado o tomará alguna de estas medidas y si enfrenta algún obstáculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, considere adiar/cancelar o(s) envio(s), verificar os prazos de validade futuros ou doar o stock. Indique se alguma destas ações foi/será tomada e se enfrenta algum obstáculo.');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.suggestion2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please consider planning & ordering, expediting, or increasing shipment quantities. Indicate if any of these actions have been/will be taken, and if you face any obstacles.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez envisager de planifier et de passer commande, d\'accélérer la livraison ou d\'augmenter les quantités expédiées. Indiquez si l\'une de ces actions a été ou sera entreprise et si vous rencontrez des difficultés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Considere planificar y ordenar, agilizar o aumentar las cantidades de envío. Indique si ya ha implementado o implementará alguna de estas acciones y si enfrenta algún obstáculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Considere o planeamento e o pedido, a agilização ou o aumento das quantidades de envio. Indique se alguma destas ações foi/será tomada e se enfrenta algum obstáculo.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.suggestion3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please consider adjusting shipment quantities. Indicate if any of these actions have been/will be taken, and if you face any obstacles.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez envisager de modifier les quantités expédiées. Indiquez si l\'une de ces mesures a été ou sera prise, et si vous rencontrez des difficultés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Considere ajustar las cantidades de envío. Indique si se ha implementado o se implementará alguna de estas medidas y si enfrenta algún obstáculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, considere ajustar as quantidades de envio. Indique se alguma destas ações foi/será tomada e se enfrenta algum obstáculo.');-- pr