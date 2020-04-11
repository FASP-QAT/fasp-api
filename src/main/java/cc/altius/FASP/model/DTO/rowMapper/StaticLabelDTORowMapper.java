/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class StaticLabelDTORowMapper implements RowMapper<StaticLabelDTO> {

    @Override
    public StaticLabelDTO mapRow(ResultSet rs, int i) throws SQLException {
        StaticLabelDTO l = new StaticLabelDTO();
        l.setLabelCode(rs.getString("LABEL_CODE"));
        l.setLabel(new LabelRowMapper().mapRow(rs, i));
        return l;
    }
}
