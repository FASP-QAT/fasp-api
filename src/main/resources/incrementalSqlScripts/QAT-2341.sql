/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 31-Oct-2022
 */

update rm_realm_problem p set p.CRITICALITY_ID=2,p.LAST_MODIFIED_DATE=now() where p.PROBLEM_ID=29;