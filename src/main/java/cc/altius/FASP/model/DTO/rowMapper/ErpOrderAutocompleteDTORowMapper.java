/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ErpOrderAutocompleteDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ErpOrderAutocompleteDTORowMapper implements RowMapper<ErpOrderAutocompleteDTO> {
    
    @Override
    public ErpOrderAutocompleteDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        ErpOrderAutocompleteDTO d = new ErpOrderAutocompleteDTO();
        d.setId(rs.getInt("ERP_ORDER_ID"));
        d.setLabel(rs.getString("LABEL"));
        return d;
    }
    
}
