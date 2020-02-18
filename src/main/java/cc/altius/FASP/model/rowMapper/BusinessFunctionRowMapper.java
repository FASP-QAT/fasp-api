/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class BusinessFunctionRowMapper implements RowMapper<BusinessFunction> {

    @Override
    public BusinessFunction mapRow(ResultSet rs, int i) throws SQLException {
        BusinessFunction b = new BusinessFunction();
        b.setBusinessFunctionId(rs.getString("BUSINESS_FUNCTION_ID"));
        Label l = new Label();
        l.setEngLabel(rs.getString("LABEL_EN"));
        l.setFreLabel(rs.getString("LABEL_FR"));
        l.setSpaLabel(rs.getString("LABEL_SP"));
        l.setPorLabel(rs.getString("LABEL_PR"));
        b.setLabel(l);
        return b;
    }

}
