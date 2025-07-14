insert into ap_label values(null,"In-Compliance","","","",1,now(),1,now(),20);
select max(l.LABEL_ID) into @max from ap_label l ;
UPDATE `ap_problem_status` SET `USER_MANAGED`='1' WHERE `PROBLEM_STATUS_ID`='2';
insert into ap_problem_status value(null,@max,0);
