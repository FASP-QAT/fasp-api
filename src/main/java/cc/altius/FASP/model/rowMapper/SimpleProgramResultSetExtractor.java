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
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class SimpleProgramResultSetExtractor implements ResultSetExtractor<SimpleProgram> {

    @Override
    public SimpleProgram extractData(ResultSet rs) throws SQLException, DataAccessException {
        boolean isFirst = true;
        SimpleProgram p = null;
        while (rs.next()) {
            if (isFirst) {
                p = new SimpleProgram(rs.getInt("ID"));
                p.setCode(rs.getString("CODE"));
                p.setLabel(new LabelRowMapper().mapRow(rs, 1));
                p.setCurrentVersionId(rs.getInt("CURRENT_VERSION_ID"));
                p.setRealmCountry(new SimpleCodeObjectRowMapper("RC_").mapRow(rs, 1));
                p.setOrganisation(new SimpleCodeObjectRowMapper("O_").mapRow(rs, 1));
                p.setProgramTypeId(rs.getInt("PROGRAM_TYPE_ID"));
                p.setActive(rs.getBoolean("ACTIVE"));
                p.setRealmId(rs.getInt("REALM_ID"));
                isFirst = false;
            }
            SimpleCodeObject ha = new SimpleCodeObjectRowMapper("HA_").mapRow(rs, 1);
            int idx = p.getHealthAreaList().indexOf(ha);
            if (idx == -1) {
                p.getHealthAreaList().add(ha);
            }
            SimpleObject r = new SimpleObjectRowMapper("R_").mapRow(rs, 1);
            idx = p.getRegionList().indexOf(r);
            if (idx == -1) {
                p.getRegionList().add(r);
            }
            SimpleCodeObject fs = new SimpleCodeObjectRowMapper("FS_").mapRow(rs, 1);
            idx = p.getFundingSourceList().indexOf(fs);
            if (idx == -1) {
                p.getFundingSourceList().add(fs);
            }
            SimpleCodeObject pa = new SimpleCodeObjectRowMapper("PA_").mapRow(rs, 1);
            idx = p.getProcurementAgentList().indexOf(pa);
            if (idx == -1) {
                p.getProcurementAgentList().add(pa);
            }
            
        }
        return p;
    }
}
