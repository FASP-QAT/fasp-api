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
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class StockStatusAcrossProductsOutputResultsetExtractor implements ResultSetExtractor<List<StockStatusAcrossProductsOutput>> {

    @Override
    public List<StockStatusAcrossProductsOutput> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<StockStatusAcrossProductsOutput> ssapList = new LinkedList<>();
        while (rs.next()) {
            StockStatusAcrossProductsOutput ssap = new StockStatusAcrossProductsOutput();
            ssap.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1)));
            int idx = ssapList.indexOf(ssap);
            if (idx != -1) {
                ssap = ssapList.get(idx);
            } else {
                ssapList.add(ssap);
            }
            SimpleCodeObject program = new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, 1), rs.getString("PROGRAM_CODE"));
            StockStatusAcrossProductsForProgram ssapfp = new StockStatusAcrossProductsForProgram();
            ssapfp.setProgram(program);
            ssap.getProgramData().add(ssapfp);
        }
        return ssapList;
    }

}
