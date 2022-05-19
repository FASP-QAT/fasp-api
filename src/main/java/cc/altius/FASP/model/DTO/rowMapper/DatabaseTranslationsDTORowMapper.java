/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.DatabaseTranslationsDTO;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class DatabaseTranslationsDTORowMapper implements RowMapper<DatabaseTranslationsDTO> {

    @Override
    public DatabaseTranslationsDTO mapRow(ResultSet rs, int i) throws SQLException {
        DatabaseTranslationsDTO l = new DatabaseTranslationsDTO();
        l.setLabelFor(rs.getString("LABEL_FOR"));
        l.setLabel(new LabelRowMapper().mapRow(rs, i));
        l.setRelatedTo(new LabelRowMapper("RELATED_TO_").mapRow(rs, i));
        if (l.getRelatedTo().getLabelId()==0) {
            l.setRelatedTo(null);
        }
        return l;
    }

}
