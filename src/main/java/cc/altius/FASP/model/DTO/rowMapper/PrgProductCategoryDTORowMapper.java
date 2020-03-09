/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgProductCategoryDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgProductCategoryDTORowMapper implements RowMapper<PrgProductCategoryDTO> {
    
    @Override
    public PrgProductCategoryDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgProductCategoryDTO pc = new PrgProductCategoryDTO();
        pc.setActive(rs.getBoolean("ACTIVE"));
        pc.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        pc.setProductCategoryId(rs.getInt("PRODUCT_CATEGORY_ID"));
        return pc;
    }
    
}
