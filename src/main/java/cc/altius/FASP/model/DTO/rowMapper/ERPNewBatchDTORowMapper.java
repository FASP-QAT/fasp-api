/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ErpBatchDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ERPNewBatchDTORowMapper implements RowMapper<ErpBatchDTO> {

    @Override
    public ErpBatchDTO mapRow(ResultSet rs, int i) throws SQLException {
        ErpBatchDTO eb = new ErpBatchDTO();
        eb.setBatchId(rs.getInt("BATCH_ID"));
        eb.setBatchNo(rs.getString("BATCH_NO"));
        eb.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        eb.setStatus(-1);
        return eb;
    }

}
