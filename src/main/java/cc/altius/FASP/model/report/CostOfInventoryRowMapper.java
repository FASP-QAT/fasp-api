/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class CostOfInventoryRowMapper implements RowMapper<CostOfInventoryOutput> {

    @Override
    public CostOfInventoryOutput mapRow(ResultSet rs, int i) throws SQLException {
        CostOfInventoryOutput c = new CostOfInventoryOutput();
        c.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper().mapRow(rs, i)));
        c.setCost(rs.getDouble("COST"));
        c.setStock(rs.getDouble("STOCK"));
        c.setCatalogPrice(rs.getDouble("CATALOG_PRICE"));
        c.setCalculated(rs.getBoolean("CALCULATED"));
        return c;
    }

}
