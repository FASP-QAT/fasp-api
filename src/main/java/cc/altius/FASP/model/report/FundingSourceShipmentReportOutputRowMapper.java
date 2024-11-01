/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class FundingSourceShipmentReportOutputRowMapper implements RowMapper<FundingSourceShipmentReportOutput> {

    @Override
    public FundingSourceShipmentReportOutput mapRow(ResultSet rs, int i) throws SQLException {
        FundingSourceShipmentReportOutput fsro = new FundingSourceShipmentReportOutput(
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)),
                rs.getDouble("QTY"),
                rs.getDouble("PRODUCT_COST"),
                rs.getDouble("FREIGHT_PERC"),
                rs.getDouble("FREIGHT_COST")
                
        );
        fsro.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_CODE")));
        fsro.setFundingSourceType(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_TYPE_ID"), new LabelRowMapper("FST_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_TYPE_CODE")));
        return fsro;
    }

}
