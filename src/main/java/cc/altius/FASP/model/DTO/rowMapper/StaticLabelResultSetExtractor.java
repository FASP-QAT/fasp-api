/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.DTO.StaticLabelLanguagesDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author altius
 */
public class StaticLabelResultSetExtractor implements ResultSetExtractor<List<StaticLabelDTO>> {

    @Override
    public List<StaticLabelDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        StaticLabelDTO sld;
        StaticLabelLanguagesDTO sll;
        List<StaticLabelDTO> staticLabelList = new LinkedList<>();
        while (rs.next()) {
            sld = new StaticLabelDTO();
            sld.setStaticLabelId(rs.getInt("STATIC_LABEL_ID"));
            if (staticLabelList.indexOf(sld) == -1) {
                sld.setLabelCode(rs.getString("LABEL_CODE"));
                staticLabelList.add(sld);
            }
            sld = staticLabelList.get(staticLabelList.indexOf(sld));
            sll = new StaticLabelLanguagesDTO();
            sll.setStaticLabelLanguageId(rs.getInt("STATIC_LABEL_LANGUAGE_ID"));
            if (sld.getStaticLabelLanguages().indexOf(sll) == -1) {
                sll.setLabelText(rs.getString("LABEL_TEXT"));
                sll.setLanguageId(rs.getInt("LANGUAGE_ID"));
                sld.getStaticLabelLanguages().add(sll);
            }
        }
        return staticLabelList;
    }

}
