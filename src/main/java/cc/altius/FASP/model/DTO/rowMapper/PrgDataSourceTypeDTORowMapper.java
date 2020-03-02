/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgDataSourceTypeDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgDataSourceTypeDTORowMapper implements RowMapper<PrgDataSourceTypeDTO>{

    @Override
    public PrgDataSourceTypeDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgDataSourceTypeDTO dst=new PrgDataSourceTypeDTO();
        dst.setActive(rs.getBoolean("ACTIVE"));
        dst.setDataSourceTypeId(rs.getInt("DATA_SOURCE_TYPE_ID"));
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        dst.setLabel(label);
        return dst;
    }
    
    
    
}
