/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class HealthAreaRowMapper implements RowMapper<HealthArea> {

    @Override
    public HealthArea mapRow(ResultSet rs, int rowNum) throws SQLException {
        HealthArea ha = new HealthArea();
        ha.setHealthAreaId(rs.getInt("HEALTH_AREA_ID"));
        ha.setRealm(new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, rowNum), rs.getString("REALM_CODE")));
        ha.setLabel(new LabelRowMapper().mapRow(rs, rowNum));
        ha.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return ha;
    }

}
