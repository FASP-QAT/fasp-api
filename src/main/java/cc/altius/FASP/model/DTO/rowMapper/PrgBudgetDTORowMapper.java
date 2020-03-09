/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgBudgetDTO;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgBudgetDTORowMapper implements RowMapper<PrgBudgetDTO> {

    @Override
    public PrgBudgetDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgBudgetDTO budget = new PrgBudgetDTO();
        budget.setBudgetId(rs.getInt("BUDGET_ID"));
        budget.setBudgetAmount(rs.getDouble("BUDGET_AMOUNT"));
        budget.setLabel(new PrgLabelDTORowMapper("BUDGET_").mapRow(rs, i));
        budget.setStartDate(rs.getDate("BUDGET_START_DATE"));
        budget.setStopDate(rs.getDate("BUDGET_STOP_DATE"));

        PrgSubFundingSourceDTO subFundingSource = new PrgSubFundingSourceDTO();
        PrgFundingSourceDTO fundingSource = new PrgFundingSourceDTO();
        fundingSource.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        fundingSource.setLabel(new PrgLabelDTORowMapper("FUNDING_SOURCE_").mapRow(rs, i));
        subFundingSource.setFundingSource(fundingSource);

        subFundingSource.setLabel(new PrgLabelDTORowMapper("SUB_FUNDING_SOURCE_").mapRow(rs, i));
        subFundingSource.setSubFundingSourceId(rs.getInt("SUB_FUNDING_SOURCE_ID"));
        budget.setSubFundingSource(subFundingSource);
        return budget;
    }

}
