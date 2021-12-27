/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  altius
 * Created: 27-Dec-2021
 */

delete pt.* from rm_problem_report_trans pt where pt.PROBLEM_REPORT_ID in (13411,13412,13413,13414,13415,13416);
delete pr.* from rm_problem_report pr  where pr.PROBLEM_REPORT_ID in (13411,13412,13413,13414,13415,13416);