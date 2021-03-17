set @actionLabel1=(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=3);
set @actionLabel2=(select ap.ACTION_LABEL_ID from ap_problem ap where ap.PROBLEM_ID=4);
UPDATE ap_label SET LABEL_EN='Please check to make sure shipment <%SHIPMENT_ID%> (<%PROCUREMNET_AGENT%> | <%RO_NO%> | <%SHIPMENT_QTY%> | <%SHIPMENT_DATE%>) was received, and update either the receive date or the shipment status.' WHERE `LABEL_ID`=@actionLabel1;
UPDATE ap_label SET LABEL_EN='Based on lead times,shipment <%SHIPMENT_ID%> (<%PROCUREMNET_AGENT%> | <%RO_NO%> | <%SHIPMENT_QTY%> | <%SHIPMENT_DATE%>) should have been submitted by now. Please double-check to make sure  the status or receive date is accurate.' WHERE `LABEL_ID`=@actionLabel2;
update rm_realm_problem rrp set rrp.LAST_MODIFIED_DATE=now();
