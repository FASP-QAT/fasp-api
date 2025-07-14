update ap_label al set al.LABEL_EN="In next 18 months ,there are <%NOOFMONTHS%> months with no forecasted consumption in <%REGION%> region.Please add forecasted consumption for <%MONTHARRAY%>."
where al.LABEL_ID =(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=8);

update rm_realm_problem rrm set rrm.LAST_MODIFIED_DATE=now() where rrm.PROBLEM_ID=8;

ALTER TABLE `rm_problem_report` 
CHANGE COLUMN `DATA5` `DATA5` VARCHAR(225) CHARACTER SET 'utf8' NULL DEFAULT NULL ;

