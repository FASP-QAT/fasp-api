/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.AnnualTargetCalculator;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class AnnualTargetCalculatorResultSetExtractor implements ResultSetExtractor<AnnualTargetCalculator> {

    @Override
    public AnnualTargetCalculator extractData(ResultSet rs) throws SQLException, DataAccessException {
        AnnualTargetCalculator atc = null;
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                atc = new AnnualTargetCalculator();
                atc.setNodeDataAnnualTargetCalculatorId(rs.getInt("NODE_DATA_ANNUAL_TARGET_CALCULATOR_ID"));
                atc.setFirstMonthOfTarget(rs.getString("FIRST_MONTH_OF_TARGET"));
                atc.setYearsOfTarget(rs.getInt("YEARS_OF_TARGET"));
            }
            atc.getActualOrTargetValueList().add(rs.getInt("ACTUAL_OR_TARGET_VALUE"));
        }
        return atc;
    }

}
