/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProgramProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class ProgramProductResultSetExtractor implements ResultSetExtractor<ProgramProduct> {

    @Override
    public ProgramProduct extractData(ResultSet rs) throws SQLException, DataAccessException {
        ProgramProduct pp = new ProgramProduct();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                pp.setProgramId(rs.getInt("PROGRAM_ID"));
                pp.setLabel(new LabelRowMapper().mapRow(rs, 1));
                pp.setProductList(new LinkedList<>());
            }

            SimpleProduct sp = new SimpleProduct();
            sp.setProductId(rs.getInt("PRODUCT_ID"));
            sp.setLabel(new LabelRowMapper("PRODUCT_").mapRow(rs, 1));
            sp.setMinMonth(rs.getInt("MIN_MONTHS"));
            sp.setMaxMonth(rs.getInt("MAX_MONTHS"));
            if (pp.getProductList().indexOf(sp) == -1) {
                pp.getProductList().add(sp);
            }
            isFirst = false;
        }
        return pp;
    }
}
