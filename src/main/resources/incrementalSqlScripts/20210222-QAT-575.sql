delete from  rm_problem_report_trans  where rm_problem_report_trans.PROBLEM_REPORT_ID in (select rpr.PROBLEM_REPORT_ID from rm_problem_report rpr where rpr.PROGRAM_ID=2135);

delete from rm_problem_report  where rm_problem_report.PROGRAM_ID=2135;

