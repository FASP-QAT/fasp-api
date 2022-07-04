/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectPrice;
import cc.altius.FASP.model.SimplePlanningUnitWithPrices;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitWithPricesListResultSetExtractor implements ResultSetExtractor<List<SimplePlanningUnitWithPrices>> {

    @Override
    public List<SimplePlanningUnitWithPrices> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<SimplePlanningUnitWithPrices> puList = new LinkedList<>();
        while (rs.next()) {
            SimplePlanningUnitWithPrices pu = new SimplePlanningUnitWithPrices(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, 1), new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PC_").mapRow(rs, 1)));
            int idx = puList.indexOf(pu);
            if (idx == -1) {
                puList.add(pu);
            } else {
                pu = puList.get(idx);
            }
            SimpleObjectPrice pa = new SimpleObjectPrice();
            pa.setId(rs.getInt("PROCUREMENT_AGENT_ID"));
            if (rs.wasNull()) {
                pa = null;
            } else {
                pa.setLabel(new LabelRowMapper("PA_").mapRow(rs, 1));
                pa.setCode(rs.getString("PROCUREMENT_AGENT_CODE"));
                pa.setPrice(rs.getDouble("CATALOG_PRICE"));
                pu.getProcurementAgentPriceList().add(pa);
            }
        }
        return puList;
    }

}
