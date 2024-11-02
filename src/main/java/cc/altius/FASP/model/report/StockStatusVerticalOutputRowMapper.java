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
public class StockStatusVerticalOutputRowMapper implements ResultSetExtractor<List<StockStatusVertical>> {

    @Override
    public List<StockStatusVertical> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<StockStatusVertical> ssvList = new LinkedList<>();
        while (rs.next()) {
            
        }
        return ssvList;
    }

}