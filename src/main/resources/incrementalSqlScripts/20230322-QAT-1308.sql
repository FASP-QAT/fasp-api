/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 22-Mar-2023
 */

update ap_label l set l.LABEL_EN='TB Rapid Diagnostic Test (RDT) Determine TB LAM Ag, 25 Tests' where l.LABEL_ID=32418;
update rm_realm_country_planning_unit rcpu SET rcpu.LAST_MODIFIED_DATE=now() where rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=1454;


update 
rm_shipment_linking sl 
left join rm_shipment s on s.SHIPMENT_ID=sl.PARENT_SHIPMENT_ID
left join rm_shipment_trans st on st.SHIPMENT_ID=s.SHIPMENT_ID and s.MAX_VERSION_ID=st.VERSION_ID
SET st.ERP_FLAG=1,st.ACTIVE=0
where sl.ACTIVE and (st.ERP_FLAG=0 OR st.ACTIVE=1);

update rm_shipment s 
left join rm_shipment_trans st on s.SHIPMENT_ID=st.SHIPMENT_ID and s.MAX_VERSION_ID=st.VERSION_ID
SET st.ERP_FLAG=1,st.ACTIVE=0
where st.PARENT_LINKED_SHIPMENT_ID in (select s.PARENT_SHIPMENT_ID from rm_shipment_linking s where s.ACTIVE)
and (st.ERP_FLAG=0 or st.ACTIVE=1);

update rm_shipment_linking sl 
left join rm_shipment s on s.SHIPMENT_ID=sl.CHILD_SHIPMENT_ID
left join rm_shipment_trans st on st.SHIPMENT_ID=s.SHIPMENT_ID and s.MAX_VERSION_ID=st.VERSION_ID
SET st.ERP_FLAG=1,st.ACTIVE=1
where sl.ACTIVE and (st.ERP_FLAG=0 OR st.ACTIVE=0);