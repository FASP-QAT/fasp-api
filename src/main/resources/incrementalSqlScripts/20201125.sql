update ap_problem p left join ap_label l on p.ACTION_LABEL_ID=l.LABEL_ID
set p.LAST_MODIFIED_DATE=now(),l.LABEL_EN='Please re-evaluate shipment dates/quantities  , which is overstocked for <%DT%> months within the next 7-18 months (lead time window). We recommend to delay/cancel or decrease quantity in shipment(s)'
where p.PROBLEM_ID=19;

insert into us_role_business_function values(NULL,'ROLE_PROGRAM_ADMIN','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW',1,now(),1,now());

insert into us_role_business_function values(NULL,'ROLE_PROGRAM_USER','ROLE_BF_SUPPLY_PLAN_VERSION_AND_REVIEW',1,now(),1,now());

ALTER TABLE `fasp`.`rm_supply_plan_amc` 
CHANGE COLUMN `AMC` `AMC` DECIMAL(14,4) NULL DEFAULT NULL ,
CHANGE COLUMN `MOS` `MOS` DECIMAL(14,4) NULL DEFAULT NULL ,
CHANGE COLUMN `MOS_WPS` `MOS_WPS` DECIMAL(14,4) NULL DEFAULT NULL ,
CHANGE COLUMN `MIN_STOCK_QTY` `MIN_STOCK_QTY` DECIMAL(14,4) NULL DEFAULT NULL ,
CHANGE COLUMN `MIN_STOCK_MOS` `MIN_STOCK_MOS` DECIMAL(14,4) NULL DEFAULT NULL ,
CHANGE COLUMN `MAX_STOCK_QTY` `MAX_STOCK_QTY` DECIMAL(14,4) NULL DEFAULT NULL ,
CHANGE COLUMN `MAX_STOCK_MOS` `MAX_STOCK_MOS` DECIMAL(14,4) NULL DEFAULT NULL ;

UPDATE ap_problem_status SET LABEL_ID=1128 WHERE PROBLEM_STATUS_ID=4;