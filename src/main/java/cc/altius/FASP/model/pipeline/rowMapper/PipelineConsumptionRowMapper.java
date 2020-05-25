/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.PplConsumption;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PipelineConsumptionRowMapper implements RowMapper<PplConsumption> {

    @Override
    public PplConsumption mapRow(ResultSet rs, int arg1) throws SQLException {
        PplConsumption c = new PplConsumption();
        c.setConsactualflag(rs.getBoolean("ConsActualFlag"));
        c.setConsamount(rs.getDouble("ConsAmount"));
        c.setConsdatasourceid(rs.getString("DataSourceName"));
        c.setConDate(rs.getString("consumptionDate"));
        c.setConsnote(rs.getString("ConsNote"));
        c.setProductid(rs.getString("PLANNING_UNIT_ID"));
        return c;
    }

}
