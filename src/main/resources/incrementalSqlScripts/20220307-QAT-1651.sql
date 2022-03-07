/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 07-Mar-2022
 */

update rm_consumption c 
left join rm_consumption_trans ct on c.CONSUMPTION_ID=ct.CONSUMPTION_ID and c.MAX_VERSION_ID=ct.VERSION_ID
set ct.PLANNING_UNIT_ID=4586
where c.PROGRAM_ID=2034 and ct.PLANNING_UNIT_ID=4585;

update rm_shipment c 
left join rm_shipment_trans ct on c.SHIPMENT_ID=ct.SHIPMENT_ID and c.MAX_VERSION_ID=ct.VERSION_ID
set ct.PLANNING_UNIT_ID=4586
where c.PROGRAM_ID=2034 and ct.PLANNING_UNIT_ID=4585;