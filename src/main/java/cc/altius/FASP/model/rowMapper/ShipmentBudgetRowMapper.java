/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Budget;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.ShipmentBudget;
import cc.altius.FASP.model.SimpleBudgetObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ShipmentBudgetRowMapper implements RowMapper<ShipmentBudget> {
    
    @Override
    public ShipmentBudget mapRow(ResultSet rs, int arg1) throws SQLException {
        ShipmentBudget sb = new ShipmentBudget();
        sb.setBudgetId(rs.getInt("BUDGET_ID"));
        sb.setBudgetAmt(rs.getDouble("BUDGET_AMT"));
        sb.setConversionRateToUsd(rs.getDouble("CONVERSION_RATE_TO_USD"));
        Currency c = new Currency();
        c.setCurrencyId(rs.getInt("CURRENCY_ID"));
        sb.setCurrency(c);
        return sb;
    }
    
}
