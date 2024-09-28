/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ShipmentBudgetAmt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentBudgetAmtRowMapper implements RowMapper<ShipmentBudgetAmt> {

    @Override
    public ShipmentBudgetAmt mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ShipmentBudgetAmt(rs.getInt("SHIPMENT_ID"), rs.getInt("BUDGET_ID"), rs.getInt("CURRENCY_ID"), rs.getDouble("CONVERSION_RATE_TO_USD"), rs.getDouble("SHIPMENT_AMT"));
    }

}
