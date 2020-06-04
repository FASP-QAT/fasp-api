/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.Region;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class QatTemRegionRowMapper implements RowMapper<Region> {
    
    @Override
    public Region mapRow(ResultSet rs, int arg1) throws SQLException {
        Region r = new Region();
        r.setRegionId(rs.getInt("REGION_ID"));
        Label l = new Label();
        l.setLabel_en(rs.getString("LABEL_EN"));
        l.setLabel_fr(rs.getString("LABEL_FR"));
        l.setLabel_pr(rs.getString("LABEl_PR"));
        l.setLabel_sp(rs.getString("LABEL_SP"));
        r.setLabel(l);
        return r;
    }
    
}
