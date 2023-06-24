/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleProgram;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class SimpleProgramListResultSetExtractor implements ResultSetExtractor<List<SimpleProgram>> {

    @Override
    public List<SimpleProgram> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<SimpleProgram> pList = new LinkedList<>();
        while (rs.next()) {
            SimpleProgram p = new SimpleProgram(rs.getInt("ID"));
            int idx = pList.indexOf(p);
            if (idx == -1) {
                pList.add(p);
                p.setCode(rs.getString("CODE"));
                p.setLabel(new LabelRowMapper().mapRow(rs, 1));
                p.setCurrentVersionId(rs.getInt("CURRENT_VERSION_ID"));
                p.setRealmCountry(new SimpleCodeObjectRowMapper("RC_").mapRow(rs, 1));
                p.setOrganisation(new SimpleCodeObjectRowMapper("O_").mapRow(rs, 1));
                p.setProgramTypeId(rs.getInt("PROGRAM_TYPE_ID"));
            } else {
                p = pList.get(idx);
            }
            SimpleCodeObject ha = new SimpleCodeObjectRowMapper("HA_").mapRow(rs, 1);
            idx = p.getHealthAreaList().indexOf(ha);
            if (idx == -1) {
                p.getHealthAreaList().add(ha);
            }
            SimpleObject r = new SimpleObjectRowMapper("R_").mapRow(rs, 1);
            idx = p.getRegionList().indexOf(r);
            if (idx == -1) {
                p.getRegionList().add(r);
            }
        }
        return pList;
    }

}
