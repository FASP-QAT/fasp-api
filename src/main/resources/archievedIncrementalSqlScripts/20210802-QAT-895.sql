delete prt.* from rm_problem_report_trans prt where prt.PROBLEM_REPORT_ID in ( select pr.PROBLEM_REPORT_ID from rm_problem_report pr where pr.PROGRAM_ID=2034 and pr.VERSION_ID=17);
delete pr.* from rm_problem_report pr where pr.PROGRAM_ID=2034 and pr.VERSION_ID=17;

delete from rm_problem_report_trans  where rm_problem_report_trans.PROBLEM_REPORT_ID in (4356,800,802,804,852,4354,4352,4355,4353);
delete from rm_problem_report  where rm_problem_report.PROBLEM_REPORT_ID in (4356,800,802,804,852,4354,4352,4355,4353);
