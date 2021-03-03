insert into ap_label values(null,"Please check your actual consumption input in region <%REGION%> and provide data for month <%GAP_MONTHS%> ","","","",1,now(),1,now(),20);
select max(l.LABEL_ID) into @max1 from ap_label l ;
insert into ap_problem values(null,1099,1,"/consumptionDetails",@max1,1,0,0,0,0,1,1,now(),1,now());
set @maxid=(select max(ap.PROBLEM_ID) from ap_problem ap);
insert into  rm_realm_problem values(null,1,@maxid,3,6,null,null,1,1,1,now(),1,now());
update rm_realm_problem rrp set rrp.LAST_MODIFIED_DATE=now();

