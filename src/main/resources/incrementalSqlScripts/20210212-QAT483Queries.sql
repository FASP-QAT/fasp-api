/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 12-Feb-2021
 */

update ap_label al set al.LABEL_EN="Please ensure you have recent actual consumption data in region <%REGION%>.
The last actual consumption data in QAT within last 6 months period is <%CONSUMPTIONMONTH%> and 
there is no actual consumption for the month of <%NOCONSUMPTIONMONTHS%>"
where al.LABEL_ID=(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=1);

UPDATE rm_realm_problem rrp SET rrp.DATA2='6',rrp.LAST_MODIFIED_DATE=now() where `REALM_PROBLEM_ID`='1';

update ap_label al set al.LABEL_EN="Please ensure you have recent inventory data in region <%REGION%>.
The last inventory data in QAT within las 6 months period is  <%INVENTORYMONTH%> and there is no invetory for the month of <%NOINVENTORYMONTHS%>"
where al.LABEL_ID=(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=2);

UPDATE rm_realm_problem rrp SET rrp.DATA2='6',rrp.LAST_MODIFIED_DATE=now() where `REALM_PROBLEM_ID`='2';

ALTER TABLE `ap_label` 
CHANGE COLUMN `LABEL_EN` `LABEL_EN` VARCHAR(1000) CHARACTER SET 'utf8' NOT NULL COMMENT 'Label in English, cannot be Null since it is language the system will default to' ,
CHANGE COLUMN `LABEL_FR` `LABEL_FR` VARCHAR(1000) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT 'Label in French' ,
CHANGE COLUMN `LABEL_SP` `LABEL_SP` VARCHAR(1000) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT 'Label in Spanish' ,
CHANGE COLUMN `LABEL_PR` `LABEL_PR` VARCHAR(1000) CHARACTER SET 'utf8' NULL DEFAULT NULL COMMENT 'Label in Pourtegese' ;


update ap_label al set al.LABEL_EN="Please 
check consumption data in region <%REGION%> 
during period <%DT%> 
as we encourage forecasting with dynamic monthly values, 
showing that factors such as program scale up,or seasonality have been taken into account 
months range and consumption value with same forecatsed consumption are <%SAMECONSUMPTIONMONTHS%>"
where al.LABEL_ID=(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=10);

update rm_realm_problem rrp set rrp.LAST_MODIFIED_DATE=now() where rrp.REALM_PROBLEM_ID=11;

insert into ap_label values(null,"In the next 1-6 months (<%RANGE_1TO6_MONTHS%>), inventory is…
  * overstocked for <%MOSABOVE_MAX_IN6MONTHS%> month(s) 	
  * understocked for <%MOSLESS_MIN_IN6MONTHS%> month(s). 
 
In the next 7-18 months (<%RANGE_7TO18_MONTHS%>), inventory is… 
  * overstocked for <%MOSABOVE_MAX_IN7TO18MONTHS%> month(s) 
  * understocked for <%MOSLESS_MIN_IN7TO18MONTHS%> month(s) 

Note: There are <%SHIPMENTS_IN6MONTHS%> shipment(s) in the next 1-6 months, and there are <%SHIPMENTS_IN7TO18MONTHS%> shipment(s) in the next 7-18 months.",null,null,null,1,now(),1,now(),22);

set @maxid1=(select max(al.LABEL_ID) from ap_label al);

insert into ap_label values(null,"Re-evaluate supply plan to meet the min/max parameters. 

If overstocked, consider delaying/canceling shipment(s), review future expiries, or donating stock. 
If understocked, consider planning & ordering, expediting, or increasing shipment quantity.",null,null,null,1,now(),1,now(),22);

set @maxid2=(select max(al.LABEL_ID) from ap_label al);

select @maxid1,@maxid2;

insert into ap_problem values(null,@maxid1,3,"/supplyPlan",@maxid2,1,1,now(),1,now());
set @maxid3=(select max(ap.PROBLEM_ID) from ap_problem ap);
select @maxid3;
insert into  rm_realm_problem values(null,1,@maxid3,3,6,18,null,1,1,1,now(),1,now());


   
insert into ap_label values(null,"Stock out(s) in the next 1-6 months (<%RANGE_1TO6_MONTHS%>): 

<%STOCKOUT_1TO6_MONTHS%>

 
Stock out(s) in the next 7-18 months (<%RANGE_7TO18_MONTHS%>): 

<%STOCKOUT_7TO18_MONTHS%> 


Note: There are <%SHIPMENTS_IN6MONTHS%> shipment(s) in the next 1-6 months, and there are <%SHIPMENTS_IN7TO18MONTHS%> shipment(s) in the next 7-18 months. ",null,null,null,1,now(),1,now(),22);

set @maxid4=(select max(al.LABEL_ID) from ap_label al);

insert into ap_label values(null,"Consider planning & ordering, expediting, or increasing shipment quantity. Communicate the potential stockout(s) with relevant stakeholders. ",null,null,null,1,now(),1,now(),22);

set @maxid5=(select max(al.LABEL_ID) from ap_label al);

select @maxid4,@maxid5;

insert into ap_problem values(null,@maxid4,3,"/supplyPlan",@maxid5,1,1,now(),1,now());
set @maxid6=(select max(ap.PROBLEM_ID) from ap_problem ap);
select @maxid6;
insert into  rm_realm_problem values(null,1,@maxid6,3,6,18,null,1,1,1,now(),1,now());


ALTER TABLE `fasp`.`rm_problem_report` 
CHANGE COLUMN `DATA5` `DATA5` TEXT CHARACTER SET 'utf8' NULL DEFAULT NULL ;

SET FOREIGN_KEY_CHECKS=0;
truncate table rm_problem_report_trans;
truncate table rm_problem_report;
SET FOREIGN_KEY_CHECKS=1;

update rm_realm_problem rrp set rrp.CRITICALITY_ID=2 where rrp.PROBLEM_ID=23;

update rm_realm_problem rrp set rrp.LAST_MODIFIED_DATE=now();


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.qpl.noMonths','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'No months');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'Pas de mois');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'No meses');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'Sem meses');-- pr


INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.qpl.notPresent','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'not present');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'pas présent');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'no presente');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'não presente');-- pr
