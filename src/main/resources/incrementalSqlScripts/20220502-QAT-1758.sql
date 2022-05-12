/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 02-May-2022
 */

update rm_program_version p 
left join ct_supply_plan_commit_request ct on p.PROGRAM_ID=ct.PROGRAM_ID and p.VERSION_ID=ct.VERSION_ID
set p.NOTES=ct.NOTES
where p.NOTES is null and ct.NOTES!='';