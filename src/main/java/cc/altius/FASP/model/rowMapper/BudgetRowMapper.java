/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class BudgetRowMapper implements RowMapper<Budget> {

    @Override
    public Budget mapRow(ResultSet rs, int rowNum) throws SQLException {
        Budget b = new Budget(rs.getInt("BUDGET_ID"), new LabelRowMapper().mapRow(rs, rowNum));
        b.setProgram(new Program(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, rowNum)));
        b.setFundingSource(
                new FundingSource(
                        rs.getInt("FUNDING_SOURCE_ID"),
                        new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, rowNum),
                        new SimpleCodeObject(
                                rs.getInt("REALM_ID"),
                                new LabelRowMapper("REALM_").mapRow(rs, rowNum),
                                rs.getString("REALM_CODE")
                        )
                )
        );
        b.setBudgetAmt(rs.getInt("BUDGET_AMT"));
        b.setStartDate(rs.getDate("START_DATE"));
        b.setStopDate(rs.getDate("STOP_DATE"));
        b.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return b;
    }

}
