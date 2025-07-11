/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 14-Feb-2023
 */

update
rm_shipment_linking sl 
left join rm_erp_order_consolidated eoc on eoc.RO_NO=sl.RO_NO and eoc.RO_PRIME_LINE_NO and eoc.ORDER_NO=sl.ORDER_NO and eoc.PRIME_LINE_NO=sl.PRIME_LINE_NO
left join rm_shipment s on s.SHIPMENT_ID=sl.CHILD_SHIPMENT_ID
left join rm_shipment_trans st on st.SHIPMENT_ID=s.SHIPMENT_ID and s.MAX_VERSION_ID=st.VERSION_ID
left join rm_realm_country_planning_unit rcpu on rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=st.REALM_COUNTRY_PLANNING_UNIT_ID 
set eoc.LAST_MODIFIED_DATE=now()
where rcpu.MULTIPLIER!=1;