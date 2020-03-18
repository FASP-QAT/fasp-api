/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgFundingSourceDTORowMapper implements RowMapper<PrgFundingSourceDTO>{

    @Override
    public PrgFundingSourceDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgFundingSourceDTO fs=new PrgFundingSourceDTO();
        fs.setActive(rs.getBoolean("ACTIVE"));
        fs.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        fs.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        fs.setRealmId(rs.getInt("REALM_ID"));
        return fs;
    }
    
    
    
}
