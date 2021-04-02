set @actionLabel1=(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=3);
set @actionLabel2=(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=4);
UPDATE ap_label SET LABEL_EN='Please check to make sure shipment <%SHIPMENT_ID%> (<%PROCUREMNET_AGENT%> | <%RO_NO%> | <%SHIPMENT_QTY%> | <%SHIPMENT_DATE%>) was received, and update either the receive date or the shipment status.' WHERE `LABEL_ID`=@actionLabel1;
UPDATE ap_label SET LABEL_EN='Based on lead times,shipment <%SHIPMENT_ID%> (<%PROCUREMNET_AGENT%> | <%RO_NO%> | <%SHIPMENT_QTY%> | <%SHIPMENT_DATE%>) should have been submitted by <%SUBMITTED_DATE%>. Please double-check to make sure  the status or receive date is accurate.' WHERE `LABEL_ID`=@actionLabel2;
update rm_realm_problem rrp set rrp.LAST_MODIFIED_DATE=now();

INSERT INTO `ap_static_label`(`STATIC_LABEL_ID`,`LABEL_CODE`,`ACTIVE`) VALUES ( NULL,'static.qpl.units','1');
SELECT MAX(l.STATIC_LABEL_ID) INTO @MAX FROM ap_static_label l ;
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,1,'units');-- en
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,2,'unités');-- fr
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,3,'unidades');-- sp
INSERT INTO ap_static_label_languages VALUES(NULL,@MAX,4,'unidades');-- pr
