/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.PplProduct;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PipelineProductRowMapper implements RowMapper<PplProduct> {

    @Override
    public PplProduct mapRow(ResultSet rs, int arg1) throws SQLException {
        PplProduct p = new PplProduct();
        p.setProductname(rs.getString("planningUnitId"));
        p.setProductminmonths(rs.getInt("ProductMinMonths"));
        return p;
    }

}
