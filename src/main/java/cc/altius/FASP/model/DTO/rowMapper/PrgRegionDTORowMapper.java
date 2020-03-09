/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgRegionDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgRegionDTORowMapper implements RowMapper<PrgRegionDTO> {

    @Override
    public PrgRegionDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgRegionDTO region = new PrgRegionDTO();
        region.setRegionId(rs.getInt("REGION_ID"));
        region.setCapacityCbm(rs.getDouble("CAPACITY_CBM"));
        region.setLabel(new PrgLabelDTORowMapper("REGION_").mapRow(rs, i));
        region.setActive(rs.getBoolean("ACTIVE"));
        return region;
    }

}
