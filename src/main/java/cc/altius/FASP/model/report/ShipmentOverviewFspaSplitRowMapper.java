/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class ShipmentOverviewFspaSplitRowMapper implements RowMapper<ShipmentOverviewFspaSplit> {

    @Override
    public ShipmentOverviewFspaSplit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShipmentOverviewFspaSplit item = new ShipmentOverviewFspaSplit();
        item.setFspa(new SimpleCodeObject(rs.getInt("FSPA_ID"), new LabelRowMapper("FSPA_").mapRow(rs, rowNum), rs.getString("FSPA_CODE")));
        item.setProgramCountry(new SimpleCodeObject(rs.getInt("P_ID"), new LabelRowMapper("P_").mapRow(rs, rowNum), rs.getString("P_CODE")));
        item.setPlanningUnit(new SimpleObject(rs.getInt("PU_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum)));
        item.setQuantity(rs.getDouble("SHIPMENT_QTY"));
        item.setCost(rs.getDouble("COST"));
        item.setFreightCost(rs.getDouble("FREIGHT_COST"));
        return item;
    }

}
