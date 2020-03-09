/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgSubFundingSourceDTORowMapper implements RowMapper<PrgSubFundingSourceDTO>{

    @Override
    public PrgSubFundingSourceDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgSubFundingSourceDTO sfs=new PrgSubFundingSourceDTO();
        sfs.setActive(rs.getBoolean("ACTIVE"));
        PrgFundingSourceDTO fundingSource=new PrgFundingSourceDTO();
        fundingSource.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        sfs.setFundingSource(fundingSource);
        sfs.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        sfs.setSubFundingSourceId(rs.getInt("SUB_FUNDING_SOURCE_ID"));
        return sfs;
    }
    
    
    
}
