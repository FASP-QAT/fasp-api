/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class BudgetReportOutputRowMapper implements RowMapper<BudgetReportOutput> {

    @Override
    public BudgetReportOutput mapRow(ResultSet rs, int i) throws SQLException {
        BudgetReportOutput br = new BudgetReportOutput();
        br.setBudget(new SimpleCodeObject(rs.getInt("BUDGET_ID"), new LabelRowMapper().mapRow(rs, i), rs.getString("BUDGET_CODE")));
        br.setCurrency(new SimpleCodeObject(rs.getInt("CURRENCY_ID"), new LabelRowMapper("CURRENCY_").mapRow(rs, i), rs.getString("CURRENCY_CODE")));
        br.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_CODE")));
        br.setFundingSourceType(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_TYPE_ID"), new LabelRowMapper("FST_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_TYPE_CODE")));
        br.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        br.setBudgetAmt(rs.getDouble("BUDGET_AMT"));
        br.setPlannedBudgetAmt(rs.getDouble("PLANNED_BUDGET_AMT"));
        br.setOrderedBudgetAmt(rs.getDouble("ORDERED_BUDGET_AMT"));
        br.setStartDate(rs.getDate("START_DATE"));
        br.setStopDate(rs.getDate("STOP_DATE"));
        return br;
    }

}
