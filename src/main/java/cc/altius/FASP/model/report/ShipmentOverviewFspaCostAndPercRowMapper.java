/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class ShipmentOverviewFspaCostAndPercRowMapper implements RowMapper<ShipmentOverviewFspaCostAndPerc> {

    @Override
    public ShipmentOverviewFspaCostAndPerc mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShipmentOverviewFspaCostAndPerc item = new ShipmentOverviewFspaCostAndPerc();
        item.setFspa(new SimpleCodeObject(rs.getInt("FSPA_ID"), new LabelRowMapper("FSPA_").mapRow(rs, rowNum), rs.getString("FSPA_CODE")));
        item.setCost(rs.getDouble("TOTAL_COST"));
        return item;
    }

}
