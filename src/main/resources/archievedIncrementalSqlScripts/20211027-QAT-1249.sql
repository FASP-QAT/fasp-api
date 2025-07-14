/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  altius
 * Created: 27-Oct-2021
 */

SET @dt = now();
UPDATE rm_manual_tagging mt 
LEFT JOIN rm_shipment s ON mt.SHIPMENT_ID=s.PARENT_SHIPMENT_ID
LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.ORDER_NO=mt.ORDER_NO AND st.PRIME_LINE_NO=mt.PRIME_LINE_NO AND s.MAX_VERSION_ID=st.VERSION_ID
LEFT JOIN rm_program p on s.PROGRAM_ID=p.PROGRAM_ID
SET st.RATE = st.RATE/mt.CONVERSION_FACTOR, st.LAST_MODIFIED_DATE=@dt
where mt.CONVERSION_FACTOR!=1 and st.SHIPMENT_ID IS NOT NULL and st.ACTIVE;

UPDATE rm_manual_tagging mt 
LEFT JOIN rm_shipment s ON mt.SHIPMENT_ID=s.PARENT_SHIPMENT_ID
LEFT JOIN rm_shipment_trans st ON s.SHIPMENT_ID=st.SHIPMENT_ID AND st.ORDER_NO=mt.ORDER_NO AND st.PRIME_LINE_NO=mt.PRIME_LINE_NO AND s.MAX_VERSION_ID=st.VERSION_ID
LEFT JOIN rm_program p on s.PROGRAM_ID=p.PROGRAM_ID
SET st.PRODUCT_COST=st.SHIPMENT_QTY*st.RATE, st.LAST_MODIFIED_DATE=@dt
where mt.CONVERSION_FACTOR!=1 and st.SHIPMENT_ID IS NOT NULL and st.ACTIVE;