update rm_realm_problem r set r.ACTIVE=0,r.LAST_MODIFIED_DATE=now() where r.REALM_PROBLEM_ID IN (22,23,28);

update rm_problem_report r set r.PROBLEM_STATUS_ID=2,r.LAST_MODIFIED_DATE=now() where r.REALM_PROBLEM_ID IN (22,23,28);

insert into ap_label values (NULL,"In the next 18 months, you 
<%PROBLEM_DESCRIPTION%>","Au cours des 18 prochains mois, vous
<%PROBLEM_DESCRIPTION%>","En los próximos 18 meses, usted
<%PROBLEM_DESCRIPTION%>","Nos próximos 18 meses, poderá
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

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' • are over max for {{count}} month(s) ({{monthNames}})\n');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' • dépassent le maximum pendant {{count}} mois ({{monthNames}})');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' • superan el límite máximo durante {{count}} mes(es) ({{monthNames}})\n');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' • estão acima do máximo para {{count}} mês(es) ({{monthNames}})\n');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.minMaxProblemDescUnderMin','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' • are under min for {{count}} month(s) ({{monthNames}})\n');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' • sont en dessous du minimum pendant {{count}} mois ({{monthNames}})');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' • están por debajo del mínimo durante {{count}} mes(es) ({{monthNames}})');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' • estão abaixo do mínimo por {{count}} mês(es) ({{monthNames}})\n');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.minMaxProblemDescStockedOut','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' • are stocked out for {{count}} month(s) ({{monthNames}})\n');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' • sont en rupture de stock depuis {{count}} mois ({{monthNames}})');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' • Están agotados durante {{count}} mes(es) ({{monthNames}})');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' • Estão esgotados há {{count}} mês(es) ({{monthNames}})\n');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.minMaxProblemDescShipments','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,' • have shipments in {{monthNames}}');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,' • avoir des expéditions en {{monthNames}}');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,' • tener envíos en {{monthNames}}');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,' • ter remessas em {{monthNames}}');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.suggestion1','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please consider delaying/canceling shipment(s), review future expiries, or donating stock. Indicate if any of these actions have been/will be taken, and you face any obstacles.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez envisager de reporter ou d\'annuler les expéditions, de vérifier les dates de péremption des produits ou de faire don des stocks. Veuillez indiquer si l\'une de ces mesures a été ou sera prise et si vous rencontrez des difficultés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, considere retrasar o cancelar los envíos, revisar las fechas de caducidad de los productos o donar el stock. Indique si se ha tomado o se tomará alguna de estas medidas y si encuentra algún obstáculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, considere adiar/cancelar o(s) envio(s), verificar os prazos de validade futuros ou doar o stock. Indique se alguma destas ações foi/será tomada e se enfrenta algum obstáculo.');-- pr"
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.suggestion2','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please consider planning & ordering, expediting, or increasing shipment quantity. Indicate if any of these actions have been/will be taken, and you face any obstacles.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez envisager de planifier et de passer commande, d\'accélérer la livraison ou d\'augmenter les quantités expédiées. Veuillez indiquer si l\'une de ces mesures a été ou sera prise, et si vous rencontrez des difficultés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, consideren planificar y realizar el pedido, acelerar el envío o aumentar la cantidad del mismo. Indiquen si se ha tomado o se tomará alguna de estas medidas y si se enfrentan a algún obstáculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Considere o planeamento e o pedido, a agilização ou o aumento da quantidade de remessas. Indique se alguma destas ações foi/será tomada e se enfrenta algum obstáculo.');-- pr
INSERT INTO `fasp`.`ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.problemList.suggestion3','1'); 
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;

INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'Please consider adjust shipment quantity. Indicate if any of these actions have been/will be taken, and you face any obstacles.');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Veuillez envisager d\'ajuster la quantité d\'articles expédiés. Veuillez indiquer si l\'une de ces mesures a été ou sera prise et si vous rencontrez des difficultés.');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'Por favor, considere ajustar la cantidad del envío. Indique si se ha tomado o se tomará alguna de estas medidas y si encuentra algún obstáculo.');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Por favor, considere ajustar a quantidade de envio. Indique se alguma destas ações foi/será tomada e se enfrenta algum obstáculo.');-- pr