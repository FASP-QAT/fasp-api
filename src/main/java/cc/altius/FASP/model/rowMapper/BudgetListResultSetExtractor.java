/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class BudgetListResultSetExtractor implements ResultSetExtractor<List<Budget>> {

    @Override
    public List<Budget> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Budget> budgetList = new LinkedList<>();
        while (rs.next()) {
            Budget b = new Budget(rs.getInt("BUDGET_ID"), rs.getString("BUDGET_CODE"), new LabelRowMapper().mapRow(rs, 1));
            int idx = budgetList.indexOf(b);
            if (idx == -1) {
                budgetList.add(b);
                b.setFundingSource(
                        new FundingSource(
                                rs.getInt("FUNDING_SOURCE_ID"),
                                rs.getString("FUNDING_SOURCE_CODE"),
                                new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, 1),
                                new SimpleCodeObject(
                                        rs.getInt("REALM_ID"),
                                        new LabelRowMapper("REALM_").mapRow(rs, 1),
                                        rs.getString("REALM_CODE")
                                )
                        )
                );
                b.setBudgetAmt(rs.getDouble("BUDGET_AMT"));
                b.setBudgetUsdAmt(rs.getDouble("BUDGET_USD_AMT"));
                b.setUsedUsdAmt(rs.getDouble("USED_USD_AMT"));
                b.setCurrency(new Currency(rs.getInt("CURRENCY_ID"), rs.getString("CURRENCY_CODE"), new LabelRowMapper("CURRENCY_").mapRow(rs, 1), rs.getDouble("CONVERSION_RATE_TO_USD")));
                b.setStartDate(rs.getDate("START_DATE"));
                b.setStopDate(rs.getDate("STOP_DATE"));
                b.setNotes(rs.getString("NOTES"));
                b.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
            } else {
                b = budgetList.get(idx);
            }
            b.getPrograms().add(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE")));
        }
        return budgetList;
    }

}
