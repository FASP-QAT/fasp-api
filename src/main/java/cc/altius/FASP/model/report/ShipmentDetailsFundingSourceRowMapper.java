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
public class ShipmentDetailsFundingSourceRowMapper implements RowMapper<ShipmentDetailsFundingSource>{

    @Override
    public ShipmentDetailsFundingSource mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentDetailsFundingSource sdfs = new ShipmentDetailsFundingSource();
        sdfs.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_CODE")));
        sdfs.setOrderCount(rs.getInt("ORDER_COUNT"));
        sdfs.setQuantity(rs.getLong("QUANTITY"));
        sdfs.setCost(rs.getDouble("COST"));
        return sdfs;
    }
    
}
