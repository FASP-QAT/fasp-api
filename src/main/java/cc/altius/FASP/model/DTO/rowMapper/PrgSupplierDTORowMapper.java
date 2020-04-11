/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgSupplierDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgSupplierDTORowMapper implements RowMapper<PrgSupplierDTO> {
    
    @Override
    public PrgSupplierDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgSupplierDTO m = new PrgSupplierDTO();
        m.setActive(rs.getBoolean("ACTIVE"));
        m.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        m.setSupplierId(rs.getInt("SUPPLIER_ID"));
        m.setRealmId(rs.getInt("REALM_ID"));
        return m;
    }
    
}
