/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgLabelDTORowMapper implements RowMapper<PrgLabelDTO> {

    private String prefix;

    public PrgLabelDTORowMapper(String prefix) {
        this.prefix = prefix;
    }

    public PrgLabelDTORowMapper() {
        this.prefix = "";
    }

    @Override
    public PrgLabelDTO mapRow(ResultSet rs, int i) throws SQLException {
        return new PrgLabelDTO(rs.getString(prefix + "LABEL_EN"), rs.getString(prefix + "LABEL_SP"), rs.getString(prefix + "LABEL_FR"), rs.getString(prefix + "LABEL_PR"));
    }

}
