/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class QatTemHealthAreaRowMapper implements RowMapper<HealthArea>{

    @Override
    public HealthArea mapRow(ResultSet rs, int arg1) throws SQLException {
        HealthArea ha = new  HealthArea();
        ha.setHealthAreaId(rs.getInt("HEALTh_AREA_ID"));
        Label l = new Label();
        l.setLabel_en(rs.getString("LABEL_EN"));
        l.setLabel_fr(rs.getString("LABEL_FR"));
        l.setLabel_pr(rs.getString("LABEl_PR"));
        l.setLabel_sp(rs.getString("LABEL_SP"));
        ha.setLabel(l);
        return ha;
    }
    
}
