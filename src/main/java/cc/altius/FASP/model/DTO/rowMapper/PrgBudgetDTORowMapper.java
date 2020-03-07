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
        PrgLabelDTO budgetLabel = new PrgLabelDTO();
        budgetLabel.setLabelEn(rs.getString("BUDGET_NAME_EN"));
        budgetLabel.setLabelFr(rs.getString("BUDGET_NAME_FR"));
        budgetLabel.setLabelPr(rs.getString("BUDGET_NAME_PR"));
        budgetLabel.setLabelSp(rs.getString("BUDGET_NAME_SP"));
        budget.setLabel(budgetLabel);
        budget.setStartDate(rs.getDate("BUDGET_START_DATE"));
        budget.setStopDate(rs.getDate("BUDGET_STOP_DATE"));

        PrgSubFundingSourceDTO subFundingSource = new PrgSubFundingSourceDTO();
        PrgFundingSourceDTO fundingSource = new PrgFundingSourceDTO();
        fundingSource.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        PrgLabelDTO fundingSourceLabel = new PrgLabelDTO();
        fundingSourceLabel.setLabelEn(rs.getString("FUNDING_SOURCE_NAME_EN"));
        fundingSourceLabel.setLabelFr(rs.getString("FUNDING_SOURCE_NAME_FR"));
        fundingSourceLabel.setLabelPr(rs.getString("FUNDING_SOURCE_NAME_PR"));
        fundingSourceLabel.setLabelSp(rs.getString("FUNDING_SOURCE_NAME_SP"));
        fundingSource.setLabel(fundingSourceLabel);
        subFundingSource.setFundingSource(fundingSource);
        PrgLabelDTO subFundingSourceLabel = new PrgLabelDTO();
        subFundingSourceLabel.setLabelEn(rs.getString("SUB_FUNDING_SOURCE_NAME_EN"));
        subFundingSourceLabel.setLabelFr(rs.getString("SUB_FUNDING_SOURCE_NAME_FR"));
        subFundingSourceLabel.setLabelPr(rs.getString("SUB_FUNDING_SOURCE_NAME_PR"));
        subFundingSourceLabel.setLabelSp(rs.getString("SUB_FUNDING_SOURCE_NAME_SP"));
        subFundingSource.setLabel(subFundingSourceLabel);
        subFundingSource.setSubFundingSourceId(rs.getInt("SUB_FUNDING_SOURCE_ID"));
        budget.setSubFundingSource(subFundingSource);
        return budget;
    }

}
